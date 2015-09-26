package org.morningstarcc.app.data;

import android.os.Bundle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Connects")
public class Connect extends Bundlable {
    @DatabaseField(id = true, columnName = "id")
	String linkid;

    @DatabaseField
	String haschild;

    @DatabaseField
	String parentId;

    @DatabaseField
	String title;

    @DatabaseField(columnName = "content")
	String contentencoded;

    @DatabaseField
	String weblink;

    @DatabaseField
	String modifieddate;

    @DatabaseField
	String isactive;
}
