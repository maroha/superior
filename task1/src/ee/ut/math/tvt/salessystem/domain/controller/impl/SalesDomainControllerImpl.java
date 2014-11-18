package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.hibernate.HibernateDataService;
import ee.ut.math.tvt.salessystem.hibernate.HibernateUtil;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	public void submitCurrentPurchase(SalesSystemModel model) throws VerificationFailedException {
		//update warehouse quantities
		for(SoldItem soldItem: model.getCurrentPurchaseTableModel()){
			StockItem stockItem = soldItem.getStockItem();
			stockItem.setQuantity(stockItem.getQuantity() - soldItem.getQuantity());
			HibernateDataService.updateStockItemQuantity(stockItem);
		}
		
		//create history item
		AcceptedOrder newAccpetedOrder = 
				new AcceptedOrder(model.getCurrentPurchaseTableModel().getItems());
		
		//add history item
		model.getPurchaseHistoryTableModel().addItem(newAccpetedOrder);
		
		//clear basket
		model.getCurrentPurchaseTableModel().clear();
	}

	public void cancelCurrentPurchase(SalesSystemModel model) throws VerificationFailedException {	
		//removes items from the basket
		model.getCurrentPurchaseTableModel().clear();
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
