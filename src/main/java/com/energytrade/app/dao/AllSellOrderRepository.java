package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllBlockchainOrder;
import com.energytrade.app.model.AllBlockchainTransaction;
import com.energytrade.app.model.AllContract;
import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllOtp;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.GeneralConfig;
import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserBlockchainKey;
import com.energytrade.app.model.UserDevice;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllSellOrderRepository extends JpaRepository<AllSellOrder, Long>
{
    
	
	  @Query("Select a from AllSellOrder a where a.allUser.userId=?1") 
	  List<AllSellOrder> getAllSellOrdersByUser(int userId );
	  
	  @Query("Select a from AllSellOrder a where a.allUser.userId=?1 and a.orderStatusPl.orderStatusId=?2 ") 
	  List<AllSellOrder> getAllSellOrdersByStatus(int userId , int statusId);
	  
	  @Query("Select a from AllUser a where a.userId=?1 ") 
	  AllUser getUserById( int userId);
	  
	  @Query("Select a from DevicePl a where a.deviceTypeId=?1 ") 
	  DevicePl getDeviceType( int deviceTypeId);
	  
	  @Query("Select a from UserDevice a where a.userDeviceId=?1 ") 
	  UserDevice getUserDevices( int userDeviceId);
	  
	  @Query("Select a from AllSellOrder a where a.sellOrderId=?1 ") 
	  AllSellOrder getSellOrder( int sellOrderId);
	  
	  @Query("Select COALESCE(max(a.sellOrderId),0) from AllSellOrder a ")
	    int getSellOrderCount();   
	  
	  @Query("Select a from UserBlockchainKey a where a.allUser.userId=?1 ") 
	  UserBlockchainKey getUserBlockchainKey( int userDeviceId);
	  
	  @Query("Select a from AllBlockchainOrder a where a.generalOrderId=?1 and a.orderType='SELL_ORDER' ") 
	  AllBlockchainOrder getBlockchainOrder( int orderId);
	  
	  @Query(value ="select all_blockchain_transactions_status_pl.blc_tx_status_name from all_blockchain_transactions , all_blockchain_orders,all_blockchain_transactions_status_pl \r\n"
	  		+ "	  		where all_blockchain_transactions.blockchain_order_id = all_blockchain_orders.all_blockchain_orders_id\r\n"
	  		+ "	  		and all_blockchain_orders.general_order_id =?1 \r\n"
	  		+ "            and all_blockchain_transactions.status = all_blockchain_transactions_status_pl.blc_tx_status_id order by all_blockchain_transactions.created_ts desc limit 1",nativeQuery = true) 
	  String getBlockChainStatus( int orderId);
	  
	  @Modifying
	  @Query("update AllSellOrder a set a.orderStatusPl.orderStatusId=?1 where a.sellOrderId=?2") 
	  void updateSellOrder(int statusId , int sellOrderId);
	  
	  @Modifying
	  @Query("update AllContract a set a.contractStatusPl.contractStatusId=?1 where a.contractId=?2") 
	  void updateContractOrder(int statusId , int contractId);
	  
	  @Modifying
	  @Query("update AllContract a set a.contractStatusPl.contractStatusId=?1 where a.allSellOrder.sellOrderId=?2") 
	  void updateContractOrderBySellOrder(int statusId , int sellOrderId);
	  
	  @Query("Select a from OrderStatusPl a where a.orderStatusName=?1 ") 
	  OrderStatusPl getOrderStatus( String status);
	  
	  @Query("Select a from ContractStatusPl a where a.contractStatusName=?1 ") 
	  ContractStatusPl getContractOrderStatus( String status);
	  
	
	  @Modifying	  
	  @Query("update AllSellOrder a set a.devicePl.deviceTypeId=?1,a.userDevice.userDeviceId=?2,a.powerToSell=?3,a.transferStartTs=?4,a.transferEndTs=?5, a.ratePerUnit=?6,a.totalAmount=?7,a.energy=?9  where a.sellOrderId=?8"
	  ) void editSellOrder(int deviceTypeId , int userDeviceId,BigDecimal powerToSell,Timestamp transferStartTs,Timestamp transferEndTs, BigDecimal ratePerUnit, BigDecimal totalAmount, int sellOrderId, BigDecimal energy);
	 
	  @Query("Select a from AllContract a where a.allUser.userId=?1") 
	  List<AllContract> getAllContractsByUser(int userId);
	  
	  @Query("Select a from AllUser a where a.allState.stateId=?2 and a.userId<>?1") 
	  List<AllUser> getUSersByAdmin(int userId,int stateId);
	  
	  @Query("Select a from AllContract a ") 
	  List<AllContract> getAllContracts();
	  
	  @Query("Select a from AllContract a where a.contractStatusPl.contractStatusId=?1") 
	  List<AllContract> getAllContracts(int statusId);
	  
	  @Query("Select b from AllContract b where b.allSellOrder.sellOrderId in (Select a.sellOrderId from AllSellOrder a where a.allUser.locality.localityId=?1 and orderStatusPl.orderStatusId not in ?2)") 
	  List<AllContract> getAllContracts(int localityId,List<Integer> status);
	  
	  @Query("Select a from AllContract a where a.contractId=?1") 
	  AllContract getContractbyId(int contractId);
	  
	 @Query("Select a from AllContract a , AllSellOrder b where a.allUser.userId=?1 and date(b.transferStartTs)>=?2 and date(b.transferEndTs)<=?3 and a.contractStatusPl.contractStatusId =?4 and a.allSellOrder.sellOrderId = b.sellOrderId") 
	  List<AllContract> getAllContractsByUser(int userId, Date startDate, Date endDate, int statusId);
	  
	  @Query("Select a from AllSellOrder a where a.allUser.allState.stateId=?1 ") 
	  List<AllSellOrder> getAllSellOrders( int stateId);
	  
//	  @Query("Select a from AllSellOrder a where a.allUser.allState.stateId=?1 and orderStatusPl.orderStatusId <>?2") 
//	  List<AllSellOrder> getAllSellOrders( int stateId, int statusId);
	  
	  @Query("Select a from AllSellOrder a where a.allUser.locality.localityId=?1 and orderStatusPl.orderStatusId <>?2") 
	  List<AllSellOrder> getAllSellOrders( int stateId, int statusId);
	  
	  @Query("Select a from AllSellOrder a where a.allUser.locality.localityId=?1 and orderStatusPl.orderStatusId not in ?2") 
	  List<AllSellOrder> getAllSellOrders( int localityId, List<Integer> statusId);
	  
	  @Query("Select a from AllSellOrder a where a.allUser.userId=?1 and date(a.transferStartTs)>=?2 and date(a.transferEndTs)<=?3 and a.orderStatusPl.orderStatusId =?4 ") 
	  List<AllSellOrder> getAllSellOrdersForUser( int userId, Date startDate, Date endDate, int statusId);
	  
	  @Query("Select a from AllSellOrder a where a.allUser.userId=?1") 
	  List<AllSellOrder> getAllSellOrdersForUserFromAdmin(int userId );
	  
	  
	  @Query("Select a.allUser from AllSellOrder a where a.sellOrderId=?1 ") 
	  AllUser getUserFromSellOrders( int orderId);
	  
	  @Query("Select count(*) from AllSellOrder a where a.allUser.userId=?1 ") 
	  int getCountOfSellOrders( int userId);
	  
	  @Query("Select COALESCE(max(a.sellOrderId),0) from AllSellOrder a  ") 
	  int getMaxSellOrderId();
	  
	  @Query("Select count(*) from AllContract a where a.allUser.userId=?1 ") 
	  int getCountOfContracts( int userId);
	  
	  @Query("Select a.allUser from AllContract a where a.contractId=?1 ") 
	  AllUser getUserFromContract( int orderId);
	  
//	  @Query("Select a from AllSellOrder a where  a.sellOrderId in ("+
//	  		 "Select a.sellOrderId from AllSellOrder a where ( a.powerToSell >=?3 and a.powerToSell<=?4) or (a.totalAmount>=?5 and a.totalAmount<=?6)) and a.orderStatusPl.orderStatusId  in (1) and ( a.transferStartTs>=?1  and a.transferEndTs<=?2) and a.allUser.userId<>?7") 
//	  List<AllSellOrder> fullMatchOfSellOrdersWoutDevices( Timestamp prestartTime,  Timestamp postEndTime,BigDecimal minPowerToSell, BigDecimal maxPowerToSell, BigDecimal minTotalPrice, BigDecimal maxTotalPrice , int userId);
	  
	  @Query(value="select * from all_sell_orders,all_users  where ( sell_order_id in ( select sell_order_id  from all_sell_orders where power_to_sell>=?3  and power_to_sell<=?4  or total_amount>=?5 and total_amount<=?6)) and transfer_start_ts>=?1 and transfer_end_ts<=?2 and (order_status_id in (1))  and seller_id<>?7 and all_users.locality_id = ?8 and all_users.user_id = all_sell_orders.seller_id",nativeQuery= true) 
		  List<AllSellOrder> fullMatchOfSellOrdersWoutDevices( String prestartTime,  String postEndTime,BigDecimal minPowerToSell, BigDecimal maxPowerToSell, BigDecimal minTotalPrice, BigDecimal maxTotalPrice , int userId, int localityId);
	  
//	  @Query("Select a from AllSellOrder a where a.sellOrderId in ("+
//		  		 "Select a.sellOrderId from AllSellOrder a where ( a.powerToSell >=?4 and a.powerToSell<=?5) or (a.totalAmount>=?6 and a.totalAmount<=?7)) and  (a.transferStartTs>=?2 and a.transferEndTs<=?3) and   a.orderStatusPl.orderStatusId  in (1) and (a.devicePl.deviceTypeId=?1) and a.allUser.userId<>?8") 
//		  List<AllSellOrder> fullMatchOfAllSellOrders(int deviceTypeId, Timestamp prestartTime, Timestamp postEndTime,BigDecimal minPowerToSell, BigDecimal maxPowerToSell, BigDecimal minTotalPrice, BigDecimal maxTotalPrice, int userId );
	  
	  @Query(value="select * from all_sell_orders,all_users  where ( sell_order_id in ( select sell_order_id  from all_sell_orders where power_to_sell>=?4  and power_to_sell<=?5  or total_amount>=?6 and total_amount<=?7)) and transfer_start_ts>=?2 and transfer_end_ts<=?3 and (order_status_id in (1)) and device_type_id=?1 and seller_id<>?8 and all_users.locality_id = ?9 and all_users.user_id = all_sell_orders.seller_id",nativeQuery = true) 
		  List<AllSellOrder> fullMatchOfAllSellOrders(int deviceTypeId, String prestartTime, String postEndTime,BigDecimal minPowerToSell, BigDecimal maxPowerToSell, BigDecimal minTotalPrice, BigDecimal maxTotalPrice, int userId, int localityId );
	  
	  @Query("Select a from GeneralConfig a where a.name in ?1")
	    ArrayList<GeneralConfig> getBlockChainConfig(ArrayList<String> listOfValues);

	      
}