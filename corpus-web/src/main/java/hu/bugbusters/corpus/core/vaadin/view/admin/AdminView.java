package hu.bugbusters.corpus.core.vaadin.view.admin;

import com.vaadin.navigator.ViewChangeListener;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.LogoutClickListener;

@SuppressWarnings("serial")
public class AdminView extends AdminDesign implements CorpusView{
	public static final String NAME = "Admin";
	public static final Role ROLE   = Role.ADMIN;

	public AdminView(){
		new DaoImpl();
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
