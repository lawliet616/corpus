package hu.bugbusters.corpus.core.exceptions;

@SuppressWarnings("serial")
public class CourseNotFoundException extends Exception {
	public CourseNotFoundException() {
		super();
	}

	public CourseNotFoundException(String message) {
		super(message);
	}
}
