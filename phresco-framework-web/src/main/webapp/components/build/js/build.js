define(["build/listener/buildListener"], function() {
	Clazz.createPackage("com.components.build.js");

	Clazz.com.components.build.js.Build = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/build/template/build.tmp",
		configUrl: "components/build/config/config.json",
		name : commonVariables.build,
		buildListener : null,
		onProgressEvent : null,
		onDownloadEvent : null,
		onUploadEvent : null,
		onValidationEvent : null,
		dynamicpage : null,
		dynamicPageListener : null,
		generateBuildContent : "",
		onMavenServiceEvent : null,
		count : 0,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.runType = "build";
			if(self.buildListener === null){
				self.buildListener = new Clazz.com.components.build.js.listener.BuildListener();
			}
			
			if(self.dynamicpage === null){
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicpage = retVal;
					self.dynamicPageListener = self.dynamicpage.dynamicPageListener;
					self.registerEvents();
				});
			}else{self.registerEvents();}
			
			self.registerHandlebars();
		},
		
		registerHandlebars : function () {
			Handlebars.registerHelper('devicedploy', function(paramVal) {
				if(paramVal === null){
					return '<img name="deployBuild" deviceDeploy="" src="themes/default/images/Phresco/deploy_icon.png" width="16" height="20" border="0" alt="">';
				}else{							
					return '<img name="deployBuild" deviceDeploy="' + paramVal + '" src="themes/default/images/Phresco/deploy_icon.png" width="16" height="20" border="0" alt="">';
				}		
			});
		},
		
		registerEvents : function(){
			var self = this;
			
			if(self.onProgressEvent === null){
				self.onProgressEvent = new signals.Signal();
			}	
			if(self.onDownloadEvent === null){
				self.onDownloadEvent = new signals.Signal();
			}
			if(self.onUploadEvent === null){
				self.onUploadEvent = new signals.Signal();
			}
			if(self.onValidationEvent === null){
				self.onValidationEvent = new signals.Signal();
			}
			if(self.onMavenServiceEvent === null){
				self.onMavenServiceEvent = new signals.Signal();
			}
			
			self.onProgressEvent.add(self.buildListener.onPrgoress, self.buildListener);
			self.onDownloadEvent.add(self.buildListener.downloadBuild, self.buildListener);
			self.onValidationEvent.add(self.buildListener.mandatoryValidation, self.buildListener);
			self.onMavenServiceEvent.add(self.buildListener.mavenServiceCall, self.buildListener);
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
		Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, commonVariables.animation);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.buildListener.getInfo(self.buildListener.getRequestHeader("", '', 'getList'), function(response){
			var buildObject = {};
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				buildObject.buildInfos = response.data;
				buildObject.userPermissions = userPermissions;
				renderFunction(buildObject, whereToRender);
			});
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			self.showHideSysSpecCtrls();
            commonVariables.navListener.showHideTechOptions();
			$("#build_genbuild ul").find(".selectpicker").selectpicker();
			self.runAgainSourceStatus(true);
			self.loadPostContent();
			self.resizeConsoleWindow();
			self.closeConsole();
			$(window).resize();
			
			var windowHeight = $(document).height(), marginTop = '';
			marginTop = $('.testSuiteTable').css("margin-top");
			
			if(marginTop !== undefined){
				marginTop = marginTop.substring(0, marginTop.length - 2);
			}
			
			var footerHeight = $('#footer').height();
			var deductionHeight = Number(marginTop) + Number(footerHeight);
			var finalHeight = windowHeight - deductionHeight - 6;
			$('.testSuiteTable').height(finalHeight);
		},
		
		runAgainSourceStatus : function(bootup){
			var self = this;
			if($("input[name=build_runagsource]").is(':visible')){
				self.buildListener.getInfo(self.buildListener.getRequestHeader("", '', 'serverstatus'), function(response){
					self.changeBtnStatus(response);
					$('#run_against_src_alert_div').hide();
					if(bootup){self.retainLogcontent(response.data);}
				});
			}
		},
		
		retainLogcontent : function(status){
			self.buildListener.getInfo(self.buildListener.getRequestHeader("", status, 'logContent'), function(response){
				if(response.data !== null){
					$('#logContent').html('');
					$('#logContent').html(response.data);
				}
			});
		},
		
		dragDrop : function(){
			$('.connectedSortable').sortable({
				connectWith: '.connectedSortable',
				cancel: ".ui-state-disabled"
			});
		},
		
		showErrorPopUp : function(responseCode){
			var self = this;
			$(".content_end").show();
			$(".msgdisplay").removeClass("success").addClass("error");
			$(".error").attr('data-i18n', 'errorCodes.' + responseCode);
			self.renderlocales(commonVariables.contentPlaceholder);	
			$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
			setTimeout(function() {
				$(".content_end").hide();
			},2500);
		},
		
		sqlQueryParam : function(ststus, control, callback){
			var sqlParamVal = "";
			
			if(ststus){
				sqlParamVal = {};
				$.each(control, function(index, current){
					if(sqlParamVal.hasOwnProperty($(current).attr('dbName'))){
						sqlParamVal[$(current).attr('dbName')].push($(current).attr('path'));
					}else{
						sqlParamVal[$(current).attr('dbName')] = []
						sqlParamVal[$(current).attr('dbName')].push($(current).attr('path'));
					}
				});
				callback(JSON.stringify(sqlParamVal));
			} else {
				callback(sqlParamVal);
			}
		},
		
		changeBtnStatus : function(response){
			var show = "btn_style", hide = "btn_style_off";

			if(!response.data){
				show = "btn_style_off";
				hide = "btn_style";
			}
			
			$('#run_against_src_alert_div').hide();
			$("input[name=build_runagsource]").removeClass(show);
			$("#stop").removeClass(hide);
			$("#restart").removeClass(hide);
			
			$("input[name=build_runagsource]").addClass(hide);
			$("#stop").addClass(show);
			$("#restart").addClass(show);
		},

		loadPostContent : function(){
			var self = this;
			if(self.dynamicpage === null){
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicpage = retVal;
					self.dynamicPageListener = self.dynamicpage.dynamicPageListener;
				});
			}
		},
		
		callDepOpencc : function(current, divId){
			var self = this;
			
			if($('#deploye_' + divId).find('form[name=deployForm] ul').children().length > 0){
				$('#deploye_' + divId).find('form[name=deployForm] div #buildNumber').val(divId);
				self.dragDrop();
				self.opencc(current,'deploye_' + divId, '' , 50);
			}else{$('#deploye_' + divId).hide();}
		},
		
		clearLogContent : function(){
			var self = this;
			$('#logContent').html('');
			$('.progress_loading').css('display','block');
			self.openConsole();
			$("#buildConsole").attr('data-flag','false');
		},
		
		openCloseConsole : function(){
			if(!commonVariables.consoleError) {self.closeConsole();}
			commonVariables.consoleError = false;
		},
		
		refreshContent : function(loadContent){
			var self = this;

			$('#build_alert_div').hide();
			if(loadContent){
				self.buildListener.getInfo(self.buildListener.getRequestHeader("", '', 'getList'), function(response) {
					if(response !== undefined && response !== null && response.data !== null && response.data.length > 0){
						var tbody = "", buildObject = {}, userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
						buildObject.buildInfos = response.data;
						buildObject.userPermissions = userPermissions;
						
						

						$.each(buildObject.buildInfos, function(index, current){
							var cancreateIpa = "";
							var manageBuilds = "";
							var deviceDeploy = "";
							var deleteOpt = "";
							var testFlightupload = "";
						
						if($("#buildRow").length < 1){	  
						 if(current.options !== null && current.options.canCreateIpa === true){
						   var table = '<table class="table table-striped table_border table-bordered big" cellpadding="0" cellspacing="0" border="0" id="buildRow"><thead class="fixedHeader"><tr><th data-i18n="build.label.bNo"></th><th data-i18n="build.label.date" ></th><th data-i18n="build.label.download"></th><th name="prcBuild" data-i18n="build.label.processBuild"></th><th name="buildDep" data-i18n="build.label.deploy"></th><th data-i18n="build.label.delete"></th><th data-i18n="build.label.upload"></th></tr></thead><tbody class="scrollContent"></tbody></table>';
                          }
						else{
						   var table = '<table class="table table-striped table_border table-bordered big" cellpadding="0" cellspacing="0" border="0" id="buildRow"><thead class="fixedHeader"><tr><th data-i18n="build.label.bNo"></th><th data-i18n="build.label.date" ></th><th data-i18n="build.label.download"></th><th name="prcBuild" data-i18n="build.label.processBuild"></th><th name="buildDep" data-i18n="build.label.deploy"></th><th data-i18n="build.label.delete"></th></tr></thead><tbody class="scrollContent"></tbody></table>'; 
							}
						   $('.qual_unit_main').html(table);
						 }

					 if(current.options !== null && current.options.canCreateIpa === true){		     
						
								cancreateIpa = '<a href="javascript:void(0)" class="tooltiptop" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="IPA Download"><img name="ipaDownload" src="themes/default/images/Phresco/ipa_icon.png" width="13" height="18" border="0" alt=""></a>';
								
								testFlightupload = '<td class="list_img" name="UploadBuildtd"><a href="javascript:void(0)" class="tooltiptop" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Upload"><img build="uploadBuild" name="upload_'+current.buildNo+'" src="themes/default/images/Phresco/testflight_icon.png" width="15" height="18" border="0" alt=""></a><div id="upload_'+current.buildNo+'" name="testFlightUploadDiv" class="dyn_popup deleteproj_msg" style="display:none;"><div><div><font class="testflight">Api Token<sup>*</sup></font><input id="apiToken" type="text" maxlength="253" value="" name="ApiToken" showhide="true" parametertype="String"><input id="apiToken" type="hidden"  name="fileName"></div><div><font class="testflight">Team Token<sup>*</sup></font><input id="teamToken" type="text" maxlength="253" value="" name="teamToken" showhide="true" parametertype="String"></div><div><font class="testflight">Notes<sup>*</sup></font><input id="notes" type="text" maxlength="253" value="" name="notes" showhide="true" parametertype="String"></div><div><font class="testflight">Notify</font><input id="notify" type="text" maxlength="253" value="" name="notify" showhide="true" parametertype="String"></div><div><font class="testflight">Distribution Lists</font><input id="distributionLists" type="text" maxlength="253" value="" name="distributionLists" showhide="true" parametertype="String"></div><div class="testflight_btn"><input type="button" name="uploadTestFlight" fileExtn="ipa" buildNo='+current.buildNo+' data-i18n="[value]build.label.upload" class="btn btn_style" /><input type="button" data-i18n="[value]build.label.close" class="btn btn_style dyn_popup_close" /></div></div></div></td>'
							}

							deviceDeploy = '<div id="deploye_'+ current.buildNo +'" class="dyn_popup popup_bg" style="display:none;"><div id="bdeploy_'+ current.buildNo +'"><form name="deployForm"><ul class="row dynamicControls"></ul><div class="hiddenControls"></div></form><div class="flt_right"><input type="button" name="deploy" data-i18n="[value]build.label.deploy" class="btn btn_style dyn_popup_close"><input type="button" data-i18n="[value]build.label.close" class="btn btn_style dyn_popup_close"></div></div></div>';

							if(buildObject.userPermissions.manageBuilds !== null && buildObject.userPermissions.manageBuilds === true){
								manageBuilds = '<a href="javascript:void(0)" class="tooltiptop" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Deploy"><img name="deployBuild" deviceDeploy="' + (current.options === null ? "" : current.options.deviceDeploy) + '" src="themes/default/images/Phresco/deploy_icon.png" width="16" height="20" border="0" alt=""></a>' + deviceDeploy;
								
								deleteOpt ='<a name="delete_'+ current.buildNo +'" class="deletebuildRow tooltiptop" title="" data-placement="bottom" data-toggle="tooltip" href="javascript:void(0)" data-original-title="Delete Row"><img name="deleteBuild" src="themes/default/images/Phresco/delete_row.png" width="14" height="18" border="0" alt=""></a><div id="delete_'+ current.buildNo +'" class="dyn_popup deleteproj_msg"><div data-i18n="build.label.deleteConform"></div><div><input type="button" name="buildDelete" data-i18n="[value]build.label.yes" class="btn btn_style dyn_popup_close" /><input type="button" data-i18n="[value]build.label.no" class="btn btn_style dyn_popup_close" /></div></div>';
								
							}else{
								manageBuilds = '<img src="themes/default/images/Phresco/deploy_icon_off.png" width="16" height="20" border="0" alt="">';
								deleteOpt ='<img src="themes/default/images/Phresco/delete_row_off.png" width="14" height="18" border="0" alt="">';
							}
						
							tbody += '<tr><td name="'+ current.buildNo +'">'+ current.buildNo +'</td><td>'+ current.timeStamp +'</td><td class="list_img"><a href="javascript:void(0)" class="tooltiptop" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Download"><img name="downloadBuild" src="themes/default/images/Phresco/download_icon.png" width="15" height="18" border="0" alt=""></a>'+ cancreateIpa +'</td><td name="prcBuild" class="list_img"><a href="javascript:void(0)" class="tooltiptop" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Process build"><img name="procBuild" src="themes/default/images/Phresco/download_icon.png" width="15" height="18" border="0" alt=""></a><div id="prcBuild_'+ current.buildNo +'" class="dyn_popup popup_bg" style="display:none; width:30%;"><div id="prcBuild_'+ current.buildNo +'"><form name="prcBForm"><ul class="row dynamicControls"></ul><div class="hiddenControls"></div></form><div class="flt_right"><input class="btn btn_style" type="button" name="processBuild" data-i18n="[value]common.btn.ok"><input class="btn btn_style dyn_popup_close" type="button"  data-i18n="[value]common.btn.close"></div></div></div></td><td name="buildDep" class="list_img">'+ manageBuilds +'</td><td class="list_img">'+ deleteOpt +'</td>'+testFlightupload+'</tr>';
						});
						
						$("#buildRow tbody").html(tbody);
						$('.dyn_popup').css('display', 'none');
						self.contentDivEvents();
						self.renderlocales(commonVariables.contentPlaceholder);
						commonVariables.navListener.showHideTechOptions();
						$(window).resize();
						self.closeConsole();
					}
				});
			}
		},
		
		contentDivEvents : function(){
			self = this;
			
			//Deploy build popup click event
			$("img[name=deployBuild]").unbind('click');
			$("img[name=deployBuild]").click(function(){
				var current = this,  sqlParam = "", queryStr = "", divId = $(this).closest('tr').find('td:eq(0)').text(), whereToRender = $('#deploye_' + divId + ' ul'), deviceDeploy = $(this).attr("deviceDeploy");
				commonVariables.goal = "deploy";
				commonVariables.phase = "deploy";
				commonVariables.buildNo = divId;
				commonVariables.iphoneDeploy = $(this).attr("deviceDeploy");
				self.checkForLock("Deploy", '', '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						if(deviceDeploy === "true"){
							// call device deployment service
							self.dynamicpage.getHtml(whereToRender, current, '', function(retVal){
								self.clearLogContent();
								$('input[name=buildDelete]').hide();

								$('#deploye_' + divId).find('form[name=deployForm] div #buildNumber').val(divId);
								self.sqlQueryParam($(current).closest('tr').find('form[name=deployForm] #executeSql').is(':checked'), $(current).closest('tr').find('form[name=deployForm] ul[name=sortable2] li'), function(retVal){
									sqlParam = retVal;
								});
								
								queryStr = $(current).closest('tr').find('form[name=deployForm]').serialize().replace("=on", "=true");
								queryStr += '&fetchSql=' + ($.isEmptyObject(sqlParam) === true ? "" : sqlParam);
								queryStr += self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
								self.onMavenServiceEvent.dispatch('mvnDeploy', queryStr, '', '', function(response){
									$('input[name=buildDelete]').show();
									$('.progress_loading').css('display','none');
									if(response !== null && response.errorFound === true){
										$(current).closest('tr').find('form[name=deployForm]').show();
										self.showErrorPopUp(response.responseCode);
									}
									self.openCloseConsole();
								});
							});
						}else{
							if(whereToRender.children().length < 1){
								self.dynamicpage.getHtml(whereToRender, current, '', function(retVal){
									self.callDepOpencc(current, divId);
								});
							}else {
								self.callDepOpencc(current, divId);
							}
						}
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}
				});		
			});
			
			//build deploy click event
			$("input[name=deploy]").unbind('click');
			$("input[name=deploy]").click(function(){
				commonVariables.runType = "deploy";
				$('input[name=kill]').attr('disabled', true);
				var current = this, sqlParam = "", queryStr = "";
				self.clearLogContent();
				$('input[name=buildDelete]').hide();

				self.sqlQueryParam($(this).closest('tr').find('form[name=deployForm] #executeSql').is(':checked'), $(this).closest('tr').find('form[name=deployForm] ul[name=sortable2] li'), function(retVal){
					sqlParam = retVal;
				});
				
				queryStr = $(this).closest('tr').find('form[name=deployForm]').serialize().replace("=on", "=true");
				queryStr += '&fetchSql=' + ($.isEmptyObject(sqlParam) === true ? "" : sqlParam);
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				queryStr += '&displayName=' + displayName;
				queryStr += self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
				self.onMavenServiceEvent.dispatch('mvnDeploy', queryStr, '', '', function(response){
					$('input[name=buildDelete]').show();
					$('.progress_loading').css('display','none');
					if(response !== null && response.errorFound === true){
						$(current).closest('tr').find('form[name=deployForm]').show();
						self.showErrorPopUp(response.responseCode);
					}
					self.openCloseConsole();
				});
			});
			
			//Build download click event
			$("img[name=downloadBuild]").unbind('click');
			$("img[name=downloadBuild]").click(function(){
				self.onDownloadEvent.dispatch($(this).parent().parent().siblings(":first").text(), 'download', function(response){});
			});
			
			//Build upload click event
			$("img[build=uploadBuild]").unbind('click');
			$("img[build=uploadBuild]").click(function(){
				self.opencc(this, $(this).attr('name'), '', 50);
				$('#apiToken').val('');
				$('#teamToken').val('');
				$('#notes').val('');
				$('#notify').val('');
				$('#distributionLists').val('');
			});

			//Upload Button click event
			$("input[name=uploadTestFlight]").unbind('click');
			$("input[name=uploadTestFlight]").click(function(){
				var buildInfo = {};
				buildInfo.buildNo = $(this).attr('buildNo');
				buildInfo.fileExtn = $(this).attr('fileExtn');
				var testFlightObj = {};
				testFlightObj.apiToken = $('#apiToken').val();
				testFlightObj.teamToken = $('#teamToken').val();
				testFlightObj.notes = $('#notes').val();
				testFlightObj.notify = $('#notify').val();
				testFlightObj.distributionLists = $('#distributionLists').val();
				if (self.testFlightValidation()) {
					self.buildListener.getInfo(self.buildListener.getRequestHeader(JSON.stringify(testFlightObj), buildInfo, 'upload'), function(response){
						$('div[ name=testFlightUploadDiv]').hide();
					});
				}
				
			});
			
			//build delete click event
			$('input[name=buildDelete]').unbind('click');
			$('input[name=buildDelete]').click(function(){
				var current = this, divId = $(this).closest('tr').find('td:eq(0)').text();
				self.buildListener.getInfo(self.buildListener.getRequestHeader(JSON.stringify([divId]), '', 'delete'), function(response){
					if(response.responseCode === "PHR700002"){
						$(current).closest('tr').remove();

						if($("#buildRow tbody tr").length < 1){
							$('.qual_unit_main').children().remove();
							$('.qual_unit_main').html('<div class="alert" style="text-align: center;" data-i18n="build.label.nodata"></div>');
							self.renderlocales(commonVariables.contentPlaceholder);
						}
						
						$(".content_end").show();
						$(".msgdisplay").removeClass("error").addClass("success");
						$(".success").attr('data-i18n', 'successCodes.' + response.responseCode);
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".success").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					}
				});
			});
			
			//Process build popup event
			$('table td img[name=procBuild]').unbind('click');
			$('table td img[name=procBuild]').click(function(){
				var current = this, divId = $(this).closest('tr').find('td:eq(0)').text(),
				whereToRender = $('#prcBuild_' + divId + ' ul');
				commonVariables.goal = "process-build";
				commonVariables.phase = "process-build";
				commonVariables.buildNo = divId;
				if(whereToRender.children().length < 1){
					self.dynamicpage.getHtml(whereToRender, this, '', function(retVal){
						if($('#prcBuild_' + divId).find('form[name=prcBForm] ul').children().length > 0){
							self.opencc(current,'prcBuild_' + divId, '' , 50);
						}else{$('#prcBuild_' + divId).hide();}
					});
				}else {
					if($('#prcBuild_' + divId).find('form[name=prcBForm] ul').children().length > 0){
						self.opencc(current,'prcBuild_' + divId, '' , 50);
					}else{$('#prcBuild_' + divId).hide();}
				}
				
			});
			
			//Process build click event
			$('input[name=processBuild]').unbind('click');
			$('input[name=processBuild]').click(function(){
				self.clearLogContent();
				$(".dyn_popup").hide();
				self.onMavenServiceEvent.dispatch('mvnProcessBuild', $(this).closest('tr').find('form[name=prcBForm]').serialize(), '', '', function(response){
					$('.progress_loading').css('display','none');
					if(response !== null && response.errorFound === true){
						$('#build_alert_div').hide();
						self.showErrorPopUp(response.responseCode);
					}else if(response !== null && response.errorFound === false){
						self.refreshContent(true);
					}
					self.openCloseConsole();
				});
			});
			
			//ipa download click event
			$('img[name=ipaDownload]').unbind('click');
			$('img[name=ipaDownload]').click(function(){
				self.onDownloadEvent.dispatch($(this).parent().parent().siblings(":first").text(), 'ipadownload', function(response){});
			});
			
			//Delete build popup event
			$(".deletebuildRow").unbind("click");
			$(".deletebuildRow").click(function() {
				self.opencc(this, $(this).attr('name'), '', 50);
			});
			
			//Show tool tip
			$(".tooltiptop").tooltip();
		},
		
		testFlightValidation :function() {
			var self=this, bCheck = true;
			var apiToken = $('#apiToken').val();
			var teamToken = $('#teamToken').val();
			var notes = $('#notes').val();
			if(apiToken === undefined || apiToken === null || $.trim(apiToken) === ""){
				bCheck = false;
				self.validationMessage($('#apiToken'));
				return bCheck;
			} else if(teamToken === undefined || teamToken === null || $.trim(teamToken) === ""){
				bCheck = false;
				self.validationMessage($('#teamToken'));
				return bCheck;
			} else if(notes === undefined || notes === null || $.trim(notes) === ""){
				bCheck = false;
				self.validationMessage($('#notes'));
				return bCheck;
			} else {
				bCheck = true;
			}
			return bCheck;
		},
		
		validationMessage : function(id) {
			var self=this;
			$(id).attr('placeholder','Enter the Value');
			$(id).addClass("errormessage");
			$(id).focus();
			$(id).val('');
			$(id).bind('keypress', function() {
				$(this).removeClass("errormessage");
			});
		},
		
		miniferClickEvents : function(){
			var self = this;

			//Minifier click event
			$("#btnMinifer").unbind("click");
			$("#btnMinifer").click(function() {
				$('input[name=kill]').attr('disabled', true);
				self.checkForLock("minify", '', '', function(response) {
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						var finalArray = [], fileInfo = {}, type = '', compressName = '', files = '', path = '', queryStr = '',validation = false;
						
						$.each($('#build_minifier table'), function(tIndex, tCurrent){
							$.each($(tCurrent).find('tbody tr'), function(rIndex, rCurrent){
								fileInfo = {}, type = $(rCurrent).find('input[name=fileType]').val().trim(),
								compressName = $(rCurrent).find('input[name='+ type +'MinName]').val().trim(),
								files = $(rCurrent).find('input[name='+ type +'MinFiles]').val().trim(),
								hiddenPath = $(rCurrent).find('input[name='+ type +'filePath]').val().trim(),
								fileWithExtension = ($(rCurrent).find('input[name='+ type +'filePath]').val().trim().split('\\').pop()), 	
								path = hiddenPath !== fileWithExtension ? hiddenPath.replace(fileWithExtension, '') :  hiddenPath;
								if(type !== '' && files !== '' && path !== ''){
									if(compressName === ''){
										$(rCurrent).find('input[name='+ type +'MinName]').addClass('errormessage')
										validation = true;
										return true;
									}else{
										fileInfo.fileType = type;
										fileInfo.compressName = compressName;
										fileInfo.csvFileName = files;
										fileInfo.opFileLoc = path;
										finalArray.push(fileInfo);
									}
								}
							});
							
							if(validation){
								return true;
							}
						});
						
						if(!validation){
							$('#build_minifier').hide();
							self.clearLogContent();
							var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
							if (userInfo !== null) {
								displayName = userInfo.displayName;
							}
							queryStr += '&displayName=' + displayName;
							queryStr += self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
							self.onMavenServiceEvent.dispatch('mvnMinification', queryStr, $('#minAllchk').is(':checked'),  finalArray, function(response){
								$('.progress_loading').css('display','none');
								self.refreshContent(true);
								self.openCloseConsole();
							});
						}
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}	
				}); 
			});
			self.addJSMinRow();	
		},
		
		addJSMinRow : function(){
			var self =this;
			
			$('img[name=jsAdd]').unbind('click');
			$('img[name=jsAdd]').click(function(){
				$(this).hide();
				var trLength = $('tbody[name=jsParent]').children().size();
				if(trLength === 1) {
					$('tbody[name=jsParent]').children().last().children().last().find('img[name=jsRemove]').show();
				}
				self.appendMinifyRow({"opFileLoc": "", "csvFileName": "", "compressName": "", "fileType": "js"});
			});
			
			$('img[name=cssAdd]').unbind('click');
			$('img[name=cssAdd]').click(function(){
				$(this).hide();
				var trLength = $('tbody[name=cssParent]').children().size();
				if(trLength === 1) {
					$('tbody[name=cssParent]').children().last().children().last().find('img[name=cssRemove]').show();
				}
				self.appendMinifyRow({"opFileLoc": "", "csvFileName": "", "compressName": "", "fileType": "css"});
			});
			
			$('img[name=jsRemove]').unbind('click');
			$('img[name=jsRemove]').click(function(){
				$(this).closest('tr').remove();
				var trLength = $('tbody[name=jsParent]').children().size();
				$('tbody[name=jsParent]').children().last().children().last().find('img[name=jsAdd]').show();
				if(trLength === 1) {
				   $('tbody[name=jsParent]').children().last().children().last().find('img[name=jsRemove]').hide();
				}
			});
			
			$('img[name=cssRemove]').unbind('click');
			$('img[name=cssRemove]').click(function(){
				$(this).closest('tr').remove();
				var trLength = $('tbody[name=cssParent]').children().size();
				$('tbody[name=cssParent]').children().last().children().last().find('img[name=cssAdd]').show();
				if(trLength === 1) {
				   $('tbody[name=cssParent]').children().last().children().last().find('img[name=cssRemove]').hide();
				}
			});
			
			$('input[name=jsBrowse]').unbind("click");
			$('input[name=jsBrowse]').click(function(){
				self.hideTreeContent();
				if($(this).closest('tr').find('div[name=treeContent]').children().length > 0){
					self.showTreeContent(this);
				}else{self.loadTreeview(this, 'jsMinFiles', 'jsfilePath', 'js');}
			});
			
			$('input[name=cssBrowse]').unbind("click");
			$('input[name=cssBrowse]').click(function(){	
				self.hideTreeContent();
				if($(this).closest('tr').find('div[name=treeContent]').children().length > 0){
					self.showTreeContent(this);
				}else{self.loadTreeview(this, 'cssMinFiles', 'cssfilePath', 'css');}
			});
			
			$('input[name=treePopupClose], input[name=selectFilePath]').unbind('click');
			$('input[name=treePopupClose], input[name=selectFilePath]').click(function(){
				self.hideTreeContent();
			});
		},
		
		appendMinifyRow : function(current){
			if(current !== null && current.fileType.toLowerCase() === "js" || current.fileType.toLowerCase() === "css"){
				self.count++;
				$('table#'+ current.fileType.toLowerCase() +'min tbody').append('<tr><td><input type="text" count = "'+self.count+'" class="append'+current.fileType.toLowerCase()+'" maxlength="30" name="'+ current.fileType.toLowerCase() +'MinName" value="'+ current.compressName +'"></td><td><input type="text" name="'+ current.fileType.toLowerCase() +'MinFiles" value="'+ current.csvFileName +'" disabled><input type="hidden" name="'+ current.fileType.toLowerCase() +'filePath" value="'+ current.opFileLoc +'"><input type="hidden" name="fileType" value="'+ current.fileType.toLowerCase() +'"><input type="button" name="'+ current.fileType.toLowerCase() +'Browse" value="Browse" data-i18n="[value]build.label.browse" class="btn btn_style"><img src="themes/default/images/Phresco/plus_icon.png" alt="" name="'+ current.fileType.toLowerCase() +'Add" style="display:none;"><img src="themes/default/images/Phresco/minus_icon.png" alt="" name="'+ current.fileType.toLowerCase() +'Remove"><div name="treeTop" class="speakstyletopright dyn_popup" style="right:70px;"><div name="treeContent"></div><div class="flt_right"><input type="button" name="selectFilePath" class="btn btn_style" value="Ok">&nbsp;&nbsp;<input type="button" value="Close" name="treePopupClose" class="btn btn_style"></div></div></td></tr>');
				
				if(current.fileType === 'js') {
					$('tbody[name=jsParent]').children().last().prev().children().last().find('img[name=jsAdd]').hide();
					$('tbody[name=jsParent]').children().last().children().last().find('img[name=jsAdd]').show();
				} else {
					$('tbody[name=cssParent]').children().last().prev().children().last().find('img[name=cssAdd]').hide();
					$('tbody[name=cssParent]').children().last().children().last().find('img[name=cssAdd]').show();
				}	
				
				self.hideTreeContent();
				self.addJSMinRow();
				self.minifierNameValidation($(".append"+current.fileType.toLowerCase()));
			}
		},
		
		minifierNameValidation : function(val) {
			var self=this;
			val.keyup(function() {
				var currentCount = $(this).attr('count');
				var currentVal =  $(this).val();
				var appCodeTextObj = $(this);
				val.each(function(index, value){
					var valueCount = $(value).attr('count');
					var appcodeVal = $(value).val();
					if(currentCount !== valueCount && currentVal === appcodeVal){
						$(appCodeTextObj).val("");
						$(appCodeTextObj).focus();
						$(appCodeTextObj).addClass('errormessage');
						$(appCodeTextObj).attr('placeholder', 'Appcode Already Exists');
						$(appCodeTextObj).bind('keypress', function() {
							$(this).removeClass("errormessage");
							$(appCodeTextObj).attr('placeholder', '');
						});
						return false;
					}
				});
			});
			
		},
		
		hideTreeContent : function(){
			$('div[name=treeTop]').hide();
			$('div[name=treeContent]').hide();
		},
		
		showTreeContent : function(current){
			$(current).closest('tr').find('div[name=treeTop]').show();
			$(current).closest('tr').find('div[name=treeContent]').show();
		},
		
		loadTreeview : function(current, filetype, filePath, type){
			var self = this;
			self.dynamicPageListener.loadTree(current, $(current).closest('tr').find('input[name='+ filetype +']'), $(current).closest('tr').find('div[name=treeContent]'), type, $(current).closest('tr').find('input[name='+ filePath +']'), true);
			//self.showTreeContent(current);
		},
		
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			self.killProcess();
			//Run again source popup click event
			$("input[name=build_runagsource]").unbind("click");
			$("input[name=build_runagsource]").click(function() {
				var openccObj = this, openccObjName = $(this).attr('name');
				self.checkForLock("Start", '', '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002" && $(openccObj).attr("class") === "btn btn_style") {
							commonVariables.goal = "start";
							commonVariables.phase = "run-against-source";
							self.dynamicpage.getHtml($('#build_runagsource ul'), openccObj, openccObjName, function(retVal){
								$('.connectedSortable').sortable({
									connectWith: '.connectedSortable',
									cancel: ".ui-state-disabled"
								});
								$("form[name=runAgainstForm] #build_runagsource").show();
							});
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}	
				});		
			});
			
			//run again source click event
			$("#runSource").unbind("click");
			$("#runSource").click(function(){
				var sqlParam = "", queryStr = "";

				self.sqlQueryParam($('form[name=runAgainstForm] #executeSql').is(':checked'), $('form[name=runAgainstForm] ul[name=sortable2] li'), function(retVal){
					sqlParam = retVal;
				});
				
				queryStr = $('form[name=runAgainstForm]').serialize().replace("=on", "=true");
				queryStr += '&fetchSql=' + ($.isEmptyObject(sqlParam) === true ? "" : sqlParam);
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				queryStr += '&displayName=' + displayName;
				queryStr += self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
				self.onValidationEvent.dispatch('run-against-source', queryStr, function(response){
					if (!response.errorFound && response.status !== "error" && response.status !== "failure"){
						$("form[name=runAgainstForm] #build_runagsource").hide();
						$(".dyn_popup").hide();
						$('#run_against_src_alert_div').show();
						self.clearLogContent();
						
						$("input[name=build_runagsource]").addClass("btn_style_off");
						$("#stop").addClass("btn_style_off");
						$("#restart").addClass("btn_style_off");
						
						self.onMavenServiceEvent.dispatch('mvnRunagainstSource', queryStr, '', '', function(response){
							$('.progress_loading').css('display','none');
							if(response.errorFound && response.status === "error" && response.status === "failure"){
								self.showErrorPopUp(response.responseCode);
							}else{self.runAgainSourceStatus();}
							self.openCloseConsole();
						});
					}
				});
			});
			
			//Run again source stop click event
			$("#stop").unbind("click");
			$("#stop").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					$("input[name=build_runagsource]").addClass("btn_style_off");
					$("#stop").addClass("btn_style_off");
					$("#restart").addClass("btn_style_off");
					var queryStr = self.isBlank($('.moduleName').val()) ? "" : 'moduleName='+$('.moduleName').val();
					self.clearLogContent();
					self.onMavenServiceEvent.dispatch('mvnStopServer', '', '', '', function(response){
						$('.progress_loading').css('display','none');
						self.runAgainSourceStatus();
						self.openCloseConsole();
					});					
				}
			});		
			
			//Run again source restart click event
			$("#restart").unbind("click");
			$("#restart").click(function() {
				if($(this).attr("class") === "btn btn_style"){
					$("input[name=build_runagsource]").addClass("btn_style_off");
					$("#stop").addClass("btn_style_off");
					$("#restart").addClass("btn_style_off");
					
					self.clearLogContent();
					var queryStr = self.isBlank($('.moduleName').val()) ? "" : 'moduleName='+$('.moduleName').val();
					self.onMavenServiceEvent.dispatch('mvnRestartServer', '', '', '', function(response){
						$('.progress_loading').css('display','none');
						self.runAgainSourceStatus();	
						self.openCloseConsole();
					});
				}
			});
			
			//Generate build popup click event
			$("input[name=build_genbuild]").unbind("click");
			$("input[name=build_genbuild]").click(function() {
				$("#package_browse").find("tbody").html('');
				var openccObj = this;
				var openccObjName = $(this).attr('name');
				self.checkForLock("build", '', '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						var whereToRender = $('#build_genbuild ul');
						$("form[name=buildForm]").show();
						if(whereToRender.children().length < 1){
							commonVariables.goal = "package";
							commonVariables.phase = "package";
							self.dynamicpage.getHtml(whereToRender, openccObj, openccObjName, function(retVal){
								var totalControls = whereToRender.find('li.ctrl').length;
									var sectionHeight = $('.testSuiteTable').height()*3/4;
									$('#build_genbuild').css("max-height", sectionHeight - 40 + 'px');
									$('form[name=buildForm]').css("max-height", sectionHeight - 92 + 'px');
									$('form[name=buildForm]').css("overflow-y", "auto");
									/* $("form[name=buildForm]").mCustomScrollbar({
										autoHideScrollbar:true,
										theme:"light-thin",
										advanced:{ updateOnContentResize: true}
									});  */
								$("#buildNumber").bind('keypress',function(e) {
									if((e.which >= 48 && e.which <= 57) || (e.which === 8)){return true;}else {e.preventDefault();}
								});
							});
						}else{
							self.opencc(openccObj,openccObjName);
							$("#package_browse").find("tbody").append('<tr><td><input type="text" class="browse_build border_div" name="targetFolder"></td><td><input type="text" class="browse_build border_div" name="selectedFileOrFolderValue" disabled><input type="hidden" name="selectedFileOrFolder" value=""><input type="hidden" name="selectedFiles" value=""><input type="button" class="btn btn_style" value="Browse" name="browseFile"><a href="#"><img name=jsAdd src="themes/default/images/Phresco/plus_icon.png" alt=""></a><a name="remove"></a><div id="browseFile" class="dyn_popup" style="display:none"><div name="treeContent"></div><div class="flt_right"><input type="button" name="selectFilePath" class="btn btn_style" value="Ok">&nbsp;&nbsp;<input type="button" value="Close" name="treePopupClose" class="btn btn_style dyn_popup_close"></div></div></td></tr>');
							self.dynamicpage.dynamicPageListener.packageBrowseEvents();
						}
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}
				});
			});
			
			//build run click event
			$("#buildRun").unbind("click");
			$("#buildRun").click(function(){
				commonVariables.runType = "build";
				$('input[name=kill]').attr('disabled', true);
				var sqlParam = "";
				
				var targetFolders = [];
				var fileOrFolders = [];
				$('input[name=selectedFileOrFolderValue]').each(function(i) {
					fileOrFolders.push($(this).val());
				});

				$('input[name=targetFolder]').each(function(i) {
					targetFolders.push($(this).val());
				});
				
				var selectedFiles = [];
				for (i in targetFolders) {
					if ((!self.isBlank(targetFolders[i]) && self.isBlank(fileOrFolders[i])) ||
					(!self.isBlank(fileOrFolders[i]) && self.isBlank(targetFolders[i]))
					|| (!self.isBlank(targetFolders[i]) && !self.isBlank(fileOrFolders[i]))) {
						selectedFiles.push(targetFolders[i] + "#FILESEP#" + fileOrFolders[i]);
					}
				}
				
				$("input[name=selectedFiles]").val(selectedFiles.join("#SEP#"));
				
				queryStr = $('form[name=buildForm]').serialize().replace("=on", "=true");
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				queryStr += '&displayName=' + displayName;
				queryStr += self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();

				self.onValidationEvent.dispatch('package', queryStr, function(response){
					if (!response.errorFound && response.status !== "error" && response.status !== "failure"){
						$("form[name=buildForm]").hide();
						$("#build_genbuild").hide();
						$('#build_alert_div').show();
						self.clearLogContent();
						self.onMavenServiceEvent.dispatch('mvnBuild', queryStr, '', '', function(response){
							$('.progress_loading').css('display','none');
							if (!response.errorFound && response.status !== "error" && response.status !== "failure"){
								self.refreshContent(true);
								self.openCloseConsole();
							}
						});
					}
				});
			});
			
			//Minifier popup click event
			$("input[name=build_minifier]").unbind("click");
			$("input[name=build_minifier]").click(function() {
				var openccObj = this;
				var openccObjName = $(this).attr('name');
				var jsCount = 0;
				var cssCount = 0;
				self.checkForLock("minify", '', '', function(response) {
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						$("#build_minifier").html('<input type="checkbox" id="minAllchk"> Compress All Files <table id="jsmin" class="table table-striped table_border table-bordered border_div" cellpadding="0" cellspacing="0" border="0" style="margin-top:8px;"><thead><tr><th colspan="2">JS Minification</th></tr></thead><tbody name="jsParent"></tbody></table><table id="cssmin" class="table table-striped table_border table-bordered border_div" cellpadding="0" cellspacing="0" border="0"><thead><tr><th colspan="2">CSS Minification</th></tr></thead><tbody name="cssParent"></tbody></table><div class="flt_right"><input type="button" value="Minify" data-i18n="[value]build.label.minify" class="btn btn_style" name="btnMinifer" id="btnMinifer"><input type="button" value="Close" data-i18n="[value]common.btn.close" class="btn btn_style dyn_popup_close"></div>');
						
						
						self.buildListener.getInfo(self.buildListener.getRequestHeader("", '', 'minifyList'), function(response){
							if(response !== null && response.data !== null){
								$.each(response.data, function(index, current){
									if(current.fileType === 'js') {
									    jsCount ++;
									} else {
										cssCount ++;
									}
									self.appendMinifyRow(current);
								});
								if($("tbody[name=jsParent]").children().size() === 1) {
									$('tbody[name=jsParent]').children().last().children().last().find('img[name=jsRemove]').hide();
								}
								if($("tbody[name=cssParent]").children().size() === 1) {
									$('tbody[name=cssParent]').children().last().children().last().find('img[name=cssRemove]').hide();
								}
							} 
							
							if(jsCount === 0) {
								self.appendMinifyRow({"opFileLoc": "", "csvFileName": "", "compressName": "", "fileType": "js"});
							} 
							if(cssCount === 0){
								self.appendMinifyRow({"opFileLoc": "", "csvFileName": "", "compressName": "", "fileType": "css"});
							}
						});
		
						self.miniferClickEvents();
						self.opencc(openccObj, openccObjName);
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}
				});		
			});
			
			//Log console div click event
			$("#buildConsole").unbind("click");
			$("#buildConsole").click(function() {
				if($('#logContent').text().toLowerCase().match("info: starting coyote http/1.1") ||
					$('#logContent').text().toLowerCase().match("server running at https://") ||
					$('#logContent').text().toLowerCase().match("server running at http://")){
					$("input[name=build_runagsource]").removeClass('btn_style');
					$("input[name=build_runagsource]").addClass('btn_style_off');
					$('.progress_loading').css('display','none');
					$("#stop").removeClass('btn_style_off');
					$("#stop").addClass('btn_style');
					$("#restart").removeClass('btn_style_off');
					$("#restart").addClass('btn_style');
					//self.runAgainSourceStatus();
				}
				self.onProgressEvent.dispatch(this);
			});

			//To open the build directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type = commonVariables.typeBuild;
				commonVariables.hideloading = true;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of build directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type = commonVariables.typeBuild;
				commonVariables.hideloading = true;
				commonVariables.navListener.copyPath(paramJson);
			});
			
			$(window).resize(function() {
				$(".dyn_popup").hide();
				
				var w1 = $(".scrollContent tr:nth-child(1) td:first-child").width();
				var w2 = $(".scrollContent tr:nth-child(1) td:nth-child(2)").width();
				var w3 = $(".scrollContent tr:nth-child(1) td:nth-child(3)").width();
				var w4 = $(".scrollContent tr:nth-child(1) td:nth-child(4)").width();
				var w5 = $(".scrollContent tr:nth-child(1) td:nth-child(5)").width();
				var w6 = $(".scrollContent tr:nth-child(1) td:nth-child(6)").width();
				
				$(".fixedHeader tr th:first-child").css("width",w1);
				$(".fixedHeader tr th:nth-child(2)").css("width",w2);
				$(".fixedHeader tr th:nth-child(3)").css("width",w3);
				$(".fixedHeader tr th:nth-child(4)").css("width",w4);
				$(".fixedHeader tr th:nth-child(5)").css("width",w5);
				$(".fixedHeader tr th:nth-child(6)").css("width",w6);
				
				//self.resizeConsoleWindow();
			});
			
			//set log console scroll content event
			self.tableScrollbar();
			
			//call content div events
			self.contentDivEvents();
			self.customScroll($(".consolescrolldiv"));

			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.build.js.Build;
});