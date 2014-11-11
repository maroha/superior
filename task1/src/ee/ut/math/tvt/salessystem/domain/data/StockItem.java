package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javafx.beans.property.SimpleIntegerProperty;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;


@TypeDefs(
	{
	 @TypeDef(name = "SimpleIntegerProperty", 
			  typeClass = ee.ut.math.tvt.salessystem.hibernate.usertypes.SimpleIntegerPropertyUserType.class)
	}
)

/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "STOCKITEM")
public class StockItem implements Cloneable, DisplayableItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "NAME")
    private String name;

	@Column(name = "PRICE")
    private double price;

    @Column(name ="DESCRIPTION")
    private String description;
    
    @Type(type="SimpleIntegerProperty")
    @Column(name = "QUANTITY")

    private SimpleIntegerProperty quantity;
    
    //How this stock item has been sold
//    @OneToMany(mappedBy = "stockItem")
//	private List<SoldItem> items;

    /**
     * Constucts new <code>StockItem</code> with the specified values.
     * @param id barcode id
     * @param name name of the product
     * @param desc description of the product
     * @param price price of the product
     */
    public StockItem(Long id, String name, String desc, double price) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
    }
    
    public StockItem(Long id, String name, String desc, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
//        this.quantity = quantity;
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    /**
     * Constructs new  <code>StockItem</code>.
     */
    public StockItem() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public int getQuantity() {
        return quantity.get();
//    	return quantity;
    }
    
    public SimpleIntegerProperty quantityProperty() {
		return quantity;
	}

    public void setQuantity(int quantity) {
//        this.quantity = quantity;
    	this.quantity.set(quantity);
    }

    public String toString() {
    	if(ConsoleUI.isConsoleMode())
    		return id + " " + name + " " + description + " " + price;
    	else
    		return name;
    }
    
    public String extendedToString(){
		return id + " " + name + " " + description + " " + price + " " + quantity.get();
    }

    /**
     * Method for querying the value of a certain column when StockItems are shown
     * as table rows in the SalesSstemTableModel. The order of the columns is:
     * id, name, price, quantity.
     */
    public Object getColumn(int columnIndex) {
        switch(columnIndex) {
            case 0: return id;
            case 1: return name;
            case 2: return new Double(price);
            case 3: return new Integer(quantity.get());
//            case 3: return new Integer(quantity);
            default: throw new RuntimeException("invalid column!");
        }
    }
    
    
    public Object clone() {
        StockItem item =
            new StockItem(getId(), getName(), getDescription(), getPrice(), getQuantity());
        return item;
    }
		
}