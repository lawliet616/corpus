package hu.bugbusters.corpus.core.vaadin.view.user.AllCourseList;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Grid.SelectionMode;
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

@SuppressWarnings("serial")
public class AllCourseListView extends AllCourseListDesign implements View {
	public static final String NAME = "AllCourseList";
	private BeanContainer<Long, Course> userDataSource = new BeanContainer<Long, Course>(Course.class);
	private Dao dao = DaoImpl.getInstance();
	private List<Course> allCourses = new ArrayList<>();
	
	public AllCourseListView() {
		fillTable();
	}
	
	private void fillTable() {
		
		for (Course course : dao.listAllCourses()) {
			boolean registered = false;
			
			for (RegisteredUser user : course.getStudents()) {
				if(user.getId() == Login.getLoggedInUserId()){
					registered = true;
				}
			}
			
			if(!registered){
				allCourses.add(course);
			}
		}
		
		
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(allCourses);
		
		
		
		GeneratedPropertyContainer gpc=new GeneratedPropertyContainer(userDataSource);
		gpc.addGeneratedProperty("register",new PropertyValueGenerator<String>(){
		    private static final long serialVersionUID=-8571003699455731586L;
		    @Override public String getValue(    Item item,    Object itemId,    Object propertyId){
		      return "Kurzus felvétele";
		    }
		    @Override public Class<String> getType(){
		      return String.class;
		    }
		  }
		);
		courseList.setContainerDataSource(gpc);
		courseList.setSelectionMode(SelectionMode.SINGLE);
		courseList.setColumns("name", "room", "credit", "register");
		courseList.sort("name", SortDirection.ASCENDING);
		
		RendererClickListener listener = new RendererClickListener() {

			@Override
			public void click(RendererClickEvent event) {
				courseList.getContainerDataSource().removeItem(event.getItemId());
				
				//Set<Course> courses = new HashSet<>();
				Set<Course> tmp = Login.getLoggedInUser().getCourses();
				List<Course> allTemp = dao.listAllCourses();
				
				for (Course course : allTemp) {
					if(course.getId() == event.getItemId()){
						tmp.add(course);
					}
				}
				RegisteredUser loggedInUser = Login.getLoggedInUser();
				
				loggedInUser.setCourses(tmp);
				
				dao.updateEntity(loggedInUser);
				
				Long id = (long) event.getItemId();
				try {
					
					Course course = dao.getCourseById(id);
					
					if(Login.getLoggedInUser().getRole() == Role.USER){
						
						Set<RegisteredUser> courseStudents = course.getStudents();
						
						courseStudents.add(Login.getLoggedInUser());
						
						course.setStudents(courseStudents);
						
					}
					
					dao.updateEntity(course);	
					
				} catch (CourseNotFoundException e) {
					e.printStackTrace();
				}
				
				
				
				
			}
		};
		
		courseList.getColumn("register").setRenderer(new ButtonRenderer(listener));
		
		headerNameSetting();
		
		courseList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void select(SelectionEvent event) {
				
				String selectedCourse = null;
				
				for (Course course : allCourses) {
					if(course.getId() == courseList.getSelectedRow()){
						selectedCourse = course.getName();
					}
				}
				
				Notification.show("Kiválasztva: " + selectedCourse);
				
			}
		});		
	}

	private void headerNameSetting() {
		
		courseList.getColumn("name").setHeaderCaption("Név");
		courseList.getColumn("room").setHeaderCaption("Terem");
		courseList.getColumn("credit").setHeaderCaption("Kredit");
		courseList.getColumn("register").setHeaderCaption("Kurzus felvétel");
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}