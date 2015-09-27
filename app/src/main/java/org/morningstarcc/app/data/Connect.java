package org.morningstarcc.app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Connects")
public class Connect implements Parcelable {
    @DatabaseField(id = true, columnName = "id")
	public String linkid;

    @DatabaseField
	public String haschild;

    @DatabaseField
	public String parentId;

    @DatabaseField
	public String title;

    @DatabaseField(columnName = "content")
	public String encoded;

    @DatabaseField
	public String weblink;

    @DatabaseField
    public String modifieddate;

    @DatabaseField
	public String isactive;

    public Connect() {}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(linkid);
        out.writeString(haschild);
        out.writeString(parentId);
        out.writeString(title);
        out.writeString(encoded);
        out.writeString(weblink);
        out.writeString(modifieddate);
        out.writeString(isactive);
    }

    public static final Parcelable.Creator<Connect> CREATOR = new Parcelable.Creator<Connect>() {
        public Connect createFromParcel(Parcel in) {
            return new Connect(in);
        }

        public Connect[] newArray(int size) {
            return new Connect[size];
        }
    };

    private Connect(Parcel in) {
        linkid = in.readString();
        haschild = in.readString();
        parentId = in.readString();
        title = in.readString();
        encoded = in.readString();
        weblink = in.readString();
        modifieddate = in.readString();
        isactive = in.readString();
    }
}
