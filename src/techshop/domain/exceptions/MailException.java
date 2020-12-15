package techshop.domain.exceptions;

import techshop.domain.exceptions.DomainException;

/**
 * Throws when email occurred error
 */
public class MailException extends DomainException {

	private static final long serialVersionUID = 512938001768649750L;
	
	public MailException() {

	}

	public MailException(String message) {
		super(message);
	}

	public MailException(Throwable cause) {
		super(cause);
	}

	public MailException(String message, Throwable cause) {
		super(message, cause);
	} 
}