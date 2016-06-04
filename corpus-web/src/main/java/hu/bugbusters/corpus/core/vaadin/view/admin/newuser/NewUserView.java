package hu.bugbusters.corpus.core.vaadin.view.admin.newuser;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.factories.UserFactory;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.password.PasswordStorage.CannotPerformOperationException;
import hu.bugbusters.corpus.core.password.PasswordStorage.InvalidHashException;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

public class NewUserView extends NewUserDesign implements View {
	public static final String NAME = "NewUser";

	public NewUserView() {
		cmbRoles.removeAllItems();
		cmbRoles.addItems(Role.values());

		btnCancel.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate();
			}
		});

		btnSave.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				createNewUser();
			}
		});
	}

	protected void createNewUser() {
		if (isValidValues()) {
			String name = txtName.getValue();
			String email = txtEmail.getValue();
			Role role = (Role) cmbRoles.getValue();
			UserFactory factory = new UserFactory();
			try {
				RegisteredUser user = factory.createRegisteredUser(name, email, role);
				new DaoImpl().saveEntity(user);
				Notification.show("Sikeres mentés.",Notification.Type.HUMANIZED_MESSAGE);
				txtName.clear();
				txtEmail.clear();
				txtName.focus();
			} catch (CannotPerformOperationException | InvalidHashException e) {
				Notification.show("Hiba a felhasználó létrehozása közben!", Notification.Type.WARNING_MESSAGE);
				e.printStackTrace();
			}
		} else {
			Notification.show("Nem töltött ki minden adatot, vagy nem választott jogosultságot!",
					Notification.Type.WARNING_MESSAGE);
		}
	}

	private boolean isValidValues() {
		if (txtName.getValue() != "" && txtEmail.getValue() != "" && cmbRoles.getValue() != null) {
			return true;
		}
		return false;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
