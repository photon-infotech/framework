define(["login/login"], function(Login) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { 
		runTests : function (configData) {
			commonVariables.api.localVal.clearSession();
			module("login.js");
			var login = new Login(), self = this;
			
			asyncTest("Login UI Test", function() {
				$("#custom").append(commonVariables.basePlaceholder);
				$("#custom").append(commonVariables.headerPlaceholder);
				$("#custom").append(commonVariables.navigationPlaceholder);
				$("#fixture").append(commonVariables.contentPlaceholder);
				$("#fixture").append(commonVariables.footerPlaceholder);

				var output;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});

				login.loadPage();
				setTimeout(function() {
					start();
					output = $(commonVariables.basePlaceholder).find("#loginContent").attr('id');
					equal("loginContent", output, "Login Rendered Successfully");
					self.runValidationTest();
				}, 1500);
			});
		},
		
		runValidationTest : function (){
			var self = this;
			asyncTest("Login Username Validation Test", function() {
				$('input#username').val('');
				$('input#password').val('');
				$('#login').click();
				setTimeout(function() {
					start();
					ok($('#usernameDiv').hasClass("loginuser_error"), 'Username div error class added test');
				}, 500);
			});
			
			asyncTest("Login Password Validation Test", function() {
				$('input#username').val('admin');
				$('input#password').val('');
				$('#login').click();
				setTimeout(function() {
					start();
					ok($('#passwordDiv').hasClass("loginuser_error"), 'Password div error class added test');
					self.forgotPasswordTest();
				}, 1000);
			});
		},
		
		forgotPasswordTest : function(){
			var self = this;
			asyncTest("Forgot Password Test", function() {
				console.info($("#confirm_forgot_password"));
				$('#userid').val('sample');
				
				var login = $.mockjax({
				  url: commonVariables.webserviceurl + "login/forgotPassword?userId=sample&custId=photon",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR100008","data":true,"status":"success"});
				  }
				
				});
			
				
				$('#confirm_forgot_password').click();
				setTimeout(function() {
					start();
					equal($("#forgot_password").css('display'), "none", "Forgot Password  Tested");
					self.runServiceTest();
				}, 4000);
			});
		},
		runServiceTest : function(){
			var self = this;
			asyncTest("Login Service Test", function() {
				
				$('input#username').val('admin');
				$('input#password').val('manage');
				
				var login = $.mockjax({
				  url: commonVariables.webserviceurl + "login",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Login Success","exception":null,"data":{	"email":null,"token":"1ibnf3ashmk0s14qv793nst71bo","authType":"LOCAL","customers":[{"options":["Home","Help","Settings","Download"],"frameworkTheme":null,"repoInfo":null,"emailId":null,"zipcode":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableAppTypes":null,"applicableTechnologies":null,"address":null,"context":null,"state":null,"type":"TYPE_BRONZE","country":null,"icon":null,"creationDate":1370260351850,"helpText":null,"system":false,"name":"Photon","id":"photon","displayName":null,"description":"photon","status":null}],"phrescoEnabled":true,"loginId":null,"firstName":null,"lastName":null,"roleIds":["4e8c0bd7-fb39-4aea-ae73-2d1286ae4ad0","4e8c0bd7-fb39-4aea-ae73-2d1286ae4ae0"],"validLogin":true,"permissions":{"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false},"password":"462d1b8c3ec910626e1433647b1675e","creationDate":1357304255000,"helpText":null,"system":false,"name":"admin","id":"admin","displayName":"Admin","description":null,"status":null}});
				  }
				
				});
				
				var projectlist = $.mockjax({
				  url: commonVariables.webserviceurl + 'project/list?customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]})
				  }
				});
				
				$('#login').click();
				setTimeout(function() {
					start();
					equal($(commonVariables.headerPlaceholder).find("font:first").text(), "Photon", "Login Service Tested");
					equal($("#footer").attr('id'), "footer", "Footer Rendering Tested");
					require(["navigationTest"], function(navigationTest){
					navigationTest.runTests(); }); 				
				}, 4000);
			});
		}
		
		
	};
});
