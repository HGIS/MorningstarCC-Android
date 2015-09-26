package org.morningstarcc.app.data;

import android.os.Bundle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Devotions")
public class Devotion extends Bundlable {
    @DatabaseField(id = true, columnName = "id")
	String devoId;

    @DatabaseField
	String title;

    @DatabaseField(columnName = "author")
	String dccreator;

    @DatabaseField(columnName = "content")
	String contentencoded;

    @DatabaseField
	String link;

    @DatabaseField
	String pubDate;

    @DatabaseField(defaultValue = "false")
    boolean read;
}
