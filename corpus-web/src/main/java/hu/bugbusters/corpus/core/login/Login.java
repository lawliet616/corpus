package hu.bugbusters.corpus.core.login;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.login.PasswordStorage.CannotPerformOperationException;
import hu.bugbusters.corpus.core.login.PasswordStorage.InvalidHashException;

public class Login {
	public static final String SESSION_ATTRIBUTE_NAME = "userId";
	
	public static void setLoggedInUserId(Long id) {
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute(SESSION_ATTRIBUTE_NAME, id);
	}
	
	public static Long getLoggedInUserId() {
		return (Long) VaadinService.getCurrentRequest().getWrappedSession().getAttribute(SESSION_ATTRIBUTE_NAME);
	}
	
	public static RegisteredUser getLoggedInUser() {
		return new DaoImpl().getUserById(getLoggedInUserId());
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
	
	public static boolean passwordVerifiy(String password, RegisteredUser registeredUser) throws CannotPerformOperationException, InvalidHashException {
		String hash = String.format(
				"%s:%d:%d:%s",
				"sha1",
				PasswordStorage.PBKDF2_ITERATIONS,
				PasswordStorage.HASH_BYTE_SIZE,
				registeredUser.getPassword()
		);
		
		return PasswordStorage.verifyPassword(password, hash);
	}
}
