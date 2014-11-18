package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
import ee.ut.math.tvt.salessystem.hibernate.HibernateDataService;

/**
 * Stock item table model.
 */
public class PurchaseHistoryTableModel extends SalesSystemTableModel<AcceptedOrder> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PurchaseHistoryTableModel.class);

	public PurchaseHistoryTableModel() {
		super(defineTableColumns());
	}
	
    public static List<TableColumn<AcceptedOrder, ?>> defineTableColumns(){
    	List<TableColumn<AcceptedOrder, ?>> columnList = new ArrayList<TableColumn<AcceptedOrder, ?>>();
    	
        TableColumn<AcceptedOrder, String> dateCol = new TableColumn<AcceptedOrder, String>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<AcceptedOrder,String>("date"));
        
        
        TableColumn<AcceptedOrder, String> timeCol = new TableColumn<AcceptedOrder, String>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<AcceptedOrder,String>("time"));
        
        TableColumn<AcceptedOrder, Integer> sumCol = new TableColumn<AcceptedOrder, Integer>("Sum");
        sumCol.setCellValueFactory(new PropertyValueFactory<AcceptedOrder,Integer>("sum"));
        
        
        columnList.add(dateCol);
        columnList.add(timeCol);
        columnList.add(sumCol);
        
        return columnList;
    }
    
    public void addItem(AcceptedOrder item){
    	if(HibernateDataService.insertAcceptedOrder(item))
    		add(item);
    }

	@Override
	protected Object getColumnValue(AcceptedOrder item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

}
