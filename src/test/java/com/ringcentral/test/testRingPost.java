package com.ringcentral.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpsURL;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.ringcentral.common.TestBase;
import com.ringcentral.common.TestConstant;

public class testRingPost extends TestBase {

	@Test
	public void testPost() throws Exception {

		String getResponse = "";
		String endPointURL = getBaseAPIURL() + TestConstant.URIACCOUNT + TestConstant.ACCOUNTID + "/extension/"
				+ TestConstant.EXTID + "/sms";
		String headerToProvide = getAccessToken(TestConstant.CLIENTID, TestConstant.CLIENTSECRET,
				TestConstant.CALLBACK);
		System.out.println("ResultingBase64 " + headerToProvide);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + headerToProvide);
		headers.put("Content-type", TestConstant.CONTENTTYPE);
		HttpResponse response = post(headers, endPointURL, "");
		HttpEntity entity = response.getEntity();
		getResponse = EntityUtils.toString(entity);
		System.out.println("Response is  " + getResponse);
	}
}
