package ee.ut.math.tvt.salessystem.domain.data;

import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


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
    private long id;

	@Column(name = "NAME", nullable=false)
    private String name;

	@Column(name = "PRICE", nullable=false)
    private double price;

    @Column(name ="DESCRIPTION")
    private String description;
    
    @Type(type="SimpleIntegerProperty")
    @Column(name = "QUANTITY", nullable=false)
    private SimpleIntegerProperty quantity;
    
    public StockItem(Long id, String name, String desc, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
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
    }
    
    public SimpleIntegerProperty quantityProperty() {
		return quantity;
	}

    public void setQuantity(int quantity) {
    	this.quantity.set(quantity);
    }

    public String toString() {
    	return id + " " + name + " " + description + " " + price;
    }

    /**
     * Method for querying the value of a certain column when StockItems are shown
     * as table rows in the SalesSstemTableModel. The order of the columns is:
     * id, name, price, quantity.
     */
    @SuppressWarnings("ucd")
    public Object getColumn(int columnIndex) {
        switch(columnIndex) {
            case 0: return id;
            case 1: return name;
            case 2: return new Double(price);
            case 3: return new Integer(quantity.get());
            default: throw new RuntimeException("invalid column!");
        }
    }
    
    @Override
    public Object clone() {
        StockItem item =
            new StockItem(getId(), getName(), getDescription(), getPrice(), getQuantity());
        return item;
    }
    
    
		
}
