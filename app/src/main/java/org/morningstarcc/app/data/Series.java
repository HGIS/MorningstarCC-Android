package org.morningstarcc.app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Series")
public class Series implements Parcelable {
    @DatabaseField(id = true, columnName = "id")
	public String SeriesId;

    @DatabaseField
	public String title;

    @DatabaseField
	public String Book;

    @DatabaseField
	public String Imagelink;

    @DatabaseField
    public String LastStudyDate;

    @DatabaseField
    public String StudyCount;

    public Series() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(SeriesId);
        out.writeString(title);
        out.writeString(Book);
        out.writeString(Imagelink);
        out.writeString(LastStudyDate);
        out.writeString(StudyCount);
    }

    public static final Parcelable.Creator<Series> CREATOR = new Parcelable.Creator<Series>() {
        public Series createFromParcel(Parcel in) {
            return new Series(in);
        }

        public Series[] newArray(int size) {
            return new Series[size];
        }
    };

    private Series(Parcel in) {
        SeriesId = in.readString();
        title = in.readString();
        Book = in.readString();
        Imagelink = in.readString();
        LastStudyDate = in.readString();
        StudyCount = in.readString();
    }
}
