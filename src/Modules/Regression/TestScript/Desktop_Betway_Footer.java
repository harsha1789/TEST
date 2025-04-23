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
 *@author PD69988
 */
public class Desktop_Betway_Footer {

	public ScriptParameters scriptParameters;

	Logger log = Logger.getLogger(Desktop_Betway_Footer.class.getName()); // To get Logs

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
			cfnlib.XpathMap.get("VuvuzelaSA"),

			};
			for (String url1 : urls) {
			Thread.sleep(2000);
			webdriver.navigate().to(url1);
			System.out.println("URL Opened: " + url1);
			Thread.sleep(2000);



			////////////Akhil AS66528 ////			
			
			cfnlib.VuvuzelCookies(report);
			cfnlib.FooterOptionCheck(report);
			cfnlib.HyperLinkOnFooter(report);
			cfnlib.BetwayLogoinFooter(report);
			cfnlib.SponsorshipsClickInFooter(report);
			cfnlib.Privacy_PolicyOptionInFooter(report);
			cfnlib.HyperlinksWithinPrivacy_Policy(report);
			cfnlib.BackbuttonOnPrivacy_PolicyPage(report);
			cfnlib.ClickContactUsOption(report);
			cfnlib.ContactUsInformationPage(report);
			cfnlib.LiveSupportButtonInContactus(report);			
			cfnlib.ContactusPagegaminglink(report);
			cfnlib.FacebookOptionInContactus(report);
			cfnlib.StandardRateCall(report);			
			cfnlib.TwitterAppOnContactUS(report);
			cfnlib.WtsAppOnContactUS(report);			
			cfnlib.SponsorshipsHyperlink(report);
			cfnlib.FAQOnFooterSection(report);
			cfnlib.FAQlistOnFooterSection(report);
			cfnlib.FAQSelectAnylistOnFooterSection(report);
			cfnlib.BackbuttonOnFAQPage(report);
			cfnlib.ResponsibleGamingOnFooterSection(report);
			cfnlib.BackbuttonOnResponsibleGamingPage(report);
			cfnlib.TermsConditionsOnFooterSection(report);
			cfnlib.TermsConditionslistAvailable(report);
			cfnlib.SelectAnylistOnTermsConditions(report);
			cfnlib.BackbuttonOnTermsConditionsPage(report);		
			cfnlib.AffiliatesOnFooter(report);
			cfnlib.LegalinformationOnFooter(report);
			cfnlib.LocaltimeOnFooter(report);
			cfnlib.betWayAppQRcodeOnFooter(report);
			cfnlib.AppstoreiconsOnFooter(report);
			cfnlib.socialmediaIconsOnFooter(report);
			cfnlib.EighteenPlusLogoOnFooter(report);
			

			
			/////// PAVAN PS69988  //////////////
			cfnlib.FooterOptionCheckForLive(report);
			cfnlib.FooterOptionsCheckForLive(report);
			cfnlib.FooterOptionCheckForCasinoGames(report);
			cfnlib.FooterOptionsCheckForCasinoGames(report);
			cfnlib.FooterOptionCheckForAviator(report);
			cfnlib.FooterOptionsCheckForAviator(report);
			cfnlib.FooterOptionCheckForBWJackpot(report);
			cfnlib.FooterOptionsCheckForBWJackpot(report);
			cfnlib.FooterOptionCheckForBetGames(report);
			cfnlib.FooterOptionsCheckForBetGames(report);
			cfnlib.FooterOptionCheckForESports(report);
			cfnlib.FooterOptionsCheckForESports(report);
			cfnlib.FooterOptionCheckForVirtuals(report);
			cfnlib.FooterOptionsCheckForVirtuals(report);
			cfnlib.FooterOptionCheckForPromotions(report);
			cfnlib.FooterOptionsCheckForPromotions(report);
			cfnlib.FooterOptionCheckForLuckyNumbers(report);
			cfnlib.FooterOptionsCheckForLuckyNumbers(report);
			

			
			
			
			
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
