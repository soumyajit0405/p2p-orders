package com.energytrade.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energytrade.app.dao.DRDao;
import com.energytrade.app.dao.SellOrderDao;


@Service("DRService")
public class DRService extends AbstractBaseService
{
    @Autowired
    private DRDao drdao;
    
    public void createEventSet(String filePath, byte [] imageByte, String location, int userId) {
         this.drdao.createEventSet(filePath, imageByte, location, userId);
    }
          
}