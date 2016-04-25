package edu.csula.datascience.acquisition;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bson.Document;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.model.soql.SoqlQuery;
import com.sun.jersey.api.client.ClientResponse;

import edu.csula.datascience.constants.ConstantKeys;
import jdk.nashorn.internal.parser.JSONParser;
import model.NycTaxiModel;
public class NycTaxiAPI implements Source<NycTaxiModel>{
    HttpClient client ;
	Collection<NycTaxiModel> allApi ;
	HttpGet request;
	HttpResponse response;
	BufferedReader rd;
	public NycTaxiAPI() throws ClientProtocolException, IOException{
		System.setProperty("https.protocols", "TLSv1.1");
		client = new DefaultHttpClient();
		allApi = new ArrayList<NycTaxiModel>();
		request =  new HttpGet("https://data.cityofnewyork.us/resource/2yzn-sicd.json?$$app_token="+ConstantKeys.api_Token_Nyc+"&$limit=500000");
		response = client.execute(request);
	}
	

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub

		
		try {
			rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			ObjectMapper mapper = new ObjectMapper(); 
			while ((line = rd.readLine()) != null) {
				return true;
			}
		} catch (UnsupportedOperationException e) {
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
		// TODO Auto-generated method stub
		
		//BufferedReader rd;
		try {
			//rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			ObjectMapper mapper = new ObjectMapper(); 
			while ((line = rd.readLine()) != null) {
			line = line.substring(1);
			System.out.println(line);
			NycTaxiModel taxiModel;
			taxiModel = mapper.readValue(line, NycTaxiModel.class);
			allApi.add(taxiModel);
		}} catch (UnsupportedOperationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		return allApi;
	}
}
