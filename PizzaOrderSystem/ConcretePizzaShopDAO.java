package PizzaOrderSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ConcretePizzaShopDAO implements PizzaShopDAO{
	//service layer call this class to modify the data object
	
	private static SessionFactory sessionFactory;
	private static Transaction transaction;
    private static Session session; 
    
	public ConcretePizzaShopDAO(){
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	@Override
	public Customer signUp(Customer customer) {
		try{
		    session = sessionFactory.openSession();
		    transaction = session.beginTransaction();
	
			Query query = session.createQuery("FROM Customer where username = :user");
			query.setString("user", customer.getUsername());
			Customer customerChec = (Customer)query.uniqueResult();
			if(customerChec == null){
				session.save(customer);
			    transaction.commit();
			    return customer;
			}else{
				return null;
			}
		}catch(HibernateException e){
	        transaction.rollback();
	        System.out.println("Transaction is rolled back.");
	        //e.printStackTrace();
	        return null;
		}
		finally{
			session.close();
		}
	}

	@Override
	public Customer logIn(String username, String password) {
		try{
		    session = sessionFactory.openSession();
		    transaction = session.beginTransaction();
		    
			Query query = session.createQuery("FROM Customer where username = :user and password = :pass");
			query.setString("user", username);
			query.setString("pass", password);
			Customer customerChec = (Customer)query.uniqueResult();
			if(customerChec != null){
			    return customerChec;
			}else{
				return null;
			}
		}catch(HibernateException e){
	        transaction.rollback();
	        System.out.println("Transaction is rolled back.");
	        //e.printStackTrace();
	        return null;
		}
		finally{
			session.close();
		}
	}

	@Override
	public void makeOrder(Customer customer, Order order, ArrayList<Topping> list) {
		try{
			session = sessionFactory.openSession();
		    transaction = session.beginTransaction();
		    
		    order.setCustomer(customer);
		    for(Topping t : list){
		    	//System.out.println(t.getTopping());
		    	t.getOrder().add(order);
		    }
		    session.save(order);
		    transaction.commit();
			
		}catch(HibernateException e){
			transaction.rollback();
			System.out.println("Transaction is rolled back.");
	        //e.printStackTrace();
		}finally{
			session.close();
		}
		
	}

	@Override
	public void makeDiscountedOrder(Customer customer, DiscountedOrder order, ArrayList<Topping> list) {
		try{
			session = sessionFactory.openSession();
		    transaction = session.beginTransaction();
		    
		    order.setCustomer(customer);
		    for(Topping t : list){
		    	t.getOrder().add(order);
		    }
		    session.save(order);
		    transaction.commit();
			
		}catch(HibernateException e){
			transaction.rollback();
			System.out.println("Transaction is rolled back.");
	        //e.printStackTrace();
		}finally{
			session.close();
		}
		
	}
	@Override
	public void viewOrders(Customer customer) {
		try{
		    session = sessionFactory.openSession();
		    transaction = session.beginTransaction();
		    
		    String toppingStr;
		    List<Order> orders;
			Query query = session.createQuery("FROM Order where userID = :id");
			query.setInteger("id", customer.getUserID()); 
			orders = query.list();
			for(Order o : orders){
				toppingStr = "";
				for(Topping t : o.allToppings){
					toppingStr = t.getTopping() + " " + toppingStr;
				}
				System.out.println("(" + "Order #" + o.getOrderID() + ") " + o.getSize() + " pizza with " 
				+ toppingStr +"(Cost: " + o.getPrice() + ") (Payment: " + o.getPayment() + ") " + "Ready at: " + o.deliveryTime);
			}
		}catch(HibernateException e){
	        transaction.rollback();
	        System.out.println("Transaction is rolled back.");
	        e.printStackTrace();
		}
		finally{
			session.close();
		}
	}
	@Override
	public void changeOrder(Customer customer,int orderNum,Order newOrder,ArrayList<Topping> list) {
		try{
		    session = sessionFactory.openSession();
		    transaction = session.beginTransaction();
		    
			Query query = session.createQuery("FROM Order where orderID = :id");
			query.setInteger("id", orderNum); 
			Order order = (Order) query.uniqueResult();
			
			double total = 0;
			switch(order.getSize()){
				case SMALL:
					total += 3;
					break;
				case MEDIUM:
					total += 5;
					break;
				default:
					total += 7;
			}
			for(Topping s : order.allToppings){
				total += s.getPrice();
			}
			
			if(total != order.price){
				order.price = newOrder.price*.9;
			}else{
				order.price = newOrder.price;
			}
			HashSet<Topping> newT = new HashSet<Topping>();
			for(Topping i : list){
				newT.add(i);
			}
			order.deliveryTime = newOrder.deliveryTime;
			order.payment = newOrder.payment;
			order.size = newOrder.size;
			order.allToppings = newT;
			
			session.saveOrUpdate(order);
			transaction.commit();

		}catch(HibernateException e){
	        transaction.rollback();
	        System.out.println("Transaction is rolled back.");
	        e.printStackTrace();
		}
		finally{
			session.close();
		}
	}

	@Override
	public Boolean deleteOrder(int orderNum) {
		try{
		    session = sessionFactory.openSession();
		    transaction = session.beginTransaction();
	
			Query query = session.createQuery("FROM Order where orderID = :id");
			query.setInteger("id", orderNum);
			Order delOrder = (Order)query.uniqueResult();
			if(delOrder == null){
				return false;
			}else{
				session.delete(delOrder);
				transaction.commit();
				return true;
			}
			
		}catch(HibernateException e){
	        transaction.rollback();
	        System.out.println("Transaction is rolled back.");
	        //e.printStackTrace();
	        return false;
		}
		finally{
			session.close();
		}
	}

}
