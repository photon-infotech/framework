<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationType"%>
<%@ page import="com.photon.phresco.commons.model.TechnologyGroup"%>
<%@ page import="com.photon.phresco.commons.model.TechnologyInfo"%>

<%
	List<ApplicationType> layers = (List<ApplicationType>) request.getAttribute(FrameworkConstants.REQ_PROJECT_LAYERS);
	List<TechnologyGroup> appLayerTechGroups = null;
	List<TechnologyGroup> webLayerTechGroups = null;
	List<TechnologyGroup> mobileLayerTechGroups = null;
	String appLayerId = "";
	String webLayerId = "";
	String mobileLayerId = "";
	String appLayerName = "";
	String webLayerName = "";
	String mobileLayerName = "";
	if (CollectionUtils.isNotEmpty(layers)) {
		for (ApplicationType layer : layers) {
		    if (FrameworkConstants.LAYER_APP_ID.equals(layer.getId())) {
		        appLayerId = layer.getId();
		        appLayerName = layer.getName();
		        appLayerTechGroups = layer.getTechGroups();
		    }
			if (FrameworkConstants.LAYER_WEB_ID.equals(layer.getId())) {
			    webLayerId = layer.getId();
			    webLayerName = layer.getName();
			    webLayerTechGroups = layer.getTechGroups();
		    }
			if (FrameworkConstants.LAYER_MOB_ID.equals(layer.getId())) {
			    mobileLayerId = layer.getId();
			    mobileLayerName = layer.getName();
			    mobileLayerTechGroups = layer.getTechGroups();
		    }
		}
	}
%>

<div class="page-header">
	<h1>
		<s:text name="lbl.projects.add"/> <small><span class="mandatory">*</span>&nbsp;<s:text name="label.mandatory"/></small>
	</h1>
</div>

