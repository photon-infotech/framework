/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.QualityUtil;
import com.photon.phresco.framework.model.PdfReportInfo;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class PdfService.
 */
@Path("/pdf")
public class PdfService extends RestBase implements FrameworkConstants, Constants, ServiceConstants, ResponseCodes {

	String status;
	String errorCode;
	String successCode;
	
	/**
	 * Download report.
	 *
	 * @param fromPage the from page
	 * @param reportFileName the report file name
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@GET
	@Path("/downloadReport")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response downloadReport(@QueryParam(REST_QUERY_FROM_PAGE) String fromPage,
			@QueryParam(REST_QUERY_REPORT_FILE_NAME) String reportFileName, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String moduleName) {
		FileInputStream fileInputStream = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			String pdfLOC = "";

			String rootModulePath = "";
			String subModuleName = "";
			
			if (StringUtils.isNotEmpty(moduleName)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = moduleName;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			
			File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			String archivePath = pomFile.getParent() + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator;
			if ((FrameworkConstants.ALL).equals(fromPage)) {
				pdfLOC = archivePath + CUMULATIVE + File.separator + reportFileName;
			} else {
				pdfLOC = archivePath + fromPage + File.separator + reportFileName;
			}
			File pdfFile = new File(pdfLOC);
			if (pdfFile.isFile()) {
				fileInputStream = new FileInputStream(pdfFile);
				return Response.status(Status.OK).entity(fileInputStream).header("Content-Disposition", "attachment; filename=" + pdfFile.getName())
					.build();
			}
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210015;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (FileNotFoundException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210016;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHR200012;
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Delete report.
	 *
	 * @param fromPage the from page
	 * @param reportFileName the report file name
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@DELETE
	@Path("/deleteReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReport(@QueryParam(REST_QUERY_FROM_PAGE) String fromPage,
			@QueryParam(REST_QUERY_REPORT_FILE_NAME) String reportFileName, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String moduleName) {
		ResponseInfo responseData = new ResponseInfo();
		try {
			String pdfLOC = "";
//			String applicationHome = FrameworkServiceUtil.getApplicationHome(appDirName);
//			if (StringUtils.isNotEmpty(moduleName)) {
//				applicationHome = applicationHome + File.separator + moduleName;
//			}
			
			String rootModulePath = "";
			String subModuleName = "";
			
			if (StringUtils.isNotEmpty(moduleName)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = moduleName;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			String archivePath = pomFile.getParent() + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator;
			if ((FrameworkConstants.ALL).equals(fromPage)) {
				pdfLOC = archivePath + CUMULATIVE + File.separator + reportFileName;
			} else {
				pdfLOC = archivePath + fromPage + File.separator + reportFileName;
			}
			File pdfFile = new File(pdfLOC);
			if (pdfFile.isFile()) {
				boolean reportDeleted = pdfFile.delete();
				if (reportDeleted) {
					status = RESPONSE_STATUS_SUCCESS;
					successCode = PHR200013;
					ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
							null, status, successCode);
					return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
							.build();
				} else {
					status = RESPONSE_STATUS_FAILURE;
					errorCode = PHR210017;
					ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
							null, status, errorCode);
					return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
							"*").build();
				}
			}
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHR200014;
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Show generate pdf popup.
	 *
	 * @param appDirName the app dir name
	 * @param fromPage the from page
	 * @return the response
	 */
	@GET
	@Path("/showPopUp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showGeneratePdfPopup(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_FROM_PAGE) String fromPage, @QueryParam(REST_QUERY_MODULE_NAME) String moduleName, @Context HttpServletRequest request) {
		ResponseInfo<Map<String, Object>> responseData = new ResponseInfo<Map<String, Object>>();
		 Map<String, Object> paramMap = new HashMap<String, Object>(8);
		try {
			List<PdfReportInfo> existingPDFs = null;
			boolean isReportAvailable = false;
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			FrameworkServiceUtil futil = new FrameworkServiceUtil();

			String rootModulePath = "";
			String subModuleName = "";
			
			if (StringUtils.isNotEmpty(moduleName)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = moduleName;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			
			File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			existingPDFs = getExistingPDFs(fromPage, pomFile.getParent());
				
			// is sonar report available
			if ((FrameworkConstants.ALL).equals(fromPage)) {
				isReportAvailable = futil.isSonarReportAvailable(frameworkUtil, request, rootModulePath, subModuleName);
			}

			// is test report available
			if (!isReportAvailable) {
				isReportAvailable = futil.isTestReportAvailable(frameworkUtil, rootModulePath, subModuleName);
			}
			
			paramMap.put("value", isReportAvailable);
			if (existingPDFs != null ) {
				paramMap.put("json", existingPDFs);
				ResponseInfo<Map<String, Object>> finalOutput = responseDataEvaluation(responseData, null,
						paramMap, RESPONSE_STATUS_SUCCESS, PHR200027);
				return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN, "*").build();
			}
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200015;
			ResponseInfo<Map<String, Object>> finalOutput = responseDataEvaluation(responseData, null,
					null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210019;
			ResponseInfo<Map<String, Object>> finalOutput = responseDataEvaluation(responseData, e,
					null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the existing pdfs.
	 *
	 * @param fromPage the from page
	 * @param appInfo the app info
	 * @return the existing pd fs
	 * @throws PhrescoException the phresco exception
	 */
	private List<PdfReportInfo> getExistingPDFs(String fromPage, String targetDir) {
		List<PdfReportInfo> pdfList = new ArrayList<PdfReportInfo>();
		
		// popup showing list of pdf's already created
		String pdfDirLoc = targetDir + File.separator;
		if (StringUtils.isEmpty(fromPage) || FROMPAGE_ALL.equals(fromPage)) {
			pdfDirLoc =  pdfDirLoc + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + CUMULATIVE;
		} else {
			pdfDirLoc = pdfDirLoc + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + fromPage;
		}
		File pdfFileDir = new File(pdfDirLoc);
		if (pdfFileDir.isDirectory()) {
			File[] children = pdfFileDir.listFiles(new FileNameFileFilter(FrameworkConstants.DOT + PDF));
			QualityUtil util = new QualityUtil();
			if (children != null) {
				util.sortResultFile(children);
			}
			for (File child : children) {
				PdfReportInfo report= new PdfReportInfo();
				// three value
				DateFormat yymmdd = new SimpleDateFormat("MMM dd yyyy HH.mm");
				
				if (child.toString().contains("detail")) {
					report.setTime(yymmdd.format(child.lastModified()));
					report.setType("detail");
					report.setFileName(child.getName());
				} else if (child.toString().contains("crisp")) {
					report.setTime(yymmdd.format(child.lastModified()));
					report.setType("crisp");
					report.setFileName(child.getName());
				}
				pdfList.add(report);
			}
		}
		return pdfList;
	}

	/**
	 * The Class FileNameFileFilter.
	 */
	public class FileNameFileFilter implements FilenameFilter {
		
		/** The filter_. */
		private String filter_;

		/**
		 * Instantiates a new file name file filter.
		 *
		 * @param filter the filter
		 */
		public FileNameFileFilter(String filter) {
			filter_ = filter;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith(filter_);
		}
	}
}
