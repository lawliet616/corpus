package hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails;

import org.vaadin.resetbuttonfortextfield.ResetButtonForTextField;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class ChangeSelfDetailsView extends ChangeSelfDetailsDesign implements View {
	public static final String NAME = "ChangeSelfDetails";
	private Dao dao = DaoImpl.getInstance();
	private RegisteredUser registeredUser;

	public ChangeSelfDetailsView() {

		fillTheDetails();

		if (Login.getLoggedInUser().getRole() == Role.USER) {
			ResetButtonForTextField.extend(txtEmail);
			txtName.setReadOnly(true);

		} else {
			ResetButtonForTextField.extend(txtEmail);
			ResetButtonForTextField.extend(txtName);
		}

		btnCancel.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
			}
		});
		btnSave.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				checkNewDetails();

			}
		});
	}

	private void fillTheDetails() {
		registeredUser = Login.getLoggedInUser();
		txtEmail.setValue(registeredUser.getEmail());
		txtName.setValue(registeredUser.getFullName());
	}

	protected void checkNewDetails() {
		if (txtEmail.getValue().isEmpty() || txtName.getValue().isEmpty()) {
			Notification.show("Nem töltött ki minden mezőt!", "Hiba!", Notification.Type.WARNING_MESSAGE);
		} else {
			updateUser();
		}
	}

	private void updateUser() {
		registeredUser.setEmail(txtEmail.getValue());
		registeredUser.setFullName(txtName.getValue());
		dao.updateEntity(registeredUser);
		Notification.show("Sikeresen megváltoztatta az adatait!", Notification.Type.HUMANIZED_MESSAGE);
		((CorpusUI) getUI()).navigate(SelfDetailsView.NAME);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
