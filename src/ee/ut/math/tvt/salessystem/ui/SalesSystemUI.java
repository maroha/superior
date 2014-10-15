package ee.ut.math.tvt.salessystem.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.lihtne.util.Util;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;

/**
 * Graphical user interface of the sales system.
 */
public class SalesSystemUI extends Stage {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(SalesSystemUI.class);

	private final SalesDomainController domainController;

	// Warehouse model
	private SalesSystemModel model;

	// Instances of tab classes
	private PurchaseTab purchaseTab;
	private HistoryTab historyTab;
	private StockTab stockTab;

	/**
	 * Constructs sales system GUI.
	 * 
	 * @param domainController
	 *            Sales domain controller.
	 */
	public SalesSystemUI(SalesDomainController domainController) {
		this.domainController = domainController;
		this.model = new SalesSystemModel(domainController);

		// Create singleton instances of the tab classes
		historyTab = new HistoryTab();
		stockTab = new StockTab(model);
		purchaseTab = new PurchaseTab(domainController, model);

		setTitle("Sales system");

		// size & location
		int width = 600;
		int height = 400;
		setWidth(width);
		setHeight(height);

		drawWidgets();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setX((screen.width - width) / 2);
		setY((screen.height - height) / 2);

		setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			   }
		});

	}

	private void drawWidgets() 	{
		TabPane root = new TabPane();
		root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //disable tab closing

		Scene scene = new Scene(root, 0, 0); //width, height are set from content
		
		setScene(scene);

		purchaseTab.setText("Point-of-sale");
		stockTab.setText("Warehouse");
		historyTab.setText("History");

		root.getTabs().addAll(purchaseTab, stockTab, historyTab);

		purchaseTab.setContent(purchaseTab.draw());
		stockTab.setContent(stockTab.draw());
		historyTab.setContent(historyTab.draw());
	}

}
