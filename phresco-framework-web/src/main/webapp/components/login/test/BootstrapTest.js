
var commonVariables = {
	globalconfig : "",
	webserviceurl : "",
	contexturl : "src",
	
	login : "login",
	loginContext : "login",

	header : "header",
	headerContext : "header",
	
	footer : "footer",
	footerContext : "footer",
	
	navigation : "navigation",
	navigationContext : "",

	basePlaceholder : "basepage\\:widget",
	headerPlaceholder : "header\\:widget",
	contentPlaceholder : "content\\:widget",
	footerPlaceholder : "footer\\:widget",
};

define(["jquery"], function($) {
	$(document).ready(function(){
		

		$.get('src/components/login/test/config.json', function(data) {
			commonVariables.globalconfig = data;
			commonVariables.webserviceurl = "framework/";
			configJson = {
				// comment out the below line for production, this one is so require doesn't cache the result
				urlArgs: "time=" +  (new Date()).getTime(),
				baseUrl: "src/",
				
				paths : {
					framework : "js/framework",
					listener : "js/commonComponents/listener",
					fastclick : "lib/fastclick",
					api : "js/api",
					lib : "lib",
					signals : "lib/signals",
					common : "js/commonComponents/common",
					modules: "js/commonComponents/modules",
					Clazz : "js/framework/class",
					components: "components",
					configData: data,
				}
			};

			$.each(commonVariables.globalconfig.components, function(index, value){
				configJson.paths[index] = value.path;
			});

			// setup require.js
			var requireConfig = requirejs.config(configJson);
			
			require(["lib/i18next-1.6.0", "projectlistTest", "headerTest", "footerTest", "navigationTest"],	function (next, projectlistTest, headerTest, footerTest, navigationTest){
				headerTest.runTests(data);
				footerTest.runTests(data);
				projectlistTest.runTests(data);
				navigationTest.runTests(data); 
			});
		}, "json");
	});
});