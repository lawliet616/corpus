package hu.bugbusters.corpus.core.vaadin.view.admin.homepage;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.login.Login;

public class AdminHomepageView extends AdminHomepageDesign implements View {
	public static final String Name="Homepage";
	private RegisteredUser user;
	
	public AdminHomepageView() {
		user = Login.getLoggedInUser();
		lblWelcome.setValue("Üdvözöllek "+user.getFullname()+"! Jó munkát!");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
