package PizzaOrderSystem;

import java.util.ArrayList;
import java.util.Set;

public interface PizzaShopDAO {
	public Customer signUp(Customer customer);
	public Customer logIn(String username, String password);
	public void makeOrder(Customer customer,Order order,ArrayList<Topping> list);
	public void makeDiscountedOrder(Customer customer,DiscountedOrder order, ArrayList<Topping> list);
	public void viewOrders(Customer customer);
	//public ArrayList<Order> viewToppings(Customer customer);
	//public void deleteToppings(Set<Topping> toppings);
	public void changeOrder(Customer customer,int orderNum,Order order,ArrayList<Topping> list);
	public Boolean deleteOrder(int orderNum);
	
	
}
