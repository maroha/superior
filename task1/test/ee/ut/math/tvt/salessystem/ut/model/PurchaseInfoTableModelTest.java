package ee.ut.math.tvt.salessystem.ut.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {

	PurchaseInfoTableModel purchaseModel;
	SoldItem soldItem3, soldItem2;
	StockItem stockItem2;

	@Before
	public void setUp() {
		purchaseModel = new PurchaseInfoTableModel();
		List<SoldItem> soldItems = new ArrayList<SoldItem>();
		soldItems.add(new SoldItem(new StockItem(1L, "stockitem_name_1", "stockitem_desc_1", 10.32d, 5), 2));
		stockItem2 = new StockItem(2L, "stockitem_name_2", "stockitem_desc_2", 12.32d, 10);
		soldItem2 = new SoldItem(stockItem2, 5);
		soldItems.add(soldItem2);
		soldItem3 = new SoldItem(new StockItem(3L, "stockitem_name_3", "stockitem_desc_3", 13.32d, 3), 1);
		soldItems.add(soldItem3);
		soldItems.add(new SoldItem(new StockItem(4L, "stockitem_name_4", "stockitem_desc_4", 14.32d, 80), 15));
		soldItems.add(new SoldItem(new StockItem(5L, "stockitem_name_5", "stockitem_desc_5", 15.32d, 9), 3));
		
		purchaseModel.populateWithData(soldItems);
	}
	
	@Test
	public void testGetSoldWithStockItem(){
		Assert.assertEquals(purchaseModel.getSoldWithStockItem(stockItem2.getId()), soldItem2);
	}

	@Test
	public void testAddItem() {
		SoldItem soldItem = new SoldItem(new StockItem(6L, "stockitem_name_6", "stockitem_desc_6", 100.32d, 99), 11);
		purchaseModel.addItem(soldItem);
		Assert.assertTrue(purchaseModel.contains(soldItem));
		Assert.assertEquals(soldItem.getQuantity(), (Integer)11);
		purchaseModel.addItem(soldItem);
		Assert.assertEquals(soldItem.getQuantity(), (Integer)22);

	}
	
	@Test
	public void testGetSum(){
		Assert.assertEquals(purchaseModel.getSum(), 356.32d, 0.00001d);
	}
	
	@Test
	public void testGetQuantityInPurchase(){
		Assert.assertEquals(purchaseModel.getQuantityInPurchase(soldItem3.getStockItem()), 1);
	}

	
}
