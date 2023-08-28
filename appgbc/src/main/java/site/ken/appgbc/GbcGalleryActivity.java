package site.ken.appgbc;

import java.util.HashSet;
import java.util.Set;

import site.ken.framework.Emulator;
import site.ken.framework.base.EmulatorActivity;
import site.ken.framework.ui.gamegallery.GalleryActivity;

public class GbcGalleryActivity extends GalleryActivity {

    @Override
    public Class<? extends EmulatorActivity> getEmulatorActivityClass() {
        return GbcEmulatorActivity.class;
    }

    @Override
    protected Set<String> getRomExtensions() {
        HashSet<String> set = new HashSet<>();
        set.add("gb");
        set.add("gbc");
        return set;
    }

    @Override
    public Emulator getEmulatorInstance() {
        return GbcEmulator.getInstance();
    }

}
