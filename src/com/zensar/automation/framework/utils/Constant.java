package com.zensar.automation.framework.utils;

import java.io.File;
/*
 *Class Name : Constatnt.java
 *Description: This final class contains the all constant variables required y other classes. 
 * */
public final class Constant {


	public static final String CONFIG="Config";
	public static final String TESTDATA="TestData";
	public static final String PRESETS="Presets";
	public static final String PATH=System.getProperty("user.dir");
	public static final String TESTDATA_EXCEL_PATH=File.separator+CONFIG+File.separator+"TestData.xls";
	public static final String SUIT_EXCEL_PATH=File.separator+CONFIG+File.separator+"SuitFile.xls";
	public static final String REGRESSION_EXCEL_PATH=File.separator+CONFIG+File.separator+"Regression.xls";
	public static final String PASSWORD="test";
	public static final String INCOMPLETEGAMENAME="thunderstruck";
	public static final String  URL="https://mobilewebserver9-zensarqa2.installprogram.eu/Lobby/en/IslandParadise/games/5reelslots";
	public static final String LOCALHUBURL="http://localhost:4444/wd/hub";
	public static final String OUTPUTIMAGEFOLDER=System.getProperty("user.dir")+"\\ImageScreenshot\\Mobile\\";
	public static final String WINDOWS="Windows"; 
	public static final String FORCE_ULTIMATE_CONSOLE="ForceUltimateConsole";
	public static final String STANDARD_CONSOLE="StandardConsole";
	public static final String STORMCRAFT_CONSOLE="StormcraftConsole";
	public static final String TRUE="true";
	public static final String FALSE="false";
	public static final String ONEDESIGN_NEWFEATURE_CLICKTOCONTINUE="OneDesign_NewFeature_ClickToContinue";
	public static final String CLOCK="clock";
	public static final String CONFIGFILE="Config File";
	public static final int  REFRESH_RETRY_COUNT=3;
	public static final String CICD_DEFAULTPROP_FILE_PATH="."+File.separator+"AutomationBinary"+File.separator+"Default.properties";
	public static final String DEFAULTPROP_FILE_PATH="."+File.separator+"Default.properties";
	public static final String GAMETESTPROP_FILE_PATH=File.separator+CONFIG+File.separator+"TestEnv.properties";
	public static final String CURRENCY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_CurrencySymbol.testdata";
	public static final String	FREEGAMES_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_Language_Verification_FreeGames.testdata";
	public static final	String	FREESPINS_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_Language_Verification_FreeSpin.testdata";
	public static final	String	BIGWIN_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_Language_Verification_BigWin.testdata";
	public static final	String	MOBILE_CURRENCY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_CurrencySymbol.testdata";
	public static final	String	MOBILE_FREEGAMES_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_Language_Verification_FreeGames.testdata";
	public static final	String	MOBILE_FREESPINS_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_Language_Verification_FreeSpin.testdata";
	public static final	String	MOBILE_BIGWIN_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_Language_Verification_BigWin.testdata";
	public static final String 	PRESETSZIPPATH=File.separator+PRESETS+File.separator;
	public static final String FORCE="force";
	public static final String LANG_XL_SHEET_NAME="LanguageCodes";
	public static final String LANGUAGE="Language";
	public static final String LANG_CODE="Language Code";
	public static final String HELPFILE_SHEET_NAME="HelpFile"; 
	public static final String YES="yes";
	public static final String CURRENCY_XL_SHEET_NAME="SupportedCurrenciesList";
	public static final String ID="ID";
	public static final String ISOCODE="ISOCode";
	public static final String SANITY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Sanity_BaseScene.testdata";
	public static final	String MOBILE_SANITY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Sanity_BaseScene.testdata";
	public static final String ISONAME = "ISOName";
	public static final String LANGUAGECURRENCY = "LanguageCurrency";
	public static final String DESKTOP = "Desktop";
	public static final String MOBILE = "Mobile";
	public static final String OPENCVPATH="C:"+File.separator+"OPCV"+File.separator+"opencv"+File.separator+"build"+File.separator+"java"+File.separator+"x64"+File.separator;
	public static final String NATIVE_APP="NATIVE_APP";
	public static final String CHROMIUM="CHROMIUM";
	public static final String CHROME="chrome";
	public static final String FIREFOX="firefox";
	public static final String INTERNETEXPLORER="internet explorer";
	public static final String EDGEDRIVER="edge";
	public static final String OPERA="opera";
	
