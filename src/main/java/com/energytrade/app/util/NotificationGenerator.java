package com.energytrade.app.util;

import java.sql.SQLException;
import java.util.ArrayList;

import com.energytrade.app.model.NotificationRequestDto;

public interface NotificationGenerator {
	
	void generateNotification(NotificationRequestDto notificationRequestDto, String message) throws ClassNotFoundException, SQLException;

}
