package site.ken.framework.gamedata.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import site.ken.framework.gamedata.dao.DaoMaster;

import site.ken.framework.gamedata.dao.GameEntityDao;
import site.ken.framework.gamedata.dao.RecentGameEntityDao;

/**
 * 自定义一个OpenHelper类，为以后数据库升级时使用
 * Created by qzc
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // MigrationHelper.migrate(db,“所有的Dao类”);//数据版本变更才会执行
     MigrationHelper.migrate(db,GameEntityDao.class);
     MigrationHelper.migrate(db,RecentGameEntityDao.class);
    }
}