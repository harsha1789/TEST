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
 * @author PD69988
 *
 */
public class Desktop_Betway_BuildABet {

	public ScriptParameters scriptParameters;

	Logger log = Logger.getLogger(Desktop_Betway_BuildABet.class.getName()); // To get Logs

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

		try {

			if (webdriver != null) {
				String url = cfnlib.XpathMap.get("VuvuzelaSA");
				Thread.sleep(2000);
				webdriver.navigate().to(url);
				System.out.println("URL Opened: " + url);
				Thread.sleep(2000);

				cfnlib.VuvuzelCookies(report);
				cfnlib.HM_ZALogin1();
				cfnlib.SportsPage(report);
				cfnlib.SportsToSoccer(report);
				cfnlib.BetBook(report);
				cfnlib.StatisticsPage(report);
				cfnlib.BuildABet(report);
				cfnlib.BuildABetTabclick(report);
				cfnlib.BuildABetGreen(report);
				cfnlib.BuildABetMarkets(report);
				cfnlib.BuildABetMarketClick(report);
				cfnlib.BuildABetMarketOdds(report);
				cfnlib.BuildABetOddsClick(report);
				cfnlib.BuildABetGreenOdds(report);
				cfnlib.BuildABetDeselectOdds(report);
				cfnlib.BuildABetOnlyOneOdds(report);
				cfnlib.BuildABetOddsError(report);
				cfnlib.BuildABetZeroSelections(report);
				cfnlib.BuildABetSelectionsTab(report);
				cfnlib.BuildABetSelectionsCount(report);
				cfnlib.BuildABetTotalOdds(report);
				cfnlib.BuildABetSelections(report);
				cfnlib.BuildABetSelectionsTotalOdds(report);
				cfnlib.BuildABetCrossButton(report);
				cfnlib.BuildABetCrossButtonClick(report);
				cfnlib.BuildABetSelectionsDDClick(report);
				cfnlib.BuildABetAddToBetSlip(report);				
				cfnlib.BuildABetAddToBetSlipClick(report);
				cfnlib.BuildABetAddToBetSlipAdd(report);
				cfnlib.BuildABetRemoveAll(report);
				cfnlib.BuildABetRemoveAllClick(report);				
				cfnlib.BuildABetRemoveAllOdds(report);		
				cfnlib.BuildABetToolTip(report);
				cfnlib.BuildABetToolTipMessage(report);
				cfnlib.BuildABetZeroSelectionsTotalOddsZero(report);
			}
		} 

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