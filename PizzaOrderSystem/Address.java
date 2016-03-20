package PizzaOrderSystem;
import javax.persistence.Embeddable;

 
@Embeddable
public class Address {
	private String street;
	private String city;
	private String state; 
	private String zipCode; 
	
	public Address(String street, String city, String state, String zipCode){
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = state;
	}
	public Address(){
		
	}
	
	public String getstreet() {
		return street;
	}
	public void setstreet(String street) {
		this.street = street;
	}
	public String getcity() {
		return city;
	}
	public void setcity(String city) {
		this.city = city;
	}
	public String getstate() {
		return state;
	}
	public void setstate(String state) {
		this.state = state;
	}
	public String getzipCode() {
		return zipCode;
	}
	public void setzipCode(String zipCode) {
		this.zipCode = zipCode; ;
	}
	
}

