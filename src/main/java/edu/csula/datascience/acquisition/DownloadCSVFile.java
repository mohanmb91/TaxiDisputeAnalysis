package edu.csula.datascience.acquisition;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
public class DownloadCSVFile {
	
	public String downloadFile(String dirName, URL downloadURL,int id){
		 //UUID uuid = UUID.randomUUID();
		 String fileName = "filename"+ id + ".csv";
		 
		 //File dir = new File(dirName);
		 
		 /*comment 
		 code for mac*/
		 File dir = new File("C:\\Users\\FEBIELGIVA\\Documents\\downloadedCSVFiles");
		 if(!dir.exists()){
			 dir.mkdir();
		 }
		 
		 File fos = new File(dir,fileName);
		 String absolutePath = fos.getAbsolutePath();
		 try {
			FileUtils.copyURLToFile(downloadURL, fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return absolutePath;
	} 

}
