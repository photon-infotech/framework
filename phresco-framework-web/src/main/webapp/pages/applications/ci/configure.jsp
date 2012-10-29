<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@	page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.framework.model.CIJob" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<script src="js/select-envs.js"></script>

<%
	String showSettings = (String) request.getAttribute(FrameworkConstants.REQ_SHOW_SETTINGS);
	if(showSettings != null && Boolean.valueOf(showSettings)){
		showSettings = "checked";
	}
	
	String[] weekDays = {"", "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	String[] months = {"", "January","February","March","April","May","June","July","August","September","October","November","December"};

	CIJob existingJob =  (CIJob) request.getAttribute(FrameworkConstants.REQ_EXISTING_JOB);
	String disableStr = existingJob == null ? "" : "disabled";
    ApplicationInfo appInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APP_INFO);
    String appId  = appInfo.getId();
    
    String actionStr = "saveJob";
    actionStr = (existingJob == null || StringUtils.isEmpty(existingJob.getRepoType())) ? "saveJob" : "updateJob";
    existingJob = (existingJob == null || StringUtils.isEmpty(existingJob.getRepoType())) ? null : existingJob; // when we setup it ll have only jenkins url and port in that case we have to check svnUrl and make null
    List<String> existingClonedWorkspaces = (List<String>) request.getAttribute(FrameworkConstants.REQ_EXISTING_CLONNED_JOBS);
    List<String> existingJobsNames = (List<String>) request.getAttribute(FrameworkConstants.REQ_EXISTING_JOBS_NAMES);
%>
<!-- <div class="popup_Modal configurePopUp" id="ciDetails"> -->
    <form name="ciDetails" action="<%= actionStr %>" method="post" autocomplete="on" class="ci_form form-horizontal" id="configureForm">
<!--         <div class="modal-header"> -->
<%--             <h3><s:text name="label.ci"/></h3> --%>
<!--             <a class="close" href="#" id="close">&times;</a> -->
<!--         </div> -->
        
<!--         <div class="modal-body" id="ciConfigs" style="padding-top: 2px;"> -->
<!--         	<div class="clearfix"> -->
<!-- 				<label for="xlInput" class="xlInput popup-label"></label> -->
<!-- 				<div class="input"> -->
<%-- 					<span id="missingData" class="missingData" style="color:#690A0B;"></span> --%>
<!-- 				</div> -->
<!-- 			</div> -->
			
<!--         	<div class="clearfix"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.name"/></label> --%>
<!-- 				<div class="input"> -->
<%-- 					<input type="text" id="name" name="name" value="<%= existingJob == null ? "" : existingJob.getName()%>" <%= existingJob == null ? "" : "disabled" %> autofocus> --%>
<!-- 				</div> -->
<!-- 			</div> -->

<div class="theme_accordion_container clearfix" style="float: none;">
    <section class="accordion_panel_wid">
        <div class="accordion_panel_inner adv-settings-accoridan-inner">
            <section class="lft_menus_container adv-settings-width">
                <span class="siteaccordion" id="siteaccordion_active"><span><s:text name="label.build.basic.config"/></span></span>
                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett">
                    <div class="scrollpanel adv_setting_accordian_bottom">
                        <section class="scrollpanel_inner">
			
			
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<span class="red">* </span> <s:text name='lbl.name' />
				</label>
				<div class="controls">
					<input type="text" id="name" name="name" class="input-xlarge" value="<%= existingJob == null ? "" : existingJob.getName()%>" <%= existingJob == null ? "" : "disabled" %> autofocus>
				</div>
			</div>
			
<!-- 			<div class="clearfix"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><s:text name="label.svn.type"/></label> --%>
				
<!-- 				<div class="input"> -->
<!-- 					<div class="multipleFields ciTypeWidth"> -->
<%-- 						<div><input type="radio" name="svnType" value="svn"  <%= existingJob == null ? "checked" : "" %> />&nbsp; <s:text name="label.svn"/></div> --%>
<!-- 					</div> -->
<!-- 					<div class="multipleFields ciTypeWidth"> -->
<%-- 						<div><input type="radio" name="svnType" value="git" />&nbsp; <s:text name="label.git"/></div> --%>
<!-- 					</div> -->
<!-- 					clonned workspace radio button -->
<!-- 					<div class="multipleFields ciTypeCloneWidth"> -->
<%-- 						<div><input type="radio" name="svnType" value="clonedWorkspace" />&nbsp; <s:text name="label.cloned"/></div> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<s:text name='label.svn.type' />
				</label>
				<div class="controls" style="padding-top: 5px;">
<!-- 				<label class="radio"> -->
					<input type="radio" name="svnType" value="svn"  <%= existingJob == null ? "checked" : "" %> />&nbsp; <s:text name="label.svn"/>
<!-- 				</label> -->
					<input type="radio" name="svnType" value="git" />&nbsp; <s:text name="label.git"/>
					<input type="radio" name="svnType" value="clonedWorkspace" />&nbsp; <s:text name="label.cloned"/>
				</div>
			</div>
			
<!-- 			<div class="clearfix" id="svnUrlField"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.svn.url"/></label> --%>
<!-- 				<div class="input"> -->
<%-- 					<input type="text" id="svnurl" class="ciSvnUrlWidth" name="svnurl" value="<%= existingJob == null ? "" : existingJob.getSvnUrl()%>"> --%>
<!-- 				</div> -->
<!-- 			</div> -->
			
<!-- 			class="ciSvnUrlWidth" -->
			<div class="control-group" id="urlControl">
				<label class="control-label labelbold popupLbl">
					<span class="red">* </span> <s:text name='label.svn.url' />
				</label>
				<div class="controls">
					<input type="text" id="svnurl" name="svnurl" class="input-xlarge" value="<%= existingJob == null ? "" : existingJob.getSvnUrl()%>">
				</div>
			</div>

