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
package com.photon.phresco.framework.param.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.*;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.FileListFilter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Utility;
import com.phresco.pom.util.PomProcessor;

public class IosTargetParameterImpl implements DynamicParameter {

    private static final String IPHONE_XCODE_WORKSPACE_PROJ_EXTN = "xcworkspace";
    private static final String IPHONE_XCODE_PROJ_EXTN = "xcodeproj";
    private static final String SCHEMES = "Schemes";
    private static final String XCODEBUILD_LIST_WORKSPACE_CMD = "xcodebuild -list ";
    private static final String IPHONE_XCODE_WORKSPACE = "-workspace ";
    private static final String IPHONE_XCODE_PROJECT = "-project ";
    private static final String XCODE_PROJECT_TARGETS = "Targets:";
    private static final String XCODE_WORKSPACE_TARGETS = "Schemes:";

    @Override
    public PossibleValues getValues(Map<String, Object> paramMap) throws IOException, ParserConfigurationException, 
             SAXException, ConfigurationException, PhrescoException {
        PossibleValues possibleValues = new PossibleValues();
        try {
        	String rootModulePath = "";
    		String subModuleName = "";
            ApplicationInfo applicationInfo = (ApplicationInfo) paramMap.get(KEY_APP_INFO);
            String rootModule = (String) paramMap.get(KEY_ROOT_MODULE);
//            String appDirName = applicationInfo.getAppDirName();
            
            if (StringUtils.isNotEmpty(rootModule)) {
    			rootModulePath = Utility.getProjectHome() + rootModule;
    			subModuleName = applicationInfo.getAppDirName();
    		} else {
    			rootModulePath = Utility.getProjectHome() + applicationInfo.getAppDirName();
    		}
        	File pomFileLocation = Utility.getPomFileLocation(rootModulePath, subModuleName);
//            StringBuilder builder = new StringBuilder(Utility.getProjectHome());
//            builder.append(appDirName);
//            builder.append(File.separatorChar);
            
//            File pomPath = new File(builder.toString(), Utility.getPomFileName(applicationInfo));
            PomProcessor pomProcessor = new PomProcessor(pomFileLocation);
            File sourceDir = null;
            String sourceDirectory = pomProcessor.getSourceDirectory();
            if (sourceDirectory.startsWith("${project.basedir}")) {
            	sourceDirectory = sourceDirectory.substring("${project.basedir}".length());
            	sourceDir = new File(pomFileLocation.getParent() + sourceDirectory);
            } else if (sourceDirectory.startsWith("/source")) {
            	sourceDir = new File(pomFileLocation.getParent() + File.separatorChar + sourceDirectory);
            } else {
            	String filePath = Utility.getProjectHome() + applicationInfo.getAppDirName() ;
            	String srcSplitDir = pomProcessor.getProperty("phresco.split.src.dir");
            	if(StringUtils.isNotEmpty(srcSplitDir)) {
            		filePath = filePath + File.separator + srcSplitDir;
            	}
            	sourceDir = new File(filePath, sourceDirectory);
            }
 
            FilenameFilter filter = new FileListFilter("", IPHONE_XCODE_WORKSPACE_PROJ_EXTN, IPHONE_XCODE_PROJ_EXTN);
            File[] listFiles = sourceDir.listFiles(filter);
            
            StringBuilder cmdBuilder = new StringBuilder();
            cmdBuilder.append(XCODEBUILD_LIST_WORKSPACE_CMD);
            if (listFiles[0].getName().contains(IPHONE_XCODE_WORKSPACE_PROJ_EXTN)) {
                cmdBuilder.append(IPHONE_XCODE_WORKSPACE);
            } else if (listFiles[0].getName().contains(IPHONE_XCODE_PROJ_EXTN)) {
                cmdBuilder.append(IPHONE_XCODE_PROJECT);
            }
            cmdBuilder.append(listFiles[0].getName().replace(" ", "\\ "));
            
            Commandline cl = new Commandline(cmdBuilder.toString());
            cl.setWorkingDirectory(sourceDir);
            Process p = cl.execute();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = null;
            boolean isTarget = false;
            
            while ((line = reader.readLine()) != null) {   
                if (line.trim().equals(XCODE_PROJECT_TARGETS) || line.trim().equals(XCODE_WORKSPACE_TARGETS)) { // getting only target
                	// For iphone projects both targets and Schemes will be displayed, If target is avilable, take target alone else schemes
                	if (CollectionUtils.isEmpty(possibleValues.getValue())) {
                		isTarget = true;
                	}
                } else if (line.trim().contains(":")) { // omitt all other configurations
                    isTarget = false;
                }
                    
                if (isTarget && StringUtils.isNotEmpty(line) && !line.trim().equals(XCODE_PROJECT_TARGETS) && !line.trim().equals(XCODE_WORKSPACE_TARGETS)) {
                    Value value = new Value();
                    value.setValue(line.trim());
                    value.setKey(line.trim());
                    possibleValues.getValue().add(value);
                }
            }
        
        } catch (Exception e) {
        	e.printStackTrace();
            throw new PhrescoException(e);
        }

        return possibleValues;
    }

}
