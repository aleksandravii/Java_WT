package techshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import techshop.domain.GoodsManager;
import techshop.domain.entities.Goods;

public class GoodsManagerTest {

	private List<Goods> listGoods;
	
	private List<Goods> expectedGoods;
	
	@Before
	public void setup() {
		listGoods = new ArrayList<>();
		Goods goods = null;
		for(int i=1;i<=100;i++) {
			goods = new Goods();
			goods.setPrice(i);
		}
		
		expectedGoods = new ArrayList<>();
		for(int i=25;i<=50;i++) {
			goods = new Goods();
			goods.setPrice(i*100);
		}
	}
	
	@Test
	public void testFindGoodsByPriceRange() {
		List<Goods> actual = GoodsManager.findGoodsByPriceRange(listGoods, 25, 50);
		assertThat(actual, is(expectedGoods));
	}

}
