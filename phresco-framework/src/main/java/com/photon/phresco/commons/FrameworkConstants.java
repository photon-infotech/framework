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
package com.photon.phresco.commons;

import java.io.File;


public interface FrameworkConstants {

    /*
     * Common constants
     */
    String HYPHEN = "-";
    
    String PHRESCO_DOT = "phresco.";
    String DOT_TARGET_DIR = ".target.dir";
    String PHRESCO_DOT_EXTRACT_DOT = "phresco.extract.";
    
    String HYPHEN_N = "-N";
    String DMODULE_NAME = "-DmoduleName=";
    String DOT_JMX = ".jmx";

	String CSV_PATTERN = "\\s*,\\s*";
    
    String LAYER_MOB_ID = "mob-layer";
    String LAYER_APP_ID = "app-layer";
    String LAYER_WEB_ID = "web-layer";
    
    String JAVASCRIPT = "javascript";
    String APP_INFO = "appInfo";
    String APP_SEPERATOR = "#APPSEP#";
    String VERSION_SEPERATOR = "#VSEP#";
    String ROW_SEPERATOR = "#ROWSEP#";

    String LBL = "lbl.";
    String LBL_BTN = "lbl.btn";
    String LBL_URL = "lbl.pageurl.";
    String LBL_PROG_TXT = "progress.txt.";
    String FROM_PAGE_EDIT = "edit";
    String FROM_PAGE_ADD = "add";
    String COMMIT = "commit";    
    String CONFIG = "config";
    String FEATURE = "feature";
    String EMAIL_ID = "emailid";
    
    String X_FILE_NAME = "X-File-Name";
    String CONFIG_TEMP_TYPE = "Config-Temp-Type";
    String SUCCESS_FALSE = "{\"success\": false}";
    String SUCCESS_TRUE = "{\"success\": true}";
    String SUCCESS_TRUE_NAME_ERR = "{\"success\": true,\"configNameError\":\"\"}";
    String SUCCESS_FALSE_NAME_ERR = "{\"success\": false,\"configNameError\":\"Name already exists\"}";
    
    String VALIDATE_THEME = "validateTheme";
    String VALIDATE_CONTENT = "validateContent";
    String CONVERT_THEME = "convertTheme";
    String CONVERT_CONTENT = "convertContent";
    
	String FEATURES = "features";
	String CONFIG_TYPE_FEATURES = "Features";
	String CONFIG_TYPE_COMPONENTS = "Components";
    int JOB_STATUS_NOTOK = -1;
    String FOLDER_DOT_PHRESCO = ".phresco";
    String RUNAGNSRC_INFO_FILE = "runagainstsource.info";
    String CONFIGURATION_INFO_FILE_NAME = "phresco-env-config.xml";
    String PHRESCO_HOME = "PHRESCO_HOME";
    String USER_HOME = "user.home";
    String PROJECTS_HOME = "projects";
    String ARCHIVE_FORMAT = ".zip";
    String IPHONE_FORMAT = ".app";
    String ANDROID_FORMAT = ".apk";
    String IPA_FORMAT = ".ipa";
    String SETTINGS_INFO_FILE_NAME = "settings.xml";
    String PHRESCO_ENV_CONFIG_FILE_NAME = "phresco-env-config.xml";
    String PHRESCO_CONFIG_FILE_NAME = "phresco-fav-config.xml";
    String BUILD_INFO_FILE_NAME = "build.info";
    String PROJECT_INFO = "project.info";
    String GIT_IMPORT_TEMP_DIR = "gitImportTemp";
    String SONAR = "sonar";
    String SOURCE = "src";
    String FROM_PAGE_LOGIN = "login";
    String CONFIG_TYPE = "type";
    String EXE_DOWNLOAD = "Exe_Download";
    String PROCESS_BUILD = "Process_Build";
    String EMBED_APPLICATION = "Embed_Application";
    String IMPORT = "import";
    String MINIFY = "minify";
    String FEATURE_CONFIG = "FeatureConfig"; 
    
    String REQ_CUSTOMER_ID = "customerId";
    String REQ_PROJECT_ID = "projectId";
    String REQ_RECENT_PROJECT_ID = "recentProjectId";
    String REQ_RECENT_APP_ID = "recentAppId";
    String REQ_APP_ID = "appId";
    String REQ_POM_VERSION = "pomVersion";
    String REQ_PILOT_PROJECTS = "pilotProjects";
    String REQ_CURRENT_APP_NAME = "currentAppName";
    String REQ_DEFAULT_SCOPE = "compile";
    String REQ_SELECTED_FEATURES = "selectedFeatures";
    String REQ_LOG_MESSAGES = "logMessages";
    String REQ_SELECTED_DOWNLOADINFO = "selectedDownloadInfo";
    String REQ_DEFAULT_FEATURES = "defaultFeatures";
    String REQ_HAS_MODULES = "hasModules";
    String REQ_HAS_JSLIBS = "hasJsLibs";
    String REQ_HAS_COMPONENTS = "hasComponents";
    String REQ_SCOPE = "scope";
    String REQ_SELECTED_DOWNLOADINFO_VERSION = "selectedDownloadInfoVersion";
    String REQ_CURRENT_SELECTBOX_ID = "selectBoxId";
    String REQ_DEFAULT_OPTION = "defaultOptTxt";
    String REQ_PROJECT_FEATURES = "projectInfoFeatures";
    String REQ_PROJECT_LAYERS = "layers";
    String REQ_PARAM_NAME_TECH_GROUP = "TechGroup";
    String REQ_PARAM_NAME_TECHNOLOGY = "Technology";
    String REQ_APP_LAYER_INFOS = "appLayerInfos";
    String REQ_APP_CODE_ID = "hasAppCodeId";
    String REQ_PARAM_NAME_TECH__ID = "techId";
    String REQ_PARAM_NAME_VERSION = "Version";
    String REQ_PARAM_NAME_PHONE = "Phone";
    String REQ_PARAM_NAME_TABLET = "Tablet";
    String REQ_PARAM_NAME_LAYER_ID = "layerId";
    String REQ_PARAM_NAME_TECH_GROUP_ID = "techGroupId";
    String REQ_JAVASCRIPT_TYPE_MODULE = "JAVASCRIPT";    
    String REQ_FEATURES_TYPE_MODULE = "FEATURE";
    String REQ_FEATURES_TYPE = "type";
    String REQ_FEATURES_MOD_GRP = "moduleGroup";
    String REQ_WEB_LAYER_APPINFO = "weblayerAppinfo";
    String MEGABYTE = "MB";
    String KILOBYTE = "KB";
    String TECH_SERVER_ID = "config_Server";
    String TECH_DATABASE_ID = "config_Database";
    String TECH_WEBSERVICE_ID = "config_WebService";
    String REQ_HUB_STATUS = "hubStatus";
    String REQ_TEST_AGAINST_VALUES = "testAgainstParameter";
    
    String REQ_KEY = "key";
    String REQ_VALUE = "value";
    
    String DO_NOT_CHECKIN_DIR = "do_not_checkin";
    String RUN = "run1";
    String FETCH_LOG_MESSAGES = "fetchLogMessages";
    String SETTINGS_XML = "-settings.xml";
    String ARCHIVES = "archives";
    String CUMULATIVE = "cumulativeReports";
    String BUILD_DIR = DO_NOT_CHECKIN_DIR + File.separator + "build";
    String CHECKIN_DIR = "do_not_checkin";
    String BUILD_PATH = "build";
    String WORKSPACE_DIR = "workspace";
    String SPACE = " ";
    String UNDERSCORE = "_";
    String PDF = "pdf";
    String XML = "xml";
    String JSON = "json";
    String POM_XML = "pom.xml";
    String PHR_POM_XML = "phresco-pom.xml";
    String STATIC_ANALYSIS_REPORT = "static-analysis-report";
    String INDEX_HTML = "index.html";
	String WAR = "war";
	String PATH_SUREFIRE_REPORTS = "/target/surefire-reports";
	String APKLIB  = "apklib";
	String APK  = "apk";
	String OTHERS = "Others";
	String OTHER = "Other";
	
	String RESPONSE_STATUS_SUCCESS = "success";
	String RESPONSE_STATUS_FAILURE = "failure";
	String RESPONSE_STATUS_ERROR = "error";
    
	 /*
     * CI constants
     */
    String JENKINS_HOME = "JENKINS_HOME";
    String CI_BUILD_NAME = "PHR_ci_build";
    String CI_JOB_INFO_NAME = "ciJob.info";
    String CI_JOB_TEMPLATE_NAME = "ciJobTemplate.info";
    String CI_JOB_JSON_BUILDS = "builds";
    String CI_JOB_BUILD_RESULT = "result";
    String CI_JOB_BUILD_ID = "id";
    String CI_JOB_BUILD_TIME_STAMP = "timestamp";
    String CI_JOB_BUILD_ARTIFACTS = "artifacts";
    String CI_JOB_BUILD_ARTIFACT = "artifact";
    String CI_JOB_BUILD_DOWNLOAD_PATH = "relativePath";
    String CI_CREATE_JOB_COMMAND = "create-job";
    String CI_UPDATE_JOB_COMMAND = "update-job";
    String CI_BUILD_JOB_COMMAND = "build";
    String CI_JOB = "job";
    String LAST_BUILD = "lastBuild";
    String CI_BUILD_DELETE_COMMAND = "delete-builds";
    String CI_JOB_DELETE_COMMAND = "delete-job";
    String CI_RELOAD_CONFIGS = "reload-configuration";
    String CI_APPEND_JOBS = "appendJobs";
    String CI_CREATE_NEW_JOBS = "createNewJobs";
    String CI_JENKINS_ALIVE = "jenkinsAlive";
    String CI_BUILD_JENKINS_ALIVE = "buildJenkinsAlive";
    String CI_BUILD_IS_IN_PROGRESS = "isBuildInProgress";
    String CI_NO_OF_JOBS_IN_PROGRESS = "noOfJobsIsInProgress";
    String CI_BUILD_TRIGGERED_FROM_UI = "buildTriggeredFromUI";
    String CI_PROFILE = "-Pci clean ";
    String CI_FUNCTIONAL_ADAPT = "-Pci ci:adapt ";
    String CI_ZIP = ".zip\"";
    String CI_PDF = ".pdf\"";
    String TIMER_TRIGGER = "TimerTrigger";
    String CLASS = "class";
    String SCM = "scm";
	String ZIP_FILE = "zip";
    String SVN = "svn";
    String TRUNK = "trunk";
    String MASTER = "master";
    String UPDATE = "update";
    String DELETE_APPLN = "deleteAppln";
    String DELETE_PROJ = "deleteProj";
    String APPLN_UPDATE = "applnUpdate";
    String FEATURE_UPDATE = "featureUpdate";
    String CONFIG_UPDATE = "configUpdate";
    String EDIT_APPLN = "editAppln";
    String NO = "no";
	String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
	String HTTPS = "https";
	String HTTP = "http";
	String SSL = "SSL";
	String HTTPS_PROXY = "https_proxy";
	String HTTP_PROXY = "http_proxy";
    
