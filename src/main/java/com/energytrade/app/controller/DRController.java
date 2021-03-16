package com.energytrade.app.controller;

import java.util.Hashtable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.FileOutputStream;
import java.util.HashMap;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.energytrade.app.services.DRService;
import com.energytrade.app.services.SellOrderService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
public class DRController extends AbstractBaseController
{
    @Autowired
    private DRService drservice;
    
	@RequestMapping(value=REST+"uploadEventSet/{location}/{userId}",method = RequestMethod.POST,headers="Accept=application/json")
    public void  uploadEventSet(@RequestBody HashMap<String,String> inputDetails, @PathVariable("location") String location, @PathVariable("userId") int userId)
    {
        try
        {
        	String imageDataArr=inputDetails.get("eventSet");
            //This will decode the String which is encoded by using Base64 class
            byte[] imageByte=Base64.decodeBase64(imageDataArr);
            
            String directory="C:\\Users\\THINKPAD\\Documents\\Works\\"+"sample.xlsx";
            drservice.createEventSet(directory, imageByte,location, userId);
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            //return "error = "+e;
        }

    }
}
