import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class RandomTest {

	StockItem item1;

	@Before
	public void setUp() {
		item1 = new StockItem(1L, "nimi", "desc", 5d);
	}

	@Test
	public void testSomething() {
		Assert.assertEquals(item1.getPrice(), 5d, 0.001);
	}
}
