package ee.ut.math.tvt.salessystem.domain.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class StockItemTest {

	StockItem item1, item2, item3, item4, item5, item6, item7;

	@Before
	public void setUp() {
		item1 = new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 13.82d, 3);
		item2 = new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 13.82d, 3);
		item3 = new StockItem(3L, "stockitem_name_1", "stockitem_desc_1", 13.82d, 3);
		item4 = new StockItem(1L, "stockitem_name_4", "stockitem_desc_1", 13.82d, 3);
		item5 = new StockItem(1L, "stockitem_name_1", "stockitem_desc_5", 13.82d, 3);
		item6 = new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 20.82d, 3);
		item7 = new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 13.82d, 100);
	}

	@Test
	public void testClone() {
		StockItem item1Clone = (StockItem) item1.clone();
		Assert.assertEquals(item1Clone.getId().longValue(), 1L);
		Assert.assertEquals(item1Clone.getName(), "stockitem_name_1");
		Assert.assertEquals(item1Clone.getDescription(), "stockitem_desc_1");
		Assert.assertEquals(item1Clone.getPrice(), 13.82d, 0.0001d);
		Assert.assertEquals(item1Clone.getQuantity(), 3);
	}
	
	@Test
	public void testGetColumn() {
		Assert.assertEquals(item1.getColumn(0), 1L);
		Assert.assertEquals(item1.getColumn(1), "stockitem_name_1");
		Assert.assertEquals((Double)item1.getColumn(2), 13.82d, 0.0001d);
		Assert.assertEquals(item1.getColumn(3), 3);
	}
	
	
	@Test
	public void testEquals(){
		Assert.assertTrue(item1.equals(item1));
		Assert.assertTrue(item1.equals(item2));
		
		Assert.assertFalse(item1.equals(item3));
		Assert.assertFalse(item1.equals(item4));
		Assert.assertFalse(item1.equals(item5));
		Assert.assertFalse(item1.equals(item6));
		Assert.assertFalse(item1.equals(item7));
	}
}
