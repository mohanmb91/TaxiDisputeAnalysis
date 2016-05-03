package edu.csula.datascience.acquisition;

import java.util.ArrayList;
import java.util.Collection;

import model.NycTaxiTweetModel;

public class NycTwitterCollectorApp {

	public void run() {
		Collection<NycTaxiTweetModel> data = new ArrayList<NycTaxiTweetModel>();
		NycTwitterCollector nycCollector = new NycTwitterCollector();
		NycTwitterSource  nycsource = new NycTwitterSource();
		
		data = nycsource.next();
		data = nycCollector.mungee(data);
		nycCollector.save(data);
		
	}

}

