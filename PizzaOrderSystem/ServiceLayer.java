package PizzaOrderSystem;

import java.util.ArrayList;
import java.util.Set;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ServiceLayer {
	
	//the service layer parses the user input and passes the structured result to the DAO
	
	public static SessionFactory sessionFactory;
	public static Transaction transaction;
    public static Session session; 
    
    
	public ServiceLayer(){		
	}
	
	public Customer login(String username, String password){
		ConcretePizzaShopDAO dao = new ConcretePizzaShopDAO();
		Customer customer = dao.logIn(username, password);
		if(customer == null){
			return null;
		}else{
			return customer;
		}
		
	}
	public Customer signUp(String username, String password, String streetName, String city, String state, String zipCode){

		ConcretePizzaShopDAO dao = new ConcretePizzaShopDAO();
		Address address = new Address();
		address.setstreet(streetName);
		address.setcity(city);
		address.setstate(state);
		address.setzipCode(zipCode);
		Customer newCustomer = new Customer();
		newCustomer.setUsername(username);
		newCustomer.setPassword(password);
		newCustomer.setAddress(address);
		
		if(dao.signUp(newCustomer) != null)
			return newCustomer;
		else{
			return null;
		}
	}
	public void makeOrder(Customer customer, String size, String[] toppings, String payment){
		
		ConcretePizzaShopDAO dao = new ConcretePizzaShopDAO();
		Order order = new Order(size,toppings,payment);
		ArrayList<Topping> list = new ArrayList<Topping>();
		for(int i = 0; i < toppings.length; i++){
			list.add(new Topping(toppings[i]));
		}
		dao.makeOrder(customer, order, list);
	}
	public void makeDiscountedOrder(Customer customer, String size, String[] toppings, String payment){
		
		ConcretePizzaShopDAO dao = new ConcretePizzaShopDAO();
		DiscountedOrder order = new DiscountedOrder(size,toppings,payment);
		ArrayList<Topping> list = new ArrayList<Topping>();
		for(int i = 0; i < toppings.length; i++){
			list.add(new Topping(toppings[i]));
		}
		dao.makeDiscountedOrder(customer, order, list);
	}
	
	public void viewOrders(Customer customer){
		ConcretePizzaShopDAO dao = new ConcretePizzaShopDAO();
		dao.viewOrders(customer);
		
	}
	public void changeOrder(Customer customer,int orderNum,String size,String[] toppings, String payment){
		ConcretePizzaShopDAO dao = new ConcretePizzaShopDAO();
		Order order = new Order(size,toppings,payment);
		ArrayList<Topping> list = new ArrayList<Topping>();
		for(int i = 0; i < toppings.length; i++){
			list.add(new Topping(toppings[i]));
		}
		dao.changeOrder(customer,orderNum,order,list);
	}
	public void cancelOrder(int orderNum){
		ConcretePizzaShopDAO dao = new ConcretePizzaShopDAO();
		dao.deleteOrder(orderNum);
	}
/*
	
	public static void main(String[] args) throws SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		
		   sessionFactory = HibernateUtil.getSessionFactory();
		   
		   Address address = new Address("1236 amb","san lean","ca","94577");
		   String[] toppings = {"pepperoni","bellpeppers"}; 
		   Order order = new DiscountedOrder("small",toppings,"visa");
		   Customer customer = new Customer(); // transient object
		   customer.setAddress(address);
		   customer.setPassword("klai8");
		   customer.setUsername("zeratal2");
	       Topping x = new Topping("pepperoni");
	       Topping y = new Topping("pepperoni");
	       Topping z = new Topping("pepperoni");
	       
		   
	      try{
	       session = sessionFactory.openSession();
	       transaction = session.beginTransaction();
	       System.out.println("made it past here");
	       
	       session.save(customer); // student is now persistent object
	       customer.setPassword("zeratal");
	       order.setCustomer(customer);
	       x.getOrder().add(order);
	       y.getOrder().add(order);
	       z.getOrder().add(order);
	       
	       session.save(y);
	       session.save(z);
	       session.save(order);
	       session.save(x);
	       
	       // session.update(student); // no need
	       //System.out.println("1:" + order);  
	       
	       transaction.commit(); // if you comment this out, no tuple is created in db. 
	      }
	       catch (HibernateException he)
	       {
	           transaction.rollback();
	           System.out.println("Transaction is rolled back.");
	           he.printStackTrace();
	       }
	      finally
	      { session.close(); }

	}
	*/
}

