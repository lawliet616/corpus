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
	private PasswordChecker checker;
	private PasswordValidator passwordValidator;
	private int minLength;
	private int maxLength;
	private int minDigChar;
	private int minUpperChar;
	private int minLowerChar;
	private int minRules;
	private List<Rule> ruleList;

	private PasswordChecker() {
	}

	public PasswordChecker getPasswordChecker() {
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

	private void config() {
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

	public void setDefaultSettings() {
		minLength = 8;
		maxLength = 16;
		minDigChar = 1;
		minUpperChar = 1;
		minLowerChar = 1;
		minRules = 3;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinDigChar() {
		return minDigChar;
	}

	public void setMinDigChar(int minDigChar) {
		this.minDigChar = minDigChar;
	}

	public int getMinUpperChar() {
		return minUpperChar;
	}

	public void setMinUpperChar(int minUpperChar) {
		this.minUpperChar = minUpperChar;
	}

	public int getMinLowerChar() {
		return minLowerChar;
	}

	public void setMinLowerChar(int minLowerChar) {
		this.minLowerChar = minLowerChar;
	}

	public int getMinRules() {
		return minRules;
	}

	public void setMinRules(int minRules) {
		this.minRules = minRules;
	}

}
