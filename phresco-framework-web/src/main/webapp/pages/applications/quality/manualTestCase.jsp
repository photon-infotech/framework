<%--

    Framework Web Archive

    Copyright (C) 1999-2013 Photon Infotech Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@page import="com.photon.phresco.commons.model.TestCase"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>

<%
	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appDirName = appInfo.getAppDirName();
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
	String requestIp = (String) request.getAttribute(FrameworkConstants.REQ_REQUEST_IP);
	String showIcons = (String) session.getAttribute(requestIp);
    String iconClass = "";
	if (!Boolean.parseBoolean(showIcons))  {
		iconClass = "hideIcons";
	}
	
	Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
	String per_disabledStr = "";
	String per_disabledClass = "btn-primary";
	if (permissions != null && !permissions.canManageTests()) {
		per_disabledStr = "disabled";
		per_disabledClass = "btn-disabled";
	}
%>
<form id="manualTestCases" class="marginBottomZero" style="height: 114%;overflow-x: hidden;overflow-y: hidden	;margin-top: 1px;">
		<div>
			<ul id="display-inline-block-example">
				 <li id="first">
					<%-- <a id="addTest" class="btn btn-primary"><s:text name='lbl.btn.add'/></a>  --%>
				</li> 
				<!-- <div class="alert alert-block hideContent" id="errorDiv" style="margin-left: 0; margin-top: 5px;"> -->
			</ul>	
		</div>
		
		<ul id="display-inline-block-example">
			<li id="first">
				<input type="button" id="addTest" class="btn <%= per_disabledClass %>" <%= per_disabledStr %> style="margin-left: 8%;" 
					value="<s:text name='lbl.btn.add'/>">
			</li>
			<li id="label">
				&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="lbl.test.suite"/></strong> 
			</li>
			<li>
				<select id="testSuite" name="testSuite" onchange='reportList(this);' class="hideContent"></select>
			</li>
			<li id="label" class="viewLable">
				&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="lbl.test.result.view"/></strong> 
			</li>
			<li>
				<select id="resultView" name="resultView" class="input-medium hideContent"> 
					<option value="tabular"><s:text name="lbl.test.view.tabular"/></option>
					<option value="graphical"><s:text name="lbl.test.view.graphical"/></option>
				</select>
			</li>
		</ul>
	</div>
	<div class="icon_fun_div printAsPdf" style="margin-top: -35px;">
			<a href="#" id="pdfPopup" >
				<img id="pdfCreation" src="images/icons/print_pdf.png" title="Generate pdf" style="height: 20px; width: 20px;"/>
			</a>
			<a href="#" class="<%= iconClass %>" id="openFolder">
				<img id="folderIcon" src="images/icons/open-folder.png" title="Open folder"/>
			</a>
			<a href="#" class="<%= iconClass %>" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>
	<div class="" id="graphicalView" style="padding-left: 15px; display:none; text-align: center;">
		<canvas id="bar" width="620" height="400">[No canvas support]</canvas>               
	</div>
	
	<div class="canvas_div canvasDiv" id="graphicalPieView">
		<canvas id="pie2" width="620" height="335">[No canvas support]</canvas>
	</div>
	
	<div class="table_div_manual qtyTable_view" id="tabularViewForManual" style="width:99%; overflow: auto;">
           	<div class="fixed-table-container responsiveFixedTableContainer qtyFixedTblContainer">
      			<div class="header-background"> </div>
	      		<div class="table-container-manualInner" style="height: 587px !important;">
			        <div style="overflow: auto;">
				        <table cellspacing="0" class="zebra-striped">
				          	<thead>
					            <tr>
									<th class="firstDiv" style="width:23%">
										<div id="thName" class="th-inner-test"><s:text name="label.testsuite.name"/></div>
									</th>
					              	<th class="secondDiv" style="width:11%">
					              		<div id="thPass" class="th-inner-test"><s:text name="label.testsuite.success"/></div>
				              		</th>
					              	<th class="thirdDiv" style="width:11%">
					              		<div id="thFail" class="th-inner-test"><s:text name="label.testsuite.failure"/></div>
				              		</th>
				              		<th class="fourthDiv" style="width:11%">
					              		<div id="thNotApp" class="th-inner-test"><s:text name="label.testcase.not.applicable"/></div>
				              		</th>
					              	<th class="fifthDiv" style="width:11%">
					              		<div id="thBlocked" class="th-inner-test"><s:text name="label.testcase.blocked"/></div>
				              		</th>
				              		<th class="sixthDiv" style="width:11%">
					              		<div id="thNotExceuted" class="th-inner-test"><s:text name="label.testsuite.notexecuted"/></div>
				              		</th>
					              	<th class="seventhDiv" style="width:11%">
					              		<div id="thTotal" class="th-inner-test"><s:text name="label.testsuite.total"/></div>
				              		</th>
					              	<th class="eightthDiv">
					              		<div id="thCovarage" class="th-inner-test"><s:text name="label.testsuite.testCoverage"/></div>
				              		</th>
					            </tr>
				          	</thead>
					
				          	<tbody id="testSuiteList">
				          	
				          	</tbody>
				          	<tfoot id="total">
				          	</tfoot>
				        </table>
					</div>
   				</div>
			</div>
		</div>
		<div id ="errorDiv" class="alert alert-message block-message warning hideContent">
			<center class="noData"><s:text name="lbl.no.manual.testsuites"/></center>
		</div>
		<div class="table_div_manual qtyTable_view" id="testCaseTable" style="width:99%; overflow: auto;">
           	<div class="fixed-table-container responsiveFixedTableContainer qtyFixedTblContainer">
      			<div class="header-background"> </div>
	      		<div class="table-container-manualInner" style="height: 589px !important;">
			        <div style="overflow: auto;">
				        <table id="testCaseTable" class="zebra-striped" cellspacing="0">
							<thead>
								<tr>
								<th class="first" style="width:13%">
									<div id="thName" class="th-inner-test"><s:text name="label.testcase.featureId"/></div>
								</th>
								<th class="second" style="width:13%">
									<div id="thName" class="th-inner-test"><s:text name="label.testcase.Id"/></div>
								</th>
								
								<th class="third" style="width:21%">
									<div id="thName" class="th-inner-test"><s:text name="label.testcase.expected.result"/></div>
								</th>
								<th class="fourth" style="width:23%">
									<div id="thName" class="th-inner-test"><s:text name="label.testcase.Actual.result"/></div>
								</th>
								<th class="fifth" style="width:09%">
									<div id="thName" class="th-inner-test"><s:text name="label.testcase.status"/></div>
								</th>
								<th class="sixth">
									<div id="thName" class="th-inner-test"><s:text name="label.testcase.comment"/></div>
								</th>
								</tr>
							</thead>
							<tbody id="testCasesList">
							
							</tbody>
						</table>
					</div>
   				</div>
			</div>
		</div>
		<div id ="errorDivForTestCase" class="alert alert-message block-message warning hideContent">
			<center class="noData"><s:text name="lbl.no.manual.testcases"/></center>
		</div>