	public static final String HTMLEXTENSION=".html";
	public static final String JPEGEXTENSION=".jpeg";
	public static final String INTERRUPTED="Interrupted";
	public static final String ANDROID="Android";
	public static final String IOS="iOS";
	public static final String SAFARI="safari";
	
	public static final String DISPALYFORMAT = "DisplayFormat";
	public static final String REGEXPRESSION = "RegExpression"; //
	public static final String REGEXPRESSION_NOSYMBOL = "RegExpressionNoSymbol";
	
	public static final String Home = "Home";
	public static final String Banking = "Banking";
	public static final String Settings = "Settings";
	public static final String Sounds = "Sounds";
	public static final String Double = "Double";
	public static final String QuickDeal = "QuickDeal";
	public static final String PoweredByMicrogaming = "PoweredByMicrogaming";
	public static final String CoinsInPaytable = "CoinsInPaytable";	
	public static final String Credits = "Credits";
	public static final String BetPlus1 = "BetPlus1";
	public static final String BetMax = "BetMax";
	public static final String Deal = "Deal";
	public static final String Held = "Held";
	public static final String Draw = "Draw";
	public static final String Collect = "Collect";
	public static final String Bet = "Bet";
	public static final String TotalBet = "TotalBet";
	public static final String CoinsInBet = "CoinsInBet";
	public static final String CoinSize = "CoinSize";
	public static final String DoubleTo = "DoubleTo";
	public static final String PickCard = "PickCard";
	public static final String Congratulations = "Congratulations";
	public static final String YouWin = "YouWin";
	public static final String DoubleLimitReached = "DoubleLimitReached";
	
	public static final String FEATUREROP_FILE_PATH="."+File.separator+"Feature.properties";
	
	//Markets - Priyanka Bethi
	public static final String MARKET_XL_SHEET_NAME="RegulatedMarkets";
	public static final String COUNTRY_NAME = "Country";
	public static final String PRODUCT_ID ="ProductId";
	public static final String MARKET_TYPEID = "MarketTypeId";
	public static final String BALANCE = "Balance";
	public static final String RUN_FLAG = "runFlag";	
	public static final String BRAND = "Brand";
	public static final String MARKET = "Market";
	public static final String RETURN_URL_PARAMETER= "returnUrlParam";
	public static final String MARKET_URL = "MarketUrl";
	public static final String runFlag = "runFlag";
	public static final String Lang1 = "Lang1";
	public static final String Lang2= "Lang2";
	public static final String LangCode1 = "LangCode1";
	public static final String LangCode2 = "LangCode2";
	public static final String LanguageCount = "LanguageCount";
	//Scenarios - Priyanka
	public static final String gameClock="gameClock";
	public static final String SpinReelLandingBaseScene = "SpinReelLandingBaseScene";
	public static final String checkSpinStopBaseScene ="checkSpinStopBaseScene";
	public static final String QuickSpin = "QuickSpin";
	public static final String PlayerProtectionNavigation = "PlayerProtectionNavigation";
	public static final String CashCheckNavigation = "CashCheckNavigation";	
	public static final String HelpNavigation = "HelpNavigation";
	public static final String PlayCheckNavigation = "PlayCheckNavigation";
	public static final String GameName= "GameName";
	public static final String Autoplay= "Autoplay";
	public static final String checkSpinStopBaseSceneUsingCoordinates= "checkSpinStopBaseSceneUsingCoordinates";
	
