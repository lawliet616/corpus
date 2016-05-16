package hu.bugbusters.corpus.core.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.admin.AdminView;
import hu.bugbusters.corpus.core.vaadin.view.login.LoginView;
import hu.bugbusters.corpus.core.vaadin.view.teacher.TeacherView;
import hu.bugbusters.corpus.core.vaadin.view.user.UserView;

@Theme("valo")
@SuppressWarnings("serial")
public class CorpusUI extends UI implements ViewChangeListener {
	Navigator navigator;
	
	@Override
	protected void init(final VaadinRequest request) {
		navigator = new Navigator(this, this);

		navigator.addView(LoginView.NAME, LoginView.class);
		navigator.addView(UserView.NAME, UserView.class);
		navigator.addView(TeacherView.NAME, TeacherView.class);
		navigator.addView(AdminView.NAME, AdminView.class);
		
		navigator.addViewChangeListener(this);
		
		navigate();
	}
	
	public void navigate() {
		if(!Login.loggedIn()) {
			navigator.navigateTo(LoginView.NAME);
		} else {
			navigateToViewByRole(Login.getLoggedInUser().getRole());
		}
	}

	private void navigateToViewByRole(Role role) {
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

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		if(event.getNewView().getClass() == LoginView.class) {
			if(Login.loggedIn()) {
				return false;
			} else {
				return true;
			}
		} else if(((CorpusView) event.getNewView()).getRole() == Login.getLoggedInUser().getRole()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
		
	}
}
