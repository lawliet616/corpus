package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CannotPerformOperationException;
import hu.bugbusters.corpus.core.exceptions.EmailAlreadyExistException;
import hu.bugbusters.corpus.core.exceptions.InvalidHashException;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.password.Password;

public class UserFactory {
	private static UserFactory userFactory;
	private Dao dao;
	private UsernameFactory usernameFactory;

	private UserFactory() {
		DaoImpl.getInstance();
		usernameFactory = UsernameFactory.getUsernameFactory();
	}

	public static UserFactory getUserFactory() {
		if (userFactory == null) {
			userFactory = new UserFactory();
		}
		return userFactory;
	}

	public RegisteredUser createRegisteredUser(String name, String email, Role role)
			throws CannotPerformOperationException, InvalidHashException, EmailAlreadyExistException {
		if (!isEmailUnique(email)) {
			throw new EmailAlreadyExistException();
		}
		RegisteredUser registeredUser = new RegisteredUser();
		registeredUser.setFullName(name);
		registeredUser.setEmail(email);
		registeredUser.setRole(role);
		registeredUser.setUsername(usernameFactory.createUsername(name));
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

	private String generatePassword(RegisteredUser registeredUser)
			throws CannotPerformOperationException, InvalidHashException {
		String password = Password.toDatabaseHash(registeredUser.getUsername());
		/*
		 * @formatter:off Generate a valid password.
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
		 * password = generator.generatePassword(settings.getMaxLength(),rules);
		 * password = Password.toDatabaseHash(password);
		 * 
		 * @formatter:on
		 */
		return password;
	}

}
