package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class NewPasswordNotMatchException extends Exception {
	public NewPasswordNotMatchException() {
		super();
	}
	
	public NewPasswordNotMatchException(String message) {
		super(message);
	}
}
