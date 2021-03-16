package com.energytrade.app.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class PushHelper {

	private static final String webServiceURI = "https://onesignal.com/api/v1/notifications";
	//private final static String notification_url = "https://onesignal.com/api/v1/notifications";
	 // private final static String AUTHTOKEN = "Basic ZGQzMDc1MDktMzQ0NC00MzM1LTkwNGEtNzMxZjE1MzA0NWQ4"; //DEV
	private final static String AUTHTOKEN = "Basic OGE3M2RlNDItYjcyMy00MWMwLWJlYzktMDNhMjQ0MmI3MDBi"; // UAT

	public String pushToUser(String status, String playerId, String message, String wod)
			throws ClassNotFoundException, SQLException {

		Connection conn = null;
		// JDBCConnection connref=new JDBCConnection();
		// Class.forName("com.mysql.jdbc.Driver");
		PreparedStatement st = null;
		// conn = connref.getOracleConnection();
		// CommonDBHelper commondbhelper=new CommonDBHelper();

		// OrderDao odao=new OrderDao();

		String strJsonBody = "";
		try {

			String jsonResponse;
			// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
			// InetSocketAddress("www-proxy.idc.oracle.com", 80));
			URL url = new URL("https://onesignal.com/api/v1/notifications");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", AUTHTOKEN);
			con.setRequestMethod("POST");
			//if (status.equalsIgnoreCase("notifyservicemanoforders")) {

				strJsonBody = "{" + "\"app_id\": \"7b5d152f-7a97-479e-8461-e876941c55c4\"," // UAT
				//		strJsonBody = "{" + "\"app_id\": \"9b0a5ec6-e306-4aa7-9713-722d8ee1f47c\"," //DEV
						+ "\"include_external_user_ids\": [\"" +playerId+"\"],"
						+ "\"data\": {\"response\": \"notifyorders\"}," + "\"contents\": {\"en\": \"" + message + "\"}," + "\"priority\": \"10\"" + "}";

			//}
			/*
			 * if (status.equalsIgnoreCase("notifybuyer")) {
			 * 
			 * strJsonBody = "{" + "\"app_id\": \"843d9906-6279-49e6-9075-8ce45f8f14c5\"," +
			 * "\"include_player_ids\": [\"" + playerId + "\"]," +
			 * "\"data\": {\"response\": \"notifyorders\"}," + "\"contents\": {\"en\": \"" +
			 * message + " -" + wod + "\"}," + "\"priority\": \"10\"" + "}";
			 * 
			 * }
			 */			
				System.out.println("strJsonBody:\n" + strJsonBody);

			byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			con.setFixedLengthStreamingMode(sendBytes.length);

			OutputStream outputStream = con.getOutputStream();
			outputStream.write(sendBytes);

			int httpResponse = con.getResponseCode();
			System.out.println("httpResponse: " + httpResponse);

			if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
				Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
			} else {
				Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
			}
			System.out.println("jsonResponse:\n" + jsonResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		PushHelper ph = new PushHelper();
		ph.pushToUser("1", "1", "1", "1");
	}
}
