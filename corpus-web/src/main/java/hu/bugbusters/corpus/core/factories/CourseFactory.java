package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.RegisteredCourse;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

public class CourseFactory {
	private static CourseFactory factory;
	private static Dao dao = DaoImpl.getInstance();

	private CourseFactory() {
	}

	public static CourseFactory getCourseFactory() {
		if (factory == null) {
			factory = new CourseFactory();
		}
		return factory;
	}

	public Course createCourse(String name, String room, int credit, String teacher) {
		Course course = new Course();
		course.setName(name);
		course.setRoom(room);
		course.setCredit(credit);
		course.setTeacher(teacher);
		return course;
	}

	public Course createAndSaveCourse(String name, String room, int credit, String teacher) {
		Course course = createCourse(name, room, credit, teacher);
		dao.saveEntity(course);
		return course;
	}

	public static void registerForCourse(RegisteredUser registeredUser, Course course){
		RegisteredCourse registeredCourse = new RegisteredCourse();
		registeredCourse.setRegisteredUser(registeredUser);
		registeredCourse.setCourse(course);
		registeredUser.getCourses().add(registeredCourse);
		dao.saveEntity(registeredCourse);
	}

	public static void registerForCourse(RegisteredUser registeredUser, Course... courses){
		for (Course course : courses){
			registerForCourse(registeredUser, course);
		}
	}
}
