package ee.ut.math.tvt.salessystem.ui.tabs;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab extends Tab{
    

	private SalesSystemModel model;
	
	TableView<SoldItem> acceptedOrderItemsTable;
	
    public HistoryTab(SalesSystemModel model) {
    	this.model = model;
    } 
    
    public Node draw() {
		GridPane panel = new GridPane();
		panel.setPadding(Insets.EMPTY);

		TitledPane titledPanel = new TitledPane("Order history", panel);
		titledPanel.setPadding(new Insets(2, 0, 0, 0));
		titledPanel.setCollapsible(false);

		TableView<AcceptedOrder> table = new TableView<AcceptedOrder>(
				model.getPurchaseHistoryTableModel());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		SalesSystemTableModel<AcceptedOrder> tableModel = ((SalesSystemTableModel<AcceptedOrder>) table
				.getItems());
		table.getColumns().addAll(tableModel.getTableColumns());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<AcceptedOrder>(){

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends AcceptedOrder> c) {
				if(table.getSelectionModel().getSelectedItem() != null)
					acceptedOrderItemsTable.getItems().setAll(table.getSelectionModel().getSelectedItem().getItems());
			}
		});
		
		TabPane root = getTabPane();

		table.prefWidthProperty().bind(root.widthProperty());
		table.prefHeightProperty().bind(root.heightProperty());

		panel.add(table, 0, 0);
		
		panel.add(drawItemsPane(), 0, 1);

		return titledPanel;
    }
    
    public TitledPane drawItemsPane(){	
        acceptedOrderItemsTable = new TableView<SoldItem>(new PurchaseInfoTableModel());
        acceptedOrderItemsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        acceptedOrderItemsTable.setPrefSize(getTabPane().widthProperty().get(),  getTabPane().heightProperty().get());
        
        PurchaseInfoTableModel tableModel = ((PurchaseInfoTableModel)acceptedOrderItemsTable.getItems());
        acceptedOrderItemsTable.getColumns().addAll(tableModel.getTableColumns());
        
        acceptedOrderItemsTable.prefWidthProperty().bind(getTabPane().widthProperty());
        acceptedOrderItemsTable.prefHeightProperty().bind(getTabPane().heightProperty());
		
        // Create the basketPane
		TitledPane titledPanel = new TitledPane("Items", acceptedOrderItemsTable);
		titledPanel.setPadding(new Insets(2,0,0,0));
		titledPanel.setCollapsible(true);

        return titledPanel;
    }
    
    
}