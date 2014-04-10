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
package com.photon.phresco.framework.impl.update;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.TechnologyTypes;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class ProfileUpdater implements FrameworkConstants {
	
	private static Document doc = null;
	
	public void updateSonarProfile(File pomPath, String techId) throws PhrescoException {
		try {
			PomProcessor pomProcessor = new PomProcessor(pomPath);
			boolean updateJavaProfile = updateJavaProfile(pomProcessor, pomPath, JAVA);
			boolean updateWebProfile = updateWebProfile(pomProcessor, pomPath, WEB);
			boolean updateJsProfile = updateJsProfile(pomProcessor, pomPath, JS, techId);
			if (updateJavaProfile || updateWebProfile || updateJsProfile) {
				pomProcessor.save();
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}
	
	private boolean updateJavaProfile(PomProcessor pomProcessor, File pomPath, String profileId) throws PhrescoException {
		return addProfile(pomProcessor, profileId, JAVA_PATH, new ArrayList<Element>(5), Boolean.TRUE);
	}
	
	private boolean updateWebProfile(PomProcessor pomProcessor, File pomPath, String profileId) throws PhrescoException {
		Element dynamicAnalysisElement = createElement(SONAR_DYNAMIC_ANALYSIS_PROFILE, FALSE);
		List<Element> elements = new ArrayList<Element>();
		elements.add(dynamicAnalysisElement);
		return addProfile(pomProcessor, profileId, WEBAPP_PATH, elements, Boolean.FALSE);
	}
	
	private boolean updateJsProfile(PomProcessor pomProcessor, File pomPath, String profileId, String techId) throws PhrescoException {
		List<Element> elements = new ArrayList<Element>();
		Element sonarExclusionElement = createElement(Constants.SONAR_EXCLUSION, LIB);
		elements.add(sonarExclusionElement);
		boolean isJqueryWidget =  techId.equals(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET) || techId.equals(TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET);
		if (isJqueryWidget) {
			return addProfile(pomProcessor, profileId, JS_PATH, elements, Boolean.FALSE);
		} else {
			return addProfile(pomProcessor, profileId, WEBAPP_PATH, elements, Boolean.FALSE);
		}
	}
	
	private boolean addProfile(PomProcessor pomProcessor, String profileId, String srcPath, 
			List<Element> elements, boolean activation) throws PhrescoException {
		boolean addProfile = false;
		elements.add(createElement(SONAR_LANGUAGE_PROFILE, profileId));
        elements.add(createElement(SONAR_BRANCH, profileId));
        elements.add(createElement(PHRESCO_SOURCE_DIRECTORY, srcPath));
        //TODO:Need to handle
//			addProfile = pomProcessor.addProfile(profileId, elements, activation);
		return addProfile;
	}

	private Element createElement(String elementName, String textContent) throws PhrescoException {
		Document doc = getDocument();
		Element element = doc.createElement(elementName);
		element.setTextContent(textContent);
		return element;
	}
	
	private Document getDocument() throws PhrescoException {
		try {
			if (doc == null) {
				DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder;
				docBuilder = dbfac.newDocumentBuilder();
				doc = docBuilder.newDocument();
			}
		} catch (ParserConfigurationException e) {
			throw new PhrescoException(e);
		}
		return doc;
	}
	

	public void updatePlugin(File sourcePom, String techId) throws PhrescoException, PhrescoPomException {
		try {
			PomProcessor pomProcessor = new PomProcessor(sourcePom);
			List<Element> skipTrue = new ArrayList<Element>();
			Element skip = createElement(SKIP, TRUE);
			skipTrue.add(skip);
			//TODO:Need to handle
//			pomProcessor.addPlugin(GROUPID, ARTIFACTID, PLUGIN_VERSION, EXECUTIONID, GOAL, skipTrue);
			pomProcessor.save();
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		}
	}
	
	public void addSonarProperties(File pomPath, String techId) throws PhrescoException, ParserConfigurationException {
		try {
			PomProcessor pomProcessor = new PomProcessor(pomPath);
			if (techId.contains(TechnologyTypes.PHP)) {
				updatePhpProperties(pomProcessor);
				pomProcessor.save();
			}
			if (techId.contains(TechnologyTypes.PHP_DRUPAL6)|| techId.contains(TechnologyTypes.PHP_DRUPAL7)) {
				 updateDrupalProperties(pomProcessor);
				pomProcessor.save();
			}
			if (techId.contains(TechnologyTypes.WORDPRESS)) {
				updateWordPressProperties(pomProcessor);
				pomProcessor.save();
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
	}

	private boolean updatePhpProperties(PomProcessor pomProcessor) throws PhrescoException {
		return addProperty(pomProcessor);
	}

	private boolean updateDrupalProperties(PomProcessor pomProcessor) throws PhrescoException {
		try {
			pomProcessor.setProperty(Constants.DRUPAL_VERSION, DRUPAL_STANDAD_VERSION);
			pomProcessor.setProperty(DRUPAL_STANDARD, DRUPAL);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return addProperty(pomProcessor);
	}
	private boolean updateWordPressProperties(PomProcessor pomProcessor) throws PhrescoException {
		try {
			pomProcessor.setProperty(WORDPRESS_STANDARD, WORDPRESS);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return addProperty(pomProcessor);
	}

	private boolean addProperty(PomProcessor pomProcessor) throws PhrescoException {
		try {
			pomProcessor.setProperty(SONAR_LANGUAGE, LANGUAGE);
			pomProcessor.setProperty(SONAR_PHPPMD_SKIP, FALSE);
			pomProcessor.setProperty(SONAR_DYNAMIC_ANALYSIS, TRUE);
			pomProcessor.setProperty(SONAR_PHPPMD_SHOULD_RUN, TRUE);
			pomProcessor.setProperty(SONAR_PHPCODESNIFFER_SHOULD_RUN, TRUE);
			pomProcessor.setProperty(SONAR_PHPCODESNIFFER_SKIP, TRUE);
			pomProcessor.setProperty(SONAR_PHPDEPEND_SHOULD_RUN, TRUE);
			pomProcessor.setProperty(SONAR_PHPUNIT_COVERAGE_SHOULD_RUN, FALSE);
			pomProcessor.setProperty(SONAR_PHPUNIT_SHOULD_RUN, FALSE);
			pomProcessor.setProperty(SONAR_PHPCPD_SHOULD_RUN, TRUE);
			pomProcessor.setProperty(SONAR_PHPCPD_EXCLUDES_SQL, SQL_EXCLUDES);
			pomProcessor.setProperty(SONAR_PHPCPD_EXCLUDES_HTML, HTML_EXCLUDES);			
			pomProcessor.setProperty(SONAR_PHPDEPEND_TIMEOUT,"100");
			pomProcessor.setProperty(SONAR_PHPPMD_TIMEOUT, "100");
			pomProcessor.setProperty(SONAR_PHPCODESNIFFER_TIMEOUT, "100");
			pomProcessor.setProperty(SONAR_PHASE, PHASE);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
		return true;
	}
}