<!-- 			<div class="clearfix" id="divBranch"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.branch"/></label> --%>
<!-- 				<div class="input"> -->
<!-- 					<input type="text" id="branch" name="branch" maxlength="63" title="63 Characters only" value=""> -->
<!-- 				</div> -->
<!-- 			</div> -->
			
			<div class="control-group" id="branchControl">
				<label class="control-label labelbold popupLbl">
					<span class="red">* </span> <s:text name='label.branch' />
				</label>
				<div class="controls">
					<input type="text" id="branch" name="branch" class="input-xlarge" maxlength="63" title="63 Characters only" value="">
				</div>
			</div>
			
			<!-- Use clonned workspaces -->
<!-- 			<div class="clearfix" id="usedClonnedWorkspace"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><s:text name="label.parent.project"/></label> --%>
<!-- 				<div class="input"> -->
<%-- 					<select name="usedClonnedWorkspace" class="xlarge" > --%>
<%-- 						<%  --%>
<!-- // 							if (existingClonedWorkspaces != null) { -->
<!-- // 								for(String clonedWrkSpace : existingClonedWorkspaces) { -->
<!-- // 									if(existingJob != null && existingJob.getName().equals(clonedWrkSpace)) { -->
<!-- // 										continue; -->
<!-- // 									} -->
									
<%-- 						%> --%>
<%-- 							<option value="<%= clonedWrkSpace %>"><%= clonedWrkSpace %></option> --%>
<%-- 						<%  --%>
<!-- // 								} -->
<!-- // 							} -->
<%-- 						%> --%>
<%-- 					</select> --%>
<!-- 				</div> -->
<!--             </div> -->
            
            <div class="control-group" id="parentProjectControl">
				<label class="control-label labelbold popupLbl">
					<s:text name='label.parent.project' />
				</label>
				<div class="controls">
						<select name="usedClonnedWorkspace" class="input-xlarge " >
						<% 
							if (existingClonedWorkspaces != null) {
								for(String clonedWrkSpace : existingClonedWorkspaces) {
									if(existingJob != null && existingJob.getName().equals(clonedWrkSpace)) {
										continue;
									}
									
						%>
							<option value="<%= clonedWrkSpace %>"><%= clonedWrkSpace %></option>
						<% 
								}
							}
						%>
					</select>
				</div>
			</div>
						
<!-- 			<div class="clearfix" id="divUsername"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.username"/></label> --%>
<!-- 				<div class="input"> -->
<!-- 					<input type="text" id="username" name="username" maxlength="63" title="63 Characters only" value=""> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="control-group" id="usernameControl">
				<label class="control-label labelbold popupLbl">
					<span class="red">* </span> <s:text name='label.username' />
				</label>
				<div class="controls">
					<input type="text" id="username" name="username" class="input-xlarge" maxlength="63" title="63 Characters only" value="">
				</div>
			</div>
			
<!-- 			<div class="clearfix" id="divPassword"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.password"/></label> --%>
<!-- 				<div class="input"> -->
<!-- 					<input type="password" id="password" name="password" maxlength="63" title="63 Characters only" value=""> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="control-group" id="passwordControl">
				<label class="control-label labelbold popupLbl">
					<span class="red">* </span> <s:text name='label.password' />
				</label>
				<div class="controls">
					<input type="password" id="password" name="password" class="input-xlarge" maxlength="63" title="63 Characters only" value="">
				</div>
			</div>
			
<!-- 			<div class="clearfix"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><s:text name="label.sender.mail"/></label> --%>
<!-- 				<div class="input"> -->
<%-- 					<input type="text" name="senderEmailId" value="<%= existingJob == null ? "" : existingJob.getSenderEmailId()%>"> --%>
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<s:text name='label.sender.mail' />
				</label>
				<div class="controls">
					<input type="text" name="senderEmailId" class="input-xlarge" value="<%= existingJob == null ? "" : existingJob.getSenderEmailId()%>">
				</div>
			</div>
			
<!-- 			<div class="clearfix"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><s:text name="label.sender.pwd"/></label> --%>
<!-- 				<div class="input"> -->
<%-- 					<input type="password" name="senderEmailPassword" value="<%= existingJob == null ? "" : existingJob.getSenderEmailPassword()%>"> --%>
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<s:text name='label.sender.pwd' />
				</label>
				<div class="controls">
					<input type="password" name="senderEmailPassword" class="input-xlarge" value="<%= existingJob == null ? "" : existingJob.getSenderEmailPassword()%>">
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<s:text name='label.recipient.mail' />
				</label>
				<div class="controls">
					<div class="input">
						<div class="multipleFields emaillsFieldsWidth">
							<div><input id="successEmail" type="checkbox" name="emails"  value="success"/>&nbsp; <s:text name="label.when.success"/></div>
						</div>
						<div class="multipleFields">
							<div><input id="successEmailId" type="text" name="successEmailIds"  value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("successEmails")%>" disabled></div>
						</div>
					</div>
					
					<div class="input">
						<div class="multipleFields emaillsFieldsWidth">
							<div><input id="failureEmail" type="checkbox" name="emails" value="failure"/> &nbsp;<s:text name="label.when.fail"/></div>
						</div>
						<div class="multipleFields">
							<div><input id="failureEmailId" type="text" name="failureEmailIds" value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("failureEmails")%>" disabled></div>
						</div>
					</div>
				</div>
			</div>
			
<!-- 			<div class="clearfix"> -->
<%-- 				<label for="xlInput" class="xlInput popup-label"><s:text name="label.recipient.mail"/></label> --%>
				
