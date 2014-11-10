package ee.ut.math.tvt.salessystem.ui.tabs;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;

public class StockTab extends Tab {

	private Button addItem;

	private SalesSystemModel model;

	public StockTab(SalesSystemModel model) {
		this.model = model;
	}

	// warehouse stock tab - consists of a menu and a table
	public Node draw() {
		GridPane panel = new GridPane();
		panel.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));
		Node n = drawStockMenuPane();
		panel.add(n, 0, 0);
		GridPane.setFillWidth(n, false);
		
		panel.add(drawStockMainPane(), 0, 1);
		return panel;
	}

	// warehouse menu
	private Node drawStockMenuPane() {
		GridPane panel = new GridPane();
		panel.setPadding(new Insets(1));
		panel.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));
    	ColumnConstraints column2 = new ColumnConstraints();
    	column2.setPrefWidth(75);
    	panel.getColumnConstraints().addAll(new ColumnConstraints(), column2);
		
		
		TextField idField = new TextField("");
		TextField nameField = new TextField("");
		TextField descField = new TextField("");
		TextField priceField = new TextField("0");
		TextField quantityField = new TextField("1");
		
		panel.add(new Label("Id"), 0, 0);
		panel.add(new Label("Name"), 0, 1);
		panel.add(new Label("Desc"), 0, 2);
		panel.add(new Label("Price"), 0, 3);
		panel.add(new Label("Quantity"), 0,4);
		
		panel.add(idField, 1, 0);
		panel.add(nameField, 1, 1);
		panel.add(descField, 1, 2);
		panel.add(priceField, 1, 3);
		panel.add(quantityField, 1,4);
		
		addItem = new Button("Add");
		
		addItem.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Long id = null;
				String name = null;
				String desc = null;
				Double price = null;
				Integer quantity = null;
				try {
					id = Long.parseLong(idField.getText());
					idField.setStyle("-fx-text-fill: default;");
					name = nameField.getText();
					desc = descField.getText();
					price = Double.parseDouble(priceField.getText());
					priceField.setStyle("-fx-text-fill: default;");
					quantity = Integer.parseInt(quantityField.getText());
					quantityField.setStyle("-fx-text-fill: default;");
				} catch (Exception e) {
					if(id == null){
						idField.setStyle("-fx-text-fill: red;");
						return;
					}
					if(price == null){
						priceField.setStyle("-fx-text-fill: red;");
						return;
					}
					if(quantity == null){
						quantityField.setStyle("-fx-text-fill: red;");
						return;
					}

					return;
				}
				
				StockItem item = new StockItem(
						id, 
						name, 
						desc, 
						price, quantity);
				model.getWarehouseTableModel().addItem(item);
			}});

		panel.add(addItem, 0,40);

		TitledPane titledPane = new TitledPane("Add products", panel);
		titledPane.setCollapsible(false);
		return titledPane;
	}

	// table of the wareshouse stock
	private Node drawStockMainPane() {
		GridPane panel = new GridPane();
		panel.setPadding(Insets.EMPTY);

		TitledPane titledPanel = new TitledPane("Warehouse status", panel);
		titledPanel.setPadding(new Insets(2, 0, 0, 0));
		titledPanel.setCollapsible(false);

		TableView<StockItem> table = new TableView<StockItem>(
				model.getWarehouseTableModel());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		SalesSystemTableModel<StockItem> tableModel = ((SalesSystemTableModel<StockItem>) table
				.getItems());
		table.getColumns().addAll(tableModel.getTableColumns());

		// disable column dragging
		table.getColumns().addListener(
				new ListChangeListener<TableColumn<StockItem, ?>>() {

					public boolean suspended;

					@Override
					public void onChanged(
							Change<? extends TableColumn<StockItem, ?>> c) {
						c.next();
						if (c.wasReplaced() && !suspended) {
							this.suspended = true;
							table.getColumns().setAll(
									tableModel.getTableColumns());
							this.suspended = false;
						}
					}
				});
		
		TabPane root = getTabPane();

		table.prefWidthProperty().bind(root.widthProperty());
		table.prefHeightProperty().bind(root.heightProperty());

		panel.add(table, 0, 1);

		return titledPanel;
	}

}
