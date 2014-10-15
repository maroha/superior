package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import com.sun.javafx.collections.ObservableListWrapper;

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

		panel.add(drawStockMenuPane(), 0, 0);

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

		addItem = new Button("Add");

		panel.add(addItem, 0, 0);

		return panel;
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
