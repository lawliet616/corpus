package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class InvalidPasswordFormatException extends Exception {
	public InvalidPasswordFormatException() {
		super();
	}
	
	public InvalidPasswordFormatException(String message) {
		super(message);
	}
}
