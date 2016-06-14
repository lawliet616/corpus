package hu.bugbusters.corpus.core.vaadin.view.common.subview.coursedetails;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.TakenCourse;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class CourseDetailsView extends CourseDetailsDesign implements View{

	public CourseDetailsView(Course selectedCourse, final Window courseDetailWindow) {
		
		int mark = 0;
		
		for (TakenCourse takenCourse : Login.getLoggedInUser().getCourses()) {
			if(takenCourse.getCourse().getId() == selectedCourse.getId()){
				mark = takenCourse.getMark();
			}
		}
		
		labelCourseName.setValue("<b>Kurzus neve:</b>\t" + selectedCourse.getName());
		labelMaxSize.setValue("<b>Maximum létszám:</b>\t" + (selectedCourse.getStudents().size() - 1) + "/" +selectedCourse.getMaxSize());
		labelCredit.setValue("<b>Kredit:</b>\t" + selectedCourse.getCredit());
		labelTeacher.setValue("<b>Oktató:</b>\t" + selectedCourse.getTeacher());
		labelRoom.setValue("<b>Terem:</b>\t" + selectedCourse.getRoom());
		if(mark == 0){
			labelMark.setValue("<b>Osztáyzat:</b>\t" + "Még nincs osztályozva");
		}else{
			labelMark.setValue("<b>Osztáyzat:</b>\t" + mark);
		}
		textAreaDesc.setValue(selectedCourse.getDescription());
		
		labelCourseName.setReadOnly(true);
		labelMaxSize.setReadOnly(true);
		labelCredit.setReadOnly(true);
		labelTeacher.setReadOnly(true);
		labelRoom.setReadOnly(true);
		labelMark.setReadOnly(true);
		textAreaDesc.setReadOnly(true);
		
		courseDetailWindow.setModal(true);
		
		cancelButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				courseDetailWindow.close();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
