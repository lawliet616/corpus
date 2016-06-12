package hu.bugbusters.corpus.core.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.PasswordSettings;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.exceptions.CourseNotFoundException;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.global.Global;
import hu.bugbusters.corpus.core.password.PasswordRows;
import hu.bugbusters.corpus.core.util.HibernateUtil;

public interface Dao {
	SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();
	int BATCH_SIZE = Global.DAO_BATCH_SIZE;

	List<Course> listAllCourses();

	List<RegisteredUser> listAllUsers();

	Course getCourseById(Long id) throws CourseNotFoundException;

	Course getCourseByName(String name) throws CourseNotFoundException;

	RegisteredUser getUserById(Long id) throws UserNotFoundException;

	RegisteredUser getUserByUserName(String name) throws UserNotFoundException;

	RegisteredUser getUserByEmail(String address) throws UserNotFoundException;

	RegisteredUser getUserByFullName(String fullName) throws UserNotFoundException;

	PasswordSettings getPasswordSettings(PasswordRows row);

	<T> void saveEntity(T entity);

	<T> void updateEntity(T entity);

	<T> void deleteEntity(T entity);

	<T> void saveEntities(List<T> entities);

	<T> void updateEntities(List<T> entities);

	<T> void deleteEntities(List<T> entities);

	<T> void saveEntities(T... entities);

	<T> void updateEntities(T... entities);

	<T> void deleteEntities(T... entities);

}
