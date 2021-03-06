package org.morningstarcc.app.http;

import android.app.Application;
import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.morningstarcc.app.data.Connect;
import org.morningstarcc.app.data.Devotion;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.data.SeriesCategory;
import org.morningstarcc.app.data.Study;
import org.morningstarcc.app.database.Database;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.android.volley.Request.Method.GET;

/**
 * Created by Kyle on 9/20/2015.
 * 11/21/2015 - Juan Manuel Gomez - Use new constructor for Series to send the SeriesType
 * 12/01/2015 - Juan Manuel Gomez - Added refresh functionality for Events
 */
public class DataService {
    public static final String UrlPrefix = "http://www.morningstarcc.org/";
    public static final String EventsUrl = "MCCEventsRSS.aspx";
    public static final String DevotionsUrl = "MCCDailyDevoRSS.aspx";
    public static final String ConnectsUrl = "MCCConnectWithUsLinksRSS.xml";
    public static final String SeriesCategoriesUrl = "MCCCurrentStudySeriesTypesRSS.aspx";
    public static final String SeriesUrl = "MCCStudySeriesByTypeRSS.aspx?typeid=";
    public static final String StudiesUrl = "MCCStudiesinseriesRSS.aspx?SeriesId=";
    public static final String BulletinUrl = "Uploads/BulletinMobile.pdf";
    public static final String LiveStreamUrl = "http://new.livestream.com/accounts/381375/events/3355193/player?autoPlay=true&amp;mute=false";

    private static final Map<Class<? extends Parcelable>, String> urlMap;

    static {
        urlMap = new HashMap<>(6);

        urlMap.put(Event.class, EventsUrl);
        urlMap.put(Devotion.class, DevotionsUrl);
        urlMap.put(Connect.class, ConnectsUrl);
        urlMap.put(SeriesCategory.class, SeriesCategoriesUrl);
        urlMap.put(Series.class, SeriesUrl);
        urlMap.put(Study.class, StudiesUrl);
    }

    private static RequestQueue queue;

    public static void init(Application context) {
        queue = Volley.newRequestQueue(context);
    }

    public static void updateAll(Context context, final Listener<Void> finishedCallback) {
        final Database database = OpenHelperManager.getHelper(context, Database.class);
        final AtomicInteger numQueries = new AtomicInteger(4);
        final Listener<Integer> decrementListener = new Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                if (response == 0) {
                    OpenHelperManager.releaseHelper();
                    finishedCallback.onResponse(null);
                }
            }
        };
        final ErrorListener decrementErrorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("Volley", "Failed call " + new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers)));
                } catch (UnsupportedEncodingException e) {
                    Log.e("Volley", "Failed call " + error);
                } catch (NullPointerException e) {
                    Log.e("Volley", "Failed call");
                }
                decrementListener.onResponse(numQueries.decrementAndGet());
            }
        };

        get(Event.class, new UpdateDbListener<>(Event.class, database, numQueries, decrementListener), decrementErrorListener);
        get(Devotion.class, new UpdateDbListener<>(Devotion.class, database, numQueries, decrementListener), decrementErrorListener);
        get(Connect.class, new UpdateDbListener<>(Connect.class, database, numQueries, decrementListener), decrementErrorListener);
        get(SeriesCategory.class, new UpdateDbListener<>(SeriesCategory.class, database, new Listener<List<SeriesCategory>>() {
            @Override
            public void onResponse(List<SeriesCategory> categories) {
                Log.d("Volley", "Queuing " + categories.size() + " requests for series");
                for (SeriesCategory category : categories) {
                    numQueries.incrementAndGet();
                    get(Series.class, category.SeriesType, new UpdateDbListener<>(Series.class, database, category.SeriesType, new Listener<List<Series>>() {
                        @Override
                        public void onResponse(List<Series> seriesList) {
                            Log.d("Volley", "Queuing " + seriesList.size() + " requests for studies");
                            for (Series series : seriesList) {
                                numQueries.incrementAndGet();
                                get(Study.class, series.SeriesId, new UpdateDbListener<>(Study.class, database, numQueries, decrementListener), decrementErrorListener);
                            }
                            decrementListener.onResponse(numQueries.decrementAndGet());

                        }
                    }), decrementErrorListener);
                }
                decrementListener.onResponse(numQueries.decrementAndGet());
            }
        }), decrementErrorListener);
    }

    public static void updateEvents(Context context, final Listener<Void> finishedCallback) {
        final Database database = OpenHelperManager.getHelper(context, Database.class);
        final AtomicInteger numQueries = new AtomicInteger(4);
        final Listener<Integer> decrementListener = new Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                OpenHelperManager.releaseHelper();
                finishedCallback.onResponse(null);
            }
        };
        final ErrorListener decrementErrorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("Volley", "Failed call " + new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers)));
                } catch (UnsupportedEncodingException e) {
                    Log.e("Volley", "Failed call " + error);
                } catch (NullPointerException e) {
                    Log.e("Volley", "Failed call");
                }
                decrementListener.onResponse(numQueries.decrementAndGet());
            }
        };

        get(Event.class, new UpdateDbListener<>(Event.class, database, numQueries, decrementListener), decrementErrorListener);
    }

    public static void updateDevotion(Context context, final Listener<Void> finishedCallback) {
        final Database database = OpenHelperManager.getHelper(context, Database.class);
        final AtomicInteger numQueries = new AtomicInteger(4);
        final Listener<Integer> decrementListener = new Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                OpenHelperManager.releaseHelper();
                finishedCallback.onResponse(null);
            }
        };
        final ErrorListener decrementErrorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("Volley", "Failed call " + new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers)));
                } catch (UnsupportedEncodingException e) {
                    Log.e("Volley", "Failed call " + error);
                } catch (NullPointerException e) {
                    Log.e("Volley", "Failed call");
                }
                decrementListener.onResponse(numQueries.decrementAndGet());
            }
        };

        get(Devotion.class, new UpdateDbListener<>(Devotion.class, database, numQueries, decrementListener), decrementErrorListener);
    }

    public static <T extends Parcelable> void get(Class<T> clazz, Listener<RssArray> listener, ErrorListener errorListener) {
        queue.add(new RssRequest(UrlPrefix + urlMap.get(clazz), listener, errorListener));
    }

    public static <T extends Parcelable> void get(Class<T> clazz, String parentId, Listener<RssArray> listener, ErrorListener errorListener) {
        queue.add(new RssRequest(UrlPrefix + urlMap.get(clazz) + parentId, listener, errorListener));
    }

    public static void getBulletin(Listener<String> listener, ErrorListener errorListener) {
        queue.add(new StringRequest(GET, UrlPrefix + BulletinUrl, listener, errorListener));
    }

    public static <T extends Parcelable> List<T> getFromCache(Context context, Class<T> clazz) {
        try {
            List<T> items = OpenHelperManager.getHelper(context, Database.class).getDao(clazz).queryForAll();
            if (items.size() > 0) return items;
        } catch (SQLException ignored) {
        }

        OpenHelperManager.releaseHelper();
        return null;
    }
}
