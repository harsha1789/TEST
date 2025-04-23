package com.zensar.automation.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zensar.automation.api.RestAPILibrary;
import com.zensar.automation.framework.library.PropertyReader;
import com.zensar.automation.framework.library.UnableToLoadPropertiesException;
import com.zensar.automation.model.EnvList;
import com.zensar.automation.model.VirtualMachines;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//default constructor
//load default properties(String token)
//load game specific propertie ie.test env reding(String gamename)
//load env specific properties(String envname)
//load all properties (Token, envname,gameName)
public class TestPropReader extends PropertyReader {

	Logger logger = Logger.getLogger(TestPropReader.class.getName());
	static TestPropReader testInstance = null;

	private TestPropReader() {
		super();
	}

	public static TestPropReader getInstance() {
		if (testInstance == null) {
			testInstance = new TestPropReader();

		}
		return testInstance;
	}

	@Override
	// Get and set method to access the properties
	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	@Override
	public void setProperty(String key, String value) {

		prop.setProperty(key, value);
	}

	/*
	 * Load BlueMesa environment related properties example Casinoas1IP,serverIp
	 * etc. and return boolean
	 * 
	 * @param String BlueMesa environment id
	 */
	public boolean loadBlueMesaEnvProperties(int envID) throws UnableToLoadPropertiesException {
		boolean isBlueMesaEnvPropLoaded = false;
		RestAPILibrary restAPILibrary = new RestAPILibrary();

		try {
			logger.info("Loading BlueMesa Environment properties inprogess...");
			if (envID != 0) {
				logger.debug("Loading BlueMesa  properties for Environment id " + envID);
				EnvList envList = restAPILibrary.getBlueMesaEnvMetaData(envID);

				List<VirtualMachines> vmList = envList.getDataObject().getVirtualMachines();

				for (VirtualMachines virtualMachines : vmList) {
					if (virtualMachines.getName().equalsIgnoreCase(getProperty("GamingDB"))) {
						setProperty("serverIp", virtualMachines.getExternalIpAddress());

					} else if (virtualMachines.getName().equalsIgnoreCase("CasinoAS1")) {
						setProperty("Casinoas1IP", virtualMachines.getExternalIpAddress());
					} else if (virtualMachines.getName().equalsIgnoreCase("RabbitMQ")) {
						setProperty("RabbitMQ", virtualMachines.getExternalIpAddress());
					} else if (virtualMachines.getName().equalsIgnoreCase("WebServer4")) {
						setProperty("WebServer4", virtualMachines.getExternalIpAddress());
					}

				}
				isBlueMesaEnvPropLoaded = true;
				logger.info("loading completed for BlueMesa Environment properties");
			}
		} catch (Exception e) {
			logger.error("Exception occur in Testpropreader :", e);
			throw new UnableToLoadPropertiesException(e.getMessage(), e);

		}

		return isBlueMesaEnvPropLoaded;
	}

	/*
	 * Load properties example return boolean
	 * 
	 * @param string Game Name
	 * 
	 * @param String BlueMesa environment id
	 * 
	 */

	public boolean loadAllProperties(String gameName, String strEnvName) throws UnableToLoadPropertiesException {
		boolean isPropLoaded = false;
		try {
			RestAPILibrary restAPILibrary = new RestAPILibrary();
			logger.info("Loading all properties in progress..");

			isPropLoaded = PropertyReader.getInstance().loadDefaultProperties();
			if (!isPropLoaded) {
				logger.debug("Default properties not loaded");
				return isPropLoaded;
			}
			isPropLoaded = PropertyReader.getInstance().loadGameProperties(gameName);
			if (!isPropLoaded) {
				logger.debug("Game Test environment properties not loaded");
				return isPropLoaded;
			}
			PropertyReader.getInstance().getProp().forEach((key, value) -> setProperty((String) key, (String) value));
			// following are the changes done for run the automation on axiom lobby

			if (TestPropReader.getInstance().getProperty("EnvironmentName") == null
					|| TestPropReader.getInstance().getProperty("EnvironmentName").equalsIgnoreCase("Bluemesa")) {
				int envid = restAPILibrary.getEnvironmentId(strEnvName);
				setProperty("EnvironmentID", Integer.toString(envid));

				isPropLoaded = loadBlueMesaEnvProperties(envid);
				if (!isPropLoaded) {
					logger.debug("fail to load bluemesa properties ");
					return isPropLoaded;
				}
			}
			setProperty("ExecutionEnv", strEnvName);
		} catch (Exception e) {
			logger.error("Exception occur in Testpropreader:", e);
			throw new UnableToLoadPropertiesException(e.getMessage(), e);

		}
		return isPropLoaded;
	}

