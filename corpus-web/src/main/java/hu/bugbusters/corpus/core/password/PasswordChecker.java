package hu.bugbusters.corpus.core.password;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.MessageResolver;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;
import hu.bugbusters.corpus.core.bean.PasswordSettings;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.global.Global;

public class PasswordChecker {
	private static PasswordChecker checker;
	private PasswordValidator passwordValidator;
	private MessageResolver resolver;
	private RuleResult result;
	private Dao dao;
	private PasswordSettings settings;
	private int minLength;
	private int maxLength;
	private int minDigChar;
	private int minUpperChar;
	private int minLowerChar;
	private int minRules;
	private List<Rule> ruleList;
	private String propertiesPath;

	private PasswordChecker() throws IOException {
		config();
	}

	public static PasswordChecker getPasswordChecker() throws IOException {
		if (checker == null) {
			checker = new PasswordChecker();
		}
		return checker;
	}

	public boolean check(String pass) {
		PasswordData passwordData = new PasswordData(new Password(pass));
		result = passwordValidator.validate(passwordData);
		if (result.isValid()) {
			return true;
		} else {
			return false;
		}
	}

	private void config() throws IOException {
		ruleList = new ArrayList<>();
		dao = new DaoImpl();
		propertiesPath = Global.PASSWORD_ERROR_MESSAGE_PATH;

		setCustomSettings();

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

		configErrorMessages();
		passwordValidator = new PasswordValidator(resolver, ruleList);
	}

	private void setCustomSettings() {
		settings = dao.getPasswordSettings(PasswordRows.CUSTOM);
		if (settings == null) {
			setDefaultSettings();
		} else {
			setValues();
		}
	}

	public void setDefaultSettings() {
		settings = dao.getPasswordSettings(PasswordRows.DEFAULT);
		setValues();
	}

	private void setValues() {
		minLength = settings.getMinLength();
		maxLength = settings.getMaxLength();
		minDigChar = settings.getMinDigChar();
		minUpperChar = settings.getMinUpperChar();
		minLowerChar = settings.getMinLowerChar();
		minRules = settings.getMinRules();
	}

	private void configErrorMessages() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(propertiesPath);
		Properties properties = new Properties();
		properties.load(input);
		resolver = new MessageResolver(properties);
	}

	public String getErrorMessage() {
		if (result != null) {
			String error = "A jelszó ";
			for (String msg : passwordValidator.getMessages(result)) {
				error += msg + ", ";
			}
			error += ".";
			return error;
		}
		return "Nincs hibaüzenet.";
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
