/**
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.SequenceInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse;

public class ApplicationManagerImpl implements ApplicationManager {

	private static final Logger S_LOGGER= Logger.getLogger(ApplicationManagerImpl.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	@Override
	public ProjectInfo update(ProjectInfo projectInfo,
			ServiceManager serviceManager) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.update(" +
					"ProjectInfo projectInfo, ServiceManager serviceManager)");
			S_LOGGER.debug("performAction() ProjectInformation = "+ projectInfo.getAppInfos().get(0));
		}
		ClientResponse response = null;
		try {
			response = serviceManager.updateProject(projectInfo);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		if (response.getStatus() == 200) {
				//TODO Define post update object and execute the corresponding technology implementation
//				updateProjectPOM(projectInfo);
//				if (TechnologyTypes.WIN_METRO.equalsIgnoreCase(techId)) {
//					ItemGroupUpdater.update(projectInfo, projectPath);
//				}
			
		} else if(response.getStatus() == 401){
			throw new PhrescoException("Session expired");
		} else {
			throw new PhrescoException("Project updation failed");
		}
		
		return projectInfo;
	}

	@Override
	public BufferedInputStream performAction(ProjectInfo projectInfo, ActionType action, List<String> mavenArgCommands, String workingDirectory) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.performAction(" +
					"Project project, ActionType action, Map<String, String> paramsMap,CallBack callBack)");
			if (projectInfo != null) {
				S_LOGGER.debug("performAction() ProjectInformation = "+projectInfo.getAppInfos().get(0));
			}
		}
		StringBuilder command = buildMavenCommand(action, mavenArgCommands);
		String buildVersion = projectInfo.getAppInfos().get(0).getBuildVersion();
		command.append(Constants.SPACE);
		command.append("-Dbuild.version=" + buildVersion);
		if (action.equals(ActionType.LIQUIBASE)) {
			return executeLiquibaseMavenCommand(projectInfo, action, command, workingDirectory);
		} else {
			return executeMavenCommand(projectInfo, action, command, workingDirectory);
		}
	}

	@Override
	public void configureReport(ApplicationInfo appInfo,List<String> reportOptions) throws PhrescoException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BuildInfo> getBuildInfos(File buildInfoFile) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.getBuildInfos(File buildInfoFile)");
		}
		 try {
			 return readBuildInfo(buildInfoFile);
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 }
	}
	
	@Override
	public BuildInfo getBuildInfo(int buildNumber, String buildInfoFileDirectory) throws PhrescoException {
		List<BuildInfo> buildInfos = getBuildInfos(new File(buildInfoFileDirectory));
		if (CollectionUtils.isNotEmpty(buildInfos)) {
			for (BuildInfo buildInfo : buildInfos) {
				if (buildInfo.getBuildNo() == buildNumber) {
					return buildInfo;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public ApplicationInfo getApplicationInfo(String customerId, String projectId, String appId) throws PhrescoException {
		ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
		List<ProjectInfo> projectInfos = projectManager.discover(customerId);
		for (ProjectInfo projectInfo : projectInfos) {
			if(projectInfo.getId().equals(projectId)) {
				List<ApplicationInfo> appInfos = projectInfo.getAppInfos();
				for (ApplicationInfo applicationInfo : appInfos) {
					if(applicationInfo.getId().equals(appId)) {
						return applicationInfo;
					}
				}
			}
		}

		return null;
	}
	
	public StringBuilder buildMavenCommand(ActionType actionType, List<String> mavenArgCommands) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.buildMavenCommand(ProjectInfo projectInfo, ActionType actionType)");
		}
        StringBuilder builder = new StringBuilder(Constants.MVN_COMMAND);
        builder.append(Constants.SPACE);
        builder.append(actionType.getActionType());
        
        if (CollectionUtils.isNotEmpty(mavenArgCommands)) {
        	for (String mavenArgCommand : mavenArgCommands) {
        		builder.append(Constants.SPACE);
        		builder.append(mavenArgCommand);
			}
        }
        return builder;
    }
	
	private BufferedInputStream executeMavenCommand(ProjectInfo projectInfo, ActionType action, StringBuilder command, String workingDirectory) throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method ApplicationManagerImpl.executeMavenCommand(Project project, ActionType action, StringBuilder command)");
    		S_LOGGER.debug("executeMavenCommand() Command = " + command.toString());
    		S_LOGGER.debug("executeMavenCommand() ActionType Name = " + action.getActionType());
		}
    	createPomArg(projectInfo, command, workingDirectory);
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

	private BufferedInputStream executeLiquibaseMavenCommand(ProjectInfo projectInfo, ActionType action, StringBuilder command, String workingDirectory) throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method ApplicationManagerImpl.executeMavenCommand(Project project, ActionType action, StringBuilder command)");
    		S_LOGGER.debug("executeMavenCommand() Project Code = " + projectInfo.getProjectCode());
    		S_LOGGER.debug("executeMavenCommand() Command = " + command.toString());
    		S_LOGGER.debug("executeMavenCommand() ActionType Name = " + action.getActionType());
		}

    	createPomArg(projectInfo, command, workingDirectory);
		Commandline cl = new Commandline(command.toString());
        if (StringUtils.isNotEmpty(workingDirectory)) {
            cl.setWorkingDirectory(workingDirectory);
        } 
        try {
            Process process = cl.execute();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStream, errorStream);
            return new BufferedInputStream(new MyWrapper(sequenceInputStream));
        } catch (CommandLineException e) {
            throw new PhrescoException(e);
        }
    }
	
	private StringBuilder createPomArg(ProjectInfo projectInfo, StringBuilder builder, String workingDir) {
		if (projectInfo != null) {
			ApplicationInfo applicationInfo = projectInfo.getAppInfos().get(0);
			String pomFileName = "";
			File pomFile = new File(workingDir + File.separator + applicationInfo.getPhrescoPomFile());
			if (pomFile.exists()) {
				pomFileName = applicationInfo.getPhrescoPomFile();
			} else {
				pomFile = new File(workingDir + File.separator + applicationInfo.getPomFile());
				if (pomFile.exists()) {
					pomFileName = applicationInfo.getPomFile();
				}
			}
				// String pomFileName = Utility.getPhrescoPomFile(applicationInfo);
			if(!Constants.POM_NAME.equals(pomFileName)) {
				builder.append(Constants.SPACE);
				builder.append("-f");
				builder.append(Constants.SPACE);
				builder.append(pomFileName);
			}
			}
		return builder;

	}
	
	private List<BuildInfo> readBuildInfo(File path) throws IOException, PhrescoException {
		 S_LOGGER.debug("Entering Method ApplicationManagerImpl.readBuildInfo(File path)");
		 S_LOGGER.debug("getBuildInfos() File Path = "+path.getPath());

		 if (!path.exists()) {
			 S_LOGGER.error("readBuildInfo() > " + FrameworkImplConstants.ERROR_FILE_PATH_INCORRECT + path);
			 return new ArrayList<BuildInfo>(1);
		 }

		 BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		 Gson gson = new Gson();
		 Type type = new TypeToken<List<BuildInfo>>(){}.getType();

		 List<BuildInfo> buildInfos = gson.fromJson(bufferedReader, type);
		 //TODO:Need to handle
		 //Collections.sort(buildInfos, new BuildInfoComparator());
		 bufferedReader.close();

		 return buildInfos;
	 }
	
	 @Override
	 public void deleteBuildInfos(String rootModulePath, int[] buildNumbers, String subModule) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ApplicationManagerImpl.deleteBuildInfos(ProjectInfo project, int[] buildNumbers)");

		 String buildInfoFile = getBuildInfoFilePath(rootModulePath, subModule);
		 List<BuildInfo> totalBuildInfos = getBuildInfos(new File(buildInfoFile));
		 List<BuildInfo> buildInfos = new CopyOnWriteArrayList<BuildInfo>();
		 BuildInfo buildInfo;
		 for (int i = 0; i < buildNumbers.length; i++) {
			 buildInfo = getBuildInfo(buildNumbers[i], buildInfoFile);
			 buildInfos.add(buildInfo);
		 }
		 
		 deleteBuildArchive(rootModulePath, buildInfos, subModule);
		 
		 
		 for (BuildInfo selectedInfo : buildInfos) {
			 Iterator<BuildInfo> iterator = totalBuildInfos.iterator();
			 while (iterator.hasNext()) {
				 BuildInfo bi = iterator.next();
				 if (bi.getBuildNo() == selectedInfo.getBuildNo()) {
					 iterator.remove();
					 break;
				 }
			 }
		 }
		 
		 try {
			 writeBuildInfo(totalBuildInfos, new File(buildInfoFile));
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 }
	 }
	 
	 private void deleteBuildArchive(String rootModulePath, List<BuildInfo> selectedInfos, String subModuleName) throws PhrescoException {
		 S_LOGGER.debug("Entering Method AppliacationmanagerImpl.deleteBuildArchive(ProjectInfo project, List<BuildInfo> selectedInfos)");
		 File file = null;
		 try {
			String delFilename = null;
			 for (BuildInfo selectedInfo : selectedInfos) {
				 //Delete zip file
				 delFilename = selectedInfo.getBuildName();
				 file = new File(getBuildInfoHome(rootModulePath, subModuleName) + delFilename);
				 FilenameUtils.removeExtension(file.getName());
				 File temp = new File(getBuildInfoHome(rootModulePath, subModuleName) + file.getName().substring(0, file.getName().length() - 4));
				 file.delete(); 
				 if (temp.exists()) {
					FileUtil.delete(temp);
				 }
			 }
			 
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	 }
	 
	 private String getBuildInfoHome(String rootModulePath, String subModuleName) throws PhrescoException {
			File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			StringBuilder buildInfoFilePath = new StringBuilder(pomFile.getParent());
			buildInfoFilePath.append(File.separator).append(FrameworkConstants.BUILD_DIR).append(File.separator);
		 return buildInfoFilePath.toString();
	 }
	 
	 private String getBuildInfoFilePath(String rootModulePath, String subModuleName) throws PhrescoException {
			File pomFile = Utility.getPomFileLocation(rootModulePath, subModuleName);
			StringBuilder buildInfoFilePath = new StringBuilder(pomFile.getParent());
			buildInfoFilePath.append(File.separator).append(FrameworkConstants.BUILD_DIR).append(File.separator).append(FrameworkConstants.BUILD_INFO_FILE_NAME);
		 return buildInfoFilePath.toString();
	 }
	 
	 private void writeBuildInfo(List<BuildInfo> buildInfos, File path) throws IOException {
		 Gson gson = new Gson();
		 String buildInfoJson = gson.toJson(buildInfos);
		 writeJson(buildInfoJson, path);
	 }
	 
	 private void writeJson(String json, File path) throws IOException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.writeJson(String json, File path)");
		 FileWriter writer = null;
		 try {
			 S_LOGGER.debug("writeJson()  File path = " +path.getPath());
			 writer = new FileWriter(path);
			 writer.write(json);
			 writer.flush();
		 } finally {
			 if (writer != null) {
				 try {
					 writer.close();
				 } catch (IOException e) {
					 S_LOGGER.warn("writeJson() > error inside finally");

				 }
			 }
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
