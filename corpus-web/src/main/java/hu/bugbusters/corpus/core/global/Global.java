package hu.bugbusters.corpus.core.global;

public class Global {
	public static final int DAO_BATCH_SIZE                   = 20;
	
	public static final String LOGIN_SESSION_ATTRIBUTE_NAME  = "userId";
	
	public static final String ROLE_ADMIN_NAME               = "Rendszergazda";
	public static final String ROLE_TEACHER_NAME             = "Tanár";
	public static final String ROLE_USER_NAME                = "Diák";
	
	public static final String PASSWORD_ERROR_MESSAGE_PATH   = "hu/bugbusters/corpus/core/resources/password.properties";

	public static final int FULLNAMELENGTH_IN_USERNAME       = 3;
	public static final char ADDITIONALCHARACTER_IN_USERNAME = 'A';
}
