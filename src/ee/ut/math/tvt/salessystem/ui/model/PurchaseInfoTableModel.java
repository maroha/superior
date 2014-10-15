package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
        
        TableColumn<SoldItem, Integer> priceCol = new TableColumn<SoldItem, Integer>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<SoldItem,Integer>("price"));
        
        TableColumn<SoldItem, Integer> quantityCol = new TableColumn<SoldItem, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<SoldItem,Integer>("quantity"));
        
        columnList.add(idCol);
        columnList.add(nameCol);
        columnList.add(priceCol);
        columnList.add(quantityCol);
        
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
    public void addItem(final SoldItem item) {
        /**
         * XXX In case such stockItem already exists increase the quantity of the
         * existing stock.
         */
        
        add(item);
        log.debug("Added " + item.getName() + " quantity of " + item.getQuantity());
    }
    
}
