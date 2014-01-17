/**
 * Phresco Framework
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
package com.photon.phresco.framework.api;


public enum ActionType {

	BUILD("phresco:package"), DEPLOY("phresco:deploy"), UNIT_TEST("phresco:unit-test"), COMPONENT_TEST("phresco:component-test"), FUNCTIONAL_TEST("phresco:functional-test"), 
	LOAD_TEST("phresco:load-test"), PERFORMANCE_TEST("phresco:performance-test"), INTEGRATION_TEST("phresco:integration-test"), CODE_VALIDATE("phresco:validate-code"), SITE_REPORT("clean site"), INSTALL("install"), 
	START("t7:run-forked"), STOP("t7:stop-forked"), RUNAGAINSTSOURCE("phresco:start"), STOPSERVER("phresco:stop"), 
	START_HUB("phresco:start-hub"), STOP_HUB("phresco:stop-hub"), START_NODE("phresco:start-node"), STOP_NODE("phresco:stop-node"), PDF_REPORT("phresco:pdf-report"),
	MINIFY("yuicompressor:compress"), IPA_DOWNLOAD("xcode:ipaBuilder"), THEME_VALIDATOR("phresco:theme-validator"), CONTENT_VALIDATOR("phresco:content-validator"),
	THEME_CONVERTOR("phresco:theme-convertor"), CONTENT_CONVERTOR("phresco:content-convertor"), PROCESS_BUILD("phresco:process-build"), ECLIPSE("phresco:eclipse"),
	LIQUIBASE("phresco:liquibase"), RELEASE("phresco:release");
	
	private String actionType;

	private ActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionType() {
		return actionType;
	}
}
