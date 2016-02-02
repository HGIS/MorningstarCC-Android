 //
  // If absolute URL from the remote server is provided, configure the CORS
  // header on that server.
  //
  var url = '../../../sdcard/bulletin.pdf';
var viewport;
  //
  // Disable workers to avoid yet another cross-origin issue (workers need
  // the URL of the script to be loaded, and dynamically loading a cross-origin
  // script does not work).
  //
  // PDFJS.disableWorker = true;

  //
  // The workerSrc property shall be specified.
  //
  PDFJS.workerSrc = '../build/pdf.worker.js';

  //
  // Asynchronous download PDF
  //
  PDFJS.getDocument(url).then(function getPdfHelloWorld(pdf) {
    //
    // Fetch the first page
    //
    pdf.getPage(1).then(function getPageHelloWorld(page) {
      var scale = 1.0;
      viewport = page.getViewport(scale);

      //
      // Prepare canvas using PDF page dimensions
      //
      var canvas = document.getElementById('the-canvas');
      var context = canvas.getContext('2d');
      canvas.height = viewport.height;
      canvas.width = viewport.width;

      //
      // Render PDF page into canvas context
      //
      var renderContext = {
        canvasContext: context,
        viewport: viewport
      };
      page.render(renderContext);

      var $annotationLayerDiv = $('#AnnotationLayer');

                      //extract links from the page and put them absolutely positioned over the canvas
                      annotations(page, canvas, $annotationLayerDiv);
    });
  });

  function annotations(page, canvas, $annotationLayerDiv) {
      page.getAnnotations().then(function(annotationsData) {
             viewport = viewport.clone({ dontFlip: true });
                    var transform = viewport.transform;
                    var transformStr = 'matrix(' + transform.join(',') + ')';
                    var data, element, i, ii;
                    //alert(annotationsData.length);
            for (i = 0, ii = annotationsData.length; i < ii; i++) {
                        data = annotationsData[i];
                        if (!data || !data.hasHtml) {
                          continue;
                        }
                        //alert("aaaa");
                        element = PDFJS.AnnotationUtils.getHtmlElement(data,
                          page.commonObjs);
                          //alert("eeee");
                        element.setAttribute('data-annotation-id', data.id);
                        /*if (typeof mozL10n !== 'undefined') {
                          mozL10n.translate(element);
                        }*/

                        var rect = data.rect;
                        var view = page.view;
                        rect = PDFJS.Util.normalizeRect([
                          rect[0],
                            view[3] - rect[1] + view[1],
                          rect[2],
                            view[3] - rect[3] + view[1]
                        ]);
                        element.style.left = rect[0] + 'px';
                        element.style.top = rect[1] + 'px';
                        element.style.position = 'absolute';
                        //alert("iiiii");
                        //CustomStyle.setProp('transform', element, transformStr);
                        //alert("oooo");
                        /*var transformOriginStr = -rect[0] + 'px ' + -rect[1] + 'px';
                       // CustomStyle.setProp('transformOrigin', element, transformOriginStr);

                        if (data.subtype === 'Link' && !data.url) {
                          var link = element.getElementsByTagName('a')[0];
                          if (link) {
                            if (data.action) {
                              bindNamedAction(link, data.action);
                            } else {
                              bindLink(link, ('dest' in data) ? data.dest : null);
                            }
                          }
                        }

                        $element.appendTo(element);*/
var link;//alert("oooo");
              if(data.url) {
                  link = '<a href="' + data.url + '" target="_blank">&nbsp;</a>'
              }
                        var $element = $('<div class="link">' + link + '</div>');

                                      //get the rect data and normalize
                                      var rect = data.rect;
                                      var view = page.view;
                                      rect = PDFJS.Util.normalizeRect([
                                          rect[0],
                                          view[3] - rect[1] + view[1],
                                          rect[2],
                                          view[3] - rect[3] + view[1]
                                      ]);

                                      //calculate the width and height
                                      var width = rect[2] - rect[0];
                                      var height = rect[3] - rect[1];

                                      //set the link element's position and width/height and append it to the annotation layer
                                      $element.css({'left': (rect[0]+2) + 'px', 'top': rect[1] + 'px', 'height': height + 'px', 'width': width + 'px' });

                                      //alert($element.css());

                                      $element.appendTo($annotationLayerDiv);
                        }
          /*for (var j = 0; j < annotationsData.length; j++) {
              data = annotationsData[j];
            alert("continue0");
              //var annotation = PDFJS.Annotation.fromData(annotationsData[j]);
var annotation = annotationsData[j];
              alert("continue1");
              if (!annotation || !annotation.hasHtml()) {
                alert("continue2");
                continue;
              }

              var dataObj = annotation.getData();
alert("continue3");
              //create the link HTML
              var link;
              if(dataObj.url) {
                  link = '<a href="' + dataObj.url + '" target="_blank">&nbsp;</a>'
              }
alert("continue4");
              //insert the link HTML into a div, and save the jQuery variable
              var $element = $('<div class="link">' + link + '</div>');

              //get the rect data and normalize
              var rect = data.rect;
              var view = page.view;
              rect = PDFJS.Util.normalizeRect([
                  rect[0],
                  view[3] - rect[1] + view[1],
                  rect[2],
                  view[3] - rect[3] + view[1]
              ]);

              //calculate the width and height
              var width = rect[2] - rect[0];
              var height = rect[3] - rect[1];

              //set the link element's position and width/height and append it to the annotation layer
              $element.css({'left': (rect[0]+2) + 'px', 'top': rect[1] + 'px', 'height': height + 'px', 'width': width + 'px' });
              $element.appendTo($annotationLayerDiv);

          }*/
      });
  }