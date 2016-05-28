package hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class SelfDetailsView extends SelfDetailsDesign implements View {
	public static final String NAME = "SELFDETAILS";

	public SelfDetailsView() {
		fillTable();
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	private void fillTable() {
		RegisteredUser user = Login.getLoggedInUser();
		lblEmail.setValue(user.getEmail());
		lblName.setValue(user.getFullname());
		lblUsername.setValue(user.getUsername());

	}

}
