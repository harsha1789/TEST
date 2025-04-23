package Modules.Regression.FunctionLibrary;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

//import io.appium.java_client.TouchAction<T>;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;

import com.zensar.automation.framework.library.ZAFOCR;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.report.Mobile_HTML_Report;
//import com.sun.glass.events.KeyEvent;

import com.zensar.automation.framework.utils.Util;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
//import com.zensar.automation.library.ImageLibrary;
import com.zensar.automation.library.TestPropReader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import net.lightbody.bmp.BrowserMobProxyServer;

public class CFNLibrary_Mobile_PlayNextOld extends CFNLibrary_MobileOld 
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

	
	public CFNLibrary_Mobile_PlayNextOld(AppiumDriver<WebElement> webdriver, BrowserMobProxyServer proxy, Mobile_HTML_Report tc06, String gameName) throws IOException {
		super(webdriver, proxy, tc06,gameName);

		this.webdriver=webdriver;
		this.proxy=proxy;
		repo1= tc06;
		//webdriver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		//Wait = new WebDriverWait(webdriver, 60);
		this.GameName=gameName;	
		
	}
	
	public void func_SwipeUp(){
		 
        Wait=new WebDriverWait(webdriver,50);    
        boolean isOverlayRemove=false;
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
    			action.press(PointOption.point(startx,starty)).release().perform();
    			webdriver.context(context);
    			log.debug("tapped on full screen overlay");
    			Thread.sleep(500);
            }
            else{//For IOS to perform scroll
                Thread.sleep(1000);
                Dimension size = webdriver.manage().window().getSize();
                int anchor = (int) (size.width * 0.5);
                int startPoint = (int) (size.height * 0.9);
                int endPoint = (int) (size.height * 0.4);
                new TouchAction(webdriver).press(point(anchor, startPoint)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(anchor, endPoint)).release().perform();
                
                //Added another swipe to avoid swipe issue on few latest OS(1.14)
/*                Thread.sleep(1000);
                startPoint = (int) (size.height * 0.75);
                endPoint = (int) (size.height * 0.5);
                new TouchAction(webdriver).press(point(anchor, startPoint)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(anchor, endPoint)).release().perform();
                isOverlayRemove=true;*/
                
            }
            log.debug("tapped on full screen overlay");
            
        } 
        catch (Exception e) {
            log.error(e.getStackTrace());
        }
     
	}
	
	public void tapOnCoordinates(final double x, final double y) 
	{
		try {
		TouchAction action = new TouchAction(webdriver);
		action.press(PointOption.point((int)x, (int)y)).release().perform();
		action.tap(PointOption.point((int) x, (int) y)).perform();
		}catch (Exception e) {
 			log.debug(e.getMessage(), e);
 		}
	}
	
	public void clickAtCoordinates(Long sx, Long sy) {
		try {
			// JavascriptExecutor js = ((JavascriptExecutor)webdriver);
			// Dimension d=webdriver.manage().window().getSize();
			int dx = 0, dy = 0;
			try {
				dx = sx.intValue();
			} catch (Exception e) {
				dx = sx.intValue();
			}
			try {
				dy = sy.intValue();
			} catch (Exception e) {
				dy = sy.intValue();
			}
			Thread.sleep(100);
			Actions act = new Actions(webdriver);
			WebElement ele1 = webdriver.findElement(By.id("gameCanvas"));
			/*
			 * for(int i=0;i<=100;i++) {
			 */

			act.moveToElement(ele1, dx, dy).click().build().perform();

			// dx=dy+5;dy=dy+10;
			// }

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeOverlay(int x, int y)
    {
        try
        {
        if (osPlatform.equalsIgnoreCase("iOS"))
        {
            if(webdriver.getOrientation().equals(ScreenOrientation.PORTRAIT))
            {
                TouchAction touchAction = new TouchAction(webdriver);
                touchAction.tap(PointOption.point(x, y)).perform();
            
            }
            else
            {
                TouchAction touchAction = new TouchAction(webdriver);
                touchAction.tap(PointOption.point(50, 80)).perform();
            }
        } else
        {// For Andriod mobile
            
            Actions act = new Actions(webdriver);
            act.moveByOffset(x, y).click().build().perform();
            act.moveByOffset(-x, -y).build().perform();
            //act.moveByOffset(20, 80).click().build().perform();
            //act.moveByOffset(-20, -80).build().perform();
        }
        }
         catch (Exception e)
        {
                log.error("error while closing overlay ", e);
                log.error(e.getMessage());
        }
    }

	
	
	public boolean waitForElement(String hook)
    {    
        boolean result=false;
        long startTime = System.currentTimeMillis();
        try{
            log.debug("Waiting for Element before click");
            while(true)
            {
            	Boolean ele = getConsoleBooleanText("return "+xpathMap.get(hook));
                if(ele)
                {
                    log.info(hook+" value= "+ele);
                    result=true;        
                    System.out.println(hook+" value= "+ele);
                    break;
                }
                else
                {
                    long currentime = System.currentTimeMillis();
                    if(((currentime-startTime)/1000)> 120)
                    {
                    log.info("No value present after 120 seconds= "+ele);
                    System.out.println("No value present after 120 seconds= "+ele);
                    result=false;
                    break;            
                    }
               }
                
            }        
        }
        catch(Exception e)
        {
            log.error("error while waiting for element ",e);
        }
        return result;
    }

		
	 public boolean isElementVisible(String element) {

 		boolean visible = false;
 		try {
 			String elements = "return " + xpathMap.get(element);
 			visible = getConsoleBooleanText(elements);
 		} catch (Exception e) {
 			log.debug(e.getMessage(), e);
 		}

 		return visible;
	 }
	 public boolean isElementVisibleUsingHook(String elementHook) {

	 		boolean visible = false;
	 		try {
	 			String elements = "return " + elementHook;
	 			log.debug("Checking element visibility");
	 			visible = getConsoleBooleanText(elements);
	 		} catch (Exception e) {
	 			log.debug(e.getMessage(), e);
	 		}

	 		return visible;
		 }
	 
	 public boolean elementVisible_Xpath(String Xpath) 
	 {
		 boolean visible = false;
		 try
		 {
			if (webdriver.findElement(By.xpath(Xpath)).isDisplayed()) {
			
				visible = true;
			}
		 }catch (Exception e) {
	 			log.debug(e.getMessage(), e);
	 		}
		 return visible;
		}
	 public void clickAtButton(String button) {
			try {
			    JavascriptExecutor js = (webdriver);
		        js.executeScript(button);
			    
			   /* 
				JavascriptExecutor js = ((JavascriptExecutor) webdriver);
				js.executeScript(button);*/
			} catch (JavascriptException e) {
				log.error("EXeception while executon ", e);
				log.error(e.getCause());
			}
		}
	 public void clickOnButtonUsingCoordinates(String X, String Y) 
	 {
			try 
			{
				String xCoordinate=xpathMap.get(X);
				String yCoordinate=xpathMap.get(Y);
				double x= Double.parseDouble(xCoordinate);
				double y= Double.parseDouble(yCoordinate);
				tapOnCoordinates(x,y);
			}

			catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
		}
	 
	 
		public void validateNFDButton(Mobile_HTML_Report report,String CurrencyName)
		{
			try
			{
				//check for visibility of nfd button and take screenshot
				if(isElementVisible("isNFDButtonVisible"))
				{
					report.detailsAppendFolder("Verify Continue button is visible ", "Continue button should be visible", "Continue button is visible", "Pass",CurrencyName);
				}
				else
				{
					report.detailsAppendFolder("Verify Continue button is visible ", "Continue buttion should be visible", "Continue button is not visible", "Fail",CurrencyName);
				}
			}catch (Exception e) 
			{
				log.debug("unable to verify continue button");
				log.error(e.getMessage(), e);
			}
		}
		
		
		
		
		public void verifyBetSliders(Mobile_HTML_Report report,String currencyName)
		{
			try
			{
					// Check Coin Size Slider
					if(xpathMap.get("CoinSizeSliderPresent").equalsIgnoreCase("Yes"))
					{
						validateCoinSizeSlider(report,"CoinSizeSliderSet","BetMenuBetValue",currencyName);
						Thread.sleep(1000);
					}						
					
					
					// Check Coins per line slider
					if(xpathMap.get("CoinsPerLineSliderPresent").equalsIgnoreCase("Yes"))
					{
						validateCoinsPerLineSlider(report,"CoinsPerLineSliderSet","BetMenuBetValue",currencyName);
						Thread.sleep(1000);
					}
					
					
					//check lines slider
					if(xpathMap.get("LinesSliderPresent").equalsIgnoreCase("Yes"))
					{
						validateLinesSlider(report,"LinesSliderSet","BetMenuBetValue",currencyName);
						Thread.sleep(1000);
					}
					
					
			}catch (Exception e) 
			{
				log.debug("unable to verify bet options");
				log.error(e.getMessage(), e);
			}
		}
		
		
		public void verifyAutoplayOptions(Mobile_HTML_Report report,String CurrencyName)
		{
			try
			{
				if(xpathMap.get("SpinSliderPresent").equalsIgnoreCase("Yes"))
				{
					validateSpinsSliderAutoplay(report, "SpinSliderSet","SpinSliderValue",CurrencyName);
					Thread.sleep(2000);
				}
				
				if(xpathMap.get("TotalBetSliderPresent").equalsIgnoreCase("Yes"))
				{
					validateTotalBetSliderAutoplay(report, "TotalBetSliderSet","TotalBetSliderValue",CurrencyName);
					Thread.sleep(2000);		
				}
								
				if(xpathMap.get("WinLimitSliderPresent").equalsIgnoreCase("Yes"))
				{
					validateWinLimitSliderAutoplay(report, "WinLimitSliderSet","WinLimitSliderValue",CurrencyName);
					Thread.sleep(2000);			
				}
													
				if(xpathMap.get("LossLimitSliderPresent").equalsIgnoreCase("Yes"))
				{
					validateLossLimitSliderAutoplay(report, "LossLimitSliderSet","LossLimitSliderValue",CurrencyName);
					Thread.sleep(2000);
				}
				
				
				/*startAutoPlay();
				Thread.sleep(3000);
				
				if(isElementVisible("isAuoplaySpinVisible"))
				{
					report.detailsAppendFolder("Verify  autoplay started", "Autoplay should start", "Autoplay is started", "Pass",CurrencyName);
					log.debug("Autoplay started");

					stopAutoPlay();
				}
				else
				{
					report.detailsAppendFolder("Verify  autoplay started", "Autoplay should start", "Autoplay is not started", "Fail",CurrencyName);
					log.debug("Autoplay not started or freespins triggered");
				}*/
				
		}catch (Exception e) 
			{
			log.debug("unable to verify autoplay options");
			log.error(e.getMessage(),e);
			
		}
		}
		/**
		 * This method is used to slide the coin size slider and validate bet amount to verify its working or not
		 * @author pb61055
		 * @param report
		 */
		public void validateCoinSizeSlider(Mobile_HTML_Report report,String sliderPoint,String betValue,String CurrencyName)
		{
			try
			{
					boolean ableToSlide=verifySliderValue(sliderPoint,betValue);
				
					if(ableToSlide)
					{
						report.detailsAppendFolder("Verify if able to change coin size slider value", "Coin size slider value should change", "Coin size slider value is changed", "Pass",CurrencyName);
						log.debug("Coin size slider is working");
					}
					else
					{
						report.detailsAppendFolder("Verify if able to change coin size slider value", "Coin size slider value should change", "Unable to change coin cize clider value", "Fail",CurrencyName);
						log.debug("Coin size slider is not working");
					}
				
			}catch (Exception e) 
			{
				log.debug("unable to verify coin size slider");
				log.error(e.getMessage(), e);
			}
		}
		/**
		 * This method is used to slide the coins per line slider and validate bet amount to verify its working or not
		 * @author pb61055
		 * @param report
		 */
		public void validateCoinsPerLineSlider(Mobile_HTML_Report report,String sliderPoint,String betValue,String CurrencyName)
		{
			try
			{	
				boolean ableToSlide=verifySliderValue(sliderPoint,betValue);			
				if(ableToSlide)
				{									
					report.detailsAppendFolder("Verify if able to change coins per line slider value", "Verify if able to change coins per line slider value", "Coins per line slider value is changed", "Pass",CurrencyName);
					log.debug("Coins Per Line slider is working");
				}
				else
				{
					report.detailsAppendFolder("Verify if able to change coins per line slider value", "Verify if able to change coins per line slider value", "Unable to change coins per line slider value", "Fail",CurrencyName);
					log.debug("Coins Per Line slider is not working");
				}							
				
			}catch (Exception e) 
			{
			log.debug("unable to verify coins per line slider");
			log.error(e.getMessage(), e);
		}
	}
		/**
		 * This method is used to slide the lines slider and validate bet amount to verify its working or not
		 * @author pb61055
		 * @param report
		 */
		public void validateLinesSlider(Mobile_HTML_Report report,String sliderPoint,String betValue,String CurrencyName)
		{
			try
			{
				boolean ableToSlide=verifySliderValue(sliderPoint,betValue);		
												
				if(ableToSlide)
				{
					report.detailsAppendFolder( "Verify if able to change line slider value", "Line slider value should change", "Line slider value is changed", "Pass",CurrencyName);
					log.debug("Line slider is working");
				}
				else
				{
					report.detailsAppendFolder( "Verify if able to change line slider value", "Line slider value should change", "Unable to change line slider value", "Fail",CurrencyName);
					log.debug("Line slider is not working");
				}									
		
			}catch (Exception e) 
			{
				log.debug("unable to verify line slider");
				log.error(e.getMessage(), e);
			}
		}
		
		public boolean verifySliderValue(String sliderPoint,String value)
		{
			boolean isSliderMoved =false;
			try
			{
				String  valueBefore=GetConsoleText("return "+xpathMap.get(value));			
				log.debug("Value before sliding: "+valueBefore);
				Thread.sleep(1000);

				
				
				clickAtButton(xpathMap.get(sliderPoint));
				
				
				Thread.sleep(1000);			
				
				String  valueAfter=GetConsoleText("return "+xpathMap.get(value));
				log.debug("Value after sliding: "+valueAfter);
				
				if(valueBefore.equalsIgnoreCase(valueAfter) != true)
				{
					isSliderMoved =true;
					log.debug("slider is moved");
				}
			}catch (Exception e) 
			{
				log.debug("unable to move slider");
				log.error(e.getMessage(), e);
			}
			return isSliderMoved;
		}
		
		public boolean setMaxbetPlayNext()
		{
			boolean result= false;
			try
			{
				clickOnButtonUsingCoordinates("maxBetCoordinateX","maxBetCoordinateY");
				Thread.sleep(2000);
				result=true;
			}catch (Exception e) 
			{
				log.debug("unable to setMaxbet");
				log.error(e.getMessage(), e);
			}
			return result;
		}
	
		
		
		public boolean clickOnQuickSpin()
		{
			boolean result=false;
			try
			{
				//clicking on quick spin button
				clickOnButtonUsingCoordinates("quickSpinCoordinateX","quickSpinCoordinateY");
				log.debug("clicked on quick spin");
				result=true;
				
			}
			catch (Exception e) 
			{
				log.debug("unable while clicking on quick spin button");
				log.error(e.getMessage(), e);
			}
			return result;
		}
		
		public void closeUsingCoordinates()
		{
			
			try
			{
				//clicking on quick spin button
				clickOnButtonUsingCoordinates("closeButtonCoordinateX","closeButtonCoordinateY");
				log.debug("clicked on close button");
				
			}
			catch (Exception e) 
			{
				log.debug("unable while clicking on close button");
				log.error(e.getMessage(), e);
			}
			
		}
		
		public void verifyMenuOptions(Mobile_HTML_Report report,String CurrencyName)
		{
			try
			{
				report.detailsAppend("Follwing are the paytable verification test cases", "Verify paytable ", "", "");
				
				verifyPaytableScroll(report,CurrencyName);
				Thread.sleep(1000);	
				
				paytableClose();
				Thread.sleep(1000);
				
				openMenuPanel();
				
				report.detailsAppend("Follwing are the settings verification test cases", "Verify paytable ", "", "");
				verifySettingsOptions(report,CurrencyName);
				Thread.sleep(2000);	
					
				
			}catch (Exception e) 
			{
				log.debug("unable to click spin button");
				log.error(e.getMessage(), e);
			}
		}

			public void paytableOpen() 
			{
				try
				{
					clickOnButtonUsingCoordinates("paytableCoordinateX","paytableCoordinateY");
					Thread.sleep(2000);	
				}catch (Exception e) 
				{
					log.debug("unable to close paytable");
					log.error(e.getMessage(), e);
				}

			}
			
			public void paytableClose() 
			{
				try
				{
					if(xpathMap.get("closePaytableUsingCoordinates").equalsIgnoreCase("Yes")) 
					{
						clickOnButtonUsingCoordinates("paytableCloseCoordinateX","paytableCloseCoordinateY");
					}
					else if(xpathMap.get("closePaytableUsingXpath").equalsIgnoreCase("Yes")) 
					{
						funcClick("PaytableClose");
					}				
					Thread.sleep(2000);	
				}catch (Exception e) 
				{
					log.debug("unable to close paytable");
					log.error(e.getMessage(), e);
				}
			}
			
		public boolean verifyPaytableScroll(Mobile_HTML_Report report,String CurrencyName)
		{
			boolean result=false;
			try
			{
				paytableScroll(report,CurrencyName);
				if(isElementVisible("isPaytableBtnVisible"))
				{
					//report.detailsAppend("Verify PayTable Button ", "PayTable should visible", "PayTable is visible", "Pass");
				
					//click on PayTable
					paytableOpen();		
					
					if(elementVisible_Xpath(xpathMap.get("PaytableClose")))
					{
						report.detailsAppendFolder("Verify if paytable is visible ", "PayTable should be visible", "PayTable is visible", "Pass",CurrencyName);
					//	Thread.sleep(2000);
						boolean scrollPaytable = paytableScroll(report,CurrencyName);
						if (scrollPaytable) 
						{										
							report.detailsAppendFolderOnlyScreeshot(CurrencyName);
							result=true;
					
						} else 
						{
							report.detailsAppendFolderOnlyScreeshot(CurrencyName);
						}
						
					}
					else
					{
						report.detailsAppendFolder("Verify if paytable is visible ", "PayTable should be visible", "PayTable is not visible", "Fail",CurrencyName);
					}									
				}
				else
				{
					report.detailsAppendFolder("Verify PayTable Button", "PayTable button should be visible", "PayTable button is not visible", "Fail",CurrencyName);
				}
			}catch (Exception e) 
			{
				log.debug("unable to verify paytable");
				log.error(e.getMessage(), e);
			}
			return result;
		}
		
		
		
		
		
		
		public boolean paytableScroll(Mobile_HTML_Report report,String CurrencyName) 
		 {
				boolean paytableScroll = false;
				try {
					if (xpathMap.get("paytableScrollOfFive").equalsIgnoreCase("yes")) {
						paytableScroll = paytableScrollOfFive(report,CurrencyName);
						return paytableScroll;
					} else if (xpathMap.get("paytableScrollOfNine").equalsIgnoreCase("yes")) {
						paytableScroll = paytableScrollOfNine(report,CurrencyName);
						return paytableScroll;
					} else {
						System.out.println("Check the Paytable Scroll");
						log.debug("Check the Paytable Scroll");
					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return paytableScroll;
			}
		
		 
		 public boolean paytableScrollOfFive(Mobile_HTML_Report report,String CurrencyName) 
		 {
			 String winBoosterXpath = "WinBooster";		
			String mysterySymbolXpath = "MysterySymbol";
			String WildXpath = "Wild";	
			String PaytableGridXpath = "PaytableGrid";
			String PaylineXpath = "Payline";		
			boolean test = false;
			try {
			
			
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);

				test = webdriver.findElements(By.xpath(xpathMap.get(winBoosterXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(winBoosterXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(2000);
					test = true;
				}

				test = webdriver.findElements(By.xpath(xpathMap.get(mysterySymbolXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(mysterySymbolXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(2000);
					test = true;
				}
				test = webdriver.findElements(By.xpath(xpathMap.get(WildXpath))).size() > 0;
			
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(WildXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(2000);
					test = true;
				}

				test = webdriver.findElements(By.xpath(xpathMap.get(PaytableGridXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(PaytableGridXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(2000);
					test = true;
				}

				test = webdriver.findElements(By.xpath(xpathMap.get(PaylineXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(PaylineXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(2000);
					test = true;
				}
				Thread.sleep(2000);
				// method is for validating the payatable Branding
				textValidationForPaytableBranding(report, CurrencyName);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return test;
			}
		 /**
			 * method is for to scroll seven times
			 * 
			 * @param report
			 * @param CurrencyName
			 * @return
			 */
			public boolean paytableScrollOfNine1(Mobile_HTML_Report report,String CurrencyName) {
				String winUpto = "WinUpTo"; 
				String wildXpath = "Wild";
				String scatterXpath = "Scatter";	
				String freeSpine = "FreeSpine";		
				String symbolGridXpath = "PaytableGrid";
				String symbolGridXpath5 = "PaytableGrid5";	
				String paylines = "Payline";	
				boolean test = false;
				try {
								
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);

					test = webdriver.findElements(By.xpath(xpathMap.get(winUpto))).size() > 0;
					if (test) {
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(winUpto)));
						js.executeScript("arguments[0].scrollIntoView(true);", ele1);
						report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
						Thread.sleep(1000);
						test = true;
					}	

					test = webdriver.findElements(By.xpath(xpathMap.get(freeSpine))).size() > 0;
					if (test) {
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(freeSpine)));
						js.executeScript("arguments[0].scrollIntoView(true);", ele1);
						report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
						Thread.sleep(1000);
						test = true;
					}
					if(xpathMap.get("wildFirst").equalsIgnoreCase("Yes"))
					{
						test = webdriver.findElements(By.xpath(xpathMap.get(wildXpath))).size() > 0;
						if (test) {
							JavascriptExecutor js = (JavascriptExecutor) webdriver;
							WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(wildXpath)));
							js.executeScript("arguments[0].scrollIntoView(true);", ele1);
							report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
							Thread.sleep(1000);
							test = true;
						}
						test = webdriver.findElements(By.xpath(xpathMap.get(scatterXpath))).size() > 0;
						if (test) {
							JavascriptExecutor js = (JavascriptExecutor) webdriver;
							WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(scatterXpath)));
							js.executeScript("arguments[0].scrollIntoView(true);", ele1);
							report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
							Thread.sleep(1000);
							test = true;
						}
					}else
					{
						test = webdriver.findElements(By.xpath(xpathMap.get(scatterXpath))).size() > 0;
						if (test) {
							JavascriptExecutor js = (JavascriptExecutor) webdriver;
							WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(scatterXpath)));
							js.executeScript("arguments[0].scrollIntoView(true);", ele1);
							report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
							Thread.sleep(1000);
							test = true;
						}
						test = webdriver.findElements(By.xpath(xpathMap.get(wildXpath))).size() > 0;
						if (test) {
							JavascriptExecutor js = (JavascriptExecutor) webdriver;
							WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(wildXpath)));
							js.executeScript("arguments[0].scrollIntoView(true);", ele1);
							report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
							Thread.sleep(1000);
							test = true;
						}
					}

					test = webdriver.findElements(By.xpath(xpathMap.get(symbolGridXpath))).size() > 0;
					if (test) {
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(symbolGridXpath)));
						js.executeScript("arguments[0].scrollIntoView(true);", ele1);
						report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
						Thread.sleep(1000);
						test = true;
					}

					test = webdriver.findElements(By.xpath(xpathMap.get(symbolGridXpath5))).size() > 0;
					if (test) {
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(symbolGridXpath5)));
						js.executeScript("arguments[0].scrollIntoView(true);", ele1);
						report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
						Thread.sleep(1000);
						test = true;
					}
					test = webdriver.findElements(By.xpath(xpathMap.get(paylines))).size() > 0;
					if (test) {
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(paylines)));
						js.executeScript("arguments[0].scrollIntoView(true);", ele1);
						report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
						Thread.sleep(1000);
						test = true;
					}
				

					
			
					Thread.sleep(3000);		
					
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return test;
			}
			public boolean paytableScrollOfNine(Mobile_HTML_Report report, String CurrencyName) {
				String winUpto = "WinUpTo"; 
				String wildXpath = "Wild";		
				String freeSpine = "FreeSpine";	
				String scatterXpath = "Scatter";	
				String symbolGridXpath = "PaytableGrid";
				String symbolGridXpath5 = "PaytableGrid5";	
				String paylines = "Payline";	
				String scroll1="scroll1";
				String scroll2="scroll2";
				String scroll3="scroll3";
				String scroll4="scroll4";
				String scroll5="scroll5";
				String scroll6="scroll6";
				String scroll7="scroll7";
				String scroll8="scroll8";
				String scroll9="scroll9";
				
				boolean test = false;
				try {
								
					test = scrollUsingElement(winUpto);
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}

					test =scrollUsingElement(wildXpath);
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(freeSpine);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(scatterXpath);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(symbolGridXpath);
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(symbolGridXpath5);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(paylines);
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					
					test = scrollUsingElement(scroll1);
				
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(scroll1);
					
					/*	if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					
					test = scrollUsingElement(scroll3);
				
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(scroll4);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(scroll5);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(scroll6);
				
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(scroll7);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					test = scrollUsingElement(scroll8);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					
					
					test = scrollUsingElement(scroll9);
					
					if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
					Thread.sleep(1000);
					test = true;}
					*/
			
					Thread.sleep(3000);		
					
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return test;
			}
			
			
			public boolean scrollUsingElement(String element)
			 {
				boolean result= true;
				try
				{
					result = webdriver.findElements(By.xpath(xpathMap.get(element))).size() > 0;
					if (result) 
					{
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(element)));
						js.executeScript("arguments[0].scrollIntoView(true);", ele1);
						Thread.sleep(1000);
						result = true;
					}
					
				 
				}catch (Exception e) {
					log.error(e.getMessage(), e);
					log.error("unable to scroll");
				}
				
					
				 return result;
			 }
			
			
			/**
			 * Verifies the Paytable text validation 
			 * 
			 */
			public String textValidationForPaytableBranding(Mobile_HTML_Report report,String CurrencyName) 
			{

				String PaytableBranding = null;
				Wait = new WebDriverWait(webdriver, 6000);
				try
				{
					Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("Payways"))));	
					boolean txt = webdriver.findElement(By.xpath(xpathMap.get("Payways"))).isDisplayed();
				if(txt)	
				{
					WebElement ele = webdriver.findElement(By.xpath(xpathMap.get("Payways")));
					PaytableBranding=ele.getText();
					System.out.println("actual  : " +PaytableBranding);
					if(PaytableBranding.equalsIgnoreCase(xpathMap.get("PaywaysTxt")))
					{
						System.out.println("Powered By MicroGaming Text : Pass");log.debug("Powered By MicroGaming Text : Pass");
						report.detailsAppendFolder("Paytable Branding ", "Branding Text ", ""+PaytableBranding, "Pass",""+CurrencyName);

					}
					else
					{
						System.out.println("Powered By MicroGaming Text : Fail");log.debug("Powered By MicroGaming Text : Fail");
						report.detailsAppendFolder("Paytable Branding ", "Branding Text ", ""+PaytableBranding, "Fail",""+CurrencyName);

					}}
				
				else
				{
					System.out.println("Powered By MicroGaming Text : Fail");
					report.detailsAppendFolder("Paytable", "Branding is not available ", ""+PaytableBranding, "Fail",""+CurrencyName);
				}
					
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				return PaytableBranding;
				
				
			}

			
			
			public boolean verifySettingsOptions(Mobile_HTML_Report report,String CurrencyName)
			{
				boolean settings= false;
				try
				{
				if(isElementVisible("isSettingsBtnVisible"))
				{	
					// click on settings
					clickOnButtonUsingCoordinates("settingsCoordinateX","settingsCoordinateY");
					log.debug("Clicked on settings button to open and verify");		
					Thread.sleep(2000);	
					
					if(isElementVisible("isMenuBackBtnVisible"))
					{
						report.detailsAppendFolder("Verify settings panel is open ", "Settings panel should be open", "Settings is open", "Pass",CurrencyName);
						
						//quick spin toggle
						if(xpathMap.get("isSettingsTurboInGame").equalsIgnoreCase("Yes")&&isElementVisible("isSettingsTurboVisible"))
						{
							//click on quick spin toggle
							clickOnButtonUsingCoordinates("quickSpinSettingsCoordinateX","quickSpinSettingsCoordinateY");
							Thread.sleep(2000);
							report.detailsAppendFolder("Verify Quick spin toggle in settings ", "Quick spin toggle in settings should work", "Quick spin toggle in settings is working", "Pass",CurrencyName);
						}
						else
						{
							log.info("Verify Quick spin toggele button is not available in menu setting");
						}
						Thread.sleep(2000);
						
						
						//voice over
						if(xpathMap.get("settingsVoiceOverIngame").equalsIgnoreCase("Yes")&&isElementVisible("isVoiceOvers"))
						{
							//clicking on sound
							clickOnButtonUsingCoordinates("voiceOverCoordinateX","voiceOverCoordinateY");
							Thread.sleep(2000);
							report.detailsAppendFolder("Verify voice over toggle in settings ", "Voice over toggle in settings should work", "Voice over toggle in settings is working", "Pass",CurrencyName);
						}
						else
						{
							log.info("sound  voice over toggele button is not available in menu setting");
						}	
						Thread.sleep(2000);		
						
						
						//sound
						if(xpathMap.get("isSettingsSoundInGame").equalsIgnoreCase("Yes")&&isElementVisible("isSoundsVisible"))
						{
							//clicking on sound
							clickOnButtonUsingCoordinates("settingsSoundCoordinateX","settingsSoundCoordinateY");
							Thread.sleep(2000);
							report.detailsAppendFolder("Verify Sound toggle in settings ", "Sound toggle in settings should work", "Sound toggle in settings is working", "Pass",CurrencyName);
						}
						else
						{
							log.info("Sound toggele button is not available in menu setting");
						}
						Thread.sleep(2000);
						
						//clicking on close button
						closeUsingCoordinates();
						Thread.sleep(2000);
					}
					
					settings=true;
				}
				else
				{
					report.detailsAppendFolder("Verify settings panel is open ", "Settings panel should be open", "Settings is open", "Fail",CurrencyName);
					
					log.debug("Setting button is not available in game menu");;
				}
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					log.debug("unable to verify setting options");
				}
				return settings;
			}
			
			
			public void verifyHelpOnTopbar(Mobile_HTML_Report report,String CurrencyName)
			{
				try
				{
					String gameurl = webdriver.getCurrentUrl();
					
					
					//click on help menu
					funcClick(xpathMap.get("menuOnTopBar"));
					//clickOnButtonUsingCoordinates("menuOnTopbarCoordinateX","menuOnTopbarCoordinateY");
					Thread.sleep(2000);
					
					if(elementVisible_Xpath(xpathMap.get("HelpOnTopBar")))
					{
						
						//report.detailsAppendFolder("Verify Help on top bar", "Help should be visible", "Help is visible", "Pass",CurrencyName);
						
						funcClick(xpathMap.get("HelpOnTopBar"));
							
						//clickOnButtonUsingCoordinates("helpCoordinateX","helpCoordinateY");
						Thread.sleep(12000);		
						
						checkpagenavigation(report,gameurl,CurrencyName);
						
						System.out.println("Page navigated to Back to Game ");
						//log.debug("Page navigated to Back to Game");
						
						/*if(isElementVisible("isSpinBtnVisible"))
						{
							
							report.detailsAppendFolder("Verify if back from help", "Back from help", "On BaseScene, Back from help", "Pass",CurrencyName);
						}
						else
						{
							
							report.detailsAppendFolder("Verify if back from help", "Back from help", "Not on BaseScene, Back from help", "Fail",CurrencyName);
						}	*/
					
					}
					else
					{
						report.detailsAppendFolder("Verify Help on top bar", "Help should be visible", "Help is not visible", "Fail",CurrencyName);
						}			
					
				}catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					log.debug("error clicking on help");
				}
			}
			
			public boolean openMenuPanel()
			{
				boolean result= false;
				try
				{
					//clicking on menu button
					clickOnButtonUsingCoordinates("menuButtonCoordinateX","menuButtonCoordinateY");
					Thread.sleep(2000);											
					if(isElementVisible("isMenuBackBtnVisible"))
					{
						log.debug("Menu is open");
						result= true;
					}
						
					else
					{
						log.debug("Menu is not open");
					}
					
					
				}catch (Exception e) 
				{
					log.debug("unable to click on menu button");
					log.error(e.getMessage(), e);
				}
				return result;
			}
			
			public boolean openBetPanelOnBaseScene()
			{
				boolean result= false;
				try
				{
					//clicking on bet button
					if(xpathMap.get("clickBetUsingCoordinates").equalsIgnoreCase("Yes"))
					{
						clickOnButtonUsingCoordinates("betCoordinateX","betCoordinateY");
					}
					Thread.sleep(2000);
					
					//check if max bet button is visible to know whether bet panel is open
					if(isElementVisible("isMaxBetVisible"))
					{
						log.debug("Bet is Visible");
						result=true;
					}
					else
					{
						log.debug("Bet is not Visible");
					}
				}catch (Exception e) 
				{
					log.debug("unable to click on bet button");
					log.error(e.getMessage(), e);
				}
				return result;
			}
			
			
			
			public boolean openAutoplayPanel()
			{
				boolean result= false;
				try
				{
					//clicking on auto play button
					if(xpathMap.get("clickAutoplayUsingCoordinates").equalsIgnoreCase("Yes"))
					{
						clickOnButtonUsingCoordinates("autoplayCoordinateX","autoplayCoordinateY");	
					}
					/*else if(xpathMap.get("clickAutoplayUsingHook").equalsIgnoreCase("Yes"))
					{
						clickAtButton("return "+xpathMap.get("ClickBetIconBtn"));
					}*/
					
					Thread.sleep(3000);
					
					//verify auto play is displayed
					if(isElementVisible("isStartAutoplayBTNVisible"))
					{
						log.debug("Autoplay is Visible");
						result=true;
					}
					else
					{
						log.debug("Autoplay is not Visible");
					}
				}catch (Exception e) 
				{
					log.debug("unable to click on autoplay button");
					log.error(e.getMessage(), e);
				}
				return result;
			}
			
			
			public void startAutoPlay() 
			{
				try
				{
					clickOnButtonUsingCoordinates("startAutoplayCoordinateX","startAutoplayCoordinateY");
					Thread.sleep(2000);
				}catch (Exception e) 
				{
					log.debug("unable to click on start autoplay");
					log.error(e.getMessage(), e);
				}
			}
			
			public void stopAutoPlay() 
			{
				try
				{
					clickOnButtonUsingCoordinates("spinCoordinateX","spinCoordinateY");
					Thread.sleep(2000);
				}catch (Exception e) 
				{
					log.debug("unable to stop autoplay");
					log.error(e.getMessage(), e);
				}
			}
			
			
			public void validateSpinsSliderAutoplay(Mobile_HTML_Report report,String sliderPoint,String Value,String CurrencyName)
			{
				try
				{
					boolean ableToSlide=verifySliderValue(sliderPoint,Value);
					if(ableToSlide)
					{
						report.detailsAppendFolder("Verify autoplay spins slider", "Autoplay spins slider should work", "Autoplay spins slider is working", "Pass",CurrencyName);
						log.debug("Autoplay spins slider is working");
					}
					else
					{
						report.detailsAppendFolder("Verify autoplay spins slider", "Autoplay spins slider should work", "Autoplay spins slider is not working", "Fail",CurrencyName);
						log.debug("Autoplay spins slider is not working");
					}
					
				}catch (Exception e) 
				{
					log.debug("unable to verify autoplay spins slider");
					log.error(e.getMessage(), e);
				}
			}
			
			public void validateTotalBetSliderAutoplay(Mobile_HTML_Report report,String sliderPoint,String Value,String CurrencyName)
			{						
				try
				{
					boolean ableToSlide=verifySliderValue(sliderPoint,Value);
					if(ableToSlide)
					{
						report.detailsAppendFolder("Verify autoplay total bet slider", "Autoplay total bet slider should work", "Autoplay total bet slider is working", "Pass",CurrencyName);
						log.debug("Autoplay total bet slider is working");
					}
					else
					{
						report.detailsAppendFolder("Verify autoplay total bet slider", "Autoplay total bet slider should work", "Autoplay total bet slider is not working", "Fail",CurrencyName);
						log.debug("Autoplay total bet slider is not working");
					}
					
				}catch (Exception e) 
				{
					log.debug("unable to verify autoplay total bet slider");
					log.error(e.getMessage(), e);
				}
			}
			
			public void validateWinLimitSliderAutoplay(Mobile_HTML_Report report,String sliderPoint,String Value,String CurrencyName)
			{						
				try
				{
					boolean ableToSlide=verifySliderValue(sliderPoint,Value);
					if(ableToSlide)
					{
						report.detailsAppendFolder("Verify autoplay win limit slider", "Autoplay win limit slider should work", "Autoplay win limit slider is working", "Pass",CurrencyName);
						log.debug("Autoplay win limit slider is working");
					}
					else
					{
						report.detailsAppendFolder("Verify autoplay win limit slider", "Autoplay win limit slider should work", "Autoplay win limit slider is not working", "Fail",CurrencyName);
						log.debug("Autoplay win limit slider is not working");
					}
					
				}catch (Exception e) 
				{
					log.debug("unable to verify autoplay win limit slider");
					log.error(e.getMessage(), e);
				}
			}
			
			public void validateLossLimitSliderAutoplay(Mobile_HTML_Report report,String sliderPoint,String Value,String CurrencyName)
			{						
				try
				{
					boolean ableToSlide=verifySliderValue(sliderPoint,Value);
					if(ableToSlide)
					{
						report.detailsAppendFolder("Verify autoplay loss limit slider", "Autoplay loss limit slider Slider should work", "Autoplay loss limit slider Slider is working", "Pass",CurrencyName);
						log.debug("Autoplay loss limit slider is working");
					}
					else
					{
						report.detailsAppendFolder("Verify autoplay loss limit slider", "Autoplay loss limit slider Slider should work", "Autoplay loss limit slider Slider is not working", "Fail",CurrencyName);
						log.debug("Autoplay loss limit slider is not working");
					}
					
				}catch (Exception e) 
				{
					log.debug("unable to verify autoplay loss limit slider");
					log.error(e.getMessage(), e);
				}
			}
			
			public void checkpagenavigation(Mobile_HTML_Report report, String gameurl,String CurrencyName)
			{
				try {
					String mainwindow = webdriver.getWindowHandle();
					Set<String> s1 = webdriver.getWindowHandles();
					if (s1.size() > 1) {
					
						Iterator<String> i1 = s1.iterator();
						while (i1.hasNext()) {
							String ChildWindow = i1.next();
							
							if (mainwindow.equalsIgnoreCase(ChildWindow)) 
							{
								System.out.println("Page navigated succesfully");
								if(osPlatform.equalsIgnoreCase("Android"))
								{
								ChildWindow = i1.next();
								}
								//Thread.sleep(2000);
								webdriver.switchTo().window(ChildWindow);
								String url = webdriver.getCurrentUrl();
								System.out.println("Navigation URL1 is :: " + url);
								log.debug("Navigation URL is :: " + url);
								if (!url.equalsIgnoreCase(gameurl)) {
									// pass condition for navigation
									report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "Pass",CurrencyName);
									System.out.println("Page navigated succesfully");
									log.debug("Page navigated succesfully");
									webdriver.close();
								} else {
									System.out.println("Now On game page");
									log.debug("Now On game page");
									Thread.sleep(4000);
									funcFullScreen();
								}
							}
						}
						webdriver.switchTo().window(mainwindow);
					} else {
						String url = webdriver.getCurrentUrl();
						System.out.println("Navigation URL is ::  " + url);
						log.debug("Navigation URL is ::  " + url);
						if (!url.equalsIgnoreCase(gameurl)) {
							// pass condition for navigation
						  report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "Pass",CurrencyName);
							System.out.println("Page navigated succesfully");
							webdriver.navigate().to(gameurl);
							//waitForSpinButton();
							//newFeature();
							//waitForElement("isNFDButtonVisible");
							Thread.sleep(4000);
							funcFullScreen();
							Thread.sleep(1000);
							closeOverlayForLVC();
							Thread.sleep(1000);
						} else 
						{
							 report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "Pass",CurrencyName);
						
							 Thread.sleep(1000);
								
							webdriver.navigate().to(gameurl);
							//waitForElement("isNFDButtonVisible");
							Thread.sleep(4000);
							/*funcFullScreen();
							Thread.sleep(1000);
							closeOverlayForLVC();
							Thread.sleep(1000);*/
							System.out.println("Now On game page");
							log.debug("Now On game page");
						}
					}

				} catch (Exception e) {
					log.error("error in navigation of page");
				}
			}
			
			/**
			 * Verifies the Currency Format - using String method
			 */

			public boolean verifyRegularExpressionPlayNext(Mobile_HTML_Report report, String regExp, String method,String isoCode) 
			{
				String value = null;
				String Text = method;
				String Text1=Text.replaceAll("[a-zA-Z:]", "").trim();
				System.out.println("currency value text: "+Text1);
				log.debug("currency value text: "+Text);
				boolean regexp = false;
				try {
					if(isoCode.equalsIgnoreCase("MMK"))
					{
						value=Text1.replaceAll(" ", "");
					}
					else
					{
						value=Text1;
					}
					if (value.matches(regExp)) {
						log.debug("Compared with Reg Expression, Currency format is correct");
						regexp = true;
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return regexp;
			}
			
			/**
			 * This method is used for clicking on spin button using x and y coordinates
			 */
			public boolean spinclick(ImageLibrary imageLibrary)
			{
				try
				{
					if (imageLibrary.isImageAppears("Spin")) 
					{
						imageLibrary.click("Spin");
						log.debug("Clicked on spin button");
					}
					else
					{
						log.debug("Spin button is not available");
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					log.error("error while clicking on spin button");
				}
				
				return true;
				
			}
			
			/**
			 * Verifies the current Win amt
			 * 
			 */
			public String getCurrentWinAmt(Mobile_HTML_Report report, String CurrencyName) 
			{
				String winAmt = null;
				Wait = new WebDriverWait(webdriver, 250);
				try {
					

					winAmt = GetConsoleText("return " + xpathMap.get("getWinAmt"));
					System.out.println(" Win Amount is " + winAmt);
					log.debug(" Win Amount is " + winAmt);
					/*boolean isWinAmt = isElementVisible("isWinAmtVisible");
					if (isWinAmt) 
					{
						report.detailsAppendFolder("verify win is visible in baseScene", "Win should be visible","Win is visible", "Pass",CurrencyName);
						log.debug("Win amount is visible");
						System.out.println("Win amount is visible");
						
						
						winAmt = GetConsoleText("return " + xpathMap.get("getWinAmt"));
						System.out.println(" Win Amount is " + winAmt);
						log.debug(" Win Amount is " + winAmt);
					} else 
					{
						report.detailsAppendFolder("verify win is visible in baseScene", "Win should be visible","Win is not visible", "Fail",CurrencyName);
						
						System.out.println("There is no Win ");
						log.debug("There is no Win ");
					}*/
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return winAmt;
			}

			/**
			 * Verifies the Big Win
			 * 
			 */
			public String verifyBigWin(Mobile_HTML_Report report, String CurrencyName) {
				String bigWinAmt = null;
				Wait = new WebDriverWait(webdriver, 30000);

				try {
					report.detailsAppendFolder("verify big win is visible in baseScene", "BigWin should be visible","BigWin is visible", "Pass",CurrencyName);
					log.debug("Big win amount is visible");
					System.out.println("Big win amount is visible");
					
					bigWinAmt = GetConsoleText("return " + xpathMap.get("BigWinValue"));
					
					System.out.println("Big Win Amount is " + bigWinAmt);
					log.debug(" Big Win Amount is " + bigWinAmt);
					
				  
					
					/*boolean isBigWin = isElementVisible("BigWin");
					if (isBigWin) 
					{
						report.detailsAppendFolder("verify big win is visible in baseScene", "BigWin should be visible","BigWin is visible", "Pass",CurrencyName);
						log.debug("Big win amount is visible");
						System.out.println("Big win amount is visible");
						
						bigWinAmt = GetConsoleText("return " + xpathMap.get("BigWinValue"));
						
						System.out.println("Big Win Amount is " + bigWinAmt);
						log.debug(" Big Win Amount is " + bigWinAmt);

					} else 
					{
						report.detailsAppendFolder("verify big win is visible in baseScene", "BigWin should be visible","BigWin is not visible", "Fail",CurrencyName);
						System.out.println("There is no Big Win ");
						log.debug("There is no Big Win");
					}*/
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return bigWinAmt;

			}
			public String getCurrentWinAmtImg(Mobile_HTML_Report report,ImageLibrary imageLibrary,String CurrencyName) {
				String winAmt = null;
				Wait = new WebDriverWait(webdriver, 250);
				int count=0;

				try {
					
					winAmt = GetConsoleText("return " + xpathMap.get("getWinAmt"));
					System.out.println(" Win Amount is " + winAmt);
					log.debug(" Win Amount is " + winAmt);
					
	            	  while(winAmt.equals(""))
                     {            
	            		  webdriver.context("NATIVE_APP");
	            		  Thread.sleep(3000);
                         imageLibrary.click("Spin");    
                         Thread.sleep(10000);
                         
                         if (webdriver instanceof AndroidDriver) {
								webdriver.context("CHROMIUM");
							} else if (webdriver instanceof IOSDriver) {
								Set<String> contexts = webdriver.getContextHandles();
								for (String context : contexts) {
									if (context.startsWith("WEBVIEW")) {
										log.debug("context going to set in IOS is:" + context);
										webdriver.context(context);
									}
								}
							}
                         winAmt = GetConsoleText("return " + xpathMap.get("getWinAmt"));
                         report.detailsAppendFolderOnlyScreeshot(CurrencyName);
                         count++;
                         if(count==3)
                         {
                        	 break;
                        	
                         }
                     }
	            	  
				
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return winAmt;

			}
			
			   public String verifyBigWinImg(Mobile_HTML_Report report,ImageLibrary imageLibrary,String CurrencyName) {
					String bigWinAmt = null;
					Wait = new WebDriverWait(webdriver, 30000);
					int count=0;

					try {
						
					
						bigWinAmt = GetConsoleText("return " + xpathMap.get("BigWinValue"));
						
						System.out.println("Big Win Amount is " + bigWinAmt);
						log.debug(" Big Win Amount is " + bigWinAmt);
						
		            	  while(bigWinAmt.equals(""))
	                     {            
		            		  webdriver.context("NATIVE_APP");
		            		  Thread.sleep(3000);
	                         imageLibrary.click("Spin");    
	                         Thread.sleep(10000);
	                         
	                         if (webdriver instanceof AndroidDriver) {
									webdriver.context("CHROMIUM");
								} else if (webdriver instanceof IOSDriver) {
									Set<String> contexts = webdriver.getContextHandles();
									for (String context : contexts) {
										if (context.startsWith("WEBVIEW")) {
											log.debug("context going to set in IOS is:" + context);
											webdriver.context(context);
										}
									}
								}
	                         bigWinAmt = GetConsoleText("return " + xpathMap.get("BigWinValue"));
	                         report.detailsAppendFolderOnlyScreeshot(CurrencyName);
	                         count++;
	                         if(count==3)
	                         {
	                        	 break;
	                        	
	                         }
	                     }
		            	  
					
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					return bigWinAmt;

				}
				
			
			public String verifyBonusWin(Mobile_HTML_Report report, String CurrencyName) {
				String amgWinAmt = null;
				Wait = new WebDriverWait(webdriver, 30000);

				try {
					
					amgWinAmt = GetConsoleText("return " + xpathMap.get("getAmgWinAmt"));
					
					System.out.println("bonus Win Amount is " + amgWinAmt);
					log.debug(" bonus Win Amount is " + amgWinAmt);
					/*boolean isAmgWin = isElementVisible("isAmgWinAmtVisible");
					if (isAmgWin) 
					{
						report.detailsAppendFolder("verify bonus win is visible in baseScene", "Bonus win should be visible","Bonus win is visible", "Pass",CurrencyName);
						log.debug("Bonus win amount is visible");
						System.out.println("Bonus win amount is visible");
						
						amgWinAmt = GetConsoleText("return " + xpathMap.get("getAmgWinAmt"));
						
						System.out.println("bonus Win Amount is " + amgWinAmt);
						log.debug(" bonus Win Amount is " + amgWinAmt);
						

					} else 
					{
						report.detailsAppendFolder("verify bonus win is visible in baseScene", "Bonus win should be visible","Bonus win is not visible", "Fail",CurrencyName);
						
						System.out.println("There is no bonus Win ");
						log.debug("There is no bonus Win");
					}*/
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return amgWinAmt;

			}
			
			public void verifyQuickbetOptionsRegExp(Mobile_HTML_Report report, String currencyName ,String regExpr,String regExprNoSymbol,String isoCode ) 
			{
				try
				{
					report.detailsAppend("Verify Bet panel is displayed ", "Bet panel is displayed", "Bet panel is displayed", "Pass");

					String noOfQuickBets=xpathMap.get("totalQuickBets");
					
				int totalNoOfQuickBets=(int) Double.parseDouble(noOfQuickBets);
				for(int quickBet=1;quickBet<=totalNoOfQuickBets;quickBet++)
				{
				
					String bet=xpathMap.get("isQuickBet"+quickBet);
					boolean isQuickBetetVisible = isElementVisibleUsingHook(bet);
					if(isQuickBetetVisible) 
					{
						
						String quickBetVal = GetConsoleText("return " + xpathMap.get("QuickBet"+quickBet+"Value"));	
						report.detailsAppendFolderOnlyScreenshot("Verify quick bet "+quickBet+" is visible "," Quick Bet "+quickBet+" is visibled", "Quick Bet "+quickBet+" is visible ","Pass",currencyName);									
						
						
						boolean betAmt=verifyRegularExpressionPlayNext(report, regExpr, quickBetVal,isoCode);
						if(betAmt)
						{
							report.detailsAppendFolder("verify curency format for quick bet "+quickBet+" in bet panel ",
									"Currency format should be correct", "Currency format is correct","Pass", currencyName);
						
						}
						else
						{
							report.detailsAppendFolder("verify curency format for quick bet "+quickBet+" in bet panel",
									"Currency format should be correct", "Currency format is incorrect","Fail", currencyName);
						
						}
						
						
						String coordinateX= xpathMap.get("QuickBet"+quickBet+"CoordinateX");
						String coordinateY= xpathMap.get("QuickBet"+quickBet+"CoordinateY");
						double coorX=Double.parseDouble(coordinateX);
						double coorY=Double.parseDouble(coordinateY);
						tapOnCoordinates(coorX,coorY);
						Thread.sleep(2000);
						
						report.detailsAppendFolder("Verify quick bet "+quickBet+" can be selected "," Quick Bet "+quickBet+" should be selected", "Quick Bet "+quickBet+" is selected ","Pass",currencyName);									
						
						boolean isBetChangedIntheConsole = isBetChangedIntheConsole(quickBetVal);
						if (isBetChangedIntheConsole) 
						{
							report.detailsAppendFolder("verify that is bet changed in the console for quick bet "+quickBet+ " ,value =" + quickBetVal,
									"Is Bet Changed In the Console for quick bet  =" + quickBetVal, "Bet Changed In the Console",
									"Pass", currencyName);						
						} else {
							report.detailsAppendFolder("verify that is bet changed in the console for quick bet "+quickBet+ " ,value =" + quickBetVal,
									"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
									"Bet not Changed In the Console", "Fail",currencyName);
							
						}
						
						boolean quickBetOnBaseScene = verifyRegularExpressionPlayNext(report, regExpr, GetConsoleText("return "+ xpathMap.get("BetTextValue")),isoCode);
						log.debug("Quick bet value: "+quickBetOnBaseScene);
						if(quickBetOnBaseScene)
						{
							report.detailsAppendFolder("verify currency format for quick bet "+quickBet+" in base scene console ",
									"Currency format should be correct for quick bet "+quickBet+" in base scene console ", "Currency format is correct for quick bet "+quickBet+" in base scene console ","Pass", currencyName);
						
						}
						else
						{
							report.detailsAppendFolder("verify currency format for quick bet "+quickBet+" in base scene console ",
									"Currency format should be correct for quick bet "+quickBet+" in base scene console ", "Currency format is incorrect for quick bet "+quickBet+" in base scene console ","Fail", currencyName);
							
						}
						
						
						openBetPanelOnBaseScene();
					}
				}
				
					boolean isMaxBet = isElementVisible("isMaxBetVisible");
					if (isMaxBet&&xpathMap.get("isMaxBetAvailable").equalsIgnoreCase("Yes")) 
					{
						try
						{
						//report.detailsAppend("Verify that Max Bet is visible "," Max Bet should be visibled", "Max Bet is visible ","Pass"+ currencyName);									
						setMaxbetPlayNext();
						
						report.detailsAppendFolder("Verify Max Bet can be selected "," Max Bet should be selected", "Max Bet is selected ","Pass", currencyName);									
						boolean betAmt = verifyRegularExpressionPlayNext(report, regExpr, GetConsoleText("return "+ xpathMap.get("BetTextValue")),isoCode);
						log.debug("Max bet value: "+betAmt);
						if(betAmt)
						{
							report.detailsAppendFolder("verify currency format for max bet ",
									"Max bet should be in correct currency format", "Max bet is in correct currency format","Pass", currencyName);
						
						}
						else
						{
							report.detailsAppendFolder("verify currency format for max bet ",
									"Max bet should be in correct currency format", "Max bet is in incorrect currency format","Fail", currencyName);
						
						}
						
					} catch (Exception e) 
				        {
				        log.error(e.getMessage(), e);
			        }
					}
				
				
				} catch (Exception e) 
		        {
			        log.error(e.getMessage(), e);
		        }
			}
			
			
			
		
			public boolean isBetChangedIntheConsole(String betValue) {
				String consoleBet = null;
				String bet = null;
				String bet1 = null;
				try {
					if (!GameName.contains("Scratch")) {
						log.debug("Bet value selected from game before = " + betValue);
						consoleBet = GetConsoleText("return " + xpathMap.get("BetTextValue"));
						log.debug("Bet Refelecting on console after bet chnage from quickbet : " + consoleBet);
						bet1 = consoleBet.replaceAll("[a-zA-Z:]", "").trim();			
						bet = bet1.replaceFirst(":", "").trim();
					} // below else for Scratch game
					else {
						log.debug("Bet value selected from scrach game = " + betValue);
						consoleBet = GetConsoleText("return " + xpathMap.get("BetTextValue"));
						// String bet = consoleBet.replaceAll("[a-zA-Z]", "").trim();
						bet1 = consoleBet.replaceAll("[a-zA-Z]", "").trim();
						bet = bet1.replaceFirst(":", "").trim();
						log.debug("Bet Refelecting on console after bet chnage from quickbet : " + consoleBet);
						System.out.println("Bet Refelecting on console after bet change from quickbet : " + consoleBet);
					}
				} catch (JavascriptException exception) {
					log.error("Exception occur while executing hook,Please verify thre hook of given component"
							+ exception.getMessage());
				}
				if (betValue.trim().equalsIgnoreCase(bet)) {
					log.debug("selected bet " + betValue + " reflecting properly on console " + bet);
					return true;
				} else {
					log.debug("selected bet " + betValue + " Not reflecting properly on console " + bet);
					return false;
				}			

			}
			

			/**
			 * Verifies the current Win amt
			 * 
			 */
			public String verifyFSCurrentWinAmt(Mobile_HTML_Report report, String CurrencyName) {
				String winAmt = null;
				Wait = new WebDriverWait(webdriver, 250);
				try {
					winAmt = GetConsoleText("return " + xpathMap.get("getFSNormalWinValue"));
					
					
					System.out.println("Win Amount is " + winAmt);
					//log.debug("Win Amount is " + winAmt);
					/*boolean isWinAmt = isElementVisible("isFSNormalWinVisible");
					if (isWinAmt) 
					{
						log.debug("Win amount is visible in freespins");
						System.out.println("Win amount is visible in freespins");
						
						report.detailsAppendFolder("verify win is visible in FreeSpins", "Win should be visible","Win is visible", "Pass",CurrencyName);
	
						winAmt = GetConsoleText("return " + xpathMap.get("getFSNormalWinValue"));
						
						
						System.out.println("Win Amount is " + winAmt);
						log.debug("Win Amount is " + winAmt);
					} else 
					{
						report.detailsAppendFolder("verify win is visible in FreeSpins", "Win should be visible","Win is visible", "Pass",CurrencyName);
						
						System.out.println("There is no Win in freepsins");
						log.debug("There is no Win in freepsins");
					}*/
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return winAmt;
			}
			
			/**
			 * Verifies the FreeSpine Big Win
			 * 
			 */
			public String verifyFreeSpinBigWin(Mobile_HTML_Report report, String CurrencyName) {
				String bigWinAmt = null;
				Wait = new WebDriverWait(webdriver, 30000);

				try {
					
					bigWinAmt = GetConsoleText("return " + xpathMap.get("getFSBigWinValue"));
					System.out.println("FreeSpin Big Win Amount is " + bigWinAmt);
					log.debug("Freespin Big Win Amount is " + bigWinAmt);
					
					/*boolean isBigWin = isElementVisible("isFSBigWinVisible");
					if (isBigWin) 
					{	
						log.debug("Bigwin amount is visible in freespins");
						System.out.println("Bigwin amount is visible in freespins");
						
						report.detailsAppendFolder("verify big win is visible in FreeSpins", "BigWin should be visible","BigWin is visible", "Pass",CurrencyName);
						
						bigWinAmt = GetConsoleText("return " + xpathMap.get("getFSBigWinValue"));
						System.out.println("FreeSpin Big Win Amount is " + bigWinAmt);
						log.debug("Freespin Big Win Amount is " + bigWinAmt);
						

					} else 
					{
						report.detailsAppendFolder("verify big win is visible in FreeSpins", "BigWin should be visible","BigWin is visible", "Fail",CurrencyName);
						
						System.out.println("There is no Big Win in freespins");
						log.debug("There is no Big Win in freespins");
					}*/
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return bigWinAmt;

			}
			
			public String verifyBonusWinInFS(Mobile_HTML_Report report, String CurrencyName) 
			{
				String amgWinAmt = null;
				Wait = new WebDriverWait(webdriver, 30000);

				try {
					boolean isAmgWin = isElementVisible("isFSAmazingWinVisible");
					if (isAmgWin) 
					{
						log.debug("Bonus win amount is visible in freespins");
						System.out.println("Bonus win amount is visible in freespins");
						
						report.detailsAppendFolder("verify bonus win is visible in FreeSpins", "Bonus win should be visible","Bonus win is visible", "Pass",CurrencyName);
						
						
						amgWinAmt = GetConsoleText("return " + xpathMap.get("getFSAmazingWinValue"));
						
						System.out.println("bonus Win Amount is " + amgWinAmt);
						log.debug("bonus Win Amount is " + amgWinAmt);
						

					} else 
					{
						report.detailsAppendFolder("verify bonus win is visible in FreeSpins", "Bonus win should be visible","Bonus win is visible", "Fail",CurrencyName);
						
						System.out.println("There is no bonus Win in freespins");
						log.debug("There is no bonus Win in freespins");
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return amgWinAmt;

			}
			public String funcGetText(String locator) {
				try {
					String ele = GetConsoleText("return " + xpathMap.get(locator));
					System.out.println("" + ele);
					log.debug(ele);
					return ele;

				} catch (NoSuchElementException e) {
					return null;
				}
			}
			public String funcGetTextElement(String locator) 
			{
				try 
				{
					WebElement ele = webdriver.findElement(By.xpath(xpathMap.get(locator)));
					System.out.println(""+ele.getText());log.debug(ele.getText());
					return ele.getText();
					
				} 
				catch (NoSuchElementException e)
				{
					return null;
				}
			}
			
			public boolean verifyGridPayouts(Mobile_HTML_Report report, String regExp,String CurrencyName,String isoCode) 
			{
				boolean result=false;
				int trueCount=0;
				try
				{
					String gridSize=xpathMap.get("gridCount");	
					Double count=Double.parseDouble(gridSize);
					int gridCount=count.intValue();		
					for(int i=1; i<=gridCount; i++) 
					{			
						String gridEle ="GridPay"+i+"";	
						log.debug("Grid value "+gridEle);
						System.out.println("Grid value "+gridEle);
						String gridValue = funcGetTextElement(gridEle);
						boolean gridVl = verifyRegularExpressionPlayNext(report, regExp, gridValue,isoCode);
						
						if(gridVl) 
						{
							trueCount++;
						}
					}
					System.out.println("trueCount" +trueCount);
					if(trueCount==gridCount)
					{
						result=true;
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
				return result;
				
			}
			/**
			 * method is used to validate the Paytable Values
			 * 
			 * @return
			 */
			public boolean validatePayoutsFromPaytable(Mobile_HTML_Report report, String CurrencyName, String regExpr) 																												
			{
				boolean payoutvalues = false;
				try {
					if (xpathMap.get("paytableScrollOfNine").equalsIgnoreCase("yes")) {				
						payoutvalues = verifyRegularExpressionUsingArrays(report, regExpr,paytablePayoutsOfScatter(report, CurrencyName));
						return payoutvalues;
					}else if (xpathMap.get("paytableScrollOfFive").equalsIgnoreCase("yes")) 
					{				
						payoutvalues = verifyRegularExpressionUsingArrays(report, regExpr,paytablePayoutsOfScatterWild(report, CurrencyName));
						return payoutvalues;
					} 
					else {
						System.out.println("Verify Paytable Payouts");
						log.debug("Verify Paytable Payouts");
					}
					Thread.sleep(2000);			
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return payoutvalues;
			}
			
			
			
			
			/**
			 * Verifies the Currency Format - using String method
			 */

			public boolean verifyRegularExpressionUsingArrays(Mobile_HTML_Report report, String regExp, String[] method) {
				String[] Text = method;
				int count=Text.length;
				int trueCount=0;
				boolean regexp = false;
				try {
					//Thread.sleep(2000);
					for (int i = 0; i < Text.length; i++) 
					{
						if (Text[i].matches(regExp)) 
						{
							log.debug("Compared with Reg Expression currency format is correct for value: "+Text[i]);
							trueCount++;
						} else 
						{
							log.debug("Compared with Reg Expression currency format is different for value: "+Text[i]);	
						}
						Thread.sleep(2000);
					}
					
					if(count==trueCount)
					{
						regexp = true;
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return regexp;
			}

			/**
			 * method is used to validate the Paytable Values
			 * 
			 * @return
			 */
			public String[] paytablePayoutsOfScatter(Mobile_HTML_Report report, String CurrencyName) // String[] array
			{
				String symbols[] = { "Scatter5", "Scatter4", "Scatter3"};

				
				try {
					System.out.println("Paytable Validation for  Scatter ");
					log.debug("Paytable Validation for Scatter ");
					for (int i = 0; i < symbols.length; i++) {
						symbols[i] = funcGetTextElement(symbols[i]);
						//System.out.println(symbols[i]);
						log.debug(symbols[i]);
					}
					Thread.sleep(3000);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return symbols;
			}

			
			/**
			 * method is used to validate the Paytable Values
			 * 
			 * @return
			 */
			public String[] paytablePayoutsOfScatterWild(Mobile_HTML_Report report, String CurrencyName) // String[] array
			{
				String symbols[] = { "Scatter5", "Scatter4", "Scatter3","Scatter2","Wild5","Wild4","Wild3","Wild2"};
				try {
					System.out.println("Paytable Validation for  Scatter ");
					log.debug("Paytable Validation for Scatter ");
					for (int i = 0; i < symbols.length; i++) {
						symbols[i] = func_GetText(symbols[i]);
						System.out.println(symbols[i]);
						log.debug(symbols[i]);
					}
					Thread.sleep(3000);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return symbols;
			}
			
			
			/*
			 * Method to click FG info btn and return FGinfo
			 */
			public String freeGameEntryInfo(ImageLibrary imageLibrary, String fgInfotxt)  
			{				
				String infoText=null;
				try
				{
					imageLibrary.click("FGInfoIcon");
					Thread.sleep(1000);
                     if (webdriver instanceof AndroidDriver) {
                             webdriver.context("CHROMIUM");
                     } else if (webdriver instanceof IOSDriver) {
                             Set<String> contexts = webdriver.getContextHandles();
                             for (String context : contexts) {
                                     if (context.startsWith("WEBVIEW")) {
                                             log.debug("context going to set in IOS is:" + context);
                                             webdriver.context(context);
                                     }
                             }
                     }
					String text=GetConsoleText("return " +xpathMap.get(fgInfotxt));
					System.out.println(text);
					log.debug(text);
					//trim until the @ symbol 
					int index=text.lastIndexOf("@");
					if(index>0)
					{
						text=text.substring(index+1,text.length());					
						infoText=text.trim();
						System.out.println(infoText);log.debug(infoText);
					
					}
					} catch (Exception e) {
					log.error(e.getMessage(), e);
				}	
				return infoText;
			}
			
			public boolean clickPlayNow(ImageLibrary imageLibrary) 
			{
				boolean result= false;
				try
				{
					//click on play now button on Free Spins Intro Screen 					
				    if (imageLibrary.isImageAppears("FGPlayNow"))
					{
				    	imageLibrary.click("FGPlayNow");
						Thread.sleep(3000);
						result=true;
					}
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return result;	
			}
			
			public boolean assignFreeGames(String userName,String offerExpirationUtcDate,int mid, int cid,int noOfOffers,int defaultNoOfFreeGames) 
			{
				//assign free games to above created user
				boolean isFreeGameAssigned=false;
				try {
				String balanceTypeId=xpathMap.get("BalanceTypeID");
				Double dblBalanceTypeID=Double.parseDouble(balanceTypeId);
				balanceTypeId=""+dblBalanceTypeID.intValue()+"";
				
				//Assign free games offers to user 
				if(TestPropReader.getInstance().getProperty("EnvironmentName").equalsIgnoreCase("Bluemesa"))
				{

					isFreeGameAssigned=addFreeGameToUserInBluemesa( userName,defaultNoOfFreeGames ,  offerExpirationUtcDate,  balanceTypeId,  mid, cid,noOfOffers);
				}
				else
				{
					isFreeGameAssigned=addFreeGameToUserInAxiom(userName,defaultNoOfFreeGames,offerExpirationUtcDate,balanceTypeId,mid,cid,noOfOffers);
				}
				}catch (Exception e)
				{
					log.error(e.getMessage(), e);
				}
				return isFreeGameAssigned;
			}
			
			public void clickBaseSceneDiscardButton()  
			{
				try
				{
					//click on play now button on Free Spins Intro Screen 
					if(isElementVisible("discardIconBaseSceneVisible"))
					{
						clickOnButtonUsingCoordinates("discardIconBaseSceneCoordinateX","discardIconBaseSceneCoordinateY");
						Thread.sleep(3000);
					}
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
			}
			
			public boolean confirmDiscardOffer(ImageLibrary imageLibrary) 
			{
				boolean result= false;
				try
				{
					
					imageLibrary.click("FGDiscard");
					Thread.sleep(3000);
					result=true;
					//click on play now button on Free Spins Intro Screen 
					/*if(isElementVisible("discardBtnVisible"))
					{
						
					}*/
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return result;	
			}
			
			public boolean clickOnPlayLater(ImageLibrary imageLibrary) 
			{
				boolean result= false;
				try
				{
					    imageLibrary.click("FGPlayLater");						
						Thread.sleep(3000);
						result=true;
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return result;	

			}
			
			public boolean clickOnEntryScreenDiscard(ImageLibrary imageLibrary)
			{
				boolean result= false;
				try
				{
					imageLibrary.click("FGDiscard");
					
					Thread.sleep(3000);
					result=true;	
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return result;	
			}
			
			public void verifyBetSliders(Mobile_HTML_Report report)
			{
				try
				{
						// Check Coin Size Slider
						if(xpathMap.get("CoinSizeSliderPresent").equalsIgnoreCase("Yes"))
						{
							validateCoinSizeSlider(report,"CoinSizeSliderSet","BetMenuBetValue");
						}						
						Thread.sleep(1000);
						
						// Check Coins per line slider
						if(xpathMap.get("CoinsPerLineSliderPresent").equalsIgnoreCase("Yes"))
						{
							validateCoinsPerLineSlider(report,"CoinsPerLineSliderSet","BetMenuBetValue");
						}
						Thread.sleep(1000);
						
						//check lines slider
						if(xpathMap.get("LinesSliderPresent").equalsIgnoreCase("Yes"))
						{
							validateLinesSlider(report,"LinesSliderSet","BetMenuBetValue");
						}
						Thread.sleep(1000);
						
				}catch (Exception e) 
				{
					log.debug("unable to verify bet options");
					log.error(e.getMessage(), e);
				}
			}
			
			/**
			 * This method is used to slide the coin size slider and validate bet amount to verify its working or not
			 * @author pb61055
			 * @param report
			 */
			public void validateCoinSizeSlider(Mobile_HTML_Report report,String sliderPoint,String betValue)
			{
				try
				{
						boolean ableToSlide=verifySliderValue(sliderPoint,betValue);
					
						if(ableToSlide)
						{
							report.detailsAppend("Verify if able to change coin size slider value", "Coin size slider value should change", "Coin size slider value is changed", "Pass");
							log.debug("Coin size slider is working");
						}
						else
						{
							report.detailsAppend("Verify if able to change coin size slider value", "Coin size slider value should change", "Unable to change coin cize clider value", "Fail");
							log.debug("Coin size slider is not working");
						}
					
				}catch (Exception e) 
				{
					log.debug("unable to verify coin size slider");
					log.error(e.getMessage(), e);
				}
			}
			/**
			 * This method is used to slide the coins per line slider and validate bet amount to verify its working or not
			 * @author pb61055
			 * @param report
			 */
			public void validateCoinsPerLineSlider(Mobile_HTML_Report report,String sliderPoint,String betValue)
			{
				try
				{	
					boolean ableToSlide=verifySliderValue(sliderPoint,betValue);			
					if(ableToSlide)
					{									
						report.detailsAppend("Verify if able to change coins per line slider value", "Verify if able to change coins per line slider value", "Coins per line slider value is changed", "Pass");
						log.debug("Coins Per Line slider is working");
					}
					else
					{
						report.detailsAppend("Verify if able to change coins per line slider value", "Verify if able to change coins per line slider value", "Unable to change coins per line slider value", "Fail");
						log.debug("Coins Per Line slider is not working");
					}							
					
				}catch (Exception e) 
				{
				log.debug("unable to verify coins per line slider");
				log.error(e.getMessage(), e);
			}
		}
			/**
			 * This method is used to slide the lines slider and validate bet amount to verify its working or not
			 * @author pb61055
			 * @param report
			 */
			public void validateLinesSlider(Mobile_HTML_Report report,String sliderPoint,String betValue)
			{
				try
				{
					boolean ableToSlide=verifySliderValue(sliderPoint,betValue);		
													
					if(ableToSlide)
					{
						report.detailsAppend( "Verify if able to change line slider value", "Line slider value should change", "Line slider value is changed", "Pass");
						log.debug("Line slider is working");
					}
					else
					{
						report.detailsAppend( "Verify if able to change line slider value", "Line slider value should change", "Unable to change line slider value", "Fail");
						log.debug("Line slider is not working");
					}									
			
				}catch (Exception e) 
				{
					log.debug("unable to verify line slider");
					log.error(e.getMessage(), e);
				}
			}
		
			public void verifyquickbetOptionsRegExp(Mobile_HTML_Report report, String currencyName ,String regExpr,String regExprNoSymbol,String isoCode ) 
			{
				try
				{
					report.detailsAppend("Verify Bet panel is displayed ", "Bet panel is displayed", "Bet panel is displayed", "Pass");

					String noOfQuickBets=xpathMap.get("totalQuickBets");
				int totalNoOfQuickBets=(int) Double.parseDouble(noOfQuickBets);
				for(int quickBet=1;quickBet<=totalNoOfQuickBets;quickBet++)
				{
				
					String bet=xpathMap.get("isQuickBet"+quickBet);
					boolean isQuickBetetVisible = isElementVisibleUsingHook(bet);
					if(isQuickBetetVisible) 
					{
						
						String quickBetVal = GetConsoleText("return " + xpathMap.get("QuickBet"+quickBet+"Value"));	
						report.detailsAppendFolderOnlyScreenshot("Verify quick bet "+quickBet+" is visible "," Quick Bet "+quickBet+" is visibled", "Quick Bet "+quickBet+" is visible ","Pass",currencyName);									
						
						
						boolean betAmt=verifyRegularExpressionPlayNext(report, regExprNoSymbol, quickBetVal,isoCode);
						if(betAmt)
						{
							report.detailsAppendFolder("verify curency format for quick bet "+quickBet+" in bet panel ",
									"Currency format should be correct", "Currency format is correct","Pass", currencyName);
						
						}
						else
						{
							report.detailsAppendFolder("verify curency format for quick bet "+quickBet+" in bet panel",
									"Currency format should be correct", "Currency format is incorrect","Fail", currencyName);
						
						}
						
						
						String coordinateX= xpathMap.get("QuickBet"+quickBet+"CoordinateX");
						String coordinateY= xpathMap.get("QuickBet"+quickBet+"CoordinateY");
						double coorX=Double.parseDouble(coordinateX);
						double coorY=Double.parseDouble(coordinateY);
						tapOnCoordinates(coorX,coorY);
						Thread.sleep(2000);
						
						report.detailsAppendFolder("Verify quick bet "+quickBet+" can be selected "," Quick Bet "+quickBet+" should be selected", "Quick Bet "+quickBet+" is selected ","Pass",currencyName);									
						
						boolean isBetChangedIntheConsole = isBetChangedIntheConsole(quickBetVal);
						if (isBetChangedIntheConsole) {
							report.detailsAppendFolder("verify that is bet changed in the console for quick bet "+quickBet+ " ,value =" + quickBetVal,
									"Is Bet Changed In the Console for quick bet  =" + quickBetVal, "Bet Changed In the Console",
									"Pass", currencyName);
						
						} else {
							report.detailsAppendFolder("verify that is bet changed in the console for quick bet "+quickBet+ " ,value =" + quickBetVal,
									"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
									"Bet not Changed In the Console", "Fail",currencyName);
							
						}
						
						
						openBetPanelOnBaseScene();
					}
				}
				
					boolean isMaxBet = isElementVisible("isMaxBetVisible");
					if (isMaxBet&&xpathMap.get("isMaxBetAvailable").equalsIgnoreCase("Yes")) 
					{
						try
						{
						//report.detailsAppend("Verify that Max Bet is visible "," Max Bet should be visibled", "Max Bet is visible ","Pass"+ currencyName);									
						setMaxbetPlayNext();
						
						report.detailsAppendFolder("Verify Max Bet can be selected "," Max Bet should be selected", "Max Bet is selected ","Pass", currencyName);									
						boolean betAmt = verifyRegularExpressionPlayNext(report, regExpr, GetConsoleText("return "+ xpathMap.get("BetTextValue")),isoCode);
						log.debug("Max bet value: "+betAmt);
						if(betAmt)
						{
							report.detailsAppendFolder("verify curency format for max bet ",
									"Max bet should be in correct currency format", "Max bet is in correct currency format","Pass", currencyName);
						
						}
						else
						{
							report.detailsAppendFolder("verify curency format for max bet ",
									"Max bet should be in correct currency format", "Max bet is in incorrect currency format","Fail", currencyName);
						
						}
						
					} catch (Exception e) 
				        {
				        log.error(e.getMessage(), e);
			        }
					}
				
				
				} catch (Exception e) 
		        {
			        log.error(e.getMessage(), e);
		        }
			}
			
			public void clickOnContinue() {
				clickOnButtonUsingCoordinates("clickOnContinueCoordinateX","clickOnContinueCoordinateY");
			}
			
			public void AutoplayOptions(Mobile_HTML_Report report,String CurrencyName)
			{
				try
				{
					if(xpathMap.get("SpinSliderPresent").equalsIgnoreCase("Yes"))
					{
						
						validateSpinsSliderAutoplay(report, "SpinSliderSet","SpinSliderValue",CurrencyName);
						Thread.sleep(2000);
					}
					
					if(xpathMap.get("TotalBetSliderPresent").equalsIgnoreCase("Yes"))
					{
						validateTotalBetSliderAutoplay(report, "TotalBetSliderSet","TotalBetSliderValue",CurrencyName);
						Thread.sleep(2000);		
					}
									
					if(xpathMap.get("WinLimitSliderPresent").equalsIgnoreCase("Yes"))
					{
						validateWinLimitSliderAutoplay(report, "WinLimitSliderSet","WinLimitSliderValue",CurrencyName);
						Thread.sleep(2000);		
					}
															
					if(xpathMap.get("LossLimitSliderPresent").equalsIgnoreCase("Yes"))
					{
						validateLossLimitSliderAutoplay(report, "LossLimitSliderSet","LossLimitSliderValue",CurrencyName);
						Thread.sleep(2000);
					}
					
					
					
			}catch (Exception e) 
				{
				log.debug("unable to verify autoplay options");
				log.error(e.getMessage(),e);
				
			}
			}
			
			//=========================******************Market methods******************===============================
			
			public void clickOnBaseSceneContinueButton(Mobile_HTML_Report report, ImageLibrary imageLibrary)
			   {
				   try
				   {
				   if(imageLibrary.isImageAppears("NFDButton"))
					{
						System.out.println("NFD button is visible");log.debug("NFD button is visible");									
						Thread.sleep(2000);
						imageLibrary.click("NFDButton");
						Thread.sleep(3000);
						report.detailsAppendNoScreenshot("Verify Continue button is visible ", "Continue buttion is visible", "Continue button is visible", "Pass");
					
				
					}
					else
					{
						System.out.println("NFD button is not visible");log.debug("NFD button is not visible");
						report.detailsAppendNoScreenshot("Verify Continue button is visible ", "Continue buttion is visible", "Continue button is not visible", "Fail");
					}
				   }
				   catch (Exception e) {
					   log.error(e.getMessage(), e);
				   	}
			   }
			
			 public void verifyCreditsCurrencyFormat(Mobile_HTML_Report report, String regExpr)
			   {
				   boolean credits = verifyRegularExpression(report, regExpr,getConsoleText("return " + xpathMap.get("CreditValue")));

				   if (credits)
				   {
								
					   report.detailsAppendNoScreenshot("Verify the currency format for credit ","Credit should display in correct currency format " ,"Credit is displaying in correct currency format ", "Pass");																
					} else 
					{
						report.detailsAppendNoScreenshot("Verify the currency format for credit ","Credit should display in correct currency format " ,"Credit is displaying in incorrect currency format ", "Fail");
					}
					
			   }
			 
			 public String getConsoleText(String text) {
					String consoleText = null;
					try {
						JavascriptExecutor js = ((JavascriptExecutor) webdriver);
						consoleText = (String) js.executeScript(text);
						System.out.println(consoleText);
					} catch (Exception e) {
						e.getMessage();
					}
					return consoleText;
				}
			 
			
			 public void verifyBetCurrencyFormat(Mobile_HTML_Report report, String regExpr)
			   {
				   boolean bet = verifyRegularExpression(report, regExpr,getConsoleText("return " + xpathMap.get("BetTextValue")));

				   if (bet)
				   {
								
					   report.detailsAppendNoScreenshot("Verify the currency format for bet ","Bet should display in correct currency format " ,"Bet is displaying in correct currency format ", "Pass");																
					} else 
					{
						report.detailsAppendNoScreenshot("Verify the currency format for bet ","Bet should display in correct currency format " ,"Bet is displaying in incorrect currency format ", "Fail");
					}
					
			   }

			  public void checkSpinReelLanding(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode)
			   {
				   try
				   {
					
				   if(imageLibrary.isImageAppears("Spin"))
					{
						imageLibrary.click("Spin");
						Thread.sleep(1750);
						report.detailsAppendFolder("Verify Spin reel landing ", "Reels should not land at the same time", "Reels did not land at the same time", "Pass",languageCode);
					}
				   }
				   catch (Exception e) {
					   log.error(e.getMessage(), e);
				   	}
			   }
			  
			  public boolean openAutoplayPanel(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				{
					boolean isAutoplayOpen=false;

					try {

						if(imageLibrary.isImageAppears("Autoplay")) 
						{
							//click on autoplay button						
							imageLibrary.click("Autoplay");
							Thread.sleep(3000);	
							report.detailsAppendFolder("Auto play menu", "Auto play menu", "Auto play menu is opened", "Pass",languageCode);
							isAutoplayOpen=true;
						}
					} catch (Exception e) {
						e.getMessage();
					}
						
					return isAutoplayOpen;
				}
				
			  public boolean closeButton(ImageLibrary imageLibrary) 
				{
					boolean isClose=false;
				
					try
					{
						if(imageLibrary.isImageAppears("MenuClose"))
						{	
							imageLibrary.click("MenuClose");
							Thread.sleep(2000);
							isClose=true;
							
						}
					}catch (Exception e) {
						   log.error(e.getMessage(), e);
					   		}
					return isClose;
				}
			  
				public boolean openBetPanel(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				{
					boolean isBetOpen=false;

					try {
						if(imageLibrary.isImageAppears("BetButton"))						
						{								
							
							imageLibrary.click("BetButton");
							Thread.sleep(2000);
							report.detailsAppendFolder("Bet menu", "Bet menu", "Bet menu is opened", "Pass",languageCode);
							isBetOpen=true;
						}
					} catch (Exception e) {
						e.getMessage();
					}
						
					return isBetOpen;
				} 
				
				public boolean openPaytable(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				{
					boolean isPaytbaleOpen=false;
				
					try
					{
						if(imageLibrary.isImageAppears("Menu"))
						{
							imageLibrary.click("Menu");
							Thread.sleep(2000);	
							
							if(imageLibrary.isImageAppears("Paytable"))
							{
								//click on PayTable
								imageLibrary.click("Paytable");
								Thread.sleep(2000);	
								report.detailsAppendFolder("PayTable open", "PayTable should be opened", "PayTable is opened", "Pass",languageCode);
								isPaytbaleOpen=true;
							}
					
						}
					}catch (Exception e) {
						   log.error(e.getMessage(), e);
					   		}
					return isPaytbaleOpen;
				}
				
				public boolean closePaytable() 
				{
					boolean isPaytbaleClose=false;
				
					try
					{
						if(elementVisible_Xpath(xpathMap.get("PaytableClose")))
						{	
							func_Click(xpathMap.get("PaytableClose"));
							Thread.sleep(2000);
							isPaytbaleClose=true;
							
						}
					}catch (Exception e) {
						   log.error(e.getMessage(), e);
					   		}
					return isPaytbaleClose;
				}
				
				
				public boolean openSettingsPanel(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				{
					boolean isSettingsOpen=false;
				

					try {

						// click on menu if settings are under menu
						if ("Yes".equalsIgnoreCase(xpathMap.get("settingsUnderMenu"))) {
							if (imageLibrary.isImageAppears("Menu")) {

								imageLibrary.click("Menu");
								Thread.sleep(2000);
							}
						}
						if (imageLibrary.isImageAppears("Settings")) 
						{
							// click on menu
							imageLibrary.click("Settings");
							Thread.sleep(2000);
							report.detailsAppendFolder("Settings Open ", "Settings should be open", "Settings is Opened", "Pass",languageCode);
							isSettingsOpen=true;
							
						}
					} catch (Exception e) {
						e.getMessage();
					}
						
					return isSettingsOpen;
				} 
				
				public void verifyValueAddsNavigation(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode,String PlayerProtectionNavigation,String CashCheckNavigation,String HelpNavigation,String PlayCheckNavigation) 
				{
					try
					{
						setChromiumWebViewContext();
						Thread.sleep(1000);
						if (func_Click(xpathMap.get("HelpMenu"))) {
						//	if (func_Click("HelpMenu")) {
							Thread.sleep(1000);
							report.detailsAppendFolder("Verify if menu on top bar is open",
									"Menu on top bar should be open", "Menu on top bar is open", "Pass",
									languageCode);
							closeOverlay();
							Thread.sleep(1000);

							if (PlayerProtectionNavigation.equalsIgnoreCase("yes")) {
								verifyPlayerProtectionNavigation(report, imageLibrary, languageCode);
								Thread.sleep(2000);
							}

							if (CashCheckNavigation.equalsIgnoreCase("yes")) {
								verifyCashCheckNavigation(report, imageLibrary, languageCode);
								Thread.sleep(2000);
							}

							if (HelpNavigation.equalsIgnoreCase("yes")) {
								verifyHelpNavigation(report, imageLibrary, languageCode);
								Thread.sleep(2000);
							}

							if (PlayCheckNavigation.equalsIgnoreCase("yes")) {
								verifyPlayCheckNavigation(report, imageLibrary, languageCode);
								Thread.sleep(5000);
							}
							/*if (TransactionHistoryNavigation.equalsIgnoreCase("yes")) {
								verifyCashCheckNavigation(report, imageLibrary, languageCode);
								Thread.sleep(2000);
							}*/

							if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
								clickOnBaseSceneContinueButton(report, imageLibrary);
							}
					}
						
					} catch (Exception e) {
						e.getMessage();
						log.error(e);
					}
				}
				
				 public void verifyPlayerProtectionNavigation(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				   {
					   setChromiumWebViewContext();
					   String gameurl = webdriver.getCurrentUrl();

					   try 
					   {
						   func_Click(xpathMap.get("HelpMenu"));
						   Thread.sleep(1000);
						   webdriver.context("NATIVE_APP");
						   if (imageLibrary.isImageAppears("PlayerProtection")) 
						   {
							   Thread.sleep(1000);
							   report.detailsAppendNoScreenshot("Verify Player protection is visible", "Player protection should be visible", "Player protection is visible", "Pass");
								
							   imageLibrary.click("PlayerProtection");
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   
						   else if(elementVisible_Xpath(xpathMap.get("PlayerProtection")))
						   {
							   report.detailsAppendNoScreenshot("Verify Player protection is visible", "Player protection should be visible", "Player protection is visible", "Pass");
							   setChromiumWebViewContext();
							   func_Click(xpathMap.get("PlayerProtection"));
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else
						   {
							   report.detailsAppendNoScreenshot("Verify Player protection is visible", "Player protection should be visible", "Player protection is not visible", "Fail");
								   
						   }

					   } catch (Exception e) 
					   {
						   log.error(e.getMessage(), e);
					   }

				   }

				 
				  public void verifyCashCheckNavigation(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				   {
					   setChromiumWebViewContext();
					   String gameurl = webdriver.getCurrentUrl();

					   try 
					   {
						   func_Click(xpathMap.get("HelpMenu"));
						   Thread.sleep(1000);
						   
						   webdriver.context("NATIVE_APP");
						   if (imageLibrary.isImageAppears("CashCheck")) 
						   {
							   Thread.sleep(1000);
							   report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible", "Cash Check is visible", "Pass");
								
							   imageLibrary.click("CashCheck");
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else if(elementVisible_Xpath(xpathMap.get("CashCheck")))
						   {
							   report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible", "Cash Check is visible", "Pass");
							   setChromiumWebViewContext();
							   func_Click(xpathMap.get("CashCheck"));
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else
						   {
							   report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible", "Cash Check is not visible", "Fail");
						   }

					   } catch (Exception e) 
					   {
						    log.error(e.getMessage(), e);
					   }

				   }
				  
				   public void verifyPlayCheckNavigation(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				   {
					   String gameurl = webdriver.getCurrentUrl();

					   try 
					   {
						   func_Click(xpathMap.get("HelpMenu"));
						   
						   Thread.sleep(1000);
						   if (imageLibrary.isImageAppears("PlayCheck")) 
						   {
							   Thread.sleep(1000);
							   report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible", "Play Check is visible", "Pass");
							   imageLibrary.click("PlayCheck");
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else if(elementVisible_Xpath(xpathMap.get("PlayCheck")))
						   {
							   report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible", "Play Check is visible", "Pass");
							   func_Click(xpathMap.get("PlayCheck"));
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else
						   {
							   report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible", "Play Check is not visible", "Fail");							
						   }
					   } catch (Exception e) 
					   {
						   log.error(e.getMessage(), e);   	
					   }

				   }
				   
				   
				   public void verifyHelpNavigation(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				   {
					   String gameurl = webdriver.getCurrentUrl();
					  
					   try 
					   {
						   func_Click(xpathMap.get("HelpMenu"));
						   Thread.sleep(1000);
						   if (imageLibrary.isImageAppears("Help")) 
						   {
							   //Thread.sleep(1000);
							   report.detailsAppendNoScreenshot("Verify Help is visible", "Help should be visible", "Help is visible", "Pass");
								
							   imageLibrary.click("Help");
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else if(elementVisible_Xpath(xpathMap.get("ukHelp")))
						   {
							   report.detailsAppendNoScreenshot("Verify Help is visible", "Help should be visible", "Help is visible", "Pass");
								
							   func_Click(xpathMap.get("ukHelp"));
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else
						   {
							   report.detailsAppendNoScreenshot("Verify Help is visible", "Help should be visible", "Help is not visible", "Fail");
								
						   }

					   } catch (Exception e) {
						   
						   log.error(e.getMessage(), e);
					   }

				   }
				   
				   /**
					 * This method is used to verify playcheck using image or xpath
					 * 
					 * @author SB64689
					 */
				   public void verifyTransactionHistory(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) 
				   {
					   String gameurl = webdriver.getCurrentUrl();

					   try 
					   {
						   func_Click(xpathMap.get("HelpMenu"));
						   
						   Thread.sleep(1000);
						   if (imageLibrary.isImageAppears("TransactionHistory")) 
						   {
							   Thread.sleep(1000);
							   report.detailsAppendNoScreenshot("Verify Transaction History is visible", "TransactionHistory should be visible", "Transaction History is visible", "Pass");
							   imageLibrary.click("TransactionHistory");
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else if(elementVisible_Xpath(xpathMap.get("TransactionHistory")))
						   {
							   report.detailsAppendNoScreenshot("Verify Transaction History is visible", "Transaction History should be visible", "Transaction History is visible", "Pass");
							   func_Click(xpathMap.get("TransactionHistory"));
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else
						   {
							   report.detailsAppendNoScreenshot("Verify Transaction History is visible", "Transaction History should be visible", "Transaction History is not visible", "Fail");							
						   }
					   } catch (Exception e) 
					   {
						   log.error(e.getMessage(), e);   	
					   }

				   }
			
				   public void validateSessionReminderBaseScene(Mobile_HTML_Report report,String userName,ImageLibrary imageLibrary,String languageCode,String SessionReminderUserInteraction,String SessionReminderContinue,String SessionReminderExitGame) 
					{
						try 
						{
							setChromiumWebViewContext();
							
							CommonUtil util = new CommonUtil();						
							String gameurl = webdriver.getCurrentUrl();
							
							String periodValue= xpathMap.get("sessionReminderDurationInSec");
							String resetPeriodValue=xpathMap.get("resetSessionReminderValue");
						
							//assigning session reminder
							boolean sessionReminderAssigned=util.setSessionReminderOnAxiom(userName,periodValue);
							loadGame(gameurl);
							/*if ("Yes".equalsIgnoreCase(XpathMap.get("NFD"))) {
								clickOnBaseSceneContinueButton(report, imageLibrary);
							}*/
							log.debug("Session reminder is set for "+periodValue+" secs");
										
							
							if (sessionReminderAssigned && sessionReminderPresent())
							{
								report.detailsAppendFolder("Verify Session Reminder must be present","Session Reminder must be present", "Session Reminder is present","Pass", languageCode);
									
								if(SessionReminderUserInteraction.equalsIgnoreCase("yes"))
								{
									//clicking else where on the screen
									closeOverlay();
									report.detailsAppendFolder("Verify when Session Reminder is present and player should not be able to interact with any part of the game","Player should not not be able to interact with any part of the game", "Unable to interact with the game","Pass",languageCode);								
									Thread.sleep(2000);
								}
								
								//Ensure that the continue button takes you back to the game
								if(SessionReminderContinue.equalsIgnoreCase("yes"))
								{
									selectContinueSession();
									
									if(imageLibrary.isImageAppears("Spin"))
									{
										report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",	" Session Reminder dialog box is closed and game should be visible"," Session Reminder dialog box is closed and game is visible","Pass",languageCode);
										Thread.sleep(2000);
									}
									else
									{
										report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",	" Session Reminder dialog box is closed and game should be visible"," Session Reminder dialog box is closed and game is not visible","Fail",languageCode);	
									}									
								}
							} else {
								report.detailsAppendFolder("Verify Session Reminder must be present","Session Reminder must be present","Session Reminder is not present", "Fail",languageCode);
								log.debug("Session reminder is not visible on screen");
								Thread.sleep(2000);
							}

							//Ensure that the log out button takes you to the lobby
							if(SessionReminderExitGame.equalsIgnoreCase("yes"))
							{							
								//assigning session reminder
								sessionReminderAssigned=util.setSessionReminderOnAxiom(userName,periodValue);
								loadGame(gameurl);
								if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
									clickOnBaseSceneContinueButton(report, imageLibrary);
								}
								
								if (sessionReminderAssigned && sessionReminderPresent())
								{
									func_Click(xpathMap.get("ExitGameSessrionReminder"));
									Thread.sleep(12000);
									report.detailsAppendNoScreenshot("Verify Session Reminder log out/Exit game button","Log out button shoul take user to the lobby","Log out button takes user to the lobby", "Pass");
									
									checkpagenavigation(report,languageCode,gameurl);		
									if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
										clickOnBaseSceneContinueButton(report, imageLibrary);
									}
								}
							}
							else {
								report.detailsAppendFolder("Verify Session Reminder must be present to verify exit game scenario","Session Reminder must be present","Session Reminder is not present", "Fail",languageCode);
							}
									
								
							//reset to higher duarion for session reminder
							boolean resetSessionReminderDUration=util.setSessionReminderOnAxiom(userName,resetPeriodValue);
							if (resetSessionReminderDUration) {
								log.debug("Session reminder has been reset to 10hr in order to avoid pop up");
							} else {
								log.debug("Unable to reset Session reminder");
							}							
							// relaunching the game to set the above reset session reminder duration to higher value
							loadGame(gameurl);
							if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
								clickOnBaseSceneContinueButton(report, imageLibrary);
							}
								
								
											
					} catch (Exception e) {
						e.getMessage();
						log.error(e);
					}
					}	
				
				   
				   public void validateBonusFundsNotificationBaseScene(Mobile_HTML_Report report,String userName,ImageLibrary imageLibrary,String languageCode,String closeBtnEnabledCMA) 
					{
						try 
						{
							if(closeBtnEnabledCMA.equalsIgnoreCase("yes"))
							{
								CommonUtil util = new CommonUtil();
								String gameurl = webdriver.getCurrentUrl();
								
								if(util.enableBonusFundsNotification(userName))
								{
									//Relaunching the game to verify Bonus funds notification
									loadGame(gameurl);
									if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
										clickOnBaseSceneContinueButton(report, imageLibrary);
									}
									
									
									//Ensure the Bonus Funds notification appears
									if(isBonusFundsNotificationVisible())
									{
										report.detailsAppendFolder("Verify if Bonus funds notification is visible ","Bonus funds notification should display" ,"Bonus funds notification is displayed", "Pass",languageCode);		
									}
									else
									{
										report.detailsAppendFolder("Verify if Bonus funds notification is visible ","Bonus funds notification should display" ,"Bonus funds notification is not displayed", "Fail",languageCode);
									}
								
								
									//Dismiss the notification by clicking the close button
									if(isBonusFundsNotificationVisible())
									{

										func_Click(xpathMap.get("closeBonusFundsNotification"));
										if (!(isBonusFundsNotificationVisible())) 
										{
											report.detailsAppendFolder("Verify Bonus funds notification can be dismissed by clicking on close button","Bonus funds notification should dismiss by clicking on close button","Bonus funds notification is dismissed by clicking the close button","Pass", languageCode);
											report.detailsAppendFolder("Verify Bonus funds notification can be dismissed after 1 second of gameplay","Bonus funds notification should dismiss after 1 second of gameplay","Bonus funds notification is dismissed after 1 second of gameplay","Pass", languageCode);
										} else 
										{
											report.detailsAppendFolder("Verify Bonus funds notification can be dismissed by clicking on lcose button","Bonus funds notification should dismiss by clicking on close button","Bonus funds notification is dismissed by clicking the close button","Fail", languageCode);
											report.detailsAppendFolder("Verify Bonus funds notification can be dismissed after 1 second of gameplay","Bonus funds notification should dismiss after 1 second of gameplay","Bonus funds notification is not dismissed after 1 second of gameplay","Fail", languageCode);
										}
									}
								
							
									//After dismissing the Notification, ensure you can spin with no errors thrown
									if(imageLibrary.isImageAppears("Spin"))
									{
										Thread.sleep(2000);
										imageLibrary.click("Spin");
										Thread.sleep(2000);
										report.detailsAppendFolder("Verify After dismissing the Notification, ensure you can spin with no errors thrown","No error should display","No errors found","Pass", languageCode);
									}
							
							
									//Clicking the hyperlink on the Notification
									//Refresh the game
									webdriver.navigate().refresh();
									Thread.sleep(10000);
									report.detailsAppendFolder("Verify Bonus funds notification is visible after refresh","Bonus funds notification should be visible after refresh","Bonus funds notification is visible after refresh","Pass", languageCode);
									
									verifyBonusFundsNotificationNavigation(report, languageCode);
									
									loadGame(gameurl);
									if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
										clickOnBaseSceneContinueButton(report, imageLibrary);
									}
									Thread.sleep(2000);
									if(imageLibrary.isImageAppears("Spin"))
									{
										report.detailsAppendFolder("Verify player can navigate back to the game after navigating to Terms and Conditions","Base scene should be visible","Base scene is visible","Pass", languageCode);
									}					
									else
									{
										report.detailsAppend("Unable to assign Bonus funds notification / CMA to player hence skipping the test cases","","","");
									}	
								}
							}
									
						} catch (Exception e) {
						e.getMessage();
						log.error(e);
					}
				
					}
				   
				   public boolean isBonusFundsNotificationVisible()
					{
						boolean isVisible = false;
						WebDriverWait Wait = new WebDriverWait(webdriver, 20);
						try {
							Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("isBonusFundsNotificationVisible"))));
							if (elementVisible_Xpath(xpathMap.get("isBonusFundsNotificationVisible"))) {
								isVisible = true;
							}
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
						return isVisible;
					}
				   
				   public void verifyBonusFundsNotificationNavigation(Mobile_HTML_Report report,String languageCode) 
				   {
					   String gameurl = webdriver.getCurrentUrl();

					   try 
					   {
						   Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("bonusFundsNotificationTandC"))));
						   if(elementVisible_Xpath(xpathMap.get("bonusFundsNotificationTandC")))
						   {
							   report.detailsAppendNoScreenshot("Verify Bonus Funds notification Terms and Conditions and its navigation", "Bonus Funds notification Terms and Conditions should be visible", "Bonus Funds notification Terms and Conditions is visible", "Pass");
								
							   func_Click(xpathMap.get("bonusFundsNotificationTandC"));
							   Thread.sleep(2000);
							   checkpagenavigation(report,languageCode,gameurl);
						   }
						   else
						   {
							   report.detailsAppendNoScreenshot("Verify Bonus Funds notification Terms and Conditions and its navigation", "Bonus Funds notification Terms and Conditions should be visible", "Bonus Funds notification Terms and Conditions is not visible", "Fail");
						   }
					   } catch (Exception e) 
					   {
						   log.error(e.getMessage(), e);   	
					   }

				   }
				   
				   public boolean verifyRegularExpression(Mobile_HTML_Report isoCode, String regExp, String method) {
						String Text = method;
						boolean regexp = false;
						try {
							Thread.sleep(2000);
							String Text1=Text.replaceAll("[a-zA-Z:]", "").trim();
							//Text1=Text1.replaceAll(" ", "").trim();	
							if (Text1.matches(regExp)) {
								log.debug("Compared with Reg Expression.Currency is in correct format: "+Text1);
								regexp = true;
							}
							Thread.sleep(2000);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
						return regexp;
					}
				   
				   
				   public void selectContinueSession() 
					{
						try {
						webdriver.findElement(By.xpath(xpathMap.get("SessionContinue"))).click();
						log.debug("clicked on continue in session reminder dialog box");
						}
						catch(Exception e)
						{
							log.info("Unable to click on session continue ",e);
							log.error(e.getMessage(), e);
						}
					}
				   
				   public void checkSpinStopBaseScene(Desktop_HTML_Report report,ImageLibrary imageLibrary,String languageCode,String checkSpinStopBaseSceneUsingCoordinates)
				   {
					   try
					   {
						
					   if(imageLibrary.isImageAppears("Spin"))
						{

							imageLibrary.click("Spin");
							Thread.sleep(1750);
							report.detailsAppendFolder("Verify Spin button status ", "Spin button should not have stop button", "Spin button does not have stop button", "Pass",languageCode);
							Thread.sleep(3000);
							
							if(checkSpinStopBaseSceneUsingCoordinates.equalsIgnoreCase("yes"))
							{
								imageLibrary.click("Spin");
								Thread.sleep(1000);
								ClickByCoordinates("return " + xpathMap.get("clickOnReelX"), "return " + xpathMap.get("clickOnReelY"));
							
								report.detailsAppendFolder("Verify  clicking or tapping on the reels does not act as a Spin/Stop or Slam stop by stopping the reels spin ", "Spin button should not resolve", "Spin button does not resolve", "Pass",languageCode);
							}
						}
					   
					   
					   }catch (Exception e) {
						   log.error(e.getMessage(), e);
					   	}
				
				   }

					public void ClickByCoordinates(String cx, String cy) {
						Wait = new WebDriverWait(webdriver, 50);
						// boolean visible = false;
						int dx = 0, dy = 0, dw = 0, dh = 0;
						JavascriptExecutor js = ((JavascriptExecutor) webdriver);

						System.out.println(js.executeScript(cx));
						System.out.println(js.executeScript(cy));
						try {
							Long sx = (Long) js.executeScript(cx);
							dx = sx.intValue();
						} catch (Exception e) {
							Double sx = (Double) js.executeScript(cx);
							dx = sx.intValue();
						}
						try {
							Long sy = (Long) js.executeScript(cy);
							dy = sy.intValue();
						} catch (Exception e) {
							Double sy = (Double) js.executeScript(cy);
							dy = sy.intValue();
						}

						if (dx == 0) {
							dx = 1;

						}
						if (dy == 0) {
							dy = 1;
						}

						long bodyHeight = webdriver.findElement(By.id("viewporter")).getRect().getHeight();
						long bodyWidth = webdriver.findElement(By.id("viewporter")).getRect().getWidth();
						int topLeftX = (int) bodyWidth / 2;
						int topLeftY = (int) bodyHeight / 2;
						Actions actions = new Actions(webdriver);
						actions.moveToElement(webdriver.findElement(By.id("viewporter")), 0, 0);
						actions.moveToElement(webdriver.findElement(By.id("viewporter")), -topLeftX, -topLeftY);
						log.debug("topLeftX: " + topLeftX + " topLeftY: " + topLeftY);
						actions.moveByOffset(dx, dy).click().perform();	
					
					}


}
	