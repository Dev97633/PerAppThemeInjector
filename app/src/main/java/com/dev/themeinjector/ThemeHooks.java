
package com.dev.themeinjector;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class ThemeHooks {

    public static void apply() {
        forceDark();
        accent();
        font();
    }

    private static void forceDark() {
        XposedHelpers.findAndHookMethod(Configuration.class, "isNightModeActive",
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    param.setResult(true);
                }
        });
    }

    private static void accent() {
        XposedHelpers.findAndHookMethod(Resources.Theme.class, "resolveAttribute",
            int.class, TypedValue.class, boolean.class,
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if ((int) param.args[0] == android.R.attr.colorAccent) {
                        ((TypedValue) param.args[1]).data = Prefs.accent();
                    }
                }
        });
    }

    private static void font() {
        XposedHelpers.findAndHookMethod(Resources.class, "getConfiguration",
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    ((Configuration) param.getResult()).fontScale = Prefs.font();
                }
        });
    }
}
