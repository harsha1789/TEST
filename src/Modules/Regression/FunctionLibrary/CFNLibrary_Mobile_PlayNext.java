package Modules.Regression.FunctionLibrary;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import com.zensar.automation.framework.report.Mobile_HTML_Report;
import com.zensar.automation.framework.report.Mobile_HTML_Report;
//import com.sun.glass.events.KeyEvent;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.Util;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
//import com.zensar.automation.library.ImageLibrary;
import com.zensar.automation.library.TestPropReader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import net.lightbody.bmp.BrowserMobProxyServer;

public class CFNLibrary_Mobile_PlayNext extends CFNLibrary_Mobile {
	long Avgquickspinduration = 0;
	long Avgnormalspinduration = 0;
	private double userwidth;
	private double userheight;
	private double userElementX;
	private int userElementY;
	private int Ty;
	private int Tx;
	public List<MobileElement> el;
	String balance = null;
	String loyaltyBalance = null;
	String totalWin = null;
	int totalWinNew = 0;
	int initialbalance1 = 0;
	String numline = null;
	int totalnumline = 0;
	int previousBalance = 0;
	public WebElement futurePrevent;
	WebElement time = null;
	WebElement slotgametitle;
	int newBalance = 0;
	int freegameremaining = 0;
	int freegamecompleted = 0;
	Properties OR = new Properties();
	String GameDesktopName;
	public AppiumDriver<WebElement> webdriver;
	public BrowserMobProxyServer proxy;
	public Mobile_HTML_Report repo1;
	public WebElement TimeLimit;
	public String GameName;
	public String wait;
	Util clickAt = new Util();

	public CFNLibrary_Mobile_PlayNext(AppiumDriver<WebElement> webdriver, BrowserMobProxyServer proxy,
			Mobile_HTML_Report tc06, String gameName) throws IOException {
		super(webdriver, proxy, tc06, gameName);

		this.webdriver = webdriver;
		this.proxy = proxy;
		repo1 = tc06;
		// webdriver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		// Wait = new WebDriverWait(webdriver, 60);
		this.GameName = gameName;

	}

