package hu.bugbusters.corpus.core.vaadin.view.common;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class SelfDetailsView extends SelfDetailsDesign implements View{
	public static final String NAME = "SELFDETAILS";
	
	public SelfDetailsView() {
		fillTable();		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {

	}

	private void fillTable() {
		lblEmail.setValue(Login.getLoggedInUser().getEmail());
		lblName.setValue(Login.getLoggedInUser().getFullname());
		lblUsername.setValue(Login.getLoggedInUser().getUsername());
		
	}

}
