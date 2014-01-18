define([], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		localStorageAPI : null,
		headerContent : null,
		footerContent : null,
		navigationContent : null,
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;

			if(self.navigationContent === null){
				require(["navigation/navigation"], function(){
					self.navigationContent = new Clazz.com.components.navigation.js.navigation();	
				});
			}	
		},
		
		/***
		 * Verify the login and navigate to dashboard page if the login is successful
		 * 
		 * @header: constructed header for each call
		 */
		doLogin : function() {
			try{
				var self = this, header = self.getRequestHeader();
				if(self.loginValidation()){
					//TODO: call login service here and call appendPlaceholder in the success function
					commonVariables.api.bCheck = true;
					commonVariables.api.ajaxRequest(header, 
						function(response){
							if(response !== undefined && response !== null && response.status !== "error" && response.status !== "failure"){
								commonVariables.continueloading = true;
								self.setUserInfo(response.data);
								self.appendPlaceholder();
								self.renderNavigation();
							} else {
								//authentication failed
								$('#login').removeAttr('disabled');
								$(".login_error_msg").css('color','red');
								$(".login_error_msg").attr('data-i18n', 'errorCodes.' + response.responseCode);
								self.renderlocales(commonVariables.basePlaceholder);
							}
						}, 
						function(serviceError){
							//service access failed
							$('#login').removeAttr('disabled');
							$(".login_error_msg").css('color','red');
							$(".login_error_msg").attr('data-i18n', 'errorCodes.' + response.responseCode);
							self.renderlocales(commonVariables.basePlaceholder);
						}
					);
				}else{$('#login').removeAttr('disabled');}
			}catch(error){
				//Exception
				$('#login').removeAttr('disabled');
			}
		},
		
		forgotpassword : function() {
			try{
				var self = this, header = self.getRequestHeader('forgotpassword');
					//TODO: call login service here and call appendPlaceholder in the success function
					commonVariables.api.bCheck = true;
					commonVariables.api.ajaxRequest(header, 
						function(response){
						if(response.responseCode === 'PHR110008' || response.responseCode === 'PHR110010') {
						$(".login_error_msg").css('color','red');
						$("#forgot_password").hide();
								$(".login_error_msg").attr('data-i18n', 'errorCodes.' + response.responseCode);
								self.renderlocales(commonVariables.basePlaceholder);
								setTimeout(function() {
									$(".login_error_msg").text('');
								},3000);
						} else if(response.responseCode === 'PHR100008') {
						$("#forgot_password").hide();
						$(".login_error_msg").css('color','green');
						$(".login_error_msg").attr('data-i18n', 'successCodes.' + response.responseCode);
						self.renderlocales(commonVariables.basePlaceholder);
						setTimeout(function() {
							$(".login_error_msg").text('');
						},3000);
						//$("#change_password").hide();
						} 
						
						}, 
						function(serviceError){
							//service access failed
							commonVariables.api.showError(response.responseCode ,"error", true);
						} 
						
					);
			}catch(error){
				//Exception
			}
		},
		
		pageRefresh : function(contentObj){
			var self = this;
			self.appendPlaceholder();
			self.renderNavigation(contentObj);
		},
		
		setUserInfo : function(data){
			var self = this, userInfo = JSON.stringify(data);
			commonVariables.api.localVal.setSession("username",$("#username").val().trim());
			commonVariables.api.localVal.setSession("password",$("#password").val().trim());
			commonVariables.api.localVal.setSession("rememberMe",$("#rememberMe").is(":checked"));
			commonVariables.api.localVal.setSession("userInfo",userInfo);
			commonVariables.api.localVal.setSession("userPermissions", JSON.stringify(data.permissions));
		},
		
		loginValidation : function(){
			var self = this, bCheck = false;
			
			if(self.userNameValidation()){
				bCheck = self.passwordValidation();
			}
			else{
				bCheck = false;
			}
			return bCheck;
		},
		
		userNameValidation : function(){
			
			if ($("#username").val() === undefined || $("#username").val() === null || $.trim($("#username").val()) === ""){
				$("#username").attr('placeholder','Enter Username');
				$("#usernameDiv").addClass("loginuser_error");
				return false;
			} else { 
				$("#usernameDiv").removeClass("loginuser_error");
				return true;
			}
		},
		
		passwordValidation : function(){
			if($("#password").val() === undefined || $("#password").val() === null || $.trim($("#password").val()) === ""){
				$("#password").attr('placeholder','Enter Password');
				$("#passwordDiv").addClass("loginuser_error");
				return false;
			}
			else{
				$("#passwordDiv").removeClass("loginuser_error");
				return true;
			}
		},
		
		/***
		 * provides the request header
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(action) {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: JSON.stringify({"username" : $("#username").val().trim(), "password" : $("#password").val().trim()}),
				webserviceurl: commonVariables.webserviceurl + commonVariables.loginContext
			};
			
			if(action === "forgotpassword") {
				header.requestMethod = "GET";
				var userid = $("#userid").val();
				var pathname = window.location.pathname;
				var arr = pathname.split('/');
				var lastindex = arr.length-1;
				var custid;
				if (arr[lastindex] == ""){
				custid = "photon";
				}
				else{
				custid = arr[lastindex];	
				}
				header.requestPostBody = {};
				//header.requestPostBody = userid;
				header.webserviceurl = commonVariables.webserviceurl+ "login/forgotPassword?userId="+userid+"&custId="+custid;
			}

			return header;
		},
		
		appendPlaceholder : function() {
			$(commonVariables.basePlaceholder).empty();
			$(commonVariables.basePlaceholder).append(commonVariables.headerPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.navigationPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.contentPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.footerPlaceholder);
		},
		
		renderNavigation : function(contentObj) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.navigationPlaceholder;

			if(self.navigationContent === null){
				require(["navigation/navigation"], function(){
					self.navigationContent = new Clazz.com.components.navigation.js.navigation();
					self.loadNavigationPage(contentObj);
				});
			}else{ self.loadNavigationPage(contentObj); }
		},
		
		loadNavigationPage : function(contentObj){
			var self = this;
			self.navigationContent.currentContent = contentObj;
			Clazz.navigationController.push(self.navigationContent, false);	
		}
	});

	return Clazz.com.components.login.js.listener.LoginListener;
});