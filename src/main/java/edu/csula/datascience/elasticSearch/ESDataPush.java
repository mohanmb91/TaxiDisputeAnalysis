package edu.csula.datascience.elasticSearch;
import com.google.gson.Gson;

import edu.csula.datascience.acquisition.NycTaxiDisputePrediction;
import model.NycTaxiModel;
import model.NycTaxiModelES;
import model.location;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * A quick elastic search example app
 *
 * It will parse the csv file from the resource folder under main and send these
 * data to elastic search instance running locally
 *
 * After that we will be using elastic search to do full text search
 *
 * gradle command to run this app `gradle esExample`
 */
public class ESDataPush {
	private final static String indexName = "taxi-data";
	private final static String typeName = "csv_data";
	static int i = 0;
	public static void main(String[] args) throws URISyntaxException, IOException {
		Node node = nodeBuilder().settings(Settings.builder()
				.put("cluster.name", "elasticsearch_mohan")
				.put("path.home", "elasticsearch-data")).node();
		Client client = node.client();

		/**
		 *
		 *
		 * INSERT data to elastic search
		 */

		// as usual process to connect to data source, we will need to set up
		// node and client// to read CSV file from the resource folder
		File csv = new File(
				ClassLoader.getSystemResource("filename5.csv")
				.toURI()
				);

		// create bulk processor
		BulkProcessor bulkProcessor = BulkProcessor.builder(
				client,
				new BulkProcessor.Listener() {
					@Override
					public void beforeBulk(long executionId,
							BulkRequest request) {
					}

					@Override
					public void afterBulk(long executionId,
							BulkRequest request,
							BulkResponse response) {
					}

					@Override
					public void afterBulk(long executionId,
							BulkRequest request,
							Throwable failure) {
						System.out.println("Facing error while importing data to elastic search");
						failure.printStackTrace();
					}
				})
				.setBulkActions(10000)
				.setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
				.setFlushInterval(TimeValue.timeValueSeconds(5))
				.setConcurrentRequests(1)
				.setBackoffPolicy(
						BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
				.build();

		// Gson library for sending json to elastic search
		Gson gson = new Gson();

		try {
			// after reading the csv file, we will use CSVParser to parse through
			// the csv files
			CSVParser parser = new CSVParser(new FileReader(csv), CSVFormat.DEFAULT.withHeader());

			// for each record, we will insert data into Elastic Search
			NycTaxiDisputePrediction nyc = new NycTaxiDisputePrediction();
			parser.forEach(eachCSVRecord -> {
				NycTaxiModelES taxiModel = null;
				// cleaning up dirty data which doesn't have time or temperature
				i += 1;
				System.out.println(i);
				
				String[] nameChecks = {" dropoff_latitude"," dropoff_longitude"," pickup_longitude"," pickup_latitude"};
				
				if(!csv.toString().contains("filename1")){
					if(!(((new Double(eachCSVRecord.get("Dropoff_latitude")).doubleValue()) == 0)
							||((new Double(eachCSVRecord.get("Dropoff_longitude")).doubleValue()) == 0)	
							||((new Double(eachCSVRecord.get("Pickup_longitude")).doubleValue()) == 0)
							||((new Double(eachCSVRecord.get("Pickup_latitude")).doubleValue()) == 0)))						
						{
						taxiModel = new NycTaxiModelES(
								eachCSVRecord.get("Lpep_dropoff_datetime"),
								Double.parseDouble(eachCSVRecord.get("Extra")),
								Double.parseDouble(eachCSVRecord.get("Fare_amount")),
								Double.parseDouble(eachCSVRecord.get("MTA_tax")),
								Integer.parseInt(eachCSVRecord.get("Passenger_count")),
								Integer.parseInt(nyc.mungeeForPaymentType(eachCSVRecord.get("Payment_type"))),
								eachCSVRecord.get("lpep_pickup_datetime"),
								Integer.parseInt(eachCSVRecord.get("RateCodeID")),
								Boolean.parseBoolean(nyc.mungeeForStoreAndFrwd(eachCSVRecord.get("Store_and_fwd_flag"))),
								Double.parseDouble(eachCSVRecord.get("Tip_amount")),
								Double.parseDouble(eachCSVRecord.get("Tolls_amount")),
								Double.parseDouble(eachCSVRecord.get("Total_amount")),
								Float.parseFloat(eachCSVRecord.get("Trip_distance")),
								Integer.parseInt(nyc.mungeeVendorId(eachCSVRecord.get("VendorID")))
								);
						taxiModel.setDropopp_location(
								eachCSVRecord.get("Dropoff_longitude"),
								eachCSVRecord.get("Dropoff_latitude")
								);
						taxiModel.setPickup_location(
								eachCSVRecord.get("Pickup_longitude"),
								eachCSVRecord.get("Pickup_latitude")
								);
						}

					}
				else if(locationAvailable(nameChecks,eachCSVRecord))						
						{
						taxiModel = new NycTaxiModelES(
								eachCSVRecord.get(" dropoff_datetime"),
								Double.parseDouble(eachCSVRecord.get(" surcharge")),
								Double.parseDouble(eachCSVRecord.get(" fare_amount")),
								Double.parseDouble(eachCSVRecord.get(" mta_tax")),
								Integer.parseInt(eachCSVRecord.get(" passenger_count")),
								Integer.parseInt(nyc.mungeeForPaymentType(eachCSVRecord.get(" payment_type"))),
								eachCSVRecord.get(" pickup_datetime"),
								Integer.parseInt(eachCSVRecord.get(" rate_code")),
								Boolean.parseBoolean(nyc.mungeeForStoreAndFrwd(eachCSVRecord.get(" store_and_fwd_flag"))),
								Double.parseDouble(eachCSVRecord.get(" tip_amount")),
								Double.parseDouble(eachCSVRecord.get(" tolls_amount")),
								Double.parseDouble(eachCSVRecord.get(" total_amount")),
								Float.parseFloat(eachCSVRecord.get(" trip_distance")),
								Integer.parseInt(nyc.mungeeVendorId(eachCSVRecord.get("vendor_id")))
								);
						taxiModel.setDropopp_location(
								eachCSVRecord.get(" dropoff_longitude"),
								eachCSVRecord.get(" dropoff_latitude")
								);
						taxiModel.setPickup_location(
								eachCSVRecord.get(" pickup_longitude"),
								eachCSVRecord.get(" pickup_latitude")
								);
					}
					//		taxiModel = nyc.mungeeOneRecord(taxiModel);

				if(taxiModel != null){
					bulkProcessor.add(new IndexRequest(indexName, typeName)
							.source(gson.toJson(taxiModel))
							);
				}
				}
					);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Structured search
		 */

	}
private static boolean locationAvailable(String[] nameChecks, CSVRecord eachCSVRecord) {
	boolean valueNotZero = true;
	try{
	for (String eachName : nameChecks) {
		if (((new Double(eachCSVRecord.get(eachName)).doubleValue()) == 0)){
			valueNotZero = false;
		}
	}
	
	}
	catch(Exception e){
		return false;
	}
	
	return valueNotZero;
}

}