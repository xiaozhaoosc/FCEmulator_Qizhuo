package site.ken.framework.gamedata.dao;

import org.greenrobot.greendao.AbstractDao;

import site.ken.framework.gamedata.dao.entity.GameEntity;
import site.ken.framework.gamedata.dao.entity.RecentGameEntity;

import site.ken.framework.gamedata.dao.GameEntityDao;
import site.ken.framework.gamedata.dao.RecentGameEntityDao;
/**
 * Created by qzc
 */
public class RecentGameEntityService extends BaseServiceDao<RecentGameEntity, Long> {
    public RecentGameEntityService(AbstractDao dao) {
        super(dao);
    }
}


