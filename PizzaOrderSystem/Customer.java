package PizzaOrderSystem;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "Customer")
//@NamedQuery(name = "Customer.getCustomer", query = "select from Customer where Username = :username and Password = :password")

public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userID", unique = true, nullable = false)
	private int userID;
	
	@NaturalId
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "address")
	private Address address;
	
	@OneToMany(mappedBy = "customer")//customer = name of variable in order
	private Set<Order> orders;
	
	
	public Set<Order> getOrders(){
		return orders;
	}
	public void setOrders(Set<Order> orders){
		this.orders = orders;
	}
	public Address getAddress(){
		return address;
	}
	public void setAddress(Address address){
		this.address = address;
	}
	
	public int getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString(){
		return username + " | " + password + " | " + address.getcity();
	}
}
