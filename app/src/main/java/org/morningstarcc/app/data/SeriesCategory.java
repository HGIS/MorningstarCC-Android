package org.morningstarcc.app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "SeriesCategories")
public class SeriesCategory implements Parcelable {
    @DatabaseField
    public String SeriesId;

    @DatabaseField
    public String LastStudyDate;

    @DatabaseField
    public String StudyCount;

    @DatabaseField
    public String title;

    @DatabaseField
    public String Book;

    @DatabaseField
    public String Imagelink;

    @DatabaseField(id = true, columnName = "id")
    public String SeriesType;

    @DatabaseField
    public String SeriesTypeSortOrder;

    @DatabaseField
    public String SeriesTypeDesc;

    public SeriesCategory() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(SeriesId);
        out.writeString(LastStudyDate);
        out.writeString(StudyCount);
        out.writeString(title);
        out.writeString(Book);
        out.writeString(Imagelink);
        out.writeString(SeriesType);
        out.writeString(SeriesTypeSortOrder);
        out.writeString(SeriesTypeDesc);
    }

    public static final Parcelable.Creator<SeriesCategory> CREATOR = new Parcelable.Creator<SeriesCategory>() {
        public SeriesCategory createFromParcel(Parcel in) {
            return new SeriesCategory(in);
        }

        public SeriesCategory[] newArray(int size) {
            return new SeriesCategory[size];
        }
    };

    private SeriesCategory(Parcel in) {
        SeriesId = in.readString();
        LastStudyDate = in.readString();
        StudyCount = in.readString();
        title = in.readString();
        Book = in.readString();
        Imagelink = in.readString();
        SeriesType = in.readString();
        SeriesTypeSortOrder = in.readString();
        SeriesTypeDesc = in.readString();
    }
}
