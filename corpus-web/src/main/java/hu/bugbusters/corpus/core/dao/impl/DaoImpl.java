package hu.bugbusters.corpus.core.dao.impl;

import java.util.List;

import hu.bugbusters.corpus.core.exceptions.CourseNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.PasswordSettings;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.password.PasswordRows;

@SuppressWarnings("unchecked")
public class DaoImpl implements Dao {

    private static DaoImpl ourInstance = new DaoImpl();

    public static DaoImpl getInstance() {
        return ourInstance;
    }

    private DaoImpl() {
    }

    @Override
    public List<Course> listAllCourses() {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria crit = session.createCriteria(Course.class);
        List<Course> courses = crit.list();

        transaction.commit();
        session.close();

        return courses;
    }

    @Override
    public List<RegisteredUser> listAllUsers() {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria crit = session.createCriteria(RegisteredUser.class);
        List<RegisteredUser> users = crit.list();

        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public Course getCourseById(Long id) throws CourseNotFoundException {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class, id);

        transaction.commit();
        session.close();

        if (course == null) throw new CourseNotFoundException();

        return course;
    }

    @Override
    public Course getCourseByName(String name) throws CourseNotFoundException {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria crit = session.createCriteria(Course.class);
        Criterion nameCrit = Restrictions.eq("name", name);
        crit.add(nameCrit);
        Course course = (Course) crit.uniqueResult();

        transaction.commit();
        session.close();

        if (course == null) throw new CourseNotFoundException();

        return course;
    }

    @Override
    public RegisteredUser getUserById(Long id) throws UserNotFoundException {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        RegisteredUser user = session.get(RegisteredUser.class, id);

        transaction.commit();
        session.close();

        if (user == null) throw new UserNotFoundException();

        return user;
    }

    @Override
    public RegisteredUser getUserByUserName(String username) throws UserNotFoundException {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria crit = session.createCriteria(RegisteredUser.class);
        Criterion userNameCrit = Restrictions.eq("username", username);
        crit.add(userNameCrit);
        RegisteredUser user = (RegisteredUser) crit.uniqueResult();

        transaction.commit();
        session.close();

        if (user == null) throw new UserNotFoundException();

        return user;
    }

    @Override
    public RegisteredUser getUserByEmail(String address) throws UserNotFoundException {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria crit = session.createCriteria(RegisteredUser.class);
        Criterion emailCrit = Restrictions.eq("email", address);
        crit.add(emailCrit);
        RegisteredUser user = (RegisteredUser) crit.uniqueResult();

        transaction.commit();
        session.close();

        if (user == null) throw new UserNotFoundException();

        return user;
    }
    
    @Override
    public RegisteredUser getUserByFullName(String name) throws UserNotFoundException {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria crit = session.createCriteria(RegisteredUser.class);
        Criterion emailCrit = Restrictions.eq("fullName", name);
        crit.add(emailCrit);
        RegisteredUser user = (RegisteredUser) crit.uniqueResult();

        transaction.commit();
        session.close();

        if (user == null) throw new UserNotFoundException();

        return user;
    }

    @Override
    public PasswordSettings getPasswordSettings(PasswordRows row) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        PasswordSettings passwordSettings = session.get(PasswordSettings.class, row.toInteger());

        transaction.commit();
        session.close();

        return passwordSettings;
    }

    @Override
    public <T> void saveEntity(T entity) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public <T> void updateEntity(T entity) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public <T> void deleteEntity(T entity) {
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();

        //deletePersistentEntity(session, entity);
        session.delete(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public <T> void saveEntities(List<T> entities) {
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();

        for (int i = 0; i < entities.size(); i++) {

            session.save(entities.get(i));
            if (i % BATCH_SIZE == 0) {
                // flush a batch of inserts and release memory
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();
    }

    @Override
    public <T> void updateEntities(List<T> entities) {
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();

        for (int i = 0; i < entities.size(); i++) {

            session.update(entities.get(i));
            if (i % BATCH_SIZE == 0) {
                // flush a batch of inserts and release memory
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();
    }

    @Override
    public <T> void deleteEntities(List<T> entities) {
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();

        for (T entity : entities) {
            deletePersistentEntity(session, entity);
        }

        tx.commit();
        session.close();
    }

    @Override
    public <T> void saveEntities(T... entities) {
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();

        for (int i = 0; i < entities.length; i++) {

            session.save(entities[i]);
            if (i % BATCH_SIZE == 0) {
                // flush a batch of inserts and release memory
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();
    }

    @Override
    public <T> void updateEntities(T... entities) {
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();

        for (int i = 0; i < entities.length; i++) {

            session.update(entities[i]);
            if (i % BATCH_SIZE == 0) {
                // flush a batch of inserts and release memory
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();
    }

    @Override
    public <T> void deleteEntities(T... entities) {
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();

        for (T entity : entities) {
            deletePersistentEntity(session, entity);
        }

        tx.commit();
        session.close();
    }

    private <T> void deletePersistentEntity(Session session, T entity) {
        Object persistentInstance = session.createCriteria(entity.getClass()).add(Example.create(entity)).uniqueResult();
        if (persistentInstance != null) {
            session.delete(persistentInstance);
        } else {
            System.out.println("Entity not found in database.");
        }
    }
}
