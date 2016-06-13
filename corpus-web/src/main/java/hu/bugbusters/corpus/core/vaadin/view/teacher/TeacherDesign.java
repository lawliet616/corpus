package hu.bugbusters.corpus.core.vaadin.view.teacher;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
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
public class TeacherDesign extends HorizontalSplitPanel {
	protected HorizontalLayout menuTitle2;
	protected Label menuTitleLabel2;
	protected CssLayout menuItems2;
	protected Button homepageSButton;
	protected Button userDetailsButton;
	protected Button studentListButton;
	protected Button courseListButton;
	protected Button emailButton;
	protected Button logoutButton;
	protected VerticalLayout content;

	public TeacherDesign() {
		Design.read(this);
	}
}
