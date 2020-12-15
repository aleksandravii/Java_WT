package techshop.domain.entities;

import techshop.domain.entities.Entity;

/**
 * Order item entity
 */
public class OrderItem extends Entity {

	private static final long serialVersionUID = 7965772170602407588L;
	
	private int amount;
	
	private int orderId;
	
	private int goodsId;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	@Override
	public String toString() {
		return "OrderItem [amount=" + amount + ", orderId=" + orderId + ", goodsId=" + goodsId + "]";
	}
	
}
