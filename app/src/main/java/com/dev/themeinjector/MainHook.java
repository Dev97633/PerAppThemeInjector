package com.dev.themeinjector;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    private static boolean injected = false;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {

        // Ignore system processes (LSPosed only)
        if (lpparam.packageName.startsWith("android") ||
            lpparam.packageName.startsWith("com.android") ||
            lpparam.packageName.equals("org.lsposed.manager")) {
            return;
        }

        // 🔹 LSPosed path
        hookApplicationAttach();

        // 🔹 LSPatch fallback
        hookApplicationAttach();
    }

    private void hookApplicationAttach() {
        XposedHelpers.findAndHookMethod(
                Application.class,
                "attach",
                Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        if (injected) return;
                        injected = true;

                        // Detect framework
                        boolean isLSPosed = isLSPosed();

                        if (isLSPosed) {
                            Prefs.init(); // LSPosed prefs
                            if (!Prefs.isEnabled()) return;
                        }

                        ThemeHooks.apply();
                    }
                }
        );
    }

    private boolean isLSPosed() {
        try {
            Class.forName("org.lsposed.lspd.service.ILSPApplicationService");
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }
}

