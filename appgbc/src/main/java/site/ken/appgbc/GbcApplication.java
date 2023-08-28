package site.ken.appgbc;

import site.ken.framework.BaseApplication;
import site.ken.framework.base.EmulatorHolder;


public class GbcApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        EmulatorHolder.setEmulatorClass(GbcEmulator.class);
    }

    @Override
    public boolean hasGameMenu() {
        return false;
    }

}