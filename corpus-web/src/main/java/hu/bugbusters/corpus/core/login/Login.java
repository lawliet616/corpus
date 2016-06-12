package hu.bugbusters.corpus.core.login;

import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.global.Global;
import hu.bugbusters.corpus.core.session.Session;

public class Login {
	public static final String SESSION_ATTRIBUTE_NAME = Global.LOGIN_SESSION_ATTRIBUTE_NAME;
	
	public static void setLoggedInUserId(Long id) {
		Session.setAttribute(SESSION_ATTRIBUTE_NAME, id);
	}
	
	public static Long getLoggedInUserId() {
		return (Long)Session.getAttribute(SESSION_ATTRIBUTE_NAME);
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
		Session.invalidate();
		UI.getCurrent().getPage().reload();
	}
}
