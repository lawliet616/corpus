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
import hu.bugbusters.corpus.core.exceptions.CannotPerformOperationException;
import hu.bugbusters.corpus.core.exceptions.EmptyFieldException;
import hu.bugbusters.corpus.core.exceptions.InvalidHashException;
import hu.bugbusters.corpus.core.exceptions.InvalidPasswordException;
import hu.bugbusters.corpus.core.exceptions.InvalidPasswordFormatException;
import hu.bugbusters.corpus.core.exceptions.NewPasswordNotMatchException;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.password.Password;
import hu.bugbusters.corpus.core.password.PasswordChecker;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class ChangePasswordView extends ChangePasswordDesign implements View {
	public static final String NAME = "ChangePassword";
	private Dao dao = DaoImpl.getInstance();
	private RegisteredUser registeredUser;

	public ChangePasswordView() {
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
		try {
			checkTextfields();
			checkOldPassword();
			checkNewPasswords();
			isPasswordValid();
			changePassword(txtNewPassword1.getValue());
		} catch(EmptyFieldException e) {
			Notification.show("Nincs minden mező kitöltve!", Notification.Type.WARNING_MESSAGE);
		} catch(InvalidPasswordException e) {
			Notification.show("Nem sikerült az azonosítás!", Notification.Type.WARNING_MESSAGE);
		} catch(NewPasswordNotMatchException e) {
			Notification.show("Nem egyeznek meg az új jelszavak!", Notification.Type.WARNING_MESSAGE);
		} catch (InvalidPasswordFormatException e) {
			Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
		} catch (CannotPerformOperationException | InvalidHashException | IOException e) {
			e.printStackTrace();
		}
	}

	private void isPasswordValid() throws IOException, InvalidPasswordFormatException {
		PasswordChecker checker = PasswordChecker.getPasswordChecker();
		boolean valid = checker.check(txtNewPassword1.getValue());
		if (!valid) {
			//Notification.show(checker.getErrorMessage(), Notification.Type.WARNING_MESSAGE);
			
			throw new InvalidPasswordFormatException(checker.getErrorMessage());
		}
	}

	private void checkTextfields() throws EmptyFieldException {
		if(txtNewPassword1.getValue().isEmpty() || txtNewPassword2.getValue().isEmpty() || txtOldPassword.getValue().isEmpty()) {
			throw new EmptyFieldException();
		}
	}

	private void changePassword(String password) throws CannotPerformOperationException, InvalidHashException {
		registeredUser.setPassword(Password.toDatabaseHash(password));
		dao.updateEntity(registeredUser);
		Notification.show("Sikeresen megváltoztatta a jelszavát!", Notification.Type.HUMANIZED_MESSAGE);
		((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
	}

	private void checkNewPasswords() throws NewPasswordNotMatchException {
		if(txtNewPassword1.getValue().compareTo(txtNewPassword2.getValue()) != 0) {
			throw new NewPasswordNotMatchException();
		}
	}

	private void checkOldPassword() throws CannotPerformOperationException, InvalidHashException, InvalidPasswordException  {
		String password = txtOldPassword.getValue();
		Password.verify(password, registeredUser);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
