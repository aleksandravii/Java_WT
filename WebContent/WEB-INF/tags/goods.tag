<%@ include file="/WEB-INF/jspf/taglib.jspf"%>
<%@tag pageEncoding="UTF-8"%>
<%@tag body-content="empty"%>
<%@ attribute name="goods" required="true" type="techshop.domain.entities.Goods"%>

<p>${goods.name}</p>
			
<p><fmt:message key="cart.price"/> ${goods.price} <fmt:message key="cart.cost.unit"/></p>
			
<p><fmt:message key="goods.release.date"/> ${goods.releaseDate}</p>