</form>		
<script>

var allValues = "";
var allTestCases = "";

$(document).ready(function() {
	if(!isiPad()){
		/* JQuery scroll bar */
		$("#graphicalView").scrollbars();
	}
	hideLoadingIcon();
	$('#tabularViewForManual').hide();
	$('#testCaseTable').hide();
	$("#graphicalView").hide();
	$("#graphicalPieView").hide();
	readManualTestCases();
	/* $('#manualTest').click(function() {
		yesnoPopup('showManualTestPopUp', '<s:text name="lbl.manual.test"/>', 'runManualTest','<s:text name="lbl.test"/>');
	}); */
	
	$('#openFolder').click(function() {
		 openFolder('<%= appDirName %><%= path %>');
	});
	       
	$('#copyPath').click(function() {
		copyPath('<%= appDirName %><%= path %>');
	});
	    
	$('#pdfCreation').click(function() {
		var params = "fromPage=manual";
		params = params.concat("&testType=");
	    params = params.concat('<%= FrameworkConstants.MANUAL %>');
		yesnoPopup('showGeneratePdfPopup', '<s:text name="lbl.app.generatereport"/>', 'printAsPdf','<s:text name="lbl.app.generate"/>', '', params);
    });
	
	$('#resultView').change(function() {
		changeView();
		canvasInit();
		canvasInitPie();
	});
		
	$('#addTest').click(function() {
		var value = $("#testSuite").val();
		if (value === "All") {
			showLoadingIcon();
			var params = getBasicParams();
			params = params.concat("&type=");
			params = params.concat(value);
			loadContent("addTestSuites", $('#manualTestCases'), $('#subcontainer'), params);
		} else {
			showLoadingIcon();
			var params = getBasicParams();
			params = params.concat("&type=");
			params = params.concat(value);
			loadContent("addTestCases", $('#manualTestCases'), $('#subcontainer'), params);
		}
	});
});

	//changeView();
	function changeView() {
		var resultView = $('#resultView').val();
		if (resultView == 'graphical') {
			var value = $("#testSuite").val();
			if (value === "All") {
				$("#graphicalView").show();
				$("#tabularViewForManual").hide();
				$('#testCaseTable').hide();
				$("#graphicalPieView").hide();
				showPieView();
			} else {
				$("#graphicalView").hide();
				$("#tabularViewForManual").hide();
				$('#testCaseTable').hide();
				showPieView();
			}
			
		} else  {
			var value = $("#testSuite").val();
			if (value === "All") {
				$("#graphicalView").hide();
				$("#graphicalPieView").hide();
				$("#tabularViewForManual").show();
				$('#testCaseTable').hide();
			} else {
				$("#graphicalView").hide();
				$("#graphicalPieView").hide();
				$("#tabularViewForManual").hide();
				$('#testCaseTable').show();
				getReport('',value);
			}
		}
	}
	
	var failurePercent;
	var successPercent;
	var notAppPercent;
	var blockedPercent;
	var name;
	var total;
	var notExePercent;
	function showPieView() {
		$("#graphicalPieView").show();
		var testSuiteName = $("#testSuite").val();
		failurePercent = 0;
		successPercent = 0;
		notAppPercent = 0;
		blockedPercent = 0;
		notExePercent = 0;
		name = "";
		total = 0;
		name = testSuiteName;
		
		for (i in allValues.allTestSuite) {
			if (allValues.allTestSuite[i].name === testSuiteName) {
				failurePercent = parseFloat(allValues.allTestSuite[i].failures);
				successPercent = parseFloat(allValues.allTestSuite[i].tests);
				notAppPercent = parseFloat(allValues.allTestSuite[i].notApplicable);
				blockedPercent = parseFloat(allValues.allTestSuite[i].blocked);
				total = parseFloat(allValues.allTestSuite[i].total);
				notExePercent = parseFloat(total-(successPercent + failurePercent + notAppPercent + blockedPercent));
				canvasInitPie();
			} else {
				failurePercent += parseFloat(allValues.allTestSuite[i].failures);
				successPercent += parseFloat(allValues.allTestSuite[i].tests);
				notAppPercent += parseFloat(allValues.allTestSuite[i].notApplicable);
				blockedPercent += parseFloat(allValues.allTestSuite[i].blocked);
				total += parseFloat(allValues.allTestSuite[i].total);
			}
		}
		
		if(name === "All") {
			notExePercent = parseFloat(total-(successPercent + failurePercent + notAppPercent + blockedPercent));
			canvasInit();
		}
		
	}
	function readManualTestCases() {
		showLoadingIcon();
		var params = getBasicParams();
		loadContent("manualTestCases", $('#manualTestCases'),'', params, true, true);
	}
	
	function popupOnOk(obj) {
			var okUrl = $(obj).attr("id");
			if (okUrl === "runManualTest") {
				$("#popupPage").modal('hide');
				showLoadingIcon();
				var path = $("#xlsxFileLocation").val();
				var params = "";
		    	if (!isBlank($('form').serialize())) {
		    		params = $('form').serialize() + "&";
		    	}
		    	params = params.concat("filePath=");
		    	params = params.concat(path);
				loadContent("runManualTest", $('#manualTestCases'),'', params, true, true);
			} else if (okUrl === "printAsPdf") {
				printPdfPreActions();
				loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), '', false);
			}
	}
	
	function successEvent(pageUrl, data) {
		if (pageUrl == "runManualTest" || pageUrl == "manualTestCases") {
			hideLoadingIcon();
			if ((data.allTestSuite != undefined || !isBlank(data.allTestSuite))) {
				allValues = data;
				$('#testSuite').empty();
				$('#testSuite').append($("<option></option>").attr("value", "All").text("All"));
				for (i in data.allTestSuite) {
					$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").show();
					fillOptions($('#testSuite'),data.allTestSuite[i].name, data.allTestSuite[i].name);
				}
				allReports();
			} else {
				$('#errorDiv').removeClass('hideContent');
				$('#addTest').hide();
			}		
		} else if(pageUrl == "readManualTestCases") {
			hideLoadingIcon();
			if ((data.allTestCases != undefined || !isBlank(data.allTestCases))) {
				allTestCases = data;
				$('#testCaseTable').show();
				$('#testCasesList').empty();
				for (i in data.allTestCases) {
					var newTestCaseRow = $(document.createElement('tr')).attr("id", data.allTestCases[i].testCaseId);
					newTestCaseRow.html("<td class='firstVal'><a href='#' onclick='getTestCaseReport(this);' name="+data.allTestCases[i].featureId+">"+data.allTestCases[i].featureId+"</td>"+
							"<td class='secondVal'>"+data.allTestCases[i].testCaseId+"</td>"+
							"<td class='thirdVal'>"+data.allTestCases[i].expectedResult+"</td>"+ 
							"<td class='fourthVal'>"+data.allTestCases[i].actualResult+"</td>"+
							"<td class='fifthVal'>"+data.allTestCases[i].status+"</td>"+
							"<td class='sixthVal'>"+data.allTestCases[i].bugComment+"</td>")
				 	newTestCaseRow.appendTo("#testCasesList");	
				}
			} else {
				$('#testCaseTable').hide();
				$('#errorDivForTestCase').removeClass('hideContent');
			}
		}
		
	}
	
	var totalPass;
	var totalFail;
	var notExecuted;
	var notApplicable;
	var blocked;
	var totalTestCases;
	var totalCoverage;
	function allReports() {
		$('#testCaseTable').hide();
		$('#testSuiteList').empty();
		totalPass = 0;
		totalFail = 0;
		blocked = 0;
		notApplicable = 0;
		notExecuted = 0;
		totalTestCases = 0;
		totalCoverage = 0;
		for (i in allValues.allTestSuite) {
			$('#tabularViewForManual').show();
			totalPass += parseFloat(allValues.allTestSuite[i].tests);
			totalFail += parseFloat(allValues.allTestSuite[i].failures);
			notApplicable += parseFloat(allValues.allTestSuite[i].notApplicable);
			blocked += parseFloat(allValues.allTestSuite[i].blocked);
			notExecuted += parseFloat(allValues.allTestSuite[i].errors);
			var notexe = allValues.allTestSuite[i].errors;
			if (notexe === 0) {
				notexe = allValues.allTestSuite[i].total - (allValues.allTestSuite[i].tests + allValues.allTestSuite[i].failures +
						allValues.allTestSuite[i].notApplicable + allValues.allTestSuite[i].blocked);
				notExecuted += notexe;
			}
			totalTestCases += parseFloat(allValues.allTestSuite[i].total);
			var coverage = allValues.allTestSuite[i].testCoverage;
			if (coverage === 0) {
				var total = parseFloat(allValues.allTestSuite[i].total);
				var notExecutd = parseFloat(allValues.allTestSuite[i].errors);
				if (notExecutd !=0) {
					coverage = ((total-notExecutd)/total)*100;
				}
				totalCoverage += coverage;
			}
		var newPropTempRow = $(document.createElement('tr')).attr("id", allValues.allTestSuite[i].name);
		newPropTempRow.html("<td class='firstVal'><a href='#' onclick='getReport(this);' name="+allValues.allTestSuite[i].name+">"+allValues.allTestSuite[i].name+"</a></td>"+
				"<td class='secondVal'>"+allValues.allTestSuite[i].tests+"</td>"+
				"<td class='thirdVal'>"+allValues.allTestSuite[i].failures+"</td>"+
				"<td class='fourthVal'>"+allValues.allTestSuite[i].notApplicable+"</td>"+
				"<td class='fifthVal'>"+allValues.allTestSuite[i].blocked+"</td>"+
				"<td class='sixthVal'>"+notexe+"</td>"+
				"<td class='seventhVal'>"+allValues.allTestSuite[i].total+"</td>"+ 
				"<td class='eightthVal'>"+Math.round(coverage)+"</td>")
	 	newPropTempRow.appendTo("#testSuiteList");	
		}
		var totalCovrge = ((totalTestCases-notExecuted)/totalTestCases)*100;
		$('#total').empty();
		 var totalRow = $(document.createElement('tr')).attr("id", "total");
		totalRow.html("<td class='width-ten-percent loadTestPopupBold'>Total</td>"+
				"<td class='width-ten-percent loadTestPopupBold'>"+totalPass+"</td>"+
				"<td class='width-ten-percent loadTestPopupBold'>"+totalFail+"</td>"+
				"<td class='width-ten-percent loadTestPopupBold'>"+notApplicable+"</td>"+
				"<td class='width-ten-percent loadTestPopupBold'>"+blocked+"</td>"+
				"<td class='width-ten-percent loadTestPopupBold'>"+notExecuted+"</td>"+
				"<td class='width-ten-percent loadTestPopupBold'>"+totalTestCases+"</td>"+ 
				"<td class='width-ten-percent loadTestPopupBold'>"+Math.round(totalCovrge)+"</td>")
	 	totalRow.appendTo("#total");	
		if (totalPass === 0 && totalFail === 0) {
			$('.viewLable').hide();
			$('#resultView').hide();
		} else {
			$('.viewLable').show();
			$('#resultView').show();
		}
	}
	
	function getReport(obj, val) {
		var id = $(obj).text();
		var testSuiteName = "";
		if (val === undefined || isBlank(val)) {
			testSuiteName = id;
		} else {
			testSuiteName = val;
		}
		 $('select[name=testSuite] option').each(function () {
	  		var value = $(this).val();
	  		if (value === testSuiteName) {
	  		$(this).attr("selected", "selected");
	  		}
	    }); 
		$('#tabularViewForManual').hide();
	    $('#testCaseTable').show();
	    showLoadingIcon();
	    var params = getBasicParams();
	    params = params.concat("&testSuitName=");
    	params = params.concat(testSuiteName);
		loadContent("readManualTestCases", $('#manualTestCases'),'', params, true, true);
	}
	
	function getTestCaseReport(obj) {
		var id = $(obj).text();
		var testSuite = $("#testSuite").val();
		showLoadingIcon();
		var params = getBasicParams();
		params = params.concat("&testId=");
		params = params.concat(id);
		params = params.concat("&type=");
		params = params.concat(testSuite);
		loadContent("addTestCases", $('#manualTestCases'), $('#subcontainer'), params);
	}
	function reportList(obj) {
		var resultView = $('#resultView').val();
		if (resultView === "tabular") {
			var val = $(obj).val();
			if(val === "All") {
				allReports();
			} else {
				getReport('',val);
			}
		} else {
			changeView();
			
		}
	}
	
	function canvasInit() {
		var theme = localStorage["color"];
        var chartTextColor = "";
        var chartGridColor = "";
        var chartAxisColor = "";
        var chartBarColor = "";
		//line chart color
      	var successColor = "";
      	var failureColor = "";
      	var errorColor = "";
      	var notExeColor = "";
      	var notAppColor = "";
      	var blockedColor = "";
		if (theme == undefined || theme == "themes/photon/css/red.css") {
	        chartTextColor = "white"; // axis text color
	        chartGridColor = "white"; // grid
	        chartAxisColor = "white"; // axis color
	        chartBarColor = "#B1121D"; //Bar color
	        //line chart color
	      	successColor = "#6f6";
	      	failureColor = "red";
	      	errorColor = "red";
	      	notExeColor = "grey";
	      	notAppColor = "#800000";
	      	blockedColor = "orange";
		} else {
	        chartTextColor = "#4C4C4C";
	        chartGridColor = "#4C4C4C";
	        chartAxisColor = "#4C4C4C";
	        chartBarColor = "#00A8F0";
	      //line chart color
	      	successColor = "#6f6";
	      	failureColor = "red";
	      	errorColor = "red";
	      	notExeColor = "grey";
	      	notAppColor = "#800000";
	      	blockedColor = "orange";
		}
		
        var bar1 = new RGraph.Bar('bar',[[ failurePercent, notExePercent, successPercent,notAppPercent, blockedPercent]]);
		bar1.Set('chart.background.barcolor1', 'transparent');
		bar1.Set('chart.background.barcolor2', 'transparent');
		bar1.Set('chart.key', ['Failure','NotExecuted','Success','NotApplicable', 'Blocked']);
		bar1.Set('chart.key.position.y', 35);
		bar1.Set('chart.key.position', 'gutter');
		bar1.Set('chart.colors', [failureColor, notExeColor, successColor, notAppColor, blockedColor]);
		bar1.Set('chart.shadow', false);
		bar1.Set('chart.shadow.blur', 0);
		bar1.Set('chart.shadow.offsetx', 0);
		bar1.Set('chart.shadow.offsety', 0);
		bar1.Set('chart.key.linewidth', 0);
		bar1.Set('chart.yaxispos', 'left');
		bar1.Set('chart.strokestyle', 'rgba(0,0,0,0)');
		bar1.Set('chart.text.angle', 45);
		bar1.Set('chart.text.color', chartTextColor);
		bar1.Set('chart.axis.color', chartAxisColor);
		bar1.Set('chart.gutter.left', 60);
		bar1.Set('chart.background.grid.color', chartGridColor);				
		bar1.Set('chart.gutter.bottom', 175);
		bar1.Set('chart.background.grid.autofit',true);
		bar1.Draw();
	}
	
	function canvasInitPie() {
	
		var pie2 = new RGraph.Pie('pie2', [ failurePercent, notExePercent, successPercent,notAppPercent, blockedPercent]); // Create the pie object
		pie2.Set('chart.gutter.left', 45);
		pie2.Set('chart.colors', ['red', 'grey', '#6f6', '#800000','orange']);
		pie2.Set('chart.key', ['Failures ('+failurePercent+')', 'NotExecuted ('+notExePercent+')','Success ('+successPercent+')', 'NotApplicable ('+notAppPercent+')','Blocked ('+blockedPercent+')', 'Total Tests ('+total+')']);
		pie2.Set('chart.key.background', 'white');
		pie2.Set('chart.strokestyle', 'white');
		pie2.Set('chart.linewidth', 3);
		pie2.Set('chart.title', ''+name+' Report');
		pie2.Set('chart.title.size',10);
		pie2.Set('chart.title.color', '#8A8A8A');
		pie2.Set('chart.exploded', [5,5,0]);
		pie2.Set('chart.shadow', true);
		pie2.Set('chart.shadow.offsetx', 0);
		pie2.Set('chart.shadow.offsety', 0);
		pie2.Set('chart.shadow.blur', 25);
		pie2.Set('chart.radius', 100);
		pie2.Set('chart.background.grid.autofit',true);
		if (RGraph.isIE8()) {
	    	pie2.Draw();
		} else {
	    	RGraph.Effects.Pie.RoundRobin(pie2);
		}
	}
</script>