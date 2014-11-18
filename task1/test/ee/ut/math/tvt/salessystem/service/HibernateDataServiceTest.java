package ee.ut.math.tvt.salessystem.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.exception.JDBCConnectionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.hibernate.HibernateDataService;
import ee.ut.math.tvt.salessystem.hibernate.HibernateUtil;

//During testing, data for Hibernate DB should be retrieved from test.data folder
//Use ant target test.startdb instead of target startdb
public class HibernateDataServiceTest {
	
	
	/**
	 * @return null if query is successful, JDBCConnectionException if unable connect to db.
	 */
	public static Throwable validationQuery(){
		try {
			HibernateUtil.currentSession().createSQLQuery("call 1").uniqueResult();
		} catch (JDBCConnectionException e) {
			 return e;
		}
		return null;
		
	}
	
	@BeforeClass
	public static void staticSetUp(){
		Assert.assertNull("Database has not been started!", validationQuery());
	}
	
	@Before
	public void setUp(){

	}
	
	@Test
	public void testGetStockItems(){
		List<StockItem> items = HibernateDataService.getStockItems();	
		Assert.assertEquals(items.get(0).getId(), (Long)1L);
		Assert.assertEquals(items.get(19).getId(), (Long)20L);
	}
	
	@Test
	public void testGetStockItem(){
		StockItem item1 = HibernateDataService.getStockItem(1);
		Assert.assertEquals(item1.getId(), (Long)1L);
		Assert.assertEquals(item1.getName(), "Lays Chips");
	}
	
	@Test
	public void testGetSoldItems(){
		List<SoldItem> items = HibernateDataService.getSoldItems();
		Assert.assertEquals(items.get(0).getId(), (Long)1L);
		Assert.assertEquals(items.get(18).getId(), (Long)19L);
	}
	
	@Test
	public void testGetAcceptedOrders(){
		List<AcceptedOrder> items = HibernateDataService.getAcceptedOrders();
		Assert.assertEquals(items.get(0).getId(), (Long)1L);
		Assert.assertEquals(items.get(4).getId(), (Long)5L);
	}
	
	@Test
	public void testGetIdentity(){
		Assert.assertNotSame(HibernateDataService.getIdentity("STOCKITEM"), -1L);
	}
	
	//dependency - testGetIdentity(), testGetStockItem()
	@Test
	public void testInsertStockItem(){
		Integer id = HibernateDataService.getIdentity("STOCKITEM")+1;
		StockItem newStockItem = new StockItem(0L, "stockitem_name_"+id, "stockitem_desc_"+id, id, id*2);
		
		Assert.assertTrue("Adding StockItem to DB", HibernateDataService.insertStockItem(newStockItem));
		
		List<StockItem> result = HibernateDataService.getStockItems();
		StockItem fromDb = result.get(result.size()-1);
		Assert.assertEquals(fromDb.getId(), newStockItem.getId());
		Assert.assertEquals(fromDb.getName(), newStockItem.getName());
		Assert.assertEquals(fromDb.getDescription(), newStockItem.getDescription());
		Assert.assertEquals(fromDb.getPrice(), newStockItem.getPrice());
		Assert.assertEquals(fromDb.getQuantity(), newStockItem.getQuantity());
	}
	
	//dependency - testGetStockItem()
	@Test
	public void testUpdateStockItemQuantity(){
		List<StockItem> oldItems = HibernateDataService.getStockItems();
		oldItems.get(0).setQuantity(oldItems.get(0).getQuantity()+1);
		HibernateDataService.updateStockItemQuantity(oldItems.get(0));
		List<StockItem> newItems = HibernateDataService.getStockItems();	
		Assert.assertEquals(newItems.get(0).getQuantity(), oldItems.get(0).getQuantity());
	}
	
	//dependency - testGetIdentity(), testGetStockItem(), getAcceptedOrders()
	@Test
	public void testInsertAcceptedOrder(){
		//create
		List<StockItem> stockItems = HibernateDataService.getStockItems();
		SoldItem soldItem1 = new SoldItem(stockItems.get(0), 5);
		SoldItem soldItem2 = new SoldItem(stockItems.get(1), 10);
		List<SoldItem> soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		soldItems.add(soldItem2);
		AcceptedOrder order = new AcceptedOrder(soldItems);
		
		//add
		Assert.assertTrue(HibernateDataService.insertAcceptedOrder(order));
		List<AcceptedOrder> resultItems = HibernateDataService.getAcceptedOrders();
		
		//check order vars
		AcceptedOrder resultOrder = resultItems.get(resultItems.size()-1);
		Assert.assertEquals(resultOrder.getId(), order.getId());
		Assert.assertEquals(resultOrder.getSum(), order.getSum());
		Assert.assertEquals(resultOrder.getDate(), order.getDate());
		
		//check solditem vars
		SoldItem dbSoldItem1 = resultOrder.getItems().get(0);
		SoldItem dbSoldItem2 = resultOrder.getItems().get(1);
		
		Assert.assertEquals(dbSoldItem1.getStockItem().getId(), soldItem1.getStockItem().getId());
		Assert.assertEquals(dbSoldItem1.getQuantity(), soldItem1.getQuantity());	
		Assert.assertEquals(dbSoldItem1.getSum(), soldItem1.getSum());	
		
		Assert.assertEquals(dbSoldItem2.getStockItem().getId(), soldItem2.getStockItem().getId());
		Assert.assertEquals(dbSoldItem2.getQuantity(), soldItem2.getQuantity());	
		Assert.assertEquals(dbSoldItem2.getSum(), soldItem2.getSum());	
	}

}