    String REMOTE = "remote";
    String ORIGIN = "origin";
    String FETCH = "fetch";
    String BRANCH = "branch";
    String MERGE = "merge";
    String REF_HEAD_MASTER = "refs/heads/master";
    String REFS_HEADS_REMOTE_ORIGIN = "+refs/heads/*:refs/remotes/origin/*";
    String URL = "url";
    String ADD = "add";
    String REPO_URL = "repoUrl";
    String ADD_TO_REPO = "addToRepo";
    String TEST_REPO_URL = "testRepoUrl";
    String PROJECT_UPDATE_POPUP = "projectUpdatePopup";
    String PROJECT_ALREADY_IMPORTED = "Project already imported";
    String CLONED_WORKSPACE = "clonedWorkspace";
	String CI_SCM = "scm";
	String CLONE_WORKSPACE_SCM = "hudson.plugins.cloneworkspace.CloneWorkspaceSCM";
	String CI_CLASS = "class";
	String PARENT_JOB_NAME = "parentJobName";
	String CI_CRITERIA = "criteria";
	String ZERO = "0";
	String ONE = "1";
	String TWO = "2";
	String PUBLISHERS_NODE = "publishers";
	String ALL_FILES = "**/*";
	String SUCCESSFUL = "Successful";
	String TAR = "TAR";
	String WORKSPACE_GLOB = "workspaceGlob";
	String ARCHIVE_METHOD = "archiveMethod";
	String BLUE = "BLUE";
	String YELLOW = "YELLOW";
	String RED = "RED";
	String COLOR = "color";
	String ORDINAL = "ordinal";
	String CI_SUCCESS_FLAG = "SUCCESS";
	String CI_UNSTABLE_FLAG = "UNSTABLE";
	String CI_FAILURE_FLAG = "FAILURE";
	String CRITERIA_ANY = "Any";
	String CRITERIA_NOT_FAILED = "Not Failed";
	String CRITERIA_SUCCESSFUL = "Successful";
	String INPROGRESS = "INPROGRESS";
	String RESTARTED = "restarted";
	String NAME = "name";
	String CHILD_PROJECTS = "childProjects";
	String THRESHOLD = "threshold";
	String ROOT_POM = "rootPOM";
	String XPATH_ROOT_NODE = "xpathRootNode";
	String SMTP_AUTH_USERNAME = "smtpAuthUsername";
	String SMTP_AUTH_PASSWORD = "smtpAuthPassword";
	String TFS_EXECUTABLE = "tfExecutable";
	String ANY = "Any";
    String GIT = "git";
    String BITKEEPER = "bitkeeper";
    String PERFORCE = "perforce";
    String TFS = "tfs";
    String REPO_TYPE = "repoType";
    String REPO = "repo";
    String IS_NOT_WORKING_COPY = "is not a working copy";
    String UPDATE_SVN_PROJECT = "updateProject";
    String SVN_TYPE = "svnType";
    String SVN_CHECKOUT_TEMP = "svn-checkout-temp";
    String CHECKOUT_TEMP = "checkout-temp";
    String PHRESCO = "/.phresco";
    String GITHUB_SCM = "hudson.plugins.git.GitSCM";
    String SVN_SCM = "hudson.scm.SubversionSCM";
    String GITHUB_SCM_FILE_NAME = "gitHubScm.xml";
    String CI_FILE_RELEASE_NODE="hudson.plugins.collabnet.filerelease.CNFileRelease";
    String CI_FILE_RELEASE_OVERRIDE_AUTH_NODE="override__auth";
    String CI_FILE_RELEASE_URL="url";
    String CI_FILE_RELEASE_USERNAME="username";
    String CI_FILE_RELEASE_PASSWORD="password";
    String CI_FILE_RELEASE_PROJECT="project";
    String CI_FILE_RELEASE_PACKAGE="rpackage";
    String CI_FILE_RELEASE_RELEASE="release";
    String CI_FILE_RELEASE_OVERWRITE="overwrite";
    String CI_FILE_RELEASE_FILE_PATTERN="file__patterns";
    String CI_FILE_RELEASE_FILE_PATTERN_NODE ="hudson.plugins.collabnet.documentuploader.FilePattern";
    String CI_BUILD_EXT = "do_not_checkin/build/*.zip";
    String STRING_NULL = "null";
    String API_JSON = "api/json";
    String BUSY_EXECUTORS = "computer/api/xml?xpath=/computerSet/busyExecutors/text()";
    String JOB_STATUS_COMMAND = "lastBuild/api/xml?depth=1&xpath=*/result/text()";
    String DELETE_TYPE_BUILD = "Build";
    String DELETE_TYPE_JOB = "Job";
    String SUCCESS_EMAILS = "successEmails";
    String FAILURE_EMAILS = "failureEmails";
    String TRIGGER_SUCCESS__EMAIL_RECIPIENT_LIST = "publishers//hudson.plugins.emailext.ExtendedEmailPublisher//configuredTriggers//hudson.plugins.emailext.plugins.trigger.SuccessTrigger//email//recipientList";
    String TRIGGER_FAILURE_EMAIL_RECIPIENT_LIST = "publishers//hudson.plugins.emailext.ExtendedEmailPublisher//configuredTriggers//hudson.plugins.emailext.plugins.trigger.FailureTrigger//email//recipientList";
    String ARTIFACT_ARCHIVER_ROOT_NODE = "publishers//hudson.tasks.ArtifactArchiver";
    String ARTIFACT_ARCHIVER_VALUE_NODE = "publishers//hudson.tasks.ArtifactArchiver//artifacts";
    String EMAIL_PUBLISHER = "publishers//hudson.plugins.emailext.ExtendedEmailPublisher";
    String HUDSON_TASKS_ARTIFACT_ARCHIVER_NODE = "hudson.tasks.ArtifactArchiver";
    String EXTENDED_EMAIL_PUBLISHER_NODE = "hudson.plugins.emailext.ExtendedEmailPublisher";
    String ATTACHEMENT_PATTERN = "publishers//hudson.plugins.emailext.ExtendedEmailPublisher//attachmentsPattern";
	String CRON_VALIDATION = "cronValidation";
	String CI_TOMCAT_HTTP_PORT = "/project/build/plugins/plugin/configuration/tomcatHttpPort";
	String CI_ERROR = "error.";
	String CI_MESSAGE = ".message";
    String GOALS = "goals";
    String TRIGGERS = "triggers";
    String TRIGGERS_SPEC = "triggers//spec";
    String SCM_BRANCHES_NAME = "scm/branches//name";
    String SCM_USER_REMOTE_CONFIGS_URL = "scm/userRemoteConfigs//url";
    String SCM_LOCATIONS_REMOTE = "scm/locations//remote";
    String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
	String HUDSON_TASKS_BUILD_TRIGGER_NODE = "hudson.tasks.BuildTrigger";
	String TFS_URL = "scm/serverUrl";
	String TFS_USERNAME = "scm/userName";
	String TFS_PASSWORD = "scm/userPassword";
	String TFS_PROJECTPATH = "scm/projectPath";
	String MAVEN_HOME_ENV = "MAVEN_HOME";
	String USE_PRIVATE_REPOSITORY_NODE = "usePrivateRepository";
	String POM_NODE = "pom";
	String MAVEN_NAME_NODE = "mavenName";
	String TARGETS_NODE = "targets";
	String HUDSON_TASKS_MAVEN_NODE = "hudson.tasks.Maven";
	String HUDSON_TASKS_SHELL = "hudson.tasks.Shell";
	String POST_BUILDERS_NODE = "postbuilders";
	String PRE_BUILDERS_NODE = "prebuilders";
	String SCM_SUBVERSION_SCM_POST_CREDENTIAL = "/scm/SubversionSCM/postCredential";
    String CI_FILE_RELEASE_PUBLISHER_NODE="publishers";
    String COLON = ":";
    String HTTP_PROTOCOL = "http";
    String PROTOCOL_POSTFIX = "://";
    String SPLIT_DOT = "\\.";
    String FORWARD_SLASH = "/";
    String BACK_SLASH = "\\";
    String GITIGNORE_FILE = "/.gitignore";
    String LOCALHOST = "localhost";
    String CONFIG_SUBMIT = "configSubmit";
    String JENKINS_START = "JenkinsStart";
    String JENKINS_STOP = "JenkinsStop";
    String NODE_START = "start";
    String NODE_STOP = "stop";
    String START_SERVER = "start";
    String STOP_SERVER = "stop";
    String NODEJS_SERVER_NAME = "serverName";
    String NODEJS_DB_NAME = "databaseName";
    String SERVER_PROTOCOL = "server.protocol";
    String SERVER_HOST = "host";
    String SERVER_PORT = "port";
    String PROTOCOL = "protocol";
    String LOG_DIR = "log";
    String RUN_AGS_ENV_FILE = "runagainstsource.info";
    String LOG_FILE = "sourcecompile.log";
    String RUN_AGS_LOG_FILE = "runagainstsource.log";
    String LINE_SEPERATOR = "line.separator";
    String READ_LOG_VIEW = "view";
    String READ_ERROR_LOG_VIEW = "errorLogView";
    String PATH = "path";
    String EDIT = "edit";
    String SHOW_ANDROID_DEVICE = "showDevice"; 
    String IMPORT_SQL = "importSql";
    String PHRESCO_SQL_PATH = "phresco.sql.path";
    String ADDITIONAL_CONTEXT_PATH = "additional_context";
    String TOMCAT_DIR ="tomcat";
    String TOMCAT_LOGS_DIR = "logs";
    String CATALINA_FILE_START_NAME ="catalina";
    String IMPORT_PROPERTY ="importsql.property";
    String TEMP_FOLDER ="temp";
    String NODEJS_RUN_AGAINST = "nodeJS_runAgnSrc";
    String PHASE_RUNGAINST_SRC = "runAgainstSource";
    String JAVA_RUN_AGAINST = "runAgnSrc";
    String CERTIFICATES = "certificates";
    String KEY_CERTIFICATE = "certificate";
    String WWW_AUTHENTICATE = "WWW-Authenticate";
    String LT_HTTPS_COLON_BACKSLASH = "<https://";
    String COLON_443_GT_SPACE = ":443> ";
    String CREDENTIAL_ENTRY_STRING = "//credentials/entry/string";
    String SITES_CONFLUENCE= "//sites";
    String USERNAME = "userName";
    String CONFLUENCE_USERNAME = "username";
    String API_TOKEN = "apiToken";
    String TEAM_TOKEN = "teamToken";
    String ENTRY_TAG = "entry";
    String CONFLUENCE_SITE_NODE="com.myyearbook.hudson.plugins.confluence.ConfluenceSite";
    String CONFLUENCE_SITE_URL="url";
    String TESTFLIGHT_TOKEN_NAME="tokenPairName";
    String HYPEN_PASSWORDCREDENIAL_TAG = "hudson.scm.SubversionSCM_-DescriptorImpl_-PasswordCredential";
    String STRING_TAG = "string";
    String YES = "yes";
    String SITE_NAME = "siteName";
    String ATTACH_ARCHIVED_ARTIFACTS = "attachArchivedArtifacts";
    String NOTIFYTEAM = "notifyTeam";
    String BUILD_NOTES = "buildNotes";
    String BUILD_IF_UNSTABLE = "buildIfUnstable";
    String APPEND_CHANGE_LOG = "appendChangelog";
    String FILE_SET = "fileSet";
    String REPLACE = "replace";
    String DEBUG = "debug";
    String SPACE_NAME = "spaceName";
    String PAGE_NAME = "pageName";
    String EDITORS = "editors";
    String HUDSON_COBERTURA_PUBLISHER = "hudson.plugins.cobertura.CoberturaPublisher";
    String COBERTURA_REPORT_FILE = "coberturaReportFile";
    String COVERAGE_XML = "**/coverage.xml";
    String ONLY_STABLE = "onlyStable";
    String FAIL_UNHEALTHY = "failUnhealthy";
    String FAIL_UNSTABLE = "failUnstable";
    String AUTO_UPDATE_HEALTH = "autoUpdateHealth";
    String AUTO_UPDATE_STABILITY = "autoUpdateStability";
    String ZOOM_COVERAGE_CHART = "zoomCoverageChart";
    String FAIL_NO_REPORTS = "failNoReports";
    String HEALTHY_TARGET = "healthyTarget";
    String UNHEALTHY_TARGET = "unhealthyTarget";
    String FAILING_TARGET = "failingTarget";
    String TARGETS = "targets";
    String ENUM_MAP= "enum-map";
    String ENUM_TYPE = "enum-type";
    String HUDSON_COBERTURA_TARGETS_COVERAGEMETRIC = "hudson.plugins.cobertura.targets.CoverageMetric";
    String INT = "int";
    String CONDITIONAL = "CONDITIONAL";
    String LINE = "LINE";
    String METHOD = "METHOD";
    String SOURCE_ENCODING = "sourceEncoding";
    String ASCII = "ASCII";
    String HUDSON_TRIGGER_TIMER = "hudson.triggers.TimerTrigger";
    String HUDSON_TRIGGER_SCMTRIGGER = "hudson.triggers.SCMTrigger";
    String SPEC = "spec";
    String SITES_CONFLUENCESITE_URL = "//sites/com.myyearbook.hudson.plugins.confluence.ConfluenceSite/url";
    String TESTFLIGHT_TOKENPAIR = "//tokenPairs/testflight.TokenPair/tokenPairName";
    String CONFLUENCECONFIGURATION = "confluenceConfigurations";
    String CONFLUENCE_URL_KEY = "confluenceUrl";
    String CONFLUENCE_USERNAME_KEY = "confluenceUsername";
    String CONFLUENCE_PASSWORD_KEY = "confluencePassword";
    String VALUES_KEY = "values";
    String MAVEN_SEP_COBER = "mvn#SEP#cobertura:cobertura";

    String JAVA_STAND_ALONE = "tech-java-standalone";
    String MAINCLASSNAME = "mainClassName";
    String JARNAME = "jarName";
    String JAVA_STANDALONE = "Javastandalone";
    String DEPENDENCY_VERSION = "1.0";
    String SYSTEM = "system";
	String JAVA_POM_MANIFEST = "manifest";
	String JAVA_POM_MAINCLASS = "mainClass";
	String FINAL_NAME = "finalName";
	String MAIN_CLASS_VALUE ="mainClassValue";
    String ENVIRONMENT_NAME = "environmentName";
    String MODULE_NAME = "moduleName";
    String SHOW_ERROR = "showerror";
    String HIDE_LOG = "hidelog";
    String SHOW_DEBUG = "showdebug";
    String SKIP_TEST = "skiptest";
	String FUNCTIONAL_TEST = "functionalTest";
	String CODE_VALIDATION = "codeValidation";
	String PDF_REPORT = "pdfReport";
	String LOAD_TEST = "loadTest";
	String PERFORMANCE_TEST_CI = "performanceTest";
	String COMPONENT_TEST_CI = "componentTest";
	String RELEASE_CI = "release";
	String REQ_FUNCTEST_SELENIUM_TOOL = "functionalTestSeleniumTool";
	String SELENIUM_GRID = "grid";
	String SELENIUM_WEBDRIVER = "webdriver";
	String SELENIUM_UIAUTOMATION = "UIAutomation";
	String BUILD = "build";
    String DEPLOY = "deploy";
    String BUILD_INFO_ENVS = "buildInfoEnvs";
    String DEPLOY_IMPORT_SQL = "importSql";
    String DEPLOY_SERVER_PARAM = "serverName";
    String DEPLOY_DATABASE_PARAM = "databaseName";
    String DEPLOY_EMAIL_PARAM = "emailName";
    String DEPLOY_WEBSERVICE_PARAM = "webServiceName";
    String TEST_PARAM = "test=";
    String TEST_PARAM_VALUE = "AllTest";
    String ALL = "All";
    String KEY_QUOTES = "\"";
    String DEPLOY_BUILD_NAME = "buildName";
    String IPHONE_BUILD_NAME = "application.path";
    String IPHONE_SIMULATOR_VERSION = "simulator.version";
    String DEVICE_TO_USE = "family";
    String IPHONE_SCHEMA_PARAM = "scheme";
    String DEPLOY_LOCATION = "deployLocation";
    String MANUAL = "manual";
    String DEPLOY_CONTEXT = "context";
    String DEPLOY_SERVERNAME="serverName";
    String DEPLOY_ANDROID_DEVICE_MODE = "android.device";
    String DEPLOY_ANDROID_EMULATOR_AVD = "android.emulator.avd";
    String ANDROID_LOWER_VER = "2.2";
    String DEVICE_DEPLOY = "device.deploy";
    String UNIT_TEST = "unittest";
    String PROJECT_UPDATE = "projectUpdate";
    String TRUE = "true";
    String FALSE = "false";
    String NODE_JS_ID = "tech-nodejs-webservice";
    String TEST_DIR = "test";
    String SAFARI_WARNING_MSG = "Safari browser is only supported for SeleniumRC";
    String TARGET_DIR = "target";
    String JMETER_REPORTS = "jmeter-reports";
    String FUNCTIONAL = "functional";
    String UNIT = "unit";
    String COMPONENT = "component";
    String LOAD = "load";
    String PERFORMACE = "performance";
    String INTEGRATION = "integration";
    String INTEGRATION_TEST = "-integrationtest";
    String WEBSERVICE = "WebService";
    String EMAIL = "Email";
    String WEBSERVICES_DIR = "WebServices";
    String ANDROID_PERFORMACE = "androidPerformance";
    String CI_SETUP = "CISetup";
    String CI_ENCRYPT_MAGIC = "::::MAGIC::::";
    String AES_ALGO = "AES";
    String SHA_ALGO = "SHA-256";
    String CI_SECRET_KEY = "73895ea77c9ddf8652324091a736cbe4c5d3f9fc338ae04fd8423afbd8308d38";
    String CI_UTF8 = "UTF-8";
    String CI_START = "CIStart";
    String CI_STOP = "CIStop";
    String CODE_404 = "404";
    String CI_PRE_BUILD_STEP = "phresco:ci-prestep -DjobName=${env.JOB_NAME}";
    
