package org.morningstarcc.app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Studies")
public class Study implements Parcelable {
    @DatabaseField(id = true, columnName = "id")
	public String StudyId;

    @DatabaseField
	public String SeriesId;

    @DatabaseField
	public String Speaker;

    @DatabaseField
	public String StudyDate;

    @DatabaseField
	public String title;

    @DatabaseField
	public String Scripture;

    @DatabaseField
	public String Description;

    @DatabaseField
	public String AudioLink;

    @DatabaseField
	public String VideoLink;

    @DatabaseField
    public String ModifiedDate;

    public Study() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(StudyId);
        out.writeString(SeriesId);
        out.writeString(Speaker);
        out.writeString(StudyDate);
        out.writeString(title);
        out.writeString(Scripture);
        out.writeString(Description);
        out.writeString(AudioLink);
        out.writeString(VideoLink);
        out.writeString(ModifiedDate);
    }

    public static final Parcelable.Creator<Study> CREATOR = new Parcelable.Creator<Study>() {
        public Study createFromParcel(Parcel in) {
            return new Study(in);
        }

        public Study[] newArray(int size) {
            return new Study[size];
        }
    };

    private Study(Parcel in) {
        StudyId = in.readString();
        SeriesId = in.readString();
        Speaker = in.readString();
        StudyDate = in.readString();
        title = in.readString();
        Scripture = in.readString();
        Description = in.readString();
        AudioLink = in.readString();
        VideoLink = in.readString();
        ModifiedDate = in.readString();
    }
}
