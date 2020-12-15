package techshop.domain.entities;

/**
 * Contain user roles
 */
public enum Role {
	
	ADMIN(0), CLIENT(1);
	
	private int id;

	private Role(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public static Role getRoleById(int id) {
		return Role.values()[id];
	}
	
	public static Role getRoleByUser(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
}
