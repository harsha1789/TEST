package Modules.Regression.FunctionLibrary;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.swing.JSlider;
import javax.swing.plaf.synth.SynthSliderUI;

//import io.appium.java_client.TouchAction<T>;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zensar.automation.framework.report.Mobile_HTML_Report;
import com.zensar.automation.framework.utils.Util;
import com.zensar.automation.library.ImageLibrary;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;
import net.lightbody.bmp.BrowserMobProxyServer;

public class CFNLibrary_Mobile_CS extends CFNLibrary_Mobile
{
	long Avgquickspinduration = 0;
	long Avgnormalspinduration = 0;
	private double userwidth;
	private double userheight;
	private double userElementX;
	private int userElementY;
	private int Ty;
	private int Tx;
	public List<MobileElement> el;
	String balance=null;
	String loyaltyBalance=null;
	String totalWin=null;
	int totalWinNew=0;
	int initialbalance1=0;
	String numline=null;
	int totalnumline=0;
	int previousBalance=0;
	public WebElement futurePrevent;
	WebElement time=null;
	WebElement slotgametitle;
	int newBalance=0;
	int freegameremaining=0;
	int freegamecompleted=0;
	Properties OR=new Properties();
	String GameDesktopName;
	public AppiumDriver<WebElement> webdriver;
	public BrowserMobProxyServer proxy;
	public Mobile_HTML_Report repo1;
	public WebElement TimeLimit;
	public String GameName;
	public String wait;
	Util clickAt=new Util();

	public CFNLibrary_Mobile_CS(AppiumDriver<WebElement> webdriver, BrowserMobProxyServer proxy, Mobile_HTML_Report tc06, String gameName) throws IOException {
		super(webdriver, proxy, tc06,gameName);

		this.webdriver=webdriver;
		this.proxy=proxy;
		repo1= tc06;
		webdriver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		Wait = new WebDriverWait(webdriver, 5000);
		this.GameName=gameName;	
		
	}
	
	

	/*Date: 30/04/2019
	 *Description:To verify is_autoplay_window. 
	 *	 *Parameter: NA
	 * @return boolean
	 */
	
	//GTR Test case
	public boolean isAutoplayWindow()
	{
		try{
		//webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
		Thread.sleep(3000);
		if(webdriver.findElement(By.xpath(xpathMap.get("AutoplayHeader"))).isDisplayed()){
		log.debug("Auto play setting window is displayed");
		}
		}
		catch(Exception e)
		{
			log.error("Autoplay setting window not Displayed");
			return false;
		}
		return true;

	}
	/* Sneha Jawarkar: Wait for Spin button */
	public boolean waitForSpinButtonstop() {
		try {
			System.out.println("Waiting for spinbutton active to come after completion of FreeSpin");
			while (true) {
				/*elementWait(
						"return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('SpinButtonComponent').Buttons.spinButton.currentState",
						"active");*/
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * * Date: 14/05/2019 Author: Sneha Jawarkar. Description: This function is
	 * used in GTR_freegame Parameter: play letter
	 */
	public void clickBaseSceneDiscardButton() {
		try {
			/*clickAtButton(
					"return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesRemainingComponent').onButtonClicked('deleteButton')");*/
			System.out.println("Clicked on basescene Discard Button");
		} catch (Exception e) {
			System.out.println("Can not Clicked on Basescene Discard Button");
		}
	}
	
	/**
	 * * Date: 17/05/2019 Author: Sneha Jawarkar. Description: This function is
	 * used in GTR_freegame Parameter: resume play button
	 */
	public boolean clickFreegameResumePlayButton() {
		boolean b = false;
		try {
			/*JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			Coordinates coordinateObj = new Coordinates();
			String align = "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesComponent').views.freeGamesResumeView.Buttons.resumeButton.label.currentStyle.alignment";
			typeCasting(
					"return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').x",
					coordinateObj);
			coordinateObj.setX(coordinateObj.getSx());
			typeCasting(
					"return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').y",
					coordinateObj);
			coordinateObj.setY(coordinateObj.getSx());
			typeCasting(
					"return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').height",
					coordinateObj);
			coordinateObj.setHeight(coordinateObj.getSx());
			typeCasting(
					"return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').width",
					coordinateObj);
			coordinateObj.setWidth(coordinateObj.getSx());
			coordinateObj.setAlign((js.executeScript(align)).toString());
			getComponentCenterCoordinates(coordinateObj);
			clickAtCoordinates(coordinateObj.getCenterX(), coordinateObj.getCenterY());
			*/Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * This method is used to wait till FS scene loads Author: Sneha Jawarkar
	 */
	public boolean freeSpinEntryScene() {
		try {
			log.debug("After refreshing,waiting for free spin's scene to come");
			Thread.sleep(10000);
				/*String currentScene = GetConsoleText("return mgs.mobile.casino.slotbuilder.v1.automation.currentScene");
				if (currentScene.equalsIgnoreCase("FREESPINS"))
				*/	return true;
				//System.out.println("free spins are started.");
				
		} catch (Exception e) {
			log.error("error while waiting for free spin scene", e);
			return false;
		}
	}

	/*
	 * Date: 16/05/2019 Author:Sneha Jawarkar Description: Freegame_GTR
	 * Parameter: NA
	 */
	public boolean completeFreeGameOfferFreespin(int freegamescount)
	{ try{
			for(int i=0;i<freegamescount;i++)
			{
				spinclick();
				Thread.sleep(10000);
				System.out.println("click on spin");
				if(freeSpinEntryScene())
				{	
					//spinclick();
					Thread.sleep(10000);					
					webdriver.navigate().refresh();
					Thread.sleep(10000);
					clickFreegameResumePlayButton();
					FS_continue();
					Thread.sleep(10000);
					
				}
			waitForSpinButtonstop();
			}
	}
			catch(Exception e){
				log.error("Can Not Clicked on spin Button");
				
			}
		return false;
	}
	

	/*
	 * Date: 16/05/2019 Author:Sneha Jawarkar Description: Freegame_GTR
	 * Parameter: NA
	 */
	public boolean completeFreeGameOffer(int freegamescount) {
		try {
			for (int i = 0; i < freegamescount; i++) {
				spinclick();
				// write for spin button to stop
				waitForSpinButtonstop();
			}
			log.debug("Clicked on spin button");
			return true;
		} catch (Exception e) {
			log.debug("Can not complete the freegame offer", e);
			return false;
		}

	}
	
	/**
	 * * Date: 14/05/2019 Author: Sneha Jawarkar. Description: This function is
	 * used in GTR_freegame Parameter: play letter
	 */
	public boolean clickOnPlayLater() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		/*try {
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			Coordinates coordinateObj = new Coordinates();
			String align = "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesComponent').views.freeGamesOfferView.Buttons.playLaterButton.buttonData.text.layoutStyles.desktop.alignment";
			typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').x",coordinateObj);
			coordinateObj.setX(coordinateObj.getSx());
			typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').y",coordinateObj);
			coordinateObj.setY(coordinateObj.getSx());
			typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').height",coordinateObj);
			coordinateObj.setHeight(coordinateObj.getSx());
			typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').width",coordinateObj);
			coordinateObj.setWidth(coordinateObj.getSx());
			coordinateObj.setAlign((js.executeScript(align)).toString());
			getComponentCenterCoordinates(coordinateObj);
			clickAtCoordinates(coordinateObj.getX(), coordinateObj.getY());
			Thread.sleep(100);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
*/		return b;

	}
	/**
	 * Date:15/5/19 Author:Sneha Jawarkar GTR_Freegame purpose
	 */

	public void backtogameCenterclick() {
		try {
			/*clickAtButton(
					"return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesCompleteView.backToGameCenterButton')");
			*/log.debug("Clicked at back to game");
		} catch (Exception e) {
			log.error("Can not clicked on Back to Button", e);
		}
	}
	

	
	/**
	 * Date: 21/6/2019
	 * Author: Sneha Jawarkar
	 * Description: GTR Reelspin
	 * @throws InterruptedException 
	 */
	public long reelSpinspeedDuration() throws InterruptedException{
		long Avgspinduration = 0;
		long loadingTime = 0;
		long sum = 0;
		for(int i=0; i<5;i++)
		{
		long start = System.currentTimeMillis();
         spinclick();
         waitForSpinButton();
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		loadingTime = totalTime/1000;
		sum=sum+loadingTime;
		log.debug("Calculation for the spin duration is running properly");
		System.out.println(i+"taken"+loadingTime);
		//newFeature();
		//summaryScreen_Wait();
	   }
		log.debug("error while spin duration");
		System.out.println("Total spin duraion for the 5 spins = "+sum);
		Avgspinduration=sum/5;
		System.out.println("Average time between 5 spins is = "+Avgspinduration);
		return Avgspinduration;
	}
	
	/*Date: 5/05/2019
	 *Description:To verify Autoplay after free spin
	 *Parameter: NA
	 * @return boolean
	 */
	
	public boolean isAutoplayWithFreespin()
	{
		boolean freeSpin=false;
		try{
			//webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
			webdriver.findElement(By.xpath(xpathMap.get("Start_Autoplay"))).click();
			Thread.sleep(5000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spin_button"))));
			log.debug("Free spin over");
			freeSpin=true;
		}
		catch(Exception e)
		{
			log.error("Free Spins are not completed");
		}
		return freeSpin;
	}
	
	
	
	/*Date: 30/04/2019
	 *Description:To verify autoplay must stop when focus being removed.
	 *Parameter: NA
	 * @return boolean
	 */
	
	//GTR Test Case
	public boolean autoplayFocusRemoved(){
		
		boolean focus;
		try{
			/*clickAtButton("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('AutoplayOptionsComponent').onButtonClicked('autoplayMoreOptionsButton')");
			Thread.sleep(1000);
			clickAtButton("mgs.mobile.casino.slotbuilder.v1.automation.getControlById('AutoplayPanelComponent').onButtonClicked('autoplayStartButton')");
			 */
			webdriver.findElement(By.id(xpathMap.get("AutoplayID"))).click();
			webdriver.findElement(By.id(xpathMap.get("Start_Autoplay_ID"))).click();
			
			Thread.sleep(3000);

			String windowhandle=webdriver.getWindowHandle();
			Robot robot= new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			//To check open windows
			ArrayList<String> tabs=new ArrayList<String>(webdriver.getWindowHandles());
			System.out.println(tabs.size());
			String window=tabs.get(1);
			webdriver.switchTo().window(window);
			webdriver.get("https://outlook.office.com");
			//Thread.sleep(2000);
			webdriver.switchTo().window(tabs.get(0));
			//closeOverlay();
			log.debug("Switch to default tab");
			//driver.switchTo().defaultContent(); 

		}
		catch(Exception e)
		{
			log.error("Focus not get changed");
			log.error(e.getMessage());
			return focus=false;
		}
		//webdriver.getWindowHandle();
		return focus=true;	
	}
	
	
	/*Date: 30/04/2019
	 *Description:To verify Auto play on refreshing 
	 *Parameter: NA
	 * @return boolean
	 */
	//GTR Test Case
	public boolean autoplayOnRefresh(){
		boolean onrefresh;
		try{
			nativeClickByID(xpathMap.get("Start_Autoplay_ID"));
		//webdriver.findElement(By.xpath(XpathMap.get("Start_Autoplay"))).click();
		Thread.sleep(3000);
		webdriver.navigate().refresh();
		funcFullScreen();
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("Spin_Button_ID"))));
		log.debug("On refresh previous autoplay session has not resume");
		}
		catch(Exception e){
			log.error("On refresh previous autoplay session has resume");
			return onrefresh=false;
		}
		
		return onrefresh= true;
	}
	
	/*Date: 30/04/2019
	 *Description:To verify maximum count of spin. 
	 *	 *Parameter: NA
	 * @return boolean
	 */
	public boolean maxSpinChk()
	{
		boolean max_spin=false;
		try{
		Thread.sleep(3000);
		nativeClickByID(xpathMap.get("AutoplayID"));
		 WebElement SizeSlider =webdriver.findElement(By.id(xpathMap.get("SpinSizeSlider_ID")));
		// String value1 =webdriver.findElement(By.id(XpathMap.get("SpinCount_ID"))).getText();
		 clickWithWebElement(SizeSlider,500);
			log.debug("drag and drop performed");
			
			String value2 =webdriver.findElement(By.id(xpathMap.get("SpinCount_ID"))).getText();
			
			
			if(value2.equals("100"))
			{
				 max_spin=true;
			}
			else{
				 max_spin=false;
			}
			

	}
	catch(Exception e){
		log.error("Session not over after autoplay spin", e);
		
	}
		return  max_spin;

}
	
	
	/*Date: 30/04/2019
	 *Description:To verify Autoplay spin session stop
	 *Parameter: NA
	 * @return boolean
	 */
public boolean isAutoplaySessionEnd(){
		
		boolean spin_session ;
		Wait=new WebDriverWait(webdriver,15);
		try{
			//Thread.sleep(3000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Start_Autoplay_ID")));
			 WebElement e=webdriver.findElement(By.id("spinsSlidertrack"));
			 //WebElement SizeSlider =webdriver.findElement(By.id(XpathMap.get("SpinSizeSlider_ID")));
			 //Thread.sleep(3000);
			 clickWithWebElement(e,-300);
				
			nativeClickByID(xpathMap.get("Start_Autoplay_ID"));
			
			log.debug("Clicked Auto play..");
			//WebDriverWait wt=new WebDriverWait(webdriver, 15);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Spin_Button_ID"))));
			spin_session=true;
		}
		catch(Exception e)
		{
			log.error("Autoplay Session didn't get over after spin", e);
			return spin_session=false;
		}
		return spin_session;
	}

	/*Date: 30/04/2019
	 *Description:To verify Autoplay spin selection 
	 *Parameter: NA
	 * @return boolean
	 */
	
	
	public boolean autoplaySpinSelection(){
		boolean spin_autoplay=false;
		try
		{
		
			WebElement e=webdriver.findElement(By.id("spinsSlidertrack"));
			clickWithWebElement(e,200);
			log.debug("Clicked on spin slider..");
			spin_autoplay=true;
		}
		catch(Exception e)
		{
			log.error("Spin count not getting change.", e);
			return spin_autoplay=false;
			
		}
		return spin_autoplay;
	}
	
	/*Date: 30/04/2019
	 *Description:To verify Autoplay with quick spin on
	 *Parameter: NA
	 * @return boolean
	 */
	
	public boolean autoPlayWithQSOn()
	{
		boolean qsoff;
		try
		{
		 qsoff = webdriver.findElement(By.xpath(xpathMap.get("QuickSpin_Off"))).isDisplayed();
		WebElement qsoffele = webdriver.findElement(By.xpath(xpathMap.get("QuickSpin_Off")));
		if(qsoff)
		{
			nativeClickByID(xpathMap.get("QuickSpin_ID"));
			nativeClickByID(xpathMap.get("AutoplayID"));
			
		}		
		}
	catch(Exception e)
	{
		log.error("Error while clicking autoplay button", e);
		qsoff=false;
	}
		return qsoff;
		
	}

	/*
	 * Date: 13/11/2017
	 * Author: Ashish Kshatriya
	 * Description: This function is used  for get Webelement text 
	 * Parameter: By locator
	 */
	public String func_GetText(String locator)
	{
		try{
			WebElement ele=webdriver.findElement(By.xpath(locator));
			return ele.getText();
		}
		catch(NoSuchElementException e)
		{
			return null; 
		}
	}
	/**
	 * *Author:Premlata
	 * This method is used to slide the coin size slider
	 * @throws InterruptedException 
	 */
	@Override
	public boolean moveCoinSizeSlider() throws InterruptedException {
		boolean isSliderMove=false;
		try
		{
			Thread.sleep(1000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("coinsSizeSlidertrack"))));
			WebElement e=webdriver.findElement(By.xpath(xpathMap.get("OneDesignCoinSizeSlider")));
			if(osPlatform.equalsIgnoreCase("Android"))
			{
				Actions action = new Actions(webdriver);   
				action.dragAndDropBy(e, 127, 0).build().perform();
				isSliderMove=true;
			}
			else
				{
				clickWithWebElement1(e, 50);
				isSliderMove=true;
				}
		}
		catch(Exception e)
		{
			isSliderMove=false;
			log.debug(e.getMessage());
		}
		return isSliderMove;
	}
	/**
	 * *Author:sneha 
	 * This method is used to check autoplay availability 
	 * @throws InterruptedException 
	 */
	
	public boolean ISAutoplayAvailable()
	{
		boolean autoplay;
		try
		{
			autoplay = webdriver.findElement(By.id(xpathMap.get("AutoplayID"))).isDisplayed();
		}
		catch(Exception e)
		{
			log.error("Autoplay id button is not visible", e);
		 	autoplay=false;
		}
		return autoplay;
	}
	/**
	 * *Author:Premlata
	 * This method is used to wait to come free spin screen ,after refreshing the free spin  
	 * @throws InterruptedException 
	 */
	public String FS_RefreshWait()
	{
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeSpin_Credits_ID"))));
			wait=webdriver.findElement(By.id(xpathMap.get("FreeSpin_Credits_ID"))).getText();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return wait;
	}
	public void Coinselectorclose(){
		//	func_Click(XpathMap.get("OneDesignCoinSliderClose"));
		try{
			webdriver.context("NATIVE_APP");
			MobileElement el4 = (MobileElement) webdriver.findElement(By.xpath(xpathMap.get("M_OneDesign_Hamburger")));
			el4.click();
			Thread.sleep(1000);
			boolean	test=webdriver.findElement(By.id(xpathMap.get("OneDesignBetbtnyes_ID"))).isDisplayed();
			if(test){
				//Wait.until(ExpectedConditions.presenceOfElementLocated(By.id(XpathMap.get("btnWarningYes"))));
				nativeClickByID(xpathMap.get("OneDesignBetbtnyes_ID"));
				Thread.sleep(2000);
			}
			else{
				System.out.println("yes button is not in the game");
			}
		}
		catch(Exception e){
		}
	}
	/**
	 * Date:07/12/2017
	 * Author:premlata mishra
	 * This method is to get total bet amount
	 * @return true
	 */
	public String Slider_TotalBetamnt()
	{
		String totalbet1=null;
		try
		{
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesignCoinSizeSlider"))));
			//String totalbet=func_GetText(XpathMap.get("OneDesigntotalbet"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesignCoinSizeSlider_ID"))));
			String totalbet=webdriver.findElement(By.id(xpathMap.get("OneDesigntotalbet_ID"))).getText();
			totalbet1=func_String_Operation(totalbet);
			System.out.println("win amount"+totalbet1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return totalbet1;
	}
	public boolean betDecrease(){
		try{
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test1=webdriver.findElements(By.xpath(xpathMap.get("OneDesignBetDecrement"))).size()>0;
			if(test1)
			{
				func_Click(xpathMap.get("OneDesignBetDecrement"));
			}
			else
			{
				System.out.println("- button is not here");
			}
			boolean test=webdriver.findElements(By.xpath(xpathMap.get("OneDesignBetbtnyes"))).size()>0;

			if(test){
				Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("OneDesignBetbtnyes"))));
				func_Click(xpathMap.get("OneDesignBetbtnyes"));
				Thread.sleep(2000);
			}
			else{
				System.out.println("yes button is not in the game");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * Date: 13/11/2017
	 * Author: Ashish Kshatriya
	 * Description: This function is used  for click on element 
	 * Parameter: By locator
	 */
	public boolean func_Click(String locator)
	{
		try{
			WebElement ele=webdriver.findElement(By.xpath(locator));
			//Actions a=new Actions(webdriver);
			if(ele != null)
			{   
				//a.moveToElement(ele, 3, 3).click();
				ele.click();
			}
			return true;
		}
		catch(NoSuchElementException e)
		{
			return false; 
		}
	}

	/**
	 * Autohr: Haivsh Jain	
	 * Description: This function used for navigating to Gibraltar url 
	 * @return true
	 */
	public String  func_navigate_DirectURL(String urlNavigate ){
		Wait=new WebDriverWait(webdriver,500);	 
		String title = null;
		try
		{
			//urlNavigate=XpathMap.get("app_url_Gibraltar_Mobile");
			webdriver.context("NATIVE_APP");
			webdriver.rotate(ScreenOrientation.LANDSCAPE);
			webdriver.context("CHROMIUM");
			webdriver.navigate().to(urlNavigate);   

			//newFeature();
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inGameMenuBtn"))); 

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("M_SpinButton_ID"))));
			funcFullScreen();
			title=webdriver.getTitle();
		} catch(Exception e){
			e.printStackTrace();
		}
		return title;
	}
	/*Havish Jain: Method is used to wait till Spin button appears*/
	public void waitForSpinButton() {
		Wait=new WebDriverWait(webdriver,600);
		try{
			log.debug("Waiting for base scene to come after completion of FreeSpin");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Spin_Button_ID"))));
		}
		catch(Exception e){
			log.error("error while waiting for base scene to come", e);
		}
	}
	
	/*Havish Jain: Method is used to wait till Stop button*/
	public void verifyStopLanguage(Mobile_HTML_Report report, String language) {
		Wait=new WebDriverWait(webdriver,20);
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Spin_Button_ID"))));
			nativeClickByID(xpathMap.get("Spin_Button_ID"));
			log.debug("Clicked on Spin button");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Stop_button_ID"))));
			report.detailsAppendFolder("verify the Stop button", "Stop button should display", "Stop button is displaying", "Pass", language);
			log.debug("has taken the screenshot of stop button");
			log.debug("Waiting for Spin button to visible");
			Wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("Spin_Button_ID"))));
		}
		catch (Exception e) {
			log.error("error in verifyStop()", e);
		}
	}
	/**
	 * Autohr: Havish Jain	
	 * Description: This function used for verifying the help icon in Gibraltar reg market
	 * @return  * @throws InterruptedException 
	 */
	public boolean verifyHelp() throws InterruptedException{
		boolean ret=false;
		boolean helpMenu = webdriver.findElement(By.id(xpathMap.get("M_HelpMenuID"))).isDisplayed();
		if(helpMenu){
			nativeClickByID(xpathMap.get("M_HelpMenuID"));

			Thread.sleep(1000);			
			boolean test=webdriver.findElement(By.id(xpathMap.get("Gibraltar_HelpLinkID"))).isDisplayed();
			if(test){
				ret=true;
			}
		}
		return ret;
	}

	/**
	 * Autohr: Havish Jain	
	 * Description: This function used for clicking help icon in Gibraltar reg market
	 * @return 
	 */
	public String clickHelp(){
		String GoogleTitle=null;
		nativeClickByID(xpathMap.get("Gibraltar_HelpLinkID"));
		//Native_ClickByID("inGameHelp");
		Wait.until(ExpectedConditions.titleContains("Google"));
		GoogleTitle=webdriver.getTitle();
		return GoogleTitle;
	}

	public String clickAt(String userdata) 
	{
		String[] coordinates1 = userdata.split(",");
		userwidth = Integer.parseInt(coordinates1[0]);
		userheight = Integer.parseInt(coordinates1[1]);
		userElementX = Integer.parseInt(coordinates1[2]);
		userElementY = Integer.parseInt(coordinates1[3]);
		Actions act = new Actions(webdriver);
		WebElement ele1 = webdriver.findElement(By.id(OR.getProperty("canvasxPath")));
		double deviceHeight = ele1.getSize().height;
		double deviceWidth = ele1.getSize().width;
		Tx = (int) ((deviceWidth / userwidth) * userElementX);
		Ty = (int) ((deviceHeight / userheight) * userElementY);
		//System.out.println("TX value:" + Tx + "Ty value:" + Ty);
		act.moveToElement(ele1, Tx, Ty).click().build().perform();
		return Tx + "," + Ty;
	}
	/**
	 * This method is used to click on Spin button
	 * Author: Havish Jain
	 * @return true
	 */

	public boolean spinclick() throws InterruptedException
	{
		try
		{
			Thread.sleep(2000);
			WebElement we = webdriver.findElement(By.id(xpathMap.get("Spin_Button_ID")));
			nativeClickByID(xpathMap.get("Spin_Button_ID"));
			log.debug("Clicked on spin button");
			//need to uncomment for other game Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("Spin_Button_ID"))));
		}
		catch (Exception e) {
			log.error("error while clicking on spin button", e);
		}
		return true;
	}
	/**
	 * This method is used to login with random string generator
	 * Author: Havish Jain
	 * @return true
	 */
	/*public long Random_LoginBaseScene(String userName){
		Wait=new WebDriverWait(webdriver,500);	 
		long loadingTime = 0;
		try {
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).clear();

			String password=XpathMap.get("Password");
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).sendKeys(userName);
			webdriver.findElement(By.xpath(XpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("password"))).sendKeys(password);
			Thread.sleep(2000);
			webdriver.findElement((By.xpath(XpathMap.get("Login")))).click();

			long start = System.currentTimeMillis();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			long finish = System.currentTimeMillis();
			long totalTime = finish - start;
			loadingTime = totalTime/1000;
			System.out.println("Total Time for Game load in Seconds is: "+loadingTime);

			func_FullScreen();
			//newFeature(); Full Screen overlay will appear first in mobile
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loadingTime;
	}*/

	/**
	 * This method is used to wait till FS scene loads
	 * Author: Havish Jain
	 * @return true
	 */
	public boolean FSSceneLoading()
	{
		//wait for Free spin scene to refresh
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FSMultiplierCountID"))));
		return true;
	}

	/**
	 * *Author:Premlata
	 * This method is used to wait till the free spin entry screen won't come
	 * @throws InterruptedException 
	 */
	public String entryScreen_Wait(String entry_Screen)
	{
		String wait= null;
		try{
			if(entry_Screen.equalsIgnoreCase("yes"))
			{
				log.debug("Waiting for free spin entry screen to come");
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeSpin_ClickToContinueID"))));
				wait= webdriver.findElement(By.id(xpathMap.get("FreeSpin_ClickToContinueID"))).getText();
			}
			else
			{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FSMultiplierCountID"))));
				wait = "Free Spin";
			}
		}
		catch (Exception e) {
			log.error("error in waiting for free spin entry screen",e);
		}
		return wait;
	}

	/**
	 * *Author:Premlata
	 * This method is used to click on free spin entry screen 
	 * @throws InterruptedException 
	 */
	public String clickToContinue()
	{
		String test=null;
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("M_FreeSpin_ClickToContinueID"))));
			//func_Click(XpathMap.get("FreeSpin_ClickToContinue"));
			nativeClickByID(xpathMap.get("M_FreeSpin_ClickToContinueID"));
			test="freeSpin";
		}
		catch (Exception e) {
			e.printStackTrace();
			test=null;
		}
		return test;
	}
	/*Havish Jain: This method is to click on "Start Free Spin" button in FS*/
	public void FS_Start() {
		nativeClickByID(xpathMap.get("StartFSID"));
		log.debug("Clicked on start button");
	}

	/*Havish Jain: This method is to click on free spin's "Continue Free Spin"*/
	public void FS_continue()
	{
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("ContinueFSID"))));	
			boolean	test=webdriver.findElement(By.id(xpathMap.get("ContinueFSID"))).isDisplayed();
			if(test){
				nativeClickByID(xpathMap.get("ContinueFSID"));
				log.debug("Clicked on free spin continue button");
				Thread.sleep(2000);
			}
			else{
				System.out.println("yes button is not in the game");
			}
		}
		catch (Exception e) {
			log.error("error while clicking on free spin continue button", e);
		}
	}

	/*Havish Jain: This method is to wait till free spin summary screen
	 */
	/*public void waitSummaryScreen() {
		try
		{
			Wait = new WebDriverWait(webdriver, 100);
			log.debug("Waiting for summary screen to come");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("CongratsFSSummaryID"))));
			Thread.sleep(1500);
		}
		catch (Exception e) {
			log.error("error while waiting for summary screen");
		}
	}*/
	public String FS_summaryScreenClick()
	{
		String fs_credits=null;
		try{
			fs_credits = FS_Credits();	
			nativeClickByID(xpathMap.get("FSSummaryCountinue_ID"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return fs_credits;
	}

	public String FS_Credits()
	{
		String FS_Credits=null;
		String FS_Credits1=null;
		try
		{
			FS_Credits=webdriver.findElement(By.id(xpathMap.get("FreeSpin_Credits_ID"))).getText();
			FS_Credits1=func_String_Operation(FS_Credits);	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return FS_Credits1;
	}
	/**
	 * *Author:Premlata
	 * This method is used to wait till the free spin summary screen won't come
	 * @throws InterruptedException 
	 */
	public String summaryScreen_Wait()
	{
		Wait = new WebDriverWait(webdriver, 5000);
		String wait=null;
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("clock_ID"))));
			/*	boolean test=webdriver.findElement(By.id(XpathMap.get("FSSummaryCountinue_ID"))).isDisplayed();
			if(test){
				wait="freeSpin";
				Thread.sleep(2000);
			}
			else
			{
				System.out.println("summary screen is not available in the game");

			}*/
		}
		catch(Exception e){
			e.getMessage();
		}
		return wait;
	}
	/*
	 * Date: 13/11/2017
	 * Author: Ashish Kshatriya
	 * Description: This function is used  for send keys on text box 
	 * Parameter: By locator, String keys
	 */
	public boolean funcSendClick(By locator, String keys)
	{
		try{		
			WebElement ele=webdriver.findElement(locator);
			if(ele != null)
			{	 
				ele.sendKeys(keys);;
			}
			return true;
		}
		catch(NoSuchElementException e)
		{
			return false; 
		}
	}

	/**
	 * Date: 30/05/2018
	 * Autohr: Havish Jain 
	 * Description: This function used to click on Practice Play when Login Popup is open
	 * @return Title
	 * 
	 */

	public String loginWithPracticePlay() {
		Wait=new WebDriverWait(webdriver,500);	 
		String Title = null;
		try {
			func_Click(xpathMap.get("practice_Play"));
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Spin_Button_ID"))));
			Title=webdriver.getTitle();
			funcFullScreen();
			//newFeature();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return Title;
	}
	/**
	 * Date: 30/05/2018
	 * Autohr: Havish Jain 
	 * Description: This function used to click on bet button in Practice Play and normal player
	 */
	
	public void setMaxBetPanel()
	{
		Wait = new WebDriverWait(webdriver, 500);
		
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("clock"))));
			openTotalBet();		
	        Wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("maxBetButton"))));
	        WebElement we = webdriver.findElement(By.id(xpathMap.get("maxBetButton")));
	        clickWithWebElement(we, 0);
	        //nativeClickByID(XpathMap.get("maxBetButton"));
			log.debug("Clicked on max bet button in bet panel");
				
			
		}
		catch(Exception e)
		{
			log.error("Error in clicking on max bet button in bet panel");
		}
		
	}
	
	

	public boolean openTotalBet()
	{
		boolean isBetOpen=false;
		try{
			Thread.sleep(1000);
			open_Bet();
			isBetOpen=true;
			log.debug("Clicked on TotalBet button");
		}
		catch (Exception e) {
			log.error("error in open_TotalBet()", e);
		}
		return isBetOpen;
	}
	
	public boolean openTotalBetBoolean()
	{
		boolean flag = false;
		try{
			Thread.sleep(1000);
			if(open_Bet())
			{log.debug("Clicked on TotalBet button");
			flag = true;
			}
		}
		catch (Exception e) {
			log.error("error in open_TotalBet()", e);
			flag = false;
		}
		
		return flag;
	}
	
	public boolean verifypaytable()
	{
		 boolean flag = false;
		 try
		 {
		    Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("OneDesign_Spin_Button"))));
		    WebElement we = webdriver.findElement(By.xpath(xpathMap.get("OneDesign_Hamburger")));
		    clickWithWebElement(we, 0);
			Thread.sleep(2000);
			 WebElement we1 = webdriver.findElement(By.id(xpathMap.get("OneDesign_PaytableID")));
			 clickWithWebElement(we1, 0);
			log.debug("Clicked on paytable icon to open ");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("paytableScroll"))));
			
			if(webdriver.findElement(By.xpath(xpathMap.get("paytableScroll"))).isDisplayed())
			{
				//report.details_append("Verify that Navigation on paytable screen is done ", " paytable Screen should open", "Paytable is open", "pass");
				paytableClose();
			
				flag = true;
			}
				else
				{
					log.debug("Game is not navigated to paytable");
				//fail
				//report.details_append("Verify that Navigation on paytable screen is done ", "paytable should open","Paytable is  not open", "fail");

					flag = false;
				}
		 }
		 catch(Exception e)
		 {
			    log.error(e.getMessage(), e);
				log.error("error in opening paytable",e);
		 }
			
		 return flag;
		
	}
	public boolean quickSpinOff()
	{
		boolean flag = false;
		try {
			spinclick();
			//Thread.sleep(1000);
			if(webdriver.findElement(By.xpath(xpathMap.get("OneDesign_Stop_Button"))).isDisplayed())
				flag = true;
			else
				flag = false;
			
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	
	
	public String getBetAmtString()
	{
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("OneDesign_Spin_Button"))));
		String betVal=func_GetText(xpathMap.get("OneDesign_BetValue"));
		String CreditVal=func_String_Operation(betVal);
		//double CreditVal1=Double.parseDouble(CreditVal);
		return CreditVal;
	}

	public boolean open_Bet() {
		boolean isBetOpen=false;
		try{
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesignbetbuttonID"))));
		WebElement we = webdriver.findElement(By.id(xpathMap.get("OneDesignbetbuttonID")));
		//clickWithWebElement(we, 0);
		nativeClickByID(xpathMap.get("OneDesignbetbuttonID"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("coinsSizeSlidertrack"))));
		isBetOpen=true;
		}
		catch(Exception e){
			isBetOpen=false;
			log.error(e.getMessage());
		}
		return isBetOpen;
	}

	/**
	 * Date: 31/05/2018
	 * Autohr: Havish Jain 
	 * Description: This function used to click on bet button in Practice Play and normal player
	 * 
	 */

	public void closeTotalBet() throws InterruptedException
	{	
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("TotalBet_OvelayID"))));
		//nativeClickByID(xpathMap.get("BetSettingsCloseID"));
		Thread.sleep(2000);
		closeOverlay();
		//open_Bet();
		
		log.debug("Closed the total bet overlay");
		Thread.sleep(2000);
		}
		catch(Exception e){
			log.error("error in close_TotalBet()", e);
		}
	}
	public String verifyCredit(){
		String CreditVal=null;
		try
		{
			//String creditValue=func_GetText(XpathMap.get("creditBalance"));
			String creditValue=webdriver.findElement(By.id(xpathMap.get("creditBalanceID"))).getText();

			CreditVal=func_String_Operation(creditValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return CreditVal;
	}
	/**
	 * Date:07/12/2017
	 * Author:Premlata Mishra
	 * This method is used for verifying the status of the quick spin
	 * @return true
	 */
	public String quickSpinStatus(){

		String opacityvalue=null;

		WebElement elementObj = webdriver.findElement(By.xpath(xpathMap.get("quickSpin_Toggle")));

		opacityvalue=elementObj.getCssValue("opacity");
		System.out.println(opacityvalue);
		return opacityvalue;
	}
	/**
	 * This method is used to verify the spin and stop visibility
	 * @param 
	 * @return true
	 * @throws InterruptedException 
	 *
	 **/
	public boolean verifySpin_Stop()
	{
		boolean spin=false;
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("M_SpinButton_ID"))));
			nativeClickByID((xpathMap.get("M_SpinButton_ID")));
			webdriver.context("NATIVE_APP");
			Thread.sleep(1000);
			spin=webdriver.findElement(By.id(xpathMap.get("Stop_button_ID"))).isDisplayed();
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Spin_Button"))));
			webdriver.context("CHROMIUM");

		}
		catch(Exception e)
		{
			spin=false;
			e.getMessage();
		}
		return spin;
	}
	/**
	 * This method is used to verify the spin visibility
	 * @param 
	 * @return true
	 * @throws InterruptedException 

	//*public boolean customeverifyimage(String button) throws InterruptedException {
		boolean isMatch=false;
		if(button.equalsIgnoreCase("spin"))
		{
			String SpinText = verifySpin();
			if (SpinText.equalsIgnoreCase("Spin")) 
				isMatch=true;
		}

		//isMatch=super.customeverifyimage(button);
		if(button.equalsIgnoreCase("Stop"))
		{
			String StopButton = null;
			try {
				StopButton = verifyStop();
				System.out.println(StopButton);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (StopButton.equalsIgnoreCase("Stop"))
				isMatch=true;
		}
		return isMatch;
	}
	 *//**
	 * This method is used to verify the Base Game Logo
	 * @param imagepath
	 * @return
	 * @throws InterruptedException 
	 *//*
	public String verifySpin() throws InterruptedException {
		String SpinText=null;
try
{
		//if(button){
		//webdriver.context("NATIVE_APP");
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Spin_Button"))));
		//webdriver.context("NATIVE_APP");
		Native_Click(XpathMap.get("Spin_Button"));
		//func_Click(XpathMap.get("Spin_Button"));
		 SpinText=func_GetText(XpathMap.get("Spin_Button"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Spin_Button"))));
		//Thread.sleep(6000);
}
catch(Exception e)
{
	e.getMessage();
}
		return SpinText;
	}*/
	public void  winClick()
	{
		try
		{
			nativeClickByID(xpathMap.get("OneDesignWinID"));
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	public String verifyStop() throws InterruptedException {
		String StopText=null;
		try
		{
			//func_Click(XpathMap.get("Spin_Button"));
			Native_Click(xpathMap.get("Spin_Button"));
			StopText=func_GetText(xpathMap.get("Stop_Text"));
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return StopText;
	}
	/**
	 * Date:07/12/2017
	 * Author:Premlata Mishra
	 * This method is used to close paytable
	 * @return true
	 * @throws InterruptedException 
	 */
	@Override
	public void paytableClose() throws InterruptedException
	{
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("PaytableBack"))));
			WebElement we = webdriver.findElement(By.xpath(xpathMap.get("PaytableBack")));
			clickWithWebElement(we, 0);
			Thread.sleep(2000);
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Date:07/12/2017
	 * Author:Premlata Mishra
	 * This method is usedto open the settings
	 * @return true
	 * @throws InterruptedException 
	 */
	public void SettingsToBasescen() throws InterruptedException
	{
		try{
			webdriver.context("NATIVE_APP");
			MobileElement el4 = (MobileElement) webdriver.findElement(By.xpath(xpathMap.get("M_OneDesign_Hamburger")));
			el4.click();
			Thread.sleep(3000);
			//	boolean wait=webdriver.findElement(By.xpath(XpathMap.get("OneDesignMenuClose"))).isDisplayed();
			/*need to uncomment for reel gems if(!GameName.equals("reelGemsDesktop")&&!GameName.equals("reelGems"))
			{
				MobileElement el5 = (MobileElement) webdriver.findElement(By.id("btnMenuLabel"));
				el5.click();
			}*/
			webdriver.context("CHROMIUM");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("mobileBetContainerID"))));
			//Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesign_Spin_Button"))));
		}
		catch(Exception e)

		{

			e.getMessage();

		}
	}
	/**
	 * Date:07/12/2017
	 * Author:Premlata Mishra
	 * only declaration of component store method
	 * @return true
	 */
	public void refresh()
	{
		try
		{
			String currentUrl = webdriver.getCurrentUrl();
			webdriver.navigate().to(currentUrl);
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	/**
	 * Date:07/12/2017
	 * Author:Premlata Mishra
	 * This method is usedto open the settings
	 * @return true
	 */

	public String gamelogo()
	{
		try{
			boolean test=webdriver.findElements(By.xpath(xpathMap.get("OneDesignLogo"))).size()>0;
			if(test){
				String	gamelogo=func_GetText(xpathMap.get("OneDesignLogo"));
				Thread.sleep(2000);
				return gamelogo;
			}
			else{
				System.out.println("yes button is not in the game");
			}
		}
		catch(Exception e){

		}
		return null;
	}
	/**
	 * Date: 29/05/2018
	 * Author: Havish Jain
	 * Description: This function is used to refresh the page and 
	 * will take screenshot of splash screen before navigating to Base Scene. 
	 * Parameter: 
	 */

	public boolean splashScreen(Mobile_HTML_Report report, String language){
		Wait=new WebDriverWait(webdriver,500);	
		boolean status=false;
		try {
			webdriver.navigate().refresh();	
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("preLoaderBackgroundID"))));
			Thread.sleep(1500);
			report.detailsAppendFolder("verify the Splash Screen", "Splash Screen should display", "Splash screen is displaying", "pass",language );
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSpin")));
			status=true;
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
		}
		return status;
	}

	/**
	 * Date: 14/11/2017
	 * Author: Laxmikanth Kodam
	 * Description: This function is used navigate the url of the application
	 * Parameter:String applicationName,String urlNavigate
	 */
