
package com.dev.themeinjector;

import android.app.Application;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class MainHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (lpparam.packageName.startsWith("android") ||
            lpparam.packageName.startsWith("com.android") ||
            lpparam.packageName.equals("org.lsposed.manager")) return;

        findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Prefs.init();
                if (!Prefs.isEnabled()) return;
                ThemeHooks.apply();
            }
        });
    }
}
