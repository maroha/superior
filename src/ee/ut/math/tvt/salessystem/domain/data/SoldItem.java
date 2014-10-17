package ee.ut.math.tvt.salessystem.domain.data;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving
 * history.
 */
public class SoldItem implements Cloneable, DisplayableItem {

	private Long id;
	private StockItem stockItem;

	private String name;
    private SimpleIntegerProperty quantity;
	private double price;

	public SoldItem(StockItem stockItem, int quantity) {
		this.stockItem = stockItem;
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
        this.quantity = new SimpleIntegerProperty(quantity);
	}

	public Long getId() {
		return stockItem.getId();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity.get();
	}
	
    public SimpleIntegerProperty quantityProperty() {
		return quantity;
	} 

	public void setQuantity(Integer quantity) {
		this.quantity.set(quantity);
	}

	public double getSum() {
		return price * ((double) quantity.get());
	}

	public StockItem getStockItem() {
		return stockItem;
	}

	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}

}
