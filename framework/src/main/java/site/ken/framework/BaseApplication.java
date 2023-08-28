package site.ken.framework;


import android.app.Application;

import com.blankj.utilcode.util.Utils;

import site.ken.framework.gamedata.dao.DbManager;
import site.ken.framework.utils.EmuUtils;
import site.ken.framework.utils.NLog;

abstract public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        boolean debug = EmuUtils.isDebuggable(this);
        NLog.setDebugMode(debug);
        DbManager.init(this,"gamename");
    }

    public abstract boolean hasGameMenu();
}
