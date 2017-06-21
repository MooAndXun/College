package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.PageviewEntity;
import cn.moo.trainingcollege.utils.StatTimeType;

import java.util.List;

/**
 * Created by 小春 on 2017/6/19.
 */
public interface PageviewDao extends BaseDao<PageviewEntity> {
    public List<Integer> getPageViewCount(StatTimeType statTimeType);
}
