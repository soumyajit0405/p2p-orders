package com.energytrade.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energytrade.app.dao.NonTradeHourDao;
import com.energytrade.app.dao.SellOrderDao;
import com.energytrade.app.model.NonTradeHour;


@Service("nonTradeHourService")
public class NonTradeHourService extends AbstractBaseService
{
    @Autowired
    private NonTradeHourDao nonTradeHourDao;
    
    public HashMap<String,Object> createNonTradeHour(HashMap<String,String> inputDetails) {
        return this.nonTradeHourDao.createNonTradeHour(inputDetails);
    }
    
    public HashMap<String,Object> getNonTradeHours(int userId) {
    	return this.nonTradeHourDao.getNonTradeHours(userId);
    }
    
    public HashMap<String,Object> editNonTradeHour(HashMap<String,String> inputDetails,int nonTradeHourId) {
        return this.nonTradeHourDao.editNonTradeHour(inputDetails, nonTradeHourId);
    }
    
    public HashMap<String,Object> cancelNonTradeHour(int nonTradeHourId, String status, String reason) {
        return this.nonTradeHourDao.cancelNonTradeHour(nonTradeHourId, status, reason);
    }
    
        
    
}