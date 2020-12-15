package techshop.domain.exceptions;

import techshop.domain.exceptions.AppException;

/**
 * Throws in domain layer
 */
public class DomainException extends AppException {
	
	private static final long serialVersionUID = -7319889044856951922L;

	public DomainException() {

	}

	public DomainException(String message) {
		super(message);
	}

	public DomainException(Throwable cause) {
		super(cause);
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	} 
	
}
