package PizzaOrderSystem;
import javax.persistence.*;

@Entity
public class DiscountedOrder extends Order{
	public DiscountedOrder(String size, String[] toppings, String payment) {
		super(size, toppings, payment);
	}
	public DiscountedOrder(){
		
	}
	@Override
	public double calculatePrice(String size){
		double total = 0;
		switch(size){
			case "small":
				total += 3;
				break;
			case "medium":
				total += 5;
				break;
			default:
				total += 7;
		}
		for(Topping s : allToppings){
			total += s.getPrice();
		}
		return total*.9;
	}
	

}
