package techshop.dao.exceptions;

import techshop.domain.exceptions.AppException;

/**
 * Throws in DAO layer
 */
public class DAOException extends AppException {

	private static final long serialVersionUID = -7932845741761505884L;

	public DAOException() {

	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
