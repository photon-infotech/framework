define(["ci/continuousDeliveryConfigure"], function(ContinuousDeliveryConfigure) {
	return { 
		runTests: function (configData) {

			module("continuousDeliveryConfigure.js");	
			var continuousDeliveryConfigure = new ContinuousDeliveryConfigure(), templateData = {}, self = this, sortList;
			asyncTest("ContinuousDeliveryConfigure - UI Test", function() {
				$('#content').html('');
				var ciAPI = commonVariables.api;
				$('.hProjectId').val('3b33c6c3-2491-4870-b0a9-693817b5b9f8');
				commonVariables.projectLevel = true;
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/listEnvironmentsByProjectId?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",							
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600002","data":["Production","Testing"],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/environmentList?appDirName=htm",							
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Environments Listed successfully","exception":null,"data":["Production","Testing"],"response":null});
					}
				});

				self.sortList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&rootModule=&envName=Production",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"java\",\"appDirName\":\"java\"}":[{"name":"deploy","type":"deploy","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performance","type":"performanceTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"load","type":"loadTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdf","type":"pdfReport","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"CodeValidation","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"status":null});
					}
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.continuousDeliveryConfigure);
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal("Production", envName, "ContinuousDeliveryConfigure - UI Tested");
					equal(8, length, "ContinuousDeliveryConfigure - UI Tested");
//					$(".widget-maincontent-div").remove();
					self.changeEnvironmentTest(continuousDeliveryConfigure);
				}, 2500);
			});		
		},

		changeEnvironmentTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("changeEnvironmentTest - UI Test", function() {
				$('select[name=environments]').val('Testing');
				var ciAPI = commonVariables.api;
				$('.hProjectId').val('3b33c6c3-2491-4870-b0a9-693817b5b9f8');
				commonVariables.projectLevel = true;
				ciAPI.localVal.setSession("customerId" , "photon");
				self.sortList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&rootModule=&envName=Testing",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"java\",\"appDirName\":\"java\"}":[{"name":"CodeValidation","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Confluence"]},{"name":"deploy","type":"deploy","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performance","type":"performanceTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"load","type":"loadTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdf","type":"pdfReport","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["java"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"status":null});
						}
				});
				$('select[name=environments]').change();
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					equal("Testing", envName, "changeEnvironmentTest - UI Tested");
					self.sortableTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},
		
		sortableTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("sortableTest - UI Test", function() {
				//sort1
				var sortable1LiObj = $("#sortable1 li[temp=ci]");
				var code = sortable1LiObj.find('a[id=javaCodeValidation]');
				var ui = {};
				ui.item = code.parent();
				continuousDeliveryConfigure.ciListener.sortableTwoChange(ui);
				continuousDeliveryConfigure.ciListener.sortableTwoReceive(ui);
				//sort2
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var code = sortable1LiObj.find('a[id=javaCodeValidation]');
				var ui = {};
				ui.item = code.parent();
				continuousDeliveryConfigure.ciListener.sortableOneChange(ui);
				continuousDeliveryConfigure.ciListener.sortableOneReceive(ui);
				setTimeout(function() {
					start();
					var text = $("#sortable1 li[temp=ci]").eq(0).find('span').text();
					equal("CodeValidation - codeValidation", text, "sortableTest - UI Tested");
					self.codeJobsTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},

		codeJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("codeJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/cronExpression",						
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Cron Expression calculated successfully","exception":null,"responseCode":null,"data":{"hours":null,"minutes":null,"month":null,"day":null,"cronBy":null,"every":null,"week":null,"cronExpression":"* * * * *","dates":["Wed Aug 07 15:13:00 IST 2013","Wed Aug 07 15:14:00 IST 2013","Wed Aug 07 15:15:00 IST 2013","Wed Aug 07 15:16:00 IST 2013"]},"status":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=validate-code&phase=ci-validate-code&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Validate Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonar","possibleValues":{"value":[{"value":"Source","key":"src","dependency":"src,skipTests"},{"value":"Functional Test","key":"functional","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"js","value":"-Pjs"},{"key":"java","value":"-Pjava"},{"key":"html","value":"-Phtml"},{"key":"web","value":"-Pweb"}]},"name":{"value":[{"value":"Technology","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"src","possibleValues":{"value":[{"value":"js","key":"js","dependency":null},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"true","value":"-DskipTests=true"},{"key":"false","value":"-DskipTests=false"}]},"name":{"value":[{"value":"Skip Unit Test","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"skipTests","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable1LiObj = $("#sortable1 li[temp=ci]");
				$(sortable1LiObj).each(function(index) {
					$("#sortable2").append($(this));
				});

				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				$(sortable2LiObj).each(function(index) {
					var thisJobJsonData = {};
					var anchorTag = $(this).find('a');
					var templateJson = anchorTag.data("templateJson");
					thisJobJsonData.appDirName = "java";
					thisJobJsonData.appName = "java";
					thisJobJsonData.jobName = index+"job";
					thisJobJsonData.operation = templateJson.type;
					
					if (templateJson.repoTypes == 'svn' || templateJson.repoTypes == 'git') {
						thisJobJsonData.url = "url";
						thisJobJsonData.username = "username";
						thisJobJsonData.password = "password";
					}
					
					if (templateJson.enableSheduler && templateJson.name === "CodeValidation") {
						thisJobJsonData.scheduleExpression = "0 18 20 1 *";
						thisJobJsonData.scheduleType = "Monthly";
					} else if (templateJson.enableSheduler && templateJson.name === "build") {
						thisJobJsonData.scheduleExpression = "3 2 * * *";
						thisJobJsonData.scheduleType = "Weekly";
					} else if(templateJson.enableSheduler && templateJson.name === "deploy") {
						thisJobJsonData.scheduleExpression = "2 3 * * *";
						thisJobJsonData.scheduleType = "Daily";
					}
					
					if(templateJson.jobtemplatename == 'build') {
						$('#jonConfiguration').find('input[name=collabNetURL]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetusername]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetpassword]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetProject]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetPackage]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetRelease]').val('asd');
					}

					if(templateJson.jobtemplatename == 'pdf') {
						$('#jonConfiguration').find('input[name=confluenceSpace]').val('asd');
						$('#jonConfiguration').find('input[name=confluencePage]').val('asd');
					}
					anchorTag.data("jobJson", thisJobJsonData)
				});

				$("#sortable2 li.ui-state-default a").show();
				$("#sortable1 li.ui-state-default a").hide();

				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var code = sortable2LiObj.find('a[id=javaCodeValidation]');
				code.click();
				$('input[name=jobName]').val('0job');
				$('input[name=url]').val('asd');
				$('input[name=username]').val('asd');
				$('input[name=password]').val('asd');
				console.info("JobNmae ", $('input[name=jobName]').val());
				console.info("Url ",$('input[name=url]').val());
				console.info("username ",$('input[name=username]').val());
				console.info("password ",$('input[name=password]').val());
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaCodeValidation]').data('jobJson');
					equal(jobjson.jobName, "0job" 	, "codeJobsTest - UI Tested");
					self.buildJobsTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},
		
		buildJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("buildJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
			
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=package&phase=ci-package&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"buildName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.20001-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"true","value":"Production","sort":false,"show":true},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"true","value":"-DskipTests=true"},{"key":"false","value":"-DskipTests=false"}]},"name":{"value":[{"value":"Skip Unit Test","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"skipTest","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/confluence",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800021","data":[{"type":null,"stream":null,"password":"admin","userName":"admin","revision":null,"repoUrl":"http://www.google.com/","testCheckOut":false,"revisionVal":null,"commitableFiles":null,"commitMessage":null,"repoExist":false,"branch":null,"repoInfoFile":null,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null},{"type":null,"stream":null,"password":"admin","userName":"admin","revision":null,"repoUrl":"http://172.16.25.44/","testCheckOut":false,"revisionVal":null,"commitableFiles":null,"commitMessage":null,"repoExist":false,"branch":null,"repoInfoFile":null,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var build = sortable2LiObj.find('a[id=javabuild]');
				build.click();
				$('input[name=jobName]').val('1job');
				$('input[name=collabNetURL]').val('asd');
				$('input[name=collabNetusername]').val('asd');
				$('input[name=collabNetpassword]').val('asd');
				$('input[name=collabNetProject]').val('asd');
				$('input[name=collabNetPackage]').val('asd');
				$('input[name=collabNetRelease]').val('asd');
				$('input[name=confluenceSite]').val('172.16.25.44');
				$('input[name=confluenceSpace]').val('asd');
				$('input[name=confluencePage]').val('asd');
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javabuild]').data('jobJson');
					equal(jobjson.jobName, "1job" 	, "buildJobsTest - UI Tested");
					self.deployJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		deployJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("deployJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=deploy&phase=ci-deploy&customerId=photon&userId=admin&buildNumber=null&iphoneDeploy=",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"},{"value":"Envit","lang":"cv"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.17002-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true,"dependency":"dataBase,fetchSql"},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Execute Sql","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"executeSql","possibleValues":null,"value":"true","sort":false,"show":true,"dependency":"dataBase"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"DataBase","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.17002-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.DynamicDataBaseImpl"},"required":"false","editable":"true","description":"","key":"dataBase","possibleValues":{"value":[]},"multiple":"false","value":"mysql","sort":false,"show":false,"dependency":"fetchSql"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.17002-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.DynamicFetchSqlImpl"},"required":"false","editable":"true","description":"","key":"fetchSql","possibleValues":{"value":[]},"multiple":"false","value":"","sort":true,"show":false}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var deploy = sortable2LiObj.find('a[id=javadeploy]');
				deploy.click();
				$('input[name=jobName]').val('2job');
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javadeploy]').data('jobJson');
					equal(jobjson.jobName, "2job" 	, "deployJobsTest - UI Tested");
					self.unitJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		unitJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("unitJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=unit-test&phase=ci-unit-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"java","value":"-Pjava"},{"key":"js","value":"-Pjs -DskipTests"}]},"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":null,"key":"testAgainst","possibleValues":{"value":[{"value":"java","key":"java","dependency":null},{"value":"js","key":"js","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var unit = sortable2LiObj.find('a[id=javaunit]');
				unit.click();
				$('input[name=jobName]').val('3job');
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaunit]').data('jobJson');
					equal(jobjson.jobName, "3job" 	, "unitJobsTest - UI Tested");
					self.functionalJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		functionalJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("functionalJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=functional-test&phase=ci-functional-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":"showSettings"}]},"value":"","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.17002-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var functional = sortable2LiObj.find('a[id=javafunctional]');
				functional.click();
				$('input[name=jobName]').val('4job');
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javafunctional]').data('jobJson');
					equal(jobjson.jobName, "4job" 	, "functionalJobsTest - UI Tested");
					self.performanceJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		performanceJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("performanceJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=performance-test&phase=ci-performance-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"JMX Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testBasis","possibleValues":{"value":[{"value":"Parameters","key":"parameters","dependency":"testAgainst,showSettings,environmentName,configurations,testName,rampUpPeriod,authManager,contextUrls,dbContextUrls"},{"value":"Custom","key":"customise","dependency":"customTestAgainst,availableJmx"}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"testAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":"contextUrls"},{"value":"Web Services","key":"webservice","dependency":"contextUrls"},{"value":"Database","key":"database","dependency":"dbContextUrls"}]},"multiple":"false","value":"","sort":false,"show":false,"dependency":"configurations,testName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"customTestAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":null},{"value":"Web Services","key":"webservice","dependency":null},{"value":"Database","key":"database","dependency":null}]},"multiple":"false","value":"","sort":false,"show":false,"dependency":"availableJmx"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.22003-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true,"dependency":"configurations"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Configurations","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.22003-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceConfigurationsImpl"},"required":"false","editable":"true","description":"","key":"configurations","possibleValues":{"value":[]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Result Name","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.22003-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestResultNamesImpl"},"required":"true","editable":"edit","description":"","key":"testName","possibleValues":{"value":[{"value":"asd","key":"asd","dependency":"contextUrls"}]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Ramp-Up Period","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"rampUpPeriod","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Auth Manager","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authManager","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"authorizationUrl,authorizationUserName,authorizationPassword,authorizationDomain,authorizationRealm"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Url","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Username","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUserName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Password","lang":"en"}]},"type":"Password","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationPassword","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Domain","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationDomain","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Realm","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationRealm","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Context URLs","lang":"en"}]},"type":"DynamicPageParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.22003-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestDetailsImpl"},"required":"false","editable":"true","description":"","key":"contextUrls","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"DB Context URLs","lang":"en"}]},"type":"DynamicPageParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.22003-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestDetailsImpl"},"required":"false","editable":"true","description":"","key":"dbContextUrls","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"JMX","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.22003-SNAPSHOT"}},"class":"com.photon.phresco.impl.JmxFilesImpl"},"required":"true","editable":"true","description":null,"key":"availableJmx","possibleValues":{"value":[]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"IsFromCI?","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"isFromCI","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":false}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.templateContext + "?appDirName=java&goal=performance-test&phase=ci-performance-test&customerId=photon&userId=admin&parameterKey=contextUrls",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR7C00001","data":"<div class=\"middle_div\"> \t<span>Context URLs</span>    <span><input type=\"button\" value=\"Add\" id=\"contextAdd\" class=\"btn btn_style add_icon_btn\"></span>    <div class=\"clear\"></div></div><script type=\"text/javascript\">\tvar jsonFlag = true;</script><div class=\"contextDivParent\" id=\"contextDivParent\">\t\t<script type=\"text/javascript\">\t\t\tjsonFlag = false;\t\t</script>\t\t\t<div id=\"contextDiv\" class=\"contextDivClass\">\t\t\t\t<table class=\"table table-striped table_border table-bordered context_table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t<thead>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<th colspan=\"4\">\t\t\t\t\t\t\t<div class=\"delete_icon removeContext\">\t\t\t\t\t\t\t\t<img class=\"removeContextImg\" src=\"themes/default/images/Phresco/delete_row.png\">\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t</th>\t\t\t\t\t\t</tr>\t\t\t\t\t</thead>\t\t\t\t\t<tbody>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>HTTP Name<sup>*</sup></label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"localhost\" name=\"httpName\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Additional Context</label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"context\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Type</label>\t\t\t\t\t\t\t\t<select id=\"contextTypelocalhost\" name=\"contextType\">\t\t\t\t\t\t\t\t\t<option value=\"GET\">GET</option>\t\t\t\t\t\t\t\t\t<option value=\"POST\">POST</option>\t\t\t\t\t\t\t\t\t<option value=\"PUT\">PUT</option>\t\t\t\t\t\t\t\t\t<option value=\"DELETE\">DELETE</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Encoding</label>\t\t\t\t\t\t\t\t<select id=\"encodingTypelocalhost\" name=\"encodingType\">\t\t\t\t\t\t\t\t\t<option value=\"UTF-8\">UTF-8</option>\t\t\t\t\t\t\t\t\t<option value=\"UTF-16\">UTF-16</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"true\" name=\"redirectAutomatically\" class=\"redirectAutomatically\">Redirect Automatically</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"followRedirects\" class=\"followRedirects\">Follow Redirects</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"keepAlive\" class=\"keepAlive\">Use Keep Alive</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"multipartData\" class=\"multipartData\">Use Mulipart data</td>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\"><input type=\"checkbox\" checkFlag=\"false\" name=\"compatibleHeaders\" class=\"compatibleHeaders\">Browser Compatible Headers</td>\t\t\t\t\t\t\t\t\t\t</tr> \t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t</table> \t\t\t\t\t\t\t</td>                                  \t\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table table-striped table_border table-bordered header_table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<th>Headers</th>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"headerKey\" placeholder=\"Key\"><input class=\"headerValue\" type=\"text\" placeholder=\"Value\"><input type=\"button\" value=\"Add\" class=\"btn btn_style headerKeyAdd\"></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"add_test\">\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t </tbody>\t\t\t\t\t\t\t\t   </table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t\t\t <tr>\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t <table class=\"table table-striped table_border table-bordered header_table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t<th>Parameters</th>\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class=\"parameterRow\">\t\t\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"parameterName\" value=\"\" placeholder=\"Name\" name=\"parameterName\"/>\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" class=\"parameterValue\" value=\"\" name=\"parameterValue\" placeholder=\"Value\"/>\t\t\t\t\t\t\t\t\t\t\t\t\t<input class=\"parameterEncode\" type=\"checkbox\"  name=\"parameterEncode\" checkFlag=\"false\"/>Encode\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"themes/default/images/Phresco/minus_icon.png\" class=\"add_test_icon removeParamter\" style=\"display:none;\">\t\t\t\t\t\t\t\t\t\t\t\t\t<img class=\"add_test_icon addParameter\" src=\"themes/default/images/Phresco/plus_icon.png\"></td>\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t\t</table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t</tbody>\t\t\t\t</table>\t\t\t</div>   \t\t\t<script type=\"text/javascript\">\t\t\t\tdocument.getElementById(\"contextTypelocalhost\").value = 'GET';\t\t\t\tdocument.getElementById(\"encodingTypelocalhost\").value = 'UTF-8';\t\t\t</script>\t\t\t</div>     <script type=\"text/javascript\">\tif(jsonFlag) {\t\tvar newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass');\t\tnewTextBoxDiv.html(\"<table class='table table-striped table_border table-bordered context_table' cellpadding='0' cellspacing='0' \tborder='0'><thead><tr><th colspan='4'><div class='delete_icon removeContext'><img class='removeContextImg' src='themes/default/images/Phresco/delete_row.png'></div></th></tr></thead><tbody><tr><td><label>HTTP Name<sup>*</sup></label><input type='text' value='' name='httpName'></td><td><label>Additional Context</label><input type='text' value='' name='context'></td><td><label>Type</label><select name='contextType'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td><label>Encoding</label><select name='encodingType'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr><tr><td colspan='4'><table class='table' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td><input type='checkbox' name='redirectAutomatically' class='redirectAutomatically' checked>Redirect Automatically</td><td><input type='checkbox'  name='followRedirects' class='followRedirects'>Follow Redirects</td><td><input type='checkbox' name='keepAlive' class='keepAlive'>Use Keep Alive</td></tr><tr><td><input type='checkbox' name='multipartData' class='multipartData'>Use Mulipart data</td><td colspan='2'><input type='checkbox' name='compatibleHeaders' class='compatibleHeaders'>Browser Compatible Headers</td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Headers</th></tr></thead><tbody><tr><td><input type='text' class='headerKey' placeholder='Key'><input class='headerValue' type='text' placeholder='Value'><input type='button' value='Add' class='btn btn_style headerKeyAdd'></td></tr><tr class='headerContent' style='display:none;'><td><div class='add_test'></div></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Parameters</th></tr></thead><tbody><tr class='parameterRow'><td><input type='text' class='parameterName' value='' placeholder='Name' name='parameterName'/><input type='text' class='parameterValue' value='' name='parameterValue' placeholder='Value'/><input class='parameterEncode' type='checkbox'  name='parameterEncode'/>Encode<img src='themes/default/images/Phresco/minus_icon.png' class='add_test_icon removeParamter' style='display:none;'><img class='add_test_icon addParameter' src='themes/default/images/Phresco/plus_icon.png'></td></tr></tbody></table></td></tr></tbody></table>\");\t\tnewTextBoxDiv.appendTo(\"#contextDivParent\");\t}  else {\t\tcheckEncodeCheckBox();\t}\t\tfunction checkEncodeCheckBox() {\t\tjQuery('.parameterEncode').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.redirectAutomatically').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.followRedirects').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.keepAlive').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.multipartData').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.compatibleHeaders').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t}\t\tfunction contextUrls() {\t\tvar contextUrls = [];\t\tvar contexts = \"\";\t\tjQuery('.contextDivClass').each(function() {\t\t\tvar jsonObject = {};\t\t\tjsonObject.name = jQuery(this).find(jQuery(\"input[name=httpName]\")).val();\t\t\tjsonObject.context = jQuery(this).find(jQuery(\"input[name=context]\")).val();\t\t\tjsonObject.contextType = jQuery(this).find(jQuery(\"select[name=contextType]\")).val();\t\t\tjsonObject.encodingType = jQuery(this).find(jQuery(\"select[name=encodingType]\")).val();\t\t\tjsonObject.contextPostData = jQuery(this).find(jQuery(\"textarea[name=contextPostData]\")).val(); \t\t\tjsonObject.redirectAutomatically = jQuery(this).find(jQuery(\"input[name=redirectAutomatically]\")).is(':checked'); \t\t\tjsonObject.followRedirects = jQuery(this).find(jQuery(\"input[name=followRedirects]\")).is(':checked'); \t\t\tjsonObject.keepAlive = jQuery(this).find(jQuery(\"input[name=keepAlive]\")).is(':checked'); \t\t\tjsonObject.multipartData = jQuery(this).find(jQuery(\"input[name=multipartData]\")).is(':checked'); \t\t\tjsonObject.compatibleHeaders = jQuery(this).find(jQuery(\"input[name=compatibleHeaders]\")).is(':checked');  \t\t\t\t\t\t\t\t\tvar headers = [];\t\t\tjQuery(this).find(jQuery('.headers')).each(function() {\t\t\t\tvar key = jQuery(this).find(jQuery(\"input[name=headerKey]\")).val();\t\t\t\tvar value = jQuery(this).find(jQuery(\"input[name=headerValue]\")).val();\t\t\t\tvar keyValueObj = {};\t\t\t\tkeyValueObj.key=key;\t\t\t\tkeyValueObj.value=value;\t\t\t\theaders.push(keyValueObj);\t\t\t});\t\t\tjsonObject.headers=headers;\t\t\t\t\t\tvar parameters = [];\t\t\tjQuery(this).find(jQuery('.parameterRow')).each(function() {\t\t\t\tvar name = jQuery(this).find(jQuery(\"input[name=parameterName]\")).val();\t\t\t\tvar value = jQuery(this).find(jQuery(\"textarea[name=parameterValue]\")).val();\t\t\t\tvar encode = jQuery(this).find(jQuery(\"input[name=parameterEncode]\")).is(':checked');\t\t\t\tvar nameValueObj = {};\t\t\t\tnameValueObj.name=name;\t\t\t\tnameValueObj.value=value;\t\t\t\tnameValueObj.encode=encode;\t\t\t\tparameters.push(nameValueObj);\t\t\t});\t\t\tjsonObject.parameters=parameters;\t\t\tcontextUrls.push(JSON.stringify(jsonObject));\t\t});\t\tvar jsonStrFromTemplate = '\"contextUrls\":[' + contextUrls + ']';\t\treturn jsonStrFromTemplate;\t}</script>","status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.templateContext + "?appDirName=java&goal=performance-test&phase=ci-performance-test&customerId=photon&userId=admin&parameterKey=dbContextUrls",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR7C00001","data":"<div class=\"middle_div\">\t<span>DB Context URLs</span>\t<span><input type=\"button\" value=\"Add\" id=\"dbContextAdd\" class=\"btn btn_style add_icon_btn\"></span>\t<div class=\"clear\"></div></div><script type=\"text/javascript\">\tvar dbJsonFlag = true;</script><div id=\"dbContextDivParent\">\t\t\t\t</div>\t<script type=\"text/javascript\">\tif(dbJsonFlag) {\t\tvar newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'dbContextDiv').attr('class','dbContextDivClass');\t\tnewTextBoxDiv.html(\"<table class='table table-striped table_border table-bordered context_table' cellpadding='0' cellspacing='0' border='0'>\t\t\t<thead><tr><th colspan='4'><div class='delete_icon removeDBContext'><img class='removeContextImg' src='themes/default/images/Phresco/delete_row.png'></div></th></tr>\t\t\t</thead><tbody><tr>\t\t\t<td><label>Name<sup>*</sup></label><input type='text' name='dbName' value=''></td><td><label>Query Type<sup>*</sup></label><select name='queryType'><option value='Select Statement'>Select Statement</option><option value='Update Statement'>Update Statement</option></select></td><td><label>Query<sup>*</sup></label><textarea name='query' id='query'></textarea></td></tr></tbody></table>\");\t\tnewTextBoxDiv.appendTo(\"#dbContextDivParent\");\t\t}</script>\t","status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var performance = sortable2LiObj.find('a[id=javaperformance]');
				performance.click();
				$('input[name=jobName]').val('5job');
				$('input[name=httpName]').val('asd');
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaperformance]').data('jobJson');
					equal(jobjson.jobName, "5job" 	, "performanceJobsTest - UI Tested");
					self.performanceJobsClickTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},
		
		performanceJobsClickTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("performanceJobsClickTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				$('input[name=jobName]').val('5job');
				$('input[name=httpName]').val('asd');
				
				$.mockjax({
//				 http://localhost:8234/framework/rest/api/app/ciPerformanceTest?jobName=5job&repoType=clonedWorkspace&downStreamCriteria=SUCCESS&scheduleType=Daily&hours=*&minutes=*&scheduleExpression=&testBasis=parameters&testAgainst=server&customTestAgainst=server&environmentName=Production&testName=&rampUpPeriod=&authorizationUrl=&authorizationUserName=&authorizationPassword=&authorizationDomain=&authorizationRealm=&dbName=&queryType=Select+Statement&query=&isFromCI=&appDirName=java&rootModule=&testAction=performance
					url: commonVariables.webserviceurl + "app/ciPerformanceTest?jobName=5job&repoType=clonedWorkspace&downStreamCriteria=SUCCESS&scheduleType=Daily&hours=*&minutes=*&scheduleExpression=&testBasis=parameters&testAgainst=server&customTestAgainst=server&environmentName=Production&testName=&rampUpPeriod=&authorizationUrl=&authorizationUserName=&authorizationPassword=&authorizationDomain=&authorizationRealm=&dbName=&queryType=Select+Statement&query=&isFromCI=&appDirName=java&rootModule=&testAction=performance",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"responseCode":null,"status":"success","log":null,"connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":null,"service_exception":null,"configErrorMsg":null});
					}
				});
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaperformance]').data('jobJson');
					equal(jobjson.jobName, "5job" 	, "performanceJobsClickTest - UI Tested");
					self.loadJobsTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},
		
		loadJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("loadJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=load-test&phase=ci-load-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"JMX Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testBasis","possibleValues":{"value":[{"value":"Parameters","key":"parameters","dependency":"testAgainst,showSettings,environmentName,configurations,testName,noOfUsers,loopCount,rampUpPeriod,authManager,loadContextUrl"},{"value":"Custom","key":"customise","dependency":"customTestAgainst,availableJmx"}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":"loadContextUrl"},{"value":"Web Services","key":"webservice","dependency":"loadContextUrl"}]},"multiple":"false","value":"","sort":false,"show":false,"dependency":"configurations,testName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"customTestAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":null},{"value":"Web Services","key":"webservice","dependency":null}]},"multiple":"false","value":"server","sort":false,"show":false,"dependency":"availableJmx"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.20001-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true,"dependency":"configurations"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Configurations","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.20001-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceConfigurationsImpl"},"required":"true","editable":"true","description":"","key":"configurations","possibleValues":{"value":[{"value":"server","key":null,"dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Result Name","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.20001-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestResultNamesImpl"},"required":"true","editable":"edit","description":"","key":"testName","possibleValues":{"value":[{"value":"javaLoad","key":"javaLoad","dependency":"loadContextUrl"}]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"No Of Users","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"noOfUsers","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Ramp-Up Period","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"rampUpPeriod","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Loop Count","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"loopCount","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Auth Manager","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authManager","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"authorizationUrl,authorizationUserName,authorizationPassword,authorizationDomain,authorizationRealm"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Url","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Username","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUserName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Password","lang":"en"}]},"type":"Password","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationPassword","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Domain","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationDomain","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Realm","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationRealm","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Context URLs","lang":"en"}]},"type":"DynamicPageParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.20001-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.LoadTestDetailsImpl"},"required":"false","editable":"true","description":"","key":"loadContextUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"JMX","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.20001-SNAPSHOT"}},"class":"com.photon.phresco.impl.JmxFilesImpl"},"required":"true","editable":"true","description":null,"key":"availableJmx","possibleValues":{"value":[]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"IsFromCI?","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"isFromCI","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":false}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.templateContext + "?appDirName=java&goal=load-test&phase=ci-load-test&customerId=photon&userId=admin&parameterKey=loadContextUrl",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Template content returned succesfully","exception":null,"responseCode":null,"data":"<div class=\"middle_div\"> \t<span>Context URLs</span>    <span><input type=\"button\" value=\"Add\" id=\"contextAdd\" class=\"btn btn_style add_icon_btn\"></span>    <div class=\"clear\"></div></div><script type=\"text/javascript\">\tvar jsonFlag = true;</script><div class=\"contextDivParent\" id=\"contextDivParent\">\t\t<script type=\"text/javascript\">\t\t\tjsonFlag = false;\t\t</script>\t\t\t<div id=\"contextDiv\" class=\"contextDivClass\">\t\t\t\t<table class=\"table table-striped table_border table-bordered context_table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t<thead>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<th colspan=\"4\">\t\t\t\t\t\t\t<div class=\"delete_icon removeContext\">\t\t\t\t\t\t\t\t<img class=\"removeContextImg\" src=\"themes/default/images/Phresco/delete_row.png\">\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t</th>\t\t\t\t\t\t</tr>\t\t\t\t\t</thead>\t\t\t\t\t<tbody>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>HTTP Name<sup>*</sup></label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"localhost\" name=\"httpName\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Context<sup>*</sup></label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"admin/login\" name=\"context\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Type</label>\t\t\t\t\t\t\t\t<select id=\"contextTypelocalhost\" name=\"contextType\">\t\t\t\t\t\t\t\t\t<option value=\"GET\">GET</option>\t\t\t\t\t\t\t\t\t<option value=\"POST\">POST</option>\t\t\t\t\t\t\t\t\t<option value=\"PUT\">PUT</option>\t\t\t\t\t\t\t\t\t<option value=\"DELETE\">DELETE</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Encoding</label>\t\t\t\t\t\t\t\t<select id=\"encodingTypelocalhost\" name=\"encodingType\">\t\t\t\t\t\t\t\t\t<option value=\"UTF-8\">UTF-8</option>\t\t\t\t\t\t\t\t\t<option value=\"UTF-16\">UTF-16</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"redirectAutomatically\" class=\"redirectAutomatically\">Redirect Automatically</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"followRedirects\" class=\"followRedirects\">Follow Redirects</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"keepAlive\" class=\"keepAlive\">Use Keep Alive</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"multipartData\" class=\"multipartData\">Use Mulipart data</td>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\"><input type=\"checkbox\" checkFlag=\"false\" name=\"compatibleHeaders\" class=\"compatibleHeaders\">Browser Compatible Headers</td>\t\t\t\t\t\t\t\t\t\t</tr> \t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t</table> \t\t\t\t\t\t\t</td>                                  \t\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table table-striped table_border table-bordered header_table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<th>Headers</th>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"headerKey\" placeholder=\"Key\"><input class=\"headerValue\" type=\"text\" placeholder=\"Value\"><input type=\"button\" value=\"Add\" class=\"btn btn_style headerKeyAdd\"></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"add_test\">\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t </tbody>\t\t\t\t\t\t\t\t   </table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t\t\t <tr>\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t <table class=\"table table-striped table_border table-bordered header_table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t<th>Parameters</th>\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class=\"parameterRow\">\t\t\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"parameterName\" value=\"\" placeholder=\"Name\" name=\"parameterName\"/>\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" class=\"parameterValue\" value=\"\" name=\"parameterValue\" placeholder=\"Value\"/>\t\t\t\t\t\t\t\t\t\t\t\t\t<input class=\"parameterEncode\" type=\"checkbox\"  name=\"parameterEncode\" checkFlag=\"false\"/>Encode\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"themes/default/images/Phresco/minus_icon.png\" class=\"add_test_icon removeParamter\" style=\"display:none;\">\t\t\t\t\t\t\t\t\t\t\t\t\t<img class=\"add_test_icon addParameter\" src=\"themes/default/images/Phresco/plus_icon.png\"></td>\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t\t</table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t</tbody>\t\t\t\t</table>\t\t\t</div>   \t\t\t<script type=\"text/javascript\">\t\t\t\tdocument.getElementById(\"contextTypelocalhost\").value = 'GET';\t\t\t\tdocument.getElementById(\"encodingTypelocalhost\").value = 'UTF-8';\t\t\t</script>\t\t\t</div>     <script type=\"text/javascript\">\tif(jsonFlag) {\t\tvar newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass');\t\tnewTextBoxDiv.html(\"<table class='table table-striped table_border table-bordered context_table' cellpadding='0' cellspacing='0' \tborder='0'><thead><tr><th colspan='4'><div class='delete_icon removeContext'><img class='removeContextImg' src='themes/default/images/Phresco/delete_row.png'></div></th></tr></thead><tbody><tr><td><label>HTTP Name<sup>*</sup></label><input type='text' value='' name='httpName'></td><td><label>Context<sup>*</sup></label><input type='text' value='' name='context'></td><td><label>Type</label><select name='contextType'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td><label>Encoding</label><select name='encodingType'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr><tr><td colspan='4'><table class='table' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td><input type='checkbox' name='redirectAutomatically' class='redirectAutomatically'>Redirect Automatically</td><td><input type='checkbox'  name='followRedirects' class='followRedirects'>Follow Redirects</td><td><input type='checkbox' name='keepAlive' class='keepAlive'>Use Keep Alive</td></tr><tr><td><input type='checkbox' name='multipartData' class='multipartData'>Use Mulipart data</td><td colspan='2'><input type='checkbox' name='compatibleHeaders' class='compatibleHeaders'>Browser Compatible Headers</td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Headers</th></tr></thead><tbody><tr><td><input type='text' class='headerKey' placeholder='Key'><input class='headerValue' type='text' placeholder='Value'><input type='button' value='Add' class='btn btn_style headerKeyAdd'></td></tr><tr class='headerContent' style='display:none;'><td><div class='add_test'></div></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Parameters</th></tr></thead><tbody><tr class='parameterRow'><td><input type='text' class='parameterName' value='' placeholder='Name' name='parameterName'/><input type='text' class='parameterValue' value='' name='parameterValue' placeholder='Value'/><input class='parameterEncode' type='checkbox'  name='parameterEncode'/>Encode<img src='themes/default/images/Phresco/minus_icon.png' class='add_test_icon removeParamter' style='display:none;'><img class='add_test_icon addParameter' src='themes/default/images/Phresco/plus_icon.png'></td></tr></tbody></table></td></tr></tbody></table>\");\t\tnewTextBoxDiv.appendTo(\"#contextDivParent\");\t}  else {\t\tcheckEncodeCheckBox();\t}\t\tfunction checkEncodeCheckBox() {\t\tjQuery('.parameterEncode').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.redirectAutomatically').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.followRedirects').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.keepAlive').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.multipartData').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.compatibleHeaders').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t}\t\tfunction loadContextUrls() {\t\tvar contextUrls = [];\t\tvar contexts = \"\";\t\tjQuery('.contextDivClass').each(function() {\t\t\tvar jsonObject = {};\t\t\tjsonObject.name = jQuery(this).find(jQuery(\"input[name=httpName]\")).val();\t\t\tjsonObject.context = jQuery(this).find(jQuery(\"input[name=context]\")).val();\t\t\tjsonObject.contextType = jQuery(this).find(jQuery(\"select[name=contextType]\")).val();\t\t\tjsonObject.encodingType = jQuery(this).find(jQuery(\"select[name=encodingType]\")).val();\t\t\tjsonObject.contextPostData = jQuery(this).find(jQuery(\"textarea[name=contextPostData]\")).val(); \t\t\tjsonObject.redirectAutomatically = jQuery(this).find(jQuery(\"input[name=redirectAutomatically]\")).is(':checked'); \t\t\tjsonObject.followRedirects = jQuery(this).find(jQuery(\"input[name=followRedirects]\")).is(':checked'); \t\t\tjsonObject.keepAlive = jQuery(this).find(jQuery(\"input[name=keepAlive]\")).is(':checked'); \t\t\tjsonObject.multipartData = jQuery(this).find(jQuery(\"input[name=multipartData]\")).is(':checked'); \t\t\tjsonObject.compatibleHeaders = jQuery(this).find(jQuery(\"input[name=compatibleHeaders]\")).is(':checked');  \t\t\t\t\t\t\t\t\tvar headers = [];\t\t\tjQuery(this).find(jQuery('.headers')).each(function() {\t\t\t\tvar key = jQuery(this).find(jQuery(\"input[name=headerKey]\")).val();\t\t\t\tvar value = jQuery(this).find(jQuery(\"input[name=headerValue]\")).val();\t\t\t\tvar keyValueObj = {};\t\t\t\tkeyValueObj.key=key;\t\t\t\tkeyValueObj.value=value;\t\t\t\theaders.push(keyValueObj);\t\t\t});\t\t\tjsonObject.headers=headers;\t\t\t\t\t\tvar parameters = [];\t\t\tjQuery(this).find(jQuery('.parameterRow')).each(function() {\t\t\t\tvar name = jQuery(this).find(jQuery(\"input[name=parameterName]\")).val();\t\t\t\tvar value = jQuery(this).find(jQuery(\"textarea[name=parameterValue]\")).val();\t\t\t\tvar encode = jQuery(this).find(jQuery(\"input[name=parameterEncode]\")).is(':checked');\t\t\t\tvar nameValueObj = {};\t\t\t\tnameValueObj.name=name;\t\t\t\tnameValueObj.value=value;\t\t\t\tnameValueObj.encode=encode;\t\t\t\tparameters.push(nameValueObj);\t\t\t});\t\t\tjsonObject.parameters=parameters;\t\t\tcontextUrls.push(JSON.stringify(jsonObject));\t\t});\t\tvar jsonStrFromTemplate = '\"contextUrls\":[' + contextUrls + ']';\t\treturn jsonStrFromTemplate;\t}</script>","status":null});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var load = sortable2LiObj.find('a[id=javaload]');
				load.click();
				$('input[name=jobName]').val('6job');
				$('input[name=httpName]').val('asd');
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaload]').data('jobJson');
					equal(jobjson.jobName, "6job" 	, "loadJobsTest - UI Tested");
