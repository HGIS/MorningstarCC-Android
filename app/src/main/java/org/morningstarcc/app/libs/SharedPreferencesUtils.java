package org.morningstarcc.app.libs;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

/**
 * Created by Kyle on 9/24/2015.
 */
public class SharedPreferencesUtils {
    private static final String FTU = "FTU";

    private static SharedPreferences mPrefs;

    public static void init(Application context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean getIsFTU() {
        boolean tmp = mPrefs.getBoolean(FTU, true);

        if (tmp) applyCompat(mPrefs.edit().putBoolean(FTU, false));

        return tmp;
    }

    private static void applyCompat(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }
}
