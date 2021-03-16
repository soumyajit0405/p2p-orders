package com.energytrade.app.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.model.AllBlockchainOrder;
import com.energytrade.app.model.AllBlockchainTransaction;
import com.energytrade.app.model.AllBlockchainTransactionsStatusPl;
import com.energytrade.app.model.UserBlockchainKey;



@Transactional
@Repository
public class BlockchainDao extends AbstractBaseDao
{
	@Autowired
    AllBlockchainOrderRepository allbcorrepo;
	
	@Autowired
	AllBlockchainTransactionRepository allbcrepo;
	
    public AllBlockchainOrder createBlockchainOrder(String batchId, String blockchainOrderId, int orderId) {
       
    	AllBlockchainOrder abco= new AllBlockchainOrder();
        try {
        	Timestamp ts = new Timestamp(System.currentTimeMillis());
        	int count= allbcorrepo.getBcOrderCount()+1;
       
       abco.setAllBlockchainOrdersId(count);
     //  abco.setBatchId(batchId);
      abco.setEffectiveDate(ts);
      abco.setGeneralOrderId(orderId);
      abco.setOrderType("SELL_ORDER");
      abco.setOrderId(blockchainOrderId);
      allbcorrepo.saveAndFlush(abco);
      return abco;
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
           
        }
        return abco;
    }
    
    public AllBlockchainOrder preCreateBlockchainOrder( int orderId) {
        
    	AllBlockchainOrder abco= new AllBlockchainOrder();
        try {
        	Timestamp ts = new Timestamp(System.currentTimeMillis());
        	int count= allbcorrepo.getBcOrderCount()+1;
       
       abco.setAllBlockchainOrdersId(count);
     //  abco.setBatchId(batchId);
      abco.setEffectiveDate(ts);
      abco.setGeneralOrderId(orderId);
      abco.setOrderType("SELL_ORDER");
    //  abco.setOrderId(blockchainOrderId);
      allbcorrepo.saveAndFlush(abco);
      return abco;
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
           
        }
        return abco;
    }
    
    public void addBlockchainTransaction(String blockchainTxId,String txType, AllBlockchainOrder allBlockchainOrder) {
        
        try {
       AllBlockchainTransaction albctx= new AllBlockchainTransaction();
       albctx.setBlockChainTrxId(blockchainTxId);
       albctx.setAllBlockchainOrder(allBlockchainOrder);
       albctx.setTransactionType(txType);
       allbcrepo.save(albctx);
       
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
           
        }
    }
    
public void updateBlockchainTransaction(int bcTxId, String blockchainTxId,String txType,int status ) {
        
        try {
       allbcrepo.updateBlockChainTx(blockchainTxId, txType, bcTxId,status);
       
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
           
        }
    }

public void updateBlockchainOrder(String bcOrderId,int orderId ) {
    
    try {
   allbcorrepo.updateBlockChainOrder(bcOrderId, orderId);
   
    }
    catch (Exception e) {
        System.out.println("Error in checkExistence" + e.getMessage());
        e.printStackTrace();
       
    }
}
    
public AllBlockchainTransaction preCreateBlockchainTransaction(AllBlockchainOrder bcOrder,String txType) {
       
	AllBlockchainTransaction albctx= new AllBlockchainTransaction();
        try {
        int count = allbcrepo.getTxCount()+1;
        AllBlockchainTransactionsStatusPl status =allbcrepo.getStatus("Pending");
       
       albctx.setAllBlockchainTrxId(count);
       albctx.setStatus(status);
       albctx.setCreatedTs(new Timestamp(System.currentTimeMillis()));
       albctx.setAllBlockchainOrder(bcOrder);
       albctx.setTransactionType(txType);
       // albctx.setTransactionType(txType);
       allbcrepo.saveAndFlush(albctx);
       
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
           
        }
        return albctx;
    }


}