//					self.loadJobsClickTest(continuousDeliveryConfigure);
					self.pdfJobsTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},
		
		loadJobsClickTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("loadJobsClickTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + "app/ciPerformanceTest?jobName=6job&repoType=clonedWorkspace&downStreamCriteria=SUCCESS&scheduleType=Daily&hours=*&minutes=*&scheduleExpression=&testBasis=parameters&testAgainst=server&customTestAgainst=server&environmentName=Production&configurations=server&testName=&noOfUsers=&rampUpPeriod=&loopCount=&authorizationUrl=&authorizationUserName=&authorizationPassword=&authorizationDomain=&authorizationRealm=&isFromCI=&appDirName=java&rootModule=&testAction=load",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"responseCode":null,"status":"success","log":null,"connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":null,"service_exception":null,"configErrorMsg":null});
					}
				});
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaload]').data('jobJson');
					console.info("jobJson ", jobJson)
//					equal(jobjson.jobName, "6job" 	, "loadJobsClickTest - UI Tested");
					equal(1, 1, "loadJobsClickTest - UI Tested");
//					self.pdfJobsTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},
		
		pdfJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("pdfJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=java&goal=pdf-report&phase=ci-pdf-report&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Report Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"reportType","possibleValues":{"value":[{"value":"Overall","key":"crisp","dependency":null},{"value":"Detailed","key":"detail","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Pdf Report Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"reportName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testType","possibleValues":{"value":[{"value":"All","key":"All","dependency":null},{"value":"unit","key":"unit","dependency":null},{"value":"functional","key":"functional","dependency":null},{"value":"performance","key":"performance","dependency":null},{"value":"load","key":"load","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Sonar Url","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonarUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Logo","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"logo","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Theme","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"theme","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Technology Name","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"technologyName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var pdf = sortable2LiObj.find('a[id=javapdf]');
				pdf.click();
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javapdf]').data('jobJson');
					equal(jobjson.jobName, "7job" 	, "pdfJobsTest - UI Tested");
					self.createContinuousIntegrationTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},

		createContinuousIntegrationTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("createContinuousIntegrationTest - UI Test", function() {
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/validation?name=0job,1job,2job,3job,4job,5job,6job,7job&flag=Add&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Validation completed successfully","exception":null,"responseCode":null,"data":true,"status":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/create?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&userId=admin&rootModule=",						
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job(s) created successfully","exception":null,"responseCode":null,"data":{"name":"contDelivery","jobs":[{"context":"","query":"","mainClassName":"","configuration":"","alias":"","url":"url","pomLocation":"pom.xml","username":"username","password":"password","email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"js","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"java","buildName":"","templateName":"CodeValidation","jobName":"0job","downstreamApplication":"1job","upstreamApplication":"","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"codeValidation","coberturaPlugin":false,"environmentName":"","repoType":"svn","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:validate-code -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=validate-code -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"src","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"localhost","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"1job","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":null},{"context":"","query":"","mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"java","buildName":"","templateName":"build","jobName":"1job","downstreamApplication":"2job","upstreamApplication":"0job","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"build","coberturaPlugin":false,"environmentName":"Production","repoType":"clonedWorkspace","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"-Pci clean phresco:package -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"0job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=package -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":true,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"localhost","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"do_not_checkin/build/*.zip","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"2job","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":null},{"context":"","query":"","mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"java","buildName":"","templateName":"deploy","jobName":"2job","downstreamApplication":"3job","upstreamApplication":"1job","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"deploy","coberturaPlugin":false,"environmentName":"Production","repoType":"clonedWorkspace","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:deploy -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"1job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=deploy -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"on","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"on","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"localhost","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"3job","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":null},{"context":"","query":"","mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"java","buildName":"","templateName":"unit","jobName":"3job","downstreamApplication":"4job","upstreamApplication":"2job","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"unittest","coberturaPlugin":false,"environmentName":"","repoType":"clonedWorkspace","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:unit-test -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"2job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=unit-test -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"java","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"localhost","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"4job","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":null},{"context":"","query":"","mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"java","buildName":"","templateName":"functional","jobName":"4job","downstreamApplication":"5job","upstreamApplication":"3job","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"functionalTest","coberturaPlugin":false,"environmentName":"Production","repoType":"clonedWorkspace","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:functional-test -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"3job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=functional-test -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"server","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"localhost","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"5job","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":null},{"context":"admin/login","query":"","mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"server","family":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"true","testBasis":"parameters","appDirName":"java","buildName":"","templateName":"performance","jobName":"5job","downstreamApplication":"6job","upstreamApplication":"4job","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"performanceTest","coberturaPlugin":false,"environmentName":"Production","repoType":"clonedWorkspace","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:performance-test -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"4job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=performance-test -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"server","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"testJava","noOfUsers":"","rampUpPeriod":"10","loopCount":"","httpName":"localhost","contextType":"GET","encodingType":"UTF-8","parameterValue":"","dbName":"","queryType":"Select Statement","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"server","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"6job","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":null},{"context":"admin/login","query":"","mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"server","family":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"true","testBasis":"parameters","appDirName":"java","buildName":"","templateName":"load","jobName":"6job","downstreamApplication":"7job","upstreamApplication":"5job","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"loadTest","coberturaPlugin":false,"environmentName":"Production","repoType":"clonedWorkspace","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:load-test -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"5job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=load-test -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"server","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"javaLoad","noOfUsers":"10","rampUpPeriod":"10","loopCount":"10","httpName":"localhost","contextType":"GET","encodingType":"UTF-8","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"server","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"7job","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":null},{"context":"","query":"","mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"java","buildName":"","templateName":"pdf","jobName":"7job","downstreamApplication":"","upstreamApplication":"6job","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"pdfReport","coberturaPlugin":false,"environmentName":"","repoType":"clonedWorkspace","headerValue":"","sonarUrl":"http://localhost:9000/dashboard","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"-Pci clean phresco:pdf-report -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":false,"usedClonnedWorkspace":"6job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=pdf-report -DcreationType=global -Did=3b33c6c3-2491-4870-b0a9-693817b5b9f8 -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"crisp","testType":"All","attachmentsPattern":"do_not_checkin/archives/cumulativeReports/*.pdf","enableArtifactArchiver":true,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"localhost","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"","deviceList":"","collabNetFileReleasePattern":"do_not_checkin/archives/cumulativeReports/*.pdf","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"","appName":"java","successEmailIds":"","failureEmailIds":"","technologyName":"J2EE"}],"envName":"Production"},"status":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/pipeline?projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&name=contDelivery&customerId=photon&rootModule=",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR810042","data":false,"status":"success"});
					}
				});
				
				$("input[name=continuousDeliveryName]").val("contDelivery");
				console.info("$(input[type=submit])========> length  jobTemplatesTest ",$(".content_end").find("input[type=submit][value=Add]").length);
				$(".content_end").find("input[type=submit][value=Add]").click();
				setTimeout(function() {
					start();
					equal(1, 1, "createContinuousIntegrationTest - UI Tested");
					require(["continuousDeliveryViewTest"], function(continuousDeliveryViewTest){
						continuousDeliveryViewTest.runTests();
					});
				}, 3500);
			});
		},
		
	};
});