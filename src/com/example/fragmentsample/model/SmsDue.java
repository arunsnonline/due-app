package com.example.fragmentsample.model;

public class SmsDue {
	
	private String vendorName;
	private String amount;
	private String dueDate;
	
	public SmsDue(String vendorName, String amount, String dueDate) {
		super();
		this.vendorName = vendorName;
		this.amount = amount;
		this.dueDate = dueDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	

}
