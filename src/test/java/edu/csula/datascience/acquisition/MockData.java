package edu.csula.datascience.acquisition;

/**
 * Mock raw data
 */
public class MockData {
   String pickUpDateTime;
   String vendorId;
   String paymentType;
public String getPickUpDateTime() {
	return pickUpDateTime;
}
public void setPickUpDateTime(String pickUpDateTime) {
	this.pickUpDateTime = pickUpDateTime;
}
public String getVendorId() {
	return vendorId;
}
public MockData(String pickUpDateTime, String vendorId, String paymentType) {
	super();
	this.pickUpDateTime = pickUpDateTime;
	this.vendorId = vendorId;
	this.paymentType = paymentType;
}
public void setVendorId(String vendorId) {
	this.vendorId = vendorId;
}
public String getPaymentType() {
	return paymentType;
}
public void setPaymentType(String paymentType) {
	this.paymentType = paymentType;
}
}
