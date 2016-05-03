package model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


public class NycTaxiTweetModel {
	
	String tweet;
	//true if positive and false if negative
	boolean positiveNegativeIndicator;
	String words ;
	String tweetDate;
	String associatedLinks ;
	
	
	public NycTaxiTweetModel(String tweet, boolean positiveNegativeIndicator, List<String> words, String tweetDate,
			List<String> associatedLinks) {
		super();
		this.tweet = tweet;
		this.positiveNegativeIndicator = positiveNegativeIndicator;
		setWords(words);
		this.tweetDate = tweetDate;
		setAssociatedLinks(associatedLinks);
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public boolean isPositiveNegativeIndicator() {
		return positiveNegativeIndicator;
	}
	public void setPositiveNegativeIndicator(boolean positiveNegativeIndicator) {
		this.positiveNegativeIndicator = positiveNegativeIndicator;
	}
	public String getWords() {
		return words;
	}
	public void setWords(List<String> wordsList) {
		Gson gson = new Gson();
		String words = gson.toJson(wordsList);
		this.words = words;
	}
	public String getTweetDate() {
		return tweetDate;
	}
	public void setTweetDate(String tweetDate) {
		this.tweetDate = tweetDate;
	}
	public String getAssociatedLinks() {
		return associatedLinks;
	}
	public void setAssociatedLinks(List<String> associatedLinksList) {
		Gson gson = new Gson();
		String associatedLinks = gson.toJson(associatedLinksList);
		this.associatedLinks = associatedLinks;
	}
	
	
}
