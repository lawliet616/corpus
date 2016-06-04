package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception {
	public InvalidPasswordException() {
		super();
	}
	
	public InvalidPasswordException(String message) {
		super(message);
	}
}
