package hu.bugbusters.corpus.core.vaadin.view.admin;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;

@SuppressWarnings("serial")
public class AdminView extends CustomComponent implements CorpusView {
	public static final String NAME = "Admin";
	public static final Role ROLE   = Role.ADMIN;
	
	private Dao dao;
	
	public AdminView() {
		dao = new DaoImpl();
		
		Label label = new Label("Hello Admin!");
		
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