<!-- 				<div class="input"> -->
<!-- 					<div class="multipleFields emaillsFieldsWidth"> -->
<%-- 						<div><input id="successEmail" type="checkbox" name="emails"  value="success"/>&nbsp; <s:text name="label.when.success"/></div> --%>
<!-- 					</div> -->
<!-- 					<div class="multipleFields"> -->
<%-- 						<div><input id="successEmailId" type="text" name="successEmailIds"  value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("successEmails")%>" disabled></div> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				
<!-- 				<div class="input"> -->
<!-- 					<div class="multipleFields emaillsFieldsWidth"> -->
<%-- 						<div><input id="failureEmail" type="checkbox" name="emails" value="failure"/> &nbsp;<s:text name="label.when.fail"/></div> --%>
<!-- 					</div> -->
<!-- 					<div class="multipleFields"> -->
<%-- 						<div><input id="failureEmailId" type="text" name="failureEmailIds" value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("failureEmails")%>" disabled></div> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				
<%-- 				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span>Build Triggers</label> --%>
<!-- 				<div class="input"> -->
<!-- 					<div class="multipleFields emaillsFieldsWidth"> -->
<!-- 						<div><input id="buildPeriodically" type="checkbox" name="triggers" value="TimerTrigger"/>&nbsp;Build periodically</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="input"> -->
<!-- 					<div class="multipleFields emaillsFieldsWidth"> -->
<!-- 						<div><input id="pollSCM" type="checkbox" name="triggers" value="SCMTrigger"/>&nbsp;Poll SCM</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<span class="red">* </span>Build Triggers
				</label>
				<div class="controls" style="padding-top: 5px;">
					<input id="buildPeriodically" type="checkbox" name="triggers" value="TimerTrigger"/>&nbsp;Build periodically
					<input id="pollSCM" type="checkbox" name="triggers" value="SCMTrigger"/>&nbsp;Poll SCM
				</div>
			</div>
				
			<%
				String schedule = existingJob == null ? "" : (String)existingJob.getScheduleType();
				
				String dailyEvery = "";
				String dailyHour = "";
				String dailyMinute = "";
				
				String weeklyWeek = "";
				String weeklyHour = "";
				String weeklyMinute = "";
				
				String monthlyDay = "";
				String monthlyMonth = "";
				String monthlyHour = "";
				String monthlyMinute = "";
				
				String CronExpre = existingJob == null ? "" : existingJob.getScheduleExpression();
				String[] cronSplit = CronExpre.split(" ");
				if(schedule.equals("Daily")) {
					if(CronExpre.contains("/")) {
						dailyEvery = "checked";
					}
					
		            if (cronSplit[0].contains("/")) {
		            	dailyMinute = cronSplit[0].substring(2) + "";
		            } else {
		            	dailyMinute = cronSplit[0];
		            }
		            
					if (cronSplit[1].contains("/")) {
						dailyHour = cronSplit[1].substring(2) + "";
		            } else {
		            	dailyHour = cronSplit[1];
		            }
					
				} else if(schedule.equals("Weekly")) {
					weeklyWeek = cronSplit[4];
					weeklyHour = cronSplit[1];
					weeklyMinute = cronSplit[0];
					
				} else if(schedule.equals("Monthly")) {
					monthlyDay = cronSplit[2];
					monthlyMonth = cronSplit[3];
					monthlyHour = cronSplit[1];
					monthlyMinute = cronSplit[0];
					
				}
			%>
			
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<s:text name='label.schedule' />
				</label>
				<div class="controls" style="padding-top: 5px;">
					<input id="scheduleDaily" type="radio" name="schedule" value="Daily"  onChange="javascript:show('Daily');" <%= ((schedule.equals("Daily")) || (schedule.equals(""))) ? "checked" : "" %> />&nbsp; <s:text name="label.daily"/>
					<input id="scheduleDaily" type="radio" name="schedule" value="Weekly" onChange="javascript:show('Weekly');" <%= schedule.equals("Weekly") ? "checked" : "" %> />&nbsp; <s:text name="label.weekly"/>
					<input id="scheduleDaily" type="radio" name="schedule" value="Monthly" onChange="javascript:show('Monthly');" <%= schedule.equals("Monthly") ? "checked" : "" %>/>&nbsp; <s:text name="label.monthly"/>
				</div>
			</div>
			
			
		<div class="clearfix">
					<div  id='Daily' style="text-align: center; margin-bottom: 5px;"> <!-- class="schedulerWidth" -->
					<div><s:text name="label.every"/> &nbsp;&nbsp;&nbsp;&nbsp;	<s:text name="label.hours"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:text name="label.minutes"/></div>
					<div class="dailyInnerDiv">
						<s:text name="label.At"/> &nbsp;&nbsp;
						<input type="checkbox" id="daily_every" name="daily_every" onChange="javascript:show('Daily');" <%= dailyEvery %>>
						&nbsp;&nbsp;
	                    <select id="daily_hour" name="daily_hour" onChange="javascript:show('Daily');" class="schedulerSelectWidth">
	                         <option value="*">*</option>
	                         <% 
	                         	for (int i = 1; i < 24; i++) {
	                         		String selectedStr = "";
	                         		if(!StringUtils.isEmpty(dailyHour) && dailyHour.equals("" + i)) {
	                         			selectedStr = "selected";
	                         		}
	                         %>
	                         	<option value="<%= i %>" <%= selectedStr %>> <%= i %> </option>
	                         <% } %>
	                   	</select>
	                     &nbsp;	&nbsp;
					    <select id="daily_minute" name="daily_minute" onChange="javascript:show('Daily');" class="schedulerSelectWidth">
							<option value="*">*</option>            
						<% 
							for (int i = 1; i < 60; i++) { 
                         		String selectedStr = "";
                         		if(!StringUtils.isEmpty(dailyMinute) && dailyMinute.equals("" + i)) {
                         			selectedStr = "selected";
                         		}
						%>
							<option value="<%= i %>" <%= selectedStr %>><%= i %></option>
						<% } %>
						</select>
                    </div>
				</div>
				<div id='Weekly' style="text-align: center; margin-bottom: 5px;"> <!-- class="schedulerWidth" -->
   					<select id="weekly_week" name="weekly_week" multiple onChange="javascript:show('Weekly');" class="schedulerDay alignVertical">
				   		<%
				   			String defaultSelectedStr = "";
				   			if(StringUtils.isEmpty(weeklyWeek)) {
				             	defaultSelectedStr = "selected";
				           	}
				   		%>
				   			<option value="*" <%= defaultSelectedStr %>>*</option>
				   		<%
				   			for(int i = 1; i < weekDays.length; i++){
				   				String selectedStr = "";
				   				if(!StringUtils.isEmpty(weeklyWeek) && weeklyWeek.equals("" + i)) {
                     				selectedStr = "selected";
                     			}
						%>
							<option value="<%= i %>" <%= selectedStr %>><%= weekDays[i] %></option>
                        <%  
                        	}
				   		%>
                    </select>
                    
                    &nbsp; <s:text name="label.smal.at"/> &nbsp;
                    <select id="weekly_hours" name="weekly_hours" onChange="javascript:show('Weekly');" class="schedulerSelectWidth alignVertical">
                        <option value="*">*</option>
	                    <% 
	                    	for (int i = 1; i < 24; i++) {
	                    		String selectedStr = "";
	                     		if(!StringUtils.isEmpty(weeklyHour) && weeklyHour.equals("" + i)) {
	                     			selectedStr = "selected";
	                     		}
	                    %>
	                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
	                    <% } %>
                    </select>&nbsp;<s:text name="label.hour"/>&nbsp;
                    
                  	<select id="weekly_minute" name="weekly_minute" onChange="javascript:show('Weekly');" class="schedulerSelectWidth alignVertical">
	                  <option value="*">*</option>            
	                  <% 
	                  		for (int i = 1; i < 60; i++) { 
	                    		String selectedStr = "";
	                     		if(!StringUtils.isEmpty(weeklyMinute) && weeklyMinute.equals("" + i)) {
	                     			selectedStr = "selected";
	                     		}
	                  %>
	                      <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
	                  <% } %>
                   	</select>&nbsp;<s:text name="label.minute"/>
                                                
				</div>
				<div id='Monthly' style="text-align: center; margin-bottom: 5px;"> <!-- class="schedulerWidth" -->
                   <s:text name="label.every"/>
                   <select id="monthly_day" name="monthly_day" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
                           <option value="*">*</option>
                       <% 
                       		for (int i = 1; i <= 31; i++) { 
	                    		String selectedStr = "";
	                     		if(!StringUtils.isEmpty(monthlyDay) && monthlyDay.equals("" + i)) {
	                     			selectedStr = "selected";
	                     		}
                       	%>
                           <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
                       <% } %>
                   </select>&nbsp;<s:text name="label.of"/>&nbsp;
                   
                  	<select id="monthly_month" name="monthly_month" multiple onChange="javascript:show('Monthly');" class="schedulerDay alignVertical">
						<%
				   			defaultSelectedStr = "";
				   			if(StringUtils.isEmpty(monthlyMonth)) {
				             	defaultSelectedStr = "selected";
				           	}
				   		%>
				   			<option value="*" <%= defaultSelectedStr %>>*</option>
				   		<%
				   			for(int i = 1; i < months.length; i++){
				   				String selectedStr = "";
				   				if(!StringUtils.isEmpty(monthlyMonth) && monthlyMonth.equals("" + i)) {
                     				selectedStr = "selected";
                     			}
						%>
							<option value="<%= i %>" <%= selectedStr %>><%= months[i] %></option>
                        <%  
                        	}
				   		%>
                    </select>
                    
					&nbsp; <s:text name="label.smal.at"/> &nbsp;
                    <select id="monthly_hour" name="monthly_hour" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
                        <option value="*">*</option>
                    <% 
                    	for (int i = 1; i < 24; i++) { 
                    		String selectedStr = "";
                     		if(!StringUtils.isEmpty(monthlyHour) && monthlyHour.equals("" + i)) {
                     			selectedStr = "selected";
                     		}
                    %>
                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
                    <% } %>
                    </select>&nbsp;<s:text name="label.hour"/>
                    &nbsp;
                    <select id="monthly_minute" name="monthly_minute" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
                        <option value="*">*</option>
                    <% 
                    	for (int i = 1; i < 60; i++) { 
                    		String selectedStr = "";
                     		if(!StringUtils.isEmpty(monthlyMinute) && monthlyMinute.equals("" + i)) {
                     			selectedStr = "selected";
                     		}
                    %>
                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
                    <% } %>
                    </select> &nbsp; <s:text name="label.minute"/>
                                                
				</div>
