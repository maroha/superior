package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;

import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		for(SoldItem soldItem: goods){
			StockItem stockItem = soldItem.getStockItem();
			stockItem.setQuantity(stockItem.getQuantity() - soldItem.getQuantity());
			HibernateDataService.updateStockItemQuantity(stockItem);
		}
		// Let's assume we have checked and found out that the buyer is underaged and
		// cannot buy chupa-chups
		//throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		List<StockItem> dataset = HibernateDataService.getStockItems();

		return dataset;
	}
	
	public List<AcceptedOrder> loadHistoryState(){
		return HibernateDataService.getAcceptedOrders();
	}
	
	@Override
	public void endSession(){
		HibernateUtil.closeSession();
	}
	
}
