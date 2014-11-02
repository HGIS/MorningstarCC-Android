package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
 *              -- PUTS THESE VALUES INTO CONTENTVALUES --
 *          </item>
 *          ...
 *      </html>
 *
 */
public class RssParser {

    private static XmlPullParser xpp;

    /**
     * Public facing method that allows other classes to get a list of ContentValues as explained above.
     *
     * @param src   Rss Feed stream
     * @return      read entries
     */
    public static List<ContentValues> parse(InputStream src) {

        // TODO: DOM interface so i can get the thingies
        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(src);
            return traverse(document.getDocumentElement());
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

        return new ArrayList<ContentValues>();
    }

    private static List<ContentValues> traverse(Node node) {
        List<ContentValues> items = new ArrayList<ContentValues>();

        if (node.getNodeName().equals("item")) {
            items.add(readItem(node.getChildNodes()));
        }
        else {
            NodeList list = node.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                items.addAll( traverse(list.item(i)) );
            }
        }

        return items;
    }

    private static ContentValues readItem(NodeList fields) {
        ContentValues item = new ContentValues();

        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);

            item.put(field.getNodeName(), field.getNodeValue());
        }

        return item;
    }*/
        List<ContentValues> items = new ArrayList<ContentValues>();

        try {
            if (xpp == null) { getPullParser(); }

            xpp.setInput(src, "UTF-8");

            for (int eventType = xpp.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = xpp.next()) {
                if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("item")) {
                    items.add(readEntry());
                }
            }
        }
        catch (XmlPullParserException e) {
            Log.e(RssParser.class.getName(), Log.getStackTraceString(e));
        }
        catch (IOException e) {
            Log.e(RssParser.class.getName(), Log.getStackTraceString(e));
        }

        return items;
    }

    // sets up the static instance of the pull parser
    private static void getPullParser() throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(true);
        xpp = factory.newPullParser();
        xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
    }

    // Parses the contents of an entry. If it encounters a start tag, it hands it off
    // to the read method for processing.
    private static ContentValues readEntry() throws XmlPullParserException, IOException {
        ContentValues item = new ContentValues();

        xpp.require(XmlPullParser.START_TAG, null, "item");
        while (xpp.next() != XmlPullParser.END_TAG) {
            if (xpp.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            item.put(xpp.getName(), removeCDATA(readField()));
        }

        xpp.require(XmlPullParser.END_TAG, null, "item");

        return item;
    }

    // reads the text in between opening and closing of a tag and returns it
    private static String readField() throws IOException, XmlPullParserException {
        String fieldName = xpp.getName();
        String value = readText();

        xpp.require(XmlPullParser.END_TAG, null, fieldName);
        return value;
    }

    // extracts text values for the items
    private static String readText() throws IOException, XmlPullParserException {
        String result = "";

        if (xpp.next() == XmlPullParser.TEXT) {
            result = xpp.getText();
            xpp.nextTag();
        }

        return result;
    }

    private static final String CDATA = "![CDATA[";
    private static String removeCDATA(String in) {
        return in.contains(CDATA) ? in.substring(CDATA.length(), in.length() - 2) : in;
    }
}