<!-- 				</div> -->
			</div>
			
			<div class="control-group">
				<label class="control-label labelbold popupLbl">
					<s:text name='label.cron.expression' />
				</label>
				<div class="controls" id="cronValidation">
				</div>
			</div>
			
													<!-- Down stream projects specification -->
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<s:text name='label.downstream.projects' />
											</label>
											<div class="controls">
												<select id="downstreamProject" name="downstreamProject" class="input-xlarge">
													<option value="">-</option>
												</select>
											</div>
										</div>
										
										<!-- clone this workspace -->
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<s:text name='label.clone.workspace' />
											</label>
											<div class="controls" style="padding-top: 5px;">
												<input type="radio" name="cloneWorkspace" value="true" />&nbsp; <s:text name="label.yes"/>
												<input type="radio" name="cloneWorkspace" value="false" checked/>&nbsp; <s:text name="label.no"/>
											</div>
										</div>
										
			            </section>
                    </div>
                </div>
            </section>  
        </div>
    </section>
</div>
<!--         </div> -->
		
<!-- 		<div class="modal-body" id="configs"> -->

			<!-- CI basic settings starts -->
			<div class="theme_accordion_container clearfix" style="float: none;">
			    <section class="accordion_panel_wid">
			        <div class="accordion_panel_inner adv-settings-accoridan-inner">
			            <section class="lft_menus_container adv-settings-width">
			                <span class="siteaccordion" id="siteaccordion_active"><span><s:text name="label.build.config"/></span></span>
			                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett">
			                    <div class="scrollpanel adv_setting_accordian_bottom">
			                        <section class="scrollpanel_inner">
			                        <!--  Ci job configuration starts -->
										<!-- Ci operation -->
										
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<s:text name='label.ci.operation' />
											</label>
											<div class="controls">
												<select id="operation" name="operation" class="input-xlarge">
											        <option value="build">Build</option>
											        	<option value="deploy">Deploy</option>
											        <option value="functionalTest">Functional Test</option>
												</select>
											</div>
										</div>