    String MAVEN = "mvn";
    String SHELL = "shell";
    String COMMAND = "command";
    String SKIP_TESTS = "-DskipTests=true";
    String TEST_DIRECTORY = "dir_type";
    String MVN_TEST_COMMAND = "mvn clean test";
    String MVN_SHAREPOINT_NUNIT_TEST_COMMAND = "mvn sharepoint:test";
    String MVN_INSTALL_COMMAND = "mvn install -DskipTests=true";
    String MVN_MOBILE_INSTALL_COMMAND = "mvn install ";
	String MVN_ANDROID_TEST_COMMAND = "mvn clean install ";
    String MVN_SELENIUM_STOP_COMMAND = "mvn selenium:stop-server";
    String MVN_SONAR_COMMAND = "mvn sonar:sonar";
    String MVN_PROJECT_UPDATE_COMMAND = "mvn scm:update";
    String MVN_JENKINS_START = "mvn t7:run-forked";
    String MVN_JENKINS_STOP = "mvn t7:stop-forked";
    String MVN_IPHONE_IPA_COMMAND = "mvn xcode:ipaBuilder";
    String MVN_IPHONE_FUNCTIONAL_COMMAND = "mvn xcode:instruments"; // IPhone functional test command
    String MVN_IPHONE_NATIVE_UNITTEST = "mvn xcode:xcodebuild"; // IPhone unit test command
    String MVN_IPHONE_CODE_VALIDATE = "mvn xcode:codevalidate"; // IPhone code validate command
    String MVN_SITE_COMMAND = "mvn clean site";
    String PHRESCO_FILE_SERVER_PORT_NO = "phresco.file.server.port";
    String APPLICATIONS = "applications";
    String SETTINGS = "settings";
    String SETTINGS_HEADER = "Settings";
    String FILE_EXTENSION_XML = "xml";
    String FILE_EXTENSION_JTL = "jtl";
    String SEMI_COLON = ";";
    String SQUARE_CLOSE = "]";
    String SQUARE_OPEN = "[";
    String GRAPH_SUMMARY_DATA = "var summaryData = ";
    String GRAPH_VOLUME_DATA = "var volumeData = ";
    String GRAPH_DATA = "var priceData = ";
    String SCRIPT_END = "</script>";
    String SCRIPT_START = "<script>";
    String GRAPH_JSON = "var jsonData = [";
    String IPHONE_SDK = "sdk";
    String IOS_TEST_TYPE = "applicationTest";
    String IPHONE_CONFIGURATION = "configuration";
    String SCHEMES = "Schemes";
    String XCODEBUILD_LIST_WORKSPACE_CMD = "xcodebuild -list -workspace ";
    String IPHONE_XCODE_WORKSPACE_PROJ_EXTN = "xcworkspace";
    String IPHONE_TARGET_NAME = "targetName";
    String IPHONE_PLISTFILE = "plistfile";
    String JAR_NAME = "jarName";
    String MAIN_CLASS_NAME = "mainClassName";
    String ENCRYPT = "encrypt";
    String IPHONE_XCODEPROJ = "/source/Phresco.xcodeproj";
    String IPHONE_XCODE_PROJ_EXTN = "xcodeproj";
    String ANDROID_DEVICE = "device";
    String ANDROID_DEVICE_LIST = "deviceList";
    String ANDROID_PROGUARD_SKIP = "android.proguard.skip";
    String PROFILE_ID = "sign";
    String GOAL_INSTALL = "install";
    String GOAL_SIGN = "sign";
    String ANDROID_PROFILE_PLUGIN_GROUP_ID = "org.apache.maven.plugins";
    String ANDROID_PROFILE_PLUGIN_ARTIFACT_ID = "maven-jarsigner-plugin";
    String ANDROID_PROFILE_PLUGIN_VERSION = "1.2";
    String ANDROID_EXECUTION_ID = "signing";
    String ELEMENT_ARCHIVE_DIR = "archiveDirectory";
    String ELEMENT_REMOVE_EXIST_SIGN = "removeExistingSignatures";
    String ELEMENT_INCLUDES = "includes";
    String ELEMENT_INCLUDE = "include";
    String ELEMENT_BUILD = "do_not_checkin/build/*.apk";
    String ELEMENT_TARGET = "do_not_checkin/target/*.apk";
    String ELEMENT_VERBOS = "verbose";
    String ELEMENT_VERIFY = "verify";
    String HEAD_REVISION = "HEAD";
    String UNVERSIONED = "unversioned";
    String REPORTS_JASPER = "reports/jasper/";
    String PLATFORM = "platform";
    String POM_LINE_BREAK = "linebreakpos";
    String POM_LINE_MAX_COL_COUNT = "10000";
    String KEYPASS = "keyPass";
    
    String CONNECTION_URL = "connectionUrl";
	String USER_NAME = "username";
	String SCM_SVN = "scm:svn:";
	
	/*
	 * Technology based Key
	 */
	String HOME_KEY = "Home";
	String SETTINGS_KEY = "Settings";
	String DOWNLOAD_KEY = "Download";
	String HELP_KEY = "Help";
	String CODE_KEY = "Code";
	String BUILD_KEY = "Build";
	String UNIT_TEST_KEY = "Unit_Test";
	String COMPONENT_TEST_KEY = "Component_Test";
	String FUNCTIONAL_TEST_KEY = "Functional_Test";
	String PERFORMANCE_TEST_KEY = "Performance_Test";
	String LOAD_TEST_KEY = "Load_Test";
	String MANUAL_TEST_KEY = "Manual_Test";
	String REPORT_KEY = "Reports";
	String CI_KEY = "CI";
	String MINIFICATION_KEY = "Minification";
	String REMOTE_DEPLOYMENT_OPTION = "Remote_Deployment";
	String RUN_AGAINST_KEY = "Run_Against_Source";
	String DEPLOY_KEY = "Deploy";
	String EXE_DOWNLOAD_KEY = "Exe_Download";
	String FEATURES_KEY = "Feature_Config";
	String COMPONENT_CONFIG = "Component_Config";
	String THEME_BUILDER_KEY = "Theme_Builder";
	
	String SIMPLE_UI = "simpleUI";
	String ADVANCE_UI = "advanceUI";
    
	/*
	 * Dynamic parameters
	 */
	String KEY_APP_INFO = "applicationInfo";
	
	/**
	 * Performance related
	 */
	String CUSTOM = "custom";
	String TYPE_GET = "get";
	String ARRAY_LIST = "java.util.List";
	String REQ_OBJECT_CLASS = "objectClass";
	String REQ_CLASS_NAME = "className";
	String REQ_VALUES_FROM_JSON = "valuesFromJson";
	String PERFORMANCE_TEST = "performance-test";
	String PHASE_LOAD = "load-test";
	String REQ_CUSTOM_TEST_AGAINST = "customTestAgainst";

	
    /*
     *  File Types for Browse in Functional
     */
    String FILE_TYPES = "FileType";
    String FILE_BROWSE = "File";
    
    
    String POST = "POST";
    String GET = "GET";
	String SKIPTESTS = "skipTests";
	String PROFILE = "profile";
	String CAN_CREATE_IPA = "canCreateIpa";
	String DEPLOY_TO_DEVICE = "deviceDeploy";
	String BUILD_NAME = "buildName";
	String BUILD_NUMBER = "buildNumber";
	String BROWSER = "browser";
	String TRIGGER_SIMULATOR = "triggerSimulator";
    
    String JFORUM_PARAMETER_URL = "/jforum.page?action=validateLogin&module=user";
    String JFORUM_USERNAME = "&username=";
    String JFORUM_PASSWORD = "&password=";
    String PUSH_SERVER_CONTEXT = "Push Your Server Context XML Here";
    
    
    String ADMIN_CONFIG_JFORUM_PATH = "phresco.forum.url";

    String CONFIG_XML = "config.xml";
    String CREDENTIAL_XML = "credential.xml";
    String CREADENTIAL_JSON = "credential.json";
    String MAIL = "mail";
    String CI_JDK_HOME_XML = "config.xml";
    String CI_SECRET_KEY_FILE = "secret.key";
    String CI_MAVEN_HOME_XML = "hudson.tasks.Maven.xml";
    String CI_CREDENTIAL_XML = "hudson.scm.SubversionSCM.xml";
    String CI_CONFLUENCE_XML = "com.myyearbook.hudson.plugins.confluence.ConfluencePublisher.xml";
    String CI_TESTFLIGHT_XML = "testflight.TestflightRecorder.xml";
    String CI_MAILER_XML = "hudson.tasks.Mailer.xml";
    String CI_TFS_XML = "hudson.plugins.tfs.TeamFoundationServerScm.xml";
    String CI_MAIL_EXT_PLUGIN = "email-ext.hpi";
    String CI_HUDSONURL = "hudsonUrl";
    String CI = "ci";
     
    
    /***
     * Environment Constants
     * 
     */
    String SETTINGS_FROM_TAB = "fromTab";
    String ENVIRONMENT_VALUES = "envs";  
    String DELETABLE_ENVS = "deletableEnvs";

    /*
     * Session Constants
     * Ex: SESSION_XXX
     */
    String SESSION_APPINFO	= "sessionAppInfo";
    String SESSION_SELECTED_INFO = "selectedInfo";
    String SESSION_SELECTED_MODULES = "selectedModules";
    String REQ_APPLICATION_TYPES = "ApplicationTypes";
    String SESSION_APPLICATION_TYPE = "ApplicationType";
    String SESSION_PROJECT = "project";
    String SESSION_TECHNOLOGY_MODULES = "technologyModules";
    String SESSION_PROPERTY_INFO_LIST = "propertyInfoList";
    String SESSION_SETTINGS_TEMPLATES = "SettingsTemplates";
    String SESSION_SELECTED_CONFIG_INFO = "selectedConfigurationInfo";
    String SESSION_OLD_NAME = "oldName";
    String SESSION_GRAPH_SCRIPT = "graphScript";
    String SESSION_NODEJS_SERVER_NAME = "_serverName";
    String SESSION_NODEJS_DB_NAME = "_databaseName";
    String SESSION_SERVER_STATUS = "_serverStatus";
    String SESSION_SERVER_PORT_VALUE ="serverPort";
    String SESSION_SERVER_HOST_VALUE ="serverHost";
    String SESSION_SERVER_PROTOCOL_VALUE ="serverProtocol";
    String SESSION_ENV_NAME = "environment";
    String SESSION_PERMISSION_IDS = "sessionPermissionIds";
    String SESSION_PERMISSIONS = "sessionPermissions";
    String SESSION_CUSTOMERS = "listOfCustomers";
    
