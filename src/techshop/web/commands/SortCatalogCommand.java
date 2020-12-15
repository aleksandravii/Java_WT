package techshop.web.commands;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import techshop.domain.entities.Goods;
import techshop.web.exceptions.AccessException;
import techshop.web.utils.ActionType;
import techshop.web.utils.ConfigurationManager;
import techshop.web.utils.SessionRequestContent;

/**
 * Command for sort goods by specified type
 */
public class SortCatalogCommand implements ActionCommand {

	private static final long serialVersionUID = 8673830782777741067L;

	private static final Comparator<Goods> SORT_GOODS_BY_NAME = new Comparator<Goods>() {
		@Override
		public int compare(Goods o1, Goods o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	private static final Comparator<Goods> SORT_GOODS_BY_PRICE = new Comparator<Goods>() {
		@Override
		public int compare(Goods o1, Goods o2) {
			return Integer.valueOf(o1.getPrice()).compareTo(o2.getPrice());
		}
	};
	
	private static final Comparator<Goods> SORT_GOODS_BY_DATE = new Comparator<Goods>() {
		@Override
		public int compare(Goods o1, Goods o2) {
			return o2.getReleaseDate().compareTo(o1.getReleaseDate());
		}
	};
	
	private static final Logger LOG = Logger.getLogger(SortCatalogCommand.class);
	
	@Override
	public String execute(SessionRequestContent content) {
		LOG.debug("Command starts");
		
		String page = ConfigurationManager.getProperty("path.page.catalog");

		if (ActionType.GET == content.getActionType()) {
			doGet(content);
		} else {
			String errorMessage = "Try to unknown access to command";
			LOG.error(errorMessage);
			throw new AccessException(errorMessage);
		}
		
		LOG.debug("Command finished");
		return page;
		
	}
	
	@SuppressWarnings("unchecked")
	private void doGet(SessionRequestContent content) {
		//load data for catalog page
		CatalogCommand catalogCommand = new CatalogCommand();
		catalogCommand.execute(content);
		
		Map<String, Object> sessionAttributes = content.getSessionAttributes();
		Map<String, Object> requestAttributes = content.getRequestAttributes();
		
		List<Goods> listGoods = (List<Goods>) sessionAttributes.get("selected_goods");
		LOG.trace("Obtained list goods from session ==> " + listGoods);
		
		String sortType = content.getRequestParameter("sort");
		LOG.trace("Obtained sort type ==> " + sortType);
		
		if ("name_a_z".equals(sortType)) {
			sortGoodsByName(listGoods);
		} else if ("name_z_a".equals(sortType)){
			sortGoodsByName(listGoods);
			reverseGoodsList(listGoods);
		} else if ("from_cheap_to_expensive".equals(sortType)) {
			sortGoodsByPrice(listGoods);
		} else if ("from_expensive_to_cheap".equals(sortType)) {
			sortGoodsByPrice(listGoods);
			reverseGoodsList(listGoods);
		} else if ("release_date_new".equals(sortType)) {
			sortGoodsByDate(listGoods);
		} else if ("release_date_old".equals(sortType)) {
			sortGoodsByDate(listGoods);
			reverseGoodsList(listGoods);
		}
		
		requestAttributes.put("goods", listGoods);
		LOG.trace("Set list goods after sort to the request attributes ==> " + listGoods);
	}
	
	private void sortGoodsByName(List<Goods> listGoods) {
		Collections.sort(listGoods, SORT_GOODS_BY_NAME);
	}
	
	private void sortGoodsByPrice(List<Goods> listGoods) {
		Collections.sort(listGoods, SORT_GOODS_BY_PRICE);
	}
	
	private void sortGoodsByDate(List<Goods> listGoods) {
		Collections.sort(listGoods, SORT_GOODS_BY_DATE);
	}
	
	private void reverseGoodsList(List<Goods> listGoods) {
		Collections.reverse(listGoods);
	}
	
}
