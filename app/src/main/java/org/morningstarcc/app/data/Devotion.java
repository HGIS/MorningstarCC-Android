package org.morningstarcc.app.data;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kyle on 9/25/2015.
 */
public class Devotion {
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

}
