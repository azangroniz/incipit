package com.osfe.ramenodb.authentication.api.db;

import org.hibernate.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Fabiano Jose Maria
 */
public class GenericRepo<E, PK> {

    protected final SessionFactory sessionFactory;
    private final Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public GenericRepo(SessionFactory sessionFactory) {
        this.sessionFactory = requireNonNull(sessionFactory);
        this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public void saveOrUpdate(E object) {
        currentSession().saveOrUpdate(object);
    }

    @SuppressWarnings("unchecked")
    public E merge(E object) {
        E newEntity = (E) currentSession().merge(requireNonNull(object));
        return newEntity;
    }

    public void persist(E object) {
        currentSession().persist(requireNonNull(object));
    }

    public void update(E object) {
        currentSession().update(object);
    }

    @SuppressWarnings("unchecked")
    public E get(Serializable id) {
        return (E) currentSession().get(entityClass, requireNonNull(id));
    }

    @SuppressWarnings("unchecked")
    public List<E> getAll() {
        return currentSession().createQuery("from " + entityClass.getCanonicalName()).list();
    }

    @SuppressWarnings("unchecked")
    public List<E> getByIds(List<PK> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        Query query = currentSession().createQuery("from " + entityClass.getName() + " where id in (:ids)");
        query.setParameter("ids", requireNonNull(ids));
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public void deleteById(Serializable id) {
        E toDelete = (E) sessionFactory.getCurrentSession().get(entityClass, requireNonNull(id));
        currentSession().delete(toDelete);
    }

    protected Criteria criteria() {
        return currentSession().createCriteria(entityClass);
    }

    protected Query namedQuery(String queryName) throws HibernateException {
        return currentSession().getNamedQuery(requireNonNull(queryName));
    }

    @SuppressWarnings("unchecked")
    protected E uniqueResult(Criteria criteria) throws HibernateException {
        return (E) requireNonNull(criteria).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    protected E uniqueResult(Query query) throws HibernateException {
        return (E) requireNonNull(query).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    protected List<E> list(Criteria criteria) throws HibernateException {
        return requireNonNull(criteria).list();
    }

    @SuppressWarnings("unchecked")
    protected List<E> list(Query query) throws HibernateException {
        return requireNonNull(query).list();
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
