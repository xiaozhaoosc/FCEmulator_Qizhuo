package site.ken.framework.ui.gamegallery;

import java.io.File;
import java.util.ArrayList;

import site.ken.framework.gamedata.dao.entity.GameEntity;
import site.ken.framework.utils.annotations.Column;
import site.ken.framework.utils.annotations.ObjectFromOtherTable;
import site.ken.framework.utils.annotations.Table;

@Table
public class ZipRomFile {

    @Column(isPrimaryKey = true)
    public long _id;

    @Column
    public String hash;

    @Column
    public String path;

    @ObjectFromOtherTable(columnName = "zipfile_id")
    public ArrayList<GameEntity> games = new ArrayList<>();

    public ZipRomFile() {
    }

    public static String computeZipHash(File zipFile) {
        return zipFile.getAbsolutePath().concat("-" + zipFile.length()).hashCode() + "";
    }
}
