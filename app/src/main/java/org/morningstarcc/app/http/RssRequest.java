package org.morningstarcc.app.http;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.morningstarcc.app.libs.RssParser;

/**
 * Created by Kyle on 9/20/2015.
 */
public class RssRequest extends Request<RssArray> {
    private final Response.Listener<RssArray> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     */
    public RssRequest(String url, Response.Listener<RssArray> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(RssArray response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<RssArray> parseNetworkResponse(NetworkResponse response) {
        Log.d("Volley", "Response " + response.statusCode + " from " + getUrl());
        RssArray parsed = RssParser.parse(response.data, HttpHeaderParser.parseCharset(response.headers));

        if (parsed == null || parsed.size() == 0) {
            return Response.error(new VolleyError(response));
        } else {
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}
