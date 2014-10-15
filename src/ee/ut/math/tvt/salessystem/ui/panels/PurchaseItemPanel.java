package ee.ut.math.tvt.salessystem.ui.panels;

import java.util.NoSuchElementException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends GridPane {

    private static final long serialVersionUID = 1L;

    // Text field on the dialogPane
    private TextField barCodeField;
	private TextField quantityField;
    private TextField nameField;
    private TextField priceField;

    private Button addItemButton;

    // Warehouse model
    private SalesSystemModel model;
    
    private TabPane root;

    /**
     * Constructs new purchase item panel.
     * 
     * @param model
     *            composite model of the warehouse and the shopping cart.
     */
    public PurchaseItemPanel(SalesSystemModel model, TabPane root) {
        this.model = model;
        this.root = root;

        add(drawDialogPane(), 0, 0);
        add(drawBasketPane(), 0, 1);

        setEnabled(false);
    }

    // shopping cart pane
    private Node drawBasketPane() {
    	
        // Create the basketPane
        GridPane basketPane = new GridPane();
        basketPane.setPadding(Insets.EMPTY);
		TitledPane titledPanel = new TitledPane("Shopping cart", basketPane);
		titledPanel.setPadding(new Insets(2,0,0,0));
		titledPanel.setCollapsible(false);

        // Create the table view
        // and add the scrollPane to the basketPanel.
		
        TableView<SoldItem> table = new TableView<SoldItem>(model.getCurrentPurchaseTableModel());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefSize(root.widthProperty().get(),  root.heightProperty().get());
        
        PurchaseInfoTableModel tableModel = ((PurchaseInfoTableModel)table.getItems());
        table.getColumns().addAll(tableModel.getTableColumns());
        
		table.prefWidthProperty().bind(root.widthProperty());
		table.prefHeightProperty().bind(root.heightProperty());

        basketPane.add(table, 0, 0);

        return titledPanel;
    }
    

    // purchase dialog
    private Node drawDialogPane() {

        // Create the panel
    	GridPane panel = new GridPane();
    	
    	panel.setPadding(new Insets(4));
    	ColumnConstraints column2 = new ColumnConstraints();
    	column2.setPrefWidth(75);
    	panel.getColumnConstraints().addAll(new ColumnConstraints(), column2);
		
		TitledPane titledPanel = new TitledPane("Product", panel);
		
		titledPanel.setPadding(new Insets(4,0,0,0));
		GridPane.setFillWidth(titledPanel, false);
		titledPanel.setCollapsible(false);
		
        // Initialize the textfields
        barCodeField = new TextField();
		quantityField = new TextField("1");
        nameField = new TextField();
        priceField = new TextField();
        
        // Fill the dialog fields if the bar code text field loses focus
        barCodeField.focusedProperty().addListener(new ChangeListener<Boolean>(){
        	@Override
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean oldValue, Boolean newValue) {
        		if (newValue){
        			//focus
        		}else{
        			//outfocus
        			fillDialogFields();
        		}
        	}
        });

        nameField.setDisable(true);
        priceField.setDisable(true);

        // == Add components to the panel

        // - bar code
        Label barCodeLabel = new Label("Bar code:");
        panel.add(barCodeLabel, 0, 0);
        panel.add(barCodeField, 1, 0);

        // - amount
        panel.add(new Label("Amount:"), 0, 1);
        panel.add(quantityField, 1, 1);

        // - name
        panel.add(new Label("Name:"), 0, 2);
        panel.add(nameField, 1, 2);

        // - price
        panel.add(new Label("Price:"), 0, 3);
        panel.add(priceField, 1, 3);

        // Create and add the button
        addItemButton = new Button("Add to cart");
        addItemButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				 addItemEventHandler();
			}
		});

        panel.add(addItemButton, 0, 5);
        
        return titledPanel;
    }

    // Fill dialog with data from the "database".
    public void fillDialogFields() {
        StockItem stockItem = getStockItemByBarcode();

        if (stockItem != null) {
            nameField.setText(stockItem.getName());
            String priceString = String.valueOf(stockItem.getPrice());
            priceField.setText(priceString);
        } else {
            reset();
        }
    }

    // Search the warehouse for a StockItem with the bar code entered
    // to the barCode textfield.
    private StockItem getStockItemByBarcode() {
        try {
            int code = Integer.parseInt(barCodeField.getText());
            return model.getWarehouseTableModel().getItemById(code);
        } catch (NumberFormatException ex) {
            return null;
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    /**
     * Add new item to the cart.
     */
    public void addItemEventHandler() {
        // add chosen item to the shopping cart.
        StockItem stockItem = getStockItemByBarcode();
        if (stockItem != null) {
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                quantity = 1;
            }
            model.getCurrentPurchaseTableModel()
                .addItem(new SoldItem(stockItem, quantity));
        }
    }

    /**
     * Sets whether or not this component is enabled.
     */
    public void setEnabled(boolean enabled) {
        this.addItemButton.setDisable(!enabled);
        this.barCodeField.setDisable(!enabled);
        this.quantityField.setDisable(!enabled);
    }


    /**
     * Reset dialog fields.
     */
    public void reset() {
        barCodeField.setText("");
        quantityField.setText("1");
        nameField.setText("");
        priceField.setText("");
    }

}
