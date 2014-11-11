package ee.ut.math.tvt.salessystem.domain.controller;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SalesDomainControllerTest {
	
	SalesDomainController controller;
	
	@Before
	public void setUp() {
		controller = new SalesDomainControllerImpl();
	}
	
	@Test
	public void submitCurrentPurchaseTest(){
//		controller.submitCurrentPurchase(goods);
	}

}
