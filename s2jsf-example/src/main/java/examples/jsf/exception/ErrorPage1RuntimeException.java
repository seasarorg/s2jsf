package examples.jsf.exception;

public class ErrorPage1RuntimeException extends RuntimeException {

	/**
	 * 
	 */
	public ErrorPage1RuntimeException() {
		super();
	}

	/**
	 * @param message
	 */
	public ErrorPage1RuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorPage1RuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorPage1RuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
