/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
package com.photon.phresco.framework.rest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.plexus.util.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.ApplicationType;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.TechnologyGroup;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ClientHelper;
import com.photon.phresco.util.ServiceConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * The Class TechnologyService.
 */
@Path("/technology")
public class TechnologyService extends RestBase implements ServiceConstants, FrameworkConstants, ResponseCodes {
	
	/**
	 * List apptypes.
	 *
	 * @param userId the user id
	 * @param customerId the customer id
	 * @return the response
	 */
	@GET
	@Path("/apptypes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listApptypes(@QueryParam(REST_QUERY_USERID) String userId,
			@QueryParam(REST_QUERY_CUSTOMERID) String customerId) {
		ResponseInfo<List<ApplicationType>> responseData = new ResponseInfo<List<ApplicationType>>();
		try {
			ServiceManager serviceManager = RestBase.CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<ApplicationType>> finalOutput = responseDataEvaluation(responseData, null,
						null, RESPONSE_STATUS_FAILURE, PHR210003);
				return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<ApplicationType> applicationTypes = serviceManager.getApplicationTypes(customerId);
			ResponseInfo<List<ApplicationType>> finalOutput = responseDataEvaluation(responseData, null,
					applicationTypes, RESPONSE_STATUS_SUCCESS, PHR200024);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<ApplicationType>> finalOutput = responseDataEvaluation(responseData, e,
					null, RESPONSE_STATUS_ERROR, PHR210045);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * List apptype tech info.
	 *
	 * @param customerName the customer name
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/customerinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listApptypeTechInfo(@QueryParam(REST_QUERY_CUSTOMER_NAME) String customerName,
			@QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<Customer> responseData = new ResponseInfo<Customer>();
		try {
			Client client = ClientHelper.createClient();
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			WebResource resource = client.resource(configuration.getServerPath() + "/admin/customers");
			resource = resource.queryParam("customerName", customerName);
			resource.accept(MediaType.APPLICATION_JSON);
			ClientResponse response = resource.get(ClientResponse.class);
			GenericType<Customer> genericType = new GenericType<Customer>() {
			};
			Customer customer = response.getEntity(genericType);
			if (!customerName.equalsIgnoreCase("Photon")) {
				Map<String, String> theme = customer.getFrameworkTheme();
				String brandingColor = theme.get("brandingColor");
				String accordionBackGroundColor = theme.get("accordionBackGroundColor");
				String bodyBackGroundColor = theme.get("bodyBackGroundColor");
				String buttonColor = theme.get("ButtonColor");
				String pageHeaderColor = theme.get("PageHeaderColor");
				String copyRightColor = theme.get("CopyRightColor");
				String labelColor = theme.get("LabelColor");
				String menufontColor = theme.get("MenufontColor");
				String menuBackGround = theme.get("MenuBackGround");
				// String subMenuBackGround = theme.get("SubMenuBackGround");
				String copyRight = theme.get("CopyRight");
				String disabledLabelColor = theme.get("DisabledLabelColor");
				String loginLogo = theme.get("loginlogo");
				String logoPadding = theme.get("logopadding");

				InputStream stream = this.getClass().getClassLoader().getResourceAsStream("customercss.xml");
				String result = getStringFromInputStream(stream);

				JSONParser parser = new JSONParser();
				JSONObject jsonObject = (JSONObject) parser.parse(result);

				Set keySet = jsonObject.keySet();
				for (Object object : keySet) {
					JSONObject fields = (JSONObject) jsonObject.get(object.toString());
					Set fieldSet = fields.keySet();
					for (Object key : fieldSet) {
						Object value = fields.get(key);
						String customercolor = value.toString();
						if (customercolor.contains("brandingColor") && StringUtils.isNotEmpty(brandingColor)) {
							fields.put(key, customercolor.replace("brandingColor", brandingColor) + " !important");
						}
						if (customercolor.equals("menuBackGround") && StringUtils.isNotEmpty(menuBackGround)) {
							fields.put(key, customercolor.replace("menuBackGround", menuBackGround) + " !important");
						}
						if (customercolor.contains("backgroundColor") && StringUtils.isNotEmpty(bodyBackGroundColor)) {
							fields.put(key, customercolor.replace("backgroundColor", bodyBackGroundColor)
									+ " !important");
						}
						if (customercolor.contains("accordionBackGroundColor")
								&& StringUtils.isNotEmpty(accordionBackGroundColor)) {
							fields.put(key, customercolor.replace("accordionBackGroundColor", accordionBackGroundColor)
									+ " !important");
						}
						if (customercolor.contains("bodyBackGroundColor")
								&& StringUtils.isNotEmpty(bodyBackGroundColor)) {
							fields.put(key, customercolor.replace("bodyBackGroundColor", bodyBackGroundColor)
									+ " !important");
						}
						if (customercolor.contains("buttonColor") && StringUtils.isNotEmpty(buttonColor)) {
							fields.put(key, customercolor.replace("buttonColor", buttonColor) + " !important");
						}
						if (customercolor.contains("pageHeaderColor") && StringUtils.isNotEmpty(pageHeaderColor)) {
							fields.put(key, customercolor.replace("pageHeaderColor", pageHeaderColor) + " !important");
						}
						if (customercolor.contains("copyRightColor") && StringUtils.isNotEmpty(copyRightColor)) {
							fields.put(key, customercolor.replace("copyRightColor", copyRightColor) + " !important");
						}
						if (customercolor.contains("labelColor") && StringUtils.isNotEmpty(labelColor)) {
							fields.put(key, customercolor.replace("labelColor", labelColor) + " !important");
						}
						if (customercolor.contains("menufontColor") && StringUtils.isNotEmpty(menufontColor)) {
							fields.put(key, customercolor.replace("menufontColor", menufontColor) + " !important");
						}
						if (customercolor.contains("copyRight") && StringUtils.isNotEmpty(copyRight)) {
							fields.put(key, customercolor.replace("copyRight", copyRight) + " !important");
						}
						if (customercolor.contains("disabledLabelColor") && StringUtils.isNotEmpty(disabledLabelColor)) {
							fields.put(key, customercolor.replace("disabledLabelColor", disabledLabelColor)
									+ " !important");
						}
						if (customercolor.contains("loginlogo") && StringUtils.isNotEmpty(loginLogo)) {
							fields.put(key, customercolor.replace("loginlogo", loginLogo) + " !important");
						}
						if (customercolor.contains("logopadding") && StringUtils.isNotEmpty(logoPadding)) {
							fields.put(key, customercolor.replace("logopadding", logoPadding) + " !important");
						}
					}
				}

				Map<String, String> customerThemeMap = new HashMap<String, String>();
				customerThemeMap.put("customerTheme", jsonObject.toJSONString());
				customer.setFrameworkTheme(customerThemeMap);

			}

			ResponseInfo<Customer> finalOutput = responseDataEvaluation(responseData, null,
					"customer returned successfully", customer);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();

		} catch (PhrescoException e) {
			ResponseInfo<Customer> finalOutput = responseDataEvaluation(responseData, e, "CustomerInfo not fetched",
					null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (ParseException e) {
			ResponseInfo<Customer> finalOutput = responseDataEvaluation(responseData, e, "CustomerInfo not fetched",
					null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Listtechnology group.
	 *
	 * @param customerId the customer id
	 * @param appTypeId the app type id
	 * @param userId the user id
	 * @return the response
	 */
	@GET
	@Path("/techgroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listtechnologyGroup(@QueryParam(REST_QUERY_CUSTOMERID) String customerId,
			@QueryParam(REST_QUERY_APPTYPEID) String appTypeId, @QueryParam(REST_QUERY_USERID) String userId) {
		ResponseInfo<List<TechnologyGroup>> responseData = new ResponseInfo<List<TechnologyGroup>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<TechnologyGroup>> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<TechnologyGroup> technologyGroups = serviceManager.getTechnologyGroups(customerId, appTypeId);
			ResponseInfo<List<TechnologyGroup>> finalOutput = responseDataEvaluation(responseData, null,
					"technologyGroups of customer returned successfully", technologyGroups);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<TechnologyGroup>> finalOutput = responseDataEvaluation(responseData, e,
					"technologyGroups of customer not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	/**
	 * To get the sub-modules of the given module
	 * @param userId
	 * @param techId
	 * @return
	 */
	@GET
	@Path(REST_API_TECHINFO)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubModules(@QueryParam(REST_QUERY_USERID) String userId, @QueryParam(REST_QUERY_TECHID) String techId) {
		ResponseInfo<List<TechnologyInfo>> responseData = new ResponseInfo<List<TechnologyInfo>>();
		try {
			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(userId);
			if (serviceManager == null) {
				ResponseInfo<List<TechnologyInfo>> finalOutput = responseDataEvaluation(responseData, null,
						"UnAuthorized User", null);
				return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin",
						"*").build();
			}
			List<TechnologyInfo> technologyInfos = serviceManager.getTechnologyInfos(techId);
			ResponseInfo<List<TechnologyInfo>> finalOutput = responseDataEvaluation(responseData, null,
					"sub-modules of technology returned successfully", technologyInfos);
			return Response.status(Status.OK).entity(finalOutput).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			ResponseInfo<List<TechnologyInfo>> finalOutput = responseDataEvaluation(responseData, e,
					"sub-modules of technology not fetched", null);
			return Response.status(Status.BAD_REQUEST).entity(finalOutput).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Gets the string from input stream.
	 *
	 * @param is the is
	 * @return the string from input stream
	 */
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
