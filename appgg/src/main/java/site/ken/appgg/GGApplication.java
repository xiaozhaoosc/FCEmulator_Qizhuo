package site.ken.appgg;

import site.ken.framework.BaseApplication;
import site.ken.framework.base.EmulatorHolder;

public class GGApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        EmulatorHolder.setEmulatorClass(GGEmulator.class);
    }

    @Override
    public boolean hasGameMenu() {
        return false;
    }
}