    /*
     * Request Constants
     * Ex: REQ_XXX
     */
    String REQ_USER_ID = "userId";
    String REQ_UI_TYPE	= "uiType";
    String REQ_FAVOURITE_CONFIGS = "favouriteConfigs";
    String REQ_FAVOURITE_CONFIG_ID = "favouriteConfigId";
    String REQ_FROM_FAVOURITE_CONFIG = "fromFavouriteConfig";
    String REQ_ENV_SPECIFIC = "envSpecific";
    String REQ_APPINFO	= "appInfo";
    String REQ_TESTCASE = "testCase";
    String REQ_TESTSUITE = "testSuite";
    String REQ_APPINFO_SERVERS = "appinfoServers";
    String REQ_APPINFO_DBASES = "appinfoDbs";
    String REQ_APP_DIR_NAME  = "appDirName";
	String REQ_TITLE_ADD_APPLICATION = "Add Application";
    String REQ_BUILD_FROM = "from";
    String REQ_PROJECT_INFO = "projectInfo";
    String REQ_NODEJS_SERVER_LOG = "NodeJsServerLog";
    String REQ_PROJECT = "project";
    String REQ_PROJECT_CODE = "projectCode";
    String REQ_APP_INFO = "applicationInfo";
    String REQ_PROJECT_LOCATION = "projectLocation";
    String REQ_SELECTED_TAB = "selectedTab";
    String REQ_SELECTED_MENU = "selectedHeader";
    String REQ_PROJECTS = "Projects";
    String REQ_OPTION_ID = "optionId";
    String REQ_TOTAL_THROUGHPUT = "totalThroughput";
    String REQ_TOTAL_STD_DEV = "totalStdDev";
    String REQ_TECHNOLOGY = "technology";
    String REQ_FUNCTIONAL_TEST_FRAMEWORKS = "funcTestFrameworks";
    String REQ_TECH_HAS_SERVER = "hasServer";
    String REQ_TECH_HAS_DB = "hasDb";
    String REQ_TECH_HAS_WEBSERVICE = "hasWebservice";
    String REQ_SELECTED_TECHNOLOGY = "SelectedTechnology";
    String REQ_NAME = "name";
    String REQ_CODE = "code";
    String REQ_TYPE = "type";
    String REQ_DESCRIPTION = "description";
    String REQ_APPLICATION = "application";
    String REQ_MODULE = "module";
    String REQ_EQLIPSE = "eclipse";
    String REQ_APPTYPE_TECHNOLOGIES = "appTypeTechnologies";
    String REQ_ADD_APPLICATION = "addApplication";
    String REQ_EDIT_APPLICATION = "addApplication";
    String REQ_FRAMEWORK = "framework";
    String REQ_PLATFORM = "platform";
    String REQ_APPLICATIONSERVER = "applicationserver";
    String REQ_DATABASE = "database";
    String REQ_EDITOR = "editor";
    String REQ_SELECTED_JSLIBS = "selectedJsLibs";
    String REQ_TEMP_SELECTED_JSLIBS = "tempSelectedJsLibs";
    String REQ_SELECTEDMODULES = "selectedModules";
    String REQ_SELECTED_PILOT_PROJ = "pilotProject";
    String REQ_TEMP_SELECTEDMODULES = "selectedFeatures";
    String REQ_TEMP_SELECTED_PILOT_PROJ = "selectedPilotProj";
    String REQ_PILOTS_IDS = "pilotsIds";
    String REQ_ALREADY_SELECTED_MODULES = "alreadySelectedModules";
    String REQ_ALREADY_SELECTED_JSLIBS = "alreadySelectedJsLibs";
    String REQ_ALL_JS_LIBS = "allJsLibs";
    String REQ_PILOTS_NAMES = "pilotsNames";
    String REQ_PILOT_PROJECT_INFO = "pilotProjectInfo";
    String REQ_QUALITY = "quality";
    String REQ_TEST_SUITE = "quality";
    String REQ_ERROR_TESTSUITE = "qualityError";
    String REQ_ERROR_DATA = "dataError";
    String REQ_TEST_RESULT = "testResult";
    String REQ_CONFIGURATION = "configuration";
    String REQ_SETTINGS_TEMPLATES = "SettingsTemplates";
    String REQ_SETTINGS_TEMPLATE = "settingTemplate";
    String REQ_CONFIG_TYPE_OTHER = "Other";
    String REQ_PROPERTIES = "properties";
    String REQ_CONFIG_PROP_KEY = "propertyKey";
    String REQ_SERVER_SETTINGS = "serverSettings";
    String REQ_DATABASE_SETTINGS = "databaseSettings";
    String REQ_EMAIL_SETTINGS = "emailSettings";
    String REQ_WEBSERVICE_SETTINGS = "webServiceSettings";
    String REQ_SERVER_CONFIGS = "serverConfigs";
    String REQ_DATABASE_CONFIGS = "databaseConfigs";
    String REQ_EMAIL_CONFIGS = "emailConfigs";
    String REQ_WEBSERVICE_CONFIGS = "webServiceConfigs";
    String REQ_DEPLOY_DATABASE = "database";
    String REQ_DEPLOY_SERVER = "server";
    String REQ_TEST_AGAINST = "testAgainst";
    String REQ_TEST_BUILD_ID = "buildId";
    String REQ_DEPLOY_READER = "DeployReader";
    String REQ_READER = "Reader";
    String REQ_SONAR_PATH = "SonarPath";
    String REQ_XLSX = "xlsx";
    String REQ_CONFIG_INFO = "configInfo";
    String REQ_PROPERTIES_INFO = "propertiesInfo";
    String REQ_SELECTED_TYPE = "selectedType";
    String REQ_TYPE_VALUES = "typeValues";
    String REQ_TECH_OPTIONS = "options";
    String REQ_CONFIG_TYPE = "configType";
    String REQ_OLD_NAME = "oldName";
    String REQ_ERROR = "error";
    String REQ_SELECTED_ITEMS = "check";
    String REQ_MANUAL_XLSX = "manualTest";
    String REQ_SELECTED_BUILDS = "selectedBuilds";
    String REQ_SELECTED_BUILDS_LIST = "builds";
    String REQ_SELECTED_JOBS_LIST = "Jobs";
    String REQ_SELECTED_ALL_BUILDS_LIST = "allBuilds";
    String REQ_SETTINGS = "settings";
    String REQ_BUILD = "build";
    String REQ_PROCESS_BUILD = "processBuild";
    String REQ_RELEASE = "release";
    String REQ_JAR = "jar";
    String REQ_SELECTED_APP_TYPE = "selectedAppType";
    String REQ_SETTINGS_INFO = "settingsInfo";
    String REQ_CURRENT_SETTINGS_TEMPLATE = "CurrentTemplate";
    String REQ_ALL_TECHNOLOGIES = "allTechnologies";
    String REQ_SETTINGS_NAME = "settingName";
    String REQ_CODE_PREFIX = "codePrefix";
    String REQ_FROM_PAGE = "fromPage";
    String REQ_THEME_FILES = "themeList";
    String REQ_THEME_FILES_MAP = "themeListMap";
    String REQ_THEME_PATH_FROM_POM = "defaultThemeBuilderPath";
    String REQ_CSS_JSON = "cssJson";
    String REQ_AGAINST_JAR = "funcTestAgaistJar";
    String REQ_SETTINGS_PATH = "settingsPath";
    String REQ_CONFIG_PATH = "configPath";
    String REQ_APPLICATION_TYPE = "applicationType";
    String REQ_OLD_CONFIG_NAME = "configName";
	String REQ_DEPLOY_DIR = "deployDir";
    String REQ_TEST_TYPE = "testType";
    String REQ_REPORT_NAME = "reportName";
    String REQ_SONAR_URL = "sonarUrl";
	String REQ_REPORT_TYPE = "reportType";
    String REQ_ACTION_TYPE = "actionType";
    String REQ_TEST_EXE = "testExecuted";
	String CHECK_REPORT_AVAILABILITY = "checkReportAvailability";
    String REQ_REPORT_STATUS = "reportStatus";
    String REQ_REPORT_DELETE_STATUS = "reportDeleteStatus";
    String REQ_SELECTEDPROJECTS = "selectedProjects";
    String REQ_BUILD_NUMBER = "build-number";
    String REQ_BUILD_WARNING = "buildWarning";
    String REQ_DEPLOY_BUILD_NUMBER = "buildNumber";
    String REQ_ANDROID_PROFILE_DET = "androidProfileDetails";
    String REQ_ANDROID_HAS_SIGNING = "hasSigning";
    String REQ_DEPLOY_IPHONE_SIMULATOR_VERSION= "simulatorVersion";
    String REQ_HIDE_DEPLOY_TO_SIMULATOR = "deployToSimulatorStatus";
	String REQ_HIDE_DEPLOY_TO_DEVICE = "deployToDeviceStatus";
    String REQ_VIDEO = "video";
    String REQ_VIDEO_INFOS = "videoInfos";
    String REQ_VIDEO_TYPES = "videoTypes";
    String REQ_SERVER_URL = "serverUrl";
    String REQ_TEST_BROWSER = "browser";
    String REQ_TEST_TYPE_SELECTED = "testTypeSelected";
    String REQ_TEST_UNIT = "unit";
    String REQ_PROJECT_MODULES = "projectModules";
    String REQ_UNIT_TEST_REPORT_OPTIONS = "unitTestReportOptions";
    String REQ_COMPONENT_TEST_REPORT_OPTIONS = "comoponentTestReportOptions";
    String REQ_TEST_FUNCTIONAL = "functional";
    String REQ_TEST_PERFORMANCE = "performance";
    String PERFORMANCE_TEST_REPORTS = "performanceTestReports";
	String PERFORMANCE_SPECIAL_HANDLE = "performanceSpecialHandle";
	String LOAD_TEST_RESULTS = "loadTestResults";
	String LOAD_TEST_REPORTS = "loadTestReports";
	String FUNCTIONAL_TEST_REPORTS = "functionalTestReports";
	String UNIT_TEST_REPORTS = "unitTestReports";
	String FUNCTIONAL_SURE_FIRE_REPORTS = "functionalSureFireReports";
	String JMETER_TEST_RESULTS_FOR_ANDROID = "jmeterTestResultsForAndroid";
	String JMETER_TEST_RESULTS = "jmeterTestResults";
    String REQ_TEST_LOAD = "load";
    String REQ_SHOW_SETTINGS = "showSettings";
    String REQ_TEST_BUILD_INFOS = "buildInfos";
    String REQ_TEST_SERVERS = "servers";
    String REQ_TESTSUITE_NAME = "TestSuite";
    String REQ_TESTCASES = "TestCases";
    String REQ_TESTSUITE_FAILURES = "Failures";
    String REQ_TESTSUITE_ERRORS = "Errs";
    String REQ_TESTSUITE_TESTS = "Tests";
    String REQ_ALL_TESTSUITE_MAP = "AllTestsMap";
    String REQ_TESTSUITES_CALL = "SettingInfoAndTestSuite";
    String REQ_LOGIN_ERROR = "loginErrors";
    String REQ_CORE_MODULES = "coreModules";
    String REQ_CUSTOM_MODULES = "customModules";
    String REQ_APPTYPE = "appType";
    String REQ_JMETER_REPORT_FILES = "JMeterReports";
    String REQ_PDF_REPORT_FILES = "pdfReportFiles";
    String REQ_ANDROID_DEVICE = "device";
    String REQ_IPHONE_DEVICE = "device";
    String REQ_IPHONE_SIMULATOR = "simulator";
    String REQ_ANDROID_DEFAULT = "default";
    String REQ_FROM_TAB = "fromTab";
    String REQ_FROM_TAB_TEST = "Test";
    String REQ_FROM_TAB_DEPLOY = "Deploy";
    String REQ_TEST_BROWSERS = "browsers";
    String REQ_PILOT_JSLIBS = "dependentJsLibraries";
    String REQ_TEST_SHOW_THROUGHPUT_GRAPH = "throughPut";
    String REQ_TEST_SHOW_RESPONSE_TIME_GRAPH = "responseTime";
    String REQ_TEST_SHOW_MIN_RESPONSE_GRAPH = "minResponseTime";
    String REQ_TEST_SHOW_MAX_RESPONSE_GRAPH = "maxResponseTime";
    String REQ_GRAPH_DATA = "GRAPH_DATA";
    String REQ_GRAPH_LABEL = "GRAPH_LABEL";
    String REQ_GRAPH_ALL_DATA = "GRAPH_ALL_DATA";
    String REQ_SHOW_GRAPH = "showGraphFor";
    String REQ_TEST_SHOW_ALL_GRAPH = "all";
    String REQ_ACTION= "action";
    String REQ_COMMITABLE_FILES= "commitableFiles";
    String REQ_GIT_COMMITABLE_FILES= "commitableGITFiles";
    String REQ_BUILD_TEST= "build";
    String REQ_DEPLOY = "deploy";
    String REQ_OLD_APPDIR = "oldAppDirName";
    String REQ_ANDROID_CONN_DEVICES = "connAndroidDevices";
    String REQ_SERVERS = "servers";
    String REQ_DATABASES = "databases";
    String REQ_WEBSERVICES = "webservices";
    String REQ_EMAIL_SUPPORTED = "emailSupported";
    String REQ_BUILD_STATUS = "buildStatus";
    String REQ_GENERATE_BUILD = "generateBuild";
    String REQ_ENV_SERVER_SETTINGS = "serverSettings";
    String REQ_ENV_DATABASE_SETTINGS = "databaseSettings";
    String REQ_ENV_EMAIL_SETTINGS = "emailSettings";
    String REQ_ENV_WEBSERVICE_SETTINGS = "webServiceSettings";
    String REQ_APPLIES_TO = "appliesto";
    String REQ_SHOW_WELCOME = "showWelcome";
    String SESSION_USER_INFO = "userInfo";
    String SESSION_USER_PASSWORD = "password";
    String REQ_TEST_RESULT_FILE = "testResultFile";
	String REQ_TOTAL_BUILDS_SIZE = "totalBuildsSize";
	String REQ_CURRENTENV = "currentEnv"; 
	String REQ_TEMPLIST_SELECTEDSERVERS = "tempListSelectedServers";
	String REQ_TEMPLIST_SELECTEDDATABASES  = "tempListSelectedDatabases";
    String REQ_LISTSELECTED_SERVERIDS  = "listSelectedServerIds";
    String REQ_LISTSELECTED_DATABASEIDS = "listSelectedDatabaseIds";
    String REQ_LISTSELECTED_VERSIONS = "listSelectedVersions";
    String REQ_HEADER = "header";
    String REQ_HEADER_TYPE = "headerType";
    String REQ_FROM	= "from";
    String REQ_REQUEST_IP = "requestIp";
    String REQ_GOAL = "goal";
    String REQ_PHASE = "phase";
	String REQ_SERVICE_MANAGER = "serviceManager";
    String REQ_MOJO = "mojo";
    String REQ_ATTRNAME	= "attrName";
    String SETTINGS_PARAMS = "SETTINGS_PARAMS";
    String REQ_KEY_SUCCESS_EMAILS = "successEmails";
    String REQ_KEY_FAILURE_EMAILS = "failureEmails";
    String REQ_SERVER_LOG = "serverLog";
    String REQ_LOG_FILE_EXISTS = "logFilexists";
    String REQ_REPORT_CRISP="crisp";
    String REQ_REPORT_DETAILED="detail";
    String REQ_REPORTS_DATA_TYPE = "reportsDataType";
	String REQ_TECH_NAME = "techName";
	String REQ_PROJECT_NAME = "projectName";
    
	String REQ_CONFLUENCE_SITES = "ConfluenceSites";
    String REQ_EXISTING_JOB = "existingJob";
    String REQ_EXISTING_CLONNED_JOBS = "existingClonedJobs";
    String REQ_EXISTING_JOBS = "existingJobs";
    String REQ_EXISTING_JOBS_NAMES = "existingJobsNames";
    String REQ_CRON_BY = "cronBy";
    String REQ_HOURS = "hours";
    String REQ_MINUTES = "minutes";
    String REQ_MONTH = "month";
    String REQ_DAY = "day";
    String REQ_SCHEDULE_EVERY = "every";
    String REQ_CRON_BY_DAILY = "Daily";
    String REQ_CRON_BY_WEEKLY = "Weekly";
    String REQ_CRON_BY_MONTHLY = "Monthly";
    String REQ_CRON_BY_WEEK = "week";
    
    String REQ_CRON_EXPRESSION = "cronExpressions";
    String REQ_CRON_DATES = "cronDates";
    String REQ_JOB_NAME = "jobName";
    String REQ_SERVER_STATUS_URL = "url";
    String REQ_VERSION_INFO = "version";
    String REQ_CI_BUILD_PROGRESS = "buildInProgress";
    String REQ_SENDER_EMAILID = "senderEmailId";
    String REQ_SENDER_EMAIL_PASSWORD = "senderEmailPassword";
    
    String REQ_CURRENT_VERSION = "currentVersion";
    String REQ_LATEST_VERSION = "latestVersion";
    String REQ_UPDATED_MESSAGE = "updatedMessage";
    String REQ_JFORUM_URL = "JforumUrl";
	String REQ_USER_NAME = "username";
	String REQ_PASSWORD = "password";
	
	String REQ_TEST_RESULT_FILE_NAMES = "testResultFileNames";
	String REQ_SELECTED_TEST_RESULT_FILE = "selectedTestResultFileName";
	String REQ_CORE_MODULE = "core";
	String REQ_CUSTOM_MODULE = "custom";
	String REQ_JSLIB_MODULE = "jsLib";
	
	String REQ_IMPORT_SQL ="importSqlPro";
	String REQ_TITLE_EXCEPTION = "Exception";
	String REQ_TITLE_ERROR = "Error";	
	String REQ_START_NODE_JS = "StartNodeJS";
	String REQ_READ_LOG_FILE = "readLogFile";
	String REQ_JAVA_READ_LOG_FILE = "javaReadLogFile";
	String REQ_JAVA_START = "javaStart";
	String REQ_JAVA_STOP = "javaStop";
	String REQ_START = "Start";
	String REQ_STOP = "Stop";
	String REQ_RE_START = "reStart";
	
	String START_NODE = "startNode";
	String START_HUB = "startHub";
	String STOP_NODE = "stopNode";
    String STOP_HUB = "stopHub";
	
	String REQ_EXTERNAL_FEATURES = "External Features";
	String REQ_CUSTOM_FEATURES = "Custom Features";
	String REQ_JS_LIBS = "JS Libraries";
	String REQ_FEATURES_FIRST_MDL_CAT = "firstModuleCat";
	String REQ_FEATURES_SECOND_MDL_CAT = "secondModuleCat";
	String REQ_FEATURES_LEFT_MODULES = "leftModules";
	String REQ_FEATURES_RIGHT_MODULES = "rightModules";
	String REQ_FEATURES_MODULE_CAT = "moduleCat";
	String REQ_FEATURES_MODULES = "modules";
	String REQ_CONFIG = "config";
	String REQ_VERSION = "version";
	String REQ_ENVIRONMENTS = "environments";
	String REQ_CONFIGURATIONS = "configurations";
	String REQ_SCHEDULER = "Scheduler";
	String REQ_SCHEDULER_KEY = "schedulerKey";
    String REQ_LOG_REPORT = "logReport";
    String REQ_PROJECT_INFO_SERVERS = "projectInfoServers";
    String REQ_PROJECT_INFO_DATABASES = "projectInfoDatabases";
    String REQ_FEATURE_NAME = "featureName";
    String REQ_FEATURE_NAMES = "featureNames";
    String REQ_HAS_CUSTOM_PROPERTY = "hasCustomProperty";
    String REQ_HAS_FUNCTIONAL_LOG_FILE = "hasFunctionalLogFile";
    String REQ_FILE_NAME = "fileName";
    
    String CONFIG_FEATURES = "config_Features";
    String CONFIG_COMPONENTS = "config_Components";
	
	String REQ_TESTCASE_NAME = "testCaseName";
	String SCREENSHOT_DIR = "screenshots";
	String IMG_PNG_TYPE = "png";
	String CONTENT_TYPE = "image/png";
	String CONTENT_DISPOSITION = "attachment";
	String DOT = ".";
	String SERIAL_NUMBER = "serialNumber";
	String REQ_XCODE_CONFIGS = "xCodeConfigs";
	String REQ_IPHONE_SDKS = "iphoneSdks";
	String REQ_IPHONE_SIMULATOR_SDKS = "iphoneSimulatorSdks";
	String MAC_OS_SDKS = "macosx";
	String MAC_DEVICE_SDKS = "iphoneos";
	String MAC_SIMULATOR_SDKS = "iphonesimulator";
	
