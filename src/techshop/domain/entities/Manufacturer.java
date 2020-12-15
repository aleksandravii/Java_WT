package techshop.domain.entities;

import techshop.domain.entities.Entity;

/**
 * Manufacturer entity
 */
public class Manufacturer extends Entity {

	private static final long serialVersionUID = 3660915490927722644L;
	
	private String manufacturerName;

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	@Override
	public String toString() {
		return "Manufacturer [manufacturerName=" + manufacturerName + "]";
	}
	
	
}
