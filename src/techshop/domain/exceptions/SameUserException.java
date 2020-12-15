package techshop.domain.exceptions;

import techshop.domain.exceptions.DomainException;

/**
 * Throw if current user already exist
 */
public class SameUserException extends DomainException {

	private static final long serialVersionUID = 512938001768649750L;
	
	public SameUserException() {

	}

	public SameUserException(String message) {
		super(message);
	}

	public SameUserException(Throwable cause) {
		super(cause);
	}

	public SameUserException(String message, Throwable cause) {
		super(message, cause);
	} 
}
