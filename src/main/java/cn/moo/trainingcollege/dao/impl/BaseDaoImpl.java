package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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
        return session.createSQLQuery(sql).list();
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

    public List<T> getListByLikeColumn(String column, Object value){
        return null;
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
    public int getCounts(){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass);
        int count = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
        return count;
    }

    public int getCounts(String columnName, String keyword){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass)
                .add(Restrictions.like(columnName, "%"+keyword+"%"));
        int count = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
        return count;
    }

}
