package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
	
	public PurchaseInfoTableModel() {
		super(defineTableColumns());
	}
	
    public static List<TableColumn<SoldItem, ?>> defineTableColumns(){
    	List<TableColumn<SoldItem, ?>> columnList = new ArrayList<TableColumn<SoldItem, ?>>();
    	
        TableColumn<SoldItem, Integer> idCol = new TableColumn<SoldItem, Integer>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<SoldItem,Integer>("id"));
        
        TableColumn<SoldItem, String> nameCol = new TableColumn<SoldItem, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<SoldItem,String>("name"));
        
        TableColumn<SoldItem, Double> priceCol = new TableColumn<SoldItem, Double>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<SoldItem,Double>("price"));
        
        TableColumn<SoldItem, Integer> quantityCol = new TableColumn<SoldItem, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<SoldItem,Integer>("quantity"));
        
        TableColumn<SoldItem, Double> sumCol = new TableColumn<SoldItem, Double>("Sum");
        sumCol.setCellValueFactory(new PropertyValueFactory<SoldItem,Double>("sum"));
        
        columnList.add(idCol);
        columnList.add(nameCol);
        columnList.add(priceCol);
        columnList.add(quantityCol);
        columnList.add(sumCol);
        
        return columnList;
    }

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
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

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.size(); i++)
			buffer.append(headers.get(i).getText() + "\t");
		buffer.append("\n");
		ListIterator<SoldItem> itr = listIterator();
		while(itr.hasNext()){
			final SoldItem item = itr.next();
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
    /**
     * Add new StockItem to table.
     */
    public void addItem(final SoldItem soldItem) {
		try {
			SoldItem item = getItemById(soldItem.getId());
			item.setQuantity(item.getQuantity() + soldItem.getQuantity());
			log.debug("Found existing item " + soldItem.getName()
					+ " increased quantity by " + soldItem.getQuantity());
		}
		catch (NoSuchElementException e) {
			add(soldItem);
			log.debug("Added " + soldItem.getName()
					+ " quantity of " + soldItem.getQuantity());
		}
    }
    
    public double getSum(){
    	double sum = 0;
		ListIterator<SoldItem> itr = listIterator();
		while(itr.hasNext()){
			final SoldItem item = itr.next();
			sum += item.getSum();
		}
		return sum;
    }
    
    public List<SoldItem> getItems(){
		return new ArrayList<SoldItem>(this);
    }
    
}
