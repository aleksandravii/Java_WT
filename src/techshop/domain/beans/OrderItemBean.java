package techshop.domain.beans;

import techshop.domain.entities.Goods;

/**
 * Order item bean. Contain information about goods, amount of goods and total price
 */
public class OrderItemBean extends Bean {

	private static final long serialVersionUID = -5033808194292726909L;

	private int amount;

	private Goods goods;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public int getPrice() {
		return amount * goods.getPrice();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((goods == null) ? 0 : goods.hashCode());
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
		OrderItemBean other = (OrderItemBean) obj;
		if (goods == null) {
			if (other.goods != null) {
				return false;
			}
		} else if (!goods.equals(other.goods)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "OrderItemBean [amount=" + amount + ", goods=" + goods + "]";
	}

}
