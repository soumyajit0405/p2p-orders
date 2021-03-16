package com.energytrade.app.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.dto.NonTradeHourDto;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.LocalityPl;
import com.energytrade.app.model.NonTradeHour;
import com.energytrade.app.model.NonTradehourStatusPl;
import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.util.CommonUtility;
import com.energytrade.app.util.CustomMessages;
import com.energytrade.app.util.ValidationUtil;


@Transactional
@Repository
public class NonTradeHourDao extends AbstractBaseDao
{
	@Autowired
    NonTradeHourRepository nontradehourrepo;
	
	@Autowired
	SellOrderDao sellorderdao= new SellOrderDao();
	
	CommonUtility cm= new CommonUtility();
	
	
    public HashMap<String,Object> createNonTradeHour(HashMap<String,String> inputDetails) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	
    try {
    	
    	LocalityPl locality = nontradehourrepo.getLocality(Integer.parseInt(inputDetails.get("localityId")));
    	AllUser alluser = nontradehourrepo.getUserById(Integer.parseInt(inputDetails.get("userId")));
    	NonTradehourStatusPl nontradehourstatus = nontradehourrepo.getNonTradeStatus("Created");
    	Timestamp startTs= Timestamp.valueOf(inputDetails.get("startTime"));
    	Timestamp endTs= Timestamp.valueOf(inputDetails.get("endTime"));
    	NonTradeHour nontradehour = new NonTradeHour();
    	nontradehour.setAllUser1(alluser);
    	nontradehour.setLocation(inputDetails.get("location"));
    	nontradehour.setNonTradeReason(inputDetails.get("nonTradeReason"));
    	nontradehour.setStartTs(startTs);
    	nontradehour.setEndTs(endTs);
    	nontradehour.setLocality(locality);
    	nontradehour.setNonTradehourStatusPl(nontradehourstatus);
    	
    	// Cancel Logic Proceeds - TO DO
    	
    	nontradehourrepo.save(nontradehour);
    	List<Integer> listSellOrders=getAllSellOrders(startTs,endTs,locality.getLocalityId());
    	//List<Integer> listSellOrders=getAllSellOrders(startTs,endTs);
    	int iResponse=cancelSellOrders(listSellOrders);
    	if(iResponse == -1) {
    		throw new Exception();
    	}
    		response.put("message",CustomMessages.getCustomMessages("SUC"));
    		response.put("key","200");
    	
    }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
            response.put("key","500");
           
        }
        return response;
    }
    
 public HashMap<String,Object> editNonTradeHour(HashMap<String,String> inputDetails, int nonTradeHourId) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	
    try {
    	LocalityPl locality = nontradehourrepo.getLocality(Integer.parseInt(inputDetails.get("localityId")));
    	
    	Timestamp startTs= Timestamp.valueOf(inputDetails.get("startTime"));
    	Timestamp endTs= Timestamp.valueOf(inputDetails.get("endTime"));
    	
    	// Cancel Logic Proceeds - TO DO
    	
    	nontradehourrepo.editSellOrder(inputDetails.get("nonTradeReason"), startTs, endTs, nonTradeHourId, locality.getLocalityId());
    	List<Integer> listSellOrders=getAllSellOrders(startTs,endTs,locality.getLocalityId());
    	int iResponse=cancelSellOrders(listSellOrders);
    	if(iResponse == -1) {
    		throw new Exception();
    	}
    		response.put("message",CustomMessages.getCustomMessages("SUC"));
    		response.put("key","200");
    	
    }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
            response.put("key","500");
           
        }
        return response;
    }
   
    
    
