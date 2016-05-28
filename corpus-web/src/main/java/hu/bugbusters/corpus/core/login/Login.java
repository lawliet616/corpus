package hu.bugbusters.corpus.core.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static boolean passwordVerify(String password, RegisteredUser registeredUser) throws CannotPerformOperationException, InvalidHashException {
		String hash = String.format(
				"%s:%d:%d:%s",
				PasswordStorage.HASH_ALGORITHM,
				PasswordStorage.PBKDF2_ITERATIONS,
				PasswordStorage.HASH_BYTE_SIZE,
				registeredUser.getPassword()
		);
		
		return PasswordStorage.verifyPassword(password, hash);
	}
	
	public static String passwordToDatabaseHash(String password) throws CannotPerformOperationException, InvalidHashException {
		String hash     = PasswordStorage.createHash(password);
		Pattern pattern = Pattern.compile("(\\p{Alnum}+):(\\d+):(\\d+):(\\p{ASCII}+)");
		Matcher matcher = pattern.matcher(hash);
		
		if(matcher.find()) {
			return matcher.group(4);
		} else {
			throw new InvalidHashException("Hash doesn't match the pattern");
		}
	}
}
