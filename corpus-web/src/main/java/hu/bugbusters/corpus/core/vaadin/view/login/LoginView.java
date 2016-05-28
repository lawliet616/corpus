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
import hu.bugbusters.corpus.core.login.PasswordStorage.CannotPerformOperationException;
import hu.bugbusters.corpus.core.login.PasswordStorage.InvalidHashException;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class LoginView extends LoginDesign implements View, ClickListener {
	public static final String NAME = "Login";

	public class WrongUserName extends Exception {
		public WrongUserName(String message) {
			super(message);
		}
	}

	public class InvalidPassword extends Exception {
		public InvalidPassword(String message) {
			super(message);
		}
	}

	public LoginView() {
		button.addClickListener(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		userName.focus();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			List<RegisteredUser> registeredUserList = new DaoImpl().getUserByUserName(userName.getValue());
			RegisteredUser registeredUser;

			if (registeredUserList.isEmpty()) {
				throw new WrongUserName("Hibás felhasználónév:" + userName.getValue());
			}

			registeredUser = registeredUserList.get(0);

			if (Login.passwordVerifiy(password.getValue(), registeredUser)) {
				Login.setLoggedInUserId(registeredUser.getId());
				((CorpusUI) getUI()).navigate();
			} else {
				throw new InvalidPassword("Hibás jelszó:" + userName.getValue());
			}
		} catch (WrongUserName | InvalidPassword e) {
			showError();
		} catch (CannotPerformOperationException | InvalidHashException e) {
			e.printStackTrace();
		}
	}

	private void showError() {
		Notification notification = new Notification("Hibás jelszó vagy felhasználónév!",
				Notification.Type.ERROR_MESSAGE);
		notification.setDelayMsec(1500);
		notification.show(getUI().getPage());

		userName.focus();
	}
}
