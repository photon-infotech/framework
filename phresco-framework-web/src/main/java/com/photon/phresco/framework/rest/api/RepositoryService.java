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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Reader;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Config;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.VersionRangeRequest;
import org.sonatype.aether.resolution.VersionRangeResolutionException;
import org.sonatype.aether.resolution.VersionRangeResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.layout.MavenDefaultLayout;
import org.sonatype.aether.version.Version;
import org.sonatype.nexus.rest.model.ArtifactInfoResource;
import org.sonatype.nexus.rest.model.ArtifactInfoResourceResponse;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.gson.Gson;
import com.perforce.p4java.exception.ConnectionException;
import com.perforce.p4java.exception.RequestException;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.LockUtil;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.ModuleInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.RepositoryFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.RepositoryManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.impl.ClientHelper;
import com.photon.phresco.framework.impl.SCMManagerImpl;
import com.photon.phresco.framework.model.RepoDetail;
import com.photon.phresco.framework.model.RepoFileInfo;
import com.photon.phresco.framework.model.RepoInfo;
import com.photon.phresco.framework.rest.api.util.ActionResponse;
import com.photon.phresco.framework.rest.api.util.ActionServiceConstant;
import com.photon.phresco.framework.rest.api.util.BufferMap;
import com.photon.phresco.framework.rest.api.util.FrameworkServiceUtil;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.MavenArtifactResolver;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Scm;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;


/**
 * The Class RepositoryService.
 */
@Path("/repository")
public class RepositoryService extends RestBase implements FrameworkConstants, ServiceConstants, ResponseCodes {

	String status;
	String errorCode;
	String successCode;

	/**
	 * Import application from repository.
	 *
	 * @param repoInfo the repodetail
	 * @return the response
	 * @throws Exception 
	 */
	@POST
	@Path("/importApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response importApplication(RepoInfo repoInfo, @QueryParam("displayName") String displayName) throws Exception {
		Response response = null;
		response = importSVNApplication(repoInfo, displayName);
		return response;
	}

