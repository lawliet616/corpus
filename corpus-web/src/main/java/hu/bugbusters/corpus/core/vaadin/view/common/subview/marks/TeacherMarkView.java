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
import com.vaadin.ui.Window;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.bean.TakenCourse;

@SuppressWarnings("serial")
public class TeacherMarkView extends TeacherMarkDesign implements View {
	
	private BeanContainer<Long, Course> userDataSource = new BeanContainer<Long, Course>(Course.class);

	public TeacherMarkView(Window markWindow, RegisteredUser user) {
		labelUser.setValue(user.getFullName());
		cmbxCourse.setNullSelectionAllowed(false);
		cmbxMark.setNullSelectionAllowed(false);
		
		Set<TakenCourse> courseList = user.getCourses();
		List<Course> courses = new ArrayList<>();
		
		for (TakenCourse course : courseList) {
			courses.add(course.getCourse());
		}
		
		userDataSource.setBeanIdProperty("id");
		userDataSource.addAll(courses);
		
		cmbxCourse.setContainerDataSource(userDataSource);
		cmbxCourse.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		cmbxCourse.setItemCaptionPropertyId("name");
		
		saveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
				
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}

}
