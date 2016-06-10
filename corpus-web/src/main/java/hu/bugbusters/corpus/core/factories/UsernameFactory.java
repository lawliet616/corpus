package hu.bugbusters.corpus.core.factories;

import java.util.Calendar;

import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.global.Global;

public class UsernameFactory {
	private static UsernameFactory factory;
	private Dao dao;

	private UsernameFactory() {
		dao = DaoImpl.getInstance();
	}

	public static UsernameFactory getUsernameFactory() {
		if (factory == null) {
			factory = new UsernameFactory();
		}
		return factory;
	}

	public String createUsername(String name) {
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
