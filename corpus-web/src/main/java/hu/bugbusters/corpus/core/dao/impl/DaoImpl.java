package hu.bugbusters.corpus.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.PasswordSettings;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.password.PasswordRows;

@SuppressWarnings("unchecked")
public class DaoImpl implements Dao {

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
	public Course getCourseById(Long id) {
		Session session = SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Course course = session.get(Course.class, id);
		transaction.commit();
		session.close();
		return course;
	}

	@Override
	public Course getCourseByName(String name) {
		Session session = SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Course course = session.get(Course.class, name);
		transaction.commit();
		session.close();
		return course;
	}

	@Override
	public RegisteredUser getUserById(Long id) {
		Session session = SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		RegisteredUser user = session.get(RegisteredUser.class, id);
		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public List<RegisteredUser> getUserByUserName(String username) {
		Session session = SESSION_FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria crit = session.createCriteria(RegisteredUser.class);
		Criterion userNameCrit = Restrictions.eq("username", username);
		crit.add(userNameCrit);
		List<RegisteredUser> users = crit.list();
		transaction.commit();
		session.close();
		return users;
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

		for (int i = 0; i < entities.size(); i++) {
			session.delete(entities.get(i));
		}

		tx.commit();
		session.close();
	}

}
