package org.morningstarcc.app.data;

/**
 * Created by Kyle on 9/20/2015.
 */
public class Event {
    public String eventid;
    public String eventstarttime;
    public String eventendtime;
    public String title;
    public String description;
    public String hasregistration;
    public String registrationlink;
    public String weblink;
    public String imagepath;
    public String pubDate;

    @Override
    public String toString() {
        return String.format("%s (%s)", title, eventid);
    }
}
