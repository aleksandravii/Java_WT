package techshop.domain.entities;

import java.io.Serializable;

/**
 * Root class for all entities
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 3471872345953520463L;
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Entity other = (Entity) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}

}
