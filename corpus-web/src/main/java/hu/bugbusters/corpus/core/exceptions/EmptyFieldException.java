package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class EmptyFieldException extends Exception {
	public EmptyFieldException() {
		super();
	}

	public EmptyFieldException(String message) {
		super(message);
	}
}
