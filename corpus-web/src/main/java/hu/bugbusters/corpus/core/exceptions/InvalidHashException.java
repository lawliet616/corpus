package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class InvalidHashException extends Exception {
	public InvalidHashException() {
        super();
    }
	
	public InvalidHashException(String message) {
        super(message);
    }
	
    public InvalidHashException(String message, Throwable source) {
        super(message, source);
    }
}
