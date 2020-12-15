package techshop.web.exceptions;

import techshop.domain.exceptions.AppException;

/**
 * Throw when obtain invalid data
 */
public class UnknownDataException extends AppException {

	private static final long serialVersionUID = -810503218189549998L;
	
	public UnknownDataException() {

	}

	public UnknownDataException(String message) {
		super(message);
	}

	public UnknownDataException(Throwable cause) {
		super(cause);
	}

	public UnknownDataException(String message, Throwable cause) {
		super(message, cause);
	} 

}