public HashMap<String,Object> getNonTradeHours(int userId) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	List<NonTradeHourDto> nonTradeHourDtoList= new ArrayList<NonTradeHourDto>();
    try {
    	
    	List<NonTradeHour> nonTradeHourlist = nontradehourrepo.getAllNonTradeHour(userId);
    	for(int i=0;i<nonTradeHourlist.size() ;i++) {
    		NonTradeHourDto nontradehourdto =new NonTradeHourDto();
    		nontradehourdto.setNonTradeHourId(nonTradeHourlist.get(i).getNonTradeHourId());
    		nontradehourdto.setStartTime(nonTradeHourlist.get(i).getStartTs().toString());
    		nontradehourdto.setEndTime(nonTradeHourlist.get(i).getEndTs().toString());
    		nontradehourdto.setNonTradeReason(nonTradeHourlist.get(i).getNonTradeReason());
    		nontradehourdto.setLocation(nonTradeHourlist.get(i).getLocation());
    		nontradehourdto.setStatus(nonTradeHourlist.get(i).getNonTradehourStatusPl().getNonTradehourStatusName());
    		nontradehourdto.setLocalityId(nonTradeHourlist.get(i).getLocality().getLocalityId());
    		nontradehourdto.setLocationName(nonTradeHourlist.get(i).getLocality().getLocalityName());
    		nontradehourdto.setIsCancellable(cm.setIsCancellable(nonTradeHourlist.get(i).getStartTs(),nonTradeHourlist.get(i).getNonTradehourStatusPl().getNonTradehourStatusName()));
    		nontradehourdto.setIsEditable(cm.setIsEditable(nonTradeHourlist.get(i).getStartTs(),nonTradeHourlist.get(i).getNonTradehourStatusPl().getNonTradehourStatusName()));
    		nonTradeHourDtoList.add(nontradehourdto);
    		
    	    long milliseconds = nonTradeHourlist.get(i).getEndTs().getTime() - nonTradeHourlist.get(i).getStartTs().getTime();
    	    int seconds = (int) milliseconds / 1000;
    	 
    	    // calculate hours minutes and seconds
    	    int hours = seconds / 3600;
    	    int minutes = hours* 60;
    	    seconds = (seconds % 3600) % 60;
    	    nontradehourdto.setDuration(minutes);

    	}
    	
    	// Cancel Logic Proceeds - TO DO
    	
    		response.put("data",nonTradeHourDtoList);
    		response.put("message",CustomMessages.getCustomMessages("SUC"));
    		response.put("key","200");
    	
    }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
            response.put("key","500");
           
        }
        return response;
    }

		public List<Integer> getAllSellOrders(Timestamp startDate, Timestamp endDate, int localityId) {
	
			List<Integer> listSellOrders= null;
		try {
			listSellOrders=nontradehourrepo.getSellOrders(startDate, endDate, localityId);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return listSellOrders;
	}
		
		public List<Integer> getAllSellOrders(Timestamp startDate, Timestamp endDate) throws ParseException {
			
			List<Integer> listSellOrders= null;
		
		try {
			listSellOrders=nontradehourrepo.getSellOrders(startDate.toString(), endDate.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return listSellOrders;
	}
		
	public int cancelSellOrders(List<Integer> listSellOrders) {
			
			
		try {
				if(listSellOrders.size() != 0)
		    	{
		    		for(int i=0;i<listSellOrders.size();i++) 
		    		{
		    			sellorderdao.updateSellOrder(listSellOrders.get(i), "CANCEL","AGG");
		    		}
		    	}
				return 1;
		    	
			}
			catch(Exception e) {
				e.printStackTrace();
				return -1;
				
			}
		}
	
	public HashMap<String,Object> cancelNonTradeHour(int nonTradeHourId, String status, String reason) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	NonTradehourStatusPl nontradehourstatus= null;
    try {
    	
    	if(status.equalsIgnoreCase("CANCEL")) {
    		nontradehourstatus=nontradehourrepo.getNonTradeStatus("Cancelled");
    	}
    	
    	nontradehourrepo.updateNonTradeHourStatus(nontradehourstatus.getNonTradehourStatusId(), nonTradeHourId, reason);
    		response.put("message",CustomMessages.getCustomMessages("SUC"));
    		response.put("key","200");
    	
    }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
            response.put("key","500");
           
        }
        return response;
    }

}	

