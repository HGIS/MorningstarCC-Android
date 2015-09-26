package org.morningstarcc.app.data;

import android.os.Bundle;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kyle on 9/25/2015.
 */
public class Bundlable {
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        for (Field f : getClass().getDeclaredFields()) {
            try {
                bundle.putString(f.getName(), f.get(this).toString());
            } catch (IllegalAccessException ignored) {
            }
        }

        return bundle;
    }

    public static Bundle[] bundle(List<? extends Bundlable> bundlables) {
        Bundle[] bundles = new Bundle[bundlables.size()];
        Iterator<? extends Bundlable> iterator = bundlables.iterator();

        for (int i = 0; iterator.hasNext(); i++) {
            bundles[i] = iterator.next().toBundle();
        }

        return bundles;
    }

    public static <T extends Bundlable> T unbundle(Bundle bundle, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T object = clazz.newInstance();

        for (String fieldName : bundle.keySet()) {
            try {
                clazz.getDeclaredField(fieldName).set(object, bundle.getString(fieldName));
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }

        return object;
    }
}
