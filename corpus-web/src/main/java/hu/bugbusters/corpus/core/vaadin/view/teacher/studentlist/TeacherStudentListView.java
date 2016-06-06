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
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;

@SuppressWarnings("serial")
public class TeacherStudentListView extends TeacherStudentListDesign implements View {
	public static final String NAME = "TeacherStudentList";
	private BeanContainer<Long, RegisteredUser> userDataSource = new BeanContainer<Long, RegisteredUser>(RegisteredUser.class);
	private Dao dao = DaoImpl.getInstance();
	private Set<RegisteredUser> users = new HashSet<>();
	private List<RegisteredUser> currentList = new ArrayList<>();
	private List<RegisteredUser> allStudentList = new ArrayList<>();
	private List<Course> allCourse = new ArrayList<>();
	
	public TeacherStudentListView() {
		allCourse = dao.listAllCourses();
		studentList();
		fillUserTable();
		createSelectGoup();
	}
	
	private void studentList() {
		for (RegisteredUser user : dao.listAllUsers()) {
			if(user.getRole() == Role.USER){
				allStudentList.add(user);
			}
		}
		
	}

	private void createSelectGoup() {
		/*																						Majd a végén kikell szedni!
		for (Course course : dao.listAllCourses()) {
			selectGroup.addItem(course.getName());
		}*/
		
		for (Course course : dao.listAllCourses()) {
			for (RegisteredUser user : course.getStudents()) {
				if(user.getId() == Login.getLoggedInUserId()){
					selectGroup.addItem(course.getName());
				}
			}
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
		String radio = selectGroup.getValue().toString();
		
		List<RegisteredUser> temp = new ArrayList<>();
		userDataSource.removeAllItems();
		currentList.clear();
		
		if(radio.equals("Egyik sem")){
			userDataSource.addAll(allStudentList);
			currentList.addAll(allStudentList);
		}
		else{
			for (Course course : allCourse) {
				if(radio.equals(course.getName())){
					users = course.getStudents();
				}
			}
			temp.addAll(users);
			
			for (RegisteredUser user : temp) {
				if(user.getRole() != Role.TEACHER){
					currentList.add(user);
				}
			}
			
			userDataSource.addAll(currentList);
		}
		
		
		//System.out.println(users.size());
		//System.out.println(selectGroup.getValue());
		
		
		/*if(users == null){
			users = (Set<RegisteredUser>) dao.listAllUsers(); //list to set
		}*/
		
	}

	private void fillUserTable() {
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(allStudentList);
		
		grid.setContainerDataSource(userDataSource);
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumns("username", "role", "fullName", "email");
		grid.addSelectionListener(new SelectionListener() {
			
			@Override
			public void select(SelectionEvent event) {
				Notification.show("Select row: "+ grid.getSelectedRows());
				
			}
		});

	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
				
	}

}
