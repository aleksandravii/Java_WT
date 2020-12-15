package techshop.domain.beans;

import java.sql.Date;
import java.util.List;

import techshop.domain.entities.User;

/**
 * Order bean. Contain information about user
 */
public class OrderBean extends Bean {

	private static final long serialVersionUID = 3296200449032924470L;

	private Date date;
	
	private String status;
	
	private User user;
	
	private int price;
	
	private List<String> goodsNames;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getGoodsNames() {
		return goodsNames;
	}

	public void setGoodsNames(List<String> goodsNames) {
		this.goodsNames = goodsNames;
	}

	@Override
	public String toString() {
		return "OrderBean [date=" + date + ", status=" + status + ", user=" + user + ", price=" + price
				+ ", goodsNames=" + goodsNames + "]";
	}
	
}
