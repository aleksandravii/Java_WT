package techshop;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import techshop.domain.GoodsManagerTest;
import techshop.web.utils.UserInputValidatorTest;

@RunWith(Suite.class)
@SuiteClasses({GoodsManagerTest.class, UserInputValidatorTest.class})
public class AllTests {

}
