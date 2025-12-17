package com.dev.themeinjector;

import android.graphics.Color;
import de.robv.android.xposed.XSharedPreferences;

public class Prefs {

    private static XSharedPreferences prefs;
    private static boolean available = false;

    static void init() {
        try {
            prefs = new XSharedPreferences("com.dev.themeinjector");
            prefs.makeWorldReadable();
            available = true;
        } catch (Throwable t) {
            available = false;
        }
    }

    static boolean isEnabled() {
        return available ? prefs.getBoolean("enabled", true) : true;
    }

    static int accent() {
        return available
                ? prefs.getInt("accent_color", Color.parseColor("#FF6200EE"))
                : Color.parseColor("#FF6200EE");
    }

    static float font() {
        return available
                ? prefs.getFloat("font_scale", 1.1f)
                : 1.1f;
    }
}

