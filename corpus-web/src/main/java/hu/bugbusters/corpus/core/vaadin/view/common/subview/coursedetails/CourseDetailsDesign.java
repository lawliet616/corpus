package hu.bugbusters.corpus.core.vaadin.view.common.subview.coursedetails;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class CourseDetailsDesign extends VerticalLayout {
	protected Label labelCourseName;
	protected Label labelMaxSize;
	protected Label labelCredit;
	protected Label labelTeacher;
	protected Label labelRoom;
	protected Label labelMark;
	protected TextArea textAreaDesc;
	protected Button cancelButton;

	public CourseDetailsDesign() {
		Design.read(this);
	}
}
