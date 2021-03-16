package com.energytrade.app.util;

import java.sql.SQLException;


import com.energytrade.app.model.NotificationRequestDto;


public class NotificationGeneratorUtil implements NotificationGenerator {
 
		  
	    @Override
		public void generateNotification(NotificationRequestDto notificationRequestDto, String message) throws ClassNotFoundException, SQLException {
	    	 PushHelper pushHelper = new PushHelper();
	    	try {
	    	 	pushHelper.pushToUser("Started",notificationRequestDto.getInclude_external_user_ids()[0].toString(),message,null);
	    	 	}catch(Exception excep) {
	    	 		System.out.println("Exception occered while sending notification");
	    	 	}
			
		}
	} 

