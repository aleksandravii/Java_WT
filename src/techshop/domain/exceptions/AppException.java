package techshop.domain.exceptions;

/**
 * An exception that provides information on an application error
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = -5900990736983551757L;

	public AppException() {

	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	} 
	
}
