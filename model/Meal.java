package model;

public class Meal {
	private String customerName;
	private int customerID;
	private String date;
	private int lunch;
	private int dinner;
	private boolean rowExists;
	
	public boolean getRowExists() {
		return rowExists;
	}
	public void setRowExists(boolean rowExists) {
		this.rowExists = rowExists;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getLunch() {
		return lunch;
	}
	public void setLunch(int lunch) {
		this.lunch = lunch;
	}
	public int getDinner() {
		return dinner;
	}
	public void setDinner(int dinner) {
		this.dinner = dinner;
	}
	@Override
	public String toString() {
		return "Meal [customerName=" + customerName + ", customerID=" + customerID + ", date=" + date + ", lunch="
				+ lunch + ", dinner=" + dinner + "]";
	}
	
}
