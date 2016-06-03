package hu.bugbusters.corpus.core.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.password.PasswordStorage.CannotPerformOperationException;
import hu.bugbusters.corpus.core.password.PasswordStorage.InvalidHashException;

public class Password {
	public static boolean verify(String password, RegisteredUser registeredUser) throws CannotPerformOperationException, InvalidHashException {
		String hash = String.format(
				"%s:%d:%d:%s",
				PasswordStorage.HASH_ALGORITHM,
				PasswordStorage.PBKDF2_ITERATIONS,
				PasswordStorage.HASH_BYTE_SIZE,
				registeredUser.getPassword()
		);
		
		return PasswordStorage.verifyPassword(password, hash);
	}
	
	public static String toDatabaseHash(String password) throws CannotPerformOperationException, InvalidHashException {
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