	String REQ_SERVER_DOWNLOAD_INFO = "serverDownloadInfos";
	String REQ_DB_DOWNLOAD_INFO = "dbDownloadInfos";
	String REQ_EDITOR_DOWNLOAD_INFO = "editorDownloadInfos";
	String REQ_TOOLS_DOWNLOAD_INFO = "toolsDownloadInfos";
	String REQ_OTHERS_DOWNLOAD_INFO = "othersDownloadInfos";
	
	String REQ_CONFIG_SERVER_NAMES = "configServerNames";
	String REQ_CONFIG_DB_NAMES = "configDbNames";
	String REQ_SRC = "src";
	
	String REQ_LOCAL_DISK = "Local Disk";
	
    /*
     * REST url
     */
    String PHRESCO_SERVER_URL = "phresco.server.url";
    String PHRESCO_CODE_PREFIX = "phresco.project.code";
    String PHRESCO_FUNCTIONAL_TESTSUITE_PATH = "phresco.quality.functional.testsuite.path";
    String PHRESCO_REPORTS_PATH = "phresco.quality.reports.path";
    String PHRESCO_CACHE_ENABLED = "phresco.framework.cache.enabled";
    String PHRESCO_SONAR_REPORT_PATH = "phresco.code.sonar.reports.path";
    String PHRESCO_SONAR_URL = "phresco.code.sonar.url";
    String PHRESCO_CI_JENKINS_URL = "phresco.ci.jenkins.url";
    String PHRESCO_JFORUM_URL = "phresco.JForum.url";
    
    String REST_APPS_PATH = "/apps";
    String REST_REPORTS = "/reports";
    String REST_APPS_UPDATE_PATH = "/apps/update";
    String REST_APPS_UPDATEPOM_PATH= "/apps/updatepom";
    String REST_APPS_UPDATEDOC_PATH = "/apps/updatedocs";
    String REST_DOWNLOADS_PATH = "/downloads";
    String REST_PILOT_PATH = "/pilots";
    String REST_SETTINGS_PATH = "/settings";
    String REST_VIDEOS_PATH = "/homepagevideos";
    String REST_LOGIN_PATH = "/login";
    String REST_CI_CONFIG_PATH = "/repo/ci/config";
    String REST_CI_SVN_PATH = "/repo/ci/svn";
    String REST_LOG_PATH = "/admin/log";
    String REST_ADMIN_CONFIG_PATH = "/admin/config";
    String REST_CREDENTIAL_PATH = "/repo/ci/credentialsxml";
    String REST_CI_JDK_HOME = "/repo/ci/javahomexml";
    String REST_CI_MAVEN_HOME = "/repo/ci/mavenhomexml";
    String REST_CI_MAILER_HOME = "/repo/ci/mailxml";
    String REST_CI_MAIL_PLUGIN = "/repo/ci/emailext";
    String REST_ENVE_PATH = "/settings/env";
    String REST_SERVERS_PATH = "/component/servers";
    String REST_DATABASE_PATH = "/component/databases";
    String REST_UPDATE = "/repo/update";

    String ATTR_TIME 	= "time";
    String ATTR_TESTS 	= "tests";
    String ATTR_NAME 	= "name";
    String ATTR_FILE = "file";
    String ATTR_FAILURES = "failures";
    String ATTR_ERRORS = "errors";
    String ATTR_ASSERTIONS = "assertions";
    String ATTR_CLASS = "class";
    String ATTR_CLASSNAME = "classname";
    String ATTR_LINE = "line";
    String ATTR_TYPE = "type";
    String ATTR_RESULT = "result";
    String ATTR_JM_THREAD_NAME = "tn";
    String ATTR_JM_LABEL = "lb";
    String ATTR_JM_SUCCESS_FLAG = "s";
    String ATTR_JM_TIMESTAMP = "ts";
    String ATTR_JM_LATENCY_TIME = "lt";
    String ATTR_JM_TIME = "t";
    String ATTR_JM_BYTES = "by";
    String ATTR_JM_RESPONSE_CODE = "rc";
    String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
    String ATTR_ID = "id";
    
    String ELEMENT_FAILURE = "failure";
    String ELEMENT_ERROR = "error";

    String NAME_FILTER_PREFIX = "[@name='";
    String NAME_FILTER_SUFIX = "']";
    String XPATH_SINGLE_TESTSUITE = "/testsuites/testsuite";
    String XPATH_MULTIPLE_TESTSUITE = "/testsuites/testsuite/testsuite";
    String XPATH_TESTCASE = "/testcase";
    String XPATH_TEST_RESULT = "/testResults/*";
    String XPATH_TESTSUTE_TESTCASE = "/testsuite/testcase";
    
    /*
     *
     */
    String ADMIN_FIELD_PASSWORD = "admin_password";
    String PASSWORD = "password";

    /*
     * Message keys
     */
    String MSG_PROJECT_INFORMATION_EMPTY = "Project information should not be empty";
    String MSG_FILE_PATH_EMPTY = "File path should not be empty";
    String SUCCESS_REPORT_STATUS = "label.report.success";
    String SUCCESS_SEND_ERROR_REPORT = "Report Submitted Successfully";
    String ERROR_REPORT_STATUS = "label.report.failure";
    String ERROR_REPORT_MISSISNG_FONT = "label.report.failure.missing.font";
    String ERROR_REPORT_MISSISNG_FONT_MSG = "label.report.failure.missiong.font.msg";
    String SUCCESS_REPORT_DELETE_STATUS = "label.report.delete.success";
    String ERROR_REPORT_DELETE_STATUS = "label.report.delete.failure";
    String MSG_REPORT_OVERALL = "Overall";
    String MSG_REPORT_DETAIL = "Detailed";
    String MSG_REPORT = "Atleast one test result is necessary";
    String EMPTY_PROJECT_CODE = "project.code.empty";
    
    /*
     * CI Message keys
     */
    String CI_BUILD_STARTED = "Build started in jenkins";
    String CI_BUILD_STARTING_ERROR = "Error while starting build in jenkins";
    
    String CI_SAVE_UPDATE_FAILED = "Save Or Updation failed.";
    String CI_EMAIL_SAVE_UPDATE_FAILED = "Email configuration failed.";
    String CI_BUILD_FAILED = "Build triggered failed.";
    String CI_ONE_JOB_REQUIRED = "Select only one job at a time for configuration";
    String CI_BUILD_LOADED_SHORTLY = "Builds will be loaded shortly";
    String CI_NO_JOBS_AVAILABLE = "No Job (s) Available";
    /*
     * Create Project keys
     */
    String SUCCESS_PROJECT = "Project {0} created successfully";
    String UPDATE_PROJECT = "Project {0} updated successfully";
    String FAILURE_PROJECT = "Project {0} creation failed";
    String IMPORT_SUCCESS_PROJECT = "Project imported successfully";
    String AUTHORIZATION = "Authorization";
    String BASIC_SPACE = "Basic ";
    String BUILD_WARNING_MESSAGE = "Build might take few minutes to generate.Do not do any action or refresh";     
    /*
     * Delete Project keys
     */
    String SUCCESS_PROJECT_DELETE = "Projects deleted successfully";
    String SUCCESS_PROJECT_UPDATE = "update.project.success";
    String SUCCESS_PROJECT_ADD = "add.project.success";
    String SUCCESS_PROJECT_COMMIT = "commit.project.success";
    String FAILURE_PROJECT_DELETE = "Projects deletion failed";
    String SUCCESS_APPLICATION_UPDATE = "update.application.success";
    /*
     * Project Import keys
     */
    String IMPORT_PROJECT_FAIL = "import.project.fail";
    String UPDATE_PROJECT_FAIL = "update.project.fail";
    String INVALID_CREDENTIALS = "import.invalid.credential";
    String APPLN_INVALID_CREDENTIALS = "import.appln.invalid.credential";
    String INVALID_FOLDER = "import.invalid.folder";
    String PROJECT_ALREADY = "import.project.already";
    String SVN_FAILED = "failed";
    String SVN_INTERNAL = "Internal";
    String SVN_IS_NOT_WORKING_COPY = "is not a working copy";
    String INVALID_URL = "import.invalid.url";
    String INVALID_REVISION = "import.invalid.revision";
    String NOT_WORKING_COPY = "not.working.dir";
    String NO_POM_XML = "project.pom.not.exist";
    String POM_URL_FAIL = "project.pomurlupdate.fail";
    
    /*
     * Project add keys
     */
    String ADD_PROJECT_SUCCESS = "add.project.success";
    String ADD_PROJECT_FAIL = "add.project.fail";
    String COMMIT_PROJECT_SUCCESS = "commit.project.success";
    String COMMIT_PROJECT_FAIL = "commit.project.fail";
    
    /*
     * Delete Build keys
     */
    String SUCCESS_BUILD_DELETE = "build.delete.success";
    String FAILURE_BUILD_DELETE = "build.delete.fail";
    String PROFILE_CREATE_SUCCESS = "profile.create.success";
    String PROFILE_UPDATE_SUCCESS = "profile.update.success";
    String PROFILE_CREATE_ERROR = "profile.create.error";
    String PROFILE_UPDATE_ERROR = "profile.update.error";
    String PROFILE_CREATE_MSG = "Create a profile";
    
    /*
     * Code keys
     */
    String FAILURE_CODE_REVIEW = "Code validation report is not available";
	String SONAR_NOT_STARTED = "sonar.not.started";
	String REQ_TECH_REPORTS = "techReports";
	String REQ_PARAMETER = "parameter";
	String REQ_VALUES = "values";
	String REQ_VALIDATE_AGAINST = "validateAgainst";
	String CHECK_IPHONE = "checkIphone";
	String CLANG_REPORT = "clangReport";
	String REQ_VALIDATE_AGAINST_VALUES = "validateAgainstValues";
	String REQ_SOURCE_VALUES = "sourceValues";
    
    /*
     * Application Redirection keys
     */
    String APPLICATION_PROJECT = "project";

    /*
     * Application Environment keys
     */
    String PATH_APPLICATION_ENVIRONMENT_DEPLOY = "deploy";
    /*
     * Create Configuration keys
     */
    String SUCCESS_CONFIGURATION = "Configuration {0} created successfully";
    String FAILURE_CONFIGURATION = "Configuration {0} creation failed";
    String CONFIG_ALREADY_EXIST = "Configuration type already created for this environment";
    String CONFIGURATION_CLONNING_FAILED = "Configuration clonning failed";
    String SETTINGS_ALREADY_EXIST = "Settings type already created for this environment";
    String NO_CONFIG_TYPE = "configuration.type.info";
    
    String ENV_SET_AS_DEFAULT_SUCCESS="environment.set.as.default.success";
    String ENV_SET_AS_DEFAULT_ERROR="environment.set.as.default.error";
    String SELECT_ENV_TO_SET_AS_DEFAULT="environment.select.set.as.default";
    String ENV_NOT_VALID="environment.not.valid";
    String ENV_CLONE_SUCCESS="environment.clone.success";
    String CLONE_FROM_CONFIG_TYPE = "cloneFromConfigType";
    String CLONE_FROM_CONFIG_DESC = "currentConfigDesc";
	String CLONE_FROM_ENV_NAME = "cloneFromEnvName";
	String CLONE_FROM_CONFIG_NAME = "cloneFromConfigName";
	String TECH_SITE_CORE = "tech-sitecore";
	String ADD_SETTINGS = "addsettings";
	String EDIT_SETTINGS = "editsettings";
	String ADD_CONFIG = "addconfig";
	String EDIT_CONFIG = "editconfig";
    
    /*
     * Create Environment keys
     */
    String CREATE_SUCCESS_ENVIRONMENT = "environment.create.success";
    String CREATE_FAILURE_ENVIRONMENT = "environment.create.fail";
    
    /*
     * Update Environment keys
     */
    String UPDATE_ENVIRONMENT = "environment.update.success";
    
    /*
     * Delete Environment keys
     */
    String DELETE_ENVIRONMENT = "environment.deleted.success";
    /*
     * Create Setting keys
     */
    String SUCCESS_SERVER = "Server {0} created successfully";
    String SUCCESS_DATABASE = "Database {0} created successfully";
    String SUCCESS_WEBSERVICE = "Webservice {0} created successfully";
    String SUCCESS_EMAIL = "Email {0} created successfully";
    String FAILURE_SETTING = "Setting {0} creation failed";
   
    /*
     * Check Box Info keys
     */
    String HIDE_LOG_MSG ="Only Error Messages will be displayed";
    String EXEC_SQL_MSG ="Existing DB with the same name will be overwritten";
    String SELECT_DB ="Select atleast one DB Sql File for Execution";
	String CONFIGURATION_UNAVAILABLE ="Configuration not available";
    
    /*
     * Quality Info keys
     */
    String MSG_IPHONE_DEPLOY ="Project will be deployed to device, which is connected first.";
    
    /*
     * login keys
     */
    String ERROR_LOGIN = "login.error.message";
    String ERROR_LOGIN_INVALID_USERNAME = "login.error.username";
    String ERROR_LOGIN_INVALID_PASSWORD = "login.error.password";
    String ERROR_LOGIN_ACCESS_DENIED = "login.error.access.denied.message";
    String ERROR_LOGIN_INVALID_USER = "login.error.invalid.user";
    String ERROR_EXCEPTION = "login.exception.message";
    String SUCCESS_LOGOUT = "logout.success.message";
    String SESSION_EXPIRED = "session.expired.message";

    /*
     * login keys
     */
    String WELCOME_SHOW = "welcome.show";


    /*
     * Delete Setting keys
     */
    String SUCCESS_SETTING_DELETE = "Settings deleted successfully";
    String SUCCESS_SETTING_CREATE = "setting.create.success";
    String FAILURE_SETTING_DELETE = "Settings deletion failed";
    String SUCCESS_SETTING_UPDATE = "setting.update.success";

    String SERVER_UPDATE_SUCCESS = "server.update.success";
    String DATABASE_UPDATE_SUCCESS = "database.update.success";
    String WEBSERVICE_UPDATE_SUCCESS = "webservice.update.success";
    String EMAIL_UPDATE_SUCCESS = "email.update.success";
    
    /*
     * Delete Configuration keys
     */
    String SUCCESS_CONFIG_DELETE = "config.delete.success";
    String FAILURE_CONFIG_DELETE = "config.delete.fail";

