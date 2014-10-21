package ee.ut.math.tvt.salessystem.ui.tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import org.apache.log4j.Logger;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.AcceptedOrder;
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
	
	private Label sumValue;

	private SalesSystemModel model;
	
	private Dialog purchaseConfirmDialog;
	
	private TextField payAmount; //confirmation dialog widget

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
		
		createPurchaseConfirmationDialog();
		
		return panel;
	}
	
	public void createPurchaseConfirmationDialog(){
		VBox confirmPane = new VBox();

		sumValue = new Label(
				String.valueOf(model.getCurrentPurchaseTableModel().getSum()));
		Label changeValue = new Label("0");
		payAmount = new TextField("0");
		payAmount.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				double paymentAmount = 0;
				if(!newValue.isEmpty()){ //check new value
					if(newValue.endsWith("f") || newValue.endsWith("d") || newValue.startsWith("-") ){
						payAmount.setText(oldValue);
						return;
					}
					newValue = newValue.replaceAll(",", ".");	
					try{
						paymentAmount = Double.parseDouble(newValue);
					}catch(Exception e){
						payAmount.setText(oldValue);
						return;
					}
				}
				changeValue.setText(String.valueOf(
						paymentAmount - Double.valueOf(sumValue.getText())));
			}
		});
		
		confirmPane.getChildren().addAll(
							new HBox(new Label("Order sum: "), sumValue),
							new HBox(new Label("Change: "), changeValue),
							new HBox(new Label("Payment amount: "), payAmount));
					
		purchaseConfirmDialog = new Dialog(getTabPane(), "Payment Confirmation");
		
		purchaseConfirmDialog.getActions().setAll(Dialog.ACTION_OK, Dialog.ACTION_CANCEL);
		purchaseConfirmDialog.setContent(confirmPane);
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
		sumValue.setText(String.valueOf(model.getCurrentPurchaseTableModel().getSum()));
		Action response = purchaseConfirmDialog.show();
		
		if(response == Dialog.ACTION_OK){
			log.info("Sale complete");
			try {
				log.debug("Contents of the current basket:\n"
						+ model.getCurrentPurchaseTableModel());
				domainController.submitCurrentPurchase(model
						.getCurrentPurchaseTableModel());
				endSale();
				
				AcceptedOrder newAccpetedOrder = 
						new AcceptedOrder(model.getCurrentPurchaseTableModel().getItems());
				
				model.getPurchaseHistoryTableModel().add(newAccpetedOrder);
				
				model.getCurrentPurchaseTableModel().clear();
			} catch (VerificationFailedException e1) {
				log.error(e1.getMessage());
			}
		}else{
			log.info("Payment canceled!");
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
		
		payAmount.setText("0");
	}

}