<div class="appInfoScrollDiv">
	<form id="formCreateProject" autocomplete="off" class="form-horizontal app_create_project" autofocus="autofocus">
	<div class="content_adder">
		<!-- Name Starts -->
		<div class="control-group" id="projectNameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.name' />
			</label>
			<div class="controls">
				<input placeholder="<s:text name="place.hldr.proj.add.name"/>" class="input-xlarge" type="text" 
					name="projectName" maxlength="30" title="<s:text name="title.30.chars"/>">
				<span class="help-inline" id="projectNameError"></span>
			</div>
		</div>
		<!-- Name Ends -->
		
		<!-- Code Starts -->
		<div class="control-group" id="projectCodeControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='label.code' />
			</label>
			<div class="controls">
				<input placeholder='<s:text name="place.hldr.proj.add.code"/>' class="input-xlarge" type="text" 
					name="projectCode" maxlength="12" title="<s:text name="title.12.chars"/>">
				<span class="help-inline" id="projectCodeError"></span>
			</div>
		</div>
		<!-- Code Ends -->
		
		<!-- Description Starts -->
		<div class="control-group">
			<label class="control-label labelbold"><s:text name='label.description' />
			</label>
			<div class="controls">
				<textarea id="projDesc" class="input-xlarge" placeholder='<s:text name="place.hldr.proj.add.desc"/>' 
					rows="3" name="projectDesc" maxlength="150" title="<s:text name="title.150.chars"/>"></textarea>
			</div>
		</div>
		<!-- Description Ends -->
		
		<!-- Version Starts -->
		<div class="control-group">
			<label class="control-label labelbold"><s:text name='label.project.version' />
			</label>
			<div class="controls">
				<input id="projVersion" class="input-xlarge" type="text" name="projectVersion" 
					placeholder='<s:text name="place.hldr.proj.add.version"/>' maxlength="20" title="<s:text name="title.20.chars"/>">
			</div>
		</div>
		<!-- Version Starts -->
		
		<!-- PreBuild/Build it myself Starts -->
		<div class="control-group">
			<label class="control-label labelbold"></label>
			<div class="controls">
				 <ul class="inputs-list">
					<li> 
						<input type="radio" name="buildType" value="buildItMyself" checked="checked"/> 
						<span class="vAlignMiddle buildTypeSpan"><s:text name="lbl.projects.build.type.myself"/></span>
						<input type="radio" name="buildType" value="preBuild" disabled/> 
						<span class="vAlignMiddle"><s:text name="lbl.projects.build.type.prebuilt"/></span>
					</li>
				</ul>
			</div>
		</div>
		<!-- PreBuild/Build it myself ends -->
		
		<div class="control-group" id="layerControl" style="text-align: center; color: #B94A48;">
		
		</div>
	
		<!-- Application layer accordion starts -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg" id="appLayerControl">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="<%= appLayerId %>"/>
								<a id="appLayerHeading" class="vAlignSub"><%= appLayerName %></a>
								<p id="appLayerError" class="accordion-error-txt"></p>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt hideContent">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="control-group">
										<label class="accordion-control-label labelbold"><s:text name='label.technology'/></label>
										<div class="controls">
											<select class="input-xlarge" name="<%= appLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY%>">
												<option value="" selected disabled>Select Technology</option>
												<% 
													if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
														for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
														    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
														    if (CollectionUtils.isNotEmpty(techInfos)) {
														        for (TechnologyInfo techInfo : techInfos) {
												%>
																	<option value='<%= techInfo.getId() %>'><%= techInfo.getName() %></option>
												<%
														        }
														    }
														}
													}
												%>
											</select>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- Application layer accordion ends -->
		
		<!-- Web layer accordion starts -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg" id="webLayerControl">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="<%= webLayerId %>"/>
								<a id="webLayerHeading" class="vAlignSub"><%= webLayerName %></a>
								<p id="webLayerError" class="accordion-error-txt"></p>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="align-div-center">
										<div class="align-in-row">
											<label class="control-label autoWidth">
												<s:text name='lbl.projects.layer.web'/>
											</label>
										
											<select class="input-medium" name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECH_GROUP %>"
												onchange="getWebLayerWidgets('<%= webLayerId %>', '<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>');">
												<option value="" selected disabled>Select Web Layer</option>
												<%
													if (CollectionUtils.isNotEmpty(webLayerTechGroups)) {
													    for (TechnologyGroup webLayerTechGroup : webLayerTechGroups) {
												%>
															<option value="<%= webLayerTechGroup.getId() %>"><%= webLayerTechGroup.getName() %></option>
												<%
													    }
													}
												%>
											</select>
										</div>
										<div class="align-in-row">
											<label class="control-label autoWidth">
												<s:text name='lbl.projcts.add.widget'/>
											</label>
										
											<select name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" class="input-medium" onchange="getWidgetVersions(this, '<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_VERSION %>');">
												<option value="" selected disabled>Select Widget</option>
											</select>
										</div>
										<div class="float-left">
											<label class="control-label autoWidth">
												<s:text name='lbl.version'/>
											</label>
										
											<select name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_VERSION %>" class="input-medium">
												<option value="" selected disabled>Select Version</option>
											</select>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- Web layer accordion ends -->
		
		<!-- Mobile layer accordion starts -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg" id="mobileLayerControl">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="<%= mobileLayerId %>"/>
								<a id="mobileLayerHeading" class="vAlignSub"><%= mobileLayerName %></a>
								<p id="mobileLayerError" class="accordion-error-txt"></p>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
								<%
									if (CollectionUtils.isNotEmpty(mobileLayerTechGroups)) {
									    for (TechnologyGroup mobileLayerTechGroup : mobileLayerTechGroups) {
								%>
									<div class="align-div-center bottom-space" id="<%= mobileLayerTechGroup.getId() %>LayerDiv">
										<div class="align-in-row width">
											<input type="checkbox" name="<%= mobileLayerId %>TechGroup" value="<%= mobileLayerTechGroup.getId() %>" id="<%= mobileLayerTechGroup.getId() %>"/>
											<span class="vAlignSub">&nbsp;<%= mobileLayerTechGroup.getName() %>&nbsp;</span>
										</div>
										<div class="align-in-row">
											<select class="input-medium" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>"
												onchange="getMobileTechVersions('<%= mobileLayerId %>', '<%= mobileLayerTechGroup.getId() %>', '<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_VERSION %>');">
												<option value="" selected disabled>Select Type</option>
												<%
													List<TechnologyInfo> mobileInfos = mobileLayerTechGroup.getTechInfos();	
													if (CollectionUtils.isNotEmpty(mobileInfos)) {
													    for (TechnologyInfo mobileInfo : mobileInfos) {
												%>
															<option value="<%= mobileInfo.getId() %>"><%= mobileInfo.getName() %></option>
												<%
														}
													}
												%>
											</select>
										</div>
										<div class="align-in-row">
											<select class="input-medium" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_VERSION %>" id="<%= mobileLayerTechGroup.getId() %>">
												<option value="" selected disabled>Select Version</option>
											</select>
										</div>
										<div class="align-in-row width">
											<input type="checkbox" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_PHONE %>" value="true"/>
											<span class="vAlignSub">&nbsp;Phone</span>
										</div>
										<div class="float-left">
											<input type="checkbox" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_TABLET %>" value="true"/>
											<span class="vAlignSub">&nbsp;Tablet</span>
										</div>
									</div>
									<div class="clear"></div>
									<%
									    	}
										}
									%>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- Mobile layer accordion ends -->
	</div>	
	</form>
	<!--  Form Ends -->
