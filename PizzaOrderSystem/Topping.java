package PizzaOrderSystem;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Topping")
public class Topping {

	@Id
	@GeneratedValue
	@Column(name = "toppingID")
	private int toppingID;
	
	@Column(name = "topping")
	private String topping;
	
	@Column(name = "price")
	private Double price; 
	
	/*
	@ManyToOne
	@JoinColumn(name = "orderID",insertable = false, updatable = false)
	*/
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "order_topping", 
			joinColumns = {@JoinColumn(name = "toppingID")}, 
			inverseJoinColumns = {@JoinColumn(name = "orderID")})
	Set<Order> order = new HashSet<Order>();
	
	public Topping(){
		
	}
	
	public void setTopping(String topping) {
		this.topping = topping;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Topping(String topping){
		this.topping = topping;
		if(topping.matches("pepperoni|sausage|bacon|extracheese|pineapple")){
			price = .5;
		}else{
			price = .25;
		}
	}
	public Set<Order> getOrder(){
		return order;
	}
	
	public void setOrder(Set<Order> order){
		this.order = order;
	}

	public String getTopping() {
		return topping;
	}

	public Double getPrice() {
		return price;
	}
	
	@Override
	public String toString(){
		return topping;
	}
}
