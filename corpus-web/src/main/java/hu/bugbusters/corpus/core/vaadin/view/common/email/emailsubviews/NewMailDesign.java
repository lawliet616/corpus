package hu.bugbusters.corpus.core.vaadin.view.common.email.emailsubviews;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
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
public class NewMailDesign extends VerticalLayout {
	protected TextField targetTxt;
	protected TextField subjectTxt;
	protected TextArea messageTxt;
	protected Button sendButton;
	protected Button cancelButton;

	public NewMailDesign() {
		Design.read(this);
	}
}