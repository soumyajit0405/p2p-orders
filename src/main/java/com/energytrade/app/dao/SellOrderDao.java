 package com.energytrade.app.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.AppStartupRunner;
import com.energytrade.app.dto.AllContractDto;
import com.energytrade.app.dto.AllSellOrderDto;
import com.energytrade.app.dto.AllUserByAdminDto;
import com.energytrade.app.dto.AllUserDto;
import com.energytrade.app.dto.SearchOrdersDto;
import com.energytrade.app.model.AllBlockchainOrder;
import com.energytrade.app.model.AllBlockchainTransaction;
import com.energytrade.app.model.AllContract;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.GeneralConfig;
import com.energytrade.app.model.NonTradehourStatusPl;
import com.energytrade.app.model.NotificationRequestDto;
import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.model.UserBlockchainKey;
import com.energytrade.app.model.UserDevice;
import com.energytrade.app.util.ApplicationConstant;
import com.energytrade.app.util.CommonUtility;
import com.energytrade.app.util.CustomMessages;
import com.energytrade.app.util.HttpConnectorHelper;
import com.energytrade.app.util.JobGenerator;
import com.energytrade.app.util.JobGeneratorImpl;
import com.energytrade.app.util.JobGeneratorUtil;
import com.energytrade.app.util.NotificationGenerator;
import com.energytrade.app.util.NotificationGeneratorImpl;
import com.energytrade.app.util.NotificationGeneratorUtil;
import com.energytrade.app.util.PushHelper;
import com.energytrade.app.util.ValidationUtil;



@Transactional
@Repository
public class SellOrderDao extends AbstractBaseDao
{
	@Autowired
    AllSellOrderRepository allsellorderrepo;
	
	@Autowired
    AllContractRepository allcontractrepo;
	
	@Autowired
    NonTradeHourRepository nontradehourrepo;

	@Autowired
    private PushHelper pushHelper;
	
	@Autowired
	HttpConnectorHelper httpconnectorhelper;
	
	@Autowired
	BlockchainDao bcdao;
	
	@Autowired
	AllBlockchainOrderRepository allbcorderrepo;
	
	@Value( "${search.timeFrame}" )
	private String searchTimeFrame;
	
	
	BigDecimal zero = new BigDecimal("0");
	CommonUtility cm= new CommonUtility();
	
	/*
	 * public HashMap<String,Object> getSellOrdersByUser(int userId) {
	 * 
	 * HashMap<String,Object> response = new HashMap<String, Object>(); try {
	 * 
	 * List<AllSellOrderDto> listDto= new ArrayList<AllSellOrderDto>();
	 * List<AllSellOrder> allSellList=
	 * allsellorderrepo.getAllSellOrdersByUser(userId); if(allSellList.size() > 0) {
	 * for(int i=0;i<allSellList.size();i++) { AllSellOrderDto allsellorderdto = new
	 * AllSellOrderDto();
	 * allsellorderdto.setDeviceTypeName(allSellList.get(i).getDevicePl().
	 * getDeviceTypeName());
	 * allsellorderdto.setOrderStatus(allSellList.get(i).getOrderStatusPl().
	 * getOrderStatusName());
	 * allsellorderdto.setPowerToSell(allSellList.get(i).getPowerToSell());
	 * allsellorderdto.setRatePerUnit(allSellList.get(i).getRatePerUnit());
	 * allsellorderdto.setSellerId(allSellList.get(i).getAllUser().getUserId());
	 * allsellorderdto.setSellOrderId(allSellList.get(i).getSellOrderId());
	 * allsellorderdto.setTotalAmount(allSellList.get(i).getTotalAmount());
	 * allsellorderdto.setTransferEndTs(allSellList.get(i).getTransferEndTs().
	 * toString());
	 * allsellorderdto.setTransferStartTs(allSellList.get(i).getTransferStartTs().
	 * toString());
	 * allsellorderdto.setUserDeviceId(allSellList.get(i).getUserDevice().
	 * getUserDeviceId()); listDto.add(allsellorderdto); }
	 * 
	 * response.put("sellOrders",listDto); response.put("message","SUC");
	 * response.put("key","200"); } }
	 * 
	 * catch (Exception e) { System.out.println("Error in checkExistence" +
	 * e.getMessage()); e.printStackTrace();
	 * response.put("message",CustomMessages.getCustomMessages("ISE"));
	 * response.put("key","500");
	 * 
	 * } return response; }
	 */
    
    public HashMap<String,Object> getContractsByUser(int userId) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    try {
    	
    	List<AllContractDto> listDto= new ArrayList<AllContractDto>();
    	List<AllContract> allContractList= allsellorderrepo.getAllContractsByUser(userId);
    	if(allContractList.size() > 0) {
    		for(int i=0;i<allContractList.size();i++) {
    			AllContractDto allcontractdto = new AllContractDto();
    			allcontractdto.setBuyerId(allContractList.get(i).getAllUser().getUserId());
    			allcontractdto.setContractId(allContractList.get(i).getContractId());
    			allcontractdto.setContractStatus(allContractList.get(i).getContractStatusPl().getContractStatusName());
    			AllSellOrder sellorder = allContractList.get(i).getAllSellOrder();
    			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
    			allsellorderdto.setDeviceTypeName(sellorder.getDevicePl().getDeviceTypeName());
    			allsellorderdto.setOrderStatus(sellorder.getOrderStatusPl().getOrderStatusName());
    			allsellorderdto.setPowerToSell(sellorder.getPowerToSell());
    			allsellorderdto.setRatePerUnit(sellorder.getRatePerUnit());
    			allsellorderdto.setSellerId(sellorder.getAllUser().getUserId());
    			allsellorderdto.setSellOrderId(sellorder.getSellOrderId());
    			allsellorderdto.setTotalAmount(sellorder.getTotalAmount());
    			allsellorderdto.setTransferEndTs(sellorder.getTransferEndTs().toString());
    			allsellorderdto.setTransferStartTs(sellorder.getTransferStartTs().toString());
    			allsellorderdto.setUserDeviceId(sellorder.getUserDevice().getUserDeviceId());
    			if (sellorder.getEnergy() != null) {
    				allsellorderdto.setEnergy(sellorder.getEnergy());
    			}
    			if (sellorder.getIsFineApplicable() != null) {
    				allsellorderdto.setIsFineApplicable(sellorder.getIsFineApplicable());
    			}
    			allcontractdto.setSellorder(allsellorderdto);
    			listDto.add(allcontractdto);
    		}
    		response.put("response",listDto);
    		response.put("message","SUC");
    		response.put("key","200");
    	}
    }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
            response.put("key","500");
           
        }
        return response;
    }

    public HashMap<String,Object> getAllSellOrdersByDate(int userId, String startDate, String endDate) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	
    try {
    	Date startDated = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
    	Date endDated = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
    	AllUser alluser = allsellorderrepo.getUserById(userId);
    	List<HashMap<String,Object>> listOfTrades = new ArrayList<HashMap<String,Object>>();
    	List<AllSellOrderDto> listDto= new ArrayList<AllSellOrderDto>();
    	List<AllSellOrder> allSellList= allsellorderrepo.getAllSellOrdersForUser(userId, startDated, endDated, 6);
    	BigDecimal totalAmountEarned = new BigDecimal(0);
    	BigDecimal totalAmountSpent = new BigDecimal(0);
    	if(allSellList.size() > 0) {
    		for(int i=0;i<allSellList.size();i++) {
    			// if(allSellList.get(i).getAllContracts().size() !=0) {
    			HashMap<String,Object> tradeData = new HashMap<String, Object>();
    			tradeData.put("deviceTypeName",allSellList.get(i).getDevicePl().getDeviceTypeName());
    			tradeData.put("status",allSellList.get(i).getOrderStatusPl().getOrderStatusName());
    			if(allSellList.get(i).getAllContracts().size() !=0) {
    			tradeData.put("participantName",allSellList.get(i).getAllContracts().get(0).getAllUser().getFullName());
    			}
    			tradeData.put("powerToSell",allSellList.get(i).getPowerToSell().toString());
    			tradeData.put("ratePerUnit",allSellList.get(i).getRatePerUnit().toString());
    			tradeData.put("transactionId",Integer.toString(allSellList.get(i).getSellOrderId()));
    			tradeData.put("totalAmount",allSellList.get(i).getTotalAmount().toString());
    			tradeData.put("transferEndTs",allSellList.get(i).getTransferEndTs().toString());
    			tradeData.put("transferStartTs",allSellList.get(i).getTransferStartTs().toString());
    			tradeData.put("userDeviceId",allSellList.get(i).getUserDevice().getUserDeviceId());
    			tradeData.put("localityId",Integer.toString(allSellList.get(i).getAllUser().getLocality().getLocalityId()));
    			tradeData.put("localityName",allSellList.get(i).getAllUser().getLocality().getLocalityName());
    			if(allSellList.get(i).getSellerFine() != null) {
    				tradeData.put("sellerFine",allSellList.get(i).getSellerFine());	
    			} else {
    				tradeData.put("sellerFine",null);
    			}
    			
    			if(allSellList.get(i).getEnergy() != null) {
    			tradeData.put("energy",allSellList.get(i).getEnergy().toString());
    			}
    			if (allSellList.get(i).getIsFineApplicable() != null) {
    				tradeData.put("isFineApplicable",allSellList.get(i).getIsFineApplicable());
    			}
    			tradeData.put("type","Sell-Order");
    			totalAmountEarned = totalAmountEarned.add(allSellList.get(i).getTotalAmount());
    			if(allSellList.get(i).getSellerFine() != null) {
    				totalAmountEarned = totalAmountEarned.subtract(allSellList.get(i).getSellerFine());
    			}
    			listOfTrades.add(tradeData);
    			}
    	//	}
    	}
    		List<AllContractDto> contractListDto= new ArrayList<AllContractDto>();
        	List<AllContract> allContractList= allsellorderrepo.getAllContractsByUser(userId, startDated, endDated, 8);
        	if(allContractList.size() > 0) {
        		for(int i=0;i<allContractList.size();i++) {
        			AllContractDto allcontractdto = new AllContractDto();
        			HashMap<String,Object> tradeData = new HashMap<String, Object>();
        			tradeData.put("transactionId",Integer.toString(allContractList.get(i).getContractId()));
        			tradeData.put("status",allContractList.get(i).getContractStatusPl().getContractStatusName());
        			AllSellOrder sellorder = allContractList.get(i).getAllSellOrder();
        			tradeData.put("deviceTypeName",sellorder.getDevicePl().getDeviceTypeName());
        			if(allContractList.get(i).getBuyerFine() != null) {
        				tradeData.put("buyerFine",allContractList.get(i).getBuyerFine());	
        			} else {
        				tradeData.put("buyerFine",null);
        			}
        			
        			tradeData.put("status",sellorder.getOrderStatusPl().getOrderStatusName());
        			tradeData.put("participantName",sellorder.getAllUser().getFullName());
        			tradeData.put("powerToSell",sellorder.getPowerToSell().toString());
        			tradeData.put("ratePerUnit",sellorder.getRatePerUnit().toString());
        			tradeData.put("transactionId",Integer.toString(sellorder.getSellOrderId()));
        			tradeData.put("totalAmount",sellorder.getTotalAmount().toString());
        			tradeData.put("transferEndTs",sellorder.getTransferEndTs().toString());
        			tradeData.put("transferStartTs",sellorder.getTransferStartTs().toString());
        			tradeData.put("userDeviceId",sellorder.getUserDevice().getUserDeviceId());
        			tradeData.put("localityId",Integer.toString(sellorder.getAllUser().getLocality().getLocalityId()));
        			tradeData.put("localityName",sellorder.getAllUser().getLocality().getLocalityName());
        			if(sellorder.getEnergy() != null) {
            			tradeData.put("energy",sellorder.getEnergy().toString());
            		}
        			if (sellorder.getIsFineApplicable() != null) {
        				tradeData.put("isFineApplicable",sellorder.getIsFineApplicable());
        			}
        			tradeData.put("type","Buy-Order");
        			System.out.println(sellorder.getTotalAmount());
        			totalAmountSpent = totalAmountSpent.add(sellorder.getTotalAmount());
        			if(allContractList.get(i).getBuyerFine() != null) {
        				totalAmountSpent = totalAmountSpent.add(allContractList.get(i).getBuyerFine());		
        			}
        			listOfTrades.add(tradeData);
        		}
        	}
        	response.put("totalSellLeads",allSellList.size());
        	response.put("totalBuyLeads",allContractList.size());
        	response.put("totalAmountSpent",totalAmountSpent);
        	response.put("totalAmountEarned",totalAmountEarned);
        	response.put("trades",listOfTrades);
    		response.put("message","SUC");
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

    public HashMap<String,Object> getAllContracts() {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    try {
    	
    	List<AllContractDto> listDto= new ArrayList<AllContractDto>();
    	List<AllContract> allContractList= allsellorderrepo.getAllContracts();
    	if(allContractList.size() > 0) {
    		for(int i=0;i<allContractList.size();i++) {
    			AllContractDto allcontractdto = new AllContractDto();
    			allcontractdto.setBuyerId(allContractList.get(i).getAllUser().getUserId());
    			allcontractdto.setContractId(allContractList.get(i).getContractId());
    			allcontractdto.setContractStatus(allContractList.get(i).getContractStatusPl().getContractStatusName());
    			AllSellOrder sellorder = allContractList.get(i).getAllSellOrder();
    			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
    			allsellorderdto.setDeviceTypeName(sellorder.getDevicePl().getDeviceTypeName());
    			allsellorderdto.setOrderStatus(sellorder.getOrderStatusPl().getOrderStatusName());
    			allsellorderdto.setPowerToSell(sellorder.getPowerToSell());
    			allsellorderdto.setRatePerUnit(sellorder.getRatePerUnit());
    			allsellorderdto.setSellerId(sellorder.getAllUser().getUserId());
    			allsellorderdto.setSellOrderId(sellorder.getSellOrderId());
    			allsellorderdto.setTotalAmount(sellorder.getTotalAmount());
    			allsellorderdto.setTransferEndTs(sellorder.getTransferEndTs().toString());
    			allsellorderdto.setTransferStartTs(sellorder.getTransferStartTs().toString());
    			allsellorderdto.setUserDeviceId(sellorder.getUserDevice().getUserDeviceId());
    			allcontractdto.setSellorder(allsellorderdto);
    			if(sellorder.getEnergy() != null) {
    				allcontractdto.setEnergy(sellorder.getEnergy());
    			}
    			if (allContractList.get(i).getIsFineApplicable() != null) {
    				allcontractdto.setIsFineApplicable(allContractList.get(i).getIsFineApplicable());
    			}
    			listDto.add(allcontractdto);
    		}
    		response.put("response",listDto);
    		response.put("message","SUC");
    		response.put("key","200");
    	}
    }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
            response.put("key","500");
           
        }
        return response;
    }

	/*
	 * public HashMap<String,Object> getAllSellOrders() {
	 * 
	 * HashMap<String,Object> response = new HashMap<String, Object>(); try {
	 * 
	 * List<AllSellOrderDto> listDto= new ArrayList<AllSellOrderDto>();
	 * List<AllSellOrder> allSellList= allsellorderrepo.getAllSellOrders();
	 * if(allSellList.size() > 0) { for(int i=0;i<allSellList.size();i++) {
	 * AllSellOrderDto allsellorderdto = new AllSellOrderDto();
	 * allsellorderdto.setDeviceTypeName(allSellList.get(i).getDevicePl().
	 * getDeviceTypeName());
	 * allsellorderdto.setOrderStatus(allSellList.get(i).getOrderStatusPl().
	 * getOrderStatusName());
	 * allsellorderdto.setPowerToSell(allSellList.get(i).getPowerToSell());
	 * allsellorderdto.setRatePerUnit(allSellList.get(i).getRatePerUnit());
	 * allsellorderdto.setSellerId(allSellList.get(i).getAllUser().getUserId());
	 * allsellorderdto.setSellOrderId(allSellList.get(i).getSellOrderId());
	 * allsellorderdto.setTotalAmount(allSellList.get(i).getTotalAmount());
	 * allsellorderdto.setTransferEndTs(allSellList.get(i).getTransferEndTs().
	 * toString());
	 * allsellorderdto.setTransferStartTs(allSellList.get(i).getTransferStartTs().
	 * toString());
	 * allsellorderdto.setUserDeviceId(allSellList.get(i).getUserDevice().
	 * getUserDeviceId()); listDto.add(allsellorderdto); }
	 *  response.put("sellOrders",listDto);
	 * response.put("contracts",contractListDto); response.put("message","SUC");
	 * response.put("key","200"); } } catch (Exception e) {
	 * System.out.println("Error in checkExistence" + e.getMessage());
	 * e.printStackTrace();
	 * response.put("message",CustomMessages.getCustomMessages("ISE"));
	 * response.put("key","500");
	 * 
	 * } return response; }
	 */

    
