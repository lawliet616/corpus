package hu.bugbusters.corpus.core.vaadin.view;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.login.Login;

@SuppressWarnings("serial")
public class LogoutClickListener implements ClickListener {

	@Override
	public void buttonClick(ClickEvent event) {
		Login.logOut();
	}

}
