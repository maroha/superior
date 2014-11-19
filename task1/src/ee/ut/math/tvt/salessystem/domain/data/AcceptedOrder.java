package ee.ut.math.tvt.salessystem.domain.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@TypeDefs(
	{
	 @TypeDef(name = "LocalDateTime", 
			  typeClass = ee.ut.math.tvt.salessystem.hibernate.usertypes.LocalDateTimeUserType.class)
	}
)

/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "ACCEPTEDORDER")
public class AcceptedOrder implements Cloneable, DisplayableItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    @Type(type="LocalDateTime")
	@Column(name = "SALE_DATE")
	private LocalDateTime dateTime;
	
    @OneToMany(targetEntity = SoldItem.class, mappedBy = "acceptedOrder", cascade = CascadeType.ALL)
    private List<SoldItem> items;
	
	@Column(name = "TOTALPRICE")
	private double sum;
    
    public AcceptedOrder(List<SoldItem> items){
    	if(items != null){
    		this.items = items;    
    		for(SoldItem item: items){
    			item.setAcceptedOrder(this);
    			sum += item.getSum();
    		}
    	}
    	dateTime = LocalDateTime.now();
    }
    
    public AcceptedOrder(){
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
    
    public LocalDateTime getLocalDateTime(){
    	return dateTime;
    }

    public Object clone() {
    	AcceptedOrder order =
            new AcceptedOrder(getItems());
        return order;
    }
    
    public Set<Long> getItemsIds(){
		Set<Long> theSet = new HashSet<Long>();
		for(SoldItem soldItem: getItems()){
			theSet.add(soldItem.getId());
		}
		return theSet;
    }
    
    public String toString(){
    	return "AcceptedOrder [dateTime=" + dateTime + ", sum=" + sum + "]";
    }
		
}