<!-- 										loading dynamic build, deploy and functional test popup pages -->
										<div class="clearfix" id="dynamicConfigLoad">
										</div>
										
										<!-- Show Settings -->
<!-- 										<div class="control-group"> -->
<!-- 											<label class="control-label labelbold popupLbl"> -->
<%-- 												<s:text name='label.show.setting' /> --%>
<!-- 											</label> -->
<!-- 											<div class="controls" style="padding-top: 5px;"> -->
<%-- 												<input type="checkbox" id="showSettings" name="showSettings" value="showsettings" <%= showSettings %>> &nbsp; <s:text name="label.show.setting"/> --%>
<%-- 												<input type="checkbox" id="proguard" name="proguard" value="false">&nbsp;<s:text name="label.progurad"/> --%>
<%-- 												<input type="checkbox" id="signing" name="signing" value="false">&nbsp;<s:text name="label.signing"/> --%>
<!-- 											</div> -->
<!-- 										</div> -->
										
									<!--  Ci job configuration ends -->
			                        </section>
			                    </div>
			                </div>
			            </section>  
			        </div>
			    </section>
			</div>
		<!-- CI basic settings ends -->
		
		<!-- build release plugin changes starts -->
			<div class="theme_accordion_container clearfix" style="float: none;">
			    <section class="accordion_panel_wid">
			        <div class="accordion_panel_inner adv-settings-accoridan-inner">
			            <section class="lft_menus_container adv-settings-width">
			                <span class="siteaccordion" id="siteaccordion_active"><span><s:text name="label.build.uploader.config"/></span></span>
			                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett">
			                    <div class="scrollpanel adv_setting_accordian_bottom">
			                        <section class="scrollpanel_inner">
			
<!-- 										<div class="clearfix"> -->
<%-- 											<label for="xlInput" class="xlInput popup-label"><s:text name="label.enable.build.release"/></label> --%>
											
<!-- 											<div class="input"> -->
<!-- 												<div class="multipleFields quartsRadioWidth"> -->
<%-- 													<div><input type="radio" name="enableBuildRelease" value="true" />&nbsp; <s:text name="label.yes"/></div> --%>
<!-- 												</div> -->
<!-- 												<div class="multipleFields quartsRadioWidth"> -->
<%-- 													<div><input type="radio" name="enableBuildRelease" value="false" checked />&nbsp; <s:text name="label.no"/></div> --%>
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<s:text name='label.enable.build.release' />
											</label>
											<div class="controls" style="padding-top: 5px;">
												<input type="radio" name="enableBuildRelease" value="true" />&nbsp; <s:text name="label.yes"/>
												<input type="radio" name="enableBuildRelease" value="false" checked />&nbsp; <s:text name="label.no"/>
											</div>
										</div>
										
										<fieldset class="popup-fieldset fieldsetBottom perFieldSet" style="text-align: left;" id="collabNetInfo">
											<legend class="fieldSetLegend"><s:text name="label.build.release"/></legend>
											
											<div id="CollabNetConfig">
											
<!-- 												<div class="clearfix"> -->
<%-- 													<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.build.release.url"/></label> --%>
<!-- 													<div class="input"> -->
<%-- 														<input type="text" id="collabNetURL" class="ciBuildReleaseURL" name="collabNetURL" value="<%= existingJob == null ? "" : existingJob.getCollabNetURL() %>"> --%>
<!-- 													</div> -->
<!-- 												</div> -->
												<div class="control-group">
													<label class="control-label labelbold popupLbl">
														<span class="red">* </span>  <s:text name='label.build.release.url' />
													</label>
													<div class="controls">
														<input type="text" id="collabNetURL" class="input-xlarge" name="collabNetURL" value="<%= existingJob == null ? "" : existingJob.getCollabNetURL() %>">
													</div>
												</div>
									
<!-- 												<div class="clearfix"> -->
<%-- 													<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.build.release.username"/></label> --%>
<!-- 													<div class="input"> -->
<%-- 														<input type="text" id="collabNetusername" name="collabNetusername" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetusername() %>"> --%>
<!-- 													</div> -->
<!-- 												</div> -->
												<div class="control-group">
													<label class="control-label labelbold popupLbl">
														<span class="red">* </span>  <s:text name='label.build.release.username' />
													</label>
													<div class="controls">
														<input type="text" id="collabNetusername" name="collabNetusername" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetusername() %>">
													</div>
												</div>
												
