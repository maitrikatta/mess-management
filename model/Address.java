package model;

public class Address {
	@Override
	public String toString() {
		return "Address [id=" + id + ", state=" + state + ", city=" + city + ", street=" + street + ", mobile=" + mobile
				+ "]";
	}
	private int id;
	private String state;
	private String city;
	private String street;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	private String mobile;
}
