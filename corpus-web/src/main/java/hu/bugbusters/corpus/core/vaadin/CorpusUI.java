package hu.bugbusters.corpus.core.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.AdminView;
import hu.bugbusters.corpus.core.vaadin.view.TeacherView;
import hu.bugbusters.corpus.core.vaadin.view.UserView;
import hu.bugbusters.corpus.core.vaadin.view.login.LoginView;

@Theme("valo")
public class CorpusUI extends UI {
	Navigator navigator;
	
	@Override
	protected void init(final VaadinRequest request) {
		navigator = new Navigator(this, this);

		navigator.addView(LoginView.NAME, LoginView.class);
		navigator.addView(UserView.NAME, new UserView());
		navigator.addView(TeacherView.NAME, new TeacherView());
		navigator.addView(AdminView.NAME, new AdminView());
		
		if(!Login.loggedIn()) {
			navigateToLogin();
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
			throw new IllegalArgumentException("Unknown view");
		}
	}

	public void navigateToLogin() {
		navigator.navigateTo(LoginView.NAME);
	}
}
