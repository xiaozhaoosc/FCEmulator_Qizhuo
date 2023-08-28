package site.ken.framework.base;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import site.ken.framework.EmulatorException;
import site.ken.framework.ui.gamegallery.GalleryActivity;
import site.ken.framework.utils.NLog;

public class EmulatorUtils {

    private static final String TAG = EmulatorUtils.class.getSimpleName();
    public static String getBaseDir(Context context) {
        File dir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalFilesDir(null);
        }
        if (dir == null) {
            dir = context.getFilesDir();
        }
        if (dir == null || !dir.exists()) {
            throw new EmulatorException("No working directory");
        }
        String dirPath = dir.getAbsolutePath();
        NLog.i(TAG, "EmulatorUtils " + dirPath);
        NLog.i(TAG, "context.getExternalFilesDir() " + context.getExternalFilesDir(null));
        return dirPath;
    }
}