public HashMap<String,Object> getAllSellOrders(int userId) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    try {
    	AllUser alluser = allsellorderrepo.getUserById(userId);
    	List<AllSellOrderDto> listDto= new ArrayList<AllSellOrderDto>();
    	//List<AllSellOrder> allSellList= allsellorderrepo.getAllSellOrders(alluser.getAllState().getStateId(),2);
    	Integer[] status = {2,7,8};
    	List<Integer> orderStatus = new ArrayList<Integer>(Arrays.asList(status));
    	
    	List<AllSellOrder> allSellList= allsellorderrepo.getAllSellOrders(alluser.getLocality().getLocalityId(),orderStatus);
    	String bcStatus = AppStartupRunner.configValues.get("blockChain");
    	if(allSellList.size() > 0) {
    		
    		for(int i=0;i<allSellList.size();i++) {
    			if (allSellList.get(i).getSellOrderId() == 76)
    			{
    				System.out.println("fgghg");
    			}
    			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
    			allsellorderdto.setDeviceTypeName(allSellList.get(i).getDevicePl().getDeviceTypeName());
    			allsellorderdto.setOrderStatus(allSellList.get(i).getOrderStatusPl().getOrderStatusName());
    			allsellorderdto.setPowerToSell(allSellList.get(i).getPowerToSell());
    			allsellorderdto.setRatePerUnit(allSellList.get(i).getRatePerUnit());
    			allsellorderdto.setSellerId(allSellList.get(i).getAllUser().getUserId());
    			allsellorderdto.setSellOrderId(allSellList.get(i).getSellOrderId());
    			allsellorderdto.setTotalAmount(allSellList.get(i).getTotalAmount());
    			allsellorderdto.setTransferEndTs(allSellList.get(i).getTransferEndTs().toString());
    			allsellorderdto.setTransferStartTs(allSellList.get(i).getTransferStartTs().toString());
    			allsellorderdto.setUserDeviceId(allSellList.get(i).getUserDevice().getUserDeviceId());
    			allsellorderdto.setLocalityId(allSellList.get(i).getAllUser().getLocality().getLocalityId());
    			allsellorderdto.setLocalityName(allSellList.get(i).getAllUser().getLocality().getLocalityName());
    			allsellorderdto.setIsCancellable(cm.setIsCancellable(allSellList.get(i).getTransferStartTs(),allSellList.get(i).getOrderStatusPl().getOrderStatusName()));
     			allsellorderdto.setIsEditable(cm.setIsEditable(allSellList.get(i).getTransferStartTs(),allSellList.get(i).getOrderStatusPl().getOrderStatusName()));
     			if (allSellList.get(i).getSellerEnergyTfr() != null) {
     				allsellorderdto.setSellerEnergyTfr(allSellList.get(i).getSellerEnergyTfr());	
     			} 
     			if (allSellList.get(i).getSellerFine() != null) {
     				allsellorderdto.setSellerFine(allSellList.get(i).getSellerFine());
     			}
     			
     			if(allSellList.get(i).getEnergy() != null ) {
     				allsellorderdto.setEnergy(allSellList.get(i).getEnergy());
     			}
     			if (allSellList.get(i).getIsFineApplicable() != null) {
     				allsellorderdto.setIsFineApplicable(allSellList.get(i).getIsFineApplicable());
    			}
     			if(bcStatus.equalsIgnoreCase("Y")) {
    				String bcTxStatus = allsellorderrepo.getBlockChainStatus(allSellList.get(i).getSellOrderId());
    				if (bcTxStatus !=null) {
    					allsellorderdto.setBlockChainStatus(bcTxStatus);
    				} else {
    					allsellorderdto.setBlockChainStatus("Successful");
    				}
    				
    			} else {
    				
    				allsellorderdto.setBlockChainStatus("Successful");
    			}
    			listDto.add(allsellorderdto);
    		}
    	}
    		List<AllContractDto> contractListDto= new ArrayList<AllContractDto>();
        	List<AllContract> allContractList= allsellorderrepo.getAllContracts(alluser.getLocality().getLocalityId(),orderStatus);
        	if(allContractList.size() > 0) {
        		for(int i=0;i<allContractList.size();i++) {
        			AllContractDto allcontractdto = new AllContractDto();
        			allcontractdto.setBuyerId(allContractList.get(i).getAllUser().getUserId());
        			allcontractdto.setContractId(allContractList.get(i).getContractId());
        			allcontractdto.setContractStatus(allContractList.get(i).getContractStatusPl().getContractStatusName());
        			if (allContractList.get(i).getSellerEnergyTfr() != null) {
         				allcontractdto.setSellerEnergyTfr(allContractList.get(i).getSellerEnergyTfr());	
         			} 
         			if (allContractList.get(i).getSellerFine() != null) {
         				allcontractdto.setSellerFine(allContractList.get(i).getSellerFine());
         			}
         			if (allContractList.get(i).getBuyerEnergyTfr() != null) {
         				allcontractdto.setBuyerEnergyTfr(allContractList.get(i).getBuyerEnergyTfr());	
         			} 
         			if (allContractList.get(i).getBuyerFine() != null) {
         				allcontractdto.setBuyerFine(allContractList.get(i).getBuyerFine());
         			}
         			if (allContractList.get(i).getIsFineApplicable() != null) {
         				allcontractdto.setIsFineApplicable(allContractList.get(i).getIsFineApplicable());
        			}
        			allcontractdto.setIsCancellable(cm.setIsCancellable(allContractList.get(i).getAllSellOrder().getTransferStartTs(), allContractList.get(i).getContractStatusPl().getContractStatusName()));
        			allcontractdto.setIsEditable("N");
        			AllSellOrder sellorder = allContractList.get(i).getAllSellOrder();
        			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
        			allsellorderdto.setDeviceTypeName(sellorder.getDevicePl().getDeviceTypeName());
        			allsellorderdto.setOrderStatus(sellorder.getOrderStatusPl().getOrderStatusName());
        			allsellorderdto.setPowerToSell(sellorder.getPowerToSell());
        			allsellorderdto.setRatePerUnit(sellorder.getRatePerUnit());
        			allsellorderdto.setSellerId(sellorder.getAllUser().getUserId());
        			allsellorderdto.setSellOrderId(sellorder.getSellOrderId());
        			allsellorderdto.setTotalAmount(sellorder.getTotalAmount());
        			allsellorderdto.setTransferEndTs(sellorder.getTransferEndTs().toString());
        			allsellorderdto.setTransferStartTs(sellorder.getTransferStartTs().toString());
        			allsellorderdto.setUserDeviceId(sellorder.getUserDevice().getUserDeviceId());
        			allsellorderdto.setLocalityId(sellorder.getAllUser().getLocality().getLocalityId());
        			allsellorderdto.setLocalityName(sellorder.getAllUser().getLocality().getLocalityName());
        			if (sellorder.getSellerEnergyTfr() != null) {
         				allsellorderdto.setSellerEnergyTfr(sellorder.getSellerEnergyTfr());	
         			} 
         			if (sellorder.getSellerFine() != null) {
         				allsellorderdto.setSellerFine(sellorder.getSellerFine());
         			}
        			if(sellorder.getEnergy() != null) {
        				allsellorderdto.setEnergy(sellorder.getEnergy());
        			}
        			if (sellorder.getIsFineApplicable() != null) {
        				sellorder.setIsFineApplicable(sellorder.getIsFineApplicable());
        			}
        			if(bcStatus.equalsIgnoreCase("Y")) {
        				String bcTxStatus = allsellorderrepo.getBlockChainStatus(allSellList.get(i).getSellOrderId());
        				if (bcTxStatus !=null) {
        					allsellorderdto.setBlockChainStatus(bcTxStatus);
        				} else {
        					allsellorderdto.setBlockChainStatus("Successful");
        				}
        				
        			} else {
        				
        				allsellorderdto.setBlockChainStatus("Successful");
        			}
        			allcontractdto.setSellorder(allsellorderdto);
        			
        			contractListDto.add(allcontractdto);
        		}
        	}
        	response.put("totalSellLeads",listDto.size());
        	response.put("totalBuyLeads",contractListDto.size());
        	response.put("sellOrders",listDto);
    		response.put("contracts",contractListDto);
    		response.put("message","SUC");
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

	/*
	 * public HashMap<String,Object> getRecentTransactions(String type, int orderId)
	 * {
	 * 
	 * HashMap<String,Object> response = new HashMap<String, Object>(); try {
	 * AllUser alluser=null; if(type.equalsIgnoreCase("SELL-ORDER")) { alluser =
	 * allsellorderrepo.getUserFromSellOrders(orderId); } else
	 * if(type.equalsIgnoreCase("CONTRACT")) { alluser =
	 * allsellorderrepo.getUserFromContract(orderId); } response.put("userName",
	 * alluser.getFullName()); response.put("userId", alluser.getUserId());
	 * response.put("email", alluser.getEmail()); response.put("phone",
	 * alluser.getPhoneNumber()); List<AllSellOrderDto> completedListDto= new
	 * ArrayList<AllSellOrderDto>(); List<AllSellOrderDto> upcomingListDto= new
	 * ArrayList<AllSellOrderDto>(); List<AllSellOrder> allSellList=
	 * allsellorderrepo.getAllSellOrdersForUserFromAdmin(alluser.getUserId());
	 * if(allSellList.size() > 0) { for(int i=0;i<allSellList.size();i++) {
	 * AllSellOrderDto allsellorderdto = new AllSellOrderDto();
	 * allsellorderdto.setDeviceTypeName(allSellList.get(i).getDevicePl().
	 * getDeviceTypeName());
	 * allsellorderdto.setOrderStatus(allSellList.get(i).getOrderStatusPl().
	 * getOrderStatusName());
	 * allsellorderdto.setPowerToSell(allSellList.get(i).getPowerToSell());
	 * allsellorderdto.setRatePerUnit(allSellList.get(i).getRatePerUnit());
	 * allsellorderdto.setSellerId(allSellList.get(i).getAllUser().getUserId());
	 * allsellorderdto.setSellOrderId(allSellList.get(i).getSellOrderId());
	 * allsellorderdto.setTotalAmount(allSellList.get(i).getTotalAmount());
	 * allsellorderdto.setTransferEndTs(allSellList.get(i).getTransferEndTs().
	 * toString());
	 * allsellorderdto.setTransferStartTs(allSellList.get(i).getTransferStartTs().
	 * toString());
	 * allsellorderdto.setUserDeviceId(allSellList.get(i).getUserDevice().
	 * getUserDeviceId());
	 * allsellorderdto.setLocalityId(allSellList.get(i).getAllUser().getLocality().
	 * getLocalityId());
	 * allsellorderdto.setLocalityName(allSellList.get(i).getAllUser().getLocality()
	 * .getLocalityName());
	 * allsellorderdto.setIsEditable(cm.setIsEditable(allSellList.get(i).
	 * getTransferStartTs()));
	 * allsellorderdto.setIsCancellable(cm.setIsCancellable(allSellList.get(i).
	 * getTransferStartTs()));
	 * if(cm.checkUpcomingStatus(allSellList.get(i).getTransferStartTs()).
	 * equalsIgnoreCase("Y")) { upcomingListDto.add(allsellorderdto); } else {
	 * completedListDto.add(allsellorderdto); }
	 * 
	 * } List<AllContractDto> upcomingContractListDto= new
	 * ArrayList<AllContractDto>(); List<AllContractDto> completedContractListDto=
	 * new ArrayList<AllContractDto>(); List<AllContract> allContractList=
	 * allsellorderrepo.getAllContracts();
	 * 
	 * if(allContractList.size() > 0) { for(int i=0;i<allContractList.size();i++) {
	 * AllContractDto allcontractdto = new AllContractDto();
	 * allcontractdto.setBuyerId(allContractList.get(i).getAllUser().getUserId());
	 * allcontractdto.setContractId(allContractList.get(i).getContractId());
	 * allcontractdto.setContractStatus(allContractList.get(i).getContractStatusPl()
	 * .getContractStatusName());
	 * allcontractdto.setIsCancellable(cm.setIsCancellable(allContractList.get(i).
	 * getAllSellOrder().getTransferStartTs())); AllSellOrder sellorder =
	 * allContractList.get(i).getAllSellOrder(); AllSellOrderDto allsellorderdto =
	 * new AllSellOrderDto();
	 * allsellorderdto.setDeviceTypeName(sellorder.getDevicePl().getDeviceTypeName()
	 * ); allsellorderdto.setOrderStatus(sellorder.getOrderStatusPl().
	 * getOrderStatusName());
	 * allsellorderdto.setPowerToSell(sellorder.getPowerToSell());
	 * allsellorderdto.setRatePerUnit(sellorder.getRatePerUnit());
	 * allsellorderdto.setSellerId(sellorder.getAllUser().getUserId());
	 * allsellorderdto.setSellOrderId(sellorder.getSellOrderId());
	 * allsellorderdto.setTotalAmount(sellorder.getTotalAmount());
	 * allsellorderdto.setTransferEndTs(sellorder.getTransferEndTs().toString());
	 * allsellorderdto.setTransferStartTs(sellorder.getTransferStartTs().toString())
	 * ;
	 * allsellorderdto.setUserDeviceId(sellorder.getUserDevice().getUserDeviceId());
	 * allsellorderdto.setLocalityId(sellorder.getAllUser().getLocality().
	 * getLocalityId());
	 * allsellorderdto.setLocalityName(sellorder.getAllUser().getLocality().
	 * getLocalityName()); allcontractdto.setSellorder(allsellorderdto);
	 * if(cm.checkUpcomingStatus(sellorder.getTransferStartTs()).equalsIgnoreCase(
	 * "Y")) { upcomingContractListDto.add(allcontractdto); } else {
	 * completedContractListDto.add(allcontractdto); }
	 * 
	 * } }
	 * 
	 * response.put("upcomingSellOrders",upcomingListDto);
	 * response.put("completedSellOrders",completedListDto);
	 * response.put("upcomingContracts",upcomingContractListDto);
	 * response.put("completedContracts",completedContractListDto);
	 * response.put("message",CustomMessages.getCustomMessages("SUC"));
	 * response.put("key","200"); } }
	 */
public HashMap<String,Object> getRecentTransactions( int userId) {
	
	HashMap<String,Object> response = new HashMap<String, Object>();
try {
	AllUser alluser=null;
			/*
			 * if(type.equalsIgnoreCase("SELL-ORDER")) { alluser =
			 * allsellorderrepo.getUserFromSellOrders(orderId); } else
			 * if(type.equalsIgnoreCase("CONTRACT")) { alluser =
			 * allsellorderrepo.getUserFromContract(orderId); }
			 */
	alluser = allsellorderrepo.getUserById(userId);
	response.put("userName", alluser.getFullName());
	response.put("userId", alluser.getUserId());
	response.put("email", alluser.getEmail());
	response.put("phone", alluser.getPhoneNumber());
	response.put("locationName", alluser.getLocality().getLocalityName());
	response.put("locationId", alluser.getLocality().getLocalityId());
	List<AllSellOrderDto> completedListDto= new ArrayList<AllSellOrderDto>();
	List<AllSellOrderDto> upcomingListDto= new ArrayList<AllSellOrderDto>();
	List<AllContractDto> upcomingContractListDto= new ArrayList<AllContractDto>();
	List<AllContractDto> completedContractListDto= new ArrayList<AllContractDto>();
	List<AllSellOrder> allSellList= allsellorderrepo.getAllSellOrdersForUserFromAdmin(alluser.getUserId());
	if(allSellList.size() > 0) {
		for(int i=0;i<allSellList.size();i++) {
			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
			allsellorderdto.setDeviceTypeName(allSellList.get(i).getDevicePl().getDeviceTypeName());
			allsellorderdto.setOrderStatus(allSellList.get(i).getOrderStatusPl().getOrderStatusName());
			allsellorderdto.setPowerToSell(allSellList.get(i).getPowerToSell());
			allsellorderdto.setRatePerUnit(allSellList.get(i).getRatePerUnit());
			allsellorderdto.setSellerId(allSellList.get(i).getAllUser().getUserId());
			allsellorderdto.setSellOrderId(allSellList.get(i).getSellOrderId());
			allsellorderdto.setTotalAmount(allSellList.get(i).getTotalAmount());
			allsellorderdto.setTransferEndTs(allSellList.get(i).getTransferEndTs().toString());
			allsellorderdto.setTransferStartTs(allSellList.get(i).getTransferStartTs().toString());
			allsellorderdto.setUserDeviceId(allSellList.get(i).getUserDevice().getUserDeviceId());
			allsellorderdto.setLocalityId(allSellList.get(i).getAllUser().getLocality().getLocalityId());
			allsellorderdto.setLocalityName(allSellList.get(i).getAllUser().getLocality().getLocalityName());
			allsellorderdto.setIsCancellable(cm.setIsCancellable(allSellList.get(i).getTransferStartTs(),allSellList.get(i).getOrderStatusPl().getOrderStatusName()));
 			allsellorderdto.setIsEditable(cm.setIsEditable(allSellList.get(i).getTransferStartTs(),allSellList.get(i).getOrderStatusPl().getOrderStatusName()));
 			if(allSellList.get(i).getEnergy() != null) {
 				allsellorderdto.setEnergy(allSellList.get(i).getEnergy());
 			}
 			if(allSellList.get(i).getIsFineApplicable() != null) {
 				allsellorderdto.setIsFineApplicable(allSellList.get(i).getIsFineApplicable());
 			}
			if(cm.checkUpcomingStatus(allSellList.get(i).getTransferStartTs()).equalsIgnoreCase("Y")) {
				upcomingListDto.add(allsellorderdto);	
			}
			else {
				completedListDto.add(allsellorderdto);
			}
			
		}
		}
		List<AllContract> allContractList= allsellorderrepo.getAllContractsByUser(userId);
    	
    	if(allContractList.size() > 0) {
    		for(int i=0;i<allContractList.size();i++) {
    			AllContractDto allcontractdto = new AllContractDto();
    			allcontractdto.setBuyerId(allContractList.get(i).getAllUser().getUserId());
    			allcontractdto.setContractId(allContractList.get(i).getContractId());
    			allcontractdto.setContractStatus(allContractList.get(i).getContractStatusPl().getContractStatusName());
    			allcontractdto.setIsCancellable(cm.setIsCancellable(allContractList.get(i).getAllSellOrder().getTransferStartTs(),allContractList.get(i).getContractStatusPl().getContractStatusName()));
    			allcontractdto.setIsEditable("N");
    			AllSellOrder sellorder = allContractList.get(i).getAllSellOrder();
    			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
    			allsellorderdto.setDeviceTypeName(sellorder.getDevicePl().getDeviceTypeName());
    			allsellorderdto.setOrderStatus(sellorder.getOrderStatusPl().getOrderStatusName());
    			allsellorderdto.setPowerToSell(sellorder.getPowerToSell());
    			allsellorderdto.setRatePerUnit(sellorder.getRatePerUnit());
    			allsellorderdto.setSellerId(sellorder.getAllUser().getUserId());
    			allsellorderdto.setSellOrderId(sellorder.getSellOrderId());
    			allsellorderdto.setTotalAmount(sellorder.getTotalAmount());
    			allsellorderdto.setTransferEndTs(sellorder.getTransferEndTs().toString());
    			allsellorderdto.setTransferStartTs(sellorder.getTransferStartTs().toString());
    			allsellorderdto.setUserDeviceId(sellorder.getUserDevice().getUserDeviceId());
    			allsellorderdto.setLocalityId(sellorder.getAllUser().getLocality().getLocalityId());
    			allsellorderdto.setLocalityName(sellorder.getAllUser().getLocality().getLocalityName());
    			if(sellorder.getEnergy() != null) {
     				allsellorderdto.setEnergy(sellorder.getEnergy());
     			}
    			if(sellorder.getIsFineApplicable() != null) {
     				allsellorderdto.setIsFineApplicable(sellorder.getIsFineApplicable());
     			}
    			allcontractdto.setSellorder(allsellorderdto);
    			if(cm.checkUpcomingStatus(sellorder.getTransferStartTs()).equalsIgnoreCase("Y")) {
    				upcomingContractListDto.add(allcontractdto);
    			}
    			else {
    			completedContractListDto.add(allcontractdto);	
    			}
    			
    		}
    	}
    	
    	response.put("upcomingSellOrders",upcomingListDto);
		response.put("completedSellOrders",completedListDto);
		response.put("upcomingContracts",upcomingContractListDto);
		response.put("completedContracts",completedContractListDto);
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

    public HashMap<String,Object> updateSellOrder(int sellOrderId, String status, String role) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	OrderStatusPl orderstatus= null;
    	ContractStatusPl contractstatus= null;
    	JSONObject inputDetails1 = new JSONObject();
    	System.out.println("Start Time"+new Timestamp(System.currentTimeMillis()));
    try {
    	
    	if(status.equalsIgnoreCase("CANCEL")) {
    		orderstatus=allsellorderrepo.getOrderStatus("cancelled");
    		contractstatus=allsellorderrepo.getContractOrderStatus("cancelled");
    	}
		AllSellOrder allsellorder = allsellorderrepo.getSellOrder(sellOrderId);
		// GeneralConfig bcConfig = allsellorderrepo.getBlockChainConfig("block_chain");
    	allsellorderrepo.updateSellOrder(orderstatus.getOrderStatusId(), sellOrderId);
    	allsellorderrepo.updateContractOrderBySellOrder(contractstatus.getContractStatusId(), sellOrderId);
    	
    	NotificationRequestDto notifReqDto = new NotificationRequestDto();
		notifReqDto.setApp_id(ApplicationConstant.APP_ID.getResponseStatus());
		System.out.println("Start Time 0"+new Timestamp(System.currentTimeMillis()));
    	// if (!role.equalsIgnoreCase("AGG")) {
		NotificationGeneratorImpl obj = new NotificationGeneratorImpl(); 
        NotificationGenerator ngenerator = new NotificationGeneratorUtil(); 
        obj.registerNotificationGenerator(ngenerator); 
        
    	if (allsellorder.getAllContracts() != null && allsellorder.getAllContracts().size() > 0) {
		notifReqDto.setInclude_external_user_ids(new String[]{Integer.toString(allsellorder.getAllContracts().get(0).getAllUser().getUserId()).toString()} );
		obj.generateNotification(notifReqDto,ApplicationConstant.ORDER_CANCELLED.getResponseStatus());
		} else {
    	notifReqDto.setInclude_external_user_ids(new String[]{Integer.toString(allsellorder.getAllUser().getUserId()).toString()} );
    	obj.generateNotification(notifReqDto,ApplicationConstant.ORDER_CANCELLED.getResponseStatus());
		}
		System.out.println("Start Time 00"+new Timestamp(System.currentTimeMillis()));
//    	} else {
//    		notifReqDto.setInclude_external_user_ids(new String[]{Integer.toString(allsellorder.getAllContracts().get(0).getAllUser().getUserId()).toString()} );
//    		sendNotificationToUser(notifReqDto,ApplicationConstant.ORDER_CANCELLED.getResponseStatus());
//    		notifReqDto.setInclude_external_user_ids(new String[]{Integer.toString(allsellorder.getAllUser().getUserId()).toString()} );
//    		sendNotificationToUser(notifReqDto,ApplicationConstant.ORDER_CANCELLED.getResponseStatus());
//    	}
 		String bcStatus = AppStartupRunner.configValues.get("blockChain");
		if (bcStatus.equalsIgnoreCase("Y")) {
			   String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
		UserBlockchainKey ubckey = allsellorderrepo.getUserBlockchainKey(allsellorder.getAllUser().getUserId());
		AllBlockchainOrder allbcorder = allsellorderrepo.getBlockchainOrder(allsellorder.getSellOrderId());
		AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(allbcorder,"ORDER_CANCELLED");
		inputDetails1.put("orderId",allbcorder.getOrderId());
		inputDetails1.put("sellerAddress",ubckey.getUserAddress());
		inputDetails1.put("sellerPrivatekey", ubckey.getPrivateKey());
		System.out.println("Start Time 1"+new Timestamp(System.currentTimeMillis()));
		JobGeneratorImpl jobg = new JobGeneratorImpl(); 
		JobGenerator jgenerator = new JobGeneratorUtil(); 
		jobg.registerJObGenerator(jgenerator);
		jobg.generateJob("CANCEL_ORDER",blockChainURL+"/api/cancelOrder",inputDetails1,1,allsellorder, tx.getAllBlockchainTrxId(),bcdao);
//		HashMap<String,String> responseFrombcnetwork = httpconnectorhelper.sendPostWithToken(blockChainURL+"/api/cancelOrder", inputDetails1, 1, ubckey.getAuthToken());
//		   //HashMap<String,String> responseAfterParse = cm.parseInput(responseFrombcnetwork);
//		   if(responseFrombcnetwork.get("status").equalsIgnoreCase("Cancelled")) {
//		   // AllBlockchainOrder allbcorder= bcdao.createBlockchainOrder(responseFrombcnetwork.get("Batch_id"),responseFrombcnetwork.get("order_id"),count1); // Call BC API and put it in another method
//		   bcdao.addBlockchainTransaction(responseFrombcnetwork.get("Tx"), "ORDER_CANCELLED",allbcorder);
//		   }
		   System.out.println("Start Time 11"+new Timestamp(System.currentTimeMillis()));
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
    System.out.println("End Time"+new Timestamp(System.currentTimeMillis()));
        return response;
    }

    public HashMap<String,Object> updateContractOrder(int contractOrderId, String status) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	ContractStatusPl contractstatus= null;
    	JSONObject inputDetails1 = new JSONObject();
    try {
    	
    	if(status.equalsIgnoreCase("CANCEL")) {
    		contractstatus=allsellorderrepo.getContractOrderStatus("cancelled");
    	}
    	
    	allsellorderrepo.updateContractOrder(contractstatus.getContractStatusId(), contractOrderId);
    	
    	AllContract allcontract = allsellorderrepo.getContractbyId(contractOrderId);
    	allsellorderrepo.updateSellOrder(1,allcontract.getAllSellOrder().getSellOrderId() );
    	// GeneralConfig bcConfig = allsellorderrepo.getBlockChainConfig("block_chain");
    	
    	NotificationRequestDto notifReqDto = new NotificationRequestDto();
		
		NotificationGeneratorImpl obj = new NotificationGeneratorImpl(); 
        NotificationGenerator ngenerator = new NotificationGeneratorUtil(); 
        obj.registerNotificationGenerator(ngenerator); 
        notifReqDto.setApp_id(ApplicationConstant.APP_ID.getResponseStatus());
		notifReqDto.setInclude_external_user_ids(new String[]{Integer.toString(allcontract.getAllSellOrder().getAllUser().getUserId()).toString()} );
		obj.generateNotification(notifReqDto,ApplicationConstant.ORDER_CANCELLED.getResponseStatus());
		notifReqDto.setInclude_external_user_ids(new String[]{Integer.toString(allcontract.getAllUser().getUserId()).toString()} );
		obj.generateNotification(notifReqDto,ApplicationConstant.ORDER_CANCELLED.getResponseStatus());
		
    	
    	String bcStatus = AppStartupRunner.configValues.get("blockChain");
    	if (bcStatus.equalsIgnoreCase("Y")) {
    		String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
    	UserBlockchainKey ubckey = allsellorderrepo.getUserBlockchainKey(allcontract.getAllUser().getUserId());
		AllBlockchainOrder allbcorder = allsellorderrepo.getBlockchainOrder(allcontract.getAllSellOrder().getSellOrderId());
		AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(allbcorder,"CONTRACT_CANCELLED");
		inputDetails1.put("orderId",allbcorder.getOrderId());
		inputDetails1.put("buyerAddress",ubckey.getUserAddress());
		inputDetails1.put("buyerPrivatekey", ubckey.getPrivateKey());
		JobGeneratorImpl jobg = new JobGeneratorImpl(); 
		JobGenerator jgenerator = new JobGeneratorUtil(); 
		jobg.registerJObGenerator(jgenerator);
		jobg.generateJob("CANCEL_CONTRACT",blockChainURL+"/api/cancelContract",inputDetails1,1,allcontract.getAllSellOrder(), tx.getAllBlockchainTrxId(),bcdao);
//		HashMap<String,String> responseFrombcnetwork = httpconnectorhelper.sendPostWithToken(blockChainURL+"/api/cancelContract", inputDetails1, 1, ubckey.getAuthToken());
//		   //HashMap<String,String> responseAfterParse = cm.parseInput(responseFrombcnetwork);
//		   if(responseFrombcnetwork.get("Status").equalsIgnoreCase("Contract Cancelled")) {
//		   // AllBlockchainOrder allbcorder= bcdao.createBlockchainOrder(responseFrombcnetwork.get("Batch_id"),responseFrombcnetwork.get("order_id"),count1); // Call BC API and put it in another method
//		   bcdao.addBlockchainTransaction(responseFrombcnetwork.get("Tx"), "CONTRACT_CANCELLED",allbcorder);
//		   }
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


 public HashMap<String,Object> editSellOrder(HashMap<String,String> inputDetails, int sellOrderId) {
    	
    	HashMap<String,Object> response = new HashMap<String, Object>();
    	JSONObject inputDetails1 = new JSONObject();
    try {
    	Timestamp transferStartTs= Timestamp.valueOf(inputDetails.get("transferStartTs"));
    	NonTradehourStatusPl nontradestatus=nontradehourrepo.getNonTradeStatus("Created");
   	 int count = nontradehourrepo.checkNonTradeHourExistence(transferStartTs,nontradestatus.getNonTradehourStatusId());
   	 if(count >  0) {
   		 response.put("message",CustomMessages.getCustomMessages("NTS"));
   	 		response.put("key","300");	 
   	 		return response;
   	 }
    	BigDecimal powerToSell= new BigDecimal(inputDetails.get("powerToSell"));
    	BigDecimal totalAmount= new BigDecimal(inputDetails.get("totalAmount"));
    	BigDecimal ratePerUnit= new BigDecimal(inputDetails.get("ratePerUnit"));
    	BigDecimal energy= new BigDecimal(inputDetails.get("energy"));
    
    	Timestamp transferEndTs= Timestamp.valueOf(inputDetails.get("transferEndTs"));
    	// GeneralConfig bcConfig = allsellorderrepo.getBlockChainConfig("block_chain");
    	allsellorderrepo.editSellOrder(Integer.parseInt(inputDetails.get("deviceTypeId")),Integer.parseInt(inputDetails.get("userDeviceId")), powerToSell, transferStartTs, transferEndTs, ratePerUnit, totalAmount,sellOrderId,energy);
    	
    	AllSellOrder allsellorder = allsellorderrepo.getSellOrder(sellOrderId);
    	String bcStatus = AppStartupRunner.configValues.get("blockChain");
    	if(bcStatus.equalsIgnoreCase("Y")) {
    		String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
    	UserBlockchainKey ubckey = allsellorderrepo.getUserBlockchainKey(allsellorder.getAllUser().getUserId());
    	AllBlockchainOrder allbcorder = allsellorderrepo.getBlockchainOrder(allsellorder.getSellOrderId());
    	AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(allbcorder,"ORDER_UPDATED");
    	inputDetails1.put("energy",energy);
    	inputDetails1.put("timeFrom",transferStartTs);
    	inputDetails1.put("timeTo",transferEndTs);
    	inputDetails1.put("sellerDeviceId",allsellorder.getDevicePl().getDeviceTypeName());
    	inputDetails1.put("location","hyderabad");
    	inputDetails1.put("price",ratePerUnit);
    	inputDetails1.put("amountOfPower",powerToSell);
    	inputDetails1.put("sellerAddress",ubckey.getUserAddress());
    	inputDetails1.put("sellerPrivatekey", ubckey.getPrivateKey());
    	inputDetails1.put("unit", ratePerUnit);
    	inputDetails1.put("orderId", allbcorder.getOrderId());
    	JobGeneratorImpl obj = new JobGeneratorImpl(); 
    	JobGenerator ngenerator = new JobGeneratorUtil(); 
        obj.registerJObGenerator(ngenerator);
        obj.generateJob("UPDATE_ORDER",blockChainURL+"/api/updateOrder",inputDetails1,1,allsellorder, tx.getAllBlockchainTrxId(),bcdao);
//    	HashMap<String,String> responseFrombcnetwork = httpconnectorhelper.sendPostWithToken(blockChainURL+"/api/updateOrder", inputDetails1, 1, ubckey.getAuthToken());
//    	   //HashMap<String,String> responseAfterParse = cm.parseInput(responseFrombcnetwork);
//    	   if(responseFrombcnetwork.get("Status").equalsIgnoreCase("Order Updated")) {
//    	 //  AllBlockchainOrder allbcorder= bcdao.createBlockchainOrder(responseFrombcnetwork.get("Batch_id"),responseFrombcnetwork.get("order_id"),sellOrderId); // Call BC API and put it in another method
//    	   bcdao.addBlockchainTransaction(responseFrombcnetwork.get("Tx"), "ORDER_UPDATED",allbcorder);
//    	   }
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
 
 
 public HashMap<String,Object> getSellOrdersByUser(int userId) {
 	
 	HashMap<String,Object> response = new HashMap<String, Object>();
 try {
 	String yes="Y";
 	String no = "N";
 	List<AllSellOrderDto> listDto= new ArrayList<AllSellOrderDto>();
 	List<AllSellOrder> allSellList= allsellorderrepo.getAllSellOrdersByUser(userId);
 	String bcStatus = AppStartupRunner.configValues.get("blockChain");
 	Integer[] status = {1,4};
	List<Integer> orderStatus = new ArrayList<Integer>(Arrays.asList(status));
 	if(allSellList.size() > 0) {
 		
 		for(int i=0;i<allSellList.size();i++) {
 			if (allSellList.get(i).getSellOrderId() == 76) {
 				System.out.println("hhh");
 			}
 			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
 			allsellorderdto.setDeviceTypeName(allSellList.get(i).getDevicePl().getDeviceTypeName());
 			allsellorderdto.setOrderStatus(allSellList.get(i).getOrderStatusPl().getOrderStatusName());
 			allsellorderdto.setDeviceTypeId(allSellList.get(i).getDevicePl().getDeviceTypeId());
 			allsellorderdto.setPowerToSell(allSellList.get(i).getPowerToSell());
 			allsellorderdto.setRatePerUnit(allSellList.get(i).getRatePerUnit());
 			allsellorderdto.setSellerId(allSellList.get(i).getAllUser().getUserId());
 			allsellorderdto.setSellOrderId(allSellList.get(i).getSellOrderId());
 			allsellorderdto.setTotalAmount(allSellList.get(i).getTotalAmount());
 			allsellorderdto.setTransferEndTs(allSellList.get(i).getTransferEndTs().toString());
 			allsellorderdto.setTransferStartTs(allSellList.get(i).getTransferStartTs().toString());
 			allsellorderdto.setUserDeviceId(allSellList.get(i).getUserDevice().getUserDeviceId());
 			allsellorderdto.setIsCancellable(cm.setIsCancellable(allSellList.get(i).getTransferStartTs(),allSellList.get(i).getOrderStatusPl().getOrderStatusName()));
 			allsellorderdto.setIsEditable(cm.setIsEditable(allSellList.get(i).getTransferStartTs(),allSellList.get(i).getOrderStatusPl().getOrderStatusName()));
 			allsellorderdto.setLocalityId(allSellList.get(i).getAllUser().getLocality().getLocalityId());
			allsellorderdto.setLocalityName(allSellList.get(i).getAllUser().getLocality().getLocalityName());
			allsellorderdto.setSellerName(allSellList.get(i).getAllUser().getFullName());
			List<AllContract> contracts = allsellorderrepo.getContractByOrder(allSellList.get(i).getSellOrderId(), orderStatus);
			if(contracts.size() > 0) {
				allsellorderdto.setBuyerName(contracts.get(0).getAllUser().getFullName());
				if(contracts.get(0).getBuyerEnergyTfr() != null) {
					allsellorderdto.setBuyerEnergyTfr(contracts.get(0).getBuyerEnergyTfr());
				} else {
					allsellorderdto.setBuyerEnergyTfr(zero);
				}
				if(contracts.get(0).getBuyerFine() != null) {
					allsellorderdto.setBuyerFine(contracts.get(0).getBuyerFine());
				}else {
					allsellorderdto.setBuyerFine(zero);
				}
				
			} else {
				allsellorderdto.setBuyerEnergyTfr(zero);
				allsellorderdto.setBuyerFine(zero);
			}
			
			if(allSellList.get(i).getEnergy() != null) {
				allsellorderdto.setEnergy(allSellList.get(i).getEnergy());
				
			}
			if(allSellList.get(i).getSellerEnergyTfr() != null) {
				allsellorderdto.setSellerEnergyTfr(allSellList.get(i).getSellerEnergyTfr());
				
			}else {
				allsellorderdto.setSellerEnergyTfr(zero);
			}
			if(allSellList.get(i).getSellerFine() != null) {
				allsellorderdto.setSellerFine(allSellList.get(i).getSellerFine());
				
			}else {
				allsellorderdto.setSellerFine(zero);
			}
			if(allSellList.get(i).getIsFineApplicable() != null) {
				allsellorderdto.setIsFineApplicable(allSellList.get(i).getIsFineApplicable());
			}
			if(bcStatus.equalsIgnoreCase("Y")) {
				String bcTxStatus = allsellorderrepo.getBlockChainStatus(allSellList.get(i).getSellOrderId());
				if (bcTxStatus !=null) {
					allsellorderdto.setBlockChainStatus(bcTxStatus);
				} else {
					allsellorderdto.setBlockChainStatus("Successful");
				}
				
			} else {
				
				allsellorderdto.setBlockChainStatus("Successful");
			}
 			listDto.add(allsellorderdto);
 		}
 	}
 		List<AllContractDto> contractListDto= new ArrayList<AllContractDto>();
     	List<AllContract> allContractList= allsellorderrepo.getAllContractsByUser(userId);
     	if(allContractList.size() > 0) {
     		for(int i=0;i<allContractList.size();i++) {
     			AllContractDto allcontractdto = new AllContractDto();
     			allcontractdto.setBuyerId(allContractList.get(i).getAllUser().getUserId());
     			allcontractdto.setContractId(allContractList.get(i).getContractId());
     			allcontractdto.setContractStatus(allContractList.get(i).getContractStatusPl().getContractStatusName());
     			allcontractdto.setIsCancellable(cm.setIsCancellable(allContractList.get(i).getAllSellOrder().getTransferStartTs(),allContractList.get(i).getContractStatusPl().getContractStatusName()));
     			allcontractdto.setIsEditable("N");
     			allcontractdto.setBuyerName(allContractList.get(i).getAllUser().getFullName());
     			if(allContractList.get(i).getBuyerEnergyTfr() != null) {
     				allcontractdto.setBuyerEnergyTfr(allContractList.get(i).getBuyerEnergyTfr());
				}else {
					allcontractdto.setBuyerEnergyTfr(zero);
				}
			if(allContractList.get(i).getBuyerFine() != null) {
				allcontractdto.setBuyerFine(allContractList.get(i).getBuyerFine());
				}else {
					allcontractdto.setBuyerFine(zero);
				}
     			AllSellOrder sellorder = allContractList.get(i).getAllSellOrder();
     			AllSellOrderDto allsellorderdto = new AllSellOrderDto();
     			allsellorderdto.setDeviceTypeName(sellorder.getDevicePl().getDeviceTypeName());
     			allsellorderdto.setOrderStatus(sellorder.getOrderStatusPl().getOrderStatusName());
     			allsellorderdto.setPowerToSell(sellorder.getPowerToSell());
     			allsellorderdto.setRatePerUnit(sellorder.getRatePerUnit());
     			allsellorderdto.setSellerId(sellorder.getAllUser().getUserId());
     			allsellorderdto.setSellOrderId(sellorder.getSellOrderId());
     			allsellorderdto.setTotalAmount(sellorder.getTotalAmount());
     			allsellorderdto.setTransferEndTs(sellorder.getTransferEndTs().toString());
     			allsellorderdto.setTransferStartTs(sellorder.getTransferStartTs().toString());
     			allsellorderdto.setUserDeviceId(sellorder.getUserDevice().getUserDeviceId());
     			allsellorderdto.setSellOrderId(sellorder.getSellOrderId());
     			allsellorderdto.setLocalityId(sellorder.getAllUser().getLocality().getLocalityId());
    			allsellorderdto.setLocalityName(sellorder.getAllUser().getLocality().getLocalityName());
    			allsellorderdto.setDeviceTypeId(sellorder.getDevicePl().getDeviceTypeId());
    			allsellorderdto.setSellerName(sellorder.getAllUser().getFullName());
    			if(allContractList.get(i).getBuyerEnergyTfr() != null) {
    					allsellorderdto.setBuyerEnergyTfr(allContractList.get(i).getBuyerEnergyTfr());
    				}else {
    					allsellorderdto.setBuyerEnergyTfr(zero);
    				}
    			if(allContractList.get(i).getBuyerFine() != null) {
    					allsellorderdto.setBuyerFine(allContractList.get(i).getBuyerFine());
    				}else {
    					allsellorderdto.setBuyerFine(zero);
    				}
    			
    			if(sellorder.getEnergy() != null) {
    				allsellorderdto.setEnergy(sellorder.getEnergy());
    			}
    			if(sellorder.getIsFineApplicable() != null) {
    				allsellorderdto.setIsFineApplicable(sellorder.getIsFineApplicable());
    			}
    			if(sellorder.getSellerEnergyTfr() != null) {
    				allsellorderdto.setSellerEnergyTfr(sellorder.getSellerEnergyTfr());
    				
    			}else {
					allsellorderdto.setSellerEnergyTfr(zero);
				}
    			if(sellorder.getSellerFine() != null) {
    				allsellorderdto.setSellerFine(sellorder.getSellerFine());
    				
    			}else {
					allsellorderdto.setSellerFine(zero);
				}
    			if(allContractList.get(i).getIsFineApplicable() != null) {
    				allcontractdto.setIsFineApplicable(allContractList.get(i).getIsFineApplicable());
    			}
    			if(bcStatus.equalsIgnoreCase("Y")) {
    				String bcTxStatus = allsellorderrepo.getBlockChainStatus(sellorder.getSellOrderId());
    				if (bcTxStatus !=null) {
    					allsellorderdto.setBlockChainStatus(bcTxStatus);
    				} else {
    					allsellorderdto.setBlockChainStatus("Successful");
    				}
    					
    				
    			} else {
    				
    				allsellorderdto.setBlockChainStatus(null);
    			}
    			allcontractdto.setSellorder(allsellorderdto);
     			contractListDto.add(allcontractdto);
     		}
     	}
 
     	
     	response.put("contracts",contractListDto);
 		response.put("sellOrders",listDto);
 		response.put("message","SUC");
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

 

 public HashMap<String,Object> createSellOrder(HashMap<String,String> inputDetails) {
 	
 	HashMap<String,Object> response = new HashMap<String, Object>();
	JSONObject inputDetails1 = new  JSONObject();
 	
 try {
	 Timestamp transferStartTs= Timestamp.valueOf(inputDetails.get("transferStartTs"));
	 NonTradehourStatusPl nontradestatus=nontradehourrepo.getNonTradeStatus("Created");
	 int count = nontradehourrepo.checkNonTradeHourExistence(transferStartTs,nontradestatus.getNonTradehourStatusId());
	 if(count >  0) {
		 response.put("message",CustomMessages.getCustomMessages("NTS"));
	 		response.put("key","300");	 
	 		return response;
	 }
	 int sellOrderId = allsellorderrepo.getSellOrderCount()+1;
 	BigDecimal powerToSell= new BigDecimal(inputDetails.get("powerToSell"));
 	BigDecimal totalAmount= new BigDecimal(inputDetails.get("totalAmount"));
 	BigDecimal ratePerUnit= new BigDecimal(inputDetails.get("ratePerUnit"));
 	BigDecimal energy= new BigDecimal(inputDetails.get("energy"));
 	Timestamp transferEndTs= Timestamp.valueOf(inputDetails.get("transferEndTs"));
 	DevicePl devicepl= allsellorderrepo.getDeviceType(Integer.parseInt(inputDetails.get("deviceTypeId")));
 	UserDevice userdevice= allsellorderrepo.getUserDevices(Integer.parseInt(inputDetails.get("userDeviceId")));
 	AllUser alluser=allsellorderrepo.getUserById(Integer.parseInt(inputDetails.get("sellerId")));
 	OrderStatusPl orderstatus=allsellorderrepo.getOrderStatus("inittiated");
 	// GeneralConfig bcConfig = allsellorderrepo.getBlockChainConfig("block_chain");
 	AllSellOrder allsellorder = new AllSellOrder();
 	allsellorder.setSellOrderId(sellOrderId);
 	allsellorder.setDevicePl(devicepl);
 	allsellorder.setAllUser(alluser);
 	allsellorder.setUserDevice(userdevice);
 	allsellorder.setPowerToSell(powerToSell);
 	allsellorder.setTransferStartTs(transferStartTs);
 	allsellorder.setTransferEndTs(transferEndTs);
 	allsellorder.setRatePerUnit(ratePerUnit);
 	allsellorder.setTotalAmount(totalAmount);
 	allsellorder.setActiveStatus((byte)1);
 	allsellorder.setOrderStatusPl(orderstatus);
 	allsellorder.setEnergy(energy);
 	allsellorder.setSellerEnergyTfr(new BigDecimal(0));
 	allsellorder.setSellerFine(new BigDecimal(0));
 	allsellorderrepo.saveAndFlush(allsellorder);
 	
 	String bcStatus = AppStartupRunner.configValues.get("blockChain");
 	if (bcStatus.equalsIgnoreCase("Y")) {
		   String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
		   AllBlockchainOrder bcOrder = bcdao.preCreateBlockchainOrder(sellOrderId);
			AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(bcOrder,"ORDER_CREATED");
 	// Create Data for Blockchain 
 	UserBlockchainKey ubckey = allsellorderrepo.getUserBlockchainKey(alluser.getUserId());
	inputDetails1.put("energy",energy);
	inputDetails1.put("timeFrom",transferStartTs);
	inputDetails1.put("timeTo",transferEndTs);
	inputDetails1.put("sellerDeviceId",devicepl.getDeviceTypeName());
	inputDetails1.put("location","hyderabad");
	inputDetails1.put("price",ratePerUnit);
	inputDetails1.put("amountOfPower",powerToSell);
	inputDetails1.put("sellerAddress",ubckey.getUserAddress());
	inputDetails1.put("sellerPrivatekey", ubckey.getPrivateKey());
	inputDetails1.put("unit", ratePerUnit);
	//inputDetails1.put("userid", ubckey.getBlockChainUserId());
	JobGeneratorImpl obj = new JobGeneratorImpl(); 
	JobGenerator ngenerator = new JobGeneratorUtil(); 
    obj.registerJObGenerator(ngenerator);
    obj.generateJob("CREATE_ORDER",blockChainURL+"/api/createOrder",inputDetails1,1,allsellorder, tx.getAllBlockchainTrxId(),bcdao);
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


 public HashMap<String,Object> createMultipleSellOrder(List<HashMap<String,String>> inputDetails) {
	 	
	 JSONObject inputDetails1 = new  JSONObject();
	 	HashMap<String,Object> response = new HashMap<String, Object>();
	 	List<AllSellOrder> listOfOrders =new ArrayList<AllSellOrder>();
	 try {
		 // GeneralConfig bcConfig = allsellorderrepo.getBlockChainConfig("block_chain");
		 int count1=allsellorderrepo.getSellOrderCount();
		 for(int i=0 ;i < inputDetails.size(); i++) {
			 count1 =count1+1;
		 Timestamp transferStartTs= Timestamp.valueOf(inputDetails.get(i).get("transferStartTs"));
		 NonTradehourStatusPl nontradestatus=nontradehourrepo.getNonTradeStatus("Created");
		 int count = nontradehourrepo.checkNonTradeHourExistence(transferStartTs,nontradestatus.getNonTradehourStatusId());
		 if(count >  0) {
			 response.put("message",CustomMessages.getCustomMessages("NTS"));
		 		response.put("key","300");	 
		 		return response;
		 }
	 	BigDecimal powerToSell= new BigDecimal(inputDetails.get(i).get("powerToSell"));
	 	BigDecimal totalAmount= new BigDecimal(inputDetails.get(i).get("totalAmount"));
	 	BigDecimal ratePerUnit= new BigDecimal(inputDetails.get(i).get("ratePerUnit"));
	 	BigDecimal energy= new BigDecimal(inputDetails.get(i).get("energy"));
	 	Timestamp transferEndTs= Timestamp.valueOf(inputDetails.get(i).get("transferEndTs"));
	 	DevicePl devicepl= allsellorderrepo.getDeviceType(Integer.parseInt(inputDetails.get(i).get("deviceTypeId")));
	 	UserDevice userdevice= allsellorderrepo.getUserDevices(Integer.parseInt(inputDetails.get(i).get("userDeviceId")));
	 	AllUser alluser=allsellorderrepo.getUserById(Integer.parseInt(inputDetails.get(i).get("sellerId")));
	 	OrderStatusPl orderstatus=allsellorderrepo.getOrderStatus("inittiated");
	 	AllSellOrder allsellorder = new AllSellOrder();
	 	allsellorder.setSellOrderId(count1);
	 	allsellorder.setDevicePl(devicepl);
	 	allsellorder.setAllUser(alluser);
	 	allsellorder.setUserDevice(userdevice);
	 	allsellorder.setPowerToSell(powerToSell);
	 	allsellorder.setTransferStartTs(transferStartTs);
	 	allsellorder.setTransferEndTs(transferEndTs);
	 	allsellorder.setRatePerUnit(ratePerUnit);
	 	allsellorder.setTotalAmount(totalAmount);
	 	allsellorder.setActiveStatus((byte)1);
	 	allsellorder.setOrderStatusPl(orderstatus);
	 	allsellorder.setEnergy(energy);
	 	allsellorder.setSellerEnergyTfr(new BigDecimal(0));
	 	allsellorder.setSellerFine(new BigDecimal(0));
	 	listOfOrders.add(allsellorder);
	 	String bcStatus = AppStartupRunner.configValues.get("blockChain");
	 	if (bcStatus.equalsIgnoreCase("Y")) {
	 	
	 	   String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
	 	 	// Create Data for Blockchain 
	 	 	UserBlockchainKey ubckey = allsellorderrepo.getUserBlockchainKey(alluser.getUserId());
	 	   AllBlockchainOrder bcOrder = bcdao.preCreateBlockchainOrder(count1);
			AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(bcOrder,"ORDER_CREATED");
	 		inputDetails1.put("energy",energy);
	 		inputDetails1.put("timeFrom",transferStartTs);
	 		inputDetails1.put("timeTo",transferEndTs);
	 		inputDetails1.put("sellerDeviceId",devicepl.getDeviceTypeName());
	 		inputDetails1.put("location","hyderabad");
	 		inputDetails1.put("price",ratePerUnit);
	 		inputDetails1.put("amountOfPower",powerToSell);
	 		inputDetails1.put("sellerAddress",ubckey.getUserAddress());
	 		inputDetails1.put("sellerPrivatekey", ubckey.getPrivateKey());
	 		inputDetails1.put("unit", ratePerUnit);
	 		JobGeneratorImpl obj = new JobGeneratorImpl(); 
	 		JobGenerator ngenerator = new JobGeneratorUtil(); 
	 	    obj.registerJObGenerator(ngenerator);
	 	    obj.generateJob("CREATE_ORDER",blockChainURL+"/api/createOrder",inputDetails1,1,allsellorder, tx.getAllBlockchainTrxId(),bcdao);
	 		//inputDetails1.put("userid", ubckey.getBlockChainUserId());
//	 		
//	 		HashMap<String,String> responseFrombcnetwork = httpconnectorhelper.sendPostWithToken(blockChainURL+"/api/createOrder", inputDetails1, 1, ubckey.getAuthToken());
//	 		   //HashMap<String,String> responseAfterParse = cm.parseInput(responseFrombcnetwork);
//	 		   if(((String)responseFrombcnetwork.get("Status")).equalsIgnoreCase("Order Created")) {
//	 		   AllBlockchainOrder allbcorder= bcdao.createBlockchainOrder(responseFrombcnetwork.get("OrderId"),responseFrombcnetwork.get("OrderId"),count1); // Call BC API and put it in another method
//	 		   bcdao.addBlockchainTransaction(responseFrombcnetwork.get("Tx"), "ORDER_CREATED",allbcorder);
//	 		   }
		 }
		 allsellorderrepo.saveAll(listOfOrders);
		 response.put("message",CustomMessages.getCustomMessages("SUC"));
	 		response.put("key","200");
	 }
	 }
	     catch (Exception e) {
	         System.out.println("Error in checkExistence" + e.getMessage());
	         e.printStackTrace();
	         response.put("message",CustomMessages.getCustomMessages("ISE"));
	         response.put("key","500");
	        
	     }
	     return response;
	 }

 public HashMap<String,Object> createContract(HashMap<String,String> inputDetails) {
	 	
	 	HashMap<String,Object> response = new HashMap<String, Object>();
	 	JSONObject inputDetails1  = new JSONObject();
	 try {
		 Timestamp currentTime= new Timestamp(System.currentTimeMillis());
		 BigDecimal energy= new BigDecimal(inputDetails.get("energy"));
	 	AllSellOrder allsellorder= allsellorderrepo.getSellOrder(Integer.parseInt(inputDetails.get("sellOrderId")));
	 	AllUser alluser=allsellorderrepo.getUserById(Integer.parseInt(inputDetails.get("buyerId")));
	 	ContractStatusPl contractstatuspl= allsellorderrepo.getContractOrderStatus("active");
	 //	GeneralConfig bcConfig = allsellorderrepo.getBlockChainConfig("block_chain");
	 	AllContract allcontract= new AllContract();
	 	allcontract.setActiveStatus((byte)1);
	 	allcontract.setAllSellOrder(allsellorder);
	 	allcontract.setAllUser(alluser);
	 	allcontract.setContractStatusPl(contractstatuspl);
	 	allcontract.setCreatedTs(currentTime);
	 	allcontract.setEnergy(energy);
	 	allcontract.setBuyerFine(new BigDecimal(0));
	 	allcontract.setBuyerEnergyTfr(new BigDecimal(0));
	 	allcontract.setSellerEnergyTfr(new BigDecimal(0));
	 	allcontract.setSellerFine(new BigDecimal(0));
	 	allcontractrepo.save(allcontract);
	 	allsellorderrepo.updateSellOrder(3, Integer.parseInt(inputDetails.get("sellOrderId")));
	 	NotificationRequestDto notifReqDto = new NotificationRequestDto();
		NotificationGeneratorImpl obj = new NotificationGeneratorImpl(); 
        NotificationGenerator ngenerator = new NotificationGeneratorUtil(); 
        obj.registerNotificationGenerator(ngenerator); 
        notifReqDto.setApp_id(ApplicationConstant.APP_ID.getResponseStatus());
        notifReqDto.setInclude_external_user_ids(new String[]{Integer.toString(allsellorder.getAllUser().getUserId()).toString()} );
		obj.generateNotification(notifReqDto,ApplicationConstant.ACCEPT_TRADE.getResponseStatus());
	 	String bcStatus = AppStartupRunner.configValues.get("blockChain");
	 	if (bcStatus.equalsIgnoreCase("Y")) {
	 		  String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
	 	UserBlockchainKey ubckey = allsellorderrepo.getUserBlockchainKey(alluser.getUserId());
		AllBlockchainOrder allbcorder = allsellorderrepo.getBlockchainOrder(allsellorder.getSellOrderId());
		AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(allbcorder,"ORDER_ACCEPTED");
		inputDetails1.put("orderId",allbcorder.getOrderId());
		inputDetails1.put("buyerDeviceId","NetMeter");
		inputDetails1.put("buyerAddress",ubckey.getUserAddress());
		inputDetails1.put("buyerPrivatekey", ubckey.getPrivateKey());
		inputDetails1.put("buyeruserid", ubckey.getBlockChainUserId());
		JobGeneratorImpl jobj = new JobGeneratorImpl(); 
    	JobGenerator jgenerator = new JobGeneratorUtil(); 
    	jobj.registerJObGenerator(jgenerator);
    	jobj.generateJob("CREATE_CONTRACT",blockChainURL+"/api/acceptOrder",inputDetails1,1,allsellorder, tx.getAllBlockchainTrxId(),bcdao);
//	HashMap<String,String> responseFrombcnetwork = httpconnectorhelper.sendPostWithToken(blockChainURL+"/api/acceptOrder", inputDetails1, 1, ubckey.getAuthToken());
//		   //HashMap<String,String> responseAfterParse = cm.parseInput(responseFrombcnetwork);
//		   if(responseFrombcnetwork.get("Status").equalsIgnoreCase("Order Accepted")) {
//		   // AllBlockchainOrder allbcorder= bcdao.createBlockchainOrder(responseFrombcnetwork.get("Batch_id"),responseFrombcnetwork.get("order_id"),count1); // Call BC API and put it in another method
//		   bcdao.addBlockchainTransaction(responseFrombcnetwork.get("Tx"), "ORDER_ACCEPTED",allbcorder);
//		   }
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
 
 
 public HashMap<String,Object> searchBuyLeads(HashMap<String,String> inputDetails) {
	 	
	 	HashMap<String,Object> response = new HashMap<String, Object>();
	 	List<AllSellOrder> allSellList  =  new ArrayList<AllSellOrder>();
	 	int timeFrame = Integer.parseInt(searchTimeFrame);
	 try {
		 String bcStatus = AppStartupRunner.configValues.get("blockChain");
			AllUser user = allsellorderrepo.getUserById(Integer.parseInt(inputDetails.get("userId")));
			if(user!=null) {
		 	BigDecimal minUnits= new BigDecimal(inputDetails.get("minUnits"));
		 	BigDecimal maxUnits= new BigDecimal(inputDetails.get("maxUnits"));
		 	BigDecimal minAmount= new BigDecimal(inputDetails.get("minAmount"));
		 	BigDecimal maxAmount= new BigDecimal(inputDetails.get("maxAmount"));
		 	Timestamp transferEndTs= Timestamp.valueOf(inputDetails.get("transferEndTs"));
		 	Timestamp transferStartTs= Timestamp.valueOf(inputDetails.get("transferStartTs"));
		 	Timestamp preTransferStartTs= new Timestamp(transferStartTs.getTime() - (timeFrame*60*60*1));
		 	//Timestamp postTransferStartTs= new Timestamp(transferStartTs.getTime() + (1000*60*60*1));
		 	//Timestamp preTransferEndTs= new Timestamp(transferEndTs.getTime() - (1000*60*60*1));
		 	Timestamp postTransferEndTs= new Timestamp(transferEndTs.getTime() + (timeFrame*60*60*1));
		 	String preStartTs= preTransferStartTs.toString().substring(0, 19);
		 	String postEndTs= postTransferEndTs.toString().substring(0, 19);
		 	if(!inputDetails.get("deviceTypeId").equalsIgnoreCase("-1")) {
		 	
		 		allSellList = allsellorderrepo.fullMatchOfAllSellOrders(Integer.parseInt(inputDetails.get("deviceTypeId")), preStartTs,postEndTs, minUnits, maxUnits, minAmount, maxAmount,Integer.parseInt(inputDetails.get("userId")),user.getLocality().getLocalityId());
		 	}
		 	else {
		 	
 		 		allSellList = allsellorderrepo.fullMatchOfSellOrdersWoutDevices( preStartTs, postEndTs, minUnits, maxUnits, minAmount, maxAmount,Integer.parseInt(inputDetails.get("userId")),user.getLocality().getLocalityId());
		 	}
		 	
		 	List<SearchOrdersDto> listDto= new ArrayList<SearchOrdersDto>();
	    	if(allSellList.size() > 0) {
	    		for(int i=0;i<allSellList.size();i++) {
	    			SearchOrdersDto allsellorderdto = new SearchOrdersDto();
	    			allsellorderdto.setDeviceTypeName(allSellList.get(i).getDevicePl().getDeviceTypeName());
	    			allsellorderdto.setOrderStatus(allSellList.get(i).getOrderStatusPl().getOrderStatusName());
	    			allsellorderdto.setPowerToSell(allSellList.get(i).getPowerToSell());
	    			allsellorderdto.setRatePerUnit(allSellList.get(i).getRatePerUnit());
	    			allsellorderdto.setSellerId(allSellList.get(i).getAllUser().getUserId());
	    			allsellorderdto.setSellOrderId(allSellList.get(i).getSellOrderId());
	    			allsellorderdto.setTotalAmount(allSellList.get(i).getTotalAmount());
	    			allsellorderdto.setTransferEndTs(allSellList.get(i).getTransferEndTs().toString());
	    			allsellorderdto.setTransferStartTs(allSellList.get(i).getTransferStartTs().toString());
	    			allsellorderdto.setUserDeviceId(allSellList.get(i).getUserDevice().getUserDeviceId());
	    			if(allSellList.get(i).getAllUser().getLocality() != null) {
	    			allsellorderdto.setLocalitionId(allSellList.get(i).getAllUser().getLocality().getLocalityId());
	    			allsellorderdto.setLocationName(allSellList.get(i).getAllUser().getLocality().getLocalityName());
	    			}
//	    			allsellorderdto.setLocalitionId(allSellList.get(i).getAllUser().getLocality().getLocalityId());
//	    			allsellorderdto.setLocationName(allSellList.get(i).getAllUser().getLocality().getLocalityName());
	    			allsellorderdto.setEnergy(allSellList.get(i).getEnergy());
	    			if (bcStatus.equalsIgnoreCase("Y")) {
	    			String bcTxStatus = allsellorderrepo.getBlockChainStatus(allSellList.get(i).getSellOrderId());
	    			if (bcTxStatus!=null && !bcTxStatus.equalsIgnoreCase("Pending")) {
	    				listDto.add(allsellorderdto);	
	    			}
	    			} else {
	    				listDto.add(allsellorderdto);
	    			}
	    			
	    		}
	    	}
	    	
		 	response.put("list", listDto);
	 	response.put("message",CustomMessages.getCustomMessages("SUC"));
	 		response.put("key","200");
	 	
	 }
	  else {
		 	response.put("data", "No user Found");
		 	response.put("message",CustomMessages.getCustomMessages("SUC"));
		 		response.put("key","200");
	 }
	 }
	     catch (Exception e) {
	         System.out.println("Error in checkExistence" + e.getMessage());
	         e.printStackTrace();
	         response.put("message",CustomMessages.getCustomMessages("ISE"));
	         response.put("key","500");
	        
	     }
	     return response;
	 }
 
 public HashMap<String,Object> getUSersByAdmin(int userId) {
 	
 	HashMap<String,Object> response = new HashMap<String, Object>();
 try {
 	
 	List<AllUserByAdminDto> listDto= new ArrayList<AllUserByAdminDto>();
 	AllUser alluser = allsellorderrepo.getUserById(userId);
 	List<AllUser> allUserLists= allsellorderrepo.getUSersByAdmin(userId,alluser.getAllState().getStateId());
 	if(allUserLists.size() > 0) {
 		for(int i=0;i<allUserLists.size();i++) {
 			 AllUserByAdminDto alluserdto=new AllUserByAdminDto();
 			   alluserdto.setActiveStatus(allUserLists.get(i).getActiveStatus());
 			   alluserdto.setBoardId(allUserLists.get(i).getAllElectricityBoard().getElectricityBoardId());
 			   alluserdto.setBoardName(allUserLists.get(i).getAllElectricityBoard().getElectricityBoardName());
 			   alluserdto.setEmail(allUserLists.get(i).getEmail());
 			   alluserdto.setFullName(allUserLists.get(i).getFullName());
 			   alluserdto.setStateId(allUserLists.get(i).getAllState().getStateId());
 			   alluserdto.setStateName(allUserLists.get(i).getAllState().getStateName());
 			   alluserdto.setUserId(allUserLists.get(i).getUserId());
 			   alluserdto.setLocalityName(allUserLists.get(i).getLocality().getLocalityName());
 			   alluserdto.setLocalityId(allUserLists.get(i).getLocality().getLocalityId());
 			   alluserdto.setSellOrderCount(allsellorderrepo.getCountOfSellOrders(allUserLists.get(i).getUserId()));
 			  alluserdto.setContractCounts( allsellorderrepo.getCountOfContracts(allUserLists.get(i).getUserId()));
 			   listDto.add(alluserdto);
 			   
 			  
 		}
 		response.put("response",listDto);
 		response.put("message",CustomMessages.getCustomMessages("SUC"));
 		response.put("key","200");
 	}
 }
     catch (Exception e) {
         System.out.println("Error in checkExistence" + e.getMessage());
         e.printStackTrace();
         response.put("message",CustomMessages.getCustomMessages("ISE"));
         response.put("key","500");
        
     }
     return response;
 }


 public void sendNotificationToUser(NotificationRequestDto notificationRequestDto, String message) {
 	
 	try {
 	pushHelper.pushToUser("Started",notificationRequestDto.getInclude_external_user_ids()[0].toString(),message,null);
 	}catch(Exception excep) {
 		System.out.println("Exception occered while sending notification");
 	}
 	//notificationClient.sendNotification(notificationRequestDto);
 }
 public ArrayList<GeneralConfig> getBlockChainValue() {
		ArrayList<GeneralConfig> configValue = new ArrayList<>();
		ArrayList<String> listOfKeys = new ArrayList<>();
		listOfKeys.add("p2p_blockchain_enabled");
		listOfKeys.add("blockchain_uat");
		try {
			configValue=allsellorderrepo.getBlockChainConfig(listOfKeys);
		}
		catch(Exception e) {
			
		} finally {
			
		}
		return configValue;
	}
 
 public HashMap<String,Object> validateTrade(HashMap<String,Object> inputDetails) {
	 	
	 	HashMap<String,Object> response = new HashMap<String, Object>();
	 	int userId = (int)inputDetails.get("userId");
	 	String startTime = (String)inputDetails.get("startTime");
	 	String endTime = (String)inputDetails.get("endTime");
	 	String tradeType =(String)inputDetails.get("tradeType");
	 	BigDecimal unitsToSell =new BigDecimal((String)inputDetails.get("units"));
	 	BigDecimal totalUnits =new BigDecimal((String)inputDetails.get("totalUnits"));
	 	BigDecimal power = new BigDecimal("0");
	 try {
		 if(totalUnits.compareTo(unitsToSell) <0) {
				response.put("key", 401);
				response.put("message", "Total in payload is less than units to sell.");
				return response;
		 }
		if(tradeType.equalsIgnoreCase("buy")) {
			int status = allsellorderrepo.validateSellOrder(userId, startTime, endTime, 1);
			System.out.println(status);
			if(status > 0) {
				response.put("key", 401);
				response.put("message", "Selected time range overlaps with a sell order");
				return response;
			} else {
				 power = allsellorderrepo.validateContractByPower(userId, startTime, endTime, 3);
				 if(power != null) {
					 power = power;
				 } else {
					 power = new BigDecimal("0");
				 }
				BigDecimal powerProjected = power.add(unitsToSell);
				System.out.println(powerProjected);
				if(powerProjected.compareTo(totalUnits) > 0) {
					response.put("key", 401);
					response.put("message", "Selected units exceeds your total device capacity as you already  have contracts in this time duration");
					return response;
				} else {
					response.put("key", 200);
					response.put("message", "Success");
					return response;	
				}
				  
			}
		} else {
			
			int status = allsellorderrepo.validateContract(userId, startTime, endTime, 3);
			System.out.println(status);
			if(status > 0) {
				response.put("key", 401);
				response.put("message", "You already have a buy order in this time range");
				return response;
			} else {
				 power = allsellorderrepo.validateSellOrderByPower(userId, startTime, endTime, 1);
				 if(power != null) {
					 power = power;
				 } else {
					 power = new BigDecimal("0");
				 }
				BigDecimal powerProjected = power.add(unitsToSell);
				System.out.println(powerProjected);
				if(powerProjected.compareTo(totalUnits) > 0) {
					response.put("key", 401);
					response.put("message", "Selected units exceeds your total device capacity as you already  have sell orders in this time duration");
					return response;
				} else {
					response.put("key", 200);
					response.put("message", "Success");
					return response;	
				}
			
				 }
		}
		 
		}
	 catch(Exception e) {
		 e.printStackTrace();
		 response.put("message",CustomMessages.getCustomMessages("ISE"));
   response.put("key","500");
			return response;
	 }
	 
	 }

}	

