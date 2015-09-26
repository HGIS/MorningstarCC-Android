package org.morningstarcc.app.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Series")
public class Series extends Bundlable {
    @DatabaseField(id = true, columnName = "id")
	public String SeriesId;

    @DatabaseField
	public String title;

    @DatabaseField
	public String Book;

    @DatabaseField
	public String Imagelink;

    Series() {}
}
