package org.morningstarcc.app.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Connects")
public class Connect {
    @DatabaseField
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
