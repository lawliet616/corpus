package hu.bugbusters.corpus.core.vaadin.view.teacher.courselist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Grid.SelectionMode;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.teacher.studentlist.TeacherStudentListDesign;

@SuppressWarnings("serial")
public class TeacherCourseListView extends TeacherCourseListDesign implements View {
	public static final String NAME = "TeacherCourseList";
	private BeanContainer<Long, Course> userDataSource = new BeanContainer<Long, Course>(Course.class);
	private Dao dao = DaoImpl.getInstance();
	private Set<Course> users = new HashSet<>();
	private List<Course> ownCourse = new ArrayList<>();
	
	public TeacherCourseListView() {
		fillTable();
	}

	private void fillTable() {
		
		for (Course course : dao.listAllCourses()) {
			for (RegisteredUser user : course.getStudents()) {
				if(user.getId() == Login.getLoggedInUserId()){
					ownCourse.add(course);
				}
			}
		}
		
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(ownCourse);
		
		courseList.setContainerDataSource(userDataSource);
		courseList.setSelectionMode(SelectionMode.SINGLE);
		courseList.setColumns("name", "room", "credit", "teacher");
		courseList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void select(SelectionEvent event) {
				Notification.show("Select row: "+ courseList.getSelectedRows());
				
			}
		});		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