/*

	public String  Func_navigate(String urlNavigate){
		try
		{
			webdriver.context("NATIVE_APP");
			webdriver.rotate(ScreenOrientation.LANDSCAPE);
			webdriver.context("CHROMIUM");
			webdriver.navigate().to(urlNavigate);  
			JavascriptExecutor js = ((JavascriptExecutor)webdriver);
			js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.id(GameName)));
			js.executeScript("arguments[0].click();", webdriver.findElement(By.id(GameName)));
			Thread.sleep(3000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Login"))));
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		return GameName;
	}*/
	/**
	 * Date:10-1-2018
	 * Name:Havish Jain
	 * Description: this function is clicking on anyelement it will take screen shot 
	 * @throws Exception 
	 */
	public void GeneralError(Mobile_HTML_Report report)
	{
		try
		{
			if(webdriver.findElements(By.xpath(xpathMap.get("General_Error"))).size()>0);
			{
				report.detailsAppend("Verify that incomplete dilogue is coming", "incomplete dialogue must come", "incomplete dialogue is coming", "fail");
				webdriver.navigate().to(xpathMap.get("Italy_url"));
			}
		}
		catch (Exception e) {
			e.getMessage();
		}

	}
	/**
	 * Date: 14/11/2017
	 * Author:Premlata Mishra
	 * Description: This function is used verify incomplete game dialogue
	 * Parameter:String applicationName,String urlNavigate
	 */
	/**
	 * Date: 14/11/2017
	 * Author:Premlata Mishra
	 * Description: This function is used verify incomplete game dialogue
	 * Parameter:String applicationName,String urlNavigate
	 */
	public String incompleteGameName(String gamename)
	{
		String incomplete_GameName=null;
		try
		{
			Wait = new WebDriverWait(webdriver, 40);	
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("five_Reel_slot"))));
			JavascriptExecutor js = ((JavascriptExecutor)webdriver);
			js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.id(gamename)));

			js.executeScript("arguments[0].click();", webdriver.findElement(By.id(gamename)));
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			webdriver.findElement(By.id(xpathMap.get("General_Error_ID"))).isDisplayed();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("General_Error_ID"))));
			incomplete_GameName=webdriver.findElement(By.id(xpathMap.get("General_Error_ID"))).getText();
		}
		catch (Exception e)
		{
			incomplete_GameName=null;	
		}
		System.out.println("incompletegamegame"+incomplete_GameName);
		return incomplete_GameName;
	}

	/*
	 * Date: 06/04/2017 
	 * Author: Kanchan B 
	 * Description: This function Navigate Game application by taking application name 
	 * Parameter: applicationName to search in different page
	 */
	/*public String Func_navigate(String Url) 
	{
		String ret=null;
		try
		{	 // Func_Landscape();
			webdriver.get(Url);
			waitForPageToBeReady();
			ret=webdriver.getTitle();
		}catch(Throwable t)
		{
			t.getMessage();
		}
		return ret; 	 
	}*/
	//code for converting mobile screen orientation 
	/**
	 * to rotate to portrait
	 * rk61073
	 */
	public boolean funcPortrait() throws Exception
	{
		boolean ret = false;
		try{
			String curContext=webdriver.getContext();
			webdriver.context("NATIVE_APP");
			webdriver.rotate(ScreenOrientation.PORTRAIT);
			Thread.sleep(2000);
			webdriver.context(curContext);
			ret=true;
		}
		catch(Exception e)
		{
			evalException(e);
		}
		return ret;
	}
	/**
	 * to rotate to Landscape
	 * rk61073
	 */
	public boolean funcLandscape() throws Exception
	{
		boolean ret = false;
		try{
			String curContext=webdriver.getContext();
			webdriver.context("NATIVE_APP");
			webdriver.rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(2000);
			webdriver.context(curContext);
			ret=true;
		}
		catch(Exception e)
		{
			evalException(e);
		}
		return ret;
	}


	/*game play error handling */

	public String funGamePlayError()
	{
		String gameplay=null;
		try 
		{  
			// boolean regulaoryLastLogin = webdriver.findElements(By.xpath(OR.getProperty("regulatoryLastLoginPopup"))).size() > 0;
			gameplay=webdriver.findElement(By.xpath(OR.getProperty("gameplayerror"))).getText();

		}
		catch(Throwable t1)
		{
			t1.getMessage();
		}
		return gameplay;
	}

	/*
	 * Date: 04/05/2017 
	 * Author: Anish
	 * Description: This function Func_Login use to perform login operation 
	 * Commented By :Kanchan 
	 * Parameter: applicationName and url
	 */
	public String funcLogin(String userName, String password) {
		String Title = null;
		try {
			webdriver.rotate(ScreenOrientation.LANDSCAPE);
			webdriver.findElement(By.xpath(OR.getProperty("GameUserName"))).clear();
			webdriver.findElement(By.xpath(OR.getProperty("GameUserName"))).sendKeys(userName);
			webdriver.findElement(By.xpath(OR.getProperty("GamePassword"))).clear();
			webdriver.findElement(By.xpath(OR.getProperty("GamePassword"))).sendKeys(password);
			webdriver.findElement(By.xpath(OR.getProperty("GameLogin"))).click();
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty("canvasxPath"))));


			Thread.sleep(3000);
			closeSavePasswordPopup();
			Title = webdriver.getTitle();
			System.out.println("The Title is " + Title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Title;
	}


	/**
	 * Date: 14/11/2017
	 * Author: Laxmikanth Kodam
	 * Description: This function is used to login the application by entering username and password
	 * Parameter:String username,String password
	 */

	public String loginToBaseScene(String username,String password){
		Wait=new WebDriverWait(webdriver,500);	 
		String Title = null;

		try {
			webdriver.findElement(By.xpath(xpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(xpathMap.get("userName"))).sendKeys(username);
			webdriver.findElement(By.xpath(xpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(xpathMap.get("password"))).sendKeys(password);

			webdriver.findElement((By.xpath(xpathMap.get("Login")))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSpin")));
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			Title=GameName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Title;
	}
	
	
	public void funvFullScreenOff()
	{
		webdriver.context("NATIVE_APP"); 
		Dimension size1 = webdriver.manage().window().getSize(); 
		int startx = size1.width / 2;
		int starty = size1.height / 2;
		TouchAction action = new TouchAction(webdriver);
		action.press(PointOption.point(startx,starty)).release().perform();
		//dr.rotate(ScreenOrientation.LANDSCAPE);
		webdriver.context("CHROMIUM");
		
		/*dr.context("NATIVE_APP"); 
		
		action.tap(PointOption.point(2300,720)).perform();*/
		//dr.rotate(ScreenOrientation.LANDSCAPE);
		//WebElement webElement = dr.findElement(By.xpath("//html"));

		Actions act = new Actions(webdriver);
		act.moveByOffset(0,0).perform(); 
		act.moveByOffset(10,10).click().build().perform();
	}
	/**
	 * Date: 14/12/2017
	 * Autohr: Laxmikanth Kodam	
	 * Description: This function used to login the application
	 * @return Title
	 * 
	 */

	public boolean loginToItalyMarket(String username, String password) {
		Wait=new WebDriverWait(webdriver,500);	 
		String Title = null;
		boolean present=false;

		try {
			webdriver.findElement(By.xpath(xpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(xpathMap.get("userName"))).sendKeys(username);
			Thread.sleep(1500);
			webdriver.findElement(By.xpath(xpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(xpathMap.get("password"))).sendKeys(password);
			Thread.sleep(1500);
			webdriver.findElement((By.xpath(xpathMap.get("Login")))).click();
			//func_Click(XpathMap.get("practice_Play"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("italyHeader"))));



			/*

				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("preLoaderBackground"))));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spinButtonBox"))));*/


			Title=webdriver.getTitle();
			if(Title!=null){

				present=true;
			}else{
				present=false;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return present;
	}

	/**
	 * Date:31/05/2018
	 * Author:Havish Jain
	 * This method is actually not necessery in component store just declaration needed 
	 * @return true
	 */
	@Override
	public boolean settingsBack()
	{
		boolean isSettingClose=false;
		try {
			/*webdriver.context("NATIVE_APP");
			MobileElement el4 = (MobileElement) webdriver.findElement(By.xpath(xpathMap.get("M_OneDesign_Hamburger")));
			el4.click();
			log.debug("Clicked on settings back button to close settings ");
			Thread.sleep(1000);
			webdriver.context("CHROMIUM");*/
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("SettingCloseID"))));
			if(nativeClickByID(xpathMap.get("SettingCloseID")))
			{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Spin_Button_ID"))));
				isSettingClose=true;
			}
				
			
		}
		catch(Exception e)
		{
			log.debug("error in settingsBack()",e);
		}
		return isSettingClose;
	}


	/**
	 * Date: 07-01-2018
	 * Author:Laxmikanth Kodam
	 * Description: verify New Feature Dialogue is appearing on the screen
	 * @return
	 * @throws InterruptedException
	 */

	public void newFeature(){


		try{
			webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			boolean test=webdriver.findElements(By.id("countinue_txt")).size()>0;

			if(test){
				nativeClickByID(xpathMap.get("M_NewFeatureScreen"));
				//func_Click(XpathMap.get("OneDesign_NewFeature_ClickToContinue"));
			}

			else{

				System.out.println("New Feature Screen is not displaying");
			}
		}
		catch(Exception e){

			e.getMessage();
		}
		finally
		{
			webdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
	}


	/*
	 * Date: 04/05/2017 
	 * Author: Anish Description: This function Func_Login use to perform login operation 
	 * Parameter: username andpassword
	 * Commented By :Kanchan 
	 */
	public String Func_LoginBaseScene_Reg(String username, String password) throws Exception {
		Wait=new WebDriverWait(webdriver,300);	 
		String Title = null;
		try {
			webdriver.findElement(By.xpath(OR.getProperty("GameUserName"))).clear();
			webdriver.findElement(By.xpath(OR.getProperty("GameUserName"))).sendKeys(username);
			webdriver.findElement(By.xpath(OR.getProperty("GamePassword"))).clear();
			webdriver.findElement(By.xpath(OR.getProperty("GamePassword"))).sendKeys(password);
			// wait.until
			webdriver.findElement(By.xpath(OR.getProperty("GameLogin"))).click();
			//try {

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("regulatoryLastLoginPopup"))));
			closeSavePasswordPopup();
			boolean regulaoryLastLogin = webdriver.findElements(By.xpath(OR.getProperty("regulatoryLastLoginPopup"))).size() > 0;

			if (regulaoryLastLogin) {

				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("GameLoginOk1"))));	
				webdriver.findElement(By.xpath(OR.getProperty("GameLoginOk1"))).click();

				Thread.sleep(500);
			}
			/* } catch (Exception e) {
    System.err.println("Error : " + e.getMessage());
   }*/
			Title = webdriver.getTitle();
		} catch (Exception e) {
			System.out.println("inside catch");
			evalException(e);
		}
		return Title;
	}

	/*
	 * Date: 03/04/2017 
	 * Author: Kanchan Badhiye 
	 * Description: verifyTime
	 * functionality for verifying time Parameter: NA
	 */
	public String verifyTime()  {
		Wait=new WebDriverWait(webdriver,300);
		String Time=null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("clock"))));
			time = webdriver.findElement(By.xpath(xpathMap.get("clock")));
			Time=time.getText();
			System.out.println("Time is: " +Time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Time;
	}


	public String verifybacktolobby()
	{
		String backtolobbytitle=null;
		try
		{
			// click on hyperlink
			WebElement backtolobby1=webdriver.findElement(By.xpath(OR.getProperty("backtolobby")));
			backtolobby1.click();
			Thread.sleep(2000);
			//Back To Lobby	
			backtolobbytitle=webdriver.getTitle();
		}
		catch(Exception e)
		{

		}
		return backtolobbytitle;
	}

	/*public String verifyvaliddatasubmit()
 {
	 String validdata=null;
	 try
	 {

	 }
	 return validdata;
 }*/
	public String logoutSpainMarket()
	{
		String loginTitle = null;
		try
		{

			Wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty("accountsLobbyID"))));
			Thread.sleep(2000);
			webdriver.findElement(By.id(OR.getProperty("accountsLobbyID"))).click(); // Clickin on Accounts
			Thread.sleep(1000);
			Wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty("logoutbuttonId"))));
			Thread.sleep(1000);
			webdriver.findElement(By.id(OR.getProperty("logoutbuttonId"))).click(); // Clicking on log out button
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty("LoginTitleid"))));
			Thread.sleep(1000);
			loginTitle = webdriver.findElement(By.id(OR.getProperty("LoginTitleid"))).getText(); // Clicking on log out button
			webdriver.findElement(By.id(OR.getProperty("closeButtonid"))).click(); // Clicking on log out button
			Thread.sleep(1000);


			/*Wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty("accountsLobbyID"))));
			 Thread.sleep(2000);
			 Wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty("closeButtonid"))));
			 webdriver.findElement(By.id(OR.getProperty("closeButtonid"))).click();
			 //Wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty("accountsLobbyID"))));
			// Thread.sleep(2000);
			  logout=webdriver.getTitle();
			   //title Spain*/


		}



		catch(Exception e)
		{
			e.printStackTrace();
		}
		return loginTitle;
	}
	/*
	 * Date: 03/04/2017
	 * Author: Ashish Kshatriya
	 * Description: Login with Set game limit for Regulatory Market Spain Country
	 * Parameter: Username, Password, Application Name
	 */
	public String loginToRegulatoryMarketSpain(String username,String password){
		Wait=new WebDriverWait(webdriver,500);	 
		String Title = null;

		try {
			webdriver.findElement(By.xpath(xpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(xpathMap.get("userName"))).sendKeys(username);
			webdriver.findElement(By.xpath(xpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(xpathMap.get("password"))).sendKeys(password);
			Thread.sleep(1500);

			webdriver.findElement((By.xpath(xpathMap.get("Login")))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_LobbyOK"))));
			webdriver.findElement((By.xpath(xpathMap.get("spain_LobbyOK")))).click();


			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_SlotGameOverlay"))));
			Title=webdriver.getTitle();
			//			System.out.println("The Title is "+Title);
		}catch(Exception e){
			e.printStackTrace();
		}
		return Title;
	} 

	/* Verify the last Login Pop up  */

	public boolean lastLoginPopup(){

		//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("lastLoginPopup"))));
		Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR.getProperty("loadingContainer"))));
		WebElement lastlogin= webdriver.findElement(By.xpath(OR.getProperty("lastLoginPopup")));
		lastlogin.click();	

		return true;
	}

	public boolean verifyhyperlink()
	{
		boolean ret=false;
		try
		{
			//Thread.sleep(30000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();
			System.out.println(innerText);

			if(innerText.contains("Error")) {
				System.out.println("Game Play Error");
				ret=false;

			} else if(webdriver.findElement(By.xpath(OR.getProperty("backtolobby"))).isDisplayed()) {

				System.out.println("backtolobby is enabled");
				String dialogActionText = webdriver.findElement(By.xpath(OR.getProperty("dialogAction"))).getText();
				System.out.println(dialogActionText);

				if(dialogActionText.contains("START NEW SESSION")) {
					webdriver.findElement(By.xpath(OR.getProperty("startNewSession"))).click();
				}

				ret=true;
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	/*
	 * Author: Havish Jain  
	 * Description:To check if Start Session Button is Present for spain reg market
	 * Parameter: N/A
	 */

	public void checkPage(Mobile_HTML_Report tc10){
		try{

			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test= webdriver.findElements(By.xpath(xpathMap.get("spain_StartNewSession"))).size()>0;

			if(test){
				tc10.detailsAppend("Verify that Start New Sessiosn Overlay appears before the game loads ", "Start New Sessiosn Overlay should appear before the game loads", "Start New Sessiosn Overlay is appearing before the game loads", "Pass");
				func_Click(xpathMap.get("spain_StartNewSession"));
			}
			else{
				System.out.println("Slot Game limit overlay appears");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Author : Havish Jain 
	 * Description: To fill Spain Start Session Form 
	 * Param: Time Limit, Reminder Period, Loss Limit and prevent Future Slot Game for Play
	 * Return: Boolean value
	 */


	public boolean fillStartSessionLossForm(String LossLimit)
	{
		Wait=new WebDriverWait(webdriver,50);	
		boolean ret= false;
		try {	
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_TimeLimit"))));

			Select sel= new Select(webdriver.findElement(By.xpath(xpathMap.get("spain_TimeLimit"))));
			sel.selectByIndex(1);

			sel= new Select(webdriver.findElement(By.xpath(xpathMap.get("spain_ReminderPeriod"))));
			sel.selectByIndex(1);

			webdriver.findElement(By.xpath(xpathMap.get("spain_LossLimit"))).sendKeys(LossLimit);
			sel= new Select(webdriver.findElement(By.xpath(xpathMap.get("spain_PreventFutureSlot"))));
			sel.selectByIndex(1);

			webdriver.findElement(By.xpath(xpathMap.get("spain_SetLimits"))).click();
			Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathMap.get("spain_SetLimitsOK"))));
			webdriver.findElement(By.xpath(xpathMap.get("spain_SetLimitsOK"))).click();
			Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(xpathMap.get("clock_ID"))));
			ret = true;	

		} catch (Exception e) {

			e.printStackTrace();
		}
		return ret;
	}
	/*
	 * 
	 * Author: Havish Jain  
	 * Description:Overlay popup for Set Session Limit for regulatory market Spain
	 * Parameter: N/A
	 */
	public boolean overLay() throws InterruptedException
	{
		try
		{
			boolean SetSessionLimits=webdriver.findElements(By.xpath(xpathMap.get("spain_SlotGameOverlay"))).size()>0;
			if(SetSessionLimits)
				return true;
			else
				return false;
		}catch(NoSuchElementException e)
		{
			return false;
		}
	}

	/**
	  Author : Havish Jain
	 * Description: To wait until loss limit is reached 
	 * Param: Null
	 * Return: String value
	 */
	public String waitUntilSessionLoss(){
		String title = null;
		try {
			for(int i=0;i<=10;i++)
			{
				Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(xpathMap.get("M_SpinButton_ID"))));
				nativeClickByID(xpathMap.get("M_SpinButton_ID"));	 
				Thread.sleep(3000);
				boolean b = webdriver.findElements(By.xpath(xpathMap.get("spain_lossLimitDialogueOK"))).size()>0;
				if(b){
					System.out.println("Loss Limit is reached");
					title = webdriver.findElement(By.xpath(xpathMap.get("spain_lossLimitDialogueOK"))).getText();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	} 

	/**
	  Author : Havish Jain
	 * Description: To close popup of session loss and capture screenshot  
	 * Param: Mobile_HTML_Report
	 * Return: 
	 */

	public void closeSessionLossPopup(Mobile_HTML_Report tc10) {
		try {

			func_Click(xpathMap.get("spain_lossLimitDialogueOK"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Loss Limit Summary overlay appear", "Loss Limit Summary overlay should appear", "Loss Limit summary overlay appears", "Pass");
			func_Click(xpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("five_Reel_slotID"))));
			tc10.detailsAppend("To verify user navigates to lobby after clicking on close button when loss limit is reached", "User should redirect to lobby after clicking on close button when loss limit is reached", "User is redirected to lobby", "Pass");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	  Author : Havish Jain
	 * Description: To vefiry that game can't be played in cooling off, loss limit is already reached and user is in lobby again  
	 * Param: Mobile_HTML_Report
	 * Return: 
	 */

	public void coolingOffPeriod(Mobile_HTML_Report tc10) {
		try{
			nativeClickByID(xpathMap.get("five_Reel_slotID"));

			JavascriptExecutor js = ((JavascriptExecutor)webdriver);
			js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.id(GameName)));

			js.executeScript("arguments[0].click();", webdriver.findElement(By.id(GameName)));

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Cooling Off Period Overlay appear on launching same game", "Cooling Off Period Overlay should appear on launching same game", "Cooling Off Period Overlay appears on launching same game", "Pass");
			func_Click(xpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("five_Reel_slotID"))));
			tc10.detailsAppend("To verify user navigates to lobby after clicking on close button when cooling off period is running", "User should redirect to lobby after clicking on close button when cooling off period is running", "User is redirected to lobby", "Pass");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	  Author : Havish Jain
	 * Description: To verify that another game can't be played in cooling off, loss limit is already reached and user is in lobby again  
	 * Param: Mobile_HTML_Report
	 * Return: 
	 */

	public void coolingOffPeriodNewGame(String gamename, Mobile_HTML_Report tc10) {
		try{
			nativeClickByID(xpathMap.get("five_Reel_slotID"));

			JavascriptExecutor js = ((JavascriptExecutor)webdriver);
			js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.id(gamename)));

			js.executeScript("arguments[0].click();", webdriver.findElement(By.id(gamename)));

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Cooling Off Period Overlay appear on launching another game", "Cooling Off Period Overlay should appear on launching another game", "Cooling Off Period Overlay appears on launching another game", "Pass");
			func_Click(xpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("five_Reel_slotID"))));
			tc10.detailsAppend("To verify user navigates to lobby after clicking on close button when cooling off period is running", "User should redirect to lobby after clicking on close button when cooling off period is running", "User is redirected to lobby", "Pass");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	  Author : Havish Jain
	 * Description: To wait until session reminder popup appears  
	 * Return: String value
	 */

	public String waitUntilSessionReminder(){
		Wait = new WebDriverWait(webdriver, 200);
		String header = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_SessionReminderContinue"))));
			Thread.sleep(1000);
			header = webdriver.findElement(By.xpath(xpathMap.get("spain_SessionReminderContinue"))).getText();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return header;
	} 

	/**
	  Author : Havish Jain
	 * Description: To click on continue button in session reminder popup   
	 */

	public void spainContinueSession() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_SessionReminderContinue"))));
		webdriver.findElement(By.xpath(xpathMap.get("spain_SessionReminderContinue"))).click();
	}

	/**
	  Author : Havish Jain
	 * Description: wait until TimeLimit reaches and redirect to lobby  
	 * Param: Null
	 * Return: String value
	 */

	/**
	 * Date:25/06/2018
	 * Author:Premlata Mishra
	 * This method is used to open the settings
	 * @return true
	 * @throws InterruptedException 
	 */
	public boolean verifyQuickSpin()  {
		boolean test=false;
		try{

			test=webdriver.getPageSource().contains("Quick Spin");
			if(test)
			{
				return test=true;
			}
		}
		catch(Exception e)			{
			return test=false;
		}
		return test;
	}

	/**
	 * Date:25/06/2018
	 * Author:Havish Jain
	 * This method is used validate Time limit reached scenario. 
	   Click continue in Reminder popup and wait till time limit is reached
	 * @return true
	 * @throws InterruptedException 
	 */

	public void waitUntilTimeLimitSession(Mobile_HTML_Report tc10) {
		try{
			for (int i=0;i<=5;i++){
				spainContinueSession();
				boolean b = webdriver.findElements(By.xpath(xpathMap.get("spain_CloseBtn"))).size()>0;
				if(b){
					break;
				}}
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Time Limit Summary overlay appear when Time limit is reached", "Time Limit Summary overlay should appear", "Time Limit summary overlay appears", "Pass");
			func_Click(xpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("five_Reel_slotID"))));
			tc10.detailsAppend("To verify user navigates to lobby after clicking on close button when Time limit is reached", "User should redirect to lobby after clicking on close button when Time limit is reached", "User is redirected to lobby", "Pass");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * Author: Havish Jain 
	 * Description:Launch the game again when spain lobby is open
	 * Parameter: N/A
	 */

	public void relaunchGame() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("five_Reel_slotID"))));
		nativeClickByID(xpathMap.get("five_Reel_slotID"));

		JavascriptExecutor js = ((JavascriptExecutor)webdriver);
		js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.id(GameName)));

		js.executeScript("arguments[0].click();", webdriver.findElement(By.id(GameName)));

		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("spain_SlotGameOverlay"))));
	}

	/*
	 * Date: 06/04/2017
	 * Author: Ashish Kshatriya 
	 * Description:Verify SlotGameSession,TotalWagered,TotalPayouts & TotalBalance for regulatory market Spain
	 * Parameter: N/A
	 */
	public String[] lossLimit() throws InterruptedException
	{   String ar[] = new String[4];
	try
	{
		boolean SlotGameSession=webdriver.findElements(By.xpath(OR.getProperty("SlotGamesession1"))).size()>0;
		try{ 
			if(SlotGameSession);
			String slotGame=webdriver.findElement(By.xpath(OR.getProperty("SlotGamesession1"))).getText().toString().trim();
			ar[0]=slotGame;
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		boolean TotalWagered=webdriver.findElements(By.xpath(OR.getProperty("TotalWagered"))).size()>0;
		try{ 
			if(TotalWagered);
			String totalWagered=webdriver.findElement(By.xpath(OR.getProperty("TotalWagered"))).getText().toString().trim();
			ar[1]=totalWagered;
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		boolean TotalPayouts=webdriver.findElements(By.xpath(OR.getProperty("TotalPayouts"))).size()>0;
		try{ 
			if(TotalPayouts);
			String totalPayout=webdriver.findElement(By.xpath(OR.getProperty("TotalPayouts"))).getText().toString().trim();
			ar[2]=totalPayout;
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		boolean TotalBalance=webdriver.findElements(By.xpath(OR.getProperty("TotalBalance"))).size()>0;
		try{ 
			if(TotalBalance);
			String totalBalance=webdriver.findElement(By.xpath(OR.getProperty("TotalBalance"))).getText().toString().trim();
			ar[3]=totalBalance;
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
	}catch(NoSuchElementException e)
	{
		e.printStackTrace();
	}
	return ar;
	}

	/**
	 * Date 26/11/2017
	 * Author:Laxmikanth Kodam
	 * This method used to verify the Bet amount
	 * @return the value to the win amount
	 */
	public double verify_Bet_Amount(){
		double betAmount=0;

		try {
			String betValue=func_GetText(xpathMap.get("betValue"));
			String BetValue=func_String_Operation(betValue);
			System.out.println(BetValue);
			betAmount=Double.parseDouble(BetValue);
			System.out.println(betAmount);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return betAmount;
	}  

	/*
	 * Date: 03/04/2017
	 * Author: Ashish Kshatriya 
	 * Description: Select value from the dropdown
	 * Parameter: UserName, Password, Application Name
	 */ 
	public void selectValue(WebElement ele, String selectData) throws InterruptedException
	{
		try
		{
			Select select=new Select(ele);
			select.selectByValue(selectData);
			Thread.sleep(500);
		}
		catch(ElementNotVisibleException e)
		{
			e.printStackTrace();
		}
	}  
	/*
	 * Date: 06/04/2017 Author:
	 * Author: Ashish Kshatriya
	 * Description: This function is used  for take screenshots in application.
	 * Parameter: WebDriver driver
	 */
	public static String createScreenshot(WebDriver webdriver) throws InterruptedException { 
		UUID uuid = UUID.randomUUID();
		/*screenshotsFolder = "ImageScreenshot//Mobile//";
     File dir = new File(screenshotsFolder);
     dir.mkdirs();*/
		String imageLocation="D:\\Mobile_Image_Parallel\\Parallel\\ImageScreenshot\\Mobile";  
		File scrFile = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
		Thread.sleep(1000);
		try {
			FileUtils.copyFile(scrFile, new File(imageLocation + uuid + ".png"));
		} catch (IOException e) {
			System.out.println("Error while generating screenshot:\n" + e.toString());
		}
		return imageLocation + uuid + ".png";
	}
	/*
	 * Date: 10/04/2017 Author:
	 * Author: Ashish Kshatriya
	 * Description: This function is used  for to Verify error has occurred on not in Game
	 * Parameter: NA
	 */
	public String errorMessage() throws InterruptedException{
		String getMessage=null;
		try{
			if(webdriver.findElement(By.id(OR.getProperty("errorNotificationPopup"))).isDisplayed())
			{
				getMessage=webdriver.findElement(By.id(OR.getProperty("errorNotificationPopupBody"))).getText();
			}
			else{
			}
		}
		catch(NoSuchElementException e)
		{
			//getTest().log(LogStatus.ERROR, "Error has been found on screen"+getTest().addScreenCapture(createScreenshot(webdriver)));
		}
		return getMessage;
	}
	/*
	 * Date: 10/04/2017 Author:
	 * Author: Ashish Kshatriya
	 * Description: This function is used  for close save password popup in chrome browser
	 * Parameter: NA
	 */	
	public WebElement closeSavePasswordPopup()
	{
		WebElement closepopup=null;
		try{
			webdriver.context("NATIVE_APP");
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty("closeSavePassword"))));
			boolean pop=webdriver.findElements(By.xpath(OR.getProperty("closeSavePassword"))).size()>0;
			if(pop){
				closepopup=webdriver.findElement(By.xpath(OR.getProperty("closeSavePassword")));
				closepopup.click();
				webdriver.context("CHROMIUM");
			}
			else
			{
				webdriver.context("CHROMIUM");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return closepopup;
	}				




	public void waitForPageToBeReady() 
	{
		JavascriptExecutor js = (JavascriptExecutor)webdriver;
		for (int i=0; i<800; i++)
		{ 
			try 
			{
				Thread.sleep(1000);
			}
			catch (Exception e) {
				e.getMessage();
			} 
			if (js.executeScript("return document.readyState").toString().equals("complete"))
			{ 
				
				break; 
			}   
		}
	}

	public String tapAt(String elementKey) throws InterruptedException {
		String userdata=OR.getProperty(elementKey);
		String[] coordinates1 = userdata.split(",");
		userwidth = Integer.parseInt(coordinates1[0]);
		userheight = Integer.parseInt(coordinates1[1]);
		userElementX = Integer.parseInt(coordinates1[2]);
		userElementY = Integer.parseInt(coordinates1[3]);
		webdriver.context("NATIVE_APP");
		double deviceHeight = webdriver.manage().window().getSize().getHeight();
		double deviceWidth = webdriver.manage().window().getSize().getWidth();
		System.out.println("Device H="+deviceHeight+" Device W="+deviceWidth);
		Tx = (int) ((deviceWidth / userwidth) * userElementX);
		Ty = (int) ((deviceHeight / userheight) * userElementY);
		TouchAction a2 = new TouchAction(webdriver);
		a2.press(PointOption.point(Tx, Ty)).release().perform();
		//a2.tap(Tx, Ty).perform();
		webdriver.context("CHROMIUM");
		Thread.sleep(5000);
		return Tx + "," + Ty;
	}
	/*************code for spain
	 * @throws Exception ***********************/
	/*code for verifying slottitle*/
	public String verifyslottitle() throws Exception
	{
		String slotgametitle1 = null;
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));

			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				slotgametitle1 = "Error";

			} else {

				slotgametitle=webdriver.findElement(By.xpath(OR.getProperty("SlotGameLimit")));
				slotgametitle1=slotgametitle.getText();
			}
		}
		catch(Exception e)
		{

		}
		return slotgametitle1;
	}

	public String PreventFuture() throws Exception
	{
		String PreventFuture1 = null;
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();
			if(innerText.contains("Error")) {
				PreventFuture1 = "Error";
			} else {
				futurePrevent=webdriver.findElement(By.xpath(OR.getProperty("PreventFuture")));
				PreventFuture1=futurePrevent.getText();
			}
		}
		catch(Exception e)
		{
		}
		return PreventFuture1;
	}

	public String verifysetlimitbutton() throws Exception
	{
		String setlimit1 = null;
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();
			if(innerText.contains("Error")) {
				setlimit1 = "Error";
			} else {
				WebElement setlimit=webdriver.findElement(By.xpath(OR.getProperty("setlimitbutton")));
				setlimit1=setlimit.getText();
			}
		}
		catch(Exception e)
		{
		}
		return setlimit1;
	}

	/*verify time limit blank */
	public boolean verifytimelimitblank() throws Exception
	{
		boolean ret=false;
		try
		{
			WebElement timelimitinputbox=webdriver.findElement(By.xpath(OR.getProperty("timeLimitInput")));
			String textInsideInputBox = timelimitinputbox.getAttribute("value");Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				ret = false;
			} else if(textInsideInputBox.isEmpty()) {// Check whether input field is blank
				System.out.println("Input field is empty");
				ret=true;
			}
		}
		catch(Exception e)
		{

		}
		return ret;
	}


	/*verify reminder period blank */
	public boolean verifyreminderperiodblank() throws Exception
	{
		boolean ret=false;
		try
		{
			WebElement timelimitinputbox=webdriver.findElement(By.xpath(OR.getProperty("reminderLimitInput")));
			String textInsideInputBox = timelimitinputbox.getAttribute("value");

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				ret = false;

			} else if(textInsideInputBox.isEmpty()) {// Check whether input field is blank

				System.out.println("Input field is empty");
				ret=true;
			}

		}
		catch(Exception e)
		{

		}
		return ret;
	}


	/*verify time loss limit blank */
	public boolean losslimitblank() throws Exception
	{
		boolean ret=true;
		try
		{

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				ret = false;

			} else {// Check whether input field is blank

				WebElement timelimitinputbox=webdriver.findElement(By.xpath(OR.getProperty("lossLimitInput")));
				String textInsideInputBox = timelimitinputbox.getAttribute("value");

				if(textInsideInputBox.isEmpty()) {
					System.out.println("Input field is empty");
					ret=true;
				}
			}

		}
		catch(Exception e)
		{

		}
		return ret;
	}


	/*verify time limit blank */
	public boolean verifyfutureprventblank() throws Exception
	{
		boolean ret=false;
		try
		{

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				ret = false;

			} else {

				WebElement timelimitinputbox=webdriver.findElement(By.xpath(OR.getProperty("preventfutureslotgamefor")));
				String textInsideInputBox = timelimitinputbox.getAttribute("value");

				if(textInsideInputBox.isEmpty()) {// Check whether input field is blank
					System.out.println("Input field is empty");
					ret=true;
				}
			}

		}
		catch(Exception e)
		{

		}
		return ret;
	}


	//C720751(5005-5006-5011) Selectable options :Ensure dropdown box appear for Time limit box

	public boolean verifytimedropdown() throws Exception
	{
		boolean ret=false;
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				ret = false;

			} else {
				WebElement timedropdown=webdriver.findElement(By.xpath(OR.getProperty("timeLimitInput")));
				timedropdown.click();
				Thread.sleep(500);
				ret=true;
			}
		}
		catch(Exception e)
		{

		}
		return ret;
	}

	//C720751(5005-5006-5011) Selectable options :Ensure dropdown box appear for reminder limit box
	public boolean verifyreminderdropdown() throws Exception
	{
		boolean ret1 = false;
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				ret1 = false;

			} else {

				WebElement reminderdropdown=webdriver.findElement(By.xpath(OR.getProperty("reminderLimitInput")));
				reminderdropdown.click();
				Thread.sleep(500);
				ret1=true;
			}
		}
		catch(Exception e)
		{

		}
		return ret1;
	}
	//Selectable option Ensure slotreminder dropdown appear
	public boolean verifypreventfutureslotreminder() throws Exception
	{
		boolean ret=false;
		try
		{

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				ret = false;

			} else {
				WebElement preventfuture=webdriver.findElement(By.xpath(OR.getProperty("preventfutureslotgamefor")));
				preventfuture.click();
				ret=true;
			}
		}
		catch(Exception e)
		{
		}
		return ret;
	}

	//Ensure there are time duration options available for selection in the different time units (eg, 1 hour, 1 minute).

	public String verifytimedurationoption(String dropdownValue) throws Exception
	{
		String selectoption=null;
		try
		{
			WebElement timedropdown=webdriver.findElement(By.xpath(OR.getProperty("timeLimitInput")));
			//timedropdown.clear();
			//timedropdown.click();
			Thread.sleep(500);
			//List<WebElement> optionsoftimelimit = webdriver.findElements(By.xpath(OR.getProperty("timelimitoptions")));
			Select optionsoftimelimit = new Select(timedropdown);
			System.out.println("select options"+optionsoftimelimit);
			optionsoftimelimit.selectByVisibleText(dropdownValue);
			//optionsoftimelimit.selectByIndex(2);
			selectoption=optionsoftimelimit.getFirstSelectedOption().getText(); 
			System.out.println("option "+optionsoftimelimit);

		}
		catch(Exception e)
		{
			e.getMessage();
		}

		return selectoption;
	}

	//Ensure there are time duration options available for selection in the different time units (eg, 1 hour, 1 minute).

	public String verifytimedurationoption() throws Exception
	{
		String selectoption=null;
		try
		{
			WebElement timedropdown=webdriver.findElement(By.xpath(OR.getProperty("timeLimitInput")));
			//timedropdown.clear();
			//timedropdown.click();
			Thread.sleep(500);
			//List<WebElement> optionsoftimelimit = webdriver.findElements(By.xpath(OR.getProperty("timelimitoptions")));
			Select optionsoftimelimit = new Select(timedropdown);
			System.out.println("select options"+optionsoftimelimit);
			optionsoftimelimit.selectByIndex(1);
			//optionsoftimelimit.selectByIndex(2);
			selectoption=optionsoftimelimit.getFirstSelectedOption().getText(); 
			System.out.println("option "+optionsoftimelimit);

		}
		catch(Exception e)
		{
			e.getMessage();
		}

		return selectoption;
	}


	public String verifyreminderperiodoption(String dropdownValue) throws Exception
	{
		String selectoption=null;
		try
		{
			WebElement reminderdropdown=webdriver.findElement(By.xpath(OR.getProperty("reminderLimitInput")));
			//reminderdropdown.clear();
			//reminderdropdown.click();
			Thread.sleep(500);
			//List<WebElement> optionsoftimelimit = webdriver.findElements(By.xpath(OR.getProperty("timelimitoptions")));
			Select optionsoftimelimit = new Select(reminderdropdown);
			System.out.println("select options"+optionsoftimelimit);
			optionsoftimelimit.selectByVisibleText(dropdownValue);
			//optionsoftimelimit.selectByIndex(2);
			selectoption=optionsoftimelimit.getFirstSelectedOption().getText(); 
			System.out.println("option "+optionsoftimelimit);

		}
		catch(Exception e)
		{

		}

		return selectoption;
	}


	public String verifyreminderperiodoption() throws Exception
	{
		String selectoption=null;
		try
		{

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))));
			String innerText = webdriver.findElement(By.xpath(OR.getProperty(xpathMap.get("General_Error_ID")))).getText();

			if(innerText.contains("Error")) {
				selectoption = "Error";

			} else {
				WebElement reminderdropdown=webdriver.findElement(By.xpath(OR.getProperty("reminderLimitInput")));
				//reminderdropdown.clear();
				//reminderdropdown.click();
				Thread.sleep(500);
				//List<WebElement> optionsoftimelimit = webdriver.findElements(By.xpath(OR.getProperty("timelimitoptions")));
				Select optionsoftimelimit = new Select(reminderdropdown);
				System.out.println("select options"+optionsoftimelimit);
				optionsoftimelimit.selectByIndex(1);
				//optionsoftimelimit.selectByIndex(2);
				selectoption=optionsoftimelimit.getFirstSelectedOption().getText(); 
				System.out.println("option "+optionsoftimelimit);
			}

		}
		catch(Exception e)
		{

		}

		return selectoption;
	}


	public String verifyfuturepreventoption(String dropdownVaue) throws Exception
	{
		String selectoption=null;
		try
		{
			WebElement timedropdown=webdriver.findElement(By.xpath(OR.getProperty("preventfutureslotgamefor")));
			//timedropdown.clear();
			//timedropdown.click();
			Thread.sleep(500);
			//List<WebElement> optionsoftimelimit = webdriver.findElements(By.xpath(OR.getProperty("timelimitoptions")));
			Select optionsoftimelimit = new Select(timedropdown);
			System.out.println("select options"+optionsoftimelimit);
			optionsoftimelimit.selectByVisibleText(dropdownVaue);
			//optionsoftimelimit.selectByIndex(2);
			selectoption=optionsoftimelimit.getFirstSelectedOption().getText(); 
			System.out.println("option "+optionsoftimelimit);

		}
		catch(Exception e)
		{
			e.getMessage();
		}

		return selectoption;
	}


	public String verifyfuturepreventoption() throws Exception
	{
		String selectoption=null;
		try
		{
			WebElement timedropdown=webdriver.findElement(By.xpath(OR.getProperty("preventfutureslotgamefor")));
			//timedropdown.clear();
			//timedropdown.click();
			Thread.sleep(500);
			//List<WebElement> optionsoftimelimit = webdriver.findElements(By.xpath(OR.getProperty("timelimitoptions")));
			Select optionsoftimelimit = new Select(timedropdown);
			System.out.println("select options"+optionsoftimelimit);
			optionsoftimelimit.selectByIndex(1);
			//optionsoftimelimit.selectByIndex(2);
			selectoption=optionsoftimelimit.getFirstSelectedOption().getText(); 
			System.out.println("option "+optionsoftimelimit);

		}
		catch(Exception e)
		{
			e.getMessage();
		}

		return selectoption;
	}

	//verify refresh function 
	public String refreshpage()
	{
		//refresh page 
		String refresh=null;
		try 
		{
			verifytimedurationoption();
			verifyreminderperiodoption();
			WebElement losslimitinputbox=webdriver.findElement(By.xpath(OR.getProperty("lossLimitInput")));
			losslimitinputbox.sendKeys("100");
			//refresh page
			webdriver.navigate().refresh();
			Thread.sleep(1000);
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty("backtolobby")))); 
			WebElement backtolobby=webdriver.findElement(By.xpath(OR.getProperty("backtolobby")));
			refresh=backtolobby.getText();
		}
		catch(Exception e)
		{

		}
		return refresh;

	}


	/**
	 * Date:31/05/2018
	 * Author:Havish Jain
	 * This method is used to open the settings
	 * @return true
	 * @throws InterruptedException 
	 */
	public boolean settingsOpen() throws InterruptedException
	{	
		boolean flag=false;
		try
		{
			//in case of video poker game put locator of deal button

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("clock"))));
			//Clicking on hamburger menu
			//webdriver.context("NATIVE_APP");
			MobileElement el4 = (MobileElement) webdriver.findElement(By.xpath(xpathMap.get("OneDesign_Hamburger")));
			el4.click();
			//menuOpen();
			log.debug("Clicked on menu button ");
			Thread.sleep(2000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesign_SettingsID"))));
			//Clicking on settings link
			if(nativeClickByID(xpathMap.get("OneDesign_SettingsID")))
				{	
					flag=true;
					log.debug("Clicked on settigns button");
					Thread.sleep(1500);
				}
	//		MobileElement el5 = (MobileElement) webdriver.findElement(By.id(xpathMap.get("OneDesign_SettingsID")));
		//	el5.click();
			
			//webdriver.context("CHROMIUM");
		}
		catch(Exception e)
		{
			flag=false;
			log.error("error in settingsOpen()", e);
		}
		return flag;

	}
	/**
	 * Date:05-06-2018
	 * Name:Havish Jain
	 * Description: this function is used to Scroll the page vertically and to take the screenshot
	 * @throws Exception 
	 */
	public boolean func_SwipeDown(){
		try {
			String context =webdriver.getContext(); 
			webdriver.context("NATIVE_APP"); 
			Dimension size1 = webdriver.manage().window().getSize(); 
			int startx = size1.width / 2;
			String startyString=xpathMap.get("startY");
			String endyString=xpathMap.get("endY");
			double startyDouble=Double.parseDouble(startyString);
			double endyDouble=Double.parseDouble(endyString);
			int starty = (int) (size1.height * startyDouble); 
			int endy = (int) (size1.height* endyDouble);
			TouchAction action = new TouchAction(webdriver);
			action.press(PointOption.point(startx, starty)).moveTo(PointOption.point(startx, endy)).release().perform();
			//action.press(startx, starty).moveTo(startx, endy).release().perform();
			webdriver.context(context);
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	} 

	/*public void point()
	{
		Point p=new Point(1, 2);
		p.move(1200, 1200);
		Point d=p.getLocation();
	} */
	/**
	 * Date:10-1-2018
	 * Name:Premlata Mishra
	 * Description: this function is used to Scroll the page with Xpath and to take the screenshot
	 * @throws Exception 
	 */
	public boolean paytableOpen(Mobile_HTML_Report report) 
	{
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesignbetbuttonID"))));
			//Clicking on hamburger menu
			webdriver.context("NATIVE_APP");
			MobileElement el4 = (MobileElement) webdriver.findElement(By.id(xpathMap.get("OneDesign_HamburgerID")));
			el4.click();
			Thread.sleep(1000);
			//Clicking on paytable link
			MobileElement el5 = (MobileElement) webdriver.findElement(By.id(xpathMap.get("OneDesign_PaytableID")));
			el5.click();
			webdriver.context("CHROMIUM");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * *Author:Premlata
	 * This method is used to wait to come BIg win overlay  
	 */
	public boolean bigWin_Wait()
	{
		boolean bigWin=false;
		try
		{
			Wait = new WebDriverWait(webdriver, 5000);
			webdriver.context("NATIVE_APP");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OD_BIgWin_ID"))));
			webdriver.context("CHROMIUM");
			bigWin=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			bigWin=false;
		}
		return bigWin;
	}
	/**
	 * Date:07/12/2017
	 * Author:premlata Mishra
	 * This method is to wait till the game will load after refresh,and the it will click on new feature screen
	 * @return true
	 */
	public String refreshWait()
	{
		try
		{
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("spinButtonBox"))));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Date:07/12/2017
	 * Author:Premlata Mishra
	 * This method is to increase the bet
	 * @return true
	 */
	public boolean betIncrease(){
		try{
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test1=webdriver.findElements(By.xpath(xpathMap.get("OneDesignBetIncreament"))).size()>0;
			if(test1)
			{
				func_Click(xpathMap.get("OneDesignBetIncreament"));
			}
			else 
			{
				System.out.println("bet button is not displaying");
			}
			boolean test=webdriver.findElements(By.xpath(xpathMap.get("OneDesignBetbtnyes"))).size()>0;
			if(test){
				Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("OneDesignBetbtnyes"))));
				func_Click(xpathMap.get("OneDesignBetbtnyes"));
				Thread.sleep(2000);
			}
			else{
				System.out.println("yes button is not in the game");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * Date:07/12/2017
	 * Author:premlata mishra
	 * actually not necessery in component store just declaration needed
	 * @return true
	 */
	public void waitTillStop() throws InterruptedException{
		try{
			webdriver.context("NATIVE_APP");
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test=webdriver.findElements(By.xpath("//*[@id='respin-footer-4']")).size()>0;
			if(test){
				while(webdriver.findElement(By.xpath("//*[@id='respin-footer-4']")).getText().isEmpty()) {
					Thread.sleep(500);
				}
			}
			else
			{
				//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Spin_Button"))));
				Wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("M_SpinButton_ID"))));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	/**
	 * Date:10-1-2018
	 * Name:Havish Jain
	 * Description: this function is used to Scroll the page using cordinates and to take the screenshot
	 * @throws Exception 
	 */


	public void capturePaytableScreenshot(Mobile_HTML_Report report, String language) 
	{
		try
		{
					
			
			WebElement we = webdriver.findElement(By.id(xpathMap.get("OneDesign_HamburgerID")));
			clickWithWebElement(we, 0);
			//Clicking on paytable link
			WebElement we1 = webdriver.findElement(By.id(xpathMap.get("OneDesign_PaytableID")));
			clickWithWebElement(we1, 0);
			webdriver.context("CHROMIUM");
			Thread.sleep(3000);
			report.detailsAppendFolder("verify the paytable screen shot", " paytable first page screen shot", "paytable first page screenshot ", "pass", language);
			String scrollCount=xpathMap.get("paytableScrollCount");
			Double scrollCountdouble=Double.parseDouble(scrollCount);
			for(int i=1; i<=scrollCountdouble; i++){
				func_SwipeDown();
				Thread.sleep(2000);
				report.detailsAppendFolder("verify the paytable screen shot", " paytable next screen shot", "paytable next page screenshot ", "pass", language);
			}
			nativeClickByID(xpathMap.get("PaytableBackID"));
			Thread.sleep(1000);
		}
		catch (Exception e) {
			e.getMessage();	
		}
	}

	public String symbol_Value(String locator)	{
		String value=Get_Amount(locator);
		return value;
	}

	/**
	 * Date:07/12/2017
	 * Author:premlata Mishra
	 * This method is to get any element value
	 * @return true
	 */
	public String Get_Amount(String locator)
	{
		//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		String Value=func_GetText(locator);
		String Value1=func_String_Operation(Value);
		return Value1;
	}
	/*
	 * Date: 29/05/2018
	 * Author:Havish Jain  
	 * Description: To Close menu container  
	 * Parameter: NA
	 */	
	public boolean menuOpen(){
		boolean ret=false;
		try {
			//Clicking on hamburger menu
			nativeClickByID(xpathMap.get("OneDesign_HamburgerID"));
			log.debug("Clicked on menu button");
			Thread.sleep(2000);
			ret=true;
		} 
		catch (InterruptedException e) {
			log.error("error in openmenu()", e);
		} 
		return ret;
	}

	/*
	 * Date: 29/05/2018
	 * Author:Havish Jain  
	 * Description: To Close menu container  
	 * Parameter: NA
	 */	
	public boolean menuClose()
	{
		try{
			Thread.sleep(1000);
			menuOpen();
			log.debug("Clicked on menu button");
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			log.error("error in menuClose", e);
		} 
		return true;
	}

	/**
	 * ate:22/11/2017
	 * Author:Laxmikanth Kodam
	 * This method used to remove the $ symbol from credits balance 
	 * @param value
	 * @return
	 */
	public String func_String_Operation(String value){
		String str=value;
		String str1=str.substring(1);
		return str1;
	}

	/**
	 * Date:22/11/2017
	 * Author:Laxmikanth Kodam
	 * This method used to check the currency multiplier inside settings
	 * @return amount
	 * @throws InterruptedException
	 */
	@SuppressWarnings("deprecation")
	public double currencymultiplier(String nodeValue,String attribute1,String attribute2, String attribute3) throws InterruptedException{
		//WebElement element=webdriver.findElement(By.xpath("//*[@id='btnSpin']"));
		//webdriver.context("NATIVE_APP");
		func_Click("newFeaturePixels");
		//element.click();
		clickAt("newFeaturePixels");
		TouchAction tc = new TouchAction(webdriver);
		/*tc.tap(element).perform(); 
		tc.tap(element, 3, 3).perform();*/
		//tc.moveTo(element).perform();
		//webdriver.context("CHROMIUM");
		func_Click(xpathMap.get("spinButtonBox"));
		func_Click(xpathMap.get("Spin_Button"));
		func_SwipeLeft();
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("settings"))));
		func_Click(xpathMap.get("coinSize_Arrow"));
		func_Click(xpathMap.get("minCoinSize_Amount"));
		String minAmount=func_GetText(xpathMap.get("coinSize_Amount"));
		String value1=func_String_Operation(minAmount);
		Double amount=Double.parseDouble(value1);
		return amount;			
	}
	/**
	 * Date:12/08/2021
	 * This method used to enable address bar
	 * @return void
	 * @throws InterruptedException
	 */

	public void clickOnAddressBar()
	{
		Wait=new WebDriverWait(webdriver,50);	
		try {
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("clock_ID"))));
			if(osPlatform.equalsIgnoreCase("Android"))
			{
				String context=webdriver.getContext();
				webdriver.context("NATIVE_APP"); 
				Dimension size1 = webdriver.manage().window().getSize(); 
				int startx = size1.width / 2;
				int starty = size1.height / 2;
				TouchAction action = new TouchAction(webdriver);
				action.press(PointOption.point(0,0)).release().perform();
				webdriver.context(context);
			}
			else{//tapping at down on the screen for ios 15 and above version
				//Thread.sleep(1000);
				Dimension size = webdriver.manage().window().getSize();
				int anchor = (int) (size.width * 0.5);
				int startPoint = (int) (size.height * 0.2);
				int endPoint = (int) (size.height *0.97);
				new TouchAction(webdriver).tap(PointOption.point(anchor, endPoint)).perform();
				Thread.sleep(2000);
				/*TouchAction touchAction = new TouchAction(webdriver);
				touchAction.tap(PointOption.point(10, 110))
				.perform();*/
			}}catch(Exception e)
			{
			log.error(e.getMessage(),e);	
			}

	}

	/**
	 * Date:07/12/2017
	 * Author:Laxmikanth Kodam
	 * This method used to swipe the pages on the mobile screen
	 * @return true
	 * 
	 */
	public boolean func_SwipeLeft(){
		try {
			webdriver.context("NATIVE_APP"); 
			Dimension size1 = webdriver.manage().window().getSize(); 
			int endx = (int) (size1.width * 0.8); 
			int startx = (int) (size1.width * 0.20); 
			int starty = size1.height / 2; 
			//	webdriver.swipe(startx, starty, endx, starty, 500);
			webdriver.context("CHROMIUM");
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
		return true;
	}

	/**
	 * Date:07/12/2017
	 * Author:Laxmikanth Kodam
	 * This method used to swipe the pages on the mobile screen
	 * @return true
	 * 
	 */
	public boolean func_SwipeRight(){
		try {
			webdriver.context("NATIVE_APP"); 
			Dimension size1 = webdriver.manage().window().getSize(); 
			int endx = (int) (size1.width * 0.8); 
			int startx = (int) (size1.width * 0.20); 
			int starty = size1.height / 2; 
			//webdriver.swipe(endx, starty, startx, starty, 500);
			webdriver.context("CHROMIUM");
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
		return true;
	}

	public void swipeRightToLeft() {
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", 0.9);
		swipeObject.put("startY", 0.5);
		swipeObject.put("endX", 0.01);
		swipeObject.put("endY", 0.5);
		swipeObject.put("duration", 3.0);
		js.executeScript("mobile: swipe", swipeObject);
	}

	public  void swipeHorizontal(AppiumDriver driver, double startPercentage, double finalPercentage, double anchorPercentage, int duration) throws Exception {
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.height * anchorPercentage);
		int startPoint = (int) (size.width * startPercentage);
		int endPoint = (int) (size.width * finalPercentage);
		//	new TouchAction(driver).press(startPoint, anchor).waitAction(duration).moveTo(endPoint, anchor).release().perform();
		//In documentation they mention moveTo coordinates are relative to initial ones, but thats not happening. When it does we need to use the function below
		//new TouchAction(driver).press(startPoint, anchor).waitAction(duration).moveTo(endPoint-startPoint,0).release().perform();
	}

	/**
	 * Date:22/11/2017
	 * Author:Laxmikanth Kodam
	 * This method is used swipe the coins size from min to max coins sizes
	 * @return true
	 * @throws Exception
	 */

	public boolean swipeMinCoinSize(double minCoinSize) throws Exception {
		try{   

			List<WebElement> forms = webdriver.findElements(By.className("coinSize-cell"));
			int count = forms.size();
			func_Click(xpathMap.get("coinSize_Arrow"));
			for(int i=3;i<count;i++){
				WebElement current= webdriver.findElement(By.xpath("//*[@id='coinSize-content']/div["+ i +"]"));
				current.click();
				Thread.sleep(2000);
			}

		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	//function for check game play req
	/*
	 * Date: 03/04/2017 Author:
	 * Author: Dhairaya Gautam
	 * Description: This function used  for page load
	 * Parameter: NA
	 */
	public String isGamePlay()
	{
		String s=null;
		try
		{
			WebElement ele1 = Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='header']")));
			ele1 = webdriver.findElement(By.xpath("//*[@id='header']"));
			s=ele1.getText();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
	/*Click on quick spin toggle button*/
	public boolean QuickSpinclick()
	{
		try
		{
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("OneDesignQuickSpin"))));
			Native_Click(xpathMap.get("OneDesignQuickSpin"));
			//func_Click(XpathMap.get("OneDesignQuickSpin"));
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return true;
	}
	
	public boolean quickSpinclickWith()
	{
		Wait=new WebDriverWait(webdriver, 5000);
		try
		{
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("OneDesignQuickSpin"))));
			WebElement we = webdriver.findElement(By.xpath(xpathMap.get("OneDesignQuickSpin")));
			clickWithWebElement(we, 0);
			//func_Click(XpathMap.get("OneDesignQuickSpin"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
	
	public boolean quickSpinOn()
	{
		boolean flag = false;
		try {
			spinclick();
			//Thread.sleep(2000);
			if(!webdriver.findElement(By.xpath(xpathMap.get("OneDesign_Stop_Button"))).isDisplayed())
				flag = true;
			else
				flag = false;
			
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	
	
	/**
	 * Date: 04/06/2018
	 * Author: Havish Jain
	 * Description: This function is used for touch event in mobile devices 
	 * Parameter: By locator
	 */
	public boolean Native_Click(String locator)
	{
		boolean present;
		try{
			webdriver.context("NATIVE_APP");
			MobileElement el4 = (MobileElement) webdriver.findElement(By.xpath(locator));
			if(el4 != null)
			{   
				Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
				el4.click();
			}
			present=true;
			//el4.click();
			webdriver.context("CHROMIUM");			
		}
		catch(NoSuchElementException e)
		{
			present =false;
			System.out.println(e.getMessage());
		}
		return present;
	}
	/**
	 * Date: 04/06/2018
	 * Author: Havish Jain
	 * Description: This function is used for touch event in mobile devices 
	 * Parameter: By locator
	 */

	public boolean nativeClickByID(String locator)
	{
		boolean present=false;
		try{
			
			String context = webdriver.getContext();
					
			try
			{
			Wait.until(ExpectedConditions.elementToBeClickable(By.id(locator)));
			}
			catch(Exception e)
			{
				log.error("Unable to find"+locator,e);
				System.out.println("Unable to find the loactor : "+locator);
			}
			MobileElement element = (MobileElement) webdriver.findElement(By.id(locator));
			
			if(element != null)
			{
				if(osPlatform.equalsIgnoreCase("Android")){
					element.click();
					//clickWithWebElement(element, 0);
				}
				else {
					//for IOS device
					 element.click();
					//clickWithWebElement1(element, 0);
					//clickWithWebElement1(element, 0);
				}
				present=true;
			}
			webdriver.context(context);			
		}
		catch(NoSuchElementException e)
		{
			System.out.println(e.getMessage());
			present=false;
		}
		return present;
	}
	/**
	 * Date: 04/06/2018
	 * Author: Havish Jain
	 * Description: This function is used for touch event in mobile devices 
	 * Parameter: By locator
	 */

	public boolean Native_ClickByXpath(String locator)
	{
		boolean present=false;
		try{
			//webdriver.context("NATIVE_APP");
			
			MobileElement el4 = (MobileElement) webdriver.findElement(By.xpath(locator));
			if(el4 != null)
			{
				el4.click();
				present=true;
			}
			//webdriver.context("CHROMIUM");			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			present=false;
		}
		return present;
	}
	/*
	 * Date: 03/04/2017 Author:
	 * Author: Anish
	 * Description: This function used  for page load
	 * Parameter: NA
	 */
	public String verifyCreditsValue(){
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("clock_ID"))));
		String aval=webdriver.findElement(By.xpath(xpathMap.get("OneDesign_Credit_Balance"))).getText();
		//String aval=func_GetText(XpathMap.get("M_creditbalance"));

		String val=func_String_Operation(aval);
		//double val1=Double.parseDouble(val);
		return val;
	}
	/**
	 * Author Lamxikanth Kodam
	 * Common added for logout()
	 * This method is common logout function for the component store 
	 * @return
	 */
	public String Func_logout_OD() {
		String loginTitle = null;
		try{
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesign_HamburgerID"))));
		nativeClickByID(xpathMap.get("OneDesign_HamburgerID"));
		Thread.sleep(2000);
		nativeClickByID(xpathMap.get("M_OneDesign_HomeIconID"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("five_Reel_slot"))));
		loginTitle=func_GetText(xpathMap.get("five_Reel_slot"));
		nativeClickByID(xpathMap.get("M_navigation_MyAccountID"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("logout"))));
		
		JavascriptExecutor js = ((JavascriptExecutor)webdriver);
		js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.id(xpathMap.get("logout_ID"))));

		js.executeScript("arguments[0].click();", webdriver.findElement(By.id(xpathMap.get("logout_ID"))));
		//Native_ClickByID(XpathMap.get("logout_ID"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("Login"))));
		//func_Click(XpathMap.get("closeButtonLogin"));
		nativeClickByID(xpathMap.get("closeButtonLoginID"));
		}
		catch(Exception e){
			e.getMessage();
		}
		return loginTitle;
	}

	/**
	 * Date:25/06/2018
	 * Author:Premlata Mishra
	 * This method is used to open the settings
	 * @return true
	 */
	public boolean verifyQuickSpin(String imagepath)  {
		boolean test=false;
		try{
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			test = webdriver.findElement(By.xpath(xpathMap.get("QuickSpin"))).isDisplayed();
			if(test)
			{
				return test=true;
			}
		}
		catch(Exception e)			{
			return test=false;
		}
		return test;
	}
	/*
	 * Date: 11/04/2017 
	 * Author:Premlata Mishra
	 * Description: This function verifies the working of the TOGGLE TOPBAR. 
	 * Parameter: imagepath
	 */
	String  topbarData;
	public String verifyToggleTopbar() {

		try {
			Thread.sleep(1000);
			nativeClickByID(xpathMap.get("Toggle_TopBar_ID"));
			webdriver.switchTo().frame(webdriver.findElement(By.name("commonUIIFrame")));

			String stake=webdriver.findElement(By.id("stake")).getText()+"!";
			//String stake=webdriver.findElement(By.id(XpathMap.get("stake_ID")));
			String paid=webdriver.findElement(By.id("paid")).getText()+"!";

			String balance=webdriver.findElement(By.id("balance")).getText()+"!";
			System.out.println("stake= "+stake);
			System.out.println("paid= "+paid); 
			System.out.println("balance= "+balance);
			topbarData=stake+paid+balance;
			System.out.println(topbarData);
			//Native_ClickByID(XpathMap.get("Toggle_TopBar_ID"));

		} catch (Exception e) { 
			e.printStackTrace();

		}
		return topbarData;
	}	
	public boolean open_Autoplay() {
		boolean b = false;
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSpin")));
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			WebElement we = webdriver.findElement(By.id(xpathMap.get("AutoplayID")));
			//clickWithWebElement(we, 0);
			nativeClickByID(xpathMap.get("AutoplayID"));
			//log.debug("Clicked on autoplay button");
			Thread.sleep(2000);
			b = true;		
		}
		catch(Exception e){
			log.error("error in open_Autoplay()",e);
		}		
		return b;
	}
	public void close_Autoplay(){
		try{
		Thread.sleep(500);
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Autoplay_OverlayID"))));
		open_Autoplay();
		Thread.sleep(500);
		}
		catch(Exception e){
			e.getMessage();
		}
	}		
	/**
	 * Date:07/12/2017
	 * Author:premlata mishra
	 * This method is to get win amount
	 * @return true
	 */
	public String getWinAmt()
	{
		//func_Click(XpathMap.get("OneDesign_Credit_Balance"));
		String Winamt1=null;
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Spin_Button_ID"))));
			boolean win=webdriver.findElement(By.id(xpathMap.get("OneDesignWinTextID"))).isDisplayed();
			if(win)
			{
				String Winamt=webdriver.findElement(By.id(xpathMap.get("OneDesignWinTextID"))).getText();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.getMessage();
				}
				if(!Winamt.isEmpty())
					Winamt1 = func_String_Operation(Winamt);
				System.out.println("win amount"+Winamt1);
			}
			else
			{
				Winamt1="0.0";
			}
		}
		catch(Exception e)
		{
			log.error("error to get win amount text", e);
			Winamt1="0.0";
		}
		return Winamt1;
	}
	/**
	 * Date:07/12/2017
	 * Author:premlata Mishra
	 * This method is to change the bet
	 * @return true
	 */
	public String getBetAmt()
	{
		String CreditVal=null;
		try
		{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("betbutton_ID"))));
			String betVal=webdriver.findElement(By.id(xpathMap.get("betbutton_ID"))).getText(); 
			CreditVal=func_String_Operation(betVal);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return CreditVal;
	}
	public String verifyFlag()
	{
		boolean test = false;
		String 	flag=null;
		try
		{
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			test = webdriver.findElement(By.id(xpathMap.get("Denmark_FlagID"))).isDisplayed();
			if (test){
				nativeClickByID(xpathMap.get("Denmark_FlagID"));
				flag=webdriver.findElement(By.id(xpathMap.get("Denmark_FlagID"))).getText();
			}			
		}
		catch (Exception e) {
			flag=null;
		}
		return flag;
	}

	/*
	 * Date: 03/04/2017 Author:
	 * Author: Dhairaya Gautam
	 * Description: This function used  for page load
	 * Parameter: NA
	 */
	public String verifyResponsibleLinkworking()
	{
		String responsible_Gaming=webdriver.findElement(By.id("responsibleGaming")).getText();
		nativeClickByID("responsibleGaming");
		return responsible_Gaming;
	}

	/*
	 * Date: 03/04/2017 Author:
	 * Author: Dhairaya Gautam
	 * Description: This function used  for page load
	 * Parameter: NA
	 */
	public String clickamount(Mobile_HTML_Report italy,String amount){
		String value=null;
		try{
			//System.out.println(s);
			Thread.sleep(1000);
			webdriver.findElement(By.id("userInput")).sendKeys(amount);
			Thread.sleep(3000);
			nativeClickByID(xpathMap.get("italySubmit_ID"));
			Thread.sleep(3000);
			italy.detailsAppend(" Verify that Take to Game screen appears ", " Take to Game screen should appear "," Take to Game screen is appearing ", "Pass");
			nativeClickByID(xpathMap.get("italySubmit_ID"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("clock_ID"))));
			//value=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return amount;
	}

	/**
	 * Date:22/11/2017
	 * Author:Laxmikanth Kodam
	 * This method is used swipe the coins per line/coins bet from min to max coins sizes
	 * @return true
	 * @throws Exception
	 */
	@Override
	public boolean swipeMinBetSize(double minBetSize)  {
		try{   

			List<WebElement> forms = webdriver.findElements(By.className("coinSize-cell"));
			int count = forms.size();
			func_Click(xpathMap.get(("coinSize_Arrow")));
			for(int i=3;i<count;i++){
				WebElement current= webdriver.findElement(By.xpath("//*[@id='coinSize-content']/div["+ i +"]"));
				current.click();
				Thread.sleep(2000);
			}
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	/**
	 * Date:22/11/2017
	 * Author:Laxmikanth Kodam
	 * This method is used swipe the coins per line/coins bet  from min to max coins sizes
	 * @return true
	 * @throws Exception
	 */
	public boolean swipeMaxBetSize(double maxBetSize) throws Exception {
		try{   
			List<WebElement> forms = webdriver.findElements(By.className("coinsPerLine-cell"));
			int count = forms.size();
			func_Click(xpathMap.get(("coinsPerLine_Arrow")));
			for(int i=0;i<=count;i++){
				WebElement current= webdriver.findElement(By.xpath("//*[@id='coinsPerLine-content']/div["+ i +"]"));
				current.click();
				Thread.sleep(2000);
			}
		}
		catch(Exception e){
			return false;
		}
		return true;
	}

	//********************************Exception Handler********************************************************//
	public void evalException(Exception ex) throws Exception
	{
		//System.out.println("*************In Exception Handling Class*************"+ex.getClass());
		//ex.printStackTrace();
		//repo1.details_append( "Execution Interrupted because of exception" , "" , "", "Interrupted");
		System.out.println("value of ex is "+ex);
		String exClass=ex.getClass().toString();
		//ex.printStackTrace();
		if(exClass.contains("StaleElementReferenceException"))
		{
			//System.out.println("Identified specific exception "+exClass);
			//System.out.println();
			repo1.detailsAppend( "Execution Interrupted because of StaleElementReferenceException" , "" , "", "Interrupted");
			webdriver.close();
		}
		else if(exClass.contains("NoSuchElementException"))
		{
			String gameplay=funGamePlayError();

			if(gameplay!=null)
			{
				repo1.detailsAppend( gameplay+" error occurred" , "" , "", "Interrupted");
				//System.out.println("Identified specific exception "+exClass);
			}
			else
			{
				repo1.detailsAppend("NoSuchElementException because of Element not found" , "" , "", "Interrupted");
			}
			webdriver.close();
		}
		else if(exClass.contains("InvalidElementStateException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of InvalidElementStateException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
			webdriver.close();
		}
		else if(exClass.contains("ElementNotVisibleException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of ElementNotVisibleException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
			webdriver.close();
		}
		else if(exClass.contains("ErrorInResponseException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of ErrorInResponseException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("InvalidSwitchToTargetException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of InvalidSwitchToTargetException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("NoSuchFrameException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of NoSuchFrameException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("NoSuchWindowException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of NoSuchWindowException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("NoSuchAttributeException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of NoSuchAttributeException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("NoAlertPresentException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of NoAlertPresentException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("ElementNotSelectableException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of ElementNotSelectableException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("InvalidCookieDomainException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of InvalidCookieDomainException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("UnableToSetCookieException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of UnableToSetCookieException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("RemoteDriverServerException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of RemoteDriverServerException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("TimeoutException"))
		{

			repo1.detailsAppend( "Execution Interrupted because of TimeoutException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("MoveTargetOutOfBoundsException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of MoveTargetOutOfBoundsException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("UnexpectedTagNameException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of UnexpectedTagNameException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("InvalidSelectorException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of InvalidSelectorException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("ImeNotAvailableException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of ImeNotAvailableException" , "" , "", "Interrupted");
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("ImeActivationFailedException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of ImeActivationFailedException" , "" , "", "Interrupted"); 
			//System.out.println("Identified specific exception "+exClass);
		}
		else if(exClass.contains("UnhandledAlertException"))
		{
			repo1.detailsAppend( "Execution Interrupted because of Unhandled message ALERT" , "" , "", "Interrupted");
			Thread.sleep(3000);
			//System.out.println("Identified specific exception "+exClass);

			//   Alert alert = null;
			//   try {
			//	    //System.out.println("Alert found"+((TargetLocator) driver).alert().getText());
			//	    alert = driver.switchTo().alert();
			//   } catch (Exception e) {
			//	    e.printStackTrace();
			//	    System.out.println("Alert found"+alert.getText());
			//    
			//    
			//   }
			//   // Get the text of the alert or prompt
			//    
			//   //System.out.println("Alert found");
			//   
			//   
			//   // And acknowledge the alert (equivalent to clicking "OK")
			//   alert.accept(); 
		} 
		else if(exClass.contains("NullPointerException"))
		{

			String gameplay=funGamePlayError();

			if(gameplay!=null)
			{
				repo1.detailsAppend( gameplay+" error occurred" , "" , "", "Interrupted");
				//System.out.println("Identified specific exception "+exClass);
			}
			else
			{
				repo1.detailsAppend("NoSuchElementException because of Element not found" , "" , "", "Interrupted");
			}


		}
	}
	/**
	 * *Author:Havish Jain
	 * This method is used to get currency symbol from credit balance
	 * @throws InterruptedException 
	 */
	public String getCurrencySymbol() {
		String currencySymbol=null;
		try
		{
			String balance = func_GetText(xpathMap.get("creditBalance"));
			currencySymbol = balance.replaceAll("[[0-9],.\\s]", "");
		}
		catch (Exception e) {
			e.getMessage();
		}
		return currencySymbol;
	}
	
	/**
	 * *Author:Havish Jain
	 * This method is used to get bet from the game without currency symbol, comma and dot.
	 * @throws InterruptedException 
	 */
	public String getCurrentBet(){
		String betValue = func_GetText(xpathMap.get("betValue"));
		String bet = betValue.replaceAll("[^0-9]","");
		log.debug("verifying multiplier ");
		return bet;
	}
	/**
	 * *Author:Havish Jain
	 * This method is used to verify currency symbol in bet
	 * @throws InterruptedException 
	 */
	public boolean betCurrencySymbol(String currency) {
		try{
		String bet = func_GetText(xpathMap.get("betValue"));
		log.debug("Getting currency symbol from game");
		if(bet.indexOf(currency)>=0)
		{
			return true;
		}
		if(currency==null || currency.equals(""))
		{
			return true;
		}
		}
		catch(Exception e)
		{
			log.error("error in currency symbol", e);;
		}
		return false;
	}
	
	/**
	 * *Author:Havish Jain
	 * This method is used to verify currency symbol in bet setting screen
	 * @throws InterruptedException 
	 */
	public boolean betSettingCurrencySymbol(String currency,Mobile_HTML_Report report) {
		String totalBet = func_GetText(xpathMap.get("OneDesigntotalbet"));
		if(totalBet.indexOf(currency)<0)
		{
			return true;
		}
		if((currency==null || currency.equals("")))
		{
			return true;
		}
		return false;
	}

	/**
	 * *Author:Havish Jain
	 * This method is used to wait till win amount occur in free spin 
	 * @throws InterruptedException 
	 */
	public boolean waitforWinAmount(Mobile_HTML_Report currency, String CurrencyName) {
		Wait=new WebDriverWait(webdriver,180);
		boolean b =false;
		String win = "";
		try {	
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FSWinOverlayID"))));
			currency.detailsAppendFolder("Verify win overlay in Free Spins", "Win Overlay should display above the reel container", "Win Overlay is displaying", "Pass", CurrencyName);
			while(true){
				win = func_GetText(xpathMap.get("FSWinValue"));									
				if(!win.isEmpty())
				{
					b = true;
					break; 
				}
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public void jackpotSummary(Mobile_HTML_Report report, String language) {
		Wait=new WebDriverWait(webdriver,500); 

		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("JackpotSummaryID"))));
			Thread.sleep(1000);
			report.detailsAppendFolder("Verify language on Jackpot Summary Screen", "Jackpot Summary Screen should display", "Jackpot Summary Screen displays", "Pass", language);
			nativeClickByID(xpathMap.get("BackToGameID"));			
			Thread.sleep(1500);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public boolean freeGamesEntryScreen() {
		Wait=new WebDriverWait(webdriver,500); 
		boolean str =false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesPlayNow"))));
			Thread.sleep(1000);			
			String text = webdriver.findElement(By.id(xpathMap.get("FreeGamesPlayNow"))).getText();
			if(text!=null)
				str=true;
			Thread.sleep(1500);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}	
	
	public void clickBaseSceneDiscard() {
		try {
			if(webdriver.findElements(By.id(xpathMap.get("FreeGamesBaseSceneDiscard"))).size()>0){
				nativeClickByID(xpathMap.get("FreeGamesBaseSceneDiscard"));
				Thread.sleep(1500);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}	
	} 

	public boolean freeGameEntryInfo() {
		Wait=new WebDriverWait(webdriver,20); 
		boolean b = false;
		try {
			b=false;
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesPlayNow"))));
			//nativeClickByID(xpathMap.get("FreeGamesEntryInfoIcon"));
			webdriver.findElement(By.id(xpathMap.get("FreeGamesEntryInfoIcon"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesInfoDetails"))));		
			b = true;
			Thread.sleep(1500);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean clickPlayNow() {
		Wait=new WebDriverWait(webdriver,500); 
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesPlayNow"))));
			nativeClickByID(xpathMap.get("FreeGamesPlayNow"));
			//webdriver.findElement(By.id(XpathMap.get("FreeGamesPlayNow"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesBaseSceneDiscard"))));
			b = true;
			Thread.sleep(1000);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	/* Author:Havish
	 * This method is used to click on Next Offer button
	 * @throws InterruptedException 
	 */
	public void clickNextOffer() {
		try {
			if(webdriver.findElements(By.id(xpathMap.get("FreeGamesNextOffer"))).size()>0){
				nativeClickByID(xpathMap.get("FreeGamesNextOffer"));
				Thread.sleep(1500);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}	
	} 

	public String freeGamesResumescreen() {
		Wait=new WebDriverWait(webdriver,500); 
		String str = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesResumeButton"))));
			Thread.sleep(1000);
			str = webdriver.findElement(By.id(xpathMap.get("FreeGamesResumeButton"))).getText();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public boolean freeGameResumeInfo() {
		Wait=new WebDriverWait(webdriver,500); 
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesResumeButton"))));
			nativeClickByID(xpathMap.get("FreeGamesResumeInfoIcon"));
			//webdriver.findElement(By.id(XpathMap.get("FreeGamesResumeInfoIcon"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesResumeInfoDetails"))));
			//nativeClickByID(xpathMap.get("FreeGamesResumeInfoDetails"));
			b = true;
			Thread.sleep(1500);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean resumeScreenDiscardClick() {
		Wait=new WebDriverWait(webdriver,500); 
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesResumeButton"))));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesResumeDiscard"))));
			nativeClickByID(xpathMap.get("FreeGamesResumeDiscard"));

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesKeepItButton"))));
			b = true;
			Thread.sleep(1500);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean confirmDiscardOffer() {
		Wait=new WebDriverWait(webdriver,500); 
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesDiscardButton"))));
			nativeClickByID(xpathMap.get("FreeGamesDiscardButton"));
			//webdriver.findElement(By.id(XpathMap.get("FreeGamesDiscardButton"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesBackToGames"))));
			Thread.sleep(1500);
			b = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean freeGamesExpriyScreen() {
		Wait=new WebDriverWait(webdriver,500); 
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("FreeGamesExpiredContinue"))));
			b = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public String freeGamesContinueExpiry() {
		Wait=new WebDriverWait(webdriver,50); 
		try {
			webdriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("clock_ID"))));
			if(webdriver.findElements(By.id(xpathMap.get("FreeGamesExpiredContinue"))).size()>0){
				nativeClickByID(xpathMap.get("FreeGamesExpiredContinue"));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean checkAvilability(String string) {
		boolean result=false;
		try {
			webdriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			if(!webdriver.findElements(By.xpath(string)).isEmpty()){
				result= true;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
	/*This method will wait for element to be visible
	 * Date:11/02/2021
	 * @author:sg56207
	@param 
	@return 
	@throws 
	*/
		public void waitFor(String locator) {
		Wait=new WebDriverWait(webdriver,60);
		try{
			log.debug("Waiting For element to visible");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		}
		catch(Exception e){
			log.error("error while waiting for element :"+locator, e);
		}
	}
		public boolean drawCollectBaseGame(Mobile_HTML_Report report,String languageCode) throws InterruptedException
		{
		    double creditBalBeforeWin;
			double creditBalAfterWin;
			WebDriverWait wait;
			WebElement winValue;
			double win;
			double bet;			
			boolean flag = false;
			try
			{
			wait = new WebDriverWait(webdriver, 200);	
			
			//********Collecting credit balance before win
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("collectBtn"))));
			creditBalBeforeWin = getCreditAmtDouble();
			bet = getBetAmtDouble();		
			log.debug("Successfully captured credit balance before win");
			Thread.sleep(1000);
			
			
			//*********Collecting win value
			winValue = webdriver.findElement(By.xpath(xpathMap.get("winValue")));
			Thread.sleep(1000);
		
			String	strWinValue = winValue.getText().replaceAll("[^.0-9]", ""); 
			
			win = Double.parseDouble(strWinValue.replace(",",""));
			
			
			//***********Clicking on collect button on double to screen
			nativeClickByID(xpathMap.get("collectBtn"));
			Thread.sleep(2000);		
			
			//*************Capturing credit balance after collecting win
			creditBalAfterWin = getCreditAmtDouble();
			creditBalBeforeWin = (creditBalBeforeWin + win) ;
			if(Double.compare(creditBalBeforeWin,creditBalAfterWin) == 0)
			{
				if(languageCode!=null)
				{
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality", "On Clicking deal and draw button" ,"Credit balance should get updated"+
					"Credit balance is updated successfully :- Credit balance before win - "+creditBalBeforeWin+" Win amount - "+win+" Credit balance after win - "+creditBalAfterWin, "Pass", languageCode);
					funcLandscape();
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality", "On Clicking deal and draw button" ,"Credit balance should get updated"+
					"Credit balance is updated successfully :- Credit balance before win - "+creditBalBeforeWin+" Win amount - "+win+" Credit balance after win - "+creditBalAfterWin, "Pass", languageCode);
					funcPortrait();
					
				}
				
				else
				{
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality", "On Clicking deal and draw button" ,"Credit balance should get updated"+
							"Credit balance is updated successfully :- Credit balance before win - "+creditBalBeforeWin+" Win amount - "+win+" Credit balance after win - "+creditBalAfterWin, "Fail", languageCode);
							
				}
			}
			else
			{
				if(languageCode!=null)
				{
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality", "On Clicking deal and draw button ,credit balance should get updated", "Credit balance is not updated", "Fail", languageCode);
				}
				else
				{
					report.detailsAppend("Verify Deal, Draw and Collect functionality", "On Clicking deal and draw button ,credit balance should get updated", "Credit balance is not updated", "Fail");
				}
			}
			
			
			}
			catch(Exception e)
			{
				log.error("Error in collecting win on base game");
			}
			return flag;
		}
		
		public void doubleToCollect(Mobile_HTML_Report report) throws Exception
		{
			WebDriverWait wait;
			WebElement winValue;
			double win=0.0;
			double creditBalBeforeWin=0.0;
			double creditBalAfterWin=0.0;
			boolean flag = false;

			try{
				wait = new WebDriverWait(webdriver, 200);

				//*********Clicking on Deal and Draw button
				if(dealClick())
				{
					if(drawClick())
					{
						Thread.sleep(2000);

						//********Collecting credit balance before win
						creditBalBeforeWin = getCreditAmtDouble();	
						log.debug("Credit balance before win - "+ creditBalBeforeWin);

						//*******Clicking on double to button

						wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("double"))));
						nativeClickByID(xpathMap.get("double"));
						Thread.sleep(3000);

						//**************Waiting for navigation on double to screen
						wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("doubleSection"))));
						wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("doubleCard"))));
						report.detailsAppend("Verify double and collect functionality", "On Clicking double button ,double to screen should be open ", "Double to screen is open", "Pass");

						nativeClickByID(xpathMap.get("doubleCard"));
						nativeClickByID(xpathMap.get("moduleContent"));
						Thread.sleep(3000);	

						//************Collecting win amount
						winValue = webdriver.findElement(By.xpath(xpathMap.get("doubleWinAmt")));
						win = Double.parseDouble(winValue.getText().substring(1).replace(",",""));

						//***********Clicking on collect button on double to screen
						nativeClickByID(xpathMap.get("collectBtn"));
						wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("deal"))));

						//*************Capturing credit balance after collecting win
						creditBalAfterWin = getCreditAmtDouble();			
						log.debug("Credit balance after win - "+creditBalAfterWin);
						creditBalBeforeWin = creditBalBeforeWin + win;
						log.debug("Balance after adding win "+creditBalBeforeWin);

						//*************Comparing Credit balance before win and after win
						if(Double.compare(creditBalBeforeWin,creditBalAfterWin)== 0)
						{
							report.detailsAppend("Verify double and collect functionality", 
									"On Clicking double button ,double to screen open and collecting win amount and balace should get updated", 
									"Credit balance before win - "+creditBalBeforeWin+" Win amount - "+win+" Credit balance after win - "+creditBalAfterWin, "Pass");
						}
						else
						{

							report.detailsAppend("Verify double and collect functionality", "On Clicking double button ,double to screen open and collecting win amount and balace should get updated", 
									"Credit balance before win - "+creditBalBeforeWin+" Win amount - "+win+" Credit balance after win - "+creditBalAfterWin, "Fail");
						}
						log.debug("Credit balance after win is correctly updated. Win amount  "+win);
					}
					else
					{
						log.error("Error in Draw functionality ");
						report.detailsAppend("Verify double and collect functionality", "On Clicking double button ,double to screen open and collecting win amount and balace should get updated", 
								"Error in Deal functionality ", "Fail");
					}
				}else
				{
					log.error("Error in Deal functionality ");
					report.detailsAppend("Verify double and collect functionality", "On Clicking double button ,double to screen open and collecting win amount and balace should get updated", 
							"Error in Draw functionality ", "Fail");
				}
			}
			catch(Exception e)
			{
				log.error("Error while collecting win amount on double to screen");
				report.detailsAppend("Verify double and collect functionality", "On Clicking double button ,double to screen open and collecting win amount and balace should get updated", 
						"Credit balance before win - "+creditBalBeforeWin+" Win amount - "+win+" Credit balance after win - "+creditBalAfterWin, "Fail");
			
			}
			


		}
		
		public boolean waitForWin() 
		{
			boolean flag = false;
			Wait = new WebDriverWait(webdriver, 500);
			try
			{
			spinclick();
			webdriver.findElement(By.xpath(xpathMap.get("reelGrid"))).click();
			Thread.sleep(2000);
			if(webdriver.findElement(By.xpath(xpathMap.get("txtInfoBarWin"))).isDisplayed())
				flag = true;
			else
				flag = false;		
			}
			catch (Exception e) {
				System.out.println(e.getStackTrace());
				flag = false;
			}
			
			return flag;
			
		}
		
		public double getBetAmtDouble()
		{
			
			String betVal;
			double betBal=0.0;
			try
			{
				betVal = webdriver.findElement(By.id(xpathMap.get("betBal"))).getText();
				betBal = Double.parseDouble(betVal.replaceAll("[^.0-9]", ""));
			}
			catch(Exception e)
			{
				log.error("Error in fetching bet - "+e);
				return betBal;
			}
			return betBal;
			
		}
		public double getCreditAmtDouble()
		{
			String creditBal;
			double creditBalance=0.0;
			try
			{
				
			creditBal = webdriver.findElement(By.id(xpathMap.get("creditBal"))).getText();
			creditBalance = Double.parseDouble(creditBal.replaceAll("[^.0-9]", ""));
			
			}
			catch(Exception e)
			{
				log.error("Error in fetching credit balance - "+e);
				return creditBalance;
			}
			return creditBalance;
		}
		
		public boolean drawClick() throws InterruptedException
		{
			WebDriverWait wait;
			boolean flag=false;
			
			try
			{
			wait = new WebDriverWait(webdriver, 20);
			//*********Clicking on Deal and Draw button
			log.debug("Finding draw element on the page");
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("draw"))));
			System.out.println("draw button visible");
			nativeClickByID(xpathMap.get("draw"));
			System.out.println("draw button click");
			
			log.debug("Successfully clicked on draw button");
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("collectBtn"))));
			
			//**********Clicking on module content to stop win counting
			nativeClickByID("moduleContent");
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("collectBtn"))));
			flag = true;
				
			}
			catch(Exception e)
			{
				log.error("Error while clicking on Draw button" , e);
			}
			return flag;
		}
			
			
		
		public boolean verifyPaytablePresent()
		{
	       WebDriverWait wait;
	       boolean flag = false;
			
			try
			{
			wait = new WebDriverWait(webdriver, 20);		
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("deal"))));
			if(webdriver.findElement(By.id(xpathMap.get("paytableContainer"))).isDisplayed())
				flag = true;
			else
				flag = false;
				log.debug(flag);
			}
			catch(Exception e)
			{
				log.error("Error in paytable display");
			}
			return flag;
		}
		
		public void moveCoinSizeSliderByXpath()
		{
			try{
				
				Thread.sleep(1000);
				nativeClickByID(xpathMap.get("mobileBetContainerID"));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("OneDesignCoinSizeSlider"))));
				WebElement e=webdriver.findElement(By.xpath("OneDesignCoinSizeSlider"));
				//clickWithWebElement(e,0);
				Actions action = new Actions(webdriver);   
				action.dragAndDropBy(e, 127, 0).build().perform();
			}catch(Exception e)
			{
				log.error(e.getMessage());
			}
		}
		
		public boolean dealClick() throws InterruptedException
		{
			
	       WebDriverWait wait;
		   boolean flag = false;
			try
			{
			log.debug(webdriver.getContextHandles());
			wait = new WebDriverWait(webdriver, 20);
			//*********Clicking on Deal and Draw button
			log.debug("Finding Deal element on the page");
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("deal"))));
			System.out.println("Deal button visible");
			nativeClickByID(xpathMap.get("deal"));
			System.out.println("Deal button click");
			log.debug("Successfully clicked on Deal button");
			log.debug("Finding draw button on the page");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("draw"))));
				flag = true;
			
			}
			catch(Exception e)
			{
				log.error("Error while clicking on Deal button" , e);
			}
			return flag;
		}
		
		public void doubleToGambleReached(Mobile_HTML_Report report, String languageCode) throws InterruptedException
		{
			
			WebDriverWait wait;
			WebElement winValue;
			double win;
		
			try
			{
			
			wait = new WebDriverWait(webdriver, 60);
			/*wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("deal"))));
			
			//*********Clicking on Deal and Draw button
			dealClick();
			drawClick();
			Thread.sleep(2000);*/
			
			//Paytable Close functionality for 50 play
			/*if(webdriver.findElements(By.id(xpathMap.get("PaytableClose"))).size()!=0)
			{
				webdriver.findElement(By.id(xpathMap.get("PaytableClose"))).click();
			}*/
			
			do
			{
				//*********Clicking on Double button
				wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("doubleBtn"))));
				nativeClickByID(xpathMap.get("doubleBtn"));
				wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("doubleSection"))));
				Thread.sleep(2000);		
				
				//***************Click on card
				wait.until(ExpectedConditions.attributeContains(webdriver.findElement(By.xpath(xpathMap.get("doubleCardFirst"))), "class", "vp card quick-transition"));
				nativeClickByID(xpathMap.get("doubleCard"));
				Thread.sleep(1000);
				
				nativeClickByID(xpathMap.get("moduleContent"));
				Thread.sleep(2000);	
				
				report.detailsAppendFolder("Verify double to functionality", "Double to functionality", "Double to functionality", "Pass", languageCode);
				funcLandscape();
				Thread.sleep(1000);
				report.detailsAppendFolder("Verify double to functionality", "Double to functionality", "Double to functionality", "Pass", languageCode);
				funcPortrait();
				
			}while(!(webdriver.findElement(By.xpath(xpathMap.get("gambleLimit"))).isDisplayed()));
			
							
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("backToGame"))));
			Thread.sleep(2000);
			if(webdriver.findElement(By.id(xpathMap.get("backToGame"))).isDisplayed())
			{
				
				report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality", "Gamble Reached text should be displayed", "Pass", languageCode);
				funcLandscape();
				report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality", "Gamble Reached text should be displayed", "Pass", languageCode);
				funcPortrait();
				
			}	
				else
				{
					report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality", "Gamble Reached text is not displayed", "Fail", languageCode);
				}
			Thread.sleep(1000);
			nativeClickByID(xpathMap.get("backToGame"));
			Thread.sleep(1000);
			}
			catch(Exception e)
			{
				log.error("Error in double to feature - "+e);
				try {
					report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality", "Gamble Reached text is not displayed", "Fail", languageCode);
				} catch (Exception e1) {
					log.error("Error in double to feature - "+e);
				}

			}
			
		}
		
		
		public void paytableClickVideoPoker(Mobile_HTML_Report report, String languageCode)
		{
			WebDriverWait wait;
			
			try
			{
			
			wait = new WebDriverWait(webdriver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("paytableContainer"))));
			
			for(int i = 1; i<=5;i++)
			{
				nativeClickByID(xpathMap.get("paytableCoinsDisplay"));
				report.detailsAppendFolder("Verify paytable functionality", "paytable functionality", "paytable functionality page"+i, "Pass", languageCode);
				funcLandscape();
				report.detailsAppendFolder("Verify paytable functionality", "paytable functionality", "paytable functionality page"+i, "Pass", languageCode);
				funcPortrait();
			}
			
			}
			catch(Exception e)
			{
				log.error("Error in paytable feature - "+e);
				try {
					report.detailsAppendFolder("Verify paytable functionality", "paytable functionality", "paytable functionality page", "Fail", languageCode);
				} catch (Exception e1) {
					log.error("Error in double to feature - "+e);
				}
			}
		}
		

		
		/**
		 * Date:10-1-2018
		 * Name:Havish Jain
		 * Description: this function is used to Scroll the page using cordinates and to take the screenshot
		 * @throws Exception 
		 */


		public void paytableOpenScroll(Mobile_HTML_Report report, String language) 
		{
			try
			{
				//webdriver.context("NATIVE_APP");
				System.out.println(""+webdriver.getContext());
				System.out.println(webdriver.getContextHandles());
				MobileElement el4 = (MobileElement) webdriver.findElement(By.id(xpathMap.get("OneDesign_HamburgerID")));
				//el4.click();
				nativeClickByID(xpathMap.get("OneDesign_HamburgerID"));
				
				//Clicking on paytable link
				//MobileElement el5 = (MobileElement) webdriver.findElement(By.id(xpathMap.get("OneDesign_PaytableID")));
				//el5.click();
				
				System.out.println(""+webdriver.getContext());
				nativeClickByID(xpathMap.get("OneDesign_PaytableID"));
				//webdriver.context("CHROMIUM");
				Thread.sleep(3000);
				//report.detailsAppendFolder("verify the paytable screen shot", " paytable first page screen shot", "paytable first page screenshot ", "pass", language);
				//funcLandscape();
				//Thread.sleep(2000);
				//report.detailsAppendFolder("verify the paytable screen shot", " paytable next screen shot", "paytable next page screenshot ", "pass", language);
				//funcPortrait();
				Thread.sleep(2000);
				String scrollCount=xpathMap.get("paytableScrollCount");
				Double scrollCountdouble=Double.parseDouble(scrollCount);
				for(int i=1; i<=scrollCountdouble; i++){
					func_SwipeDown();
					Thread.sleep(2000);
					report.detailsAppendFolder("verify the paytable screen shot", " paytable next screen shot", "paytable next page screenshot ", "pass", language);
					/*funcLandscape();
					Thread.sleep(2000);
					report.detailsAppendFolder("verify the paytable screen shot", " paytable next screen shot", "paytable next page screenshot ", "pass", language);
					funcPortrait();
					Thread.sleep(2000);*/
				}
				nativeClickByID(xpathMap.get("PaytableBackID"));
				Thread.sleep(1000);
			}
			catch (Exception e) {
				e.getMessage();	
			}
		}
		


		 public void waitFreeSpinEntry(Mobile_HTML_Report language,String languageDescription, String languageCode)
		    {
		        try{
		        waitForSpinButton();
		        spinclick();
		        new WebDriverWait(webdriver, 90).until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesignWinID"))));
		        //Native_ClickByID("BonusCanvas");       
		        //new WebDriverWait(webdriver, 90).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("freeSpinEntryScreenID"))));
		        new WebDriverWait(webdriver, 150).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='FreeSpinIntro'][contains(@style, 'display: inherit')]")));
		                Thread.sleep(2000);
		                language.detailsAppendFolder("Verify that the application should display Free Entry Summary in " + languageCode + " ","Free Spin Summary should display in " + languageDescription + " ","Free Spin Summary displays in portrait mode " + languageDescription + " ", "Pass", languageCode);
		                System.out.println("After Entry screen Portrait");
		        funcLandscape();
		        language.detailsAppendFolder("Verify that the application should display Free Entry Summary in " + languageCode + " ","Free Spin Summary should display in " + languageDescription + " ","Free Spin Summary displays in portrait mode " + languageDescription + " ", "Pass", languageCode);
		        System.out.println("After Entry screen landscape");
		        funcPortrait();
		        //FSSceneLoading();
		        //FS_continue();
		        //waitSummaryScreen();
		        new WebDriverWait(webdriver, 500).until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("CongratsFSSummaryID"))));
		        }
		        catch(Exception e)
		        {
		            log.error("Error in free spin entry");
		            log.error(e);
		            System.out.println(e);
		        }
		    }
		 
		 /**
		  * Method to check the free spin feature with address bar unable and disable
		  * Date:11/8/2021
		  */
		 public void freeSpinWithAddressBar(Mobile_HTML_Report language,String languageDescription, String languageCode)
		    {
			 try{
				 waitForSpinButton();
				 spinclick();
				 new WebDriverWait(webdriver, 90).until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesignWinID"))));
				// new WebDriverWait(webdriver, 150).until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("freeSpinEntryScreenID"))));
				 new WebDriverWait(webdriver, 150).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathMap.get("freeSpinEntryScreenXpath"))));
				 Thread.sleep(1000);
				 language.detailsAppendFolder("Verify that the application should display Free Entry Summary in " + languageCode + " ","Free Spin Summary should display in " + languageDescription + " ","Free Spin Summary displays in portrait mode " + languageDescription + " ", "Pass", languageCode);
				 clickOnAddressBar();
				 language.detailsAppendFolder("Verify that the application should display Free Entry Summary in " + languageCode + " after enabling the address bar ","Free Entry Summary should display in " + languageDescription + " ","Free Entry Summary displays in portrait mode " + languageDescription + " after enbaling address bar", "Pass", languageCode);

				
				 
				 Thread.sleep(3000);
				 language.detailsAppendFolder("Verify that the application should display Free spin scene in " + languageCode + " ","Free Spin scene should display in " + languageDescription + " ","Free Spin scene displays in portrait mode " + languageDescription + " ", "Pass", languageCode);
				 clickOnAddressBar();
				 language.detailsAppendFolder("Verify that the application should display Free spin scene in " + languageCode + " after enabling the address bar ","Free Spin scene should display in " + languageDescription + " ","Free Spin scene displays in portrait mode " + languageDescription + " after enbaling address bar", "Pass", languageCode);
				 
				 
				 new WebDriverWait(webdriver, 500).until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("CongratsFSSummaryID"))));
				 Thread.sleep(1000);
				 language.detailsAppendFolder("Verify that the application should display Free spin summary in " + languageCode + " ","Free Spin summary should display in " + languageDescription + " ,before enabling the address bar","Free Spin summary displays in landscape mode " + languageDescription + " ", "Pass", languageCode);
				 clickOnAddressBar();
				 language.detailsAppendFolder("Verify that the application should display Free spin Summary in " + languageCode + " after enabling the address bar ","Free Spin summary should display in " + languageDescription + " ","Free Spin summary displays in portrait mode " + languageDescription + " after enbaling address bar", "Pass", languageCode);
				 
			 
			 }
		        catch(Exception e)
		        {
		            log.error("Error in free spin entry");
		            log.error(e);
		            System.out.println(e);
		        }
		    }
		 /**
			 * Date: 24/05/2018
			 * Author: Havish Jain
			 * Description: This function is used to remove hand gesture and to play in full screen.
			 * Parameter:
			 */

			public void func_FullScreen(Mobile_HTML_Report language){
				Wait=new WebDriverWait(webdriver,50);	 
				try {
					Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("Spin_Button_ID"))));
					boolean b = webdriver.findElement(By.id(xpathMap.get("FullScreenOverlayID"))).isDisplayed();
					if(b){
						nativeClickByID(xpathMap.get("FullScreenOverlayID"));
						
					}
					Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSpin")));
					language.detailsAppend("Full screen","Full screen should be displayed", "Full screen should be displayed", "Pass");
					//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));			
					//newFeature();

				} 
				catch (Exception e) {
					System.out.println("Full screen is not displaying");
					try {
						language.detailsAppend("Full screen","Full screen is not displayed", "Full screen is not displayed", "Fail");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			} 

			/**
			 * Date: 24/05/2018
			 * Author: Havish Jain
			 * Description: This function is used to remove hand gesture and to play in full screen.
			 * Parameter:
			 * @return boolean 
			 *//*

			public boolean funcFullScreen(){

				Wait=new WebDriverWait(webdriver,10);	
				boolean isOverlayRemove=false;
				try {
					
		            Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("clock_ID"))));

					if(osPlatform.equalsIgnoreCase("Android"))
					{
						String context=webdriver.getContext();
						webdriver.context("NATIVE_APP"); 
						Dimension size1 = webdriver.manage().window().getSize(); 
						int startx = size1.width / 2;
						int starty = size1.height / 2;
						TouchAction action = new TouchAction(webdriver);
						action.press(PointOption.point(startx,starty)).release().perform();
						webdriver.context(context);
						isOverlayRemove=true;
					}
					else{//For IOS to perform scroll
						Thread.sleep(2000);
						System.out.println("current contex"+webdriver.getContext());
						Dimension size = webdriver.manage().window().getSize();
						int anchor = (int) (size.width * 0.5);
						int startPoint = (int) (size.height * 0.5);
						int endPoint = (int) (size.height * 0.4);
						new TouchAction(webdriver).press(point(anchor, startPoint)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(anchor, endPoint)).release().perform();
							
					}
					log.debug("tapped on full screen overlay");
					
				} 
				catch (Exception e) {
					log.error(e.getStackTrace());
				}
				return isOverlayRemove;
			}*/
			
		//***** To Max Coin Size
		public boolean moveCoinSizeSliderToMaxBet() {
			boolean isSetToMaxBet=false;
		Wait = new WebDriverWait(webdriver, 5000);
				try {
					func_Click(xpathMap.get("OneDesignbetbutton"));
					WebElement coinSizeSlider = webdriver.findElement(By.xpath(xpathMap.get("OneDesignCoinSizeSlider_ID")));
					Thread.sleep(3000);
					Actions action = new Actions(webdriver);
					//action.dragAndDropBy(coinSizeSlider, -130, 0).build().perform();

					Thread.sleep(2000);
					action.dragAndDropBy(coinSizeSlider, 100, 0).build().perform();
					//action.moveByOffset(0, 250).build().perform(); 300
					Thread.sleep(3000);
					isSetToMaxBet=true;
					
					//Handling exception for closing the total bet
				} catch (MoveTargetOutOfBoundsException e) {
					log.error(e.getMessage(),e);
					isSetToMaxBet=true;
				
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				return isSetToMaxBet;
			}
		/*
		@Override
		public boolean settingsClose()
		{
			boolean isSettingClose=false;
			try {
				
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("SettingCloseID"))));
				if(nativeClickByID(xpathMap.get("SettingCloseID")))
				{
					isSettingClose=true;
					log.debug("Settings Closed");
					Thread.sleep(1500);
				}
					
				
			}
			catch(Exception e)
			{
				log.debug("error in settingsBack()",e);
			}
			return isSettingClose;
		}*/
		
/**
* sets the context before retrieving the text 
* @author rk61073
*/
public boolean setContext() throws IOException
{
	boolean setcontext = false;
	try
	{
		if (webdriver instanceof AndroidDriver) 
		{
			webdriver.context("CHROMIUM");
		}
		else if (webdriver instanceof IOSDriver) 
		{
		  Set<String> contexts = webdriver.getContextHandles();
			for (String context : contexts) {
				if (context.startsWith("WEBVIEW")) 
				{
					log.debug("context going to set in IOS is:" + context);
					webdriver.context(context);
				}
			}
		}setcontext= true;Thread.sleep(2000);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
		setcontext= false;
	}
	return setcontext;
}
		
/**
 * 
 * @param locator
 * @return
 * @author rk61073
 * @throws IOException 
 */
public String fun_GetText(String locator) throws IOException 
{
	WebDriverWait Wait = new WebDriverWait(webdriver, 10);String text = null;
	try
	{
		setContext();
		
		WebElement ele	= Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get(locator))));
		if(ele.isDisplayed() && ele!=null)
		{
		 ele = webdriver.findElement(By.xpath(xpathMap.get(locator)));
		 text = ele.getText();log.debug(text);
		}
		else
		{
		System.out.println("Unable to get the text for : "+locator + " : FAIL");log.debug("Unable to get the text for : "+locator + " : FAIL");	
		}
		return ele.getText();
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	return text;
	
}
/**
 * 
 * @param locator
 * @return
 * @author rk61073
 * @throws IOException 
 */
