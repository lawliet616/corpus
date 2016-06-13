package hu.bugbusters.corpus.core.vaadin.view.user.AllCourseList;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.bean.TakenCourse;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.factories.CourseFactory;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews.EmailReadView;
import hu.bugbusters.corpus.core.vaadin.view.common.subview.coursedetails.CourseDetailsView;

@SuppressWarnings("serial")
public class AllCourseListView extends AllCourseListDesign implements View {
	public static final String NAME = "AllCourseList";
	private BeanContainer<Long, Course> userDataSource = new BeanContainer<Long, Course>(Course.class);
	private Dao dao = DaoImpl.getInstance();
	private List<Course> allCourses = new ArrayList<>();
	private RendererClickListener listener;
	private RendererClickListener listenerDetails;

	public AllCourseListView() {
		fillTable();
	}

	private void fillTable() {

		for (Course course : dao.listAllCourses()) {
			boolean registered = false;

			for (TakenCourse user : course.getStudents()) {
				if (user.getRegisteredUser().getId() == Login.getLoggedInUserId()) {
					registered = true;
				}
			}

			if (!registered) {
				allCourses.add(course);
			}
		}

		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(allCourses);

		GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(userDataSource);
		
		gpc.addGeneratedProperty("details", new PropertyValueGenerator<String>() {
			private static final long serialVersionUID = -8571003699455731586L;

			@Override
			public String getValue(Item item, Object itemId, Object propertyId) {
				return "Kurzus részletek";
			}

			@Override
			public Class<String> getType() {
				return String.class;
			}
		});
		gpc.addGeneratedProperty("register", new PropertyValueGenerator<String>() {
			private static final long serialVersionUID = -8571003699455731586L;

			@Override
			public String getValue(Item item, Object itemId, Object propertyId) {
				return "Kurzus felvétele";
			}

			@Override
			public Class<String> getType() {
				return String.class;
			}
		});
		
		courseList.setContainerDataSource(gpc);
		courseList.setSelectionMode(SelectionMode.SINGLE);
		courseList.setColumns("name", "room", "credit", "teacher", "maxSize", "details", "register");
		courseList.sort("name", SortDirection.ASCENDING);

		listenerSetting();

		courseList.getColumn("details").setRenderer(new ButtonRenderer(listenerDetails));
		courseList.getColumn("register").setRenderer(new ButtonRenderer(listener));

		headerNameSetting();

		courseList.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {

				String selectedCourse = null;

				for (Course course : allCourses) {
					if (course.getId() == courseList.getSelectedRow()) {
						selectedCourse = course.getName();
					}
				}

				Notification.show("Kiválasztva: " + selectedCourse);

			}
		});
	}

	private void listenerSetting() {
		listener = new RendererClickListener() {

			@Override
			public void click(RendererClickEvent event) {

				courseList.getContainerDataSource().removeItem(event.getItemId());

				List<Course> tmp = new ArrayList<>();
				List<Course> allTemp = dao.listAllCourses();

				for (Course course : allTemp) {
					if (course.getId() == event.getItemId()) {
						tmp.add(course);
					}
				}
				RegisteredUser loggedInUser = Login.getLoggedInUser();

				for (Course course : tmp) {
					CourseFactory.registerForCourse(loggedInUser, course);
				}
			}
		};
		
		listenerDetails = new RendererClickListener() {

			@Override
			public void click(RendererClickEvent event) {
				Course selectedCourse = null;
				
				for (Course course : dao.listAllCourses()) {
					if(course.getId() == event.getItemId()){
						selectedCourse = course;
						break;
					}
				}
				
				Window courseDetailWindow = new Window("Részletek");
				courseDetailWindow.setContent(new CourseDetailsView(selectedCourse, courseDetailWindow));
				
				courseDetailWindow.setResizable(false);
				courseDetailWindow.setWidth("70%");
				courseDetailWindow.setHeight("60%");
				courseDetailWindow.center();
				courseDetailWindow.setDraggable(false);
				courseDetailWindow.setModal(true);
				
				((CorpusUI) getUI()).addWindow(courseDetailWindow);
			}
		};
		
	}

	private void headerNameSetting() {

		courseList.getColumn("name").setHeaderCaption("Név");
		courseList.getColumn("room").setHeaderCaption("Terem");
		courseList.getColumn("credit").setHeaderCaption("Kredit");
		courseList.getColumn("teacher").setHeaderCaption("Oktató");
		courseList.getColumn("maxSize").setHeaderCaption("Maximális létszám");
		courseList.getColumn("details").setHeaderCaption("");
		courseList.getColumn("register").setHeaderCaption("");

	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
