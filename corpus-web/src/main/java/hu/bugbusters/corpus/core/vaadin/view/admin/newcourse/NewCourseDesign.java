package hu.bugbusters.corpus.core.vaadin.view.admin.newcourse;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
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
public class NewCourseDesign extends VerticalLayout {
	protected TextField txtName;
	protected TextField txtRoom;
	protected TextField txtCredit;
	protected TextField txtMaxUser;
	protected ComboBox cmbTeachers;
	protected TextArea txtDescription;
	protected Button btnSave;

	public NewCourseDesign() {
		Design.read(this);
	}
}
