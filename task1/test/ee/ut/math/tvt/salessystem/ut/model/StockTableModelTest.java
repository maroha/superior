package ee.ut.math.tvt.salessystem.ut.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {

	StockTableModel stockModel;
	StockItem stockItem3;

	@Before
	public void setUp() {
		stockModel = new StockTableModel();
		List<StockItem> stockItems = new ArrayList<StockItem>();
		stockItems.add(new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 10.32d, 5));
		stockItems.add(new StockItem(2L, "stockitem_name_2", "stockitem_desc_2", 12.32d, 10));
		stockItem3 = new StockItem(3L, "stockitem_name_3", "stockitem_desc_3", 13.32d, 3);
		stockItems.add(stockItem3);
		stockItems.add(new StockItem(4L, "stockitem_name_4", "stockitem_desc_4", 14.32d, 80));
		stockItems.add(new StockItem(5L, "stockitem_name_5", "stockitem_desc_5", 15.32d, 9));
		
		stockModel.populateWithData(stockItems);
	}

	@Test
	public void testValidateNameUniqueness() {
		Assert.assertTrue(stockModel.validateNameUniqueness("stockitem_name_6"));
		Assert.assertFalse(stockModel.validateNameUniqueness("stockitem_name_3"));
	}
	
	@Test
	public void testHasEnoughInStock() {
		Assert.assertFalse(stockModel.hasEnoughInStock(stockItem3, 4));
		Assert.assertTrue(stockModel.hasEnoughInStock(stockItem3, 3));
		Assert.assertTrue(stockModel.hasEnoughInStock(stockItem3, 2));
	}
	
	@Test
	public void testGetItemByIdWhenItemExists() {
		Assert.assertEquals(stockModel.getItemById(3L), stockItem3);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		stockModel.getItemById(6L);
	}

	
}
