package techshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import techshop.dao.GoodsDAO;
import techshop.dao.exceptions.DAOException;
import techshop.domain.entities.Colour;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Manufacturer;
import techshop.domain.exceptions.DomainException;

/**
 * Contain methods to manage goods entity. Methods call data from DAO layer and
 * process it. If occurred error, then perform add information to LOG and throws
 * {@link DomainException}
 */
public class GoodsManager {

	private static GoodsDAO goodsDAO = new GoodsDAO();

	private static final Logger LOG = Logger.getLogger(GoodsManager.class);

	public static Goods createNewGoods(Goods goods) {
		try {
			return goodsDAO.insertGoods(goods);
		} catch (DAOException e) {
			LOG.error("Cannot save goods!", e);
			throw new DomainException(e);
		}
	}

	public static void updateGoods(Goods goods) {
		try {
			goodsDAO.updateGoods(goods);
		} catch (DAOException e) {
			LOG.error("Cannot update goods!", e);
			throw new DomainException(e);
		}
	}

	public static void removeGoods(int id) {
		try {
			goodsDAO.removeGoods(id);
		} catch (DAOException e) {
			LOG.error("Cannot update goods!", e);
			throw new DomainException(e);
		}
	}

	public static List<Goods> getAllGoods() {
		try {
			return goodsDAO.getAllGoods();
		} catch (DAOException e) {
			LOG.error("Cannot obtain goods!", e);
			throw new DomainException(e);
		}
	}

	public static Goods getGoodsById(int id) {
		try {
			return goodsDAO.getGoodsById(id);
		} catch (DAOException e) {
			LOG.error("Cannot obtain goods!", e);
			throw new DomainException(e);
		}
	}

	public static List<Goods> getGoodsByCategory(String categoryName) {
		try {
			return goodsDAO.getGoodsByCategory(categoryName);
		} catch (DAOException e) {
			LOG.error("Cannot obtain goods!", e);
			throw new DomainException(e);
		}
	}

	public static List<String> getGoodsNamesByOrderId(int orderId) {
		try {
			return goodsDAO.getGoodsNamesByOrderId(orderId);
		} catch (DAOException e) {
			LOG.error("Cannot obtain goods!", e);
			throw new DomainException(e);
		}
	}

	public static List<Goods> findGoodsByPriceRange(List<Goods> listGoods, int priceFrom, int priceTo) {
		List<Goods> matchGoods = new ArrayList<>();
		int goodsPrice = 0;

		for (Goods goods : listGoods) {
			goodsPrice = goods.getPrice();
			if (goodsPrice >= priceFrom && goodsPrice <= priceTo) {
				matchGoods.add(goods);
			}
		}

		return matchGoods;
	}

	public static List<Manufacturer> getAllManufacturers() {
		try {
			return goodsDAO.getAllManufacturers();
		} catch (DAOException e) {
			LOG.error("Cannot obtain manufacturers!", e);
			throw new DomainException(e);
		}
	}

	public static List<Manufacturer> getManufacturersByCategory(String categoryName) {
		try {
			return goodsDAO.getManufacturersByCategory(categoryName);
		} catch (DAOException e) {
			LOG.error("Cannot obtain manufacturers!", e);
			throw new DomainException(e);
		}
	}

	public static List<Colour> getAllColours() {
		try {
			return goodsDAO.getAllColours();
		} catch (DAOException e) {
			LOG.error("Cannot obtain colours!", e);
			throw new DomainException(e);
		}
	}

	public static List<Colour> getColoursByCategory(String categoryName) {
		try {
			return goodsDAO.getColoursByCategory(categoryName);
		} catch (DAOException e) {
			LOG.error("Cannot obtain colours!", e);
			throw new DomainException(e);
		}
	}

	public static int getMaxPrice() {
		try {
			return goodsDAO.getMaxPrice();
		} catch (DAOException e) {
			LOG.error("Cannot obtain price!", e);
			throw new DomainException(e);
		}
	}

	public static int getMinPrice() {
		try {
			return goodsDAO.getMinPrice();
		} catch (DAOException e) {
			LOG.error("Cannot obtain price!", e);
			throw new DomainException(e);
		}
	}
	
	//additional task
	public static List<Goods> getGoodsByCount(int count) {
		try {
			return goodsDAO.getGoodsByCount(count);
		} catch (DAOException e) {
			LOG.error("Cannot obtain goods!", e);
			throw new DomainException(e);
		}
	}

}
