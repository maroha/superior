package ee.ut.math.tvt.salessystem.service;

import java.math.BigInteger;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private static final Logger log = Logger.getLogger(HibernateUtil.class);
	
	private static Session session = HibernateUtil.currentSession();
	
	public static List<StockItem> getStockItems() {
		List<StockItem> result = session.createQuery("from StockItem").list();
		return result;
	}

	public static List<SoldItem> getSoldItems() {
		List<SoldItem> result = session.createQuery("from SoldItem").list();
		return result;
	}

	public static List<AcceptedOrder> getAcceptedOrders() {
		List<AcceptedOrder> result = session.createQuery("from AcceptedOrder").list();
		return result;
	}
	
	private static boolean doTransaction(Runnable action){
		log.info("Transaction begin:");
		Transaction transaction = session.beginTransaction();
    	try{
        	action.run(); //do the query
        	transaction.commit();
        	log.info("Transaction end. Success: " + true);
        	return true;
    	} catch(Exception e){ //something went wrong with the transaction
    		transaction.rollback();
    		log.info("Transaction end. Success: " + false);
    		log.error(e);
    	}
    	return false;
	}
	
	public static boolean insertStockItem(StockItem item){
		return doTransaction(new Runnable(){
			@Override
			public void run() {
				Query q = session.createSQLQuery("INSERT INTO StockItem VALUES(" + item.getId() + ",'" + item.getName() + "'," + item.getPrice() + "," + item.getQuantity() + ",'" + item.getDescription() +"');");
				q.executeUpdate();
			}
		});
	}
	
	public static boolean updateStockItemQuantity(StockItem item){
		return doTransaction(new Runnable(){
			@Override
			public void run() {
		    	Query q = session.createSQLQuery("UPDATE StockItem SET QUANTITY=" + item.getQuantity() + " WHERE ID=" + item.getId());
		    	q.executeUpdate();
			}
		});
	}
	
	public static boolean insertAcceptedOrder(AcceptedOrder item){
		return doTransaction(new Runnable(){
			@Override
			public void run() {
		    	Query q = session.createSQLQuery("INSERT INTO AcceptedOrder VALUES (NULL, '" + item.getDate() +" "+ item.getTime() + "'," + item.getSum() + ");");
		    	q.executeUpdate();
		    	Query id_val = session.createSQLQuery("CALL IDENTITY();");
		    	BigInteger acceptedOrderId = (BigInteger)id_val.uniqueResult();
		    	acceptedOrderId.add(new BigInteger("1"));
		    	item.setId(acceptedOrderId.longValue());
		    	for(SoldItem soldItem: item.getItems()){
		        	Query qs = session.createSQLQuery("INSERT INTO SoldItem VALUES (NULL," + soldItem.getStockItem().getId() + "," + acceptedOrderId + ",'" + soldItem.getName() +"',"+ soldItem.getQuantity() + "," +soldItem.getPrice() + ");");
		        	qs.executeUpdate();
		    	}
			}
		});
	}

	/**
	 * @param string table
	 * @return current identity value of the table
	 */
	public static Integer getIdentity(String table) {
		final SimpleIntegerProperty identity = new SimpleIntegerProperty(-1);
		doTransaction(new Runnable(){
			@Override
			public void run() {
		    	Query id = session.createSQLQuery("SELECT MAX(ID) FROM " + table);
		    	Integer result = (Integer)id.uniqueResult();
		    	identity.set(result);
			}
		});
		return identity.get();
		
	}
	

}