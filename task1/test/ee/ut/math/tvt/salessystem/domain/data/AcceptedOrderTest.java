package ee.ut.math.tvt.salessystem.domain.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class AcceptedOrderTest {
	
	@Before
	public void setUp() {
	}

	@Test
	public void testAddSoldItem() {
		StockItem stockItem1 = new StockItem(1L, "stock_item_name_1", "stock_item_desc_2", 10, 5);
		SoldItem soldItem1 = new SoldItem(stockItem1, 1);
		List<SoldItem> soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		AcceptedOrder item1 = new AcceptedOrder(soldItems);
		Assert.assertTrue(item1.getItems().contains(soldItem1));
	}
	
	@Test
	public void testGetSumWithNoItems() {
		AcceptedOrder item1 = new AcceptedOrder();
		Assert.assertEquals(item1.getSum(), 0, 0.0001d);
	}
	
	@Test
	public void testGetSumWithOneItem() {
		StockItem stockItem1 = new StockItem(1L, "stock_item_name_1", "stock_item_desc_2", 10, 5);
		SoldItem soldItem1 = new SoldItem(stockItem1, 1);
		List<SoldItem> soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		AcceptedOrder item1 = new AcceptedOrder(soldItems);
		Assert.assertEquals(item1.getSum(), soldItem1.getSum(), 0.0001d);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {
		StockItem stockItem1 = new StockItem(1L, "stock_item_name_1", "stock_item_desc_1", 14.37, 5);
		StockItem stockItem2 = new StockItem(2L, "stock_item_name_2", "stock_item_desc_2", 18.11, 5);
		SoldItem soldItem1 = new SoldItem(stockItem1, 1);
		SoldItem soldItem2 = new SoldItem(stockItem2, 1);
		List<SoldItem> soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		soldItems.add(soldItem2);
		AcceptedOrder item1 = new AcceptedOrder(soldItems);
		Assert.assertEquals(item1.getSum(), soldItem1.getSum()+soldItem2.getSum(), 0.0001d);
	}
	

}
