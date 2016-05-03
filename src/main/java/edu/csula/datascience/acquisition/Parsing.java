package edu.csula.datascience.acquisition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import au.com.bytecode.opencsv.CSVReader;
import model.NycTaxiModel;

public class Parsing implements Source<NycTaxiModel>{

	File fileName;
	public Parsing(File fileName){
		this.fileName = fileName; 
	}

	@Override
	public boolean hasNext() {
		try {
			CSVReader parser = new CSVReader(new FileReader(this.fileName));
			if(parser.readNext() != null){
				return true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Collection<NycTaxiModel> next() {
		NycTaxiDisputePrediction nyc = new NycTaxiDisputePrediction();
		CSVParser parser;
		int count = 0;
		Collection<NycTaxiModel> data = new ArrayList<NycTaxiModel>();
		System.out.println("intial size"+count);
		String dropoff_datetime =  null,dropoff_latitude =  null,dropoff_longitude = null,extra = null;
		String fare_amount = null,mta_tax = null,passenger_count = null,vendor_id =null;
		String trip_distance = null,total_amount = null,tolls_amount = null,tip_amount = null,store_and_fwd_flag = null;
		String rate_code = null,pickup_longitude = null,pickup_latitude = null,pickup_datetime = null,payment_type = null;
		try {
			parser = new CSVParser(new FileReader(fileName), CSVFormat.DEFAULT.withHeader());
			for (CSVRecord eachCSVRecord : parser) {
				if(!fileName.toString().contains("filename1")){
					pickup_datetime = eachCSVRecord.get("lpep_pickup_datetime");
					dropoff_datetime = eachCSVRecord.get("Lpep_dropoff_datetime");
					dropoff_latitude = eachCSVRecord.get("Dropoff_latitude");
					dropoff_longitude = eachCSVRecord.get("Dropoff_longitude");
					extra = eachCSVRecord.get("Extra");
					fare_amount = eachCSVRecord.get("Fare_amount");
					mta_tax = eachCSVRecord.get("MTA_tax");
					passenger_count = eachCSVRecord.get("Passenger_count");
					vendor_id = eachCSVRecord.get("VendorID");
					trip_distance = eachCSVRecord.get("Trip_distance");
					total_amount = eachCSVRecord.get("Total_amount");
					tolls_amount = eachCSVRecord.get("Tolls_amount");
					tip_amount = eachCSVRecord.get("Tip_amount");
					store_and_fwd_flag = eachCSVRecord.get("Store_and_fwd_flag");
					rate_code = eachCSVRecord.get("RateCodeID");
					pickup_longitude = eachCSVRecord.get("Pickup_longitude");
					pickup_latitude = eachCSVRecord.get("Pickup_latitude");
					payment_type = eachCSVRecord.get("Payment_type");
				}
				else
				{
					pickup_datetime = eachCSVRecord.get(" pickup_datetime");
					dropoff_datetime = eachCSVRecord.get(" dropoff_datetime");
					dropoff_latitude = eachCSVRecord.get(" dropoff_latitude");
					dropoff_longitude = eachCSVRecord.get(" dropoff_longitude");
					extra = eachCSVRecord.get(" surcharge");
					fare_amount = eachCSVRecord.get(" fare_amount");
					mta_tax = eachCSVRecord.get(" mta_tax");
					passenger_count = eachCSVRecord.get(" passenger_count");
					vendor_id = eachCSVRecord.get("vendor_id");
					trip_distance = eachCSVRecord.get(" trip_distance");
					total_amount = eachCSVRecord.get(" total_amount");
					tolls_amount = eachCSVRecord.get(" tolls_amount");
					tip_amount = eachCSVRecord.get(" tip_amount");
					store_and_fwd_flag = eachCSVRecord.get(" store_and_fwd_flag");
					rate_code = eachCSVRecord.get(" rate_code");
					pickup_longitude = eachCSVRecord.get(" pickup_longitude");
					pickup_latitude = eachCSVRecord.get(" pickup_latitude");
					payment_type = eachCSVRecord.get(" payment_type");
				}
				NycTaxiModel taxiModel = new NycTaxiModel(dropoff_datetime, dropoff_latitude, dropoff_longitude, extra, fare_amount, mta_tax, passenger_count, payment_type, pickup_datetime, pickup_latitude, pickup_longitude, rate_code, store_and_fwd_flag, tip_amount, tolls_amount, total_amount, trip_distance, vendor_id);
				data.add(taxiModel);
				
				if(data.size() >= 1000000)
				{
					data = nyc.mungee(data);
					nyc.save(data);
					data= new ArrayList<NycTaxiModel>();
				}
				count++;
				System.out.println(fileName + "    " + count);
			}
			System.out.println("Total Number of records "+count);
		parser.close();
		} catch (IOException e) {
			System.err.println(count+"error occured at this count");
			e.printStackTrace();
		}
		return data;
	}
	}