	public boolean loadAllProperties(String gameName, int envId) throws UnableToLoadPropertiesException {
		boolean isPropLoaded = false;
		try {
			logger.info("Loading all properties in progress..");

			PropertyReader.getInstance().loadDefaultProperties();
			PropertyReader.getInstance().loadGameProperties(gameName);
			PropertyReader.getInstance().getProp().forEach((key, value) -> setProperty((String) key, (String) value));
			if (TestPropReader.getInstance().getProperty("EnvironmentName") == null
					|| TestPropReader.getInstance().getProperty("EnvironmentName").equalsIgnoreCase("Bluemesa")) {
				loadBlueMesaEnvProperties(envId);
				setProperty("EnvironmentID", Integer.toString(envId));
			}

			isPropLoaded = true;

		} catch (Exception e) {
			logger.error("Exception occur in Testpropreader:", e);
			throw new UnableToLoadPropertiesException(e.getMessage(), e);

		}
		return isPropLoaded;
	}

	/**
	 * This method is used to get the all titan version from the axiom lobby and
	 * user inputs dynamically
	 * 
	 * @author pb61055
	 * @return
	 */
	public String getTitanVersionAxiom() {
		RestAPILibrary restAPILibrary = new RestAPILibrary();
		Response response = null;
		Scanner sc = new Scanner(System.in);
		String userGivenVersion = null;
		int versionCount = 0;
		JsonArray versionArray = new JsonArray();
		ArrayList<String> versionlist = new ArrayList<String>();
		String envName = TestPropReader.getInstance().getProperty("ExecutionEnv");
		try {

			OkHttpClient client = restAPILibrary.getHTTPClient();
			Request request = new Request.Builder().url(TestPropReader.getInstance().getProperty("AxiomEnvRefererUrl")
					+ "-" + envName + ".installprogram.eu/MobileSettings/TitanVersions").method("GET", null).build();
			response = client.newCall(request).execute();

			if (response.isSuccessful()) {
				String responsenew = response.body().string();
				JsonParser parse = new JsonParser();
				JsonObject jsonObject = (JsonObject) parse.parse(responsenew);
				JsonArray titanVersionArray = (JsonArray) jsonObject.get("dataObject");
				System.out.println("Titan versions available : ");
				for (int count = 0; count < titanVersionArray.size(); count++) {
					JsonElement titanVersionElement = (JsonElement) titanVersionArray.get(count);
					JsonObject titanVersionObject = titanVersionElement.getAsJsonObject();
					JsonElement appVersionEle = (JsonElement) titanVersionObject.get("appVersion");

					JsonElement isHealthyEle = (JsonElement) titanVersionObject.get("isHealthy");
					boolean isHealthy = isHealthyEle.getAsBoolean();

					if (isHealthy) {
						String appVersion = appVersionEle.getAsString();
						logger.info(appVersion);
						System.out.println(appVersion);

						versionArray.add(appVersion);

						/*
						 * Code for getting latest version long appVersionLong =
						 * Long.parseLong(appVersion.replace(".", "")); if (appVersionLong >=
						 * longLatestver) { latestVesion = appVersion; longLatestver = appVersionLong; }
						 */
					}
				}
				if (versionArray != null) {
					int len = versionArray.size();
					for (int i = 0; i < len; i++) {
						versionlist.add(versionArray.get(i).toString());
					}
				}

				System.out.print("Enter Titan Version -> ");
				userGivenVersion = sc.next();

				while (!versionlist.contains("\"" + userGivenVersion + "\"")) {
					System.out.print("Invalid input details!! Enter Titan version again -> ");
					userGivenVersion = sc.next();
					versionCount++;
					if (versionCount == 3) {
						System.out.println(
								"Incorrect deatils entered multiple times, Please terminate and start execution again!!");
						userGivenVersion = null;
						break;
					}
				}
				logger.info("Request success with response :" + responsenew);
			} else {
				logger.info("Request fail with response :" + response.message());

			}
		} catch (Exception e) {
			logger.error("Exception occur in mapEnvNameToEnvId(String envName) ", e);
			logger.error(e.getMessage());
		}
		return userGivenVersion;
	}