public String fun_GetText_Id(String locator) throws IOException 
{
	WebDriverWait Wait = new WebDriverWait(webdriver, 10);String text = null;
	try
	{
		setContext();
		
		WebElement ele	= Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get(locator))));
		if(ele.isDisplayed() && ele!=null)
		{
		 ele = webdriver.findElement(By.id(xpathMap.get(locator)));
		 text = ele.getText();log.debug(text);
		}
		else
		{
		System.out.println("Unable to get the text for : "+locator + " : FAIL");log.debug("Unable to get the text for : "+locator + " : FAIL");	
		}
		return ele.getText();
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	return text;
	
}
/**
 * Verifies the Currency Format -  using String method
 * @param isoCode
 * @param regExp
 * @param method
 * @return
 * @author rk61073
 */
public boolean verifyRegularExpression(Mobile_HTML_Report report, String regExp, String method)
{
	String Text = method;
	boolean regexp= false;
	try
	{
		Thread.sleep(2000);
	if (Text.matches(regExp)) 
	{
		log.debug("Compared with Reg Expression.Currency is same");
		regexp = true; 
	}
	else
	{
		log.debug("Compared with Reg Expression.Currency is different");
	}
	Thread.sleep(2000);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}	
	return regexp ;		
}
/**
 * method is used to navigate 
 * @param report
 * @param gameurl
 * @author rk61073
 */
