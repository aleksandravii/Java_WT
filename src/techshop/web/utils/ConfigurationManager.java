package techshop.web.utils;

import java.util.ResourceBundle;

/**
 * Configurator manager. Provide get access to resource bundle that contain some
 * path page.
 * 
 * @author Nikita Datsenko
 *
 */
public class ConfigurationManager {

	private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

	public static String getProperty(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}

}