	/**
	 * This method is used to get all game version on axiom lobby and user inputs
	 * dynamically
	 * 
	 * @author pb61055
	 * @param gameName
	 * @param deviceType
	 * @return
	 */
	public String getGameVersionAxiom(String gameName, String deviceType) {
		String gameVersion = null;
		Response response = null;
		Scanner sc = new Scanner(System.in);
		String userGivenVersion = null;
		int versionCount = 0;
		String variant = null;
		String variantVersion = null;
		JsonArray versionArray = new JsonArray();
		ArrayList<String> versionlist = new ArrayList<String>();
		RestAPILibrary restAPILibrary = new RestAPILibrary();

		//gameName = "11CoinsofFire";
		try {
			String envName = TestPropReader.getInstance().getProperty("ExecutionEnv");
			OkHttpClient okHttpClientclient = restAPILibrary.getHTTPClient();
			if (okHttpClientclient != null) {
				Request request = new Request.Builder()
						.url(TestPropReader.getInstance().getProperty("AxiomEnvRefererUrl") + "-" + envName
								+ ".installprogram.eu/Games/InstalledGameRecords")
						.method("GET", null).build();
				response = okHttpClientclient.newCall(request).execute();

				if (response.isSuccessful() && response.code() == 200) {

					String responsenew = response.body().string();
					JsonParser parse = new JsonParser();
					JsonObject jsonObject = (JsonObject) parse.parse(responsenew);
					JsonArray gameArray = (JsonArray) jsonObject.get("dataObject");

					System.out.print("Variant Game (Yes/No) -> ");
					variant = sc.next();

					if ((variant.equalsIgnoreCase("yes") || variant.equalsIgnoreCase("no"))) {
						if (variant.equalsIgnoreCase("yes")) {
							System.out.print("Please specify variant version (Ex : V92) -> ");
							variantVersion = sc.next();
							gameName = gameName + variantVersion;
						}

						for (int count = 0; count < gameArray.size(); count++) {

							JsonElement gameVersionElement = (JsonElement) gameArray.get(count);
							JsonObject gameVersionObject = gameVersionElement.getAsJsonObject();

							JsonElement gameNameEle = (JsonElement) gameVersionObject.get("shortName");
							String name = gameNameEle.getAsString();

							if (gameName.toLowerCase().contentEquals(name.toLowerCase())) {
								JsonElement gameDispalyNameEle = (JsonElement) gameVersionObject.get("displayName");
								String dispalyName = gameDispalyNameEle.getAsString();
								
								if (dispalyName.contains(deviceType)) {
									
									String shortName = (String) gameVersionObject.get("shortName").getAsString();
									TestPropReader.getInstance().setProperty("shortName", shortName );
									
									//for getting game mid
									String gameMID = (String) gameVersionObject.get("moduleId").getAsString();
									System.out.println("MID : "+gameMID);
									
									
									//setting mid value
									TestPropReader.getInstance().setProperty("gameMID", gameMID);
									
									
									//for getting game cid
									String gameCID = (String) gameVersionObject.get("clientId").getAsString();
									System.out.println("CID : "+gameCID);
									
									//setting cid value
									TestPropReader.getInstance().setProperty("gameCID", gameCID);
									
									//Game version
									JsonArray gameVersionArray = (JsonArray) gameVersionObject.get("versions");
									System.out.println("GameVersions available : ");

									for (int count1 = 0; count1 < gameVersionArray.size(); count1++) {
										JsonElement versionElement = (JsonElement) gameVersionArray.get(count1);
										JsonObject versionObject = versionElement.getAsJsonObject();
										JsonElement versionEle = (JsonElement) versionObject.get("version");

										gameVersion = versionEle.getAsString();
										logger.info(gameVersion);
										System.out.println(gameVersion);

										versionArray.add(versionEle);

										// Code for getting latest version
										/*
										 * long appVersionLong = Long.parseLong(gameVersion.replace(".", "")); if
										 * (appVersionLong >= longLatestver) { latestVesion = gameVersion; longLatestver
										 * = appVersionLong; }
										 */
									}

									if (versionArray != null) {
										int len = versionArray.size();
										for (int i = 0; i < len; i++) {
											versionlist.add(versionArray.get(i).toString());
										}
									}

									System.out.print("Enter gameVersion -> ");
									userGivenVersion = sc.next();

									while (!versionlist.contains("\"" + userGivenVersion + "\"")) {
										System.out.print("Invalid input details!! Enter Game version again -> ");
										userGivenVersion = sc.next();
										versionCount++;
										if (versionCount == 3) {
											System.out.println(
													"Incorrect deatils entered multiple times, Please terminate and start execution again!!");
											userGivenVersion = null;
											break;
										}
									}
								}

							}
						}
					} else {
						System.out.println("Invalid input, Please terminate and start execution again!!");
					}

				} else {
					System.out.println(
							"Incorrect deatils entered multiple times, Please terminate and start execution again!!");
				}
			}

			else {
				logger.error("getGameVersionFromAxiom request  fail with message:");
				logger.error(response.message());
			}

		} catch (Exception e) {
			logger.error("Exception occur in getGameversionFromAxiom()  ", e);
			logger.error(e.getMessage());
		}

		return userGivenVersion;
	}

}// end of class
