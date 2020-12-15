package techshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import techshop.domain.entities.Colour;
import techshop.domain.entities.Goods;
import techshop.domain.entities.Group;
import techshop.domain.entities.Manufacturer;

/**
 * Match set groups, manufacturers, colors by specified parameters and match
 * goods by this data
 */
public class GoodsMatcher {

	private List<Goods> listGoods;

	private List<Group> listGroups;

	private List<Manufacturer> listManufacturers;

	private List<Colour> listColours;
	

	private String[] requestGroups;

	private String[] requestManufacturers;

	private String[] requestColours;
	

	private List<Group> matchGroups = new ArrayList<>();

	private List<Manufacturer> matchManufacturers = new ArrayList<>();

	private List<Colour> matchColours = new ArrayList<>();
	

	private static final Logger LOG = Logger.getLogger(GoodsMatcher.class);

	public List<Goods> getListGoods() {
		return listGoods;
	}

	public void setListGoods(List<Goods> listGoods) {
		this.listGoods = listGoods;
	}

	public void setListGroups(List<Group> listGroups) {
		this.listGroups = listGroups;
	}

	public void setListManufacturers(List<Manufacturer> listManufacturers) {
		this.listManufacturers = listManufacturers;
	}

	public void setListColours(List<Colour> listColours) {
		this.listColours = listColours;
	}

	public void setRequestGroups(String[] requestGroups) {
		this.requestGroups = requestGroups;
	}

	public void setRequestManufacturers(String[] requestManufacturers) {
		this.requestManufacturers = requestManufacturers;
	}

	public void setRequestColours(String[] requestColours) {
		this.requestColours = requestColours;
	}

	/**
	 * Delegates list of goods to match by groups, manufacturers and colours to
	 * methods. Each of these methods takes a list of goods, compares with a
	 * particular group of fields, adds to new list and returns it.
	 */
	public void matchGoodsByData() {
		LOG.debug("Start match goods");
		LOG.trace("Matched goods ==> " + listGoods);

		// Match obtain category data with request group names from user
		matchRequestData();

		listGoods = matchGoodsByGroup(listGoods);
		listGoods = matchGoodsByManufacturer(listGoods);
		listGoods = matchGoodsByColour(listGoods);

		LOG.trace("Goods after match ==> " + listGoods);
		LOG.debug("Finished match goods");
	}

	private void matchRequestData() {
		LOG.debug("Start match data");

		matchGroups();
		matchManufacturers();
		matchColours();

		LOG.debug("Finished match data");
	}

	private void matchGroups() {
		if (requestGroups == null || requestGroups.length == 0) {
			return;
		}

		for (Group group : listGroups) {
			for (String groupName : requestGroups) {
				if (group.getGroupName().equals(groupName)) {
					matchGroups.add(group);
				}
			}
		}
	}

	private void matchManufacturers() {
		if (requestManufacturers == null || requestManufacturers.length == 0) {
			return;
		}

		for (Manufacturer manufacturer : listManufacturers) {
			for (String manufacturerName : requestManufacturers) {
				if (manufacturer.getManufacturerName().equals(manufacturerName)) {
					matchManufacturers.add(manufacturer);
				}
			}
		}
	}

	private void matchColours() {
		if (requestColours == null || requestColours.length == 0) {
			return;
		}

		for (Colour colour : listColours) {
			for (String colourName : requestColours) {
				if (colour.getColourName().equals(colourName)) {
					matchColours.add(colour);
				}
			}
		}
	}

	private List<Goods> matchGoodsByGroup(List<Goods> listGoods) {
		List<Goods> processListGoods = new ArrayList<>();
		processListGoods.addAll(listGoods);

		if (matchGroups == null || matchGroups.isEmpty()) {
			return processListGoods;
		}

		List<Goods> matchGoods = new ArrayList<>();
		for (Goods goods : processListGoods) {
			for (Group group : matchGroups) {
				if (goods.getGroupId() == group.getId()) {
					matchGoods.add(goods);
				}
			}
		}

		return matchGoods;
	}

	private List<Goods> matchGoodsByManufacturer(List<Goods> listGoods) {
		List<Goods> processListGoods = new ArrayList<>();
		processListGoods.addAll(listGoods);

		if (matchManufacturers == null || matchManufacturers.isEmpty()) {
			return processListGoods;
		}

		List<Goods> matchGoods = new ArrayList<>();
		for (Goods goods : processListGoods) {
			for (Manufacturer manufacturer : matchManufacturers) {
				if (goods.getManufacturerId() == manufacturer.getId()) {
					matchGoods.add(goods);
				}
			}
		}
		return matchGoods;
	}

	private List<Goods> matchGoodsByColour(List<Goods> listGoods) {
		List<Goods> processListGoods = new ArrayList<>();
		processListGoods.addAll(listGoods);

		if (matchColours == null || matchColours.isEmpty()) {
			return processListGoods;
		}

		List<Goods> matchGoods = new ArrayList<>();
		for (Goods goods : processListGoods) {
			for (Colour colour : matchColours) {
				if (goods.getColourId() == colour.getId()) {
					matchGoods.add(goods);
				}
			}
		}
		return matchGoods;
	}

}
