package Modules.Regression.TestScript;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.zensar.automation.framework.model.ScriptParameters;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import Modules.Regression.FunctionLibrary.CFNLibrary_Desktop;
import Modules.Regression.FunctionLibrary.factory.DesktopFunctionLibraryFactory;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.testng.annotations.Test;

/**
 * This script is used to verify betway website
 * 
 * @author AS66528
 *
 * @author PD69988
 */

public class Desktop_Betway_Login {

	public ScriptParameters scriptParameters;

	Logger log = Logger.getLogger(Desktop_Betway_Login.class.getName()); // To get Logs

	// -------------------Main script defination---------------------//
	@Test(threadPoolSize = 5, invocationCount = 9)
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

			

//////////////////////////////VUVUZELA SIGNUP///////////////////////////////////////////////////			
				String[] urls = {
						cfnlib.XpathMap.get("VuvuzelaSA")
						
						
						 };
				for (String url1 : urls) {
					Thread.sleep(2000);
					webdriver.navigate().to(url1);
					System.out.println("URL Opened: " + url1);


					cfnlib.VuvuzelCookies(report);
					cfnlib.CheckLoginButtonVisible(report);
					cfnlib.LoginButtonInLoginPage(report);
					cfnlib.LoginInHamburgerMenu(report);
					cfnlib.LoginForm(report);
					cfnlib.LoginFields1(report);
					cfnlib.VerifyLoginButtonConsistency(report);
				   	cfnlib.LoginValidNumber(report);
					cfnlib.LoginMobileNoDisplayCode_27(report);
					cfnlib.LoginMobileNumberWithZeros(report);
					cfnlib.LoginMobilenumbertooshort(report);
					cfnlib.LoginMobilenumberlong(report);
					cfnlib.LoginMobilenumberhyphens(report);
					cfnlib.LoginMobilenumberparentheses(report);
					cfnlib.LoginMobilenumberSpaces_Hyphens(report);
					cfnlib.LoginMobilenumberAlphabeticCharacters(report);
					cfnlib.LoginMobilenumberSpecialCharacters(report);
					cfnlib.LoginMobilenumberStartingwithHyphen(report);
					cfnlib.LoginMobilenumberEndingwithHyphen(report);
					cfnlib.LoginMobilenumberConsecutiveSpaces(report);
					cfnlib.LoginStrongPassword(report);
					cfnlib.LoginCombinePassword(report);
					cfnlib.LoginNumericPassword(report);
					cfnlib.LoginSpecialCharacterPassword(report);
					cfnlib.LoginPasswordMinimum(report);
					cfnlib.LoginPasswordMaximum(report);
					cfnlib.LoginPasswordwithAllowedCharacters(report);
					cfnlib.LoginPasswordwithSpaces(report);
					cfnlib.LoginTrimPassword(report);
					cfnlib.LoginBlankPassword(report);
					cfnlib.LoginMaximumPasswordLength(report);
					cfnlib.LoginMinimumPasswordLength(report);
					cfnlib.NonNumericCharactersinMobileNumber(report);

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