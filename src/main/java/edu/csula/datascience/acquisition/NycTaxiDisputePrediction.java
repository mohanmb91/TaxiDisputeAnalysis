package edu.csula.datascience.acquisition;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import model.NycTaxiModel;
import model.NycTaxiSource;

public class NycTaxiDisputePrediction implements  Collector<NycTaxiModel,NycTaxiModel>{

	static String startUrl = "http://www.nyc.gov/html/tlc/html/about/trip_record_data.shtml";
	static String dirName = System.getProperty("user.home")+"/Documents/downloadedCSVFiles";
	 
	public static void main(String[] args) throws MalformedURLException, IOException {
		// web scrapping is done and file downloaded
		//WebScraping webScrapingObj = new WebScraping();
		//webScrapingObj.crawler(startUrl,dirName);
		String home = System.getProperty("user.home");
		String absolutePath[]={ dirName+"/filename1.csv",
								dirName+"/filename2.csv",
								dirName+"/filename3.csv",
								dirName+"/filename4.csv",
								dirName+"/filename5.csv", };
		NycTaxiDisputePrediction nyc = new NycTaxiDisputePrediction();
		
		//Parsing is done here for the downloaded files and mungeed and saved into mongo
		Parsing  csvParserObj;
		Collection<NycTaxiModel> data = new ArrayList<NycTaxiModel>();
		for (String eachAbsolutePath : absolutePath) {
			csvParserObj = new Parsing(new File(eachAbsolutePath)); 
			data = new ArrayList<NycTaxiModel>();
			System.out.println(eachAbsolutePath);
			if(csvParserObj.hasNext()){
				data = csvParserObj.next();
				data = nyc.mungee(data);
				nyc.save(data);	
			}
		}
		
		// From API collector fetch data and do mungee and save to mongoDB
		NycTaxiAPI apiCollector = new NycTaxiAPI();
		 data = new ArrayList<NycTaxiModel>();
		if(apiCollector.hasNext()){
			data = apiCollector.next();
		}
		
		data = nyc.mungee(data);
		nyc.save(data);
	}

	@Override
	public Collection<NycTaxiModel> mungee(Collection<NycTaxiModel> src) {
		for(NycTaxiModel eachTaxiModel: src){
			try{
			if(Integer.parseInt(eachTaxiModel.getPayment_type()) == 4 ){
				eachTaxiModel.setPayment_type("1");
			}else{
				eachTaxiModel.setPayment_type("0");
			}
			}
			catch(NumberFormatException e){
				if(eachTaxiModel.getPayment_type().equals("DIS")){
					eachTaxiModel.setPayment_type("1");
				}else{
					eachTaxiModel.setPayment_type("0");
				}
			}
			if(eachTaxiModel.getStore_and_fwd_flag().equals("Y")){
				eachTaxiModel.setStore_and_fwd_flag("1");
			}
			else if(eachTaxiModel.getStore_and_fwd_flag().equals("N")){
				eachTaxiModel.setStore_and_fwd_flag("0");
			}
		    if(eachTaxiModel.getVendor_id().equals("CMT")){
				eachTaxiModel.setVendor_id("1");
			}
		    else if(eachTaxiModel.getVendor_id().equals("VTS")){
		    	eachTaxiModel.setVendor_id("2");
		    }
		    else{
		    	System.out.println(eachTaxiModel.getVendor_id());
		    }
			
		}
		return src;
	}

	@Override
	public void save(Collection<NycTaxiModel> data) {
		// TODO Auto-generated method stub
		BulkInsertMongo insertIntoMongo = new BulkInsertMongo();
		insertIntoMongo.insertMongo(data);
	}

}
