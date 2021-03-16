package com.energytrade.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energytrade.app.dao.SellOrderDao;


@Service("orderService")
public class SellOrderService extends AbstractBaseService
{
    @Autowired
    private SellOrderDao sellorderdao;
    
    public HashMap<String,Object> updateSellOrder(int sellOrderId, String status) {
        return this.sellorderdao.updateSellOrder(sellOrderId, status,"SELLER");
    }
        
    public HashMap<String,Object> updateContractOrder(int contractOrderId, String status) {
        return this.sellorderdao.updateContractOrder(contractOrderId, status);
    }
    
    public HashMap<String,Object> editSellOrder(HashMap<String,String> inputDetails, int sellOrderId) {
        return this.sellorderdao.editSellOrder(inputDetails, sellOrderId);
    }
    
    public HashMap<String,Object> createSellOrder(HashMap<String,String> inputDetails) {
        return this.sellorderdao.createSellOrder(inputDetails);
    }
    
    public HashMap<String,Object> createContract(HashMap<String,String> inputDetails) {
        return this.sellorderdao.createContract(inputDetails);
    }
    
    public HashMap<String,Object> searchBuyLeads(HashMap<String,String> inputDetails) {
        return this.sellorderdao.searchBuyLeads(inputDetails);
    }
    
    public HashMap<String,Object> getSellOrdersByUser(int userId) {
        return this.sellorderdao.getSellOrdersByUser(userId);
    }
    
    public HashMap<String,Object> getContractsByUser(int userId) {
        return this.sellorderdao.getContractsByUser(userId);
    }
    
    public HashMap<String,Object> getAllSellOrders(int userId, String startDate, String endDate) {
        return this.sellorderdao.getAllSellOrdersByDate(userId, startDate, endDate);
    }
    
    public HashMap<String,Object> getAllSellOrders(int userId) {
        return this.sellorderdao.getAllSellOrders(userId);
    }
    
    public HashMap<String,Object> getAllContracts() {
        return this.sellorderdao.getAllContracts();
    }
    
    public HashMap<String,Object> getRecentTransactions(int orderId) {
    	return this.sellorderdao.getRecentTransactions(orderId);
    }
    
    public HashMap<String,Object> getUSersByAdmin(int userId) {	
    	return this.sellorderdao.getUSersByAdmin(userId);
    	
    }
    
    public HashMap<String,Object> createMultipleSellOrder(List<HashMap<String,String>> inputDetails) {
    	return this.sellorderdao.createMultipleSellOrder(inputDetails);
    }
    
}