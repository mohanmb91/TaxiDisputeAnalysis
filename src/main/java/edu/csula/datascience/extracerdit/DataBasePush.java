package edu.csula.datascience.extracredit;


import com.google.common.collect.Lists;

import edu.csula.datascience.acquisition.NycTaxiDisputePrediction;
import model.NycTaxiModelES;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * A quick example app to send data to elastic search on AWS
 */
public class DataBasePush {
	private final static String indexName = "taxi-data";
	private final static String typeName = "csv_data";
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static String DB_URL = "jdbc:mysql://mysql3.gear.host:3306/applogindb";


	//  Database credentials
	static final String USER = "applogindb";
	static final String PASS = "Di78B?!Qu5ou";
	public static int count = 0;
	static final String insertSQL = "INSERT INTO bigdata (Dropoff_latitude,Dropoff_longitude,Pickup_longitude,Pickup_latitude,Payment_type,Passenger_count,Trip_distance) values";
	
	
	public static void main(String[] args) throws URISyntaxException, IOException {


		String sql = null;
		String data = null;
		Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			//STEP 4: Execute a query
			System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();

			File csv = new File(
					ClassLoader.getSystemResource("filename1.csv")
					.toURI()
					);

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
					if((!(((new Double(eachCSVRecord.get("Dropoff_latitude")).doubleValue()) == 0)
							||((new Double(eachCSVRecord.get("Dropoff_longitude")).doubleValue()) == 0)	
							||((new Double(eachCSVRecord.get("Pickup_longitude")).doubleValue()) == 0)
							||((new Double(eachCSVRecord.get("Pickup_latitude")).doubleValue()) == 0)))
							&& ((Integer.parseInt(nyc.mungeeForPaymentType(eachCSVRecord.get("Payment_type")))) == 1))
					{
						sql = insertSQL + "(" + 
								eachCSVRecord.get("Dropoff_latitude") + "," +
								eachCSVRecord.get("Dropoff_longitude") + "," +
								eachCSVRecord.get("Pickup_longitude") + "," +
								eachCSVRecord.get("Pickup_latitude") + "," +
								Integer.parseInt(nyc.mungeeForPaymentType(eachCSVRecord.get("Payment_type"))) + "," +
								Integer.parseInt(eachCSVRecord.get("Passenger_count")) + "," +
								Float.parseFloat(eachCSVRecord.get("Trip_distance")) + ")";
						stmt.executeUpdate(sql);
					}


				}
				else{ 
					if((locationAvailable(nameChecks,eachCSVRecord)) 
							&&	((Integer.parseInt(nyc.mungeeForPaymentType(eachCSVRecord.get(" payment_type")))) == 1))						
					{

						sql = insertSQL + "(" + 
								eachCSVRecord.get(" dropoff_latitude") + "," +
								eachCSVRecord.get(" dropoff_longitude") + "," +
								eachCSVRecord.get(" pickup_longitude") + "," +
								eachCSVRecord.get(" pickup_latitude") + "," +
								Integer.parseInt(nyc.mungeeForPaymentType(eachCSVRecord.get(" payment_type"))) + "," +
								Integer.parseInt(eachCSVRecord.get(" passenger_count")) + "," +
								Float.parseFloat(eachCSVRecord.get(" trip_distance")) + ")";
						stmt.executeUpdate(sql);

					}
				}

			}


		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try

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
