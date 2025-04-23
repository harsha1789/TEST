package Modules.Regression.TestScript;

import org.apache.log4j.Logger;
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
 * @author PD69988
 *
 */
public class Desktop_Betway_BetslipUAT {

	public ScriptParameters scriptParameters;

	Logger log = Logger.getLogger(Desktop_Betway_BetslipUAT.class.getName()); // To get Logs

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
cfnlib.XpathMap.get("VuvuzelaSAUAT"),
};
for (String url1 : urls) {
Thread.sleep(2000);
webdriver.navigate().to(url1);
System.out.println("URL Opened: " + url1);
Thread.sleep(2000);



//////PAVAN PD69988 ////	
cfnlib.VuvuzelCookies(report);
cfnlib.loginUAT();

cfnlib.SingleMultiBetslip(report);
cfnlib.DefaultMultiBetslipAddition(report);
cfnlib.DefaultSelectAllButton(report);
cfnlib.DeSelectLegsBetSlip(report);
cfnlib.SelectDeSelectedLegsBetSlip(report);
cfnlib.SelectAllWhiteBetSlip(report);
cfnlib.BookingCodeBetSlip(report);
cfnlib.ValidBookingCodeBetSlip(report);
cfnlib.InValidBookingCodeBetSlip(report);
cfnlib.EmptyBookingCodeBetSlip(report);
cfnlib.BookingCodeMultiBetslip(report);
cfnlib.BookingCodeSelectMultiBetslip(report);
cfnlib.ValidBookingCodeMultiBetslip(report);
cfnlib.InValidBookingCodeMultiBetslip(report);
cfnlib.EmptyBookingCodeMultiBetslip(report);
cfnlib.SingleBetSlipClick(report);
cfnlib.VerifySingleBetSlipClick(report);
cfnlib.CheckBoxClick(report);
cfnlib.DefaultSelectAll(report);
cfnlib.VerifyMultiBetslip(report);
cfnlib.MultiBetslipCheckBox(report);
cfnlib.MultiBetslipSelectAll(report);
cfnlib.MultiBetslipStatisticsPage(report);
cfnlib.MultiBookingCodeClose(report);
cfnlib.WhatsAppButton(report);
cfnlib.TwitterButton(report);
cfnlib.FacebookButton(report);
cfnlib.MailButton(report);
cfnlib.ContinueBettingButton(report);
cfnlib.SingleBetKeepBetsCheckBox(report);
cfnlib.SingleBetKeepBetsCheckBoxClick(report);
cfnlib.MultiBetKeepBetsCheckBox(report);
cfnlib.MultiBetKeepBetsCheckBoxClick(report);
cfnlib.SingleBetAcceptOddsCheckBox(report);
cfnlib.SingleBetAcceptOddsCheckBoxClick(report);
cfnlib.MultiBetAcceptOddsCheckBox(report);
cfnlib.MultiBetAcceptOddsCheckBoxClick(report);
cfnlib.TotalBetwayReturn(report);
cfnlib.PotentialReturn1(report);
cfnlib.BetWager1(report);


//////////Akhil AS66528 ////			

cfnlib.VerifyMultipleoddsFunctionality(report);
cfnlib.UserAddInvalidWagerInputs_BetWager(report);
cfnlib.TotalBetwayReturnWagercalculation(report);
cfnlib.RemoveAlloddsInMultiBetslip(report);
cfnlib.RemoveAlloddsInSinglebetslip(report);
cfnlib.Multi_VerifyQuickBetbutton(report);
cfnlib.Multi_EnterAmountInQuickBetSection(report);
cfnlib.Multi_Verify_QuickBetbutton(report);
cfnlib.Multi_QuickBetbutton(report);
cfnlib.Multi_VerifyResetbutton(report);
cfnlib.Multi_QuickbetCalculation(report);
cfnlib.Multi_WinBoostvalueinBetAmount(report);
cfnlib.Multi_WinBoostCalculationPage(report);
cfnlib.Multi_WinBoosttooltip(report);
cfnlib.Multi_WinBoostpopupwindowcontent(report);
cfnlib.Multi_WinBoostInBetamountCalculation(report);
cfnlib.Single_BetConfirmationPopupWindow(report);
cfnlib.Multi_BetConfirmationPopupWindow(report);
cfnlib.Single_MyBetButtonAccountPopupWindow(report);
cfnlib.VerifyMyBet_ALLRadioButtonInSingleodd(report);
cfnlib.VerifyOpenBet_SportsDropdown(report);
cfnlib.VerifyMyBet_AutoCashoutPopupWindow(report);
cfnlib.VerifyMyBet_AutoCashoutSlider(report);
cfnlib.VerifySetbutton_inAutoCashoutsection(report);
cfnlib.VerifyRemovebutton_inAutoCashoutsection(report);
cfnlib.VerifyMyBet_ALLRadioButtonInMultiodd(report);
cfnlib.VerifySingleodds_HideLossestoggleOn(report);
cfnlib.VerifySingleodds_HideLossestoggleOff(report);
cfnlib.VerifyMultiodds_HideLossestoggleOn(report);
cfnlib.VerifyMultiodds_HideLossestoggleOff(report);
cfnlib.VerifyMultiodds_OpenbetsPopup(report);
cfnlib.VerifyMultiodds_cashoutPopUpWindow(report);



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
	
