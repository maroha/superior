package ee.ut.math.tvt.salessystem.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * A simple CLI (limited functionality).
 * 
 */
public class ConsoleUI {
	private static final Logger log = Logger.getLogger(ConsoleUI.class);

	private final SalesDomainController dc;
	
	private SalesSystemModel model;

	public ConsoleUI(SalesDomainController domainController) {
		this.dc = domainController;
		
		model = new SalesSystemModel(domainController);
	}

	/**
	 * Run the sales system CLI.
	 */
	public void run() {
		try {

			System.out.println("===========================");
			System.out.println("=       Sales System      =");
			System.out.println("===========================");
			printUsage();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String command = "";

			while (true) {
				System.out.print("> ");
				command = in.readLine();
				processCommand(command.trim().toLowerCase());
				System.out.println("Done. ");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void showStock(List<StockItem> stock) {
		System.out.println("-------------------------");
		for (StockItem si : stock) {
			System.out.println(si.getId() + " "
					+ si.getName() + " "
					+ si.getPrice() + "Euro ("
					+ si.getQuantity() + " items)");
		}
		if (stock.size() == 0) {
			System.out.println("\tNothing");
		}
		System.out.println("-------------------------");
	}
	
	private void showCart(List<SoldItem> stock) {
		System.out.println("-------------------------");
		for (SoldItem si : stock) {
			System.out.println(si.getId() + " "
					+ si.getName() + " "
					+ si.getPrice() + "Euro ("
					+ si.getQuantity() + " items)");
		}
		if (stock.size() == 0) {
			System.out.println("\tNothing");
		}
		System.out.println("-------------------------");
	}

	private void printUsage() {
		System.out.println("-------------------------");
		System.out.println("Usage:");
		System.out.println("h\t\tShow this help");
		System.out.println("w\t\tShow warehouse contents");
		System.out.println("c\t\tShow cart contents");
		System.out
				.println("a IDX NR \tAdd NR of stock item with index IDX to the cart");
		System.out.println("p\t\tPurchase the shopping cart");
		System.out.println("r\t\tReset the shopping cart");
		System.out.println("-------------------------");
	}

	private StockItem getStockItemById(int id) {
		return model.getWarehouseTableModel().getItemById(id);
	}

	private void processCommand(String command) {
		String[] c = command.split(" ");

		if (c[0].equals("h"))
			printUsage();
		else if (c[0].equals("q")){
			dc.endSession();
			System.exit(0);
		}else if (c[0].equals("w"))
			showStock(model.getWarehouseTableModel());
		else if (c[0].equals("c"))
			showCart(model.getCurrentPurchaseTableModel());
		else if (c[0].equals("p"))
			try {
				dc.submitCurrentPurchase(model);
			} catch (VerificationFailedException e) {
				log.error(e.getMessage());
			}
		else if (c[0].equals("r")) 
			try {
				dc.cancelCurrentPurchase(model);
			} catch (VerificationFailedException e) {
				log.error(e.getMessage());
			}
		else if (c[0].equals("a") && c.length == 3) {
			int idx = Integer.parseInt(c[1]);
			int amount = Integer.parseInt(c[2]);
			StockItem stockItem = getStockItemById(idx);
			SoldItem soldItem = new SoldItem(stockItem, 0);
			amount += model.getCurrentPurchaseTableModel().getQuantityInPurchase(stockItem);
        	soldItem.setQuantity(Math.min(amount, stockItem.getQuantity()));
        	model.getCurrentPurchaseTableModel().addItem(soldItem);
		}
	}
	
}
