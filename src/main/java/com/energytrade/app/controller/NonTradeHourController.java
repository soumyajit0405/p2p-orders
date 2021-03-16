package com.energytrade.app.controller;

import java.util.Hashtable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.energytrade.app.dao.NonTradeHourDao;
import com.energytrade.app.model.NonTradeHour;
import com.energytrade.app.services.NonTradeHourService;
import com.energytrade.app.services.SellOrderService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
public class NonTradeHourController extends AbstractBaseController
{
    @Autowired
    private NonTradeHourService nonTradeHourService;
    
    @RequestMapping(value =REST+"createNonTradeHour" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> createNonTradeHour(@RequestBody HashMap<String,String> inputDetails) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", nonTradeHourService.createNonTradeHour(inputDetails));
    	return response;
    }
    
    @RequestMapping(value =REST+"getNonTradeHours/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getNonTradeHours(@PathVariable("userId") int userId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", nonTradeHourService.getNonTradeHours(userId));
    	return response;
    }
    
    @RequestMapping(value =REST+"editNonTradeHour/{nonTradeHourId}" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> editNonTradeHour(@RequestBody HashMap<String,String> inputDetails,@PathVariable("nonTradeHourId") int nonTradeHourId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", nonTradeHourService.editNonTradeHour(inputDetails, nonTradeHourId));
    	return response;
    }
    
    @RequestMapping(value =REST+"cancelNonTradeHour/{nonTradeHourId}" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> cancelNonTradeHour(@RequestBody HashMap<String,String> inputDetails,@PathVariable("nonTradeHourId") int nonTradeHourId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", nonTradeHourService.cancelNonTradeHour( nonTradeHourId, inputDetails.get("status"), inputDetails.get("reason")));
    	return response;
    }
}
