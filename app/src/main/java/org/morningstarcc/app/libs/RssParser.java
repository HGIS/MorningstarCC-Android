package org.morningstarcc.app.libs;

import android.util.Log;

import org.morningstarcc.app.http.XmlArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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
    public static XmlArray parse(byte[] data, String encoding) {
        return parse(new ByteArrayInputStream(data), encoding);
    }

    private static XmlArray parse(InputStream stream, String encoding) {
        XmlArray xmlArray = new XmlArray();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser;
            String text = null;
            HashMap<String, String> item = null;

            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(stream, encoding);

            for (int eventType = parser.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
                String tag = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tag.equalsIgnoreCase("item")) {
                            item = new HashMap<>();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tag.equalsIgnoreCase("item")) {
                            xmlArray.add(item);
                            item = null;
                        } else if (item != null) {
                            item.put(tag, text);
                        }
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            Log.e("RssParser", e.toString());
        } catch (IOException e) {
            Log.e("RssParser", e.toString());
        }

        return xmlArray;
    }
}