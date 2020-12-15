package techshop.web.exceptions;

import techshop.domain.exceptions.AppException;

/**
 * Throw when occurred try unknown access
 */
public class AccessException extends AppException {

	private static final long serialVersionUID = -3592946134040412385L;
	
	public AccessException() {

	}

	public AccessException(String message) {
		super(message);
	}

	public AccessException(Throwable cause) {
		super(cause);
	}

	public AccessException(String message, Throwable cause) {
		super(message, cause);
	} 

}
