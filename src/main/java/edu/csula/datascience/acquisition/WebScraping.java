package edu.csula.datascience.acquisition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.NycTaxiModel;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
public class WebScraping {

	public void crawler(String startUrl, String dirName) throws MalformedURLException, IOException {

		//get the possible url form the page and parse it 
		List<URL> allPossibleUrls =  getAllUrlFromRootUrl(startUrl);
		String[] words = {"yellow_tripdata_2014-01","green_tripdata_2014-01","green_tripdata_2014-02", "green_tripdata_2014-03", "green_tripdata_2014-04"};  
		DownloadCSVFile downloadFile = new DownloadCSVFile();
		List<String> absolutePathList =  new ArrayList<>();
		int id = 2 ;
		for (URL eachURL : allPossibleUrls) {
			String shortWords[] = eachURL.toString().split("/");
			String shortWord = shortWords[shortWords.length -1];
			shortWord = shortWord.replace(".csv", "");
			if(Arrays.asList(words).contains(shortWord)){
				id++;
				System.out.println("download start");
				String absolutePath = downloadFile.downloadFile(dirName, eachURL,id);
				System.out.println("download end");
				System.out.println(eachURL);
				absolutePathList.add(absolutePath);
			}	
		}

	}

	private List<URL> getAllUrlFromRootUrl(String startUrl) {
		Document doc = null;
		try {
			doc = Jsoup.connect(startUrl).ignoreContentType(true).ignoreHttpErrors(true).timeout(10*1000).get();
		}
		catch (UnknownHostException e) {
			System.err.println("Unknown host");
			e.printStackTrace(); // I'd rather (re)throw it though.
		}
		catch (SocketTimeoutException e) {
			System.err.println("IP cannot be reached");
			e.printStackTrace(); // I'd rather (re)throw it though.
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		Elements elements = doc.select("a");
		List<URL> filteredElements = new ArrayList<URL>();
		LinkedHashSet<URL> outGoingLinksSet =  new LinkedHashSet<URL>();
		ProperURL obj = new ProperURL();
		for (Element element : elements) {
			String eachOutgoingURL = element.absUrl("href");
			URL aURL;
			try {
				if((eachOutgoingURL != null) && (eachOutgoingURL != "")){
					aURL = new URL(eachOutgoingURL);
					if((("http".equals(aURL.getProtocol())) || ("https".equals(aURL.getProtocol())))
							&& (aURL.getPath().endsWith(".csv"))){
						outGoingLinksSet.add(obj.removeUrl(element.absUrl("href")));
					}
				}	
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
		for (URL eachSetElement : outGoingLinksSet) {
			filteredElements.add(eachSetElement);
		}	
		return filteredElements;
	}


}
