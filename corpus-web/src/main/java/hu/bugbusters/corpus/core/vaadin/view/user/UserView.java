package hu.bugbusters.corpus.core.vaadin.view.user;

import com.vaadin.navigator.ViewChangeListener;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.LogoutClickListener;

@SuppressWarnings("serial")
public class UserView extends UserDesign implements CorpusView {
	public static final String NAME = "User";
	public static final Role ROLE   = Role.USER;

	public UserView(){
		logoutButton.addClickListener(new LogoutClickListener());
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

	}

	@Override
	public Role getRole() {
		return ROLE;
	}
}
