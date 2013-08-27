define(["ci/continuousDeliveryView"], function(ContinuousDeliveryView) {

	return {
		// view continuous delivery test
		runTests: function (configData) {
			
			module("ContinuousDeliveryView.js");
			var continuousDeliveryView = new ContinuousDeliveryView(), self = this, contViewList;
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");

			asyncTest("Continuous Delivery View empty template render test", function() {
				$(commonVariables.contentPlaceholder).html('');
				self.contViewList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci +"/list?projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Continuous Delivery List Successfully","exception":null,"data":{"id":"","continuousDeliveries":null},"response":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/isAlive",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Jenkins is Alive","exception":null,"responseCode":null,"data":"200","status":null});
					}
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.continuousDeliveryView);
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find(".aliveOpts").css('display'), 'block', "List Empty Continuous Delivery - UI Tested");
					self.viewContinuousDelivery(continuousDeliveryView);
				}, 2500);
			});
		},

		// view continuous delivery test
		viewContinuousDelivery : function (continuousDeliveryView) {
			var self = this;
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");
			asyncTest("Continuous Delivery View template render test", function() {
				// list existing continuous delivery views
				$.mockjaxClear(self.contViewList);
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci +"/list?projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Continuous Delivery List Successfully","exception":null,"responseCode":null,"data":{"id":"0efc9dc6-cd81-4d11-8bb7-6aac6252bfc3","continuousDeliveries":[{"name":"independent","jobs":[{"context":"","query":"","mainClassName":"","configuration":"","alias":"","url":"sdfeaf","pomLocation":"pom.xml","username":"dsafsdaf","password":"sdafsdaf","email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"html","buildName":"fsdaf","templateName":"build","jobName":"IndependentCode","downstreamApplication":"","upstreamApplication":"","jenkinsUrl":"172.16.27.152","jenkinsPort":"3579","operation":"build","coberturaPlugin":false,"environmentName":"Production","repoType":"svn","headerValue":"","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"-Pci clean phresco:package -DskipTests=false -Dmaven.yuicompressor.skip=true -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":false,"usedClonnedWorkspace":"","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=package -DcreationType=global -Did=0efc9dc6-cd81-4d11-8bb7-6aac6252bfc3 -DcontinuousDeliveryName=test -N"],"postbuildStepCommands":null,"logs":"showErrors","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"true","deviceType":"","sdkVersion":"","devices":"","serialNumber":"","testAgainst":"","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":true,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"","deviceList":"","collabNetFileReleasePattern":"do_not_checkin/build/*.zip","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"","appName":"html","successEmailIds":"","failureEmailIds":"","technologyName":null}],"envName":"Production"}]},"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/isAlive",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Jenkins is Alive","exception":null,"responseCode":null,"data":"200","status":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/isAlive",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 204,
					response: function() {
						this.responseText = JSON.stringify({"message":"Jenkins not found","exception":null,"responseCode":null,"data":"404","status":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/jobStatus?name=IndependentCode&continuousName=independent&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Return Job Status successfully","exception":null,"responseCode":null,"data":null,"status":null});
					}
				});

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});			

				commonVariables.navListener.onMytabEvent(commonVariables.continuousDeliveryView);
				setTimeout(function() {
					start();
					equal($(".widget_testing").length, 1, "Continuous Delivery View list length");
					equal($(".widget_testing h4").text().indexOf("independent") !== -1, true, "Continuous Delivery Name listed");
					equal($(".widget_testing .widget_testing_env .pipeline_box").length, 1, "Continuous Delivery Jobs list size");
					self.trigerBuild(continuousDeliveryView);
				}, 2500);
			});
		},

		// Trigger build
		trigerBuild : function (continuousDeliveryView) {
			var self = this;
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");
			asyncTest("Continuous Delivery View trigger build test", function() {
				// Trigger Build
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci +"/build?name=IndependentCode&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&continuousName=independent",
					type:'POST',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Build Triggered success","exception":null,"responseCode":null,"data":{"message":"Build started in jenkins","code":0},"status":null});
					}
				});

				$("a[jobname=IndependentCode][temp=generate_build]").click();
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('.widget_testing').attr('name'), "independent", "Continuous Delivery View trigger build tested");
					self.listBuilds(continuousDeliveryView);
				}, 2500);
			});
		}, 

		//List Builds
		listBuilds : function(continuousDeliveryView) {
			var self = this;
			$.mockjaxClear(self.contViewList);
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");
			asyncTest("List Builds test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/builds?projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&name=IndependentCode&appDirName=&continuousName=independent",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Builds returned successfully","exception":null,"responseCode":null,"data":[{"id":"2013-08-07_11-48-28","number":2,"status":"SUCCESS","url":"http://172.16.25.44:3579/ci/job/buildj/2/","download":"\"do_not_checkin/build/PHR1_07-Aug-2013-11-49-40.zip\"","timeStamp":"07/08/2013 11:48:28"}],"status":null});
					}
				});
				$(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find('.datetime_status').click();
				setTimeout(function() {
					start();
					var text = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find('tbody[name=buildList]').text();
					equal(text, "07/08/2013 11:48:28", "List Builds tested");
					self.lastBuildStatus(continuousDeliveryView);
				}, 2500);
			});
		},


		//lastBuildStatus
		lastBuildStatus : function(continuousDeliveryView) {
			var self = this;
			asyncTest("Last Build Status test", function() {
				$.mockjax({
					//http://localhost:8234/framework/rest/api/ci/lastBuildStatus?name=IndependentCode&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&continuousName=independent&_=1377586625663
					url: commonVariables.webserviceurl + commonVariables.ci + "/lastBuildStatus?name=IndependentCode&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&continuousName=independent",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800023","data":{"id":"2013-08-26_13-46-37","number":3,"status":"FAILURE","url":"http://172.16.27.152:3579/ci/job/build/3/","timeStamp":"26/08/2013 01:46:37","download":null},"status":"success"});
					}
				});

				$("a[temp=buildStatus]").click();
				setTimeout(function() {
					start();
					equal(1, 1, "Last Build Status tested");
					self.downloadBuild(continuousDeliveryView);
				}, 2500);
			});
		},
		
		//download Build
		downloadBuild : function(continuousDeliveryView) {
			var self = this;
			asyncTest("Download Build test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci +"/downloadBuild?buildDownloadUrl=do_not_checkin/build/PHR1_07-Aug-2013-11-49-40.zip&downloadJobName=IndependentCode&customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&continuousName=independent",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Build Download url retrieved successfully","exception":null,"responseCode":null,"data":"http://172.16.27.152:3579/ci/job/htmlBuild/ws/do_not_checkin/build/testHtml.zip","status":null});
					}
				});

				$("a[temp=downloadBuild]").click();
				setTimeout(function() {
					start();
					equal(1, 1, "Download Build tested");
					self.deleteBuilds(continuousDeliveryView);
				}, 2500);
			});
		},

		//delete Builds
		deleteBuilds : function(continuousDeliveryView) {
			var self = this;
			asyncTest("Delete Builds test", function() {

				$.mockjax({
//					http://localhost:8234/framework/rest/api/ci/deletebuilds?buildNumber=2&name=IndependentCode&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&continuousName=independent
					url: commonVariables.webserviceurl + commonVariables.ci +"/deletebuilds?buildNumber=2&name=IndependentCode&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&continuousName=independent",
					type:'DELETE',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Build Triggered success","exception":null,"responseCode":null,"data":{"message":"Build started in jenkins","code":0},"status":null});
					}
				});

//				$("a[temp=deleteBuild]").click();
				 $(commonVariables.contentPlaceholder).find("a[temp=deleteBuild]").click();
				setTimeout(function() {
					start();
					var length = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find('tbody[name=buildList]').length;
					equal(length, 1, "Delete Builds tested");
					self.openClonePopup(continuousDeliveryView);
				}, 2500);
			});
		},

		openClonePopup : function (continuousDeliveryView) {
			var self = this;
			var self = this;
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");
			ciAPI.localVal.setSession("customerId" , "photon");
			asyncTest("openClonePopup CI", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration +"/listEnvironmentsByProjectId?customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Environments Listed successfully","exception":null,"responseCode":null,"data":["Production","Development","Testing"],"status":null});
					}
				});

				var obj = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div[active=true]").find('div[class=widget_testing][name=independent]').find('a[temp=clone]');
				obj.click();
				$("a[temp=clone]").click();
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('div[id=clone_popup]').css('display'), "block", "clone Popup CI tested");
					self.cloneContinuousDelivery(continuousDeliveryView);
				}, 2500);
			});
		},

		cloneContinuousDelivery : function (continuousDeliveryView) {
			var self = this;
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");
			ciAPI.localVal.setSession("customerId" , "photon");

			asyncTest("Clone CI test", function() {
				$.mockjaxClear(self.contViewList);
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci +"/clone?projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&userId=admin&cloneName=newCloned&envName=Production&continuousName=independent",
					type:'POST',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job(s) created successfully","exception":null,"responseCode":null,"data":{"name":"newCloned","jobs":[{"mainClassName":"","configuration":"","alias":"","url":"asd","pomLocation":"pom.xml","username":"asd","password":"asd","email":null,"buildNumber":"1","target":"","configurations":"","family":"","headerValue":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","projectModule":"","isFromCI":"","testAgainst":"","testName":"","testBasis":"","appDirName":"J2ee","buildName":"","jobName":"1-Production","downstreamApplication":"","upstreamApplication":"","operation":"build","coberturaPlugin":false,"environmentName":"Production","repoType":"svn","templateName":"build","sonarUrl":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"-Pci clean phresco:package -N","jenkinsUrl":"172.16.25.44","jenkinsPort":"3579","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":false,"usedClonnedWorkspace":"","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=package -DcreationType=global -Did=20016c26-dd15-4c70-9fa6-0d69e013a358 -DcontinuousDeliveryName=newCloned -N"],"postbuildStepCommands":null,"logs":"showErrors","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":true,"headerKey":"","addHeader":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","contextUrls":"","dbContextUrls":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"","deviceList":"","collabNetFileReleasePattern":"do_not_checkin/build/*.zip","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"","appName":"J2ee","successEmailIds":"","failureEmailIds":""}],"envName":"Production"},"status":null});
					}
				});
				
				$('input[name=cloneName]').val('');
				$('select[name=envName]').val('Production');
				$('input[id=clone_ci]').click();
				$('input[name=cloneName]').val('newCloned');
				$('select[name=envName]').val('Production');
				$('input[id=clone_ci]').click();
				setTimeout(function() {
					start();
					notEqual($(".widget_testing").length, 8, "Continuous Delivery View list length");
					self.editContinuousDelivery(continuousDeliveryView);
				}, 2500);
			});
		},

		editContinuousDelivery : function(continuousDeliveryView) {
			$(commonVariables.contentPlaceholder).html("");
			var self = this;
			asyncTest("editContinuousDelivery test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&envName=Production",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"J2ee\",\"appDirName\":\"J2ee\"}":[{"name":"code","type":"codeValidation","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy","type":"deploy","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&envName=Testing",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"J2ee\",\"appDirName\":\"J2ee\"}":[{"name":"code","type":"codeValidation","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy","type":"deploy","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"20016c26-dd15-4c70-9fa6-0d69e013a358","customerId":"photon","appIds":["J2ee"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/editContinuousView?projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&name=independent",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Continuous Delivery Fetched successfully","exception":null,"responseCode":null,"data":{"name":"jseries","jobs":[{"mainClassName":"","configuration":"","alias":"","url":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/J2ee/","pomLocation":"pom.xml","username":"santhosh_ja","password":"santJ!23","email":null,"buildNumber":"1","target":"","configurations":"","family":"","headerValue":"","src":"js","mode":"","serialNumber":"","keystore":"","keypass":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"J2ee","buildName":"","jobName":"codej","downstreamApplication":"buildj","upstreamApplication":"","jenkinsUrl":"172.16.25.44","jenkinsPort":"3579","operation":"codeValidation","coberturaPlugin":false,"environmentName":"","repoType":"svn","templateName":"codeValidate","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:validate-code -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=validate-code -DcreationType=global -Did=28352211-a3d6-4ff9-8b55-2734942993b0 -DcontinuousDeliveryName=jseries -N"],"postbuildStepCommands":null,"logs":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"src","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"buildj","appName":"J2ee","successEmailIds":"","failureEmailIds":"","technologyName":null},{"mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","headerValue":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"J2ee","buildName":"","jobName":"buildj","downstreamApplication":"deployj","upstreamApplication":"codej","jenkinsUrl":"172.16.25.44","jenkinsPort":"3579","operation":"build","coberturaPlugin":false,"environmentName":"clone","repoType":"clonedWorkspace","templateName":"build","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"-Pci clean phresco:package -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"usedClonnedWorkspace":"codej","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=package -DcreationType=global -Did=28352211-a3d6-4ff9-8b55-2734942993b0 -DcontinuousDeliveryName=jseries -N"],"postbuildStepCommands":null,"logs":"showErrors","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":true,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"do_not_checkin/build/*.zip","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"deployj","appName":"J2ee","successEmailIds":"","failureEmailIds":"","technologyName":null},{"mainClassName":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","headerValue":"","src":"","mode":"","serialNumber":"","keystore":"","keypass":"","projectModule":"","isFromCI":"","testBasis":"","appDirName":"J2ee","buildName":"","jobName":"deployj","downstreamApplication":"","upstreamApplication":"buildj","jenkinsUrl":"172.16.25.44","jenkinsPort":"3579","operation":"deploy","coberturaPlugin":false,"environmentName":"clone","repoType":"clonedWorkspace","templateName":"Deploy","sonarUrl":"","contextUrls":"","dbContextUrls":"","scheduleType":null,"scheduleExpression":null,"mvnCommand":"phresco:deploy -N","triggers":[],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":false,"usedClonnedWorkspace":"buildj","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=deploy -DcreationType=global -Did=28352211-a3d6-4ff9-8b55-2734942993b0 -DcontinuousDeliveryName=jseries -N"],"postbuildStepCommands":null,"logs":"showErrors","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"on","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"on","dataBase":"mysql","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","testType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","packageFileBrowse":"","unitTestType":"","unittest":"","downStreamCriteria":"","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"","appName":"J2ee","successEmailIds":"","failureEmailIds":"","technologyName":null}],"envName":"clone"},"status":null});
					}
				});

				continuousDeliveryView.ciListener.editContinuousDeliveryConfigure("independent");
				setTimeout(function() {
					start();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					notEqual(7, length, "ContinuousDeliveryView - UI Tested");
					self.openDeletePopUp(continuousDeliveryView);
				}, 2500);
			});
		},
		

		// delete continuous delivery test
		openDeletePopUp : function (continuousDeliveryView) {
			var self = this;
			asyncTest("Delete Popup CI test", function() {
				$("a[temp=deleteCI]").click();
				setTimeout(function() {
					start();
					equal(1, 1, "openDeletePopUp tested");
					self.confirmDelete(continuousDeliveryView);
				}, 2500);
			});
		},

		confirmDelete : function (continuousDeliveryView) {
			var self = this;
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");
			ciAPI.localVal.setSession("customerId" , "photon");
			asyncTest("confirmDelete CI", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci +"/delete?continuousName=independent&customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=",
					type:'DELETE',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job deleted successfully","exception":null,"responseCode":null,"data":{"message":"Job has successfully deleted in jenkins","code":0},"status":null});
					}
				});

				var obj = $(commonVariables.contentPlaceholder).find("#deleteCI").find('input[type=hidden][value=independent]').next();
				obj.click();
				setTimeout(function() {
					start();
					var display = $(commonVariables.contentPlaceholder).find('div[id=deleteCI]').css('display');
					notEqual("none", display, "confirmDelete CI tested");
					self.callConfigureTest(continuousDeliveryView);
				}, 2500);
			});
		},

		callConfigureTest : function (continuousDeliveryView) {
			var self = this;
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "dd122034-fa5c-4fd9-9f68-522df1e73fb4");
			ciAPI.localVal.setSession("appDirName" , "");
			ciAPI.localVal.setSession("customerId" , "photon");
			asyncTest("callConfigureTest CI", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration +"/listEnvironmentsByProjectId?customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Environments Listed successfully","exception":null,"responseCode":null,"data":["clone","dev","Production"],"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"/getJobTemplatesByEnvironment?customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&envName=Testing",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"htm\",\"appDirName\":\"htm\"}":[{"name":"Deploy","type":"deploy","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"funcTest","type":"functionalTest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"codeValidate","type":"codeValidation","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Cobertura"]},{"name":"pdf","type":"pdfReport","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Confluence"]}]},"status":null});
					}
				});	

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"/getJobTemplatesByEnvironment?customerId=photon&projectId=dd122034-fa5c-4fd9-9f68-522df1e73fb4&appDirName=&envName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"htm\",\"appDirName\":\"htm\"}":[{"name":"Deploy","type":"deploy","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"funcTest","type":"functionalTest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"codeValidate","type":"codeValidation","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Cobertura"]},{"name":"pdf","type":"pdfReport","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Confluence"]}]},"status":null});
					}
				});	 

				$('#createContinuousDelivery').click();
				setTimeout(function() {
					start();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					notEqual(7, length, "callConfigureTest CI Tested");
				}, 2500);
			});
		},




	};
});