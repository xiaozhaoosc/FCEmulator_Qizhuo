package site.ken.appgg;

import site.ken.framework.base.JniBridge;

public class GgCore extends JniBridge {
    private static GgCore instance = new GgCore();

    static {
        System.loadLibrary("gg");
    }

    private GgCore() {
    }

    public static GgCore getInstance() {
        return instance;
    }

}
