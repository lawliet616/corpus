package hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
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
public class SelfDetailsDesign extends VerticalLayout {
	protected GridLayout btnSave;
	protected Label lblName;
	protected Label lblEmail;
	protected Label lblUsername;
	protected Button btnChangeDetails;

	public SelfDetailsDesign() {
		Design.read(this);
	}
}
