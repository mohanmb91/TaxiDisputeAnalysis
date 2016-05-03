package edu.csula.datascience.acquisition;

import java.util.Collection;

import edu.csula.datascience.acquisition.BulkInsertMongo;
import edu.csula.datascience.acquisition.Collector;
import model.NycTaxiTweetModel;

public class NycTwitterCollector implements Collector<NycTaxiTweetModel, NycTaxiTweetModel>  {
	
	
	@Override
	public Collection<NycTaxiTweetModel> mungee(Collection<NycTaxiTweetModel> src) {
		return src;
	}
	
	

	@Override
	public void save(Collection<NycTaxiTweetModel> data) {
		BulkInsertMongo insertIntoMongo = new BulkInsertMongo();
		insertIntoMongo.insertTweetMongo(data);
	}


}