public void checkpagenavigation(Mobile_HTML_Report report, String gameurl,String CurrencyName) 
{
	try 
	{
		String mainwindow = webdriver.getWindowHandle();
		Set<String> s1 = webdriver.getWindowHandles();
		if (s1.size() > 1) 
		{
		  Iterator<String> i1 = s1.iterator();
		  while (i1.hasNext()) {
		  String ChildWindow = i1.next();
		  if (mainwindow.equalsIgnoreCase(ChildWindow))
     {
		//report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot", "Navigation page screenshot ", "PASS");
		ChildWindow = i1.next();
		webdriver.switchTo().window(ChildWindow);
		String url = webdriver.getCurrentUrl();
		log.debug("Navigation URL is :: " + url);
		if (!url.equalsIgnoreCase(gameurl))
		{
		// pass condition for navigation	
		report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "PASS",CurrencyName);
		log.debug("Page navigated succesfully");
		webdriver.close();
		} 
		else
		{
			//System.out.println("Now On game page");
			log.debug("Now On game page");
		}
	}
}
		webdriver.switchTo().window(mainwindow);
	} 
		else
	{
		String url = webdriver.getCurrentUrl();
	//System.out.println("Navigation URL is ::  " + url);
	log.debug("Navigation URL is ::  " + url);
	if (!url.equalsIgnoreCase(gameurl)) {
		// pass condition for navigation
		report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "PASS",CurrencyName);
		log.debug("Page navigated succesfully");
		webdriver.navigate().to(gameurl);
		waitForSpinButton();
		//newFeature();
	}
	else 
	{
		webdriver.navigate().to(gameurl);
		waitForSpinButton();
		//System.out.println("Now On game page");
		log.debug("Now On game page");
       }
		}
	} catch (Exception e)
	{
		log.error("error in navigation of page");
	}
	}