    String ERROR_ENV_DUPLICATE = "Environment {0} already exists";
    String ERROR_DUPLICATE_ENV_IN_SETTINGS = "Environment {0} already exists in global settings";
    String ERROR_NO_CONFIG = "environment.config.not.available";
    String ERROR_NAME = "err.msg.empty.name";
    String ERROR_INVALID_NAME = "err.msg.invalid.name";
    String ERROR_THEME_PATH_MISSING = "err.msg.empty.path";
    String ERROR_THEME_EXISTS = "err.msg.theme.name.exists";
    String PROP_TEMP_MISSING = " is missing";
    String ERROR_APP_DIR_EXISTS = "err.msg.app.dir.exists";
    String ERROR_NAME_EXISTS = "err.msg.project.name.exists";
    String ERROR_CODE_EXISTS = "err.msg.project.code.exists";
    String ERROR_APP_CODE_EXISTS = "err.msg.app.code.exists";
    String ERROR_APP_CODE_MISSING ="err.msg.app.code.missing";
    String ERROR_CODE = "err.msg.empty.code";
    String ERROR_EMAIL_ID = "err.msg.invalid.email";
    String ERROR_EMAIL_ID_EMPTY = "err.msg.empty.email";
    String ERROR_VERSION = "err.msg.empty.version";
    String ERROR_SERV_VER_MISSING = "err.serv.ver.missing";
    String ERROR_SERV_MISSING = "err.serv.missing";
    String ERROR_DB_VER_MISSING = "err.db.ver.missing";
    String ERROR_DB_MISSING = "err.db.missing";
    String ERROR_WS_MISSING = "err.ws.missing";
    String ERROR_FUNCTIONAL_FRAMEWORK = "err.functional.framework";
	String ERROR_TYPE = "err.msg.empty.type";
    String ERROR_LAYER = "err.msg.empty.layer";
    String ERROR_TECHNOLOGY = "err.msg.empty.technology";
    String ERROR_SELECT_TECHNOLOGY ="err.msg.select.technology";
    String ERROR_ENV = "err.msg.empty.environment";
    String ERROR_CONFIG_TYPE = "err.msg.empty.config.type";
    String ERROR_APPLIES_TO = "err.msg.empty.applies.to";
    String ERROR_CONFIG_SITE_NAME = "err.msg.empty.config.site.name";
    String ERROR_CONFIG_APP_NAME = "err.msg.empty.config.app.name";
    String ERROR_CONFIG_VERSION = "err.msg.empty.config.version";
    String ERROR_SITE_CORE_PATH_MISSING = "err.msg.empty.site.core.path";
    String CLONE_CONFIG_STATUS = "cloneConfigStatus";
    String ERROR_ENV_REMOVE = "Environment {0} is already in use";
    String ERROR_ENVS_REMOVE = "Environment(s) {0} are already in use";
    String ERROR_DUPLICATE_NAME = "Name already exists";
    String ERROR_DUPLICATE_NAME_IN_SETTINGS = "Name already exists in global settings";
    String ERROR_PRODUCTION_EXISTS_IN_CONFIGURATIONS= "Production is application specific environment";
    String ERROR_DUPLICATE_NAME_IN_CONFIGURATIONS = "Name already exists in configurations of the {0} project";
    String ERROR_SELECT = "Select database and server";
    String ERROR_TEST_SUITE = "Test result is not available for this project";
    String ERROR_PARSE_EXCEPTION = "quality.xml.parse.error";
    String ERROR_TEST_CASE = "Test case is not available for this project";
    String ERROR_MANDATORY_FIELDS = "Some mandatory values are not filled";
    String ERROR_SETTINGS = "ERROR_SETTINGS";
    String ERROR_UNIT_TEST = "unittest.not.executed";
    String ERROR_COMPONENT_TEST = "componenttest.not.executed";
    String ERROR_FUNCTIONAL_TEST = "functionaltest.not.executed";
    String ERROR_PERFORMANCE_TEST = "performancetest.not.executed";
    String ERROR_LOAD_TEST = "loadtest.not.executed";
    String ERROR_ENV_CONFIG = "{0} configuration is not available for the selected environment";
    String ERROR_ANDROID_DATA = "Data is not available";
    String ERROR_PORT = "Invalid Port Number";
    String ERROR_EMAIL = "Enter Valid Email";
    String NO_SETTINGS_ENV = "settings.error.message";
    String ERROR_TESTCASE_ID_EXISTS = "err.msg.testCase.id.exists";
    String ERROR_TEST_SCENARIO_NAME_EXISTS = "err.msg.test.suite.name.exists";
    String ERROR_TESTCASE_ID_MISSING = "err.testCase.id.missing";
    String ERROR_TEST_SCENARIO_NAME_MISSING = "err.test.suite.name.missing";
	String ERROR_FEATURE_ID_MISSING = "err.feature.id.missing";
    
    /*
     * Delete Environment keys
     */
    String SUCCESS_ENVIRONMENT_DELETE = "environment.delete.success";
    String FAILURE_ENVIRONMENT_DELETE = "environment.delete.fail";
    
    /*
     * Browser keys
     */
    String BROWSER_FIREFOX_VALUE = "Firefox";
    String BROWSER_CHROME_VALUE = "Chrome";
    String BROWSER_INTERNET_EXPLORER_VALUE = "Internet Explorer";
    String BROWSER_SAFARI_VALUE = "Safari";
    String BROWSER_OPERA_VALUE = "Opera";
    
    /*
     * Browser keys for windows
     */
    String WIN_BROWSER_FIREFOX_KEY = "firefox";
    String WIN_BROWSER_CHROME_KEY = "googlechrome";
    String WIN_BROWSER_INTERNET_EXPLORER_KEY = "iexplore";
    String WIN_BROWSER_SAFARI_KEY = "safari";
    String WIN_BROWSER_OPERA_KEY = "opera";
    // web driver ie key
    String WIN_BROWSER_WEB_DRIVER_INTERNET_EXPLORER_KEY = "internet explorer";
    
    /*
     * Browser keys for Mac
     */
    String MAC_BROWSER_FIREFOX_KEY = "firefox";
    String MAC_BROWSER_CHROME_KEY = "googlechrome";
    String MAC_BROWSER_SAFARI_KEY = "safari";
    String MAC_BROWSER_OPERA_KEY = "opera";

    /*
     * Browser keys for Linux
     */
    String LINUX_BROWSER_FIREFOX_KEY = "firefox";
    String LINUX_BROWSER_CHROME_KEY = "googlechrome";
    String LINUX_BROWSER_SAFARI_KEY = "safari";
    String LINUX_BROWSER_OPERA_KEY = "opera";
    
    String PROJECT_VERSION = "1.0.0";
	String VERSION = "version";
	String ARTIFACT_ID = "artifactId";
	String FRAMEWORK_GROUP_ID = "com.photon.phresco.framework";
	String FRAMEWORK_ARTIFACT_ID = "phresco-framework-web";
	String DEPENDENCY = "dependency";
	String DEPENDENCIES = "dependencies";
	String POM_FILE = "pom.xml";
	String PROPERTIES = "properties";
	String PROPERTY_VERSION = "phresco.framework.version";
    
    /*
     * Quality report property keys
     */
    String TEST_SLASH_PERFORMANCE = "/test/performance/";
    String RESULTS_SLASH_JMETER = "/results/jmeter/";
    String SLASH_JSON = "/json";
    
    /* CI Keys */
    String SUCCESS_JOB = "ci.create.success";
    String FAILURE_JOB = "ci.create.failure";
    String SUCCESS_BUILD = "ci.build.success";
    String FAILURE_BUILD = "ci.build.failure";
    String SUCCESS_UPDATE = "ci.update.success";
    String FAILURE_UPDATE = "ci.update.failure";
    String CI_MAIL_CONFIGURE_SUCCESS = "ci.mail.configure.success";
    String CI_CONFLUENCE_CONFIGURE_SUCCESS = "ci.confluence.configure.success";
    
    /* About Keys */
    String ABOUT_SUCCESS_UPDATE = "abt.update.success";
    String ABOUT_FAILURE_FAILURE = "abt.update.failure";
    
    /* OS Types */
//    String OS_NAME= "os.name";
    String OS_ARCH = "os.arch";
//    String WINDOWS = "Windows";
    String SERVER = "Server";
    String DATABASE = "Database";
    String WINDOWS7 = "Windows 7";
    String MAC = "Mac";
    String LINUX = "Linux";
    String WINDOWS_CHECK = "win";
    String MAC_CHECK = "mac";
    String LINUX_CHECK = "nux";
    String OS_BIT64 = "64";
    String OS_BIT86 = "86";
    
   /* .phresco folder content */
   String ARCHETYPE_METADATA=".phresco/archetype-metadata.xml";
   String EXCLUDEFILE = ".phresco/excludefiles.txt";
   
   /* RBACK Roles */
   String ENGINEER = "Engineer";
   String RELEASE_ENGINEER = "ReleaseEngineer";
   
   String FILE_SEPARATOR = "/";
   
   /* Validation */
   String VALIDATE_FROM = "validateFrom";
   String VALIDATE_FRAMEWORK = "framework";
   String VALIDATE_PROJECT = "project";
   String VALIDATE_IN_BG = "validateInBg";
   String SESSION_FRMK_VLDT_RSLT  = "frmkVldtRslt";
   String SESSION_FRMK_VLDT_STATUS  = "frmkVldtStatus";
   String SESSION_PRJT_VLDT_RSLT  = "_PrjtVldtRslt";
   String SESSION_PRJT_VLDT_STATUS  = "_PrjtVldtStatus";
   
   /* Code validate */
   String CODE_VALIDATE_PARAM = "sonar.branch";
   
   /* Site report */
   String REQ_SITE_REPORT = "siteReport";
   String REQ_SITE_REPORT_PATH = "siteReportPath";
   String SITE_REPORT_DIR = "site";
   String REQ_SITE_REPORTS = "reports";
   String REQ_SITE_SLECTD_REPORTS = "selectedReports";
   String REQ_SITE_SLECTD_REPORTSCATEGORIES = "maven-project-info-reports-plugin";
   String REQ_SITE_SELECTD_CATEGORIES = "selectedCategories";
   String REQ_SONAR_REPORT = "sonarReport";
   
   
   /* Site report success message */
   String SUCCESS_SITE_CONFIGURE = "configured.site.success";
   
   /* Android Key store file */
   String SIGNING_TYPES="keystore";
   
   /* Unit test report directory */
   String UNIT_TEST_QUNIT_REPORT_DIR = "/do_not_checkin/target/surefire-reports/qunit";
   String UNIT_TEST_JASMINE_REPORT_DIR = "/do_not_checkin/target/jasmine";
   
   /* Minification */
   
   String REQ_COMPRESS_NAME = "compressName";
   String REQ_SELECTED_FILES = "selectedJsFiles";
   String REQ_CHECKED_FILE_LIST = "jsMinCheck";
   String REQ_SELECTED_FILE_NAMES = "jsFileName";
   String REQ_MINIFIED_FILES = "minifiedFiles";
   String REQ_JS_MINIFY_MAP = "jsMminifyMap";
   String REQ_CSS_MINIFY_MAP = "cssMminifyMap";
   String REQ_MINIFY = "minify";
   String REQ_MINIFICATION = "minification";
   String POM_SKIP = "skip";
   String POM_SOURCEDIR = "sourceDirectory";
   String POM_SOURCE_DIRECTORY = "src/main/webapp";
   String POM_OUTPUTDIR = "outputDirectory";
   String POM_OUTPUT_DIRECTORY = "${project.basedir}/do_not_checkin/target/compressed";
   String POM_FORCE = "force";
   String POM_JS_WARN = "jswarn";
   String POM_NO_SUFFIX = "nosuffix";
   String POM_VALUE_TRUE = "true";
   String POM_VALUE_FALSE = "false";
   String POM_AGGREGATIONS = "aggregations";
   String POM_AGGREGATION = "aggregation";
   String POM_INPUTDIR = "inputDir";
   String POM_INPUT_DIRECTORY = "${project.basedir}/compressed";
   String POM_INCLUDES ="includes";
   String POM_INCLUDE ="include";
   String POM_EXCLUDES ="excludes";
   String POM_EXCLUDE ="exclude";
   String POM_EXCLUDE_CSS = "**/*.css";
   String POM_EXCLUDE_JS = "**/lib/**/*.js";
   String POM_EXCLUDE_MIN_JS = "**/*-min.js";
   String POM_EXCLUDE_MINIFIED_JS = "**/*.min.js";
   String POM_EXCLUDE_MIN_CSS = "**/*-min.css";
   String POM_EXCLUDE_MINIFIED_CSS = "**/*.min.css";
   String HYPEN_MIN_JS = "-min.js";
   String HYPEN_MIN_DOT = "-min.";
   String DOT_MIN_DOT = ".min.";
   String HYPHEN_MIN = "-min";
   String MINIFY_FILE_LOCATION = "_fileLocation";
   String FILES_TO_MINIFY = "filesToMinify";
   String POM_OUTPUT = "output";
   String MINIFY_OUTPUT_DIRECTORY = "${project.basedir}";
   String MINIFY_FILE_EXT = ".min.js";
   String MINIFY_PLUGIN_GROUPID ="net.alchim31.maven";
   String MINIFY_PLUGIN_ARTFACTID ="yuicompressor-maven-plugin";
	
	 /* Constants For Framework update */
   String TEMP_ZIP_FILE = "temp.zip";
   String MAVEN_SETTINGS_FILE = "../tools/maven/conf/settings.xml";
   String OUTPUT_SETTINGS_DIR = "tools/maven/conf";
   String BACKUP_DIRNAME = "backups";
   String BIN_DIR = "bin";
   String PREV_DIR = "../";
   String UPGRADE_PROP_NAME = "upgrade.properties";
   String ARCHIVE_EXTENSION = ".zip";
   String VERSION_SERVICE_PATH = "version";
   
   /* Constants For Profile update */
   
