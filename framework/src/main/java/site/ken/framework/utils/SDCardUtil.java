/**
 * Based on post in http://stackoverflow.com/questions/5694933/find-an-external-sd-card-location
 * author: http://stackoverflow.com/users/565319/richard
 */
package site.ken.framework.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class SDCardUtil {

    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "externalSdCard";
    private static final String TAG = "utils.SDCardUtil";

    /**
     * @return True if the external storage is available. False otherwise.
     */
    public static boolean isAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }

    /**
     * @return True if the external storage is writable. False otherwise.
     */
    public static boolean isWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);

    }

    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    /**
     * @return A map of all storage locations available
     */
    public static HashSet<File> getAllStorageLocations(Context context) {
        HashSet<String> sdcards = new HashSet<>(3);
        sdcards.add("/mnt/sdcard");
        sdcards.add(SD_PATH+ "/fcgamezip");
        sdcards.add(Environment.getExternalStorageState()+ "/fcgamezip");
        sdcards.add(Environment.getExternalStorageDirectory()+ "/fcgamezip");
        sdcards.add(Environment.getDataDirectory().getAbsolutePath()+ "/fcgamezip");
        sdcards.add(Environment.getDownloadCacheDirectory().getAbsolutePath()+ "/fcgamezip");
        sdcards.add(Environment.getRootDirectory().getAbsolutePath()+ "/fcgamezip");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            sdcards.add(Environment.getStorageDirectory().getAbsolutePath()+ "/fcgamezip");
        }
        sdcards.add(context.getExternalFilesDir(null).getAbsolutePath()+ "/fcgamezip");
        NLog.i(TAG, "selectedFolder sdcards:" + sdcards);
        try {
            File mountFile = new File("/proc/mounts");
            if (mountFile.exists()) {
                Scanner scanner = new Scanner(mountFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String lineLower = line.toLowerCase(); // neukladat lower
                    // primo do
                    // line, protoze
                    // zbytek line je
                    // case sensitive
                    if (lineLower.contains("vfat") || lineLower.contains("exfat") ||
                            lineLower.contains("fuse") || lineLower.contains("sdcardfs")) {
                        String[] lineElements = line.split(" ");
                        String path = lineElements[1];
                        sdcards.add(path);
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            NLog.e(TAG, "", e);
        }
//        getSDcardsPath(sdcards);
        HashSet<File> result = new HashSet<>(sdcards.size());
        for (String mount : sdcards) {
            File root = new File(mount);
            if (root.exists() && root.isDirectory() && root.canRead()) {
                result.add(root);
            }
        }
        NLog.i(TAG, "selectedFolder result:" + result);
        return result;
    }

    /**
     * Copy from
     * http://www.javacodegeeks.com/2012/10/android-finding-sd-card-path.html
     *
     * @return
     */
    private static void getSDcardsPath(HashSet<String> set) {
        File file = new File("/system/etc/vold.fstab");
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fr != null) {
                String defaultExternalStorage =
                        Environment.getExternalStorageDirectory().getAbsolutePath();
                br = new BufferedReader(fr);
                String s = br.readLine();
                while (s != null) {
                    if (s.startsWith("dev_mount")) {
                        String[] tokens = s.split("\\s");
                        String path = tokens[2]; // mount_point
                        if (!defaultExternalStorage.equals(path)) {
                            set.add(path);
                            break;
                        }
                    }
                    s = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * http://svn.apache.org/viewvc/commons/proper/io/trunk/src/main/java/org/
     * apache/commons/io/FileUtils.java?view=markup
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        File canon;
        if (file.getParent() == null) {
            canon = file;
        } else {
            File canonDir = file.getParentFile().getCanonicalFile();
            canon = new File(canonDir, file.getName());
        }
        return !canon.getCanonicalFile().equals(canon.getAbsoluteFile());
    }
}
