package hu.bugbusters.corpus.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.util.HibernateUtil;

public class DaoImpl implements Dao{

	@Override
	public List<Course> listAllCourses() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Criteria crit = session.createCriteria(Course.class);
		List<Course> courses = crit.list();
		transaction.commit();
		session.close();
		return courses;
	}

	@Override
	public List<RegisteredUser> listAllUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Criteria crit = session.createCriteria(RegisteredUser.class);
		List<RegisteredUser> users = crit.list();
		transaction.commit();
		session.close();
		return users;
	}

	@Override
	public Course getCourseById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Course course = session.get(Course.class, id);
		transaction.commit();
		session.close();
		return course;
	}

	@Override
	public Course getCourseByName(String name) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Course course = session.get(Course.class, name);
		transaction.commit();
		session.close();
		return course;
	}

	@Override
	public RegisteredUser getUserById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		RegisteredUser user = session.get(RegisteredUser.class, id);
		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public List<RegisteredUser> getUserByUserName(String username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
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
	public void saveCourse(Course course) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(course);
		transaction.commit();
		session.close();
	}

	@Override
	public void saveUser(RegisteredUser registeredUser) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(registeredUser);
		transaction.commit();
		session.close();
	}

	@Override
	public void updateCourse(Course course) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(course);
		transaction.commit();
		session.close();
	}

	@Override
	public void updateUser(RegisteredUser registeredUser) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(registeredUser);
		transaction.commit();
		session.close();
	}

	@Override
	public void deleteCourse(Course course) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(course);
		transaction.commit();
		session.close();
	}

	@Override
	public void deleteUser(RegisteredUser registeredUser) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(registeredUser);
		transaction.commit();
		session.close();
	}

}
