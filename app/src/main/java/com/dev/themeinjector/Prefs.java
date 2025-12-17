
package com.dev.themeinjector;

import android.graphics.Color;
import de.robv.android.xposed.XSharedPreferences;

public class Prefs {

    private static XSharedPreferences prefs;

    static void init() {
        prefs = new XSharedPreferences("com.dev.themeinjector");
        prefs.makeWorldReadable();
    }

    static boolean isEnabled() {
        return prefs.getBoolean("enabled", true);
    }

    static int accent() {
        return prefs.getInt("accent_color", Color.parseColor("#FF6200EE"));
    }

    static float font() {
        return prefs.getFloat("font_scale", 1.1f);
    }
}
