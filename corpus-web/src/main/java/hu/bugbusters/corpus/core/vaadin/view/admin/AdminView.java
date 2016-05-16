package hu.bugbusters.corpus.core.vaadin.view.admin;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;

@SuppressWarnings("serial")
public class AdminView extends AdminDesign implements CorpusView{
	public static final String NAME = "Admin";
	public static final Role ROLE   = Role.ADMIN;

	private Dao dao;

	public AdminView(){
		dao = new DaoImpl();
		this.logoutButton.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(Button.ClickEvent event) {
				Login.logOut();
				((CorpusUI)getUI()).navigateToLogin();
			}
		});
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

	}

	@Override
	public Role getRole() {
		return ROLE;
	}
}
