package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllBlockchainOrder;
import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllEvent;
import com.energytrade.app.model.AllEventSet;
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
import com.energytrade.app.model.UserBlockchainKey;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllBlockchainOrderRepository extends JpaRepository<AllBlockchainOrder, Long>
{

	@Query("Select COALESCE(max(a.allBlockchainOrdersId),0) from AllBlockchainOrder a ")
    int getBcOrderCount();
	
	@Query("Select a from AllBlockchainOrder a where a.allBlockchainOrdersId=?1 ")
    AllBlockchainOrder getBlockChainOrder(int bcorderid);
	
	@Query("select a from  UserBlockchainKey a  where a.allUser.userId=?1")
	UserBlockchainKey getUserBlockChainKey(int userId);
	
	@Modifying
    @Query("update AllBlockchainOrder a set a.orderId=?1 where a.generalOrderId=?2")
     void updateBlockChainOrder(String bcOrderId, int orderId);
  
}