package model;


public class Customer {

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fname=" + fname + ", lname=" + lname + ", age=" + age + ", gender=" + gender
				+ ", admissionOn=" + admissionOn + "]";
	}
	private int id;
	private String fname;
	private String lname;
	private int age;
	private String gender;
	private String admissionOn;
	
	// getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname.toUpperCase();
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname.toUpperCase();
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setAdmissionOn(String admissionOn) {
		this.admissionOn = admissionOn;
	}
	public String getAdmissionOn() {
		return admissionOn;
	}
}
