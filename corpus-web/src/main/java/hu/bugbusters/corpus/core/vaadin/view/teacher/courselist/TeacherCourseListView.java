package hu.bugbusters.corpus.core.vaadin.view.teacher.courselist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CourseNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews.NewMailView;

@SuppressWarnings("serial")
public class TeacherCourseListView extends TeacherCourseListDesign implements View {
	public static final String NAME = "TeacherCourseList";
	private BeanContainer<Long, Course> userDataSource = new BeanContainer<Long, Course>(Course.class);
	private Dao dao = DaoImpl.getInstance();
	private Set<Course> course = new HashSet<>();
	private List<Course> ownCourse = new ArrayList<>();
	private RendererClickListener listener, listenerMail;
	
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
		
		
		
		GeneratedPropertyContainer gpc=new GeneratedPropertyContainer(userDataSource);
		
		if(Login.getLoggedInUser().getRole() == Role.TEACHER){
			
			gpc.addGeneratedProperty("courseMail",new PropertyValueGenerator<String>(){
			    private static final long serialVersionUID=-8571003699455731586L;
			    @Override public String getValue(    Item item,    Object itemId,    Object propertyId){
			      return "Kurzusmail küldése";
			    }
			    @Override public Class<String> getType(){
			      return String.class;
			    }
			  }
			);
			
			courseList.setContainerDataSource(gpc);
			courseList.setSelectionMode(SelectionMode.SINGLE);
			courseList.setColumns("name", "room", "credit", "courseMail");
			courseList.sort("name", SortDirection.ASCENDING);
			
		}else{
			gpc.addGeneratedProperty("delete",new PropertyValueGenerator<String>(){
			    private static final long serialVersionUID=-8571003699455731586L;
			    @Override public String getValue(    Item item,    Object itemId,    Object propertyId){
			      return "Kurzus leadása";
			    }
			    @Override public Class<String> getType(){
			      return String.class;
			    }
			  }
			);
			
			courseList.setContainerDataSource(gpc);
			courseList.setSelectionMode(SelectionMode.SINGLE);
			courseList.setColumns("name", "room", "credit", "delete");
			courseList.sort("name", SortDirection.ASCENDING);
		}
		
		defineListener();
		
		if(Login.getLoggedInUser().getRole() == Role.TEACHER){
			courseList.getColumn("courseMail").setRenderer(new ButtonRenderer(listenerMail));
		}else{
			courseList.getColumn("delete").setRenderer(new ButtonRenderer(listener));
		}
		
		headerNameSetting();
		
		courseList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void select(SelectionEvent event) {
				
				String selectedCourse = null;
				
				for (Course course : ownCourse) {
					if(course.getId() == courseList.getSelectedRow()){
						selectedCourse = course.getName();
					}
				}
				
				Notification.show("Kiválasztva: " + selectedCourse);
				
			}
		});		
	}

	private void defineListener() {
		
		listener = new RendererClickListener() {

			@Override
			public void click(RendererClickEvent event) {
				courseList.getContainerDataSource().removeItem(event.getItemId());
				
				Set<Course> courses = new HashSet<>();
				Set<Course> tmp = Login.getLoggedInUser().getCourses();
				
				for (Course course : tmp) {
					if(course.getId() != event.getItemId()){
						courses.add(course);
					}
				}
				RegisteredUser loggedInUser = Login.getLoggedInUser();
				
				loggedInUser.setCourses(courses);
				
				dao.updateEntity(loggedInUser);
				
				Long id = (long) event.getItemId();
				try {
					
					Course course = dao.getCourseById(id);
					
					if(Login.getLoggedInUser().getRole() == Role.TEACHER){
						
						course.setTeacher("Nincs tanár jelnleg");
						
						
					}else if(Login.getLoggedInUser().getRole() == Role.USER){
						
						Set<RegisteredUser> courseStudents = new HashSet<>();
						Set<RegisteredUser> tmpStudents = course.getStudents();
						
						for (RegisteredUser registeredUser : tmpStudents) {
							if(registeredUser.getId() != Login.getLoggedInUserId()){
								courseStudents.add(registeredUser);
							}
						}
						
						course.setStudents(courseStudents);
						
					}
					
					dao.updateEntity(course);	
					
				} catch (CourseNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		
		listenerMail = new RendererClickListener() {

			@Override
			public void click(RendererClickEvent event) {
				
				Course course = null;
				try {
					course = dao.getCourseById((Long) event.getItemId());
				} catch (CourseNotFoundException e) {
					e.printStackTrace();
				}
				
				Set<RegisteredUser> courseStudents = course.getStudents();
				
				Window newMail = new Window("Új e-mail");
				newMail.setContent(new NewMailView(newMail, courseStudents));
				newMail.setStyleName("subWindow");
				
				newMail.setResizable(false);
				newMail.setWidth("75%");
				newMail.setHeight("70%");
				newMail.center();
				newMail.setDraggable(false);
				newMail.setModal(true);
				
				((CorpusUI) getUI()).addWindow(newMail);
				
			}
		};
		
	}

	private void headerNameSetting() {
		
		if(Login.getLoggedInUser().getRole() == Role.TEACHER){
			courseList.getColumn("name").setHeaderCaption("Név");
			courseList.getColumn("room").setHeaderCaption("Terem");
			courseList.getColumn("credit").setHeaderCaption("Kredit");
			courseList.getColumn("courseMail").setHeaderCaption("Kurzusmail");
			
		}else{
			courseList.getColumn("name").setHeaderCaption("Név");
			courseList.getColumn("room").setHeaderCaption("Terem");
			courseList.getColumn("credit").setHeaderCaption("Kredit");
			courseList.getColumn("delete").setHeaderCaption("Kurzus leadás");
		}
		
		
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
