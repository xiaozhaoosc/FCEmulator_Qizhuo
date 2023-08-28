package site.ken.framework.gamedata.dao;

import org.greenrobot.greendao.AbstractDao;

import site.ken.framework.gamedata.dao.entity.GameEntity;
import site.ken.framework.gamedata.dao.entity.RecentGameEntity;

import site.ken.framework.gamedata.dao.GameEntityDao;
import site.ken.framework.gamedata.dao.RecentGameEntityDao;
/**
 * Created by qzc
 */
public class GameEntityService extends BaseServiceDao<GameEntity, Long> {
    public GameEntityService(AbstractDao dao) {
        super(dao);
    }
}


