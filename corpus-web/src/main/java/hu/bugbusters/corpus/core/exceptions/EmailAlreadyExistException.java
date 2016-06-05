package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class EmailAlreadyExistException extends Exception {

	public EmailAlreadyExistException() {
		super();
	}

	public EmailAlreadyExistException(String message) {
		super(message);
	}

	public EmailAlreadyExistException(String message, Throwable source) {
		super(message, source);
	}
}
