package techshop.domain.entities;

/**
 * Color entity
 */
public class Colour extends Entity {

	private static final long serialVersionUID = -6265594600976446124L;
	
	private String colourName;

	public String getColourName() {
		return colourName;
	}

	public void setColourName(String colorName) {
		this.colourName = colorName;
	}

	@Override
	public String toString() {
		return "Colour [colourName=" + colourName + "]";
	}
	
}
