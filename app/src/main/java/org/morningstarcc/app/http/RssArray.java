package org.morningstarcc.app.http;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyle on 9/24/2015.
 */
public class RssArray {
    private List<HashMap<String, String>> items;

    public RssArray() {
        items = new ArrayList<>();
    }

    public void add(HashMap<String, String> item) {
        items.add(item);
    }

    public int size() {
        return items.size();
    }

    public <T> List<T> convert(Class<T> clazz) {
        List<T> conversion = new ArrayList<>(items.size());

        try {
            for (HashMap<String, String> item : items) {
                T object = clazz.newInstance();

                for (Map.Entry<String, String> field : item.entrySet()) {
                    try {
                        clazz.getDeclaredField(field.getKey()).set(object, field.getValue().replace(":", ""));
                    } catch (NoSuchFieldException ignored) {
                        Log.e("Rss", "No such field " + field.getKey() + " for " + clazz);
                    } catch (IllegalAccessException ignored) {
                        Log.e("Rss", "Cannot access field " + field.getKey());
                    }
                }

                conversion.add(object);
            }
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        return conversion;
    }
}
