package com.ringcentral.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.ringcentral.common.TestBase;
import com.ringcentral.common.TestConstant;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;



public class testRingSampleTestCase extends TestBase {
	
	Response response;
	String toTest    = "";
	String valToTest = "";
	
	
	@Test (priority = 1)
	public void testStatusCode(){
		
	}
	
	
	@Test (priority = 2)
	public void testVersionApi(){
		
		toTest = "uriString";
		
		
		response= getTheGetResponseUsingRestAssured(TestConstant.URIVERSION);
		valToTest = getParticularFromResponseBodyUsingRestAssured(toTest, response);
		assertEquals(valToTest, TestConstant.EXPECTEDVERSION);
		
		
		//RestAssured.given().accept("application/json").when().get(BaseUrl+"/restapi/v1.0/").then().statusCode(200);
	}

}
