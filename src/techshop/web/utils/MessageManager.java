package techshop.web.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Message manager. Provide get access to resource bundle that contain messages
 * to user.
 * 
 * @author Nikita Datsenko
 *
 */
public class MessageManager {

	private static ResourceBundle resource_bundle = ResourceBundle.getBundle("resources", Locale.getDefault());

	public static void setLocale(String language) {
		resource_bundle = ResourceBundle.getBundle("resources", new Locale(language));
	}

	public static String getProperty(String key) {
		return resource_bundle.getString(key);
	}

}
