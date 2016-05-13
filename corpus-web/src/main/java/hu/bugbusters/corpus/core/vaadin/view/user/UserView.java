package hu.bugbusters.corpus.core.vaadin.view.user;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;

@SuppressWarnings("serial")
public class UserView extends CustomComponent implements CorpusView {
	public static final String NAME = "User";
	public static final Role ROLE   = Role.USER;
	
	private Dao dao;

	public UserView() {
		dao = new DaoImpl();
		
		Label label = new Label("Hello User!");
		
		setCompositionRoot(label);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	@Override
	public Role getRole() {
		return ROLE;
	}
}
