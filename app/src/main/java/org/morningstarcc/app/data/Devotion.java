package org.morningstarcc.app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Devotions")
public class Devotion implements Parcelable {
    @DatabaseField(id = true, columnName = "id")
	public String devoId;

    @DatabaseField
	public String title;

    @DatabaseField(columnName = "author")
	public String creator;

    @DatabaseField(columnName = "content")
	public String encoded;

    @DatabaseField
	public String link;

    @DatabaseField
	public String pubDate;

    @DatabaseField(defaultValue = "false")
    public Boolean read;

    public Devotion() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(devoId);
        out.writeString(title);
        out.writeString(creator);
        out.writeString(encoded);
        out.writeString(link);
        out.writeString(pubDate);
        out.writeString("" + read);
    }

    public static final Parcelable.Creator<Devotion> CREATOR = new Parcelable.Creator<Devotion>() {
        public Devotion createFromParcel(Parcel in) {
            return new Devotion(in);
        }

        public Devotion[] newArray(int size) {
            return new Devotion[size];
        }
    };

    private Devotion(Parcel in) {
        devoId = in.readString();
        title = in.readString();
        creator = in.readString();
        encoded = in.readString();
        link = in.readString();
        pubDate = in.readString();
        read = Boolean.parseBoolean(in.readString());
    }
}
