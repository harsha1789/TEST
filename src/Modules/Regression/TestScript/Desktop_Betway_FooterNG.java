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
 */
public class Desktop_Betway_FooterNG {

	public ScriptParameters scriptParameters;

	Logger log = Logger.getLogger(Desktop_Betway_FooterNG.class.getName()); // To get Logs

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
			String[] urls = {
			cfnlib.XpathMap.get("VuvuzelaNG"),

			};
			for (String url1 : urls) {
			Thread.sleep(2000);
			webdriver.navigate().to(url1);
			System.out.println("URL Opened: " + url1);
			Thread.sleep(2000);


			////////////Akhil AS66528 ////			
		
			cfnlib.VuvuzelCookies(report);			
			
            //////////////NG REGION FS TCS ////	
			cfnlib.LotteriesBoardOnFooterSection(report);
			cfnlib.HowTbuttonOnFooterSection(report);
			cfnlib.HowToOptionsAvailable(report);
			cfnlib.HowToResetPasswordePage(report);
			cfnlib.ClickAnyOptionInResetPassowrdTab(report);
			cfnlib.HowtoDepositPage(report);
			cfnlib.ClickAnyOptionInDeposit(report);
			cfnlib.HowtoBetPage(report);
			cfnlib.ClickAnyOptionInBet(report);
			cfnlib.HowtoBetgamesPage(report);
			cfnlib.ClickAnyOptionInBetgames(report);
			cfnlib.HowtoSMSPage(report);
			cfnlib.HowtoCasinoPage(report);
			cfnlib.ClickAnyOptionInCasino(report);
			cfnlib.HowtoVirtualPage(report);
			cfnlib.ClickAnyOptionInVirtual(report);
			cfnlib.HowtoLuckyNumbersPage(report);
			cfnlib.ClickAnyOptionInLuckyNumbers(report);
			cfnlib.HowtoWithdrawPage(report);
			cfnlib.ClickAnyOptionInWithdraw(report);
			cfnlib.HowtoJackpotsPage(report);
			cfnlib.ClickAnyOptionInJackpots(report);
			cfnlib.HowtoLiveCasinoPage(report);
			cfnlib.ClickAnyOptionInLiveCasino(report);
			
			
			
			
			}
			}}

		 // try
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
