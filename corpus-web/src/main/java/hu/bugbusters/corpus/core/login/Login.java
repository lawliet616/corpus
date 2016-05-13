package hu.bugbusters.corpus.core.login;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.vaadin.CorpusUI;

public class Login {
	public static final String SESSION_ATTRIBUTE_NAME = "userId";
	
	public static void setLoggedInUserId(Long id) {
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute(SESSION_ATTRIBUTE_NAME, id);
	}
	
	public static Long getLoggedInUserId() {
		return (Long) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SESSION_ATTRIBUTE_NAME);
	}
	
	public static Role getLoggedInUserRole() {
		Dao dao = new DaoImpl();
		RegisteredUser registeredUser = dao.getUserById(getLoggedInUserId());
		
		return registeredUser.getRole();
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
	}
}
