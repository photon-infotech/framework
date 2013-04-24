define(["framework/widget", "framework/widgetWithTemplate", "application/api/applicationAPI", "features/features"], function() {

	Clazz.createPackage("com.components.application.js.listener");

	Clazz.com.components.application.js.listener.ApplicationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		applicationAPI : null,
		featureContent : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
				this.applicationAPI = new Clazz.com.components.application.js.api.ApplicationAPI();
				this.featureContent = new Clazz.com.components.features.js.Features();
		},
		
		onFeature : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.featureContent, true);
		},
		
		addServerDatabase : function(appType) {
			var self = this, server = '<tr class="servers"> <td data-i18n="application.edit.servers"></td><td><select><option>Select Servers</option></select></td><td data-i18n="application.edit.versions"></td><td colspan="3"><select><option>Select Version</option></select> <img name="addServer" src="../themes/default/images/helios/plus_icon.png" width="25" height="20" border="0" alt=""> <img name="removeServer" src="../themes/default/images/helios/minus_icon.png"  width="25" height="20" border="0" alt=""></td></tr>',
			
			database ='<tr class="database"><td data-i18n="application.edit.database"></td><td><select><option>Select Database</option></select></td><td data-i18n="application.edit.versions"></td> <td colspan="3"><select> <option>Select Version</option></select><img src="../themes/default/images/helios/plus_icon.png" name="addDatabase" width="25" height="20" border="0" alt=""> <img src="../themes/default/images/helios/minus_icon.png" name="removeDatabase" width="25" height="20" border="0" alt=""></td></tr>';
			if (appType === "addServer") {
				$(server).insertAfter($("img[name=addServer]").parents('tr.servers:last'));
			} else {
				$(database).insertAfter($("img[name=addDatabase]").parents('tr.database:last'));
			}
			
			$("img[name=addServer]").unbind("click");
			$("img[name=addDatabase]").unbind("click");
			self.addServerDatabaseEvent();
			$("img[name=removeServer]").unbind("click");
			$("img[name=removeDatabase]").unbind("click");
			self.removeServerDatabaseEvent();
			
		},

		addServerDatabaseEvent : function(){
			var self=this;
			$("img[name=addServer]").click(function(){
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'));
			});
			
			$("img[name=addDatabase]").click(function(){
				self.dynamicRenderLocales(commonVariables.contentPlaceholder);
				self.addServerDatabase($(this).attr('name'));
			});
		},
		
		removeServerDatabaseEvent : function() {
			var self=this;
			$("img[name=removeServer]").click(function(){
				$(this).parent().parent().remove();;
			});
			
			$("img[name=removeDatabase]").click(function(){
				$(this).parent().parent().remove();;
			});
		},
		
		dynamicRenderLocales : function(contentPlaceholder) {
			var self=this;
			self.renderlocales(contentPlaceholder);
		}
		
	});

	return Clazz.com.components.application.js.listener.ApplicationListener;
});