package techshop.web.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import techshop.web.utils.UserInputValidator;

public class UserInputValidatorTest {

	@Test
	public void testIsValidParameters() {
		assertFalse(UserInputValidator.isValidParameters(new String[]{null}));
		assertFalse(UserInputValidator.isValidParameters(""));
		assertTrue(UserInputValidator.isValidParameters("param"));
	}

	@Test
	public void testIsValidEmailAddress() {
		assertFalse(UserInputValidator.isValidEmailAddress("invalidparam"));
		assertTrue(UserInputValidator.isValidEmailAddress("testmail@test.ua"));
	}

	@Test
	public void testIsValidDate() {
		assertFalse(UserInputValidator.isValidDate("0"));
		assertFalse(UserInputValidator.isValidDate(null));
		assertTrue(UserInputValidator.isValidDate("2017-01-14"));
	}

}
