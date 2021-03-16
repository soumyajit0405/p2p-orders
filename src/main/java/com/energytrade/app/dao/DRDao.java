package com.energytrade.app.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.dto.AllContractDto;
import com.energytrade.app.dto.AllSellOrderDto;
import com.energytrade.app.dto.AllUserByAdminDto;
import com.energytrade.app.dto.AllUserDto;
import com.energytrade.app.dto.SearchOrdersDto;
import com.energytrade.app.model.AllContract;
import com.energytrade.app.model.AllEvent;
import com.energytrade.app.model.AllEventSet;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.NonTradehourStatusPl;
import com.energytrade.app.model.NotificationRequestDto;
import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.model.UserDevice;
import com.energytrade.app.util.ApplicationConstant;
import com.energytrade.app.util.CommonUtility;
import com.energytrade.app.util.CustomMessages;
import com.energytrade.app.util.PushHelper;
import com.energytrade.app.util.ValidationUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



@Transactional
@Repository
public class DRDao extends AbstractBaseDao
{
	@Autowired
    EventSetRepository eventsetrepo;
	
	@Autowired
    EventRepository eventrepo;
	
	public void createEventSet ( String filePath, byte[] imageByte, String location, int userId ) {
		
		try {
			AllEventSet eventSet = createEventSet(location, userId); 
			FileInputStream file=createFile(filePath, imageByte, eventSet);
			// createEventSetObjects(file);
            //Create Workbook instance holding reference to .xlsx file
                        
		}
		catch( Exception e) {
			
		}
		finally {
			
		}
	}
	
	public AllEventSet createEventSet (String location,int userId) {
		AllEventSet alleventset1 = new AllEventSet();
		try {
			int count= eventsetrepo.getEventSetCount();
			Date d= new Date();
			int year = d.getYear();
			int month = d.getMonth();
			int date= d.getDate();
			AllUser alluser = eventsetrepo.getUserById(userId);
			AllEventSet alleventset = new AllEventSet();
			alleventset.setName(location+year+month+date);
			alleventset.setAllUser(alluser);
			alleventset.setEventSetId(count+1);
			  eventsetrepo.saveAndFlush(alleventset);
			 alleventset1= eventsetrepo.getEventSet(count+1);
			//return alleventset1;
		} catch(Exception e) {
			
		}finally {
			
		}
		return alleventset1;
		
	}
	public FileInputStream createFile( String filePath, byte[] imageByte, AllEventSet eventSet) throws IOException {
		
		FileInputStream file = null ;
		try {
			new FileOutputStream(filePath).write(imageByte);
			 file = new FileInputStream(new File(filePath));
			 createEventSetObjects(file,eventSet);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			file.close();
		}
		return file;
	}
	
	public void createEventSetObjects( FileInputStream file, AllEventSet alleventset ) throws IOException {
		
		List<AllEventSet> listOfEventSets = new ArrayList<AllEventSet>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            Row firstrow = rowIterator.next();
            int count=0;
            int rowCount = eventrepo.getEventCount();
            List<AllEvent> listOfEvents=new ArrayList<AllEvent>();
            while (rowIterator.hasNext()) 
            {
            	count++;
            	rowCount++;
            	AllEvent allevent = new AllEvent();
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
//                 Cell cell1= row.getCell(0);
//                 System.out.println(cell1.getStringCellValue());
                 Cell cell2= row.getCell(1);
                 System.out.println(cell2.getStringCellValue());
                 Cell cell3= row.getCell(2);
                 System.out.println(cell3.getNumericCellValue());
                 Cell cell4= row.getCell(3);
                 if(cell4 !=null ) {
                	 System.out.println(cell4.getNumericCellValue());	 
                 }
                 allevent.setEventId(rowCount);
                 allevent.setEventName(alleventset.getName()+count);
                 allevent.setAllEventSet(alleventset);
                 allevent.setPlannedPower(cell3.getNumericCellValue());
                 if(cell4 !=null ) {
                	 allevent.setExpectedPrice(cell4.getNumericCellValue());    
                 }
                               
               System.out.println("");
               listOfEvents.add(allevent);
            }
            eventrepo.saveAll(listOfEvents);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			file.close();
		}
	}
}	

