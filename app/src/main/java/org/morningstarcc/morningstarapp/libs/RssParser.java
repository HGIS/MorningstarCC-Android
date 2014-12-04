package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Kyle on 8/10/2014.
 *
 * Statically parses an Rss Feed from a given InputStream using an XmlPullParser, returning a List
 *  of ContentValues containing the fields of each item tag in the feed.
 *
 *  E.g.,
 *      <html>
 *          ...
 *          <item>
 *              -- Puts these mappings into ContentValues --
 *          </item>
 *          ...
 *      </html>
 *
 */
public class RssParser {

    /**
     * Public facing method that allows other classes to get a list of ContentValues as explained above.
     *
     * @param src   Rss Feed stream
     * @return      read entries
     */
    // TODO: apparently SAX parser is faster version of the same thing
    public static List<ContentValues> parse(InputStream src) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(src);

            if (document.hasChildNodes())
                return readItems(document.getElementsByTagName("item"));
        }
        catch (ParserConfigurationException e) {
            Log.e("RssParser", Log.getStackTraceString(e));
        }
        catch (SAXException e) {
            Log.e("RssParser", Log.getStackTraceString(e));
        }
        catch (IOException e) {
            Log.e("RssParser", Log.getStackTraceString(e));
        }
        catch (IllegalArgumentException e) {
            Log.e("RssParser", Log.getStackTraceString(e));
        }

        return new ArrayList<ContentValues>();
    }

    // TODO: break failure down, so I can at least get some items -- i think this requires SAX, since it builds everything initially
    private static List<ContentValues> readItems(NodeList nodeList) {
        List<ContentValues> items = new ArrayList<ContentValues>(nodeList.getLength());

        for (int i = 0; i < nodeList.getLength(); i++) {
            items.add(readItem((Element) nodeList.item(i)));
        }

        return items;
    }

    private static ContentValues readItem(Element element) {
        ContentValues item = new ContentValues();
        NodeList nodes = element.getElementsByTagName("*");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node cur = nodes.item(i);

            item.put('\"' + cur.getNodeName() + '\"', cur.getTextContent());
        }

        return item;
    }
}