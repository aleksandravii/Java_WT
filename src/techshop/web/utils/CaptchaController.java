package techshop.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import techshop.web.exceptions.AccessException;

/**
 * Captcha controller.
 * 
 * @author Nikita Datsenko
 *
 */
public class CaptchaController {

	private static final String SECRET_KEY = "6LfXlREUAAAAANTER38NwXI_v7N_cyiiAeJOgcSN";

	private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

	private static final Logger LOG = Logger.getLogger(CaptchaController.class);

	public static boolean isValid(String gRecaptchaResponse) {
		LOG.debug("Verify started");
		LOG.trace("gRecaptchaResponse = " + gRecaptchaResponse);

		boolean valid = false;
		try {
			valid = verify(gRecaptchaResponse);
		} catch (IOException e) {
			LOG.error("Verifying error!", e);
			throw new AccessException("Captcha processing error", e);
		}

		if (valid) {
			LOG.trace("Captcha valid");
		} else {
			LOG.trace("Captcha invalid!");
		}

		LOG.debug("Verify finisheded");

		return valid;
	}

	/**
	 * Send request from captcha to the server and get boolean responce.
	 * 
	 * @param gRecaptchaResponse
	 *            from captcha.
	 * @return verify boolean result.
	 * @throws IOException
	 */
	private static boolean verify(String gRecaptchaResponse) throws IOException {
		if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
			return false;
		}

		URL verifyUrl = new URL(SITE_VERIFY_URL);

		HttpsURLConnection connection = (HttpsURLConnection) verifyUrl.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);

		String postParams = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;

		OutputStream outStream = connection.getOutputStream();
		outStream.write(postParams.getBytes());
		outStream.flush();
		outStream.close();

		int responseCode = connection.getResponseCode();
		LOG.trace("responseCode = " + responseCode);

		InputStream is = connection.getInputStream();
		JsonReader jsonReader = Json.createReader(is);
		JsonObject jsonObject = jsonReader.readObject();
		jsonReader.close();

		LOG.trace("Response: " + jsonObject);

		boolean success = jsonObject.getBoolean("success");
		return success;
	}

}