</div>

<!--  Submit and Cancel buttons Starts -->
<div class="actions">
	<input type="button" id="createProject" value="<s:text name="lbl.create"/>" class="btn btn-primary">
	<input type="button" id="cancel" value="<s:text name="label.cancel"/>" class="btn btn-primary">
</div>
<!--  Submit and Cancel buttons Ends -->

<script type="text/javascript">
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if(!isiPad()) {
		$(".appInfoScrollDiv").scrollbars();
	}

	$(document).ready(function() {
		enableScreen();//To enable the page after the full page is rendered
		
		//Will be triggered when the create project button is clicked
		$('#createProject').click(function() {
			var params = $('#formCustomers').serialize();
			validate('createProject', $('#formCreateProject'), $("#container"), params, '<s:text name='progress.txt.add.proj'/>');
		});
	});
	
	//To show the validation error messages
	function findError(data) {
		if (!isBlank(data.projectNameError)) {
			showError($("#projectNameControl"), $("#projectNameError"), data.projectNameError);
		} else {
			hideError($("#projectNameControl"), $("#projectNameError"));
		}
		
		if (!isBlank(data.projectCodeError)) {
			showError($("#projectCodeControl"), $("#projectCodeError"), data.projectCodeError);
		} else {
			hideError($("#projectCodeControl"), $("#projectCodeError"));
		}
		
		if (!isBlank(data.layerError)) {
			showError($("#layerControl"), $("#layerControl"), data.layerError);
		} else {
			hideError($("#layerControl"), $("#layerControl"));
		}
		
		if (!isBlank(data.appTechError)) {
			showErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"), data.appTechError);
		} else {
			hideErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"));
		}
		
		if (!isBlank(data.webTechError)) {
			showErrorInAccordion($("#webLayerControl"), $('#webLayerHeading'), $("#webLayerError"), data.webTechError);
		} else {
			hideErrorInAccordion($("#webLayerControl"), $('#webLayerHeading'), $("#webLayerError"));
		}
		
		if (!isBlank(data.mobTechError)) {
			showErrorInAccordion($("#mobileLayerControl"), $('#mobileLayerHeading'), $("#mobileLayerError"), data.mobTechError);
		} else {
			hideErrorInAccordion($("#mobileLayerControl"), $('#mobileLayerHeading'), $("#mobileLayerError"));
		}
	}
	
	var objName; //select box object name
	//To get the versions of the selected mobile technologies
	function getMobileTechVersions(layerId, techGroupId, toBeFilledCtrlName) {
		objName = toBeFilledCtrlName;
		var params = getBasicParams();
		params = params.concat("&layerId=");
		params = params.concat(layerId);
		params = params.concat("&techGroupId=");
		params = params.concat(techGroupId);
		loadContent("fetchMobileTechVersions", $('#formCreateProject'), '', params, true);
	}
	
	//To get the widgets of the selected web layer and load in the widget select box 
	function getWebLayerWidgets(layerId, widgetObjName) {
		objName = widgetObjName;
		var params = getBasicParams();
		params = params.concat("&layerId=");
		params = params.concat(layerId);
		loadContent("fetchWebLayerWidgets", $('#formCreateProject'), '', params, true);
	}
	
	var map = {};
	//Success event functions
	function successEvent(pageUrl, data) {
		//To fill the versions for the selected mobile technology
		if (pageUrl == "fetchMobileTechVersions") {
			fillVersions($("select[name='"+ objName +"']"), data.versions, "No Versions available");
		}
		
		//To fill the widgets for the selected web
		if (pageUrl == "fetchWebLayerWidgets") {
			for (i in data.widgets) {
				fillOptions($("select[name='"+ objName +"']"), data.widgets[i].id, data.widgets[i].name);
				map[data.widgets[i].id] = data.widgets[i].techVersions;
			}
		}
	}
	
	//To fill the versions of the selected widget
	function getWidgetVersions(obj, objectName) {
		var id = $(obj).val();
		fillVersions($("select[name='"+ objectName +"']"), map[id], "No Versions available");
	}
</script>