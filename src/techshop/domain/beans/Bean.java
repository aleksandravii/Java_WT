package techshop.domain.beans;

import java.io.Serializable;

/**
 * Root bean class
 */
public abstract class Bean implements Serializable {

	private static final long serialVersionUID = -7069077184559177837L;
	
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
		Bean other = (Bean) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Bean [id=" + id + "]";
	}

}