	public static final String SessionReminder = "SessionReminder";
	public static final String SessionReminderUserInteraction= "SessionReminderUserInteraction";
	public static final String SessionReminderContinue= "SessionReminderContinue";
	public static final String SessionReminderExitGame= "SessionReminderExitGame";
	public static final String BounsFundsNotification= "BounsFundsNotification";
	public static final String closeBtnEnabledCMA= "closeBtnEnabledCMA";
	public static final String ValueAddsNavigation= "ValueAddsNavigation";
	public static final String BankingNavigation= "BankingNavigation";
	public static final String BalanceUpdateUsingBanking= "BalanceUpdateUsingBanking";
	public static final String reelSpinDurationExpTime="reelSpinDurationExpTime";
	public static final String VerifyreelSpinDuration="VerifyreelSpinDuration";
	public static final String BetOnTopBarStatus="BetOnTopBarStatus";
	public static final String HelpOnTopBarStatus="HelpOnTopBarStatus";
	public static final String NetPositionStatus="NetPositionStatus";
	public static final String responsibleGaming="responsibleGaming";
	public static final String FreeSpinScenarioChecks="FreeSpinScenarioChecks";
	public static final String checkSpinStopFreeSpinUsingCoordinates="checkSpinStopFreeSpinUsingCoordinates";
	public static final String NetPositionLaunch="NetPositionLaunch";
	public static final String NetPositionRefresh="NetPositionRefresh";
	public static final String NetPositionNormalWin="NetPositionNormalWin";
	public static final String NetPositionBigWin="NetPositionBigWin";
	public static final String NetPositionBaseScene="NetPositionBaseScene";
	public static final String netPositionloss="netPositionloss";
	public static final String spinScenario="spinScenario";
	public static final String lossWithMinimumBet="lossWithMinimumBet";
	public static final String winWithMaximumBet="winWithMaximumBet";
	public static final String FreeGamesScenario="FreeGamesScenario";
	public static final String offerScreen="offerScreen";
	public static final String RefreshInFreeGame="RefreshInFreeGame";
	public static final String WinLossScnFreegameForNetPostion="WinLossScnFreegameForNetPostion";
	public static final String FreeSpinScnForNetPostion="FreeSpinScnForNetPostion";
	public static final String FreeGamesToBaseGame="FreeGamesToBaseGame";
	public static final String netPositionUnderFreespin="netPositionUnderFreespin";
	public static final String SessionReminderUnderFreespin="SessionReminderUnderFreespin";
	public static final String practicePlay="practicePlay";
	public static final String practicePlaysetHighBet="practicePlaysetHighBet";
	public static final String practicePlaySetLowBet="practicePlaySetLowBet";
	public static final String practicePlaySpinerror="practicePlaySpinerror";
	public static final String PracticePlaybetRefresh="PracticePlaybetRefresh";
	public static final String PlayForRealPopUP="PlayForRealPopUP";
	public static final String PlayForRealyesMenuFuction="PlayForRealyesMenuFuction";
	public static final String PlayForRealyesBtnFuction="PlayForRealyesBtnFuction";
	public static final String ProgressiveTickercounting="ProgressiveTickercounting";
	public static final String italyTaketoGameScen="italyTaketoGameScen";
	public static final String ValidateTopBar="ValidateTopBar";
	public static final String TakeToGamePracticePlayitly="TakeToGamePracticePlayitly";
	public static final String ReelsdonotstopmenuOpened="ReelsdonotstopmenuOpened";
	public static final String WinCurrencyvalidation="WinCurrencyvalidation";

	
	
	//HelpFile 
	public static final String MarketTypeID = "MarketTypeID";
	public static final String CurrencyISOCode = "CurrencyISOCode";
	public static final String REG_MARKET_NAME = "RegMarketName";
	public static final String LANG_XL_SHEET_NAME_For_MALTA = "MaltaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_ONTARIO = "OntarioSupportedLangs"; 
    public static final String LANG_XL_SHEET_NAME_For_ITALY = "ItalySupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_ROMANIA = "RomaniaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_CROATIA = "CroatiaSupportedLangs";   
    public static final String LANG_XL_SHEET_NAME_For_SWITZERLAND = "SwitzerlandSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_UK = "UKSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_GERMANY = "GermanySupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_DOTCOM = "DotcomSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_DENMARK = "DenmarkSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_GILBRALTRA = "gibraltarSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_SPAIN = "SpainSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_SWEDEN = "SwedenSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_BELGUIM= "BelguimSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_ASIAs = "AsiaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_ALDERNEY = "AlderneySupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_NETHERLAND = "NetherLandSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_BULGARIA = "BulgariaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_MICHIGAN = "MichiganSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_NEWJERSEY = "NewjerseySupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_PENNSYLVANIA = "PennsylvaniaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_ARGENTINABUENOSAIRESCITY = "ArgentinaBACSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_ARGENTINABUENOSAIRESPROVINCE = "ArgentinaBAPSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_GREECE = "GreeceSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_CONNECTICUT = "ConnecticutSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_LATVIA = "LatviaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_ARGENTINACORDOBAPROVINCE = "ArgentinaCPSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_COLOMBIA = "ColombiaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_LITHUANIA = "LithuaniaSupportedLangs";
    public static final String LANG_XL_SHEET_NAME_For_PORTUGAL = "PortugalSupportedLangs";
    
    
    public static final String QuickFire = "QuickFire";
    public static final String HelpText="HelpTextTranslation";
	public static final String EASYHELP_XL_SHEET_NAME="easyHelpTranslations";
	public static final String HelpTextLink="HelpTextLink";
    
	/*
	 * Defining private constructor as make the class singleton
	 * */
	private Constant() {
	}




}