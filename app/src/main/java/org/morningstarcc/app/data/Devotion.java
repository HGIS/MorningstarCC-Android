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
	public String devoId;

    @DatabaseField
	public String title;

    @DatabaseField(columnName = "author")
	public String dccreator;

    @DatabaseField(columnName = "content")
	public String contentencoded;

    @DatabaseField
	public String link;

    @DatabaseField
	public String pubDate;

    @DatabaseField(defaultValue = "false")
    public Boolean read;
}
