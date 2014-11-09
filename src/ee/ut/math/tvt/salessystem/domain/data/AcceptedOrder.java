package ee.ut.math.tvt.salessystem.domain.data;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;


/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "ACCEPTEDORDER")
public class AcceptedOrder implements Cloneable, DisplayableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_date")
	private LocalDateTime dateTime;

    @ManyToMany
    @JoinTable(
            name = "ACCEPTEDORDER_TO_SOLDITEMS",
            joinColumns = @JoinColumn(name = "ACCEPTEDORDER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SOLDITEM_ID", referencedColumnName = "ID")
    )
	private List<SoldItem> items;

    @Column(name = "totalprice")
	private double sum;
    
    
    public AcceptedOrder(List<SoldItem> items) {
    	this.items = items;    
    	for(SoldItem item: items){
    		sum += item.getSum();
    	}
    	dateTime = LocalDateTime.now();
    }

    public List<SoldItem> getItems() {
        return items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDate(){
    	return dateTime.toLocalDate().toString();
    }
    
    public String getTime(){
    	return dateTime.toLocalTime().toString();
    }
    
    public double getSum(){
    	return sum;
    }

    public Object clone() {
    	AcceptedOrder order =
            new AcceptedOrder(getItems());
        return order;
    }
		
}
