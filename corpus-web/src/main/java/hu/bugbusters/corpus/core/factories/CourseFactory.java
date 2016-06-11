package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.TakenCourse;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

public class CourseFactory {
	private static Dao dao = DaoImpl.getInstance();

	public static Course createCourse(String name, String room, int credit, String teacher) {
		Course course = new Course();
		course.setName(name);
		course.setRoom(room);
		course.setCredit(credit);
		course.setTeacher(teacher);
		return course;
	}

	public static Course createAndSaveCourse(String name, String room, int credit, String teacher) {
		Course course = createCourse(name, room, credit, teacher);
		dao.saveEntity(course);
		return course;
	}

	public static void registerForCourse(RegisteredUser registeredUser, Course course){
		TakenCourse takenCourse = new TakenCourse();
		takenCourse.setRegisteredUser(registeredUser);
		takenCourse.setCourse(course);
		dao.saveEntity(takenCourse);
	}

	public static void registerForCourse(RegisteredUser registeredUser, Course... courses){
		for (Course course : courses){
			registerForCourse(registeredUser, course);
		}
	}
}