/**
 * validated the hambugermenu options 
 * @author rk61073
 */
public boolean hamburgerMenu(Mobile_HTML_Report report,String CurrencyName)
{
	boolean hamburgerMenuOptions= false;
	String gameurl = webdriver.getCurrentUrl();
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("HamburgerMenu")))) != null)
	{
	fun_Click("HamburgerMenu");Thread.sleep(3000);
	System.out.println("Hamburger menu clicked");log.debug("Hamburger menu clicked");
	hamburgerMenuOptions = true;
	}
	else
	{
	System.out.println("Unable to click on Hamburger Menu");log.debug("Unable to click on Hamburger Menu");
	hamburgerMenuOptions = false;
	}
	
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("HomeIconfromHamburgerMenu")))) != null)
	{
	fun_Click("HomeIconfromHamburgerMenu");Thread.sleep(3000);
	checkpagenavigation(report,gameurl,CurrencyName);
	System.out.println("Home button clicked");log.debug("Home button clicked");
	hamburgerMenuOptions = true;
	}
	else
	{
	System.out.println("Unable to click on Home");log.debug("Unable to click on Home");
	hamburgerMenuOptions = false;
	}
	
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("HamburgerMenu")))) != null)
	{
	fun_Click("HamburgerMenu");Thread.sleep(3000);
	System.out.println("Hamburger menu clicked");log.debug("Hamburger menu clicked");
	hamburgerMenuOptions = true;
	}
	else
	{
	System.out.println("Unable to click on Hamburger Menu");log.debug("Unable to click on Hamburger Menu");
	hamburgerMenuOptions = false;
	}
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("BankingIconfromHamburgerMenu")))) != null)
	{
	fun_Click("BankingIconfromHamburgerMenu");Thread.sleep(3000);
	checkpagenavigation(report,gameurl,CurrencyName);
	System.out.println("Banking button clicked ");log.debug("Banking button clicked");
	hamburgerMenuOptions = true;
	}
	else
	{
	System.out.println("Unable to click on Banking ");log.debug("Unable to click on Banking ");
	hamburgerMenuOptions = false;
	}
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("HamburgerMenu")))) != null)
	{
	fun_Click("HamburgerMenu");Thread.sleep(3000);
	System.out.println("Hamburger menu clicked");log.debug("Hamburger menu clicked");
	hamburgerMenuOptions = true;
	}
	else
	{
	System.out.println("Unable to click on Hamburger Menu");log.debug("Unable to click on Hamburger Menu");
	hamburgerMenuOptions = false;
	}
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("SettingsIconfromHamburgerMenu")))) != null)
	{
	fun_Click("SettingsIconfromHamburgerMenu");Thread.sleep(3000);
	System.out.println("Settings clicked");log.debug("Settings clicked");
	hamburgerMenuOptions = true;
	}
	else
	{
	System.out.println("Unable to click on Settings ");log.debug("Unable to click on Settings ");
	hamburgerMenuOptions = false;
	}
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("CloseSettings")))) != null)
	{
	fun_Click("CloseSettings");Thread.sleep(3000);
	System.out.println("Close settings button");log.debug("Close settings button");
	hamburgerMenuOptions = true;
	}
	else
	{
	System.out.println("Unable to click on close Settings button ");log.debug("Unable to click on close Settings button ");
	hamburgerMenuOptions = false;
	}	
}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	return hamburgerMenuOptions;
}
/**
 * method is used to perform click action 
 * @param locator
 * @return
 * @author rk61073
 * @throws IOException 
 */
public boolean fun_Click(String locator) throws IOException 
{
	Wait = new WebDriverWait(webdriver, 120);
	boolean present = false;
	try 
	{
		setContext();
		
    if(osPlatform.equalsIgnoreCase("Android"))
	{
	WebElement ele = webdriver.findElement(By.xpath(xpathMap.get(locator)));
	if (ele != null) 
	{
		ele.click();log.debug("Click on "+locator);
		present = true;
	}
	else
	{
		System.out.println("Unable to Click on "+locator);log.debug("Unable to Click on "+locator);
		present = false;
	}
		}
	else
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get(locator)))) != null)
		{
		
		MobileElement element = (MobileElement) webdriver.findElement(By.xpath(xpathMap.get(locator)));
		if (element != null) 
		{
			element.click();log.debug("Click on "+locator);
			present = true;
		}
		else
		{
			System.out.println("Unable to Click on "+locator);log.debug("Unable to Click on "+locator);
			present = false;
		}
	}
	}
	
	
	}
	catch (NoSuchElementException e) 
	{
		log.error(e.getMessage(), e);
	}
	return present;
}
/**
 * method is used to perform click action 
 * @param locator
 * @return
 * @author rk61073
 * @throws IOException 
 */
public boolean fun_ClickByID(String locator) throws IOException 
{
	Wait = new WebDriverWait(webdriver, 120);
	boolean present = false;
	try 
	{
		setContext();
		
    if(osPlatform.equalsIgnoreCase("Android"))
	{
	WebElement ele = webdriver.findElement(By.id(xpathMap.get(locator)));
	if (ele.isDisplayed() && ele != null) 
	{
		ele.click();log.debug("Click on "+locator);
		present = true;
	}
	else
	{
		System.out.println("Unable to Click on "+locator);log.debug("Unable to Click on "+locator);
		present = false;
	}
		}
	else
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get(locator)))) != null)
		{
		MobileElement element = (MobileElement) webdriver.findElement(By.id(xpathMap.get(locator)));
		if (element != null) 
		{
			element.click();log.debug("Click on "+locator);
			present = true;
		}
		else
		{
			System.out.println("Unable to Click on "+locator);log.debug("Unable to Click on "+locator);
			present = false;
		}
	}
	}
	
	
	}
	catch (NoSuchElementException e) 
	{
		log.error(e.getMessage(), e);
	}
	return present;
}
/**
 * method is for click & get text 
 * @param report
 * @param menuclick
 * @param locator
 * @return
 * @author rk61073
 */
public String clickAndGetText(Mobile_HTML_Report report, String menuclick ,String locator) 
{
	String getText = null;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);

	try 
	{
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get(menuclick)))) != null); 
	{
	webdriver.findElement(By.xpath(xpathMap.get(menuclick))).click();
	boolean isloactorVisible = webdriver.findElement(By.xpath(xpathMap.get(locator))).isDisplayed();
	if (isloactorVisible) 
	{
		getText = webdriver.findElement(By.xpath(xpathMap.get(locator))).getText(); 
		log.debug( "Text is : "+getText);		
		System.out.println( "Text is : "+getText);	
    }
	else 
	{
		System.out.println("Unable to locate an element");log.debug("Unable to locate an element");
	}}}
	catch (Exception e)
	{
		log.error(e.getMessage(), e);
	}
	return getText ;
}
/**method is for click and navigate 
 * @param report
 * @param locator
 * @return
 * @author rk61073
 */
public boolean clickAndNavigate(Mobile_HTML_Report report , String locator,String currencyName) 
{
	boolean ele =false;
	String gameurl = webdriver.getCurrentUrl();
try
{		
	boolean isElementVisisble = webdriver.findElement(By.xpath(xpathMap.get(locator))).isDisplayed();	
if(isElementVisisble)
	{
	webdriver.findElement(By.xpath(xpathMap.get(locator))).click();
	//report.detailsAppend(" Click ", " Clicked ", " Clicked", "PASS");
	Thread.sleep(3000);
	checkpagenavigation(report, gameurl,currencyName); 
	//cHeckPagenavigation(report, gameurl);
	//newFeature();
	Thread.sleep(1000);
	ele = true;
	}
}
catch(Exception e)
{
	log.error(e.getMessage(), e);
}
return ele;
}
/**
 * method is to validate help from top bar & navigates to help
 * @param report
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean helpFromTopBarMenu(Mobile_HTML_Report report,String currencyName) throws InterruptedException
{
String menuclick = "MenufromTopBar";
String locator = "HelpfromTopBarMenu";
String Text = clickAndGetText(report,menuclick,locator);
boolean text= false;
try
{
if (Text.equals("Help")) 
{
System.out.println("Help text from the topbar menu is same");
log.debug("Help text from the topbar menu is same");
text = true;
} Thread.sleep(2000);
clickAndNavigate(report,locator,currencyName);
}
catch (Exception e) 
{
log.error(e.getMessage(), e);
}
return text ;
}
/**
 * method is for clock 
 * @author rk61073
 */
public String clockfromTopBar(Mobile_HTML_Report lvcReport, String currencyName) 
{
	String clockText = null;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("ClockfromTopBar")))) != null )
	{
	clockText = fun_GetText("ClockfromTopBar");
	//System.out.println("Clock is visible");log.debug("Clock is visible");
	}
	else
	{
		System.out.println("Clock is not visible");log.debug("Clock is not visible");
	}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	return clockText;
}
/**
 * method is to enable & disable the quick deal button 
 * @param report
 * @param method
 * @return
 *  @author rk61073
 */
public boolean isQuickDealButtonAvailable(Mobile_HTML_Report report, String currencyName)
{
	boolean isQuickDealButtonAvailable= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("QuickDealButton")))) != null)
		{
		fun_Click("QuickDealButton");Thread.sleep(3000);//to enable the button 
		System.out.println("Quickdeal button is enabled");log.debug("Quickdeal button is enabled");
		
		fun_Click("QuickDealButton");Thread.sleep(3000);//to disable the button 
		System.out.println("Quickdeal button is disabled");log.debug("Quickdeal button is disabled");
		isQuickDealButtonAvailable = true;
		
		}
		else
		{
		System.out.println("Unable to click on QuickDealButton");log.debug("Unable to click on QuickDealButton");
		isQuickDealButtonAvailable = false;	
		}}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	
	return isQuickDealButtonAvailable;		
}

/**
 * This methode Set conSizeSliderTo Max bet
 * 
 * @author sb64689
 * @return
 */

public boolean moveCoinSizeSliderToMaxBet_Poker(Mobile_HTML_Report report, String regExpr, String currencyName)
{


	boolean isSetToMaxBet = false;
	Wait = new WebDriverWait(webdriver, 5000);
	try {
		// func_Click(XpathMap.get("OneDesignbetbutton"));
	webdriver.findElement(By.xpath(xpathMap.get("OneDesignbetbutton"))).click();

	WebElement coinSizeSlider = webdriver.findElement(By.xpath(xpathMap.get("OneDesignCoinSizeSlider_ID")));

	Actions action = new Actions(webdriver);

	String bet_attr = coinSizeSlider.getAttribute("style");
	System.out.println(bet_attr.trim());
	Thread.sleep(2000);

	if (!bet_attr.equals("left: 100%;")) {

		action.dragAndDropBy(coinSizeSlider, 200, 0).build().perform(); // 500
		Thread.sleep(2000);
		isSetToMaxBet = true;
	} else {
		// if already set max bet then make flag as true
			isSetToMaxBet = true;
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}
	return isSetToMaxBet;
}

/**
 * settings open
 * 
 * @author sb64689
 * @param report
 * @param languageCode
 * @return
 */
public boolean settings_Open(Mobile_HTML_Report report, String languageCode) {
	boolean flag = false;
	try {
		// in case of video poker game put locator of deal button

	Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("clock"))));
	// Clicking on hamburger menu
	// webdriver.context("NATIVE_APP");
	// tapOnCoordinates(20,140);
	MobileElement el4 = (MobileElement) webdriver.findElement(By.xpath(xpathMap.get("OneDesign_Hamburger")));
	el4.click();
	funcLandscape();
	report.detailsAppendFolder("Verify hamburger functionality", "hamburger functionality",
			"hamburger functionality", "Pass", languageCode);
	funcPortrait();
	report.detailsAppendFolder("Verify hamburger functionality", "hamburger functionality",
			"hamburger functionality", "Pass", languageCode);
	// nativeClickByID(xpathMap.get("OneDesign_Hamburger"));
	//// menuOpen();
	log.debug("Clicked on menu button ");
	Thread.sleep(2000);
	Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("OneDesign_SettingsID"))));
	// Clicking on settings link
	if (nativeClickByID(xpathMap.get("OneDesign_SettingsID"))) {
		flag = true;
		log.debug("Clicked on settigns button");
		Thread.sleep(2000);
	}
	// MobileElement el5 = (MobileElement)
	// webdriver.findElement(By.id(xpathMap.get("OneDesign_SettingsID")));
	// el5.click();

	// webdriver.context("CHROMIUM");
} catch (Exception e) {
	flag = false;
	log.error("error in settingsOpen()", e);
	}
	return flag;

}

