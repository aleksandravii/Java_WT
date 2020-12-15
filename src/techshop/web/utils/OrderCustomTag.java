package techshop.web.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import techshop.domain.beans.OrderBean;
import techshop.domain.entities.User;

/**
 * Custom tag.
 * 
 * @author Nikita Datsenko
 *
 */
public class OrderCustomTag extends SimpleTagSupport {

	private OrderBean orderBean;

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.print("<td>" + orderBean.getId() + "</td>");
		out.print("<td class=\"date\">" + orderBean.getDate() + "</td>");
		out.print("<td class=\"price\">" + orderBean.getPrice() + "</td>");
		out.print("<td class=\"status\">" + orderBean.getStatus() + "</td>");

		List<String> goodsNames = orderBean.getGoodsNames();
		if (goodsNames != null && !goodsNames.isEmpty()) {
			out.print("<td>");
			for (String goodsName : goodsNames) {
				out.print(goodsName + "<br>");
				
			}
			out.print("</td>");
		}

		User user = orderBean.getUser();
		if (user != null) {
			out.print("<td>" + user.getFirstName() + " " + user.getLastName() + "</td>");
		}

	}
	
}
