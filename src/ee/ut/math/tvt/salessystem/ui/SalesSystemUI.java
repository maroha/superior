package ee.ut.math.tvt.salessystem.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;

import static java.lang.System.*;

/**
 * Graphical user interface of the sales system.
 */
public class SalesSystemUI extends Stage {

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
		this.model = new SalesSystemModel(domainController);

		// Create singleton instances of the tab classes
		historyTab = new HistoryTab(model);
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
				domainController.endSession();
                exit(0);
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
