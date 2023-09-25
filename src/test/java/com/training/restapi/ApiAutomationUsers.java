package com.training.restapi;
//Static hamcrest method: equalto, hasItem,hasItems, startsWith, endsWith, containsString, equalToIgnoringCase

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.* ;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.Path;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class ApiAutomationUsers 
{
	String sHostUrl = "https://jsonplaceholder.typicode.com";
	String sEPUsers= "/users"; // path parameter ; you can also add query parameter after the path with"?"
	String sURI= " ";

	@Test
	public void validateUser() throws IOException
	{
		sURI = sHostUrl + sEPUsers;
		System.out.println(sURI);
		RestAssured.baseURI = sURI;
		
		Response response = RestAssured.given().get();
		//response.prettyPrint();
		
		int statusvalidation= response.getStatusCode();
		System.out.println(statusvalidation);
		//String actualusername = response.jsonPath().get("name[0]"); 
		//System.out.println(actualusername);
		
		String filepath = "/Users/harneetkaur/eclipse-workspace/ApiAutomation/TestData/ExpectedResponseUsers.json";
		String fileReadytoRead  = readFileReturnString(filepath);//converting Json file into string file
		 
		String expectedusername= JsonPath.read(fileReadytoRead,"$.[0].name");
		
		System.out.println(expectedusername);
	    //assertEquals(actualusername, expectedusername);
		String expectedcity= JsonPath.read(fileReadytoRead,"$.[3].address.city");
		
		String expectedemail= JsonPath.read(fileReadytoRead,"$.[0].email");
		String expectedIgnoreCase=JsonPath.read(fileReadytoRead,"$.[9].name");
	    
	    response.then().body("name[0]", equalTo(expectedusername));//method 1:hamcrest
		response.then().body("email[0]",startsWith("S") ); //method 2:hamcrest
		response.then().body("address.city",hasItem("South Christy") ); //method 3:hamcrest :no need for list shown below
		response.then().body("address.city",hasItem(expectedcity));//instead of hardcoding as above
		response.then().body("address.city",hasItems("Gwenborough","McKenziehaven")) ;//method 4:hamcrest
		response.then().body("username[2]",containsString("Samantha"));//method5 :hamcrest
		response.then().body("name[9]", equalToIgnoringCase(expectedIgnoreCase));//method 6
		/*
		 * List<String>addresscities = response.jsonPath().getList("address.city");//if
		 * you want list of all cities for(String addresscity : addresscities ) {
		 * System.out.println(addresscity); }
		 */
		 
		
	}


	private String readFileReturnString(String filepath) throws IOException
	{
		//this method takes filePath and decodes it to byte
		byte[]encoded =  Files.readAllBytes(Paths.get(filepath));//import json jayway
		return new String(encoded,  StandardCharsets.UTF_8);
	}

} 
