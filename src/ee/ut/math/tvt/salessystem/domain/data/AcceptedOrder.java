package ee.ut.math.tvt.salessystem.domain.data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
public class AcceptedOrder implements Cloneable, DisplayableItem {
	
    private Long id;
	
	private LocalDateTime dateTime;
	
	private List<SoldItem> items;
	
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
