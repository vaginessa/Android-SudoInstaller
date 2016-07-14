/*
 * Sudo for Android Installer - SudoInstLib-AssetMgr
 * SudoInstLib-AssetMgr (A decompilation of the DRGAPI-AssetManager functions that I lost the code for, but still needed)
 * com.darkrealmgaming.androidsudo.AssetManager
 *
 * I've decompiled the old APK and ripped the functions I needed out of it since I lost the original source.
 * This class exists ONLY for the purpose of being able to compile this app without the DRGAPI-AssetManager files that I've lost.
 * It is not well documented, and should not be used for other tasks.
 *
 * Copyright (c) 2016 ColtonDRG.com
 * Licensed under The MIT License (https://github.com/ColtonDRG/Android-SudoInstaller/blob/master/LICENSE)
 */

package com.darkrealmgaming.androidsudo;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import eu.chainfire.libsuperuser.Shell.SU;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetManager {

    public static final String LOGTAG = "SudoInstLib-AssetMgr";

    public static boolean ExtractToAppCache(Context context, String fileAsset, String output) {
        Log.i(LOGTAG, "Starting extraction of asset file \"" + fileAsset + "\"...");
        Log.i(LOGTAG, "The file will be extracted as \"" + context.getCacheDir() + "/" + output + "\"");
        try {
            InputStream in = context.getAssets().open(fileAsset);
            OutputStream out = new FileOutputStream(new File(context.getCacheDir() + "/" + output));
            try {
                copyFile(in, out);
                in.close();
                out.flush();
                out.close();
                Log.i(LOGTAG, "Successfully extracted asset file!");
                return true;
            } catch (IOException e) {
                OutputStream outputStream = out;
                Log.e(LOGTAG, "Failed to extract asset file!");
                return false;
            }
        } catch (IOException e2) {
            Log.e(LOGTAG, "Failed to extract asset file!");
            return false;
        }
    }

    public static boolean OpenRecoveryScript(Context context, String fileAsset) {
        Log.i(LOGTAG, "Loading OpenRecoveryScript from \"" + fileAsset + "\"...");
        try {
            InputStream in = context.getAssets().open(fileAsset);
            OutputStream out = new FileOutputStream(new File(context.getCacheDir() + "/openrecoveryscript"));
            try {
                copyFile(in, out);
                in.close();
                out.flush();
                out.close();
                Log.i(LOGTAG, "Successfully extracted OpenRecoveryScript!");
                Log.i(LOGTAG, "Loading OpenRecoveryScript into System...");
                SU.run("cp " + context.getCacheDir() + "/openrecoveryscript /cache/recovery/openrecoveryscript");
                Log.i(LOGTAG, "Loaded OpenRecoveryScript into system!");
                return true;
            } catch (IOException e) {
                OutputStream outputStream = out;
                Log.e(LOGTAG, "Failed to load OpenRecoveryScript into system!");
                return false;
            }
        } catch (IOException e2) {
            Log.e(LOGTAG, "Failed to load OpenRecoveryScript into system!");
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int read = in.read(buffer);
            if (read != -1) {
                out.write(buffer, 0, read);
            } else {
                return;
            }
        }
    }

}
