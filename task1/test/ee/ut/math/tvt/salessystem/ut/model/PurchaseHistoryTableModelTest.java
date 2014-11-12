package ee.ut.math.tvt.salessystem.ut.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseHistoryTableModel;

public class PurchaseHistoryTableModelTest {
	
	PurchaseHistoryTableModel historyModel;
	AcceptedOrder order1;

	@Before
	public void setUp() {
		historyModel = new PurchaseHistoryTableModel();
		StockItem stockItem1 = new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 20d, 50);
		SoldItem soldItem1 = new SoldItem(stockItem1, 20);
		List<SoldItem> soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		order1 = new AcceptedOrder(soldItems);
		order1.setId(1L);
		historyModel.add(order1);
	}
	
	@Test
	public void testGetItemByIdWhenItemExists() {
		Assert.assertEquals(historyModel.getItemById(1L), order1);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		historyModel.getItemById(2L);
	}



	
}
