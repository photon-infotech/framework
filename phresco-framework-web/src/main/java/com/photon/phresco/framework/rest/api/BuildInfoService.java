/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.impl.ConfigurationReader;
import com.photon.phresco.framework.model.MinifyInfo;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class BuildInfoService.
 */
@Path("/buildinfo")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BuildInfoService extends RestBase implements FrameworkConstants, ServiceConstants, ResponseCodes {
	
	/**
	 * List of buildinfos.
	 *
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module) {
		ResponseInfo<List<BuildInfo>> responseData = new ResponseInfo<List<BuildInfo>>();
		try {
			String rootModulePath = "";
			String subModuleName = "";
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			String buildInfoFilePath = getBuildInfoFilePath(rootModulePath, subModuleName);
			File buildInfoFile = new File(buildInfoFilePath);
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			List<BuildInfo> builds = applicationManager.getBuildInfos(buildInfoFile);
			if (CollectionUtils.isEmpty(builds)) {
				ResponseInfo<List<BuildInfo>> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_SUCCESS, PHR700010);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
						.build();
			}
			Collections.sort(builds, new BuildComparator());
			ResponseInfo<List<BuildInfo>> finalOutput = responseDataEvaluation(responseData, null,
					builds, RESPONSE_STATUS_SUCCESS, PHR700001);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<BuildInfo>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR710002);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	@GET
	@Path("/minifer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response minifer(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module) {
		ResponseInfo<List<BuildInfo>> responseData = new ResponseInfo<List<BuildInfo>>();
		try {
			String rootModulePath = "";
			String subModuleName = "";
			
//			appDirName = StringUtils.isNotEmpty(module) ? appDirName + File.separator + module : appDirName;
			
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			
//			String pomPath = FrameworkServiceUtil.getAppPom(appDirName);
			File pomPath = Utility.getPomFileLocation(rootModulePath, subModuleName);
			PomProcessor pomProcessor = new PomProcessor(pomPath);
			com.phresco.pom.model.Plugin.Configuration pluginConfig = pomProcessor.getPlugin(MINIFY_PLUGIN_GROUPID,
					MINIFY_PLUGIN_ARTFACTID).getConfiguration();
			// To check for availability of minification plugin in pom.xml
			if (pluginConfig != null) {
				List<Element> elements = pluginConfig.getAny();
				if (elements != null) {
					for (Element element : elements) {
						List<MinifyInfo> includesFiles = includesFiles(element, appDirName);// To read already minified details from pom
						if(CollectionUtils.isNotEmpty(includesFiles)) {
							ResponseInfo<List<BuildInfo>> finalOutput = responseDataEvaluation(responseData, null,
									includesFiles, RESPONSE_STATUS_SUCCESS, PHR700011);
							return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
						}
					}
				}
			}
			ResponseInfo<List<BuildInfo>> finalOutput = responseDataEvaluation(responseData, null,
					null, RESPONSE_STATUS_SUCCESS, PHR700012);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<List<BuildInfo>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR710022);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoPomException e) {
			ResponseInfo<List<BuildInfo>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR710023);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	

	/**
	 * Builds the info zip.
	 *
	 * @param appDirName the app dir name
	 * @param buildNumber the build number
	 * @return the response
	 */
	@GET
	@Path("/downloadBuild")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response buildInfoZip(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_BUILD_NUMBER) int buildNumber, @QueryParam(REST_QUERY_MODULE_NAME) String module) {
		String rootModulePath = "";
		String subModuleName = "";
		
		if (StringUtils.isNotEmpty(module)) {
			rootModulePath = Utility.getProjectHome() + appDirName;
			subModuleName = module;
		} else {
			rootModulePath = Utility.getProjectHome() + appDirName;
		}
		
		InputStream fileInputStream = null;
		ResponseInfo responseData = new ResponseInfo();
		StringBuilder builder = new StringBuilder();
		try {
			String buildInfoFilePath = getBuildInfoFilePath(rootModulePath, subModuleName);
			File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			String fileName = "";
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			BuildInfo buildInfo = applicationManager.getBuildInfo(buildNumber, buildInfoFilePath);
			if (buildInfo.getBuildNo() == buildNumber) {
				String deliverables = buildInfo.getDeliverables();
				fileName = buildInfo.getBuildName();
				if (StringUtils.isEmpty(deliverables)) {
					builder.append(pomFile.getParent());
					builder.append(File.separator);
					String moduleName = buildInfo.getModuleName();
					if (StringUtils.isNotEmpty(moduleName)) {
						builder.append(moduleName);
						builder.append(File.separator);
					}
					builder.append(BUILD_DIR);
					builder.append(File.separator);
					builder.append(buildInfo.getBuildName());
				} else {
					builder.append(buildInfo.getDeliverables());
					fileName = fileName.substring(fileName.lastIndexOf(FrameworkConstants.FORWARD_SLASH) + 1);
					boolean status = fileName.endsWith(APKLIB) || fileName.endsWith(APK);
					if (status) {
						fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ARCHIVE_FORMAT;
					} else {
						fileName = FilenameUtils.removeExtension(fileName) + ARCHIVE_FORMAT;
					}
				}
				fileInputStream = new FileInputStream(new File(builder.toString()));
			}
			return Response.status(Status.OK).entity(fileInputStream).header("Content-Disposition", "attachment; filename=" + fileName).build();
		} catch (FileNotFoundException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR710003);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR710011);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	private String getBuildInfoFilePath(String rootModulePath, String subModuleName) throws PhrescoException {
		File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
		StringBuilder buildInfoFilePath = new StringBuilder(pomFile.getParent());
		buildInfoFilePath.append(File.separator).append(BUILD_DIR).append(File.separator).append(BUILD_INFO_FILE_NAME);
		
		return buildInfoFilePath.toString();
	}

	/**
	 * Delete build.
	 *
	 * @param buildNumbers the build numbers
	 * @param projectId the project id
	 * @param customerId the customer id
	 * @param appId the app id
	 * @return the response
	 */
	@DELETE
	@Path("/deletebuild")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBuild(String[] buildNumbers, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module) {
		String rootModulePath = "";
		String subModuleName = "";
		
		if (StringUtils.isNotEmpty(module)) {
			rootModulePath = Utility.getProjectHome() + appDirName;
			subModuleName = module;
		} else {
			rootModulePath = Utility.getProjectHome() + appDirName;
		}
		
		ResponseInfo responseData = new ResponseInfo();
		try {
			int[] buildInts = new int[buildNumbers.length];
			for (int i = 0; i < buildNumbers.length; i++) {
				buildInts[i] = Integer.parseInt(buildNumbers[i]);
			}
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			applicationManager.deleteBuildInfos(rootModulePath, buildInts, subModuleName);
		} catch (PhrescoException e) {
			ResponseInfo finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR710004);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, RESPONSE_STATUS_SUCCESS, PHR700002);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/checkstatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkStatus(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_MODULE_NAME) String module){
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		String host = null, protocol = null, environmentName = null, port = null;
		Boolean connectionAlive = false;
		FileReader readers = null;
		try {
			String rootModulePath = "";
			String subModuleName = "";
			
			if (StringUtils.isNotEmpty(module)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = module;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
			File pomFileLocation = Utility.getPomFileLocation(rootModulePath, subModuleName);
			File configurationInfo = new File(dotPhrescoFolderPath+ File.separator + PHRESCO_ENV_CONFIG_FILE_NAME);
			File runAgainsSourceInfo = new File(dotPhrescoFolderPath+ File.separator + RUNAGNSRC_INFO_FILE);
			File logFile = new File(pomFileLocation.getParent() + File.separator + DO_NOT_CHECKIN_DIR + File.separator + LOG_DIR + File.separator + RUN_AGS_LOG_FILE);
			if (!runAgainsSourceInfo.exists()) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, connectionAlive, RESPONSE_STATUS_SUCCESS, PHR710005);
			return Response.status(Response.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
			boolean checkForFailureInLog = checkForFailureInLog(logFile);
			if (checkForFailureInLog) {
				ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, connectionAlive, RESPONSE_STATUS_SUCCESS, PHR710005);
				return Response.status(Response.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
				}
			
				readers = new FileReader(runAgainsSourceInfo);
				JSONObject jsonobject = new JSONObject();
				JSONParser parser = new JSONParser();
				jsonobject = (JSONObject)parser.parse(readers);
				environmentName = (String)jsonobject.get(SESSION_ENV_NAME);
				ConfigurationReader reader = new ConfigurationReader(configurationInfo);
				List<Configuration> config = reader.getConfigurations(environmentName, FrameworkConstants.SERVER);
				for (Configuration configs : config) {
					Properties properties = configs.getProperties();
					host = properties.getProperty(SERVER_HOST);
					port = properties.getProperty(SERVER_PORT);
					protocol = properties.getProperty(PROTOCOL);
				}
			connectionAlive = isConnectionAlive(protocol, host, Integer.parseInt(port));
			if (connectionAlive) {
				ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, connectionAlive, RESPONSE_STATUS_SUCCESS, PHR700003);
				return Response.status(Response.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			} else {
				ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, connectionAlive, RESPONSE_STATUS_SUCCESS, PHR710006);
				return Response.status(Response.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}

		} catch (Exception e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR710007);
			return Response.status(Response.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} finally {
			try {
				if(readers != null) {
					readers.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	@GET
	@Path("/logContent")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logContent(@QueryParam("status") String status, @QueryParam("appDirName") String appDirName, @QueryParam("moduleName") String moduleName) {
		ResponseInfo<String> responseData = new ResponseInfo<String>();
		String readLogFile = "";
		try {
			
			String rootModulePath = "";
			String subModuleName = "";
			
			if (StringUtils.isNotEmpty(moduleName)) {
				rootModulePath = Utility.getProjectHome() + appDirName;
				subModuleName = moduleName;
			} else {
				rootModulePath = Utility.getProjectHome() + appDirName;
			}
			
			File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
			if (StringUtils.isNotEmpty(status) && status.equals("true")) {
				
				readLogFile = readRunAgsSrcLogFile(pomFile.getParent());
			} else {
				deleteLogFile(pomFile.getParent());
				deleteInfoFile(dotPhrescoFolderPath);
			}

			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, null, readLogFile,
					RESPONSE_STATUS_SUCCESS, "");
			return Response.status(Response.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (PhrescoException e) {
			ResponseInfo<Boolean> finalOutput = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, "");
			return Response.status(Response.Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	private void deleteInfoFile(String dotPhrescoFolderPath) throws PhrescoException {
		File infoFile = new File(dotPhrescoFolderPath + File.separator + RUNAGNSRC_INFO_FILE);
		if (infoFile.isFile() && infoFile.exists()) {
			infoFile.delete();
		}
	}

	public void deleteLogFile(String targetDir) throws PhrescoException {
		try {
			File logFile = new File(getLogFilePath(targetDir));
			File infoFile = new File(getLogFolderPath(targetDir) + File.separator + RUN_AGS_LOG_FILE);
			if (logFile.isFile() && logFile.exists()) {
				logFile.delete();
			}
			if (infoFile.isFile() && infoFile.exists()) {
				infoFile.delete();
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}

	private String getLogFilePath(String targetDir) throws PhrescoException {
		StringBuilder builder = new StringBuilder(getLogFolderPath(targetDir));
		builder.append(File.separator);
		builder.append(LOG_FILE);
		return builder.toString();
	}

	private String readRunAgsSrcLogFile(String targetDir) throws PhrescoException {
		BufferedReader reader = null;
		try {
			File runAgsLogfile = new File(getLogFolderPath(targetDir) + File.separator + RUN_AGS_LOG_FILE);
			if (runAgsLogfile.exists()) {
				reader = new BufferedReader(new FileReader(runAgsLogfile));
				String text = "";
				StringBuffer contents = new StringBuffer();
				while ((text = reader.readLine()) != null) {
					contents.append(text).append(System.getProperty(LINE_SEPERATOR));
				}
				return contents.toString();
			}
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeReader(reader);
		}

		return "";
	}
	
	private String getLogFolderPath(String targetDir) throws PhrescoException {
		StringBuilder builder = new StringBuilder(targetDir);
		builder.append(File.separator);
		builder.append(DO_NOT_CHECKIN_DIR);
		builder.append(File.separator);
		builder.append(LOG_DIR);
		return builder.toString();
	}
	
	private List<MinifyInfo> includesFiles(Element element, String appDirName) throws PhrescoException {
		List<MinifyInfo> consoldateInfo = new ArrayList<MinifyInfo>();
		String opFileLoc = "";
		try {
			if (POM_AGGREGATIONS.equals(element.getNodeName())) {
				NodeList aggregationList = element.getElementsByTagName(POM_AGGREGATION);
				for (int i = 0; i < aggregationList.getLength(); i++) {
					MinifyInfo minifyInfo = new MinifyInfo();
					Element childNode = (Element) aggregationList.item(i);
					NodeList includeList = childNode.getElementsByTagName(POM_INCLUDES).item(0).getChildNodes();
					StringBuilder csvFileNames = new StringBuilder(); 
					String sep = "";
					for (int j = 0; j < includeList.getLength()-1; j++) {//To convert select files to Comma seperated value
						Element include = (Element) includeList.item(j);
						String file = include.getTextContent().substring(include.getTextContent().lastIndexOf(FILE_SEPARATOR)+1);
						csvFileNames.append(sep);
						csvFileNames.append(file);
						sep = COMMA;
					}
					Element outputElement = (Element) childNode.getElementsByTagName(POM_OUTPUT).item(0);
					//To get compressed name with extension
					String opFileName = outputElement.getTextContent().substring(outputElement.getTextContent().lastIndexOf(FILE_SEPARATOR)+1);
					String compressName = opFileName.substring(0, opFileName.indexOf("."));//To get only the compressed name without extension
					String compressedExtension = opFileName.substring(opFileName.lastIndexOf(FrameworkConstants.DOT)+1);//To get extension of compressed file
					opFileLoc = outputElement.getTextContent().substring(0, outputElement.getTextContent().lastIndexOf(FILE_SEPARATOR)+1);
					opFileLoc = opFileLoc.replace(MINIFY_OUTPUT_DIRECTORY, FrameworkServiceUtil.getApplicationHome(appDirName).replace(File.separator, FrameworkConstants.FORWARD_SLASH));
					
					if (JS.equals(compressedExtension)) {//if extension is js , add minified details to jsMap
						minifyInfo.setFileType(JS);
						minifyInfo.setCompressName(compressName);
						minifyInfo.setCsvFileName(csvFileNames.toString().replace(HYPHEN_MIN, ""));
						minifyInfo.setOpFileLoc(opFileLoc);
					} else {//if extension is CSS , add minified details to cssMap
						minifyInfo.setFileType("css");
						minifyInfo.setCompressName(compressName);
						minifyInfo.setCsvFileName(csvFileNames.toString().replace(HYPHEN_MIN, ""));
						minifyInfo.setOpFileLoc(opFileLoc);
					}
					consoldateInfo.add(minifyInfo);
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return consoldateInfo;
	}
	

	private  boolean isConnectionAlive(String protocol, String host, int port) {
		boolean isAlive = true;
		try {
			Thread.sleep(3000);
			URL url = new URL(protocol, host, port, "");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			isAlive = false;
		}
		return isAlive;
	}
	
	private boolean checkForFailureInLog(File errorLog) throws PhrescoException {
		BufferedReader reader = null;
		String line = null;
		boolean errorParam = false ;
		try {
			reader = new BufferedReader(new FileReader(errorLog));
			while ((line = reader.readLine()) != null) {
				if (line.contains("[INFO] BUILD FAILURE") || line.contains("[ERROR]") || line.contains("Error")) {
					errorParam = true;
				}
			}
			return errorParam;
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeReader(reader);
		}
		
	}
}

class BuildComparator implements Comparator<BuildInfo> {
	public int compare(BuildInfo buildInfo1, BuildInfo buildInfo2) {
		DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
		Date  buildTime1 = new Date();
		Date buildTime2 = new Date();
		try {
			buildTime1 = (Date)formatter.parse(buildInfo1.getTimeStamp());
			buildTime2 = (Date)formatter.parse(buildInfo2.getTimeStamp());
		} catch (ParseException e) {
		}
		
		return buildTime2.compareTo(buildTime1);
	}
}
