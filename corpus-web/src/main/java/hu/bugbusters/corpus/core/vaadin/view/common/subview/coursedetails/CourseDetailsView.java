package hu.bugbusters.corpus.core.vaadin.view.common.subview.coursedetails;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.Course;

@SuppressWarnings("serial")
public class CourseDetailsView extends CourseDetailsDesign implements View{

	public CourseDetailsView(Course selectedCourse, final Window courseDetailWindow) {
		labelCourseName.setValue("<b>Kurzus neve:</b>\t" + selectedCourse.getName());
		labelMaxSize.setValue("<b>Maximum létszám:</b>\t" + (selectedCourse.getStudents().size() - 1) + "/" +selectedCourse.getMaxSize());
		labelCredit.setValue("<b>Kredit:</b>\t" + selectedCourse.getCredit());
		labelTeacher.setValue("<b>Oktató:</b>\t" + selectedCourse.getTeacher());
		labelRoom.setValue("<b>Terem:</b>\t" + selectedCourse.getRoom());
		textAreaDesc.setValue(selectedCourse.getDescription());
		
		labelCourseName.setReadOnly(true);
		labelMaxSize.setReadOnly(true);
		labelCredit.setReadOnly(true);
		labelTeacher.setReadOnly(true);
		labelRoom.setReadOnly(true);
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
