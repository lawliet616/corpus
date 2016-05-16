package hu.bugbusters.corpus.core.vaadin.view.login;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class LoginView extends LoginDesign implements View, ClickListener {
	public static final String NAME = "Login";
	
	public LoginView() {
		button.addClickListener(this);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		userName.focus();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		List<RegisteredUser> registeredUserList = new DaoImpl().getUserByUserName(userName.getValue());
		
		if(!registeredUserList.isEmpty()) {
			RegisteredUser registeredUser = registeredUserList.get(0);
			
			if(registeredUser.getPassword().equals(password.getValue())) {
				Login.setLoggedInUserId(registeredUser.getId());
				
				((CorpusUI)getUI()).navigate();
			} else {
				showError();
			}
		} else {
			showError();
		}
	}

	private void showError() {
		Notification notification = new Notification("Hibás jelszó vagy felhasználónév!", Notification.Type.ERROR_MESSAGE);
		notification.setDelayMsec(1500);
		notification.show(getUI().getPage());
		
		userName.focus();
	}
}
