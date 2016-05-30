package hu.bugbusters.corpus.core.password;

import java.util.ArrayList;
import java.util.List;

import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;

public class PasswordChecker {
	private static PasswordChecker checker;
	private static PasswordValidator passwordValidator;
	private static int minLength;
	private static int maxLength;
	private static int minDigChar;
	private static int minUpperChar;
	private static int minLowerChar;
	private static int minRules;
	private static List<Rule> ruleList;

	private PasswordChecker() {
	}

	public static PasswordChecker getPasswordChecker() {
		if (checker == null) {
			checker = new PasswordChecker();
			config();
		}
		return checker;
	}

	public boolean check(String pass) {
		PasswordData passwordData = new PasswordData(new Password(pass));
		RuleResult result = passwordValidator.validate(passwordData);
		if (result.isValid()) {
			return true;
		} else {
			return false;
		}
	}

	private static void config() {
		ruleList = new ArrayList<>();

		setDefaultSettings();

		LengthRule lengthRule = new LengthRule(minLength, maxLength);
		WhitespaceRule whitespaceRule = new WhitespaceRule();
		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
		charRule.getRules().add(new DigitCharacterRule(minDigChar));
		charRule.getRules().add(new UppercaseCharacterRule(minUpperChar));
		charRule.getRules().add(new LowercaseCharacterRule(minLowerChar));
		charRule.setNumberOfCharacteristics(minRules);

		ruleList.add(lengthRule);
		ruleList.add(whitespaceRule);
		ruleList.add(charRule);

		passwordValidator = new PasswordValidator(ruleList);
	}

	public static void setDefaultSettings() {
		minLength = 8;
		maxLength = 16;
		minDigChar = 1;
		minUpperChar = 1;
		minLowerChar = 1;
		minRules = 3;
	}

	public static int getMinLength() {
		return minLength;
	}

	public static void setMinLength(int minLength) {
		PasswordChecker.minLength = minLength;
	}

	public static int getMaxLength() {
		return maxLength;
	}

	public static void setMaxLength(int maxLength) {
		PasswordChecker.maxLength = maxLength;
	}

	public static int getMinDigChar() {
		return minDigChar;
	}

	public static void setMinDigChar(int minDigChar) {
		PasswordChecker.minDigChar = minDigChar;
	}

	public static int getMinUpperChar() {
		return minUpperChar;
	}

	public static void setMinUpperChar(int minUpperChar) {
		PasswordChecker.minUpperChar = minUpperChar;
	}

	public static int getMinLowerChar() {
		return minLowerChar;
	}

	public static void setMinLowerChar(int minLowerChar) {
		PasswordChecker.minLowerChar = minLowerChar;
	}

	public static int getMinRules() {
		return minRules;
	}

	public static void setMinRules(int minRules) {
		PasswordChecker.minRules = minRules;
	}

}
