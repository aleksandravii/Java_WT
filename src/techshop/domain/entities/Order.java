package techshop.domain.entities;

import techshop.domain.entities.Entity;

import java.sql.Date;

/**
 * Order entity
 */
public class Order extends Entity {

	private static final long serialVersionUID = -314751665357976457L;
	
	private Date date;
	
	private String status;
	
	private int price;
	
	private int userId;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Order [date=" + date + ", status=" + status + ", price=" + price + ", userId=" + userId + "]";
	}
	
}
