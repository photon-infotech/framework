/**
 * Phresco Framework
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
package com.photon.phresco.framework.api;

import java.util.HashMap;
import java.util.List;

import org.apache.wink.json4j.OrderedJSONObject;

import com.photon.phresco.commons.model.Dashboard;
import com.photon.phresco.commons.model.DashboardInfo;
import com.photon.phresco.commons.model.Dashboards;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.Widget;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.DeleteProjectInfo;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.DashboardSearchInfo;

public interface ProjectManager {

	/**
	 * This method provides the list of projects available in the local phresco workspace for the given customerId
	 * @param CustomerId
	 * @return Returns list of ProjectInfos
	 * @throws PhrescoException
	 */
	List<ProjectInfo> discover(String customerId) throws PhrescoException;
	
	/**
	 * This method provides the list of projects of all customers available in the local phresco workspace 
	 * @return Returns list of ProjectInfos
	 * @throws PhrescoException
	 */
	List<ProjectInfo> discover() throws PhrescoException;
	
	/**
	 * This method provides the list of projects under a rootModule available in the local phresco workspace 
	 * @return Returns list of ProjectInfos
	 * @throws PhrescoException
	 */
	List<ProjectInfo> discoverFromRootModule(String rootModule) throws PhrescoException;
	
	/**
	 * This method returns the Project for the given projectId
	 * @param projectId
	 * @return Returns the ProjectInfo with all the ApplicationInfos
	 * @throws PhrescoException
	 */
	ProjectInfo getProject(String projectId, String customerId) throws PhrescoException;
	
	/**
	 * This method creates the project with the selected application layers
	 * @param ProjectInfo
	 * @return ProjectInfo
	 * @throws PhrescoException
	 */
	ProjectInfo create(ProjectInfo projectInfo, ServiceManager serviceManager) throws PhrescoException;
	
	/**
	 * This method updates the project
	 * @param ProjectInfo
	 * @return ProjectInfo
	 * @throws PhrescoException
	 */
	ProjectInfo updateApplication(ProjectInfo projectInfo, ServiceManager serviceManager, String oldAppDirName, String rootModule) throws PhrescoException;
	
	
	/**
	 * @param projectInfo
	 * @param serviceManager
 	 * @param rootModule
	 * @return
	 * @throws PhrescoException
	 */
	ProjectInfo updateApplicationFeatures(ProjectInfo projectInfo, ServiceManager serviceManager, String rootModule) throws PhrescoException;
	/**
	 * This method deletes the project in local filesystem and not is server
	 * @param ProjectInfo
	 * @return boolean - returns true if deletion is success and false if deletion fails.
	 * @throws PhrescoException
	 */
	boolean delete(DeleteProjectInfo deleteProjectInfo) throws PhrescoException;
	
	/**
	 * This method returns the ProjectInfo of the given appId
	 * @param projectId
	 * @param customerId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	ProjectInfo getProject(String projectId, String customerId, String appId) throws PhrescoException;
	
	/**
	 * This method returns dashboard id of new dashboard
	 * @param dashboardInfo
	 * @return
	 * @throws PhrescoException
	 */
	String configureDashboardConfig(DashboardInfo dashboardInfo) throws PhrescoException;

	/**
	 * This method returns dashboard 
	 * @param projectid
	 * @param appdirname
	 * @param dashboardid
	 * @return
	 * @throws PhrescoException
	 */
	Dashboard getDashboardConfig(String projectid, String appdirname, String dashboardid) throws PhrescoException;
	
	/**
	 * This method returns status of update operation
	 * @param dashboardInfo
	 * @return
	 * @throws PhrescoException
	 */
	boolean updateDashboardConfig(DashboardInfo dashboardInfo) throws PhrescoException;
	
	/**
	 * Returns the all dashboards of all the applications under the given project
	 * @param projectid
	 * @return
	 * @throws PhrescoException
	 */
	HashMap<String, Dashboards> listAllDashboardConfig(String projectid) throws PhrescoException;

	/**
	 * This method returns widget id of the new widget
	 * @param dashboardInfo
	 * @return
	 * @throws PhrescoException
	 */
	String addDashboardWidgetConfig(DashboardInfo dashboardInfo) throws PhrescoException;
	
	/**
	 * This method returns Widget of the given widgetid
	 * @param projectid
	 * @param appdirname
	 * @param dashboardid
	 * @param widgetid
	 * @return
	 * @throws PhrescoException
	 */
	Widget getDashboardWidgetConfig(String projectid, String appdirname, String dashboardid, String widgetid) throws PhrescoException;
	
	/**
	 * This method returns status of update operation
	 * @param dashboardInfo
	 * @return
	 * @throws PhrescoException
	 */
	Boolean updateDashboardWidgetConfig(DashboardInfo dashboardInfo) throws PhrescoException;

	/**
	 * This method returns list of all dashboards of the application
	 * @param projectid
	 * @param appdirname
	 * @return
	 * @throws PhrescoException
	 */
	Dashboards listDashboardWidgetConfig(String projectid, String appdirname) throws PhrescoException;
	
	/**
	 * @param dashboardInfo
	 * @return
	 * @throws PhrescoException
	 */
	Boolean deleteDashboardConfig(DashboardInfo dashboardInfo) throws PhrescoException;
	
	/**
	 * @param dashboardInfo
	 * @return
	 * @throws PhrescoException
	 */
	Boolean deleteDashboardWidgetConfig(DashboardInfo dashboardInfo) throws PhrescoException;
	
	/**
	 * This method returns the Search result of the given Dashboard query for a project
	 * @param projectId
	 * @param customerId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	OrderedJSONObject getdata(DashboardSearchInfo dashboardsearchinfo) throws PhrescoException;
	
	
	ProjectInfo  addIntegrationTest(ProjectInfo projectInfo, ServiceManager serviceManager) throws PhrescoException;
}
