package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

public class CourseFactory {
	private static CourseFactory factory;

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
		DaoImpl.getInstance().saveEntity(course);
		return course;
	}
}
