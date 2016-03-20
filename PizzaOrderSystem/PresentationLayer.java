package PizzaOrderSystem;
import java.util.ArrayList;
import java.util.Scanner;

public class PresentationLayer {
	Scanner in = new Scanner(System.in);
	Customer customer = null;
	ServiceLayer sl = new ServiceLayer();
	
	
	public void startSession(){
		Scanner temp = new Scanner(System.in);
		System.out.println("(Sign Up) | (Log In) | (New Order) | (New Discounted Order)"
				+ " | (View Order) | (Change Order) | (Cancel Order)");
		String input = clean(temp.nextLine());
		switch (input){
		case "signup":
			signUp();
			break;
		case "login":
			logIn();
			break;
		case "neworder":
		case "order":
			regularOrder();
			break;
		case "newdiscountedorder":
		case "discount":
		case "discounted":
		case "discountedorder":
			discountedOrder();
			break;
		case "vieworder":
		case "view":
			viewOrder();
			break;
		case "changeorder":
		case "change":
			changeOrder();
			break;
		case "cancelorder":
		case "cancel":
			cancelOrder();
			break;
		default: 
			System.out.println("error: " + input);
			
		}
		temp.close();
	}
	public void signUp(){
		System.out.println("[Account Sign Up]");
		String username,password,streetName,city,state,zipcode;
		System.out.println("Username: ");
		username = in.nextLine();
		System.out.println("Password: ");
		password = in.nextLine();
		System.out.println("Street Name: ");
		streetName = in.nextLine();
		System.out.println("City: ");
		city = in.nextLine();
		System.out.println("State: ");
		state = in.nextLine();
		System.out.println("Zipcode: ");
		zipcode = in.nextLine();
		customer = sl.signUp(username, password, streetName, city, state, zipcode);
		if(customer != null){
			System.out.println("Accounted Created! You are signed in as " + customer.getUsername());
			startSession();
		}
		else{
			System.out.println("An account exists with that username");
			signUp();
		}
		
	}
	
	public void logIn(){
		System.out.println("[Account Log In]");
		String username,password;
		System.out.println("Username: ");
		username = in.nextLine();
		System.out.println("Password: ");
		password = in.nextLine();
		customer = sl.login(username, password);
		if(customer == null){
			System.out.println("The username/password you entered does not exist create an account");
			signUp();
		}else{
			System.out.println("You have successfully logged in as " + customer.getUsername());
			startSession();
		}
		
	}
	
	public void regularOrder(){
		
		boolean flag = true;
		
		String size = null;
		String [] toppings = null;
		String payment = null;
		
		if(customer == null){
			System.out.println("You must sign in");
			logIn();
		}
		
		System.out.println("[Size]");
		while(flag){
			System.out.println("Small(6-inch):$3 | Medium(8-inch):$5  | Large(12-inch):$7");
			size = in.nextLine();
			if(size.trim().toLowerCase().matches("small|medium|large")){
				flag = false;
			}else{
				System.out.println("Invalid size entered");
			}
		}
		size = clean(size);
		System.out.println(size);
		flag = true;
		System.out.println("[Toppings]");
		System.out.println("3 max comma separated toppings (ex. pepperoni,bacon,extra cheese)");
		while(flag){
			System.out.println("Premium Toppings (0.50): Pepperoni | Sausage | Bacon | Pineapple | Extra Cheese ");
			System.out.println("Regular Toppings (0.25): Mushrooms | Onions | Black Olives | Green Peppers | Spinach");
			toppings = in.nextLine().split(",");
			int tLength = toppings.length;
			for(int i = 0; i < tLength; i++){
				toppings[i] = clean(toppings[i]);
			}
			
			for(int i = 0; i < tLength; i++){
				if(tLength > 3){
					System.out.println("You exceed 3 toppings");
					break;
				}
				if(toppings[i].toLowerCase().matches("pepperoni|(mushroom)s?|(onion)s?|sausage|bacon|"
						+ "extracheese|(blackolive)s?|(greenpepper)s?|pineapple|spinach")){
					flag = false;
				}else{
					System.out.println("Invalid topping entered");
					flag = true;
					break;
				}
			}
		}
		flag = true;
		System.out.println("[Payment]");
		while(flag){
			System.out.println("We accept: Visa | Cash | Mastercard");
			payment = in.nextLine().toLowerCase();
			if(payment.matches("visa|cash|(master)card?")){
				flag = false;
			}else{
				System.out.println("That form of payment is not accepted");
			}
		}
		sl.makeOrder(customer,size,toppings,payment);
		System.out.println("Your order has been placed");
		startSession();
		
	}
	public void discountedOrder(){
		
		boolean flag = true;
		String size = null;
		String [] toppings = null;
		String payment = null;
		
		if(customer == null){
			System.out.println("You must sign in");
			logIn();
		}		
		
		System.out.println("[Size]");
		while(flag){
			System.out.println("Small(6-inch):$3 | Medium(8-inch):$5  | Large(12-inch):$7");
			size = in.nextLine();
			if(size.trim().toLowerCase().matches("small|medium|large")){
				flag = false;
			}else{
				System.out.println("Invalid size entered");
			}
		}
		size = clean(size);
		System.out.println(size);
		flag = true;
		System.out.println("[Toppings]");
		System.out.println("3 max comma separated toppings (ex. pepperoni,bacon,extra cheese)");
		while(flag){
			System.out.println("Premium Toppings (0.50): Pepperoni | Sausage | Bacon | Pineapple | Extra Cheese ");
			System.out.println("Regular Toppings (0.25): Mushrooms | Onions | Black Olives | Green Peppers | Spinach");
			toppings = in.nextLine().split(",");
			int tLength = toppings.length;
			for(int i = 0; i < tLength; i++){
				toppings[i] = clean(toppings[i]);
			}
			
			for(int i = 0; i < tLength; i++){
				if(tLength > 3){
					System.out.println("You exceed 3 toppings");
					break;
				}
				if(toppings[i].toLowerCase().matches("pepperoni|(mushroom)s?|(onion)s?|sausage|bacon|"
						+ "extracheese|(blackolive)s?|(greenpepper)s?|pineapple|spinach")){
					flag = false;
				}else{
					System.out.println("Invalid topping entered");
					flag = true;
					break;
				}
			}
		}
		flag = true;
		System.out.println("[Payment]");
		while(flag){
			System.out.println("We accept: Visa | Cash | Mastercard");
			payment = in.nextLine().toLowerCase();
			if(payment.matches("visa|cash|(master)card?")){
				flag = false;
			}else{
				System.out.println("That form of payment is not accepted");
			}
		}
		sl.makeDiscountedOrder(customer,size,toppings,payment);
		System.out.println("Your discounted order has been placed");
		startSession();
	}
	Boolean changeOrder = false;
	public void viewOrder(){
		
		if(customer == null){
			System.out.println("You must sign in");
			logIn();
		}
		
		sl.viewOrders(customer);
		if(changeOrder == false){
			startSession();
		}
		
	}
	