/**
 * @author sv65878
 * @param report
 * @param CurrencyName
 * @return
 */
public boolean helpFromTopBarMenunew(Mobile_HTML_Report report, String CurrencyName) {
	boolean text = false;
	String helptext = null;
	String gameurl = webdriver.getCurrentUrl();
	WebDriverWait Wait = new WebDriverWait(webdriver, 120);
	try {

		WebElement MenuFromTop = webdriver.findElement(By.xpath(xpathMap.get("MenuFromTopBar_1")));
	if (MenuFromTop.isDisplayed()) {
		MenuFromTop.click();
	}

	WebElement HelpFromTop = Wait
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("HelpText"))));
	if (HelpFromTop.isDisplayed()) {
		helptext = fun_GetText("HelpText");
		func_Click("HelpText");
		checkpagenavigation_1(report, gameurl, CurrencyName);
		waitForSpinButton();
	}

	if (helptext.equals("Help")) {
		System.out.println("Help text from the topbar menu is same");
		log.debug("Help text from the topbar menu is same");
			text = true;
		}
		Thread.sleep(2000);

	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	return text;
}

/**
 * method is used to navigate
 * 
 * @param report
 * @param gameurl
 * @author sv65878
 */
public void checkpagenavigation_1(Mobile_HTML_Report report, String gameurl, String CurrencyName) {
	try {
		String mainwindow = webdriver.getWindowHandle();
		Set<String> s1 = webdriver.getWindowHandles();
		if (s1.size() > 1) {
			Iterator<String> i1 = s1.iterator();
			while (i1.hasNext()) {
				String ChildWindow = i1.next();
				if (mainwindow.equalsIgnoreCase(ChildWindow)) {
					// report.detailsAppend("verify the Navigation screen shot", " Navigation page
				// screen shot", "Navigation page screenshot ", "PASS");
				ChildWindow = i1.next();
				webdriver.switchTo().window(ChildWindow);
				String url = webdriver.getCurrentUrl();
				log.debug("Navigation URL is :: " + url);
				if (!url.equalsIgnoreCase(gameurl)) {
					// pass condition for navigation

					// report.detailsAppendFolder("verify the Navigation screen shot", " Navigation
					// page screen shot","Navigation page screenshot ", "PASS",CurrencyName);
					log.debug("Page navigated succesfully");
					webdriver.close();
				} else {
					// System.out.println("Now On game page");
					log.debug("Now On game page");
				}
			}
		}
		webdriver.switchTo().window(mainwindow);
	} else {
		String url = webdriver.getCurrentUrl();
		// System.out.println("Navigation URL is :: " + url);
		log.debug("Navigation URL is ::  " + url);
		if (!url.equalsIgnoreCase(gameurl)) {
			// pass condition for navigation
			report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot",
					"Navigation page screenshot ", "PASS", CurrencyName);
			log.debug("Page navigated succesfully");
			webdriver.navigate().to(gameurl);
			Thread.sleep(15000);

			funcFullScreen();
			waitForSpinButton();
			// newFeature();
		} else {
			webdriver.navigate().to(gameurl);
			// waitForSpinButton();
			// System.out.println("Now On game page");
			log.debug("Now On game page");
		}
	}
} catch (Exception e) {
	log.error("error in navigation of page");
	}
}

/**
 * move the coin size to min bet
 * 
 * @author sb64689
 * @return
 */
public boolean moveCoinSizeSliderToMinBet_Poker() {
	boolean isSetToMinBet = false;
	Wait = new WebDriverWait(webdriver, 5000);
	try {
		Native_ClickByXpath(xpathMap.get("OneDesignbetbutton"));

	// report.detailsAppendFolder("Base Game", "Draw Button", "Draw Button", "PASS",
	// "" + currencyName);
	WebElement coinSizeSlider = webdriver.findElement(By.xpath(xpathMap.get("OneDesignCoinSizeSlider_ID")));
	Thread.sleep(2000);
	Actions action = new Actions(webdriver);

	String bet_attr = coinSizeSlider.getAttribute("style");
	System.out.println(bet_attr.trim());

	Thread.sleep(2000);

	if (!bet_attr.equals("left: 100%;")) {
		System.out.println("slided the coin slider");
		log.debug("slided the coin slider");
		action.dragAndDropBy(coinSizeSlider, -100, 0).build().perform();
		String bet_attrAftr = coinSizeSlider.getAttribute("style");
		System.out.println(bet_attrAftr.trim());
		Thread.sleep(2000);
		isSetToMinBet = true;
	} else {
		// if already set max bet then make flag as true
			isSetToMinBet = true;
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}
	return isSetToMinBet;
}



/**
 * Paytable Payouts
 * 
 * @author sb64689
 * @param report
 * @param CurrencyName
 * @return
 */

public String[] paytablePayouts(Mobile_HTML_Report report, String CurrencyName) {
	String paytablePayout[] = { "payout1", "payout2", "payout3", "payout4", "payout5", "payout6", "payout7",
		"payout8", "payout9", "payout10", "payout11" };
String demo[] = new String[11];
try {
	for (int i = 1; i <= 5; i++) {
		func_Click("Paytable");
		for (int j = 0; j < paytablePayout.length; j++) {
			demo[j] = fun_GetText(paytablePayout[j]);
		}
	}
	func_Click("PaytableClose");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return demo;
}

public boolean verifyRegularExpressionUsingArrays(Mobile_HTML_Report report, String regExp, String[] method) {
	String[] Text = method;
	boolean regexp = false;
	try {
		Thread.sleep(2000);
		for (int i = 0; i < Text.length; i++) {
			if (Text[i].matches(regExp)) {
				log.debug("Compared with Reg Expression .Currency is same");
			regexp = true;
		} else {
			log.debug("Compared with Reg Expression .Currency is different");
				regexp = false;
			}
			Thread.sleep(2000);
		}
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	return regexp;
}

public boolean isDealButtonAvailable(Mobile_HTML_Report report, String currencyName) {
	boolean isDealButtonAvailable = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try {
		if (Wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DealButton")))) != null) {
		func_Click("DealButton");
		Thread.sleep(3000);// to enable the button
		System.out.println("deal button is enabled");
		log.debug("deal button is enabled");

		/*
		 * func_Click("DealButton"); Thread.sleep(3000);// to disable the button
		 * System.out.println("deal button is disabled");
		 * log.debug("deal button is disabled");
		 */

		isDealButtonAvailable = true;

	} else {
		System.out.println("Unable to click on DealButton");
		log.debug("Unable to click on DealButton");
			isDealButtonAvailable = false;
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}

	return isDealButtonAvailable;
}

/**
 * method is to Validate draw button
 * 
 * @param report
 * @param method
 * @return
 * @author sv65878
 */
public boolean isDrawButtonAvailable(Mobile_HTML_Report report, String currencyName) {
	boolean isDrawButtonAvailable = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try {
		if (Wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DrawButton")))) != null) {
		func_Click("DrawButton");
		Thread.sleep(3000);// to enable the button
		System.out.println("Draw button is enabled");
		log.debug("Draw button is enabled");

		/*
		 * func_Click("DrawButton");Thread.sleep(3000);//to disable the button
		 * System.out.println("deal button is disabled");log.
		 * debug("deal button is disabled");
		 */
		isDrawButtonAvailable = true;

	} else {
		System.out.println("Unable to click on DrawButton");
		log.debug("Unable to click on DrawButton");
			isDrawButtonAvailable = false;
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}

	return isDrawButtonAvailable;
}

/**
 * method is to Validate Double button
 *
 * @param report
 * @param method
 * @return
 * @author sv65878
 */
public boolean isDoubleButtonAvailable(Mobile_HTML_Report report, String currencyName) {
	boolean isDoubleButtonAvailable = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try {
		if (Wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DoubleButton")))) != null) {
		// func_Click("DoubleButton");
		Thread.sleep(3000);
		System.out.println("DoubleButton is Available");
		log.debug("DoubleButton is Available");

		/*
		 * func_Click("DealButton");Thread.sleep(3000);//to disable the button
		 * System.out.println("deal button is disabled");log.
		 * debug("deal button is disabled");
		 */
		isDoubleButtonAvailable = true;

	} else {
		System.out.println("Unable to find  DoubleButton");
		log.debug("Unable to find DoubleButton");
			isDoubleButtonAvailable = false;
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}

	return isDoubleButtonAvailable;
}

/**
 * @author sb64689
 * @param report
 * @param currencyName
 * @return
 */

public boolean isCollectBtnButtonAvailable(Mobile_HTML_Report report, String currencyName) {
	boolean isCollectBtnButtonAvailable = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try {
		WebElement collectBtn = webdriver.findElement(By.xpath(xpathMap.get("CollectButton")));
	Thread.sleep(3000);
	if (collectBtn.isDisplayed()) {
		/*
		 * if
		 * (Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.
		 * get("CollectButton")))) != null) {
		 */
		func_Click("CollectButton");
		Thread.sleep(3000);
		System.out.println("CollectButton button is enabled");
		log.debug("CollectButton button is enabled");
		isCollectBtnButtonAvailable = true;

	} else {
		System.out.println("Unable to click on CollectButton");
		log.debug("Unable to click on CollectButton");
			isCollectBtnButtonAvailable = false;
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}

	return isCollectBtnButtonAvailable;
}

/**
 * DoubleTo
 * 
 * @param report
 * @param regExpr
 * @param CurrencyName
 * @return
 * @author sb64689
 */

public boolean doubleToFunc(Mobile_HTML_Report report, String regExpr, String CurrencyName) {
	boolean totalwin = false;
	WebDriverWait wait;
	WebElement winValue;
	double win;
	boolean results = false;

	WebElement GambleLimit;
	try {

		wait = new WebDriverWait(webdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("deal"))));

	// *********Clicking on Deal and Draw button
	dealClick();
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("draw"))));
	WebElement Drawbtn = webdriver.findElement(By.id(xpathMap.get("draw")));
	// Drawbtn.click();
	drawClick();
	Thread.sleep(2000);
	// *********Clicking on PayTable Close
	Thread.sleep(10000);
	WebElement paytableClose = webdriver.findElement(By.id(xpathMap.get("PaytableCoinsDisplay1")));
	if (paytableClose.isDisplayed()) {
		nativeClickByID(xpathMap.get("PaytableCrossBtn"));
		System.out.println("Paytable Closed");
	}

	// *********Clicking on Double button
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("double"))));
	report.detailsAppendFolder("Basegame Double Button", "Double Button  is Available",
			"Double Button is Available", "PASS", "" + CurrencyName);
	nativeClickByID(xpathMap.get("double"));
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("doubleSection"))));
	Thread.sleep(5000);

	// *********Credit and Win Amount
	WebElement creditVal = Wait
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DoubleCreditVal"))));
	System.out.println("Credit value under Double :" + fun_GetText("DoubleCreditVal"));
	report.detailsAppendFolder("Double Section Credit Amount", "Credit Amount is Available",
			"Credit Amount is Available", "PASS", "" + CurrencyName);
	verifyRegularExpression(report, regExpr, fun_GetText("DoubleCreditVal"));

	WebElement BetVal = Wait
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DoubleBetVal"))));
	System.out.println("Credit value under Double :" + fun_GetText("DoubleBetVal"));
	report.detailsAppendFolder("Double Section Bet Amount", "Bet Amount is Available",
			"Bet Amount is Available", "PASS", "" + CurrencyName);
	verifyRegularExpression(report, regExpr, fun_GetText("DoubleBetVal"));

	// ***************Click on card
	wait.until(ExpectedConditions.attributeContains(
			webdriver.findElement(By.xpath(xpathMap.get("doubleCardFirst"))), "class",
			"vp card quick-transition"));
	nativeClickByID(xpathMap.get("doubleCard"));
	Thread.sleep(7000);

	// ***************Get Win Amount
	WebElement doubleWinamt = Wait
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DoubleWinAmount"))));
	if (doubleWinamt.isDisplayed()) {
		report.detailsAppendFolder("Double Section Win Amount", "Win Amount is Available",
				"Win Amount is Available", "PASS", "" + CurrencyName);
		totalwin = verifyRegularExpression(report, regExpr, fun_GetText("DoubleWinAmount"));
		// report.detailsAppendFolder("Double To ","Total Win ","Total Win
		// ","PASS",""+CurrencyName);
		totalwin = true;
	} else {
		System.out.println("Unable to get the total win ");
		log.debug("Unable to get the total win ");
		report.detailsAppendFolder("Double To ", "Total Win ", "Total Win ", "FAIL", "" + CurrencyName);
		totalwin = false;
	}
	// ***************Collect and Double the win amount
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("DoubleBTN"))));
	WebElement DoubleBTN = webdriver.findElement(By.id(xpathMap.get("DoubleBTN")));
	if (DoubleBTN.isDisplayed()) {
		report.detailsAppendFolder("Double To ", "Double Button is visible ", "Double Button is visible ",
				"Pass", "" + CurrencyName);
	} else {
		report.detailsAppendFolder("Double To ", "Double Button is not visible ",
				"Double Button is not visible ", "FAIL", "" + CurrencyName);
	}

	Thread.sleep(2000);
	WebElement CollectBTN = Wait
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("CollectButton1"))));
	if (CollectBTN.isDisplayed()) {

		func_Click("CollectButton1");
		report.detailsAppendFolder("Double to Collect Button", "Double to Collect Button is Available",
				"Double to Collect Button is Available", "PASS", "" + CurrencyName);
		System.out.println("collect button clicked");
		waitForSpinButton();
		System.out.println("Return to base game");

	} else {
		System.out.println("Collect button not visible");
	}

} catch (Exception e) {
	log.error("Error in double to feature - " + e);
	try {
		// report.detailsAppendFolder("Verify DoubleTo functionality", "DoubleTo
		// functionality", "DoubleTo functionality", "Fail", CurrencyName);
	} catch (Exception e1) {
		log.error("Error in double to feature - " + e);
		}
	}
	return totalwin;
}

/**
 * @author sb64689 DoubleTogambleNew
 * @param report
 * @param regExpr
 * @param CurrencyName
 * @return
 */

public boolean doubleToGambleReached1(Mobile_HTML_Report report, String regExpr, String CurrencyName) {

	WebDriverWait wait;
	WebElement winValue;
	double win;
	boolean results = false;

	WebElement GambleLimit;
	try {

		wait = new WebDriverWait(webdriver, 60);

		wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("deal"))));

	// *********Clicking on Deal and Draw button
	dealClick();
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("draw"))));
	WebElement Drawbtn = webdriver.findElement(By.id(xpathMap.get("draw")));
	// Drawbtn.click();
	drawClick();
	Thread.sleep(2000);

	// *********Clicking on PayTable Close
	// wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("PaytableCoinsDisplay1"))));

	Thread.sleep(5000);
	WebElement paytableClose = webdriver.findElement(By.id(xpathMap.get("PaytableCoinsDisplay1")));
	if (paytableClose.isDisplayed()) {
		nativeClickByID(xpathMap.get("PaytableCrossBtn"));
		System.out.println("Paytable Closed");
	}

	// *********Clicking on Double button
	Thread.sleep(2000);
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("double"))));
	nativeClickByID(xpathMap.get("double"));
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("doubleSection"))));
	Thread.sleep(2000);

	do {
		// ***************Click on card
		wait.until(ExpectedConditions.attributeContains(
				webdriver.findElement(By.xpath(xpathMap.get("doubleCardFirst"))), "class",
				"vp card quick-transition"));
		Thread.sleep(2000);
		nativeClickByID(xpathMap.get("doubleCard"));
		Thread.sleep(7000);

		// DoubleToBtn
		GambleLimit = webdriver.findElement(By.xpath(xpathMap.get("gambleLimit")));
		Thread.sleep(2000);
		if (GambleLimit.isDisplayed()) {
			System.out.println("GambleLimit Reached");
			report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality",
					"Gamble Reached text should be displayed", "Pass", CurrencyName);
			break;
		} else {
			wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("DoubleBTN"))));
			WebElement DoubleBTN = webdriver.findElement(By.id(xpathMap.get("DoubleBTN")));
			Thread.sleep(2000);
			if (DoubleBTN.isDisplayed()) {

				// tapOnCoordinates(266,510 );
				func_Click("DoubleButtton");
				System.out.println("Double clicked");
				Thread.sleep(3000);
			} else {
				System.out.println("DoubleTo not clicked");
			}

		}

	} while (!(webdriver.findElement(By.xpath(xpathMap.get("gambleLimit"))).isDisplayed()));
	Thread.sleep(3000);
	GambleLimit = webdriver.findElement(By.xpath(xpathMap.get("gambleLimit")));
	Thread.sleep(1000);
	if (GambleLimit.isDisplayed()) {
		results = true;
	}
	Thread.sleep(2000);
	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("backToGame"))));
	Thread.sleep(2000);
	if (webdriver.findElement(By.id(xpathMap.get("backToGame"))).isDisplayed()) {

		report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality",
				"Gamble Reached text should be displayed", "Pass", CurrencyName);
		funcLandscape();
		report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality",
				"Gamble Reached text should be displayed", "Pass", CurrencyName);
		funcPortrait();

	} else {
		report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality",
				"Gamble Reached text is not displayed", "Fail", CurrencyName);
	}
	Thread.sleep(1000);
	nativeClickByID(xpathMap.get("backToGame"));
	Thread.sleep(1000);
} catch (Exception e) {
	log.error("Error in double to feature - " + e);
	try {
		report.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality",
				"Gamble Reached text is not displayed", "Fail", CurrencyName);
	} catch (Exception e1) {
		log.error("Error in double to feature - " + e);
		}

	}
	return results;

}

/**
 * method is to set max bet in poker
 * 
 * @return
 * @author sb64689
 */
public boolean moveCoinSizeSliderToMaxBet_Poker() {
	boolean isSetToMaxBet = false;
	Wait = new WebDriverWait(webdriver, 5000);
	try {
		Native_ClickByXpath(xpathMap.get("OneDesignbetbutton"));
	funcLandscape();

	WebElement coinSizeSlider = webdriver.findElement(By.xpath(xpathMap.get("OneDesignCoinSizeSlider_ID")));
	Thread.sleep(2000);
	Actions action = new Actions(webdriver);

	String bet_attr = coinSizeSlider.getAttribute("style");
	System.out.println(bet_attr.trim());

	Thread.sleep(2000);

	if (!bet_attr.equals("left: 100%;")) {

		System.out.println("slided the coin slider");
		log.debug("slided the coin slider");
		// action.dragAndDropBy(coinSizeSlider, 500, 0).build().perform();
		Thread.sleep(2000);
		action.dragAndDropBy(coinSizeSlider, 300, 0).build().perform(); // for deuces wild 10 play
		// action.dragAndDropBy(coinSizeSlider, 250, 0).build().perform();
		String bet_attrAftr = coinSizeSlider.getAttribute("style");
		System.out.println(bet_attrAftr.trim());
		Thread.sleep(2500);
		isSetToMaxBet = true;
	} else {
		// if already set max bet then make flag as true
			isSetToMaxBet = true;
		}
	} catch (Exception e) {
		log.debug(e.getMessage());
	}
	return isSetToMaxBet;
}
/**wait for deal button
 * @author rk61073
 */
public void waitForDealButton() 
{
	Wait = new WebDriverWait(webdriver, 600);
	try 
	{
		log.debug("Waiting for base scene");
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DealButton"))));
	} 
	catch (Exception e) 
	{
		log.error("error while waiting for base scene", e);
	}
}
/**
 * 
 * @param report
 * @param language
 * @return
 * @author rk61073
 */
public boolean gameLogo(Mobile_HTML_Report report, String language)
{
	 boolean refresh= false;
	Wait = new WebDriverWait(webdriver, 40);
	try 
	{
	    webdriver.navigate().refresh();
		report.detailsAppendFolder("Verify the game logo ","Game logo","Game logo","PASS",""+language);
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("DealButton")))) != null)
		{
			boolean fullscreen = funcFullScreen();
			if(fullscreen)
			{
				log.debug("Full screen");
			}
			else
			{
				funcFullScreen();
			}
			Thread.sleep(2000);refresh = true;
		}
		else 
		{
			refresh = false;
		}
		
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}return refresh;
}
/**
 * method is for paytable 
 * @author rk61073
 */
public boolean paytable(Mobile_HTML_Report report, String CurrencyName)
{
	boolean isPaytableVisible= false;
	try 
	{
	boolean paytable =	NativeClickByXpath("PaytableIcon","PaytableBtn");
		if(paytable)
		{
		Thread.sleep(3000);
		log.debug("Clicked on PaytableIcon");
		//report.detailsAppendFolder("Verify Paytable payouts ","Paytable Opened","Paytable Opened : "+paytable,"PASS",""+CurrencyName);
		isPaytableVisible = true;
		}
		else
		{
		System.out.println("Unable to click on PaytableIcon");log.debug("Unable to click on PaytableIcon");
		report.detailsAppendFolder("Verify Paytable payouts ","Paytable Opened","Paytable Opened : "+paytable,"FAIL",""+CurrencyName);
		isPaytableVisible = false;
		}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	
	return isPaytableVisible;		
}	
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfEleven(Mobile_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9","payout10","payout11"};
	String demo[] = new String[11];
	try
   {
	   for(int i=1;i<=5;i++)
		{
		   NativeClickByXpath("CoinsPlusButton","CoinsPlusBtn"); 
	       String coins = fun_GetText("Coins");String coinsText =coins.replaceAll("[^/,0-9]", "");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);
		}
		report.detailsAppendNoScreenshot("Scenario : Verify paytable payouts . Where, COINS : "+coinsText , "Paytable payouts . Where, COINS : "+coinsText, "", "");
		portraitAndLandscape(report ,CurrencyName );
	     
		}
	   isPaytableClose();
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
        e.printStackTrace();
    }
    return demo ;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfThirteen(Mobile_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9","payout10","payout11","payout12","payout13"};
	String demo[] = new String[13];
	try
   {
	   for(int i=1;i<=5;i++)
		{
		   NativeClickByXpath("CoinsPlusButton","CoinsPlusBtn"); 
	       String coins = fun_GetText("Coins");String coinsText =coins.replaceAll("[^/,0-9]", "");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);
		}
		report.detailsAppendNoScreenshot("Scenario : Verify paytable payouts . Where, COINS : "+coinsText , "Paytable payouts . Where, COINS : "+coinsText, "", "");
		portraitAndLandscape(report ,CurrencyName );
		
		}
	   isPaytableClose();
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
       e.printStackTrace();
    }
    return demo ;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfTen(Mobile_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9","payout10"};
    String demo[] = new String[10];
	try
   {
	   for(int i=1;i<=5;i++)
		{
		   NativeClickByXpath("CoinsPlusButton","CoinsPlusBtn"); 
	       String coins = fun_GetText("Coins");String coinsText =coins.replaceAll("[^/,0-9]", "");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);	
		}
		report.detailsAppendNoScreenshot("Scenario : Verify paytable payouts . Where, COINS : "+coinsText , "Paytable payouts . Where, COINS : "+coinsText, "", "");
		portraitAndLandscape(report ,CurrencyName );
		
		}
	   isPaytableClose();
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
        e.printStackTrace();
    }
    return demo;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfNine(Mobile_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9"};
    String demo[] = new String[9];
	try
   {
	   for(int i=1;i<=5;i++)
		{
		   NativeClickByXpath("CoinsPlusButton","CoinsPlusBtn"); 
         String coins = fun_GetText("Coins");
         String coinsText =coins.replaceAll("[^/,0-9]", "");
         
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);
		}
		
		report.detailsAppendNoScreenshot("Scenario : Verify paytable payouts . Where, COINS : "+coinsText , "Paytable payouts . Where, COINS : "+coinsText, "", "");
		portraitAndLandscape(report ,CurrencyName );
		
		}
	   isPaytableClose();
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
        e.printStackTrace();
    }
    return demo;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean paytablePayoutsByChangingTheCoinSizes(Mobile_HTML_Report report,String regExpr,String CurrencyName) 
{
	boolean payoutvalues = false;
	try
	{
		if(xpathMap.get("PaytablePayoutsofEleven").equalsIgnoreCase("yes"))
		{
		 payoutvalues =   verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfEleven(report,CurrencyName)); 
		 return payoutvalues;
		}
		else if(xpathMap.get("PaytablePayoutsofThirteen").equalsIgnoreCase("yes")) 
		{
			payoutvalues =	verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfThirteen(report,CurrencyName));
			return payoutvalues;
		}
		else if(xpathMap.get("PaytablePayoutsofTen").equalsIgnoreCase("yes"))
		{
			payoutvalues =	verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfTen(report,CurrencyName));
			return payoutvalues;
		}
		else if(xpathMap.get("PaytablePayoutsofNine").equalsIgnoreCase("yes"))
		{
			payoutvalues =	verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfNine(report,CurrencyName));
			return payoutvalues;
		}
		else
		{
			System.out.println("Verify Paytable Payouts");log.debug("Verify Paytable Payouts");
		}
		Thread.sleep(2000);
		}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	return payoutvalues;
}
/**
 * method is to check the availability if an element 
 * @param string
 * @return
 * @author rk61073
 */
public boolean checkAvilabilityofElement(String element) 
{
	boolean isComponentAvilable = true;
	WebDriverWait wait;
	wait = new WebDriverWait(webdriver, 10);
	try 
	{
		setContext();
		
		if (osPlatform.equalsIgnoreCase("Android")) 
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get(element))));
		}
		else
		{
			WebElement ele = webdriver.findElement(By.xpath(xpathMap.get(element)));
			if(ele != null && ele.isDisplayed())
			{
				isComponentAvilable = true;
			}
		}
		
	}
	catch (Exception e) 
	{
		// if component in the game not avilable in it while give an exception
		isComponentAvilable = false;
	}
	return isComponentAvilable;
}
/**
 * method is to validate the credit , bet updation 
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public void validateCreditAmtUpdationAndBetAmtDeduction(Mobile_HTML_Report report, String languageCode)
{
	String getTheCreditAmtBeforeClickingOnDeal ="";
	String getTheBetAmtBeforeClickingOnDeal="";
	String getTheCreditAmtAfterClickingOnDeal ="";
	String getTheTotalWinAmtAfterClickingOnDeal ="";
	String getTheCreditAmtAfterClickingOnCollectBtn ="";
	
	try
	{
		if(checkAvilabilityofElement("DealButton"))
		{
			getTheCreditAmtBeforeClickingOnDeal= fun_GetText("CreditAmount");
			Double getCreditAmtBeforeClickingOnDeal = Double.parseDouble(getTheCreditAmtBeforeClickingOnDeal.replaceAll("[^0-9]", ""));
			log.debug("Credit amt before clicking on deal  : " +getCreditAmtBeforeClickingOnDeal);
			
			getTheBetAmtBeforeClickingOnDeal= fun_GetText("BetAmount");
			Double getBetAmtBeforeClickingOnDeal = Double.parseDouble(getTheBetAmtBeforeClickingOnDeal.replaceAll("[^0-9]", ""));
			log.debug("Bet amt before clicking on deal    : " +getBetAmtBeforeClickingOnDeal);
			
			dealBtn();
			
		   double creditAmt = (getCreditAmtBeforeClickingOnDeal - getBetAmtBeforeClickingOnDeal);
		   log.debug("Credit Amt minus bet amt after clicking on deal  : " +creditAmt);
		   
		    getTheCreditAmtAfterClickingOnDeal= fun_GetText("CreditAmount");
		    Double getCreditAmtAfterClickingOnDeal = Double.parseDouble(getTheCreditAmtAfterClickingOnDeal.replaceAll("[^0-9]", ""));
		    log.debug("Credit amt after clicking on deal : " +getCreditAmtAfterClickingOnDeal);
		  
			if(getCreditAmtAfterClickingOnDeal == creditAmt )
			{
				log.debug("Credit is deducing correctly from bet amt on clicking on deal btn : PASS ");
				report.detailsAppendFolder("Verify credit amt is deducing correctly from bet amt on clicking on deal btn","Credit amt is deducing correctly from bet amt on clicking on deal btn","Credit amt is deducing correctly from bet amt on clicking on deal btn","PASS",""+languageCode);
			}
			else
			{
				System.out.println("Credit is deducing correctly from bet amt on clicking on deal btn : FAIL ");log.debug("Credit is deducing correctly from bet amt on clicking on deal btn : FAIL ");
				report.detailsAppendFolder("Verify credit amt is deducing correctly from bet amt on clicking on deal btn","Credit amt is deducing correctly from bet amt on clicking on deal btn","Credit amt is deducing correctly from bet amt on clicking on deal btn","FAIL",""+languageCode);
			}
			
			drawBtn();Thread.sleep(2000);NativeClickById("moduleContent");
			
			//if collect button available
			if(checkAvilabilityofElement("CollectButton"))
			{
			getTheTotalWinAmtAfterClickingOnDeal= fun_GetText("TotalWinAmount");
			double getTotalWinAmtAfterClickingOnDeal = Double.parseDouble(getTheTotalWinAmtAfterClickingOnDeal.replaceAll("[^0-9]", ""));
			log.debug("Total Win amt on clicking on the deal : " +getTotalWinAmtAfterClickingOnDeal);
			
			double totalWinOnDealClick = (getCreditAmtAfterClickingOnDeal + getTotalWinAmtAfterClickingOnDeal);
			log.debug("Total Win   : " +totalWinOnDealClick);
			
			  NativeClickByXpath("CollectButton","CollectBtnFromDouble");
			 checkAvilabilityofElement("IsDealButtonEnabled");Thread.sleep(2000);
			 
			 getTheCreditAmtAfterClickingOnCollectBtn= fun_GetText("CreditAmount");
		     double getCreditAmtAfterClickingOnCollectBtn = Double.parseDouble(getTheCreditAmtAfterClickingOnCollectBtn.replaceAll("[^0-9]", ""));
		     log.debug("Creditss after clicking on deal : " +getCreditAmtAfterClickingOnCollectBtn);
			 
			if(totalWinOnDealClick == getCreditAmtAfterClickingOnCollectBtn )
			{
				log.debug("After win, credit amt is updating correctly  : PASS ");
				report.detailsAppendFolder("After win, credit amt is updating correctly","Credit amt is updating correctly after win","Credit amt is updating correctly after win","PASS",""+languageCode);
			}
			else
			{
				System.out.println("After win, credit amt is updating correctly  : FAIL ");log.debug("After win, credit amt is updating correctly  : FAIL ");
				report.detailsAppendFolder("After win, credit amt is updating correctly","Credit amt is updating correctly after win","Credit amt is updating correctly after win","FAIL",""+languageCode);
			}	}
			//if deal button available
			else
			{
				getTheTotalWinAmtAfterClickingOnDeal= fun_GetText("TotalWinAmount");
				double getTotalWinAmtAfterClickingOnDeal = Double.parseDouble(getTheTotalWinAmtAfterClickingOnDeal.replaceAll("[^0-9]", ""));
				System.out.println("Total Win amt on clicking on deal : " +getTotalWinAmtAfterClickingOnDeal);
				
				double totalWinOnDealClick = (getCreditAmtAfterClickingOnDeal + getTotalWinAmtAfterClickingOnDeal);
				System.out.println("Total Win   : " +totalWinOnDealClick);
				
				 checkAvilabilityofElement("IsDealButtonEnabled");Thread.sleep(3000);
				 
				 getTheCreditAmtAfterClickingOnCollectBtn= fun_GetText("CreditAmount");
			     double getCreditAmtAfterClickingOnCollectBtn = Double.parseDouble(getTheCreditAmtAfterClickingOnCollectBtn.replaceAll("[^0-9]", ""));
				 System.out.println("Credits after clicking on deal : " +getCreditAmtAfterClickingOnCollectBtn);
				 
			    if(totalWinOnDealClick == getCreditAmtAfterClickingOnCollectBtn )
				{
					System.out.println("After win, credit amt is updating correctly  : PASS ");
					report.detailsAppendFolder("After win, credit amt is updating correctly","Credit amt is updating correctly after win","Credit amt is updating correctly after win","PASS",""+languageCode);
				}
				else
				{
					System.out.println("After win, credit amt is updating correctly  : FAIL ");log.debug("After win, credit amt is updating correctly  : FAIL ");
					report.detailsAppendFolder("After win, credit amt is updating correctly","Credit amt is updating correctly after win","Credit amt is updating correctly after win","FAIL",""+languageCode);
				}	
			}
		}
		   }
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	}
/**
 * method is to rotate to portrait and landscape 
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public void portraitAndLandscape(Mobile_HTML_Report report, String CurrencyName)
{
	try 
	{
	
	boolean landscape=funcLandscape();
	if(landscape)
	{
		 log.debug("Landscape Modes : PASS "); 
		 report.detailsAppendFolder("Verify Landscape Mode","Landscape Mode","Landscape Mode","PASS",""+CurrencyName);
	}
	else
	{
		if(funcLandscape())
		{
			log.debug("Landscape Modes : PASS "); 
			 report.detailsAppendFolder("Verify Landscape Mode","Landscape Mode","Landscape Mode","PASS",""+CurrencyName);
		}
		else
		{
		System.out.println("Landscape Mode : FAIL "); log.debug("Landscape Mode : FAIL "); 
		report.detailsAppendFolder("Verify Landscape Mode","Landscape Mode","Landscape Mode","FAIL",""+CurrencyName);
		}
		}Thread.sleep(2000);

	boolean portrait= funcPortrait();
	if(portrait)
	{
		 log.debug("Portrait Mode : PASS "); 
		 report.detailsAppendFolder("Verify Portrait Mode","Portrait Mode","Portrait Mode","PASS",""+CurrencyName);
	}
	else
	{
		if(funcPortrait())
		{
			log.debug("Portrait Mode : PASS "); 
			 report.detailsAppendFolder("Verify Portrait Mode","Portrait Mode","Portrait Mode","PASS",""+CurrencyName);
		}
		else
		{
		System.out.println("Portrait Mode : FAIL "); log.debug("Portrait Mode : FAIL "); 
		report.detailsAppendFolder("Verify Portrait Mode","Portrait Mode","Portrait Mode","FAIL",""+CurrencyName);
		}
		}Thread.sleep(2000);
	
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}			
}
/**
 * method is to tap on co-ordinates
 * @author rk61073
 */
