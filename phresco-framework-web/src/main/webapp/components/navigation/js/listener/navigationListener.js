define([], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		localStorageAPI : null,
		header : null,
		footer : null,
		projectlist : null,
		addproject : null,
		editApplication : null,
		featurelist : null,
		codequality : null,
		configuration : null, 
		build : null,
		currentTab : null,
		manualTest : null,
		editproject : null,
		settings : null,
		editProjectSettings : null,
		projectSettings : null,
		downloads : null,
		dashboard : null,
		unitTest : null,
		functionalTest : null,
		componentTest : null,
		integrationTest : null,
		performanceTest : null,
		loadTest : null,
		testsuiteResult : null,
		testcaseResult : null,
		dynamicpage : null,
		editConfiguration : null,
		jobTemplates : null,
		continuousDeliveryView : null,
		mavenService : null,
		continuousDeliveryConfigure : null,
		act:null,
		sourceRepo : null,
		buildRepo : null,
		tridiongeneral : null,
		tridionpublish : null,
		zapmenu : null,
		seo : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		onAddProject : function() {
			var self = this;
			self.getMyObj(commonVariables.addproject, function(addProjectObj){
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(addProjectObj, commonVariables.animation);
			});
		},
		
		landingPage : function(currentContent){
			var self = this;
			commonVariables.continueloading = true;
			self.renderHeader(function(retVal){
				if(currentContent === undefined || currentContent === null){
					self.renderFooter(function(retVal){
						self.renderContent(function(retVal){
							commonVariables.continueloading = false;
						});
					});
				} else if(currentContent !== undefined && currentContent !== null && currentContent !== "") {
					self.renderFooter(function(retVal){
						self.dynamicContent(currentContent, function(retVal){
							commonVariables.continueloading = false;
						});
					});
				}
			});
		},

		getMyObj : function(keyword, callback) {
			var self=this, retuenObj, objInfo = "";
				switch(keyword){
				
					case commonVariables.header :

						if(self.header === null){
							require(["header/header"], function(){
								self.header = new Clazz.com.commonComponents.modules.header.js.Header();
								callback(self.header);	
							});
						}else{
							callback(self.header);
						}
						
						break;
						
					case commonVariables.footer :
					
						if(self.footer === null){
							require(["footer/footer"], function(){
								self.footer = new Clazz.com.commonComponents.modules.footer.js.Footer();
								callback(self.footer);	
							});
						}else{
							callback(self.footer);
						}
					
						break;
						
					case commonVariables.projectlist :
						
						if(self.projectlist === null){
							require(["projectlist/projectList"], function(){
								self.projectlist = new Clazz.com.components.projectlist.js.ProjectList();
								callback(self.projectlist);	
							});
						}else{
							callback(self.projectlist);
						}

						break;
						
					case commonVariables.editApplication :
						
						if(self.editApplication === null){
							require(["application/application"], function(){
								self.editApplication = new Clazz.com.components.application.js.Application();
								callback(self.editApplication);	
							});
						}else{
							callback(self.editApplication);
						}

						break;
						
					case commonVariables.featurelist :
					
						if(self.featurelist === null){
							require(["features/features"], function(){
								self.featurelist = new Clazz.com.components.features.js.Features();
								callback(self.featurelist);	
							});
						}else{
							callback(self.featurelist);
						}

						break;
						
					case commonVariables.codequality :
						
						if(self.codequality === null){
							require(["codequality/codequality"], function(){
								self.codequality = new Clazz.com.components.codequality.js.CodeQuality();
								callback(self.codequality);	
							});
						}else{
							callback(self.codequality);
						}

						break;
						
					case commonVariables.configuration :
						
						if(self.configuration === null){
							require(["configuration/configuration"], function(){
								self.configuration = new Clazz.com.components.configuration.js.Configuration();
								callback(self.configuration);	
							});
						}else{
							callback(self.configuration);
						}

						break;
						
					case commonVariables.build :
						
						if(self.build === null){
							require(["build/build"], function(){
								self.build = new Clazz.com.components.build.js.Build();
								callback(self.build);	
							});
						}else{
							callback(self.build);
						}

						break;
						
					case commonVariables.addproject :
						
						if(self.addproject === null){
							require(["projects/addproject"], function(){
								self.addproject = new Clazz.com.components.projects.js.addProject();
								callback(self.addproject);	
							});
						}else{
							callback(self.addproject);
						}
						
						break;
					
					case commonVariables.editproject :
						
						if(self.editproject === null){
							require(["projects/editproject"], function(){
								self.editproject = new Clazz.com.components.projects.js.EditProject();
								callback(self.editproject);	
							});
						}else{
							callback(self.editproject);
						}
						
						break;
						
					case commonVariables.unitTest :
						
						if(self.unitTest === null){
							require(["unitTest/unitTest"], function() {
								self.unitTest = new Clazz.com.components.unitTest.js.UnitTest();
								callback(self.unitTest);	
							});
						}else{
							callback(self.unitTest);
						}
						
						break;
						
					case commonVariables.componentTest :
						
						if (self.componentTest === null) {
							require(["componentTest/componentTest"], function() {
								self.componentTest = new Clazz.com.components.componentTest.js.ComponentTest();
								callback(self.componentTest);	
							});
						}else{
							callback(self.componentTest);
						}
						
						break;
						
					case commonVariables.functionalTest :
						
						if(self.functionalTest === null){
							require(["functionalTest/functionalTest"], function() {
								self.functionalTest = new Clazz.com.components.functionalTest.js.FunctionalTest();
								callback(self.functionalTest);	
							});
						}else{
							callback(self.functionalTest);
						}
						
						break;

					case commonVariables.integrationTest :
						if (self.integrationTest === null) {
							require(["integrationTest/integrationTest"], function() {
								self.integrationTest = new Clazz.com.components.integrationTest.js.IntegrationTest();
								callback(self.integrationTest);	
							});
						}else{
							callback(self.integrationTest);
						}
						
						break;
						
					case commonVariables.dashboard :
						if(self.dashboard === null) {
							require(["dashboard/dashboard"], function() {
								self.dashboard = new Clazz.com.components.dashboard.js.Dashboard();
								callback(self.dashboard);	
							});
						}else{
							callback(self.dashboard);
						}	
						
						break;
						
					case commonVariables.testsuiteResult :
						if (self.testsuiteResult === null) {
							require(["testResult/testsuite"], function() {
								self.testsuiteResult = new Clazz.com.components.testResult.js.Testsuite();
								callback(self.testsuiteResult);	
							});
						} else {
							callback(self.testsuiteResult);
						}
						
						break;
						
					case commonVariables.testcaseResult :
						
						if (self.testcaseResult === null) {
							require(["testResult/testcase"], function() {
								self.testcaseResult = new Clazz.com.components.testResult.js.Testcase();
								callback(self.testcaseResult);	
							});
						} else {
							callback(self.testcaseResult);
						}
						
						break;
						
					case commonVariables.performanceTest : 
						if (self.performanceTest === null) {
							require(["performanceTest/performanceTest"], function() {
								self.performanceTest = new Clazz.com.components.performanceTest.js.PerformanceTest();
								callback(self.performanceTest);	
							});
						} else {
							callback(self.performanceTest);	
						}
						break;
					
					case commonVariables.loadTest : 
						if (self.loadTest === null) {
							require(["loadTest/loadTest"], function() {
								self.loadTest = new Clazz.com.components.loadTest.js.LoadTest();
								callback(self.loadTest);	
							});
						} else {
							callback(self.loadTest);	
						}
						break;

					case commonVariables.editConfiguration :
						
						if(self.editConfiguration === null){
							require(["configuration/editConfiguration"], function(){
								self.editConfiguration = new Clazz.com.components.configuration.js.EditConfiguration();
								callback(self.editConfiguration);	
							});
						}else{
							callback(self.editConfiguration);
						}
						
						break;

					case commonVariables.dynamicPage :
						
						if(self.dynamicpage === null){
							require(["dynamicPage/dynamicPage"], function(){
								self.dynamicpage = new Clazz.com.components.dynamicPage.js.DynamicPage();
								callback(self.dynamicpage);	
							});
						}else{
							callback(self.dynamicpage);
						}
						
						break;

					case commonVariables.jobTemplates :
						
						if(self.jobTemplates === null){
							require(["ci/jobTemplates"], function(){
								self.jobTemplates = new Clazz.com.components.ci.js.JobTemplates();
								callback(self.jobTemplates);	
							});
						}else{
							callback(self.jobTemplates);
						}
						
						break;

					case commonVariables.continuousDeliveryConfigure :
						
						if(self.continuousDeliveryConfigure === null){
							require(["ci/continuousDeliveryConfigure"], function(){
								self.continuousDeliveryConfigure = new Clazz.com.components.ci.js.ContinuousDeliveryConfigure();
								callback(self.continuousDeliveryConfigure);	
							});
						}else{
							callback(self.continuousDeliveryConfigure);
						}
						
						break;

					case commonVariables.continuousDeliveryView :
						
						if(self.continuousDeliveryView === null){
							require(["ci/continuousDeliveryView"], function(){
								self.continuousDeliveryView = new Clazz.com.components.ci.js.ContinuousDeliveryView();
								callback(self.continuousDeliveryView);	
							});
						}else{
							callback(self.continuousDeliveryView);
						}
						
						break;
						
					case commonVariables.mavenService :
					
						if(self.mavenService === null){
							require(["mavenService/listener/mavenServiceListener"], function(){
								self.mavenService = new Clazz.com.components.mavenService.js.listener.MavenServiceListener();
								callback(self.mavenService);	
							});
						}else{
							callback(self.mavenService);
						}
					
						break;
						
					case commonVariables.manualTest :
						
						if (self.manualTest === null) {
							require(["manualTest/manualTest"], function() {
								self.manualTest = new Clazz.com.components.manualTest.js.ManualTest();
								callback(self.manualTest);	
							});
						}else{
							callback(self.manualTest);
						}
						
						break;
						
					case commonVariables.settings :
						
						if (self.settings === null) {
							require(["settings/settings"], function() {
								self.settings = new Clazz.com.components.settings.js.Settings();
								callback(self.settings);	
							});
						}else{
							callback(self.settings);
						}
						
						break;

					case commonVariables.downloads :
						if (self.downloads === null) {
							require(["downloads/downloads"], function() {
								self.downloads = new Clazz.com.components.downloads.js.Downloads();
								callback(self.downloads);	
							});
						}else{
							callback(self.downloads);
						}
						
						break;
						
					case commonVariables.projectSettings :
						if(self.projectSettings === null) {
							require(["configuration/settings"], function(){
								self.projectSettings = new Clazz.com.components.configuration.js.ProjectSettings();
								callback(self.projectSettings);	
							});
						}else{
							callback(self.projectSettings);
						}
						
						break;
						
					case commonVariables.editprojectSettings :
						if(self.editProjectSettings === null) {
							require(["configuration/editSettings"], function(){
								self.editProjectSettings = new Clazz.com.components.configuration.js.EditSettings();
								callback(self.editProjectSettings);	
							});
						}else{
							callback(self.editProjectSettings);
						}
						
						break;

					case commonVariables.buildRepo :
						if(self.buildRepo === null) {
							require(["repository/buildRepository"], function(){
								self.buildRepo = new Clazz.com.components.repository.js.BuildRepository();
								callback(self.buildRepo);	
							});
						}else{
							callback(self.buildRepo);
						}
						
						break;
						
					case commonVariables.sourceRepo :
						if(self.sourceRepo === null) {
							require(["repository/sourceRepository"], function(){
								self.sourceRepo = new Clazz.com.components.repository.js.SourceRepository();
								callback(self.sourceRepo);	
							});
						}else{
							callback(self.sourceRepo);
						}
						
						break;
						
					case commonVariables.tridiongeneral :
						if(self.tridiongeneral === null) {
							require(["tridiongeneral/tridiongeneral"], function(){
								self.tridiongeneral = new Clazz.com.components.tridiongeneral.js.tridiongeneral();
								callback(self.tridiongeneral);	
							});
						}else{
							callback(self.tridiongeneral);
						}
						
						break;						
											
					case commonVariables.tridionpublish :
						if(self.tridionpublish === null) {
							require(["tridionpublish/tridionpublish"], function(){
								self.tridionpublish = new Clazz.com.components.tridionpublish.js.tridionpublish();
								callback(self.tridionpublish);	
							});
						}else{
							callback(self.tridionpublish);
						}
						
						break;						
					
					case commonVariables.zapMenu :
						if(self.zapmenu === null) {
							require(["zapmenu/zapmenu"], function(){
								self.zapmenu = new Clazz.com.components.zapmenu.js.zapmenu();
								callback(self.zapmenu);	
							});
						}else{
							callback(self.zapmenu);
						}
						
						break;						
										
					case commonVariables.seo :
						if(self.seo === null) {
							require(["seo/seo"], function(){
								self.seo = new Clazz.com.components.seo.js.seo();
								callback(self.seo);	
							});
						}else{
							callback(self.seo);
						}
						
						break;						
					
				}
		},

		//To show/hide controls based on the component 
		showHideControls : function(keyword) {
			var self = this;
			switch(keyword) {
				case commonVariables.projectlist :
					$("#projectList").show();
					$("#createProject").hide();
					$("#settingsNav").hide();
					$("#downloadsNav").hide();
					self.applyRBAC(keyword);
					break;
					
				case commonVariables.editApplication :
					$("#applicationedit").show();
					$("#settingsNav").hide();
					$("#downloadsNav").hide();
					if (self.isBlank(commonVariables.editAppFrom) && commonVariables.editAppHasModules) {
						$("li[name=editMenu]").not("#featurelist").not("#configuration").hide();
//					} else if (commonVariables.editAppFrom === "multimodule") {
//						$("li[name=editMenu]").not(".continuousDeliveryView").show();
					} else {
						$("li[name=editMenu]").show();
					}
					
					break;
					
				case commonVariables.settings :
					$("#settingsNav").show();
					$("#downloadsNav").hide();
					$("#projectList").hide();
					$("#createProject").hide();
					$("#applicationedit").hide();
					$('#editprojectTab').hide();
					break;

				case commonVariables.downloads :
					$("#downloadsNav").show();
					$("#settingsNav").hide();
					$("#projectList").hide();
					$("#createProject").hide();
					$("#applicationedit").hide();
					$('#editprojectTab').hide();
					self.applyRBAC(keyword);
					break;
			}
		},
		
		//To apply the RBAC to the users
		applyRBAC : function(keyword) {
			var self = this;
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			if(userPermissions){
				switch(keyword) {
					case commonVariables.projectlist :
						if (!userPermissions.importApplication && !userPermissions.manageApplication) {
							$("#importApp").prop("disabled", true);
						}
						if (!userPermissions.manageApplication) {
							$("#addproject").prop("disabled", true);
						}
						break;
						
					case commonVariables.configuration :
						if (!userPermissions.manageConfiguration) {
							$("input[name=env_pop]").prop("disabled", true);
						}
						break;
				}
			}
		},
		
		showHideParentOptions : function() {
			var self = this;
			var applicableOptions = JSON.parse(commonVariables.api.localVal.getSession('applicableOptions'));
			//console.info('applicableOptions = ' , applicableOptions);
			if (jQuery.inArray(commonVariables.optionsParentCode, applicableOptions) === -1) {
				$("#codequality").hide();
			} else {
				$("#codequality").show();
			}
			if (jQuery.inArray(commonVariables.optionsParentBuild, applicableOptions) === -1) {
				$("#build").hide();
			} else {
				$("#build").show();
			}

			if (jQuery.inArray(commonVariables.optionsParentDeploy, applicableOptions) === -1) {
				$("table th[name=buildDep]").hide();
			} else {
				$("table th[name=buildDep]").show();
			}

			if (jQuery.inArray(commonVariables.optionsParentUnitTest, applicableOptions) === -1) {
				$(".testmenu").hide();
				$("#unitTest").hide();
			} else {
				$(".testmenu").show();
				$("#unitTest").show();
				$("#componentTest").hide();
				$("#functionalTest").hide();
				$("#performanceTest").hide();
				$("#loadTest").hide();
				$("#manualTest").hide();
				$("#zapMenu").hide();
				$("#SEO").hide();
				$("#W3CMenu").hide();
			}
			
		},
		
		//To show hide the options like build, deploy based on the applicable options for the technology
		showHideTechOptions : function() {
			var self = this;
			var applicableOptions = JSON.parse(commonVariables.api.localVal.getSession('applicableOptions'));
			if (jQuery.inArray(commonVariables.optionsCode, applicableOptions) === -1) {
				$("#codequality").hide();
			} else {
				$("#codequality").show();
			}
			if (jQuery.inArray(commonVariables.optionsReports, applicableOptions) === -1) {
				$("#mavenReport").hide();
			} else {
				$("#mavenReport").show();
			}
			if (jQuery.inArray(commonVariables.optionsUnitTest, applicableOptions) === -1) {
				$("#unitTest").hide();
			} else {
				$("#unitTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsComponentTest, applicableOptions) === -1) {
				$("#componentTest").hide();
			} else {
				$("#componentTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsFunctionalTest, applicableOptions) === -1) {
				$("#functionalTest").hide();
			} else {
				$("#functionalTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsPerformanceTest, applicableOptions) === -1) {
				$("#performanceTest").hide();
			} else {
				$("#performanceTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsLoadTest, applicableOptions) === -1) {
				$("#loadTest").hide();
			} else {
				$("#loadTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsManualTest, applicableOptions) === -1) {
				$("#manualTest").hide();
			} else {
				$("#manualTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsCI, applicableOptions) === -1) {
				$(".continuousDeliveryView").hide();
			} else if (commonVariables.editAppFrom === "multimodule") {
				$(".continuousDeliveryView").show();
			}
			if (jQuery.inArray(commonVariables.optionsRunAgainstSrc, applicableOptions) === -1) {
				$("input[name=build_runagsource]").hide();
				$("#stop").hide();
				$("#restart").hide();
			} else {
				$("input[name=build_runagsource]").show();
				$("#stop").show();
				$("#restart").show();
			}
			if (jQuery.inArray(commonVariables.optionsMinification, applicableOptions) === -1) {
				$("#minifier").hide();
			} else {
				$("#minifier").show();
			}
			if (jQuery.inArray(commonVariables.optionsBuild, applicableOptions) === -1) {
				$('input[name=build_genbuild]').hide();
			} else {
				$('input[name=build_genbuild]').show();
			}
			if (jQuery.inArray(commonVariables.optionsDeploy, applicableOptions) === -1) {
				$("table th[name=buildDep]").hide();
				$("table td[name=buildDep]").hide();
			} else {
				$("table th[name=buildDep]").show();
				$("table td[name=buildDep]").show();
			}
			
			if (jQuery.inArray(commonVariables.optionsExeDownload, applicableOptions) === -1) { 
				$('img[name=ipaDownload]').hide();
			} else {
				$('img[name=ipaDownload]').show();
			}
			
			if (jQuery.inArray(commonVariables.optionsTridioninfo, applicableOptions) === -1) {
				$("#tridiongeneral").hide();
			} else {
				$("#tridiongeneral").show();
			}
			
			if (jQuery.inArray(commonVariables.optionsTridionpublish, applicableOptions) === -1) {
				$("#tridionpublish").hide();
			} else {
				$("#tridionpublish").show();
			}

			if (jQuery.inArray(commonVariables.optionsZap, applicableOptions) === -1) {
				$("#zapMenu").hide();
			} else {
				$("#zapMenu").show();
			}

			if (jQuery.inArray(commonVariables.optionsSEO, applicableOptions) === -1) {
				$("#SEO").hide();
			} else {
				$("#SEO").show();
			}

			if (jQuery.inArray(commonVariables.optionsW3C, applicableOptions) === -1) {
				$("#W3CMenu").hide();
			} else {
				$("#W3CMenu").show();
			}

			if (jQuery.inArray(commonVariables.optionsProcessBuild, applicableOptions) === -1) {
				//process build
				$("table th[name=prcBuild]").hide();
				$("table td[name=prcBuild]").hide();
			} else {
				$("table th[name=prcBuild]").show();
				$("table td[name=prcBuild]").show();

			}
			if (jQuery.inArray(commonVariables.optionsRemoteDeployment, applicableOptions) === -1) {

			} else {
				
			}
			if (jQuery.inArray(commonVariables.optionsEmbedApplication, applicableOptions) === -1) {

			} else {
				
			}
			if (jQuery.inArray(commonVariables.optionsThemeBuilder, applicableOptions) === -1) {

			} else {
				
			}
		},
		
		//Handles the open folder action
		openFolder : function(actionBody) {
			var self = this;
			self.navigationAction(self.getActionHeader(actionBody, "openFolder"), function(response) {});
		},
		
		//Handles the copy path action
		copyPath : function(actionBody) {
			var self = this;
			self.navigationAction(self.getActionHeader(actionBody, "copyPath"), function(response) {
				if (response !== undefined && "success"===response.status ) {
					commonVariables.api.showError(commonVariables.api.success["PHR4C00001"], 'success', true, true, false);
				} else {
					commonVariables.api.showError(commonVariables.api.error["PHR4C00002"], 'error', true, true, false);
				}
			});
		},
		
		renderHeader : function(callback) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.headerPlaceholder;
			self.getMyObj(commonVariables.header, function(returnVal){
				self.header.data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				Clazz.navigationController.push(self.header, false);
				callback(true);
			});
		},
		
		renderContent : function(callback){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.header.headerListener.currentTab = "Projects";
			self.getMyObj(commonVariables.projectlist, function(returnVal){
				self.currentTab = commonVariables.projectlist;
				Clazz.navigationController.push(self.projectlist, commonVariables.animation);
				callback(true);
			});
		},
		
		dynamicContent : function(contentObj, callback){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			
			self.getMyObj(contentObj, function(returnVal){
				self.currentTab = contentObj;
				Clazz.navigationController.push(returnVal, commonVariables.animation);
				callback(true);
			});
		},
		
		renderFooter : function(callback){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.footerPlaceholder;
			self.getMyObj(commonVariables.footer, function(returnVal){
				Clazz.navigationController.push(self.footer, false);
				callback(true);
			});
		},
		
		onMytabEvent : function(keyword) {
			var self = this, currentObj;
			if (self.currentTab !== commonVariables.editApplication && keyword === commonVariables.editApplication){
				self.getMyObj(commonVariables.editApplication, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				}); 
			} else if (self.currentTab !== commonVariables.featurelist && keyword === commonVariables.featurelist) {
				self.getMyObj(commonVariables.featurelist, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				}); 
			} else if (self.currentTab !== commonVariables.codequality && keyword === commonVariables.codequality) {
				self.getMyObj(commonVariables.codequality, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				}); 
			} else if (self.currentTab !== commonVariables.configuration && keyword === commonVariables.configuration) {
				self.getMyObj(commonVariables.configuration, function(returnVal){
					currentObj = returnVal;
					currentObj.envSpecific = true;
					currentObj.favourite = false;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.build && keyword === commonVariables.build) {
				self.getMyObj(commonVariables.build, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.jobTemplates && keyword === commonVariables.jobTemplates) {
				self.getMyObj(commonVariables.jobTemplates, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.continuousDeliveryView && keyword === commonVariables.continuousDeliveryView) {
				self.getMyObj(commonVariables.continuousDeliveryView, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.continuousDeliveryConfigure && keyword === commonVariables.continuousDeliveryConfigure) {
				self.getMyObj(commonVariables.continuousDeliveryConfigure, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  else if (self.currentTab !== commonVariables.tridiongeneral && keyword === commonVariables.tridiongeneral) {
				self.getMyObj(commonVariables.tridiongeneral, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  else if (self.currentTab !== commonVariables.tridionpublish && keyword === commonVariables.tridionpublish) {
				self.getMyObj(commonVariables.tridionpublish, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.unitTest) {
				self.getMyObj(commonVariables.unitTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.functionalTest) {
				self.getMyObj(commonVariables.functionalTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.componentTest) {
				self.getMyObj(commonVariables.componentTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.performanceTest) {
				self.getMyObj(commonVariables.performanceTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  else if (keyword === commonVariables.loadTest) {
				self.getMyObj(commonVariables.loadTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.integrationTest) {
				self.getMyObj(commonVariables.integrationTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.manualTest) {
				self.getMyObj(commonVariables.manualTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  else if (keyword === commonVariables.editproject) {
				self.getMyObj(commonVariables.editproject, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  else if (keyword === commonVariables.dashboard) {
				self.getMyObj(commonVariables.dashboard, function(returnVal){
					currentObj = returnVal;
					currentObj.clicked = false;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.projectSettings) {
				self.getMyObj(commonVariables.projectSettings, function(returnVal){
					currentObj = returnVal;
					currentObj.favourite = false;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.buildRepo) {		
				self.getMyObj(commonVariables.buildRepo, function(returnVal){
					currentObj = returnVal;
					currentObj.favourite = false;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.sourceRepo) {
				self.getMyObj(commonVariables.sourceRepo, function(returnVal){
					currentObj = returnVal;
					currentObj.favourite = false;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}	else if (self.currentTab !== commonVariables.zapMenu && keyword === commonVariables.zapMenu) {
				self.getMyObj(commonVariables.zapMenu, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  	else if (self.currentTab !== commonVariables.seo && keyword === commonVariables.seo) {
				self.getMyObj(commonVariables.seo, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  	
						

		},
		
		myTabRenderFunction : function(currentObj, keyword) {
			var self = this;
			if(currentObj !== undefined && currentObj !== null){
				self.currentTab = keyword;
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(currentObj, commonVariables.animation);
			}
		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self=this, header, data = {}, userId, appDirName='';
			var type = requestBody.type;
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			if (action === "openFolder" || action === "copyPath") {
				if (!self.isBlank(moduleParam)) {
					appDirName = $('.rootModule').val()
				} else if(commonVariables.api.localVal.getProjectInfo() !== null) {
					var projectInfo = commonVariables.api.localVal.getProjectInfo();
					appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
				}
			}
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			self.act=action;
			if (action === "openFolder") {
				header.requestMethod = "GET";
				if (type === "integrationTest") {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.openFolderContext + "?type=" + type + "&appDirName=" + commonVariables.projectCode +"-integrationTest" + moduleParam;
				} else {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.openFolderContext + "?type=" + type + "&appDirName=" + appDirName + moduleParam;
				}
			} else if (action === "copyPath") {
				header.requestMethod = "GET";
				if (type === "integrationTest") {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.copyPathContext + "?type=" + type + "&appDirName=" + commonVariables.projectCode +"-integrationTest" + moduleParam;
				} else {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.copyPathContext + "?type=" + type + "&appDirName=" + appDirName + moduleParam;
				}
			} else if(action === "importpost") {
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + "repository/importApplication?displayName="+displayName + "&userId=" + userInfo.name;
			} else if (action === "copyToClipboard") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.copyToClipboardContext;
				header.requestPostBody = requestBody.log;
			}
			return header;
		},
		
		navigationAction : function(header, callback) {
			var self = this;			
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
					
						//commonVariables.loadingScreen.removeLoading();
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							if(response.responseCode === "PHR200017") {
								commonVariables.api.showError(response.responseCode ,"success", true);
							}
							callback(response);						
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true);
						}
					},
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);		
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		}, 
		
		navigationActionForImport : function(header, callback) {
			var self = this;			
			try {
//				commonVariables.loadingScreen.showLoading();
				$("#importloading").hide();
				commonVariables.api.ajaxRequestForScm(header,
					function(response) {
//						commonVariables.loadingScreen.removeLoading();
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							if(response.responseCode === "PHR200017") {
								commonVariables.api.showError(response.responseCode ,"success", true);
							} else if(response.responseCode === "PHR210048") {
								commonVariables.api.showError(response.responseCode ,"success", true);
							}
							callback(response);						
						} else {
							if (commonVariables.callLadda) {
								Ladda.stopAll();
							}
							commonVariables.api.showError(response.responseCode ,"error", true);
							if(response.responseCode === 'PHR210050' || response.responseCode === 'PHR210049') {
								$("#importloading").hide();
							}
							
							if(response.responseCode === 'PHR210026') {
								commonVariables.api.showError("prjtexists" ,"error", true);
								$('.popuploading').hide();
							}
							$('#project_list_import').find('input, textarea, button, select').attr("disabled", false);
						}
					},
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);		
					}
				);
			} catch(exception) {
				commonVariables.loadingScreen.removeLoading();
			}
		}, 
		
		validateImport : function (callback) {
			var self = this;
			var hasError = false;
			var importType = $("#importType").val();
			if (importType === "git") {
				hasError = self.validateGitData($('#importRepourl'));
				if (hasError) {
					self.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				}
				if ($('#importDotPhrescoSrc').is(":checked") && !hasError) {
					hasError = self.validateGitData($('#importPhrescoRepourl'));
					if (hasError) {
						self.showDotPhrescoTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
				if ($('#importTestSrc').is(":checked") && !hasError) {
					hasError = self.validateGitData($('#importTestRepourl'));
					if (hasError) {
						self.showTestTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
			}
			if (!hasError && importType === "svn") {
				hasError = self.validateSvnData($('#importRepourl'), $('#importUserName'), $('#importPassword'),$('#importType'), $('input[name=headoption]:checked'), $('#revision'));
				if (hasError) {
					self.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				}
				if ($('#importDotPhrescoSrc').is(":checked") && !hasError) {
					hasError = self.validateSvnData($('#importPhrescoRepourl'), $('#importPhrescoUserName'), $('#importPhrescoPassword'),$('#phrescoImportType'),$('input[name=phrescoHeadoption]:checked'), $('#phrescoRevision'));
					if (hasError) {
						self.showDotPhrescoTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
				if ($('#importTestSrc').is(":checked") && !hasError) {
					hasError = self.validateSvnData($('#importTestRepourl'), $('#importTestUserName'), $('#importTestPassword'), $('#testImportType'),$('input[name=testHeadoption]:checked'), $('#testRevision'));
					if (hasError) {
						self.showTestTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
			}
			if (!hasError && importType === "perforce") {
				hasError = self.validatePerforceData($('#importRepourl'), $('.stream'));
				if (hasError) {
					self.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				}
				if ($('#importDotPhrescoSrc').is(":checked") && !hasError) {
					hasError = self.validatePerforceData($('#importPhrescoRepourl'), $('.phrescoStream'));
					if (hasError) {
						self.showDotPhrescoTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
				if ($('#importTestSrc').is(":checked") && !hasError) {
					hasError = self.validatePerforceData($('#importTestRepourl'), $('.testStream'));
					if (hasError) {
						self.showTestTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
			}
			if (!hasError && importType === "bitkeeper") {
				hasError = self.validateBitkeeperData($("#importRepourl"));
				if (hasError) {
					self.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				}
				if ($('#importDotPhrescoSrc').is(":checked") && !hasError) {
					hasError = self.validateBitkeeperData($('#importPhrescoRepourl'));
					if (hasError) {
						self.showDotPhrescoTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
				if ($('#importTestSrc').is(":checked") && !hasError) {
					hasError = self.validateBitkeeperData($('#importTestRepourl'));
					if (hasError) {
						self.showTestTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
			}
			if (!hasError && importType === "tfs") {
				hasError = self.validateTfsData($('#importRepourl'), $('#importUserName'), $('#importPassword'));
				if (hasError) {
					self.showSrcTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
				}
				if ($('#importDotPhrescoSrc').is(":checked") && !hasError) {
					hasError = self.validateTfsData($('#importPhrescoRepourl'), $('#importPhrescoUserName'), $('#importPhrescoPassword'));
					if (hasError) {
						self.showDotPhrescoTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
				if ($('#importTestSrc').is(":checked") && !hasError) {
					hasError = self.validateTfsData($('#importTestRepourl'), $('#importTestUserName'), $('#importTestPassword'));
					if (hasError) {
						self.showTestTab($("#importDotphresco"), $("#importSource"), $("#importTest"), $("#importDotPhrescoSrc"), $("#importTestSrc"));
					}
				}
			}
			callback(hasError);
		},
		
		showSrcTab : function(dotPhrCntObj, srcCntObj, tstCntObj, dotPhrChkObj, tstChkObj) {
			dotPhrCntObj.removeClass("active in");
			srcCntObj.addClass("active in");
			tstCntObj.removeClass("active in");
			dotPhrChkObj.parent().prev().addClass("active");
			dotPhrChkObj.parent().removeClass("active");
			tstChkObj.parent().removeClass("active");
		},
		
		showDotPhrescoTab : function(dotPhrCntObj, srcCntObj, tstCntObj, dotPhrChkObj, tstChkObj) {
			dotPhrCntObj.addClass("active in");
			srcCntObj.removeClass("active in");
			tstCntObj.removeClass("active in");
			dotPhrChkObj.parent().prev().removeClass("active");
			dotPhrChkObj.parent().addClass("active");
			tstChkObj.parent().removeClass("active");
		},
		
		showTestTab : function(dotPhrCntObj, srcCntObj, tstCntObj, dotPhrChkObj, tstChkObj) {
			dotPhrCntObj.removeClass("active in");
			srcCntObj.removeClass("active in");
			tstCntObj.addClass("active in");
			dotPhrChkObj.parent().prev().removeClass("active");
			dotPhrChkObj.parent().removeClass("active");
			tstChkObj.parent().addClass("active");
		},
		
		validateGitData : function(repoUrlObj) {
			var self = this;
			repoUrlObj.removeClass("errormessage");
			console.info("entering Validate Git data");
			var repoUrl = repoUrlObj.val();
			if (repoUrl === "") {
				self.validateTextBox(repoUrlObj, 'Enter Url');
				return true;
			}
			if(!self.isValidUrl(repoUrl)) {
			    repoUrlObj.val('');
				self.validateTextBox(repoUrlObj, 'Invalid Repo Url');
				return true;
			}
			return false;
		},
		
		validateSvnData : function(repoUrlObj, userNameObj, pwdObj,repotypeObj, headOptObj, revisionObj) {
			var self = this;
			userNameObj.removeClass("errormessage");
			pwdObj.removeClass("errormessage");
			repoUrlObj.removeClass("errormessage");			
			var repoUrl = repoUrlObj.val();
			var userName = userNameObj.val().replace(/\s/g, '');
			var pswd = pwdObj.val();
			var repotype = repotypeObj.val();
			if(repotype === "git"){
				 var pattern = /git/;
				 var exists = pattern.test(repoUrl);
				if(exists === false){				    
					repoUrlObj.val('');
					self.validateTextBox(repoUrlObj, 'Invalid Repo Url');
					return true;
				}
			}
			if (repoUrl === "") {
				self.validateTextBox(repoUrlObj, 'Enter Url');
				return true;
			}
			if(!self.isValidUrl(repoUrl)) {
				repoUrlObj.val('');
				self.validateTextBox(repoUrlObj, 'Invalid Repo Url');
				return true;
			}
			if (userName === "") {
				self.validateTextBox(userNameObj, 'Enter user name');
				return true;
			} 
			if (pswd === "") {
				self.validateTextBox(pwdObj, 'Enter password');
				return true;
			}
			if (headOptObj !== undefined && headOptObj !== null && headOptObj !== "") {
				var revision = headOptObj.val();
				if (revisionObj !== undefined && revisionObj !== null && revisionObj !== "") {
					revisionObj.removeClass("errormessage");
					if (revision === "revision" && revisionObj.val() === "") {
						self.validateTextBox(revisionObj, 'Enter revision');
						return true;
					}
				}
			}
			
			return false;
		},
		
		validatePerforceData : function(repoUrlObj, streamObj) {
			var self = this;
			var repoUrl = repoUrlObj.val();
			var stream = streamObj.val();
			if (repoUrl === "") {
				self.validateTextBox(repoUrlObj, 'Enter Url');
				return true;
			}
			if (!self.validateperforce(repoUrl)) {
				repoUrlObj.val('');
				self.validateTextBox(repoUrlObj, 'Invalid Repo Url');
				return true;
			}
			
			if (stream === "") {
				self.validateTextBox(streamObj, 'Enter Stream');
				return true;
			}
			return false;
		},
		
		validateBitkeeperData : function(repoUrlObj) {
			var self = this;
			var repoUrl = repoUrlObj.val();
			if (repoUrl === "") {
				self.validateTextBox(repoUrlObj, 'Enter Url');
				return true;
			}
			return false;
		},
		
		validateTfsData : function(repoUrlObj, userNameObj, pwdObj) {
			var self = this;
			
			userNameObj.removeClass("errormessage");
			pwdObj.removeClass("errormessage");
			repoUrlObj.removeClass("errormessage");
			
			var repoUrl = repoUrlObj.val();
			var userName = userNameObj.val().replace(/\s/g, '');
			var pswd = pwdObj.val();
			if (repoUrl === "") {
				self.validateTextBox(repoUrlObj, 'Enter Url');
				return true;
			}
			if(!self.isValidUrl(repoUrl)) {
				repoUrlObj.val('');
				self.validateTextBox(repoUrlObj, 'Invalid Repo Url');
				return true;
			}
			if (userName === "") {
				self.validateTextBox(userNameObj, 'Enter user name');
				return true;
			} 
			if (pswd === "") {
				self.validateTextBox(pwdObj, 'Enter password');
				return true;
			} 
			return false;
		},
		
		validateTextBox : function (textBoxObj, errormsg) {
			if(textBoxObj !== "" && errormsg !== "") {
				textBoxObj.focus();
				textBoxObj.attr('placeholder', errormsg);
				textBoxObj.addClass("errormessage");
			} else {
				textBoxObj.attr('placeholder', errormsg);
				textBoxObj.removeClass("errormessage");
			}
		},
		
		addImportEvent : function(){
			var self = this;
			var repoInfo = {}, actionBody, action;
			
			repoInfo.srcRepoDetail = self.getImportSrcRepoDetail();
			if ($('#importDotPhrescoSrc').is(":checked")) {
				repoInfo.phrescoRepoDetail = self.getImportPhrescoRepoDetail();
				repoInfo.splitPhresco = true;
			}
			if ($('#importTestSrc').is(":checked")) {
				repoInfo.testRepoDetail = self.getImportTestRepoDetail();
				repoInfo.splitTest = true;
			}
			actionBody = repoInfo;
			action = "importpost";
			commonVariables.hideloading = true;
			$("#project_list_import").find('input').attr('disabled','disabled');
			$("#project_list_import").find('select').attr('disabled','disabled');
			var header = self.getActionHeader(actionBody, action);
			self.navigationActionForImport(header, function(response){
				$("#project_list_import").find('input').removeAttr('disabled');
				$("#project_list_import").find('select').removeAttr('disabled');
				self.hideBtnLoading("button[name='importbtn']");
				if (response.exception === null) {
					$("#project_list_import").hide();	
					setTimeout(function() {
						self.getMyObj(commonVariables.projectlist, function(returnVal){
							self.projectlist = returnVal;
							Clazz.navigationController.push(self.projectlist, commonVariables.animation);
						});
					},1000);	
				}
				commonVariables.hideloading = false;
			});
		},
		
		getImportSrcRepoDetail : function() {
			var repoDetail = {};
			var revision = $("input[name='headoption']:checked").val();
			if (revision === "revision") {
				revision = $("#revision").val();
			} else{
				revision = revision;
			}
			var repoType = $("#importType").val()
			repoDetail.type = repoType;
			repoDetail.repoUrl = $("#importRepourl").val();
			
			if ('svn' === repoType) {
				repoDetail.userName = $("#importUserName").val();
				repoDetail.password = $("#importPassword").val();
				repoDetail.revision = revision;
			} else {
				repoDetail.branch = $(".branchval").val();
				repoDetail.userName = $("#gitUserName").val();
				repoDetail.password = $("#gitPassword").val();
				repoDetail.passPhrase = $(".passPhraseval").val();
			}
			
			if ('perforce' === repoType) {
				repoDetail.stream = $('.stream').val();
			}
			
			if ('tfs' === repoType) {
			   repoDetail.userName = $("#importUserName").val();
			   repoDetail.password = $("#importPassword").val();
			   repoDetail.proName = $('.projName').val();
			   repoDetail.serverPath = $('.serverpath').val();
			}
			return repoDetail;
		},
		
		getImportPhrescoRepoDetail : function() {
			var repoDetail = {};
			var revision = $("input[name='phrescoHeadoption']:checked").val();
			if (revision === "revision") {
				revision = $("#revision").val();
			} else{
				revision = revision;
			}
			var repoType = $("#phrescoImportType").val()
			repoDetail.type = repoType;
			repoDetail.repoUrl = $("#importPhrescoRepourl").val();
			
			if ('svn' === repoType) {
				repoDetail.userName = $("#importPhrescoUserName").val();
				repoDetail.password = $("#importPhrescoPassword").val();
				repoDetail.revision = revision;
			} else {
				repoDetail.branch = $(".phrescoBranchval").val();
				repoDetail.userName = $("#phrescoGitUserName").val();
				repoDetail.password = $("#phrescoGitPassword").val();
				repoDetail.passPhrase = $(".phrescoPassPhraseval").val();
			}
			
			if ('perforce' === repoType) {
				repoDetail.stream = $('.phrescoStream').val();
			}
			if ('tfs' === repoType) {
			   repoDetail.userName = $("#phrescoGitUserName").val();
			   repoDetail.password = $("#phrescoGitPassword").val();
			   repoDetail.proName = $('.projName').val();
			   repoDetail.serverPath = $('.serverpath').val();
			}
			return repoDetail;
		},
		
		getImportTestRepoDetail : function() {
			var repoDetail = {};
			var revision = $("input[name='testHeadoption']:checked").val();
			if (revision === "revision") {
				revision = $("#revision").val();
			} else{
				revision = revision;
			}
			var repoType = $("#testImportType").val()
			repoDetail.type = repoType;
			repoDetail.repoUrl = $("#importTestRepourl").val();
			
			if ('svn' === repoType) {
				repoDetail.userName = $("#importTestUserName").val();
				repoDetail.password = $("#importTestPassword").val();
				repoDetail.revision = revision;
			} else {
				repoDetail.branch = $(".testBranchval").val();
				repoDetail.userName = $("#testGitUserName").val();
				repoDetail.password = $("#testGitPassword").val();
				repoDetail.passPhrase = $(".testPassPhraseval").val();
			}
			
			if ('perforce' === repoType) {
				repoDetail.stream = $('.testStream').val();
			}
			if ('tfs' === repoType) {
			   repoDetail.userName = $("#testGitUserName").val();
			   repoDetail.password = $("#testGitPassword").val();
			   repoDetail.proName = $('.projName').val();
			   repoDetail.serverPath = $('.serverpath').val();
			}
			return repoDetail;
		},

		validateperforce : function(importRepourl) {
			if(importRepourl.indexOf(':') > 0 ){
				var arr=new Array();
				var arr=importRepourl.split(":"); 
				if(/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/i.test(arr[0]) === true || arr[0] === "localhost" ){
					if(!isNaN(arr[1]) && isFinite(arr[1])){
						return true;
					}
				}
			}
			return false;
		},
		
		//To copy the console log content to the clip-board
		copyToClipboard : function(consoleObj) {
			var self = this;
			var logContent = consoleObj.text();
			var data = {};
			data.log = escape(logContent);
			self.navigationAction(self.getActionHeader(data, "copyToClipboard"), function(response) {
				if(consoleObj.attr('class') === 'modal-body temp') {
					consoleObj.next('div').find("div[id='copyloadicon']").hide();
				}
			});
		},
		
		configDropdown : function(val) {
			var self=this, favConfigList="", favConfig, flag = false;
			$("#configuration").html('');
			$.each(val, function(index, value){
				if (value.favourite === true || value.envSpecific === false) {
					flag = true;
					favConfigList += '<li name="configuration" configType="'+value.templateName+'" favourite='+value.favourite+' envSpecific='+value.envSpecific+'><a href="javascript:void(0)">'+value.templateName+'</a></li>';
				}
			});
			if (flag === true) {
				favConfig = '<a href="javascript:void(0)" id="drop4Config" role="quality" class="dropdown-toggle drop-qual" data-toggle="dropdown">Configuration<b class="caret"></b></a><div class="dropdown-menu cust_sel test_options" role="quality" aria-labelledby="drop4"><ul name="configurationList">'+favConfigList+'</ul></div>';
			} else {
				favConfig = '<a href="javascript:void(0)" id="drop4Config" role="quality" class="dropdown-toggle drop-qual" data-toggle="dropdown">Configuration</b></a>';
			}
			$("#configuration").append(favConfig);
			self.clickEvent();
		},
		
		clickEvent : function() {
			var self=this, envSpec, configType, favourite;
			$("ul[name=configurationList] li").unbind("click");
			$("ul[name=configurationList] li").click(function() {
				$("#myTab li a").removeClass("act");
				$("#configuration a#drop4Config").addClass("act");
				envSpec = $(this).attr('envSpecific');
				configType = $(this).attr('configType');
				favourite = $(this).attr('favourite');
				envSpec = (envSpec === "true") ? true : false;
				favourite = (favourite === "true") ? true : false;
				if(favourite === true) {
					commonVariables.favConfig = configType;
				} else {
					commonVariables.favConfig = '';
				}
				commonVariables.subtabClicked = true;
				commonVariables.navListener.currentTab = '';
				self.getMyObj(commonVariables.configuration, function(returnVal){
					self.nonEnvConfigurations = returnVal;
					self.nonEnvConfigurations.envSpecific = envSpec;
					self.nonEnvConfigurations.configType = configType;
					self.nonEnvConfigurations.favourite = favourite;
					Clazz.navigationController.push(self.nonEnvConfigurations, true);
				});
			});
		}
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});
