package com.energytrade.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.persistence.Query;

import com.energytrade.app.model.AllForecast;

public class CommonUtility {

	public static String generateOTPCode()
	{
		Random random = new Random();
		String id = String.format("%04d", random.nextInt(10000));
		return id;
	}
	
	public String generateDeliveryCode()
	{
		
		Random random = new Random();
		String id = String.format("%04d", random.nextInt(10000));
		return id;
	}
	
	

public String generateAuthCode(){


long count = (long)768;

Random rand=new Random();
//String paddedId=String.format("%07d%", rand.nextInt(10000000));
String authCode="SFT-"+String.format("%07d", (int)count)+"-"+String.format("%04d", rand.nextInt(10000));
return authCode;
}


	public  void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        System.out.println("is "+is.toString());
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	        	System.out.println(" file "+length);
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}

	
	public  String setIsCancellable(Timestamp startTime, String status) throws IOException {
	   
		if(status.equalsIgnoreCase("cancelled")) {
			return "N";
		}
		final Long GMT = (long) (((330 * 60)) * 1000);
		 Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		 currentTime.setTime(currentTime.getTime()+GMT);
		// currentTime.setTime(currentTime.getTime());
		 System.out.println("Timestamp "+currentTime);
  	    long milliseconds = startTime.getTime() - currentTime.getTime();
	    double seconds =  milliseconds / 1000;
	 
	    // calculate hours minutes and seconds
	    double hours = seconds / 3600;
	    double minutes = hours* 60;
	    if(minutes > 60) {
	    	return "Y";
	    }
	    else {
	    	return "N";
	    }
  	      
	    }
	
	public  String setIsEditable(Timestamp startTime, String status) throws IOException {
		
		if(status.equalsIgnoreCase("cancelled") || status.equalsIgnoreCase("Contracted")) {
			return "N";
		}
		final Long GMT = (long) (((330 * 60)) * 1000);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		 currentTime.setTime(currentTime.getTime()+GMT);
		// currentTime.setTime(currentTime.getTime());
		 System.out.println("Timestamp "+currentTime);
 	    long milliseconds = startTime.getTime() - currentTime.getTime();
	    double seconds =  milliseconds / 1000;
	 
	    // calculate hours minutes and seconds
	    double hours = seconds / 3600;
	    double minutes = hours* 60;
	    if(minutes > 60) {
	    	return "Y";
	    }
	    else {
	    	return "N";
	    }
 	      
	    }
	
	public  String checkUpcomingStatus(Timestamp startTime) throws IOException {
		   
		 Timestamp currentTime = new Timestamp(System.currentTimeMillis());
	    long milliseconds = startTime.getTime() - currentTime.getTime();
	    int seconds = (int) milliseconds / 1000;
	 
	    // calculate hours minutes and seconds
	    int hours = seconds / 3600;
	    int minutes = hours* 60;
	    if(minutes > 0) {
	    	return "Y";
	    }
	    else {
	    	return "N";
	    }
	      
	    }
	
	public  String setIsCancellableNonTrade(Timestamp startTime) throws IOException {
		   
		 Timestamp currentTime = new Timestamp(System.currentTimeMillis());
 	    long milliseconds = startTime.getTime() - currentTime.getTime();
	    int seconds = (int) milliseconds / 1000;
	 
	    // calculate hours minutes and seconds
	    int hours = seconds / 3600;
	    int minutes = hours* 60;
	    if(minutes > 0) {
	    	return "Y";
	    }
	    else {
	    	return "N";
	    }
 	      
	    }
	
	public  String setIsEditableNonTrade(Timestamp startTime) throws IOException {
		   
		 Timestamp currentTime = new Timestamp(System.currentTimeMillis());
	    long milliseconds = startTime.getTime() - currentTime.getTime();
	    int seconds = (int) milliseconds / 1000;
	 
	    // calculate hours minutes and seconds
	    int hours = seconds / 3600;
	    int minutes = hours* 60;
	    if(minutes > 0) {
	    	return "Y";
	    }
	    else {
	    	return "N";
	    }
	      
	    }
	
	public  int checkDates(Hashtable<String,String> inputDetails) throws IOException {
		   
	    try {
	      if(inputDetails.get("startDate")== null || inputDetails.get("endDate")== null)
	      {
	    	  return -3;
	      }
	      else
	      {
	    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	          Date startDate = null,endDate = null;
			try {
				startDate = sdf.parse(inputDetails.get("startDate"));
				
				  endDate = sdf.parse(inputDetails.get("endDate"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         

	       
	          if (startDate.compareTo(endDate) > 0) {
	            return -2;
	      }
	          return 1;
	    	
	    }
	    }
	    finally
	    {
	    	
	    }
	      
	    }
	

	public  int checkExtension(String fileName) throws IOException {
	   
	    try {
	    	System.out.println("File Name "+fileName);
	     
	    	String [] extension=fileName.split("\\.");
	    	System.out.println("File Name "+extension[0]);
	    	System.out.println("extension"+extension[1]);
	    	if(! extension[1].equalsIgnoreCase("jpeg") &&  !extension[1].equalsIgnoreCase("jpg") && !extension[1].equalsIgnoreCase("png"))
	    		
	    	{
	    	return 2;	
	    	}
	    	else
	    	{
	    		return 1;
	    	}
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	   return -1; 	
	    }
	      
	    }
	 public  boolean isEmailValid(String email)
	    {
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
	                            "[a-zA-Z0-9_+&*-]+)*@" +
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
	                            "A-Z]{2,7}$";
	                             
	        Pattern pat = Pattern.compile(emailRegex);
	        if (email == null)
	            return false;
	        return pat.matcher(email).matches();
	    }
	
	 public  boolean isPasswordValid(String password)
	    {
	        
	        if (password.length() < 6)
	            return false;
	        
	        return true;
	    }
	public static void main(String args[])
	{
		CommonUtility cm=new CommonUtility();
		System.out.println(cm.generateAuthCode());
	}

	public List<HashMap<String,Object>> convertData(HashMap<String, Object> inputDetails) throws ParseException {
		
		String foreCastDate=(String)inputDetails.get("forecast_date");
		 DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	        //String todate= df.format(today);
	    //    String todate= (String) listOfData.get("forecast_date");
	     Date   todate= df.parse(foreCastDate);
	     List<HashMap<String,Object>> listOfFormattedData= new ArrayList<HashMap<String,Object>>();
	     Date orgtodate= todate;
		List<HashMap<String,String>> listOfData = (List<HashMap<String,String>>) inputDetails.get("data");
		for(int i =0;i<listOfData.size();i++) {
			todate = orgtodate;
			HashMap<String,String> foreCastData = listOfData.get(i);
			for(int j =1; j<=7;j++) {
				HashMap<String,Object> formattedData = new HashMap<String, Object>();
				if(foreCastData.get("PpvD"+j) == null || foreCastData.get("PpvD"+j).equalsIgnoreCase("")) {
					formattedData.put("EV-POWER","-1");	
				}
				else {
					formattedData.put("EV-POWER", foreCastData.get("PpvD"+j));
				}
				if(foreCastData.get("PpvD"+j) == null || foreCastData.get("PpvD"+j).equalsIgnoreCase("")) {
					formattedData.put("SOLAR-POWER","-1");	
				}
				else {
					formattedData.put("SOLAR-POWER", foreCastData.get("PpvD"+j));
				}
				if(foreCastData.get("PpvD"+j) == null || foreCastData.get("PpvD"+j).equalsIgnoreCase("")) {
					formattedData.put("GEN-POWER","-1");	
				}
				else {
					formattedData.put("GEN-POWER", foreCastData.get("PpvD"+j));
				}
				if(foreCastData.get("PlD"+j) == null || foreCastData.get("PpvD"+j).equalsIgnoreCase("")) {
					formattedData.put("POWER-LOAD","-1");	
				}
				else {
					formattedData.put("POWER-LOAD", foreCastData.get("PlD"+j));
				}
				
				formattedData.put("date",todate);
				formattedData.put("slot",foreCastData.get("slot"));
    			todate = new Date(todate.getTime() + (1000 * 60 * 60 * 24));
    			listOfFormattedData.add(formattedData);
        		
			}
		}
		return listOfFormattedData;
	}

}
