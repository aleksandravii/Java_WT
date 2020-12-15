package techshop.domain.beans;

import techshop.domain.beans.Bean;

import java.sql.Date;

/**
 * Goods bean. Contain manufacturer, group, and category names
 */
public class GoodsBean extends Bean {

	private static final long serialVersionUID = 6101074970390109931L;

	private String name;

	private int price;
	
	private Date releaseDate;

	private String manufacturerName;
	
	private String groupName;
	
	private String categotieName;

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

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCategotieName() {
		return categotieName;
	}

	public void setCategotieName(String categotieName) {
		this.categotieName = categotieName;
	}

	@Override
	public String toString() {
		return "GoodsBean [name=" + name + ", price=" + price + ", releaseDate=" + releaseDate + ", manufacturerName="
				+ manufacturerName + ", groupName=" + groupName + ", categotieName=" + categotieName + "]";
	}

}
