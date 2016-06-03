package hu.bugbusters.corpus.core.dao;

import java.util.List;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.PasswordSettings;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.password.PasswordRows;
import hu.bugbusters.corpus.core.util.HibernateUtil;
import org.hibernate.SessionFactory;

public interface Dao {
	public static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();
	public static final int BATCH_SIZE                 = 20;

	List<Course> listAllCourses();
	List<RegisteredUser> listAllUsers();

	Course getCourseById(Long id);
	Course getCourseByName(String name);

	RegisteredUser getUserById(Long id);
	List<RegisteredUser> getUserByUserName(String name);
	
	PasswordSettings getPasswordSettings(PasswordRows row);

	<T> void saveEntity(T entity);
	<T> void updateEntity(T entity);
	<T> void deleteEntity(T entity);

	<T> void saveEntities(List<T> entities);
	<T> void updateEntities(List<T> entities);
	<T> void deleteEntities(List<T> entities);
}