<!-- 												<div class="clearfix"> -->
<%-- 													<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.build.release.password"/></label> --%>
<!-- 													<div class="input"> -->
<%-- 														<input type="password" id="collabNetpassword" name="collabNetpassword" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetpassword() %>"> --%>
<!-- 													</div> -->
<!-- 												</div> -->
												<div class="control-group">
													<label class="control-label labelbold popupLbl">
														<span class="red">* </span>  <s:text name='label.build.release.password' />
													</label>
													<div class="controls">
														<input type="password" id="collabNetpassword" name="collabNetpassword" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetpassword() %>">
													</div>
												</div>
												
<!-- 												<div class="clearfix"> -->
<%-- 													<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.build.release.project"/></label> --%>
<!-- 													<div class="input"> -->
<%-- 														<input type="text" id="collabNetProject" name="collabNetProject" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetProject() %>"> --%>
<!-- 													</div> -->
<!-- 												</div> -->
												
												<div class="control-group">
													<label class="control-label labelbold popupLbl">
														<span class="red">* </span>  <s:text name='label.build.release.project' />
													</label>
													<div class="controls">
														<input type="text" id="collabNetProject" name="collabNetProject" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetProject() %>">
													</div>
												</div>
												
												
<!-- 												<div class="clearfix"> -->
<%-- 													<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.build.release.package"/></label> --%>
<!-- 													<div class="input"> -->
<%-- 														<input type="text" id="collabNetPackage" name="collabNetPackage" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetPackage() %>"> --%>
<!-- 													</div> -->
<!-- 												</div> -->
												<div class="control-group">
													<label class="control-label labelbold popupLbl">
														<span class="red">* </span>  <s:text name='label.build.release.package' />
													</label>
													<div class="controls">
														<input type="text" id="collabNetPackage" name="collabNetPackage" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetPackage() %>">
													</div>
												</div>
												
<!-- 												<div class="clearfix"> -->
<%-- 													<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.build.release.release.name"/></label> --%>
<!-- 													<div class="input"> -->
<%-- 														<input type="text" id="collabNetRelease" name="collabNetRelease" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetRelease() %>"> --%>
<!-- 													</div> -->
<!-- 												</div> -->
												
												<div class="control-group">
													<label class="control-label labelbold popupLbl">
														<span class="red">* </span> <s:text name='label.build.release.release.name' />
													</label>
													<div class="controls">
														<input type="text" id="collabNetRelease" name="collabNetRelease" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetRelease() %>">
													</div>
												</div>
												
<!-- 												<div class="clearfix"> -->
<%-- 													<label for="xlInput" class="xlInput popup-label"><s:text name="label.build.release.overwrite"/></label> --%>
													
<!-- 													<div class="input"> -->
<!-- 														<div class="multipleFields quartsRadioWidth"> -->
<%-- 															<div><input type="radio" name="overwriteFiles" value="true" checked />&nbsp; <s:text name="label.yes"/></div> --%>
<!-- 														</div> -->
<!-- 														<div class="multipleFields quartsRadioWidth"> -->
<%-- 															<div><input type="radio" name="overwriteFiles" value="false" />&nbsp; <s:text name="label.no"/></div> --%>
<!-- 														</div> -->
<!-- 													</div> -->
<!-- 												</div> -->
												<div class="control-group">
													<label class="control-label labelbold popupLbl">
														<s:text name='label.build.release.overwrite' />
													</label>
													<div class="controls" style="padding-top: 5px;">
														<input type="radio" name="overwriteFiles" value="true" checked />&nbsp; <s:text name="label.yes"/>
														<input type="radio" name="overwriteFiles" value="false" />&nbsp; <s:text name="label.no"/>
													</div>
												</div>
										
											</div>
											
										</fieldset>
			                        </section>
			                    </div>
			                </div>
			            </section>  
			        </div>
			    </section>
			</div>
    	<!-- build release plugin changes ends -->
    	
<!-- 		</div> -->
    
<!--         <div class="modal-footer"> -->
<!--              <div style="float: left;" id="loadingDiv"> -->
<!-- 					<div id="errMsg"></div> -->
<!-- 				    <img src="themes/photon/images/loading_red.gif" class="popupLoadingIcon" style="display: none;">  -->
<!-- 	    	 </div>  -->
	    	<input type="hidden" name="oldJobName" value="<%= existingJob == null ? "" : existingJob.getName()%>" >
<%--             <input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel"> --%>
<%--             <input type="button" class="btn primary" value="<s:text name="label.save"/>" id="actionBtn"> --%>
<%--             <input type="button" class="btn primary" value="<s:text name="label.next"/>" id="nextBtn"> --%>
<%--             <input type="button" class="btn primary" value="<s:text name="label.previous"/>" id="preBtn"> --%>
<!--         </div> -->
    </form>
<!-- </div> -->

