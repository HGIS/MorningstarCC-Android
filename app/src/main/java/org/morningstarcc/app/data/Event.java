package org.morningstarcc.app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/20/2015.
 */
@DatabaseTable(tableName = "Events")
public class Event implements Parcelable {
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

    @DatabaseField
    public String calendarOnly;

    @DatabaseField
    public String ModifiedDate;

    public Event() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(eventid);
        out.writeString(eventstarttime);
        out.writeString(eventendtime);
        out.writeString(title);
        out.writeString(description);
        out.writeString(hasregistration);
        out.writeString(registrationlink);
        out.writeString(weblink);
        out.writeString(imagepath);
        out.writeString(pubDate);
        out.writeString(calendarOnly);
        out.writeString(ModifiedDate);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Event(Parcel in) {
        eventid = in.readString();
        eventstarttime = in.readString();
        eventendtime = in.readString();
        title = in.readString();
        description = in.readString();
        hasregistration = in.readString();
        registrationlink = in.readString();
        weblink = in.readString();
        imagepath = in.readString();
        pubDate = in.readString();
        calendarOnly = in.readString();
        ModifiedDate = in.readString();
    }
}
