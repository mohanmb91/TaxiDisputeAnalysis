package model;

	import java.util.List;

import org.elasticsearch.common.geo.GeoPoint;

	public class NycTaxiModelES {
		
		String dropoff_datetime;
		double extra;
		String dropopp_location;
		String pickup_location;
		double fare_amount;
		double mta_tax;
		int passenger_count;
		int dispute;
		String pickup_datetime;
		int rate_code;
		boolean store_and_fwd_flag;
		double tip_amount;
		double tolls_amount;
		double total_amount;
		float trip_distance;
		int vendor_id;
		public NycTaxiModelES(){
			
		}
		public NycTaxiModelES(String dropoff_datetime,
		double extra,
		double fare_amount,
		double mta_tax,
		int passenger_count,
		int dispute,
		String pickup_datetime,
		int rate_code,
		boolean store_and_fwd_flag,
		double tip_amount,
		double tolls_amount,
		double total_amount,
		float trip_distance,
		int vendor_id) {
			super();
			this.dropoff_datetime = dropoff_datetime;
			this.extra = extra;
			this.fare_amount = fare_amount;
			this.mta_tax = mta_tax;
			this.passenger_count = passenger_count;
			this.dispute = dispute;
			this.pickup_datetime = pickup_datetime;
			this.rate_code = rate_code;
			this.store_and_fwd_flag = store_and_fwd_flag;
			this.tip_amount = tip_amount;
			this.tolls_amount = tolls_amount;
			this.total_amount = total_amount;
			this.trip_distance = trip_distance;
			this.vendor_id = vendor_id;
		}
		public String getPickup_datetime() {
			return pickup_datetime;
		}
		public void setPickup_datetime(String pickup_datetime) {
			this.pickup_datetime = pickup_datetime;
		}
		public double getExtra() {
			return extra;
		}
		public void setExtra(double extra) {
			this.extra = extra;
		}
		public double getFare_amount() {
			return fare_amount;
		}
		public void setFare_amount(double fare_amount) {
			this.fare_amount = fare_amount;
		}
		public double getMta_tax() {
			return mta_tax;
		}
		public void setMta_tax(double mta_tax) {
			this.mta_tax = mta_tax;
		}
		public int getPassenger_count() {
			return passenger_count;
		}
		public void setPassenger_count(int passenger_count) {
			this.passenger_count = passenger_count;
		}
		
		public int getDispute() {
			return dispute;
		}
		public void setDispute(int dispute) {
			this.dispute = dispute;
		}
		public int getRate_code() {
			return rate_code;
		}
		public void setRate_code(int rate_code) {
			this.rate_code = rate_code;
		}
		public boolean isStore_and_fwd_flag() {
			return store_and_fwd_flag;
		}
		public void setStore_and_fwd_flag(boolean store_and_fwd_flag) {
			this.store_and_fwd_flag = store_and_fwd_flag;
		}
		public double getTip_amount() {
			return tip_amount;
		}
		public void setTip_amount(double tip_amount) {
			this.tip_amount = tip_amount;
		}
		public double getTolls_amount() {
			return tolls_amount;
		}
		public void setTolls_amount(double tolls_amount) {
			this.tolls_amount = tolls_amount;
		}
		public double getTotal_amount() {
			return total_amount;
		}
		public void setTotal_amount(double total_amount) {
			this.total_amount = total_amount;
		}
		public float getTrip_distance() {
			return trip_distance;
		}
		public void setTrip_distance(float trip_distance) {
			this.trip_distance = trip_distance;
		}
		public int getVendor_id() {
			return vendor_id;
		}
		public void setVendor_id(int vendor_id) {
			this.vendor_id = vendor_id;
		}
		public void setDropopp_location(String dropopp_location) {
			this.dropopp_location = dropopp_location;
		}
		public void setPickup_location(String pickup_location) {
			this.pickup_location = pickup_location;
		}
		public String getDropoff_datetime() {
			return dropoff_datetime;
		}
		public String getDropopp_location() {
			return dropopp_location;
		}
		public String getPickup_location() {
			return pickup_location;
		}
		public void setPickup_location(String longitude,String lat) {
			GeoPoint geo = new GeoPoint(new Double(lat).doubleValue(), new Double(longitude).doubleValue());
//			loc.setLat(new Double(lat).doubleValue());
//			loc.setLon(new Double(longitude).doubleValue());
			
			this.pickup_location = lat+","+longitude; 
		}
		public void setDropopp_location(String longitude,String lat) {
//			location loc = new location();
//			loc.setLat(new Double(lat).doubleValue());
//			loc.setLon(new Double(longitude).doubleValue());
			
			this.dropopp_location = lat+","+longitude; 
			
		}
		public void setDropoff_datetime(String dropoff_datetime) {
			this.dropoff_datetime = dropoff_datetime;
		}
		
	}
