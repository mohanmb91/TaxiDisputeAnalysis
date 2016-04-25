package edu.csula.datascience.acquisition;

/**
 * A simple model for testing
 */
public class SimpleModel {
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
	public SimpleModel(String pickUpDateTime, String vendorId, String paymentType) {
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

    public static SimpleModel build(MockData data) {
        return new SimpleModel(data.getPickUpDateTime(), data.getVendorId(),data.getPaymentType());
    }
}