	public void func_SwipeUp() {

		Wait = new WebDriverWait(webdriver, 50);
		boolean isOverlayRemove = false;
		try {
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("clock_ID"))));
			if (osPlatform.equalsIgnoreCase("Android")) {
				String context = webdriver.getContext();
				webdriver.context("NATIVE_APP");
				Dimension size1 = webdriver.manage().window().getSize();
				int startx = size1.width / 2;
				int starty = size1.height / 2;
				TouchAction action = new TouchAction(webdriver);
				action.press(PointOption.point(startx, starty)).release().perform();
				webdriver.context(context);
				log.debug("tapped on full screen overlay");
				Thread.sleep(500);
			} else {// For IOS to perform scroll
				Thread.sleep(1000);
				Dimension size = webdriver.manage().window().getSize();
				int anchor = (int) (size.width * 0.5);
				int startPoint = (int) (size.height * 0.9);
				int endPoint = (int) (size.height * 0.4);
				new TouchAction(webdriver).press(point(anchor, startPoint)).waitAction(waitOptions(ofMillis(1000)))
						.moveTo(point(anchor, endPoint)).release().perform();

				// Added another swipe to avoid swipe issue on few latest OS(1.14)
				/*
				 * Thread.sleep(1000); startPoint = (int) (size.height * 0.75); endPoint = (int)
				 * (size.height * 0.5); new TouchAction(webdriver).press(point(anchor,
				 * startPoint)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(anchor,
				 * endPoint)).release().perform(); isOverlayRemove=true;
				 */

			}
			log.debug("tapped on full screen overlay");

		} catch (Exception e) {
			log.error(e.getStackTrace());
		}

	}

	public void tapOnCoordinates(final double x, final double y) {
		try {
			TouchAction action = new TouchAction(webdriver);
			action.press(PointOption.point((int) x, (int) y)).release().perform();
			action.tap(PointOption.point((int) x, (int) y)).perform();
		} catch (Exception e) {
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

	public void closeOverlay(int x, int y) {
		try {
			if (osPlatform.equalsIgnoreCase("iOS")) {
				if (webdriver.getOrientation().equals(ScreenOrientation.PORTRAIT)) {
					TouchAction touchAction = new TouchAction(webdriver);
					touchAction.tap(PointOption.point(x, y)).perform();

				} else {
					TouchAction touchAction = new TouchAction(webdriver);
					touchAction.tap(PointOption.point(50, 80)).perform();
				}
			} else {// For Andriod mobile

				Actions act = new Actions(webdriver);
				act.moveByOffset(x, y).click().build().perform();
				act.moveByOffset(-x, -y).build().perform();
				// act.moveByOffset(20, 80).click().build().perform();
				// act.moveByOffset(-20, -80).build().perform();
			}
		} catch (Exception e) {
			log.error("error while closing overlay ", e);
			log.error(e.getMessage());
		}
	}

	public boolean waitForElement(String hook) {
		boolean result = false;
		long startTime = System.currentTimeMillis();
		try {
			log.debug("Waiting for Element before click");
			while (true) {
				Boolean ele = getConsoleBooleanText("return " + xpathMap.get(hook));
				if (ele) {
					log.info(hook + " value= " + ele);
					result = true;
					System.out.println(hook + " value= " + ele);
					break;
				} else {
					long currentime = System.currentTimeMillis();
					if (((currentime - startTime) / 1000) > 180) {
						log.info("No value present after 180 seconds= " + ele);
						System.out.println("No value present after 180 seconds= " + ele);
						result = false;
						break;
					}
				}

			}
		} catch (Exception e) {
			log.error("error while waiting for element ", e);
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

	public boolean elementVisible_Xpath(String Xpath) {
		boolean visible = false;
		try {
			if (webdriver.findElement(By.xpath(Xpath)).isDisplayed()) {

				visible = true;
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return visible;
	}

	public void clickAtButton(String button) {
		try {
			JavascriptExecutor js = (webdriver);
			js.executeScript(button);

			/*
			 * JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			 * js.executeScript(button);
			 */
		} catch (JavascriptException e) {
			log.error("EXeception while executon ", e);
			log.error(e.getCause());
		}
	}

	public void clickOnButtonUsingCoordinates(String X, String Y) {
		try {
			String xCoordinate = xpathMap.get(X);
			String yCoordinate = xpathMap.get(Y);
			double x = Double.parseDouble(xCoordinate);
			double y = Double.parseDouble(yCoordinate);
			tapOnCoordinates(x, y);
		}

		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void validateNFDButton(Mobile_HTML_Report report, String CurrencyName) {
		try {
			// check for visibility of nfd button and take screenshot
			if (isElementVisible("isNFDButtonVisible")) {
				report.detailsAppendFolder("Verify Continue button is visible ", "Continue button should be visible",
						"Continue button is visible", "Pass", CurrencyName);
			} else {
				report.detailsAppendFolder("Verify Continue button is visible ", "Continue buttion should be visible",
						"Continue button is not visible", "Fail", CurrencyName);
			}
		} catch (Exception e) {
			log.debug("unable to verify continue button");
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to validate bet sliders
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void verifyBetSliders(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("BetMax")) {
				setChromiumWebViewContext();
				// Check Coin Size Slider
				if (xpathMap.get("CoinSizeSliderPresent").equalsIgnoreCase("Yes")) {
					validateCoinSizeSlider(report, "CoinSizeSliderSet", "BetMenuBetValue", currencyName);
					Thread.sleep(1000);
				}

				// Check Coins per line slider
				if (xpathMap.get("CoinsPerLineSliderPresent").equalsIgnoreCase("Yes")) {
					validateCoinsPerLineSlider(report, "CoinsPerLineSliderSet", "BetMenuBetValue", currencyName);
					Thread.sleep(1000);
				}

				// check lines slider
				if (xpathMap.get("LinesSliderPresent").equalsIgnoreCase("Yes")) {
					validateLinesSlider(report, "LinesSliderSet", "BetMenuBetValue", currencyName);
					Thread.sleep(1000);
				}
			}

		} catch (Exception e) {
			log.debug("unable to verify bet options");
			log.error(e.getMessage(), e);
		}
	}

	public void verifyAutoplayOptions(Mobile_HTML_Report report, String CurrencyName) {
		try {
			setChromiumWebViewContext();
			if (xpathMap.get("SpinSliderPresent").equalsIgnoreCase("Yes")) {
				validateSpinsSliderAutoplay(report, "SpinSliderSet", "SpinSliderValue", CurrencyName);
				Thread.sleep(2000);
			}

			if (xpathMap.get("TotalBetSliderPresent").equalsIgnoreCase("Yes")) {
				validateTotalBetSliderAutoplay(report, "TotalBetSliderSet", "TotalBetSliderValue", CurrencyName);
				Thread.sleep(2000);
			}

			if (xpathMap.get("WinLimitSliderPresent").equalsIgnoreCase("Yes")) {
				validateWinLimitSliderAutoplay(report, "WinLimitSliderSet", "WinLimitSliderValue", CurrencyName);
				Thread.sleep(2000);
			}

			if (xpathMap.get("LossLimitSliderPresent").equalsIgnoreCase("Yes")) {
				validateLossLimitSliderAutoplay(report, "LossLimitSliderSet", "LossLimitSliderValue", CurrencyName);
				Thread.sleep(2000);
			}

			/*
			 * startAutoPlay(); Thread.sleep(3000);
			 * 
			 * if(isElementVisible("isAuoplaySpinVisible")) {
			 * report.detailsAppendFolder("Verify  autoplay started",
			 * "Autoplay should start", "Autoplay is started", "Pass",CurrencyName);
			 * log.debug("Autoplay started");
			 * 
			 * stopAutoPlay(); } else {
			 * report.detailsAppendFolder("Verify  autoplay started",
			 * "Autoplay should start", "Autoplay is not started", "Fail",CurrencyName);
			 * log.debug("Autoplay not started or freespins triggered"); }
			 */

		} catch (Exception e) {
			log.debug("unable to verify autoplay options");
			log.error(e.getMessage(), e);

		}
	}

	/**
	 * This method is used to slide the coin size slider and validate bet amount to
	 * verify its working or not
	 * 
	 * @author pb61055
	 * @param report
	 */
	public void validateCoinSizeSlider(Mobile_HTML_Report report, String sliderPoint, String betValue,
			String CurrencyName) {
		try {
			boolean ableToSlide = verifySliderValue(sliderPoint, betValue);

			if (ableToSlide) {
				report.detailsAppendFolder("Verify if able to change coin size slider value",
						"Coin size slider value should change", "Coin size slider value is changed", "Pass",
						CurrencyName);
				log.debug("Coin size slider is working");
			} else {
				report.detailsAppendFolder("Verify if able to change coin size slider value",
						"Coin size slider value should change", "Unable to change coin cize clider value", "Fail",
						CurrencyName);
				log.debug("Coin size slider is not working");
			}

		} catch (Exception e) {
			log.debug("unable to verify coin size slider");
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to slide the coins per line slider and validate bet
	 * amount to verify its working or not
	 * 
	 * @author pb61055
	 * @param report
	 */
	public void validateCoinsPerLineSlider(Mobile_HTML_Report report, String sliderPoint, String betValue,
			String CurrencyName) {
		try {
			boolean ableToSlide = verifySliderValue(sliderPoint, betValue);
			if (ableToSlide) {
				report.detailsAppendFolder("Verify if able to change coins per line slider value",
						"Verify if able to change coins per line slider value",
						"Coins per line slider value is changed", "Pass", CurrencyName);
				log.debug("Coins Per Line slider is working");
			} else {
				report.detailsAppendFolder("Verify if able to change coins per line slider value",
						"Verify if able to change coins per line slider value",
						"Unable to change coins per line slider value", "Fail", CurrencyName);
				log.debug("Coins Per Line slider is not working");
			}

		} catch (Exception e) {
			log.debug("unable to verify coins per line slider");
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to slide the lines slider and validate bet amount to
	 * verify its working or not
	 * 
	 * @author pb61055
	 * @param report
	 */
	public void validateLinesSlider(Mobile_HTML_Report report, String sliderPoint, String betValue,
			String CurrencyName) {
		try {
			boolean ableToSlide = verifySliderValue(sliderPoint, betValue);

			if (ableToSlide) {
				report.detailsAppendFolder("Verify if able to change line slider value",
						"Line slider value should change", "Line slider value is changed", "Pass", CurrencyName);
				log.debug("Line slider is working");
			} else {
				report.detailsAppendFolder("Verify if able to change line slider value",
						"Line slider value should change", "Unable to change line slider value", "Fail", CurrencyName);
				log.debug("Line slider is not working");
			}

		} catch (Exception e) {
			log.debug("unable to verify line slider");
			log.error(e.getMessage(), e);
		}
	}

	public boolean verifySliderValue(String sliderPoint, String value) {
		boolean isSliderMoved = false;
		try {
			String valueBefore = GetConsoleText("return " + xpathMap.get(value));
			log.debug("Value before sliding: " + valueBefore);
			Thread.sleep(1000);

			clickAtButton(xpathMap.get(sliderPoint));

			Thread.sleep(1000);

			String valueAfter = GetConsoleText("return " + xpathMap.get(value));
			log.debug("Value after sliding: " + valueAfter);

			if (valueBefore.equalsIgnoreCase(valueAfter) != true) {
				isSliderMoved = true;
				log.debug("slider is moved");
			}
		} catch (Exception e) {
			log.debug("unable to move slider");
			log.error(e.getMessage(), e);
		}
		return isSliderMoved;
	}

	public boolean setMaxbetPlayNext() {
		boolean result = false;
		try {
			clickOnButtonUsingCoordinates("maxBetCoordinateX", "maxBetCoordinateY");
			Thread.sleep(2000);
			result = true;
		} catch (Exception e) {
			log.debug("unable to setMaxbet");
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public boolean clickOnQuickSpin() {
		boolean result = false;
		try {
			// clicking on quick spin button
			clickOnButtonUsingCoordinates("quickSpinCoordinateX", "quickSpinCoordinateY");
			log.debug("clicked on quick spin");
			result = true;

		} catch (Exception e) {
			log.debug("unable while clicking on quick spin button");
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public void closeUsingCoordinates() {

		try {
			// clicking on quick spin button
			clickOnButtonUsingCoordinates("closeButtonCoordinateX", "closeButtonCoordinateY");
			log.debug("clicked on close button");

		} catch (Exception e) {
			log.debug("unable while clicking on close button");
			log.error(e.getMessage(), e);
		}

	}

	public void verifyMenuOptions(Mobile_HTML_Report report, String CurrencyName) {
		try {
			report.detailsAppend("Follwing are the paytable verification test cases", "Verify paytable ", "", "");

			verifyPaytableScroll(report, CurrencyName);
			Thread.sleep(1000);

			paytableClose();
			Thread.sleep(1000);

			openMenuPanel();

			report.detailsAppend("Follwing are the settings verification test cases", "Verify paytable ", "", "");
			verifySettingsOptions(report, CurrencyName);
			Thread.sleep(2000);

		} catch (Exception e) {
			log.debug("unable to click spin button");
			log.error(e.getMessage(), e);
		}
	}

	public void paytableOpen() {
		try {
			clickOnButtonUsingCoordinates("paytableCoordinateX", "paytableCoordinateY");
			Thread.sleep(2000);
		} catch (Exception e) {
			log.debug("unable to close paytable");
			log.error(e.getMessage(), e);
		}

	}

	public void paytableClose() {
		try {
			if (xpathMap.get("closePaytableUsingCoordinates").equalsIgnoreCase("Yes")) {
				clickOnButtonUsingCoordinates("paytableCloseCoordinateX", "paytableCloseCoordinateY");
			} else if (xpathMap.get("closePaytableUsingXpath").equalsIgnoreCase("Yes")) {
				funcClick("PaytableClose");
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			log.debug("unable to close paytable");
			log.error(e.getMessage(), e);
		}
	}

	public boolean verifyPaytableScroll(Mobile_HTML_Report report, String CurrencyName) {
		boolean result = false;
		try {
			paytableScroll(report, CurrencyName);
			if (isElementVisible("isPaytableBtnVisible")) {
				// report.detailsAppend("Verify PayTable Button ", "PayTable should visible",
				// "PayTable is visible", "Pass");

				// click on PayTable
				paytableOpen();

				if (elementVisible_Xpath(xpathMap.get("PaytableClose"))) {
					report.detailsAppendFolder("Verify if paytable is visible ", "PayTable should be visible",
							"PayTable is visible", "Pass", CurrencyName);
					// Thread.sleep(2000);
					boolean scrollPaytable = paytableScroll(report, CurrencyName);
					if (scrollPaytable) {
						report.detailsAppendFolderOnlyScreeshot(CurrencyName);
						result = true;

					} else {
						report.detailsAppendFolderOnlyScreeshot(CurrencyName);
					}

				} else {
					report.detailsAppendFolder("Verify if paytable is visible ", "PayTable should be visible",
							"PayTable is not visible", "Fail", CurrencyName);
				}
			} else {
				report.detailsAppendFolder("Verify PayTable Button", "PayTable button should be visible",
						"PayTable button is not visible", "Fail", CurrencyName);
			}
		} catch (Exception e) {
			log.debug("unable to verify paytable");
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public boolean paytableScroll(Mobile_HTML_Report report, String CurrencyName) {
		boolean paytableScroll = false;
		try {
			if (xpathMap.get("paytableScrollOfFive").equalsIgnoreCase("yes")) {
				paytableScroll = paytableScrollOfFive(report, CurrencyName);
				return paytableScroll;
			} else if (xpathMap.get("paytableScrollOfNine").equalsIgnoreCase("yes")) {
				paytableScroll = paytableScrollOfNine(report, CurrencyName);
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

	public boolean paytableScrollOfFive(Mobile_HTML_Report report, String CurrencyName) {
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
	public boolean paytableScrollOfNine1(Mobile_HTML_Report report, String CurrencyName) {
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
			if (xpathMap.get("wildFirst").equalsIgnoreCase("Yes")) {
				test = webdriver.findElements(By.xpath(xpathMap.get(wildXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(wildXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass",
							"" + CurrencyName);
					Thread.sleep(1000);
					test = true;
				}
				test = webdriver.findElements(By.xpath(xpathMap.get(scatterXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(scatterXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass",
							"" + CurrencyName);
					Thread.sleep(1000);
					test = true;
				}
			} else {
				test = webdriver.findElements(By.xpath(xpathMap.get(scatterXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(scatterXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass",
							"" + CurrencyName);
					Thread.sleep(1000);
					test = true;
				}
				test = webdriver.findElements(By.xpath(xpathMap.get(wildXpath))).size() > 0;
				if (test) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(wildXpath)));
					js.executeScript("arguments[0].scrollIntoView(true);", ele1);
					report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass",
							"" + CurrencyName);
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
		String scroll1 = "scroll1";
		String scroll2 = "scroll2";
		String scroll3 = "scroll3";
		String scroll4 = "scroll4";
		String scroll5 = "scroll5";
		String scroll6 = "scroll6";
		String scroll7 = "scroll7";
		String scroll8 = "scroll8";
		String scroll9 = "scroll9";

		boolean test = false;
		try {

			test = scrollUsingElement(winUpto);
			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}

			test = scrollUsingElement(wildXpath);
			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}
			test = scrollUsingElement(freeSpine);

			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}
			test = scrollUsingElement(scatterXpath);

			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}
			test = scrollUsingElement(symbolGridXpath);
			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}
			test = scrollUsingElement(symbolGridXpath5);

			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}
			test = scrollUsingElement(paylines);
			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}

			test = scrollUsingElement(scroll1);

			if (test) {
				report.detailsAppendFolder("PayTable", "PayTable Scroll", "PayTable Scroll", "Pass", "" + CurrencyName);
				Thread.sleep(1000);
				test = true;
			}
			test = scrollUsingElement(scroll1);

			/*
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;}
			 * 
			 * test = scrollUsingElement(scroll3);
			 * 
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;} test = scrollUsingElement(scroll4);
			 * 
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;} test = scrollUsingElement(scroll5);
			 * 
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;} test = scrollUsingElement(scroll6);
			 * 
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;} test = scrollUsingElement(scroll7);
			 * 
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;} test = scrollUsingElement(scroll8);
			 * 
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;}
			 * 
			 * 
			 * test = scrollUsingElement(scroll9);
			 * 
			 * if (test) {report.detailsAppendFolder("PayTable", "PayTable Scroll",
			 * "PayTable Scroll", "Pass", "" + CurrencyName); Thread.sleep(1000); test =
			 * true;}
			 */

			Thread.sleep(3000);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return test;
	}

	public boolean scrollUsingElement(String element) {
		boolean result = true;
		try {
			result = webdriver.findElements(By.xpath(xpathMap.get(element))).size() > 0;
			if (result) {
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(xpathMap.get(element)));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(1000);
				result = true;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("unable to scroll");
		}

		return result;
	}

	/**
	 * Verifies the Paytable text validation
	 * 
	 */
	public String textValidationForPaytableBranding(Mobile_HTML_Report report, String CurrencyName) {

		String PaytableBranding = null;
		Wait = new WebDriverWait(webdriver, 6000);
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get("Payways"))));
			boolean txt = webdriver.findElement(By.xpath(xpathMap.get("Payways"))).isDisplayed();
			if (txt) {
				WebElement ele = webdriver.findElement(By.xpath(xpathMap.get("Payways")));
				PaytableBranding = ele.getText();
				System.out.println("actual  : " + PaytableBranding);
				if (PaytableBranding.equalsIgnoreCase(xpathMap.get("PaywaysTxt"))) {
					System.out.println("Powered By MicroGaming Text : Pass");
					log.debug("Powered By MicroGaming Text : Pass");
					report.detailsAppendFolder("Paytable Branding ", "Branding Text ", "" + PaytableBranding, "Pass",
							"" + CurrencyName);

				} else {
					System.out.println("Powered By MicroGaming Text : Fail");
					log.debug("Powered By MicroGaming Text : Fail");
					report.detailsAppendFolder("Paytable Branding ", "Branding Text ", "" + PaytableBranding, "Fail",
							"" + CurrencyName);

				}
			}

			else {
				System.out.println("Powered By MicroGaming Text : Fail");
				report.detailsAppendFolder("Paytable", "Branding is not available ", "" + PaytableBranding, "Fail",
						"" + CurrencyName);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return PaytableBranding;

	}

	public boolean verifySettingsOptions(Mobile_HTML_Report report, String CurrencyName) {
		boolean settings = false;
		try {
			if (isElementVisible("isSettingsBtnVisible")) {
				// click on settings
				clickOnButtonUsingCoordinates("settingsCoordinateX", "settingsCoordinateY");
				log.debug("Clicked on settings button to open and verify");
				Thread.sleep(2000);

				if (isElementVisible("isMenuBackBtnVisible")) {
					report.detailsAppendFolder("Verify settings panel is open ", "Settings panel should be open",
							"Settings is open", "Pass", CurrencyName);

					// quick spin toggle
					if (xpathMap.get("isSettingsTurboInGame").equalsIgnoreCase("Yes")
							&& isElementVisible("isSettingsTurboVisible")) {
						// click on quick spin toggle
						clickOnButtonUsingCoordinates("quickSpinSettingsCoordinateX", "quickSpinSettingsCoordinateY");
						Thread.sleep(2000);
						report.detailsAppendFolder("Verify Quick spin toggle in settings ",
								"Quick spin toggle in settings should work", "Quick spin toggle in settings is working",
								"Pass", CurrencyName);
					} else {
						log.info("Verify Quick spin toggele button is not available in menu setting");
					}
					Thread.sleep(2000);

					// voice over
					if (xpathMap.get("settingsVoiceOverIngame").equalsIgnoreCase("Yes")
							&& isElementVisible("isVoiceOvers")) {
						// clicking on sound
						clickOnButtonUsingCoordinates("voiceOverCoordinateX", "voiceOverCoordinateY");
						Thread.sleep(2000);
						report.detailsAppendFolder("Verify voice over toggle in settings ",
								"Voice over toggle in settings should work", "Voice over toggle in settings is working",
								"Pass", CurrencyName);
					} else {
						log.info("sound  voice over toggele button is not available in menu setting");
					}
					Thread.sleep(2000);

					// sound
					if (xpathMap.get("isSettingsSoundInGame").equalsIgnoreCase("Yes")
							&& isElementVisible("isSoundsVisible")) {
						// clicking on sound
						clickOnButtonUsingCoordinates("settingsSoundCoordinateX", "settingsSoundCoordinateY");
						Thread.sleep(2000);
						report.detailsAppendFolder("Verify Sound toggle in settings ",
								"Sound toggle in settings should work", "Sound toggle in settings is working", "Pass",
								CurrencyName);
					} else {
						log.info("Sound toggele button is not available in menu setting");
					}
					Thread.sleep(2000);

					// clicking on close button
					closeUsingCoordinates();
					Thread.sleep(2000);
				}

				settings = true;
			} else {
				report.detailsAppendFolder("Verify settings panel is open ", "Settings panel should be open",
						"Settings is open", "Fail", CurrencyName);

				log.debug("Setting button is not available in game menu");
				;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.debug("unable to verify setting options");
		}
		return settings;
	}

	public void verifyHelpOnTopbar(Mobile_HTML_Report report, String CurrencyName) {
		try {
			String gameurl = webdriver.getCurrentUrl();

			// click on help menu
			funcClick(xpathMap.get("menuOnTopBar"));
			// clickOnButtonUsingCoordinates("menuOnTopbarCoordinateX","menuOnTopbarCoordinateY");
			Thread.sleep(2000);

			if (elementVisible_Xpath(xpathMap.get("HelpOnTopBar"))) {

				// report.detailsAppendFolder("Verify Help on top bar", "Help should be
				// visible", "Help is visible", "Pass",CurrencyName);

				funcClick(xpathMap.get("HelpOnTopBar"));

				// clickOnButtonUsingCoordinates("helpCoordinateX","helpCoordinateY");
				Thread.sleep(12000);

				checkpagenavigation(report, gameurl, CurrencyName);

				System.out.println("Page navigated to Back to Game ");
				// log.debug("Page navigated to Back to Game");

				/*
				 * if(isElementVisible("isSpinBtnVisible")) {
				 * 
				 * report.detailsAppendFolder("Verify if back from help", "Back from help",
				 * "On BaseScene, Back from help", "Pass",CurrencyName); } else {
				 * 
				 * report.detailsAppendFolder("Verify if back from help", "Back from help",
				 * "Not on BaseScene, Back from help", "Fail",CurrencyName); }
				 */

			} else {
				report.detailsAppendFolder("Verify Help on top bar", "Help should be visible", "Help is not visible",
						"Fail", CurrencyName);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.debug("error clicking on help");
		}
	}

	public boolean openMenuPanel() {
		boolean result = false;
		try {
			// clicking on menu button
			clickOnButtonUsingCoordinates("menuButtonCoordinateX", "menuButtonCoordinateY");
			Thread.sleep(2000);
			if (isElementVisible("isMenuBackBtnVisible")) {
				log.debug("Menu is open");
				result = true;
			}

			else {
				log.debug("Menu is not open");
			}

		} catch (Exception e) {
			log.debug("unable to click on menu button");
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public boolean openBetPanelOnBaseScene() {
		boolean result = false;
		try {
			// clicking on bet button
			if (xpathMap.get("clickBetUsingCoordinates").equalsIgnoreCase("Yes")) {
				clickOnButtonUsingCoordinates("betCoordinateX", "betCoordinateY");
			}
			Thread.sleep(2000);

			// check if max bet button is visible to know whether bet panel is open
			if (isElementVisible("isMaxBetVisible")) {
				log.debug("Bet is Visible");
				result = true;
			} else {
				log.debug("Bet is not Visible");
			}
		} catch (Exception e) {
			log.debug("unable to click on bet button");
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public boolean openAutoplayPanel() {
		boolean result = false;
		try {
			// clicking on auto play button
			if (xpathMap.get("clickAutoplayUsingCoordinates").equalsIgnoreCase("Yes")) {
				clickOnButtonUsingCoordinates("autoplayCoordinateX", "autoplayCoordinateY");
			}
			/*
			 * else if(xpathMap.get("clickAutoplayUsingHook").equalsIgnoreCase("Yes")) {
			 * clickAtButton("return "+xpathMap.get("ClickBetIconBtn")); }
			 */

			Thread.sleep(3000);

			// verify auto play is displayed
			if (isElementVisible("isStartAutoplayBTNVisible")) {
				log.debug("Autoplay is Visible");
				result = true;
			} else {
				log.debug("Autoplay is not Visible");
			}
		} catch (Exception e) {
			log.debug("unable to click on autoplay button");
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public void startAutoPlay() {
		try {
			clickOnButtonUsingCoordinates("startAutoplayCoordinateX", "startAutoplayCoordinateY");
			Thread.sleep(2000);
		} catch (Exception e) {
			log.debug("unable to click on start autoplay");
			log.error(e.getMessage(), e);
		}
	}

	public void stopAutoPlay() {
		try {
			clickOnButtonUsingCoordinates("spinCoordinateX", "spinCoordinateY");
			Thread.sleep(2000);
		} catch (Exception e) {
			log.debug("unable to stop autoplay");
			log.error(e.getMessage(), e);
		}
	}

	public void validateSpinsSliderAutoplay(Mobile_HTML_Report report, String sliderPoint, String Value,
			String CurrencyName) {
		try {
			boolean ableToSlide = verifySliderValue(sliderPoint, Value);
			if (ableToSlide) {
				report.detailsAppendFolder("Verify autoplay spins slider", "Autoplay spins slider should work",
						"Autoplay spins slider is working", "Pass", CurrencyName);
				log.debug("Autoplay spins slider is working");
			} else {
				report.detailsAppendFolder("Verify autoplay spins slider", "Autoplay spins slider should work",
						"Autoplay spins slider is not working", "Fail", CurrencyName);
				log.debug("Autoplay spins slider is not working");
			}

		} catch (Exception e) {
			log.debug("unable to verify autoplay spins slider");
			log.error(e.getMessage(), e);
		}
	}

	public void validateTotalBetSliderAutoplay(Mobile_HTML_Report report, String sliderPoint, String Value,
			String CurrencyName) {
		try {
			boolean ableToSlide = verifySliderValue(sliderPoint, Value);
			if (ableToSlide) {
				report.detailsAppendFolder("Verify autoplay total bet slider", "Autoplay total bet slider should work",
						"Autoplay total bet slider is working", "Pass", CurrencyName);
				log.debug("Autoplay total bet slider is working");
			} else {
				report.detailsAppendFolder("Verify autoplay total bet slider", "Autoplay total bet slider should work",
						"Autoplay total bet slider is not working", "Fail", CurrencyName);
				log.debug("Autoplay total bet slider is not working");
			}

		} catch (Exception e) {
			log.debug("unable to verify autoplay total bet slider");
			log.error(e.getMessage(), e);
		}
	}

	public void validateWinLimitSliderAutoplay(Mobile_HTML_Report report, String sliderPoint, String Value,
			String CurrencyName) {
		try {
			boolean ableToSlide = verifySliderValue(sliderPoint, Value);
			if (ableToSlide) {
				report.detailsAppendFolder("Verify autoplay win limit slider", "Autoplay win limit slider should work",
						"Autoplay win limit slider is working", "Pass", CurrencyName);
				log.debug("Autoplay win limit slider is working");
			} else {
				report.detailsAppendFolder("Verify autoplay win limit slider", "Autoplay win limit slider should work",
						"Autoplay win limit slider is not working", "Fail", CurrencyName);
				log.debug("Autoplay win limit slider is not working");
			}

		} catch (Exception e) {
			log.debug("unable to verify autoplay win limit slider");
			log.error(e.getMessage(), e);
		}
	}

	public void validateLossLimitSliderAutoplay(Mobile_HTML_Report report, String sliderPoint, String Value,
			String CurrencyName) {
		try {
			boolean ableToSlide = verifySliderValue(sliderPoint, Value);
			if (ableToSlide) {
				report.detailsAppendFolder("Verify autoplay loss limit slider",
						"Autoplay loss limit slider Slider should work", "Autoplay loss limit slider Slider is working",
						"Pass", CurrencyName);
				log.debug("Autoplay loss limit slider is working");
			} else {
				report.detailsAppendFolder("Verify autoplay loss limit slider",
						"Autoplay loss limit slider Slider should work",
						"Autoplay loss limit slider Slider is not working", "Fail", CurrencyName);
				log.debug("Autoplay loss limit slider is not working");
			}

		} catch (Exception e) {
			log.debug("unable to verify autoplay loss limit slider");
			log.error(e.getMessage(), e);
		}
	}

	public void checkpagenavigation(Mobile_HTML_Report report, String gameurl, String CurrencyName) {
		try {
			String mainwindow = webdriver.getWindowHandle();
			Set<String> s1 = webdriver.getWindowHandles();
			if (s1.size() > 1) {

				Iterator<String> i1 = s1.iterator();
				while (i1.hasNext()) {
					String ChildWindow = i1.next();

					if (mainwindow.equalsIgnoreCase(ChildWindow)) {
						System.out.println("Page navigated succesfully");
						if (osPlatform.equalsIgnoreCase("Android")) {
							ChildWindow = i1.next();
						}
						// Thread.sleep(2000);
						webdriver.switchTo().window(ChildWindow);
						String url = webdriver.getCurrentUrl();
						System.out.println("Navigation URL1 is :: " + url);
						log.debug("Navigation URL is :: " + url);
						if (!url.equalsIgnoreCase(gameurl)) {
							// pass condition for navigation
							report.detailsAppendFolder("verify the Navigation screen shot",
									" Navigation page screen shot", "Navigation page screenshot ", "Pass",
									CurrencyName);
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
					report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot",
							"Navigation page screenshot ", "Pass", CurrencyName);
					System.out.println("Page navigated succesfully");
					webdriver.navigate().to(gameurl);
					// waitForSpinButton();
					// newFeature();
					// waitForElement("isNFDButtonVisible");
					Thread.sleep(4000);
					funcFullScreen();
					Thread.sleep(1000);
					closeOverlayForLVC();
					Thread.sleep(1000);
				} else {
					report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot",
							"Navigation page screenshot ", "Pass", CurrencyName);

					Thread.sleep(1000);

					webdriver.navigate().to(gameurl);
					// waitForElement("isNFDButtonVisible");
					Thread.sleep(4000);
					/*
					 * funcFullScreen(); Thread.sleep(1000); closeOverlayForLVC();
					 * Thread.sleep(1000);
					 */
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

	public boolean verifyRegularExpressionPlayNext(Mobile_HTML_Report report, String regExp, String method,
			String isoCode) {
		String value = null;
		String Text = method;
		String Text1 = Text.replaceAll("[a-zA-Z:]", "").trim();
		System.out.println("currency value text: " + Text1);
		log.debug("currency value text: " + Text);
		boolean regexp = false;
		try {
			if (isoCode.equalsIgnoreCase("MMK")) {
				value = Text1.replaceAll(" ", "");
			} else {
				value = Text1;
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
	public boolean spinclick(ImageLibrary imageLibrary) {
		try {
			if (imageLibrary.isImageAppears("Spin")) {
				imageLibrary.click("Spin");
				log.debug("Clicked on spin button");
			} else {
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
	public String getCurrentWinAmt(Mobile_HTML_Report report, String CurrencyName) {
		String winAmt = null;
		Wait = new WebDriverWait(webdriver, 250);
		try {

			winAmt = GetConsoleText("return " + xpathMap.get("getWinAmt"));
			System.out.println(" Win Amount is " + winAmt);
			log.debug(" Win Amount is " + winAmt);
			/*
			 * boolean isWinAmt = isElementVisible("isWinAmtVisible"); if (isWinAmt) {
			 * report.detailsAppendFolder("verify win is visible in baseScene",
			 * "Win should be visible","Win is visible", "Pass",CurrencyName);
			 * log.debug("Win amount is visible");
			 * System.out.println("Win amount is visible");
			 * 
			 * 
			 * winAmt = GetConsoleText("return " + xpathMap.get("getWinAmt"));
			 * System.out.println(" Win Amount is " + winAmt); log.debug(" Win Amount is " +
			 * winAmt); } else {
			 * report.detailsAppendFolder("verify win is visible in baseScene",
			 * "Win should be visible","Win is not visible", "Fail",CurrencyName);
			 * 
			 * System.out.println("There is no Win "); log.debug("There is no Win "); }
			 */
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
			report.detailsAppendFolder("verify big win is visible in baseScene", "BigWin should be visible",
					"BigWin is visible", "Pass", CurrencyName);
			log.debug("Big win amount is visible");
			System.out.println("Big win amount is visible");

			bigWinAmt = GetConsoleText("return " + xpathMap.get("BigWinValue"));

			System.out.println("Big Win Amount is " + bigWinAmt);
			log.debug(" Big Win Amount is " + bigWinAmt);

			/*
			 * boolean isBigWin = isElementVisible("BigWin"); if (isBigWin) {
			 * report.detailsAppendFolder("verify big win is visible in baseScene",
			 * "BigWin should be visible","BigWin is visible", "Pass",CurrencyName);
			 * log.debug("Big win amount is visible");
			 * System.out.println("Big win amount is visible");
			 * 
			 * bigWinAmt = GetConsoleText("return " + xpathMap.get("BigWinValue"));
			 * 
			 * System.out.println("Big Win Amount is " + bigWinAmt);
			 * log.debug(" Big Win Amount is " + bigWinAmt);
			 * 
			 * } else { report.detailsAppendFolder("verify big win is visible in baseScene",
			 * "BigWin should be visible","BigWin is not visible", "Fail",CurrencyName);
			 * System.out.println("There is no Big Win "); log.debug("There is no Big Win");
			 * }
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return bigWinAmt;

	}

	public String getCurrentWinAmtImg(Mobile_HTML_Report report, ImageLibrary imageLibrary, String CurrencyName) {
		String winAmt = null;
		Wait = new WebDriverWait(webdriver, 250);
		int count = 0;

		try {

			winAmt = GetConsoleText("return " + xpathMap.get("winAmt"));
			System.out.println(" Win Amount is " + winAmt);
			log.debug(" Win Amount is " + winAmt);

			while (winAmt.equals("")) {
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
				if (count == 3) {
					break;

				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return winAmt;

	}

	public String verifyBigWinImg(Mobile_HTML_Report report, ImageLibrary imageLibrary, String CurrencyName) {
		String bigWinAmt = null;
		Wait = new WebDriverWait(webdriver, 30000);
		int count = 0;

		try {

			bigWinAmt = GetConsoleText("return " + xpathMap.get("BigWinValue"));

			System.out.println("Big Win Amount is " + bigWinAmt);
			log.debug(" Big Win Amount is " + bigWinAmt);

			while (bigWinAmt.equals("")) {
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
				if (count == 3) {
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
			setChromiumWebViewContext();
			amgWinAmt = GetConsoleText("return " + xpathMap.get("getAmgWinAmt"));

			System.out.println("bonus Win Amount is " + amgWinAmt);
			log.debug(" bonus Win Amount is " + amgWinAmt);
			/*
			 * boolean isAmgWin = isElementVisible("isAmgWinAmtVisible"); if (isAmgWin) {
			 * report.detailsAppendFolder("verify bonus win is visible in baseScene",
			 * "Bonus win should be visible","Bonus win is visible", "Pass",CurrencyName);
			 * log.debug("Bonus win amount is visible");
			 * System.out.println("Bonus win amount is visible");
			 * 
			 * amgWinAmt = GetConsoleText("return " + xpathMap.get("getAmgWinAmt"));
			 * 
			 * System.out.println("bonus Win Amount is " + amgWinAmt);
			 * log.debug(" bonus Win Amount is " + amgWinAmt);
			 * 
			 * 
			 * } else {
			 * report.detailsAppendFolder("verify bonus win is visible in baseScene",
			 * "Bonus win should be visible","Bonus win is not visible",
			 * "Fail",CurrencyName);
			 * 
			 * System.out.println("There is no bonus Win ");
			 * log.debug("There is no bonus Win"); }
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return amgWinAmt;

	}

	public void verifyQuickbetOptionsRegExp(Mobile_HTML_Report report, String currencyName, String regExpr,
			String regExprNoSymbol, String isoCode) {
		try {
			report.detailsAppend("Verify Bet panel is displayed ", "Bet panel is displayed", "Bet panel is displayed",
					"Pass");

			String noOfQuickBets = xpathMap.get("totalQuickBets");

			int totalNoOfQuickBets = (int) Double.parseDouble(noOfQuickBets);
			for (int quickBet = 1; quickBet <= totalNoOfQuickBets; quickBet++) {

				String bet = xpathMap.get("isQuickBet" + quickBet);
				boolean isQuickBetetVisible = isElementVisibleUsingHook(bet);
				if (isQuickBetetVisible) {

					String quickBetVal = GetConsoleText("return " + xpathMap.get("QuickBet" + quickBet + "Value"));
					report.detailsAppendFolderOnlyScreenshot("Verify quick bet " + quickBet + " is visible ",
							" Quick Bet " + quickBet + " is visibled", "Quick Bet " + quickBet + " is visible ", "Pass",
							currencyName);

					boolean betAmt = verifyRegularExpressionPlayNext(report, regExpr, quickBetVal, isoCode);
					if (betAmt) {
						report.detailsAppendFolder("verify curency format for quick bet " + quickBet + " in bet panel ",
								"Currency format should be correct", "Currency format is correct", "Pass",
								currencyName);

					} else {
						report.detailsAppendFolder("verify curency format for quick bet " + quickBet + " in bet panel",
								"Currency format should be correct", "Currency format is incorrect", "Fail",
								currencyName);

					}

					String coordinateX = xpathMap.get("QuickBet" + quickBet + "CoordinateX");
					String coordinateY = xpathMap.get("QuickBet" + quickBet + "CoordinateY");
					double coorX = Double.parseDouble(coordinateX);
					double coorY = Double.parseDouble(coordinateY);
					tapOnCoordinates(coorX, coorY);
					Thread.sleep(2000);

					report.detailsAppendFolder("Verify quick bet " + quickBet + " can be selected ",
							" Quick Bet " + quickBet + " should be selected", "Quick Bet " + quickBet + " is selected ",
							"Pass", currencyName);

					boolean isBetChangedIntheConsole = isBetChangedIntheConsole(quickBetVal);
					if (isBetChangedIntheConsole) {
						report.detailsAppendFolder(
								"verify that is bet changed in the console for quick bet " + quickBet + " ,value ="
										+ quickBetVal,
								"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Bet Changed In the Console", "Pass", currencyName);
					} else {
						report.detailsAppendFolder(
								"verify that is bet changed in the console for quick bet " + quickBet + " ,value ="
										+ quickBetVal,
								"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Bet not Changed In the Console", "Fail", currencyName);

					}

					boolean quickBetOnBaseScene = verifyRegularExpressionPlayNext(report, regExpr,
							GetConsoleText("return " + xpathMap.get("BetTextValue")), isoCode);
					log.debug("Quick bet value: " + quickBetOnBaseScene);
					if (quickBetOnBaseScene) {
						report.detailsAppendFolder(
								"verify currency format for quick bet " + quickBet + " in base scene console ",
								"Currency format should be correct for quick bet " + quickBet
										+ " in base scene console ",
								"Currency format is correct for quick bet " + quickBet + " in base scene console ",
								"Pass", currencyName);

					} else {
						report.detailsAppendFolder(
								"verify currency format for quick bet " + quickBet + " in base scene console ",
								"Currency format should be correct for quick bet " + quickBet
										+ " in base scene console ",
								"Currency format is incorrect for quick bet " + quickBet + " in base scene console ",
								"Fail", currencyName);

					}

					openBetPanelOnBaseScene();
				}
			}

			boolean isMaxBet = isElementVisible("isMaxBetVisible");
			if (isMaxBet && xpathMap.get("isMaxBetAvailable").equalsIgnoreCase("Yes")) {
				try {
					// report.detailsAppend("Verify that Max Bet is visible "," Max Bet should be
					// visibled", "Max Bet is visible ","Pass"+ currencyName);
					setMaxbetPlayNext();

					report.detailsAppendFolder("Verify Max Bet can be selected ", " Max Bet should be selected",
							"Max Bet is selected ", "Pass", currencyName);
					boolean betAmt = verifyRegularExpressionPlayNext(report, regExpr,
							GetConsoleText("return " + xpathMap.get("BetTextValue")), isoCode);
					log.debug("Max bet value: " + betAmt);
					if (betAmt) {
						report.detailsAppendFolder("verify currency format for max bet ",
								"Max bet should be in correct currency format", "Max bet is in correct currency format",
								"Pass", currencyName);

					} else {
						report.detailsAppendFolder("verify currency format for max bet ",
								"Max bet should be in correct currency format",
								"Max bet is in incorrect currency format", "Fail", currencyName);

					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

		} catch (Exception e) {
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
			// log.debug("Win Amount is " + winAmt);
			/*
			 * boolean isWinAmt = isElementVisible("isFSNormalWinVisible"); if (isWinAmt) {
			 * log.debug("Win amount is visible in freespins");
			 * System.out.println("Win amount is visible in freespins");
			 * 
			 * report.detailsAppendFolder("verify win is visible in FreeSpins",
			 * "Win should be visible","Win is visible", "Pass",CurrencyName);
			 * 
			 * winAmt = GetConsoleText("return " + xpathMap.get("getFSNormalWinValue"));
			 * 
			 * 
			 * System.out.println("Win Amount is " + winAmt); log.debug("Win Amount is " +
			 * winAmt); } else {
			 * report.detailsAppendFolder("verify win is visible in FreeSpins",
			 * "Win should be visible","Win is visible", "Pass",CurrencyName);
			 * 
			 * System.out.println("There is no Win in freepsins");
			 * log.debug("There is no Win in freepsins"); }
			 */
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

			/*
			 * boolean isBigWin = isElementVisible("isFSBigWinVisible"); if (isBigWin) {
			 * log.debug("Bigwin amount is visible in freespins");
			 * System.out.println("Bigwin amount is visible in freespins");
			 * 
			 * report.detailsAppendFolder("verify big win is visible in FreeSpins",
			 * "BigWin should be visible","BigWin is visible", "Pass",CurrencyName);
			 * 
			 * bigWinAmt = GetConsoleText("return " + xpathMap.get("getFSBigWinValue"));
			 * System.out.println("FreeSpin Big Win Amount is " + bigWinAmt);
			 * log.debug("Freespin Big Win Amount is " + bigWinAmt);
			 * 
			 * 
			 * } else { report.detailsAppendFolder("verify big win is visible in FreeSpins",
			 * "BigWin should be visible","BigWin is visible", "Fail",CurrencyName);
			 * 
			 * System.out.println("There is no Big Win in freespins");
			 * log.debug("There is no Big Win in freespins"); }
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return bigWinAmt;

	}

	public String verifyBonusWinInFS(Mobile_HTML_Report report, String CurrencyName) {
		String amgWinAmt = null;
		Wait = new WebDriverWait(webdriver, 30000);

		try {
			boolean isAmgWin = isElementVisible("isFSAmazingWinVisible");
			if (isAmgWin) {
				log.debug("Bonus win amount is visible in freespins");
				System.out.println("Bonus win amount is visible in freespins");

				report.detailsAppendFolder("verify bonus win is visible in FreeSpins", "Bonus win should be visible",
						"Bonus win is visible", "Pass", CurrencyName);

				amgWinAmt = GetConsoleText("return " + xpathMap.get("getFSAmazingWinValue"));

				System.out.println("bonus Win Amount is " + amgWinAmt);
				log.debug("bonus Win Amount is " + amgWinAmt);

			} else {
				report.detailsAppendFolder("verify bonus win is visible in FreeSpins", "Bonus win should be visible",
						"Bonus win is visible", "Fail", CurrencyName);

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

	public String funcGetTextElement(String locator) {
		try {
			WebElement ele = webdriver.findElement(By.xpath(xpathMap.get(locator)));
			System.out.println("" + ele.getText());
			log.debug(ele.getText());
			return ele.getText();

		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean verifyGridPayouts(Mobile_HTML_Report report, String regExp, String CurrencyName, String isoCode) {
		boolean result = false;
		int trueCount = 0;
		try {
			String gridSize = xpathMap.get("gridCount");
			Double count = Double.parseDouble(gridSize);
			int gridCount = count.intValue();
			for (int i = 1; i <= gridCount; i++) {
				String gridEle = "GridPay" + i + "";
				log.debug("Grid value " + gridEle);
				System.out.println("Grid value " + gridEle);
				String gridValue = funcGetTextElement(gridEle);
				boolean gridVl = verifyRegularExpressionPlayNext(report, regExp, gridValue, isoCode);

				if (gridVl) {
					trueCount++;
				}
			}
			System.out.println("trueCount" + trueCount);
			if (trueCount == gridCount) {
				result = true;
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
	public boolean validatePayoutsFromPaytable(Mobile_HTML_Report report, String CurrencyName, String regExpr) {
		boolean payoutvalues = false;
		try {
			if (xpathMap.get("paytableScrollOfNine").equalsIgnoreCase("yes")) {
				payoutvalues = verifyRegularExpressionUsingArrays(report, regExpr,
						paytablePayoutsOfScatter(report, CurrencyName));
				return payoutvalues;
			} else if (xpathMap.get("paytableScrollOfFive").equalsIgnoreCase("yes")) {
				payoutvalues = verifyRegularExpressionUsingArrays(report, regExpr,
						paytablePayoutsOfScatterWild(report, CurrencyName));
				return payoutvalues;
			} else {
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
		int count = Text.length;
		int trueCount = 0;
		boolean regexp = false;
		try {
			// Thread.sleep(2000);
			for (int i = 0; i < Text.length; i++) {
				if (Text[i].matches(regExp)) {
					log.debug("Compared with Reg Expression currency format is correct for value: " + Text[i]);
					trueCount++;
				} else {
					log.debug("Compared with Reg Expression currency format is different for value: " + Text[i]);
				}
				Thread.sleep(2000);
			}

			if (count == trueCount) {
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
		String symbols[] = { "Scatter5", "Scatter4", "Scatter3" };

		try {
			System.out.println("Paytable Validation for  Scatter ");
			log.debug("Paytable Validation for Scatter ");
			for (int i = 0; i < symbols.length; i++) {
				symbols[i] = funcGetTextElement(symbols[i]);
				// System.out.println(symbols[i]);
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
		String symbols[] = { "Scatter5", "Scatter4", "Scatter3", "Scatter2", "Wild5", "Wild4", "Wild3", "Wild2" };
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
	public String freeGameEntryInfo(ImageLibrary imageLibrary, String fgInfotxt) {
		String infoText = null;
		try {
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
			String text = GetConsoleText("return " + xpathMap.get(fgInfotxt));
			System.out.println(text);
			log.debug(text);
			// trim until the @ symbol
			int index = text.lastIndexOf("@");
			if (index > 0) {
				text = text.substring(index + 1, text.length());
				infoText = text.trim();
				System.out.println(infoText);
				log.debug(infoText);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return infoText;
	}

	public boolean clickPlayNow(ImageLibrary imageLibrary) {
		boolean result = false;
		try {
			// click on play now button on Free Spins Intro Screen
			if (imageLibrary.isImageAppears("FGPlayNow")) {
				imageLibrary.click("FGPlayNow");
				Thread.sleep(3000);
				result = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public boolean assignFreeGames(String userName, String offerExpirationUtcDate, int mid, int cid, int noOfOffers,
			int defaultNoOfFreeGames) {
		// assign free games to above created user
		boolean isFreeGameAssigned = false;
		try {
			String balanceTypeId = xpathMap.get("BalanceTypeID");
			Double dblBalanceTypeID = Double.parseDouble(balanceTypeId);
			balanceTypeId = "" + dblBalanceTypeID.intValue() + "";

			// Assign free games offers to user
			if (TestPropReader.getInstance().getProperty("EnvironmentName").equalsIgnoreCase("Bluemesa")) {

				isFreeGameAssigned = addFreeGameToUserInBluemesa(userName, defaultNoOfFreeGames, offerExpirationUtcDate,
						balanceTypeId, mid, cid, noOfOffers);
			} else {
				isFreeGameAssigned = addFreeGameToUserInAxiom(userName, defaultNoOfFreeGames, offerExpirationUtcDate,
						balanceTypeId, mid, cid, noOfOffers);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isFreeGameAssigned;
	}

	public void clickBaseSceneDiscardButton() {
		try {
			// click on play now button on Free Spins Intro Screen
			if (isElementVisible("discardIconBaseSceneVisible")) {
				clickOnButtonUsingCoordinates("discardIconBaseSceneCoordinateX", "discardIconBaseSceneCoordinateY");
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public boolean confirmDiscardOffer(ImageLibrary imageLibrary) {
		boolean result = false;
		try {

			imageLibrary.click("FGDiscard");
			Thread.sleep(3000);
			result = true;
			// click on play now button on Free Spins Intro Screen
			/*
			 * if(isElementVisible("discardBtnVisible")) {
			 * 
			 * }
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public boolean clickOnPlayLater(ImageLibrary imageLibrary) {
		boolean result = false;
		try {
			imageLibrary.click("FGPlayLater");
			Thread.sleep(3000);
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;

	}

	public boolean clickOnEntryScreenDiscard(ImageLibrary imageLibrary) {
		boolean result = false;
		try {
			imageLibrary.click("FGDiscard");

			Thread.sleep(3000);
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public void verifyquickbetOptionsRegExp(Mobile_HTML_Report report, String currencyName, String regExpr,
			String regExprNoSymbol, String isoCode) {
		try {
			report.detailsAppend("Verify Bet panel is displayed ", "Bet panel is displayed", "Bet panel is displayed",
					"Pass");

			String noOfQuickBets = xpathMap.get("totalQuickBets");
			int totalNoOfQuickBets = (int) Double.parseDouble(noOfQuickBets);
			for (int quickBet = 1; quickBet <= totalNoOfQuickBets; quickBet++) {

				String bet = xpathMap.get("isQuickBet" + quickBet);
				boolean isQuickBetetVisible = isElementVisibleUsingHook(bet);
				if (isQuickBetetVisible) {

					String quickBetVal = GetConsoleText("return " + xpathMap.get("QuickBet" + quickBet + "Value"));
					report.detailsAppendFolderOnlyScreenshot("Verify quick bet " + quickBet + " is visible ",
							" Quick Bet " + quickBet + " is visibled", "Quick Bet " + quickBet + " is visible ", "Pass",
							currencyName);

					boolean betAmt = verifyRegularExpressionPlayNext(report, regExprNoSymbol, quickBetVal, isoCode);
					if (betAmt) {
						report.detailsAppendFolder("verify curency format for quick bet " + quickBet + " in bet panel ",
								"Currency format should be correct", "Currency format is correct", "Pass",
								currencyName);

					} else {
						report.detailsAppendFolder("verify curency format for quick bet " + quickBet + " in bet panel",
								"Currency format should be correct", "Currency format is incorrect", "Fail",
								currencyName);

					}

					String coordinateX = xpathMap.get("QuickBet" + quickBet + "CoordinateX");
					String coordinateY = xpathMap.get("QuickBet" + quickBet + "CoordinateY");
					double coorX = Double.parseDouble(coordinateX);
					double coorY = Double.parseDouble(coordinateY);
					tapOnCoordinates(coorX, coorY);
					Thread.sleep(2000);

					report.detailsAppendFolder("Verify quick bet " + quickBet + " can be selected ",
							" Quick Bet " + quickBet + " should be selected", "Quick Bet " + quickBet + " is selected ",
							"Pass", currencyName);

					boolean isBetChangedIntheConsole = isBetChangedIntheConsole(quickBetVal);
					if (isBetChangedIntheConsole) {
						report.detailsAppendFolder(
								"verify that is bet changed in the console for quick bet " + quickBet + " ,value ="
										+ quickBetVal,
								"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Bet Changed In the Console", "Pass", currencyName);

					} else {
						report.detailsAppendFolder(
								"verify that is bet changed in the console for quick bet " + quickBet + " ,value ="
										+ quickBetVal,
								"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Bet not Changed In the Console", "Fail", currencyName);

					}

					openBetPanelOnBaseScene();
				}
			}

			boolean isMaxBet = isElementVisible("isMaxBetVisible");
			if (isMaxBet && xpathMap.get("isMaxBetAvailable").equalsIgnoreCase("Yes")) {
				try {
					// report.detailsAppend("Verify that Max Bet is visible "," Max Bet should be
					// visibled", "Max Bet is visible ","Pass"+ currencyName);
					setMaxbetPlayNext();

					report.detailsAppendFolder("Verify Max Bet can be selected ", " Max Bet should be selected",
							"Max Bet is selected ", "Pass", currencyName);
					boolean betAmt = verifyRegularExpressionPlayNext(report, regExpr,
							GetConsoleText("return " + xpathMap.get("BetTextValue")), isoCode);
					log.debug("Max bet value: " + betAmt);
					if (betAmt) {
						report.detailsAppendFolder("verify curency format for max bet ",
								"Max bet should be in correct currency format", "Max bet is in correct currency format",
								"Pass", currencyName);

					} else {
						report.detailsAppendFolder("verify curency format for max bet ",
								"Max bet should be in correct currency format",
								"Max bet is in incorrect currency format", "Fail", currencyName);

					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void clickOnContinue() {
		clickOnButtonUsingCoordinates("clickOnContinueCoordinateX", "clickOnContinueCoordinateY");
	}

	/**
	 * This method is used to click on Base scene continue/NFD buttin
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 */
	public void clickOnBaseSceneContinueButton(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("NFDButton")) {
				System.out.println("NFD button is visible");
				log.debug("NFD button is visible");
				Thread.sleep(2000);
				imageLibrary.click("NFDButton");
				Thread.sleep(2000);

			} else {
				System.out.println("NFD button is not visible");
				log.debug("NFD button is not visible");
				report.detailsAppendFolder("Verify Continue button is visible ", "Continue buttion is visible",
						"Continue button is not visible", "Fail", currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to refresh game
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void refreshGame(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			setChromiumWebViewContext();
			webdriver.navigate().refresh();
			log.debug("game Refresh ");
			Thread.sleep(10000);
			funcFullScreen();
			Thread.sleep(3000);
			if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
				clickOnBaseSceneContinueButton(report, imageLibrary, currencyName);
			}
			Thread.sleep(3000);
			report.detailsAppendFolder("Verify Refresh", "After Refresh", "Ater Refresh", "Pass", currencyName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to verify quick spin
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void verifyQuickSpin(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("QuickSpinOn")) {
				// click on bet menu
				Thread.sleep(2000);
				imageLibrary.click("QuickSpinOn");
				Thread.sleep(2000);
				if (imageLibrary.isImageAppears("QuickSpinOff")) {
					report.detailsAppendFolder("Quick spin Buttom ", "Quick Spin button should be clicked",
							"Quick Spin button is clicked", "Pass", currencyName);
				} else {
					report.detailsAppendFolder("Quick spin Buttom ", "Quick Spin button should be clicked",
							"unable to click on quick spin", "Fail", currencyName);
				}
			} else {
				report.detailsAppendFolder("Quick spin Buttom ", "Quick Spin button should be available",
						"quick spin button is not visible", "Fail", currencyName);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to open Autoplay
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @return
	 */
	public boolean openAutoplayPanel(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		boolean isAutoplayOpen = false;
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Autoplay")) {
				// click on autoplay button
				imageLibrary.click("Autoplay");
				Thread.sleep(3000);
				if (imageLibrary.isImageAppears("Autoplay10x")) {
					report.detailsAppendFolder("Autoplay menu", "Autoplay menu", "Autoplay menu is opened", "Pass",
							currencyName);
					isAutoplayOpen = true;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return isAutoplayOpen;
	}

	/**
	 * This method is used to validate Autoplay panel
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void verifyAutoplayPanel(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			if (openAutoplayPanel(report, imageLibrary, currencyName)) {
				refreshGame(report, imageLibrary, currencyName);
			}

			if (openAutoplayPanel(report, imageLibrary, currencyName)) {
				verifyAutoplayOptions(report, currencyName);

				startAutoplay(report, imageLibrary, currencyName);

				stopAutoplay(report, imageLibrary, currencyName);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This method is used to click on start autoplay
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void startAutoplay(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("AutoplayStart")) {
				imageLibrary.click("AutoplayStart");
				Thread.sleep(2000);
				if (xpathMap.get("startAutoplayDialog").equalsIgnoreCase("Yes")) {
					if (imageLibrary.isImageAppears("Start")) {
						Thread.sleep(2000);
						imageLibrary.click("Start");
						Thread.sleep(1000);
						report.detailsAppendFolder("Verify able to click StartAutoplay",
								"Should be able to click on StartAutoplay", "Able to click on StartAutoplay", "PASS",
								currencyName);
					} else {
						report.detailsAppendFolder("Verify if able to click on StartAutoplay",
								"Should be able to click on StartAutoplay", "Not able to click on StartAutoplay",
								"Fail", currencyName);
					}
					Thread.sleep(5000);
				}
				/*if (imageLibrary.isImageAppears("Spin"))
					report.detailsAppendFolder("Autoplay", "Autoplay should work", "Autoplay is working", "Pass",
							currencyName);
				else
					report.detailsAppendFolder("Autoplay", "Autoplay should work", "Autoplay is not working", "Fail",
							currencyName);*/
			} else {
				report.detailsAppendFolder("Autoplay", "Start Autoplay Button visibility",
						"Start Autoplay Button is not Visible", "Fail", currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to stop autoplay spins
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void stopAutoplay(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("AutoplayStop")) {
				imageLibrary.click("AutoplayStop");
				Thread.sleep(3000);
				if (imageLibrary.isImageAppears("Spin"))
					report.detailsAppendFolder("Autoplay", "Autoplay Stop should work", "Autoplay is stopped", "Pass",
							currencyName);
				else
					report.detailsAppendFolder("Autoplay", "Autoplay Stop should work", "Autoplay is did not stop",
							"Fail", currencyName);
			} else {
				report.detailsAppendFolder("Autoplay", "Stop Autoplay Button visibility",
						"Start Autoplay Button is not Visible", "Fail", currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to open bet panel
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @return
	 */
	public boolean openBetPanel(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		boolean isBetOpen = false;

		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("BetButton")) {

				imageLibrary.click("BetButton");
				Thread.sleep(2000);
				report.detailsAppendFolder("Bet menu", "Bet menu", "Bet menu is opened", "Pass", currencyName);
				isBetOpen = true;
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return isBetOpen;
	}

	/**
	 * This method is used to validate bet panel
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void verifyBetPanel(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			if (openBetPanel(report, imageLibrary, currencyName)) {
				refreshGame(report, imageLibrary, currencyName);
			}

			if (openBetPanel(report, imageLibrary, currencyName)) {
				verifyBetSliders(report, imageLibrary, currencyName);

				setMaxBet(report, imageLibrary, currencyName);
			}

		} catch (Exception e) {
			log.debug("unable to bet panel");
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This method is used to validate set max bet
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void setMaxBet(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("BetMax")) {
				Thread.sleep(2000);
				imageLibrary.click("BetMax");
				Thread.sleep(2000);
				if (imageLibrary.isImageAppears("Spin")) {
					report.detailsAppendFolder("Max Bet", "Click on Max Button", "Base Scene is visible", "Pass",
							currencyName);
				} else {
					report.detailsAppendFolder("Max Bet", "Click on Max Button", "Base Scene is visible not visible",
							"Fail", currencyName);
				}
			}
		} catch (Exception e) {
			log.debug("unable to bet panel");
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This method is used to open Menu panel
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @return
	 */
	public boolean openMenuPanel(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		boolean isMenuOpen = false;
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Menu")) {
				Thread.sleep(2000);
				imageLibrary.click("Menu");
				Thread.sleep(2000);
				report.detailsAppendFolder("Menu Open ", "Menu should open", "Menu is Opened", "Pass", currencyName);
				isMenuOpen = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return isMenuOpen;
	}

	/**
	 * This method is used to close settings/Paytable/Autoplay/bet using close
	 * button
	 * 
	 * @author pb61055
	 * @param imageLibrary
	 * @return
	 */
	public boolean closeButton(ImageLibrary imageLibrary) {
		boolean isClose = false;

		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("MenuClose")) {
				imageLibrary.click("MenuClose");
				Thread.sleep(2000);
				isClose = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isClose;
	}

	/**
	 * This method is is used to verify paytable and paytable scroll
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void verifyPaytable(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			if (openPaytable(report, imageLibrary, currencyName)) {
				refreshGame(report, imageLibrary, currencyName);
			}

			if (openPaytable(report, imageLibrary, currencyName)) {
				if (closePaytable(imageLibrary)) {
					boolean scrollPaytable = paytableScroll(report, currencyName);
					if (scrollPaytable)
						report.detailsAppendFolder("PayTable ", "PayTable Scroll ", "PayTable Scroll", "Pass",
								currencyName);
					else
						report.detailsAppendFolder("PayTable ", "PayTable Scroll ", "PayTable Scroll", "Fail",
								currencyName);

					closePaytable(imageLibrary);
					Thread.sleep(2000);
					if (imageLibrary.isImageAppears("Spin"))
						report.detailsAppendNoScreenshot("Verify Paytable Closed", "Paytable should be Closed",
								"Base Scene is visible after paytable closed", "Pass");
					else
						report.detailsAppendFolder("Verify Paytable Closed", "Paytable should be Closed",
								"Paytable is not closed", "Fail", currencyName);
				} else {
					report.detailsAppendFolder("PayTable ", "PayTable should opened", "PayTable is not opened", "Fail",
							currencyName);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to validate Settings panel
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void verifySettingsPanel(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			if (openSettingsPanel(report, imageLibrary, currencyName)) {
				refreshGame(report, imageLibrary, currencyName);
			}

			if (openSettingsPanel(report, imageLibrary, currencyName)) {
				if ("Yes".equalsIgnoreCase(xpathMap.get("settingsUnderMenu")))
					closeButton(imageLibrary);
				else
					imageLibrary.click("SettingsClose");
				Thread.sleep(2000);

				if (imageLibrary.isImageAppears("Spin")) {
					report.detailsAppendNoScreenshot("Base Scene after Settings closed ",
							"Base Scene after Settings closed", "We are on Base Scene after Settings closed", "Pass");
				} else {
					report.detailsAppendFolder("Base Scene after Settings closed ", "Base Scene after Settings closed",
							"We are not on Base Scene after Settings closed", "Fail", currencyName);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * *This method is used to open paytable
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @return
	 */
	public boolean openPaytable(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		boolean isPaytbaleOpen = false;

		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Menu")) {
				imageLibrary.click("Menu");
				Thread.sleep(2000);

				if (imageLibrary.isImageAppears("Paytable")) {
					// click on PayTable
					imageLibrary.click("Paytable");
					Thread.sleep(2000);
					report.detailsAppendFolder("PayTable open", "PayTable should be opened", "PayTable is opened",
							"Pass", currencyName);
					isPaytbaleOpen = true;
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isPaytbaleOpen;
	}

	/**
	 * This method is used to close paytable
	 * 
	 * @author pb61055
	 * @param imageLibrary
	 * @return
	 */
	public boolean closePaytable(ImageLibrary imageLibrary) {
		boolean isPaytbaleClose = false;

		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("PaytableClose")) {
				imageLibrary.click("PaytableClose");
			} else if (elementVisible_Xpath(xpathMap.get("PaytableClose"))) {
				setChromiumWebViewContext();
				func_click(xpathMap.get("PaytableClose"));
				Thread.sleep(2000);
				isPaytbaleClose = true;
			} else {
				log.debug("unable to close paytable");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isPaytbaleClose;
	}

	/**
	 * This method is used to open settings
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @return
	 */
	public boolean openSettingsPanel(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		boolean isSettingsOpen = false;
		try {
			webdriver.context("NATIVE_APP");
			// click on menu if settings are under menu
			if ("Yes".equalsIgnoreCase(xpathMap.get("settingsUnderMenu"))) {
				if (imageLibrary.isImageAppears("Menu")) {

					imageLibrary.click("Menu");
				}
			}
			if (imageLibrary.isImageAppears("Settings")) {
				// click on menu
				imageLibrary.click("Settings");
				report.detailsAppendFolder("Settings Open ", "Settings should be open", "Settings is Opened", "Pass",
						currencyName);
				isSettingsOpen = true;
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			e.getMessage();
		}

		return isSettingsOpen;
	}

	public void verifyHelpNavigation(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,String RTP) {
		try {
			String gameurl = webdriver.getCurrentUrl();
			webdriver.context("NATIVE_APP");
			Thread.sleep(2000);
			if (imageLibrary.isImageAppears("HelpOnTopBar")) {
				Thread.sleep(2000);
				imageLibrary.click("HelpOnTopBar");
				Thread.sleep(2000);
				report.detailsAppend("Verify able to click on HelpOnTopBar", "Should be able to click on HelpOnTopBar",
						"Able to click on HelpOnTopBar", "PASS");
				Thread.sleep(3000);
				if (imageLibrary.isImageAppears("Help")) {
					Thread.sleep(3000);
					imageLibrary.click("Help");
					Thread.sleep(3000);
					report.detailsAppend("Verify able to click on HelpMenu", "Should be able to click on HelpMenu",
							"Able to click on HelpMenu", "PASS");
					
					
					if(RTP.equalsIgnoreCase("yes"))
					{
						report.detailsAppendFolder("Verify Help is visible", "Help should be visible", "Help is visible",
								"Pass",currencyName);
						helpFileRTP(report, currencyName);
					}
					
					checkpagenavigation(report, gameurl, currencyName);
				} else {
					report.detailsAppend("Verify if able to click on HelpMenu", "Should be able to click on HelpMenu",
							"Not able to click on HelpMenu", "Fail");
				}

			} else {
				report.detailsAppend("Verify if able to click on HelpOnTopBar",
						"Should be able to click on HelpOnTopBar", "Not able to click on HelpOnTopBar", "Fail");
			}

			setChromiumWebViewContext();
			Thread.sleep(3000);
			if (xpathMap.get("HelpMenuPresent").equalsIgnoreCase("Yes")) {
				verifyHelpOnTopbar(report, currencyName);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * This method is used for verifying currency format for credits
	 * 
	 * @author pb61055
	 * @param report
	 * @param currencyName
	 * @param regExpr
	 */

	public void verifyCreditsCurrencyFormat(Mobile_HTML_Report report, String regExpr) {
		setChromiumWebViewContext();
		boolean credits = verifyRegularExpression(report, regExpr,
				GetConsoleText("return " + xpathMap.get("CreditValue")));

		if (credits) {

			report.detailsAppendNoScreenshot("Verify the currency format for credit ",
					"Credit should display in correct currency format ",
					"Credit is displaying in correct currency format ", "Pass");
		} else {
			report.detailsAppendNoScreenshot("Verify the currency format for credit ",
					"Credit should display in correct currency format ",
					"Credit is displaying in incorrect currency format ", "Fail");
		}

	}

	/**
	 * This method is used for verifying currency format for credits
	 * 
	 * @author pb61055
	 * @param report
	 * @param currencyName
	 * @param regExpr
	 */

	public void verifyBetCurrencyFormat(Mobile_HTML_Report report, String regExpr) {
		setChromiumWebViewContext();
		boolean bet = verifyRegularExpression(report, regExpr,
				GetConsoleText("return " + xpathMap.get("BetTextValue")));
		if (bet) {

			report.detailsAppendNoScreenshot("Verify the currency format for bet ",
					"Bet should display in correct currency format ", "Bet is displaying in correct currency format ",
					"Pass");
		} else {
			report.detailsAppendNoScreenshot("Verify the currency format for bet ",
					"Bet should display in correct currency format ", "Bet is displaying in incorrect currency format ",
					"Fail");
		}

	}

	/**
	 * This method is used to validate paytable payout currency format
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void validatePaytablePayoutsCurrencyFormat(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String currencyName, String regExpr, String isoCode) {
		try {
			openPaytable(report, imageLibrary, currencyName);
			if (closePaytable(imageLibrary)) {
				setChromiumWebViewContext();
				boolean scrollPaytable = paytableScroll(report, currencyName);
				if (scrollPaytable)
					report.detailsAppendFolder("PayTable ", "PayTable Scroll ", "PayTable Scroll", "Pass",
							currencyName);
				else
					report.detailsAppendFolder("PayTable ", "PayTable Scroll ", "PayTable Scroll", "Fail",
							currencyName);

				boolean scatterAndWildPayouts = validatePayoutsFromPaytable(report, currencyName, regExpr);
				boolean symbolPayouts = verifyGridPayouts(report, regExpr, currencyName, isoCode);
				if (scatterAndWildPayouts && symbolPayouts) {
					report.detailsAppendNoScreenshot(
							"Verify Paytable payout currency format for selected bet value with the game currency format",
							"Paytable payout verification with the game currency format ",
							"Paytbale payout verification  with the game currency format is done and is correct",
							"Pass");
					log.debug("Paytable currency format: Pass");

				} else {
					report.detailsAppendNoScreenshot(
							"Verify Paytable payout currency format for selected bet value with the game currency format",
							"Paytable payout verification with the game currency format ",
							"Paytable payout verification with the game currency format is done but Failed coz some formats are not matched",
							"Fail");
					log.debug("Paytable currency format: Fail");
				}

				closePaytable(imageLibrary);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to validate normal win amount
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void verifyNormalWinCurrencyFormat(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String regExpr, String isoCode) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Spin")) {
				Thread.sleep(3000);
				imageLibrary.click("Spin");
				Thread.sleep(3000);
				report.detailsAppendFolder("Verify able to click on Spin", "Should be able to click on spin button",
						"Able to click on spin button", "PASS", currencyName);
				System.out.println("Spin clicked");
			} else {
				report.detailsAppend("Verify if able to click on Spin", "Should be able to click on Spin",
						"Not able to click on Spin", "Fail");
			}

			Thread.sleep(2000);

			setChromiumWebViewContext();
			boolean winFormatVerification = verifyRegularExpressionPlayNext(report, regExpr,
					getCurrentWinAmtImg(report, imageLibrary, currencyName), isoCode);
			if (winFormatVerification) {
				System.out.println("Base Game Win Value : PASS");
				log.debug("Base Game Win Value : PASS");

				report.detailsAppendFolder("Base Game", "Win Amt", "Win Amt", "PASS", currencyName);
			} else {
				System.out.println("Base Game Win Value : FAIL");
				log.debug("Base Game Win Value : FAIL");
				report.detailsAppendFolder("Base Game", "Win Amt", "Win Amt", "FAIL", currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to validate big win amount
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void verifyBigWinCurrencyFormat(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String regExpr, String isoCode) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Spin")) {
				Thread.sleep(3000);
				imageLibrary.click("Spin");
				Thread.sleep(3000);
				report.detailsAppendFolder("Verify able to click on Spin", "Should be able to click on spin button",
						"Able to click on spin button", "PASS", currencyName);
				System.out.println();
			} else {
				report.detailsAppendFolder("Verify if able to click on Spin", "Should be able to click on Spin",
						"Not able to click on Spin", "Fail", currencyName);
			}

			Thread.sleep(3000);
			setChromiumWebViewContext();
			/*
			 * boolean bigWinFormatVerification =
			 * cfnlib.verifyRegularExpressionPlayNext(report, regExpr,
			 * cfnlib.verifyBigWin(report, CurrencyName),isoCode);
			 */
			boolean bigWinFormatVerification = verifyRegularExpressionPlayNext(report, regExpr,
					verifyBigWinImg(report, imageLibrary, currencyName), isoCode);
			if (bigWinFormatVerification) {
				System.out.println("Base Game BigWin Value : PASS");
				log.debug("Base Game BigWin Value : PASS");
				report.detailsAppendFolder("Base Game", "Big Win Amt", "Big Win Amt", "PASS", currencyName);
			} else {
				System.out.println("Base Game BigWin Value : FAIL");
				log.debug("Base Game BigWin Value : FAIL");
				report.detailsAppendFolder("Base Game", " Big Win Amt", "Big Win Amt", "FAIL", currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This method is used to validate Bonus/Amazing win amount
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void verifyBonusWinCurrencyFormat(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String regExpr, String isoCode) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Spin")) {
				Thread.sleep(3000);
				imageLibrary.click("Spin");
				Thread.sleep(3000);
				report.detailsAppendFolder("Verify able to click on Spin", "Should be able to click on spin button",
						"Able to click on spin button", "PASS", currencyName);

				Thread.sleep(8000);

				setChromiumWebViewContext();

				boolean winVerification = verifyRegularExpressionPlayNext(report, regExpr,
						verifyBonusWin(report, currencyName), isoCode);
				if (winVerification) {
					report.detailsAppendFolder("verify Bonus win amount currency format in baseScene",
							"Bonus win amount should be in correct currency format",
							"Bonus win amount is in correct currency format", "Pass", currencyName);
					System.out.println("Base Game bonue Win Value : Pass");
					log.debug("Base Game Win Value : Pass");

				} else {
					report.detailsAppendFolder("verify Bonus win amount currency format in baseScene",
							"Bonus win amount should be in correct currency format",
							"Bonus win amount is in incorrect currency format", "Fail", currencyName);
					System.out.println("Base Game bonus Win Value : Fail");
					log.debug("Base Game bonus Win Value : Fail");

				}
			} else {
				report.detailsAppend("Verify if able to click on Spin", "Should be able to click on Spin",
						"Not able to click on Spin", "Fail");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to verify Freespins currency format
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void verifyFreeSpinsCurrencyFormat(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String regExpr, String isoCode, String regExprNoSymbol) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Spin")) {
				Thread.sleep(3000);
				imageLibrary.click("Spin");
				Thread.sleep(3000);
				report.detailsAppendFolder("Verify able to click on Spin", "Should be able to click on spin button",
						"Able to click on spin button", "PASS", currencyName);
				System.out.println("Spin clicked");
			} else {
				report.detailsAppendFolder("Verify if able to click on Spin", "Should be able to click on Spin",
						"Not able to click on Spin", "Fail", currencyName);
			}

			Thread.sleep(20000);

			if (imageLibrary.isImageAppears("FreeSpinEntry")) {
				imageLibrary.click("FreeSpinEntry");
				Thread.sleep(3000);
				report.detailsAppendFolder("Verify able to click on FreeSpin",
						"Should be able to click on Freespin Entry", "Able to click on  Freespin Entry", "PASS",
						currencyName);

			} else {
				report.detailsAppendFolder("Verify if able to click on Freespin Entry",
						"Should be able to click on  Freespin Entry", "Not able to click on Freespin Entry", "Fail",
						currencyName);
			}

			Thread.sleep(10000);

			if (imageLibrary.isImageAppears("FSLetsGo")) {
				Thread.sleep(3000);
				imageLibrary.click("FSLetsGo");
				Thread.sleep(3000);
				report.detailsAppendFolder("Verify able to click on FreeSpin LetsGo",
						"Should be able to click on FreeSpinLetsGo", "Able to click on FreeSpin LetsGo", "PASS",
						currencyName);
				System.out.println();
			} else {
				report.detailsAppendFolder("Verify if able to click on FreeSpin LetsGo",
						"Should be able to click on FreeSpin LetsGo", "Not able to click on FreeSpin LetsGo", "Fail",
						currencyName);
			}

			setChromiumWebViewContext();

			boolean winFormatVerificationINFS = verifyRegularExpressionPlayNext(report, regExpr,
					verifyFSCurrentWinAmt(report, currencyName), isoCode);
			if (winFormatVerificationINFS) {
				report.detailsAppendFolder("verify win amount currency format in FreeSpins",
						"Win amount should be in correct currency format", "Win amount is in correct currency format",
						"Pass", currencyName);
				System.out.println("Freespins Win Value : Pass");
				log.debug("Freespins Win Value : Pass");
			} else {
				report.detailsAppendFolder("verify win amount currency format in FreeSpins",
						"Win amount should be in correct currency format", "Win amount is in incorrect currency format",
						"Fail", currencyName);

				System.out.println("Freespins  Win Value : Fail");
				log.debug("Freespins Win Value : Fail");
			}

			report.detailsAppend("Verify able to FreeSpin Normal Win", "Should be able to See FS Normal Win",
					"Able to see Free Spine Normal win", "PASS");
			Thread.sleep(12000);

			// method is used to get the current big win and check the currency format
			boolean bigWinFormatVerificationINFS = verifyRegularExpressionPlayNext(report, regExpr,
					verifyFreeSpinBigWin(report, currencyName), isoCode);
			if (bigWinFormatVerificationINFS) {
				report.detailsAppendFolder("verify bigwin amount currency format in FreeSpins",
						"BigWin amount should be in correct currency format",
						"BigWin amount is in correct currency format", "Pass", currencyName);

				System.out.println("Free Spins BigWin Value : Pass");
				log.debug("Free Spins BigWin Value : Pass");

			} else {
				report.detailsAppendFolder("verify bigwin amount currency format in FreeSpins",
						"BigWin amount should be in correct currency format",
						"BigWin amount is in correct currency format", "Fail", currencyName);

				System.out.println("Free Spins BigWin Value : Fail");
				log.debug("Free Spins BigWin Value : Fail");
			}

			report.detailsAppend("Verify able to FreeSpin Big  Win", "Should be able to See FS Big Win",
					"Able to see Free Spine Bigwin", "PASS");
			Thread.sleep(80000);

			if (TestPropReader.getInstance().getProperty("IsBonusAvailable").equalsIgnoreCase("yes")) {
				// Base Game Bonus
				if (checkAvilabilityofElement("BonusFeatureImage")) {
					Thread.sleep(8000);
					report.detailsAppendFolder("Base Game", " Bonus Feature is Available",
							" Bonus Feature is Available", "PASS", "" + currencyName);
					System.out.println("Bonus Feature is Available");
					log.debug("Bonus Feature is Available");

					// method is used to get the click , get the bonus text,verifies bonus
					// summary screen in base game and check the currency format
					boolean bonus = verifyRegularExpressionUsingArrays(report, regExprNoSymbol,
							bonusFeatureClickandGetText(report, currencyName));
					System.out.println("Bonus Game : PASS");
					log.debug("Bonus Game : PASS");

					Thread.sleep(2000);
				} else {
					report.detailsAppendFolder("Base Game", " Bonus Feature is Not Available",
							" Bonus Feature is Not Available", "FAIL", currencyName);
					System.out.println("Bonus Game : FAIL");
					log.debug("Bonus Game : FAIL");
				}
			} else {
				System.out.println("Bonus is not Available");
				log.debug("Bonus is not Available");
				report.detailsAppendFolder("Base Game", " Bonus Feature is not  Available",
						" Bonus Feature is not Available", "FAIL ", "" + currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to verify Free spins summary
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void verifyFreeSpinsSummaryScreen(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String currencyName, String regExpr) {
		try {
			webdriver.context("NATIVE_APP");
			if (waitTillFreespinSummary(report, imageLibrary, currencyName)) {
				boolean fsSummaryScreen = verifyRegularExpression(report, regExpr,
						func_GetText("FSSummaryScreenWinAmt"));

				if (fsSummaryScreen) {
					report.detailsAppendNoScreenshot("Verify able to see FreeSpin Summary",
							"Should be able to see FreeSpin Summary with Currency format",
							"Able to see FreeSpin Summary with Currency format", "Pass");
				/*	imageLibrary.click("FreeSpinSum");
					report.detailsAppendFolder("Verify able to click on FreeSpin Summary",
							"Should be able to click on  FreeSpin Summary", "Able to click on FreeSpin Summary", "Pass",currencyName);*/
				}
			} else {
				report.detailsAppendNoScreenshot("Verify if able to click on  FreeSpin Summary ",
						"Should be able to click on  FreeSpin Summary with Currency format",
						"Not able to click on  FreeSpin Summary with Currency format", "Fail");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to verify Free games entry screen and free games info
	 * text currency format
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void verifyFreeGamesEntryScreen(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String regExpr, String isoCode) {
		try {
			webdriver.context("NATIVE_APP");
			Thread.sleep(3000);
			if (imageLibrary.isImageAppears("FGPlayLater")) {

				report.detailsAppendFolder("Verify freegames entry screen", "freegames entry screen should display",
						"freegames entry screen is displaying", "Pass", currencyName);

				boolean isfreeGameEntryInfoVisible = verifyRegularExpressionPlayNext(report, regExpr,
						freeGameEntryInfo(imageLibrary, "fgInfotxt"), isoCode);
				if (isfreeGameEntryInfoVisible) {
					System.out.println("Free Games Entry Screen Info Icon Text Validation : Pass");
					log.debug("Free Games Entry Screen Info Icon Text Validation : Pass");
					report.detailsAppendFolder("Verify freegames info currency format",
							"freegames info currency should display with correct currency format",
							"freegames info currency displaying with correct currency format", "Pass", currencyName);
				} else {
					System.out.println("Free Games Entry Screen Info Icon Text Validation : Fail");
					log.debug("Free Games Entry Screen Info Icon Text Validation : Fail");
					report.detailsAppendFolder("Verify freegames info currency format",
							"freegames info currency should display with correct currency format",
							"freegames info currency displaying with incorrect currency format", "Fail", currencyName);
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This method is used to verify and click on free games play later button
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void freeGamesPlayLater(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("FGPlayLater")) {
				report.detailsAppendFolder("Verify FG Play Later button visible", "Play Later button Should be visible",
						"FG Play Later button is visible", "Pass", currencyName);
				Thread.sleep(2000);
				imageLibrary.click("FGPlayLater");
				Thread.sleep(2000);
			} else {
				report.detailsAppendFolder("Verify FG Play Later button visible", "Play Later button Should be visible",
						"FG Play Later button is not visible", "Fail", currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to verify and click on free games play now button
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void freeGamesPlayNow(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("FGPlayNow")) {
				report.detailsAppendFolder("Verify FG Play Now button visible", "Play Now button Should be visible",
						"FG Play Now button is visible", "Pass", currencyName);
				Thread.sleep(2000);
				imageLibrary.click("FGPlayNow");
				Thread.sleep(2000);
			} else {
				report.detailsAppendFolder("Verify FG Play Now button visible", "Play Now button Should be visible",
						"FG Play Now button is not visible", "Fail", currencyName);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to verify delete button on FG's entry screen
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void freeGamesDeleteEntryScreen(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			Thread.sleep(1000);
			if (imageLibrary.isImageAppears("FGDelete")) {
				imageLibrary.click("FGDelete");
				report.detailsAppendFolder("Verify Game launched after refresh to check Discard FG Offer",
						"Game should be launched", "Game is launched", "Pass", currencyName);

				if (imageLibrary.isImageAppears("FGDiscard")) {
					report.detailsAppendFolder("Check that Discard Offer screen is displaying",
							"Discard Offer screen should display", "Discard Offer screen is displaying", "Pass",
							currencyName);

					confirmDiscardOffer(imageLibrary);

				} else {
					report.detailsAppendFolder("Check that Discard Offer screen is displaying",
							"Discard Offer screen should display", "Discard Offer screen is not displaying", "Fail",
							currencyName);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used for free games delete on base game screen
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void freeGamesDeleteBaseGameScreen(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String currencyName) {
		try {

			webdriver.context("NATIVE_APP");

			if (imageLibrary.isImageAppears("FreeGameBaseDiscard")) {
				imageLibrary.click("FreeGameBaseDiscard");
				report.detailsAppendFolder("Verify Discard FG Offer on base scene",
						"Verify Discard FG Offer on base scene", "Verify Discard FG Offer on base scene", "Pass",
						currencyName);

				if (imageLibrary.isImageAppears("FreeGameDiscardOffer")) {
					report.detailsAppendFolder("Check that Discard Offer screen is displaying",
							"Discard Offer screen should display", "Discard Offer screen is displaying", "Pass",
							currencyName);

					imageLibrary.click("FreeGameDiscardOffer");
				} else {
					report.detailsAppendFolder("Check that Discard Offer screen is displaying",
							"Discard Offer screen should display", "Discard Offer screen is not displaying", "Fail",
							currencyName);
				}

			} else {
				report.detailsAppendFolder("Verify Game launchaed after refresh to check Discard FG Offer ",
						"Game should be launched", "Game is not launched", "Fail", currencyName);
			}
			Thread.sleep(5000);
			/*clickBaseSceneDiscardButton();
			report.detailsAppendFolder("Verify Discard offer is visible on baseScene",
					"Discard offer should be visible on baseScene", "Discard offer is visible on baseScene", "Pass",
					currencyName);

			boolean isOfferDiscarded = confirmDiscardOffer();
			if (isOfferDiscarded)
				report.detailsAppendFolder(
						"Check that Offer is discarded successfully and Free Games Summary Screen should display",
						"Offer should be discarded successfully and Free Games Summary Screen should display",
						"Offer is discarded and Free Games Summary Screen is displaying", "Pass", currencyName);
			else
				report.detailsAppendFolder(
						"Check that Offer is discarded successfully and Free Games Summary Screen should display",
						"Offer should be discarded successfully and Free Games Summary Screen should display",
						"Offer is not discarded and Free Games Summary Screen is not displaying", "Fail", currencyName);*/

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used verify FG Summary screen currency format
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param regExpr
	 */
	public void verifyFGSummaryScreen(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String regExpr, String isoCode) {
		try {

			setChromiumWebViewContext();
			boolean FGSummaryWonAmount = verifyRegularExpressionPlayNext(report, regExpr,
					GetConsoleText("return " + xpathMap.get("FGSummaryAmount")), isoCode);
			if (FGSummaryWonAmount) {
				System.out.println("Free Game Summary won Amount : Pass");
				log.debug("Free Game Summary won Amount : Pass");
				report.detailsAppendFolder("Verify freegames summary currency when win occurs ",
						"freegames summary win should display in correct currency format",
						"freegames summary win displaying in correct currency format", "Pass", currencyName);
			} else {
				System.out.println("Free Game Summary won Amount : Pass");
				log.debug("Free  Game Summary won Amount : Fail");
				report.detailsAppendFolder("Verify freegames summary currency when win occurs ",
						"freegames summary win should display with correct currency format",
						"freegames summary win displaying in incorrect currency format", "Fail", currencyName);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/*
	 * public void clickOnBaseSceneContinueButton(Mobile_HTML_Report report,
	 * ImageLibrary imageLibrary) { try {
	 * if(imageLibrary.isImageAppears("NFDButton")) {
	 * System.out.println("NFD button is visible");log.debug("NFD button is visible"
	 * ); Thread.sleep(2000); imageLibrary.click("NFDButton"); Thread.sleep(3000);
	 * report.detailsAppendNoScreenshot("Verify Continue button is visible ",
	 * "Continue buttion is visible", "Continue button is visible", "Pass");
	 * 
	 * 
	 * } else { System.out.println("NFD button is not visible");log.
	 * debug("NFD button is not visible");
	 * report.detailsAppendNoScreenshot("Verify Continue button is visible ",
	 * "Continue buttion is visible", "Continue button is not visible", "Fail"); } }
	 * catch (Exception e) { log.error(e.getMessage(), e); } }
	 */
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

	public void checkSpinReelLanding(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		try {

			if (imageLibrary.isImageAppears("Spin")) {
				imageLibrary.click("Spin");
				Thread.sleep(1650);
				report.detailsAppendFolder("Verify Spin reel landing ", "Reels should not land at the same time",
						"Reels did not land at the same time", "Pass", languageCode);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public boolean closePaytable() {
		boolean isPaytbaleClose = false;

		try {
			if (elementVisible_Xpath(xpathMap.get("PaytableClose"))) {
				func_Click(xpathMap.get("PaytableClose"));
				Thread.sleep(2000);
				isPaytbaleClose = true;

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isPaytbaleClose;
	}

	public void verifyValueAddsNavigation(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,
			String PlayerProtectionNavigation, String CashCheckNavigation, String HelpNavigation,
			String PlayCheckNavigation, String RTP, String playCheckTranscations, String playCheckLast30days) {
		try {
			setChromiumWebViewContext();
			Thread.sleep(1000);
			if (func_Click(xpathMap.get("HelpMenu"))) {
				// if (func_Click("HelpMenu")) {
				Thread.sleep(1000);
				report.detailsAppendFolder("Verify if menu on top bar is open", "Menu on top bar should be open",
						"Menu on top bar is open", "Pass", languageCode);
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
					verifyHelpNavigation(report, imageLibrary, languageCode,RTP);
					Thread.sleep(2000);
				}

				if (PlayCheckNavigation.equalsIgnoreCase("yes")) {
					verifyPlayCheckNavigation(report, imageLibrary, languageCode,playCheckTranscations,playCheckLast30days);
					Thread.sleep(5000);
				}
				/*
				 * if (TransactionHistoryNavigation.equalsIgnoreCase("yes")) {
				 * verifyCashCheckNavigation(report, imageLibrary, languageCode);
				 * Thread.sleep(2000); }
				 */

				funcFullScreen();
				if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
					clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
				}
			}

		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
	}

	public void verifyPlayerProtectionNavigation(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String languageCode) {
		setChromiumWebViewContext();
		String gameurl = webdriver.getCurrentUrl();

		try {
			func_Click(xpathMap.get("HelpMenu"));
			Thread.sleep(1000);
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("PlayerProtection")) {
				Thread.sleep(1000);
				report.detailsAppendNoScreenshot("Verify Player protection is visible",
						"Player protection should be visible", "Player protection is visible", "Pass");

				imageLibrary.click("PlayerProtection");
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			}

			else if (elementVisible_Xpath(xpathMap.get("PlayerProtection"))) {
				report.detailsAppendNoScreenshot("Verify Player protection is visible",
						"Player protection should be visible", "Player protection is visible", "Pass");
				setChromiumWebViewContext();
				func_Click(xpathMap.get("PlayerProtection"));
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			} else {
				report.detailsAppendNoScreenshot("Verify Player protection is visible",
						"Player protection should be visible", "Player protection is not visible", "Fail");

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void verifyCashCheckNavigation(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		setChromiumWebViewContext();
		String gameurl = webdriver.getCurrentUrl();

		try {
			func_Click(xpathMap.get("HelpMenu"));
			Thread.sleep(1000);

			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("CashCheck")) {
				Thread.sleep(1000);
				report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible",
						"Cash Check is visible", "Pass");

				imageLibrary.click("CashCheck");
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			} else if (elementVisible_Xpath(xpathMap.get("CashCheck"))) {
				report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible",
						"Cash Check is visible", "Pass");
				setChromiumWebViewContext();
				func_Click(xpathMap.get("CashCheck"));
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			} else {
				report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible",
						"Cash Check is not visible", "Fail");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void verifyPlayCheckNavigation(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,String PlayCheckTransactions,String PlayCheckLast30days) {
		String gameurl = webdriver.getCurrentUrl();
		setChromiumWebViewContext();
		try {
			func_Click(xpathMap.get("HelpMenu"));
			Thread.sleep(1000);
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("PlayCheck")) {
				Thread.sleep(1000);
				report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible",
						"Play Check is visible", "Pass");
				imageLibrary.click("PlayCheck");
				Thread.sleep(2000);
				setChromiumWebViewContext();
				if(PlayCheckTransactions.equalsIgnoreCase("yes"))
				{
					report.detailsAppendFolder("Verify Play Check", "Play Check transcations should be visible",
							"Play Check transcations is visible", "Pass",languageCode);
					boolean ele = webdriver.findElement(By.xpath(xpathMap.get("playcheckView"))).isDisplayed();
					if(ele)
					{
						func_Click(xpathMap.get("playcheckView"));
						report.detailsAppendFolder("Verify Play Check transcations", "Play Check transcations should be visible",
								"Play Check transcations is visible", "Pass",languageCode);
					}
				
				}
				if(PlayCheckLast30days.equalsIgnoreCase("yes"))
				{
					boolean ele = webdriver.findElement(By.xpath(xpathMap.get("playcheckCalender"))).isDisplayed();
					if(ele)
					{
						func_Click(xpathMap.get("playcheckCalender"));
						boolean ele1 = webdriver.findElement(By.xpath(xpathMap.get("playChecklast30days"))).isDisplayed();
						if(ele1)
						{
							func_Click(xpathMap.get("playChecklast30days"));
							report.detailsAppendNoScreenshot("Verify Play Check", "Play Check transcations for last 30 days",
									"Play Check transcations for last 30 days","Pass");
						}
					}
				
				}
				checkpagenavigation(report, languageCode, gameurl);
			} else if (elementVisible_Xpath(xpathMap.get("PlayCheck"))) {
				report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible",
						"Play Check is visible", "Pass");
				func_Click(xpathMap.get("PlayCheck"));
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			} else {
				report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible",
						"Play Check is not visible", "Fail");
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
	public void verifyTransactionHistory(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		String gameurl = webdriver.getCurrentUrl();

		try {
			func_Click(xpathMap.get("HelpMenu"));

			Thread.sleep(1000);
			if (imageLibrary.isImageAppears("TransactionHistory")) {
				Thread.sleep(1000);
				report.detailsAppendNoScreenshot("Verify Transaction History is visible",
						"TransactionHistory should be visible", "Transaction History is visible", "Pass");
				imageLibrary.click("TransactionHistory");
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			} else if (elementVisible_Xpath(xpathMap.get("TransactionHistory"))) {
				report.detailsAppendNoScreenshot("Verify Transaction History is visible",
						"Transaction History should be visible", "Transaction History is visible", "Pass");
				func_Click(xpathMap.get("TransactionHistory"));
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			} else {
				report.detailsAppendNoScreenshot("Verify Transaction History is visible",
						"Transaction History should be visible", "Transaction History is not visible", "Fail");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void validateSessionReminderBaseScene(Mobile_HTML_Report report, String userName, ImageLibrary imageLibrary,
			String languageCode, String SessionReminderUserInteraction, String SessionReminderContinue,
			String SessionReminderExitGame) {
		try {
			setChromiumWebViewContext();

			CommonUtil util = new CommonUtil();
			String gameurl = webdriver.getCurrentUrl();

			String periodValue = xpathMap.get("sessionReminderDurationInSec");
			String resetPeriodValue = xpathMap.get("resetSessionReminderValue");

			// assigning session reminder
			boolean sessionReminderAssigned = util.setSessionReminderOnAxiom(userName, periodValue);
			loadGame(gameurl);
			/*
			 * if ("Yes".equalsIgnoreCase(XpathMap.get("NFD"))) {
			 * clickOnBaseSceneContinueButton(report, imageLibrary); }
			 */
			log.debug("Session reminder is set for " + periodValue + " secs");

			if (sessionReminderAssigned && sessionReminderPresent()) {
				report.detailsAppendFolder("Verify Session Reminder must be present",
						"Session Reminder must be present", "Session Reminder is present", "Pass", languageCode);

				if (SessionReminderUserInteraction.equalsIgnoreCase("yes")) {
					// clicking else where on the screen
					closeOverlay();
					report.detailsAppendFolder(
							"Verify when Session Reminder is present and player should not be able to interact with any part of the game",
							"Player should not not be able to interact with any part of the game",
							"Unable to interact with the game", "Pass", languageCode);
					Thread.sleep(2000);
				}

				// Ensure that the continue button takes you back to the game
				if (SessionReminderContinue.equalsIgnoreCase("yes")) {
					selectContinueSession();

					if (imageLibrary.isImageAppears("Spin")) {
						report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",
								" Session Reminder dialog box is closed and game should be visible",
								" Session Reminder dialog box is closed and game is visible", "Pass", languageCode);
						Thread.sleep(2000);
					} else {
						report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",
								" Session Reminder dialog box is closed and game should be visible",
								" Session Reminder dialog box is closed and game is not visible", "Fail", languageCode);
					}
				}
			} else {
				report.detailsAppendFolder("Verify Session Reminder must be present",
						"Session Reminder must be present", "Session Reminder is not present", "Fail", languageCode);
				log.debug("Session reminder is not visible on screen");
				Thread.sleep(2000);
			}

			// Ensure that the log out button takes you to the lobby
			if (SessionReminderExitGame.equalsIgnoreCase("yes")) {
				// assigning session reminder
				sessionReminderAssigned = util.setSessionReminderOnAxiom(userName, periodValue);
				loadGame(gameurl);
				if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
					clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
				}

				if (sessionReminderAssigned && sessionReminderPresent()) {
					func_Click(xpathMap.get("ExitGameSessrionReminder"));
					Thread.sleep(12000);
					report.detailsAppendNoScreenshot("Verify Session Reminder log out/Exit game button",
							"Log out button shoul take user to the lobby", "Log out button takes user to the lobby",
							"Pass");

					checkpagenavigation(report, languageCode, gameurl);
					if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
						clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
					}
				}
			} else {
				report.detailsAppendFolder("Verify Session Reminder must be present to verify exit game scenario",
						"Session Reminder must be present", "Session Reminder is not present", "Fail", languageCode);
			}

			// reset to higher duarion for session reminder
			boolean resetSessionReminderDUration = util.setSessionReminderOnAxiom(userName, resetPeriodValue);
			if (resetSessionReminderDUration) {
				log.debug("Session reminder has been reset to 10hr in order to avoid pop up");
			} else {
				log.debug("Unable to reset Session reminder");
			}
			// relaunching the game to set the above reset session reminder duration to
			// higher value
			loadGame(gameurl);
			if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
				clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
			}

		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
	}

	public void validateBonusFundsNotificationBaseScene(Mobile_HTML_Report report, String userName,
			ImageLibrary imageLibrary, String languageCode, String closeBtnEnabledCMA) {
		try {
			if (closeBtnEnabledCMA.equalsIgnoreCase("yes")) {
				CommonUtil util = new CommonUtil();
				String gameurl = webdriver.getCurrentUrl();

				if (util.enableBonusFundsNotification(userName)) {
					// Relaunching the game to verify Bonus funds notification
					loadGame(gameurl);
					if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
						clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
					}

					// Ensure the Bonus Funds notification appears
					if (isBonusFundsNotificationVisible()) {
						report.detailsAppendFolder("Verify if Bonus funds notification is visible ",
								"Bonus funds notification should display", "Bonus funds notification is displayed",
								"Pass", languageCode);
					} else {
						report.detailsAppendFolder("Verify if Bonus funds notification is visible ",
								"Bonus funds notification should display", "Bonus funds notification is not displayed",
								"Fail", languageCode);
					}

					// Dismiss the notification by clicking the close button
					if (isBonusFundsNotificationVisible()) {

						func_Click(xpathMap.get("closeBonusFundsNotification"));
						if (!(isBonusFundsNotificationVisible())) {
							report.detailsAppendFolder(
									"Verify Bonus funds notification can be dismissed by clicking on close button",
									"Bonus funds notification should dismiss by clicking on close button",
									"Bonus funds notification is dismissed by clicking the close button", "Pass",
									languageCode);
							report.detailsAppendFolder(
									"Verify Bonus funds notification can be dismissed after 1 second of gameplay",
									"Bonus funds notification should dismiss after 1 second of gameplay",
									"Bonus funds notification is dismissed after 1 second of gameplay", "Pass",
									languageCode);
						} else {
							report.detailsAppendFolder(
									"Verify Bonus funds notification can be dismissed by clicking on lcose button",
									"Bonus funds notification should dismiss by clicking on close button",
									"Bonus funds notification is dismissed by clicking the close button", "Fail",
									languageCode);
							report.detailsAppendFolder(
									"Verify Bonus funds notification can be dismissed after 1 second of gameplay",
									"Bonus funds notification should dismiss after 1 second of gameplay",
									"Bonus funds notification is not dismissed after 1 second of gameplay", "Fail",
									languageCode);
						}
					}

					// After dismissing the Notification, ensure you can spin with no errors thrown
					if (imageLibrary.isImageAppears("Spin")) {
						Thread.sleep(2000);
						imageLibrary.click("Spin");
						Thread.sleep(2000);
						report.detailsAppendFolder(
								"Verify After dismissing the Notification, ensure you can spin with no errors thrown",
								"No error should display", "No errors found", "Pass", languageCode);
					}

					// Clicking the hyperlink on the Notification
					// Refresh the game
					webdriver.navigate().refresh();
					Thread.sleep(10000);
					report.detailsAppendFolder("Verify Bonus funds notification is visible after refresh",
							"Bonus funds notification should be visible after refresh",
							"Bonus funds notification is visible after refresh", "Pass", languageCode);

					verifyBonusFundsNotificationNavigation(report, languageCode);

					loadGame(gameurl);
					if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
						clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
					}
					Thread.sleep(2000);
					if (imageLibrary.isImageAppears("Spin")) {
						report.detailsAppendFolder(
								"Verify player can navigate back to the game after navigating to Terms and Conditions",
								"Base scene should be visible", "Base scene is visible", "Pass", languageCode);
					} else {
						report.detailsAppend(
								"Unable to assign Bonus funds notification / CMA to player hence skipping the test cases",
								"", "", "");
					}
				}
			}

		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}

	}

	public boolean isBonusFundsNotificationVisible() {
		boolean isVisible = false;
		WebDriverWait Wait = new WebDriverWait(webdriver, 20);
		try {
			setChromiumWebViewContext();
			Wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(xpathMap.get("isBonusFundsNotificationVisible"))));
			if (elementVisible_Xpath(xpathMap.get("isBonusFundsNotificationVisible"))) {
				isVisible = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isVisible;
	}

	public void verifyBonusFundsNotificationNavigation(Mobile_HTML_Report report, String languageCode) {
		String gameurl = webdriver.getCurrentUrl();

		try {
			Wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(xpathMap.get("bonusFundsNotificationTandC"))));
			if (elementVisible_Xpath(xpathMap.get("bonusFundsNotificationTandC"))) {
				report.detailsAppendNoScreenshot(
						"Verify Bonus Funds notification Terms and Conditions and its navigation",
						"Bonus Funds notification Terms and Conditions should be visible",
						"Bonus Funds notification Terms and Conditions is visible", "Pass");

				func_Click(xpathMap.get("bonusFundsNotificationTandC"));
				Thread.sleep(2000);
				checkpagenavigation(report, languageCode, gameurl);
			} else {
				report.detailsAppendNoScreenshot(
						"Verify Bonus Funds notification Terms and Conditions and its navigation",
						"Bonus Funds notification Terms and Conditions should be visible",
						"Bonus Funds notification Terms and Conditions is not visible", "Fail");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public boolean verifyRegularExpression(Mobile_HTML_Report report, String regExp, String Text) {
		String newText = null;
		boolean regexp = false;
		try {
			Thread.sleep(2000);
			
			if(Text.contains("kr"))
				newText=Text;
			else
				newText = Text.replaceAll("[a-zA-Z:]", "").trim();
			if (newText.matches(regExp)) {
				log.debug("Compared with Reg Expression.Currency is in correct format: " + newText);
				regexp = true;
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return regexp;
	}

	public void selectContinueSession() {
		try {
			setChromiumWebViewContext();
			webdriver.findElement(By.xpath(xpathMap.get("SessionContinue"))).click();
			log.debug("clicked on continue in session reminder dialog box");
		} catch (Exception e) {
			log.info("Unable to click on session continue ", e);
			log.error(e.getMessage(), e);
		}
	}

	public void checkSpinStopBaseScene(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,
			String checkSpinStopBaseSceneUsingCoordinates) {
		try {

			if (imageLibrary.isImageAppears("Spin")) {

				imageLibrary.click("Spin");
				Thread.sleep(1750);
				report.detailsAppendFolder("Verify Spin button status ", "Spin button should not have stop button",
						"Spin button does not have stop button", "Pass", languageCode);
				Thread.sleep(3000);

				if (checkSpinStopBaseSceneUsingCoordinates.equalsIgnoreCase("yes")) {
					imageLibrary.click("Spin");
					Thread.sleep(1000);
					ClickByCoordinates("return " + xpathMap.get("clickOnReelX"),
							"return " + xpathMap.get("clickOnReelY"));

					report.detailsAppendFolder(
							"Verify  clicking or tapping on the reels does not act as a Spin/Stop or Slam stop by stopping the reels spin ",
							"Spin button should not resolve", "Spin button does not resolve", "Pass", languageCode);
				}
			}

		} catch (Exception e) {
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

	/**
	 * This method is used to launch the game and click on NFD button
	 * 
	 * @author pb61055
	 * @param url
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @return
	 */
	public boolean loadGameAndClickContinueBtn(String url, Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String currencyName) {
		boolean isGameLaunch = false;
		Wait = new WebDriverWait(webdriver, 120);
		try {
			setChromiumWebViewContext();
			webdriver.navigate().to(url);
			Thread.sleep(8000);
			funcFullScreen();
			Thread.sleep(3000);
			if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
				clickOnBaseSceneContinueButton(report, imageLibrary, currencyName);
			}
			Thread.sleep(2000);
			log.debug("game loaded  ");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.debug("Exception occur in loadgame()");
		}
		return isGameLaunch;
	}

	/**
	 * This method used to spin until free spins are triggered
	 * 
	 * @author pb61055
	 * @param imageLibrary
	 */
	public void spinClick(ImageLibrary imageLibrary) {
		try {
			webdriver.context("NATIVE_APP");

			if (imageLibrary.isImageAppears("Spin")) {
				imageLibrary.click("Spin");
			}

			Thread.sleep(12000);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to verify free spins entry screen
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 */
	public void freeSpinsEntryScreen(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,
			String FreeSpinTransitionBaseGameToFeature) {
		try {
			webdriver.context("NATIVE_APP");
			report.detailsAppendFolder("Verify Free Spins Entry screen", "Free spin entry screen should be visible",
					"Free spin entry screen is visible", "Pass", languageCode);

			if ("Yes".equalsIgnoreCase(xpathMap.get("isFSHaveMultipleOptions"))) {

				SelectFreeSpinOption(report, imageLibrary);
				Thread.sleep(2000);
				// Free Spins - Transition from Base Game to Feature
				if (FreeSpinTransitionBaseGameToFeature.equalsIgnoreCase("yes")) {
					report.detailsAppendFolder("Verify Free Spins Transition from Base Game to Feature",
							"Player should be able to click on Free spin option and enter free spins",
							"Player is able to click on Free spin option and enter free spins", "Pass", languageCode);
				}
			}
			Thread.sleep(5000);

			// imageLibrary.waitTillImageVisible("tapToContinueFreespins");

			if (imageLibrary.isImageAppears("tapToContinueFreespins"))
			{
				report.detailsAppendFolder("Free Spins", "Free Spins scene",
						"Free Spins scene", "Pass", languageCode);
				imageLibrary.click("tapToContinueFreespins");
				Thread.sleep(3000);
				
				report.detailsAppendFolder("Free Spins", "Should be landed on Free Spins scene",
						"Landed on Free Spins Scene", "Pass", languageCode);
				// Thread.sleep(3000);
				// report.detailsAppendFolder("Free Spins", "Free Spins scene", "Free Spins
				// scene", "Pass", languageCode);
			}
			closeOverlay();
			report.detailsAppendNoScreenshot("Verify Quick spin", "Quick Spin", "Quick spin", "Pass");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to select FS option
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 */
	public void SelectFreeSpinOption(Mobile_HTML_Report report, ImageLibrary imageLibrary) {
		try {
			webdriver.context("NATIVE_APP");

			if (imageLibrary.isImageAppears("FSOption1")) {
				imageLibrary.click("FSOption1");
			}
		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}

	}

	public void netPositioninFSAfterRefresh(Mobile_HTML_Report report, String languageCode) {
		try {
			setChromiumWebViewContext();
			String netPosition = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();

			String netPositionValue = netPosition.replaceAll("[^0-9.-]", "").trim();
			if (netPositionValue.equals("0.00")) {
				report.detailsAppendNoScreenshot("Verify net position is 0.00 after refresh in Freespins",
						"Net position should be 0.00", "Net position is 0.00", "Pass");
			} else {
				report.detailsAppendFolder("Verify net position is 0.00 after refresh in Freespins",
						"Net position should be 0.00", "Net position is not 0.00", "Fail", languageCode);
			}
		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
	}

	public void checkFSRefresh(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,
			String NetPositionInFS,String BounsFundsNotification,String freeSpinUserName) {
		try {
			
			setChromiumWebViewContext();
			CommonUtil util = new CommonUtil();
			
			if(BounsFundsNotification.equalsIgnoreCase("yes"))
			{
				if (util.enableBonusFundsNotification(freeSpinUserName)) {
					
					log.debug("CMA assigned");
				}
			}
				
			webdriver.navigate().refresh();
			Thread.sleep(8000);

			
			// Ensure the Bonus Funds notification appears
			if (isBonusFundsNotificationVisible()) {
				report.detailsAppendFolder("Verify if Bonus funds notification is visible ",
						"Bonus funds notification should display", "Bonus funds notification is displayed", "Pass",
						languageCode);
			} else {
				report.detailsAppendFolder("Verify if Bonus funds notification is visible ",
						"Bonus funds notification should display", "Bonus funds notification is not displayed",
						"Fail", languageCode);
			}
			
		
			webdriver.context("NATIVE_APP");

			if (imageLibrary.isImageAppears("FSRefreshContinueBTN")) {

				report.detailsAppendFolder("FS continue button after refresh",
						"FS continue button should be displayed after refresh",
						"Free Spin Scene is visible after refresh", "Pass", languageCode);
				imageLibrary.click("FSRefreshContinueBTN");
				Thread.sleep(3000);
				report.detailsAppendFolder("Verify Free Spin refresh",
						"Free Spin Scene should be visible after refresh", "Free Spin Scene is visible", "Pass",
						languageCode);
			}

			if (NetPositionInFS.equalsIgnoreCase("yes")) {
				netPositioninFSAfterRefresh(report, languageCode);
			}
		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
	}

	/**
	 * This method is used for check reel landing in FS and bonus
	 * 
	 * @author pb61055
	 * @param report
	 * @param currencyName
	 */
	public void checkSpinReelLandingFeature(Mobile_HTML_Report report, String currencyName) {
		try {
			report.detailsAppendFolder("Verify Spin reel landing", "Reels should not land at the same time",
					"Reels did not land at the same time", "Pass", currencyName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void validateSessionReminderFSScene(Mobile_HTML_Report report, String userName, ImageLibrary imageLibrary,
			String languageCode, String SessionReminderUserInteraction, String SessionReminderContinue,
			String SessionReminderExitGame) {
		try {
			setChromiumWebViewContext();
			CommonUtil util = new CommonUtil();
			String gameurl = webdriver.getCurrentUrl();

			String periodValue = xpathMap.get("sessionReminderDurationInSec");
			String resetPeriodValue = xpathMap.get("resetSessionReminderValue");

			// assigning session reminder
			boolean sessionReminderAssigned = util.setSessionReminderOnAxiom(userName, periodValue);
			loadGame(gameurl);
			Thread.sleep(5000);
			/*
			 * if ("Yes".equalsIgnoreCase(XpathMap.get("NFD"))) {
			 * clickOnBaseSceneContinueButton(report, imageLibrary); }
			 */
			log.debug("Session reminder is set for " + periodValue + " secs");

			if (sessionReminderAssigned && sessionReminderPresent()) {
				report.detailsAppendFolder("Verify Session Reminder must be present",
						"Session Reminder must be present", "Session Reminder is present", "Pass", languageCode);

				// Ensure that the continue button takes you back to the game
				if (SessionReminderContinue.equalsIgnoreCase("yes")) {
					selectContinueSession();
					webdriver.context("NATIVE_APP");
					if (imageLibrary.isImageAppears("FSRefreshContinueBTN")) {
						imageLibrary.click("FSRefreshContinueBTN");
						Thread.sleep(2000);
						report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",
								" Session Reminder dialog box is closed and game should be visible",
								" Session Reminder dialog box is closed and game is visible", "Pass", languageCode);
						Thread.sleep(2000);
					} else {
						report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",
								" Session Reminder dialog box is closed and game should be visible",
								" Session Reminder dialog box is closed and game is not visible", "Fail", languageCode);
					}
				}
			} else {
				report.detailsAppendFolder("Verify Session Reminder must be present",
						"Session Reminder must be present", "Session Reminder is not present", "Fail", languageCode);
				log.debug("Session reminder is not visible on screen");
				Thread.sleep(2000);
			}
			setChromiumWebViewContext();
			// reset to higher duarion for session reminder
			boolean resetSessionReminderDUration = util.setSessionReminderOnAxiom(userName, resetPeriodValue);
			if (resetSessionReminderDUration) {
				log.debug("Session reminder has been reset to 10hr in order to avoid pop up");
			} else {
				log.debug("Unable to reset Session reminder");
			}
			// relaunching the game to set the above reset session reminder duration to
			// higher value
			loadGame(gameurl);
			Thread.sleep(5000);

			if (imageLibrary.isImageAppears("FSRefreshContinueBTN")) {
				imageLibrary.click("FSRefreshContinueBTN");
				closeOverlayForLVC();
			}

		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
	}

	/**
	 * This method is used to verify Free Spins- Transition from Feature to Base
	 * Game and refresh during transition
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 */
	public void freeSpinsToBaseGameTransitions(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String languageCode, String FreeSpinTransitionFeatureToBaseGame,
			String FreeSpinTransitionFeatureToBaseGameRefresh) {
		try {
			setChromiumWebViewContext();
			// Free Spins - Transition from Feature to Base Game
			if (FreeSpinTransitionFeatureToBaseGame.equalsIgnoreCase("yes")) {
				Thread.sleep(5000);
				if (waitTillFreespinSummary(report, imageLibrary, languageCode)) {
					closeFreeSpins(report, imageLibrary, languageCode);
					Thread.sleep(3000);
					report.detailsAppendNoScreenshot("Verify Free Spins - Transition from Feature to Base Game",
							"Free Spins - Transition from Feature to Base Game",
							"Free Spins - Transition from Feature to Base Game", "Pass");
				} else
					report.detailsAppendNoScreenshot("Verify Free Spins - Transition from Feature to Base Game",
							"Free Spins - Transition from Feature to Base Game",
							"Free Spins - Transition from Feature to Base Game", "Fail");
			}

			// Free Spins - Refresh - Transition from Feature to Base Game
			if (FreeSpinTransitionFeatureToBaseGameRefresh.equalsIgnoreCase("yes")) {
				setChromiumWebViewContext();
				webdriver.navigate().refresh();
				Thread.sleep(8000);
				if (waitTillFreespinSummary(report, imageLibrary, languageCode)) {
					closeFreeSpins(report, imageLibrary, languageCode);
					report.detailsAppendNoScreenshot(
							"Verify Free Spins - Refresh - Transition from Feature to Base Game",
							"Free Spins - Refresh - Transition from Feature to Base Game",
							"Free Spins - Refresh - Transition from Feature to Base Game", "Pass");
				} else
					report.detailsAppendNoScreenshot(
							"Verify Free Spins - Refresh - Transition from Feature to Base Game",
							"Free Spins - Refresh - Transition from Feature to Base Game",
							"Free Spins - Refresh - Transition from Feature to Base Game", "Fail");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to wait till Free spins continue button is visible
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 * @return
	 */
	public boolean waitTillFreespinSummary(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		boolean isFSSummaryVisible = false;
		try {
			setChromiumWebViewContext();
			if ("Yes".equalsIgnoreCase(xpathMap.get("isFSSummaryHookAvailable"))) {
				if (waitForElement("FSSummaryScreen"))
					isFSSummaryVisible = true;
			} else {
				webdriver.context("NATIVE_APP");
				if (imageLibrary.waitTillImageVisible("FSSummaryContinueBtn"))
					isFSSummaryVisible = true;
			}
			Thread.sleep(5000);
			if (isFSSummaryVisible)
				report.detailsAppendFolder("Verify if Free spins summary screen is visible",
						"Free spins Summary Screen should be visible", "Free spins Summary Screen is visible", "Pass",
						languageCode);
			else
				report.detailsAppendFolder("Verify if Free spins summary screen is visible",
						"Free spins Summary Screen should be visible", "Free spins Summary Screen is not visible",
						"Fail", languageCode);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isFSSummaryVisible;
	}

	/**
	 * This method is used to close freespins
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void closeFreeSpins(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("FSSummaryContinueBtn")) {
				Thread.sleep(2000);
				// click on spin button
				imageLibrary.click("FSSummaryContinueBtn");
				Thread.sleep(3000);
				if (imageLibrary.isImageAppears("Spin")) {
					report.detailsAppendFolder("Verify FS summary screen click", "FS summary screen click",
							" FS summary screen clicked", "Pass", currencyName);
				} else {
					System.out.println("Free Spins Summary Screen : Fail");
					log.debug("Free Spins Summary Screen : Fail");
					report.detailsAppendFolder("Verify FS summary screen click", "FS summary screen click",
							" FS summary screen not clicked", "Fail", currencyName);
				}
			} // Closing Summary Screen

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to check net position value after refresh
	 * 
	 * @author pb61055
	 * @param report
	 * @param languageCode
	 */
	public void checkNetPositionAfterRefresh(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String languageCode) {
		try {
			setChromiumWebViewContext();
			webdriver.navigate().refresh();
			Thread.sleep(10000);

			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("FSSummaryContinueBtn")) {
				imageLibrary.click("FSSummaryContinueBtn");
			}

			setChromiumWebViewContext();
			String netPositionValueRefresh = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
			netPositionValueRefresh = netPositionValueRefresh.replaceAll("[^0-9.-]", "").trim();
			if (netPositionValueRefresh.equals("0.00")) {
				report.detailsAppendNoScreenshot("Verify net position is 0.00 when refreshed",
						"Net position should be 0.00", "Net position is 0.00", "Pass");
			} else {
				report.detailsAppendFolder("Verify net position is 0.00 when refreshed", "Net position should be 0.00",
						"Net position is not 0.00", "Fail", languageCode);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to refresh game
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 */
	public void refreshGameAndClickContinueBtn(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String currencyName) {
		try {
			setChromiumWebViewContext();
			// String gameurl = webdriver.getCurrentUrl();
			webdriver.navigate().refresh();
			log.debug("game Refresh  ");
			Thread.sleep(10000);
			if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
				clickOnBaseSceneContinueButton(report, imageLibrary, currencyName);
			}
			Thread.sleep(2000);
			report.detailsAppendFolder("Verify Refresh", "After Refresh", "Ater Refresh", "Pass", currencyName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to calculate net postion value and verify if win amount
	 * is updated correctly
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param winType
	 */
	public void verifyNetPositionValue(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String winType) {
		boolean isNetPositionUpdated = false;
		boolean isPlayerWon = false;
		String winAmount = null;
		try {
			setChromiumWebViewContext();
			String netPositionBeforeSpin = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
			double dblNetPositionBeforeSpin = Double
					.parseDouble(netPositionBeforeSpin.replaceAll("[^0-9.-]", "").replace(".", ""));

			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Spin")) {
				imageLibrary.click("Spin");
				Thread.sleep(5000);
				setChromiumWebViewContext();
				closeOverlayForLVC();
			}
			setChromiumWebViewContext();
			if (winType.equalsIgnoreCase("NormalWin")) {
				Thread.sleep(5000);
			} else if (winType.equalsIgnoreCase("BigWin") || winType.equalsIgnoreCase("BonusWin")) {
				Thread.sleep(12000);
			} else
				log.debug("no extra sleep time");

			setChromiumWebViewContext();
			Thread.sleep(2000);

			String netPositionAfterSpin = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
			double dblNetPositionAfterSpin = Double
					.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));

			String betValue = getConsoleText("return " + xpathMap.get("BetTextValue"));
			double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

			if (winType.isEmpty())
				isPlayerWon(report, imageLibrary, currencyName);
			else
				isPlayerWon = isPlayerWon(report, imageLibrary, currencyName, winType);

			if (isPlayerWon) {
				winAmount = playerWinAmount(report, imageLibrary, currencyName, winType);
				double dblWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));
				try

				{
					double dblBSandBetValue = dblNetPositionBeforeSpin - dblBetValue;
					if ((dblBSandBetValue + dblWinAmount) == dblNetPositionAfterSpin) {
						System.out.println("Win is added to net position successfully");
						isNetPositionUpdated = true;
					} else {
						System.out.println("Win is not added to net position successfully");
						isNetPositionUpdated = false;
					}
				} catch (Exception e) {
					log.error(e);
				}

			} else {
				if ((dblNetPositionBeforeSpin - dblBetValue) == (dblNetPositionAfterSpin)) {
					System.out.println("There is no win, net position is updated successfully");
					isNetPositionUpdated = true;
				} else {
					System.out.println("There is no win, net position is not updated successfully");
					isNetPositionUpdated = false;
				}
			}
			if (isNetPositionUpdated) {
				report.detailsAppendFolder("verify Net position updates for " + winType, "Net position should update ",
						"Net position is updating ", "Pass", currencyName);
				log.debug("Net Position before spin " + netPositionBeforeSpin + " Net psotion after spin "
						+ netPositionAfterSpin);
			} else {
				report.detailsAppendFolder("verify Net position updates for " + winType, "Net position should update ",
						"Net position is not updating ", "Fail", currencyName);
				log.debug("Net Position before spin " + netPositionBeforeSpin + " Net psotion after spin "
						+ netPositionAfterSpin);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to validate if player has won any win
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param winType
	 * @return
	 */
	public boolean isPlayerWon(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String winType) {
		String winAmount = null;
		boolean isplayerWon = false;
		try {
			setChromiumWebViewContext();
			winAmount = playerWinAmount(report, imageLibrary, currencyName, winType);
			if (winAmount != null && !"".equals(winAmount)) {
				isplayerWon = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isplayerWon;
	}

	/**
	 * This method is used to validate if player has won any win
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param winType
	 * @return
	 */
	public boolean isPlayerWon(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
		String winAmount = null;
		boolean isplayerWon = false;
		try {
			setChromiumWebViewContext();
			winAmount = getCurrentWinAmtImg(report, imageLibrary, currencyName);
			if (winAmount != null && !"".equals(winAmount)) {
				isplayerWon = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isplayerWon;
	}

	/**
	 * This method is used to get Player win amount by Passing win type
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param currencyName
	 * @param winType
	 * @return
	 */
	public String playerWinAmount(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
			String winType) {
		String winAmount = null;
		try {
			setChromiumWebViewContext();
			if (winType.equalsIgnoreCase("NormalWin"))
				winAmount = getCurrentWinAmtImg(report, imageLibrary, currencyName);
			else if (winType.equalsIgnoreCase("BigWin"))
				winAmount = verifyBigWinImg(report, imageLibrary, currencyName);
			else if (winType.equalsIgnoreCase("BonusWin"))
				winAmount = verifyBonusWin(report, currencyName);
			else
				log.debug("no win");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return winAmount;
	}

	/**
	 * This method is used to verify Net Position in Free spins
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 * @param winType
	 */
	public void verifyNetPositionFreeSpins(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,
			double dblNetPositionBeforeFS) {
		try {
			setChromiumWebViewContext();
			webdriver.navigate().refresh();
			Thread.sleep(8000);

			boolean isNetPositionUpdated = false;
			String freeSpinsSummaryWinAmt = null;

			if (waitTillFreespinSummary(report, imageLibrary, languageCode)) {
				freeSpinsSummaryWinAmt = GetConsoleText("return " + xpathMap.get("freeSpinsSummaryWinAmt"));
			}

			closeFreeSpins(report, imageLibrary, languageCode);

			setChromiumWebViewContext();

			String netPositionAfterFS = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
			double dblNetPositionAfterFS = Double
					.parseDouble(netPositionAfterFS.replaceAll("[^0-9.-]", "").replace(".", ""));

			String betValue = getConsoleText("return " + xpathMap.get("BetTextValue"));
			double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

			if (freeSpinsSummaryWinAmt != null) {

				double dblWinAmount = Double.parseDouble(freeSpinsSummaryWinAmt.replaceAll("[^0-9]", ""));
				try

				{
					double dblBSandBetValue = dblNetPositionBeforeFS - dblBetValue;
					if ((dblBSandBetValue + dblWinAmount) == dblNetPositionAfterFS) {
						System.out.println("Win is added to net position successfully");
						isNetPositionUpdated = true;
					} else {
						System.out.println("Win is not added to net position successfully");
						isNetPositionUpdated = false;
					}
				} catch (Exception e) {
					log.error(e);
				}

			} else {
				if ((dblNetPositionBeforeFS - dblBetValue) == (dblNetPositionAfterFS)) {
					System.out.println("There is no win, net position is updated successfully");
					isNetPositionUpdated = true;
				} else {
					System.out.println("There is no win, net position is not updated successfully");
					isNetPositionUpdated = false;
				}
			}
			if (isNetPositionUpdated) {
				report.detailsAppendFolder("verify Net position updates for FreeSpins", "Net position should update ",
						"Net position is updating ", "Pass", languageCode);
				log.debug("Net Position before spin " + dblNetPositionBeforeFS + " Net psotion after spin "
						+ dblNetPositionAfterFS);
			} else {
				report.detailsAppendFolder("verify Net position updates for FreeSpins", "Net position should update ",
						"Net position is not updating ", "Fail", languageCode);
				log.debug("Net Position before spin " + dblNetPositionBeforeFS + " Net psotion after spin "
						+ dblNetPositionAfterFS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * This method is used to get net poition before any feature is triggered
	 * 
	 * @author pb61055
	 * @return
	 */
	public double getNetPositionDblValue() {
		double dblNetPosition = 0;
		try {
			String netPosition = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
			dblNetPosition = Double.parseDouble(netPosition.replaceAll("[^0-9.-]", "").replace(".", ""));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dblNetPosition;
	}

	/**
	 * This method is used to validate cma for Free spins
	 * @author pb61055
	 * @param report
	 * @param userName
	 * @param imageLibrary
	 * @param languageCode
	 * @param closeBtnEnabledCMA
	 */
	public void validateBonusFundsNotificationFS(Mobile_HTML_Report report, String userName, ImageLibrary imageLibrary,
			String languageCode, String closeBtnEnabledCMA) {
		try {
			if (closeBtnEnabledCMA.equalsIgnoreCase("yes")) 
			{
				setChromiumWebViewContext();
				CommonUtil util = new CommonUtil();

				if (util.enableBonusFundsNotification(userName)) {
					// Refreshing the game to verify Bonus funds notification
					webdriver.navigate().refresh();
					Thread.sleep(8000);
					
				}

				setChromiumWebViewContext();
				// Ensure the Bonus Funds notification appears
				if (isBonusFundsNotificationVisible()) {
					report.detailsAppendFolder("Verify if Bonus funds notification is visible ",
							"Bonus funds notification should display", "Bonus funds notification is displayed", "Pass",
							languageCode);
				} else {
					report.detailsAppendFolder("Verify if Bonus funds notification is visible ",
							"Bonus funds notification should display", "Bonus funds notification is not displayed",
							"Fail", languageCode);
				}

			}

		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}

	}
	/**
	 * This method is used t trigger bonus
	 * @author pb61055
	 * @param imageLibrary
	 */
	public boolean triggerBonus(ImageLibrary imageLibrary)
	{
		boolean isBonusTriggered=false;
		try
		{	
			setChromiumWebViewContext();
			int retryCount=5;
			int count=0;
			
			while(count<retryCount)
			{
				setChromiumWebViewContext();
				if(elementVisible_Xpath(xpathMap.get("isBonusVisible"))) 
				{
					isBonusTriggered=true;
					break;
				}
				 webdriver.context("NATIVE_APP");
				if (imageLibrary.isImageAppears("Spin")) {
					imageLibrary.click("Spin");
					
				}
				count++;
			}
				Thread.sleep(5000);
		}
		 catch (Exception e) {
			   log.error(e.getMessage(), e);
		   	}
		return isBonusTriggered;
	}
	
	/**
	 * This method is used to select bonus option
	 * @author pb61055
	 * @param imageLibrary
	 */
	public void selectBonusOption(ImageLibrary imageLibrary) 
	{
		try 
		{
			webdriver.context("NATIVE_APP");
		if(imageLibrary.isImageAppears("BonusOption1")) {
			imageLibrary.click("BonusOption1");
			}
		}
		 catch (Exception e) {
			   log.error(e.getMessage(), e);
		   	}
	}
	/**
	 * This method is used to verify Link and Win Transitions
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 * @param BonusTransitionBaseGameToFeature
	 * @param BonusTransitionBaseGameToFeatureRefresh
	 */
	public void verifyBonusTransitionBaseToBonus(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode,String BonusTransitionBaseGameToFeature,
			String BonusTransitionBaseGameToFeatureRefresh)
	{
		try
		{
			setChromiumWebViewContext();
			// Bonus - Transition from Feature to Base Game
			if (BonusTransitionBaseGameToFeature.equalsIgnoreCase("yes")) {
				
				if (elementVisible_Xpath(xpathMap.get("isBonusVisible"))) {
					report.detailsAppendFolder("Verify Bonus - Transition from Base Game to Feature",
							"Bonus - Transition from Base Game to Feature",
							"Bonus - Transition from Base Game to Feature", "Pass", languageCode);
				}
			}

			// Bonus - Refresh - Transition from Feature to Base Game
			if (BonusTransitionBaseGameToFeatureRefresh.equalsIgnoreCase("yes")) {
				webdriver.navigate().refresh();
				Thread.sleep(8000);
				if (elementVisible_Xpath(xpathMap.get("isBonusVisible"))) {
					report.detailsAppendFolder("Verify Bonus - Transition from Base Game to Feature",
							"Bonus - Transition from Base Game to Feature",
							"Bonus - Transition from Base Game to Feature", "Pass", languageCode);
				}
			}
			report.detailsAppendNoScreenshot("Verify Quick spin", "Quick Spin", "Quick spin", "Pass");
			report.detailsAppendNoScreenshot("Verify Spin stop/slam stop", "Spin stop/slam stop", "Spin stop/slam stop", "Pass");
		}
		 catch (Exception e) {
			   log.error(e.getMessage(), e);
		   	}
	}
	
	/**
	 * This method is used to validate Bonus session reminder
	 * 
	 * @author pb61055
	 * @param report
	 * @param userName
	 * @param imageLibrary
	 * @param languageCode
	 * @param SessionReminderUserInteraction
	 * @param SessionReminderContinue
	 * @param SessionReminderExitGame
	 */
	public void validateSessionReminderBonus(Mobile_HTML_Report report, String userName, ImageLibrary imageLibrary,
			String languageCode, String SessionReminderUserInteraction, String SessionReminderContinue,
			String SessionReminderExitGame) {
		try {
			setChromiumWebViewContext();
			CommonUtil util = new CommonUtil();
			String gameurl = webdriver.getCurrentUrl();

			String periodValue = xpathMap.get("sessionReminderDurationInSec");
			String resetPeriodValue = xpathMap.get("resetSessionReminderValue");

			// assigning session reminder
			boolean sessionReminderAssigned = util.setSessionReminderOnAxiom(userName, periodValue);
			loadGame(gameurl);
			Thread.sleep(5000);

			log.debug("Session reminder is set for " + periodValue + " secs");

			if (sessionReminderAssigned && sessionReminderPresent()) {
				report.detailsAppendFolder("Verify Session Reminder must be present",
						"Session Reminder must be present", "Session Reminder is present", "Pass", languageCode);

				// Ensure that the continue button takes you back to the game
				if (SessionReminderContinue.equalsIgnoreCase("yes")) {
					selectContinueSession();
					
					webdriver.context("NATIVE_APP");
					if (imageLibrary.isImageAppears("BonusSpin")) {
						if ("Yes".equalsIgnoreCase(xpathMap.get("BonusRefreshHasContinueBtn"))) {
							imageLibrary.isImageAppears("BonusRefreshContinue");
						}
						report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",
								" Session Reminder dialog box is closed and game should be visible",
								" Session Reminder dialog box is closed and game is visible", "Pass", languageCode);
						Thread.sleep(2000);
					} else {
						report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",
								" Session Reminder dialog box is closed and game should be visible",
								" Session Reminder dialog box is closed and game is not visible", "Fail", languageCode);
					}
				}
			} else {
				report.detailsAppendFolder("Verify Session Reminder must be present",
						"Session Reminder must be present", "Session Reminder is not present", "Fail", languageCode);
				log.debug("Session reminder is not visible on screen");
				Thread.sleep(2000);
			}
			
			setChromiumWebViewContext();
			// reset to higher duarion for session reminder
			boolean resetSessionReminderDUration = util.setSessionReminderOnAxiom(userName, resetPeriodValue);
			if (resetSessionReminderDUration) {
				log.debug("Session reminder has been reset to 10hr in order to avoid pop up");
			} else {
				log.debug("Unable to reset Session reminder");
			}
			// relaunching the game to set the above reset session reminder duration to
			// higher value

			loadGame(gameurl);
			Thread.sleep(5000);
			if ("Yes".equalsIgnoreCase(xpathMap.get("BonusRefreshHasContinueBtn"))) {
				imageLibrary.isImageAppears("BonusRefreshContinue");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * This method is used to verify link and win feature
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 */
	public void verifyLinkAndWinFeature(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("BonusSpin")) 
			{
				spinUntilImageAppears(report, imageLibrary, "BonusComplete");
			}
			setChromiumWebViewContext();
			if (elementVisible_Xpath(xpathMap.get("bonusFeatureCongratulations"))) {
				report.detailsAppendFolder("Verify Bonus is completed", "Bonus Feature should be complete",
						"Bonus Feature is completed", "Pass", languageCode);
			} else if (imageLibrary.isImageAppears("BonusContinue")) {
				report.detailsAppendFolder("Verify Bonus is completed", "Bonus Feature should be complete",
						"Bonus Feature is completed", "Pass", languageCode);
			} else {
				report.detailsAppendFolder("Verify Bonus is completed", "Bonus Feature should be complete",
						"Bonus Feature is not completed", "Fail", languageCode);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
	
	/**
	 * This method is used to spin until img appear on screen
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param image
	 */
	public void spinUntilImageAppears(Mobile_HTML_Report report, ImageLibrary imageLibrary, String image) {
		try {
			webdriver.context("NATIVE_APP");
			while (!(imageLibrary.isImageAppears(image))) 
			{
				setChromiumWebViewContext();
				webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				webdriver.context("NATIVE_APP");
				imageLibrary.click("Spin");
				Thread.sleep(8000);
				if (imageLibrary.isImageAppears(image)) 
				{
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * This method is used to verify Net Position in Bonus
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 * @param winType
	 */
	public void verifyNetPositionBonus(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,
			String winType, double dblNetPositionBeforeBonus) {
		try {
			setChromiumWebViewContext();
			boolean isNetPositionUpdated = false;
			boolean isPlayerWon = false;
			String winAmount = null;

			// webdriver.navigate().refresh();
			// Thread.sleep(8000);

			String betValue = getConsoleText("return " + xpathMap.get("BetTextValue"));
			double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

			String netPositionAfterBonus = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
			double dblNetPositionAfterBonus = Double
					.parseDouble(netPositionAfterBonus.replaceAll("[^0-9.-]", "").replace(".", ""));

			if (winType.isEmpty())
				isPlayerWon(report, imageLibrary, languageCode);
			else
				isPlayerWon = isPlayerWon(report, imageLibrary, languageCode, winType);

			if (isPlayerWon) {
				winAmount = playerWinAmount(report, imageLibrary, languageCode, winType);
				double dblWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));
				try

				{
					double dblBSandBetValue = dblNetPositionBeforeBonus - dblBetValue;
					if ((dblBSandBetValue + dblWinAmount) == dblNetPositionAfterBonus) {
						System.out.println("Win is added to net position successfully");
						isNetPositionUpdated = true;
					} else {
						System.out.println("Win is not added to net position successfully");
						isNetPositionUpdated = false;
					}
				} catch (Exception e) {
					log.error(e);
				}

			} else {
				if ((dblNetPositionBeforeBonus - dblBetValue) == (dblNetPositionAfterBonus)) {
					System.out.println("There is no win, net position is updated successfully");
					isNetPositionUpdated = true;
				} else {
					System.out.println("There is no win, net position is not updated successfully");
					isNetPositionUpdated = false;
				}
			}
			if (isNetPositionUpdated) {
				report.detailsAppendFolder("verify Net position updates for " + winType, "Net position should update ",
						"Net position is updating ", "Pass", languageCode);
				log.debug("Net Position before spin " + dblNetPositionBeforeBonus + " Net psotion after spin "
						+ netPositionAfterBonus);
			} else {
				report.detailsAppendFolder("verify Net position updates for " + winType, "Net position should update ",
						"Net position is not updating ", "Fail", languageCode);
				log.debug("Net Position before spin " + dblNetPositionBeforeBonus + " Net psotion after spin "
						+ netPositionAfterBonus);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * This method is used to verify Link and Win Transitions
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 * @param BonusTransitionFeatureToBaseGame
	 * @param BonusTransitionFeatureToBaseGameRefresh
	 */
	public void verifyBonusTransitionBonusToBase(Mobile_HTML_Report report, ImageLibrary imageLibrary,
			String languageCode, String BonusTransitionFeatureToBaseGame,
			String BonusTransitionFeatureToBaseGameRefresh) {
		try {
			setChromiumWebViewContext();
			// Bonus - Transition from Feature to Base Game
			if (BonusTransitionFeatureToBaseGame.equalsIgnoreCase("yes")) {
				if (elementVisible_Xpath(xpathMap.get("bonusFeatureCongratulations"))) 
				{
					webdriver.context("NATIVE_APP");
					imageLibrary.click("BonusContinue");
					Thread.sleep(3000);
					report.detailsAppendNoScreenshot("Verify Bonus - Transition from Feature to Base Game",
							"Bonus - Transition from Feature to Base Game",
							"Bonus - Transition from Feature to Base Game", "Pass");
				} else
					report.detailsAppendNoScreenshot("Verify Bonus - Transition from Feature to Base Game",
							"Bonus - Transition from Feature to Base Game",
							"Bonus - Transition from Feature to Base Game", "Fail");
			}

			setChromiumWebViewContext();
			// Bonus - Refresh - Transition from Feature to Base Game
			if (BonusTransitionFeatureToBaseGameRefresh.equalsIgnoreCase("yes")) {
				webdriver.navigate().refresh();
				Thread.sleep(8000);
				if (elementVisible_Xpath(xpathMap.get("bonusFeatureCongratulations"))) 
				{
					webdriver.context("NATIVE_APP");
					imageLibrary.click("BonusContinue");
					Thread.sleep(3000);
					report.detailsAppendNoScreenshot("Verify Bonus - Refresh - Transition from Feature to Base Game",
							"Bonus - Refresh - Transition from Feature to Base Game",
							"Bonus - Refresh - Transition from Feature to Base Game", "Pass");
				} else
					report.detailsAppendNoScreenshot("Verify Bonus - Refresh - Transition from Feature to Base Game",
							"Bonus - Refresh - Transition from Feature to Base Game",
							"Bonus - Refresh - Transition from Feature to Base Game", "Fail");
			}
			
			setChromiumWebViewContext();
			// Game does not carry previous spin info after a refresh - Bonus
			checkNetPositionAfterRefresh(report, imageLibrary, languageCode);
			verifyNetPositionValue(report, imageLibrary, languageCode, "NormalWin");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void FGOfferDelete(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {

		try {
			setChromiumWebViewContext();
			RefreshGame("clock");
			Thread.sleep(15000);
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("FGPlayNow")) {
				report.detailsAppendFolder("Verify Game launchaed after refresh to check Discard FG Offer",
						"Game should be launched", "Game is launched", "Pass", languageCode);
				if (imageLibrary.isImageAppears("FGDelete")) {
					report.detailsAppendFolder("Verify FG Discard offer Button visible",
							"FG Discard offer Button should be visible", "FG Discard offer Button is visible", "Pass",
							languageCode);
					imageLibrary.click("FGDelete");
					Thread.sleep(1000);
					
					//String discardText=imageLibrary.getText("FGDiscard");
					//System.out.println("discardText=="+discardText);
					
					//in case of case sencitivity convert the string to lower/upper case and verify
					String value=imageLibrary.getTextToCompare();
					if(value.contains("DISCARD"))
					{
						System.out.println("Pass");
					}
					else
					{
						System.out.println("Fail");
					}
					webdriver.context("NATIVE_APP");
					if (imageLibrary.isImageAppears("FGDiscard")) {
						report.detailsAppendFolder("Verify FG Discard page visible",
								"FG Discard page should be visible", "Discard page is visible", "Pass", languageCode);
						imageLibrary.click("FGDiscard");

						Thread.sleep(1000);

						if (xpathMap.get("NFD").equalsIgnoreCase("Yes")) {

							clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
						}

						else {
							Thread.sleep(2000);

							if (imageLibrary.isImageAppears("Spin")) {
								System.out.println("Base Scene after FG Play Later");
								log.debug("Base Scene after FG Play Later");
								report.detailsAppendFolder("Verify Base Scene after discarding FG", "Base is visible",
										"Free Game Scene is visible", "Pass", languageCode);
							} else {
								System.out.println("Base Scene after FG Play Later is not visible");
								log.debug("Base Scene after FG Play Later is not visible");
								report.detailsAppendFolder("Verify Base Scene after discarding FG", "Base is visible",
										"Free Game Scene is not visible", "Fail", languageCode);
							}
						}

					} else {
						report.detailsAppendFolder("VerifyFG Discard page visible ",
								"FG Discard page should be visible", "Discard page is not visible", "Fail",
								languageCode);
					}
				} else {
					report.detailsAppendFolder("Verify FG Discard offer Button visible",
							"FG Discard offer Button should be visible", "FG Delete offer Button is not visible",
							"Fail", languageCode);
				}
			} else {
				report.detailsAppendFolder("Verify Game launchaed after refresh to check Discard FG Offer ",
						"Game should be launched", "Game is not launched", "Fail", languageCode);
			}

		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
	}
	
	public void RefreshGame(String Element) {
		webdriver.navigate().refresh();
		if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(xpathMap.get("TypeOfGame"))) {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get(Element))));
		} else {
			log.debug("Waiting for clock to be visible");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMap.get(Element))));
			log.debug("Waiting for clock is visible");
		}
		log.debug("game Refresh  ");
	}
	/**
	 * This method is used to check FG offer Screen
	 * 
	 * @author sp68146
	 * @param report
	 * @param imageLibrary
	 * @param languageCode
	 * @throws InterruptedException
	 */
	public boolean fGAssignment(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode)
			throws InterruptedException {
		boolean isFgAssigned = false;
		try {
			Thread.sleep(60000);
			webdriver.navigate().refresh();
			Thread.sleep(7000);
			funcFullScreen();
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("FGPlayNow")) {
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify FG Screen visibility",
						"ensure Play now, Play Later, Delete icon are displayed", "Landed on FG offer screen", "Pass",
						languageCode);
				isFgAssigned = true;
				Thread.sleep(2000);
			} else {
				report.detailsAppendFolder("Verify FG Screen visibility",
						"ensure Play now, Play Later, Delete icon are displayed", "Not landed on FG offer screen",
						"Fail", languageCode);

			}

		} catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
		return isFgAssigned;
	}
	
	public void fGPlayLater(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode) {
		try {
			webdriver.context("NATIVE_APP");
			imageLibrary.click("FGPlayLater");
			Thread.sleep(2000);
			setChromiumWebViewContext();
			if(xpathMap.get("NFD").equalsIgnoreCase("Yes"))
			{
				
				//check for visiblity of nfd button and take screenshot
				Thread.sleep(3000);
				webdriver.context("NATIVE_APP");
				if(imageLibrary.isImageAppears("NFDButton"))
				{							
					imageLibrary.click("NFDButton");
					Thread.sleep(3000);
					report.detailsAppendFolder("Verify if able to click on continue after clicking on Play Later option",
							"Should be able to click on continue button", "Able to click on continue button",
							"Pass", languageCode);														
		  
					if(imageLibrary.isImageAppears("Spin"))
					{
						System.out.println("Base Scene after FG Play Later");log.debug("Base Scene after FG Play Later");
						report.detailsAppendFolder("Verify Base Scene after FG Play Later", "Base should be visible", "Base Scene is visible", "Pass",languageCode);
					}
					else
					{
						System.out.println("Base Scene after FG Play Later is not visible");log.debug("Base Scene after FG Play Later is not visible");
						report.detailsAppendFolder("Verify Base Scene after FG Play Later", "Base should be visible", "Base Scene is not visible", "Fail", languageCode);
					}
				}
				else
				{
					report.detailsAppendFolder("Verify Continue button is visible after FG Play Later ", "Continue buttion should be visible", "Continue button is not visible", "Fail", languageCode);
				}
			}
			else
			{	
				webdriver.context("NATIVE_APP");
				if(imageLibrary.isImageAppears("Spin"))
				{
					System.out.println("Base Scene after FG Play Later");log.debug("Base Scene after FG Play Later");
					report.detailsAppendFolder("Verify Base Scene after FG Play Later", "Base is visible", "Free Game Scene is visible", "Pass", languageCode);
				}
				else
				{
					System.out.println("Base Scene after FG Play Later is not visible");log.debug("Base Scene after FG Play Later is not visible");
					report.detailsAppendFolder("Verify Base Scene after FG Play Later", "Base is visible", "Free Game Scene is not visible", "Fail", languageCode);
				}																									
			}
		}
		catch (Exception e) {
			e.getMessage();
			log.error(e);
		}	
		
	}
		
	public void FGOfferAcceptAndResumeFG(Mobile_HTML_Report report,ImageLibrary imageLibrary,String languageCode, String FGOfferResume, String FGSummaryScreen,String FGOfferDiscardBaseScene) {
		try {
		Thread.sleep(30000);
		setChromiumWebViewContext();
		webdriver.navigate().refresh();
		funcFullScreen();
		webdriver.context("NATIVE_APP");
		if (FGOfferDiscardBaseScene.equalsIgnoreCase("yes")) 
		{
			if (imageLibrary.isImageAppears("FreeGameNextOffer")) 
			{
				imageLibrary.click("FreeGameNextOffer");
				Thread.sleep(3000);
			}
		}
		if (imageLibrary.isImageAppears("FGPlayNow")) {
			Thread.sleep(1000);
			imageLibrary.click("FGPlayNow");
			Thread.sleep(3000);
			if (xpathMap.get("NFDHasHook").equalsIgnoreCase("Yes")) {
				clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
			}
			if (imageLibrary.isImageAppears("Spin")) {
				System.out.println("Free Game Scene is visible");
				log.debug("Free Game Scene is visible");
				report.detailsAppendFolder("Verify Free Game Scene", "Free Game Scene is visible",
						"Free Game Scene is visible", "Pass", languageCode);
				
	/*			setChromiumWebViewContext();
	  			String netPositionBeforeSpin = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
				double dblNetPositionBeforerSpin = Double.parseDouble(netPositionBeforeSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
				System.out.println("Credit value/Net Position before FG spin " + netPositionBeforeSpin);
				webdriver.context("NATIVE_APP");
				imageLibrary.click("Spin");
				Thread.sleep(4000);
				setChromiumWebViewContext();
				String netPositionAfterSpin = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).getText();
				double dblNetPositionAfterSpin = Double.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
				if (dblNetPositionBeforerSpin==dblNetPositionAfterSpin) {
					report.detailsAppendFolder("verify FG Net position ", "Net position should not be deducted in FG",
							"Net Position is not deducted ", "pass",languageCode);
				} else {
					report.detailsAppendFolder("verify FG Net position ", "Net position should not be deducted in FG",
							"Net Position is deducted ", "fail",languageCode);
				}
				Thread.sleep(2000);
				// check if spin is successful,take screenshot
				report.detailsAppendNoScreenshot("verify FG Spin 1", "Spin Button is working", "Spin Button is working",
						"Pass");

				System.out.println("Net Position after FG spin " + dblNetPositionAfterSpin);
				webdriver.context("NATIVE_APP");
		*/		imageLibrary.click("Spin");
				Thread.sleep(5000);
				// check if spin is successful,take screenshot
				report.detailsAppendFolder("verify FG Spin 2", "Spin Button is working", "Spin Button is working",
						"Pass",languageCode);

				//// *************************Resume FG scenario********************
				if (FGOfferResume.equalsIgnoreCase("Yes")) {
					setChromiumWebViewContext();
					webdriver.navigate().refresh();
					Thread.sleep(10000);
					funcFullScreen();
					webdriver.context("NATIVE_APP");
					Thread.sleep(1500);
					if (imageLibrary.isImageAppears("FGResumePlay")) {
						report.detailsAppendFolder("Verify Free Game Resume Button",
								"Free Game Resume button should be visible", "Free Game Resume button is visible",
								"Pass",languageCode);
						if (FGSummaryScreen.equalsIgnoreCase("Yes")) {

							imageLibrary.click("FGResumePlay");
							Thread.sleep(3000);
							if (xpathMap.get("NFDHasHook").equalsIgnoreCase("Yes")) {
								clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
								Thread.sleep(3000);
							}
							imageLibrary.click("Spin");
							Thread.sleep(7000);
							// check if spin is successful,take screenshot
							report.detailsAppendFolder("verify FG Spin 3", "Spin Button is working",
									"Spin Button is working", "Pass", languageCode);
							if (imageLibrary.isImageAppears("FGSummaryBackToGameBtn")) {

								report.detailsAppendFolder("verify FG Summary Screen",
										" should be landed on FG summary screen", "landed on FG summary screen",
										"Pass", languageCode);
							}
						}
					} else {
						report.detailsAppendFolder("Verify Free Game Resume Button",
								"Free Game Resume button should be visible",
								"Free Game Resume button is not visible", "Fail", languageCode);

					}
				}
			} else {
				System.out.println("Free Game Scene is not visible");
				log.debug("Free Game Scene is not visible");
				report.detailsAppendFolder("Verify Free Game Scene", "Free Game Scene is visible",
						"Free Game Scene is not visible", "Fail",languageCode);
			}

		}
	} catch (Exception e) {
		e.getMessage();
		log.error(e);
	}

}

	public void freeSpinsInFG(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		try {
			if(fGAssignment(report,imageLibrary,languageCode)) {	
				
				if(AcceptfgOffer(report, imageLibrary, languageCode)) {
				// trigger free spins
				imageLibrary.click("Spin");
				if(xpathMap.get("tapToContinueFreespinsAvailable").equalsIgnoreCase("Yes"))
				{
					//cfnlib.waitForElement("tapToContinueFreespins");
					if(xpathMap.get("isFSHaveMultipleOptions").equalsIgnoreCase("Yes"))
					{
						imageLibrary.click("tapToFSOption1");
						Thread.sleep(3000);
					}
					imageLibrary.click("tapToContinueFreespins");
				}
											
				Thread.sleep(10000);
				// 1st SS for FS in FG
				report.detailAppend("Verify free spins in Free Game", "Net Position should not change during free spins in Free Game",
						"Net Position should not change during free spins in Free Game", "PASS",languageCode );
				Thread.sleep(5000);
				// 2nd SS for FS in FG
				report.detailAppend("Verify free spins in Free Game", "Net Position should not change during free spins in Free Game",
						"Net Position should not change during free spins in Free Game", "PASS",languageCode );
		
				if(waitTillFreespinSummary(report,imageLibrary,languageCode)) {					
						closeFreeSpins(report, imageLibrary, languageCode);
						Thread.sleep(3000);
						report.detailAppend("Verify Free Spins - Transition from Feature to FG", "Free Spins - Transition from Feature to FG","Free Spins - Transition from Feature to FG", "Pass", languageCode);
					}
					else {
						report.detailAppend("Verify Free Spins - Transition from Feature to FG", "Free Spins - Transition from Feature to FG","Free Spins - Transition from Feature to FG", "Fail", languageCode);
					}
				 FGOfferDelete(report, imageLibrary,languageCode);
				}
				else {
					report.detailsAppendFolder("Verify Free Game Accept Offer", "Free Game offer should be accepted",
							"Free Game offer is not accepted", "Fail",languageCode );
				}
			}
			
		}catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
				
	}

	public boolean AcceptfgOffer(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		boolean fgOfferAccepted= false;
		try {			
			webdriver.navigate().refresh();
			Thread.sleep(7000);
			if (imageLibrary.isImageAppears("FGPlayNow")) {
				imageLibrary.click("FGPlayNow");
				Thread.sleep(3000);
				if (xpathMap.get("NFDHasHook").equalsIgnoreCase("Yes")) {
					clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
				}
				if (imageLibrary.isImageAppears("Spin")) {
					System.out.println("Free Game Scene is visible");
					log.debug("Free Game Scene is visible");
					report.detailsAppendFolder("Verify Free Game Scene", "Free Game Scene is visible",
							"Free Game Scene is visible", "Pass",languageCode );
					fgOfferAccepted=true;
				}
				else {
					report.detailsAppendFolder("Verify Free Game Scene", "Free Game Scene should be visible",
							"Free Game Scene is not visible", "fail",languageCode );
				}
			}
			else {
				report.detailsAppendFolder("Verify Free Game Scene", "Free Games play now button should be visible",
						"Free Game Play now Button is not visible", "fail",languageCode );
			}
		}catch (Exception e) {
			e.getMessage();
			log.error(e);
		}
		return fgOfferAccepted;
	}
	
	/**
	 * This method is used to validate set required bet
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param Image
	 * @param currencyName
	 */
	public void setBet(Mobile_HTML_Report report, ImageLibrary imageLibrary, String Image,String currencyName) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears(Image)) {
				Thread.sleep(2000);
				imageLibrary.click(Image);
				Thread.sleep(2000);
				if (imageLibrary.isImageAppears("Spin")) {
					report.detailsAppendFolder("Max Bet", "Click on bet Button", "Base Scene is visible", "Pass",
							currencyName);
				} else {
					report.detailsAppendFolder("Max Bet", "Click on bet Button", "Base Scene is visible not visible",
							"Fail", currencyName);
				}
			}

		} catch (Exception e) {
			log.debug("unable to bet panel");
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This method is used to validate set Min bet
	 * 
	 * @author pb61055
	 * @param report
	 * @param imageLibrary
	 * @param Image
	 * @param currencyName
	 */
	public void setMinBet(Mobile_HTML_Report report, ImageLibrary imageLibrary,String currencyName) {
		try {
			setChromiumWebViewContext();
			verifyBetSliders(report, imageLibrary, currencyName);
			webdriver.context("NATIVE_APP");
			Thread.sleep(2000);
			if (imageLibrary.isImageAppears("MinBet")) 
			{
				Thread.sleep(2000);
				imageLibrary.click("MinBet");
				Thread.sleep(2000);
				if (imageLibrary.isImageAppears("Spin")) {
					report.detailsAppendFolder("Min Bet", "Click on Min bet Button", "Base Scene is visible", "Pass",
							currencyName);
				} else {
					report.detailsAppendFolder("Min Bet", "Click on Min bet Button", "Base Scene is visible not visible",
							"Fail", currencyName);
				}
			}

		} catch (Exception e) {
			log.debug("unable to bet panel");
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * This method used to spin until free spins are triggered
	 * 
	 * @author pb61055
	 */
	public void spin(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
		try {
			webdriver.context("NATIVE_APP");
			if (imageLibrary.isImageAppears("Spin")) 
			{
				imageLibrary.click("Spin");
				Thread.sleep(5000);
				report.detailsAppendFolder("Verify Spins value", "Spins value", "Spins value", "Pass", languageCode);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * To verify Help text link on top bar
	 * @author pb61055
	 */
	public void verifyHelpTextlink(Mobile_HTML_Report report,String currencyName)
	{
		setChromiumWebViewContext();
		String gameurl = webdriver.getCurrentUrl();
		try 
		{
			boolean isHelpText = webdriver.findElement(By.xpath(xpathMap.get("helpTextLink"))).isDisplayed();
			if(isHelpText)
			{
				log.debug("Help Text is Visible from TopBar");
				webdriver.findElement(By.xpath(xpathMap.get("helpTextLink"))).click();
				log.debug("clicked on Help Text Link from topbar");
				Thread.sleep(3000);
				report.detailsAppendNoScreenshot("Verify Help Text link from Topbar","Help Text link from Topbar is Displayed", "Help Text link from Topbar is Displayed","Pass");			
				checkpagenavigation(report,currencyName, gameurl);
				log.debug("Navigated back to the base game from Help Text Link ");
			}else 
				{
					
					report.detailsAppendNoScreenshot("Verify that Navigation to Help text link screen","Navigation to Help text link screen", "Navigation to Help text link screen not Done","Fail");
				}
			
		} 
		catch (Exception e)
		{
			log.error("Error in checking help page ", e);
		}
	
	}

	/**
	 * This method is used to verify SelfTest using image or xpath
	 * 
	 * @author pb61055
	 */
	public void verifySelfTestNavigation(Mobile_HTML_Report report, String currencyName) 
	{
		setChromiumWebViewContext();
		String gameurl = webdriver.getCurrentUrl();
		try 
		{
			boolean isSelfTest = webdriver.findElement(By.xpath(xpathMap.get("selfTest"))).isDisplayed();
			if(isSelfTest)
			{
				log.debug("selfTest is Visible from TopBar");
				webdriver.findElement(By.xpath(xpathMap.get("selfTest"))).click();
				log.debug("clicked on selfTest");
				Thread.sleep(3000);
				report.detailsAppendNoScreenshot("Verify selfTest ","selfTest", "selfTest","Pass");			
				checkpagenavigation(report,currencyName, gameurl);
				log.debug("Navigated back to the base game from Help Text Link ");
			}else 
				{
					
					report.detailsAppendNoScreenshot("Verify that Navigation to SelfTest","Navigation to SelfTest link screen", "Navigation to SelfTest link screen not Done","Fail");
				}
			
		} 
		catch (Exception e)
		{
			log.error("Error in checking help page ", e);
		}
	
	}
	
	// *************************** Practice Play *****************
		public void practicePlay(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
			try {
				String urlNew;
				setChromiumWebViewContext();
				String currentUrl = webdriver.getCurrentUrl();
				urlNew = currentUrl.replaceAll("isPracticePlay=False", "isPracticePlay=True");
				loadGame(urlNew);

				if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {
					webdriver.context("NATIVE_APP");
					imageLibrary.waitTillImageVisible("NFDButton");
					clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
					report.detailsAppendFolder("Verify Practice Play", " Practice play should be visible",
							" Practice Play is visible", "Pass", languageCode);
					if (imageLibrary.isImageAppears("Spin")) {
						imageLibrary.click("Spin");
						report.detailsAppendFolder("Verify Practice Play first Spin", " Practice play first Spin",
								" Practice play first Spin ", "Pass", languageCode);

						imageLibrary.waitTillImageVisible("Spin");
						imageLibrary.click("Spin");
						Thread.sleep(1000);
						report.detailsAppendFolder("Verify Practice Play second Spin", " Practice play second Spin",
								" Practice play second Spin ", "Pass", languageCode);

						imageLibrary.waitTillImageVisible("Spin");
						imageLibrary.click("Spin");
						Thread.sleep(1000);
						report.detailsAppendFolder("Verify Practice Play third Spin", " Practice play third Spin",
								" Practice play third Spin ", "Pass", languageCode);

					}
				}
				// Load Game in real i.e. isPracticePlay=False
				loadGame(currentUrl);
				System.out.println("Game loaded in real mode from practice play");
				log.info("Game loaded in real mode from practice play");
				if ("Yes".equalsIgnoreCase(xpathMap.get("NFD"))) {

					imageLibrary.waitTillImageVisible("NFDButton");
					clickOnBaseSceneContinueButton(report, imageLibrary, languageCode);
					report.detailsAppendFolder("Verify Game after Practice play", "Game should be out of Pratice Play ",
							"Game is out of Pratice Play ", "Pass", languageCode);
				}

			} catch (Exception e) {
				e.getMessage();
				log.error(e);
			}
		}

		/**
		 * This method is used to go to RTP in helpfile
		 * @author pb61055
		 * @param report
		 * @param currencyName
		 */
		public void helpFileRTP(Mobile_HTML_Report report, String currencyName)
		{
			boolean isVisible = false;
			try
			{
				setChromiumWebViewContext();
				webdriver.switchTo().frame(webdriver.findElement(By.xpath(xpathMap.get("RTPiFrame"))));
				Thread.sleep(3000);
				isVisible = webdriver.findElements(By.xpath(xpathMap.get("helpFileRTP"))).size() > 0;
				if (isVisible) {
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					WebElement ele = webdriver.findElement(By.xpath(xpathMap.get("helpFileRTP")));
					js.executeScript("arguments[0].scrollIntoView(true);", ele);
					report.detailsAppendFolder("RTP", "RTP should be visible", "RTP is visible", "Pass", currencyName);
					Thread.sleep(3000);
				}
				else
					report.detailsAppendFolder("RTP", "RTP should be visible", "RTP is not visible", "Fail", currencyName);
			}catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		/**
		 * This method is used to refresh during spin
		 * @author pb61055
		 * @param report
		 * @param imageLibrary
		 * @param currencyName
		 */
		public void spinRefresh(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName) {
			try {
				webdriver.context("NATIVE_APP");
				Thread.sleep(2000);
				if (imageLibrary.isImageAppears("Spin")) {
					Thread.sleep(3000);
					imageLibrary.click("Spin");
					Thread.sleep(500);
					refreshGameAndClickContinueBtn(report, imageLibrary, currencyName);
					report.detailsAppendFolder("Verify Spin after refresh ", "Spin after refresh",
							"Spin after refresh", "Pass", currencyName);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		/**
		 * This method is used to verify netposition test cases on base scene Test Data
		 * 1. no win 2. normal win 3. Big win *
		 * 
		 * @param report
		 * @param imageLibrary
		 * @param currencyName
		 * @param regExpr
		 * @param NetPositionLaunch
		 * @param NetPositionRefresh
		 * @param NetPositionNormalWin
		 * @param NetPositionBigWin
		 */
		public void verifyNetPositionBaseScene(Mobile_HTML_Report report, ImageLibrary imageLibrary, String currencyName,
				String regExpr, String NetPositionLaunch, String NetPositionRefresh, String NetPositionWinLoss,
				String NetPositionBigWin, String NetPositionRefreshWin, String NetPositionRefreshLoss) {
			
			setChromiumWebViewContext();
			String gameurl = webdriver.getCurrentUrl();
			String netPositionValueLaunch = null;
			String netPositionValueRefresh = null;

			try {
				
				webdriver.context("NATIVE_APP");
				loadGameAndClickContinueBtn(gameurl, report, imageLibrary, currencyName);

				// The game session net position calculates correctly - (win and loss test data
				// should be Passed)
				if (NetPositionWinLoss.equalsIgnoreCase("yes")) {
					verifyNetPositionValue(report, imageLibrary, currencyName, "NoWin");
					verifyNetPositionValue(report, imageLibrary, currencyName, "NormalWin");
				}

				// The game session net position calculates correctly after a Big Win
				if (NetPositionBigWin.equalsIgnoreCase("yes")) {
					verifyNetPositionValue(report, imageLibrary, currencyName, "BigWin");
				}

				// The game session net position displays on the Titan/V info bar
				// The net position resets on each launch of the game
				if (NetPositionLaunch.equalsIgnoreCase("yes")) {
					loadGameAndClickContinueBtn(gameurl, report, imageLibrary, currencyName);
					if (isNetProfitAvailable()) {
						if (imageLibrary.isImageAppears("Spin")) {
							imageLibrary.click("Spin");
							Thread.sleep(3000);
							imageLibrary.click("Spin");
							Thread.sleep(5000);
							imageLibrary.waitTillImageVisible("Spin");
							report.detailsAppendFolder("Verify net position before re-launching the game again",
									"Net position before re-launching the game ",
									"Net position before re-launching the game", "Pass", currencyName);

							loadGameAndClickContinueBtn(gameurl, report, imageLibrary, currencyName);
							report.detailsAppendFolder("Verify net position after re-launching the game ",
									"Net position after re-launching the game ", "Net position after re-launching the game",
									"Pass", currencyName);

							netPositionValueLaunch = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition")))
									.getText();
							boolean isFormatCorrect = verifyRegularExpression(report, regExpr, netPositionValueLaunch);
							if (isFormatCorrect) {

								report.detailsAppendNoScreenshot("Verify net position is in correct currency format",
										"Net position should be in correct currency format",
										"Net position is in correct currency format", "Pass");
							} else {
								report.detailsAppendFolder("Verify net position is in correct currency format",
										"Net position should be in correct currency format",
										"Net position is not in correct currency format", "Fail", currencyName);
							}

							netPositionValueLaunch = netPositionValueLaunch.replaceAll("[^0-9.-]", "").trim();
							if (netPositionValueLaunch.equals("0.00")) {
								report.detailsAppendNoScreenshot("Verify net position is 0.00 when launched",
										"Net position should be 0.00", "Net position is 0.00", "Pass");
							} else {
								report.detailsAppendFolder("Verify net position is 0.00 when launched",
										"Net position should be 0.00", "Net position is not 0.00", "Fail", currencyName);
							}

						}
					}
				}

				// The net position resets when you refresh/reload the game
				if (NetPositionRefresh.equalsIgnoreCase("yes")) {
					if (isNetProfitAvailable()) {
						if (imageLibrary.isImageAppears("Spin")) {
							imageLibrary.click("Spin");
							Thread.sleep(3000);
							imageLibrary.click("Spin");
							Thread.sleep(5000);
							imageLibrary.waitTillImageVisible("Spin");
							report.detailsAppendFolder("Verify net position before refreshing the game ",
									"Net position before refreshing the game ", "Net position before refreshing the game ",
									"Pass", currencyName);

							checkNetPositionAfterRefresh(report, imageLibrary, currencyName);
						}
					}

				}

				// Game does not carry previous spin info after a refresh - Win Scenario
				if (NetPositionRefreshWin.equalsIgnoreCase("yes")) {
					loadGameAndClickContinueBtn(gameurl, report, imageLibrary, currencyName);

					netPositionValueLaunch = netPositionValueLaunch.replaceAll("[^0-9.-]", "").trim();
					if (netPositionValueLaunch.equals("0.00")) {
						report.detailsAppendFolder("Verify net position is 0.00 when launched for win refresh scenario",
								"Net position should be 0.00", "Net position is 0.00", "Pass", currencyName);
					} else {
						report.detailsAppendFolder("Verify net position is 0.00 when launched for win refresh scenario",
								"Net position should be 0.00", "Net position is not 0.00", "Fail", currencyName);
					}
					// As first spin is set to no win, spinning here to get normal win for next spin
					// as per testdata
					if (imageLibrary.isImageAppears("Spin")) {
						imageLibrary.click("Spin");
						Thread.sleep(3000);
						imageLibrary.waitTillImageVisible("Spin");
					}
					verifyNetPositionValue(report, imageLibrary, currencyName, "NormalWin");

					refreshGameAndClickContinueBtn(report, imageLibrary, currencyName);

					netPositionValueLaunch = netPositionValueLaunch.replaceAll("[^0-9.-]", "").trim();
					if (netPositionValueRefresh.equals("0.00")) {
						report.detailsAppendNoScreenshot(
								"Verify net position is 0.00 when refreshed after win for win refresh scenario",
								"Net position should be 0.00", "Net position is 0.00", "Pass");
					} else {
						report.detailsAppendFolder(
								"Verify net position is 0.00 when refreshed after win for win refresh scenario",
								"Net position should be 0.00", "Net position is not 0.00", "Fail", currencyName);
					}
					// As first spin is set to no win, spinning here to get normal win for next spin
					// as per testdata
					if (imageLibrary.isImageAppears("Spin")) {
						imageLibrary.click("Spin");
						Thread.sleep(3000);
						imageLibrary.waitTillImageVisible("Spin");
					}
					verifyNetPositionValue(report, imageLibrary, currencyName, "NormalWin");

				}

				// Game does not carry previous spin info after a refresh - Loss Scenario
				if (NetPositionRefreshLoss.equalsIgnoreCase("yes")) {
					loadGameAndClickContinueBtn(gameurl, report, imageLibrary, currencyName);

					netPositionValueLaunch = netPositionValueLaunch.replaceAll("[^0-9.-]", "").trim();
					if (netPositionValueLaunch.equals("0.00")) {
						report.detailsAppendFolder("Verify net position is 0.00 when launched for loss refresh scenario",
								"Net position should be 0.00", "Net position is 0.00", "Pass", currencyName);
					} else {
						report.detailsAppendFolder("Verify net position is 0.00 when launched for loss refresh scenario",
								"Net position should be 0.00", "Net position is not 0.00", "Fail", currencyName);
					}
					verifyNetPositionValue(report, imageLibrary, currencyName, "NoWin");

					refreshGameAndClickContinueBtn(report, imageLibrary, currencyName);

					netPositionValueLaunch = netPositionValueLaunch.replaceAll("[^0-9.-]", "").trim();
					if (netPositionValueRefresh.equals("0.00")) {
						report.detailsAppendNoScreenshot(
								"Verify net position is 0.00 when refreshed after loss for loss refresh scenario",
								"Net position should be 0.00", "Net position is 0.00", "Pass");
					} else {
						report.detailsAppendFolder(
								"Verify net position is 0.00 when refreshed after loss for loss refresh scenario",
								"Net position should be 0.00", "Net position is not 0.00", "Fail", currencyName);
					}

					if (imageLibrary.isImageAppears("Spin")) {
						imageLibrary.click("Spin");
						Thread.sleep(5000);
						imageLibrary.waitTillImageVisible("Spin");
					}
					if (imageLibrary.isImageAppears("Spin")) {
						imageLibrary.click("Spin");
						Thread.sleep(12000);
						imageLibrary.waitTillImageVisible("Spin");
					}

					verifyNetPositionValue(report, imageLibrary, currencyName, "NoWin");

				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		}

		/**
		 * This method is used to check if net posotion is available or not
		 */
		public boolean isNetProfitAvailable() {
			boolean isNetProfitAvailable = false;
			try {
				isNetProfitAvailable = webdriver.findElement(By.xpath(xpathMap.get("isNetPosition"))).isDisplayed();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return isNetProfitAvailable;
		}
		
		public void spinCases(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
			try {
				setChromiumWebViewContext();
				// added for test data related
				String gameurl = webdriver.getCurrentUrl();
				
				webdriver.context("NATIVE_APP");
				loadGameAndClickContinueBtn(gameurl, report, imageLibrary, languageCode);

				
				if (imageLibrary.isImageAppears("Spin")) {
					imageLibrary.click("Spin");
					report.detailsAppendFolder("Verify Spins ", " Spin 1", " Spin 1 Should be done", "Pass", languageCode);

					imageLibrary.waitTillImageVisible("Spin");
					imageLibrary.click("Spin");
					Thread.sleep(1000);
					report.detailsAppendFolder("Verify Spins ", " Spin 2", " Spin 2 Should be done", "Pass", languageCode);

					imageLibrary.waitTillImageVisible("Spin");
					imageLibrary.click("Spin");
					Thread.sleep(1000);
					report.detailsAppendFolder("Verify Spins ", " Spin 3", " Spin 3 Should be done", "Pass", languageCode);
					;
					imageLibrary.waitTillImageVisible("Spin");
				}
			} catch (Exception e) {
				e.getMessage();
				log.error(e);
			}
		}

		public void loadGamewithCredits(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode) {
			try {
				setChromiumWebViewContext();
				// added for test data related
				String gameurl = webdriver.getCurrentUrl();
				
				webdriver.context("NATIVE_APP");
				loadGameAndClickContinueBtn(gameurl, report, imageLibrary, languageCode);

				if (imageLibrary.isImageAppears("Spin")) {
					imageLibrary.click("Spin");
					report.detailsAppendFolder("Verify Spins with updated balance ", " Spin 1", " Spin 1 Should be done",
							"Pass", languageCode);

					imageLibrary.waitTillImageVisible("Spin");
					imageLibrary.click("Spin");
					Thread.sleep(1000);
					report.detailsAppendFolder("Verify Spins with updated balance ", " Spin 2", " Spin 2 Should be done",
							"Pass", languageCode);

					imageLibrary.waitTillImageVisible("Spin");
					imageLibrary.click("Spin");
					Thread.sleep(1000);
					report.detailsAppendFolder("Verify Spins with updated balance ", " Spin 3", " Spin 3 Should be done",
							"Pass", languageCode);

				}
			} catch (Exception e) {
				e.getMessage();
				log.error(e);
			}
		}

		public void loadGamewithoutCredits(Mobile_HTML_Report report, ImageLibrary imageLibrary, String languageCode,
				String userName, String balance) {
			try {
				CommonUtil util = new CommonUtil();
				setChromiumWebViewContext();
				String newBalance = xpathMap.get("newBalance");
				util.updateUserBalance(userName, newBalance);

				Thread.sleep(3000);
				// added for test data related
				
				String gameurl = webdriver.getCurrentUrl();
				
				webdriver.context("NATIVE_APP");
				loadGameAndClickContinueBtn(gameurl, report, imageLibrary, languageCode);
				if (imageLibrary.isImageAppears("Spin")) {
					imageLibrary.click("Spin");
					Thread.sleep(3000);
					if (elementVisible_Xpath(xpathMap.get("insufficientBalanceOverlay"))) {
						report.detailsAppendFolder("Verify Spins without Balance ",
								" Insufficient Balance pop up should be displayed ",
								"Insufficient Balance pop up is displayed", "Pass", languageCode);
					} else {
						report.detailsAppendFolder("Verify Spins without Balance ",
								" Insufficient Balance pop up should be displayed ",
								"Insufficient Balance pop up is not displayed", "Fail", languageCode);
					}

				}
				util.updateUserBalance(userName, balance);
				loadGameAndClickContinueBtn(gameurl, report, imageLibrary, languageCode);
			} catch (Exception e) {
				e.getMessage();
				log.error(e);
			}
		}
		
		/**
		 * This method is used for making depoit via banking option and verifying credit
		 * after deposit
		 * 
		 * @param report
		 * @param userName
		 * @param balance
		 * @param currencyName
		 * @param imageLibrary
		 */
		public boolean creditDepositUsingBanking(Mobile_HTML_Report report, String userName, String balance,
				String currencyName, ImageLibrary imageLibrary, String currencySymbol) {
			String newCreditValue = null;
			Wait = new WebDriverWait(webdriver, 180);
			boolean isUpdated = false;
			try {
				setChromiumWebViewContext();
				CommonUtil util = new CommonUtil();
				String gameurl = webdriver.getCurrentUrl();

				String newBalance = xpathMap.get("newBalance");
				util.updateUserBalance(userName, newBalance);

				loadGameAndClickContinueBtn(gameurl, report, imageLibrary, currencyName);
				setChromiumWebViewContext();

				newCreditValue = getConsoleText("return " + xpathMap.get("CreditValue"));

				String value = newCreditValue.replaceAll("[^0-9.-]", "").trim();
				System.out.println("value " + value);

				if (value.equals("0.00")) {
					report.detailsAppendFolder("Verify credit value ", "Credit amount should be 0", "Credit amount is 0",
							"Pass", currencyName);

				} else {
					report.detailsAppendFolder("Verify credit value ", "Credit amount should be 0",
							"Credit amount is not 0", "Fail", currencyName);

				}

				if (imageLibrary.isImageAppears("Spin")) {
					imageLibrary.click("Spin");
					Thread.sleep(3000);

					if (elementVisible_Xpath(xpathMap.get("insufficientBalanceOverlay"))) {
						report.detailsAppendFolder("Verify if the Insufficient balance dialog box appears",
								" Insufficient balance dialog box should appears",
								" Insufficient balance dialog box is visible", "Pass", currencyName);

						webdriver.findElement(By.xpath(xpathMap.get("bankingYes"))).click();
						Thread.sleep(5000);

						if (elementVisible_Xpath(xpathMap.get("isBankingVisible"))) {
							report.detailsAppendFolder("Verify if the Banking is open", "Banking should be open",
									"Banking is open", "Pass", currencyName);

							webdriver.navigate().refresh();
							Thread.sleep(10000);
							log.debug("Actual user: " + userName + "Entered user: " + userName);
							webdriver.findElement(By.xpath(xpathMap.get("bankingLoginName"))).sendKeys(userName);
							Thread.sleep(10000);
							webdriver.findElement(By.xpath(xpathMap.get("bankingBalance"))).sendKeys(balance);
							Thread.sleep(2000);
							Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMap.get("bankingSubmit"))));

							report.detailsAppendFolder("Verify if values entered correclty", "Values should display",
									"Values are visible", "Pass", currencyName);

							try {
								webdriver.findElement(By.xpath(xpathMap.get("bankingSubmit"))).click();
							} catch (Exception e) {
								report.detailsAppendFolder("Unable to click on submit button ",
										"Unable to click on submit button ", "Unable to click on submit button ", "Fail",
										currencyName);
								log.error(e.getMessage(), e);
							}

							loadGameAndClickContinueBtn(gameurl, report, imageLibrary, currencyName);

							String updatedCreditValue = getConsoleText("return " + xpathMap.get("CreditValue"));
							String credit = updatedCreditValue.replaceAll("[^0-9.-]", "").trim();
							if (credit.equals(balance)) {
								report.detailsAppendFolder("Verify credit value ",
										"Credit amount should be updated using banking deposit",
										"Credit amount is updated using banking deposit", "Pass", currencyName);
								isUpdated = true;
							} else {
								report.detailsAppendFolder("Verify credit value ",
										"Credit amount should be updated using banking deposit",
										"Credit amount is not updated using banking deposit", "Fail", currencyName);
							}

						} else {
							report.detailsAppendFolder("Verify if the Banking is open", "Banking should be visible",
									"Banking is not visible", "Fail", currencyName);
						}
					} else {
						report.detailsAppendFolder("Verify if the Insufficient balance dialog box appears",
								" Insufficient balance dialog box should appears",
								" Insufficient balance dialog box is not visible", "Fail", currencyName);

					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return isUpdated;
		}

}
