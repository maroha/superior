package ee.ut.math.tvt.salessystem.ui.tabs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab extends Tab {

	private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private final SalesDomainController domainController;

	private Button newPurchase;

	private Button submitPurchase;

	private Button cancelPurchase;

	private PurchaseItemPanel purchasePane;

	private SalesSystemModel model;

	public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
	}

	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Node draw() {
		GridPane panel = new GridPane();
		panel.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		// Add the purchase menu
		HBox purchaseMenuPane = getPurchaseMenuPane();
		purchaseMenuPane.setAlignment(Pos.TOP_CENTER);
		panel.add(purchaseMenuPane, 0, 0);

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model, getTabPane());
		panel.add(purchasePane, 0, 1);

		return panel;
	}

	// The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	private HBox getPurchaseMenuPane() {
		HBox pane = new HBox(2);

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel
		pane.getChildren().addAll(newPurchase, submitPurchase, cancelPurchase);

		return pane;
	}

	// Creates the button "New purchase"
	private Button createNewPurchaseButton() {
		Button b = new Button("New purchase");
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				newPurchaseButtonClicked();

			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private Button createConfirmButton() {
		Button b = new Button("Confirm");
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				submitPurchaseButtonClicked();

			}

		});
		b.setDisable(true);

		return b;
	}

	// Creates the "Cancel" button
	private Button createCancelButton() {
		Button b = new Button("Cancel");
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cancelPurchaseButtonClicked();
			}

		});
		b.setDisable(true);

		return b;
	}

	/*
	 * === Event handlers for the menu buttons (get executed when the buttons
	 * are clicked)
	 */

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>submit purchase</code> event. */
	protected void submitPurchaseButtonClicked() {
		log.info("Sale complete");
		try {
			log.debug("Contents of the current basket:\n"
					+ model.getCurrentPurchaseTableModel());
			domainController.submitCurrentPurchase(model
					.getCurrentPurchaseTableModel());
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/*
	 * === Helper methods that bring the whole purchase-tab to a certain state
	 * when called.
	 */

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();

		purchasePane.setEnabled(true);
		submitPurchase.setDisable(false);
		cancelPurchase.setDisable(false);
		newPurchase.setDisable(true);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();

		cancelPurchase.setDisable(true);
		submitPurchase.setDisable(true);
		newPurchase.setDisable(false);
		purchasePane.setEnabled(false);
	}

}
