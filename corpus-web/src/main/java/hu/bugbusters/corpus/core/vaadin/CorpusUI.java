package hu.bugbusters.corpus.core.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.util.Login;
import hu.bugbusters.corpus.core.util.Role;
import hu.bugbusters.corpus.core.vaadin.view.AdminView;
import hu.bugbusters.corpus.core.vaadin.view.LoginView;
import hu.bugbusters.corpus.core.vaadin.view.TeacherView;
import hu.bugbusters.corpus.core.vaadin.view.UserView;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CorpusUI extends UI {
	Navigator navigator;
	
	@Override
	protected void init(final VaadinRequest request) {
		navigator = new Navigator(this, this);

		navigator.addView(LoginView.NAME, new LoginView());
		navigator.addView(UserView.NAME, new UserView());
		navigator.addView(TeacherView.NAME, new TeacherView());
		navigator.addView(AdminView.NAME, new AdminView());
		
		if(!Login.loggedIn()) {
			navigator.navigateTo(LoginView.NAME);
		} else {
			Login.navigateByRole();
		}
	}

	public void navigateToViewByRole(Role role) {
		switch(role) {
		case ADMIN:
			navigator.navigateTo(AdminView.NAME);
			break;
		case TEACHER:
			navigator.navigateTo(TeacherView.NAME);
			break;
		case USER:
			navigator.navigateTo(UserView.NAME);
			break;
		default:
			navigator.navigateTo(ErrorView.NAME);
			break;
		}
	}
}
