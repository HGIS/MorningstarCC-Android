package org.morningstarcc.app.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kyle on 9/25/2015.
 */
@DatabaseTable(tableName = "Studies")
public class Study extends Bundlable {
    @DatabaseField(columnName = "id")
	String StudyId;

    @DatabaseField
	String SeriesId;

    @DatabaseField
	String Speaker;

    @DatabaseField
	String StudyDate;

    @DatabaseField
	String title;

    @DatabaseField
	String Scripture;

    @DatabaseField
	String Description;

    @DatabaseField
	String AudioLink;

    @DatabaseField
	String VideoLink;

    public Study() {}
}