public void tapOnCoordinates(AppiumDriver<WebElement> webdriver, final double x, final double y)
{
	// new TouchAction(webdriver).tap((int)x, (int)y).perform();
	// int X=(int) Math.round(x); // it will round off the values
	// int Y=(int) Math.round(y);
	System.out.println("X cor - " + x + "," + " Y cor - " + y);
	System.out.println(x);
	System.out.println(y);
	TouchAction action = new TouchAction(webdriver);

	action.press(PointOption.point((int) x, (int) y)).release().perform();

	// action.tap(PointOption.point(X, Y)).perform();

}
/**
 * method is used to click on the webelements
 * @author rk61073
 */
public void clickWithTheWebElementAndriod(AppiumDriver<WebElement> webdriver,WebElement webViewElement,int Xcoordinate_AddValue) 
{
	String s = null;
try 
{
			
	JavascriptExecutor javaScriptExe = (JavascriptExecutor) webdriver;			  	
	int webViewWidth = ((Long) javaScriptExe.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
	int webViewHeight = ((Long) javaScriptExe.executeScript("return window.innerHeight || document.body.clientHeight")).intValue();
	Dimension elementSize = webViewElement.getSize();			  		
	int webViewElementCoX = webViewElement.getLocation().getX() + (elementSize.width / 2);
	int webViewElementCoY = webViewElement.getLocation().getY() + (elementSize.height / 2);		

	double   connectedDeviceWidth = typeCasting(javaScriptExe.executeScript("return window.screen.width * window.devicePixelRatio"),webdriver); 
	double   connectedDeviceHeight = typeCasting(javaScriptExe.executeScript("return window.screen.height * window.devicePixelRatio"),webdriver);

	log.debug("webViewHeight "+webViewHeight);
	log.debug("connectedDeviceHeight "+connectedDeviceHeight);
	
	
	
	int webViewInnerHeight = ((Long) javaScriptExe
			.executeScript("return window.innerHeight || document.body.clientHeight")).intValue();
	log.debug("webViewInnerHeight " + webViewInnerHeight);
	int webViewOuterHeight = ((Long) javaScriptExe
			.executeScript("return window.outerHeight || document.body.clientHeight")).intValue();
	log.debug("webViewOuterHeight " + webViewOuterHeight);
	int webViewOffsetHeight = ((Long) javaScriptExe
			.executeScript("return window.offsetHeight || document.body.clientHeight")).intValue();
	log.debug("webViewOffsetHeight " + webViewOffsetHeight);
	int webViewBottomHeight = webViewOffsetHeight - webViewInnerHeight;
	log.debug("webViewBottomHeight " + webViewBottomHeight);
	
	AndroidDriver<WebElement> androidDriver =(AndroidDriver<WebElement>) webdriver;
	
	
	long statusBarHeight=(Long)androidDriver.getCapabilities().getCapability("statBarHeight");
	log.debug("statusBarHeight "+statusBarHeight);
	
	double pixelRatio =0 ;
	Object pixelRatioObject = androidDriver.getCapabilities().getCapability("pixelRatio");
	
	if(pixelRatioObject instanceof Long ){
		 pixelRatio=(Long)androidDriver.getCapabilities().getCapability("pixelRatio");
		
	}else if (pixelRatioObject instanceof Double ) {
		 pixelRatio=(Double)androidDriver.getCapabilities().getCapability("pixelRatio");
	}
	
	
	/***************************************/
	double navigationBarHeight=0;
	double urlHeight=0.0;
			
	
	
	double webViewOffsetHeightInPixel= webViewOffsetHeight*pixelRatio;
	
	// to check the game in fullscreen
	double hightDifference = connectedDeviceHeight - webViewOffsetHeightInPixel;
	
	
	boolean isGameInFullScreenMode = (hightDifference < 2 && hightDifference > -2 );
	log.debug(" isGameInFullScreenMode ::"+isGameInFullScreenMode);
	
	if(!isGameInFullScreenMode)
	{
		if(webViewOffsetHeight!=0) 
		{
		// Calculate the navigation Bar height and urlHeigt
		navigationBarHeight = connectedDeviceHeight - (webViewOffsetHeightInPixel + statusBarHeight);

		log.debug("navigationBarHeight " + navigationBarHeight);
		urlHeight = ((webViewOffsetHeight - webViewHeight) * pixelRatio) + statusBarHeight;
		log.debug(" urlHeight and  statusBarHeight " + urlHeight);
		}
		else 
		{
			navigationBarHeight=0;
			urlHeight = connectedDeviceHeight-((webViewHeight * pixelRatio));
		}
	}
	
	/***************************************/
	
	
	String curContext=webdriver.getContext();
	webdriver.context("NATIVE_APP");
	
	
	/*if(xpathMap.get("serviceUrl").equalsIgnoreCase("yes"))
		urlHeight = (double) screenHeight * (0.12);
	else if(xpathMap.get("serviceUrl").equalsIgnoreCase("no"))
		urlHeight = 0.0;
	else
		urlHeight = (double) screenHeight * (0.22);	*/
		//urlHeight=0.0;		
		double relativeScreenViewHeight = connectedDeviceHeight - urlHeight-navigationBarHeight;	
		double nativeViewEleX = (double) (((double) webViewElementCoX / (double) webViewWidth) * connectedDeviceWidth);
		//double nativeViewEleY = (double) (((double) webViewElementCoY / (double) webViewHeight) );			  		
		double nativeViewEleY = (double) (((double) webViewElementCoY / (double) webViewHeight)* relativeScreenViewHeight);
		
		tapOnCoordinates(webdriver,nativeViewEleX+Xcoordinate_AddValue, (nativeViewEleY + urlHeight + 1));	
		webdriver.context(curContext);
	} catch (Exception e)
	{
		e.getStackTrace();
	}
}
/**
 * Click with Web Element using the co-ordinates
 * @author rk61073
 */
public void clickWithWebElementAndroid (AppiumDriver<WebElement> webdriver,WebElement webViewElement,int Xcoordinate_AddValue)
{
	String s=null;
	try 
	{
	double urlHeight=0.0;
	JavascriptExecutor javaScriptExe = (JavascriptExecutor) webdriver;			  	
	int webViewWidth = ((Long) javaScriptExe.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
	int webViewHeight = ((Long) javaScriptExe.executeScript("return window.innerHeight || document.body.clientHeight")).intValue();
	Dimension elementSize = webViewElement.getSize();			  		
	int webViewElementCoX = webViewElement.getLocation().getX() + (elementSize.width / 2);
	int webViewElementCoY = webViewElement.getLocation().getY() + (elementSize.height / 2);		

	double   connectedDeviceWidth = typeCasting(javaScriptExe.executeScript("return window.screen.width * window.devicePixelRatio"),webdriver); 
	double   connectedDeviceHeight = typeCasting(javaScriptExe.executeScript("return window.screen.height * window.devicePixelRatio"),webdriver);

	String curContext=webdriver.getContext();
	webdriver.context("NATIVE_APP");
	
	//urlHeight = (double) connectedDeviceHeight * (0.12);
	/*if(xpathMap.get("serviceUrl").equalsIgnoreCase("yes"))
		urlHeight = (double) screenHeight * (0.12);
	else if(xpathMap.get("serviceUrl").equalsIgnoreCase("no"))
		urlHeight = 0.0;
	else
		urlHeight = (double) screenHeight * (0.22);	*/
		//urlHeight=0.0;		
		double relativeScreenViewHeight = connectedDeviceHeight - urlHeight;	
		double nativeViewEleX = (double) (((double) webViewElementCoX / (double) webViewWidth) * connectedDeviceWidth);
		//double nativeViewEleY = (double) (((double) webViewElementCoY / (double) webViewHeight) );			  		
		double nativeViewEleY = (double) (((double) webViewElementCoY / (double) webViewHeight)* relativeScreenViewHeight);
		
		tapOnCoordinates(webdriver,nativeViewEleX+Xcoordinate_AddValue, (nativeViewEleY + urlHeight + 1));	
		webdriver.context(curContext);
}
catch(Exception e)
{
	e.printStackTrace();
}
}
/**
 * method is to click the WebElements using Xpath 
 * @author rk61073
 */	
public boolean NativeClickByXpath(String locator) 
{
boolean click = false;
try 
{
		if(fun_Click(locator))	
		{
			click = true;
		}
		else
		{
			click = false;
		}
	} 
	catch (Exception e) 
	{
		System.out.println("Unable to click  ");log.debug("Unable to click");
		click = false;
	}
	return click;
}
/**
 * method is to click the WebElements using Xpath 
 * @author rk61073
 */	
public boolean NativeClickByXpath(String locator, String imgName ) 
{
boolean click = false;//AppiumDriver<WebElement> webdriver,String GameName
try 
{

	if (osPlatform.equalsIgnoreCase("Android")) 
	{	
		if(fun_Click(locator))	
		{click = true;}
		else if(imgClick(webdriver,GameName,imgName))
		{click = true;}
		else{click = false;}
	}
	
	else 
	{
		if(imgClick(webdriver,GameName,imgName))	
		{click = true;}
		else if(fun_Click(locator))
		{click = true;}
		else{click = false;}
	}
			
	} 
	catch (Exception e) 
	{
		System.out.println("Unable to click  ");log.debug("Unable to click");
		click = false;
	}
	return click;
}
/**
 * method is to click the WebElements using Xpath 
 * @author rk61073
 */	
public boolean NativeClickById(String locator,String imgName ) 
{
	boolean click = false;
Wait = new WebDriverWait(webdriver, 10);
try 
{
	if (osPlatform.equalsIgnoreCase("Android")) 
	{	
		if(fun_ClickByID(locator))	
		{click = true;}
		else if(imgClick(webdriver,GameName,imgName))
		{click = true;}
		else{click = false;}
	}
	else 
	{
		if(imgClick(webdriver,GameName,imgName))	
		{click = true;}
		else if(fun_ClickByID(locator))
		{click = true;}
		else{click = false;}
	}
	} 
	catch (Exception e) 
	{
		System.out.println("Unable to click  ");log.debug("Unable to click");
		click = false;
	}
	return click;
}
/**
 * method is to click the WebElements using id 
 * @author rk61073
 */	
public boolean NativeClickById(String locator) 
{
boolean present = false;
try 
{	
	if(fun_ClickByID(locator))
	{
		present = true;
	}
	else
	{
		present = false;
	}
	
} 
	catch (Exception e) 
	{
		log.debug(e.getMessage());
		present = false;
	}
	return present;
}
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean dealBtn() throws InterruptedException
{
	boolean deal = false;
	try 
	{
		log.debug("Finding deal button on the page");
		//checkAvilabilityofElement("DealButton");
		NativeClickByXpath("DealButton","DealBtn");Thread.sleep(2000);
		log.debug("Successfully clicked on Deal button");
		log.debug("Finding draw button on the page");
		fun_ClickByID("checkInterupt");
		//interrupt();
		//checkAvilabilityofElement("IsDrawBtnEnabled");
		deal = true;
	} 
	catch (Exception e)
	{
		System.out.println("Error while clicking on deal button : "+e);
		log.error("Error while clicking on deal button", e);deal = false;
	}
	return deal;
}
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean drawBtn() throws InterruptedException 
{
	WebDriverWait wait;
	boolean draw = false;
	try 
	{
		//wait = new WebDriverWait(webdriver, 5);
    	//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("draw"))));
		NativeClickByXpath("DrawButton","DrawBtn");
		Thread.sleep(3000);
		log.debug("Successfully clicked on draw button");
		fun_ClickByID("checkInterupt");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("collectBtn"))));
		//NativeClickById("moduleContent");
		// interrupt();
		//wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathMap.get("collectBtn"))));
		//checkAvilabilityofElement("IsCollectBtnEnabled");
		 draw = true;
     }
   catch (Exception e)
	{
		System.out.println("Error while clicking on draw button : "+e);
		log.error("Error while clicking on draw button", e);
	}
	return draw;
}
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean payTableClose() throws InterruptedException 
{
	boolean paytable = false;
	try 
	{		
		//checkAvilabilityofElement("IsCollectButtonEnabled");	
		//checkAvilabilityofElement("PaytableClose");
		if(NativeClickByXpath("PaytableClose","PaytableCloseBtn"))
		{
		   log.debug("Click on paytableclose button : PASS ");
		   paytable = true;
		}
		else
		{
			System.out.println("Click on paytableclose button : FAIL ");
			log.debug("Click on paytableclose button : FAIL ");
			paytable = false;
		}
	}
	catch (Exception e) 
	{
		System.out.println("Error while clicking on paytableclose button : "+e);
		log.error("Error while clicking on paytableclose button", e);
	}
	return paytable;
}
/**
 * method is to slide to  max bet
 * @return
 * @author rk61073
 */
public boolean slideTheCoinSizeToMax()
{	boolean setCoinSizeToMaxBet= false;
	try 
		{ 
		
		setContext();
		
		if (osPlatform.equalsIgnoreCase("Android")) 
		{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("coinsizeslider")))) != null)
		{
		    WebElement coinsize = webdriver.findElement(By.xpath(xpathMap.get("coinsizeslider")));
			Actions action = new Actions(webdriver);
			String bet_attr = coinsize.getAttribute("style");log.debug(bet_attr.trim());Thread.sleep(2000);
			if (!bet_attr.equals("left: 100%;")) 
			{			
				Thread.sleep(2000);
		        action.dragAndDropBy(coinsize, 200, 0).build().perform(); 
		        log.debug("Slided the coin size slider to max : PASS ");
				String bet_attrAftr = coinsize.getAttribute("style");log.debug(bet_attrAftr.trim());
				Thread.sleep(2500);setCoinSizeToMaxBet = true;
				
				if(!bet_attrAftr.equals("left: 100%;"))
				{
					action.dragAndDropBy(coinsize, 100, 0).build().perform();
					coinsize.click();
					
					log.debug(bet_attrAftr.trim());log.debug("Slided the coin size slider to max : PASS ");
					setCoinSizeToMaxBet = true;
				}
			} 
			else
			{
				setCoinSizeToMaxBet = true;// if already set max bet then make flag as true
			}
		}
		else
		{
			log.debug("Slided the coinsize to max : FAIL ");System.out.print("Slided the coinsize to max : FAIL ");setCoinSizeToMaxBet = false;
		}}
		else
		{
			//for iOS
		System.out.println("11");
		WebElement coinsize = webdriver.findElement(By.xpath(xpathMap.get("isit")));
		WebElement check2 = webdriver.findElement(By.xpath(xpathMap.get("Check2")));
		WebElement check3 = webdriver.findElement(By.xpath(xpathMap.get("Check3")));
		 /*Actions action1 = new Actions(webdriver);
		action1.dragAndDropBy(coinsize, 50, 0).build().perform();
		  Thread.sleep(2000);
		  action1.click(coinsize);*/
		  
			//WebElement coinsize = webdriver.findElement(By.xpath(xpathMap.get("isit")));
			JavascriptExecutor js = (JavascriptExecutor) webdriver;
			  js.executeScript("arguments[0].setAttribute('style', 'left: 100%;')",coinsize);
			  
			  WebDriverWait  Wait = new WebDriverWait(webdriver, 60);
			  Thread.sleep(4000);
			  Actions action = new Actions(webdriver);
			  /*Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("Check2")))) ;
			  action.dragAndDropBy(check2, 100, 0).build().perform();
			  action.dragAndDropBy(check3, 100, 0).build().perform();
			  action.dragAndDropBy(coinsize, 100, 0).build().perform();
			  Thread.sleep(4000);*/
			 		
			  Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("Check2")))) ;
			  Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("Check2"))));
			  action.click(check2); Thread.sleep(4000);
			  
			  Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("Check3")))) ;
			  Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("Check3"))));
			  //action.click(coinsize); Thread.sleep(4000);
			//  action.click(check2); Thread.sleep(4000);
			  action.click(check3); Thread.sleep(4000);
			  
			//  NativeClickByXpath("isit","CoinsBtn");
			  js.executeScript("arguments[0].click();", coinsize);
			  js.executeScript("document.getElementsByClassName('range-slider__thumb')[0].click");
			  js.executeScript("arguments[1].click();", coinsize);
			//  js.executeScript("document.getElementsByClassName('range-slider__thumb')[0].click");
			  js.wait(1000);
		
			
	
			  clickAtButton("return "+xpathMap.get("Slide"));
			  
			//  WebElement coinsize = webdriver.findElement(By.xpath(xpathMap.get("isit")));
			  coinsize.click();
			  
			  
			 clickAtButton("return "+xpathMap.get("click1"));
			  clickAtButton("return "+xpathMap.get("click2"));
			  
			  /*for(int i=0;i<=5;i++)
			  { 
				
			  fun_Click("isit");
		
			  }*/
			  clickWithTheWebElementAndriod(webdriver, coinsize, 0);
			  clickWithWebElementAndroid(webdriver, coinsize,0);
			  
			
			  
			  
			  
			  
			  Actions actions = new Actions(webdriver);
			  DesiredCapabilities capabilities = new DesiredCapabilities();
			  capabilities.setCapability("platformName", "iOS");
			  capabilities.setCapability("platformVersion", "14.5");
			  capabilities.setCapability("deviceName", "iPhone 12");
			  capabilities.setCapability("automationName", "XCUITest");
			  capabilities.setCapability("app", "/path/to/your/app.ipa");
			  URL appiumUrl = new URL("http://localhost:4723/wd/hub");
			  IOSDriver<IOSElement> driver = new IOSDriver<>(appiumUrl, capabilities);
			  
			  IOSElement source = driver.findElement(By.id("source-element"));
			  IOSElement destination = driver.findElement(By.id("destination-element"));

			 
			 // TouchAction touch = new TouchAction(driver);
			  
			
			 // tap(source).moveTo(destination).release().perform();

			  //actions.press(source).moveTo(destination).release().perform();



			  actions.dragAndDropBy(coinsize, 50, 0).build().perform();
			  Thread.sleep(2000);
			  actions.click(coinsize).build().perform();
			  actions.moveToElement(coinsize).click();
			  actions.moveToElement(coinsize).build().perform();
			
			  actions.moveToElement(coinsize).clickAndHold().moveByOffset(100,0).release().perform();
				Thread.sleep(3500);
				 actions.dragAndDropBy(coinsize, 50, 0).build().perform();
			  
			  
		}
		}
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}return setCoinSizeToMaxBet;}
/**
 * method is used to click  the x ele& y co-ordinates using actions class
 * @param x
 * @param y
 * @author rk61073
 */
public void tapOnCoordinates(String locator, int x, int y)
{
	try
	{
	Actions act = new Actions(webdriver);
	WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(locator))); 
    //act.moveByOffset(x,y).doubleClick();
	act.moveByOffset(x,y).click();
    act.build().perform(); 
    }
    catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
}
/**
 * method is to slide to  max bet
 * @author rk61073
 */
public boolean slideTheCoinsToMax()
{
  boolean setCoinsToMaxBet = false; 
  for(int i=0 ;i<=10;i++)
	{
try 
{
	setContext();
	if (osPlatform.equalsIgnoreCase("Android")) 
	{	
 WebElement coins = webdriver.findElement(By.xpath(xpathMap.get("coinslider")));
 if(coins.isDisplayed() && coins != null)
 {   
	Thread.sleep(2000);Actions action = new Actions(webdriver);
    String bet_attr = coins.getAttribute("style");
    log.debug(bet_attr.trim());Thread.sleep(2000);
	if (!bet_attr.equals("left: 100%;")) 
	{
		setContext();
		Thread.sleep(2000);action.dragAndDropBy(coins, 150, 0).build().perform(); 
		String bet_attrAftr = coins.getAttribute("style");
		log.debug(bet_attrAftr.trim());log.debug("Slided the coins slider to max : PASS ");
		Thread.sleep(2500);
		setCoinsToMaxBet = true;
		
		if(!bet_attrAftr.equals("left: 100%;"))
		{
			action.dragAndDropBy(coins, 50, 0).build().perform();
			log.debug(bet_attrAftr.trim());log.debug("Slided the coins slider to max : PASS ");
			setCoinsToMaxBet = true;
		}}
	else 
	{
		setCoinsToMaxBet = true;// if already set max bet then make flag as true
	}}
   else
    {
	   log.debug("Slided the coins slider to max : FAIL ");System.out.print("Slided the coins slider to max : FAIL "); setCoinsToMaxBet = false;
	 }}
	else
	{
		//for iOS
		
		By slider11= By.xpath("window.gsap.to(document.getElementsByClassName(\"range-slider__thumb\"),1,{left:\"100%\"})\r\n"
				+ "");
		// Set Loop counter to get desired value
		//((WebElement) slider11).sendKeys(Keys.ARROW_LEFT); // Or ARROW_RIGHT
		// End loop
		
		Actions action = new Actions(webdriver);
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		
		WebElement buttonElement = webdriver.findElement(By.cssSelector(".range-slider")); 
		js.executeScript("arguments[0].click();", buttonElement);
		
		  clickAtButton("return "+xpathMap.get("Slide"));
		  
		  System.out.println("This is making it slide");
		  
		  
		  
		js.executeScript("window.scrollTo(0,100)");
		  clickAtButton("return "+xpathMap.get("Slide1"));
		  action.click();
		action.doubleClick(webdriver.findElement(By.xpath("//*[@id=\"betSettingsCoins\"]/div[3]/div[2]/div[contains(@style,'left: 100%;')]"))).perform();
       // SynthSliderUI.MAX_SCROLL
		action.doubleClick(webdriver.findElement(By.xpath("//*[@id=\"betSettingsCoins\"]/div[3]/div[2]/div[contains(@style,'left: 100%;')]"))).click();
		action.doubleClick(webdriver.findElement(By.xpath("//*[@id=\"betSettingsCoins\"]/div[3]/div[2]/div[contains(@style,'left: 100%;')]"))).click();

	}
} 
catch (Exception e)
{
	log.debug(e.getMessage());
}}
return setCoinsToMaxBet;}
/**
 * 
 * @return
 * @author rk61073
 */
public void closeOverlay()
{
	try
	{
		if (osPlatform.equalsIgnoreCase("iOS")) 
		{
            TouchAction touchAction = new TouchAction(webdriver);
            touchAction.tap(PointOption.point(120, 120)).perform();
        }
		else
		{
            Actions act = new Actions(webdriver);
            act.moveByOffset(10, 60).click().build().perform();
            act.moveByOffset(-20, -80).build().perform();
       }
	}
	catch(Exception e)
	{
		e.getMessage();	
	}
}
/**
 * 
 * @return
 * @author rk61073
 */
public void closeOverlayPortraitAndLandscape()
{
    try
    {
    if (osPlatform.equalsIgnoreCase("iOS"))
    {
        if(webdriver.getOrientation().equals(ScreenOrientation.PORTRAIT))
        {
            TouchAction touchAction = new TouchAction(webdriver);
            touchAction.tap(PointOption.point(40, 120)).perform();
        }
        else
        {
            TouchAction touchAction = new TouchAction(webdriver);
            touchAction.tap(PointOption.point(50, 80)).perform();
        }
    }
    else
    {// For Andriod mobile
        
        Actions act = new Actions(webdriver);
        act.moveByOffset(20, 80).click().build().perform();
        act.moveByOffset(-20, -80).build().perform();
    }
    }
     catch (Exception e)
    {
            log.error("error while closing overlay ", e);
            log.error(e.getMessage());
    }
}
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean doubleBtn() throws InterruptedException 
{
	boolean doubleBtn = false;
	try 
	{
		//checkAvilabilityofElement("IsCollectButtonEnabled");	
		//checkAvilabilityofElement("DoubleButton");	
		if(NativeClickByXpath("DoubleButton","DoubleBtn"))
		{
			log.debug("Clicked on double button"); Thread.sleep(5000);
			doubleBtn = true;
		}
		else
		{
			System.out.println("Unable to clicked on double button");log.debug("Unable to clicked on double button");
			doubleBtn = false;
		}
		//checkAvilabilityofElement("DefaultDoubleCard");	
		
	} catch (Exception e)
	{
		System.out.println("Error while clicking on collect button : "+e);
		log.error("Error while clicking on collect button", e);
	}
	return doubleBtn;
}
/**
 * verify if the gamble limit reached or not , currency format , credit is updated correctly or not in double and in base after the win 
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public void gambleLimitReachedForLVC(Mobile_HTML_Report report,String regExpr, String currencyName)
{
	 String getcreditAmtBeforeWin ="";
	 String getCongratsWinAmount  = "";
	 double creditValidation = 0.0;
	 String getCreditAmountAfterWin = "";
	 double getcreditsAmtBeforeWin =0.0;
	 String  getcreditAmt =""; 	 
try
{
	 while(!checkAvilabilityofElement("GambleLimitReachedForLVC"))
	    {
		   /*if(checkAvilabilityofElement("DefaultDoubleCard"))
			{*/	Thread.sleep(3000);
		   log.debug("Moved to double screen : PASS");
		   
		     getcreditAmtBeforeWin = fun_GetText("CreditAmountFromDoubleTo");
			 getcreditsAmtBeforeWin = Double.parseDouble(getcreditAmtBeforeWin.replaceAll("[^0-9]", ""));
			 log.debug(getcreditsAmtBeforeWin);System.out.println(getcreditsAmtBeforeWin);
			 
			 if(NativeClickByXpath("SelectDoubleCard","CardInDouble"))
			 {
			 log.debug("Card Selection in Double  : PASS");
			 if(checkAvilabilityofElement("GambleLimitReached")) 
			 {
				break; 
			 }
		 }//closing double to card click
		 else 
		 {
			 System.out.println("Card selection in Double : FAIL");log.debug("Card selection in Double : FAIL");
			 report.detailsAppendFolder("Card Selection in Double ", "Select the card in Double", "Select the card in Double", "FAIL", currencyName);
		 }/*}//closing validating default card in double to 
	   else 
	   {
		   System.out.println("Moved to double screen : FAIL");log.debug("Moved to double screen : FAIL");
		   report.detailsAppendFolder("Navigate to double  ", "Moved to double to screen", "Moved to double to screen", "FAIL", currencyName);
	   }*/
	   
	   checkAvilabilityofElement("TotalWinAmount"); NativeClickById("moduleContent");
	   /************click on double button**********/
		  if(NativeClickByXpath("DoubleToButton","DoubleToBtnFromDouble"))
		  {
			 log.debug("Clicked on double button : PASS");
		  }
		  else 
		  {
			  log.debug("Clicked on double button : FAIL");System.out.println("Clicked on double button : FAIL"); 
			  report.detailsAppendFolder("Click double button in double ", "Click on double button ", "Clicked on double button", "FAIL", currencyName);
		  }
		}//closing while loop 
	    
	/******************* Validate total win amt in double  ***********************/
	boolean totalWinAmtInDouble =verifyRegularExpression(report,regExpr,fun_GetText("DoubleToTotalwinAmt"));
	
	 if(checkAvilabilityofElement("GambleLimitReached"))
	   {
		 log.debug("Gamble limit reached : PASS");
		   if(checkAvilabilityofElement("IsBackToBaseGameButtonEnabled"))
		   {
			     log.debug("Verify  back button in double  : PASS");
			   
			   
			     getCongratsWinAmount = fun_GetText("CongratulationsWinAmt"); 
				 double getCongratsWinAmt = Double.parseDouble(getCongratsWinAmount.replaceAll("[^0-9]", ""));
				 log.debug(getCongratsWinAmt);System.out.println(getCongratsWinAmt);
				
				 creditValidation = (getcreditsAmtBeforeWin + getCongratsWinAmt);
				 log.debug(creditValidation);System.out.println(creditValidation);
				 
				 getCreditAmountAfterWin = fun_GetText("CreditAmountFromDoubleTo");
				 double getcreditAmtAfterWin = Double.parseDouble(getCreditAmountAfterWin.replaceAll("[^0-9]", ""));
				 log.debug(getcreditAmtAfterWin);System.out.println(getcreditAmtAfterWin);
				 
				 if(getcreditAmtAfterWin == creditValidation)
				 {
					 log.debug("After win where gamble limit reached , the Credit amt in double updated correctly : PASS");
					 report.detailsAppendFolder("After win where gamble limit reached , the Credit amt in double updated correctly","Credit amt in double updated correctly ","Credit amt in double updated correctly ","PASS",""+currencyName);
				 }
				 else
				 {
					 log.debug("After win , Credit amt in double updated correctly : FAIL"); System.out.println("After win , Credit amt in double updated correctly : FAIL");
					 report.detailsAppendFolder("After win where gamble limit reached , the Credit amt in double updated correctly","Credit amt in double updated correctly ","Credit amt in double updated correctly ","FAIL",""+currencyName);
				 }
			   
			   
			   /******************* Validate congratulations amt in double  ***********************/
				boolean congratulationsAmtInDouble = verifyRegularExpression(report,regExpr,fun_GetText("CongratulationsWinAmt"));
				
				String glr = "Total win amt when gamble limit reached : "+totalWinAmtInDouble+"  Congrants amt when gamble limit reached : "+congratulationsAmtInDouble;
				
				if(congratulationsAmtInDouble && totalWinAmtInDouble) 
				{
					log.debug("Validate congratulations and win amts when gamble limit reached : PASS");
					report.detailsAppendFolder("Validate congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached","PASS",""+currencyName);
				}
				else
				{
					log.debug("Verify congratulations amt when gamble limit reached : FAIL");System.out.println("Verify congratulations amt when gamble limit reached : FAIL");
					report.detailsAppendFolder("Validate congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached",""+glr,"FAIL",""+currencyName);	
				}
			   
			    report.detailsAppendFolder("Verify gamble limit reached", "Gamble limit reached", "Gamble limit reached", "PASS", currencyName);
			    closeOverlayPortraitAndLandscape();
				
				report.detailsAppendNoScreenshot("Scenario : Back to base game from double screen ", "Click on back to base game btn in double and moved to base game", "", ""); 
			  
				if (fun_GetText("GambleLimitReached") != null && NativeClickByXpath("BackToBaseGame","BackBtnFromDouble")) 
				{
					log.debug("Back to base game : PASS");
					
					if(checkAvilabilityofElement("DealButton"))
					{
						
					 getcreditAmt = fun_GetText("CreditAmountFromDoubleTo");
					 double getCreditAmt= Double.parseDouble(getcreditAmt.replaceAll("[^0-9]", ""));
					 log.debug(getCreditAmt);System.out.println(getCreditAmt);
					 
					 if(getCreditAmt == getcreditAmtAfterWin )
					 {
						 log.debug("After win in double to , Credit amt in double and in base is same and updated correctly : PASS");
						 report.detailsAppendFolder("After win in double to , Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","PASS",""+currencyName);
					 }
					 else
					 {
						 log.debug("After win in double to , Credit amt in double and in base is same and updated correctly : FAIL"); System.out.println("After win in double to , Credit amt in double and in base is same and updated correctly : FAIL");
						 report.detailsAppendFolder("After win in double to , Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","FAIL",""+currencyName);
					 }
						
					report.detailsAppendFolder("Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "Clicked on back to base game button in double to screen and moved to base game", "PASS", currencyName);
					closeOverlayPortraitAndLandscape();
					
					}
				} 
			    else
			    {
					System.out.println("Back to base game : FAIL");System.out.println("Back to base game  : FAIL");
					report.detailsAppendFolder("Verify back button", "Click on back button in double", "Click on back button in double", "FAIL", currencyName);
				} 
		   }
		   else
		   {
				System.out.println("Verify  back button in double : FAIL");System.out.println("GVerify  back button in double : FAIL");
				report.detailsAppendFolder("Verify  back button in double", "Back button in double", "Back button in double", "FAIL", currencyName);
			} 
	   }
	 else
	   {
			System.out.println("Gamble limitis reached  : FAIL");System.out.println("Gamble limitis reached  : FAIL");
			report.detailsAppendFolder("Verify gamble limit reached", "Gamble limitis reached", "Gamble limitis reached", "FAIL", currencyName);
		} 
			
			}
