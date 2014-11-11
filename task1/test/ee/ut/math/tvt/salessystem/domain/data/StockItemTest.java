package ee.ut.math.tvt.salessystem.domain.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class StockItemTest {

	StockItem item1;

	@Before
	public void setUp() {
		item1 = new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 13.82d, 3);
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
	
}
