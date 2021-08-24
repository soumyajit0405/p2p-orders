package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllOtp;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.LocalityPl;
import com.energytrade.app.model.NonTradeHour;
import com.energytrade.app.model.NonTradehourStatusPl;
import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NonTradeHourRepository extends JpaRepository<NonTradeHour, Long>
{
      
	  @Query("Select a from NonTradeHour a where a.allUser1.userId=?1 order by endTs desc ") 
	  List<NonTradeHour> getAllNonTradeHour(int userId);
	  
	  @Query("Select a from AllUser a where a.userId=?1 ") 
	  AllUser getUserById( int userId);
	  
	  @Query("Select a from NonTradehourStatusPl a where a.nonTradehourStatusName=?1 ") 
	  NonTradehourStatusPl getNonTradeStatus( String status);
	  
	  @Query("Select a.sellOrderId from AllSellOrder a where a.transferStartTs >=?1 and a.transferStartTs <=?2 and a.allUser.locality.localityId=?3") 
	  List<Integer> getSellOrders(  Timestamp startDate , Timestamp endDate, int localityId);
	  
	  @Query(value ="Select sell_order_id from all_sell_orders  where transfer_start_ts >=:startDate and transfer_start_ts <=:endDate and a.allUser.locality.localityId=:localityId union "
		  		+ "Select sell_order_id from all_sell_orders  where transfer_end_ts >=:startDate and transfer_end_ts <=:endDate and a.allUser.locality.localityId=:localityId union 	"
		  		+ "Select sell_order_id from all_sell_orders  where transfer_start_ts <=:startDate and transfer_end_ts >=:endDate and order_status_id <> 2 and a.allUser.locality.localityId=:localityId",nativeQuery = true) 
		  List<Integer> getSellOrders( @Param("startDate") String startDate ,@Param("endDate") String endDate, @Param("localityId") int localityId);
	  
	  @Modifying  
	  @Query("update NonTradeHour a set a.nonTradeReason=?1,a.startTs=?2,a.endTs=?3,a.locality.localityId=?5  where a.nonTradeHourId=?4"
	  ) void editSellOrder(String reason,Timestamp startTime,Timestamp endTime, int nonTradeHourId, int localityId);
	  
	  @Modifying  
	  @Query("update NonTradeHour a set a.nonTradehourStatusPl.nonTradehourStatusId=?1,a.nonTradeReason=?3  where a.nonTradeHourId=?2"
	  ) void updateNonTradeHourStatus(int nonTradeHourStatus, int nonTradeHourId, String reason);
	  
	  @Query("Select count(a.nonTradeHourId) from NonTradeHour a where a.startTs >=?1 and a.endTs <=?1 and a.nonTradehourStatusPl.id=?2") 
	  int checkNonTradeHourExistence(  Timestamp startDate, int statusId );
	  
	  @Query("Select a from LocalityPl a where a.localityId=?1 ") 
	  LocalityPl getLocality( int localityId);
	 
        
}