	/**
	 * Update imported applicaion  from repository.
	 *
	 * @param appDirName the app dir name
	 * @param repoInfo the repodetail
	 * @return the response
	 */
	@POST
	@Path("/updateImportedApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateImportedApplicaion(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam("displayName") String displayName,
			RepoInfo repoInfo) {
		Response response = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			UUID uniqueKey = UUID.randomUUID();
			String unique_key = uniqueKey.toString();
			ProjectInfo projectInfo = Utility.getProjectInfo(Utility.getProjectHome() + appDirName, "");
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			response = updateSVNProject(responseData, applicationInfo, appDirName, repoInfo, unique_key, displayName);
			return response;
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		}
	}
	
	/**
	 * To check whether the application is already an SCM application
	 * @param appDirName
	 * @return
	 */
	@GET
	@Path("/repoExists")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response isSCMApplication(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName) {
		Response response = null;
		ResponseInfo responseData = new ResponseInfo();
		try {
			File pomFileLocation = Utility.getPomFileLocation(Utility.getProjectHome() + appDirName, "");
			PomProcessor pomProcessor = new PomProcessor(pomFileLocation);
			String property = pomProcessor.getProperty(Constants.POM_PROP_KEY_SRC_REPO_URL);
			if (StringUtils.isNotEmpty(property)) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200053;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, true, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210053;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoPomException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210053;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHR200053;
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, false, status, successCode);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Adds the project to repository.
	 * @param appDirName
	 * @param repoInfo
	 * @param userId
	 * @param projectId
	 * @param appId
	 * @param displayName
	 * @return
	 */
	@POST
	@Path("/addProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProjectToRepo(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, RepoInfo repoInfo,
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_PROJECTID) String projectId,
			@QueryParam(REST_QUERY_APPID) String appId, @QueryParam("displayName") String displayName) {
		RepoDetail srcRepoDetail = repoInfo.getSrcRepoDetail();
		String type = srcRepoDetail.getType();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		return addToRepo(appDirName, repoInfo, userId, projectId, appId, type, unique_key, displayName);
	}

	/**
	 * Commit imported project to repository.
	 *
	 * @param repoInfo the repodetail
	 * @param appDirName the app dir name
	 * @return the response
	 */
	@POST
	@Path("/commitProjectToRepo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commitImportedProject(RepoInfo repoInfo, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,  @QueryParam("displayName") String displayName) {
		ResponseInfo responseData = null;
		Response response = null;
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ProjectInfo projectInfo = Utility.getProjectInfo(Utility.getProjectHome() + appDirName, "");
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);

			RepoDetail phrescoRepoDetail = repoInfo.getPhrescoRepoDetail();
			StringBuilder rootDir = new StringBuilder(Utility.getProjectHome()).append(appDirName);
			File phrescoDir = new File(rootDir.toString(), appDirName + Constants.SUFFIX_PHRESCO);
			if (repoInfo.isSplitPhresco() && phrescoRepoDetail != null) {
				if (phrescoDir.exists() && phrescoDir.isDirectory()) {

					response = commitProject(phrescoRepoDetail, applicationInfo, displayName, unique_key, phrescoDir);
				}
			}

			RepoDetail srcRepoDetail = repoInfo.getSrcRepoDetail();
			File srcDir = new File(rootDir.toString(), appDirName);
			if (!srcDir.exists()) {
				srcDir = new File(rootDir.toString());
			}
			if (srcRepoDetail != null) {
				response = commitProject(srcRepoDetail, applicationInfo, displayName, unique_key, srcDir);
			}

			RepoDetail testRepoDetail = repoInfo.getTestRepoDetail();
			if (repoInfo.isSplitTest() && testRepoDetail != null) {
				File pomFile = getPomFromWrokDir(applicationInfo);
				PomProcessor processor = new PomProcessor(pomFile);
				String testDirName = processor.getProperty(Constants.POM_PROP_KEY_SPLIT_TEST_DIR);
				File testDir = new File(rootDir.toString(), testDirName);
				response = commitProject(testRepoDetail, applicationInfo, displayName, unique_key, testDir);
			}
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoPomException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		}
		return response;
	}

	private Response commitProject(RepoDetail repoDetail, ApplicationInfo applicationInfo, 
			String displayName, String unique_key, File workingDir) throws PhrescoException {
		Response response = null;
		try {
			String type = repoDetail.getType();
			com.photon.phresco.framework.impl.util.FrameworkUtil.saveCredential(repoDetail, null);
			if (type.equals(SVN)) {
				response = commitSVNProject(repoDetail, applicationInfo, unique_key, displayName);
			} else if (type.equals(GIT)) {
				response = commitGitProject(applicationInfo, repoDetail, type, unique_key, displayName, workingDir);
			} else if (type.equals(BITKEEPER)) {
				response = commitBitKeeperProject(applicationInfo, repoDetail, type, unique_key, displayName, workingDir);
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return response;
	}
	/**
	 * Fetch pop up values.
	 *
	 * @param appDirName the app dir name
	 * @param action the action
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/popupValues")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchPopUpValues(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_ACTION) String action, @QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<RepoInfo> responseData = new ResponseInfo<RepoInfo>();
		try {
			File pomFile = Utility.getPomFileLocation(Utility.getProjectHome() + appDirName, "");
			PomProcessor processor = new PomProcessor(pomFile);
			String srcRepoURL = processor.getProperty(Constants.POM_PROP_KEY_SRC_REPO_URL);
			String dotPhresoRepoURL = processor.getProperty(Constants.POM_PROP_KEY_PHRESCO_REPO_URL);
			String testRepoURL = processor.getProperty(Constants.POM_PROP_KEY_TEST_REPO_URL);

			StringBuilder rootDir = new StringBuilder(Utility.getProjectHome()).append(appDirName);

			RepoInfo repoInfo = new RepoInfo();
			File srcDir = new File(rootDir.toString(), appDirName);
			if (!srcDir.exists()) {
				srcDir = new File(rootDir.toString());
			}
			
			if (srcDir.exists() && StringUtils.isNotEmpty(srcRepoURL)) {
				RepoDetail srcRepoDetail = createRepoDetail(srcRepoURL, userId, action, srcDir);
				repoInfo.setSrcRepoDetail(srcRepoDetail);
			}

			File phrescoDir = new File(rootDir.toString(), appDirName + Constants.SUFFIX_PHRESCO);
			if (StringUtils.isNotEmpty(dotPhresoRepoURL) && phrescoDir.exists()) {
				RepoDetail phrescoRepoDetail = createRepoDetail(dotPhresoRepoURL, userId, action, phrescoDir);
				repoInfo.setPhrescoRepoDetail(phrescoRepoDetail);
				repoInfo.setSplitPhresco(true);
			}

			String splitTestDirName = processor.getProperty(Constants.POM_PROP_KEY_SPLIT_TEST_DIR);
			File testDir = new File(rootDir.toString(), splitTestDirName);
			if (testDir.exists() && StringUtils.isNotEmpty(testRepoURL)) {
				RepoDetail testRepoDetail = createRepoDetail(testRepoURL, userId, action, testDir);
				repoInfo.setTestRepoDetail(testRepoDetail);
				repoInfo.setSplitTest(true);
			}

			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200021;
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, null,
					repoInfo, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210036;
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoPomException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210036;
			ResponseInfo<RepoDetail> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		}
	}

	/**
	 * Fetch log messages.
	 *
	 * @param repodetail the repodetail
	 * @return the response
	 * @throws PhrescoException the phresco exception
	 */
	@POST
	@Path("/logMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchLogMessages(RepoDetail repodetail) throws PhrescoException {
		List<String> restrictedLogs = null;
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			List<String> svnLogMessages = scmi.getSvnLogMessages(repodetail.getRepoUrl(), repodetail.getUserName(),
					repodetail.getPassword());
			restrictedLogs = restrictLogs(svnLogMessages);
		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Authorization Realm")) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210023;
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else if (e.getLocalizedMessage().contains("OPTIONS request failed on")
					|| (e.getLocalizedMessage().contains("PROPFIND") && e.getLocalizedMessage().contains(
					"405 Method Not Allowed"))
					|| e.getLocalizedMessage().contains("Repository moved temporarily to")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210038;
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210039;
				ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status ,errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHR200023;
		ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, null,
				restrictedLogs, status, successCode);
		return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Restrict logs.
	 *
	 * @param svnLogMessages the svn log messages
	 * @return the list
	 */
	private List<String> restrictLogs(List<String> svnLogMessages) {
		List<String> Messages = new ArrayList<String>();
		if (svnLogMessages.size() > 5) {
			for (int i = svnLogMessages.size() - 5; i <= svnLogMessages.size() - 1; i++) {
				Messages.add(svnLogMessages.get(i));
			}
		} else {
			for (int i = 0; i <= svnLogMessages.size() - 1; i++) {
				Messages.add(svnLogMessages.get(i));
			}
		}
		return Messages;
	}

	/**
	 * Import svn application.
	 *
	 * @param type the type
	 * @param repoInfo the repodetail
	 * @return the response
	 */
	private Response importSVNApplication(RepoInfo repoInfo, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject = scmi.importProject(repoInfo, displayName, unique_key);
			if(repoInfo.isSplitTest()) {
				scmi.importTest(importProject, repoInfo);
			}
			if(repoInfo.isSplitPhresco()) {
				scmi.importPhresco(importProject, repoInfo);
			}
			scmi.updateSCMConnection(repoInfo, importProject);
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200017;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (SVNAuthenticationException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210023;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210024;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210025;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Import git application.
	 *
	 * @param type the type
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response importGITApplication(String type, RepoInfo repodetail, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject = scmi.importProject(repodetail, displayName, unique_key);
			if (importProject != null) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200017;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210022;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	}

	/**
	 * Import bit keeper application.
	 *
	 * @param type the type
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response importBitKeeperApplication(String type, RepoInfo repodetail, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject = scmi.importProject(repodetail, displayName, unique_key);
			if (importProject != null) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200017;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210022;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (Exception e) {
			if ("Project already imported".equals(e.getLocalizedMessage())) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210027;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else if ("Failed to import project".equals(e.getLocalizedMessage())) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	}

	/**
	 * Fetch pop up values.
	 *
	 * @param appDirName the app dir name
	 * @param action the action
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/browseBuildRepo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBuildRepoStructure(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_PROJECTID) String projectId) throws PhrescoException {
		Response response = null;
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		List<String> urls = new ArrayList<String>();
		List<String> documents = new ArrayList<String>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager != null) {
				com.photon.phresco.commons.model.RepoInfo repo = serviceManager.getCustomer(customerId).getRepoInfo();
				String releaseRepoURL = repo.getReleaseRepoURL();
				String snapshotRepoURL = repo.getSnapshotRepoURL();
				List<ApplicationInfo> appInfos = com.photon.phresco.framework.impl.util.FrameworkUtil.getAppInfos(customerId, projectId);
				for (ApplicationInfo applicationInfo : appInfos) {
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

					Document doc = docBuilder.newDocument();
					Element rootElement = doc.createElement(ROOT);
					doc.appendChild(rootElement);

					Element rootItem = doc.createElement(ITEM);
					rootItem.setAttribute(TYPE, FOLDER);
					rootItem.setAttribute(PATH, "");
					rootItem.setAttribute(NAME, ROOT_ITEM);

					rootElement.appendChild(rootItem);

					Element applicationItem = doc.createElement(ITEM);
					applicationItem.setAttribute(TYPE, FOLDER);
					applicationItem.setAttribute(PATH, "");
					applicationItem.setAttribute(NAME, applicationInfo.getAppDirName());

					rootItem.appendChild(applicationItem);

					List<ModuleInfo> modules = applicationInfo.getModules();
					if (CollectionUtils.isNotEmpty(modules)) {
						for (ModuleInfo module : modules) {
							Artifact artifact = readArtifact(applicationInfo.getAppDirName(), "", module.getCode());
							String moduleName = module.getCode();

							Element moduleItem = doc.createElement(ITEM);
							moduleItem.setAttribute(TYPE, FOLDER);
							moduleItem.setAttribute(NAME, moduleName);
							applicationItem.appendChild(moduleItem);

							if (StringUtils.isNotEmpty(releaseRepoURL)) {
								urls.add(releaseRepoURL);
							} 
							if (StringUtils.isNotEmpty(snapshotRepoURL)) {
								urls.add(snapshotRepoURL);
							}
							doc = constructDomSource(artifact, applicationInfo.getAppDirName(), urls, moduleName, doc, moduleItem);
						}
						documents.add(com.photon.phresco.framework.impl.util.FrameworkUtil.convertDocumentToString(doc));
					} else {
						Artifact artifact = readArtifact(applicationInfo.getAppDirName(), "", "");
						if (StringUtils.isNotEmpty(releaseRepoURL)) {
							urls.add(releaseRepoURL);
						} 
						if (StringUtils.isNotEmpty(snapshotRepoURL)) {
							urls.add(snapshotRepoURL);
						}
						
						Document document = constructDomSource(artifact, applicationInfo.getAppDirName(), urls, "", doc, applicationItem);
						documents.add(com.photon.phresco.framework.impl.util.FrameworkUtil.convertDocumentToString(document));
					}
				}
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, documents, status, successCode);
				response = Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		}
		return response;
	}
	
	/**
	 * To Fetch the branches and tags of the given project
	 * @param customerId
	 * @param projectId
	 * @return
	 * @throws PhrescoException
	 */

	@GET
	@Path("/browseSourceRepo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSourceRepo(@QueryParam(REST_QUERY_CUSTOMERID) String customerId, @QueryParam(REST_QUERY_PROJECTID) String projectId, @QueryParam(USERNAME) String username, @QueryParam(REQ_PASSWORD) String password) throws PhrescoException {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		Response response = null;
		String srcRepoUrl = "";
		Document document = null;
		List<String> documents = new ArrayList<String>();
		List<String> errormessages = new ArrayList<String>();
		try {
			List<ApplicationInfo> appInfos = com.photon.phresco.framework.impl.util.FrameworkUtil.getAppInfos(customerId, projectId);
			for (ApplicationInfo applicationInfo : appInfos) {
				String appDirName = applicationInfo.getAppDirName();
				File pomFileLocation = Utility.getPomFileLocation(Utility.getProjectHome() + appDirName, "");
				PomProcessor pomProcessor = new PomProcessor(pomFileLocation);
				srcRepoUrl = pomProcessor.getProperty(Constants.POM_PROP_KEY_PHRESCO_REPO_URL);
				if (StringUtils.isEmpty(srcRepoUrl)) {
					srcRepoUrl = pomProcessor.getProperty(Constants.POM_PROP_KEY_SRC_REPO_URL);
				}
				if (StringUtils.isNotEmpty(srcRepoUrl)) {
					String repoType = getRepoType(srcRepoUrl);
					RepositoryManager repositoryManager = RepositoryFactory.getRepository(repoType);
					File credentialPath = new File(Utility.getPhrescoTemp() + File.separator + CREADENTIAL_JSON);
					if (credentialPath.exists()) {
						Map<String, String> credential = com.photon.phresco.framework.impl.util.FrameworkUtil.getCredential(srcRepoUrl);
						if (MapUtils.isNotEmpty(credential)) {
							username = credential.get(REQ_USER_NAME);
							String encryptedPassword = credential.get(REQ_PASSWORD);
							password = com.photon.phresco.framework.impl.util.FrameworkUtil.getdecryptedPassword(encryptedPassword);
							try {
								document = repositoryManager.getSource(appDirName, username, password, srcRepoUrl);
								String docs = com.photon.phresco.framework.impl.util.FrameworkUtil.convertDocumentToString(document);
								documents.add(docs);
							} catch (PhrescoException e) {
								String message = e.getMessage();
								if (StringUtils.isNotEmpty(message)) {
									message = message.substring(message.indexOf(HTTPS));
									errormessages.add(message);
									status = RESPONSE_STATUS_FAILURE;
									errorCode = PHRSR10007;
									Exception exception = new Exception(AUTHENTICATION_FAILED);
									ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, exception, errormessages, status, errorCode);
									return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
								}
							}
						} else {
							errormessages.add(srcRepoUrl);
							status = RESPONSE_STATUS_FAILURE;
							errorCode = PHRSR10007;
							Exception exception = new Exception(AUTHENTICATION_FAILED);
							ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, exception, errormessages, status, errorCode);
							return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
						}
					} else {
						errormessages.add(srcRepoUrl);
						status = RESPONSE_STATUS_FAILURE;
						errorCode = PHRSR10007;
						Exception exception = new Exception(AUTHENTICATION_FAILED);
						ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, exception, errormessages, status, errorCode);
						return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
					}
				}
			}
		} catch (PhrescoPomException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRSR10001;
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHRSR00001;
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, documents, status, successCode);
		response = Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		return response;
	}
	
	@POST
	@Path("/saveCredentails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCredential(@QueryParam(REST_QUERY_URL) String url, @QueryParam(REST_QUERY_USER_NAME) String username, @QueryParam(REST_QUERY_PASSWORD) String password) throws PhrescoException {
		ResponseInfo<List<String>> responseData = new ResponseInfo<List<String>>();
		Response response = null;
		List<String> documents = new ArrayList<String>();
		try {
			String encryptedPassword = com.photon.phresco.framework.impl.util.FrameworkUtil.getEncryptedPassword(password);
			com.photon.phresco.framework.impl.util.FrameworkUtil.saveCredential(url, username, encryptedPassword);
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHRSR10009;
			ResponseInfo<List<String>> finalOutput = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
		}
		status = RESPONSE_STATUS_SUCCESS;
		successCode = PHRSR00009;
		ResponseInfo finalOutput = responseDataEvaluation(responseData, null, documents, status, successCode);
		response = Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		return response;
	}
	

	/**
	 * To get the artifact information
	 * @param customerId
	 * @param appDirName
	 * @param userId
	 * @param nature
	 * @param version
	 * @param moduleName
	 * @return
	 * @throws PhrescoException
	 */

	@GET
	@Path("/artifactInfo")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings({"unchecked","rawtypes"})
	public Response getArtifactInfo(@QueryParam(REST_QUERY_CUSTOMERID) String customerId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_NATURE) String nature, @QueryParam(REST_QUERY_VERSION) String version, @QueryParam(REST_QUERY_MODULE_NAME) String moduleName) throws PhrescoException {
		Map<String, Object> infoMap = new HashMap<String, Object>();
		ResponseInfo<Map<String, Object>> responseData = new ResponseInfo<Map<String, Object>>();
		RemoteRepository repo = null;
		Response response = null;
		ArtifactInfoResource infos = null;
		Artifact artifact = null;
		String artifactPath = "";
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager != null) {
				com.photon.phresco.commons.model.RepoInfo repos = serviceManager.getCustomer(customerId).getRepoInfo();
				artifact = readArtifact(appDirName, version, moduleName);
				MavenDefaultLayout defaultLayout = new MavenDefaultLayout();
				URI paths = defaultLayout.getPath(artifact);
				MetadataXpp3Reader reader = new MetadataXpp3Reader();
				if (nature.equalsIgnoreCase(SNAPSHOT)) {
					repo = new RemoteRepository("", DEFAULT, repos.getSnapshotRepoURL());
					artifactPath =(repo.getUrl() + repo.getId() + paths).replace("\\", "/");
					String path = artifactPath.substring(0, artifactPath.lastIndexOf("/"));
					URL metadataPath = new URL(path + MVN_METADATA_XML);
					InputStream inputStream = metadataPath.openStream();
					Metadata metadata = reader.read(inputStream);

					version = version.substring(0, version.indexOf(HYPEN_CHAR)+1) +  metadata.getVersioning().getSnapshot().getTimestamp();
					artifactPath = artifactPath.substring(0, artifactPath.lastIndexOf(HYPEN_CHAR));
					artifactPath = artifactPath.substring(0, artifactPath.lastIndexOf(HYPEN_CHAR) + 1);
					artifactPath =  artifactPath + version + PRIORITY_JAR ;

				} else {
					repo = new RemoteRepository("", DEFAULT, repos.getReleaseRepoURL());
					artifactPath =(repo.getUrl() + repo.getId() + paths).replace("\\", "/");
				}
				Client client = ClientHelper.createClient();
				WebResource webResource = client.resource(artifactPath + DESCRIBE_INFO);
				ClientResponse clientResponse = webResource.get(ClientResponse.class);
				String info = (String)clientResponse.getEntity(String.class);
				if (!info.startsWith(HTML_ELEMENT)) {
					ArtifactInfoResourceResponse artifactInfo = new Gson().fromJson(info, ArtifactInfoResourceResponse.class);
					infos = artifactInfo.getData();
					infoMap.put(ARTIFACT_INFO, infos);
					infoMap.put(MAVEN_INFO, artifact);
				}
			}
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, infoMap, status, successCode);
			response = Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (MalformedURLException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR610020);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (UniformInterfaceException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR610020);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (ClientHandlerException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR610020);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (IOException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR610020);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (XmlPullParserException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR, PHR610020);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
		return response;
	}

	/**
	 * To download the artifact
	 * @param customerId
	 * @param appDirName
	 * @param userId
	 * @param nature
	 * @param version
	 * @param moduleName
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Path("/download")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response downloadService(@QueryParam(REST_QUERY_CUSTOMERID) String customerId, @QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam(REST_QUERY_USERID) String userId,  @QueryParam(REST_QUERY_NATURE) String nature,  @QueryParam(REST_QUERY_VERSION) String version, @QueryParam(REST_QUERY_MODULE_NAME) String moduleName) throws PhrescoException {
		Artifact artifact = null;
		InputStream inputStream = null;
		File artifactFile = null;
		List<Artifact> artifacts = new ArrayList<Artifact>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			String url = "";
			if (serviceManager != null) {
				com.photon.phresco.commons.model.RepoInfo repos = serviceManager.getCustomer(customerId).getRepoInfo();
				if (nature.equalsIgnoreCase(RELEASE)) {
					url = repos.getReleaseRepoURL();
				} else {
					url = repos.getSnapshotRepoURL();
				}
				artifact = readArtifact(appDirName, version, moduleName);
				artifacts.add(artifact);
				URL artifacturl = MavenArtifactResolver.resolveSingleArtifact(url, "", "", artifacts);
				artifactFile = new File(artifacturl.toURI());
				inputStream = new FileInputStream(artifactFile);
			}
			return Response.status(Status.OK).entity(inputStream).header("Content-Disposition", "attachment; filename=" + artifactFile.getName()).build();

		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	@POST
	@Path("/createBranch")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBranch(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_RELEASE_VERSION) String releaseVersion, 
			@QueryParam(REST_QUERY_COMMENT) String comment, @QueryParam(REST_QUERY_CURRENT_BRANCH) String currentBranch, 
			@QueryParam(REST_QUERY_BRANCH_NAME) String branchName, @QueryParam(REST_QUERY_DOWNLOAD_OPTION) boolean downloadOption) {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		ActionResponse response = new ActionResponse();
		String appDirPath = Utility.getProjectHome() + appDirName;
		try {
			File pomFile = Utility.getPomFileLocation(appDirPath, "");
			// Construct command for branch
			StringBuilder builder = new StringBuilder();
			builder.append(Constants.MVN_COMMAND)
			.append(Constants.STR_BLANK_SPACE)
			.append(ActionType.CREATE_BRANCH.getActionType())
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_APPDIR_NAME)
			.append(Constants.STR_EQUALS).append(appDirName)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_RELEASE_VERSION).append(Constants.STR_EQUALS)
			.append(releaseVersion)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_COMMENT).append(Constants.STR_EQUALS)
			.append("\"" + comment + "\"")
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_CURRENT_BRANCH)
			.append(Constants.STR_EQUALS).append(currentBranch)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_BRANCH_NAME)
			.append(Constants.STR_EQUALS).append(branchName)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_DOWNLOAD_OPTION)
			.append(Constants.STR_EQUALS).append(downloadOption)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.HYPHEN_F).append(Constants.STR_BLANK_SPACE).append(pomFile.getName());
			String workingDirectory = pomFile.getParent();
			UUID uniqueKey = UUID.randomUUID();
			String unique_key = uniqueKey.toString();

			BufferedInputStream executeMavenCommand = executeMavenCommand(builder, workingDirectory);
			if (executeMavenCommand != null) {
				response = generateResponse(executeMavenCommand, unique_key);
			}
			if (response.getStatus() != RESPONSE_STATUS_FAILURE) {
				response.setResponseCode(PHRSR00002);
			}
			return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR,
					PHRSR10004);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
			
			
		}
	}
	
	@POST
	@Path("/tag")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTag(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_COMMENT) String comment, @QueryParam(REST_QUERY_CURRENT_BRANCH) String currentBranch, 
			@QueryParam(REST_QUERY_TAG) String tag, @QueryParam(REST_QUERY_RELEASE_VERSION) String releaseVersion) {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		ActionResponse response = new ActionResponse();
		String appDirPath = Utility.getProjectHome() + appDirName;
		try {
			File pomFile = Utility.getPomFileLocation(appDirPath, "");
			// Construct command for branch
			StringBuilder builder = new StringBuilder();
			builder.append(Constants.MVN_COMMAND)
			.append(Constants.STR_BLANK_SPACE)
			.append(ActionType.TAG.getActionType())
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_APPDIR_NAME)
			.append(Constants.STR_EQUALS).append(appDirName)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_RELEASE_VERSION).append(Constants.STR_EQUALS)
			.append(releaseVersion)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_COMMENT).append(Constants.STR_EQUALS)
			.append("\"" + comment + "\"")
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_CURRENT_BRANCH)
			.append(Constants.STR_EQUALS).append(currentBranch)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.SCM_HYPHEN_D).append(REST_QUERY_TAG)
			.append(Constants.STR_EQUALS).append(tag)
			.append(Constants.STR_BLANK_SPACE)
			.append(Constants.HYPHEN_F).append(Constants.STR_BLANK_SPACE).append(pomFile.getName());
			String workingDirectory = pomFile.getParent();
			UUID uniqueKey = UUID.randomUUID();
			String unique_key = uniqueKey.toString();

			BufferedInputStream executeMavenCommand = executeMavenCommand(builder, workingDirectory);
			if (executeMavenCommand != null) {
				response = generateResponse(executeMavenCommand, unique_key);
			}
			if (response.getStatus() != RESPONSE_STATUS_FAILURE) {
				response.setResponseCode(PHRSR00003);
			}
			return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR,
					PHRSR10005);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVersion(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName, @QueryParam(REST_QUERY_USER_NAME) String username, @QueryParam(REST_QUERY_PASSWORD) String password, 
            @QueryParam(REST_QUERY_CURRENT_BRANCH) String currentBranch) {
        ResponseInfo<Map<String, String>> responseData = new ResponseInfo<Map<String, String>>();
        String version = "";
        Map<String, String> versionMap = new HashMap<String, String>();
        String phrescoTemp = Utility.getPhrescoTemp();
        String uuid = UUID.randomUUID().toString();
        String workingDir = phrescoTemp + uuid;
        String appDirPath = Utility.getProjectHome() + appDirName;
        try {
            File pomFile = Utility.getPomFileLocation(appDirPath, "");
            PomProcessor pomProcessor = new PomProcessor(pomFile);
            String connectionUrl = pomProcessor.getProperty(Constants.POM_PROP_KEY_PHRESCO_REPO_URL);
            if (StringUtils.isEmpty(connectionUrl) || !PHR_POM_XML.equals(pomFile.getName())) {
            	connectionUrl = pomProcessor.getProperty(Constants.POM_PROP_KEY_SRC_REPO_URL);
            }
            String repoType = getRepoType(connectionUrl);
            if (Constants.SCM_GIT.equals(repoType)) {
                gitPomCheckout(connectionUrl, currentBranch, phrescoTemp, uuid, pomFile);
            } else if (Constants.SCM_SVN.equals(repoType)) {
                svnPomCheckout(connectionUrl, phrescoTemp, uuid, pomFile);
            }
            File file = new File(workingDir, pomFile.getName());
            if (file.exists()) {
                PomProcessor processor = new PomProcessor(file);
                version = processor.getVersion();
                String[] split = version.split("-");
                String tagVerion = split[0];
                versionMap.put(Constants.CURRENT_VERSION, version);
                versionMap.put(Constants.TAG_VERSION, tagVerion);
                String[] splitVersion = tagVerion.split("\\.");
                String devVersion = splitVersion[0] + FrameworkConstants.DOT + (Integer.parseInt(splitVersion[1]) + 1) + FrameworkConstants.DOT + splitVersion[2] + HYPHEN + SNAPSHOT;
                versionMap.put(Constants.DEV_VERSION, devVersion);
            }
        } catch (PhrescoPomException e) {
            status = RESPONSE_STATUS_ERROR;
            errorCode = PHRSR10005;
            ResponseInfo<StringBuilder> finalOutput = responseDataEvaluation(responseData, e,
                    null, status, errorCode);
            return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
                    .build();
        } catch (IOException e) {
            status = RESPONSE_STATUS_ERROR;
            errorCode = PHRSR10005;
            ResponseInfo<StringBuilder> finalOutput = responseDataEvaluation(responseData, e,
                    null, status, errorCode);
            return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
                    .build();
        } catch (PhrescoException e) {
            status = RESPONSE_STATUS_ERROR;
            errorCode = PHRSR10005;
            ResponseInfo<StringBuilder> finalOutput = responseDataEvaluation(responseData, e,
                    null, status, errorCode);
            return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
                    .build();
        }
        finally {
            try {
                FileUtils.deleteDirectory(new File(workingDir));
            } catch (IOException e) {
                status = RESPONSE_STATUS_ERROR;
                errorCode = PHRSR10005;
                ResponseInfo<StringBuilder> finalOutput = responseDataEvaluation(responseData, e,
                        null, status, errorCode);
                return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER)
                        .build();
            }
        }
        status = RESPONSE_STATUS_SUCCESS;
        successCode = PHRSR00003;
        ResponseInfo<Map<String, String>> finalOutput = responseDataEvaluation(responseData, null, versionMap, status, successCode);
        return Response.status(Status.OK).entity(finalOutput).header(ACCESS_CONTROL_ALLOW_ORIGIN,ALL_HEADER).build();
    }
	
    private void gitPomCheckout(String connectionUrl, String currentBranch,
            String phrescoTemp, String uuid, File pomFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SCM_GIT).append(Constants.STR_BLANK_SPACE)
        .append(Constants.SCM_GIT_CLONE).append(Constants.STR_BLANK_SPACE)
        .append(Constants.SCM_HYPHEN_N).append(Constants.STR_BLANK_SPACE)
        .append(Constants.HYPHEN_DEPTH_ONE).append(Constants.STR_BLANK_SPACE)
        .append(connectionUrl).append(Constants.STR_BLANK_SPACE)
        .append(Constants.HYPHEN_B).append(Constants.STR_BLANK_SPACE)
        .append(currentBranch).append(Constants.STR_BLANK_SPACE)
        .append(uuid);
        BufferedReader executeCommand = Utility.executeCommand(sb.toString(), phrescoTemp);
        while (executeCommand.readLine() != null) {
        }
        sb = new StringBuilder()
        .append(Constants.SCM_GIT).append(Constants.STR_BLANK_SPACE)
        .append(Constants.SCM_CHECKOUT).append(Constants.STR_BLANK_SPACE)
        .append(currentBranch).append(Constants.STR_BLANK_SPACE)
        .append(pomFile.getName());
        executeCommand = Utility.executeCommand(sb.toString(), phrescoTemp + uuid);
        while (executeCommand.readLine() != null) {
        }
    }

    private void svnPomCheckout(String connectionUrl, String phrescoTemp, String uuid, File pomFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SCM_SVN).append(Constants.STR_BLANK_SPACE)
        .append(Constants.SCM_CHECKOUT).append(Constants.STR_BLANK_SPACE)
        .append(connectionUrl).append(Constants.STR_BLANK_SPACE)
        .append(uuid).append(Constants.STR_BLANK_SPACE)
        .append(Constants.HYPHEN_DEPTH).append(Constants.STR_BLANK_SPACE)
        .append(Constants.EMPTY);
        File file = new File(phrescoTemp , uuid);
        file.mkdirs();
        BufferedReader executeCommand = Utility.executeCommand(sb.toString(), phrescoTemp);
        while (executeCommand.readLine() != null) {
        	System.out.println(executeCommand.readLine());
        }

        sb = new StringBuilder()
        .append(Constants.SCM_SVN).append(Constants.STR_BLANK_SPACE)
        .append(Constants.SVN_UPDATE).append(Constants.STR_BLANK_SPACE)
        .append(pomFile.getName());
        executeCommand = Utility.executeCommand(sb.toString(), phrescoTemp + uuid);
        while (executeCommand.readLine() != null) {
        	System.out.println(executeCommand.readLine());
        }
    }
	
	@POST
	@Path("/release")
	@Produces(MediaType.APPLICATION_JSON)
	public Response release(@QueryParam(REST_QUERY_APPDIR_NAME) String appDirName,
			@QueryParam("message") String message, @QueryParam("developmentVersion") String developmentVersion,
			@QueryParam("releaseVersion") String releaseVersion, @QueryParam("tag") String tag,
			@QueryParam("branchName") String branchName, @QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<Boolean> responseData = new ResponseInfo<Boolean>();
		ActionResponse response = new ActionResponse();
		String projHome = "";
		String username = "";
		String password = "";
		try {
			projHome = Utility.getProjectHome().concat(appDirName);
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			ProjectInfo projectInfo = Utility.getProjectInfo(projHome, "");
			String customerId = projectInfo.getCustomerIds().get(0);
			Customer customer = serviceManager.getCustomer(customerId);
			com.photon.phresco.commons.model.RepoInfo repoInfo = customer.getRepoInfo();
			File pomFile = Utility.getPomFileLocation(projHome, "");
			PomProcessor pomProc = new PomProcessor(pomFile);
			String sourceRepoUrl = pomProc.getProperty(Constants.POM_PROP_KEY_SRC_REPO_URL);
			if(StringUtils.isNotEmpty(sourceRepoUrl)) {
				File credentialPath = new File(Utility.getPhrescoTemp() + File.separator + CREADENTIAL_JSON);
				if (credentialPath.exists()) {
					Map<String, String> credential = com.photon.phresco.framework.impl.util.FrameworkUtil.getCredential(sourceRepoUrl);
					username = credential.get(REQ_USER_NAME);
					String encryptedPassword = credential.get(REQ_PASSWORD);
					password = com.photon.phresco.framework.impl.util.FrameworkUtil.getdecryptedPassword(encryptedPassword);
				}	
			}
			StringBuilder builder = new StringBuilder(Constants.MVN_COMMAND);
			builder.append(Constants.SPACE);
			builder.append(ActionType.RELEASE.getActionType());
			builder.append(Constants.SPACE);
			builder.append("-Dusername=" + username);
			builder.append(Constants.SPACE);
			builder.append("-Dpassword=" + password);
			builder.append(Constants.SPACE);
			builder.append("-Dmessage=" + "\"" + message + "\"");
			builder.append(Constants.SPACE);
			builder.append("-DdevelopmentVersion=" + developmentVersion);
			builder.append(Constants.SPACE);
			builder.append("-DreleaseVersion=" + releaseVersion);
			builder.append(Constants.SPACE);
			builder.append("-Dtag=" + tag);
			builder.append(Constants.SPACE);
			builder.append("-DbranchName=" + branchName);
			builder.append(Constants.SPACE);
			builder.append("-DappDirName=" + appDirName);
			builder.append(Constants.SPACE);
			builder.append("-DrepoUserName=" + repoInfo.getRepoUserName());
			builder.append(Constants.SPACE);
			builder.append("-DrepoPassword=" + repoInfo.getRepoPassword());
			builder.append(Constants.SPACE);
			builder.append("-f");
			builder.append(Constants.SPACE);
			builder.append(pomFile.getName());
			UUID uniqueKey = UUID.randomUUID();
			String unique_key = uniqueKey.toString();
			
			String workingDirectory = "";
			if (StringUtils.isNotEmpty(pomFile.getParent())) {
				workingDirectory = pomFile.getParent();
			}
			BufferedInputStream executeMavenCommand = executeMavenCommand(builder, workingDirectory);
			if (executeMavenCommand != null) {
				response = generateResponse(executeMavenCommand, unique_key);
			}
			if (response.getStatus() != RESPONSE_STATUS_FAILURE) {
				response.setResponseCode(PHRSR10008);
			}
			return Response.status(Status.OK).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR,
					PHR610044);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoPomException e) {
			ResponseInfo<String> finalOuptut = responseDataEvaluation(responseData, e, null, RESPONSE_STATUS_ERROR,
					PHR610045);
			return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	private static Document constructGitTree(List<String> branchList,	List<String> tagLists, String url, String appDirName) throws PhrescoException {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document doc = documentBuilder.newDocument();

			Element rootElement = doc.createElement(ROOT);
			doc.appendChild(rootElement);

			Element rootItem = doc.createElement(ITEM);
			rootItem.setAttribute(TYPE, FOLDER);
			rootItem.setAttribute(NAME, ROOT_ITEM);

			rootElement.appendChild(rootItem);

			Element urlItem = doc.createElement(ITEM);
			urlItem.setAttribute(TYPE, FOLDER);
			urlItem.setAttribute(NAME, url);

			rootItem.appendChild(urlItem);

			Element branchItem = doc.createElement(ITEM);
			branchItem.setAttribute(TYPE, FOLDER);
			branchItem.setAttribute(NAME, BRANCHES);
			branchItem.setAttribute(URL, url);
			urlItem.appendChild(branchItem);


			for (String branch: branchList) {
				String branchName = branch.substring(branch.lastIndexOf("/") + 1, branch.length());
				Element branchItems = doc.createElement(ITEM);
				branchItems.setAttribute(TYPE, FILE);
				branchItems.setAttribute(NAME, branchName);
				branchItems.setAttribute(URL, url);
				branchItems.setAttribute(REQ_APP_DIR_NAME, appDirName);
				branchItems.setAttribute(NATURE, BRANCHES);
				branchItem.appendChild(branchItems);
			}

			Element tagItem = doc.createElement(ITEM);
			tagItem.setAttribute(TYPE, FOLDER);
			tagItem.setAttribute(NAME, TAGS);
			tagItem.setAttribute(URL, url);
			urlItem.appendChild(tagItem);

			for (String tag: tagLists) {
				Element tagItems = doc.createElement(ITEM);
				tagItems.setAttribute(TYPE, FILE);
				tagItems.setAttribute(NAME, tag);
				tagItems.setAttribute(URL, url);
				tagItems.setAttribute(REQ_APP_DIR_NAME, appDirName);
				tagItems.setAttribute(NATURE, TAGS);
				tagItem.appendChild(tagItems);
			}

			return doc;
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		}
	}


	private static Document constructSvnTree(Map<String , List<String>> list,  String url, String appDirName) throws PhrescoException {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document doc = documentBuilder.newDocument();

			Element rootElement = doc.createElement(ROOT);
			doc.appendChild(rootElement);

			Element rootItem = doc.createElement(ITEM);
			rootItem.setAttribute(TYPE, FOLDER);
			rootItem.setAttribute(NAME, ROOT_ITEM);

			rootElement.appendChild(rootItem);

			Element urlItem = doc.createElement(ITEM);
			urlItem.setAttribute(TYPE, FOLDER);
			urlItem.setAttribute(NAME, url);

			rootItem.appendChild(urlItem);

			Element branchItem = doc.createElement(ITEM);
			branchItem.setAttribute(TYPE, FOLDER);
			branchItem.setAttribute(NAME, BRANCHES);
			branchItem.setAttribute(URL, url);
			urlItem.appendChild(branchItem);

			Element tagItem = doc.createElement(ITEM);
			tagItem.setAttribute(TYPE, FOLDER);
			tagItem.setAttribute(NAME, TAGS);
			tagItem.setAttribute(URL, url);
			urlItem.appendChild(tagItem);


			Element trunkItem = doc.createElement(ITEM);
			trunkItem.setAttribute(TYPE, FOLDER);
			trunkItem.setAttribute(NAME, TRUNK);
			trunkItem.setAttribute(URL, url);
			urlItem.appendChild(trunkItem);

			List<String> trunkList = list.get(TRUNK);
			for (String trunk: trunkList) {
				Element trunkItems = doc.createElement(ITEM);
				trunkItems.setAttribute(NAME, trunk);
				trunkItems.setAttribute(URL, url);
				trunkItems.setAttribute(REQ_APP_DIR_NAME, appDirName);
				trunkItems.setAttribute(NATURE, TRUNK);
				trunkItem.appendChild(trunkItems);
			}

			List<String> branchList = list.get(BRANCHES);
			for (String branch: branchList) {
				Element branchItems = doc.createElement(ITEM);
				branchItems.setAttribute(NAME, branch);
				branchItems.setAttribute(URL, url);
				branchItems.setAttribute(REQ_APP_DIR_NAME, appDirName);
				branchItems.setAttribute(NATURE, BRANCHES);
				branchItem.appendChild(branchItems);
			}

			List<String> tagList = list.get(TAGS);
			for (String tag: tagList) {
				Element tagItems = doc.createElement(ITEM);
				tagItems.setAttribute(NAME, tag);
				tagItems.setAttribute(URL, url);
				tagItems.setAttribute(REQ_APP_DIR_NAME, appDirName);
				tagItems.setAttribute(NATURE, TAGS);
				tagItem.appendChild(tagItems);
			}

			return doc;
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		}
	}

	private Artifact readArtifact(String appDirName, String version, String moduleName) throws PhrescoException {
		Artifact artifact = null;
		try {
			String appDirPath = Utility.getProjectHome() + appDirName;
			ProjectInfo projectInfo = Utility.getProjectInfo(appDirPath, "");
			File sourceFolderLocation = Utility.getSourceFolderLocation(projectInfo, appDirPath, moduleName);
			File pomFile = new File (sourceFolderLocation, POM_FILE);
			if (pomFile.exists()) {
				PomProcessor processor = new PomProcessor(pomFile);
				if (StringUtils.isNotEmpty(version)) {
					artifact = new DefaultArtifact(processor.getGroupId(), processor.getArtifactId(), "", processor.getPackage(), version);
				} else {
					artifact = new DefaultArtifact(processor.getGroupId(), processor.getArtifactId(), "", processor.getPackage(), "");
				}
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return artifact;
	}

	private Response importPerforceApplication(String type, RepoDetail repodetail, String displayName) throws Exception {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject=scmi.importFromPerforce(repodetail, new File(""));
			if (importProject != null) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200017;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210022;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (ConnectionException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210049;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} 
		catch (RequestException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210050;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} 
		catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210026;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	}

	private Response importTfsApplication(String type, RepoInfo repodetail, String displayName) throws Exception {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		UUID uniqueKey = UUID.randomUUID();
		String unique_key = uniqueKey.toString();
		try {
			ApplicationInfo importProject = scmi.importProject(repodetail, displayName, unique_key);
			if (importProject != null) {
				status = RESPONSE_STATUS_SUCCESS;
				successCode = PHR200017;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
				.build();
			} else {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210022;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (Exception e) {
			if (PROJECT_ALREADY_IMPORTED.equals(e.getLocalizedMessage())) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210027;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else if ("Failed to import project".equals(e.getLocalizedMessage())) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210026;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} finally {
			try {
				LockUtil.removeLock(unique_key);
			} catch (PhrescoException e) {
			}
		}
	}

	/**
	 * Update git project.
	 *
	 * @param responseData the response data
	 * @param type the type
	 * @param applicationInfo the application info
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response updateGitProject(ResponseInfo responseData, String type, ApplicationInfo applicationInfo,
			RepoDetail repodetail, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), FrameworkConstants.UPDATE, displayName, uniqueKey)), true);

			scmi.updateProject(repodetail, new File(""));

			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (InvalidRemoteException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (TransportException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (SVNAuthenticationException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210023;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210024;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210025;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Update svn project.
	 *
	 * @param responseData the response data
	 * @param type the type
	 * @param applicationInfo the application info
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response updateSVNProject(ResponseInfo responseData, ApplicationInfo applicationInfo,
			String appDirName, RepoInfo repoInfo, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), 
					FrameworkConstants.UPDATE, displayName, uniqueKey)), true);
			RepoDetail srcRepoDetail = repoInfo.getSrcRepoDetail();
			RepoDetail testRepoDetail = repoInfo.getTestRepoDetail();
			RepoDetail phrescoRepoDetail = repoInfo.getPhrescoRepoDetail();
			StringBuilder workingDir = new StringBuilder(Utility.getProjectHome()).append(appDirName);
			String splitSrcFolderName = "";
			String splitTestFolderName = "";
			File splitPhrescoDir = new File(workingDir.toString(), appDirName + Constants.SUFFIX_PHRESCO);
			File splitSrcDir = new File(workingDir.toString(), appDirName);

			File pomFile = null;
			if (splitPhrescoDir.exists()) {
				if (StringUtils.isNotEmpty(applicationInfo.getPhrescoPomFile())) {
					pomFile = new File(splitPhrescoDir.getPath(), applicationInfo.getPhrescoPomFile());
				} else if (splitSrcDir.exists())  {
					pomFile = new File(splitSrcDir.getPath(), applicationInfo.getPomFile());
				}
			} else if (splitSrcDir.exists()) {
				String pomFileName = applicationInfo.getPomFile();
				if (StringUtils.isNotEmpty(applicationInfo.getPhrescoPomFile())) {
					pomFileName = applicationInfo.getPhrescoPomFile();
				}
				pomFile = new File(splitSrcDir.getPath(), pomFileName);
			}
			if (pomFile != null && pomFile.exists()) {
				PomProcessor pomProcessor = new PomProcessor(pomFile);
				splitSrcFolderName = pomProcessor.getProperty(Constants.POM_PROP_KEY_SPLIT_SRC_DIR);
				splitTestFolderName = pomProcessor.getProperty(Constants.POM_PROP_KEY_SPLIT_TEST_DIR);
			}

			if (repoInfo.isSplitPhresco() && phrescoRepoDetail != null && splitPhrescoDir.exists()) {
				scmi.updateProject(phrescoRepoDetail, splitPhrescoDir);
			}
			if (repoInfo.isSplitTest() && testRepoDetail != null && StringUtils.isNotEmpty(splitTestFolderName)) {
				scmi.updateProject(srcRepoDetail, new File(workingDir.toString(), splitTestFolderName));
			}

			if (srcRepoDetail != null) {
				if (splitSrcDir.exists()) {
					workingDir.append(File.separator).append(splitSrcFolderName);
				}
				scmi.updateProject(srcRepoDetail, new File(workingDir.toString()));
			}
			scmi.updateSCMConnection(repoInfo, applicationInfo);	
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
					null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (InvalidRemoteException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (TransportException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210024;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (SVNAuthenticationException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210023;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (SVNException e) {
			if (e.getMessage().indexOf(SVN_FAILED) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210024;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else if (e.getMessage().indexOf(SVN_INTERNAL) != -1) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR210025;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else if (e.getMessage().indexOf(SVN_IS_NOT_WORKING_COPY) != -1) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210029;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (FileExistsException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210027;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Update bit keeper project.
	 *
	 * @param type the type
	 * @param applicationInfo the application info
	 * @param repodetail the repodetail
	 * @return the response
	 */
	private Response updateBitKeeperProject(String type, ApplicationInfo applicationInfo, RepoDetail repodetail, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), FrameworkConstants.UPDATE, displayName, uniqueKey)), true);
			scmi.updateProject(repodetail, new File(""));
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to pull")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210030;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	private Response updatePerforceProject(String type, ApplicationInfo applicationInfo, RepoDetail repodetail, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), FrameworkConstants.UPDATE, displayName, uniqueKey)), true);
			scmi.updateProject(repodetail, new File(""));
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200018;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to pull")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210030;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210028;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (ConnectionException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210049;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (RequestException e) {
			status = RESPONSE_STATUS_FAILURE;
			errorCode = PHR210050;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210028;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}
	
	/**
	 * Adds the svn project.
	 *
	 * @param appDirName the app dir name
	 * @param repodetail the repodetail
	 * @param userId the user id
	 * @param projectId the project id
	 * @param appId the app id
	 * @param type the type
	 * @return the response
	 */
	private Response addToRepo(String appDirName, RepoInfo repoInfo, String userId, String projectId,
			String appId, String type, String uniqueKey, String displayName) {
		SCMManagerImpl scmi = new SCMManagerImpl();
		ResponseInfo responseData = new ResponseInfo();
		try {
			ApplicationInfo applicationInfo = FrameworkServiceUtil.getApplicationInfo(appDirName);
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), ADD_TO_REPO, displayName, uniqueKey)), true);
			scmi.importToRepo(repoInfo, applicationInfo);
			User user = ServiceManagerImpl.USERINFO_MANAGER_MAP.get(userId);
			// updateLatestProject(user, projectId, appId);
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200019;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
					null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("git-receive-pack not found")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210032;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()),
						null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210031;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Commit svn project.
	 *
	 * @param repodetail the repodetail
	 * @return the response
	 */
	public Response commitSVNProject(RepoDetail repodetail, ApplicationInfo applicationInfo, String uniqueKey, String displayName) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			if (CollectionUtils.isNotEmpty(repodetail.getCommitableFiles())) {
				List<File> listModifiedFiles = new ArrayList<File>(repodetail.getCommitableFiles().size());
				for (String commitableFile : repodetail.getCommitableFiles()) {
					listModifiedFiles.add(new File(commitableFile));
				}
				//To generate the lock for the particular operation
				LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), COMMIT, displayName, uniqueKey)), true);

				scmi.commitSpecifiedFiles(listModifiedFiles, repodetail.getUserName(), repodetail.getPassword(),
						repodetail.getCommitMessage());
			}
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200020;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null,
					null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210033;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Commit bit keeper project.
	 *
	 * @param appDirName the app dir name
	 * @param repodetail the repodetail
	 * @param type the type
	 * @return the response
	 */
	public Response commitBitKeeperProject(ApplicationInfo applicationInfo, RepoDetail repodetail, 
			String type, String uniqueKey, String displayName, File workingDir) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), COMMIT , displayName, uniqueKey)), true);
			scmi.commitToRepo(repodetail, workingDir);
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200020;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			if (e.getLocalizedMessage().contains("Nothing to push")) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210034;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR210033;
				ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), COMMIT_PROJECT_FAIL, null);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
				"*").build();
			}
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210033;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Commit git project.
	 *
	 * @param appDirName the app dir name
	 * @param repodetail the repodetail
	 * @param type the type
	 * @return the response
	 */
	public Response commitGitProject(ApplicationInfo applicationInfo, RepoDetail repodetail, String type, 
			String uniqueKey, String displayName, File workingDir) {
		ResponseInfo responseData = new ResponseInfo();
		SCMManagerImpl scmi = new SCMManagerImpl();
		try {
			//To generate the lock for the particular operation
			LockUtil.generateLock(Collections.singletonList(LockUtil.getLockDetail(applicationInfo.getId(), COMMIT , displayName, uniqueKey)), true);
			if (!repodetail.getCommitableFiles().isEmpty()) {
				scmi.commitToRepo(repodetail, workingDir);
			}
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR200020;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, null, null, status, successCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR210033;
			ResponseInfo finalOutput = responseDataEvaluation(responseData, new Exception(e.getMessage()), null, status, errorCode);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
			.build();
		} finally {
			try {
				LockUtil.removeLock(uniqueKey);
			} catch (PhrescoException e) {

			}
		}
	}

	/**
	 * Gets the repo type.
	 *
	 * @param repoUrl the repo url
	 * @return the repo type
	 */
	private String getRepoType(String repoUrl) {
		String repoType = "";
		if (repoUrl.startsWith("bk")) {
			repoType = BITKEEPER;
		} else if (repoUrl.endsWith(".git") || repoUrl.contains("gerrit") || repoUrl.startsWith("ssh")) {
			repoType = GIT;
		} else if (repoUrl.contains("svn")) {
			repoType = SVN;
		}
		return repoType;
	}

	private RepoDetail createRepoDetail(String repoUrl, String userId, String action, File workingDir) throws PhrescoException {
		RepoDetail repodetail = new RepoDetail();
		boolean setRepoExistForCommit = false;
		try {
			String repoType = getRepoType(repoUrl);
			if (repoType.equals(BITKEEPER)) {
				repodetail.setRepoExist(true);
			} else if (repoType.equals(GIT)) {
				setRepoExistForCommit  = checkGitProject(workingDir, setRepoExistForCommit);
				repodetail.setRepoExist(setRepoExistForCommit);
				repodetail.setRepoInfoFile(gitCommitableFiles(workingDir));
			} else if (repoType.equals(SVN)) {
				repodetail.setRepoExist(true);
				repodetail.setRepoInfoFile(svnCommitableFiles(workingDir));
			}
			repodetail.setUserName(userId);
			repodetail.setType(repoType);
			repodetail.setRepoUrl(repoUrl);
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
		return repodetail;
	}

	private void fillRepoDetail(RepoDetail repoDetail, String url, String userId, boolean exist) {
		repoDetail.setUserName(userId);
		repoDetail.setRepoUrl(url);
		repoDetail.setRepoExist(exist);
		repoDetail.setType(getRepoType(url));
	}

	private File getPomFromWrokDir(ApplicationInfo appInfo) {
		String appDirName = appInfo.getAppDirName();
		StringBuilder filePath = new StringBuilder(Utility.getProjectHome()).append(appDirName);
		File dotPhresoFile = new File(filePath.toString(), appDirName + Constants.SUFFIX_PHRESCO);
		if(dotPhresoFile.exists() && dotPhresoFile.isDirectory()) {
			if(StringUtils.isNotEmpty(appInfo.getAppDirName())) {
				filePath.append(File.separator).append(appDirName + Constants.SUFFIX_PHRESCO).append(File.separator).append(appInfo.getPhrescoPomFile());
				return new File(filePath.toString());
			}
		}
		File sourceFile = new File(filePath.toString(), appDirName);
		if(sourceFile.exists() && sourceFile.isDirectory()) {
			filePath.append(File.separator).append(appDirName).append(File.separator).append(appInfo.getPomFile());
			return new File(filePath.toString());
		}

		return new File(filePath.toString(), appInfo.getPomFile());
	}

	/**
	 * Gets the connection url.
	 *
	 * @param applicationInfo the application info
	 * @return the connection url
	 * @throws PhrescoException the phresco exception
	 */
	private String getConnectionUrl(ApplicationInfo applicationInfo) throws PhrescoException {
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			PomProcessor processor = frameworkUtil.getPomProcessor(applicationInfo);
			Scm scm = processor.getSCM();
			if (scm != null && !scm.getConnection().isEmpty()) {
				return scm.getConnection();
			}
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}

		return "";
	}

	/**
	 * Check git project.
	 *
	 * @param applicationInfo the application info
	 * @param setRepoExistForCommit the set repo exist for commit
	 * @return true, if successful
	 * @throws PhrescoException the phresco exception
	 */
	private boolean checkGitProject(File  workingDir, boolean setRepoExistForCommit)
	throws PhrescoException {
		setRepoExistForCommit = true;
		String url = "";
		InitCommand initCommand = Git.init();
		initCommand.setDirectory(workingDir);
		Git git = null;
		try {
			git = initCommand.call();
		} catch (GitAPIException e) {
			throw new PhrescoException(e);
		}

		Config storedConfig = git.getRepository().getConfig();
		url = storedConfig.getString(REMOTE, ORIGIN, URL);
		if (StringUtils.isEmpty(url)) {
			File toDelete = git.getRepository().getDirectory();
			try {
				FileUtils.deleteDirectory(toDelete);
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
			setRepoExistForCommit = false;

		}
		git.getRepository().close();

		return setRepoExistForCommit;
	}

	/**
	 * Svn commitable files.
	 *
	 * @param appDirName the app dir name
	 * @return the list
	 * @throws PhrescoException the phresco exception
	 */
	private List<RepoFileInfo> svnCommitableFiles(File workingDir) throws PhrescoException {
		List<RepoFileInfo> commitableFiles = null;
		String revision = "";
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			revision = HEAD_REVISION;
			commitableFiles = scmi.getCommitableFiles(workingDir, revision);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return commitableFiles;
	}

	/**
	 * Git commitable files.
	 *
	 * @param appDirName the app dir name
	 * @return the list
	 * @throws PhrescoException the phresco exception
	 */
	private List<RepoFileInfo> gitCommitableFiles(File workingDir) throws PhrescoException {
		List<RepoFileInfo> gitCommitableFiles = null;
		try {
			SCMManagerImpl scmi = new SCMManagerImpl();
			gitCommitableFiles = scmi.getGITCommitableFiles(workingDir);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return gitCommitableFiles;
	}

	/**
	 * Update latest project.
	 *
	 * @param user the user
	 * @param projectId the project id
	 * @param appId the app id
	 * @throws PhrescoException the phresco exception
	 */
	private void updateLatestProject(User user, String projectId, String appId) throws PhrescoException {
		try {
			File tempPath = new File(Utility.getPhrescoTemp() + File.separator + USER_PROJECT_JSON);
			JSONObject userProjJson = null;
			JSONParser parser = new JSONParser();
			if (tempPath.exists()) {
				FileReader reader = new FileReader(tempPath);
				userProjJson = (JSONObject) parser.parse(reader);
				reader.close();
			} else {
				userProjJson = new JSONObject();
			}

			userProjJson.put(user.getId(), projectId + Constants.STR_COMMA + appId);
			FileWriter writer = new FileWriter(tempPath);
			writer.write(userProjJson.toString());
			writer.close();
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (ParseException e) {
			throw new PhrescoException(e);
		}
	}

	private static Document constructDomSource(Artifact artifacts, String appDirName, List<String> urls, String moduleName, Document doc, Element appendItem) throws PhrescoException {
		List<UUID> randomIds = new ArrayList<UUID>();
		try {
			Element snapshot = doc.createElement(ITEM);
			snapshot.setAttribute(TYPE, FOLDER);
			snapshot.setAttribute(PATH, "");
			snapshot.setAttribute(NAME, SNAPSHOT);
			appendItem.appendChild(snapshot);


			Element release = doc.createElement(ITEM);
			release.setAttribute(TYPE, FOLDER);
			release.setAttribute(PATH, "");
			release.setAttribute(NAME, RELEASE);
			appendItem.appendChild(release);

			RepositorySystem system = newRepositorySystem();
			Artifact artifact = new DefaultArtifact(artifacts.getGroupId() + ":" + artifacts.getArtifactId()+ ":" + artifacts.getExtension() + ":" + "[,)");
			for (String url : urls) {
				UUID randomUUID = UUID.randomUUID();
				randomIds.add(randomUUID);
				RepositorySystemSession session = newRepositorySystemSession( system , randomUUID, "");
				RemoteRepository repo =  new RemoteRepository("", DEFAULT, url);
				VersionRangeRequest rangeRequest = new VersionRangeRequest();
				rangeRequest.setArtifact(artifact);
				rangeRequest.addRepository(repo);
				VersionRangeResult rangeResult = system.resolveVersionRange( session, rangeRequest);
				List<Version> versions = rangeResult.getVersions();
				if (versions.size() > 10) {
					versions = versions.subList(versions.size() - 10, versions.size());
				}
				constructArtifactItem(versions, artifact, repo, doc, appDirName, moduleName);
				clearCache(randomUUID);
			}
			urls.clear();
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (VersionRangeResolutionException e) {
			throw new PhrescoException(e);
		} finally {
			if (CollectionUtils.isNotEmpty(randomIds)) {
				for (UUID uuid : randomIds) {
					clearCache(uuid);
				}
			}
		}
		return doc;
	}

	private static void constructArtifactItem(List<Version> versions, Artifact artifactInfo, RemoteRepository repo, Document doc, String appDirName, String moduleName) throws PhrescoException {
		try {
			Element versionItem = null;
			Element element =  null;
			if (CollectionUtils.isNotEmpty(versions)) {
				for (Version vers : versions) {
					String version = vers.toString();
					Artifact repoArtifact = new DefaultArtifact(artifactInfo.getGroupId(), artifactInfo.getArtifactId(), artifactInfo.getExtension(), version);
					MavenDefaultLayout defaultLayout = new MavenDefaultLayout();
					URI Paths = defaultLayout.getPath(repoArtifact);
					String artifactPath = Paths.getPath();
					String pathString = Paths.toString();
					String fileName = pathString.substring(pathString.lastIndexOf("/") + 1);

					Element jarItem = doc.createElement(ITEM);
					jarItem.setAttribute(TYPE, FILE);
					jarItem.setAttribute(NAME, fileName);
					jarItem.setAttribute(REQ_APP_DIR_NAME, appDirName);
					jarItem.setAttribute(MODULE_NAME, moduleName);

					versionItem = doc.createElement(ITEM);
					versionItem.setAttribute(TYPE, FOLDER);
					versionItem.setAttribute(NAME, version);
					versionItem.setAttribute(PATH, artifactPath);

					versionItem.appendChild(jarItem);
					String expression = "";
					if (StringUtils.isNotEmpty(moduleName)) {
						expression = ROOT_ITEM_XPATH + moduleName + NAME_FILTER_SUFIX;
					}
					
					if (version.contains(SNAPSHOT)) {
						jarItem.setAttribute(NATURE, SNAPSHOT);
						XPath xpath = XPathFactory.newInstance().newXPath();
						expression = expression + SNAPSHOT_ITEM;
						Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
						if (node != null) {
							element = (Element) node;
							element.appendChild(versionItem);
						}
					} else {
						jarItem.setAttribute(NATURE, RELEASE);
						XPath xpath = XPathFactory.newInstance().newXPath();
						expression = expression + RELEASE_ITEM;
						Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
						if (node != null) {
							element = (Element) node;
							element.appendChild(versionItem);
						}
					}
				}
			}
		} catch (DOMException e) {
			throw new PhrescoException(e);
		} catch (XPathExpressionException e) {
			throw new PhrescoException(e);
		}
	}

	private static RepositorySystem newRepositorySystem() throws PhrescoException {
		try {
			return new DefaultPlexusContainer().lookup(RepositorySystem.class);
		} catch (ComponentLookupException e) {
			throw new PhrescoException(e);
		} catch (PlexusContainerException e) {
			throw new PhrescoException(e);
		}
	}

	private static RepositorySystemSession newRepositorySystemSession(RepositorySystem system, UUID randomUUID, String localPath) {
		MavenRepositorySystemSession session = new MavenRepositorySystemSession();
		LocalRepository localRepo = null;
		if (randomUUID != null) {
			localRepo = new LocalRepository(Utility.getPhrescoTemp() + File.separator + randomUUID);
		} else if (StringUtils.isNotEmpty(localPath)) {
			localRepo = new LocalRepository(localPath);
		}
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));
		return session;
	}

	private static void clearCache(UUID randomUUID)  throws PhrescoException {
		try {
			File path = new File(Utility.getPhrescoTemp() + File.separator + randomUUID);
			if (path.exists()) {
				FileUtils.deleteDirectory(path);
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

	private static String convertDocumentToString(Document doc) throws PhrescoException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			throw new PhrescoException(e);
		}
	}

	private static SVNClientManager getSVNClientManager(String userName, String password) {
		DAVRepositoryFactory.setup();
		DefaultSVNOptions options = new DefaultSVNOptions();
		return SVNClientManager.newInstance(options, userName, password);
	}

	private static SVNURL getSVNURL(String repoURL) throws PhrescoException { 
		SVNURL svnurl = null;
		try {
			svnurl = SVNURL.parseURIEncoded(repoURL);
		} catch (SVNException e) {
			throw new PhrescoException(e);
		}
		return svnurl;
	}

	private ActionResponse generateResponse(BufferedInputStream server_logs, String unique_key) {
		BufferMap.addBufferReader(unique_key, server_logs);
		ActionResponse response = new ActionResponse();
		response.setStatus(ActionServiceConstant.STARTED);
		response.setLog(ActionServiceConstant.STARTED);
		response.setService_exception("");
		response.setUniquekey(unique_key);

		return response;
	}
	
	private BufferedInputStream executeMavenCommand(StringBuilder command, String workingDirectory) throws PhrescoException {
		Commandline cl = new Commandline(command.toString());
        if (StringUtils.isNotEmpty(workingDirectory)) {
            cl.setWorkingDirectory(workingDirectory);
        } 
        try {
            Process process = cl.execute();
            return new BufferedInputStream(new MyWrapper(process.getInputStream()));
        } catch (CommandLineException e) {
            throw new PhrescoException(e);
        }
    }
	
	static class MyWrapper extends PushbackInputStream {
	    MyWrapper(InputStream in) {
	        super(in);
	    }
	
	    @Override
	    public int available() throws IOException {
	        int b = super.read();
	        super.unread(b);
	        return super.available();
	    }
	}
}
