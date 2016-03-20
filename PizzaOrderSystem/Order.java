package PizzaOrderSystem;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="Orders")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@NamedQuery(name = "Order.findByName", query = "from Orders where orderID = :orderID")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID", unique = true, nullable = false)
	int orderID;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	Customer customer;
	
	@Column(name = "price")
	double price;
	
	@Column(name = "deliveryTime")
	String deliveryTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "size")
	PizzaSize size;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment")
	PaymentMethod payment;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name = "order_topping", 
			joinColumns = {@JoinColumn(name = "orderID")}, 
			inverseJoinColumns = {@JoinColumn(name = "toppingID")})
	Set<Topping> allToppings = new HashSet<Topping>();
	
	
	public Order(String size, String[] toppings, String payment){
		this.allToppings = setToppings(toppings);
		this.price = calculatePrice(size);
		this.payment = setPaymentMethod(payment);
		this.size = setPizzaSize(size);
		this.deliveryTime = setDeliveryTime();
	}
	public Order(){
		
	}
	private String setDeliveryTime(){
		String deliveryT;
		Calendar calendar = new GregorianCalendar();
		int hour = calendar.get(Calendar.HOUR);
		int minuteNum = calendar.get(Calendar.MINUTE);
		String minute;
		if(minuteNum < 10){
			minute = "0" + minuteNum;
		}else{
			minute = "" + minuteNum;
		}
		
		int ampmNum = calendar.get(Calendar.AM_PM);
		String ampm;
		if (ampmNum == 1 && hour != 11){
			ampm = "pm";
		}else if(ampmNum == 1 && hour == 11){
			ampm = "am";
		}else if(ampmNum == 0 && hour != 11){
			ampm = "am";
		}else{
			ampm = "pm";
		}
		deliveryT = (hour+1) + ":" + minute + ampm;
		return deliveryT;
	}
	public Customer getCustomer(){
		return customer;
	}
	public void setCustomer(Customer customer){
		this.customer = customer;
	}
	
	public int getOrderID() {
		return orderID;
	}

	public double getPrice() {
		return price;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public PizzaSize getSize() {
		return size;
	}

	public PaymentMethod getPayment() {
		return payment;
	}

	public Set<Topping> getAllToppings() {
		return allToppings;
	}

	private PaymentMethod setPaymentMethod(String payment){
		if(payment.equals("visa")){
			return PaymentMethod.VISA;
		}else if(payment.equals("cash")){
			return PaymentMethod.CASH;
		}else{
			return PaymentMethod.MASTER; 
		}
	}
	
	private PizzaSize setPizzaSize(String size){
		if(size.equals("small")){
			return PizzaSize.SMALL;
		}else if(size.equals("medium")){
			return PizzaSize.MEDIUM;
		}else return PizzaSize.LARGE;
	}
	
	public Set<Topping> setToppings(String[] toppings){
		Set<Topping> allToppings = new HashSet<Topping>();
		int tLength = toppings.length;
		
		for(int i = 0; i < tLength; i++){
			allToppings.add(new Topping(toppings[i]));
		}
		return allToppings;
	}
	
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
		return total;
	}


}