catch (Exception e) 
{
	log.debug(e.getMessage());
}}

/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean paytablePayoutsByChangingTheCoinSizes(Mobile_HTML_Report report,String CurrencyName) 
{
	boolean payoutvalues = false;
	try
	{
		
		if(xpathMap.get("PaytablePayoutsofThirteen").equalsIgnoreCase("yes"))
		{
		 payoutvalues =   paytablePayoutsOfThirteen(report,CurrencyName) != null; 
		 return payoutvalues;
		}
		if(xpathMap.get("PaytablePayoutsofEleven").equalsIgnoreCase("yes"))
		{
		 payoutvalues =   paytablePayoutsOfEleven(report,CurrencyName) != null; 
		 return payoutvalues;
		}
		else if(xpathMap.get("PaytablePayoutsofTen").equalsIgnoreCase("yes"))
		{
			payoutvalues =	paytablePayoutsOfTen(report,CurrencyName) != null;
			return payoutvalues;
		}
		else if(xpathMap.get("PaytablePayoutsofNine").equalsIgnoreCase("yes"))
		{
			payoutvalues =	paytablePayoutsOfNine(report,CurrencyName) != null;
			return payoutvalues;
		}
		else
		{
			System.out.println("Verify Paytable Payouts");log.debug("Verify Paytable Payouts");
		}
		Thread.sleep(2000);
		}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	return payoutvalues;
}
/**
 * validate gamble limit reached 
 * @author rk61073
 */
public void gambleLimitReached(Mobile_HTML_Report report, String currencyName)
{
try
{
	do
	{
		if(checkAvilabilityofElement("DefaultDoubleCard"))
		{
			if(NativeClickByXpath("SelectDoubleCard"))
			{
				Thread.sleep(3000);
				//NativeClickById("moduleContent");Thread.sleep(1000);
				log.debug("Card selection in Double� : PASS");
				if(webdriver.findElement(By.xpath(xpathMap.get("gambleLimit"))).isDisplayed())
				{
					break;
				}
			}//closing double to card click
			else
			{
				System.out.println("Cardd selection in Double TO : FAIL");
				log.debug("Card selection in Double TO : FAIL");
				report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", currencyName);
				}
			}//closing validating default card in double to
		else
		{
			System.out.println("Availabitity of open card in double to : FAIL");
			log.debug("Availabitity of open card in double to : FAIL");
			report.detailsAppendFolder("Verify availabitity of open card in double to", "Availabitity of open card in double to", "Availabitity of open card in double to", "FAIL", currencyName);
			}
		if(NativeClickByXpath("DoubleToButton","DoubleToBtnFromDouble"))
		{
			log.debug("Double To button in Double To : PASS");
		}
		else
		{
			System.out.println("Click on double button in double to : FAIL");
			report.detailsAppendFolder("Verify double button in double to", "Click on double button in double to", "Click on double button in double to", "FAIL", currencyName);
			}
		}while (!(webdriver.findElement(By.xpath(xpathMap.get("gambleLimit"))).isDisplayed()));
	if (fun_GetText("GambleLimitReached") != null && checkAvilabilityofElement("BackToBaseGame","BackBtnFromDouble") )
	{
		log.debug("Gamble limit text : PASS");portraitAndLandscape(report,currencyName);
	}
	else
	{
		System.out.println("Gamble limit text : FAIL");
		report.detailsAppendFolder("Verify gamble limit reached", "Gamble limit reached", "Gamble limit reached", "FAIL", currencyName);
		}
	report.detailsAppendNoScreenshot("Scenario : Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "", "");
	checkAvilabilityofElement("GambleLimitReach","BackBtnFromDouble");
	Thread.sleep(2000);
	if (NativeClickByXpath("BackToBaseGame","BackBtnFromDouble"))
	{
		Thread.sleep(2000);
		if(checkAvilabilityofElement("DealButton"))
		{
			log.debug("Back to base from double to screen : PASS");
			portraitAndLandscape(report,currencyName);
			}//closing deal button availability
	}//closing back to base game
	else
	{
		System.out.println("Back to base from double to screen : FAIL");
		log.debug("Back to base from double to screen : FAIL");
		report.detailsAppendFolder("Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "Clicked on back to base game button in double to screen and moved to base game", "FAIL", currencyName);
	}
	
	/*
	if(checkAvilabilityofElement("DefaultDoubleCard"))
	 {	
	 if(NativeClickByXpath("SelectDoubleCard","CardInDouble"))
	 {    
	   Thread.sleep(2000);NativeClickById("moduleContent");Thread.sleep(1000);
	  log.debug("Clicked on double button and moved to double to screen : PASS");Thread.sleep(2000);
	  report.detailsAppendNoScreenshot("Scenario : Verify double to after the card selection", "Double to after the card selection", "", ""); 
	  portraitAndLandscape(report,currencyName);
	  }
	 else 
	 {
		 System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
		 report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", currencyName);
	 }}
  else 
  {
	   System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
	   report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", currencyName);
  }
    
       boolean doubleBtn = NativeClickByXpath("DoubleToButton","DoubleToBtnFromDouble");
		if(doubleBtn) 
		{
			log.debug("Click on double btn  : PASS");
		}
		else
		{
			log.debug("Click on double btn : FAIL");System.out.println("Click on double btn : FAIL");
			report.detailsAppendFolder("Verify double button","Click on double button ","Click on double button ","FAIL",""+currencyName);
		}	
	
	*//******** Gamble limit reached  ************//*
		while(!checkAvilabilityofElement("GambleLimitReach"))
        {
	   if(checkAvilabilityofElement("DefaultDoubleCard"))
		{	
		 
		 if(NativeClickByXpath("SelectDoubleCard","CardInDouble"))
		 {
			 Thread.sleep(3000);NativeClickById("moduleContent");Thread.sleep(1000);
			 log.debug("Card selection in Double  : PASS");
			 if(checkAvilabilityofElement("GambleLimitReach")) 
			 {
				break; 
			 }
		 }//closing double to card click
		 else 
		 {
			 System.out.println("Cardd selection in Double TO : FAIL");log.debug("Card selection in Double TO : FAIL");
			 report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", currencyName);
		 }
		 }//closing validating default card in double to 
	   else 
	   {
		   System.out.println("Availabitity of open card in double to : FAIL");log.debug("Availabitity of open card in double to : FAIL");
		   report.detailsAppendFolder("Verify availabitity of open card in double to", "Availabitity of open card in double to", "Availabitity of open card in double to", "FAIL", currencyName);
	     }
	 
		  if(NativeClickByXpath("DoubleToButton","DoubleToBtnFromDouble"))
		  {
		    log.debug("Double To button in Double To : PASS");
		  }
		  else 
		  {
			  System.out.println("Click on double button in double to : FAIL"); 
			  report.detailsAppendFolder("Verify double button in double to", "Click on double button in double to", "Click on double button in double to", "FAIL", currencyName);
		  }
			}//closing while loop
	        Thread.sleep(2000);
			//report.detailsAppendNoScreenshot("Scenario : Verify gamble limit reached", "Gamble limit reached", "", ""); 
			if (fun_GetText("GambleLimitReached") != null) 
			{
				log.debug("Gamble limit text : PASS");
				portraitAndLandscape(report,currencyName);
			} 
			else
			{
				System.out.println("Gamble limit text : FAIL");
				report.detailsAppendFolder("Verify gamble limit reached", "Gamble limit reached", "Gamble limit reached", "FAIL", currencyName);
			}
			
			report.detailsAppendNoScreenshot("Scenario : Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "", ""); 
			checkAvilabilityofElement("GambleLimitReach"); Thread.sleep(2000);
			if (NativeClickByXpath("BackToBaseGame","BackBtnFromDouble"))
			{ Thread.sleep(2000);
				if(checkAvilabilityofElement("DealButton"))
				{      
					log.debug("Back to base from double to screen : PASS");
					portraitAndLandscape(report,currencyName);
				}//closing deal button availability 
			}//closing back to base game 
			else 
			{
				System.out.println("Back to base from double to screen : FAIL");log.debug("Back to base from double to screen : FAIL");
				report.detailsAppendFolder("Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "Clicked on back to base game button in double to screen and moved to base game", "FAIL", currencyName);
			}*/}
catch (Exception e) 
{
	log.debug(e.getMessage());
}	
}
/**
 * click on collect btn from double screen
 * @author rk61073
 */
public void collectFromDouble(Mobile_HTML_Report report,String languageCode)
{
try
{	if(checkAvilabilityofElement("DefaultDoubleCard","CardInDouble"))
	 {	
	 if(NativeClickByXpath("SelectDoubleCard"))
	 {    
	   Thread.sleep(2000);
	   //NativeClickById("moduleContent");Thread.sleep(1000);
	  // interrupt();
	  log.debug("Clicked on double button and moved to double to screen : PASS");Thread.sleep(2000);
	  report.detailsAppendNoScreenshot("Scenario : Verify double to after the card selection", "Double to after the card selection", "", ""); 
	   portraitAndLandscape(report,languageCode);
	  }
	 else 
	 {
		 System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
		 report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", languageCode);
	 }}
  else 
  {
	   System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
	   report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", languageCode);
  }
    
       boolean collectBtn = NativeClickByXpath("CollectButton","CollectBtnFromDouble");
		if(collectBtn) 
		{
			log.debug("Click on double btn  : PASS");
		}
		else
		{
			log.debug("Click on double btn : FAIL");System.out.println("Click on double btn : FAIL");
			report.detailsAppendFolder("Verify double button","Click on double button ","Click on double button ","FAIL",""+languageCode);
		}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}

}
/**
 * click on back btn from double screen when gamble limit reach
 * @author rk61073
 */
public void gambleLimitReachFromDouble(Mobile_HTML_Report report,String languageCode)
{
	try
	{
	     if(checkAvilabilityofElement("DefaultDoubleCard"))
	 	 {	
	 	 if(NativeClickByXpath("SelectDoubleCard"))
	 	 {    
	 	   Thread.sleep(2000);
	 	   //NativeClickById("moduleContent");Thread.sleep(1000);
	 	 // interrupt();
	 	  log.debug("Clicked on double button and moved to double to screen : PASS");Thread.sleep(2000);
	 	  report.detailsAppendNoScreenshot("Scenario : Verify gamble limit reach from double", "Gamble limit reach from double", "", ""); 
	 	  portraitAndLandscape(report,languageCode);
	 	  }
		 else 
		 {
			 System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
			 report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", languageCode);
		 }}
	   else 
	   {
		   System.out.println("Card Selection in double to : FAIL");log.debug("Card Selection in double to : FAIL");
		   report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", languageCode);
	   }
	      checkAvilabilityofElement("GambleLimitReach","BackBtnFromDouble"); Thread.sleep(2000);
	        boolean backBtn = NativeClickByXpath("BackToBaseGameBtn","BackBtnFromDouble");
			if(backBtn) 
			{
				log.debug("Click on double btn  : PASS");
			}
			else
			{
				log.debug("Click on double btn : FAIL");System.out.println("Click on double btn : FAIL");
				report.detailsAppendFolder("Verify double button","Click on double button ","Click on double button ","FAIL",""+languageCode);
			}}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}

}
/**
 * check if paytable avaiable and close the paytable
 * @author rk61073
 */
public void isPaytableClose()	
{
try
{
	if(xpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	{
		if(checkAvilabilityofElement("isPaytableVisible","PaytableCloseBtn"))
		{
		if(NativeClickByXpath("PaytableClose","PaytableCloseBtn"))
		{
			log.debug("Click on paytable close button : PASS");
		}}
	else 
	{	
		System.out.println("Click on paytable close button : FAIL");log.debug("Click on paytable close button : FAIL");
	}}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}
}

/**
 * click on collect btn from double screen
 * @author rk61073
 */
public void collectFromDoubleForLVC(Mobile_HTML_Report report,String CurrencyName,String regExpr)
{
	try
	{
		if(NativeClickByXpath("SelectDoubleCard","CardInDouble"))
		{ 
			NativeClickById("moduleContent");
			checkAvilabilityofElement("TotalWinAmount");
			log.debug("Select the card , wait until the double & collect button enable in double : PASS");
			/******************* Validate collect amt,double to amt , congratulations amt and win amt  in  double  ***********************/
			boolean collectAmtInDouble = verifyRegularExpression(report,regExpr,fun_GetText("DoubleToCollectAmt"));
			boolean doubleToAmtInDouble =verifyRegularExpression(report,regExpr,fun_GetText("DoubleDoubleToAmt"));
			boolean congratulationsAmtInDouble = verifyRegularExpression(report,regExpr,fun_GetText("CongratulationsWinAmt"));
			boolean totalWinAmtInDouble = verifyRegularExpression(report,regExpr,fun_GetText("DoubleToTotalwinAmt"));
			
			String amtsInDouble = "Collect amt in double : "+collectAmtInDouble+" Double to amt in double : "+doubleToAmtInDouble+" Congrats amt in double : "+congratulationsAmtInDouble+"  Win amt in double : "+totalWinAmtInDouble;
			
			if(collectAmtInDouble && doubleToAmtInDouble && congratulationsAmtInDouble && totalWinAmtInDouble) 
			{
				log.debug("Validate collect,double to ,congratulations and win amts in double : PASS");
				report.detailsAppendFolder("Validate collect,double to,congratulations and win amts in double after card selection","Collect,double to,congratulations and win amt in double after card selection","Collect,double to,congtatulatios and win amts in double after card selection","PASS",""+CurrencyName);
			}
			else
			{
				log.debug(amtsInDouble);System.out.println(amtsInDouble);
				report.detailsAppendFolder("Validate collect,double to,congratulations and win amts in double after card selection","Collect,double to,congratulations and win amt in double after card selection",""+amtsInDouble,"FAIL",""+CurrencyName);	
			}
			
		  portraitAndLandscape(report,CurrencyName);
		
			boolean doubleBtn = NativeClickByXpath("CollectButton","CollectBtn");
			if(doubleBtn) 
			{
				log.debug("Click on double btn  : PASS");
			}
			else
			{
				log.debug("Click on double btn : FAIL");System.out.println("Click on double btn : FAIL");
				report.detailsAppendFolder("Verify double button","Click on double button ","Click on double button ","FAIL",""+CurrencyName);
			}
		}//closing if loop
		else
		{
			log.debug("Select the card , wait until the double & collect button enable in double : FAIL");System.out.println("Select the card , wait until the double & collect button enable in double : FAIL");
			report.detailsAppendFolder("Verify select the card , wait until the double & collect button enable in double","Select the card , wait until the double & collect button enable in double","Select the card , wait until the double & collect button enable in double","FAIL",""+CurrencyName);	
		}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
}
/**
 * click on back btn from double screen when gamble limit reach
 * @author rk61073
 */
public void gambleLimitReachFromDoubleForLVC(Mobile_HTML_Report report,String CurrencyName,String regExpr)
{
	String getcreditAmtBeforeWin ="";
	 String getCongratsWinAmount  = "";
	 double creditValidation = 0.0;
	 String getCreditAmountAfterWin = "";
	 double getcreditsAmtBeforeWin =0.0;
	 String  getcreditAmt ="";
	try
	{
			   if(checkAvilabilityofElement("DefaultDoubleCard"))
				{	
				   Thread.sleep(3000);
			     log.debug("Moved to double screen : PASS");
			   
			     getcreditAmtBeforeWin = fun_GetText("CreditAmountFromDoubleTo");
				 getcreditsAmtBeforeWin = Double.parseDouble(getcreditAmtBeforeWin.replaceAll("[^0-9]", ""));
				 log.debug(getcreditsAmtBeforeWin);System.out.println(getcreditsAmtBeforeWin);
				 
				 if(NativeClickByXpath("SelectDoubleCard","CardInDouble"))
				 {
				 log.debug("Card Selection in Double  : PASS");
			
			 }//closing double to card click
			 else 
			 {
				 System.out.println("Card selection in Double : FAIL");log.debug("Card selection in Double : FAIL");
				 report.detailsAppendFolder("Card Selection in Double ", "Select the card in Double", "Select the card in Double", "FAIL", CurrencyName);
			 }}//closing validating default card in double to 
		   else 
		   {
			   System.out.println("Moved to double screen : FAIL");log.debug("Moved to double screen : FAIL");
			   report.detailsAppendFolder("Navigate to double  ", "Moved to double to screen", "Moved to double to screen", "FAIL", CurrencyName);
		   }
		   
		   checkAvilabilityofElement("TotalWinAmount"); NativeClickById("moduleContent");
		   
		    
		/******************* Validate total win amt in double  ***********************/
		boolean totalWinAmtInDouble =verifyRegularExpression(report,regExpr,fun_GetText("DoubleToTotalwinAmt"));
		
		 if(checkAvilabilityofElement("GambleLimitReach"))
		   {
			 log.debug("Gamble limit reached : PASS");
			   if(checkAvilabilityofElement("IsBackToBaseGameButtonEnabled"))
			   {
				     log.debug("Verify  back button in double  : PASS");
				   
				     getCongratsWinAmount = fun_GetText("CongratulationsWinAmt"); 
					 double getCongratsWinAmt = Double.parseDouble(getCongratsWinAmount.replaceAll("[^0-9]", ""));
					 log.debug(getCongratsWinAmt);System.out.println(getCongratsWinAmt);
					
					 creditValidation = (getcreditsAmtBeforeWin + getCongratsWinAmt);
					 log.debug(creditValidation);System.out.println(creditValidation);
					 
					 getCreditAmountAfterWin = fun_GetText("CreditAmountFromDoubleTo");
					 double getcreditAmtAfterWin = Double.parseDouble(getCreditAmountAfterWin.replaceAll("[^0-9]", ""));
					 log.debug(getcreditAmtAfterWin);System.out.println(getcreditAmtAfterWin);
					 
					 if(getcreditAmtAfterWin == creditValidation)
					 {
						 log.debug("After win where gamble limit reached , the Credit amt in double updated correctly : PASS");
						 report.detailsAppendFolder("After win where gamble limit reached , the Credit amt in double updated correctly","Credit amt in double updated correctly ","Credit amt in double updated correctly ","PASS",""+CurrencyName);
					 }
					 else
					 {
						 log.debug("After win , Credit amt in double updated correctly : FAIL"); System.out.println("After win , Credit amt in double updated correctly : FAIL");
						 report.detailsAppendFolder("After win where gamble limit reached , the Credit amt in double updated correctly","Credit amt in double updated correctly ","Credit amt in double updated correctly ","FAIL",""+CurrencyName);
					 }
				   
				   
				   /******************* Validate congratulations amt in double  ***********************/
					boolean congratulationsAmtInDouble = verifyRegularExpression(report,regExpr,fun_GetText("CongratulationsWinAmt"));
					
					String glr = "Total win amt when gamble limit reached : "+totalWinAmtInDouble+"  Congrants amt when gamble limit reached : "+congratulationsAmtInDouble;
					
					if(congratulationsAmtInDouble && totalWinAmtInDouble) 
					{
						log.debug("Validate congratulations and win amts when gamble limit reached : PASS");
						report.detailsAppendFolder("Validate congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached","PASS",""+CurrencyName);
					}
					else
					{
						log.debug("Verify congratulations amt when gamble limit reached : FAIL");System.out.println("Verify congratulations amt when gamble limit reached : FAIL");
						report.detailsAppendFolder("Validate congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached",""+glr,"FAIL",""+CurrencyName);	
					}
				   
				    report.detailsAppendFolder("Verify gamble limit reached", "Gamble limit reached", "Gamble limit reached", "PASS", CurrencyName);
				    portraitAndLandscape(report,CurrencyName);
					
					report.detailsAppendNoScreenshot("Scenario : Back to base game from double screen ", "Click on back to base game btn in double and moved to base game", "", ""); 
				  
					if (fun_GetText("GambleLimitReached") != null && NativeClickByXpath("BackToBaseGameBtn","BackBtnFromDouble")) 
					{
						log.debug("Back to base game : PASS");
						
						if(checkAvilabilityofElement("DealButton"))
						{
							
						 getcreditAmt = fun_GetText("CreditAmountFromDoubleTo");
						 double getCreditAmt= Double.parseDouble(getcreditAmt.replaceAll("[^0-9]", ""));
						 log.debug(getCreditAmt);System.out.println(getCreditAmt);
						 
						 if(getCreditAmt == getcreditAmtAfterWin )
						 {
							 log.debug("After win in double to , Credit amt in double and in base is same and updated correctly : PASS");
							 report.detailsAppendFolder("After win in double to , Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","PASS",""+CurrencyName);
						 }
						 else
						 {
							 log.debug("After win in double to , Credit amt in double and in base is same and updated correctly : FAIL"); System.out.println("After win in double to , Credit amt in double and in base is same and updated correctly : FAIL");
							 report.detailsAppendFolder("After win in double to , Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","Credit amt in double and in base is same and updated correctly","FAIL",""+CurrencyName);
						 }
							
						report.detailsAppendFolder("Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "Clicked on back to base game button in double to screen and moved to base game", "PASS", CurrencyName);
						portraitAndLandscape(report,CurrencyName);
						
						}
					} 
				    else
				    {
						System.out.println("Back to base game : FAIL");System.out.println("Back to base game  : FAIL");
						report.detailsAppendFolder("Verify back button", "Click on back button in double", "Click on back button in double", "FAIL", CurrencyName);
					} 
			   }
			   else
			   {
					System.out.println("Verify  back button in double : FAIL");System.out.println("GVerify  back button in double : FAIL");
					report.detailsAppendFolder("Verify  back button in double", "Back button in double", "Back button in double", "FAIL", CurrencyName);
				} 
		   }
		 else
		   {
				System.out.println("Gamble limitis reached  : FAIL");System.out.println("Gamble limitis reached  : FAIL");
				report.detailsAppendFolder("Verify gamble limit reached", "Gamble limitis reached", "Gamble limitis reached", "FAIL", CurrencyName);
			} 
				
				
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
}
/**
 * img click by setting web driver context  
 * @author rk61073
 * @return 
 * @throws IOException 
 */
public boolean imgClick( AppiumDriver<WebElement> webdriver,String GameName,String imgName) throws IOException
{
	boolean imgClick = false;
	
	ImageLibrary img = new ImageLibrary(webdriver, GameName, "Mobile");
	img.setScreenOrientation(webdriver.getOrientation().toString().toLowerCase());
		
	try {
		
		webdriver.context("NATIVE_APP");
		
		if (img.isImageAppears(imgName))
		{
			img.click(imgName);
			
			log.debug("Img click for "+imgName + " : PASS");imgClick=true;
		} 
		else
		{
			System.out.println("Img click for "+imgName + " : FAIL");imgClick=false;
		}
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	return imgClick;
}
/**
* sets the context before clicking
* @author rk61073
*/
public boolean setContextToClick() throws IOException
{
	boolean setcontext = false;
	try
	{	
		webdriver.context("NATIVE_APP");
		 setcontext= true;
	    //String context = webdriver.getContext(); webdriver.context(context);	
	    //webdriver.context("CHROMIUM");
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
		setcontext= false;
	}
	return setcontext;
}
/**
 * method is to check the availability if an element 
 * @param string
 * @return
 * @author rk61073
 */
public boolean checkAvilabilityofElement(String element,String imgName) 
{
	boolean click = true;
	try 
	{
		ImageLibrary img = new ImageLibrary(webdriver, GameName, "Mobile");
		img.setScreenOrientation(webdriver.getOrientation().toString().toLowerCase());
		
		if (osPlatform.equalsIgnoreCase("Android")) 
		{	
			if(checkAvilabilityofElement(element))	
			{click = true;}
			else if(img.isImageAppears(imgName))
			{click = true;}
			else{click = false;}
		}
		else
		{
			if(img.isImageAppears(imgName))	
			{click = true;}
			else if(checkAvilabilityofElement(element))
			{click = true;}
			else{click = false;}
		}
		
	}
	catch (Exception e) 
	{
		// if component in the game not avilable in it while give an exception
		click = false;
	}
	return click;
}
/**
 * it interrupts the actions 
 * @param string
 * @return
 * @author rk61073
 */
public void interrupt() 
{
	try 
	{
		/*if (osPlatform.equalsIgnoreCase("Android")) 
		{
			nativeClickByID(xpathMap.get("moduleContent"));
		}
		else
		{
			closeOverlay();
		}*/
		try
		{
			String selectedamout ="";
			
		/*	
			WebElement scroll = webdriver.findElement(By.xpath(xpathMap.get("coinsizeslider")));
		    JavascriptExecutor js1 = (JavascriptExecutor)webdriver;
		    js1.executeScript("window.scrollBy(250,0)", scroll);
			
			*/
			WebElement slider = webdriver.findElement(By.xpath(xpathMap.get("userInput")));// select scroll bar
			JavascriptExecutor js = (JavascriptExecutor) webdriver;

			js.executeScript("arguments[0].click();", webdriver.findElement(By.xpath(xpathMap.get("userInput"))));
			js.executeScript("window.scrollBy(100,0)", slider);
			//js.executeScript("javascript:document.getElementById(\"" + xpathMap.get("userInput") + "\").value=" + amount + ";");// (Value took from Excel)
			if (osPlatform.equalsIgnoreCase("Android")) {
				slider.sendKeys(Keys.RIGHT);
			} else {
				slider.sendKeys(Keys.TAB);
				// slider.click();

			}
			js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.xpath(xpathMap.get("userInput"))));
			System.out.println("Slider value2 for credits is : " + slider.getAttribute("value"));// value updated in the
			// game 90.01
log.debug("Slider value2 for credits is : " + slider.getAttribute("value"));
selectedamout = slider.getAttribute("value");
			
			
			HashMap<String,Object>scrollObject = new HashMap();
			scrollObject.put("direction", "right");
			scrollObject.put("class", "range-slider__thumb");
			scrollObject.put("style", "100%");
			
			System.out.println(scrollObject.containsValue("range-slider__thumb"));
			
			webdriver.executeScript("mobile:scroll", scrollObject);
		//webdriver.findElementById("range-slider__fill").click();
		webdriver.findElementByClassName("//*[@id=\\\"betSettingsCoinSize\\\"]/div[3]/div[2]/div[@class='range-slider__thumb']").click();
		webdriver.findElementById("100%").click();
		
		
		JavascriptExecutor js11 = (JavascriptExecutor) webdriver;
		HashMap<String, Integer> swipeObject = new HashMap<String, Integer>();
		swipeObject.put("startX", 0);
		swipeObject.put("startY", 0);
		swipeObject.put("endX", 100);
		swipeObject.put("endY", 100);
		swipeObject.put("duration", 1);
		js11.executeScript("mobile: swipe", swipeObject);
		
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		 WebElement credit = webdriver.findElement(By.xpath(xpathMap.get("CreditAmount")));
		Actions move = new Actions(webdriver);
       move.moveToElement(credit).clickAndHold();
       move.moveByOffset(100,0).build().perform();
		closeOverlay();
		//closeOverlayPortraitAndLandscape();
		Thread.sleep(2000);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	
}

public void checkGLR(Mobile_HTML_Report report, String currencyName)
{ 
	try 
	{
		do
		 {
			   if(checkAvilabilityofElement("DefaultDoubleCard"))
				{	
				 if(NativeClickByXpath("SelectDoubleCard"))
				 {
					 Thread.sleep(3000);
					 //NativeClickById("moduleContent");Thread.sleep(1000);
					 log.debug("Card selection in Double  : PASS");
					 
					 if(webdriver.findElement(By.xpath(xpathMap.get("gambleLimit"))).isDisplayed()) 
					 {
						break; 
					 }
					 
				 }//closing double to card click
				 else 
				 {
					 System.out.println("Cardd selection in Double TO : FAIL");log.debug("Card selection in Double TO : FAIL");
					 report.detailsAppendFolder("Verify card selection in double to ", "Card selection in double to", "Card selection in double to", "FAIL", currencyName);
				 }
				 }//closing validating default card in double to 
			   else 
			   {
				   System.out.println("Availabitity of open card in double to : FAIL");log.debug("Availabitity of open card in double to : FAIL");
				   report.detailsAppendFolder("Verify availabitity of open card in double to", "Availabitity of open card in double to", "Availabitity of open card in double to", "FAIL", currencyName);
			     }
			 
				  if(NativeClickByXpath("DoubleToButton","DoubleToBtnFromDouble"))
				  {
				    log.debug("Double To button in Double To : PASS");
				  }
				  else 
				  {
					  System.out.println("Click on double button in double to : FAIL"); 
					  report.detailsAppendFolder("Verify double button in double to", "Click on double button in double to", "Click on double button in double to", "FAIL", currencyName);
				  }
		}while (!(webdriver.findElement(By.xpath(xpathMap.get("gambleLimit"))).isDisplayed()));
		
		if (fun_GetText("GambleLimitReached") != null && checkAvilabilityofElement("BackToBaseGame") ) 
		{
			log.debug("Gamble limit text : PASS");
			portraitAndLandscape(report,currencyName);
		} 
		else
		{
			System.out.println("Gamble limit text : FAIL");
			report.detailsAppendFolder("Verify gamble limit reached", "Gamble limit reached", "Gamble limit reached", "FAIL", currencyName);
		}
		
		report.detailsAppendNoScreenshot("Scenario : Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "", ""); 
		checkAvilabilityofElement("GambleLimitReach"); Thread.sleep(2000);
		if (NativeClickByXpath("BackToBaseGame","BackBtnFromDouble"))
		{ Thread.sleep(2000);
			if(checkAvilabilityofElement("DealButton"))
			{      
				log.debug("Back to base from double to screen : PASS");
				portraitAndLandscape(report,currencyName);
			}//closing deal button availability 
		}//closing back to base game 
		else 
		{
			System.out.println("Back to base from double to screen : FAIL");log.debug("Back to base from double to screen : FAIL");
			report.detailsAppendFolder("Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "Clicked on back to base game button in double to screen and moved to base game", "FAIL", currencyName);
		}	
		
		
		
		
		
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	
}
}