	public void changeOrder(){
		changeOrder = true;
		int orderNum;
		viewOrder();
		System.out.println("Which order would you like to change? Enter the order number.");
		orderNum = in.nextInt();
		
		boolean flag = true;
		String size = null;
		String [] toppings = null;
		String payment = null;		
		
		System.out.println("[Size]");
		while(flag){
			System.out.println("Small(6-inch):$3 | Medium(8-inch):$5  | Large(12-inch):$7");
			size = in.nextLine();
			if(size.trim().toLowerCase().matches("small|medium|large")){
				flag = false;
			}else{
				System.out.println("Invalid size entered");
			}
		}
		size = clean(size);
		System.out.println(size);
		flag = true;
		System.out.println("[Toppings]");
		System.out.println("3 max comma separated toppings (ex. pepperoni,bacon,extra cheese)");
		while(flag){
			System.out.println("Premium Toppings (0.50): Pepperoni | Sausage | Bacon | Pineapple | Extra Cheese ");
			System.out.println("Regular Toppings (0.25): Mushrooms | Onions | Black Olives | Green Peppers | Spinach");
			toppings = in.nextLine().split(",");
			int tLength = toppings.length;
			for(int i = 0; i < tLength; i++){
				toppings[i] = clean(toppings[i]);
			}
			
			for(int i = 0; i < tLength; i++){
				if(tLength > 3){
					System.out.println("You exceed 3 toppings");
					break;
				}
				if(toppings[i].toLowerCase().matches("pepperoni|(mushroom)s?|(onion)s?|sausage|bacon|"
						+ "extracheese|(blackolive)s?|(greenpepper)s?|pineapple|spinach")){
					flag = false;
				}else{
					System.out.println("Invalid topping entered");
					flag = true;
					break;
				}
			}
		}
		flag = true;
		System.out.println("[Payment]");
		while(flag){
			System.out.println("We accept: Visa | Cash | Mastercard");
			payment = in.nextLine().toLowerCase();
			if(payment.matches("visa|cash|(master)card?")){
				flag = false;
			}else{
				System.out.println("That form of payment is not accepted");
			}
		}
		sl.changeOrder(customer,orderNum,size,toppings,payment);
		changeOrder = false;
		startSession();
	}
	public void cancelOrder(){
		changeOrder = true;
		int orderNum;
		viewOrder();
		System.out.println("Which order would you like to change? Enter the order number.");
		orderNum = in.nextInt();
		sl.cancelOrder(orderNum);
		changeOrder = false;
		startSession();
	}
	
	public String clean(String input){
		String ret = input.replaceAll(" ", "");
		ret = ret.toLowerCase();
		return ret;
	}
	
	public static void main(String[] args) {
		PresentationLayer layer = new PresentationLayer();
		while(true){
			layer.startSession();
		}
		
		
		
	}
}
