package com.ringcentral.common;

import java.io.IOException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * 
 *
 */

public class TestBase {
	
	
	private static String BaseAPIURL = "";
	private static Response ResponceCaptured ;
	private static ResponseBody responseBody;
	private static String value = "";
	
	
	public TestBase(){
		doSetUp();
	}
	
	public static String getBaseAPIURL(){
		 return BaseAPIURL;
	}
	
	private final void doSetUp(){
		setEnvironmentVariables();
		
	}
	
	public static void setBaseAPIURL(String BaseAPIURL){
		
		TestBase.BaseAPIURL = BaseAPIURL;
	}
	
	
	
	
	protected void setEnvironmentVariables(){
		
		try{
		setBaseAPIURL(TestConstant.BASEAPI);
		
		Properties prop = new Properties();
		//RestAssured 
		
		}catch(Exception ex){
			
		}
		
	}
	
	
	
	public  Response getTheGetResponseUsingRestAssured(String uri){
		RestAssured.baseURI = getBaseAPIURL();
		RequestSpecification requestSpecification = RestAssured.given();
		ResponceCaptured = requestSpecification.get(uri);
		return ResponceCaptured;
		
	}
	
	/**
	 * 
	 * @param whichKeyValue
	 * @param response
	 */
	
	public String getParticularFromResponseBodyUsingRestAssured(String whichKeyValue,Response response){
		responseBody = response.getBody();
		System.out.println("BODY  " +responseBody.asString());
		JsonPath jsonPath = response.jsonPath();
		value = jsonPath.get(whichKeyValue);
		return value;
		
		
	}
	
	/**
	 * getBastAuthWithBase64
	 * @param email
	 * @param pwd
	 * @return
	 */
	
	

	public String getBaseAuth(String clientID, String clientSecret) {
		String str = clientID + ":" + clientSecret;
		byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
		return new String(bytesEncoded);

	}

	
	public HttpResponse post(HashMap<String, String> headers, String url, String content) throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		System.out.println("End Point URL: " + url);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
			post.addHeader(entry.getKey(), entry.getValue());
		}

		StringEntity entity = new StringEntity(content);
		System.out.println("content: " + content);
		//System.out.println("entity: " +entity);
		
		post.setEntity(entity);
		int count = 0;
		int maxTries = 3;
		HttpResponse response = null;
		while (true) {
			try {
				Thread.sleep(1000);
				response = client.execute(post);
				break;
			} catch (SocketException e) {
				// handle exception
				if (++count == maxTries)
					throw e;
			}
		}

		// HttpResponse response = client.execute(post);
		return response;
	}
	
	/**
	 * 
	 * @param headers
	 * @param url
	 * @param content
	 * @return
	 * @throws Exception
	 */
	
	public HttpResponse get(HashMap<String, String> headers, String url, String content) throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpGet post = new HttpGet(url);
		System.out.println("End Point URL: " + url);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			// System.out.println(entry.getKey() + " : " + entry.getValue());
			post.addHeader(entry.getKey(), entry.getValue());
		}

		int count = 0;
		int maxTries = 3;
		HttpResponse response = null;
		while (true) {
			try {
				Thread.sleep(1000);
				response = client.execute(post);
				break;
			} catch (SocketException e) {
				// handle exception
				if (++count == maxTries)
					throw e;
			}
		}

		return response;
	}


	/**
	 * 
	 * @param headers
	 * @param url
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public HttpResponse delete(HashMap<String, String> headers, String url) throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpDelete delete = new HttpDelete(url);
		System.out.println("End Point URL: " + url);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			// System.out.println(entry.getKey() + " : " + entry.getValue());
			delete.addHeader(entry.getKey(), entry.getValue());
		}

		int count = 0;
		int maxTries = 3;
		HttpResponse response = null;
		while (true) {
			try {
				Thread.sleep(1000);
				response = client.execute(delete);
				break;
			} catch (SocketException e) {
				// handle exception
				if (++count == maxTries)
					throw e;
			}
		}

		return response;
	}

	
	public  String readTheInput(String whatYouExpect){
		
		Scanner sc = new Scanner(System.in);
		System.out.println(whatYouExpect);
		String string = sc.nextLine();
		
		
		return string;
		
	}
	


	
	public String getAccessToken(String clientID, String clientsecret, String callBackURL) throws Exception {
		
		String endURL = TestConstant.BASEAPI+"/restapi/oauth/token";
		
		
		String contentType = "application/json";
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("accept", contentType);
	    headers.put("Authorization", "Basic " +getBaseAuth(TestConstant.EMAIL,TestConstant.PWD));
		headers.put("client_id", clientID);
		//headers.put("response_type", "code");
		//headers.put("redirect_uri", "https://fadedthunder.com");
	    headers.put("grant_type", TestConstant.PWD);
	    headers.put("username", "14242249945");
	    headers.put("pasword", TestConstant.PWD);
	    System.out.println("Headers: " +headers);
		
		

		
		
		HttpResponse response = post(headers, endURL,"");
		System.out.println("RESPONSE " +response);
		
		/*assertTrue("Expected 200 status should be displayed while getting Bearer toke from 'generateaccesstoken' call",
				response.getStatusLine().getStatusCode() == 200);*/
		String responceString = (readJsonFromResponse(response));

		Object responseObject = null;
		responseObject = new org.json.JSONObject(responceString);
		JSONObject obj = (org.json.JSONObject) responseObject;
		Iterator<String> keys = obj.keys();
		String access_token = "";
		while (keys.hasNext()) {
			String key = keys.next();
			if (key.contains("access_token")) {
				access_token = (String) obj.get(key);
				return access_token;
			}
		}

		return access_token;
	}
	
	
	public String getTimeStamp() throws java.text.ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date();
		long gmtMilliSeconds = (long) date.getTime();
		return Long.toString(gmtMilliSeconds / 1000);
	}

	public String getSignature(String consumerKey, String consumerSecret, String callBackURL, String timeStamp)
			throws Exception {
		// TODO Auto-generated method stub
		String hash = null;
		String mixcode = null;
		String Hash = null;

		mixcode = callBackURL + consumerKey + timeStamp;
		hash = getSHA256Hash(consumerSecret, mixcode);
		return hash;
	}

	public static String getSHA256Hash(String consumerSecret, String mixcode) {

		String hashedStr = "";

		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(consumerSecret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			hashedStr = Base64.encodeBase64String(sha256_HMAC.doFinal(mixcode.getBytes()));
			// System.out.println(hashedStr);
		} catch (Exception e) {
			System.out.println("Error");
		}

		return hashedStr;
	}
	
	

	public String readJsonFromResponse(HttpResponse response) throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		// System.out.println("response: " + responseString);
		return responseString;
	}

	public Date cvtToGmt(Date date) {
		TimeZone tz = TimeZone.getDefault();
		Date ret = new Date(date.getTime() - tz.getRawOffset());

		// if we are now in DST, back off by the delta. Note that we are
		// checking the GMT date, this is the KEY.
		if (tz.inDaylightTime(ret)) {
			Date dstDate = new Date(ret.getTime() - tz.getDSTSavings());

			// check to make sure we have not crossed back into standard time
			// this happens when we are on the cusp of DST (7pm the day before
			// the change for PDT)
			if (tz.inDaylightTime(dstDate)) {
				ret = dstDate;
			}
		}
		return ret;
	}

	
}
	
	