package edu.csula.datascience.acquisition;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import edu.csula.datascience.constants.*;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class NycTaxiTwitter {
	static long minId = Long.MAX_VALUE;
public static void main(String[] args) throws TwitterException{
	

	ConfigurationBuilder cb = new ConfigurationBuilder();
	cb.setDebugEnabled(true)
		.setOAuthConsumerKey(ConstantKeys.consumer_Key)
		.setOAuthConsumerSecret(ConstantKeys.consumer_Secret_Key)
		.setOAuthAccessToken(ConstantKeys.api_Token_Key)
		.setOAuthAccessTokenSecret(ConstantKeys.api_Token_Secret_Key);
	
	TwitterFactory tf = new TwitterFactory(cb.build());
	twitter4j.Twitter twitter = tf.getInstance();
	
    try {
        Query query = new Query("@nyctaxi");
        QueryResult result;
        result = twitter.search(query);
        List<Status> tweets = result.getTweets();
        for (Status tweet : tweets) {
            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        }
    }
    catch (TwitterException te) {
        te.printStackTrace();
        System.out.println("Failed to search tweets: " + te.getMessage());
        System.exit(-1);
    }
     
     System.out.println("done");
     
}

}
