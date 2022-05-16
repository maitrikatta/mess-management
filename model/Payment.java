package model;

import java.util.Date;

public class Payment {
	@Override
	public String toString() {
		return "Payment [id=" + id + ", total=" + total + ", paid=" + paid + ", paymentDate=" + paymentDate + "]";
	}
	private int id;
	private float total;
	private float paid;
	private String paymentDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public float getPaid() {
		return paid;
	}
	public void setPaid(float paid) {
		this.paid = paid;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
}
