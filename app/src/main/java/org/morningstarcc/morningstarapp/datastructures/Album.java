package org.morningstarcc.morningstarapp.datastructures;

/**
 * Created by Kyle on 8/2/2014.
 */
public class Album {
    public final String title;
    public final String link;
    public final String summary;

    public Album(String title, String summary, String link) {
        this.title = title;
        this.summary = summary;
        this.link = link;
    }
}
