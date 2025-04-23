package Modules.Regression.TestScript;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;

/**
 * This script is used to verify betway website
 * 
 * @author AS66528
 *
 */
public class Desktop_Betway_BetslipZA {

	public ScriptParameters scriptParameters;

	Logger log = Logger.getLogger(Desktop_Betway_BetslipZA.class.getName()); // To get Logs

	// -------------------Main script defination---------------------//
	public void script() throws Exception {
		String mstrTCName = scriptParameters.getMstrTCName();
		String mstrTCDesc = scriptParameters.getMstrTCDesc();
		String mstrModuleName = scriptParameters.getMstrModuleName();
		WebDriver webdriver = scriptParameters.getDriver();
		BrowserMobProxyServer proxy = scriptParameters.getProxy();
		String startTime = scriptParameters.getStartTime();
		String filePath = scriptParameters.getFilePath();
		String browserName = scriptParameters.getBrowserName();
		String framework = scriptParameters.getFramework();
		String gameName = scriptParameters.getGameName();
		String status = null;
		int mintDetailCount = 0;
		int mintPassed = 0;
		int mintFailed = 0;
		int mintWarnings = 0;
		int mintStepNo = 0;

		Desktop_HTML_Report report = new Desktop_HTML_Report(webdriver, browserName, filePath, startTime, mstrTCName,
				mstrTCDesc, mstrModuleName, mintDetailCount, mintPassed, mintFailed, mintWarnings, mintStepNo, status,
				gameName);
		DesktopFunctionLibraryFactory desktopFunctionLibraryFactory = new DesktopFunctionLibraryFactory();
		CFNLibrary_Desktop cfnlib = desktopFunctionLibraryFactory.getFunctionLibrary(framework, webdriver, proxy,
				report, gameName);

			

///////////////////////////////////   BETSLIP  VUVUZELA  UAT    ////////////////////////////
		    
try {

if (webdriver != null) {
String[] urls = {
cfnlib.XpathMap.get("VuvuzelaSA"),
};
for (String url1 : urls) {
Thread.sleep(2000);
webdriver.navigate().to(url1);
System.out.println("URL Opened: " + url1);
Thread.sleep(2000);



////////////Akhil AS66528 ////

cfnlib.VuvuzelCookies(report);
cfnlib.HM_ZALogin2();

cfnlib.BS_SingleMultiBetslip(report);
cfnlib.BS_DefaultSelectAllButton(report);
cfnlib.BS_DeSelectLegsBetSlip(report);
cfnlib.BS_SelectDeSelectedLegsBetSlip(report);
cfnlib.BS_SelectAllWhiteBetSlip(report);
cfnlib.BS_BookingCodeBetSlip(report);
cfnlib.BS_ValidBookingCodeBetSlip(report);
cfnlib.BS_InValidBookingCodeBetSlip(report);
cfnlib.BS_EmptyBookingCodeBetSlip(report);
cfnlib.BS_BookingCodeMultiBetslip(report);
cfnlib.BS_ValidBookingCodeMultiBetslip(report);
cfnlib.BS_InValidBookingCodeMultiBetslip(report);
cfnlib.BS_EmptyBookingCodeMultiBetslip(report);
cfnlib.BS_SingleBetSlipClick(report);
cfnlib.BS_VerifySingleBetSlipClick(report);
cfnlib.BS_CheckBoxClick(report);
cfnlib.BS_DefaultSelectAll(report);
cfnlib.BS_VerifyMultiBetslip(report);
cfnlib.BS_MultiBetslipCheckBox(report);
cfnlib.BS_MultiBetslipDeSelectAll(report);
cfnlib.BS_MultiBetslipStatisticsPage(report);
cfnlib.BS_MultiBookingCodeClose(report);
cfnlib.BS_MultiBetBookingCodeApps(report);
cfnlib.BS_MultiBetContinueBettingButton(report);
cfnlib.BS_SingleBetBookingCodeApps(report);
cfnlib.BS_SingleBetContinueBettingButton(report);
cfnlib.BS_SingleBetKeepBetsCheckBox(report);
cfnlib.BS_SingleBetKeepBetsCheckBoxClick(report);
cfnlib.BS_MultiBetKeepBetsCheckBox(report);
cfnlib.BS_MultiBetKeepBetsCheckBoxClick(report);
cfnlib.BS_SingleBetAcceptOddsCheckBox(report);
cfnlib.BS_SingleBetWagerFieldTotalAmountCal (report);
cfnlib.BS_MultiBetWagerFieldTotalAmountCal(report);
cfnlib.BS_SingleBetslipRemoveAllBtnClicked(report);
cfnlib.BS_MultiBetWinBoost(report);
cfnlib.InsufficientErrorBet(report);
cfnlib.BS_MyBetButtonAccountPopupWindow(report);

}}
				

		} // try
			// -------------------Handling the exception---------------------//

		catch (Exception e) {
			log.error(e.getMessage(), e);
			cfnlib.evalException(e);
		}

		finally {

			webdriver.quit();

			// Global.appiumService.stop();
		}

		}
	}
	
