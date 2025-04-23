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
 * @author PJ65867
 *
 */
public class Desktop_Betway_Header {

	public ScriptParameters scriptParameters;
	Logger log = Logger.getLogger(Desktop_Betway_Header.class.getName()); // To get Logs

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

		ArrayList<String> tabs = new ArrayList<String>(webdriver.getWindowHandles());

		try {

			if (webdriver != null) {
				String url = cfnlib.XpathMap.get("VuvuzelaURL");
				Thread.sleep(2000);
				webdriver.navigate().to(url);
				System.out.print("URL opened");
				Thread.sleep(2000);

				cfnlib.VuvuzelCookies(report);
				cfnlib.LoginButtonVisible(report);
				cfnlib.LoginButtonVisibleOnSignupWindow(report);
				cfnlib.LoginButtonVisibleOnHamburger(report);
				cfnlib.LoginButton(report);
				cfnlib.LoginFields(report);
				cfnlib.LoginButtonVisibleOnMultiplePage(report);
				cfnlib.ValidMobileNo(report);
				cfnlib.CountryCode(report);
				cfnlib.HomePageoptions(report);
				cfnlib.LivePage(report);
				cfnlib.CasinoGamesPage(report);
				cfnlib.HorseRacingPage(report);
				cfnlib.LuckyNumbersPage(report);
				cfnlib.BetwayJackpotPage(report);
				cfnlib.VirtualPage(report);
				cfnlib.EsportsPage(report);
				cfnlib.PromotionPage(report);
				cfnlib.LiveChat(report);
				
				/// method remove due to not applicable ///
//				cfnlib.EmailClick(report); 
//				cfnlib.HeaderSection_HamburgerMenuClickedZA(report);
//				cfnlib.HeaderSection_BetwayClickedZA(report);
//				cfnlib.HeaderSection_RefreshOnCashTabClickedZA(report);
//				cfnlib.HeaderSection_EyeIconClickedZA(report);
//				cfnlib.HeaderSection_FreeBetClickedZA(report);
//				cfnlib.SportsBonusInfoNG(report);
//				cfnlib.CasinoBonusRefresh(report);
//				cfnlib.DepositHeader(report);
//				cfnlib.AccountHeader(report);
//				cfnlib.ZALogout1();
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