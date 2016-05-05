package hu.bugbusters.corpus.core.dao;

import java.util.List;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;

public interface Dao {
	public List<Course> listAllCourses();
	public List<RegisteredUser> listAllUsers();
	
	public Course getCourseById(Long id);
	public Course getCourseByName(String name);

	public RegisteredUser getUserById(Long id);
	public RegisteredUser getUserByUserName(String name);
	
	public void saveCourse(Course course);
	public void saveUser(RegisteredUser registeredUser);
	
	public void updateCourse(Course course);
	public void updateUser(RegisteredUser registeredUser);
	
	public void deleteCourse(Course course);
	public void deleteUser(RegisteredUser registeredUser);
}
