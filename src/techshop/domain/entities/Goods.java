package techshop.domain.entities;

import java.sql.Date;

/**
 * Goods entity
 */
public class Goods extends Entity {
	
	private static final long serialVersionUID = -1345119814327335999L;
	
	private String name;
	
	private int price;
	
	private Date releaseDate;
	
	private int colourId;
	
	private int groupId;
	
	private int manufacturerId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getColourId() {
		return colourId;
	}

	public void setColourId(int colorId) {
		this.colourId = colorId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + colourId;
		result = prime * result + groupId;
		result = prime * result + manufacturerId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Goods other = (Goods) obj;
		if (colourId != other.colourId) {
			return false;
		}
		if (groupId != other.groupId) {
			return false;
		}
		if (manufacturerId != other.manufacturerId) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Goods [name=" + name + ", price=" + price + ", releaseDate=" + releaseDate + ", colourId=" + colourId
				+ ", groupId=" + groupId + ", manufacturerId=" + manufacturerId + "]";
	}

}
