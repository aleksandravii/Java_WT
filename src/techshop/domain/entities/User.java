package techshop.domain.entities;

import techshop.domain.entities.Entity;

/**
 * User entity
 */
public class User extends Entity {

	private static final long serialVersionUID = 7905692692618674455L;
	
	private String login;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private boolean isBlocked;
	
	private int roleId;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", isBlocked=" + isBlocked + ", roleId=" + roleId + "]";
	}

}
