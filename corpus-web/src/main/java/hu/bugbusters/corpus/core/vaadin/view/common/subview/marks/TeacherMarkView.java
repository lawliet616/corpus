package hu.bugbusters.corpus.core.vaadin.view.common.subview.marks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.bean.TakenCourse;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class TeacherMarkView extends TeacherMarkDesign implements View {
	
	private BeanContainer<Long, Course> userDataSource = new BeanContainer<Long, Course>(Course.class);
	private Set<TakenCourse> courseList;
	private Set<TakenCourse> courseListTeacher;
	private ArrayList<Course> courses  = new ArrayList<>();
	private Dao dao = DaoImpl.getInstance();

	public TeacherMarkView(Window markWindow, RegisteredUser user) {
		labelUser.setValue(user.getFullName());
		cmbxCourse.setNullSelectionAllowed(false);
		cmbxMark.setNullSelectionAllowed(false);
		
		courseList = user.getCourses();
		courseListTeacher = Login.getLoggedInUser().getCourses();
		
		for (TakenCourse course : courseList) {
			for (TakenCourse teacherCourse : courseListTeacher) {
				if(course.getCourse().getId() == teacherCourse.getCourse().getId()){
					courses.add(course.getCourse());
					break;
				}
			}
			
		}
		
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(courses);
		
		cmbxCourse.setContainerDataSource(userDataSource);
		cmbxCourse.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		cmbxCourse.setItemCaptionPropertyId("name");
		
		saveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				if(cmbxCourse.getValue() != null && cmbxMark.getValue() != null){
					System.out.println(cmbxCourse.getValue());
					TakenCourse choosenTakenCourse = null;
					
					for (TakenCourse takenCourse : courseList) {
						if(takenCourse.getCourse().getId() == cmbxCourse.getValue()){
							choosenTakenCourse = takenCourse;
						}
					}
					
					int mark = Integer.parseInt((String) cmbxMark.getValue());
					
					
					choosenTakenCourse.setMark(mark);
					
					dao.updateEntity(choosenTakenCourse);
					
					Notification.show("Jegybeírás megtörtént!");
				}else{
					Notification.show("Nincs megadva kurzus vagy hallgató!");
				}
				
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}

}
