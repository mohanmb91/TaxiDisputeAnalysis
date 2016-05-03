package edu.csula.datascience.acquisition;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.csula.datascience.constants.ConstantKeys;
import model.NycTaxiTweetModel;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class NycTwitterSource implements Source<NycTaxiTweetModel>{
	
	static long minId ;
	static List<NycTaxiTweetModel> nycTaxiTweet = new ArrayList<NycTaxiTweetModel>();
	static String positiveWordFile ;
	static String negativeWordFile ;
	
	public NycTwitterSource(){
		minId = Long.MAX_VALUE;
		positiveWordFile ="C:\\Users\\FEBIELGIVA\\Documents\\sentimentalWords\\positive-words.txt";
		negativeWordFile ="C:\\Users\\FEBIELGIVA\\Documents\\sentimentalWords\\negative-words.txt";
	}
	
	
	@Override
	public boolean hasNext() {
		return false;
	}

	
	@Override
	public Collection<NycTaxiTweetModel> next() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(ConstantKeys.consumer_Key)
		.setOAuthConsumerSecret(ConstantKeys.consumer_Secret_Key)
		.setOAuthAccessToken(ConstantKeys.api_Token_Key)
		.setOAuthAccessTokenSecret(ConstantKeys.api_Token_Secret_Key);

		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter4j.Twitter twitter = tf.getInstance();

		Set<String> negativeWordsSet = new HashSet<String>();
		Set<String> positiveWordsSet = new HashSet<String>();

		try {
			negativeWordsSet = fetchTheNegativeWords();
			positiveWordsSet = fetchThePositiveWords();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		try {
			Query query = new Query("@nyctaxi");
			QueryResult result;
			result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) {
				System.out.println();
				System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText() );
				analysisTweets(positiveWordsSet,negativeWordsSet,tweet.getText(),tweet.getCreatedAt().toString());
				System.out.println(tweet.getId());
				System.out.println(tweet.getCreatedAt());
				
			}
		}
		catch (TwitterException | IOException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
		
		System.out.println("Displays the content in model");
		for (NycTaxiTweetModel eachTweet : nycTaxiTweet) {
			System.out.println(eachTweet.getTweet()+"  "+eachTweet.getAssociatedLinks()+" "+eachTweet.getWords()+" "+eachTweet.isPositiveNegativeIndicator());
		}

		System.out.println("done");
		return nycTaxiTweet;
	}
	
	
	
	private static Set<String> fetchTheNegativeWords() throws FileNotFoundException, IOException {
		Set<String> set = new HashSet<String>(); 
		try(BufferedReader br = new BufferedReader(new FileReader(negativeWordFile))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				set.add(line);

			}
		}
		return set;
	}

	private static Set<String> fetchThePositiveWords() throws FileNotFoundException, IOException {

		//	Map<String,String> wordsMap = new HashMap<String,String>();
		Set<String> set = new HashSet<String>(); 
		try(BufferedReader br = new BufferedReader(new FileReader(positiveWordFile))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				set.add(line);

			}
		}
		return set;

	}
	private static void analysisTweets(Set<String> positiveWordsSet, Set<String> negativeWordsSet, String tweet,String tweetDate) throws FileNotFoundException, IOException {

		String eachWordArray[] = tweet.split(" ");
		Set<String> wordsArrayPositive = new HashSet<String>();
		Set<String> wordsArrayNegative = new HashSet<String>();
		Set<String> associatedLinks = new HashSet<String>();
		for (String eachWord : eachWordArray) {
			
			if((eachWord.startsWith("http:")) || (eachWord.startsWith("https:")) || (eachWord.startsWith("www:"))){
//				ProperURL url = new ProperURL();
//				associatedLinks.add(url.removeUrl(eachWord).toString());
				associatedLinks.add(eachWord);
				System.out.println(eachWord);
			}
			
			if(positiveWordsSet.contains(eachWord.toLowerCase())){
				System.out.println(eachWord);
				wordsArrayPositive.add(eachWord.toLowerCase());
			}
			else
				if(negativeWordsSet.contains(eachWord.toLowerCase())){
					System.out.println(eachWord);
					wordsArrayNegative.add(eachWord.toLowerCase());
			}
		}
		
		
		if(wordsArrayNegative.size() > wordsArrayPositive.size()){
			nycTaxiTweet.add(new NycTaxiTweetModel(tweet, false, new ArrayList<String>(wordsArrayNegative), tweetDate, new ArrayList<String>(associatedLinks)));
		}
		else
			if(wordsArrayNegative.size() < wordsArrayPositive.size()){
				nycTaxiTweet.add(new NycTaxiTweetModel(tweet, true, new ArrayList<String>(wordsArrayPositive), tweetDate, new ArrayList<String>(associatedLinks)));			
		}
		else		
			if((wordsArrayNegative.size() == wordsArrayPositive.size()) &&(wordsArrayNegative.size() > 1)){
				nycTaxiTweet.add(new NycTaxiTweetModel(tweet, false, new ArrayList<String>(wordsArrayNegative), tweetDate, new ArrayList<String>(associatedLinks)));

			}

	}

}

