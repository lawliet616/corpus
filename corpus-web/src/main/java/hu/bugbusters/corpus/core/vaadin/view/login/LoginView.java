package hu.bugbusters.corpus.core.vaadin.view.login;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CannotPerformOperationException;
import hu.bugbusters.corpus.core.exceptions.InvalidHashException;
import hu.bugbusters.corpus.core.exceptions.InvalidPasswordException;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.password.Password;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class LoginView extends LoginDesign implements View, ClickListener {
	public static final String NAME = "Login";

	public LoginView() {
		button.addClickListener(this);
		button.setClickShortcut(KeyCode.ENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		userName.focus();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			RegisteredUser registeredUser = DaoImpl.getInstance().getUserByUserName(userName.getValue());

			Password.verify(password.getValue(), registeredUser);

			Login.setLoggedInUserId(registeredUser.getId());

			((CorpusUI) getUI()).navigate();
		} catch (UserNotFoundException | InvalidPasswordException e) {
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
