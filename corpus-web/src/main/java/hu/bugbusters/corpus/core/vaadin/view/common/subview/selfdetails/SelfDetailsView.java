package hu.bugbusters.corpus.core.vaadin.view.common.subview.selfdetails;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

@SuppressWarnings("serial")
public class SelfDetailsView extends SelfDetailsDesign implements View {
	public static final String NAME = "SelfDetails";

	public SelfDetailsView() {
		fillTable();
		btnChangeDetails.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				((CorpusUI) getUI()).navigate(ChangeSelfDetailsView.NAME);
			}
		});
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
