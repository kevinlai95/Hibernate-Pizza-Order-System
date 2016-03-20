package PizzaOrderSystem;

public enum PizzaSize {
	SMALL,
	MEDIUM,
	LARGE;
	
	private int small = 3;
	private int medium = 5;
	private int large = 7;
	
	public int getSmall() {
		return small;
	}
	public int getMedium() {
		return medium;
	}
	public int getLarge() {
		return large;
	}
	public void setSmall(int newValue){
		this.small = newValue;
	}
	public void setMedium(int newValue){
		this.medium = newValue;
	}
	public void setLarge(int newValue){
		this.large = newValue;
	}
	
}
