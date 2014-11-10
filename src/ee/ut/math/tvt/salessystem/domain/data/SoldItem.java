package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.persistence.*;

@TypeDefs(
	{
	 @TypeDef(name = "SimpleIntegerProperty", 
			  typeClass = ee.ut.math.tvt.salessystem.hibernate.usertypes.SimpleIntegerPropertyUserType.class),
		 @TypeDef(name = "SimpleDoubleProperty", 
				  typeClass = ee.ut.math.tvt.salessystem.hibernate.usertypes.SimpleDoublePropertyUserType.class)
	}

)

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving
 * history.
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "STOCKITEM_ID", nullable = false)
    private StockItem stockItem;
    
	@Column(name = "NAME")
	private String name;
	
    @Type(type="SimpleIntegerProperty")
    @Column(name = "QUANTITY")
    private SimpleIntegerProperty quantity;
    
	@Column(name = "ITEMPRICE")

	private double price;
	
    @Type(type="SimpleDoubleProperty")
    @Column(name = "QUANTITY*ITEMPRICE")
    private SimpleDoubleProperty sum;
	
    @ManyToOne
    @JoinColumn(name = "ACCEPTEDORDER_ID", nullable = false)
    private AcceptedOrder acceptedOrder;

	public SoldItem(StockItem stockItem, int quantity) {
		this.stockItem = stockItem;
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
        this.quantity = new SimpleIntegerProperty(quantity);
        sum = new SimpleDoubleProperty(this.price*quantity);
	}
	
	public SoldItem(){}

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
		sum.set(price * quantity);
		this.quantity.set(quantity);
	}

	public double getSum() {
		return sum.get();
	}
	
	public SimpleDoubleProperty sumProperty(){
		return sum;
	}

	public StockItem getStockItem() {
		return stockItem;
	}

	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}
	
//	public AcceptedOrder getAcceptedOrder() {
//		return acceptedOrder;
//	}
//	
//	public void setAcceptedOrder(AcceptedOrder acceptedOrder) {
//		this.acceptedOrder = acceptedOrder;
//	}

}
