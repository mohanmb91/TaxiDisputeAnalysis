package model;

public class NycTaxiModel {
	String dropoff_datetime;
	String dropoff_latitude;
	String dropoff_longitude;
	String extra;
	String fare_amount;
	String mta_tax;
	public NycTaxiModel(){
		
	}
	public NycTaxiModel(String dropoff_datetime, String dropoff_latitude, String dropoff_longitude, String extra,
			String fare_amount, String mta_tax, String passenger_count, String payment_type, String pickup_datetime,
			String pickup_latitude, String pickup_longitude, String rate_code, String store_and_fwd_flag,
			String tip_amount, String tolls_amount, String total_amount, String trip_distance, String vendor_id) {
		super();
		this.dropoff_datetime = dropoff_datetime;
		this.dropoff_latitude = dropoff_latitude;
		this.dropoff_longitude = dropoff_longitude;
		this.extra = extra;
		this.fare_amount = fare_amount;
		this.mta_tax = mta_tax;
		this.passenger_count = passenger_count;
		this.payment_type = payment_type;
		this.pickup_datetime = pickup_datetime;
		this.pickup_latitude = pickup_latitude;
		this.pickup_longitude = pickup_longitude;
		this.rate_code = rate_code;
		this.store_and_fwd_flag = store_and_fwd_flag;
		this.tip_amount = tip_amount;
		this.tolls_amount = tolls_amount;
		this.total_amount = total_amount;
		this.trip_distance = trip_distance;
		this.vendor_id = vendor_id;
	}
	public String getDropoff_datetime() {
		return dropoff_datetime;
	}
	public void setDropoff_datetime(String dropoff_datetime) {
		this.dropoff_datetime = dropoff_datetime;
	}
	public String getDropoff_latitude() {
		return dropoff_latitude;
	}
	public void setDropoff_latitude(String dropoff_latitude) {
		this.dropoff_latitude = dropoff_latitude;
	}
	public String getDropoff_longitude() {
		return dropoff_longitude;
	}
	public void setDropoff_longitude(String dropoff_longitude) {
		this.dropoff_longitude = dropoff_longitude;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getFare_amount() {
		return fare_amount;
	}
	public void setFare_amount(String fare_amount) {
		this.fare_amount = fare_amount;
	}
	public String getMta_tax() {
		return mta_tax;
	}
	public void setMta_tax(String mta_tax) {
		this.mta_tax = mta_tax;
	}
	public String getPassenger_count() {
		return passenger_count;
	}
	public void setPassenger_count(String passenger_count) {
		this.passenger_count = passenger_count;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getPickup_datetime() {
		return pickup_datetime;
	}
	public void setPickup_datetime(String pickup_datetime) {
		this.pickup_datetime = pickup_datetime;
	}
	public String getPickup_latitude() {
		return pickup_latitude;
	}
	public void setPickup_latitude(String pickup_latitude) {
		this.pickup_latitude = pickup_latitude;
	}
	public String getPickup_longitude() {
		return pickup_longitude;
	}
	public void setPickup_longitude(String pickup_longitude) {
		this.pickup_longitude = pickup_longitude;
	}
	public String getRate_code() {
		return rate_code;
	}
	public void setRate_code(String rate_code) {
		this.rate_code = rate_code;
	}
	public String getStore_and_fwd_flag() {
		return store_and_fwd_flag;
	}
	public void setStore_and_fwd_flag(String store_and_fwd_flag) {
		this.store_and_fwd_flag = store_and_fwd_flag;
	}
	public String getTip_amount() {
		return tip_amount;
	}
	public void setTip_amount(String tip_amount) {
		this.tip_amount = tip_amount;
	}
	public String getTolls_amount() {
		return tolls_amount;
	}
	public void setTolls_amount(String tolls_amount) {
		this.tolls_amount = tolls_amount;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getTrip_distance() {
		return trip_distance;
	}
	public void setTrip_distance(String trip_distance) {
		this.trip_distance = trip_distance;
	}
	public String getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}
	String passenger_count;
	String payment_type;
	String pickup_datetime;
	String pickup_latitude;
	String pickup_longitude;
	String rate_code;
	String store_and_fwd_flag;
	String tip_amount;
	String tolls_amount;
	String total_amount;
	String trip_distance;
	String vendor_id;
}