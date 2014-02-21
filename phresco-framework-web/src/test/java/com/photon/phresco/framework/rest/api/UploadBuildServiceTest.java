package com.photon.phresco.framework.rest.api;

import com.photon.phresco.framework.model.TestFlight;

import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;


public class UploadBuildServiceTest  {
	UploadBuildService buildService = new UploadBuildService();


//	@Test
	public void uploadToTestFlight()  {
		String osType = System.getProperty("os.name");
		if (isMac(osType)) {
			TestFlight testFlight = new TestFlight();
			testFlight.setApiToken("d116f9ba20d3f5a53b1b94f5f6dc2b6d_MTQ5Mjk0NTIwMTMtMTItMDUgMDA6MjE6MjYuNzEwNjQw");
			testFlight.setTeamToken("37f3148376f9fc4876b38b706e8f262d_MzA5NDMzMjAxMy0xMi0wNSAwNTo1NjozNy4xNTQ0Nzk");
			testFlight.setNotes("This build was uploaded via the upload API");
			testFlight.setNotify("True");
			testFlight.setDistributionLists("Dev");
			Response response = buildService.uploadToTestFlight("", "TestIphoneProject", "1", "ipa", testFlight);
			Assert.assertEquals(200, response.getStatus());
		}
	}

//	@Test
	public void uploadToTestFlightFailure()  {
		String osType = System.getProperty("os.name");
		if (isMac(osType)) {
			TestFlight testFlight = new TestFlight();
			//testFlight.setApiToken("d116f9ba20d3f5a53b1b94f5f6dc2b6d_MTQ5Mjk0NTIwMTMtMTItMDUgMDA6MjE6MjYuNzEwNjQw");
			testFlight.setTeamToken("37f3148376f9fc4876b38b706e8f262d_MzA5NDMzMjAxMy0xMi0wNSAwNTo1NjozNy4xNTQ0Nzk");
			testFlight.setNotes("This build was uploaded via the upload API");
			testFlight.setNotify("True");
			testFlight.setDistributionLists("Dev");
			//		testFlight.setFilePath("C:\\Documents and Settings\\rohan_l\\My Documents\\TestFlight\\robodemo-sample-1.0.1.apk");
			Response confDashboard = buildService.uploadToTestFlight("", "TestIphoneProject", "1", "ipa", testFlight);
			ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
			System.out.println(responseInfo.getStatus());
			Assert.assertEquals("error", responseInfo.getStatus());
		}
	}

//	@Test
	public void uploadToTestFlightFailure1()  {
		String osType = System.getProperty("os.name");
		if (isMac(osType)) {
			TestFlight testFlight = new TestFlight();
			testFlight.setApiToken("d116f9ba20d3f5a53b1b94f5f6dc2b6d_MTQ5Mjk0NTIwMTMtMTItMDUgMDA6MjE6MjYuNzEddsfdfdfdfwNjQw");
			testFlight.setTeamToken("37f3148376f9fc4876b38b706e8f262d_MzA5NDMzMjAxMy0xMi0wNSAwNTo1NjozNy4xNTQ0Nzk");
			testFlight.setNotes("This build was uploaded via the upload API");
			testFlight.setNotify("True");
			testFlight.setDistributionLists("Dev");
			Response confDashboard = buildService.uploadToTestFlight("", "TestIphoneProject", "1", "ipa", testFlight);
			ResponseInfo<String> responseInfo = (ResponseInfo<String> )confDashboard.getEntity();
			System.out.println(responseInfo.getStatus());
			Assert.assertEquals("failure", responseInfo.getStatus());
		}
	}
	public static boolean isMac(String os) {
		return (os.indexOf("Mac") >= 0) ||(os.indexOf("mac") >= 0)  ;
	}

}
