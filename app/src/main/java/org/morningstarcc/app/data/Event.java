package org.morningstarcc.app.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/20/2015.
 */
@DatabaseTable(tableName = "Events")
public class Event extends Bundlable {
    @DatabaseField(id = true, columnName = "id")
    public String eventid;

    @DatabaseField
    public String eventstarttime;

    @DatabaseField
    public String eventendtime;

    @DatabaseField
    public String title;

    @DatabaseField
    public String description;

    @DatabaseField
    public String hasregistration;

    @DatabaseField
    public String registrationlink;

    @DatabaseField
    public String weblink;

    @DatabaseField
    public String imagepath;

    @DatabaseField
    public String pubDate;

    public Event() {}
}