   String SONAR_LANGUAGE_PROFILE = "sonar.language";
   String SONAR_BRANCH   = "sonar.branch";
   String SONAR_SOURCE = "source"; 
   String FUNCTIONALTEST = "functional";
   String REPORT_ELEMENT_NODE_JS = "ReportElementNodeJs";
   String REPORT_ELEMENT_JS_WEB = "reportElementJsWeb";
   String REPORT_ELEMENT_SRC_FUNC = "reportElementSrcFunc";
   String REPORT_ELEMENT_JAVA_FUNC = "reportElementJavaFunc";
   String REPORT_ELEMENT_SHAREPOINT_SRC_FUNC = "reportElementSharepointSrcFunc";
   String PHRESCO_SOURCE_DIRECTORY = "phresco.source.directory";
   String SONAR_DYNAMIC_ANALYSIS_PROFILE = "sonar.dynamicAnalysis";
   String JS_PATH = "src/main/js";
   String WEBAPP_PATH = "src/main/webapp";
   String JAVA_PATH = "src/main/java";
   String LIB = "**/lib/**";
   String JAVA = "java";
   String WEB = "web";
   String JS = "js";
   String CSS = "css";
   
   
   /* Constants for Sonar properties */ 
	   String SONAR_LANGUAGE = "sonar.language";
	   String SONAR_PHPPMD_SKIP = "sonar.phpPmd.skip";
	   String SONAR_DYNAMIC_ANALYSIS = "sonar.dynamicAnalysis";
	   String SONAR_PHPPMD_SHOULD_RUN = "sonar.phpPmd.shouldRun";
	   String SONAR_PHPCODESNIFFER_SHOULD_RUN = "sonar.phpCodesniffer.shouldRun";
	   String SONAR_PHPCODESNIFFER_SKIP = "sonar.phpCodesniffer.skip";
	   String SONAR_PHPDEPEND_SHOULD_RUN = "sonar.phpDepend.shouldRun";
	   String SONAR_PHPUNIT_COVERAGE_SHOULD_RUN = "sonar.phpUnit.coverage.shouldRun";
	   String SONAR_PHPUNIT_SHOULD_RUN = "sonar.phpUnit.shouldRun";
	   String SONAR_PHPCPD_SHOULD_RUN = "sonar.phpcpd.excludes";
	   String SONAR_PHPCPD_EXCLUDES_SQL = "sonar.phpcpd.excludes";
	   String SONAR_PHPCPD_EXCLUDES_HTML = "sonar.phpcpd.excludes";
	   String SONAR_PHPDEPEND_TIMEOUT = "sonar.phpDepend.timeout";
	   String SONAR_PHPPMD_TIMEOUT = "sonar.phpPmd.timeout";
	   String SONAR_PHPCODESNIFFER_TIMEOUT = "sonar.phpCodesniffer.timeout";
	   String DRUPAL_STANDARD = "sonar.phpCodesniffer.standardArgument";
	   String WORDPRESS_STANDARD = "sonar.phpCodesniffer.standardArgument";
	   String DRUPAL = "Drupal";
	   String DRUPAL_STANDAD_VERSION = "7.8";
	   String WORDPRESS = "WordPress";	    
	   String SONAR_PHASE = "sonar.phase";
	   String LANGUAGE = "php";
	   String PHASE = "validate";
	   String SQL_EXCLUDES = "source/sql";
	   String HTML_EXCLUDES = "source/public_html";
	   String TEST = "test";
	   String FROMPAGE_ALL = "All";
	   
	   /* Constants for plugin properties */ 
	   String SKIP = "skip";
	   String GROUPID = "net.alchim31.maven";
	   String ARTIFACTID = "yuicompressor-maven-plugin";
	   String PLUGIN_VERSION = "1.3.0";
	   String EXECUTIONID = "yuicompressor-default";	   
	   String GOAL = "compress";

	   /* Remote Deployment Server Authentication */
	   String REQ_RMT_DEP_IS_CERT_AVAIL = "isCertAvailable";
	   String REQ_RMT_DEP_FILE_BROWSE_FROM = "fileBrowseFrom";
	   String CONFIGURATION = "configuration";
	   String FILE_TYPE_CRT = "crt";
	   
	   
	   /* WINDOWS CONSTANT */
		  String HELLOWORD_PROJECT_FILE = "HelloWorld.csproj"; 
		  String HELLOWORLD = "HelloWorld";
		  String HELLOWORD_SOLUTIONFILE = "HelloWorld.sln";
		  String PROPERTYGROUP = "PropertyGroup";
		  String ROOTNAMESPACE = "RootNamespace";
		  String ASSEMBLY_NAME = "AssemblyName";
		  String CERTIFACTE_KEY = "PackageCertificateKeyFile";
		  String TEMPORARY_KEY = "_TemporaryKey.pfx";
		  String NONE = "None";
		  String NEWLINE = "\n";
		  String LIBS = "Lib\\";
		  String PROJECT_FILE = ".csproj";
		  String CONTENT = "Content";

		  String SLN_FILE = "Metro.sln";
		  
		  //SettingsTemplate keys 
		  String SETTINGS_TEMP_KEY_APP_NAME = "applicationName";
		  String SETTINGS_TEMP_KEY_SITE_NAME = "siteName";
		  String SETTINGS_TEMP_SITECORE_INST_PATH = "sitecoreInstPath";
		  String DEPLOY_DIR = "deploy_dir";
		  String ADMIN_USERNAME = "admin_username";
		  String ADMIN_PASSWORD = "admin_password";
		  String REMOTE_DEPLOYMENT = "remoteDeployment";
		  String TYPE_VERSION = "version";
		  
		  //SettingsTemplate values 
		  String IIS_SERVER = "IIS";
		  String NODEJS_SERVER = "NodeJs";
		  String NODEJS_MAC_SERVER = "NodeJs Mac";
		  String SHAREPOINT_SERVER= "Sharepoint Server";
		  
	  /* Resolutions */ 
		String _1600_900 = "1600*900";
		String _1440_900 = "1440*900";
		String _1360_768 = "1360*768";
		String _1280_1024 = "1280*1024";
		String _1280_960 = "1280*960";
		String _1280_800 = "1280*800";
		String _1024_768 = "1024*768";
		String _320_480 = "320*480";
		String REQ_RESOLUTIONS = "resolution";
		
		/*****************************
	     * Error Report
	     * I18N Keys Constants
	     * String EXCEPTION_XXX
	     *****************************/ 
		String EXCEPTION_PROJECT_CREATE = "excep.hdr.proj.create";
		String EXCEPTION_PROJECT_LIST = "excep.hdr.proj.list";
		String EXCEPTION_PROJECT_ADD = "excep.hdr.proj.add";
		String EXCEPTION_PROJECT_UPDATE = "excep.hdr.proj.update";
		String EXCEPTION_PROJECT_DELETE = "excep.hdr.proj.delete";
		String EXCEPTION_APPLICATION_EDIT = "excep.hdr.appInfo.edit";
		String EXCEPTION_PROJECT_MOB_TECH_VERSIONS = "excep.hdr.proj.mob.tech.versions";
		String EXCEPTION_DOWNLOADINFOS = "excep.hdr.downloadInfos";
		String EXCEPTION_LOADMENU = "excep.hdr.load.menu";
		String EXCEPTION_TECHNOLOGY = "excep.hdr.tech";
		String EXCEPTION_PROJECT_WEB_LAYER_WIDGETS = "excep.hdr.proj.web.layer.widgets";
		String EXCEPTION_QUALITY_UNIT_LOAD = "excep.hdr.quality.unit.load";
		String EXCEPTION_QUALITY_COMPONENT_LOAD = "excep.hdr.quality.component.load";
		String EXCEPTION_QUALITY_COMPONENT_TESTSUITES = "excep.hdr.quality.component.load.testsuites";
		String EXCEPTION_QUALITY_COMPONENT_PARAMS = "excep.hdr.quality.component.load.params";
		String EXCEPTION_QUALITY_UNIT_TESTSUITES = "excep.hdr.quality.unit.load.testsuites";
		String EXCEPTION_QUALITY_UNIT_RUN = "excep.hdr.quality.unit.run";
		String EXCEPTION_QUALITY_COMPONENT_RUN = "excep.hdr.quality.component.run";
		String EXCEPTION_QUALITY_UNIT_PARAMS = "excep.hdr.quality.unit.load.params";
		String EXCEPTION_QUALITY_FUNCTIONAL_LOAD = "excep.hdr.quality.functional.load";
		String EXCEPTION_QUALITY_FUNCTIONAL_TESTSUITES = "excep.hdr.quality.functional.load.testsuites";
		String EXCEPTION_QUALITY_FUNCTIONAL_PARAMS = "excep.hdr.quality.functional.load.params";
		String EXCEPTION_QUALITY_FUNCTIONAL_RUN = "excep.hdr.quality.functional.run";
		String EXCEPTION_QUALITY_FUNCTIONAL_START_HUB_PARAMS = "excep.hdr.quality.functional.start.hub.params";
		String EXCEPTION_QUALITY_FUNCTIONAL_START_NODE_PARAMS = "excep.hdr.quality.functional.start.node.params";
		String EXCEPTION_QUALITY_FUNCTIONAL_START_HUB = "excep.hdr.quality.functional.start.hub";
		String EXCEPTION_QUALITY_FUNCTIONAL_START_NODE = "excep.hdr.quality.functional.start.node";
		String EXCEPTION_QUALITY_FUNCTIONAL_STOP_NODE = "excep.hdr.quality.functional.stop.node";
		String EXCEPTION_QUALITY_FUNCTIONAL_STOP_HUB = "excep.hdr.quality.functional.stop.hub";
		String EXCEPTION_QUALITY_FUNCTIONAL_HUB_CONNECTION = "excep.hdr.quality.functional.hub.connection";
		String EXCEPTION_QUALITY_FUNCTIONAL_NODE_CONNECTION = "excep.hdr.quality.functional.node.connection";
		String EXCEPTION_QUALITY_FUNCTIONAL_HUB_LOG = "excep.hdr.quality.functional.hub.log";
		String EXCEPTION_QUALITY_FUNCTIONAL_NODE_LOG = "excep.hdr.quality.functional.node.log";
		String EXCEPTION_QUALITY_LOAD_TESTSUITES = "excep.hdr.quality.load.testsuites";
		String EXCEPTION_QUALITY_LOAD_PARAMS = "excep.hdr.quality.load.params";
		String EXCEPTION_QUALITY_LOAD_RUN = "excep.hdr.quality.load.run";
		String EXCEPTION_BUILD_POPUP = "excep.hdr.build.popup";
		String EXCEPTION_PROCESS_BUILD_POPUP = "excep.hdr.processBuild.popup";
		String EXCEPTION_DEPLOY_POPUP = "excep.hdr.deploy.popup";
		String EXCEPTION_BUILD_GENERATE = "excep.hdr.build.generate";
		String EXCEPTION_DEPLOY_GENERATE = "excep.hdr.deploy.generate";
		String EXCEPTION_LOAD_CLASS = "excep.hdr.load.class";
		String EXCEPTION_BUILDS_LIST = "excep.hdr.builds.list";
		String EXCEPTIN_BUILD_DEPENDANT_VALUE = "excep.hdr.build.depndnt.val";
		String EXCEPTION_QUALITY_LOAD = "excep.hdr.quality.unit";
		String EXCEPTION_REPORT_VIEW_SITE = "excep.hdr.report.view";
		String EXCEPTION_REPORT_GENERATE_SITE_REPORT = "excep.hdr.report.generate";
		String EXCEPTION_REPORT_CONFIGURE = "excep.hdr.report.configure";
		String EXCEPTION_REPORT_CREATE_REPORT_CONFIG = "excep.hdr.report.create.report.config";
		String EXCEPTION_CONFIGURATION_LIST_ENV = "excep.hdr.configuration.list.env";
		String EXCEPTION_CONFIGURATION_READ_ENV_LIST = "excep.hdr.configuration.read.env.list";
		String EXCEPTION_SETTINGS_LIST_ENV = "excep.hdr.settings.list.env";
		String EXCEPTION_CONFIGURATION_FILE_FAIL = "excep.hdr.config.fail.file";
		String EXCEPTION_CONFIGURATION_LIST_CONFIG = "excep.hdr.configuration.list.config";
		String EXCEPTION_CONFIGURATION_ENV_LIST = "excep.hdr.configuration.env.list";
		String EXCEPTION_CONFIGURATION_OPEN_ENV_POPUP = "excep.hdr.configuration.open.env.popup";
		String EXCEPTION_CONFIGURATION_UPDATE_FAILS = "excep.hdr.config.fail.envs";
		String EXCEPTION_CONFIGURATION_ADD = "excep.hdr.config.add";
		String EXCEPTION_CONFIGURATION_SAVE_CONFIG = "excep.hdr.config.save.config";
		String EXCEPTION_CONFIGURATION_SAVE_SETTINGS = "excep.hdr.config.save.settings";
		String EXCEPTION_CONFIGURATION_CREATE_ENVIRONMENT = "excep.hdr.config.create.env";
		String EXCEPTION_SETTINGS_LIST_CONFIG = "excep.hdr.settings.list.config";
		String EXCEPTION_CONFIGURATION_SHOW_PROPERTIES = "excep.hdr.configuration.show.properties";
		String EXCEPTION_CONFIGURATION_DELETE_ENVIRONMENT = "excep.hdr.configuration.delete.environment";
		String EXCEPTION_CONFIGURATION_UPDATE_CONFIG = "excep.hdr.configuration.update.config";
		String EXCEPTION_CONFIGURATION_UPDATE_SETTINGS = "excep.hdr.configuration.update.settings";
		String EXCEPTION_CONFIGURATION_EDIT = "excep.hdr.configuration.edit";
		String EXCEPTION_CONFIGURATION_UPDATE = "excep.hdr.configuration.update";
		String EXCEPTION_RUNAGNSRC_SERVER_START = "excep.hdr.proj.runagnsrc.server.start";
		String EXCEPTION_RUNAGNSRC_SERVER_STOP = "excep.hdr.proj.runagssrc.stop";
		String EXCEPTION_RUNAGNSRC_SERVER_RESTART = "excep.hdr.proj.runagssrc.restart";
		String EXCEPTION_CI_JOB_LIST = "excep.hdr.ci.job.list.failed";
		String EXCEPTION_CI_CONFIGURE_POPUP = "excep.hdr.ci.configure.popup.failed";
		String EXCEPTION_CI_MAIL_CONFIGURE_POPUP = "excep.hdr.ci.mail.configure.popup.failed";
		String EXCEPTION_CI_JOB_DELETION = "excep.hdr.ci.job.deletion.failed";
		String EXCEPTION_CI_BUILD_DELETION = "excep.hdr.ci.build.deletion.failed";
		String EXCEPTION_CI_BUILD_DOWNLOAD_NOT_AVAILABLE = "excep.hdr.ci.build.download.not.available";
		String EXCEPTION_BUILD_DOWNLOAD_NOT_AVAILABLE = "excep.hdr.code.load";
		String EXCEPTION_FEATURE_MANIFEST_NOT_AVAILABLE = "excep.hdr.feature.manifest.not.available";
		String EXCEPTION_FEATURE_CONFIGURATION = "excep.hdr.feature.configuration";
		String EXCEPTION_MANDAOTRY_MSG = "except.mandatory.msg";
		String EXCEPTION_LOG_FILE_DOWNLOAD_NOT_AVAILABLE = "excep.hdr.func.log.download.not.available";
		String BUILDNAME_ALREADY_EXIST = "excep.build.name.exist";
		String BUILDNUMBER_ALREADY_EXIST = "excep.build.number.exist";
		
		/*****************************
	     * Success Action Messages
	     * I18N Keys Constants
	     * String ACT_SUCC_XXX
	     *****************************/
		String ACT_SUCC_PROJECT_CREATE = "succ.project.create";
		String ACT_SUCC_PROJECT_UPDATE = "succ.project.update";
		String ACT_SUCC_PROJECT_DELETE = "succ.project.delete";
		String ACT_SUCC_CONFIG_ADD = "succ.config.create";
		String ACT_SUCC_CONFIG_UPDATE = "succ.config.update";
		String ACT_SUCC_CONFIG_DELETE = "succ.config.delete";
		String ACT_SUCC_SETTINGS_ADD = "succ.setting.create";
		String ACT_SUCC_SETTINGS_UPDATE = "succ.setting.update";
		String ACT_SUCC_ENV_ADD = "succ.env.create";
		String ACT_SUCC_NON_CONFIG_DEL = "succ.non.config.delete";
		String ACT_SUCC_ENV_DELETE = "succ.env.delete";
		String ACT_SUCC_FEATURE_CONFIGURE = "succ.feature.configure";
		String ACT_SUCC_CONFIG_CLONE = "succ.config.clone";

