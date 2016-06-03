package hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails;

import java.io.IOException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.password.PasswordStorage.CannotPerformOperationException;
import hu.bugbusters.corpus.core.password.PasswordStorage.InvalidHashException;
import hu.bugbusters.corpus.core.password.Password;
import hu.bugbusters.corpus.core.password.PasswordChecker;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class ChangePasswordView extends ChangePasswordDesign implements View {
	public static final String NAME = "ChangePassword";
	private Dao dao;
	private RegisteredUser registeredUser;

	public ChangePasswordView() {
		dao = new DaoImpl();
		registeredUser = Login.getLoggedInUser();
		lblName.setValue(registeredUser.getUsername());
		btnCancel.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
			}
		});
		btnSave.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				checkDetails();
			}
		});
	}

	protected void checkDetails() {
		if (checkTextfields()) {
			if (checkOldPassword()) {
				if (checkNewPasswords()) {
					if (isPasswordValid()) {
						changePassword(txtNewPassword1.getValue());
					}
				} else {
					Notification.show("Nem egyeznek meg az új jelszavak!", Notification.Type.WARNING_MESSAGE);
				}
			} else {
				Notification.show("Nem sikerült az azonosítás!", Notification.Type.WARNING_MESSAGE);
			}
		} else {
			Notification.show("Nincs minden mező kitöltve!", Notification.Type.WARNING_MESSAGE);
		}
	}

	private boolean isPasswordValid() {
		PasswordChecker checker;
		boolean valid = false;
		try {
			checker = PasswordChecker.getPasswordChecker();
			valid = checker.check(txtNewPassword1.getValue());
			if (valid) {
				return true;
			} else {
				Notification.show(checker.getErrorMessage(), Notification.Type.WARNING_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean checkTextfields() {
		return !(txtNewPassword1.getValue().isEmpty() || txtNewPassword2.getValue().isEmpty()
				|| txtOldPassword.getValue().isEmpty());
	}

	private void changePassword(String password) {
		try {
			registeredUser.setPassword(Password.toDatabaseHash(password));
			dao.updateEntity(registeredUser);
			Notification.show("Sikeresen megváltoztatta a jelszavát!", Notification.Type.HUMANIZED_MESSAGE);
			((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
		} catch (InvalidHashException e) {
			e.printStackTrace();
		}
	}

	private boolean checkNewPasswords() {
		return txtNewPassword1.getValue().compareTo(txtNewPassword2.getValue()) == 0;
	}

	private boolean checkOldPassword() {
		try {
			String password = txtOldPassword.getValue();
			return Password.verify(password, registeredUser);
		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
		} catch (InvalidHashException e) {
			e.printStackTrace();
		}
		return true;

	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
