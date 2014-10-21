package org.morningstarcc.morningstarapp.libs;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyle on 8/10/2014.
 * TODO: this is buggged to return nuh-zing
 */
public class RssParser {

    private static final String TAG = "RssParser";

    private static XmlPullParser xpp;

    /** */

    public static ArrayList<HashMap<String, String>> parse(InputStream src) {
        ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();

        try {
            getPullParser();

            xpp.setInput(src, "UTF-8");
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("item")) {
                    items.add(readEntry());
                }

                eventType = xpp.next();
            }
        }
        catch (XmlPullParserException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return items;
    }

    private static void getPullParser() throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(true);

        xpp = factory.newPullParser();
    }

    /** */
    /*public static ArrayList< HashMap<String, String> > parse(InputStream src) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            parser.setInput(src, "UTF-8");
            //parser.nextTag();

            return readFeed(parser);
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            if (src == null) {
                Log.e(TAG, "InputStream src is null");
            }
        }

        return null;
    }

    private static ArrayList< HashMap<String, String> > readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList< HashMap<String, String> > data = new ArrayList< HashMap<String, String> >();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            //Log.i(TAG, "\t using");
            String name = parser.getName();
            // Starts by looking for the item tag
            if (name.equals("item")) {
                data.add(readEntry(parser));
            }
            else {
                skip(parser);
            }
        }
        return data;
    }

    */// Parses the contents of an entry. If it encounters a start tag, it hands it off
    // to the read method for processing.
    private static HashMap<String, String> readEntry() throws XmlPullParserException, IOException {
        HashMap<String, String> item = new HashMap<String, String>();

        xpp.require(XmlPullParser.START_TAG, null, "item");
        while (xpp.next() != XmlPullParser.END_TAG) {
            if (xpp.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            item.put(xpp.getName(), readField());
        }

        xpp.require(XmlPullParser.END_TAG, null, "item");

        return item;
    }

    private static String readField() throws IOException, XmlPullParserException {
        String fieldName = xpp.getName(),
               value = readText();

        xpp.require(XmlPullParser.END_TAG, null, fieldName);
        
        return value;
    }

    // For the tags title and summary, extracts their text values.
    private static String readText() throws IOException, XmlPullParserException {
        String result = "";

        if (xpp.next() == XmlPullParser.TEXT) {
            result = xpp.getText();
            xpp.nextTag();
        }

        return result;
    }/*

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }

        int tag = parser.next();
        for (int depth = 1; depth != 0; tag = parser.next()) {
            if (tag == XmlPullParser.END_TAG) {
                depth--;
            }
            else if (tag == XmlPullParser.START_TAG) {
                depth++;
            }
        }
    }*/
}
