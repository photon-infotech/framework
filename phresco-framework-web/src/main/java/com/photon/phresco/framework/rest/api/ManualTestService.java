package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;

import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.model.ManualTestResult;
import com.photon.phresco.framework.model.TestSuite;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/manual")
public class ManualTestService extends RestBase {
	
	@GET
	@Path("/manualTemplate")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getManualTemplate(@QueryParam("fileType") String fileType) throws PhrescoException {
        try {
        	InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("helios_manul_test_template." + fileType);
        	if(resourceStream == null) {
        		return Response.status(Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").entity(resourceStream).build();
        	}
        	return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*").entity(resourceStream).build();
        } catch (Exception e) {
        	throw new PhrescoException(e);
		}
	}
	
	@POST
	@Path("/uploadTemplate")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response uploadManualTemplate(@Context HttpServletRequest request, @QueryParam("appDirName") String appDirName) 
		throws PhrescoException {
        try {
        	PomProcessor pomProcessor = FrameworkUtil.getInstance().getPomProcessor(appDirName);
        	String manualTestReportPath = pomProcessor.getProperty("phresco.manualTest.testcase.path");
        	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    		builder.append(appDirName);
    		builder.append(File.separator);
    		builder.append(manualTestReportPath);
    		ServletInputStream inputStream = request.getInputStream();
    		File file = new File(builder.toString() + "/helios_manul_test_template.xls");
    		FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (Exception e) {
        	throw new PhrescoException(e);
		}
		return Response.status(Status.OK).build();
	}
	
	@GET
	@Path("/testsuites")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestSuites(@QueryParam("appDirName") String appDirName) throws PhrescoException {
		ResponseInfo<List<TestSuite>> responseData = new ResponseInfo<List<TestSuite>>();
		List<TestSuite> readManualTestSuiteFile = new ArrayList<TestSuite>();
		ManualTestResult createManualTestResult = null;
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String manualTestDir = getManualTestReportDir(appDirName);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(manualTestDir);
			if (new File(sb.toString()).exists()) {
				readManualTestSuiteFile = frameworkUtil.readManualTestSuiteFile(sb.toString());
			} 
			createManualTestResult = createManualTestResult(readManualTestSuiteFile);
			ResponseInfo<List<TestSuite>> finalOutput = responseDataEvaluation(responseData, null, "Testsuites returned Successfully", createManualTestResult);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<List<TestSuite>> finalOutput = responseDataEvaluation(responseData, e, "Failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} 
	}
	
	@GET
	@Path("/testcases")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestCase(@QueryParam("appDirName") String appDirName, @QueryParam("testsuitename") String testsuitename) throws PhrescoException {
		ResponseInfo<List<com.photon.phresco.commons.model.TestCase>> responseData = new 
			ResponseInfo<List<com.photon.phresco.commons.model.TestCase>>();
		List<com.photon.phresco.commons.model.TestCase> readTestCase = null;
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			String manualTestDir = getManualTestReportDir(appDirName);
			StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(manualTestDir);
			readTestCase = frameworkUtil.readManualTestCaseFile(sb.toString(), testsuitename, null);
			ResponseInfo<List<com.photon.phresco.commons.model.TestCase>> finalOutput = responseDataEvaluation(responseData, null, "Testcases returned Successfully", readTestCase);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			ResponseInfo<List<com.photon.phresco.commons.model.TestCase>> finalOutput = responseDataEvaluation(responseData, e, "Failed", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@POST
	@Path("/testsuitess")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response createTestSuite(@QueryParam("testSuiteName") String testSuiteName, @QueryParam("appDirName") String appDirName) throws PhrescoException {
		FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
		String path = getManualTestReportDir(appDirName);
		StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(path);
		String cellValue[] = {"", "", testSuiteName, "", "", "", "", "", "", "", "", "", ""};
		frameworkUtil.addNew(sb.toString(), testSuiteName, cellValue);
		return Response.status(Status.OK).entity("TestSuite Added Successfully").build();
	}
	
	@POST
	@Path("/testcases")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response createTestCase(com.photon.phresco.commons.model.TestCase testCase, @QueryParam("testSuiteName") String testSuiteName,
			@QueryParam("appDirName") String appDirName) throws PhrescoException {
		FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
		String path = getManualTestReportDir(appDirName);
		StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(path);
		String cellValue[] = {"", testCase.getFeatureId(), "",testCase.getTestCaseId(), testCase.getDescription(), testCase.getSteps(), "", "",
				testCase.getExpectedResult(), testCase.getActualResult(), testCase.getStatus(), "", "", testCase.getBugComment()};
		frameworkUtil.addNewTestCase(sb.toString(), testSuiteName,cellValue, testCase.getStatus());
		return Response.status(Status.OK).entity("TestCase Added Successfully").build();
	}
	
	@PUT
	@Path("/testcases")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response updateTestCase(com.photon.phresco.commons.model.TestCase testCase, @QueryParam("testSuiteName") String testSuiteName,
			@QueryParam("appDirName") String appDirName) throws PhrescoException {
		FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
		String path = getManualTestReportDir(appDirName);
		StringBuilder sb = new StringBuilder(Utility.getProjectHome()).append(appDirName).append(path);
		frameworkUtil.readManualTestCaseFile(sb.toString(), testSuiteName, testCase);
		return Response.status(Status.OK).entity("TestCase Updated Successfully").build();
	}
	
	private ManualTestResult createManualTestResult(List<TestSuite> readManualTestSuiteFile) {
		ManualTestResult mtr = new ManualTestResult();
		float totalTestSuccess = 0;
		float totalFailure = 0;
		float totalNotApplicable = 0;
		float totalBlocked = 0;
		float totalNotExecuted = 0;
		float total = 0;
		float totalTestCoverage = 0;
		for (TestSuite testSuite : readManualTestSuiteFile) {
			totalTestSuccess =  totalTestSuccess + testSuite.getSuccess();
			totalFailure = totalFailure + testSuite.getFailures();
			totalNotApplicable = totalNotApplicable + testSuite.getNotApplicable();
			totalBlocked = totalBlocked + testSuite.getBlocked();
			totalNotExecuted = totalNotExecuted + testSuite.getNotExecuted();
			total = total + testSuite.getTotal();
			totalTestCoverage = totalTestCoverage + testSuite.getTestCoverage();
		}
		mtr.setTestSuites(readManualTestSuiteFile);
		mtr.setTotalSuccess(totalTestSuccess);
		mtr.setTotalFailure(totalFailure);
		mtr.setTotalNotApplicable(totalNotApplicable);
		mtr.setTotalBlocked(totalBlocked);
		mtr.setTotalNotExecuted(totalNotExecuted);
		mtr.setTotal(total);
		mtr.setTotalTestCoverage(totalTestCoverage);
		return mtr;
	}

	private String getManualTestReportDir(String appDirName) throws PhrescoException {
        try {
			return FrameworkUtil.getInstance().getPomProcessor(appDirName).getProperty(Constants.POM_PROP_KEY_MANUALTEST_RPT_DIR);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
}