<script type="text/javascript">
	var selectedSchedule = $("input:radio[name=schedule]:checked").val();
	loadSchedule(selectedSchedule);
	
	$(document).ready(function() {
		//accordian
// 		accordion();
		
		$('.siteaccordion').unbind('click');
		accordion();
		
		credentialsDisp();
		$("#name").focus();
		
		$("#successEmail").click(function() {
			if($(this).is(":checked")) {
				$("#successEmailId").attr("disabled", false);
			} else {
				$("#successEmailId").attr("disabled", true);
				$("#successEmailId").val('');
	   		}
		});
		
		$("#failureEmail").click(function() {
			if($(this).is(":checked")) {
				$("#failureEmailId").attr("disabled", false);
			} else {
				$("#failureEmailId").attr("disabled", true);
				$("#failureEmailId").val('');
	   		}
		});
		
		$("input:radio[name=schedule]").click(function() {
		    var selectedSchedule = $(this).val();
		    loadSchedule(selectedSchedule);
		});
		
		$("input:radio[name=svnType]").click(function() {
			credentialsDisp();
		});
		
		show(selectedSchedule);
		<% 
			if(existingJob != null) {
				for(String trigger : existingJob.getTriggers()) {
		%>
					$("input[value='<%= trigger%>']").prop("checked", true);
		<%
				}
		%>
			//based on svn type radio button have to be selected
		<%
				String repoType = "svn";
				if (StringUtils.isNotEmpty(existingJob.getRepoType())) {
					repoType = existingJob.getRepoType();
				}
		%>
				$("input:radio[name=svnType][value=<%= existingJob.getRepoType() %>]").prop("checked", true);
		
				$("input:text[name=branch]").val("<%= existingJob.getBranch() %>");
				$("input:text[name=username]").val("<%= existingJob.getUserName() %>");
				$("input[name=password]").val("<%= existingJob.getPassword() %>");
				
				// when the job is not null, have to make selection in radio buttons of collabnet plugin
				$('input:radio[name=enableBuildRelease]').filter("[value='"+<%= existingJob.isEnableBuildRelease() %>+"']").attr("checked", true);
				$('input:radio[name=overwriteFiles]').filter("[value='"+<%= existingJob.isCollabNetoverWriteFiles() %>+"']").attr("checked", true);
				
				$("#usedClonnedWorkspace option[value='<%= existingJob.getUsedClonnedWorkspace() %>']").attr('selected', 'selected');
				$("#downstreamProject option[value='<%= existingJob.getDownStreamProject() %>']").attr('selected', 'selected');
				$("#operation option[value='<%= existingJob.getOperation() %>']").attr('selected', 'selected');
				$("#environments option[value='<%= existingJob.getEnvironment() %>']").attr('selected', 'selected');
				//clone the workspace
				$("input:radio[name=cloneWorkspace][value=<%= existingJob.isCloneWorkspace() %>]").prop("checked", true);
				
				
				$("input:radio[name=deployTo][value=<%= existingJob.getDeployTo() %>]").prop("checked", true);
		<%
			}
		%>
	    
		credentialsDisp();
		
		$('input:radio[name=enableBuildRelease]').click(function() {
			enableDisableCollabNet();
			ciConfigureError('errMsg', "");
		});
		// while editing a job , based on value show hide it (CollabNet build release)
		enableDisableCollabNet();
		
		// Automation implementation - ci config - based on technology show hide information
		$('#operation').change(function() {
			showConfigBasedOnTech();
		});
		showConfigBasedOnTech();
		
	});
	
	function showConfigBasedOnTech() {
		// android , iphone and other technology
		//hide java stanalone info by default
// 		$('#javaStandaloneConfig').hide();
		if($('#operation').val() == "build") {
			loadContent('generateBuild','', $('#dynamicConfigLoad'), getBasicParams(), false);
		} else if($('#operation').val() == "deploy") {
			loadContent('deploy', '', $('#dynamicConfigLoad'), getBasicParams(), false);
		} else if($('#operation').val() == "functionalTest") {
			loadContent('generateFunctionalTest', '', $('#dynamicConfigLoad'), getBasicParams(), false);
		}	
	}
	
	function popupOnOk(obj) {
 		var okUrl = $(obj).attr("id");
		// do the validation for collabNet info only if the user selects git radio button
 		var validation = configureJobValidation();
		// when validation is true
 		if (validation && $("input:radio[name=enableBuildRelease][value='true']").is(':checked')) {
 			if(collabNetValidation()){
 				configureJob(okUrl);
 			}
 		} else if (validation) {
			configureJob(okUrl);
		}
	}
	
	// after validation success, show loading icon and creates job
	function configureJob(url) {
		isCiRefresh = true;
// 		getCurrentCSS();
// 		$('.popupLoadingIcon').css("display","block");
// 		var url = $("#configureForm").attr("action");
		$('#configureForm :input').attr('disabled', false);
		loadContent(url, $('#configureForm, #generateBuildForm'), $('#subcontainer'), getBasicParams(), false);
	}
	
	function enableDisableCollabNet() {
		if($('input:radio[name=enableBuildRelease]:checked').val() == "true") {
			$('#collabNetInfo').show();
			$('input:text[name=collabNetURL]').focus();
		} else {
			$('#collabNetInfo').hide();
			//when user selects no resets all the value
			$('input:text[name=collabNetURL], input:text[name=collabNetusername], input:password[name=collabNetpassword]').val('');
			$('input:text[name=collabNetProject], input:text[name=collabNetPackage], input:text[name=collabNetRelease]').val('');
		}
		
	}
	
	// when the configure popup is clicked on save button, it should validate mandatory fields before submitting form
	function configureJobValidation() {
		var name= $("#name").val();
		var svnurl= $("#svnurl").val();
		var username= $("#username").val();
		var password= $("#password").val();
		if(isBlank(name)) {
			$("#errMsg").html("Enter the Name");
			$("#name").focus();
			$("#name").val("");
			return false;
		} 
		
		if($("input:radio[name=svnType][value='svn']").is(':checked')) {
			if(isValidUrl(svnurl)){
				$("#errMsg").html("Enter the URL");
				$("#svnurl").focus();
				$("#svnurl").val("");
				return false;
			}
			
			if(isBlank($.trim($("input[name= username]").val()))){
				$("#errMsg").html("Enter UserName");
				$("#username").focus();
				$("#username").val("");
				return false;
			}
			if(isBlank(password)) {
				$("#errMsg").html("Enter Password");
				$("#password").focus();
				return false;
			}
		} 
		
		if($("input:radio[name=svnType][value='git']").is(':checked')) {
			if(isValidUrl(svnurl)) {
				$("#errMsg").html("Enter the URL");
				$("#svnurl").focus();
				$("#svnurl").val("");
				return false;
			}
			
			if(isBlank($.trim($("input[name=branch]").val()))){
				$("#errMsg").html("Enter Branch Name");
				$("#branch").focus();
				$("#branch").val("");
				return false;
			}
		} 
		
		if($("input:radio[name=svnType][value='clonedWorkspace']").is(':checked')) {
			if(!$("#usedClonnedWorkspace option:selected").length){
				$("#errMsg").html("There is no parent project to configure");
				return false;
			}
		}
		
		ciConfigureError('errMsg', "");
		return true;
	}
	
	function collabNetValidation() {
		if(isValidUrl($('input:text[name=collabNetURL]').val())) {
			ciConfigureError('errMsg', "URL is missing");
			$('input:text[name=collabNetURL]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetusername]').val())) {
			ciConfigureError('errMsg', "Username is missing");
			$('input:text[name=collabNetusername]').focus();
			return false;
		} else if (isBlank($('input:password[name=collabNetpassword]').val())) {
			ciConfigureError('errMsg', "Password is missing");
			$('input:password[name=collabNetpassword]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetProject]').val())) {
			ciConfigureError('errMsg', "Project is missing");
			$('input:text[name=collabNetProject]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetPackage]').val())) {
			ciConfigureError('errMsg', "Package is missing");
			$('input:text[name=collabNetPackage]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetRelease]').val())) {
			ciConfigureError('errMsg', "Release is missing");
			$('input:text[name=collabNetRelease]').focus();
			return false;
		} else {
			ciConfigureError('errMsg', "");
			return true;
	 	}
	}
	
	function credentialsDisp() {
		if($("input:radio[name=svnType][value='svn']").is(':checked')) {
			$('#usernameControl, #passwordControl, #urlControl').show();
			$('#branchControl, #parentProjectControl').hide();
		} else if($("input:radio[name=svnType][value='git']").is(':checked')) {
			$('#usernameControl, #passwordControl, #parentProjectControl').hide();
			$('#branchControl, #urlControl').show();
		}  else if($("input:radio[name=svnType][value='clonedWorkspace']").is(':checked')) {
			$('#usernameControl, #passwordControl, #branchControl, #urlControl').hide();
			$('#parentProjectControl').show();
		}
	}
	
	function ciConfigureError(id, errMsg){
		$("#" + id ).empty();
		$("#" + id ).append(errMsg);
	}
    
	function loadSchedule(selectedSchedule) {
		hideAllSchedule();
		$("#" + selectedSchedule).show();	
	}
	
	function hideAllSchedule() {
		$("#Daily").hide();
		$("#Weekly").hide();
		$("#Monthly").hide();
	}
	
    function show(ids) {
        var buttonObj = window.document.getElementById("enableButton");

        if(ids == "Daily") {
            var dailyEveryObj = window.document.getElementById("daily_every");
            var dailyHourObj = window.document.getElementById("daily_hour");
            var hours = dailyHourObj.options[dailyHourObj.selectedIndex].value;
            var dailyMinuteObj = window.document.getElementById("daily_minute");
            var minutes = dailyMinuteObj.options[dailyMinuteObj.selectedIndex].value;
    		var params = "cronBy=";
			params = params.concat("Daily");
			params = params.concat("&hours=");
			params = params.concat(hours);
			params = params.concat("&minutes=");
			params = params.concat(minutes);
			params = params.concat("&every=");
			params = params.concat(dailyEveryObj.checked);
			cronValidationLoad(params);
        } else if(ids == "Weekly") {

            var weeklyHourObj = window.document.getElementById("weekly_hours");
            var hours = weeklyHourObj.options[weeklyHourObj.selectedIndex].value;

            var weeklyMinuteObj = window.document.getElementById("weekly_minute");
            var minutes = weeklyMinuteObj.options[weeklyMinuteObj.selectedIndex].value;

            var weekObj = window.document.getElementById("weekly_week");
            var week;
            var count = 0;
            
            if (weekObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < weekObj.options.length; i++){

                    if(weekObj.options[i].selected){
                        if (count == 0) {
                            week = weekObj.options[i].value;
                        } else {
                           week += "," + weekObj.options[i].value;
                        }

                        count++;
                    }
                }
	        	var params = "cronBy=";
				params = params.concat("Weekly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&week=");
				params = params.concat(week);
				cronValidationLoad(params);
        		
            }

      } else if(ids == "Monthly") {

            var monthlyDayObj = window.document.getElementById("monthly_day");
            var day = monthlyDayObj.options[monthlyDayObj.selectedIndex].value;

            var monthlyHourObj = window.document.getElementById("monthly_hour");
            var hours = monthlyHourObj.options[monthlyHourObj.selectedIndex].value;

            var monthlyMinuteObj = window.document.getElementById("monthly_minute");
            var minutes = monthlyMinuteObj.options[monthlyMinuteObj.selectedIndex].value;

            var monthObj = window.document.getElementById("monthly_month");
            var month;
            var count = 0;
            if (monthObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < monthObj.options.length; i++){

                    if(monthObj.options[i].selected){
                        if (count == 0) {
                            month = monthObj.options[i].value;
                        } else {
                           month += "," + monthObj.options[i].value;
                        }

                        count++;
                    }
                }
        		var params = "cronBy=";
				params = params.concat("Monthly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&month=");
				params = params.concat(month);
				params = params.concat("&day=");
				params = params.concat(day);
				cronValidationLoad(params);
            }

        }
    }
    
    function cronValidationLoad(additionalParams) {
		var params = getBasicParams();
		params = params.concat("&");
		params = params.concat(additionalParams);
    	loadContent('cronValidation','', $('#cronValidation'), params, false);
    	
    }
</script>