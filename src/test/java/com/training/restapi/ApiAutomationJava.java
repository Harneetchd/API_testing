package com.training.restapi;

import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiAutomationJava 
{
	String sHostUrl ="https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	String sEPLogin = "/login";
	String sEPgetData = "/getdata";
	String sUrI = " ";// Universal Resource Identifier
	Response response;
	

	@Test
	public void login()
	{
		sUrI= sHostUrl + sEPLogin;
		System.out.println(sUrI);//  Universal Resource Identifier 
		RestAssured.baseURI = sUrI;// assign to baseURI
		String token;
		
		response = RestAssured.given().body("{\"username\": \"chdniti@gmail.com\", \n"
				+ "\"password\": \"chdniti123\"\n"
				+ "} ").when().contentType("application/json").post();//post operation
		
		int status = response.getStatusCode();// returns integer value
		System.out.println(status);
		 response.prettyPrint(); // prints the pretty format
		 
		token = response.jsonPath().get("token[0]");
		System.out.println(token);// printing the token through json path traverse
		
		
		if(status ==201)
		{
			System.out.println("Test Passed");
		}else
		{
			System.out.println("Test Failed");
		}
		
		sUrI = sHostUrl + sEPgetData; // going to next URI: get data after login 
		RestAssured.baseURI = sUrI;
		 
		Map<String,String> headerdata= new HashMap<String,String>();// headers have two values: Hashmap used
		headerdata.put("content-type", "application/json");
		headerdata.put("token", token);
		
		response=RestAssured.given().headers(headerdata).get();//get all the data 
		response.prettyPrint();  
		
	    List<String>accountnumbers=response.jsonPath().getList("accountno");//if you want list of all account numbers 
		for(String accnumber:accountnumbers) 
		{
			System.out.println(accnumber);
		}
		
	} 
}
