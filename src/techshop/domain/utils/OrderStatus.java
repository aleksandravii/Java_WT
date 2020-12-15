package techshop.domain.utils;

/**
 * Order statuses
 */
public enum OrderStatus {

	REGISTERED, PAID, CANCELLED;

	public String getName() {
		return name().toLowerCase();
	}

}
