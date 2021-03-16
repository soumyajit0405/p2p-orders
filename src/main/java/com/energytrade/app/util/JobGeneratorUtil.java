package com.energytrade.app.util;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.energytrade.app.dao.BlockchainDao;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllUser;


public class JobGeneratorUtil implements JobGenerator {
 
		
	    @Override
		public void triggerBlockChain(String type, String url, JSONObject data, int val, AllSellOrder order, int blockChainTxId, BlockchainDao bcdao) throws ClassNotFoundException, SQLException {
	    	String status = "";
	    	try {
	    		HttpConnectorHelper httpconnectorhelper = new HttpConnectorHelper();
	    	
	    	HashMap<String,String> responseFrombcnetwork = httpconnectorhelper.sendPost(url,data, val);
	     	   //HashMap<String,String> responseAfterParse = cm.parseInput(responseFrombcnetwork);
	    	if (type.equalsIgnoreCase("CREATE_ORDER")) {
	    		status ="ORDER_CREATED";
	     	   if(responseFrombcnetwork.get("Status").equalsIgnoreCase("Order Created")) {
	     	   bcdao.updateBlockchainOrder(responseFrombcnetwork.get("OrderId"), order.getSellOrderId());
	     	   bcdao.updateBlockchainTransaction(blockChainTxId, responseFrombcnetwork.get("Tx"), "ORDER_CREATED",2);
	     	   }
	    	} else if (type.equalsIgnoreCase("UPDATE_ORDER")) {
	    		status ="ORDER_UPDATED";
		     	   if(responseFrombcnetwork.get("Status").equalsIgnoreCase("Order Updated")) {
			     	  // bcdao.updateBlockchainOrder(responseFrombcnetwork.get("OrderId"), order.getSellOrderId());
			     	   bcdao.updateBlockchainTransaction(blockChainTxId, responseFrombcnetwork.get("Tx"), "ORDER_UPDATED",2);
			     	   }
			} else if (type.equalsIgnoreCase("CANCEL_ORDER")) {
				status ="ORDER_CANCELLED";
		     	   if(responseFrombcnetwork.get("status").toString().equalsIgnoreCase("Cancelled")) {
			     	  // bcdao.updateBlockchainOrder(responseFrombcnetwork.get("OrderId"), order.getSellOrderId());
			     	   bcdao.updateBlockchainTransaction(blockChainTxId, responseFrombcnetwork.get("Tx"), "ORDER_CANCELLED",2);
			     	   }
			 } else if (type.equalsIgnoreCase("CREATE_CONTRACT")) {
					status ="ORDER_ACCEPTED";
			     	   if(responseFrombcnetwork.get("Status").toString().equalsIgnoreCase("Order Accepted")) {
				     	  // bcdao.updateBlockchainOrder(responseFrombcnetwork.get("OrderId"), order.getSellOrderId());
				     	   bcdao.updateBlockchainTransaction(blockChainTxId, responseFrombcnetwork.get("Tx"), "ORDER_ACCEPTED",2);
				   }
			} else if (type.equalsIgnoreCase("CANCEL_CONTRACT")) {
				status ="CONTRACT_CANCELLED";
		     	   if(responseFrombcnetwork.get("Status").toString().equalsIgnoreCase("Contract Cancelled")) {
			     	  // bcdao.updateBlockchainOrder(responseFrombcnetwork.get("OrderId"), order.getSellOrderId());
			     	   bcdao.updateBlockchainTransaction(blockChainTxId, responseFrombcnetwork.get("Tx"), "CONTRACT_CANCELLED",2);
			  }
		}
	    	}
	    	catch(Exception e) {
	    		 bcdao.updateBlockchainTransaction(blockChainTxId, "", status,3);
	    		e.printStackTrace();
	        	}
		}
	} 

