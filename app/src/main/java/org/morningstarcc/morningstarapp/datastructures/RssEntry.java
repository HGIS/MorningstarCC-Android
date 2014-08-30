package org.morningstarcc.morningstarapp.datastructures;

import java.util.ArrayList;

/**
 * Created by Kyle on 8/24/2014.
 */
public abstract class RssEntry {

    public abstract ArrayList<String> getDesiredTags();
    public abstract String get(String tag);
}
