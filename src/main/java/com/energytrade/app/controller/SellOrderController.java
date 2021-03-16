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

import com.energytrade.app.services.SellOrderService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
public class SellOrderController extends AbstractBaseController
{
    @Autowired
    private SellOrderService sellOrderService;
    
    @RequestMapping(value =REST+"updateSellOrder/{sellOrderId}" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> updateSellOrder(@RequestBody HashMap<String,String> inputDetails, @PathVariable("sellOrderId") int sellOrderId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.updateSellOrder(sellOrderId, inputDetails.get("status")));
    	return response;
    }
    
    @RequestMapping(value =REST+"updateContract/{contractOrderId}" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> updateContractOrder(@RequestBody HashMap<String,String> inputDetails, @PathVariable("contractOrderId") int contractOrderId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.updateContractOrder(contractOrderId, inputDetails.get("status")));
    	return response;
    }
    
    @RequestMapping(value =REST+"editSellOrder/{sellOrderId}" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> editSellOrder(@RequestBody HashMap<String,String> inputDetails, @PathVariable("sellOrderId") int sellOrderId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.editSellOrder(inputDetails, sellOrderId));
    	return response;
    }
    
    @RequestMapping(value =REST+"createSellOrder" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> createSellOrder(@RequestBody HashMap<String,String> inputDetails) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.createSellOrder(inputDetails));
    	return response;
    }
    
    @RequestMapping(value =REST+"createMultipleSellOrder" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> createMultipleSellOrder(@RequestBody HashMap<String,List<HashMap<String,String>>> inputDetails) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.createMultipleSellOrder(inputDetails.get("listOfOrders")));
    	return response;
    }
    
    @RequestMapping(value =REST+"createContract" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> createContract(@RequestBody HashMap<String,String> inputDetails) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.createContract(inputDetails));
    	return response;
    }

    @RequestMapping(value =REST+"getAllOrdersByUser/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getSellOrdersByUser( @PathVariable("userId") int userId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.getSellOrdersByUser(userId));
    	return response;
    }
    
    @RequestMapping(value =REST+"getContractsByUser/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getContractsByUser( @PathVariable("userId") int userId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.getContractsByUser(userId));
    	return response;
    }
    
    @RequestMapping(value =REST+"getAllTrades/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getAllSellOrders(  @PathVariable("userId") int userId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.getAllSellOrders(userId));
    	return response;
    }
    
    @RequestMapping(value =REST+"getTradesByDate/{userId}/{startDate}/{endDate}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getAllSellOrders(  @PathVariable("userId") int userId, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.getAllSellOrders(userId,startDate,endDate));
    	return response;
    }
    
    @RequestMapping(value =REST+"getUserOrdersByAdmin/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getUserOrdersByAdmin(@PathVariable("userId") int userId ) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.getRecentTransactions(userId));
    	return response;
    }
    
    @RequestMapping(value =REST+"getAllContracts" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getAllContracts( ) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.getAllContracts());
    	return response;
    }
    
    @RequestMapping(value =REST+"searchBuyLeads" , method =  RequestMethod.POST , headers =  "Accept=application/json" )
    public HashMap<String,Object> searchBuyLeads(@RequestBody HashMap<String,String> inputDetails) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.searchBuyLeads(inputDetails));
    	return response;
    }
    
    @RequestMapping(value =REST+"getUSersByAdmin/{userId}" , method =  RequestMethod.GET , headers =  "Accept=application/json" )
    public HashMap<String,Object> getUSersByAdmin( @PathVariable("userId") int userId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	response.put("response", sellOrderService.getUSersByAdmin(userId));
    	return response;
    }
}
