package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.NycTaxiModel;

public class BulkInsertMongo {
	static MongoClient mongoClient;
	static MongoDatabase database;
	static MongoCollection<Document> collection;

	public BulkInsertMongo(){
	mongoClient = new MongoClient();
	//mongoClient.dropDatabase("bigdata");
	database = mongoClient.getDatabase("bigdata");
	collection = database.getCollection("data");
	}
	
	public void insertMongo(Collection<NycTaxiModel> data){
		List<Document> documents = data.stream()
				.filter(data1 -> data1.getDropoff_datetime() != null)
				.filter(data1 -> data1.getDropoff_latitude() != null)
				.map(item -> new Document()
						.append("dropoff_datetime", item.getDropoff_datetime())
						.append("dropoff_latitude", item.getDropoff_latitude())
						.append("dropoff_longitude", item.getDropoff_longitude())
						.append("extra", item.getExtra())
						.append("fare_amount", item.getFare_amount())
						.append("mta_tax", item.getMta_tax())
						.append("passenger_count", item.getPassenger_count())
						.append("payment_type", item.getPayment_type())
						.append("pickup_datetime", item.getPickup_datetime())
						.append("pickup_latitude", item.getPickup_latitude())
						.append("pickup_longitude", item.getPickup_longitude())
						.append("rate_code", item.getRate_code())
						.append("store_and_fwd_flag", item.getStore_and_fwd_flag())
						.append("tip_amount", item.getTip_amount())
						.append("total_amount", item.getTotal_amount())
						.append("tolls_amount", item.getTolls_amount())
						.append("trip_distance", item.getTrip_distance())
						.append("vendor_id", item.getVendor_id()))
				.collect(Collectors.toList());
		collection.insertMany(documents);
		mongoClient.close();
	}
	
}
