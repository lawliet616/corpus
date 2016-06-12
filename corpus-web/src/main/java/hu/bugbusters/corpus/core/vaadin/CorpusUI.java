package hu.bugbusters.corpus.core.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.global.Global;
import hu.bugbusters.corpus.core.login.Login;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.session.Session;
import hu.bugbusters.corpus.core.util.DbTest;
import hu.bugbusters.corpus.core.vaadin.view.CorpusView;
import hu.bugbusters.corpus.core.vaadin.view.admin.AdminView;
import hu.bugbusters.corpus.core.vaadin.view.login.LoginView;
import hu.bugbusters.corpus.core.vaadin.view.teacher.TeacherView;
import hu.bugbusters.corpus.core.vaadin.view.user.UserView;

@Theme("CorpusTheme")
@SuppressWarnings("serial")
public class CorpusUI extends UI implements ViewChangeListener {
	Navigator navigator;
	
	@Override
	protected void init(final VaadinRequest request) {
		DbTest.run();
		getPage().setTitle("Corpus");
		
		navigator = new Navigator(this, this);

		navigator.addView(LoginView.NAME, LoginView.class);
		navigator.addView(UserView.NAME, UserView.class);
		navigator.addView(TeacherView.NAME, TeacherView.class);
		navigator.addView(AdminView.NAME, AdminView.class);
		
		navigator.addViewChangeListener(this);
		
		navigate();
		System.out.println(navigator.getCurrentView());
	}
	
	public void navigate() {
		navigate("");
	}
	
	public void navigate(String parameters) {
		String navigatorString;
		
		if(!Login.loggedIn()) {
			navigatorString = LoginView.NAME;
		} else {
			if(navigator.getCurrentView() == null) {
				navigatorString = (String) Session.getAttribute(Global.NAVIGATOR_SESSION_ATTRIBUTE_NAME);
			} else {
				navigatorString = String.format(
						"%s/%s",
						navigateToViewByRole(Login.getLoggedInUser().getRole()),
						parameters
				);
				
				Session.setAttribute(Global.NAVIGATOR_SESSION_ATTRIBUTE_NAME, navigatorString);
			}
		}
		
		navigator.navigateTo(navigatorString);
	}

	private String navigateToViewByRole(Role role) {
		String view;
		switch(role) {
		case ADMIN:
			view = AdminView.NAME;
			break;
		case TEACHER:
			view = TeacherView.NAME;
			break;
		case USER:
			view = UserView.NAME;
			break;
		default:
			throw new IllegalArgumentException("Unknown view");
		}
		
		return view;
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
