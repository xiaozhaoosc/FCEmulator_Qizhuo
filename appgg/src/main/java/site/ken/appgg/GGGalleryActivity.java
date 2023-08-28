package site.ken.appgg;

import java.util.HashSet;
import java.util.Set;

import site.ken.framework.Emulator;
import site.ken.framework.base.EmulatorActivity;
import site.ken.framework.ui.gamegallery.GalleryActivity;

public class GGGalleryActivity extends GalleryActivity {

    @Override
    public Emulator getEmulatorInstance() {
        return GGEmulator.getInstance();
    }

    @Override
    public Class<? extends EmulatorActivity> getEmulatorActivityClass() {
        return GGEmulatorActivity.class;
    }

    @Override
    protected Set<String> getRomExtensions() {
        HashSet<String> set = new HashSet<>();
        set.add("gg");
        return set;
    }
}
