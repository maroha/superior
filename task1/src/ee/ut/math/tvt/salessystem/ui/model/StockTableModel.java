package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.hibernate.HibernateDataService;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel() {
		super(defineTableColumns());
	}
	
    private static List<TableColumn<StockItem, ?>> defineTableColumns(){
    	List<TableColumn<StockItem, ?>> columnList = new ArrayList<TableColumn<StockItem, ?>>();
    	
        TableColumn<StockItem, Integer> idCol = new TableColumn<StockItem, Integer>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<StockItem,Integer>("id"));
        
        
        TableColumn<StockItem, String> nameCol = new TableColumn<StockItem, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<StockItem,String>("name"));
        
        TableColumn<StockItem, Integer> priceCol = new TableColumn<StockItem, Integer>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<StockItem,Integer>("price"));
        
        TableColumn<StockItem, Integer> quantityCol = new TableColumn<StockItem, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<StockItem,Integer>("quantity"));
        
        columnList.add(idCol);
        columnList.add(nameCol);
        columnList.add(priceCol);
        columnList.add(quantityCol);
        
        return columnList;
    }


	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param newItem
	 */
	public void addItem(final StockItem newItem) {
		try {
			StockItem existingItem = getItemById(newItem.getId());
			int oldQuantity = existingItem.getQuantity();
			int newQuantity = existingItem.getQuantity() + newItem.getQuantity();
			if(newQuantity < 0){
				if(oldQuantity > 0){
					existingItem.setQuantity(0);
					log.debug("Found existing item " + newItem.getName()
							+ " set quantity to 0");
				}
			}else{
				existingItem.setQuantity(newQuantity);
				log.debug("Found existing item " + newItem.getName()
						+ " increased quantity by " + newItem.getQuantity());
			}
			HibernateDataService.updateStockItemQuantity(existingItem);
		}
		catch (NoSuchElementException e) {
			if(HibernateDataService.insertStockItem(newItem)){
				add(newItem);
				log.debug("Added " + newItem.getName()
						+ " quantity of " + newItem.getQuantity());
			}

		}
	}
	
	public boolean validateNameUniqueness(String name){
		for(StockItem item: this){
			if(item.getName().equals(name))
				return false;
		}
		return true;
	}
	
	public boolean hasEnoughInStock(StockItem item, int takeAmount){
	    for(StockItem i : this) {
	        if (i.getId().equals(item.getId())) {
	            return (i.getQuantity() >= takeAmount);
	        }
	    }
	    return false;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.size(); i++)
			buffer.append(headers.get(i).getText() + "\t");
		buffer.append("\n");
    	ListIterator<StockItem> itr = listIterator();
    	while(itr.hasNext()){
    		final StockItem stockItem = itr.next();
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
    	}

		return buffer.toString();
	}

}
