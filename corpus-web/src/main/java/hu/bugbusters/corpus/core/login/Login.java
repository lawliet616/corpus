package hu.bugbusters.corpus.core.login;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.global.Global;

public class Login {
	public static final String SESSION_ATTRIBUTE_NAME = Global.LOGIN_SESSION_ATTRIBUTE_NAME;
	
	public static void setLoggedInUserId(Long id) {
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute(SESSION_ATTRIBUTE_NAME, id);
	}
	
	public static Long getLoggedInUserId() {
		return (Long) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SESSION_ATTRIBUTE_NAME);
	}
	
	public static RegisteredUser getLoggedInUser() {
		try {
			return DaoImpl.getInstance().getUserById(getLoggedInUserId());
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean loggedIn() {
		if(getLoggedInUserId() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void logOut() {
		VaadinService.getCurrentRequest().getWrappedSession().invalidate();
		UI.getCurrent().getPage().reload();
	}
}
