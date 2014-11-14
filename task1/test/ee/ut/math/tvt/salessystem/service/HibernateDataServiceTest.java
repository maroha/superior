package ee.ut.math.tvt.salessystem.service;

import java.net.ConnectException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hibernate.Session;

//During testing, data for Hibernate DB should be retrieved from test.data folder
//Use ant target test.startdb instead of target startdb
public class HibernateDataServiceTest {
	
	StockItem stockItem1, stockItem20;
	
	@Before
	public void setUp(){
		stockItem1 = new StockItem(1L,"Lays Chips","Desc is not important",5.00000000000000000000000000000000d,18);
		stockItem20 = new StockItem(20L,"Durex condoms","Desc is not important",3.00000000000000000000000000000000d,8898);
		Assert.assertNull("Database has not been started!", HibernateUtil.validationQuery());
	}
	
	@Test
	public void testGetStockItemsFirstAndLastItem(){
		List<StockItem> items = HibernateDataService.getStockItems();	
		Assert.assertEquals(items.get(0), stockItem1);
		Assert.assertEquals(items.get(19), stockItem20);
	}

}
