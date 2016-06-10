package hu.bugbusters.corpus.core.vaadin.view.teacher.studentlist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Grid.SelectionMode;

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
	private List<RegisteredUser> ownStudentList = new ArrayList<>();
	private List<Course> allCourse = new ArrayList<>();
	
	public TeacherStudentListView() {
		allCourse = dao.listAllCourses();
		studentList();
		fillUserTable();
		createSelectGoup();
	}
	
	private void studentList() {
		
		Set<Course> courses = new HashSet<>();
		
		for (RegisteredUser user : dao.listAllUsers()) {
			
			courses = user.getCourses();
			
			for (Course course : courses) {
				if(course.getTeacher().equals(Login.getLoggedInUser().getFullName())){
					if(user.getRole() == Role.USER ){
						ownStudentList.add(user);
					}
					break;
				}
			}
		}
		
	}

	private void createSelectGoup() {
		
		for (Course course : dao.listAllCourses()) {
			for (RegisteredUser user : course.getStudents()) {
				if(user.getId() == Login.getLoggedInUserId()){
					selectGroup.addItem(course.getName());
				}
			}
		}
		selectGroup.addItem("Mindegyik");
		selectGroup.setValue("Mindegyik");
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
			userDataSource.addAll(ownStudentList);
			currentList.addAll(ownStudentList);
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
		
		grid.sort("username", SortDirection.ASCENDING);
		
	}

	private void fillUserTable() {
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(ownStudentList);
		
		grid.setContainerDataSource(userDataSource);
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumns("username", "role", "fullName", "email");
		grid.sort("username", SortDirection.ASCENDING);
		
		headerNameSetting();

	}
	

	private void headerNameSetting() {
		
		grid.getColumn("username").setHeaderCaption("Név");
		grid.getColumn("role").setHeaderCaption("Jogosultság");
		grid.getColumn("fullName").setHeaderCaption("Teljes név");
		grid.getColumn("email").setHeaderCaption("E-mail");
		
		
		
		
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
				
	}

}
