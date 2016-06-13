package edu.csula.datascience.elasticSearch;

import com.google.common.collect.Lists;

import edu.csula.datascience.acquisition.NycTaxiDisputePrediction;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import model.NycTaxiModelES;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.action.index.IndexRequest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * A quick example app to send data to elastic search on AWS
 */
public class ESDataPushRemote {
	private final static String indexName = "taxi-data";
	private final static String typeName = "csv_data";
	public static int count = 0;
    public static void main(String[] args) throws URISyntaxException, IOException {
        
    	 String awsAddress = "http://search-big-data-taxitripdata-l5ga55hddou4quzgubbxeycusm.us-west-2.es.amazonaws.com/";
         JestClientFactory factory = new JestClientFactory();
         factory.setHttpClientConfig(new HttpClientConfig
             .Builder(awsAddress)
             .multiThreaded(true)
             .build());
         JestClient client = factory.getObject();

         // as usual process to connect to data source, we will need to set up
         // node and client// to read CSV file from the resource folder
         File csv = new File(
             ClassLoader.getSystemResource("filename1.csv")
                 .toURI()
         );

        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
        	CSVParser parser = new CSVParser(new FileReader(csv), CSVFormat.DEFAULT.withHeader());

            Collection<NycTaxiModelES> nycTaxiModels = Lists.newArrayList();

            int i = 0;

            // for each record, we will insert data into Elastic Search
//            parser.forEach(record -> {
            NycTaxiDisputePrediction nyc = new NycTaxiDisputePrediction();
            for (CSVRecord eachCSVRecord: parser) {
            	NycTaxiModelES taxiModel = null;
				// cleaning up dirty data which doesn't have time or temperature
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
				 if (i < 500) {
                     nycTaxiModels.add(taxiModel);
                     count += 1;
                     System.out.println(count);
                     i ++;
                 } else {
                     try {
                         Collection<BulkableAction> actions = Lists.newArrayList();
                         nycTaxiModels.stream()
                             .forEach(tmp -> {
                                 actions.add(new Index.Builder(tmp).build());
                             });
                         Bulk.Builder bulk = new Bulk.Builder()
                             .defaultIndex(indexName)
                             .defaultType(typeName)
                             .addAction(actions);
                         client.execute(bulk.build());
                         i = 0;
                         nycTaxiModels = Lists.newArrayList();
                         //System.out.println("Inserted 500 documents to cloud");
                         count += 1;
                         System.out.println(count);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
            }

            Collection<BulkableAction> actions = Lists.newArrayList();
            nycTaxiModels.stream()
                .forEach(tmp -> {
                    actions.add(new Index.Builder(tmp).build());
                });
            Bulk.Builder bulk = new Bulk.Builder()
                .defaultIndex(indexName)
                .defaultType(typeName)
                .addAction(actions);
            client.execute(bulk.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("We are done! Yay!");
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