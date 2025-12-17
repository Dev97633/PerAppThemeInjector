package com.dev.themeinjector;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class ThemeHooks {

    private static boolean applied = false;

    public static void apply() {
        if (applied) return;
        applied = true;

        safeForceDark();
        safeAccent();
        safeFont();
    }

    /* 🌙 Force Dark Mode (LSPosed + LSPatch safe) */
    private static void safeForceDark() {
        try {
            XposedHelpers.findAndHookMethod(
                    Configuration.class,
                    "isNightModeActive",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            param.setResult(true);
                        }
                    }
            );
        } catch (Throwable ignored) {}
    }

    /* 🎨 Accent Color Injection */
    private static void safeAccent() {
        try {
            XposedHelpers.findAndHookMethod(
                    Resources.Theme.class,
                    "resolveAttribute",
                    int.class,
                    TypedValue.class,
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            int attr = (int) param.args[0];
                            if (attr == android.R.attr.colorAccent) {
                                TypedValue out = (TypedValue) param.args[1];
                                out.type = TypedValue.TYPE_INT_COLOR_ARGB8;
                                out.data = Prefs.accent();
                            }
                        }
                    }
            );
        } catch (Throwable ignored) {}
    }

    /* 🔤 Font Scale (defensive) */
    private static void safeFont() {
        try {
            XposedHelpers.findAndHookMethod(
                    Resources.class,
                    "getConfiguration",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            Configuration config = (Configuration) param.getResult();
                            if (config != null) {
                                config.fontScale = Prefs.font();
                            }
                        }
                    }
            );
        } catch (Throwable ignored) {}
    }
}

