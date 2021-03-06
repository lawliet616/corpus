package hu.bugbusters.corpus.core.vaadin.view.teacher.studentlist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.TakenCourse;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.coursedetails.CourseDetailsView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.marks.TeacherMarkView;

@SuppressWarnings("serial")
public class TeacherStudentListView extends TeacherStudentListDesign implements View {
	public static final String NAME = "TeacherStudentList";
	private BeanContainer<Long, RegisteredUser> userDataSource = new BeanContainer<Long, RegisteredUser>(
			RegisteredUser.class);
	private Dao dao = DaoImpl.getInstance();
	private Set<TakenCourse> users = new HashSet<>();
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

		Set<TakenCourse> courses = Login.getLoggedInUser().getCourses();
		// List<RegisteredUser> users = new ArrayList<>();

		for (TakenCourse course : courses) {
			for (TakenCourse takenCourse : course.getCourse().getStudents()) {
				if (takenCourse.getRegisteredUser().getRole() == Role.USER) {
					if (!ownStudentList.contains(takenCourse.getRegisteredUser())) {
						ownStudentList.add(takenCourse.getRegisteredUser());
					}
				}
			}
		}

	}

	private void createSelectGoup() {

		for (Course course : dao.listAllCourses()) {
			for (TakenCourse user : course.getStudents()) {
				if (user.getRegisteredUser().getId() == Login.getLoggedInUserId()) {
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

		List<TakenCourse> temp = new ArrayList<>();
		userDataSource.removeAllItems();
		currentList.clear();

		if (radio.equals("Mindegyik")) {
			userDataSource.addAll(ownStudentList);
			currentList.addAll(ownStudentList);
		} else {
			for (Course course : allCourse) {
				if (radio.equals(course.getName())) {
					users = course.getStudents();
				}
			}
			temp.addAll(users);

			for (TakenCourse user : temp) {
				if (user.getRegisteredUser().getRole() != Role.TEACHER) {
					currentList.add(user.getRegisteredUser());
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
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setColumns("username", "role", "fullName", "email");
		grid.sort("username", SortDirection.ASCENDING);

		headerNameSetting();
		
		grid.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				
				RegisteredUser user = null;
				
				for (RegisteredUser regUser : dao.listAllUsers()) {
					if(regUser.getId() == grid.getSelectedRow()){
						user = regUser;
						break;
					}
				}
				
				Window markWindow = new Window("Jegybeírás");
				markWindow.setContent(new TeacherMarkView(markWindow, user));
				
				markWindow.setResizable(false);
				markWindow.setWidth("50%");
				markWindow.setHeight("25%");
				markWindow.center();
				markWindow.setDraggable(false);
				markWindow.setModal(true);
				
				((CorpusUI) getUI()).addWindow(markWindow);

			}
		});

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