		/*****************************
	     * Error Action Messages
	     * I18N Keys Constants
	     * String ACT_ERR_XXX
	     *****************************/
		String ACT_ERR_CONFIG_CLONE_EXISTS = "err.config.clone.exists";
		String ACT_ERR_CONFIG_DEFAULT_ENV = "err.config.default.env";
		String ACT_ERR_RUNAGNSRC_IN_PROGRESS = "err.runagnsrc.in.progress";
		
		/**
		 * Dynamic Parameter Constants
		 */
		String TYPE_STRING = "string";
		String TYPE_HIDDEN = "hidden";
		String TYPE_PASSWORD  = "password";
		String TYPE_NUMBER = "number";
		String TYPE_BOOLEAN = "boolean";
		String TYPE_FILE = "FileType";
		String TYPE_ACTIONS = "Actions";
		String TYPE_SCHEDULER = "Scheduler";
		String TEXT_BOX = "text";
		String TYPE_LIST = "list";
		String TYPE_EDITABLE_COMBO = "editableList";
		String TYPE_MAP = "map";
		String TYPE_FILE_BROWSE = "fileBrowse";
		String TYPE_DYNAMIC_PAGE_PARAMETER = "DynamicPageParameter";
		String TYPE_DYNAMIC_PARAMETER = "DynamicParameter";
		String TYPE_PACKAGE_FILE_BROWSE = "packageFileBrowse";
		String PLUGIN_PARAMETER_FRAMEWORK = "framework";
		String REQ_DYNAMIC_PARAMETERS = "dynamicParameters";
		String REQ_PARAMETERS = "parameters";
		String REQ_TEST_BASIS = "testBasis";
		String REQ_DYNAMIC_PAGE_PARAMETER  = "dynamicPageParameter";
		String REQ_DYNAMIC_POSSIBLE_VALUES = "possibleValues";
		String SESSION_WATCHER_MAP = "sessionDynamicParamMap";
		
		/**
		 * Constants for Changing Logo 
		 */
		String IMAGES	 = "images";
		String CUSTOMERS = "customers";
		String PNG       = ".png";
		
		
		/**
		 * Constants for Error in Changing Logo and Theme 
		 */
		String EXCEPTION_FETCHLOGO_IMAGE = "changecustomer.exception.message.fetchlogo";
		String EXCEPTION_FRAMEWORK_THEME = "changecustomer.exception.message.themechange";
		String EXCEPTION_FRAMEWORKSTREAM = "changecustomer.exception.message.stream";
		
		/**
		 * Constants for Changing sonar theme colors
		 */
		String PHOTON = "photon";
		String CUST_BASE_COLOR = "customerBaseColor=";
		String CUST_DISABLED_LABEL_COLOR = "disabledLabelColor";
		String CUST_LABEL_COLOR = "labelColor";
		String CUST_MENUFONT_COLOR = "menufontColor";
		String CUST_MENU_BACK_GROUND = "menuBackGround";
		String CUST_BRANDING_COLOR = "brandingColor";
		String CUST_BODY_BACK_GROUND_COLOR = "bodyBackGroundColor";
		String CUST_FRAMEWORK_THEME = "frameworkTheme";
		
		/**
		 * Framework theme color keys
		 */
		String BRANDING_COLOR =  "brandingColor";
	    String ACCORDION_BACKGROUND_COLOR = "accordionBackGroundColor";
	    String BODYBACKGROUND_COLOR = "bodyBackGroundColor";
	    String BUTTON_COLOR = "ButtonColor";
	    String PAGEHEADER_COLOR = "PageHeaderColor";
	    String COPYRIGHT_COLOR = "CopyRightColor";
	    String LABEL_COLOR = "LabelColor";
	    String MENU_FONT_COLOR = "MenufontColor"; 
	    String MENU_BACKGROUND_COLOR = "MenuBackGround";
	    String SUB_MENU_BACKGROUND_COLOR = "SubMenuBackGround";
	    String COPYRIGHT = "CopyRight";
	    String DISABLED_LABEL_COLOR = "DisabledLabelColor";
	    String USER_JSON = "user.json";
	    String USER_PROJECT_JSON = "user-project.json";
	    
	    String PACKAGE = "package";
	    String PHRESCO_HYPHEN_BUILD_XML = "phresco-build.xml";
	    String SEPARATOR_SEP = "#SEP#";
	    String SEPARATOR_FILESEP = "#FILESEP#";
	    String DIRECTORIES = "directories";
	    String DIRECTORY = "directory";
	    String TO_DIRECTORY = "toDirectory";
	    String FILES = "files";
	    String FILE = "file";
	    String LOCK_FILE = "process.lock";
	    
	    /**
	     * BitKeeper Commands
	     */
	    String BK_CLONE = "bk clone";
	    String BK_PARENT = "bk parent";
	    String BK_PULL = "bk pull";
	    String BK_PUSH = "bk push";
	    String BK_COMMIT = "bk commit";
	    String BK_ADD_FILES = "bk -x -r ci -i";
	    String BK_CI = "bk ci";
	    String BK_ADD_COMMENT = "-y\"";
	    
	    String ALREADY_EXISTS = "exists and is not empty";
	    
	   /**
	    * Theme builder keys
	    */
	    String SUCCESS_THEME_BUILDER_CREATE = "Theme Builder Created Successfully";
	    String FAILURE_THEME_BUILDER_CREATE = "Theme Builder Creation Failed";
	    String SUCCESS_THEME_BUILDER_DELETE = "Theme(s) Deleted Successfully";
	    String REQ_BROWSE_THEME_IMAGE = "themeBuilderImage";
	    String REQ_FROM_THEME_BUILDER = "themeBuilder";
	    String REQ_THEME_UPLOAD_RESULT = "uploadResultMap";
	    
	    /*****************************
	     * Permission Constants
	     * String PER_XXX
	     *****************************/
	    String PER_MANAGE_APPLICATIONS = "manage_applications";
	    String PER_VIEW_APPLICATIONS = "view_applications";
	    String PER_IMPORT_APPLICATIONS = "import_applications";
	    String PER_MANAGE_REPO = "manage_repo";
	    String PER_ADD_TO_REPO = "add_to_repository";
	    String PER_COMMIT_TO_REPO = "commit_to_repository";
	    String PER_UPDATE_REPO = "update_repository";
	    String PER_MANAGE_PDF_REPORTS = "manage_pdf_reports";
	    String PER_VIEW_PDF_REPORTS = "view_pdf_reports";
	    String PER_MANAGE_CODE_VALIDATION = "manage_code_validation";
	    String PER_VIEW_CODE_VALIDATION = "view_code_validation";
	    String PER_MANAGE_CONFIGURATIONS = "manage_configurations";
	    String PER_VIEW_CONFIGURATIONS = "view_configurations";
	    String PER_MANAGE_BUILDS = "manage_builds";
	    String PER_VIEW_BUILDS = "view_builds";
	    String PER_MANAGE_TEST = "manage_test";
	    String PER_VIEW_TEST = "view_test";
	    String PER_MANAGE_CI_JOBS = "manage_ci_jobs";
	    String PER_VIEW_CI_JOBS = "view_ci_jobs";
	    String PER_EXECUTE_CI_JOBS = "execute_ci_jobs";
	    String PER_MANAGE_MAVEN_REPORTS = "manage_maven_reports";
	    String PER_VIEW_MAVEN_REPORTS = "view_maven_reports";
	    
	    String TEST_UNIT = "unitTest";
	    String TEST_FUNCTIONAL = "functionalTest";
	    String TEST_COMPONENT = "componentTest";
	    String TEST_MANUAL = "manualTest";
	    String TEST_LOAD = "loadTest";
	    String TEST_PERFORMANCE = "performanceTest";
	    
	    String LOGO = "logo";
	    String FAV_ICON = "favIcon";
	    String LOGIN_ICON = "loginIcon";
		String THEME = "theme";
		String TECHNOLOGY_NAME = "technologyName";
		 /**
	    * Manual Test Extensions
	    */
	    final String ODS = "ods";
		final String XLS = "xls";
		final String XLSX = "xlsx";

		/**
		 * Liquibase Constants
		 */
		final String LIQUIBASE = "liquibase";
		final String ENV_NAME = "environmentName";
		final String SRC_ENV_NAME = "srcEnvironmentName";
		final String DEST_ENV_NAME = "destEnvironmentName";
		final String L_DBDOC = "dbdoc";
		final String ARG_COMMAND = "-Dcommand=";
		final String CONF_NAME = "configurationName";
		final String SRC_CONF_NAME = "srcConfigurationName";
		final String DEST_CONF_NAME = "destConfigurationName";
		final String L_UPDATE = "update";
		final String L_INSTALL = "install";
		final String L_UPDATE_SUCCESS = "Liquibase Update Successful";
		final String CHANGES_FOLDER = "changes/";
		final String PATH_TO_LIQUIBASE = "/src/main/resources/liquibase/";
		final String DBCHANGELOG = "databaseChangeLog";
		final String XMLNS = "xmlns";
		final String DBCHANGELOG_ORG = "http://www.liquibase.org/xml/ns/dbchangelog";
		final String XML_SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
		final String SCHEMA_LOCATION = "xsi:schemaLocation";
		final String SCHEMA_DEFINITION = "http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd";
		final String CHANGESET = "changeSet";
		final String CHANGESET_ID = "id";
		final String CHANGESET_AUTHOR = "author";
		final String SQL = "sql";
		final String ROLLBACK = "rollback";
		final String MASTER_XML = "/master.xml";
		final String BACKUP_MASTER_XML = "/backupmaster.xml";
		final String INCLUDE_TAG = "include";
		final String FILE_ATTR = "file";
		final String UPDATE_XML = "update.xml";
		final String INSTALL_XML = "install.xml";
		final String INSTALL_COMPLETED_FOR = "Install completed for ";
		final String UPDATE_COMPLETED_FOR = "Update completed for ";
		final String THIS_VERSION = "this version";
		final String UTF_8 = "UTF-8";
		final String L_DIFF = "diff";
		final String L_STATUS = "status";
		final String L_ROLLBACK_COUNT = "rollbackCount";
		final String L_ROLLBACK_TO_DATE = "rollbackToDate";
		final String L_ROLLBACK = "rollback";
		final String L_TAG = "tag";
		final String ROLLBACK_COUNT_VALUE = "rollbackCountValue";
		final String CHANGELOGPATH_ROLLBACK_COUNT_VALUE = "changeLogPathForRollbackCount";
		final String ROLLBACK_TO_DATE = "rollbackDate";
		final String CHANGELOGPATH_ROLLBACK_DATE = "changeLogPathForRollbackDate";
		final String CHANGELOGPATH_DBDOC = "changelogFileForDbDoc";
		final String VERBOSE = "verbose";
		final String ROLLBACK_TAG = "tag";
		final String TAG = "dbtag";
		final String CHANGELOGPATH_ROLLBACK_TAG = "changeLogPathForRollbackTag";
		final String CHANGELOGPATH_STATUS = "changeLogPathForStatus";
		final String ORACLE = "oracle";
		final String MYSQL = "mysql";
		final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	    final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	    final String HOST = "host";
		final String PORT = "port";
		final String DBNAME = "dbname";
		final String DBTYPE = "type";
		final String TAG_COL = "TAG";
		final String SOURCE_USERNAME = "sourceUsername";
		final String REFERENCE_USERNAME = "referenceUsername";
		final String SOURCE_PASSWORD = "sourcePassword";
		final String REFERENCE_PASSWORD = "referencePassword";
		final String SOURCE_HOST = "sourceHost";
		final String REFERENCE_HOST = "referenceHost";
		final String SOURCE_PORT = "sourcePort";
		final String REFERENCE_PORT = "referencePort";
		final String SOURCE_DBNAME = "sourceDbName";
		final String REFERENCE_DBNAME = "referenceDbName";
		
		/**
	     *  Version Range service constants
	     */
	    
	    String ITEM  			= "item";
	    String TYPE  			= "type";
	    String FOLDER 			= "folder";
	    String SNAPSHOT     	= "SNAPSHOT";
	    String RELEASE      	= "RELEASE";
	    String ROOT_ITEM 		= "rootItem";
	    String ROOT 			= "root";
	    String DEFAULT 			= "default";
	    String ROOT_ITEM_XPATH  = "root//item[@name='";
	    String SNAPSHOT_ITEM 	= "//item[@name='SNAPSHOT']";
	    String RELEASE_ITEM   	= "//item[@name='RELEASE']";
	    String SEPARATOR_CONSTANT = "\\.";
	    String BRANCHES     	= "branches";
	    String TAGS				= "tags";
	    String NATURE			= "nature";
	    String MVN_METADATA_XML	= "/maven-metadata.xml";
	    String PRIORITY_JAR     = "-1.jar";
	    String HYPEN_CHAR 		= "-";
	    String DESCRIBE_INFO    = "?describe=info";
	    String ARTIFACT_INFO    = "artifactInfo";
	    String MAVEN_INFO       = "mavenInfo";
	    
	    /**
	     *  Send error report mail constants
	     */
	   
	    String MAIL_ID  	 = "phresco.do.not.reply@gmail.com";
	    String MAIL_PASSWORD = "phresco123";
	    String MAIL_SUBJECT  = "Phresco framework error report";

	    /**
	     * delete sonar project constants
	     * 
	     */
	    
	    String SONAR_USERNAME = "admin";
	    String SONAR_PASSWORD = "admin";
	    String SONAR_API_URL  = "/api/projects/";
	    String SONAR_COLON	  = ":";
	    String JAVA_PROFILE   = "java";
	    String JS_PROFILE  	  = "js";
	    String WEB_PROFILE    = "web";
	    String HTML_PROFILE   = "html";
	    String CSHARP_PROFILE = "c#";
	    String DASHBORAD_INDEX = "/dashboard/index/";
	    
	    String COM_PHOTON_PHRESCO_PLUGINS = "com.photon.phresco.plugins";
	    String PHRESCO_MAVEN_PLUGIN = "phresco-maven-plugin";
	    String ORG_APACHE_MAVEN_PLUGINS = "org.apache.maven.plugins";
	    String MAVEN_RELEASE_PLUGIN = "maven-release-plugin";
		String DOT_GIT 		   = ".git";	
	    String DOT_SVN         = "svn";
	    String BIT_KEEPER     = "bk";
	    String SSH            = "ssh";
	    String GERRIT         = "gerrit";
	    String AUTHENTICATION_FAILED = "Authentication Failed";
	    String PASS_PHARASE = "PBKDF2WithHmacSHA1kjkljfklsdgl";
	    String DESAL         = "DESede";
	    String MODEL_VERSION = "4.0.0";
	    String HTML_ELEMENT = "<html>";

}
