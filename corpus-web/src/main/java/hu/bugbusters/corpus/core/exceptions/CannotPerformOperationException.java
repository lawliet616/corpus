package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class CannotPerformOperationException extends Exception {
	public CannotPerformOperationException() {
        super();
    }
	
	public CannotPerformOperationException(String message) {
        super(message);
    }
	
    public CannotPerformOperationException(String message, Throwable source) {
        super(message, source);
    }
}
