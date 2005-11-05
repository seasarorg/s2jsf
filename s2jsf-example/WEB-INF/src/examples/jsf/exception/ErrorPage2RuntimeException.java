package examples.jsf.exception;

public class ErrorPage2RuntimeException extends RuntimeException {

	/**
	 * 
	 */
	public ErrorPage2RuntimeException() {
		super();
	}

	/**
	 * @param message
	 */
	public ErrorPage2RuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorPage2RuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorPage2RuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
