package hu.bugbusters.corpus.core.factories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.vt.middleware.password.CharacterRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.PasswordGenerator;
import edu.vt.middleware.password.UppercaseCharacterRule;
import hu.bugbusters.corpus.core.bean.PasswordSettings;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.global.Global;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.password.Password;
import hu.bugbusters.corpus.core.password.PasswordRows;
import hu.bugbusters.corpus.core.password.PasswordStorage.CannotPerformOperationException;
import hu.bugbusters.corpus.core.password.PasswordStorage.InvalidHashException;

public class UserFactory {
	private RegisteredUser registeredUser;
	private Dao dao;

	public UserFactory() {
		dao = new DaoImpl();
	}

	public RegisteredUser createRegisteredUser(String name, String email, Role role)
			throws CannotPerformOperationException, InvalidHashException {
		registeredUser = new RegisteredUser();
		registeredUser.setFullname(name);
		registeredUser.setEmail(email);
		registeredUser.setRole(role);
		registeredUser.setUsername(createUsername(name));
		registeredUser.setPassword(generatePassword());

		return registeredUser;
	}

	private String generatePassword() throws CannotPerformOperationException, InvalidHashException {
		String password = Password.toDatabaseHash(registeredUser.getUsername());
		/* Generate a valid password.
		 * 
		 * PasswordGenerator generator = new PasswordGenerator();
		 * PasswordSettings settings = dao.getPasswordSettings(PasswordRows.CUSTOM);
		 * 
		 * if (settings == null) { 
		 * 		settings = dao.getPasswordSettings(PasswordRows.DEFAULT);
		 * }
		 * 
		 * List<CharacterRule> rules = new ArrayList<>();
		 * rules.add(new DigitCharacterRule(settings.getMinDigChar()));
		 * rules.add(new UppercaseCharacterRule(settings.getMinUpperChar()));
		 * rules.add(new LowercaseCharacterRule(settings.getMinLowerChar()));
		 * 
		 * password = generator.generatePassword(settings.getMaxLength(),
		 * rules); password = Password.toDatabaseHash(password);
		 */
		return password;
	}

	private String createUsername(String name) {
		String username = "";
		char[] charArray = name.toCharArray();
		int fullnameLengthInUsername = Global.FULLNAMELENGTH_IN_USERNAME;
		char additionalCharacter = Global.ADDITIONALCHARACTER_IN_USERNAME;

		if (charArray.length >= fullnameLengthInUsername) {
			username = nameToUsername(username, charArray, fullnameLengthInUsername);
		} else {
			username = nameToUsername(username, charArray, charArray.length);
			for (int i = 0; i < fullnameLengthInUsername - charArray.length; i++) {
				username += additionalCharacter;
			}
		}
		Calendar now = Calendar.getInstance();
		username += now.get(Calendar.YEAR);

		char lastCharacter = additionalCharacter;
		while (!isUsernameUnique(username, lastCharacter)) {
			lastCharacter += 1;
		}

		username += lastCharacter;

		return username;
	}

	private boolean isUsernameUnique(String username, char lastCharacter) {
		List<RegisteredUser> registeredUserList = new DaoImpl().getUserByUserName(username + lastCharacter);
		if (registeredUserList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private String nameToUsername(String username, char[] charArray, int length) {
		for (int i = 0; i < length; i++) {
			username += Character.toUpperCase(charArray[i]);
		}
		return username;
	}

}
