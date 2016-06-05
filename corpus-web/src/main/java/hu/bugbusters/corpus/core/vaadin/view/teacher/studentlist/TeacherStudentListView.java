package hu.bugbusters.corpus.core.vaadin.view.teacher.studentlist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Role;

@SuppressWarnings("serial")
public class TeacherStudentListView extends TeacherStudentListDesign implements View {
	public static final String NAME = "TeacherStudentList";
	private BeanContainer<Long, RegisteredUser> userDataSource;
	private Filter userFilter;
	private Filter adminFilter;
	private Filter teacherFilter;
	private Dao dao;
	private Set<RegisteredUser> users = new HashSet<>();
	private List<RegisteredUser> list = new ArrayList<>();
	
	public TeacherStudentListView() {
		dao = new DaoImpl();
		createFilters();
		fillUserTable();
		createSelectGoup();
	}
	
	private void createSelectGoup() {
		for (Course course : dao.listAllCourses()) {
			selectGroup.addItem(course.getName());
		}
		selectGroup.addItem("Egyik sem");
		selectGroup.setValue("Egyik sem");
		selectGroup.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				selectFilter();
			}
		});
		
		
	}

	protected void selectFilter() {
		userDataSource.removeAllContainerFilters();
		if(selectGroup.getValue() == Role.ADMIN) {
			userDataSource.addContainerFilter(adminFilter);
		} else if(selectGroup.getValue() == Role.TEACHER) {
			userDataSource.addContainerFilter(teacherFilter);
		} else if(selectGroup.getValue() == Role.USER) {
			userDataSource.addContainerFilter(userFilter);
		}
		
		
		for (Course course : dao.listAllCourses()) {
			if(selectGroup.getValue() == course.getName()){
				users = course.getStudents();
			}
		}
		System.out.println(users.size());
		System.out.println(selectGroup.getValue());
		/*if(users == null){
			users = (Set<RegisteredUser>) dao.listAllUsers(); //list to set
		}*/
		
	}

	private void createFilters() {
		userFilter = new SimpleStringFilter("role", Role.USER.toString(), true, false);
		adminFilter = new SimpleStringFilter("role", Role.ADMIN.toString(), true, false);
		teacherFilter = new SimpleStringFilter("role", Role.TEACHER.toString(), true, false);
	}

	private void fillUserTable() {
		list.addAll(users);
		userDataSource = new BeanContainer<Long, RegisteredUser>(RegisteredUser.class);
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(list);
		
		grid.setContainerDataSource(userDataSource);
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumns("username", "role", "fullName", "email");
		grid.addSelectionListener(new SelectionListener() {
			
			@Override
			public void select(SelectionEvent event) {
				Notification.show("Select row: "+ grid.getSelectedRows());
				
			}
		});
		grid.setColumnOrder("username", "role", "fullName", "email");

	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
				
	}

}
