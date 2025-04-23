package Modules.Regression.TestScript;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver.WindowType;
import org.testng.annotations.Test;

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
public class Desktop_Betway_HamburgerMenuZA {

	public ScriptParameters scriptParameters;
	Logger log = Logger.getLogger(Desktop_Betway_HamburgerMenuZA.class.getName()); // To get Logs

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

//		ArrayList<String> tabs = new ArrayList<String>(webdriver.getWindowHandles());

		try {

			if (webdriver != null) {
				String url = cfnlib.XpathMap.get("VuvuzelaSA");
				Thread.sleep(2000);
				webdriver.navigate().to(url);
				System.out.print("VUVUZELA ZA URL Launched");
				Thread.sleep(2000);

				cfnlib.VuvuzelCookies(report);                
				cfnlib.HM_ZALogin1();
				cfnlib.HM_VerifyReponsibleGaming(report);
				cfnlib.HM_ReponsibleGamingClickable(report);
				cfnlib.HM_ReponsibleGamingWindowOpens(report);
				cfnlib.HM_HeaderLabelAvailableInPopup(report);
				cfnlib.HM_CoolingOffPeriodAvailable(report);
				cfnlib.HM_LimitInputFieldwithPlaceholder(report);
				cfnlib.HM_CheckPeriodwithDaysWeeksMonthVisible(report);
				cfnlib.HM_TextSetYourPeriodAvailable(report);
				cfnlib.HM_Verified_i_tooltip(report);
				cfnlib.HM_i_tooltipMousehoverd(report);
				cfnlib.HM_MoreThan3months_SetPeriodNotClickable(report);
				cfnlib.HM_MoreThan12weeks_SetPeriodNotClickable(report);
				cfnlib.HM_MoreThan90Days_SetPeriodNotClickable(report);
				cfnlib.HM_LessThan3months_SetPeriodBeClickable(report);
				cfnlib.HM_LessThan12weeks_SetPeriodBeClickable(report);
				cfnlib.HM_LessThan90Days_SetPeriodBeClickable(report);				
				cfnlib.HM_VerifyDocumentUploadOption(report);
				cfnlib.HM_DocumentUploadClickable(report);
				cfnlib.HM_DocumentUploadWindowOpens(report);
				cfnlib.HM_CheckedIdDocPassportLetterAvailable(report);
				cfnlib.HM_VerifyChooseFileBtnAvailable(report);
				cfnlib.HM_VerifySaveFileBtnAvailable(report);				
				cfnlib.HM_VerifyAcceptablefiletypePolicy(report);
				cfnlib.HM_VerifyAcceptablefiletypeIcons(report);
				cfnlib.HM_ChooseFileBtnClickable(report);
				cfnlib.HM_ChooseFileBtnClick_FileAppOpens(report);
				cfnlib.HM_NoFileSelectedMessageAppear(report);			
				cfnlib.HM_TransactionSummaryAvaialable(report);
				cfnlib.HM_TransactionSummaryPage(report);
				cfnlib.HM_Transaction_Last100Button(report);
				cfnlib.HM_Transaction_Searchbarfunctionality(report);
				cfnlib.HM_TransactionTypeDropDownOptionSelected(report);
				cfnlib.HM_ExportButtonClicked(report);				
				cfnlib.HM_UpdateDetailsAvailableinHM(report);				
				cfnlib.HM_ConfirmAccountDetailsPopup(report);			
				cfnlib.HM_VerifyAccountPersonalDetails(report);				
				cfnlib.HM_ManagePasswordPage(report);
				cfnlib.HM_LogOutBtninHM(report);			
				cfnlib.HM_InsiderBlogOption(report);
				cfnlib.HM_InsiderBlogPage(report);
				cfnlib.HM_PromotionsOption(report);
				cfnlib.HM_PromotionsPage(report);			
				cfnlib.HM_BetwayAppOption(report);
				cfnlib.HM_BetwayAppPage(report);
				cfnlib.HM_ContactUsOption(report);
				cfnlib.HM_ContactUsPage(report);
				cfnlib.HM_LiveScoresOption(report);
				cfnlib.HM_LiveScoresPage(report);
				cfnlib.HM_BettingRulesOption(report);
				cfnlib.HM_BettingRulesPage(report);				
				cfnlib.HM_StatsCenterOption(report);
				cfnlib.HM_StatsCenterPage(report);
				cfnlib.HM_Logout();
				cfnlib.HM_ZALogin2();
				cfnlib.HM_InfoIconOnCurrentBonus(report);
				cfnlib.HM_PaylimitExpireDateInBonusPopup(report);
				cfnlib.HM_UpcomingPendingBonusInSummary(report);
							
        ////////// testcases remove due to option not Avialble //
				
//				cfnlib.HM_MoneyBackBoostOption(report);
//				cfnlib.HM_MoneyBackBoostPage(report);
				

			}

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