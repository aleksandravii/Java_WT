package techshop.dao.exceptions;

/**
 * Throws when in database was found same entity
 */
public class AlreadyExistException extends DAOException {

	private static final long serialVersionUID = -2462800354764031248L;

	public AlreadyExistException() {

	}

	public AlreadyExistException(String message) {
		super(message);
	}

	public AlreadyExistException(Throwable cause) {
		super(cause);
	}

	public AlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
