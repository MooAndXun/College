package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.BaseDao;
import cn.moo.trainingcollege.utils.TimeUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Moo on 2017/1/13.
 */
@Repository
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;

    public BaseDaoImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    public void add(T t){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(t);
    }
    public void delete(T t){
        Session session = sessionFactory.getCurrentSession();
        session.delete(t);
    }
    public void update(T t){
        Session session = sessionFactory.getCurrentSession();
        session.update(t);
    }

    public List<?> doSqlQuery(String sql){
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sql).addEntity(entityClass).list();
    }

    public List<?> doHqlQuery(String hql) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(hql).list();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public T getById(String id){
        Session session = sessionFactory.getCurrentSession();
        return (T)session.get(entityClass, id);
    }

    @SuppressWarnings({"rawtypes", "unchecked" })
    public T getByColumn(String column, Object value) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass);
        criteria.add(Restrictions.eq(column, value));
        List<?> list = criteria.list();
        if ((list.size()) == 0){
            return null;
        }else{
            return (T)list.get(0);
        }
    }
    @SuppressWarnings({"rawtypes", "unchecked" })
    public List<T> getListByColumn(String column, Object value) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass);
        criteria.add(Restrictions.eq(column, value));
        return criteria.list();
    }

    @SuppressWarnings({"rawtypes", "unchecked" })
    public List<T> getListByColumn(String column, Object value, int page, int size) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass);
        criteria.add(Restrictions.eq(column, value));
        criteria.setFirstResult((page-1)*size);
        criteria.setMaxResults(size);
        return criteria.list();
    }
    @SuppressWarnings({"rawtypes", "unchecked" })
    public List<T> getListByColumn(String column, Object value, int page, int size, String ordercolumn, boolean asc) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass);
        criteria.add(Restrictions.eq(column, value));
        if(ordercolumn!=null){
            if(asc){
                criteria.addOrder(Order.asc(ordercolumn));
            }else{
                criteria.addOrder(Order.desc(ordercolumn));
            }
        }
        criteria.setFirstResult((page-1)*size);
        criteria.setMaxResults(size);
        return criteria.list();
    }

    @SuppressWarnings({"rawtypes", "unchecked" })
    public List<T> getListByLikeColumn(String column, Object value, int page, int size, String ordercolumn, boolean asc) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass)
                .add(Restrictions.like(column, "%"+value+"%"));
        if(ordercolumn!=null){
            if(asc){
                criteria.addOrder(Order.asc(ordercolumn));
            }else{
                criteria.addOrder(Order.desc(ordercolumn));
            }
        }
        criteria.setFirstResult((page-1)*size);
        criteria.setMaxResults(size);
        return criteria.list();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<T> list=session.createQuery("From "+entityClass.getName()).list();
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> getAllByPage(int page,int size){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass)
                .setFirstResult((page-1)*size)
                .setMaxResults(size);

        return criteria.list();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> getListByLikeColumn(String column, Object value){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass)
                .add(Restrictions.like(column, "%"+value+"%"));
        return criteria.list();
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Object> getPropertyList(String column, boolean asc) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        if(asc) {
            query= session.createQuery("select " + column + " from " + entityClass.getName() + " order by " + column + " asc");
        }else {
            query= session.createQuery("select " + column + " from " + entityClass.getName() + " order by " + column + " desc");
        }

        return query.list();
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Object> getPropertyListByColumn(String column, String paramColumn, String value, boolean asc) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        if(asc) {
            query= session.createQuery("select " + column + " from " + entityClass.getName() + " where " + paramColumn + " = '" + value + "' order by " + column + " asc");
        }else {
            query= session.createQuery("select " + column + " from " + entityClass.getName() + " where " + paramColumn + " = '" + value + "' order by " + column + " desc");
        }

        return query.list();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int getCounts(){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass);
        int count = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
        return count;
    }

    @Override
    public int getCounts(String columnName, Object value){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass)
                .add(Restrictions.eq(columnName, value));
        int count = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
        return count;
    }

    List<Integer> getIntTimeLine(String sql) {
        Session session = sessionFactory.getCurrentSession();
        List<Map<String, Object>> list = session.createSQLQuery(sql)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            results.add(0);
        }
        for (Map<String, Object> map : list) {
            int month = Integer.valueOf((String)map.get("month"));
            int member = ((BigInteger)map.get("num")).intValue();
            results.set(month-1, member);
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;

        List<Integer> tempList1 = results.subList(0, month);
        List<Integer> tempList2 = results.subList(month, results.size());

        tempList2.addAll(tempList1);

        return tempList2;
    }

    List<Double> getDoubleTimeLine(String sql) {
        Session session = sessionFactory.getCurrentSession();
        List<Map<String, Object>> list = session.createSQLQuery(sql)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();

        List<Double> results = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            results.add(0.0);
        }
        for (Map<String, Object> map : list) {
            int month = Integer.valueOf((String)map.get("month"));
            double member = (Double)map.get("num");
            results.set(month-1, member);
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;

        List<Double> tempList1 = results.subList(0, month);
        List<Double> tempList2 = results.subList(month, results.size());

        tempList2.addAll(tempList1);

        return tempList2;
    }

    List getYearTimeLine(List<Map<String, Object>> dbData, boolean isDouble, String dataKey) {
        List<Object> resultList = new ArrayList<>();
        int yearGap = 3;

        for (int i = 0; i < yearGap; i++) {
            resultList.add(isDouble?0.0:0);
        }

        int currentYear = TimeUtil.getCurrentYear();
        for (Map<String, Object> dataMap :
                dbData) {
            int year = Integer.valueOf((String) dataMap.get("year"));
            if(currentYear-year<yearGap) {
                if(isDouble) {
                    Object originalData = dataMap.get(dataKey);
                        double data = originalData instanceof BigDecimal?((BigDecimal)originalData).doubleValue():(Double)originalData;
                        resultList.set((yearGap-1)+year-currentYear, data);
                } else {
                    int data = ((BigInteger)dataMap.get(dataKey)).intValue();
                    resultList.set((yearGap-1)+year-currentYear, data);
                }
            }
        }
        return resultList;
    }

    List getMonthTimeLine(List<Map<String, Object>> dbData, boolean isDouble, String dataKey) {
        List<Object> resultList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            resultList.add(isDouble?0.0:0);
        }

        for (Map<String, Object> dataMap :
                dbData) {
            int month = Integer.valueOf((String) dataMap.get("month"));
            if(isDouble) {
                Object originalData = dataMap.get(dataKey);
                double data = originalData instanceof BigDecimal?((BigDecimal)originalData).doubleValue():(Double)originalData;
                resultList.set(month-1, data);
            } else {
                int data = ((BigInteger)dataMap.get(dataKey)).intValue();
                resultList.set(month-1, data);
            }
        }

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        List<Object> tempList1 = resultList.subList(0, month);
        List<Object> tempList2 = resultList.subList(month, resultList.size());
        tempList2.addAll(tempList1);
        resultList = tempList2;

        return resultList;
    }

    List getWeekTimeLine(List<Map<String, Object>> dbData, boolean isDouble, String dataKey) {
        List<Object> resultList = new ArrayList<>();
        int weekGap = 8;

        for (int i = 0; i < weekGap; i++) {
            resultList.add(isDouble?0.0:0);
        }

        int currentWeek = TimeUtil.getCurrentWeek();
        for (Map<String, Object> dataMap :
                dbData) {
            int week = Integer.valueOf((String) dataMap.get("week"));
            if(currentWeek-week<weekGap) {
                if(isDouble) {
                    Object originalData = dataMap.get(dataKey);
                    double data = originalData instanceof BigDecimal?((BigDecimal)originalData).doubleValue():(Double)originalData;
                    resultList.set((weekGap-1)+week-currentWeek, data);
                } else {
                    int data = ((BigInteger)dataMap.get(dataKey)).intValue();
                    resultList.set((weekGap-1)+week-currentWeek, data);
                }
            }
        }
        return resultList;
    }

}
