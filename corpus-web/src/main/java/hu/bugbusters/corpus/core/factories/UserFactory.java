package hu.bugbusters.corpus.core.factories;

import java.util.Calendar;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CannotPerformOperationException;
import hu.bugbusters.corpus.core.exceptions.EmailAlreadyExistException;
import hu.bugbusters.corpus.core.exceptions.InvalidHashException;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.global.Global;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.password.Password;

public class UserFactory {
	private static UserFactory factory;
	private Dao dao = DaoImpl.getInstance();

	private UserFactory() {
	}

	public static UserFactory getUserFactory() {
		if (factory == null) {
			factory = new UserFactory();
		}
		return factory;
	}

	public RegisteredUser createRegisteredUser(String name, String email, Role role)
			throws CannotPerformOperationException, InvalidHashException, EmailAlreadyExistException {
		if(!isEmailUnique(email)) {
			throw new EmailAlreadyExistException();
		}
		RegisteredUser registeredUser = new RegisteredUser();
		registeredUser.setFullName(name);
		registeredUser.setEmail(email);
		registeredUser.setRole(role);
		registeredUser.setUsername(createUsername(name));
		registeredUser.setPassword(generatePassword(registeredUser));

		return registeredUser;
	}

	public RegisteredUser createAndSaveRegisteredUser(String name, String email, Role role)
			throws CannotPerformOperationException, InvalidHashException, EmailAlreadyExistException {
		RegisteredUser registeredUser = createRegisteredUser(name, email, role);
		dao.saveEntity(registeredUser);

		return registeredUser;
	}

	private boolean isEmailUnique(String email) {
		try {
			dao.getUserByEmail(email);
			return false;
		} catch (UserNotFoundException e) {
			return true;
		}
	}

	private String generatePassword(RegisteredUser  registeredUser) throws CannotPerformOperationException, InvalidHashException {
		String password = Password.toDatabaseHash(registeredUser.getUsername());
		/*
		 * Generate a valid password.
		 * 
		 * PasswordGenerator generator = new PasswordGenerator();
		 * PasswordSettings settings =
		 * dao.getPasswordSettings(PasswordRows.CUSTOM);
		 * 
		 * if (settings == null) { settings =
		 * dao.getPasswordSettings(PasswordRows.DEFAULT); }
		 * 
		 * List<CharacterRule> rules = new ArrayList<>(); rules.add(new
		 * DigitCharacterRule(settings.getMinDigChar())); rules.add(new
		 * UppercaseCharacterRule(settings.getMinUpperChar())); rules.add(new
		 * LowercaseCharacterRule(settings.getMinLowerChar()));
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
		try {
			dao.getUserByUserName(username + lastCharacter);

			return false;
		} catch (UserNotFoundException e) {
			return true;
		}
	}

	private String nameToUsername(String username, char[] charArray, int length) {
		for (int i = 0; i < length; i++) {
			username += Character.toUpperCase(charArray[i]);
		}
		return username;
	}

}
