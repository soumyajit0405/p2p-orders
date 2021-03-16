package com.energytrade.app.util;

import java.sql.SQLException;
import java.util.ArrayList;

import com.energytrade.app.model.NotificationRequestDto;


public class NotificationGeneratorImpl {
	
	private NotificationGenerator ngenerator;
	
	public void registerNotificationGenerator(NotificationGenerator ngenerator) 
    { 
        this.ngenerator = ngenerator; 
    } 
	
	public void generateNotification(NotificationRequestDto notificationRequestDto, String message) 
    { 
  
        // An Async task always executes in new thread 
        new Thread(new Runnable() { 
            public void run() 
            { 
                // check if listener is registered. 
                if (ngenerator != null) { 
  
                    // invoke the callback method of class A 
                    try {
						ngenerator.generateNotification(notificationRequestDto, message);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                } 
            } 
        }).start(); 
    }
	
	}


