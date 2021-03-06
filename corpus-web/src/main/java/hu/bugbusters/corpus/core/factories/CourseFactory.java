package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.TakenCourse;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Role;

public class CourseFactory {
	private static Dao dao = DaoImpl.getInstance();

	public static Course createCourse(String name, String room, int credit, String teacher, int maxSize,
			String description) {
		Course course = new Course();
		course.setName(name);
		course.setRoom(room);
		course.setCredit(credit);
		course.setTeacher(teacher);
		course.setMaxSize(maxSize);
		course.setDescription(description);
		return course;
	}

	public static Course createAndSaveCourse(String name, String room, int credit, String teacher, int maxSize,
			String description) {
		Course course = createCourse(name, room, credit, teacher, maxSize, description);
		dao.saveEntity(course);
		return course;
	}

	public static void registerForCourse(RegisteredUser registeredUser, Course course) {
		for (TakenCourse takenCourse : registeredUser.getCourses()){
			if(takenCourse.getCourse().equals(course)){
				return;
			}
		}


		TakenCourse takenCourse = new TakenCourse();
		takenCourse.setRegisteredUser(registeredUser);
		takenCourse.setCourse(course);
		dao.saveEntity(takenCourse);
	}

	public static void registerForCourse(RegisteredUser registeredUser, Course... courses) {
		for (Course course : courses) {
			registerForCourse(registeredUser, course);
		}
	}

	public static void quitCourse(RegisteredUser registeredUser, Course course){
		for (TakenCourse takenCourse : course.getStudents()){
			if(takenCourse.getRegisteredUser().equals(registeredUser)){
				dao.deleteEntity(takenCourse);
				break;
			}
		}
	}

	public static void quitCourse(RegisteredUser registeredUser, Course... courses){
		for (Course course : courses) {
			quitCourse(registeredUser, course);
		}
	}
}
