package ee.ut.math.tvt.salessystem.domain.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SoldItemTest {

	StockItem stockItem1; 
	
	@Before
	public void setUp() {
		stockItem1 = new StockItem(1L, "stock_item_name_1", "stock_item_desc_2", 10.32d, 5);
	}

	@Test
	public void testGetSum() {
		SoldItem soldItem1 = new SoldItem(stockItem1, 5);
		Assert.assertEquals(soldItem1.getSum(), 5*10.32d, 0.0001d);
	}
	
	@Test
	public void testGetSumWithZeroQuantity() {
		SoldItem soldItem1 = new SoldItem(stockItem1, 0);
		Assert.assertEquals(soldItem1.getSum(), 0, 0.0001d);
	}

}
