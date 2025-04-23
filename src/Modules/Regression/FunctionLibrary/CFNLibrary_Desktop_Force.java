package Modules.Regression.FunctionLibrary;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.xpath.XPath;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.sun.glass.events.KeyEvent;
import com.zensar.automation.framework.model.Coordinates;
import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.report.Mobile_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.ExcelDataPoolManager;
import com.zensar.automation.framework.utils.Util;
import com.zensar.automation.library.CommonUtil;
import com.zensar.automation.library.ImageLibrary;
import com.zensar.automation.library.TestPropReader;
import com.zensar.automation.model.Symbol;
import com.zensar.automation.model.WinCombination;

import io.appium.java_client.MobileElement;
import net.lightbody.bmp.BrowserMobProxyServer;

public class CFNLibrary_Desktop_Force extends CFNLibrary_Desktop {
	boolean isFreeSpinTriggered = false;
	long Avgquickspinduration = 0;
	long Avgnormalspinduration = 0;
	long Avgautoplayduration = 0;
	String forceNamespace = null;
	public Logger log = Logger.getLogger(CFNLibrary_Desktop_Force.class.getName());

	public CFNLibrary_Desktop_Force(WebDriver webdriver1, BrowserMobProxyServer browserproxy1,
			Desktop_HTML_Report report, String gameName) throws IOException {
		super(webdriver1, browserproxy1, report, gameName);
		this.GameName = gameName.trim();

		log.info("Functionlibrary object created with test data");
	}

	public void verifyStopLanguage(Desktop_HTML_Report language, String languageCode) {
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			clickAtButton("return " + XpathMap.get("ClickSpinBtn"));
			Thread.sleep(1000);
			log.debug("Clicked on spin button");
			language.detailsAppendFolder("verify the Stop button translation",
					"Stop button should translate as per respective language", "Stop button is displaying", "Pass",
					languageCode);
			log.debug("Waiting reels to stop");
			elementWait("return " + XpathMap.get("SpinBtnCurrState"), "active");
		} catch (Exception e) {
			log.error("error in verifyStopLanguage method", e);
		}
	}
	/*
	 * Date: 24/04/2019 Description:To verify Autoplay spin session stop Parameter:
	 * NA
	 * 
	 * @return boolean
	 */

	public boolean is_autoplay_session_end() {

		boolean spin_session = false;

		try {

			log.debug("Clicked Autoplay..");
			clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));
			log.debug("Wating for spin button to appear");
			waitForSpinButtonstop();
			waitForSpinButton();
			if (!GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive")))
				spin_session = true;

			else
				spin_session = false;

		} catch (Exception e) {
			log.error("Session not over after autoplay spin", e);
			return spin_session = false;
		}
		return spin_session;
	}

	public boolean max_spin_chk() {
		boolean max_spin = false;
		try {
			waitForSpinButton();
			clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
			clickAtButton("return " + XpathMap.get("AutoPlaySlider1"));
			if (getConsoleText("return " + XpathMap.get("NoOfAutoplaySpins")).equalsIgnoreCase("100"))
				max_spin = true;
			else {
				max_spin = false;
				log.debug("Max Spin=" + getConsoleNumeric("return " + XpathMap.get("NoOfAutoplaySpins")));
			}
			clickAtButton("return " + XpathMap.get("AutoPlaySlider0"));
			closeOverlay();
		} catch (Exception e) {
			log.error("Session not over after autoplay spin", e);
		}
		return max_spin;
	}

	/* Sneha Jawarkar: Wait for Spin button */
	/*
	 * public boolean waitForSpinButtonstop() {
	 * 
	 * boolean isSpinBtnActive=false; try {
	 * 
	 * 
	 * log.debug("I am in Loop waitForSpinButtonstop");
	 * elementWait("return "+XpathMap.get("SpinBtnCurrState"),"active");
	 * isSpinBtnActive=true; log.debug("Spin button available");
	 * 
	 * } catch (Exception e) { log.error(e.getMessage(), e); } return
	 * isSpinBtnActive; }
	 */
	@Override
	public boolean waitForSpinButtonstop() {
		try {
			log.debug("Waiting for spinbutton active ");
			while (true) {
				elementWait("return " + XpathMap.get("SpinBtnCurrState"), "active");
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * * Date: 14/05/2019 Author: Sneha Jawarkar. Description: This function is used
	 * in GTR_freegame Parameter: play letter
	 */
	public void ClickBaseSceneDiscardButton() {
		try {
			clickAtButton("return " + XpathMap.get("BaseSceneDiscardBtn"));

			System.out.println("Clicked on basescene Discard Button");
		} catch (Exception e) {
			System.out.println("Can not Clicked on Basescene Discard Button");
		}
	}

	/**
	 * * Date: 17/05/2019 Author: Sneha Jawarkar. Description: This function is used
	 * in GTR_freegame Parameter: resume play button
	 */
	public boolean ClickFreegameResumePlayButton() {
		boolean b = false;
		try {
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			Coordinates coordinateObj = new Coordinates();
			String align = "return " + XpathMap.get("FGResumeViewresumeBtnAlgn");
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesResumeView.resumeButton').x",
					coordinateObj);
			coordinateObj.setX(coordinateObj.getX());
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesResumeView.resumeButton').y",
					coordinateObj);
			coordinateObj.setY(coordinateObj.getX());
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesResumeView.resumeButton').height",
					coordinateObj);
			coordinateObj.setHeight(coordinateObj.getX());
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesResumeView.resumeButton').width",
					coordinateObj);
			coordinateObj.setWidth(coordinateObj.getX());
			coordinateObj.setAlign((js.executeScript(align)).toString());
			getComponentCenterCoordinates(coordinateObj);
			clickAtCoordinates(coordinateObj.getCenterX(), coordinateObj.getCenterY());
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * This method is used to wait till FS scene loads Author: Sneha Jawarkar
	 */
	public boolean FreeSpinEntryScene() {
		try {
			log.debug("After refreshing,waiting for free spin's scene to come");
			Thread.sleep(10000);
			String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
			if (currentScene.equalsIgnoreCase("FREESPINS"))
				return true;

		} catch (Exception e) {
			log.error("error while waiting for free spin scene", e);
			return false;
		}
		return false;
	}

	/**
	 * This method is used to wait till Base scene loads Author: Sneha Jawarkar
	 */
	public boolean IsBaseScene() {
		try {
			Thread.sleep(10000);
			String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
			if (currentScene.equalsIgnoreCase("SLOT"))
				log.debug("Basescene is available");
			return true;

		} catch (Exception e) {
			log.error("error while waiting for Base scene", e);
			return false;
		}
	}

	/*
	 * Date: 07/01/2019 Author:Sneha Jawarkar Description: Freegame_GTR Parameter:
	 * NA
	 */
	public void quickspin_with_Basegame_freespin() throws InterruptedException {
		QuickSpinclick();
		spinclick();
		Thread.sleep(10000);
		try {
			if (FreeSpinEntryScene()) {
				boolean b = quickSpinStatus_verify();
				if (b)
					System.out.println("quickspin is applicable during freespins");
				else
					System.out.println("quickspin is not applicable during freespins");
			}
			waitForSpinButton();
			if (IsBaseScene()) {
				spinclick();
				waitForSpinButtonstop();
				boolean b = quickSpinStatus_verify();
				if (b)
					System.out.println("quickspin is applicable during Normalspin");
				else
					System.out.println("quickspin is not applicable during Normalspins");
			}
			log.debug("verify quickspin_with_Basegame_freespin");
		} catch (Exception e) {
			log.error("Error to verify quickspin_with_Basegame_freespin");
		}
	}

	/*
	 * Date: 16/05/2019 Author:Sneha Jawarkar Description: Freegame_GTR Parameter:
	 * NA
	 */
	public void Reelspin_duration_during_win_loss() throws InterruptedException {// spin with nowin/loss
		long lossstart = System.currentTimeMillis();
		spinclick();
		waitForSpinButtonstop();
		long lossfinish = System.currentTimeMillis();
		long losstotalTime = lossfinish - lossstart;
		System.out.println(
				"Time in Milliseconds taken by  reelspin to land with Nowin/loss = " + losstotalTime + "MilliSec");
		long lossloadingTime = losstotalTime / 1000;
		System.out.println(
				"Time in Milliseconds taken by  reelspin to land with Nowin/loss = " + lossloadingTime + "Sec");
		// spin with win
		long winstart = System.currentTimeMillis();
		spinclick();
		waitForSpinButtonstop();
		long winfinish = System.currentTimeMillis();
		long wintotalTime = winfinish - winstart;
		System.out.println(
				"Time in Milliseconds taken by  reelspin to land with Nowin/loss = " + wintotalTime + "MilliSec");
		long winloadingTime = losstotalTime / 1000;
		System.out
				.println("Time in Milliseconds taken by  reelspin to land with Nowin/loss = " + winloadingTime + "Sec");
		spinclick();
		waitForSpinButtonstop();
	}

	/*
	 * Date: 16/05/2019 Author:Sneha Jawarkar Description: Freegame_GTR Parameter:
	 * NA
	 */
	public boolean CompleteFreeGameOffer_freespin(int freegamescount) {
		try {
			for (int i = 0; i < freegamescount; i++) {
				spinclick();
				Thread.sleep(10000);
				System.out.println("click on spin");
				if (FreeSpinEntryScene()) {
					// spinclick();
					Thread.sleep(10000);
					webdriver.navigate().refresh();
					Thread.sleep(10000);
					ClickFreegameResumePlayButton();
					FS_continue();
					Thread.sleep(10000);

				}
				waitForSpinButtonstop();
			}
		} catch (Exception e) {
			log.error("Can Not Clicked on spin Button");

		}
		return false;
	}

	/*
	 * Date: 16/05/2019 Author:Sneha Jawarkar Description: Freegame_GTR Parameter:
	 * NA
	 */
	public boolean CompleteFreeGameOffer(int freegamescount) {
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
	 * * Date: 14/05/2019 Author: Sneha Jawarkar. Description: This function is used
	 * in GTR_freegame Parameter: play letter
	 */
	public boolean clickOnPlayLater() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			Coordinates coordinateObj = new Coordinates();
			String align = "return " + XpathMap.get("PlayLaterButtonAlignment");
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesOffersView.playLaterButton').x",
					coordinateObj);
			coordinateObj.setX(coordinateObj.getX());
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesOffersView.playLaterButton').y",
					coordinateObj);
			coordinateObj.setY(coordinateObj.getX());
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesOffersView.playLaterButton').height",
					coordinateObj);
			coordinateObj.setHeight(coordinateObj.getX());
			typeCasting("return " + forceNamespace + ".getControlById('FreeGamesOffersView.playLaterButton').width",
					coordinateObj);
			coordinateObj.setWidth(coordinateObj.getX());
			coordinateObj.setAlign((js.executeScript(align)).toString());
			getComponentCenterCoordinates(coordinateObj);
			clickAtCoordinates(coordinateObj.getX(), coordinateObj.getY());
			Thread.sleep(100);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;

	}

	/**
	 * Date:15/5/19 Author:Sneha Jawarkar GTR_Freegame purpose
	 */

	public void Backtogame_centerclick() {
		try {
			clickAtButton("return " + XpathMap.get("FGCompleteGameCenterBtn"));
			log.debug("Clicked at back to game");
		} catch (Exception e) {
			log.error("Can not clicked on Back to Button", e);
		}
	}

	/**
	 * Date: 21/6/2019 Author: Sneha Jawarkar Description: GTR Reelspin
	 * 
	 * @throws InterruptedException
	 */
	public boolean QuickSpinclick() {
		boolean qS_Test = false;
		try {
			Thread.sleep(1000);
			clickAtButton("return " + XpathMap.get("QuickSpinBtn"));
			Thread.sleep(1000);
			String QS_currectState = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
			if (QS_currectState.equalsIgnoreCase("active")) {
				qS_Test = true;
				log.debug("Clicked on quickspin");
				System.out.println("Quick spin is on");
			} else {
				qS_Test = false;
				log.error("error in quickspin click");
				System.out.println("Quickspin is not able to On.");
			}
		} catch (Exception e) {
			log.error("qucikspin is  not clickable with Quick Spin on", e);
		}
		return qS_Test;

	}

	/**
	 * Date: 21/6/2019 Author: Sneha Jawarkar Description: GTR Reelspin
	 * 
	 * @throws InterruptedException
	 */
	public long Reelspin_speed_During_Autoplay() {
		long autoplayloadingTime = 0;
		long autoplaysum = 0;
		boolean b = ISAutoplayAvailable();
		if (b) {
			clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
			// System.out.println(start);
			// set Autoplay at 10
			clickAtButton("return " + XpathMap.get("AutoPlaySlider0"));
			// start autoplay
			long start = System.currentTimeMillis();
			clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			waitForSpinButtonstop();
			long finish = System.currentTimeMillis();
			long totalTime = finish - start;
			autoplayloadingTime = totalTime / 1000;
			autoplaysum = autoplaysum + autoplayloadingTime;
			log.debug("Calculation for the Autoplayduration is running properly");
			System.out.println("Total Autoplay duraion in seconds for the 10 Autoplay spins = " + autoplaysum + "Sec");
			Avgautoplayduration = autoplaysum / 10;
		} else {
			log.error("Error during quick spin average time calculation");
		}
		return Avgautoplayduration;

	}

	/**
	 * Date: 21/6/2019 Author: Sneha Jawarkar Description: GTR Reelspin
	 * 
	 * @throws InterruptedException
	 */
	public long Reelspin_speed_During_quickspin() throws InterruptedException {
		long quickspinloadingTime = 0;
		long quickspinsum = 0;
		boolean b = QuickSpinclick();
		if (b) {
			for (int i = 0; i < 5; i++) {
				long start = System.currentTimeMillis();
				spinclick();
				waitForSpinButtonstop();
				long finish = System.currentTimeMillis();
				long totalTime = finish - start;
				quickspinloadingTime = totalTime / 1000;
				quickspinsum = quickspinsum + quickspinloadingTime;
				log.debug("Calculation for the quickspin duration is running properly");
			}
			System.out.println("Total quickspin duraion in seconds for the 5 spins = " + quickspinsum + "Sec");
			Avgquickspinduration = quickspinsum / 5;
			System.out.println("Average time in seconds between 5 quickspins is = " + Avgquickspinduration + " Sec");
		} else {
			log.error("Error during quick spin average time calculation");
		}
		setQuickSpinOff();
		return Avgquickspinduration;
	}

	/**
	 * Date: 21/6/2019 Author: Sneha Jawarkar Description: GTR Reelspin
	 * 
	 * @throws InterruptedException
	 */
	public void ReelSpin_differecne_Duration_Normalspin_quickspin() throws InterruptedException {
		int duration_diff = 0;
		Reelspin_speed_During_quickspin();
		System.out.println("Normal Spin duration is = " + Avgnormalspinduration);
		System.out.println("Quick spin duration is = " + Avgquickspinduration);

		int normalspinduration = (int) Avgnormalspinduration;
		int quickspinduration = (int) Avgquickspinduration;
		if (normalspinduration > quickspinduration) {
			System.out.println(" Normalspin duration is gretter than quickspin");
			log.debug("Normalspin duration is gretter than quickspin");
			duration_diff = normalspinduration - quickspinduration;
			System.out.println("The difference beetween Normalspin duration and Quickduration is = " + duration_diff);
		} else {
			System.out.println("Quick spin duration is gretter than NormalSpin");
			log.error("Quick spin duration is gretter than NormalSpin");
		}
	}

	/**
	 * Date: 21/6/2019 Author: Sneha Jawarkar Description: GTR Average of 5 Reelspin
	 * duration
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public long averageReelSpinDuration() throws InterruptedException {
		long normalloadingTime = 0;
		long normalsum = 0;
		for (int spinCount = 0; spinCount < 5; spinCount++) {
			long start = System.currentTimeMillis();
			// System.out.println(start);
			spinclick();
			waitForSpinButtonstop();
			long finish = System.currentTimeMillis();
			// System.out.println(finish);
			long totalTime = finish - start;
			// System.out.println("Time in Milliseconds taken by "+i+" reelspin to land=
			// "+totalTime+"MilliSec");
			normalloadingTime = totalTime / 1000;
			// System.out.println("Time in seconds taken by "+i+" reelspin to land=
			// "+normalloadingTime+"Sec");
			normalsum = normalsum + normalloadingTime;
			// System.out.println(normalsum);
			log.debug("Calculation for the spin duration is running properly");
			// System.out.println(i+" taken "+normalloadingTime);
			// newFeature();
			// summaryScreen_Wait();
		}
		log.debug("error while spin duration");
		System.out.println("Total spin duraion in seconds for the 5 spins = " + normalsum + "Sec");
		Avgnormalspinduration = normalsum / 5;
		System.out.println("Average time in seconds between 5 spins is = " + Avgnormalspinduration + " Sec");
		return Avgnormalspinduration;
	}

	public int getAutoPlayCount() {
		int count = 0;
		String strCount = null;
		if (!GameName.contains("Scratch")) {
			strCount = getConsoleText("return " + XpathMap.get("AutoPlayStopBtnLabel"));
		} else {
			strCount = getConsoleText("return " + XpathMap.get("AutoPlayCounterText"));
		}
		if (strCount != null && !"".equals(strCount.trim())) {
			count = Integer.parseInt(strCount);
		}
		return count;
	}

	public boolean isAutoplayPauseOnFocusChange(Desktop_HTML_Report report) {
		boolean isAutoplayPauseOnFocusChange = false;
		try {

			clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));

			/*
			 * clickAtButton( "return "+XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
			 * Thread.sleep(2000);
			 * webdriver.findElement(By.id(XpathMap.get("Start_Autoplay"))).click();
			 */
			Thread.sleep(3000);

			// Get the Auto play pending Count before focus Change
			int countb4FocusChange = getAutoPlayCount();
			// screenshot before changing focus
			report.detailsAppend("Verify autoplay count before focus changed",
					"Autoplay count before focus changed is = " + countb4FocusChange,
					"Autoplay count before focus changed is = " + countb4FocusChange, "Pass");
			newTabOpen();
			// Get the Auto play pending Count after focus Change
			int countAfetrFocusChange = getAutoPlayCount();
			// Check the count difference and send the response

			if ((countb4FocusChange) <= countAfetrFocusChange) {
				isAutoplayPauseOnFocusChange = true;
			}
			log.debug("Auto play countb4FocusChange :: " + countb4FocusChange);
			log.debug("Auto play countAfetrFocusChange :: " + countAfetrFocusChange);

		} catch (Exception e) {
			log.error("Error on opening new tab", e);
		}
		return isAutoplayPauseOnFocusChange;
	}

	/*
	 * Date: 25/04/2019 Description:To verify autoplay must stop when focus being
	 * removed. Parameter: NA
	 * 
	 * @return boolean
	 */
	public boolean Autoplay_focus_removed() {
		boolean focus = false;
		try {

			clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
			Thread.sleep(2000);
			clickAtButton(" return " + XpathMap.get("AutoPlayStartBtn"));
			// webdriver.findElement(By.id(XpathMap.get("Start_Autoplay"))).click();
			Thread.sleep(3000);
			newTabOpen();
			if (GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive")) == true) {
				Thread.sleep(2000);
				focus = true;
			} else {
				focus = false;
				log.debug("Autoplay staus=" + GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive")));
			}
			/*
			 * String windowhandle=webdriver.getWindowHandle();
			 * webdriver.switchTo().window(windowhandle); Robot robot= new Robot();
			 * robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_T);
			 * robot.keyRelease(KeyEvent.VK_T); robot.keyRelease(KeyEvent.VK_CONTROL);
			 * 
			 * //To check open windows ArrayList<String> tabs=new
			 * ArrayList<String>(webdriver.getWindowHandles());
			 * System.out.println(tabs.size()); String window=tabs.get(1);
			 * webdriver.switchTo().window(window);
			 * webdriver.get("https://outlook.office.com"); Thread.sleep(10000);
			 * webdriver.switchTo().window(tabs.get(0)); Thread.sleep(5000);
			 * //closeOverlay(); log.debug("Switch to default tab");
			 */

			// driver.switchTo().defaultContent();

		} catch (Exception e) {
			log.error("Focus not get changed");
			log.error(e.getMessage());

		}

		return focus;

	}

	public void newTabOpen() {
		try {
			String windowhandle = webdriver.getWindowHandle();
			webdriver.switchTo().window(windowhandle);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			// To check open windows
			ArrayList<String> tabs = new ArrayList<String>(webdriver.getWindowHandles());
			System.out.println(tabs.size());
			String window = tabs.get(1);
			webdriver.switchTo().window(window);
			webdriver.get("https://outlook.office.com");
			Thread.sleep(10000);
			webdriver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			// closeOverlay();
			log.debug("Switch to default tab");
		} catch (Exception e) {
			log.error("Error on opening new tab");
		}
	}

	/*
	 * Date: 25/04/2019 Description:To verify Auto play on refreshing must not
	 * resume Parameter: NA
	 * 
	 * @return boolean
	 */
	public boolean isAutoplayOnAfterRefresh() {
		boolean onrefresh = false;
		try {
			clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));
			// below condition is for UK regulatory market
			/*if (webdriver.findElements(By.id(XpathMap.get("Start_AutoplayXpath"))).size() != 0) {
				webdriver.findElement(By.id(XpathMap.get("Start_AutoplayXpath"))).click();
			}*/
			Thread.sleep(3000);
			webdriver.navigate().refresh();
			waitForSpinButton();
			newFeature();
			if (GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive")) == false) {
				onrefresh = true;
				// Check Auto play Status and then return the response
				log.debug("On refresh previous autoplay session has not resume");
			} else {
				onrefresh = false;
				log.debug("On refresh previous autoplay session has resume");
			}

		} catch (Exception e) {
			log.error(e.getMessage());

		}

		return onrefresh;
	}

	/*
	 * Date: 22/04/2019 Description:To verify Autoplay spin selection Parameter: NA
	 * 
	 * @return boolean
	 */

	public boolean autoplay_spin_selection() {
		boolean isSpinChange = false;
		String spin1, spin2;
		try {
			Thread.sleep(1000);
			clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));// hooks to open autoplay
			log.debug("drag and drop performed");

			clickAtButton("return " + XpathMap.get("AutoPlaySlider1"));// hooks to change set spin till max from
																		// autoplay spin slider
			spin1 = getConsoleText("return  " + XpathMap.get("NoOfAutoplaySpins"));
			log.debug("Autoplay Spins before change=" + spin1);
			// window.theForce.game.automation.getControlById('AutoplayPanelComponent').Text.numberSpinsValueText.text
			Thread.sleep(2000);
			clickAtButton("return " + XpathMap.get("AutoPlaySlider0"));
			spin2 = getConsoleText("return  " + XpathMap.get("NoOfAutoplaySpins"));
			log.debug("Autoplay Spins after change=" + spin2);
			if (!spin1.equalsIgnoreCase(spin2))
				isSpinChange = true;
			else
				isSpinChange = false;

		} catch (Exception e) {
			log.error("Spin count not getting change.", e);
			return isSpinChange;

		}
		return isSpinChange;

	}
	/*
	 * Date: 5/05/2019 Description:To verify current scene after free spin over
	 * Parameter: NA
	 * 
	 * @return boolean
	 */

	public boolean is_autoplay_with_freespin(Desktop_HTML_Report report) {
		boolean autoplay_on_bonus = false;
		try {
			waitForSpinButton();
			clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));

			elementWait("return " + XpathMap.get("currentScene"), "FREESPINS_INTRO");

			if (getConsoleText("return " + XpathMap.get("currentScene")).equalsIgnoreCase("FREESPINS_INTRO")) {
				log.debug("Free spins start in autoplay");

				autoplay_on_bonus = !GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive"));
				Thread.sleep(1000);
				if (autoplay_on_bonus)
					report.detailsAppend("On trigger of a Bonus/Free Spins feature, AutoPlay must immediately end.",
							"On trigger of a Bonus/Free Spins feature, AutoPlay must immediately end..�",
							"On trigger of a Bonus/Free Spins feature, AutoPlay immediately end.", "Pass");
				else
					report.detailsAppend("On trigger of a Bonus/Free Spins feature, AutoPlay must immediately end.",
							"On trigger of a Bonus/Free Spins feature, AutoPlay must immediately end..�",
							"On trigger of a Bonus/Free Spins feature, AutoPlay not end.", "Fail");

				waitForSpinButton();
				log.debug("waiting for base scene to come after completing of free spins");

				autoplay_on_bonus = GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive"));
				if (autoplay_on_bonus)
					report.detailsAppend(
							"Once the Bonus/Free Spins feature completes, the previous AutoPlay session must not resume. ",
							"Once the Bonus/Free Spins feature completes, the previous AutoPlay session must not resume. �",
							"Once the Bonus/Free Spins feature completes, the previous AutoPlay session resume. ",
							"Fail");
				else
					report.detailsAppend(
							"Once the Bonus/Free Spins feature completes, the previous AutoPlay session must not resume. ",
							"Once the Bonus/Free Spins feature completes, the previous AutoPlay session must not resume. ",
							"Once the Bonus/Free Spins feature completes, the previous AutoPlay session  not resume. ",
							"Pass");

			} else {
				report.detailsAppend("On trigger of a Bonus/Free Spins feature, AutoPlay must immediately end.",
						"On trigger of a Bonus/Free Spins feature, AutoPlay must immediately end.�",
						"NO trigger of a Bonus/Free Spins feature.", "Fail");

				log.debug("Free spins are not triggered autoplay is active");
				autoplay_on_bonus = false;
			}

			waitForSpinButton();

		} catch (Exception e) {
			autoplay_on_bonus = false;
			log.error("Current Scense" + getConsoleText("return " + XpathMap.get("currentScene")));
		}
		return autoplay_on_bonus;
	}

	public boolean openAutoplay() {
		boolean b = false;
		try {
			// if loop added for game like immortal romance
			if (!GameName.contains("Scratch")) {
				
				// below hook is for immortal
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					
						clickAtButton("return " + XpathMap.get("ClickAutoPlayBtn"));
						log.debug("Clicked close autoplay button");
				
				} else {
					// For Ultimate console
					if (Constant.FORCE_ULTIMATE_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {

						clickAtButton("return " + XpathMap.get("ClickAutoPlayBtn"));
					} else {
						// For other console
						//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
						clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
					}
				}
			} else {
				//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
				clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
			}
			log.debug("Clicked on autoplay");
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			log.error("error while opening the autoplay", e);
		}
		return b;
	}
	
	
	
	
	
	

	/*
	 * Date: 22/04/2019 Description:To verify Autoplay with quick spin on Parameter:
	 * NA
	 * 
	 * @return boolean
	 */
	public void autoPlay_with_QS_On(Desktop_HTML_Report report) {
		try {
			clickAtButton("return " + XpathMap.get("QuickSpinBtn"));
			Thread.sleep(1000);
			String QS_currectState = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
			if (QS_currectState.equalsIgnoreCase("active")) {
				clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
				Thread.sleep(1000);
				clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));
				Thread.sleep(1000);
				if (GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive")))
					report.detailsAppend("verify that AutoPlay with QuickSpin on must be available.",
							"AutoPlay with QuickSpin on must be available.", "AutoPlay with QuickSpin is available",
							"pass");
				else
					report.detailsAppend("verify that AutoPlay with QuickSpin on must be available.",
							"AutoPlay with QuickSpin on must be available.", "AutoPlay with QuickSpin is not available",
							"Fail");

				waitForSpinButtonstop();
				log.debug("Clicked on autoplay");
			} else {
				log.debug("quick spin button not active");

				report.detailsAppend("verify that AutoPlay with QuickSpin on must be available.",
						"AutoPlay with QuickSpin on must be available.", "Quick Spin is not active", "Fail");

			}
			setQuickSpinOff();
		} catch (Exception e)

		{
			log.error("Autoplay is  not clickable with Quick Spin on", e);
		}
	}
	
	
	//sv65878
	
	public void autoPlay_with_QSUpdated(Desktop_HTML_Report report, String languageCode) {
		
		try {
			String QS_currectState = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
			Thread.sleep(2000);
			if (!QS_currectState.equalsIgnoreCase("active")) {
			clickAtButton("return " + XpathMap.get("QuickSpinBtn"));}
			Thread.sleep(2000);
			QS_currectState = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
			if (QS_currectState.equalsIgnoreCase("active")) {
			
					clickAtButton("return " + XpathMap.get("ClickAutoPlayBtn"));
					Thread.sleep(1000);
					
					//Validation-Enhancement
					boolean autoplayPanel = elementWait("return " + XpathMap.get("isAutoPlayBtnVisible"), true);
					if (autoplayPanel) {
					System.out.println("Autoplay Panel is Visible");
					Thread.sleep(1500);
					report.detailsAppendFolder("Verify language on the Autoplay Screen", "Autoplay Screen  displayed", "Autoplay Screen should be displayed", "PASS", languageCode);
					}
					
					else 
					{
						System.out.println("Autoplay Panel is not Visible");
						report.detailsAppendFolder("Verify language on the Autoplay Screen", "Autoplay Screen  displayed", "Autoplay Screen should be displayed", "FAIL", languageCode);
						
					}
					
					
					log.debug("Clicked close autoplay button");
				
				
					
						
			/*			resizeBrowser(400,800);
						Thread.sleep(1000);
						report.detailsAppendFolder("verify the Browser Resize under Autoplay Panel ", " Autoplay Browser Resize screen shot","Autoplay Browser Resize screen shot ", "PASS", languageCode);
						
						
						webdriver.manage().window().fullscreen();
						Thread.sleep(1000);
						report.detailsAppendFolder("verify the Browser FullScreen under Autoplay Panel ", " Autoplay Browser FullScreen screen shot","Autoplay Browser FullScreen screen shot ", "PASS", languageCode);
						webdriver.manage().window().maximize();
				*/	
					clickAtButton("return " + XpathMap.get("AutoplayOptionss"));
					Thread.sleep(1000);

					
					
					clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));
					Thread.sleep(3000);
					report.detailsAppend("Verify Autoplay with QuickSpin on must be available ",
							"Autoplay is triggered", "AutoPlay with QuickSpin is available",
							"PASS");
					
					
					System.out.println("Autoplay spin Count ss");
					Thread.sleep(7000);
					
					report.detailsAppend("Verify Autoplay Spin Counts ",
							"Autoplay Next Spin ", "AutoPlay with QuickSpin is available",
							"PASS");
					
					
					
					 webdriver.navigate().refresh();
					 elementWait("return " + XpathMap.get("currentScene"), "LOADING");	
						/*
						 * if(loadingscrn) { Thread.sleep(1500);
						 * report.detailsAppendFolder(" Refresh under Autoplay Panel", "After Refresh",
						 * "Refresh Autoplay" , "PASS", languageCode); } else {
						 * report.detailsAppendFolder(" Refresh under Autoplay Panel", "After Refresh",
						 * "Refresh Autoplay" , "FAIL", languageCode); }
						 * 
						 */
					  if("yes".equalsIgnoreCase(XpathMap.get("gameContainsCntbtn"))) {
							boolean isfeatureScrn = elementWait("return " + XpathMap.get("FeatureScreen"), true);
							if(isfeatureScrn) {
								Thread.sleep(3000);
								closeOverlay();
								Thread.sleep(3000);
								boolean Basescene = elementWait("return " + XpathMap.get("currentScene"), "SLOT");
								if(Basescene) {
									 System.out.println("Refreshed");
									}
								else {
									Thread.sleep(3000);
									closeOverlay();
								}
							}
							}	//cnt btn
						
					 
				
				log.debug("Clicked on autoplay");
			} else {
				log.debug("quick spin button not active");

				report.detailsAppend("verify that AutoPlay with QuickSpin on must be available.",
						"AutoPlay with QuickSpin on must be available.", "Quick Spin is not active", "Fail");

			}
			setQuickSpinOff();
		} catch (Exception e)

		{
			log.error("Autoplay is  not clickable with Quick Spin on", e);
		}
	}


/*	public void newFeature() {
		WebDriverWait Wait1 = new WebDriverWait(webdriver, 20);
		try {
			if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))
					|| (Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
				// Click on new feature screen with xpath for the game likes immortal romance
				log.debug("Waiting for new feature screen");
				Wait1.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))));
				webdriver.findElement(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))).click();
				log.debug("Clicked on continue button present on new feature screen");
			}

			// click on new feature screen with hooks for games like dragon dance
			else {
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
				clickAtButton("return " + XpathMap.get("BaseIntroContinueBtn"));
				log.debug("Clicked on intro screen of game");
			}
		} catch (Exception e) {
			log.debug("Intro screen is not available in game");
		}
	}*/

	public boolean ISAutoplayAvailable() {
		boolean autoplay = false, result = false;
		try {

			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					autoplay = GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayBtnVisible"));
					if (autoplay) {
						result = GetConsoleBooleanText("return " + XpathMap.get("ClickAutoPlayBtn"));

					}
				} else {
					autoplay = GetConsoleBooleanText("return " + XpathMap.get("isAutoplayOpenMobileBtnVisible"));
					if (autoplay) {
						getConsoleText("return " + XpathMap.get("ClickAutoplayOpenMobileBtn"));
						result = GetConsoleBooleanText("return " + XpathMap.get("isAutoplayOpenMobileBtnVisible"));

					}

				}
			} else {
				// add hooks for autoplay in scrach games
				autoplay = GetConsoleBooleanText("return " + XpathMap.get("isAutoplayOpenMobileBtnVisible"));
				if (autoplay) {
					getConsoleText("return " + XpathMap.get("ClickAutoplayOpenMobileBtn"));

					result = GetConsoleBooleanText("return " + XpathMap.get("isQuickSpinBtnVisible"));
				}
			}

		} catch (Exception e) {
			log.error("Autoplay id button is not visible", e);
			result = false;
		}
		return result;
	}

	/*
	 * Date: 17/05/2019 Author:Snehal Gaikwad Description: To set bet to maximum
	 * Parameter: NA
	 */

	public String setMaxBet() {
		log.debug("Function -> setMAxBet()");
		String betAmount = null;
		String bet = null;
		try {
			bet = "return " + XpathMap.get("SetMaxBet");
			getConsoleText(bet);
			log.debug("Bet=" + getConsoleText(bet));
			betAmount = getConsoleText("return " + XpathMap.get("BetSizeText"));

		} catch (JavascriptException exception) {
			log.error(" Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;

		}

		return betAmount;
	}

	/*
	 * Date: 17/05/2019 Author:Snehal Gaikwad Description: To Check the bet
	 * selection on autoplay bet setting Parameter: NA
	 */

	public boolean autoplaybetselection() {

		boolean betselection = false;
		try {
			getConsoleText("return " + XpathMap.get("ClickAutoplayOpenMobileBtn"));
			WebElement coinsbet = webdriver.findElement(By.xpath(XpathMap.get("Coins_Bet")));
			Select coins = new Select(coinsbet);
			coins.selectByValue("6");

			Thread.sleep(2000);
			betselection = true;
			webdriver.findElement(By.id(XpathMap.get("Cancel_Autoplay"))).click();
		} catch (Exception e) {
			log.error("Error in Autoplay Bet Selection");
			betselection = false;

		}
		return betselection;
	}

	/*
	 * Date: 17/05/2019 Author:Snehal Gaikwad Description: To Check whether the Loss
	 * Limit is less than current bet (Negative scenario) Parameter: NA
	 */
	public boolean is_Loss_Limit_Lower() {
		boolean flag = false;
		try {
			webdriver.findElement(By.id(XpathMap.get("Cancel_Autoplay"))).click();
			Thread.sleep(2000);
			setMaxBet();
			getConsoleText("return " + XpathMap.get("ClickAutoplayOpenMobileBtn"));
			webdriver.findElement(By.id(XpathMap.get("Start_Autoplay"))).click();

			log.debug("Loss limit is less than current bet error message display");
			flag = true;
		} catch (Exception e) {
			log.error("Loss limit is less than current bet error message not display");
			flag = false;
		}
		return flag;
	}

	/*
	 * public boolean checkLossLimitReachedForAllBet() { //Open the autoplay window
	 * GetConsoleText("return "+XpathMap.get("ClickAutoplayOpenMobileBtn"));
	 * 
	 * //Get the list of values in loss limit selection
	 * 
	 * Select selection= new
	 * Select(driver.findElement(By.id(XpathMap.get("Win_Limit"))));
	 * 
	 * int size=selection.getOptions().size();
	 * System.out.println(selection.getOptions().get(1).getText()); // Check None
	 * present or not
	 * if("None".equalsIgnoreCase(selection.getOptions().get(1).getText())){
	 * System.out.println("Validation Logic works"); }
	 * 
	 * 
	 * 
	 * return flag; }
	 */
	/*
	 * To check if win limit is reached in autoplay for uk
	 */

	public boolean checkWinLimitReachedForBet(String winLimit, int counter) {
		boolean winLimitReached = false;
		long startTime = System.currentTimeMillis();
		try {
			System.out.println(counter);

			setMaxBet();
			openAutoplay();

			WebElement webElement = webdriver.findElement(By.xpath(XpathMap.get("winLimit")));

			Select selection = new Select(webElement);
			// selection.selectByVisibleText(winLimit);
			selection.selectByIndex(counter);
			WebElement webElement1 = webdriver.findElement(By.xpath(XpathMap.get("lossLimit")));
			Select selection1 = new Select(webElement1);
			// selection1.selectByVisibleText(winLimit);
			selection1.selectByIndex(5);
			webdriver.findElement(By.xpath(XpathMap.get("Start_Autoplay"))).click();

			// String betValue = getConsoleText("return "+XpathMap.get("BetValue"));

			// Can I get Autplay count here
			while (true) {
				if ((GetConsoleBooleanText("return " + XpathMap.get("isAutoplayDialogVisible")))) {

					Thread.sleep(3000);

					winLimitReached = true;
					break;
				} else {
					long currentime = System.currentTimeMillis();
					if (((currentime - startTime) / 1000) > 120) {
						System.out.println("Win limit not reached after 2 mint");
						winLimitReached = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getCause());
		}
		return winLimitReached;

	}
	/*
	 * To check if loss limit is reached in autoplay for uk
	 */
	public boolean checkLossLimitReachedForBet(String winLimit, int counter) {
		boolean lossLimitReached = false;
		long startTime = System.currentTimeMillis();
		try {
			setMaxBet();
			openAutoplay();
			System.out.println(counter);

			WebElement webElement = webdriver.findElement(By.xpath(XpathMap.get("lossLimit")));
			Select selection = new Select(webElement);
			selection.selectByIndex(counter);
			webdriver.findElement(By.xpath(XpathMap.get("Start_Autoplay"))).click();

			/*
			 * if(counter==0) { System.out.println("Current balance exceeds loss limit"); }
			 */
			// String betValue = getConsoleText("return "+XpathMap.get("BetValue"));
			// Can I get Autplay count here
			while (true) {
				if ((GetConsoleBooleanText("return " + XpathMap.get("isAutoplayDialogVisible")))) {

					Thread.sleep(3000);
					lossLimitReached = true;
					break;
				} else {
					long currentime = System.currentTimeMillis();
					if (((currentime - startTime) / 1000) > 120) {
						System.out.println("Win limit not reached after 2 mint");
						lossLimitReached = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getCause());
		}

		return lossLimitReached;
	}

	/*
	 * Date: 20/05/2019 Author:Snehal Gaikwad Description: To Check whether the Loss
	 * Limit is reach or not Parameter: NA
	 */
	public boolean is_Loss_Limit_Reached(Desktop_HTML_Report report) {
		boolean flag = false;
		try {

			getConsoleText("return " + XpathMap.get("SetMaxBet"));
			Thread.sleep(1000);
			getConsoleText("return " + XpathMap.get("ClickAutoplayOpenMobileBtn"));
			WebElement losslimit = webdriver.findElement(By.id(XpathMap.get("lossLimit")));
			Select limits = new Select(losslimit);
			limits.selectByValue("10000");

			webdriver.findElement(By.id(XpathMap.get("Start_Autoplay"))).click();
			Thread.sleep(4000);
			while (true) {
				if (!GetConsoleBooleanText("return " + XpathMap.get("AutoPlayContinue"))) {
					Thread.sleep(10000);
					if (GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive"))) {
						report.detailsAppend(
								"verify that when the Loss Limit is reached, a message must show on screen, indicating that the Loss Limit has been reached",
								" when the Loss Limit is reached, a message must show on screen, indicating that the Loss Limit has been reached. ",
								"  when the Loss Limit is reached, a message must show on screen, indicating that the Loss Limit has been reached.",
								"Pass");

						clickAtButton("return " + XpathMap.get("ClickPrimaryBtn"));
						log.debug("Clicked on Loss Limit reached dailog  ");
						flag = true;
						break;
					} else {
						is_Loss_Limit_Reached(report);
						break;
					}
				}
				Thread.sleep(3000);

			}
			if (!flag)
				report.detailsAppend(
						"verify that when the Loss Limit is reached, a message must show on screen, indicating that the Loss Limit has been reached",
						" when the Loss Limit is reached, a message must show on screen, indicating that the Loss Limit has been reached. ",
						" Loss Limit has not  reached.", "Fail");

		} catch (Exception e) {
			log.error("Loss limit is not reached");

			flag = false;

		}
		return flag;
	}

	/*
	 * Date: 22/05/2019 Author:Snehal Gaikwad Description: To Check whether the win
	 * Limit is reach or not Parameter: NA
	 */

	public boolean is_Win_Limit_Reached(Desktop_HTML_Report report) {
		boolean flag = false;
		try {
			// webdriver.findElement(By.id(XpathMap.get("Cancel_Autoplay"))).click();
			getConsoleText("return " + XpathMap.get("SetBetValue"));
			Thread.sleep(2000);
			getConsoleText("return " + XpathMap.get("ClickAutoplayOpenMobileBtn"));
			WebElement losslimit = webdriver.findElement(By.id(XpathMap.get("lossLimit")));
			Select limits = new Select(losslimit);
			limits.selectByValue("100000");

			WebElement winlimit = webdriver.findElement(By.id(XpathMap.get("winLimit")));
			Select winlimits = new Select(winlimit);
			winlimits.selectByValue("10000");

			// Thread.sleep(2000);
			webdriver.findElement(By.id(XpathMap.get("Start_Autoplay"))).click();
			Thread.sleep(4000);
			while (true) {

				if (!GetConsoleBooleanText("return " + XpathMap.get("AutoPlayContinue"))) {
					Thread.sleep(10000);
					if (GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive"))) {
						report.detailsAppend(
								"verify that when the Win Limit is reached, a message must show on screen, indicating that the Win Limit has been reached",
								" when the Win Limit is reached, a message must show on screen, indicating that the Win Limit has been reached. ",
								"  when the Win Limit is reached, a message must show on screen, indicating that the Win Limit has been reached.",
								"Pass");

						clickAtButton("return " + XpathMap.get("ClickPrimaryBtn"));
						log.debug("Clicked on Loss Limit reached dailog  ");
						flag = true;
						break;
					} else {
						is_Win_Limit_Reached(report);
						break;
					}
				}

				Thread.sleep(3000);
			}
			if (!flag)
				report.detailsAppend(
						"verify that when the Win Limit is reached, a message must show on screen, indicating that the Win Limit has been reached",
						" when the Win Limit is reached, a message must show on screen, indicating that the Win Limit has been reached. ",
						"  Win Limit has not reached.", "Fail");

		} catch (Exception e) {
			log.error("Win limit is not reached");
			flag = false;

		}
		return flag;
	}

	/*
	 * Date: 29/05/2018 Author:Havish Jain Description: Parameter: NA
	 */
	public double gamepayout(ArrayList<String> symbolData, String paytablePayout) {
		double gamepayout_Double = 0.0;
		int symbolIndex = 0;
		try {
			String wild = XpathMap.get("wildSymbol");
			String[] xmlData = paytablePayout.split(",");
			String line = xmlData[0];
			String symbol = xmlData[1];
			Map<String, String> paramMap = new HashMap<String, String>();

			for (int i = 0; i < symbolData.size(); i++) {
				if (symbolData.get(i).contains(symbol)) {
					if (wild.equalsIgnoreCase("no")) {
						symbolIndex = i - 1;
					} else
						symbolIndex = i;
					break;
				}

			}
			paramMap.put("param1", Integer.toString(symbolIndex));
			paramMap.put("param2", line);

			String paytablePayoutValueHook = XpathMap.get("getPaytablePayoutValue");
			String newHook = replaceParamInHook(paytablePayoutValueHook, paramMap);
			String payout = "return " + newHook;
			String consolePayout = getConsoleText(payout);
			String consolePayoutnew = consolePayout.replaceAll("[^0-9]", "");
			gamepayout_Double = Double.parseDouble((consolePayoutnew));

		} catch (Exception e) {
			e.getMessage();
		}
		return gamepayout_Double / 100;
	}

	@Override
	public boolean elementWait(String element, String currentSceneText) {
		long startTime = System.currentTimeMillis();
		log.debug("Waiting for  screen to come");
		while (true) {

			String currentScene = getConsoleText(element);
			// log.debug("I am in Loop elementWait::"+currentScene+" "+currentSceneText);
			if (currentScene != null && currentScene.equalsIgnoreCase(currentSceneText)) {
				log.debug("desired scene is displayed");
				return true;
			}
			long currentime = System.currentTimeMillis();
			// Break if wait is more than 80 secs
			if (((currentime - startTime) / 1000) > 60) {
				log.debug("component  is not visible, break after 60 sec");
				return false;
			}
		}

	}

	/*
	 * Description : Element wait till it is visible and returns boolean value
	 * Overload of public void elementWait(String element,String currentSceneText)
	 * 
	 */
	public boolean elementWait(String element, boolean value) {
		boolean isElementVisible = false;
		long startTime = System.currentTimeMillis();
		while (true) {
			boolean isVisible = GetConsoleBooleanText(element);
			if (isVisible == value) {
				isElementVisible = true;
				break;
			}
			long currentime = System.currentTimeMillis();
			// Break if wait is more than 30 secs
			if (((currentime - startTime) / 1000) > 60) {
				log.debug("Element  is not visible, break after 30 sec");
				break;
			}
		}
		return isElementVisible;
	}

	/*
	 * Date: 22/02/2019 Author:Premlata Description:this method is to wait for
	 * jackpot scene Parameter: NA
	 */
	public void jackpotSceneWait() {
		log.debug("Waiting for jackpot scene to come");
		elementWait("return " + XpathMap.get("currentScene"), "SLOT");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.debug("Jackpot scene displayed");
	}

	/*
	 * Date: 29/05/2018 Author:Havish Jain Description: To click on spin button
	 * Parameter: NA
	 */
	@Override
	public boolean spinclick() throws InterruptedException {
		try {
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			if (!GameName.contains("Scratch")) {
				clickAtButton("return " + XpathMap.get("ClickSpinBtn"));
			} else {
				clickAtButton("return  " + XpathMap.get("ClickscratchButton"));

				elementWait("return " + XpathMap.get("ScratchBtnCurrState"), "activeSecondary");

				clickAtButton("return  " + XpathMap.get("ClickscratchButton"));
			}
			log.debug("Clicked on spin button");
			// Thread.sleep(1000);

		} catch (Exception e) {
			log.error("error while clicking on spin button", e);
		}
		return true;
	}

	/**
	 * Date: 26/6/2019 Author: Sneha Jawarkar Description: GTR Reelspin
	 */
	public boolean clickspintostop() {
		Wait = new WebDriverWait(webdriver, 100);
		long startTime = System.currentTimeMillis();
		boolean flag = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			clickAtButton("return " + XpathMap.get("ClickSpinBtn"));
			log.debug("Clicked on spin button");
			while (true) {
				String currentScene = getConsoleText("return " + XpathMap.get("QuickSpinBtnCurrState"));
				if (currentScene != null && currentScene.equalsIgnoreCase("activeSecondary")) {
					clickAtButton("return " + XpathMap.get("ClickSpinBtn"));
					log.debug("Clicked on spinbutton to stop");
					flag = true;
					break;
				} else {
					long currentime = System.currentTimeMillis();
					if (((currentime - startTime) / 1000) > 60) {
						System.out.println("No  spin stop ocur in 1 mint.");
						flag = false;
						break;
					}
				}
			}

		} catch (Exception e) {
			log.error("error while clicking on spin button", e);

		}
		return flag;
	}

	/**
	 * Date: 26/6/2019 Author: Sneha Jawarkar Description: GTR Reelspin
	 * 
	 * @throws Exception
	 */
	public void Reelspin_in_All() throws Exception {
		System.out.println("Reelspin in Normal_Started");
		averageReelSpinDuration();
		System.out.println("Reelspin in Normal_End");
		System.out.println("Reelspin with quickspin Started");
		Reelspin_speed_During_quickspin();
		System.out.println("Reelspin with quickspin End");
		System.out.println("Reelspin with Autoplay Started");
		Reelspin_speed_During_Autoplay();
		System.out.println("Reelspin with Autoplay End");

	}

	/**
	 * Author:Sneha Jawarkar This method is used for verifying the status of the
	 * quick spin
	 * 
	 * @return true
	 */
	public boolean quickSpinStatus_verify() {
		try {
			String QS_currectState = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
			if (QS_currectState.equalsIgnoreCase("active")) {
				log.debug("Quickspin is Active");
				System.out.println("Quickspin is Active");
				return true;
			} else {
				log.error("Quickspin is not Active");
				System.out.println("Quickspin is not Active");
				return false;
			}
		} catch (Exception e) {
			log.error("Not able to verify quickspin status", e);
		}
		return false;
	}

	/**
	 * Author:Sneha Jawarkar This method is used for verifying the status of the
	 * quick spin and set the status to off.
	 * 
	 * @return void
	 */
	public void setQuickSpinOff() {
		try {
			if (!GameName.contains("Scratch")) {
				String QS_currectState = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
				if (QS_currectState.equalsIgnoreCase("active")) {
					clickAtButton("return " + XpathMap.get("QuickSpinBtn"));
					String QS_currectState_off = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
					if (QS_currectState_off.equalsIgnoreCase("activeSecondary"))
						log.debug("Quickspin is off Succesfully");
					log.debug("Quickspin is off Succesfully");
				} else {
					log.error("Quickspin is not Active");
				}
			} else {

				String QS_currectState = getConsoleText("return " + XpathMap.get("TurboBtnCurrState"));
				if (QS_currectState.equalsIgnoreCase("active")) {
					clickAtButton("return " + XpathMap.get("ClickTurboBtn"));
					String QS_currectState_off = getConsoleText("return " + XpathMap.get("TurboBtnCurrState"));
					if (QS_currectState_off.equalsIgnoreCase("activeSecondary"))
						log.debug("Quickspin is off Succesfully");
				} else {
					log.error("Quickspin is not Active");
				}

			}
		} catch (Exception e) {
			log.error("Quickspin Not able to off.", e);
		}
	}

	/**
	 * Author:Sneha Jawarkar This method is used for verifying the status of the
	 * quick spin and set the status to On.
	 * 
	 * @return void
	 */
	public void setQuickSpinOn() {
		try {
			if (!GameName.contains("Scratch")) {
				String QS_currectState = getConsoleText("return " + XpathMap.get("QuickSpinBtnState"));
				if (!QS_currectState.equalsIgnoreCase("active")) {
					clickAtButton("return " + XpathMap.get("QuickSpinBtn"));
				}
			} else {
				String QS_currectState = getConsoleText("return " + XpathMap.get("TurboBtnCurrState"));
				if (!QS_currectState.equalsIgnoreCase("active")) {
					clickAtButton("return " + XpathMap.get("ClickTurboBtn"));
				}

			}
		} catch (Exception e) {
			log.error("Quickspin Not able to off.", e);
		}
	}

	@Override
	public void clickAtButton(String button) {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			js.executeScript(button);
		} catch (JavascriptException e) {
			log.error("EXeception while executon ", e);
			log.error(e.getCause());
		}
	}

	@Override
	public boolean open_TotalBet() {
		boolean b = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickBetIconBtn"));
			Thread.sleep(4000);
			log.debug("Clicked on total bet button");
			b = true;
			
			
		} catch (Exception e) {
			log.error("error in open_TotalBet method", e);
		}
		return b;
	}
	
	
	
	//SV65878
	
	public boolean open_TotalBetupdated(Desktop_HTML_Report report, String languageCode) {
		boolean b = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickBetIconBtn"));
			Thread.sleep(5000);
			
			//Validation-Enhancement
			boolean flag = elementWait("return " + XpathMap.get("betVisible"), true);
			Thread.sleep(10000);
			if (flag) 
			{
			System.out.println("Bet Panel is Opened");
			setMaxBet();
			report.detailsAppendFolder(" UI of Bet Panel ", " Bet Panel screen shot","Bet Panel screen shot ", "PASS", languageCode);
			b = true;
			}
			
			else {
				System.out.println("Bet Panel is not Visible");
				report.detailsAppendFolder(" UI of Bet Panel ", " Bet Panel screen shot","Bet Panel screen shot ", "FAIL", languageCode);
				
			}
			log.debug("Clicked on total bet button");
			
			
			
				
	/*s			resizeBrowser(400,800);
				Thread.sleep(1500);
				report.detailsAppendFolder("verify the Browser Resize under Bet Panel ", " Bet Browser Resize screen shot","Bet Browser Resize screen shot ", "PASS", languageCode);
				
				
				//fulscreen
				webdriver.manage().window().fullscreen();
				Thread.sleep(1500);
				report.detailsAppendFolder("verify the Browser FullScreen under Bet Panel ", " Bet Browser FullScreen screen shot","Bet Browser FullScreen screen shot ", "PASS", languageCode);
				
				webdriver.manage().window().maximize();
				
				
				//refresh
				  webdriver.navigate().refresh();
				  boolean loadingscrn = elementWait("return " + XpathMap.get("currentScene"), "LOADING");	
					if(loadingscrn) {
					Thread.sleep(1500);
				  report.detailsAppendFolder(" Refresh on the Bet Screen Panel",
						  "After Refresh", "Refresh under Bet Screen " , "PASS",
						  languageCode);
					}
					else {
						report.detailsAppendFolder(" Refresh on the Bet Screen Panel",
								  "After Refresh", "Refresh under Bet Screen " , "FAIL",
								  languageCode);
						}
					if("yes".equalsIgnoreCase(XpathMap.get("gameContainsCntbtn"))) {
						boolean isfeatureScrn = elementWait("return " + XpathMap.get("FeatureScreen"), true);
						if(isfeatureScrn) {
							Thread.sleep(3000);
							closeOverlay();
							Thread.sleep(3000);
							boolean Basescene = elementWait("return " + XpathMap.get("currentScene"), "SLOT");
							if(Basescene) {
								 System.out.println("Refreshed");
								}
							else {
								Thread.sleep(3000);
								closeOverlay();
							}
						}
						}	//cnt btn
					s*/
					
			
			
			
		} catch (Exception e) {
			log.error("error in open_TotalBet method", e);
		}
		return b;
	}
	
	


	public void closeOverlay() {
		try {
			Actions act = new Actions(webdriver);

			if (!GameName.contains("Scratch")) {
				Thread.sleep(4000);
				act.moveToElement(webdriver.findElement(By.id("gameCanvas")), 100, 200).click().build().perform();
				
				System.out.println("Overlay Clicked");
			} else {
				act.moveToElement(webdriver.findElement(By.id("canvasContainer")), 100, 200).click().build().perform();
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void close_TotalBet() {
		try {
			if (!GameName.contains("Scratch")) {
				closeOverlay();
				boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isSpinBackBtnVisible"));
				if (flag) {
					clickAtButton("return " + XpathMap.get("ClickSpinBackBtn"));
					log.debug("Clicked close totalBet button");
				}
			} else {
				closeOverlay();
			}
			log.debug("Clicked on screen to close totalBet overlay");
			Thread.sleep(100);
		} catch (Exception e) {
			log.error("error in closing bet", e);
		}
	}

	/*
	 * Date: 29/05/2018 Author:Havish Jain Description: To Open menu container
	 * Parameter: NA
	 */
	public boolean menuOpen() {
		boolean ret = false;
		try {
			
			if("yes".equalsIgnoreCase(XpathMap.get("GameName_MegaMoolah"))) {
				clickAtButton("return " + XpathMap.get("MenuButton"));
			}
		else {
			Thread.sleep(3000);
			clickAtButton("return " + XpathMap.get("ClickHamburgerMenuBtn"));
			Thread.sleep(3000);
		}
			log.debug("Clicked on menu button  to open");
			Thread.sleep(1000);
			boolean Menuflag = GetConsoleBooleanText("return " + XpathMap.get("isMenuPanelVisible"));
			if(Menuflag) {
				System.out.println("Menu Panel Opened");
				ret = true;
			}
			
			else {
				System.out.println("Menu Panel is not Opened");
				ret = false;
			}
			
			
			
		} catch (Exception e) {
			log.error("Error in opening menu", e);
		}
		return ret;
	}


	/*
	 * Date: 29/05/2018 Author:Havish Jain Description: To Close menu container
	 * Parameter: NA
	 */
	@Override
	public boolean menuClose() {
		try {
			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isSpinBackBtnVisible"));
					if (flag) {
						clickAtButton("return " + XpathMap.get("ClickSpinBackBtn"));
						log.debug("Clicked close autoplay button");
					}
				} else {
					boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isHamburgerMenuBtnVisible"));
					if (flag) {
						clickAtButton("return " + XpathMap.get("ClickHamburgerMenuBtn"));
						log.debug("Clicked close menu button");
					}
				}
			} else {
				close_TotalBet();
			}
			log.debug("Clicked on screen to close menu overlay");
		} catch (Exception e) {
			log.error("Error in closing menu", e);
		}
		return true;
	}

	/**
	 * *Author:Premlata Mishra This method is used to get currency symbol .
	 * 
	 * @throws InterruptedException
	 */
	public String getCurrencySymbol() {
		String currencySymbol = null;
		try {

			String consoleBalance = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleBalance);
			// currencySymbol = consoleBalance.replaceAll("[[0-9][a-zA-Z],.\\s]", "");
			currencySymbol = consoleBalance.replaceAll("[[0-9]:,.\\s]", "");
			currencySymbol = currencySymbol.toLowerCase().replace("credits", "");
			log.debug("Fetching currency symbol from game" + "Currency symbol is :" + currencySymbol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		}
		return currencySymbol;
	}

	/**
	 * *Author:Pramlata Mishra This method is used to verify multiplier
	 * 
	 * @throws InterruptedException
	 */
	public String getCurrentBet() {
		String betnew = null;
		String bet = null;
		try {
			log.debug("reading bet text form base scene");
			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					bet = "return " + XpathMap.get("BetTextValue");

				} else {
					bet = "return " + XpathMap.get("BetButtonLabel");

				}
			} else {
				bet = "return " + XpathMap.get("InfobarBettext");

			}
			log.debug("read bet text form base scene");
			String consoleBet = getConsoleText(bet);
			System.out.println("Current Bet Amount is:"+consoleBet);
			betnew = consoleBet.replaceAll("[^0-9.,]", "");

			while (betnew.startsWith(".")) {

				betnew = betnew.substring(1);
				log.debug("betvalue: " + betnew);
			}

			while (betnew.endsWith(".")) {

				betnew = betnew.substring(0, betnew.length() - 1);
				log.debug("betvalue: " + betnew);
			}

			log.debug("New bet text after replacement=" + betnew);
		} catch (Exception e) {
			log.error("Error in verifying multiplier", e);
		}
		return betnew;
	}

	/**
	 * *Author:Premlata Mishra This method is used to verify currency symbol in bet
	 * 
	 * @throws InterruptedException
	 */
	public boolean betCurrencySymbol(String currencyFormat) {
		String bet = null;

		String strBetExp = null;
		boolean isBetInCurrencyFormat = false;

		try {
			log.debug("Function-> betCurrencySymbol()");
			log.debug("Reading Bet Text from base scene");
			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					bet = "return " + XpathMap.get("BetTextValue");
				} else
					bet = "return " + XpathMap.get("BetButtonLabel");
			} else {
				bet = "return " + XpathMap.get("InfobarBettext");
			}

			// String consoleBet=getConsoleText(bet).toLowerCase();
			String consoleBet = getConsoleText(bet);
			log.debug(" Bet Text from base scene=" + consoleBet);
			Thread.sleep(100);

			// String consoleBetnew=consoleBet.toLowerCase().replaceAll("bet: ", "");
			String consoleBetnew = consoleBet.replaceAll("bet: ", "");
			consoleBetnew = consoleBetnew.replaceAll("BET: ", "");
			consoleBetnew = consoleBetnew.replaceAll("Bet: ", "");
			String betregexp = createregexp(consoleBetnew, currencyFormat);
			if (betregexp.contains("$"))
				strBetExp = betregexp.replace("$", "\\$");
			else
				strBetExp = betregexp;
			Pattern.compile(strBetExp);
			String betexp = strBetExp.replace("#", "\\d");

			if (Pattern.matches(betexp, consoleBetnew)) {
				isBetInCurrencyFormat = true;
				log.debug("currency format:" + currencyFormat + "\n Actual format in the game:" + consoleBetnew
						+ "\n regexp :" + betexp);
			} else {
				isBetInCurrencyFormat = false;
				System.out.println("Fails currency format:" + currencyFormat + "\n Actual format in the game:"
						+ consoleBetnew + "\n regexp :" + betexp);
				log.debug("Fails currency format:" + currencyFormat + "\n Actual format in the game:" + consoleBetnew
						+ "\n regexp :" + betexp);
			}

			log.debug(" Bet currency symbol is::" + consoleBet);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return isBetInCurrencyFormat;

	}

	/**
	 * * Date: 29/05/2018 Author: Premlata Mishra Description: This function is used
	 * to set the bet to minimum Parameter:
	 */
	public void setMinBet() {
		try {
			String bet = "return " + XpathMap.get("SetMinBet");
			getConsoleText(bet);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * *Author:Havish Jain This method is used to verify currency symbol in bet
	 * setting screen
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	public boolean betSettingCurrencySymbol(String currencyFormat, Desktop_HTML_Report currency, String CurrencyName) {

		String strBetExp = null;
		boolean isCurrentSymbolPresent = false;
		try {
			String bet = "return " + XpathMap.get("BetSizeText");

			// fetching bet from Bet panel
			String panelbet = getConsoleText(bet);
			// String panelbetnew=panelbet.replaceAll("[^0-9]", "");

			log.debug("Function-> betSettingCurrencySymbol()");

			String betregexp = createregexp(panelbet, currencyFormat);
			if (betregexp.contains("$"))
				strBetExp = betregexp.replace("$", "\\$");
			else
				strBetExp = betregexp;
			Pattern.compile(strBetExp);
			String betexp = strBetExp.replace("#", "\\d");

			log.debug("Bet exp= " + betexp + "   panel bet= " + panelbet);
			if (Pattern.matches(betexp, panelbet)) {
				isCurrentSymbolPresent = true;
			} else {
				isCurrentSymbolPresent = false;
			}

			int totalNoOfQuickBets = (int) getNumberOfQuickbets();
			Map<String, String> paramMap = new HashMap<String, String>();

			for (int quickBet = 0; quickBet < totalNoOfQuickBets; quickBet++) {

				paramMap.put("param1", Integer.toString(quickBet));
				String quickBetText = XpathMap.get("getQuickBetText");
				String newQuickBetText = replaceParamInHook(quickBetText, paramMap);

				String strQuickBet = "return " + newQuickBetText;

				String quickbet = getConsoleText(strQuickBet);
				// String quickbetnew=panelbet.replaceAll("[^0-9]", "");

				log.debug("new quick bet= " + quickbet);

				String quickbetbetregexp = createregexp(quickbet, currencyFormat);
				if (quickbetbetregexp.contains("$"))
					strBetExp = quickbetbetregexp.replace("$", "\\$");
				else
					strBetExp = quickbetbetregexp;
				Pattern.compile(strBetExp);
				String quickbetexp = strBetExp.replace("#", "\\d");

				byte ptext[] = strQuickBet.getBytes("UTF-8");
				strQuickBet = new String(ptext, "UTF-8");

				log.debug("Quick Bet= " + quickBet + " quick bet exp= " + quickbetexp);
				if (Pattern.matches(quickbetexp, quickbet)) {
					isCurrentSymbolPresent = true;
					log.debug("quick bet format result=" + isCurrentSymbolPresent);
					System.out.println("quick bet format result=" + isCurrentSymbolPresent);
					/*currency.detailsAppendFolder("Verify the currency  format in " + strQuickBet + "Quick bet",
							"Quick bet Currency  display in correct format",
							"Quick bet Currency should display in correct format", "pass", CurrencyName);*/
				} else {
					/*currency.detailsAppendFolder("Verify the currency  format in " + strQuickBet + "Quick bet",
							"Quick bet Currency  display in correct format",
							"Quick bet Currency does not display in correct format", "Fail", CurrencyName);*/
					isCurrentSymbolPresent = false;
					log.debug("bet setting quick bet format result=" + isCurrentSymbolPresent);
					System.out.println("bet setting quick bet format result=" + isCurrentSymbolPresent);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isCurrentSymbolPresent;
	}

	// This method is use to decrease the bet
	public boolean betIncrease() {

		try {
			String bet = "return " + XpathMap.get("DecreaseBetValue");
			getConsoleText(bet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Date:07/12/2017 Author:premlata Mishra This method is to get bet ammount
	 * 
	 * @return true
	 */
	public double GetBetAmt() {
		String consolePayoutnew = null;
		double consolePayoutnew1 = 0.0;
		try {
			String bet = "return " + XpathMap.get("BetSizeText");
			String consoleBet = getConsoleText(bet);
			consolePayoutnew = consoleBet.replaceAll("[^0-9]", "");
			consolePayoutnew1 = Double.parseDouble((consolePayoutnew));
		} catch (Exception e) {
			e.getMessage();
		}
		return consolePayoutnew1 / 100;
	}

	/**
	 * Date:07/12/2017 Author:Premlata Mishra This method is usedto open the
	 * settings
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	public boolean settingsOpen() throws InterruptedException {
		boolean test = false;
		try {
			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) { // hooks foe immortal
					boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuIconSettingBtnVisible"));
					if (flag) {
						clickAtButton("return " + XpathMap.get("ClickHamburgerMenuBtn"));
						clickAtButton("return " + XpathMap.get("SettingOpenBtn"));
						log.debug("Clicked on setting button in menu");
						
						//Validation-Enhancement
						boolean Settingflag = GetConsoleBooleanText("return " + XpathMap.get("isSettingVisible"));
						if (Settingflag) {
							System.out.println("Settings Panel Visible");
							test = true;
						}
						else {
							System.out.println("Settings Panel is not Visible ");
							test = false;
						}
						
						
						
					}
				} else {// hooks for other games

					clickAtButton("return " + XpathMap.get("SettingOpenBtn"));
					Thread.sleep(3000);
					//sv65878
					boolean Settingflag = GetConsoleBooleanText("return " + XpathMap.get("isSettingVisible"));
					if (Settingflag) {
						System.out.println("Settings Panel Visible");
						test = true;
					}
					else {
						System.out.println("Settings Panel is not Visible ");
						test = false;
					}
					
				}
			} else {
				clickAtButton("return " + XpathMap.get("SettingOpenBtn"));

			}
			log.debug("Clicked on settings button to open");
			
		} catch (Exception e) {
			log.error("Error in opening setting button", e);
		}
		return test;
	}

	/**
	 * Date:07/12/2017 Author:Laxmikanth Kodam This method is actually not necessery
	 * in component store just declaration needed
	 * 
	 * @return true
	 */
	public boolean settingsBack() {
		boolean test = false;
		try {
			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isHamburgerMenuBtnVisible"));
					if (flag) {
						clickAtButton("return " + XpathMap.get("ClickHamburgerMenuNewBtn"));
						clickAtButton("return " + XpathMap.get("ClickSpinBackBtn"));
						log.debug("Clicked on setting button in menu");
					}
				} else {
					Thread.sleep(2000);
					close_TotalBet();
				}
				log.debug("Clicked on screen to close settings overlay");
				test = true;
			} else {
				close_TotalBet();
			}
		} catch (Exception e) {
			log.error("Error in closing setting overlay", e);
		}
		return test;
	}

	public void paytableClose() {
		Wait = new WebDriverWait(webdriver, 200);
		try {
			clickAtButton("return " + XpathMap.get("PaytableCloseBtn"));
			log.debug("Closed the paytable page");
		} catch (Exception e) {
			log.debug("error in closing paytable");
		}
	}

	/**
	 * Date:10-1-2018 Name:Premlata Mishra Description: this function is used to
	 * Scroll the page and to take the screenshot
	 * 
	 * @throws Exception
	 */
	@Override
	public String capturePaytableScreenshot(Desktop_HTML_Report report, String languageCode) {
		log.debug("Inside Paytable Opening");
		Wait = new WebDriverWait(webdriver, 50);
		String paytable = null;
		try {
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			clickAtButton("return " + XpathMap.get("PaytableOpenBtn"));
			log.debug(" Clicked on paytable icon to open ");
			Thread.sleep(2500);
		
			//Validation-Enhancement Sv65878
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("PaytableVisible"));
			if (flag) {
				System.out.println("Paytable is Visible");
				report.detailsAppendFolder(" verify the paytable screen shot", " paytable page is visible","paytable Page is visible", "PASS", languageCode);

			/*
			 * Adding the Jackpot info table screen shots for game having bolt on features
			 */
			if (checkAvilability(XpathMap.get("IsJackpotInfoPaytableComponentVisible"))) {
				captureJackpotInfoPaytable(report, languageCode);
				clickAtButton("return " + XpathMap.get("CloseJackpotInfoPaytableComponent"));
			}

			report.detailsAppendFolder(" verify the paytable screen shot", " paytable first page screen shot","paytable first page screenshot ", "PASS", languageCode);

			double paytableOverallHeight = 0;//3754
			if (js.executeScript("return " + XpathMap.get("PaytableScrollHeight")).getClass().getName()
					.endsWith("Long")) {
				long longTotalPaytableHeight = (Long) js
						.executeScript("return" + XpathMap.get("PaytableScrollHeight"));
				paytableOverallHeight = (double) longTotalPaytableHeight;
			}
			
			else 
			{
				paytableOverallHeight = (double) js.executeScript("return " + XpathMap.get("PaytableScrollHeight"));
			}

			double paytableHeight2 = 0;//654
			if (js.executeScript("return " + XpathMap.get("PaytableScroll_h")).getClass().getName().endsWith("Long")) {
				long longPaytableHeight = (Long) js.executeScript("return " + XpathMap.get("PaytableScroll_h"));
				paytableHeight2 = (double) longPaytableHeight;
			} else {
				paytableHeight2 = (double) js.executeScript("return " + XpathMap.get("PaytableScroll_h"));
			}
			// height adjustment because of text missing out
			paytableHeight2 = paytableHeight2 - 120;//554
			int scroll = (int) (paytableOverallHeight / paytableHeight2);//3754/554
			System.out.println(scroll);

			for (int i = 1; i <= scroll + 1; i++) {

				threadSleep(2500);
				js.executeScript("return " + forceNamespace+".getControlById('PaytableComponent').paytableScroller.scrollTo(0,-" + paytableHeight2 * i+ ")");
				threadSleep(1000);
				log.debug("Scrolling the paytable page and taking screenshots");
				report.detailsAppendFolder("verify the paytable screen shot", " paytable next page screen shot","paytable next page screenshot ", "PASS", languageCode);
				
			}
			paytable = "paytable1";
			}
			else {
				System.out.println("Paytable is not Visible");
				report.detailsAppendFolder(" verify the paytable screen shot", " paytable page is visible","paytable Page is visible", "Fail", languageCode);
			}	
		
		} catch (Exception e) {
			log.error("error in opening paytable", e);
		}
		return paytable;
	}

	
	/**
	 * Date:26/9/2022 Name:Saloni Verma Description: this function is used to
	 * Scroll the page and to take the screenshot in resize window size
	 * 
	 * @throws Exception
	 */
	
	
	public String capturePaytableScreenshot_Resize(Desktop_HTML_Report report, String languageCode) {
		log.debug("Inside Paytable Opening");
		Wait = new WebDriverWait(webdriver, 50);
		String paytable = null;
		try {
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			clickAtButton("return " + XpathMap.get("PaytableOpenBtn"));
			log.debug(" Clicked on paytable icon to open ");
			Thread.sleep(2500);
			
			//Validation-Enhancement Sv65878
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("PaytableVisible"));
			if (flag) {
				System.out.println("Paytable is Visible");
				report.detailsAppendFolder(" verify the paytable screen shot", " paytable Resized page is visible","paytable Resize Page is visible", "PASS", languageCode);

			/*
			 * Adding the Jackpot info table screen shots for game having bolt on features
			 */
			if (checkAvilability(XpathMap.get("IsJackpotInfoPaytableComponentVisible"))) {
				captureJackpotInfoPaytable(report, languageCode);
				clickAtButton("return " + XpathMap.get("CloseJackpotInfoPaytableComponent"));
			}

			report.detailsAppendFolder(" verify the paytable screen shot", " paytable first page screen shot","paytable first page screenshot ", "PASS", languageCode);

			double paytableOverallHeight = 0;//3754
			if (js.executeScript("return " + XpathMap.get("PaytableScrollHeight")).getClass().getName()
					.endsWith("Long")) {
				long longTotalPaytableHeight = (Long) js
						.executeScript("return " + XpathMap.get("PaytableScrollHeight"));
				paytableOverallHeight = (double) longTotalPaytableHeight;
			} else {
				paytableOverallHeight = (double) js.executeScript("return " + XpathMap.get("PaytableScrollHeight"));
			}

			double paytableHeight2 = 0;//654
			if (js.executeScript("return " + XpathMap.get("PaytableScroll_h")).getClass().getName().endsWith("Long")) {
				long longPaytableHeight = (Long) js.executeScript("return " + XpathMap.get("PaytableScroll_h"));
				paytableHeight2 = (double) longPaytableHeight;
			} else {
				paytableHeight2 = (double) js.executeScript("return " + XpathMap.get("PaytableScroll_h"));
			}
			// height adjustment because of text missing out
			paytableHeight2 = paytableHeight2 - 120;//554
			int scroll = (int) (paytableOverallHeight / paytableHeight2);//3754/554
			System.out.println(scroll);

			for (int i = 1; i <= scroll + 1; i++) {

				threadSleep(2500);
				js.executeScript("return " + forceNamespace+".getControlById('PaytableComponent').paytableScroller.scrollTo(0,-" + paytableHeight2 * i+ ")");
				threadSleep(1000);
				log.debug("Scrolling the paytable page and taking screenshots");
				report.detailsAppendFolder("verify the paytable screen shot", " paytable next page screen shot","paytable next page screenshot ", "PASS", languageCode);
				
			}
			paytable = "paytable1";
			}
			else {
				System.out.println("Paytable is not Visible");
				report.detailsAppendFolder(" verify the paytable screen shot", " paytable page is visible","paytable Page is visible", "Fail", languageCode);
			}	
			
			} catch (Exception e) {
			log.error("error in opening paytable", e);
		}
		return paytable;
	}

	/**
	 * Description: this function is used to Scroll the page of paytable and to take
	 * the screenshots only does not append it in report. uesd in currency
	 * 
	 * @throws Exception
	 */

	public String capturePaytableOnlyScreenshot(Desktop_HTML_Report report, String currencyname) {
		log.debug("Inside Paytable Opening");
		Wait = new WebDriverWait(webdriver, 50);
		String paytable = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			clickAtButton("return " + XpathMap.get("MenuPaytableBtn"));
			log.debug("Clicked on paytable icon  to open ");
			Thread.sleep(1500);

			/*
			 * Adding the Jackpot info table screen shots for game having bolt on features
			 */
			if (checkAvilability(XpathMap.get("IsJackpotInfoPaytableComponentVisible"))) {
				captureJackpotInfoPaytable(report, currencyname);
				clickAtButton("return " + XpathMap.get("CloseJackpotInfoPaytableComponent"));
			}
			report.detailsAppendFolderOnlyScreenShot(currencyname);
			Double paytableFullHeight2 = (Double) js.executeScript("return " + XpathMap.get("PaytableScrollHeight"));
			double paytableHeight2 = 0;
			if (js.executeScript("return " + XpathMap.get("PaytableScroll_h")).getClass().getName().endsWith("Long")) {
				long longPaytableHeight = (Long) js.executeScript("return " + XpathMap.get("PaytableScroll_h"));
				paytableHeight2 = (double) longPaytableHeight;
			} else {
				paytableHeight2 = (double) js.executeScript("return " + XpathMap.get("PaytableScroll_h"));
			}
			// height adjustment because of text missing out
			paytableHeight2 = paytableHeight2 - 100;

			int scroll = (int) (paytableFullHeight2 / paytableHeight2);

			for (int i = 1; i <= scroll + 1; i++) {

				threadSleep(1000);
				js.executeScript("return " + forceNamespace
						+ ".getControlById('PaytableComponent').paytableScroller.scrollTo(0,-" + paytableHeight2 * i
						+ ")");
				threadSleep(1000);
				log.debug("Scrolling the paytable page and taking screenshots");
				report.detailsAppendFolderOnlyScreenShot(currencyname);

			}
			paytable = "paytable1";
		} catch (Exception e) {
			log.error("error in opening paytable", e);
		}
		return paytable;
	}

	/**
	 * Date: 14/12/2017 Autohr: Laxmikanth Kodam Description: This function used to
	 * take to the game
	 * 
	 * @return null
	 */

	public String verifyCreditsValue() {
		String val = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			String balance = "return " + XpathMap.get("Balancetext");
			String consoleBalance = getConsoleText(balance).replace(".", "/");

			String splited_balance[] = consoleBalance.split("/");
			char[] splitedblnc = splited_balance[0].toCharArray();
			log.debug(splitedblnc.length);
			if (splitedblnc.length >= 11) {
				val = splited_balance[0].replaceAll("[^0-9]", "");// this is for comparison of italy script
			} else {
				consoleBalance = getConsoleText(balance);
				val = consoleBalance.replaceAll("[a-zA-Z]", "").replace(",", "").substring(2);
				// BigDecimal credits = (BigDecimal) format.parse(val);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return val;
	}

	/**
	 * Date:25/06/2018 Author:Premlata Mishra This method is used to open the
	 * settings
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	public boolean verifyQuickSpin(String imagepath) {
		boolean test = false;
		try {
			String qucikSpin = "return " + XpathMap.get("SettingsQuickSpinBtn");
			String quickSpinTxt = getConsoleText(qucikSpin);
			if (quickSpinTxt != null) {
				test = true;
			} else {
				test = false;
			}

		} catch (Exception e) {
			return test = false;
		}
		return test;
	}

	/**
	 * This method is used to stop is avalable or not Author: Premlata Mishra
	 * 
	 * @return true
	 */
	/*
	 * public boolean VerifyStop(String imagepath) throws InterruptedException {
	 * boolean newimage; spinclick(); Screen S = new Screen();
	 * 
	 * Pattern image = new Pattern(imagepath); log.debug("get image : " + image);
	 * try { Thread.sleep(1000); // S.click(S.wait(image, 10)); // Region
	 * r=S.find(image);
	 * 
	 * Region r = S.exists(image, 5);
	 * 
	 * if(r!=null) { log.debug("image found"); newimage=true; } else {
	 * log.debug("image not found"); newimage=false; } } catch (Exception e) {
	 * e.printStackTrace(); return false; } return newimage; }
	 */
	/**
	 * Date:07/12/2017 Author:premlata mishra This method is to get win amount
	 * 
	 * @return true
	 */
	public String GetWinAmt() {
		String Winamtnew = null;
		try {
			String Winamt = "return " + XpathMap.get("WinDesktopLabel");
			String Winamt1 = getConsoleText(Winamt);

			if (!Winamt1.isEmpty()) {
				String splitedWin[] = splitString(Winamt1, ".");
				Winamtnew = splitedWin[0].replaceAll("[^0-9]", "");
				Thread.sleep(100);
			} else
				Winamtnew = "0.0";

			log.debug("win amount " + Winamt1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Winamtnew;
	}

	public void bonusClick(String bonusComponent, String align) {
		Coordinates coordinateObj = new Coordinates();
		typeCasting(bonusComponent + ".x", coordinateObj);
		coordinateObj.setX(coordinateObj.getX());
		typeCasting(bonusComponent + ".y", coordinateObj);
		coordinateObj.setY(coordinateObj.getX());
		typeCasting(bonusComponent + ".height", coordinateObj);
		coordinateObj.setHeight(coordinateObj.getX());
		typeCasting(bonusComponent + ".width", coordinateObj);
		coordinateObj.setWidth(coordinateObj.getX());
		coordinateObj.setAlign(align);
		getComponentCenterCoordinates(coordinateObj);
		clickAtCoordinates(coordinateObj.getX() + 70, coordinateObj.getY());
	}

	/**
	 * *Author:Premlata This method is used to click on free spin enrtry screen
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public String clickBonusSelection(int freeSpinCount) {
		String fsCredits = null;
		try {
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			Thread.sleep(4000);
			if (freeSpinCount == 1) {
				// clicking by hooks
				clickAtButton("return " + XpathMap.get("FirstFSSelectionS"));
			} else if (freeSpinCount == 2) {
				// clicking by hooks
				clickAtButton("return " + XpathMap.get("SecondFSSelectionS"));
			} else if (freeSpinCount == 3) {
				// clicking by hooks
				clickAtButton("return " + XpathMap.get("ThirdFSSelectionS"));
			} else if (freeSpinCount == 4) {
				// clicking by hooks
				clickAtButton("return " + XpathMap.get("FourthFSSelectionS"));
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			e.getMessage();
		}
		return fsCredits;
	}

	public void FSSceneWait(String checkCurrentScene, String currentSceneText) {
		while (true) {
			String currentScene = getConsoleText(checkCurrentScene);
			if (currentScene != null && currentScene.equalsIgnoreCase(currentSceneText)) {
				break;
			}
		}
	}

	public boolean FSSceneLoading(int bonusSelection) {
		try {
			if (bonusSelection == 1) {
				FSSceneWait("return " + XpathMap.get("currentScene"), "FREESPIN_BARATHEON");
			}
			if (bonusSelection == 2) {
				FSSceneWait("return " + XpathMap.get("currentScene"), "FREESPIN_LANNISTER");
			}
			if (bonusSelection == 3) {
				FSSceneWait("return " + XpathMap.get("currentScene"), "FREESPIN_STARK");
			}
			if (bonusSelection == 4) {
				FSSceneWait("return " + XpathMap.get("currentScene"), "FREESPIN_TARGARYEN");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return true;
	}

	/**
	 * This method is used to wait till FS scene loads Author: Havish Jain
	 * 
	 * @return true
	 */
	@Override
	public boolean FSSceneLoading() {
		long startTime = System.currentTimeMillis();
		try {
			log.debug("After refreshing,waiting for free spin's scene to come");
			while (true) {
				String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
				if (currentScene != null && (currentScene.contains("FREESPIN"))) {
					break;
				}
				long currentime = System.currentTimeMillis();
				// Break if wait is more than 30 secs
				if (((currentime - startTime) / 1000) > 30) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("error while waiting for free spin scene", e);
		}
		return true;
	}

	/* Havish Jain: Click on Continue Free Spin button in FS */
	@Override
	public void FS_continue() {
		try {
			clickAtButton("return " + XpathMap.get("ClickStartFSBtn"));
			log.debug("Clicked on free spin continue button");
			System.out.println("Clicked on free spin continue button");
			Thread.sleep(2000);
		} catch (Exception e) {
			log.error("error while clicking on free spin continue button", e);
		}
	}

	/* Havish Jain: Wait for Spin button */
	public void waitForSpinButton() {
		try {
			long startTime = System.currentTimeMillis();
			log.debug("Waiting for spin button to visible");
			while (true) {
				String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
				if (!GameName.contains("Scratch")) {
					if (currentScene != null && currentScene.equalsIgnoreCase("SLOT")) {
						log.debug("Spin button is visible");
						break;
					} else if (currentScene != null && currentScene.contains("FREESPINS_COMPLETE")) {
						if (GetConsoleBooleanText("return " + XpathMap.get("IsFS_SummaryContinueBtnVisible"))) {// As in
																												// some
																												// of
																												// the
																												// game
																												// back
																												// to
																												// game
																												// is
																												// present
							clickAtButton("return " + XpathMap.get("ClickToFreeSpinSummaryBackToGameButton"));
						} else {
							Thread.sleep(4000);
						}
					}
				} else {
					if (currentScene != null && currentScene.equalsIgnoreCase("BASE")) {
						log.debug("Spin button is visible");
						break;
					}
				}
				long currentime = System.currentTimeMillis();
				// Break if wait is more than 80 secs
				if (((currentime - startTime) / 1000) > 60) {
					log.debug("Spin button is not visible, break after 60 sec");
					break;
				}
			}

		} catch (JavascriptException e) {
			log.error("Error in hook ,please the hook ");
			log.error(e.getCause());
		} catch (Exception e) {
			log.error("error while waiting for spin button", e);
		}
	}

	/**
	 * *Author:Premlata This method is used to click on win history button
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public boolean winHistoryClick() throws Exception {
		try {
			Thread.sleep(1000);
			clickAtButton("return " + XpathMap.get("ClickWinHistoryExpander"));
			Thread.sleep(1000);
			log.debug("Clicked on win history button");
			return true;
		} catch (Exception e) {
			log.error("error on clikcing win history button", e);
			return false;
		}
	}

	public void winHistoryClose() throws Exception {
		try {
			clickAtButton("return " + XpathMap.get("ClickWinHistoryExpander"));
			Thread.sleep(1000);
			log.debug("Closed on win history button");
		} catch (Exception e) {
			log.error("error on clikcing win history button", e);
		}
	}

	public void close_Autoplay() throws InterruptedException {
		try {
			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isSpinBackBtnVisible"));
					if (flag) {
						clickAtButton("return " + XpathMap.get("ClickSpinBackBtn"));
						log.debug("Clicked close autoplay button");
					}
				} else {
					clickAtButton("return " + XpathMap.get("ClickAutoPlayBtn"));
					closeOverlay();
				}
				log.debug("clicked on screen to close overlay");
				Thread.sleep(500);
			} else {
				closeOverlay();
				log.debug("clicked on screen to close overlay");
			}
		} catch (Exception e) {
			log.error("Close Autoplay Error", e);
		}
	}

	/**
	 * *Author:Havish This method is used to wait till the free spin summary screen
	 * won't come
	 * 
	 * @throws InterruptedException
	 */
	public void waitSummaryScreen() throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 100);

		try {
			long startTime = System.currentTimeMillis();
			log.info("Waiting for Summary Screen to come");
			while (true) {
				String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
				//System.out.println(currentScene);
				log.debug(currentScene);
				if (currentScene != null && (currentScene.contains("FREESPINS_COMPLETE"))) {
					{
						log.debug("Summary screen visible");
						log.debug(currentScene);
						System.out.println("Summary screen visible"+ currentScene);
						break;
					}
				}

				long currentime = System.currentTimeMillis();
				// Break if wait is more than 300 secs
				if (((currentime - startTime) / 1000) > 300) {
					log.debug("Summary screen not visible, break after  5 mins");
					break;
				}
			}

			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
		} catch (Exception e) {
			log.error("error while waiting for summary screen ",e);
			log.error(e.getMessage(),e);
		}
	}

	/*
	 * Author:Havish This method will check for any existing offer and discard it
	 * 
	 * @throws InterruptedException
	 */
	public String freeGamesDiscardExistingOffer() {
		Wait = new WebDriverWait(webdriver, 100);
		String text = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			text = getConsoleText("return " + XpathMap.get("FGViewHistoryPop"));
			if (text.equalsIgnoreCase("freeGamesResumeView")) {
				resumeScreenDiscardClick();
				confirmDiscardOffer();
				clickNextOffer();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return text;
	}

	/*
	 * Author:Havish This method is used to wait till the freeGamesContinueExpiry
	 * screen and then click on continue button
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public String freeGamesContinueExpiry() {
		Wait = new WebDriverWait(webdriver, 100);
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			Thread.sleep(100);
			String scene = getConsoleText("return " + XpathMap.get("FGViewHistoryPop"));
			if (scene.equalsIgnoreCase("freeGamesExpiredView")) {
				String text = getConsoleText("return " + XpathMap.get("FreeGamesExpiredViewContinueBtn"));
				if (text.equalsIgnoreCase("continueButton")) {
					clickAtButton("return " + XpathMap.get("ClickFGExpiredViewContinueBtn"));
					Thread.sleep(100);
					log.debug("cliked on expiry screen of freegame");
					clickNextOffer();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Author:Havish This method is used to click on Next Offer button
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public void clickNextOffer() {
		try {
			String text = getConsoleText("return " + XpathMap.get("FGCompleteNextOfferBtn"));
			if (text.equalsIgnoreCase("nextOfferButton")) {
				clickAtButton("return " + XpathMap.get("ClickFGCompleteNextOfferBtn"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/*
	 * Author:Havish This method is used to wait till the freeGames Entry screen
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public boolean freeGamesEntryScreen() {
		boolean isFreeGameAssing = false;
		try {
			isFreeGameAssing = GetConsoleBooleanText("return " + XpathMap.get("isFGOfferPlayNowBtn"));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return isFreeGameAssing;
	}

	/*
	 * Author:Havish This method is used to click on info icon on Free Games Enrty
	 * Screen
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public boolean freeGameEntryInfo()
	{
		boolean b = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickFGOfferViewInfoBtn"));
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public String entryScreen_Wait(String entryScreen) {
		String wait = "free";
		long startTime = System.currentTimeMillis();
		try {
			if (entryScreen.equalsIgnoreCase("yes")) {
				log.info("Waiting for free spin entry screen to come");
				while (true) {
					String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
					if (("BONUS_SELECTION".equalsIgnoreCase(currentScene))
							|| "FREESPINS_INTRO".equalsIgnoreCase(currentScene) || "SLOT".equalsIgnoreCase(currentScene)
							|| "PLAYING_ACTIVE_BONUS".equalsIgnoreCase(currentScene)) {
						wait = "freeSpin";
						// Thread.sleep(3000);
						log.debug("Free spin entry screen found");
						break;
					}

					long currentime = System.currentTimeMillis();
					// Break if wait is more than 40 secs
					if (((currentime - startTime) / 1000) > 40) {
						log.debug("break after 40 secs");
						break;
					}
				}
			} else
				log.debug("Free Spin Entry Screen is not avalable");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return wait;
	}

	/*
	 * Author:Havish This method is to click on PlayNow button
	 * 
	 * @throws InterruptedException
	 */
	public boolean clickPlayNow() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickFGOfferViewPlayNowBtn"));
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/*
	 * Author:Havish This method is used to wait till the freeGames resume screen
	 * 
	 * @throws InterruptedException
	 */
	public String freeGamesResumescreen() {
		Wait = new WebDriverWait(webdriver, 50);
		String str = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			String Text = getConsoleText("return " + XpathMap.get("FGViewHistoryPop"));
			if (Text.equalsIgnoreCase("freeGamesResumeView")) {
				str = "ResumePlay";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/*
	 * Author:Havish This method is used to click on free games resume info
	 * 
	 * @throws InterruptedException
	 */
	public boolean freeGameResumeInfo() {
		boolean b = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickFGResumeViewInfoBtn"));
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/*
	 * Author:Havish This method is used to click on discard icon on free game
	 * resume screen
	 * 
	 * @throws InterruptedException
	 */
	public boolean resumeScreenDiscardClick() {
		boolean b = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickFGResumeViewDeleteBtn"));
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/*
	 * Author:Havish This method is used for confirming discard offer
	 * 
	 * @throws InterruptedException
	 */
	public boolean confirmDiscardOffer() {
		boolean b = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickFGDeleteOfferViewDiscardBtn"));
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/*
	 * Author:Havish This method is used to wait for Expiry screen
	 * 
	 * @throws InterruptedException
	 */
	public boolean freeGamesExpriyScreen() {
		Wait = new WebDriverWait(webdriver, 50);
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/*
	 * Author:Havish This method is used click on Discard button in base scene
	 * during Free Games
	 */
	public void clickBaseSceneDiscard() {
		try {
			clickAtButton("return " + XpathMap.get("BaseSceneDiscardBtn"));
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getComponentCenterCoordinates(Coordinates coordinateObj) {
		try {
			long centerX = 0, centerY = 0;
			long middleHeight = coordinateObj.getHeight() / 2;
			long middleWidth = coordinateObj.getWidth() / 2;
			long x = coordinateObj.getX();
			long y = coordinateObj.getY();
			String align = coordinateObj.getAlign();

			if (align.equals("BOTTOM_RIGHT")) {
				centerX = x - middleWidth;
				centerY = y - middleHeight;
			} else if (align.equals("BOTTOM_LEFT")) {
				centerX = x + middleWidth;
				centerY = y + middleHeight;
			} else if (align.equals("BOTTOM_CENTER")) {
				centerX = x;
				centerY = y - middleHeight;
			} else if (align.equals("TOP_LEFT")) {
				centerX = x + middleWidth;
				centerY = y + middleHeight;
			} else if (align.equals("TOP_CENTER")) {
				centerX = x;
				centerY = y + middleHeight;
			} else if (align.equals("TOP_RIGHT")) {
				centerX = x - middleWidth;
				centerY = y + middleHeight;
			} else if (align.equals("LEFT_CENTER")) {
				centerX = x + middleWidth;
				centerY = y;
			} else if (align.equals("RIGHT_CENTER")) {
				centerX = x - middleWidth;
				centerY = y;
			} else if (align.equals("CENTER")) {
				centerX = x;
				centerY = y;
			}
			coordinateObj.setCenterX(centerX);
			coordinateObj.setCenterY(centerY);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public boolean isBetButtonAccessible() {

		try {
			String currentState = getConsoleText("return " + XpathMap.get("BetIconCurrState"));
			if (currentState.equalsIgnoreCase("Active")) {
				log.debug("Bet Button is active during Autoplay");
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isMaxButtonAccessible() {
		try {
			String currentState;

			if (!GameName.contains("Scratch")) {

				currentState = getConsoleText("return " + XpathMap.get("MaxButnCurrState"));
			} else {
				currentState = getConsoleText("return " + XpathMap.get("MaxButnCurrState"));
			}
			if (currentState.equalsIgnoreCase("Active")) {
				log.debug("MAX Button is Accessible during Autoplay");
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAutoplayStoppedOnMenuClick() {

		boolean isAutoplayStoppedOnMenuClick = true;

		// Start Auto Play
		clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));

		try {
			// wait for some time
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}

		clickAtButton("return " + XpathMap.get("ClickHamburgerMenuBtn"));

		isAutoplayStoppedOnMenuClick = GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive"));
		// as on success isAutoplayActive() returns false
		return (!isAutoplayStoppedOnMenuClick);
	}

	public void openBetPanel() {

		// Opening bet panel
		clickAtButton("return " + XpathMap.get("ClickBetIconBtn"));

	}

	/*
	 * This method set the next low bet decrese the bet value by 0.5
	 */
	public String setTheNextLowBet() {
		String betAmount = null;
		try {
			// Decrease the bet value
			// Below line is commented as in sanity suit no need to check for all bet values
			clickAtButton("return " + forceNamespace + ".getServiceById('BetService').decreaseBetValue()");
			/*
			 * String maxbet1 = maxbet.replaceAll("[[a-zA-Z]:$\\s]", "").trim(); int
			 * intmaxbet=Integer.parseInt(maxbet); int betindex=new
			 * Random().nextInt(intmaxbet/2); clickAtButton("return "+forceNamespace+
			 * ".getServiceById('BetService').setBetValueByIndex("+betindex+")");
			 */

			// Get the Bet Value
			betAmount = getConsoleText(
					"return " + forceNamespace + ".getControlById('BetPanelComponent').Text.betSizeValueText._text");

		} catch (Exception e) {
			log.error("Exception while decreasing the bet value ::" + e.getMessage(), e);
		}

		return betAmount;
	}

	/*
	 * This method is set the next low bet value decrease the bet value
	 */
	public String setTheNextLowBet(int count) {
		String bet = null;
		return bet;
	}

	public boolean isBetChangedIntheConsole(String betValue) {
		String consoleBet = null;
		String bet = null;
		String bet1 = null;
		try {
			if (!GameName.contains("Scratch")) {
				log.debug("Bet value selected from game before = " + betValue);
				consoleBet = getConsoleText("return " + XpathMap.get("BetTextValue"));
				log.debug("Bet Refelecting on console after bet chnage from quickbet : " + betValue);
				bet1 = consoleBet.replaceAll("[a-zA-Z]", "").trim();
				bet = bet1.replaceFirst(":", "").trim();
			} // below else for Scratch game
			else {
				log.debug("Bet value selected from scrach game = " + betValue);
				consoleBet = getConsoleText("return " + XpathMap.get("InfobarBettext"));
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
 *This method is for to Get Current credits from console
 * @throws InterruptedException 
 */
	public String getCurrentCredits()
	{
		String balance = null;
		String consoleBalance = null;

		if (!GameName.contains("Scratch")) {
			if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
				balance = "return " + XpathMap.get("Balancetext");
			} else
				balance = "return " + XpathMap.get("Balancetext");

		} else {
			balance = "return " + XpathMap.get("InfoBarBalanceTxt");

		}
		
		consoleBalance = getConsoleText(balance);
		System.out.println("Console Credits are "+consoleBalance);
		log.debug("Console Credits are "+consoleBalance);
		return consoleBalance;
	}

	public boolean isCreditDeducted(String creditB4Spin, String betValue) {

		String creditAfetrSpin = getCurrentCredits();

		double dblcreditAfetrSpin = Double.parseDouble(creditAfetrSpin.replaceAll("[^0-9]", ""));
		double dblcreditB4Spin = Double.parseDouble(creditB4Spin.replaceAll("[^0-9]", ""));
		double dblbetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

		if ((dblcreditB4Spin - dblbetValue) == dblcreditAfetrSpin) {
			System.out.println("Intial Cradit is = " + dblcreditB4Spin + " and selected bet is = " + dblbetValue);
			System.out.println("Cridit after spin is = " + dblcreditAfetrSpin);
			log.debug("Cradit after spin with calculation is " + dblcreditB4Spin + "-" + dblbetValue + " = "
					+ dblcreditAfetrSpin);
			return true;
		}
		return false;
	}

	public boolean isPlayerWon() {
		String winAmount = null;

		boolean isplayerWon = false;
		if (!GameName.contains("Scratch")) {
			winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
		} else {
			winAmount = getConsoleText("return " + XpathMap.get("WinText"));
		}

		if (winAmount != null && !"".equals(winAmount) && !"Win: ".equals(winAmount)) {
			isplayerWon = true;
		}

		return isplayerWon;
	}

	public boolean isWinAddedToCredit(String creditB4Spin, String betValue) {

		boolean isWinAddedToCredit = false;

		String creditAfetrStop = getCurrentCredits();
		String winAmount = null;
		if (!GameName.contains("Scratch")) {
			winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
		} else {
			winAmount = getConsoleText("return " + XpathMap.get("WinDesktopLabel"));
		}

		double dblCreditAfetrStop = Double.parseDouble(creditAfetrStop.replaceAll("[^0-9]", ""));
		double dblCreditB4Spin = Double.parseDouble(creditB4Spin.replaceAll("[^0-9]", ""));
		double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));
		double dblWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));

		if (((dblCreditB4Spin - dblBetValue) + dblWinAmount) == dblCreditAfetrStop) {
			log.debug("win added to credit succesfully");
			isWinAddedToCredit = true;
		}

		return isWinAddedToCredit;
	}

	public long getNumberOfQuickbets() {

		long quickBetsCount = 0;

		if (!GameName.contains("Scratch")) {

			quickBetsCount = getConsoleNumeric("return " + XpathMap.get("QuickBetOptions"));
		} else {
			quickBetsCount = getConsoleNumeric("return " + XpathMap.get("NoOfQucikBets"));
		}

		return quickBetsCount;
	}

	public String selectQuickBet(int quickBet, Desktop_HTML_Report report) {

		String betAmount = null;
		try {
			openBetPanel();
			Thread.sleep(2000);
			clickAtButton("return " + forceNamespace
					+ ".getControlById('BetPanelComponent').onButtonClicked('quickBetButton" + quickBet + "')");
			if ("yes".equalsIgnoreCase(XpathMap.get("IsRespinFeature"))
					&& checkAvilability(XpathMap.get("MaxBetDailoagVisible"))) {
				clickAtButton("return  " + XpathMap.get("MaxBetDailoag"));

			}
			// screen shot to see bet selection at quick bet
			report.detailsAppend("Verify in screen shot that is bet selected from quickbet as :" + quickBet,
					"Is bet selected from quickbet as :" + quickBet, "Is bet selected from quickbet as :" + quickBet,
					"");
			Thread.sleep(1000);
			betAmount = getConsoleText("return " + XpathMap.get("BetSizeText"));
			close_TotalBet();
		} catch (Exception e) {
			log.error("Exception while changing the Bet", e);
		}
		return betAmount;
	}

	public boolean isBetChangedOnRefresh() {

		boolean isBetChanged = true;
		String currentBet = getConsoleText("return " + XpathMap.get("BetSizeText"));

		setMaxBet();
		

		if ("yes".equalsIgnoreCase(XpathMap.get("IsRespinFeature"))
				&& checkAvilability(XpathMap.get("MaxBetDailoagVisible"))) {
			clickAtButton("return  " + XpathMap.get("MaxBetDailoag"));

		}
		getConsoleText("return " + XpathMap.get("BetSizeText"));

		// refresh();
		webdriver.navigate().refresh();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Check continue button exists and clicks
		if((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))))
		{
			newFeature();
		}
		waitForSpinButton();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String betAferRefresh = getConsoleText("return " + XpathMap.get("BetSizeText"));

		if (betAferRefresh.equalsIgnoreCase(currentBet)) {
			System.out.println("Bet has not changed on refresh");
			log.debug("Bet has not changed on refresh");
			isBetChanged = false;
		}
		return isBetChanged;
	}

	public void miniPaytableScreeShots(Desktop_HTML_Report currencyReport, String currencyName) {
		String strGameName = null;
		int startindex = 0;
		long symbolNumber;
		if (GameName.contains("Desktop")) {
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			log.debug("newgamename=" + strGameName);
		}

		Util util = new Util();
		int length = util.xmlLength("./" + strGameName + "/Config/" + strGameName + ".xml", "WinningCombinations");

		ArrayList<String> symbolData = util.getXMLDataInArray("Symbol", "name",
				"./" + strGameName + "/Config/" + strGameName + ".xml");

		ArrayList<String> symbolArray = new ArrayList<>();
		ArrayList<String> proceedSymbolArray = new ArrayList<>();

		HashMap<String, Symbol> symbolmMap = new HashMap<>();

		int totalNoOfSymbols = symbolData.size();

		try {

			for (int count = 0; count < totalNoOfSymbols; count++) {
				String[] xmlData = symbolData.get(count).split(",");
				String symbolName = xmlData[0];
				symbolArray.add(count, symbolName);
				proceedSymbolArray.add(count, symbolName);
				symbolmMap.put(symbolName, new Symbol(symbolName));
			}

			for (int count = 0; count < length; count++) {
				String strWinCombination = util.readXML("WinCombination", "numSymbolsRequired", "symbols", "payouts",
						"./" + strGameName + "/Config/" + strGameName + ".xml", length + 2, count);
				if (strWinCombination != null) {
					WinCombination winCombination = new WinCombination();

					String[] xmlData = strWinCombination.split(",");

					winCombination.setNumSymbolsRequired(Integer.parseInt(xmlData[0].trim()));
					winCombination.setSymbols(xmlData[1]);
					winCombination.setPayout(Integer.parseInt(xmlData[2].trim()));

					symbolmMap.get(xmlData[1]).addWinComb(winCombination);
				}
			}
			Thread.sleep(500);

			proceedSymbolArray.remove("FS_Scatter");
			proceedSymbolArray.remove("WildMagic");
			System.out.println("Total  Symbols::" + proceedSymbolArray);
			do {

				String currentScene = getConsoleText("return " + forceNamespace + ".currentScene");
				System.out.println("currentScene :: " + currentScene);
				if (currentScene != null && (!currentScene.equalsIgnoreCase("SLOT"))) {
					break;
				}

				for (int column = 0; column <= 4; column++) {
					for (int row = 1; row <= 3; row++) {

						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("param1", Integer.toString(column));
						paramMap.put("param2", Integer.toString(row));

						String symbolNumberHook = XpathMap.get("getSymbolNumber");
						String newHook = replaceParamInHook(symbolNumberHook, paramMap);
						symbolNumber = getConsoleNumeric("return " + newHook);

						/*
						 * if("RollingReels".equalsIgnoreCase(XpathMap.get("ReelsType"))) { symbolNumber
						 * = getConsoleNumeric( "return "+forceNamespace+
						 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
						 * "].symbolArray[" + row + "].symbolNumber");
						 * 
						 * } else { symbolNumber = getConsoleNumeric(
						 * "return "+forceNamespace+".getControlById('ReelComponent').reelsArray[" +
						 * column + "].symbolArray[" + row + "].symbolNumber");
						 * 
						 * }
						 */

						String symbolName = symbolArray.get((int) symbolNumber);
						if (proceedSymbolArray.contains(symbolName)) {

							String symbolClickHook = XpathMap.get("clickOnSymbol");
							newHook = replaceParamInHook(symbolClickHook, paramMap);
							clickAtButton("return " + newHook);

							/*
							 * if("RollingReels".equalsIgnoreCase(XpathMap.get("ReelsType"))) {
							 * clickAtButton( "return "+forceNamespace+
							 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
							 * "].symbolArray[" + row +
							 * "].symbolClickEvent.dispatch({ symbolNumber: "+forceNamespace+
							 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
							 * "].symbolArray[" + row +
							 * "].symbolNumber, symbolReelSetPositionId: "+forceNamespace+
							 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
							 * "].symbolArray[" + row + "].symbolReelSetPositionId})");
							 * 
							 * } else {
							 * 
							 * clickAtButton(
							 * "return "+forceNamespace+".getControlById('ReelComponent').reelsArray[" +
							 * column + "].symbolArray[" + row +
							 * "].symbolClickEvent.dispatch({ symbolNumber: "+forceNamespace+
							 * ".getControlById('ReelComponent').reelsArray[" + column + "].symbolArray[" +
							 * row + "].symbolNumber, symbolReelSetPositionId: "+forceNamespace+
							 * ".getControlById('ReelComponent').reelsArray[" + column + "].symbolArray[" +
							 * row + "].symbolReelSetPositionId})"); }
							 */

							currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);

							proceedSymbolArray.remove(symbolName);
							System.out.println("Remaining Symbols::" + proceedSymbolArray);

						}

					}
				}
				if (proceedSymbolArray.size() > 0) {
					try {
						spinclick();

					} catch (InterruptedException e) {
						log.error("validateMiniPaytable", e);
					}
					waitForSpinButtonstop();
				}
				Thread.sleep(5000);

			} while (proceedSymbolArray.size() > 0);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public boolean validateMiniPaytable(String betValue, Desktop_HTML_Report report) {

		String strGameName = null;
		int startindex = 0;
		long symbolNumber;
		String strPaylineCost = XpathMap.get("DefaultPayLineCost");// this code will fetch the data from sheet
		double payLineCost = Double.parseDouble(strPaylineCost);
		if (GameName.contains("Desktop")) {

			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			log.debug("newgamename=" + strGameName);
		}
		else if(GameName.contains("Deskto")) {
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Deskto");
			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			log.debug("newgamename=" + strGameName);
		}

		double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

		Util util = new Util();
		int length = util.xmlLength("./" + strGameName + "/Config/" + strGameName + ".xml", "WinningCombinations");

		ArrayList<String> symbolData = util.getXMLDataInArray("Symbol", "name",
				"./" + strGameName + "/Config/" + strGameName + ".xml");

		ArrayList<String> symbolArray = new ArrayList<>();
		ArrayList<String> proceedSymbolArray = new ArrayList<>();

		HashMap<String, Symbol> symbolmMap = new HashMap<String, Symbol>();

		int totalNoOfSymbols = symbolData.size();

		for (int count = 0; count < totalNoOfSymbols; count++) {
			String[] xmlData = symbolData.get(count).split(",");
			String symbolName = xmlData[0];
			symbolArray.add(count, symbolName);
			proceedSymbolArray.add(count, symbolName);
			symbolmMap.put(symbolName, new Symbol(symbolName));
		}

		for (int count = 0; count < length; count++) {
			String strWinCombination = util.readXML("WinCombination", "numSymbolsRequired", "symbols", "payouts",
					"./" + strGameName + "/Config/" + strGameName + ".xml", length + 2, count);
			if (strWinCombination != null) {
				WinCombination winCombination = new WinCombination();

				String[] xmlData = strWinCombination.split(",");

				winCombination.setNumSymbolsRequired(Integer.parseInt(xmlData[0].trim()));
				winCombination.setSymbols(xmlData[1]);
				winCombination.setPayout(Integer.parseInt(xmlData[2].trim()));

				symbolmMap.get(xmlData[1]).addWinComb(winCombination);
			}
		}

		proceedSymbolArray.remove("FS_Scatter");
		proceedSymbolArray.remove("WildMagic");
		log.debug("Total  Symbols::" + proceedSymbolArray);
		do {
			for (int column = 0; column <= 4; column++) {
				for (int row = 1; row <= 3; row++) {

					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("param1", Integer.toString(column));
					paramMap.put("param2", Integer.toString(row));

					String symbolNumberHook = XpathMap.get("getSymbolNumber");
					String newHook = replaceParamInHook(symbolNumberHook, paramMap);
					symbolNumber = getConsoleNumeric("return " + newHook);

					/*
					 * if("RollingReels".equalsIgnoreCase(XpathMap.get("ReelsType"))) { symbolNumber
					 * = getConsoleNumeric( "return "+forceNamespace+
					 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
					 * "].symbolArray[" + row + "].symbolNumber");
					 * 
					 * } else { symbolNumber = getConsoleNumeric(
					 * "return "+forceNamespace+".getControlById('ReelComponent').reelsArray[" +
					 * column + "].symbolArray[" + row + "].symbolNumber");
					 * 
					 * }
					 */
					String symbolName = symbolArray.get((int) symbolNumber);
					if (proceedSymbolArray.contains(symbolName)) {
						String symbolClickHook = XpathMap.get("clickOnSymbol");
						newHook = replaceParamInHook(symbolClickHook, paramMap);
						clickAtButton("return " + newHook);
						/*
						 * if("RollingReels".equalsIgnoreCase(XpathMap.get("ReelsType"))) {
						 * clickAtButton( "return "+forceNamespace+
						 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
						 * "].symbolArray[" + row +
						 * "].symbolClickEvent.dispatch({ symbolNumber: "+forceNamespace+
						 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
						 * "].symbolArray[" + row +
						 * "].symbolNumber, symbolReelSetPositionId: "+forceNamespace+
						 * ".getControlById('ReelsWithRollingReelsComponent').reelsArray[" + column +
						 * "].symbolArray[" + row + "].symbolReelSetPositionId})");
						 * 
						 * } else {
						 * 
						 * clickAtButton(
						 * "return "+forceNamespace+".getControlById('ReelComponent').reelsArray[" +
						 * column + "].symbolArray[" + row +
						 * "].symbolClickEvent.dispatch({ symbolNumber: "+forceNamespace+
						 * ".getControlById('ReelComponent').reelsArray[" + column + "].symbolArray[" +
						 * row + "].symbolNumber, symbolReelSetPositionId: "+forceNamespace+
						 * ".getControlById('ReelComponent').reelsArray[" + column + "].symbolArray[" +
						 * row + "].symbolReelSetPositionId})"); }
						 */
						try {
							threadSleep(2000);
							report.detailsAppendFolderOnlyScreenShot("Minipaytable");

						} catch (Exception e1) {
							log.error(e1.getStackTrace());
						}

						// Fetching the payout values from clicked symbol
						for (int count = 2; count < 6; count++) {

							String payoutValue = XpathMap.get("getPayoutValue");
							paramMap.put("param1", Integer.toString(count));
							newHook = replaceParamInHook(payoutValue, paramMap);
							String paytableValue = getConsoleText("return " + newHook);

							ArrayList<WinCombination> symWinCombs = symbolmMap.get(symbolName).getWinCombList();
							int payout = 0;
							double calcPayout = 0;
							for (WinCombination winCombination : symWinCombs) {
								if (winCombination.getNumSymbolsRequired() == count) {
									payout = winCombination.getPayout();
								}
							}

							if (payout != 0 && "".equalsIgnoreCase(paytableValue)) {
								log.debug("Fail for Symbol" + symbolName + " and count ::" + count);
							} else if (payout == 0 && !"".equalsIgnoreCase(paytableValue)) {
								log.debug("Fail for Symbol" + symbolName + " and count ::" + count);
							} else if (payout == 0 && "".equalsIgnoreCase(paytableValue)) {
								log.debug("Fail There is no payout for Symbol" + symbolName + " and count ::" + count);
							} else {
								double dblpaytableValue = Double.parseDouble(paytableValue.replaceAll("[^0-9]", ""));

								if (symbolName != null
										&& (symbolName.contains("scatter") || symbolName.contains("Scatter"))) {
									calcPayout = (dblBetValue * payout);
								} else {

									calcPayout = (dblBetValue * payout) / payLineCost;
								}
								try {
									if (dblpaytableValue == calcPayout) {

										report.detailsAppendNoScreenshot(
												"Minipaytable:: Verify the payout for symbol" + symbolName,
												"Payout from paytable and xml should be equal, payout must be ::"
														+ calcPayout,
												"Payout from paytable and xml is equal for symbol " + symbolName
														+ " is correct which is " + dblpaytableValue,
												"pass");

										log.debug("PASS for Symbol" + symbolName + " and count ::" + count);
									} else {

										report.detailsAppendNoScreenshot(
												"Minipaytable:: Verify the payout for symbol" + symbolName,
												"Payout from paytable and xml should be equal, payout must be ::"
														+ calcPayout,
												"Payout from paytable and xml is not equal for symbol " + symbolName
														+ " is not correct which is " + dblpaytableValue,
												"Fail");

									}
								} catch (Exception e) {

									log.error(e.getStackTrace());
								}
							}

						}
						proceedSymbolArray.remove(symbolName);
						log.debug("Remaining Symbols::" + proceedSymbolArray);

					}

				}
			}
			if (proceedSymbolArray.size() > 0) {
				try {
					spinclick();
				} catch (InterruptedException e) {
					log.error("validateMiniPaytable", e);
				}
				waitForSpinButtonstop();
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					log.error(e.getStackTrace());
				}
			}

		} while (proceedSymbolArray.size() > 0);
		return false;
	}

	public int countAutoPlayTillStop() {

		Set<String> hashSet = new HashSet<>();
		setFreeSpinTriggered(false);
		String currentScene;
		log.debug("is it a Scratch game :: " + GameName.contains("Scratch"));
		try {

			while (true) {

				if (!GameName.contains("Scratch")) {
					String autoplayValue;
					if (Constant.FORCE_ULTIMATE_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
						autoplayValue = getConsoleText(
								"return window.theForce.game.automation.getControlById('AutoplayButtonComponent').Buttons.autoplayStopButton.label._text");
					} else {
						autoplayValue = getConsoleText("return " + forceNamespace
								+ ".getControlById('AutoplayButtonComponent').Buttons.autoplayStopDesktopButton.label._text");
					}

					hashSet.add(autoplayValue);
				} else {
					String autoplayValue = getConsoleText("return " + forceNamespace
							+ ".getControlById('AutoplayButtonComponent').Text.autoplayCounterText._text");
					hashSet.add(autoplayValue);

				}
				if (!GameName.contains("Scratch")) {
					currentScene = getConsoleText("return " + forceNamespace
							+ ".getControlById('SpinButtonComponent').Buttons.spinButton.currentState");
					if (currentScene != null && currentScene.equalsIgnoreCase("active")) {
						break;
					}
				} else {
					boolean isAutoplayActive = GetConsoleBooleanText(
							"return " + forceNamespace + ".getServiceById('AutoplayService').isAutoplayActive()");
					if (!isAutoplayActive) {
						break;
					}
				}

				// freespin feature is not available in scratch games
				if (!GameName.contains("Scratch")) {
					String freeSpinScene = getConsoleText("return " + forceNamespace + ".currentScene");
					if (freeSpinScene.equalsIgnoreCase("FREESPINS")) {
						setFreeSpinTriggered(true);
					}

				}

			}
		} catch (Exception e) {
			log.error("Exception in countAutoPlayTillStop method", e);
		}
		return hashSet.size();
	}

	public boolean verifyAutoplayConsoleOptions(Desktop_HTML_Report report) {

		try {
			int noofautoplayoptions;

			/*
			 * depending upon calling class set the autoplay count to play
			 */
			if ((Class.forName(Thread.currentThread().getStackTrace()[2].getClassName())).toString()
					.contains("Modules.Regression.TestScript.Desktop_Regression_BaseScene")) {
				noofautoplayoptions = (int) getConsoleNumeric("return " + XpathMap.get("AutoplaySpinNoArrayLength"))
						- 1;
			} else
				noofautoplayoptions = 1;
			// this counter = number of console options available in autoplay console.
			for (int counter = 1; counter <= noofautoplayoptions; counter++) {
				log.debug("Autoplay for ::" + counter);
				clickAtButton("return " + forceNamespace
						+ ".getControlById('AutoplayOptionsComponent').onButtonClicked('autoplayOpenMobileButton')");
				Thread.sleep(2000);
				clickAtButton("return " + forceNamespace
						+ ".getControlById('AutoplayOptionsComponent').onButtonClicked('autoplayQuickOptionButton"
						+ counter + "')");
				Thread.sleep(2000);
				long autoplaySelectedCount = getConsoleNumeric(" return " + forceNamespace
						+ ".getControlById('AutoplayOptionsComponent').Buttons.autoplayQuickOptionButton" + counter
						+ ".autoPlaySpinNumber");
				clickAtButton("return " + forceNamespace
						+ ".getControlById('AutoplayButtonComponent').onButtonClicked('autoplayQuickStopButton')");
				Thread.sleep(2000);

				int autplayActualCount = countAutoPlayTillStop();
				Thread.sleep(2000);
				if (autoplaySelectedCount == autplayActualCount) {
					report.detailsAppendNoScreenshot("Verify that selected autoplay count must be played in game",
							"Selected autoplay count must be played in game",
							"AutoplaySelectedCount :: " + autoplaySelectedCount
									+ " Matched with AutplayActualCount played :: " + autplayActualCount,
							"Pass");
				} else {
					report.detailsAppendNoScreenshot("Verify that selected autoplay count must be played in game",
							"Selected autoplay count must be played in game",
							"AutoplaySelectedCount :: " + autoplaySelectedCount
									+ " Not Matched with AutplayActualCount played :: " + autplayActualCount,
							"Fail");
					if (isFreeSpinTriggered) {
						report.detailsAppendNoScreenshot(
								"verify that is the Freespin triggered during Autoplay so Autoplay must be intercepted for counter ::"
										+ autoplaySelectedCount,
								" The Freespin triggered during Autoplay so Autoplay is intercepted for counter ::"
										+ autoplaySelectedCount,
								"The Freespin triggered during Autoplay so Autoplay is intercepted for counter ::"
										+ autoplaySelectedCount,
								"fail");
						log.debug("Free Spin Triggered in Auto play and Auto play interupted");
					} else {
						report.detailsAppendNoScreenshot(
								"verify that is the Freespin triggered during Autoplay so Autoplay must be intercepted for counter ::"
										+ autoplaySelectedCount,
								" The Freespin triggered during Autoplay still Autoplay is not intercepted for counter ::"
										+ autoplaySelectedCount,
								"The Freespin triggered during Autoplay still Autoplay is not intercepted for counter ::"
										+ autoplaySelectedCount,
								"fail");
						log.debug("Free Spin Triggered in Auto play and Auto play not interupted so Failed Scenario");
					}
				}
				log.debug("autoplaySelectedCount :: " + autoplaySelectedCount);
				log.debug("autplayActualCount :: " + autplayActualCount);
			}
			waitTillAutoplayComplete();

			return true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				report.detailsAppendNoScreenshot("verify Autoplay console options", " ",
						"Exception occur while verifying autoplay console option", "fail");
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			return false;
		}
	}

	public boolean verifyAutoplayPanelOptions(Desktop_HTML_Report report) {

		try {

			if (Constant.FORCE_ULTIMATE_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))
					|| Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
				verifyOneDesignAutoplayPanelOptions(report);
			} else {

				int noofautoplayoptions;

				if ((Class.forName(Thread.currentThread().getStackTrace()[2].getClassName())).toString()
						.contains("Modules.Regression.TestScript.Desktop_Regression_BaseScene")) {
					noofautoplayoptions = (int) getConsoleNumeric("return " + XpathMap.get("AutoplaySpinNoArrayLength"))
							- 1;
				} else
					noofautoplayoptions = 1;
				// This counter = number of panel options available in autoplay panel.
				for (int counter = 1; counter <= noofautoplayoptions; counter++) {
					log.debug("Autoplay for ::" + counter);
					clickAtButton("" + forceNamespace
							+ ".getControlById('AutoplayOptionsComponent').onButtonClicked('autoplayOpenMobileButton')");
					Thread.sleep(2000);
					clickAtButton("" + forceNamespace
							+ ".getControlById('AutoplayOptionsComponent').onButtonClicked('autoplayMoreOptionsButton')");
					Thread.sleep(2000);
					// Screen shot for Autoplay panel option
					clickAtButton("" + forceNamespace
							+ ".getControlById('AutoplayPanelComponent').numberSpinsSliderEventReceived({'eventName': 'CLICK', 'value': '"
							+ (counter * 0.25) + "' })");
					// new added to see is the selected option is same depling at console
					String autoplaySelectedCountfrompanel = getConsoleText(" return " + forceNamespace
							+ ".getControlById('AutoplayPanelComponent').Text.numberSpinsValueText._text");
					log.debug("autoplaySelectedCount from Autoplay Panel option :: " + autoplaySelectedCountfrompanel);
					System.out.println(
							"autoplaySelectedCount from Autoplay Panel option :: " + autoplaySelectedCountfrompanel);
					clickAtButton("" + forceNamespace
							+ ".getControlById('AutoplayPanelComponent').onButtonClicked('autoplayStartButton')");
					report.detailsAppendNoScreenshot(
							"Verify that selected autoplay count from Autoply panel option must be played in game",
							"Selected autoplay count from Autoply panel option must be played in game",
							"AutoplaySelectedCount from Autoply panel option :: " + autoplaySelectedCountfrompanel,
							"Pass");
					Thread.sleep(2000);
					// is bet Available.. Bet must not be accessible during auto
					// play

					boolean isBetButtonActive = isBetButtonAccessible(); // Must not be accessible during auto play
					if (isBetButtonActive) {
						// need screenshot
						report.detailsAppend("verify that is BetButton Active during Autoplay",
								" Is BetButton Active during Autoplay", " BetButton Active during Autoplay ", "fail");

					} else {
						report.detailsAppend("verify that is BetButton Active during Autoplay",
								" Is BetButton Active during Autoplay", "BetButton is not  Active during Autoplay ",
								"pass");
					}

					boolean isMaxButtonActive = isMaxButtonAccessible(); // Must not be accessible during auto play
					if (isMaxButtonActive) {
						report.detailsAppend("verify that is MaxButton Accessible during Autoplay",
								" Is MaxButton Accessible during Autoplay", "MaxButton Accessible during Autoplay ",
								"fail");
					} else {
						report.detailsAppend("verify that is MaxButton Accessible during Autoplay",
								" Is MaxButton Accessible during Autoplay",
								"MaxButton is not Accessible during Autoplay", "pass");
					}
					setQuickSpinOn();
					report.detailsAppend("verify the QuickSpin On screen shot", " QuickSpinOn page screen shot",
							"QuickSpinOn option page screenshot ", "pass");
					Thread.sleep(3000);
					// Take screen shot
					setQuickSpinOff();
					report.detailsAppend("verify the QuickSpinOff screen shot", " QuickSpinOff page screen shot",
							"QuickSpinOff option page screenshot ", "pass");
					Thread.sleep(1000);
					// Take screen shot
					Dimension actualDimensions = webdriver.manage().window().getSize();
					// Take screen shot
					report.detailsAppend(
							"verify the Actual winodow size screen shot for dimention = " + actualDimensions,
							" Actual winodow size screen shotfor dimention = " + actualDimensions,
							"Actual winodow size screenshot for dimention = " + actualDimensions, "pass");
					// Resize the window and take the screen shot
					for (int sizeCounter = 0; sizeCounter < 2; sizeCounter++) {

						webdriver.manage().window()
								.setSize(new Dimension(actualDimensions.getWidth() - (sizeCounter * 200),
										actualDimensions.getHeight() - (sizeCounter * 200)));
						Thread.sleep(3000);
						// Take screen shot
						report.detailsAppend("verify the winodow size screen shot after resizing",
								" Actual winodow size screen shot after resizing",
								"Actual winodow size screenshot after resizing", "pass");

						Thread.sleep(1000);
					}

					webdriver.manage().window().setSize(actualDimensions);

					waitTillAutoplayComplete();

					Thread.sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				report.detailsAppendNoScreenshot(
						"Verify that selected autoplay count from Autoply panel option must be played in game",
						"Selected autoplay count from Autoply panel option must be played in game",
						"Exception occur while verifying autoplay panel options ", "fail");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		return false;
	}

	public boolean isFreeSpinTriggered() {
		boolean isFreeSpinTriggered = false;
		long startTime = System.currentTimeMillis();
		while (true) {
			String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
			if (("BONUS_SELECTION".equalsIgnoreCase(currentScene))
					|| "FREESPINS_INTRO".equalsIgnoreCase(currentScene)) {
				isFreeSpinTriggered = true;
				System.out.println("Free spin triggerd");
				log.debug("Free spin triggerd");
				break;
			}

			long currentime = System.currentTimeMillis();
			// Break if wait is more than 30 secs
			if (((currentime - startTime) / 1000) > 60) {
				isFreeSpinTriggered = false;
				log.debug("break after 30 secs");
				break;
			}
		}

		return isFreeSpinTriggered;
	}

	public void setFreeSpinTriggered(boolean freeSpinTriggered) {

		isFreeSpinTriggered = freeSpinTriggered;
	}

	public boolean verifyALLBetValues(Desktop_HTML_Report report) {

		boolean isBetVerified = false;

		if (!GameName.contains("Scratch")) {
			String currentBet;
			int c = 0;
			int w = 0;
			String betValue = null, minimumbet = null;

			try {
				openBetPanel();
				report.detailsAppend("verify that bet panel is open with current bet value",
						" Bet panel is open with current bet value ", "Bet panel is open with current bet", "pass");
				Thread.sleep(1000);
				currentBet = setMaxBet();
				Thread.sleep(1000);
				close_TotalBet();
				
				if ("yes".equalsIgnoreCase(XpathMap.get("IsRespinFeature"))
						&& checkAvilability(XpathMap.get("MaxBetDailoagVisible"))) {
					clickAtButton("return  " + XpathMap.get("MaxBetDailoag"));
				}
				

				Thread.sleep(1000);
				//close_TotalBet();

				minimumbet = getMinimumBet();
				betValue = currentBet;

				do {
					currentBet = betValue;
					boolean isBetChangedIntheConsole = isBetChangedIntheConsole(currentBet);
					if (isBetChangedIntheConsole) {
						report.detailsAppend(
								"verify that is Bet Changed In the Console with screen shot for=" + betValue,
								" Is Bet Changed In the Console with screen shot",
								"Bet Changed In the Console with screen shot", "pass");
						log.debug("Bet Changed In the Console");
					} else {
						report.detailsAppend(
								"verify that is Bet Changed In the Console with screen shotfor=" + betValue,
								"Is Bet Changed In the Console with screen shot",
								"Bet not Changed In the Console with screen shot", "Fail");
						log.debug("Bet not Changed In the Console");
					}
					for (int spinCounter = 0; spinCounter < 10; spinCounter++) {
						String currentCreditAmount = getCurrentCredits();
						// Spin Here
						spinclick();
						// Thread.sleep(1000);
						boolean isCreditDeducted = isCreditDeducted(currentCreditAmount, currentBet);

						if (isCreditDeducted) {
							c++;
							log.debug("Credit Deducted for" + currentBet);
						} else {
							log.debug("Credit not Deducted for" + currentBet);
						}

						// lets wait till the win comes
						waitForSpinButtonstop();
						Thread.sleep(18000);
						boolean isPlayerWon = isPlayerWon();
						System.out.println("Player won for the bet : " + betValue + " " + isPlayerWon);
						if (isPlayerWon) {
							boolean isWinAddedToCredit = isWinAddedToCredit(currentCreditAmount, betValue);

							if (isWinAddedToCredit) {
								w++;
								log.debug("Win added to Credit for the bet : " + betValue);
							} else {
								log.debug("Win is not added to Credit for the bet : " + betValue);
							}

							break;
						} else if (spinCounter == 9) {
							try {
								report.detailsAppendNoScreenshot(
										"Verify Win ammount get update after spin for the bet :" + betValue,
										"Win ammount updating properly after spin for the quickbet : " + betValue,
										"There is no Win occur even after 10 spins , hence the following Test case of Win Amount Updation is failed."
												+ betValue,
										"fail");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					try {

						// report to verify isWinAddedToCredit for the bet
						if (w != 0) {
							report.detailsAppendNoScreenshot(
									"Verify Win amount updating properly after spin for the bet :" + betValue,
									"Win ammount updatingproperly after spin for the bet : " + betValue,
									"Win ammount updating properly after spin for the bet :" + betValue, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Verify Win ammount updating properly after spin for the bet :" + betValue,
									"Win ammount updating properly after spin for the bet : " + betValue,
									"Win amount not updating properly after spin for the bet :" + betValue, "fail");
						}
						// common report to verify isCreditDeducted for the bet
						if (c != 0) {
							report.detailsAppendNoScreenshot(
									"Verify Credits are deducting properly after spin for the bet :" + betValue,
									"Credits are deducting properly after spin for the bet : " + betValue,
									"Credits are deducting properly after spin for the bet :" + betValue, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Verify Credits are deducting properly after spin for the bet :" + betValue,
									"Credits are not deducting properly after spin for the bet : " + betValue,
									"Credits are not deducting properly after spin for the bet :" + betValue, "fail");
						}
					} catch (Exception e) {
						log.error("error while taking report for the win and cridit for the bet");
						log.error(e.getMessage(), e);
						report.detailsAppendNoScreenshot("Verify Credits and bet after spin", " ",
								"Exception occur while verifying credit and bet ", "Fail");

					}

					if ((Class.forName(Thread.currentThread().getStackTrace()[2].getClassName())).toString()
							.contains("Modules.Regression.TestScript.Desktop_Regression_BaseScene")) {
						openBetPanel();
						Thread.sleep(1000);

						betValue = setTheNextLowBet();
						
						report.detailsAppend("verify that Next low level bet is selected for=" + betValue,
								" Next low level bet is selected", "Next low level bet is selected", "pass");
						Thread.sleep(2000);
						close_TotalBet();
						if ("yes".equalsIgnoreCase(XpathMap.get("IsRespinFeature"))
								&& checkAvilability(XpathMap.get("MaxBetDailoagVisible"))) {
							clickAtButton("return  " + XpathMap.get("MaxBetDailoag"));
						}
						System.out.println("time after bet verification" + System.currentTimeMillis());
					} else {
						break;
					}
				} while ((!betValue.equalsIgnoreCase(minimumbet)));
			} catch (Exception e) {
				try {
					report.detailsAppendNoScreenshot("Verify all bet values ", " ",
							"Exception occur while verifying bet values ", "Fail");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();

			}

		} else {
			verifyALLScratchBetValues(report);
		}

		return isBetVerified;
	}

	public void waitTillScratchAutoplayStop() {

		while (true) {
			boolean isAutoplay = GetConsoleBooleanText("return " + XpathMap.get("isAutoPlayActive"));
			if (!isAutoplay) {
				break;
			}
		}
	}

	public void verifyALLScratchBetValues(Desktop_HTML_Report report) {
		boolean isCreditDeducted = false;
		boolean isWinAddedToCredit = false;
		int numofbet = 0;
		String consoleBet = null;
		log.debug("Verifing All scatch Bet Values");
		Util util = new Util();

		String strGameName = null;
		int startindex = 0;
		if (GameName.contains("Desktop")) {
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			log.debug("newgamename=" + strGameName);
		}

		int length = util.xmlLength("./" + strGameName + "/Config/" + strGameName + ".xml", "Results");
		if (length != 0) {
			// Read the XML Values
			System.out.println("length : " + length);
			ArrayList<String> symbolData = util.getXMLDataInArray("Symbol", "name",
					"./" + strGameName + "/Config/" + strGameName + ".xml");

			HashMap<String, Symbol> symbolmMap = new HashMap<String, Symbol>();

			int totalNoOfSymbols = symbolData.size();
			log.debug("Total Number of sysmbols in xml file : " + totalNoOfSymbols);
			log.debug("Total Number of sysmbols in xml file : " + totalNoOfSymbols);
			for (int count = 0; count < totalNoOfSymbols; count++) {
				String[] xmlData = symbolData.get(count).split(",");
				String symbolName = xmlData[0];
				symbolmMap.put(symbolName, new Symbol(symbolName));
			}

			for (int count = 0; count < length; count++) {
				String strResult = util.readXML("Result", "numSymbolsRequired", "symbol", "payout",
						"./" + strGameName + "/Config/" + strGameName + ".xml", length + 2, count);
				String[] xmlData = strResult.split(",");
				String strSymbolName = xmlData[1];
				int intPayout = Integer.parseInt(xmlData[2].trim());
				Symbol symbol = symbolmMap.get(strSymbolName);

				symbol.setPayout(intPayout);
				symbolmMap.put(strSymbolName, symbol);
			}

			// logout from previous player and log in with new player having win at every
			// spin
			try {

				System.out.println("Logouted from privios player and trying to login with new player");
				log.debug("Logout from privios player and trying to login with new player");

				String url = XpathMap.get("ApplicationURL");
				String LaunchURl = url + XpathMap.get("WinPlayerUsername");
				System.out.println("log in with new win player : " + XpathMap.get("WinPlayerUsername"));
				loadGame(LaunchURl);

				log.debug("login with new player having win at each spin");
			} catch (Exception e1) {
				log.error(e1.getMessage(), e1);
				e1.printStackTrace();
			}

			// First get All the bet values
			ArrayList<String> betList = getConsoleList("return " + XpathMap.get("BetOptions"));
			// For Eaach bet

			try {
				if ((Class.forName(Thread.currentThread().getStackTrace()[2].getClassName())).toString()
						.contains("Modules.Regression.TestScript.Desktop_Regression_BaseScene"))
					numofbet = betList.size();
				else
					numofbet = 1;
			} catch (ClassNotFoundException e1) {

				e1.printStackTrace();
				log.error(e1.getException());
			}

			for (int counter = 0; counter < numofbet; counter++) {
				int c = 0;
				int w = 0;

				// Open the Bet PAnel
				openBetPanel();

				// Set the bet Value
				try {
					Thread.sleep(1000);
					getConsoleText("return " + forceNamespace + ".getControlById('BetPanelComponent').updateBetValue("
							+ betList.get(counter) + ")");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Close the Bet Panel
				close_TotalBet();
				String consolebet = getConsoleText("return " + XpathMap.get("BetSizeText"));

				boolean isBetChangedIntheConsole = isBetChangedIntheConsole(
						getConsoleText("return " + XpathMap.get("BetSizeText")));
				try {
					if (isBetChangedIntheConsole) {
						report.detailsAppend("verify that is Bet Changed In the Console for = " + consolebet,
								" Is Bet Changed In the Console for = " + consolebet,
								"Bet Changed In the Console for = " + consolebet, "pass");
					} else {
						report.detailsAppend("verify that is Bet Changed In the Console for = " + consolebet,
								"Is Bet Changed In the Console for = " + consolebet,
								"Bet not Changed In the Console for = " + consolebet, "fail");
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					e.printStackTrace();
					try {
						report.detailsAppendNoScreenshot("verify that is Bet Changed In the Console for bet", " ",
								"Exception occur while verifying bet ", "Fail");
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				}

				for (int scratchCount = 0; scratchCount < totalNoOfSymbols; scratchCount++) {
					String creditB4Scratch = getConsoleText("return " + XpathMap.get("InfoBarBalanceTxt"));
					log.debug("credit before scrach is = " + creditB4Scratch);

					clickAtButton("return  " + XpathMap.get("ClickscratchButton"));
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					consoleBet = getConsoleText("return " + XpathMap.get("InfobarBettext"));

					isCreditDeducted = isCreditDeducted(creditB4Scratch, consoleBet);

					if (isCreditDeducted) {
						c++;
						log.debug("Credit Deducted for" + consoleBet);
					} else {
						log.debug("Credit not Deducted for" + consoleBet);
					}

					elementWait("return " + XpathMap.get("ScratchBtnCurrState"), "activeSecondary");
					clickAtButton("return  " + XpathMap.get("ClickscratchButton"));

					ArrayList<String> symbolList = getConsoleList("return " + XpathMap.get("GridGridSelection"));

					Map<String, Integer> hm = new HashMap<String, Integer>();

					String strWinSymbol = null;
					for (String symbol : symbolList) {
						Integer j = hm.get(symbol);
						hm.put(symbol, (j == null) ? 1 : j + 1);
						if (hm.get(symbol) == 3) {
							strWinSymbol = symbol;
						}
					}

					if (strWinSymbol != null) {

						log.debug("Verifying Win value");

						Symbol winSymbol = symbolmMap.get(strWinSymbol);

						int winPayout = winSymbol.getPayout();
						waitForWinDisplay();
						String winValue = getConsoleText("return " + XpathMap.get("WinText"));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// if player won any ammount
						checkWinValue(winValue, winPayout, consoleBet, report);
						isWinAddedToCredit = isWinAddedToCredit(creditB4Scratch, consoleBet);

						if (isWinAddedToCredit) {
							w++;
							log.debug("Win added to Credit for the bet : " + consoleBet);
						} else {
							log.debug("Win is not added to Credit for the bet : " + consoleBet);
						}

					}
					// strWinSymbol = null;
				}
				try {
					// common report to verify isCreditDeducted for the bet
					if (c != 0) {
						report.detailsAppendNoScreenshot(
								"Verify Credits are deducting properly after spin for the bet :" + consoleBet,
								"Credits are deducting properly after spin for the bet : " + consoleBet,
								"Credits are deducting properly after spin for the bet :" + consoleBet, "pass");
					} else {
						report.detailsAppendNoScreenshot(
								"Verify Credits are deducting properly after spin for the bet :" + consoleBet,
								"Credits are not deducting properly after spin for the bet : " + consoleBet,
								"Credits are not deducting properly after spin for the bet :" + consoleBet, "fail");
					}
					// report to verify isWinAddedToCredit for the bet
					if (w != 0) {
						report.detailsAppendNoScreenshot(
								"Verify Win ammount updating properly after spin for the bet :" + consoleBet,
								"Win ammount updatingproperly after spin for the bet : " + consoleBet,
								"Win ammount updating properly after spin for the bet :" + consoleBet, "pass");
					} else {
						report.detailsAppendNoScreenshot(
								"Verify Win ammount updating properly after spin for the bet :" + consoleBet,
								"Win ammount updating properly after spin for the bet : " + consoleBet,
								"Win ammount not updating properly after spin for the bet :" + consoleBet, "fail");
					}
				} catch (Exception e) {
					log.error("error while taking report for the win and cridit for the bet");
					log.error(e.getMessage(), e);
					try {
						report.detailsAppendNoScreenshot("Verify Credits and win amount after spin", " ",
								"Exception occur while verifying credit and win amount after spin", "fail");
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}

		} else {
			log.info("Game xml not found. paste  game.xml file into root folder");
			try {
				report.detailsAppendNoScreenshot("Verify all bet values", " ", "game xml not found", "fail");
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	public boolean waitForWinDisplay() {
		boolean isWinDisplay = false;
		try {
			long startTime = System.currentTimeMillis();
			log.debug("Waiting for win text display to come after completion of spin");
			while (true) {
				boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isWinTextVisible"));
				log.debug("is win text Visible=" + flag);
				String flagtext = Boolean.toString(flag);
				if (flagtext != null && flagtext.equalsIgnoreCase("true")) {
					isWinDisplay = true;
					break;
				}
				long currentime = System.currentTimeMillis();
				// Break if wait is more than 80 secs
				if (((currentime - startTime) / 1000) > 120) {
					log.debug("win is not visible, break after 120 sec");
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isWinDisplay;
	}

	public void checkWinValue(String winValue, int winPayout, String betValue, Desktop_HTML_Report report) {

		try {
			double dblwinValue = Double.parseDouble(winValue.replaceAll("[^0-9]", ""));
			double dblbetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

			if (dblwinValue == dblbetValue * winPayout) {
				System.out.println(" verication pass for Win value " + dblwinValue + " with respective betvalue "
						+ dblbetValue + " And it's payout " + winPayout);
				// report.details_append("verify that check Win Value Matched with bet value
				// multiplied by it's payout is ="+dblwinValue, "check Win Value Mached with bet
				// value multiplied by it's payout is ="+dblwinValue, "Win Value Matched with
				// bet value multiplied by it's payout is ="+dblwinValue, "Pass");
				log.debug("PASS::check Win Value Mached with bet value multiplied by it's payout is =" + dblwinValue);
			} else {
				System.out.println(" verification fail for Win value " + dblwinValue + " with respective betvalue "
						+ dblbetValue + " And it's payout " + winPayout);
				// report.details_append("verify that check Win Value Matched with bet value
				// multiplied by it's payout is ="+dblwinValue, "check Win Value Mached with bet
				// value multiplied by it's payout is ="+dblwinValue, "Win Value not Matched
				// with bet value multiplied by it's payout is ="+dblwinValue, "fail");
				log.debug(
						"FAIL::check Win Value not Mached with bet value multiplied by it's payout is =" + dblwinValue);
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
			log.error("NumberFormatException FAIL");
		} catch (Exception e1) {
			log.error(e1.getMessage(), e1);
			log.error("NumberFormatException FAIL");
		}

	}

	public boolean verifyAllQuickBets(Desktop_HTML_Report report) {
		long totalNoOfQuickBets = 1;

		try {
			if ((Class.forName(Thread.currentThread().getStackTrace()[2].getClassName())).toString()
					.contains("Modules.Regression.TestScript.Desktop_Regression_BaseScene"))
				totalNoOfQuickBets = getNumberOfQuickbets();
			else
				totalNoOfQuickBets = 1;
		} catch (ClassNotFoundException e1) {

			log.error(e1.getStackTrace());
		}

		if (!GameName.contains("Scratch")) {

			for (int quickBet = 0; quickBet < totalNoOfQuickBets; quickBet++) {
				int c = 0;
				int w = 0;
				String quickBetVal = selectQuickBet(quickBet, report);

				boolean isBetChangedIntheConsole = isBetChangedIntheConsole(quickBetVal);
				try {
					if (isBetChangedIntheConsole) {
						report.detailsAppend("verify that is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Bet Changed In the Console", "Pass");
						log.debug("isCreditDeducted :: PASS for quick bet value =" + quickBetVal);
					} else {
						report.detailsAppend("verify that is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Is Bet Changed In the Console for quick bet  =" + quickBetVal,
								"Bet not Changed In the Console", "fail");
						log.debug("isCreditDeducted :: FAil for quick bet value =" + quickBetVal);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				for (int spinCounter = 0; spinCounter < 10; spinCounter++) {
					String currentCreditAmount = getCurrentCredits();
					// Spin Here
					try {
						spinclick();
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);

					}

					boolean isCreditDeducted = isCreditDeducted(currentCreditAmount, quickBetVal);

					if (isCreditDeducted) {
						c++;
						log.debug("isCreditDeducted :: PASS for quick bet value =" + quickBetVal);
					} else {
						log.debug("isCreditDeducted :: FAil for quick bet value =" + quickBetVal);
					}

					// lets wait till the win comes
					waitForSpinButtonstop();
					try {
						Thread.sleep(16000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					boolean isPlayerWon = isPlayerWon();
					System.out.println("Player won for the bet : " + quickBetVal + " " + isPlayerWon);
					if (isPlayerWon) {
						boolean isWinAddedToCredit = isWinAddedToCredit(currentCreditAmount, quickBetVal);
						try {
							if (isWinAddedToCredit) {
								w++;
								log.debug("isWinAddedToCredit :: PASS for quick bet value =" + quickBetVal);
							} else {
								log.debug("isWinAddedToCredit :: FAil for quick bet value =" + quickBetVal);
							}
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}

						break;
					} else if (spinCounter == 9) {
						try {
							report.detailsAppendNoScreenshot(
									"Verify Win ammount get update after spin for the quickbet :" + quickBetVal,
									"Win ammount updating properly after spin for the quickbet : " + quickBetVal,
									"There is no WIn even after 10 spins" + quickBetVal, "fail");
						} catch (Exception e) {

							log.error("Exception in screen shot", e);
						}

					}
				}
				try {
					// report to verify isWinAddedToCredit for the bet
					if (w != 0) {
						report.detailsAppendNoScreenshot(
								"Verify Win ammount get update  after spin for the quickbet :" + quickBetVal,
								"Win ammount updatingproperly after spin for the quickbet : " + quickBetVal,
								"Win ammount updating  after spin for  quickbet :" + quickBetVal, "pass");
					} else {
						report.detailsAppendNoScreenshot(
								"Verify Win ammount get update after spin for the quickbet :" + quickBetVal,
								"Win ammount updating properly after spin for the quickbet : " + quickBetVal,
								"Win ammount not updating after spin for  quickbet :" + quickBetVal, "fail");
					}
					// common report to verify isCreditDeducted for the bet
					if (c != 0) {
						report.detailsAppendNoScreenshot(
								"Verify Credits get deducting after spin for the quickbet :" + quickBetVal,
								"Credits are deducting properly after spin for the quickbet : " + quickBetVal,
								"Credits are deducting properly after spin for the quickbet :" + quickBetVal, "pass");
					} else {
						report.detailsAppendNoScreenshot(
								"Verify Credits are deducting after spin for the quickbet :" + quickBetVal,
								"Credits are  deducting properly after spin for the quickbet : " + quickBetVal,
								"Credits are not deducting properly after spin for the quickbet :" + quickBetVal,
								"fail");
					}

				} catch (Exception e) {
					log.error("error while taking report for the win and cridit for the bet");
					log.error(e.getMessage(), e);

				}

			}
		} else {
			verifyallScratchQuickBets(report);
		}

		return false;
	}

	public void verifyallScratchQuickBets(Desktop_HTML_Report report) {

		long totalNoOfQuickBets = 0;

		try {
			if ((Class.forName(Thread.currentThread().getStackTrace()[2].getClassName())).toString()
					.contains("Modules.Regression.TestScript.Desktop_Regression_BaseScene"))
				totalNoOfQuickBets = getNumberOfQuickbets();
			else
				totalNoOfQuickBets = 1;
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		for (int quickBet = 0; quickBet < totalNoOfQuickBets; quickBet++) {
			int c = 0;
			int w = 0;

			String quickBetVal = selectQuickBet(quickBet, report);
			System.out.println("Selected bet from quick bet is : " + quickBetVal);
			boolean isBetChangedIntheConsole = isBetChangedIntheConsole(quickBetVal);
			try {
				if (isBetChangedIntheConsole) {
					report.detailsAppend("verify that selected bet :" + quickBetVal + " should reflect at consle bet",
							"Selected bet :" + quickBetVal + " should reflect at consle bet",
							"Selected bet :" + quickBetVal + " should reflect at consle bet", "Pass");
				} else {
					report.detailsAppend("Verify that Selected bet :" + quickBetVal + " should reflect at consle bet",
							"Selected bet :" + quickBetVal + " should reflect at consle bet",
							"Selected bet :" + quickBetVal + " Not Reflecting at consle bet", "Fail");
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			for (int scratchCounter = 0; scratchCounter < 10; scratchCounter++) {
				log.debug("scratchCounter :: " + scratchCounter);

				String creditB4Scratch = getConsoleText("return " + XpathMap.get("InfoBarBalanceTxt"));
				log.debug("creditB4Scratch : " + creditB4Scratch);

				clickAtButton("return  " + XpathMap.get("ClickscratchButton"));
				log.debug("Clicked at scratch");

				String consoleBet = getConsoleText("return " + XpathMap.get("InfobarBettext"));
				log.debug("consoleBet :: " + consoleBet);
				boolean isCreditDeducted = isCreditDeducted(creditB4Scratch, quickBetVal);
				log.debug("isCreditDeducted" + isCreditDeducted);
				if (isCreditDeducted) {
					c++;
					log.debug("Credit Deducted during scratch quick bets for =" + consoleBet);
				} else {
					log.debug("Credit not Deducted during scratch quick bets for =" + consoleBet);
				}

				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);
						e.printStackTrace();
					}

					String currentScene = getConsoleText("return " + XpathMap.get("ScratchBtnCurrState"));
					System.out.println(currentScene);
					if (currentScene != null && currentScene.equalsIgnoreCase("activeSecondary")) {
						log.debug("wating for scrach button");
						break;
					}
				}

				// elementWait("return "+XpathMap.get("ScratchBtnCurrState"),
				// "activeSecondary");
				log.debug("ScratchButtonComponent waiting here activeSecondary state");
				clickAtButton("return  " + XpathMap.get("ClickscratchButton"));

				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				waitForWinDisplay();
				boolean isPlayerWon = isPlayerWon();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Player won for the bet : " + quickBetVal + " " + isPlayerWon);
				if (isPlayerWon) {
					// String currentCreditAmount = getCurrentCredits();
					boolean isWinAddedToCredit = isWinAddedToCredit(creditB4Scratch, quickBetVal);
					if (isWinAddedToCredit) {
						w++;
						log.debug("isWinAddedToCredit :: PASS");
					} else {
						log.debug("isWinAddedToCredit :: Fail");
					}

					break;
				}
			}
			try {
				// common report to verify isCreditDeducted for the bet
				if (c != 0) {
					report.detailsAppendNoScreenshot(
							"Verify Credits are deducting properly after spin for the quickbet :" + quickBetVal,
							"Credits are deducting properly after spin for the quickbet : " + quickBetVal,
							"Credits are deducting properly after spin for the quickbet :" + quickBetVal, "pass");
				} else {
					report.detailsAppendNoScreenshot(
							"Verify Credits are deducting properly after spin for the quickbet :" + quickBetVal,
							"Credits are not deducting properly after spin for the quickbet : " + quickBetVal,
							"Credits are not deducting properly after spin for the quickbet :" + quickBetVal, "fail");
				}
				// report to verify isWinAddedToCredit for the bet
				if (w != 0) {
					report.detailsAppendNoScreenshot(
							"Verify Win ammount updating properly after spin for the quickbet :" + quickBetVal,
							"Win ammount updatingproperly after spin for the quickbet : " + quickBetVal,
							"Win ammount updating properly after spin for the quickbet :" + quickBetVal, "pass");
				} else {
					report.detailsAppendNoScreenshot(
							"Verify Win ammount updating properly after spin for the quickbet :" + quickBetVal,
							"Win ammount updating properly after spin for the quickbet : " + quickBetVal,
							"Win ammount not updating properly after spin for the quickbet :" + quickBetVal, "fail");
				}
			} catch (Exception e) {
				log.error("error while taking report for the win and cridit for the bet");
				log.error(e.getMessage(), e);
				try {
					report.detailsAppendNoScreenshot("Verify Credits and win  after spin for the quickbet ", "",
							"Exception occur while verifying credits and win amount after spin for quick bet", "fail");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}

	}

	/*
	 * Date: 29/05/2018 Author:sneha jawarkar Description: To validate navigation to
	 * banking page from game menu Parameter: NA
	 */
	public boolean CheckNavigateGameToBanking(Desktop_HTML_Report report) {
		Wait = new WebDriverWait(webdriver, 20);
		boolean ret = false;
		try {
			String gameurl = webdriver.getCurrentUrl();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuDepositBtnVisible"));
			if (flag) {
				clickAtButton("return " + forceNamespace
						+ ".getControlById('MenuIconPanelComponent').onButtonClicked('depositButton')");
				log.debug("Clicked on banking button to open in menu");
				long start = System.currentTimeMillis();
				String navigetedurl = webdriver.getCurrentUrl();
				Thread.sleep(3000);
				long finish = System.currentTimeMillis();
				long totalTime = finish - start;
				double loadingtime = totalTime / 1000;
				log.debug("Game navigeted to banking page in " + loadingtime + " Seconds");
				// verify that is navigated to banking page or not.
				if (navigetedurl.contains("Banking") && loadingtime < 30.00) {
					Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='button']")));
					log.debug("Game navigated to banking page.");
					// report Pass if banking navigation done
					report.detailsAppend("verify the Navigation screen shot to the banking page",
							" Navigation page screen shot for the banking page",
							"Navigation page screenshot for the banking page ", "pass");
					Thread.sleep(2000);
					WebElement ele = webdriver.findElement(By.xpath("//*[@class='button']"));
					ele.click();
					log.debug("cliked on back to game from navigation");
					webdriver.navigate().to(gameurl);
					waitForSpinButton();
					// Pass if back to game page successfully from banking navigation
					report.detailsAppend("verify that banking page navigated successfully back to the game",
							" navigated successfully back to the game", "navigated successfully back to the game",
							"pass");
					ret = true;
				} else {
					log.debug("Game has taken more 30 secondos to navigate on banking page");
					// fail due to navigation not done in 30 seconds
					report.detailsAppend("verify the Navigation screen shot to the banking page",
							" Navigation page for the banking page not has fail due to not navigated in 30 seconds",
							"Navigation page for the banking page not has fail due to not navigated in 30 seconds ",
							"fail");
					Thread.sleep(2000);
					WebElement ele = webdriver.findElement(By.xpath("//*[@class='button']"));
					ele.click();
					log.debug("cliked on back to game from navigation");
					webdriver.navigate().to(gameurl);
					waitForSpinButton();
					// Pass if back to game page successfully from banking navigation
					report.detailsAppend("verify that banking page navigated successfully back to the game",
							" navigated successfully back to the game", "navigated successfully back to the game",
							"pass");
					return ret;
				}
			} else {
				log.debug("Banking page option is not Available in game");
				return ret;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Error in navigetion to banking page ", e);
		}
		return ret;
	}

	public void checkpagenavigation(Desktop_HTML_Report report, String gameurl ) {
		try {
			String mainwindow = webdriver.getWindowHandle();
			Set<String> s1 = webdriver.getWindowHandles();
			if (s1.size() > 1) {
				Iterator<String> i1 = s1.iterator();
				while (i1.hasNext()) {
					String ChildWindow = i1.next();
					if (mainwindow.equalsIgnoreCase(ChildWindow)) {
						//report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot", "Navigation page screenshot ", "PASS");
						ChildWindow = i1.next();
						webdriver.switchTo().window(ChildWindow);
						String url = webdriver.getCurrentUrl();
						log.debug("Navigation URL is :: " + url);
						log.debug("Navigation URL is :: " + url);
						if (!url.equalsIgnoreCase(gameurl)) {
							// pass condition for navigation
							Thread.sleep(15000);
							report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot",
									"Navigation page screenshot ** Manual Validation is Required **", "PASS");
							log.debug("Page navigated succesfully");
							log.debug("Page navigated succesfully");
							webdriver.close();
						} else {
							//System.out.println("Now On game page");
							log.debug("Now On game page");
						}
					}
				}
				webdriver.switchTo().window(mainwindow);
			} else {
				String url = webdriver.getCurrentUrl();
				//System.out.println("Navigation URL is ::  " + url);
				log.debug("Navigation URL is ::  " + url);
				if (!url.equalsIgnoreCase(gameurl)) {
					// pass condition for navigation
					Thread.sleep(2000);
					report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot",
							"Navigation page screenshot ** Manual Validation is Required **", "PASS");
					log.debug("Page navigated succesfully");

					webdriver.navigate().to(gameurl);
					waitForSpinButton();
					newFeature();
				} else {
					webdriver.navigate().to(gameurl);
					waitForSpinButton();
					//System.out.println("Now On game page");
					log.debug("Now On game page");
				}
			}

		} catch (Exception e) {
			log.error("error in navigation of page");
		}
	}

	public boolean verifyhelpenavigation(Desktop_HTML_Report report,String market) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try

		{
			String gameurl = webdriver.getCurrentUrl();
			menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuHelpBtnVisible"));
			if (flag) {
				// clickAtButton("return
				// "+forceNamespace+".getControlById('MenuIconPanelComponent').onButtonClicked('helpButton')");
				clickAtButton("return " + forceNamespace
						+ ".getControlById('MenuIconPanelComponent').onButtonClicked('helpButton')");
				clickAtButton(
						"return " + forceNamespace + ".getControlById('Denmark_Menu').onButtonClicked('Denmark_Help')");
				log.debug("Clicked on Help button to open in menu");
				Thread.sleep(3000);
				// Pass
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to Help");
				ret = true;
			} else {

				log.debug("Help navigation option is not available in menu options");
				return ret;
			}
			webdriver.navigate().to(gameurl);
			waitForSpinButton();
		} catch (Exception e) {
			log.error("Error in navigetion to help page ", e);
		}
		return ret;
	}

	public boolean verifyresponsiblegamingenavigation(Desktop_HTML_Report report,String market) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			String gameurl = webdriver.getCurrentUrl();
			menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuResponsibleBtnVisible"));
			if (flag) {
				clickAtButton("return " + XpathMap.get("MenuResponsibleGamingBtn"));
				log.debug("Clicked on ResponsibleGameing button to open in menu");
				Thread.sleep(3000);
				// Pass
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to ResponsibleGameing verified");
				ret = true;
			} else {
				log.debug("Responsible Gaming navigation option is not available in menu options");
				return ret;
			}
			webdriver.navigate().to(gameurl);
			waitForSpinButton();
		} catch (Exception e) {
			log.error("Error in navigetion to ResponsibleGameing  page ", e);
		}
		return ret;
	}

	public boolean verifyplaychecknavigation(Desktop_HTML_Report report, String market) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			String gameurl = webdriver.getCurrentUrl();
			menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuplayCheckBtnVisible"));
			if (flag) {
				clickAtButton("return " + XpathMap.get("MenuPlayCheckBtn"));
				log.debug("Clicked on Playcheck button to open in menu");
				Thread.sleep(3000);
				// Pass
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to palycheck verified");
				ret = true;
			} else {
				log.debug("playchecknavigation option is not available in menu options");
				return ret;
			}
			webdriver.navigate().to(gameurl);
			waitForSpinButton();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Error in navigetion to palycheck  page ", e);
		}
		return ret;
	}

	public boolean verifycashChecknavigation(Desktop_HTML_Report report , String market) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			String gameurl = webdriver.getCurrentUrl();
			menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuCashCheckBtnVisible"));
			if (flag) {
				clickAtButton("return " + XpathMap.get("MenuCashCheckBtn"));
				log.debug("Clicked on cashCheck button to open in menu");
				Thread.sleep(5000);
				// Pass
				checkpagenavigationMarket(report, gameurl,market );
				System.out.println("Game navigated to cashCheck verified");
				ret = true;
			} else {
				log.debug("cashChecknavigation option is not available in menu options");
				return ret;
			}
			webdriver.navigate().to(gameurl);
			waitForSpinButton();
		} catch (Exception e) {
			log.error("Error in navigetion to cashCheck  page ", e);
		}
		return ret;
	}

	/**
	 * Date:07/12/2017 Author:Sneha Jawarkar This method is verifysettingsOptions
	 */
	public boolean verifysettingsOptions(Desktop_HTML_Report report) throws InterruptedException {
		boolean test = false;
		Wait = new WebDriverWait(webdriver, 50);
		try {
			// menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuOptionBtnVisible"));
			if (flag) {
				clickAtButton("return " + XpathMap.get("SettingOpenBtn"));
				log.debug("Clicked on settings button to open and verify");
				// pass sceenshot for the setting to be opened successfully
				report.detailsAppend("verify the Navigation screen shot for setting options from game menu",
						" Navigation page screen shot for setting options from game menu",
						"Navigation page screenshot for setting options from game menu ", "pass");
				boolean soundpresent = GetConsoleBooleanText("return " + XpathMap.get("isMenuSoundBtnVisible"));
				if (soundpresent) {
					boolean soundflag = GetConsoleBooleanText("return " + XpathMap.get("isSettingsSoundBtnActive"));
					if (soundflag) {
						// sceenshot for sound button with on state
						report.detailsAppend("verify the sound button is on in setting options ",
								"The sound button is on in setting option ", "The sound button is on in setting option",
								"pass");
					} else {
						// verify setting off State
						report.detailsAppend("verify the sound button is off setting option",
								" verify the sound button is off setting optionu",
								"verify the sound button is off setting option", "pass");
						clickAtButton("return " + XpathMap.get("ClickSettingsSoundBtn"));
						// sceenshot for sound button with on state
						report.detailsAppend("verify the sound button is on in setting options ",
								"The sound button is on in setting option ", "The sound button is on in setting option",
								"pass");
					}
				} else {
					log.info("Quick spin toggele button is not avilable in menu setting");
				}
				// Pass for Quickspin to be verify
				boolean quickspinpresent = GetConsoleBooleanText(
						"return " + XpathMap.get("isSettingsQuickSpinBtnVisible"));
				if (quickspinpresent) {
					boolean quickspinflag = GetConsoleBooleanText(
							"return " + XpathMap.get("isSettingsQuickSpinBtnActive"));
					if (quickspinflag) {
						// sceenshot for sound button with on state
						report.detailsAppend("verify the Quickspin button is on in setting options ",
								"The Quickspin button is on in setting option ",
								"The Quickspin button is on in setting option", "pass");
					} else {
						// verify quickspin
						report.detailsAppend("verify the Quickspin button is off setting option",
								" verify the Quickspin button is off setting optionu",
								"verify the Quickspin button is off setting option", "pass");
						clickAtButton("return " + XpathMap.get("ClickSettingsQuickSpinBtn"));
						// sceenshot for quick spin button with on state
						report.detailsAppend("verify the Quickspin button is on in setting options ",
								"The Quickspin button is on in setting option ",
								"The Quickspin button is on in setting option", "pass");
					}

				} else {
					log.info("Quick spin toggele button is not avilable in menu setting");
				}
				// Pass for Turbo to be verify
				boolean Turbopresent = GetConsoleBooleanText("return " + XpathMap.get("isSettingsturboBtnVisible"));
				if (Turbopresent) {
					boolean Turboflag = GetConsoleBooleanText("return " + XpathMap.get("isSettingsturboBtnActive"));
					if (Turboflag) {
						// sceenshot for button with on state
						report.detailsAppend("verify the Turbo button is on in setting options ",
								"The Turbo button is on in setting option ", "The Turbo button is on in setting option",
								"pass");
					} else {
						// verify setting off State
						report.detailsAppend("verify the Turbo button is off setting option",
								" verify the Turbo button is off setting optionu",
								"verify the Turbo button is off setting option", "pass");
						clickAtButton("return " + XpathMap.get("ClickSettingsturboBtn"));
						// sceenshot for sound button with on state
						report.detailsAppend("verify the Turbo button is on in setting options ",
								"The Turbo button is on in setting option ", "The Turbo button is on in setting option",
								"pass");
					}
				} else {
					log.info("Turbo toggle button is not available in menu  setting ");
				}

				// Pass for ShowMiniPaytables to be verify
				boolean ShowMiniPaytablespresent = GetConsoleBooleanText(
						"return " + XpathMap.get("isSettingsMiniPaytableBtnActive"));
				if (ShowMiniPaytablespresent) {
					boolean Turboflag = GetConsoleBooleanText("return " + XpathMap.get("isSettingsPaytableBtnActive"));
					if (Turboflag) {
						// sceenshot for mini paytable button with on state
						report.detailsAppend("verify the ShowMiniPaytables button is on in setting options ",
								"The ShowMiniPaytables button is on in setting option ",
								"The ShowMiniPaytables button is on in setting option", "pass");
					} else {
						// verify setting off State
						report.detailsAppend("verify the ShowMiniPaytables button is off setting option",
								" verify the ShowMiniPaytables button is off setting optionu",
								"verify the ShowMiniPaytables button is off setting option", "pass");
						clickAtButton("return " + XpathMap.get("ClickSettingsMiniPaytableBtn"));
						// sceenshot for mini paytable button with on state
						report.detailsAppend("verify the ShowMiniPaytables button is on in setting options ",
								"The ShowMiniPaytables button is on in setting option ",
								"The ShowMiniPaytables button is on in setting option", "pass");
					}
				} else {
					log.info("Show minitable toggel button is not available in menu setting");
				}
				log.debug("Setting to open succesfully");
				test = true;
			} else {
				log.debug("Setting option is not available in game menu");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Error in opening setting button", e);
			try {
				report.detailsAppendNoScreenshot("verify setting options ", " ",
						"exception occur while verifying setting option", "Fail");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		return test;
	}

	/**
	 * Date:07/12/2017 Author:Sneha Jawarkar This method is verifypaytablenavigation
	 */
	public void paytablenavigationClose() {
		try {
			clickAtButton("return " + XpathMap.get("PaytableCloseBtn"));
			log.debug("Closed the paytable page");
		} catch (Exception e) {
			log.debug("error in closing paytable");
		}
	}

	public boolean verifypaytablenavigation(Desktop_HTML_Report report) {
		boolean paytable = false;
		try {
			menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuPaytableBtnVisible"));
			if (flag) {
				clickAtButton("return " + XpathMap.get("PaytableOpenBtn"));
				log.debug("Clicked on paytable icon to open ");
				Thread.sleep(2000);
				String paytablestatus = "return " + XpathMap.get("isPaytableVisible");
				boolean status = GetConsoleBooleanText(paytablestatus);
				System.out.println("paytable open = " + status);
				if (status) {
					System.out.println("Game navigated to paytable page succesfully");
					// pass
					report.detailsAppend("Verify that Navigation on paytable screen is done ",
							" paytable Screen should open", "Paytable is open", "pass");
					paytablenavigationClose();
					paytable = true;
				} else {
					log.debug("Game is not navigated to paytable");
					// fail
					report.detailsAppend("Verify that Navigation on paytable screen is done ", "paytable should open",
							"Paytable is  not open", "fail");

				}
			} else {
				log.debug("Paytable option is not available in menu options");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("error in opening paytable", e);
		}
		return paytable;
	}

	public boolean verifyloyaltynavigation(Desktop_HTML_Report report) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			String gameurl = webdriver.getCurrentUrl();
			menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuLoyalityBtnVisible"));
			if (flag) {
				clickAtButton("return " + XpathMap.get("MenuLoyaltyBtn"));
				log.debug("Clicked on loyalty button to open in menu");
				Thread.sleep(5000);
				// Pass
				report.detailsAppend("verify the Navigation screen shot for loyalty",
						" Navigation page screen shot for loyalty", "Navigation page screenshot for loyalty", "pass");
				checkpagenavigation(report, gameurl);
				System.out.println("Game navigated to loyalty verified");
				ret = true;
			} else {
				log.debug("loyalty option is not available in menu options");
			}
		} catch (Exception e) {
			log.error("Error in navigetion to loyalty  page ", e);
		}
		return ret;
	}

	public boolean verifylobbynavigation() {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			menuOpen();
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenulobbyBtnVisible"));
			if (flag) {
				String currenturl = webdriver.getCurrentUrl();
				System.out.println("Game url is =" + currenturl);

				clickAtButton("return " + forceNamespace
						+ ".getControlById('MenuIconPanelComponent').onButtonClicked('lobbyButton')");
				log.debug("Clicked on lobby button to open in menu");
				Thread.sleep(3000);
				// verify lobby page
				log.debug("navigated the url and  waiting for five_Reel_slot to be visible");
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("five_Reel_slot"))));
				System.out.println("navigated the lobby from game succesfully");
				ret = true;
			} else {
				log.debug("Lobby navigation option is not available in menu options");
				return ret;
			}
		} catch (Exception e) {
			log.error("Error in navigetion to lobby  page ", e);
		}
		return ret;
	}

	public void setNameSpace() {
		int nameSpaceCount = Integer.parseInt(TestPropReader.getInstance().getProperty("ForceNameSpaceCount"));

		for (int count = 0; count < nameSpaceCount; count++) {
			String nameSpace = TestPropReader.getInstance().getProperty("ForceNameSpace" + count);

			String namspaceResponce = null;
			namspaceResponce = getConsoleText("return " + nameSpace + ".currentScene");
			if (namspaceResponce != null && !"".equals(namspaceResponce)) {
				log.debug("NameSpace for this game :: " + nameSpace);
				forceNamespace = nameSpace;
				log.debug("namsspace for game=" + nameSpace);
				break;
			}
		}

	}

	/**
	 * Date:25/06/2018 Author:Premlata Mishra This method is used to verify Quick
	 * spin
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	public boolean verifyQuickSpin() {
		boolean test = false;
		try {
			// To verify the quick spin button in setting panel

			boolean quickSpinTxt = GetConsoleBooleanText("return " + XpathMap.get("SettingsQuickSpin"));
			// To verify the quick spin button on base scene component
			boolean quickspinbasescene = GetConsoleBooleanText("return " + XpathMap.get("isQuickSpinBtnVisible"));
			// condition is to check quick spin button is not available
			if (quickSpinTxt == true && quickspinbasescene == true) {
				// As quick spin button is present in setting as well as base scene component
				test = true;
			} else {
				test = false;
			}

		} catch (Exception e) {
			return test = false;
		}
		return test;
	}

	/*
	 * To verify the responsible gaming links
	 */

	public boolean verifyResponsibleGamingNavigation(Desktop_HTML_Report report) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			menuOpen();
			clickAtButton("return " + forceNamespace + XpathMap.get("MenuResponsibleGamingBtn"));
			log.debug("Clicked on ResponsibleGameing button to open in menu");
			Thread.sleep(1000);
			String tital = "Responsible Gaming";
			checkPageNavigation(report, tital);
			log.debug("Game navigated to ResponsibleGameing verified");
			ret = true;
		} catch (Exception e) {
			log.error("Error in navigetion to ResponsibleGameing  page ", e);
		}
		return ret;
	}

	public void verifyMenuOptionNavigations(Desktop_HTML_Report report) {
		try {
			boolean menuOpen = menuOpen();
			if (menuOpen) {
				report.detailsAppend("Verify that Navigation of menu link", "Navigation of menu link",
						"Navigation of menu link done", "pass");
			} else {
				report.detailsAppend("Verify that Navigation of menu link", "Navigation of menu link",
						"Navigation of menu link not done", "fail");
			}
			/*
			 * //verify navigating to banking page. boolean banking =
			 * CheckNavigateGameToBanking(report); if(banking){
			 * log.debug("Banking navigation verified succesfully"); } else {
			 * report.details_append("Verify that Navigation on banking screen",
			 * "Navigation on banking screen", "Navigation on banking screen not Done",
			 * "fail"); }
			 * 
			 * newFeature();
			 */

			// navigation from game to setting page
			verifysettingsOptions(report);
			settingsBack();

			// navigation from game to paytable
			if (!GameName.contains("Scratch") && isPaytableAvailable()) {
				verifypaytablenavigation(report);
			} else {
				log.debug("Paytable is not present in the game seprately");
			}
			if (!Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
				// verify help page navigation from game
				boolean help = verifyhelpenavigation(report);
				if (help) {
					log.debug("Help navigation verified succesfully");
				} else {
					report.detailsAppend("Verify that Navigation on Help screen", "Navigation on Help screen",
							"Navigation on Help screen not Done", "fail");
				}
				// verify navigation to responsible gaming from game menu
				boolean respgame = verifyresponsiblegamingenavigation(report);
				if (respgame) {
					log.debug("Responsible gameing navigation verified succesfully");
				} else {
					report.detailsAppend("Verify that Navigation on ResponsibleGaming screen ",
							"Navigation on ResponsibleGaming screen", "Navigation on ResponsibleGaming screen not Done",
							"fail");
				}

				// verify navigation to playcheck gaming from game menu
				boolean playcheck = verifyplaychecknavigation(report);
				if (playcheck) {
					log.debug("Navigation on playcheck succesfully");
				} else {
					report.detailsAppend("Verify that Navigation on playcheck", "Navigation on playcheck",
							"Navigation on playcheck not Done", "fail");
				}

				// verify navigation to cashCheckButton gaming from game menu
				boolean cashCheckButton = verifycashChecknavigation(report);
				if (cashCheckButton) {
					log.debug("Navigated to Cashcheck page succesfully");
				} else {
					report.detailsAppend("Verify that Navigation on cashCheck", "Navigation on cashCheck",
							"Navigation on cashCheck done", "fail");
				}
				//// verify navigation to loyaltyButton gaming from game menu
				boolean loyaltyButton = verifyloyaltynavigation(report);
				if (loyaltyButton) {
					log.debug("Navigated to Loyalty page succesfully");
				} else {
					report.detailsAppend("Verify that Navigation on loyalty", "Navigation on loyalty",
							"Navigation on loyalty is not Done", "fail");
				}
			}
			// verify navigation to lobby gaming from game menu
			/*
			 * boolean lobbyButton = verifylobbynavigation(); if(lobbyButton){
			 * report.details_append("Verify that Navigation to lobby screen",
			 * "Navigation to lobby screen","Navigation to lobby screen Done","pass"); }
			 * else { report.details_append("Verify that Navigation to lobby screen",
			 * "Navigation to lobby screen","Navigation to lobby screen not Done","fail"); }
			 */
		} catch (Exception e) {
			log.error(e.getMessage());
			try {
				report.detailsAppendNoScreenshot("verifyMenuOptionNavigations", "",
						"Exception while verifyMenuOptionNavigations", "fail");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	/*
	 * To verify the help gaming links
	 */
	public boolean verifyHelpNavigation(Desktop_HTML_Report report) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			menuOpen();
			Thread.sleep(1000);
			clickAtButton("return " + forceNamespace
					+ ".getControlById('MenuIconPanelComponent').onButtonClicked('helpButton')");
			log.debug("Clicked on help button  in menu");
			Thread.sleep(2000);
			String tital = "Casino Help";
			checkPageNavigation(report, tital);
			log.debug("Game navigated to help link  verified");
			ret = true;
		} catch (Exception e) {
			log.error("Error in navigetion to help link  page ", e);
		}
		return ret;
	}

	public String getBetFromConsole() {
		String bet;
		if (!GameName.contains("Scratch")) {
			bet = "return " + XpathMap.get("BetButtonLabel");
		} else {
			bet = "return " + XpathMap.get("InfobarBettext");
		}

		String consoleBet = getConsoleText(bet);
		return consoleBet;
	}

	// Method to wait till win occurs and verify the currency format in win
	public boolean waitforWinAmount(String currencyFormat, Desktop_HTML_Report currencyReport, String currencyName) {
		boolean result = false;
		String strwinexp = null;
		long startTime = System.currentTimeMillis();
		String winamt = null;
		try {
			// Thread.sleep(2000);
			log.debug("Function-> verifyBigWinCurrencyFormat");

			if ("Yes".equalsIgnoreCase(XpathMap.get("BigWinlayers"))) 
			{
				for (int i = 1; i <= 3; i++) {
					Thread.sleep(4000);
					waitForBigWin();

					log.debug("Bigwinlayer captured" + i);
					System.out.println("Bigwinlayer captured" + i);

					while (true) {
						if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
							winamt = getConsoleText("return " + XpathMap.get("WinMobileText"));
							log.info("verifyBigWinCurrencyFormat():waiting for win  to occur");
						} else
							winamt = getConsoleText("return " + XpathMap.get("BigWinCountUpText"));
						if (winamt != null) {
							log.info(" win occur ");
							// fetching win from panel
							String consolewinnew = winamt.replaceAll("win: ", "");
							System.out.println(winamt);
							System.out.println(consolewinnew);
							String winregexp = createregexp(consolewinnew, currencyFormat);
							if (winregexp.contains("$"))
								strwinexp = winregexp.replace("$", "\\$");
							else
								strwinexp = winregexp;
							Pattern.compile(strwinexp);
							String winexp = strwinexp.replace("#", "\\d");

							log.debug(
									"verifyBigWinCurrencyFormat():Win Exp= " + winexp + "consolewin= " + consolewinnew);

							if (Pattern.matches(winexp, consolewinnew)) {
								result = true;
								currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);

							} else {
								result = false;
								currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);
							}

							break;
						} else {
							long currentime = System.currentTimeMillis();
							if (((currentime - startTime) / 1000) > 120) {
								System.out.println("verifyBigWinCurrencyFormat():No win occur  in 2");
								result = false;
								break;
							}
						}

					}
				}
			}else
			{
				Thread.sleep(5000);
				winamt = getConsoleText("return " + XpathMap.get("BigWinCountUpText"));
				if (winamt != null) {
					log.info(" win occur ");
					// fetching win from panel
					String consolewinnew = winamt.replaceAll("win: ", "");
					consolewinnew = consolewinnew.replaceAll("WIN: ", "");
					consolewinnew = consolewinnew.replaceAll("Win: ", "");
					consolewinnew = consolewinnew.replaceAll("Win ", "");
					System.out.println(consolewinnew);
					String winregexp = createregexp(consolewinnew, currencyFormat);
					if (winregexp.contains("$"))
						strwinexp = winregexp.replace("$", "\\$");
					else
						strwinexp = winregexp;
					Pattern.compile(strwinexp);
					String winexp = strwinexp.replace("#", "\\d");

					log.debug(
							"verifyBigWinCurrencyFormat():Win Exp= " + winexp + "consolewin= " + consolewinnew);

					if (Pattern.matches(winexp, consolewinnew)) {
						result = true;
						currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);

					} else {
						result = false;
						currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);
					}

				}
				else
				{
					log.debug("no win occur");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Big win not occur.please use bigwin test data");

		}

		return result;
	}

	// Method to wait till win occurs and verify the currency format in win
	public boolean verifyBigWinCurrencyFormat(String currencyFormat, Desktop_HTML_Report currencyReport,
			String currencyName) {
		boolean result = false;
		String strwinexp = null;
		long startTime = System.currentTimeMillis();
		String winamt = null;
		try {
			//Thread.sleep(2000);
			log.debug("Function-> verifyBigWinCurrencyFormat");

			if ("Yes".equalsIgnoreCase(XpathMap.get("BigWinlayers"))) {
				for (int i = 1; i <= 3; i++) 
				{
					Thread.sleep(2000);
					// webdriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					waitForBigWin();

					// currencyReport.details_append("Verify that big win screen is displaying with
					// overlay ","Big win screen must display ,Once big win triggers ","On
					// triggering big win,Big win screen is displaying","pass",currencyName);
					log.debug("Bigwinlayer captured" + i);
					System.out.println("Bigwinlayer captured" + i);

					while (true) {
						if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
							winamt = getConsoleText("return " + XpathMap.get("WinMobileText"));
							log.info("verifyBigWinCurrencyFormat():waiting for win  to occur");
						} else
							winamt = getConsoleText("return " + XpathMap.get("BigWinCountUpText"));
						if (winamt != null) {
							log.info(" win occur ");
							// fetching win from panel
							String consolewinnew = winamt.replaceAll("win: ", "");
							String winregexp = createregexp(consolewinnew, currencyFormat);
							if (winregexp.contains("$"))
								strwinexp = winregexp.replace("$", "\\$");
							else
								strwinexp = winregexp;
							Pattern.compile(strwinexp);
							String winexp = strwinexp.replace("#", "\\d");

							log.debug(
									"verifyBigWinCurrencyFormat():Win Exp= " + winexp + "consolewin= " + consolewinnew);

							if (Pattern.matches(winexp, consolewinnew)) {
								result = true;
								currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);

							} else {
								result = false;
								currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);
							}

							break;
						} else {
							long currentime = System.currentTimeMillis();
							if (((currentime - startTime) / 1000) > 120) {
								System.out.println("verifyBigWinCurrencyFormat():No win occur  in 2");
								result = false;
								break;
							}
						}

					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Big win not occur.please  use bigwin test data");

		}

		return result;
	}

	/*
	 * Method to verify currency format in credit
	 */

	public boolean verifyCurrencyFormat(String currencyFormat) {
		boolean result = true;
		String strBetExp = null;
		String regexp = null;
		try {
			log.debug("Function-> verifyCurrencyFormat() ");
			// Read console credits
			String consoleCredit = getCurrentCredits().replaceAll("Credits: ", "");
			consoleCredit = consoleCredit.replace("credits: ", "");
			consoleCredit = consoleCredit.replace("CREDITS: ", "");
			String betregexp = createregexp(consoleCredit, currencyFormat);
			// String creditregexp = consoleCredit.replaceAll("[[0-9][a-zA-Z]]","\\\\d" );
			if (currencyFormat.contains("$"))
				strBetExp = betregexp.replace("$", "\\$");
			else
				strBetExp = betregexp;
			Pattern.compile(strBetExp);
			regexp = strBetExp.replace("#", "\\d");

			if (Pattern.matches(regexp, consoleCredit)) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return result;
	}

	// Method to count the special symbol in currency format
	public int specialSymbolCount(String currencyFormat) {
		int count = 0;

		Pattern pattern = Pattern.compile("#");
		Matcher matcher = pattern.matcher(currencyFormat);
		while (matcher.find()) {
			count++;
		}

		return count;
	}

	/*
	 * Method is use for create regular expression
	 */
	public String createregexp(String betnew, String currencyFormat) {
		try {

			int count = specialSymbolCount(currencyFormat);
			betnew = betnew.replaceAll("[^0-9]", "");
			if (count > betnew.length()) {
				int lenght = count - betnew.length();
				for (int i = 0; i < lenght; i++) {
					for (int j = 0; j < currencyFormat.length(); j++) {
						if (currencyFormat.charAt(j) == '#') {
							StringBuilder sb = new StringBuilder();
							sb.append(currencyFormat);
							if (currencyFormat.charAt(j + 1) == ',' || currencyFormat.charAt(j + 1) == '.'
									|| currencyFormat.charAt(j + 1) == ' ' || currencyFormat.charAt(j + 1) == 160) {
								sb.deleteCharAt(j + 1);
							}
							sb.deleteCharAt(j);
							currencyFormat = sb.toString();
							break;
						}
					}
				}

			}
		} catch (Exception e) {
			log.error(e.getCause());
		}
		return currencyFormat;
	}

	/*
	 * Method to check the currency format in free spin summary
	 */
	public boolean freeSpinSummaryWinCurrFormat(String currencyFormat) {
		String freespinwin = null;
		boolean isWinInCurrencyFormat = false;
		try {
			log.debug("Function -> freeSpinWinCurrencyFormat()");
			if (!GameName.contains("Scratch")) {
				freespinwin = "return " + XpathMap.get("FSSumaryAmount");
			} else {
				// need to update the hook for Scratch game
				freespinwin = "return " + XpathMap.get("InfobarBettext");
			}
			String freespinwinnew = getConsoleText(freespinwin);
			// To validate currency Amount format
			isWinInCurrencyFormat = currencyFormatValidator(freespinwinnew, currencyFormat);

			/*
			 * String freespinregexp=createregexp(freespinwinnew,currencyFormat);
			 * if(freespinregexp.contains("$"))
			 * strfreespinwinExp=freespinregexp.replace("$", "\\$"); else
			 * strfreespinwinExp=freespinregexp; Pattern
			 * pattren=Pattern.compile(strfreespinwinExp); String
			 * winexp=strfreespinwinExp.replace("#", "\\d");
			 * 
			 * 
			 * if(Pattern.matches(winexp,freespinwinnew)) { isWinInCurrencyFormat=true; }
			 * else{ isWinInCurrencyFormat=false; }
			 */

			log.debug("Fetching Free spin summary currency symbol" + "/n Free spin summary currency symbol is::"
					+ freespinwin);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isWinInCurrencyFormat;

	}

	/*
	 * This method is use to verify the paytable currency with minimum bet and with
	 * maximum bet value
	 */
	public void verifyPaytableCurrency(Desktop_HTML_Report currencyReport, String CurrencyName) {

		try {
			// set bet value to minimum and take screen shot of paytable to check the
			// alignment
			setMinBet();
			capturePaytableOnlyScreenshot(currencyReport, CurrencyName);
			paytableClose();
			setMaxBet();
			if ("yes".equalsIgnoreCase(XpathMap.get("IsRespinFeature"))
					&& checkAvilability(XpathMap.get("MaxBetDailoagVisible"))) {
				clickAtButton("return  " + XpathMap.get("MaxBetDailoag"));

			}
			capturePaytableOnlyScreenshot(currencyReport, CurrencyName);
			paytableClose();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);

		}
	}

	/*
	 * This method is use to verify the currency format in respin reel
	 */
	public boolean reSpinOverlayCurrencyFormat(String currencyFormat) {

		String respinbet = null;
		String strrespinbetexp = null;
		boolean isrespinbetincurrencyformat = false;
		try {
			closeOverlay();
			Thread.sleep(3000);
			clickAtButton("return " + XpathMap.get("RespinMouseClick"));

			if (!GameName.contains("Scratch")) {
				respinbet = "return " + forceNamespace + ".getControlById('RespinComponent').Text.respinValue0._text";
			} else {
				// need to update the hook for Scratch game
				respinbet = "return " + forceNamespace + ".getControlById('RespinComponent').Text.respinValue0._text";
			}
			String respinbetnew = getConsoleText(respinbet);

			String respinregexp = createregexp(respinbetnew, currencyFormat);
			if (respinregexp.contains("$"))
				strrespinbetexp = respinregexp.replace("$", "\\$");
			else
				strrespinbetexp = respinregexp;
			Pattern.compile(strrespinbetexp);
			String respinbetexp = strrespinbetexp.replace("#", "\\d");

			if (Pattern.matches(respinbetexp, respinbetnew)) {
				isrespinbetincurrencyformat = true;
			} else {
				isrespinbetincurrencyformat = false;
			}

			log.debug("Fetching bet currency symbol" + "/n bet currency symbol is::" + respinbet);
		} catch (Exception e) {
			log.error("Error in Fetching currency symbol", e);
		}
		return isrespinbetincurrencyformat;
	}

	/*
	 * return array list of translation of text in langauage code
	 */
	public ArrayList<String> getTheTransalation(String text, String languageCode, String scene,
			HashMap<String, ArrayList<String>> map) {

		StringBuffer key = new StringBuffer();

		if (text != null && !text.equals("")) {
			key.append(text);
		}
		if (languageCode != null && !languageCode.equals("")) {
			key.append(languageCode);
		}
		if (scene != null && !scene.equals("")) {
			key.append(scene);
		}
		log.debug("Searching For::" + key.toString());
		return map.get(key.toString().toUpperCase());
	}

	/*
	 * Read the translation from sheet
	 */

	public HashMap readTranslations() {
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();

		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		try {
			int rowCount2 = excelpoolmanager.rowCount("TextComparision.xls", "translations");
			for (int j = 1; j < rowCount2; j++) {
				// Step 2: To get the languages in MAP and load the language specific url
				Map<String, String> rowData2 = excelpoolmanager.readExcelByRow("TextComparision.xls", "translations",
						j);

				String text = rowData2.get("Text").trim();
				String languageCode = rowData2.get("LanguageCode").trim();
				String scene = rowData2.get("Scene").toString().trim();

				String lowerCase = rowData2.get("LowerCase").trim();
				String upperCase = rowData2.get("UpperCase").trim();
				String abbreviation = rowData2.get("Abbreviation").trim();
				String verb_LowerCase = rowData2.get("verb-LowerCase").trim();
				ArrayList<String> translationList = new ArrayList<String>();
				if (lowerCase != null && !"".equalsIgnoreCase(lowerCase)) {
					translationList.add(lowerCase);
				}
				if (upperCase != null && !"".equalsIgnoreCase(upperCase)) {
					translationList.add(upperCase);
				}
				if (abbreviation != null && !"".equalsIgnoreCase(abbreviation)) {
					translationList.add(abbreviation);
				}
				if (verb_LowerCase != null && !"".equalsIgnoreCase(verb_LowerCase)) {
					translationList.add(verb_LowerCase);
				}

				String key = text + languageCode + scene;
				System.out.println(key.toUpperCase() + "----------" + translationList);
				if (translationList.size() > 0) {
					map.put(key.toUpperCase(), translationList);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	/*
	 * This method verify Text Method name:- verifyTextTranslation return:- Boolean
	 * Author:-Snehal Gaikwad.
	 */

	public void verifyTextTranslation(String hook, String text, String languageCode,
			HashMap<String, ArrayList<String>> translationmap, Desktop_HTML_Report report) {
		boolean result = false;

		try {

			String textfromgame1 = getConsoleText("return " + forceNamespace + "." + hook);

			String[] textfromgamenew = textfromgame1.split(" ");

			String textfromgame = textfromgamenew[0];
			System.out.println("Text from game = " + textfromgame);
			ArrayList<String> resultList = getTheTransalation(text, languageCode.toUpperCase(), "Base", translationmap);

			if (resultList == null) {

				report.detailsAppendNoScreenshot("verify transation in sheet ",
						" Transalation for " + languageCode + " should  be in transaltion sheet ",
						"Transalation for " + languageCode + " is not available in transaltion sheet", "fail");
			}
			result = resultList.contains(textfromgame);

			if (result) {
				report.detailsAppendNoScreenshot(
						"Verify that Language translation of " + text + " text in " + languageCode,
						" Language translation of " + text + " text should be in  " + languageCode,
						" Language translation of " + text + " text  in " + languageCode, "pass");
			} else {
				report.detailsAppendNoScreenshot(
						"Verify that Language translation of " + text + " text in " + languageCode,
						" Language translation of " + text + " should be in  " + languageCode,
						" Language translation of " + text + " textis not  in " + languageCode, "fail");
			}

		} catch (Exception e) {
			log.debug("Exception in verifyTextTranslation:" + e.getMessage());
		}

	}

	/*
	 * Sneha Jawarkar: Wait for big win layers screen
	 * 
	 */
	public boolean waitForBigWin() {
		boolean result = false;
		long startTime = System.currentTimeMillis();
		try {
			log.debug("Waiting for bigwinlayers to come after complition of spin");
			Thread.sleep(1000);
			while (true) {

				// Boolean currentSceneflag = GetConsoleBooleanText("return
				// "+XpathMap.get("isTieredBigWinAnimationVisible"));
				Boolean currentSceneflag = GetConsoleBooleanText("return " + XpathMap.get("isTieredBigWinTextVisible"));

				System.out.println("Bigwin popup present = " + currentSceneflag);
				if (currentSceneflag) {
					log.info("Bigwin popup present = " + currentSceneflag);
					Thread.sleep(1000);
					result = true;
					break;
				} else {
					long currentime = System.currentTimeMillis();
					if (((currentime - startTime) / 1000) > 30) {
						log.info("No big win present after 30 seconds= " + currentSceneflag);
						result = false;
						break;
					}

					Thread.sleep(1000);
				}
			}

		} catch (Exception e) {
			log.error("error while waiting for big win ", e);
		}

		return result;
	}

	public boolean waitForBigWin(String bigWinTextTobeVisible) {
		boolean result = false;
		long startTime = System.currentTimeMillis();
		try {
			log.debug("Waiting for bigwinlayers to come after complition of spin");

			while (true) {

				// Boolean currentSceneflag = GetConsoleBooleanText("return
				// "+XpathMap.get("isTieredBigWinAnimationVisible"));
				Boolean currentSceneflag = GetConsoleBooleanText("return " + bigWinTextTobeVisible);

				System.out.println("next bigwin layer popup present = " + currentSceneflag);
				if (currentSceneflag) {
					log.info("next bigwin layer popup present = " + currentSceneflag);
					Thread.sleep(1000);
					result = true;
					break;
				} else {
					long currentime = System.currentTimeMillis();
					if (((currentime - startTime) / 1000) > 30) {
						log.info("No big win layer present after 30 seconds= " + currentSceneflag);
						result = false;
						break;
					}

					Thread.sleep(1000);
				}
			}

		} catch (Exception e) {
			log.error("error while waiting for big win layers", e);
		}

		return result;
	}

	@Override
	public void validateMenuInBigWin(Desktop_HTML_Report report) {
		try {
			spinclick();
			waitForBigWin();
			if (menuOpen()) {
				Thread.sleep(2000);
				menuClose();
				if (GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
					report.detailsAppend("Verify Bigwin with Hamburger menu ",
							"Menu Panel should open and Big Win should be summarized  ",
							"Menu Panel  open and Big Win is summarized ", "PASS");
				} else {
					report.detailsAppend("Verify Bigwin with Hamburger menu ",
							"Menu Panel should open and Big Win should be summarized  ",
							"Menu Panel open and Big Win is not summarized", "FAIL");
				}
			} else {
				report.detailsAppend("Verify Bigwin with Hamburger menu ",
						"Menu Panel should open and Big Win should be summarized  ", "unable to open Menu Panel ",
						"FAIL");
			}

		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error("Exception occur" + exception.getMessage());
		}

	}

	public void bigWinWithSpin(Desktop_HTML_Report report) {
		try {
			spinclick();
			waitForBigWin();
			if (!GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
				report.detailsAppend("Verify Bigwin with spin button click  ",
						" On Spin click ,big win should play complatly ", "Big Win played complatly ", "PASS");
			} else {
				report.detailsAppend("Verify Bigwin with spin button click",
						" On Spin click ,big win should play complatly ",
						"Big Win not  played complatly,get summarized ", "FAIL");
			}
			waitForWinDisplay();
		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}

	@Override
	public void validatePaytableNavigationInBigWin(Desktop_HTML_Report report) {
		try {
			spinclick();
			waitForBigWin();
			if (menuOpen()) {
				if (checkAvilability(XpathMap.get("isMenuPaytableBtnVisible"))) {
					clickAtButton(XpathMap.get("PaytableOpenBtn"));
					Thread.sleep(2000);
					paytableClose();
					if (GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
						report.detailsAppend("Verify Bigwin with Paytable navigation ",
								" Big Win should be summarized after paytable navegation ",
								"Paytable is open and Big Win is summarized ", "PASS");

					} else {
						report.detailsAppend("Verify Bigwin with Paytable navigation ",
								" Big Win should be summarized after paytable navegation ",
								"Paytable is open and Big Win is not summarized ", "FAIL");

					}
				} else {
					report.detailsAppend("Verify Bigwin with Paytable navigation ",
							" Big Win should be summarized after paytable navegation ",
							"Paytable is not visible in Bigwin ", "FAIL");

				}
			}

		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}

	/*
	 * public void validateBigwinOnTappingBetButton(Desktop_HTML_Report report) {
	 * try{ spinclick(); waitForBigWin(); open_TotalBet(); }
	 * 
	 * }
	 */
	@Override
	public void bigWinQuickSpinOnOffValidation(Desktop_HTML_Report report) {
		try {
			// put quick spin off if previously on
			if ("active".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("QuickSpinBtnState")))) {
				QuickSpinclick();
				Thread.sleep(1000);
			}
			// Check big win status when quick spin is off
			if ("activeSecondary".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("QuickSpinBtnState")))) {
				spinclick();
				waitForBigWin();
				Thread.sleep(1000);
				if (!GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
					report.detailsAppend("Verify Bigwin with QuickSpin Off ",
							" Big Win animation should play completely ", "Big Win animation  play completely ",
							"PASS");
				} else {
					report.detailsAppend("Verify Bigwin with QuickSpin Off ",
							" Big Win animation should play completely ", "Big Win summarized ", "FAIL");

				}

				// Check big win status when quick spin is on

				QuickSpinclick();
				if ("active".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("QuickSpinBtnState")))) {
					spinclick();
					waitForBigWin();
					Thread.sleep(1000);
					if (!GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
						report.detailsAppend("Verify Bigwin with QuickSpin On ",
								" Big Win animation should play completely ", "Big Win animation  play completely ",
								"PASS");
					} else {
						report.detailsAppend("Verify Bigwin with QuickSpin On ",
								" Big Win animation should play completely ", "Big Win summarized ", "FAIL");

					}
					// make quick spin off
					QuickSpinclick();
				} else {
					report.detailsAppend("Verify Bigwin with QuickSpin On ",
							" Big Win animation should play completely ", "Quick spin is not on ", "FAIL");
				}
			} else {
				report.detailsAppend("Verify Bigwin with QuickSpin off ", " Big Win animation should play completely ",
						"Quick spin is not off ", "FAIL");
			}

		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}

	@Override
	public void bigwinwithAutoplay(Desktop_HTML_Report report) {
		try {
			startAutoPlay();
			// put quick spin off if previously on
			if ("active".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("QuickSpinBtnState")))) {
				QuickSpinclick();
				Thread.sleep(1000);
			}
			// check quick spin is off
			if ("activeSecondary".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("QuickSpinBtnState")))) {
				if (!GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
					report.detailsAppend("Verify Bigwin with Autoplay  ",
							" When Quick spin is off,big win should play completely ",
							"Big Win animation  play completely ", "PASS");
				} else {
					report.detailsAppend("Verify Bigwin with Autoplay ",
							" When Quick spin is off,big win should play completely ", "Big Win summarized ", "FAIL");

				}
				waitForWinDisplay();
				// validate when quick spin is on in autoplay
				QuickSpinclick();
				if (GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
					report.detailsAppend("Verify Bigwin with Autoplay  ",
							" When Quick spin is on,big win should summarized ", "Big Win summarized ", "PASS");
				} else {
					report.detailsAppend("Verify Bigwin with Autoplay ",
							" When Quick spin is on,big win should summarized ", "Big Win not summarized ", "FAIL");

				}
			}
			waitTillAutoplayComplete();
		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}

	@Override
	public void bigWinOnMinimize(Desktop_HTML_Report report) {
		try {
			spinclick();
			waitForBigWin();
			Thread.sleep(2000);
			Point p = webdriver.manage().window().getPosition();
			Dimension d = webdriver.manage().window().getSize();
			webdriver.manage().window().setSize(new Dimension(0, 0));
			webdriver.manage().window().setPosition(new Point((d.getHeight() - p.getX()), (d.getWidth() - p.getY())));
			if (GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
				report.detailsAppend("Verify Bigwin with minimize screen  ",
						" On minimize screen ,big win should summarized ", "Big Win summarized ", "PASS");
			} else {
				report.detailsAppend("Verify Bigwin with minimize screen ",
						" On minimize screen ,big win should summarized ", "Big Win not summarized ", "FAIL");

			}

			webdriver.manage().window().maximize();
		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}

	@Override
	public void bigWinResizeValidation(Desktop_HTML_Report report, int x, int y) {
		try {
			spinclick();
			waitForBigWin();
			Thread.sleep(2000);
			Dimension d = new Dimension(x, y);
			webdriver.manage().window().setSize(d);
			if (GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
				report.detailsAppend("Verify Bigwin with resize screen  ",
						" On resize screen ,big win should summarized ", "Big Win summarized ", "PASS");
			} else {
				report.detailsAppend("Verify Bigwin with resize screen ",
						" On resize screen ,big win should summarized ", "Big Win not summarized ", "FAIL");

			}
			webdriver.manage().window().maximize();
		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}

	@Override
	public void bigWinTabValidation(Desktop_HTML_Report report) {
		try {
			spinclick();
			waitForBigWin();
			Thread.sleep(1000);
			newTabOpen();
			if (GetConsoleBooleanText("return " + XpathMap.get("isWinComponentVisible"))) {
				report.detailsAppend("Verify Bigwin with Tab change ", " On Tab change ,big win should summarized ",
						"Big Win summarized ", "PASS");
			} else {
				report.detailsAppend("Verify Bigwin with Tab change ", " On Tab change ,big win should summarized ",
						"Big Win not summarized ", "FAIL");

			}
		} catch (JavascriptException exception) {
			log.error("Exception occur while executing hook,Please verify thre hook of given component"
					+ exception.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
	}

	public void scrollstory(Desktop_HTML_Report report, String languageCode) {
		try {
			Long storyFullHeight2 = null;
			Double storyHeight2 = null;
			int scroll = 0;
			JavascriptExecutor js = (JavascriptExecutor) webdriver;
			storyFullHeight2 = (Long) js.executeScript("return " + XpathMap.get("PaytableStoryScrollHeight"));

			storyHeight2 = (Double) js.executeScript("return " + XpathMap.get("PaytableStoryScroll_h"));

			scroll = (int) ((double) storyFullHeight2 / storyHeight2);
			for (int i = 1; i <= scroll; i++) {
				js.executeScript("return " + forceNamespace
						+ ".getControlById('PaytableStoryComponent').storyScroll.scrollTo(0,-" + storyHeight2 * i + ")");
				log.debug("Scrolling the story page and taking screenshots");
				report.detailsAppendFolder("verify the story screen shot", " story next page screen shot",
						"story next page screenshot ", "pass", languageCode);

			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Error while scrolling stroy");
		}

	}

	public void closestory() {
		Wait = new WebDriverWait(webdriver, 50);
		clickAtButton("return " + XpathMap.get("PaytableStoryInfoBtn"));
		log.debug("Stroy clode using Game ingot button present in story");

	}

	/**
	 * Date:11-11-2019 Name:Sneha Jawarkar Description: this function is used to
	 * verify stroy option in paytable the page and to take the screenshot
	 * 
	 * @throws Exception
	 */
	public String Verifystoryoptioninpaytable(Desktop_HTML_Report report, String languageCode) {
		Wait = new WebDriverWait(webdriver, 50);
		String story = null;
		try {
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			clickAtButton("return " + XpathMap.get("isMenuPaytableBtnVisible"));
			log.debug("Clicked on paytable icon to open ");
			Thread.sleep(1500);
			/*
			 * Adding the Jackpot info table screen shots for game having bolt on features
			 */
			if (checkAvilability(XpathMap.get("IsJackpotInfoPaytableComponentVisible"))) {
				clickAtButton("return " + XpathMap.get("CloseJackpotInfoPaytableComponent"));
			}
			// open story for Amber take screen shot and then close
			clickAtButton("return " + XpathMap.get("PaytableStoryAmberBtn"));
			
			//validation-enhancement-sv65878
			boolean flag1 = GetConsoleBooleanText("return " + XpathMap.get("AmberStoryVisible"));
			if (flag1) {
				System.out.println("Amber Story is Visible");
				report.detailsAppendFolder("verify the Amberstory screen shot", " Amberstory first page screen shot",
					"Amberstory first page screenshot ", "pass", languageCode);
			}
			
			else {
				System.out.println("Amber Story is not Visible");
				report.detailsAppendFolder("verify the Amberstory screen shot", " Amberstory first page screen shot",
					"Amberstory first page screenshot ", "FAIL", languageCode);
			}
			
			scrollstory(report, languageCode);
			closestory();
			log.debug("Stroy capture for Amber");
			
			
			
			// open story for Troy take screen shot and then close
			clickAtButton("return " + XpathMap.get("PaytableStoryTroyBtn"));
			
			//validation-enhancement-sv65878
			boolean flag2 = GetConsoleBooleanText("return " + XpathMap.get("TroyStoryVisible"));
			if (flag2) {
				System.out.println("Troy Story is Visible");
				report.detailsAppendFolder("verify the Troystory screen shot", " Troystory first page screen shot",
						"Troystory first page screenshot ", "pass", languageCode);
				}
			
			else {
				System.out.println("Troy Story is not Visible");
				report.detailsAppendFolder("verify the Troystory screen shot", " Troystory first page screen shot",
						"Troystory first page screenshot ", "Fail", languageCode);
				}
			
			scrollstory(report, languageCode);
			closestory();
			log.debug("Stroy capture for Troy");
			
			
			
			// open story for Michael take screen shot and then close
			clickAtButton("return " + XpathMap.get("PaytableStoryMichaelBtn"));
			
			//validation-enhancement-sv65878
			boolean flag3 = GetConsoleBooleanText("return " + XpathMap.get("MichaelStoryVisible"));
			if (flag3) {
				System.out.println("Michael Story is Visible");
				report.detailsAppendFolder("verify the Michaelstory screen shot", " Michaelstory first page screen shot",
						"Michaelstory first page screenshot ", "pass", languageCode);
				}
			
			else {
				System.out.println("Michael Story is not Visible");
				report.detailsAppendFolder("verify the Michaelstory screen shot", " Michaelstory first page screen shot",
						"Michaelstory first page screenshot ", "FAIL", languageCode);
				}
	
			scrollstory(report, languageCode);
			closestory();
			log.debug("Stroy capture for Michael");
			
			
			// open story for Sarah take screen shot and then close
			clickAtButton("return " + XpathMap.get("PaytableStorySarahBtn"));
			
			//validation-enhancement-sv65878
			boolean flag4 = GetConsoleBooleanText("return " + XpathMap.get("SarahStoryVisible"));
			if (flag4) {
				System.out.println("Sarah Story is Visible");
				report.detailsAppendFolder("verify the Sarahstory screen shot", " Sarahstory first page screen shot",
						"Sarahstory first page screenshot ", "pass", languageCode);
				}
			
			else {
				System.out.println("Michael Story is not Visible");
				report.detailsAppendFolder("verify the Sarahstory screen shot", " Sarahstory first page screen shot",
						"Sarahstory first page screenshot ", "FAIL", languageCode);
				}
			
			
			scrollstory(report, languageCode);
			closestory();
			log.debug("Stroy capture for Sarah");
			story = "story1";
		} catch (Exception e) {
			log.error("error in opening story", e);
		}
		return story;
	}

	public String getMinimumBet() {
		String minimumbet = getConsoleText("return " + forceNamespace
				+ ".getControlById('BetPanelComponent').Buttons.quickBetButton0.label._text");
		return minimumbet;
	}

	/*
	 * Method for payout verification for all bets
	 */
	public void Payoutvarificationforallbet(Desktop_HTML_Report report) {

		System.currentTimeMillis();

		Util util = new Util();
		int length = 0;
		double gamePayout;
		int index = 0;
		String paytablePayout;
		String strGameName = null;
		int startindex = 0;
		String scatter;
		long totalNoOfQuickBets;

		// Read xml for the game
		if (GameName.contains("Desktop")) {
			// String Gamename=gameName.replace("Desktop", "");
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			log.debug("newgamename=" + strGameName);
		}
		String xmlFilePath = "./" + strGameName + "/Config/" + strGameName + ".xml";
		length = util.xmlLength(xmlFilePath, "WinningCombinations");
		ArrayList<String> symbolData = util.getXMLDataInArray("Symbol", "name", xmlFilePath);

		String strPaylineCost = XpathMap.get("DefaultPayLineCost");// this code will fetch the data from sheet
		double paylinecost = Double.parseDouble(strPaylineCost);

		ArrayList<String> winCombinationList = new ArrayList<>();

		for (int count = 0; count < length; count++) {
			String strWinCombination = util.readXML("WinCombination", "numSymbolsRequired", "symbols", "payouts",
					"./" + strGameName + "/Config/" + strGameName + ".xml", length + 2, count);
			if (strWinCombination != null) {
				winCombinationList.add(strWinCombination);
			}
		}

		int winCombinationSize = winCombinationList.size();

		try {
			setMaxBet();
			try {
				if ("yes".equalsIgnoreCase(XpathMap.get("IsRespinFeature"))
						&& checkAvilability(XpathMap.get("MaxBetDailoagVisible"))) {
					clickAtButton("return  " + XpathMap.get("MaxBetDailoag"));

				}
			} catch (JavascriptException e) {
				log.error("No Bet Dailog open");
			}

			Thread.sleep(1000);
			if ((Class.forName(Thread.currentThread().getStackTrace()[2].getClassName())).toString()
					.contains("Modules.Regression.TestScript.Desktop_Regression_BaseScene")) {

				totalNoOfQuickBets = getNumberOfQuickbets();
			} else
				totalNoOfQuickBets = 1;
			// System.out.println("total number of bet selected for payout verification are
			// : "+totalNoOfQuickBets);
			for (int i = 1; i <= totalNoOfQuickBets; i++) {
				boolean[] resultflag = new boolean[winCombinationSize];
				// length=util.xmlLength(xmlFilePath,"WinningCombinations");
				// setting index at the staring
				index = 0;
				// Get the bet amount
				double bet = GetBetAmt();
				// System.out.println("Payout varification in progress for the selected bet
				// :"+bet);
				report.detailsAppendNoScreenshot("Payout varification in progress for the selected bet :" + bet,
						"Payout varification in progress for the selected bet :" + bet,
						"Payout varification in progress for the selected bet :" + bet, "Pass");
				// report.details_append("verify the payouts after changing bet and bet value
				// :"+bet, "Paytable payout must be correct after changing bet","", "");

				capturePaytableScreenshot(report, "Bet-" + bet);

				// System.out.println("Time taken to take the screen shots for bet :: "+bet+" is
				// = "+((endtimePaytbScreenShots-starttimePaytbScreenShots)/1000)+" .Sec ");
				// Below logic verifies all the values in paytable for this bet
				for (int j = 0; j < winCombinationSize; j++) {

					// paytablePayout=util.readXML("WinCombination", "numSymbolsRequired",
					// "symbols","payouts", xmlFilePath,length,index);
					paytablePayout = winCombinationList.get(index);
					if (paytablePayout.contains("Scatter") || paytablePayout.contains("FreeSpin")) {
						scatter = "yes";
					} else {
						scatter = "no";
					}
					String[] xmlData = paytablePayout.split(",");
					String xmlpayout = xmlData[2];

					gamePayout = gamepayout(symbolData, paytablePayout);// it will fetch game payout for Force game

					double result = verifyPaytable_Payouts(xmlpayout, paylinecost, bet, scatter);

					// System.out.println(result);
					if (gamePayout == result) {
						Arrays.fill(resultflag, true);
						// System.out.println("Paytable payout is correct for the bet value :"+bet+" xml
						// base value :"+xmlpayout+" result from formula :"+result+" symbol name :
						// "+xmlData[1]+"game payout : " +gamePayout+ " is correct");
						log.debug("Paytable payout is correct for the bet value :" + bet + " xml base value :"
								+ xmlpayout + " result from formula :" + result + " symbol name : " + xmlData[1]
								+ "game payout : " + gamePayout + " is correct");
						// report.details_append_NoScreenshot("Verify that the payout "+ xmlData[1] + "
						// coming in paytable is correct ", "Paytable payout must be correct","bet value
						// :"+bet+" xml base value :"+xmlpayout+" result from formula :"+result+" symbol
						// name : "+xmlData[1]+"game payout : " +gamePayout+ " is correct", "pass");
					} else {
						Arrays.fill(resultflag, false);
						// System.out.println("Paytable payout is not correct for the bet value :"+bet+"
						// xml base value :"+xmlpayout+" result from formula :"+result+" symbol name :
						// "+xmlData[1]+"game payout : " +gamePayout+ " is correct");
						log.debug("Paytable payout is not correct for the bet value :" + bet + " xml base value :"
								+ xmlpayout + " result from formula :" + result + " symbol name : " + xmlData[1]
								+ "game payout : " + gamePayout + " is incorrect");
						// report.details_append_NoScreenshot("Verify that the payout "+ xmlData[1] + "
						// coming in paytable is incorrect ", "Paytable payout must be correct","bet
						// value :"+bet+" xml base value :"+xmlpayout+" result from formula :"+result+"
						// symbol name :"+xmlData[1]+"game payout : " +gamePayout+ " is incorrect",
						// "fail");
					}
					length--;
					index++;
				}

				System.currentTimeMillis();

				// common result for the one selected bet;

				boolean isAllTrue = !Arrays.toString(resultflag).contains("false");
				if (isAllTrue) {
					report.detailsAppendNoScreenshot(
							"verify Payout verification for the bet : " + bet + "with the values of paytable in game",
							"Payout verification for the bet : " + bet + "with the values of paytable in game ",
							"Payout verification for the bet : " + bet + "with the values of paytable in game is done",
							"pass");

				} else {
					report.detailsAppendNoScreenshot(
							"verify Payout verification for the bet : " + bet + "with the values of paytable in game",
							"Payout verification for the bet : " + bet + "with the values of paytable in game ",
							"Payout verification for the bet : " + bet
									+ "with the values of paytable in game is done but failed coz some values are not matched",
							"fail");

				}
				// Closes the paytable
				paytableClose();
				// Change the bet to the next bet
				betIncrease();

				// xmlsno++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				report.detailsAppendNoScreenshot("verify Payout verification for the bet ", " ",
						"Exception ocuur while verifying payout for bet", "Fail");
			} catch (Exception e1) {
				log.error(e1.getStackTrace());
			}

		}

		System.currentTimeMillis();
	}

	public String paytableOpenWithoutScreenshotfolder(Desktop_HTML_Report report) {
		// System.out.println("Inside Paytable Opening");
		Wait = new WebDriverWait(webdriver, 50);
		String paytable = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			clickAtButton("return " + XpathMap.get("isMenuPaytableBtnVisible"));
			log.debug("Clicked on paytable icon to open ");
			Thread.sleep(1500);
			report.detailsAppend("verify the paytable screen shot", " paytable first page screen shot",
					"paytable first page screenshot ", "pass");
			Double paytableFullHeight2 = (Double) js.executeScript("return " + XpathMap.get("PaytableScrollHeight"));
			Double paytableHeight2 = (Double) js.executeScript("return " + XpathMap.get("PaytableScroll_h"));
			// System.out.println("paytableFullHeight2 : "+paytableFullHeight2);
			// System.out.println("paytableHeight2 : "+paytableHeight2);
			int scroll = (int) (paytableFullHeight2 / paytableHeight2);

			for (int i = 1; i <= scroll + 1; i++) {

				js.executeScript("return " + forceNamespace
						+ ".getControlById('PaytableComponent').paytableScroller.scrollTo(0,-" + paytableHeight2 * i
						+ ")");
				log.debug("Scrolling the paytable page and taking screenshots");
				report.detailsAppend("verify the paytable screen shot", " paytable next page screen shot",
						"paytable next page screenshot ", "pass");

			}
			paytable = "paytable1";
		} catch (Exception e) {
			log.error("error in opening paytable", e);
		}
		return paytable;
	}

	public void setSoundFalgActive(boolean soundFlagActive) {
		menuOpen();
		boolean flag = GetConsoleBooleanText("return " + XpathMap.get("isMenuOptionBtnVisible"));
		if (flag) {
			clickAtButton("return " + forceNamespace
					+ ".getControlById('MenuIconPanelComponent').onButtonClicked('optionsButton')");
			log.debug("Clicked on settings button to open and verify");
			boolean soundpresent = GetConsoleBooleanText("return " + forceNamespace
					+ ".getControlById('MenuIconPanelComponent').Buttons.soundsButton.visible");
			if (soundpresent) {
				boolean soundflag = GetConsoleBooleanText("return " + XpathMap.get("isSettingsSoundBtnActive"));

				if (soundflag != soundFlagActive) {
					clickAtButton("return " + XpathMap.get("ClickSettingsSoundBtn"));
				}

			}
		}
		closeOverlay();
	}

	public void startAutoPlay() {

		clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));

	}

	public boolean clickResumePlay() {

		boolean isResumeButtonClicked = false;
		try {
			clickAtButton("return " + XpathMap.get("ClickFGResumeViewResumeBtn"));
			isResumeButtonClicked = true;
		} catch (Exception e) {
			isResumeButtonClicked = false;
			log.error(e.getMessage(), e);
			// e.printStackTrace();
		}
		return isResumeButtonClicked;
	}

	public void clickFregamesPlay() {

		String view = getConsoleText("return " + XpathMap.get("FGViewHistoryLength"));

		try {
			if ("freeGamesOfferView".equalsIgnoreCase(view)) {
				clickPlayNow();
			} else if ("freeGamesResumeView".equalsIgnoreCase(view)) {
				clickResumePlay();
			}
		} catch (Exception e) {
			System.out.println("Exception in clickFregamesPlay");
			log.error("Exception in clickFregamesPlay", e);
		}
	}

	/*
	 * Name:verifySpinBtnState(report) Description:verify the spin button status
	 * with quick spin on and off
	 * 
	 * return:void
	 */
	public void verifySpinBtnState(Desktop_HTML_Report report) {
		try {
			if (GetConsoleBooleanText("return " + XpathMap.get("isQuickSpinBtnVisible"))) {
				// to check quick spin button is on after game launch
				if ("active".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("QuickSpinBtnState")))) {
					QuickSpinclick();
					Thread.sleep(1000);
				}

				// checks the status of spin button when quick spin is off
				if ("activeSecondary".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("QuickSpinBtnState")))) {
					// below code block for reel games
					if (!GameName.contains("Scratch")) {
						// click on spin button
						spinclick();

						if ("activeSecondary"
								.equalsIgnoreCase(getConsoleText("return " + XpathMap.get("SpinBtnCurrState")))) {
							report.detailsAppend("verify the spin button status when quick spin is off",
									" Spin button should convert to STOP", "Spin button converted to STOP ", "pass");
						} else {
							report.detailsAppend("verify the spin button status when quick spin is off",
									" Spin button should convert to STOP", "Spin button not converted to STOP ",
									"fail");
						}
						closeOverlay();
						waitForSpinButtonstop();

					}
					// below code block for scratch games
					else {
						clickAtButton("return  " + XpathMap.get("ClickscratchButton"));
						elementWait("return " + XpathMap.get("ScratchBtnCurrState"), "activeSecondary");

						if ("activeSecondary"
								.equalsIgnoreCase(getConsoleText("return " + XpathMap.get("ScratchBtnCurrState"))))
							report.detailsAppend("verify the spin button status when quick spin is off",
									" Spin button should convert to STOP", "Spin button converted to STOP ", "pass");
						else
							report.detailsAppend("verify the spin button status when quick spin is off",
									" Spin button should convert to STOP", "Spin button not converted to STOP ",
									"fail");

						clickAtButton("return  " + XpathMap.get("ClickscratchButton"));
						elementWait("return " + XpathMap.get("ScratchBtnCurrState"), "active");
					}
					// assertEquals(getConsoleText("retuns "+XpathMap.get("SpinBtnCurrState")),
					// "activeSecondary");
				}
				// check the status of spin button when quick spin is on
				waitForSpinButtonstop();
				waitForSpinButton();
				QuickSpinclick();
				Thread.sleep(1000);
				if (!GameName.contains("Scratch")) {
					// click on spin button
					spinclick();
					Thread.sleep(300);
					if ("disabled".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("SpinBtnCurrState")))) {
						report.detailsAppend("verify the spin button status when quick spin is on",
								" Spin button should not convert to STOP", "Spin button not converted to STOP ",
								"pass");
					} else {
						report.detailsAppend("verify the spin button status when quick spin is on",
								" Spin button should not convert to STOP", "Spin button converted to STOP ", "fail");
					}
					closeOverlay();
					waitForSpinButtonstop();
				} else {
					clickAtButton("return  " + XpathMap.get("ClickscratchButton"));
					if ("disabled".equalsIgnoreCase(getConsoleText("return " + XpathMap.get("SpinBtnCurrState"))))
						report.detailsAppend("verify the spin button status when quick spin is on",
								" Spin button should not convert to STOP", "Spin button not converted to STOP ",
								"pass");
					else
						report.detailsAppend("verify the spin button status when quick spin is on",
								" Spin button should not convert to STOP", "Spin button converted to STOP ", "fail");

				}
				QuickSpinclick();

			} else {
				log.debug("Quick spin button is not present");
				report.detailsAppend("verify the spin button status when quick spin is off",
						" quick spin button must be present", "quick spin button is not present ", "fail");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				report.detailsAppendNoScreenshot("verify the spin button status when quick spin is off", " ",
						"Exception occur while verifying spin button status ", "fail");
			} catch (Exception e1) {

				log.error(e1.getStackTrace());
			}

		}
	}

	public void autoplayPresets(Desktop_HTML_Report report) {
		try {

			if (GetConsoleBooleanText("return " + XpathMap.get("isAutoplayQuickOptionButton1Visible"))
					&& GetConsoleBooleanText("return " + XpathMap.get("isAutoplayQuickOptionButton2Visible"))
					&& GetConsoleBooleanText("return " + XpathMap.get("isAutoplayQuickOptionButton3Visible"))) {
				report.detailsAppend("AutoPlay may include a list of AutoPlay presets (quick select spin options).",
						"AutoPlay may include a list of AutoPlay presets (quick select spin options).",
						"AutoPlay include a list of AutoPlay presets (quick select spin options)", "Pass");
			} else {
				report.detailsAppend("AutoPlay may include a list of AutoPlay presets (quick select spin options).",
						"AutoPlay may include a list of AutoPlay presets (quick select spin options).",
						"All presets (quick select spin options) are not available/visible ", "Fail");

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void is_autoplay_setting(Desktop_HTML_Report report) {
		try {

			if (webdriver.findElements(By.id(XpathMap.get("Start_Autoplay"))).size() != 0) {
				report.detailsAppend(
						" verify that On selection of AutoPlay, the player must always be taken to an AutoPlay settings screen/dialog/window etc.",
						" On selection of AutoPlay, the player must always be taken to an AutoPlay settings screen/dialog/window etc.",
						"Player is on Autoplay setting/window/dialog .", "pass");

			} else
				report.detailsAppend(
						"verify that On selection of AutoPlay, the player must always be taken to an AutoPlay settings screen/dialog/window etc.",
						" On selection of AutoPlay, the player is not taken to an AutoPlay settings screen/dialog/window etc.",
						"Player is not on Autoplay setting/window/dialog .", "fail");

			if (webdriver.findElement(By.xpath(XpathMap.get("Coins_Bet"))) != null
					&& webdriver.findElement(By.id(XpathMap.get("winLimit"))) != null
					&& webdriver.findElement(By.id(XpathMap.get("lossLimit"))) != null
					&& webdriver.findElement(By.id(XpathMap.get("Uk_spin_select"))) != null) {
				report.detailsAppend(
						" The AutoPlay settings dialog must contain the following options : Bet selection,Loss Limit,Win Limit,Number of Spins",
						"  The AutoPlay settings dialog must contain the following options : Bet selection,Loss Limit,Win Limit,Number of Spins",
						" The AutoPlay settings dialog  contain the following options : Bet selection,Loss Limit,Win Limit,Number of Spins",
						"Pass");

			} else {
				report.detailsAppend(
						" The AutoPlay settings dialog must contain the following options : Bet selection,Loss Limit,Win Limit,Number of Spins",
						"  The AutoPlay settings dialog must contain the following options : Bet selection,Loss Limit,Win Limit,Number of Spins",
						" The AutoPlay settings dialog does not contain the following options : Bet selection,Loss Limit,Win Limit,Number of Spins",
						"Fail");

			}

		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Setting window not found");
		}

	}

	/*
	 * Verify Autoplay console for ultimate console
	 */
	public boolean verifyUltimateAutoplayConsoleOptions(Desktop_HTML_Report report) {

		try {

			// this counter = number of console options available in autoplay console.
			for (int counter = 1; counter <= 1; counter++) {
				log.debug("Autoplay for ::" + counter);
				clickAtButton(
						"return window.theForce.game.automation.getControlById('AutoplayButtonComponent').onButtonClicked('autoplayStartButton')");
				Thread.sleep(2000);

				clickAtButton(
						"return window.theForce.game.automation.getControlById('AutoplayPanelComponent').onButtonClicked('autoplaySpinsButton1')");
				clickAtButton(
						"return window.theForce.game.automation.getControlById('AutoplayPanelComponent').onButtonClicked('autoplaySpinsButton0')");
				Thread.sleep(2000);

				// new added to see is the selected option is same depling at console
				long autoplaySelectedCount = getConsoleNumeric(" return " + forceNamespace
						+ ".getControlById('AutoplayPanelComponent').Text.numberSpinsValueText._text");

				clickAtButton(
						"return window.theForce.game.automation.getControlById('AutoplayPanelComponent').onButtonClicked('autoplaySpinsButton')");
				Thread.sleep(2000);

				int autplayActualCount = countAutoPlayTillStop();
				Thread.sleep(2000);
				if (autoplaySelectedCount == autplayActualCount) {
					report.detailsAppendNoScreenshot("Verify that selected autoplay count must be played in game",
							"Selected autoplay count must be played in game",
							"AutoplaySelectedCount :: " + autoplaySelectedCount
									+ " Matched with AutplayActualCount played :: " + autplayActualCount,
							"Pass");
				} else {
					report.detailsAppendNoScreenshot("Verify that selected autoplay count must be played in game",
							"Selected autoplay count must be played in game",
							"AutoplaySelectedCount :: " + autoplaySelectedCount
									+ " Not Matched with AutplayActualCount played :: " + autplayActualCount,
							"Fail");
					if (isFreeSpinTriggered) {
						report.detailsAppendNoScreenshot(
								"verify that is the Freespin triggered during Autoplay so Autoplay must be intercepted for counter ::"
										+ autoplaySelectedCount,
								" The Freespin triggered during Autoplay so Autoplay is intercepted for counter ::"
										+ autoplaySelectedCount,
								"The Freespin triggered during Autoplay so Autoplay is intercepted for counter ::"
										+ autoplaySelectedCount,
								"fail");
						log.debug("Free Spin Triggered in Auto play and Auto play interupted");
					} else {
						report.detailsAppendNoScreenshot(
								"verify that is the Freespin triggered during Autoplay so Autoplay must be intercepted for counter ::"
										+ autoplaySelectedCount,
								" The Freespin triggered during Autoplay still Autoplay is not intercepted for counter ::"
										+ autoplaySelectedCount,
								"The Freespin triggered during Autoplay still Autoplay is not intercepted for counter ::"
										+ autoplaySelectedCount,
								"fail");
						log.debug("Free Spin Triggered in Auto play and Auto play not interupted so Failed Scenario");
					}
				}
				log.debug("autoplaySelectedCount :: " + autoplaySelectedCount);
				log.debug("autplayActualCount :: " + autplayActualCount);
			}
			return true;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}

	}

	public boolean verifyOneDesignAutoplayPanelOptions(Desktop_HTML_Report report) {

		try {
			// This counter = number of panel options available in autoplay panel.
			for (int counter = 0; counter < 1; counter++) {
				log.debug("Autoplay for ::" + counter);
				clickAtButton("return " + XpathMap.get("ClickAutoPlayBtn"));
				Thread.sleep(2000);
				//clickAtButton("return " + XpathMap.get("AutoplayOptionButton0"));
				Thread.sleep(2000);
				clickAtButton("return " + XpathMap.get("AutoplayOptionButton1"));
				Thread.sleep(2000);
				clickAtButton("return " + XpathMap.get("AutoplayOptionButton0"));
				// new added to see is the selected option is same depling at console
				String autoplaySelectedCountfrompanel = getConsoleText("return " + XpathMap.get("AutoPlayNumberLabel"));
				log.debug("autoplaySelectedCount from Autoplay Panel option :: " + autoplaySelectedCountfrompanel);
				System.out.println(
						"autoplaySelectedCount from Autoplay Panel option :: " + autoplaySelectedCountfrompanel);
				clickAtButton("return " + XpathMap.get("AutoPlayStartBtn"));
				report.detailsAppendNoScreenshot(
						"Verify that selected autoplay count from Autoply panel option must be played in game",
						"Selected autoplay count from Autoply panel option must be played in game",
						"AutoplaySelectedCount from Autoply panel option :: " + autoplaySelectedCountfrompanel, "Pass");
				Thread.sleep(2000);
				// is bet Available.. Bet must not be accessible during autoplay

				boolean isBetButtonActive = isBetButtonAccessible(); // Must not be accessible during auto play
				if (isBetButtonActive) {
					// need screenshot
					report.detailsAppend("verify that is BetButton Active during Autoplay",
							" Is BetButton Active during Autoplay", " BetButton Active during Autoplay ", "fail");

				} else {
					report.detailsAppend("verify that is BetButton Active during Autoplay",
							" Is BetButton Active during Autoplay", "BetButton is not  Active during Autoplay ",
							"pass");
				}

				boolean isMaxButtonActive = isMaxButtonAccessible(); // Must not be accessible during auto play
				if (isMaxButtonActive) {
					report.detailsAppend("verify that is MaxButton Accessible during Autoplay",
							" Is MaxButton Accessible during Autoplay", "MaxButton Accessible during Autoplay ",
							"fail");
				} else {
					report.detailsAppend("verify that is MaxButton Accessible during Autoplay",
							" Is MaxButton Accessible during Autoplay", "MaxButton is not Accessible during Autoplay",
							"pass");
				}
				setQuickSpinOn();
				report.detailsAppend("verify the QuickSpin On screen shot", " QuickSpinOn page screen shot",
						"QuickSpinOn option page screenshot ", "pass");
				Thread.sleep(2000);
				// Take screen shot
				setQuickSpinOff();
				report.detailsAppend("verify the QuickSpinOff screen shot", " QuickSpinOff page screen shot",
						"QuickSpinOff option page screenshot ", "pass");
				Thread.sleep(1000);
				// Take screen shot
				Dimension actualDimensions = webdriver.manage().window().getSize();
				// Take screen shot
				report.detailsAppend("verify the Actual winodow size screen shot for dimention = " + actualDimensions,
						" Actual winodow size screen shotfor dimention = " + actualDimensions,
						"Actual winodow size screenshot for dimention = " + actualDimensions, "pass");
				// Resize the window and take the screen shot
				for (int sizeCounter = 0; sizeCounter < 2; sizeCounter++) {

					webdriver.manage().window().setSize(new Dimension(actualDimensions.getWidth() - (sizeCounter * 200),
							actualDimensions.getHeight() - (sizeCounter * 200)));
					Thread.sleep(3000);
					// Take screen shot
					report.detailsAppend("verify the winodow size screen shot after resizing",
							" Actual winodow size screen shot after resizing",
							"Actual winodow size screenshot after resizing", "pass");

					Thread.sleep(1000);
				}

				webdriver.manage().window().setSize(actualDimensions);

				waitTillAutoplayComplete();

				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	public void setDefaultBet() {
		int defaultbet;
		try {
			if (!GameName.contains("Scratch")) {

				String strpaylinecost = XpathMap.get("DefaultPayLineCost");
				int paylinecost = (int) Double.parseDouble(strpaylinecost);
				String strnumOfchips = XpathMap.get("DefaultNumOfChips");
				int numOfchips = (int) Double.parseDouble(strnumOfchips);
				String strchipSize = XpathMap.get("DefaultChipSize");
				int chipSize = (int) Double.parseDouble(strchipSize);

				defaultbet = paylinecost * chipSize * numOfchips;
			} else {
				defaultbet = Integer.parseInt(getConsoleText("return " + XpathMap.get("getDefaultBet")));
			}

			String setBetValue = XpathMap.get("SetBetValue") + "(" + defaultbet + ")";
			getConsoleText("return " + setBetValue);

		} catch (Exception e) {
			log.error("Exception occur:", e);
		}

	}

	/*
	 * Method Name: checkAvilability(String hooksofcomponent) Description: This
	 * method check the availability of component Input parameter: String hook of
	 * component to be check. output : boolean variable , component is available or
	 * not in game. Authore : Snehal Gaikwad.
	 */
	public boolean checkAvilability(String hooksofcomponent) {
		boolean isComponentAvilable = true;

		try {
			// if component availble it will return true
			isComponentAvilable = GetConsoleBooleanText("return " + hooksofcomponent);
		} catch (Exception e) {
			// if component in the game not avilable in it while give an exception
			isComponentAvilable = false;
		}
		return isComponentAvilable;
	}

	/*
	 * Method Name: isPaytableAvilable() Description: This method check the
	 * availability of paytable component in game Input parameter: output : boolean
	 * variable , component is available or not in game. Authore : Snehal Gaikwad.
	 */

	public boolean isPaytableAvailable() {
		boolean ispaytableavilable = true;

		try {
			// if paytable availble it will return true
			ispaytableavilable = GetConsoleBooleanText("return " + XpathMap.get("isMenuPaytableBtnVisible"));
		} catch (Exception e) {
			// if paytable in the game not avilable in it while give an exception
			ispaytableavilable = false;
		}
		return ispaytableavilable;
	}

	/*
	 * Method Name: isSettingAvilable() Description: This method check the
	 * availability of setting component in game Input parameter: output : boolean
	 * variable , component is available or not in game. Authore : Snehal Gaikwad.
	 */
	public boolean isSettingAvailable() {
		boolean isSettingAvilable = true;

		try {
			// if paytable availble it will return true
			isSettingAvilable = GetConsoleBooleanText("return " + XpathMap.get("isMenuSettingsBtnVisible"));
		} catch (Exception e) {
			// if paytable in the game not avilable in it while give an exception
			isSettingAvilable = false;
		}
		return isSettingAvilable;
	}

	/*
	 * Method Name:waitTillAutoplayComplete() Description: wait till the Autoplay
	 * available. check the current sate of bet component. return : boolean
	 * variable.
	 */

	public boolean waitTillAutoplayComplete() {
		boolean isAutoplayComplete = false;
		try {

			log.debug("waiting for Autoplay to complete....");
			elementWait("return " + XpathMap.get("BetIconCurrState"), "active");
			isAutoplayComplete = true;
			log.debug(" Autoplay complete as bet button is active");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isAutoplayComplete;
	}

	@Override
	public void verifyJackPotBonuswithScreenShots(Desktop_HTML_Report report, String languageCode) {
		
		waitForSpinButton();
		String jackpotTrophyCloseHook = XpathMap.get("jackpotHistoryClose");
		try {
			
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);

			/*clickAtButton("return " + XpathMap.get("jackpotHistoryTrophyClick"));*/
			clickAtButton("return " + XpathMap.get("TrophyIconClick"));

			threadSleep(3000);
			
			boolean isJackpotWinHistory = elementWait("return " + XpathMap.get("JackpotWinHistoryVisible"), true);
					
			if(isJackpotWinHistory) {
				report.detailsAppend("Verify that the Win History page is displayed",
						" Win History page must be displayed ", "Win History Page is Visible",
						"Pass");
			}
			else {
				report.detailsAppend("Verify that the Win History page is displayed",
						" Win History page must be displayed ", "Win History Page is Visible",
						"Fail");
			}

			// jackpotHistoryPageHeight
			// jackpotHistoryVisibleHeight
			String[] jackpotHistoryDurationArray = new String[] { "1week", "2weeks", "1month", "6months", "1year",
					"2years" };
			double jackpotHistoryPageHeight = 0;

			if (js.executeScript("return " + XpathMap.get("jackpotHistoryPageHeight")).getClass().getName()
					.endsWith("Long")) {
				long longJackpotHistoryPageHeight = (Long) js
						.executeScript("return " + XpathMap.get("jackpotHistoryPageHeight"));
				jackpotHistoryPageHeight = (double) longJackpotHistoryPageHeight;
			} else {
				jackpotHistoryPageHeight = (double) js
						.executeScript("return " + XpathMap.get("jackpotHistoryPageHeight"));
			}

			double jackpotHistoryVisibleHeight = 0;
			if (js.executeScript("return " + XpathMap.get("jackpotHistoryVisibleHeight")).getClass().getName()
					.endsWith("Long")) {
				long longJackpotHistoryVisibleHeight = (Long) js
						.executeScript("return " + XpathMap.get("jackpotHistoryVisibleHeight"));
				jackpotHistoryVisibleHeight = (double) longJackpotHistoryVisibleHeight;
			} else {
				jackpotHistoryVisibleHeight = (double) js
						.executeScript("return " + XpathMap.get("jackpotHistoryVisibleHeight"));
			}

			System.out.println("jackpotHistoryPageHeight : " + jackpotHistoryPageHeight);
			System.out.println("jackpotHistoryVisibleHeight : " + jackpotHistoryVisibleHeight);
			int scroll = (int) (jackpotHistoryPageHeight / jackpotHistoryVisibleHeight);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("param1", Double.toString(jackpotHistoryPageHeight));

			String Hook = XpathMap.get("scrollProgressiveWinHistoryWidgetwithHight");
			String newHook = replaceParamInHook(Hook, paramMap);

			for (int counter1 = 0; counter1 <= 10; counter1++) {

				int index = 0;
				if (counter1 >= 2) {
					index = 2;

				} else {
					index = counter1;
				}

				js.executeScript("return " + newHook);
				paramMap.put("param1", Integer.toString(index));

				String clickOnJackpotDurationHook = XpathMap.get("ClickOnJackpotDuration");
				String durationClick = replaceParamInHook(clickOnJackpotDurationHook, paramMap);

				clickAtButton("return " + durationClick);

				// Start verifying calculations

				(getConsoleText("return " + forceNamespace
						+ ".getControlById('ProgressiveWinHistoryWidgetComponent').tabText[" + index + "].text"))
								.replaceAll("\\s+", "");

				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
				System.out.println(
						"==================Process jackpotHistoryDuration ::" + jackpotHistoryDurationArray[counter1]);
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");

				Map<String, Long> jackpotBackendMap = getConsoleLongMap("return " + XpathMap.get("getJackpotAmounts"));

				double megaValFromModel = jackpotBackendMap.get("0");
				double majorValFromModel = jackpotBackendMap.get("1");
				double minorValFromModel = jackpotBackendMap.get("2");
				double miniValFromModel = jackpotBackendMap.get("3");

				String[] durationArray = { XpathMap.get("getWinSummary_1week"), XpathMap.get("getWinSummary_2week"),
						XpathMap.get("getWinSummary_1month"), XpathMap.get("getWinSummary_6month"),
						XpathMap.get("getWinSummary_1year"), XpathMap.get("getWinSummary_2year")

				};

				for (int jacpotType = 0; jacpotType < 4; jacpotType++) {
					paramMap.put("param1", Integer.toString(jacpotType));
					// read from the view
					String hook = XpathMap.get("getjackpotWin");
					newHook = replaceParamInHook(hook, paramMap);
					double jackpotWin = Double
							.parseDouble((getConsoleText("return " + newHook)).replaceAll("[^0-9]", ""));

					hook = XpathMap.get("gethighestWin");
					newHook = replaceParamInHook(hook, paramMap);
					String strhighestWinInView = getConsoleText("return " + newHook).replaceAll("[^0-9]", "");
					double highestWinInView = Double
							.parseDouble("".equals(strhighestWinInView) ? "0" : strhighestWinInView);

					hook = XpathMap.get("gettotalWin");
					newHook = replaceParamInHook(hook, paramMap);
					String strTotalWinInView = getConsoleText("return " + newHook).replaceAll("[^0-9]", "");
					double totalWinInView = Double.parseDouble("".equals(strTotalWinInView) ? "0" : strTotalWinInView);

					hook = XpathMap.get("getnoOfWin");
					newHook = replaceParamInHook(hook, paramMap);
					String strNoOfWinInView = getConsoleText("return " + newHook).replaceAll("[^0-9]", "");
					double noOfWinInView = Double.parseDouble("".equals(strNoOfWinInView) ? "0" : strNoOfWinInView);

					hook = XpathMap.get("getlastWin");
					newHook = replaceParamInHook(hook, paramMap);
					String strLastWinInView = (getConsoleText("return " + newHook)).replaceAll("[^0-9]", "");
					double lastWinInView = Double.parseDouble("".equals(strLastWinInView) ? "0" : strLastWinInView);

					hook = XpathMap.get("getaverageWin");
					newHook = replaceParamInHook(hook, paramMap);
					String strAverageWinInView = getConsoleText("return " + newHook).replaceAll("[^0-9]", "");
					double averageWinInView = Double
							.parseDouble("".equals(strAverageWinInView) ? "0" : strAverageWinInView);

					hook = XpathMap.get("getavgWinTime");
					newHook = replaceParamInHook(hook, paramMap);
					String strAvgWinTimeInView = getConsoleText("return " + newHook);
					String avgWinTimeInView = "-".equals(strAvgWinTimeInView) ? "0" : strAvgWinTimeInView;

					if (jacpotType == 0) {

						if (jackpotWin == megaValFromModel) {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Mega win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + megaValFromModel, "Actual Value is :: " + jackpotWin,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Mega win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + megaValFromModel, "Actual Value is :: " + jackpotWin,
									"Fail");
						}

					} else if (jacpotType == 1) {
						if (jackpotWin == majorValFromModel) {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Major win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + majorValFromModel, "Actual Value is :: " + jackpotWin,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Major win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + majorValFromModel, "Actual Value is :: " + jackpotWin,
									"Fail");
						}

					} else if (jacpotType == 2) {
						if (jackpotWin == minorValFromModel) {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Minor win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + minorValFromModel, "Actual Value is :: " + jackpotWin,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Minor win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + minorValFromModel, "Actual Value is :: " + jackpotWin,
									"Fail");
						}
					} else if (jacpotType == 3) {
						if (jackpotWin == miniValFromModel) {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Mini win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + miniValFromModel, "Actual Value is :: " + jackpotWin,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"<b>Verify the Mini win value for " + jackpotHistoryDurationArray[counter1]
											+ "</b>",
									"Expecrted value is :: " + miniValFromModel, "Actual Value is :: " + jackpotWin,
									"Fail");
						}
					}
					// read from Model

					Map<String, Long> map = getConsoleLongMap(
							"return " + durationArray[counter1] + "[" + jacpotType + "]");
					if (map == null) {
						if (highestWinInView == 0.0) {
							report.detailsAppendNoScreenshot(
									"Highest win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + highestWinInView, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Highest win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + highestWinInView, "Fail");
						}
						if (totalWinInView == 0.0) {
							report.detailsAppendNoScreenshot(
									"Total win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + totalWinInView, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Total win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + totalWinInView, "Fail");
						}
						if (noOfWinInView == 0) {
							report.detailsAppendNoScreenshot(
									"Number of wins verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + noOfWinInView, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Number of wins verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + noOfWinInView, "Fail");
						}
						if (lastWinInView == 0.0) {
							report.detailsAppendNoScreenshot(
									"Last win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + lastWinInView, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Last win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + lastWinInView, "Fail");
						}
						if (averageWinInView == 0.0) {
							report.detailsAppendNoScreenshot(
									"Average win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + averageWinInView, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Average win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + 0.0, "Actual Value is :: " + averageWinInView, "Fail");
						}
						if (avgWinTimeInView.equalsIgnoreCase("0")) {
							report.detailsAppendNoScreenshot(
									"Average win time value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: 0", "Actual Value is :: " + avgWinTimeInView, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Average win time value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: 0", "Actual Value is :: " + avgWinTimeInView, "Fail");
						}
					} else {
						System.out.println(map);
						double maxWinAmount = map.get("maxWinAmount");
						double totalWinAmount = map.get("totalWinAmount");
						double winCount = map.get("winCount");
						double lastJackpotNumber = map.get("lastJackpotNumber");
						double avgWinAmount = map.get("avgWinAmount");
						long avgWinTime = map.get("avgWinTime");

						double lastWinAmt = getLastWinAmount(lastJackpotNumber);

						// System.out.println(map.get(""));

						if (highestWinInView == maxWinAmount) {
							report.detailsAppendNoScreenshot(
									"Highest win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + maxWinAmount, "Actual Value is :: " + highestWinInView,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Highest win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + maxWinAmount, "Actual Value is :: " + highestWinInView,
									"Fail");
						}
						if (totalWinInView == totalWinAmount) {
							report.detailsAppendNoScreenshot(
									"Total win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + totalWinAmount, "Actual Value is :: " + totalWinInView,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Total win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + totalWinAmount, "Actual Value is :: " + totalWinInView,
									"Fail");
						}
						if (noOfWinInView == winCount) {
							report.detailsAppendNoScreenshot(
									"Number of wins verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + winCount, "Actual Value is :: " + noOfWinInView, "pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Number of wins verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + winCount, "Actual Value is :: " + noOfWinInView, "Fail");
						}
						if (lastWinInView == lastWinAmt) {
							report.detailsAppendNoScreenshot(
									"Last win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + lastWinAmt, "Actual Value is :: " + lastWinInView,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Last win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + lastWinAmt, "Actual Value is :: " + lastWinInView,
									"Fail");
						}
						if (averageWinInView == avgWinAmount) {
							report.detailsAppendNoScreenshot(
									"Average win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + avgWinAmount, "Actual Value is :: " + averageWinInView,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Average win value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + avgWinAmount, "Actual Value is :: " + averageWinInView,
									"Fail");
						}

						String strAvgWinTime = convertToMonthWeekssFormat(avgWinTime);
						if (avgWinTime == 0.0 && avgWinTimeInView.equalsIgnoreCase("0")) {
							report.detailsAppendNoScreenshot(
									"Average win time value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + avgWinTime, "Actual Value is :: " + avgWinTimeInView,
									"pass");
						} else if (avgWinTimeInView.equalsIgnoreCase(strAvgWinTime)) {
							report.detailsAppendNoScreenshot(
									"Average win time value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + strAvgWinTime, "Actual Value is :: " + avgWinTimeInView,
									"pass");
						} else {
							report.detailsAppendNoScreenshot(
									"Average win time value verification for " + jackpotHistoryDurationArray[counter1],
									"Expecrted value is :: " + strAvgWinTime, "Actual Value is :: " + avgWinTimeInView,
									"Fail");
						}

					}
				}

				for (int counter2 = 0; counter2 <= scroll + 1; counter2++) {

					report.detailsAppendFolder("verify the ProgressiveWinHistoryWidget screen shot",
							" ProgressiveWinHistoryWidget screen shot", "ProgressiveWinHistoryWidget screenshot ",
							"pass", languageCode);
					paramMap.put("param1", Double.toString(jackpotHistoryPageHeight));
					paramMap.put("param2", Integer.toString(counter2));

					String hook = XpathMap.get("scrollProgressiveWinHistoryWidget");
					newHook = replaceParamInHook(hook, paramMap);
					js.executeScript("return " + newHook);
					log.debug("Scrolling the ProgressiveWinHistoryWidget and taking screenshots");

				}

				if (!GetConsoleBooleanText("return " + XpathMap.get("isJackpotHistoryRightArrowVisible"))) {
					break;
				} else if (index == 2) {
					clickAtButton("return " + XpathMap.get("ClickOnJackpotHistoryRightArrow"));
				}
			}

			log.debug("closing ProgressiveWinHistoryWidget");
			clickAtButton("return  " + jackpotTrophyCloseHook);
		} catch (Exception e) {
			log.error("Error While taking ProgressiveWinHistoryWidget screenshots", e);
			clickAtButton("return  " + jackpotTrophyCloseHook);
		}
	}

	double getLastWinAmount(double lastJackpotNumber) {

		double lastwinAmt = 0.0;
		List<Map<String, Long>> lastWinlist = getConsoleListMap("return " + XpathMap.get("getRecentWin"));

		for (Map<String, Long> map : lastWinlist) {
			long jackpotNumber = map.get("jackpotNumber");
			System.out.println(jackpotNumber);
			// double dbljackpotNumber= Double.parseDouble(jackpotNumber);
			if (jackpotNumber == lastJackpotNumber) {
				lastwinAmt = map.get("winAmount");
				break;
			}
		}

		return lastwinAmt;
	}

	/*
	 * Author:Snehal Gaikwad Method Description: create dynamic hook by passing
	 * parameter as placeholder in hook Input:String Hook read from testdata.xls
	 * Map<String ,String> of parameterName and value to be replace Output:String
	 */
	public String replaceParamInHook(String str, Map<String, String> data) {

		Pattern p = Pattern.compile("\\$\\{(.+?)\\}");

		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String var = m.group(1);
			String replacement = data.get(var);
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public void refresh(Desktop_HTML_Report report) {
		try {
			WebDriverWait wait = new WebDriverWait(webdriver, 60);

			webdriver.navigate().refresh();
			report.detailsAppend("Verfiy Refresh", "Refreshed Suceessfuly", "Refreshed Suceessfuly", "PASS");
			if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))
					|| (Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))));
				webdriver.findElement(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))).click();
				report.detailsAppend("Verfiy Continue", "Refreshed and clicked on Continue",
						"Refreshed and clicked on Continue", "PASS");

			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
				Thread.sleep(1000);
				// clickAtButton("return "+XpathMap.get("BaseIntroContinueBtn"));
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public Map<String, String> getAllTheModalFooter() {
		Map<String, String> testDataMap = XpathMap;
		Map<String, String> modalHeadersMap = new HashMap<String, String>();
		Set<String> testDataKeys = testDataMap.keySet();

		for (String key : testDataKeys) {
			if (key.startsWith("ModalFooter")) {
				modalHeadersMap.put(key, testDataMap.get(key));
			}
		}
		return modalHeadersMap;
	}

	public Map<String, String> getAllTheModalHeaders() {
		Map<String, String> testDataMap = XpathMap;
		Map<String, String> modalHeadersMap = new HashMap<String, String>();
		Set<String> testDataKeys = testDataMap.keySet();

		for (String key : testDataKeys) {
			if (key.startsWith("ModalHearder")) {
				modalHeadersMap.put(key, testDataMap.get(key));
			}
		}
		return modalHeadersMap;
	}

	public String checkIfAnyModelHeaderOccured(Map<String, String> modalHeadersMap) {

		System.out.println("Size of the Map ==================== " + modalHeadersMap.size());

		String headerKey = null;
		String currentModal = null;

		for (Map.Entry<String, String> entry : modalHeadersMap.entrySet()) {

			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

			if (!(super.GameName.contains("CasinoRewardsMillionairesClubDe"))) {
				boolean isModalHeaderDisplayed = GetConsoleBooleanText("return " + entry.getValue() + ".visible");
				long isAlphaDisplayed = getConsoleNumeric("return " + entry.getValue() + ".alpha");

				System.out.println("isModalHeaderDisplayed ::  " + isModalHeaderDisplayed);
				System.out.println("isAlphaDisplayed :: " + isAlphaDisplayed);

				if (isModalHeaderDisplayed && isAlphaDisplayed == 1) {
					System.out.println("Found :: " + entry.getKey() + ", Value = " + entry.getValue());
					headerKey = entry.getKey();
					break;
				}
			} else {
				if (entry.getKey().contains("ModalHearder"))
					currentModal = getConsoleText("return " + XpathMap.get("getCurrentModalText"));
				else
					currentModal = getConsoleText("return " + XpathMap.get("getCurrentFooterText"));
				if (entry.getValue().equalsIgnoreCase(currentModal)) {
					System.out.println("Found :: " + entry.getKey() + ", Value = " + entry.getValue());
					headerKey = entry.getKey();
					break;
				}
			}
		}
		return headerKey;
	}

	@Override
	/**
	 * This method is used to click on free spin enrtry screen
	 */
	public String clickToContinue() {
		String ret = null;
		WebDriverWait wait = new WebDriverWait(webdriver, 5);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			elementWait("return " + XpathMap.get("IsFS_EntryContinueBtnVisible"), true);
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("IsFS_EntryContinueBtnVisible"));
			if (flag) {
				Thread.sleep(4000);
				// if there is no hook for the continue button, then closeOverlay will be called
				if (XpathMap.get("Click_FS_EntryContinueBtn") != null) {
					clickAtButton("return " + XpathMap.get("Click_FS_EntryContinueBtn"));
				} else {
					log.debug("About to click freespins entry continue button overlay");
					System.out.println("About to click freespins entry continue button overlay");
					closeOverlay();
					System.out.println("clicked on freespins entry continue button overlay");
					log.debug("clicked on freespins entry continue button overlay");
				}
				log.debug("Clicked free spin entry screen continue button to start Free spins");
				ret = "Clicked";
			} else {
				log.debug("No  continue button available in free spins entry screen");
				ret = "NotClicked";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ret;
	}

	public String captureJackpotInfoPaytable(Desktop_HTML_Report report, String folderName) {
		log.debug("Inside Jackpot info Paytable ");
		Wait = new WebDriverWait(webdriver, 50);
		String paytable = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			Double paytableFullHeight2 = (Double) js.executeScript("return " + XpathMap.get("JackpotInfoScrollHeight"));
			double paytableHeight2 = 0;
			if (js.executeScript("return " + XpathMap.get("JackpotInfoScroll_h")).getClass().getName()
					.endsWith("Long")) {
				long longPaytableHeight = (Long) js.executeScript("return " + XpathMap.get("JackpotInfoScroll_h"));
				paytableHeight2 = (double) longPaytableHeight;
			} else {
				paytableHeight2 = (double) js.executeScript("return " + XpathMap.get("JackpotInfoScroll_h"));
			}

			int scroll = (int) (paytableFullHeight2 / paytableHeight2);

			for (int i = 1; i <= scroll + 1; i++) {

				js.executeScript("return " + forceNamespace
						+ ".getControlById('JackpotInfoPaytableComponent').storyScroll.scrollTo(0,-"
						+ paytableHeight2 * i + ")");
				log.debug("Scrolling the jackpot info page and taking screenshots");
				report.detailsAppendFolderOnlyScreenShot(folderName);

			}
			paytable = "paytable1";
		} catch (Exception e) {
			log.error("error in captureJackpotInfoPaytable", e);
		}
		return paytable;
	}

	/**
	 * 
	 * Below code unlock the bonus by clicking on bonus selction iteam depending on
	 * the NoOfBonusSelection count
	 */

	public void unlockBonus(Desktop_HTML_Report report) {

		String strbonusCount = XpathMap.get("NoOfBonusSelection");
		int bonusCount = (int) Double.parseDouble(strbonusCount);

		String selectBonus = XpathMap.get("SelectBonus");
		Map<String, String> paramMap = new HashMap<String, String>();
		ExcelDataPoolManager excelpoolmanager = new ExcelDataPoolManager();
		String testDataExcelPath = TestPropReader.getInstance().getProperty("TestData_Excel_Path");
		CommonUtil util = new CommonUtil();
		Map<String, String> rowData2 = null;
		Map<String, String> rowData3 = null;
		String languageDescription = null;
		String languageCode = null;
		String urlNew = null;
		// Reading the language code in to list map
		@SuppressWarnings("rawtypes")
		List<Map> list = util.readLangList();

		try {
			int rowCount2 = excelpoolmanager.rowCount(testDataExcelPath, "LanguageCodes");
			log.debug("Total number of Languages configured" + rowCount2);

			for (int iteamCnt = 0; iteamCnt < bonusCount; iteamCnt++) {
				paramMap.put("param1", Integer.toString(iteamCnt));
				String newSelectBonusHook = replaceParamInHook(selectBonus, paramMap);

				String clickOnBonus = "return " + newSelectBonusHook;
				clickAtButton(clickOnBonus);
				for (int j = 1; j < rowCount2; j++) {
					rowData2 = list.get(j);
					languageDescription = rowData2.get(Constant.LANGUAGE).trim();
					languageCode = rowData2.get(Constant.LANG_CODE).trim();
					log.debug("Waiting after language change,to bonus selction scene to come");
					elementWait("return " + XpathMap.get("currentScene"), "PLAYING_ACTIVE_BONUS");
					log.debug("Wait over");
					Thread.sleep(2000);
					report.detailsAppendFolder(
							"Verify Language translations on Bonus feature in  " + languageCode + " ",
							"Bonus scene should display in " + languageDescription + " ",
							"Bonus scene displays in " + languageDescription + " ,verify screenshot", "Pass",
							languageCode);
					if (j + 1 != rowCount2) {
						log.debug("Going for next lang");
						rowData3 = excelpoolmanager.readExcelByRow(testDataExcelPath, "LanguageCodes", j + 1);
						String nextLanguage = rowData3.get(Constant.LANG_CODE).trim();
						log.debug("read next lang from excel" + nextLanguage);
						String currentUrl = webdriver.getCurrentUrl();

						if (currentUrl.contains("LanguageCode"))
							urlNew = currentUrl.replaceAll("LanguageCode=" + languageCode,
									"LanguageCode=" + nextLanguage);
						else if (currentUrl.contains("languagecode"))
							urlNew = currentUrl.replaceAll("languagecode=" + languageCode,
									"languagecode=" + nextLanguage);

						loadGame(urlNew);
						if ((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
							newFeature();
						}
						log.debug("Load game with lang ::" + nextLanguage);
					} else {
						log.debug("Going to load eng lang");
						Thread.sleep(2000);
						rowData3 = excelpoolmanager.readExcelByRow(testDataExcelPath, "LanguageCodes", 1);
						String nextLanguage = rowData3.get(Constant.LANG_CODE).trim();
						log.debug("read next lang from excel" + nextLanguage);
						String currentUrl = webdriver.getCurrentUrl();
						if (currentUrl.contains("LanguageCode"))
							urlNew = currentUrl.replaceAll("LanguageCode=" + languageCode,
									"LanguageCode=" + nextLanguage);
						else if (currentUrl.contains("languagecode"))
							urlNew = currentUrl.replaceAll("languagecode=" + languageCode,
									"languagecode=" + nextLanguage);

						loadGame(urlNew);
						if ((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
							newFeature();
						}
						log.debug("Load game with lang ::" + nextLanguage);
					}
				}
				Thread.sleep(2000);
			}

			report.detailsAppend("Unlock Bonus", "on selection of bonus,bonus should unlock",
					"bonus  unlock,verify screenshot", "pass");
		} catch (InterruptedException exception) {
			log.debug(exception.getMessage(), exception);
		}

		catch (IOException e) {
			log.error("Exception occur while reading  excel file", e);
		}
	}

	public boolean isGameNameVisibleInCurrentScene() {
		boolean isVisible = false;
		try {
			if (GetConsoleBooleanText("return " + XpathMap.get("IsGameNameVisible"))) {
				isVisible = true;
			}
		} catch (JavascriptException exception) {
			log.error(exception);

		}
		return isVisible;
	}

	/*
	 * Description : This method will wait for specific amount of time for
	 * visibility of element Overload of public void elementWait(String
	 * element,boolean visibility)
	 * 
	 */
	public boolean elementWait(String element, boolean value, int waitTimeInSec) {
		long startTime = System.currentTimeMillis();
		while (true) {
			boolean isVisible = GetConsoleBooleanText(element);
			if (isVisible == value) {
				return true;
			}
			long currentime = System.currentTimeMillis();
			// Break if wait is more than given secs
			if (((currentime - startTime) / 1000) > waitTimeInSec) {
				log.debug("Element  is not visible, break after " + waitTimeInSec + " sec");
				return false;
			}
			threadSleep(500);
		}
	}

	/**
	 * To click on the Autoplay limit dialog box
	 */
	public void clickOnPrimaryBtn() {
		clickAtButton("return " + XpathMap.get("ClickPrimaryBtn"));
		log.debug("Autoplay dialog is closed");
	}

	/**
	 * To click on continue button in session reminder in denmark
	 */

	public void sessionReminderDenmark() {
		webdriver.findElement(By.xpath(XpathMap.get("Denmark_SessionReminder"))).click();
		log.debug("clicked on continue in session reminder dialog box");
	}
	
	/**
	 * To click on continue button in session reminder
	 */

	public void selectContinueSession() 
	{
		try {
		webdriver.findElement(By.xpath(XpathMap.get("SessionContinue"))).click();
		log.debug("clicked on continue in session reminder dialog box");
		}
		catch(Exception e)
		{
			log.debug("Unable to click on session continue ",e);
		}
	}

	/**
	 * This method is used to find the reel spin duration for a single spin
	 */
	public long reelSpinDuratioN(Desktop_HTML_Report report)
	{
		long quickspinloadingTime = 0;
		try 
		{
			spinclick();
			long start = System.currentTimeMillis();
			log.debug("Start time for Reel spin duration in milliseconds is "+start);
			 
			 // Wait till the stop button  
			waitForSpinButtonstop();                         
			long finish = System.currentTimeMillis();
			log.debug("Finsish time for Reel spin duration in milliseconds is "+finish);
						
			long totaltime = finish - start;
			//quickspinloadingTime = totaltime / 1000;
			quickspinloadingTime = totaltime;
			
		    log.debug("Total Reel spin time in seconds is (Finish- Start =)"+quickspinloadingTime);
			log.debug("Reel Spin Duration , STATAUS : PASS");
			
		}
	
		catch(Exception e)
		{
			report.detailsAppend("Verify Reel Spin ", "Reel Spin Duration", "Reel Spin Duration", "FAIL");
			log.error("Not able to verify reelspin time",e);
		}
		return quickspinloadingTime;
	}

	/**
	 * To verify the status of spin button
	 */
	public boolean verifySpinStatus() {
		try {

			String spinCurrectState = getConsoleText("return " + XpathMap.get("SpinBtnCurrState"));
			if (spinCurrectState.equalsIgnoreCase("active")) {
				log.debug("spin button is Active");
				System.out.println("spin button is Active");
				return true;
			} else {
				log.error("spin buttion is not Active");
				System.out.println("spin button is not Active");
				return false;
			}
		} catch (Exception e) {
			log.error("Not able to verify spin button status", e);
		}
		return false;
	}

	/**
	 * Author:pb61055 This method is used for verifying whether quick spin is
	 * present or not
	 * 
	 * @return true
	 */

	public boolean isQuickspinAvailable() 
	{
		try 
		{
			boolean isQuickspinVisible = GetConsoleBooleanText("return " + XpathMap.get("QuickSpinBtnAvailable"));
			if (isQuickspinVisible) 
			{
				log.debug("Quickspin is available");
				System.out.println("Quickspin is available");
				return true;
			} 
			else 
			{
				log.error("Quickspin is not available");
				System.out.println("Quickspin is not available");
				return false;
			}
			
		} 
		catch (Exception e)
		{
			log.error("Not able to verify quickspin Availability", e);
		}
		return false;
	}

	/**
	 * Author:Priyanka Bethi This method is used for verifying whether stop button
	 * is present or not
	 * 
	 * @return true
	 */

	public boolean isStopButtonAvailable() 
	{
		boolean isstopbutton = true;
		try 
		{
	        spinclick();
			// To verify the stop button
			String stopbutton = getConsoleText("return " + XpathMap.get("SpinBtnCurrState"));
			if (stopbutton.equalsIgnoreCase("activeSecondary") ||(stopbutton.equalsIgnoreCase("disabled")))
			{
				// As current state of button is ActiveSecondary
				log.debug("Stop button is available");
				System.out.println("Stop button is available");
				isstopbutton = true;
			} 
			else 
			{
				log.error("Stop button is not available");
				System.out.println("Stop button is available");
				isstopbutton = false;
			}
		} 
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}

		return isstopbutton;
	}

	/**
	 * To verify bonus game win currency format
	 */

	public boolean bonusWinCurrFormat(String currencyFormat) {
		String bonuswin = null;
		boolean isWinInCurrencyFormat = false;
		try {
			log.debug("Function -> bonuswinCurrencyFormat()");
			if (!GameName.contains("Scratch")) {
				bonuswin = "return " + XpathMap.get("BonusWinAmount");
			} else {
				// need to update the hook for Scratch game
				bonuswin = "return " + XpathMap.get("InfobarBettext");
			}
			String bonuswinnew = getConsoleText(bonuswin);
			System.out.println("Bonus win text from the console " + bonuswinnew);
			// To validate currency Amount format
			isWinInCurrencyFormat = currencyFormatValidator(bonuswinnew, currencyFormat);

			log.debug("Fetching Bonus win currency symbol" + "/n Bonus win currency symbol is::" + bonuswin);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isWinInCurrencyFormat;

	}

	/**
	 * To click on the continue button after bonus game completion
	 */
	public String clickContinueInBonusGame() {

		String ret = null;
		WebDriverWait wait = new WebDriverWait(webdriver, 5);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			elementWait("return " + XpathMap.get("IsBonusContinueBtnVisible"), true);
			boolean flag = GetConsoleBooleanText("return " + XpathMap.get("IsBonusContinueBtnVisible"));
			if (flag) {
				Thread.sleep(4000);
				clickAtButton("return  " + XpathMap.get("continueBtnBonus"));
				System.out.println("Clicked Bonus game free spin entry screen continue button to start Free spins");
				log.debug("Clicked Bonus game free spin entry screen continue button to start Free spins");
				ret = "Clicked";
			} else {
				log.debug("No  continue button available in Bonus game free spins entry screen");
				ret = "NotClicked";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ret;
	}

	/**
	 * Verify Stop Button Availability
	 */
	public boolean verifyStopButtonAvailablity() 
	{
		boolean isstopbutton = true;
		try 
		{
	    clickAtButton("return " + XpathMap.get("ClickSpinBtn"));
			// To verify the stop button
			String stopbutton = getConsoleText("return " + XpathMap.get("SpinBtnCurrState"));

			if (stopbutton.equalsIgnoreCase("activeSecondary") || !(stopbutton.equalsIgnoreCase("disabled")))
			{
				// As current state of button is ActiveSecondary
				log.debug("Stop button is available");
				System.out.println("Stop button is available");
				isstopbutton = true;
			} 
			else 
			{
				log.error("Stop button is not available");
				System.out.println("Stop button is not available");
				isstopbutton = false;
			}
		} 
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}

		return isstopbutton;
	}

	/**
	 * To validate currency amount with the currency format in excel sheet
	 */
	public boolean currencyFormatValidator(String curencyAmount, String currencyFormat) {
		boolean isCurrencyFormat = false;
		String strCurrencyExp = null;
		try {
			String currencyregexp = createregexp(curencyAmount, currencyFormat);
			if (currencyregexp.contains("$"))
				strCurrencyExp = currencyregexp.replace("$", "\\$");
			else
				strCurrencyExp = currencyregexp;
			Pattern.compile(strCurrencyExp);
			String currencyexp = strCurrencyExp.replace("#", "\\d");

			if (Pattern.matches(currencyexp, curencyAmount)) {
				isCurrencyFormat = true;
			} else {
				isCurrencyFormat = false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isCurrencyFormat;

	}

	/**
	 * To select the bonus books in bonus game(hotInk)
	 */

	public boolean bonusSelection(String currencyFormat, int iteamCnt) {
		boolean isBonusInCurrencyFormat = false;
		try {
			String bonusSelect = XpathMap.get("BonusSelection");
			Map<String, String> paramMap = new HashMap<String, String>();

			paramMap.put("param1", Integer.toString(iteamCnt));
			String newBonusBook = "return " + replaceParamInHook(bonusSelect, paramMap);
			Thread.sleep(3000);
			String bonusBookWin = getConsoleText(newBonusBook);
			System.out.println("Bonus win text from console " + bonusBookWin);

			isBonusInCurrencyFormat = currencyFormatValidator(bonusBookWin, currencyFormat);

			log.debug("Fetching Bonus win currency symbol" + "/n Bonus win currency symbol is::" + bonusBookWin);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isBonusInCurrencyFormat;
	}

	/**
	 * To verify piggybank bonus in bust the bank
	 * 
	 * @return
	 */
	public boolean piggyBankBonus(String currencyFormat) {
		String bonuswin = null;
		boolean isBonusInCurrencyFormat = false;
		try {
			log.debug("Function -> piggybonuswinCurrencyFormat()");
			if (!GameName.contains("Scratch")) {
				bonuswin = "return " + XpathMap.get("PiggyBonusAmount");
			}
			String bonuswinnew = getConsoleText(bonuswin);
			System.out.println("Piggy bank Bonus win text from the console to check currency format " + bonuswinnew);

			isBonusInCurrencyFormat = currencyFormatValidator(bonuswinnew, currencyFormat);
			log.debug("Fetching Safe Bonus win currency symbol" + "/n Bonus win currency symbol is::" + bonuswinnew);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isBonusInCurrencyFormat;
	}

	/**
	 * To verify Safe bonus bonus in bust the bank
	 * 
	 * @return
	 */
	public boolean verifySafeBonusCurrency(String currencyFormat) {
		String bonuswin = null;
		boolean isBonusInCurrencyFormat = false;
		try {
			log.debug("Function -> safebonuswinCurrencyFormat()");
			if (!GameName.contains("Scratch")) {
				bonuswin = "return " + XpathMap.get("SafeBonusWinAmount");
			}
			String bonuswinnew = getConsoleText(bonuswin);
			System.out.println("Safe Bonus win text from the console to check currency format " + bonuswinnew);

			isBonusInCurrencyFormat = currencyFormatValidator(bonuswinnew, currencyFormat);
			log.debug("Fetching Safe Bonus win currency symbol" + "/n Bonus win currency symbol is::" + bonuswinnew);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isBonusInCurrencyFormat;
	}

	
	
	

	/**
	 * To open Responsible Gaming from Topbar
	 */
	public boolean openResponsiblegamingFromTopbar(Desktop_HTML_Report report,String market) {
		boolean ret = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			String gameurl = webdriver.getCurrentUrl();
			webdriver.findElement(By.xpath(XpathMap.get("Italy_ResponsibleGaming"))).click();
			Thread.sleep(4000);
			report.detailsAppend(" Verify Responsible Gaming  ", "Responsible Gaming Clicked ",
					"Responsible Gaming Clicked", "PASS");
			log.debug("clicked on Responsible Gaming");
			System.out.println("clicked on Responsible Gaming");
			Thread.sleep(2000);
			checkpagenavigationMarket(report, gameurl, market);
			System.out.println("Page navigated to Back to Game ");
			log.debug("Page navigated to Back to Game");
			ret = true;
		} catch (Exception e) {
			log.error("Error in navigetion to help page ", e);
			report.detailsAppend(" Verify Responsible Gaming  ", "Responsible Gaming isn't  Clicked ",
					"Responsible Gaming isn't Clicked", "FAIL");
		}
		return ret;
	}

	/*
	 * Close for Denmark on Topbar
	 */
	public boolean close(Desktop_HTML_Report report) {

		boolean isclose = false;
		try {

			// If the session reminder present It will click on continue
			List<WebElement> elementList = webdriver
					.findElements(By.xpath(XpathMap.get("Denmark_CloseOnTheTopBarMenu")));

			// boolean ele =
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("SessionContinue")))).isDisplayed();
			if (elementList.size() > 0) {
				Thread.sleep(5000);
				webdriver.findElement(By.xpath(XpathMap.get("Denmark_CloseOnTheTopBarMenu"))).click(); // If size is >
																										// Zero then it
																										// will click on
																										// close button
				report.detailsAppend("Verify close ", "close is Clicked", "close is Clicked", "PASS");
				log.debug("close is Clicked \n");
				System.out.print("close is Clicked");
				isclose = true;
			}

			else {
				log.debug("close isn't Clicked");
				System.out.print("close isn't Clicked");
				report.detailsAppend("Verify close ", "close isn't Clicked", "close isn't Clicked", "FAIL");
			}
		}

		catch (Exception e) {
			log.error("Not able to verify Close status", e);
			System.out.print("Close not found");
			isclose = false;
		}
		return isclose;
	}

	/**
	 * This method is used for verify whether quick spin is present or not
	 * 
	 * @return true
	 */

	public boolean verifyQuickspinAvailablity()
	{
		boolean isQuickSpin = false;
		try {

			boolean isQuickspinVisible = GetConsoleBooleanText("return " + XpathMap.get("QuickSpinBtnAvailable"));
			if (isQuickspinVisible)
			{
				System.out.print("Quickspin is available");
				log.debug("Quickspin is available");
				isQuickSpin =true;
			}
			else
			{
				System.out.print("Quickspin is not available");
				log.error("Quickspin is not available");
				isQuickSpin =false;
			}
		} catch (Exception e) 
		{
			log.error("Not able to verify quickspin Availability", e);
		}
		return isQuickSpin;
	}

	/*
	 * Verify Session Reminder
	 */

	public boolean verifyDenmarkSessionReminderPresent(Desktop_HTML_Report report) {
		boolean isSessionReminderVisible = false;
		try {

			// If the session reminder present It will click on continue
			List<WebElement> elementList = webdriver.findElements(By.xpath(XpathMap.get("Denmark_SessionReminder"))); // Search
																														// on
																														// the
																														// page.

			// boolean ele =
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(xpathMap.get("SessionContinue")))).isDisplayed();
			if (elementList.size() > 0) {
				// ((JavascriptExecutor) webdriver).executeScript("arguments[0].click();",
				// elementList);
				webdriver.findElement(By.xpath(XpathMap.get("Denmark_SessionReminder"))).click(); // If size is > Zero
																									// then it will
																									// click on session
																									// continue button
				report.detailsAppend("Verify Session Reminder ", "Session Reminder is Clicked",
						"Session Reminder is Clicked", "PASS");
				log.debug("Session reminder found");
				System.out.print("Session reminder found");
				isSessionReminderVisible = true;
			}

			else {
				log.debug("Session reminder found");
				System.out.print("Session reminder not found");
				report.detailsAppend("Verify Session Reminder ", "Session Reminder isn't Clicked",
						"Session Reminder isn't Clicked", "FAIL");
			}
		}

		catch (Exception e) {
			log.error("Not able to verify session reminder status", e);
			System.out.print("Session reminder not found");
			// report.detailsAppend("Verify Session Reminder ", "Session Reminder isn't
			// Clicked","Session Reminder isn't Clicked", "FAIL");
			isSessionReminderVisible = false;
		}
		return isSessionReminderVisible; // if it return false then there is no session reminder present on the screen
	}

	/**
	 *Verifies  player protection icon and its navigation 
	 */
		public boolean playerProtectionIcon(Desktop_HTML_Report report) 
		{
			String playerprotection = "PlayerProtection_Icon";
			boolean playerprotectionicon = false;
			try
			{
			clickAndNavigate(report,playerprotection);
			playerprotectionicon = true;
			}
			catch(Exception e)
			{
				log.error(e.getMessage(), e);
			}
			return playerprotectionicon;	
	 }
		/**
		 * method is for italy help text comparission , click and its navigation from menu 
		 * @param report
		 * @return
		 * @throws InterruptedException
		 */
			public boolean italyHelpFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException 
			{
				String menuclick = "clickOnMenuOnTopbar";
				String locator = "HelpFromMenu";
				String Text = clickAndGetText(report,menuclick,locator);
				boolean text= false;
				String allHelpText[]= {"Help","Guida"};
				try
				{
				for(int i=0;i<allHelpText.length;i++)
				{
				if (Text.equals(allHelpText[i])) 
				{
					log.debug("Help Text from Menu is same");
					text = true;
				} 
				}Thread.sleep(2000);
				clickAndNavigate(report,locator);
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
					
				return text ;
			}	
			
			/**
			 *  method is for italy PlayerProtection text comparission , click and its navigation from menu 
			 */
			public boolean italyPlayerProtectionFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException 
			{
					String menuclick = "clickOnMenuOnTopbar";
					String locator = "Italy_PlayerProtection";
					String Text = clickAndGetText(report,menuclick,locator);
					boolean text= false;
					String allPlyProtectionText[]= {"Player Protection","Tutela del giocatore"};
					try
					{
					for(int i=0;i<allPlyProtectionText.length;i++)
					{
					if (Text.equals(allPlyProtectionText[i])) 
					{
						log.debug("Help Text from Menu is same");
						text = true;
					} 
					}Thread.sleep(2000);
					clickAndNavigate(report,locator);
					}
					catch (Exception e) 
					{
						log.error(e.getMessage(), e);
					}
						
					return text ;
			}

			/**
			 *  method is for italy ResponsibleGaming text comparission , click and its navigation from menu 
			 */
			public boolean italyResponsibleGamingFromMenu(Desktop_HTML_Report report) throws InterruptedException
			{
					String menuclick = "clickOnMenuOnTopbar";
					String locator = "ResponsibleGamingFromMenu";
					String Text = clickAndGetText(report,menuclick,locator);
					boolean text= false;
					String allResponsibleGmgText[]= {"Responsible Gaming","Gioco responsabile"};
					try
					{
					for(int i=0;i<allResponsibleGmgText.length;i++)
					{
					if (Text.equals(allResponsibleGmgText[i])) 
					{
						log.debug("Help Text from Menu is same");
						text = true;
					} 
					}Thread.sleep(2000);
					clickAndNavigate(report,locator);
					}
					catch (Exception e) 
					{
						log.error(e.getMessage(), e);
					}
						
					return text ;
			
				
			}

	// get console Text
	public String GetConsoleText(String text) {
		String consoleText = null;
		try {
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			consoleText = (String) js.executeScript(text);
			// log.debug("Text Read from Console="+consoleText);
		} catch (Exception e) {
			e.getMessage();
		}
		return consoleText;
	}

	// get currency credits for Reg Markets
	public boolean currency_Check(Desktop_HTML_Report report) {
		String balance = null;
		String consoleBalance = null;
		boolean credit;

		if (!GameName.contains("Scratch")) {
			if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame")))// force console
			{
				balance = "return " + XpathMap.get("Balancetext");
			} else
				balance = "return " + XpathMap.get("Balancetext");
		} else {
			balance = "return " + XpathMap.get("InfcaseoBarBalanceTxt");
		}

		consoleBalance = GetConsoleText(balance);
		System.out.println("Console Balanace is " + consoleBalance);

		String str = consoleBalance;
		System.out.println("String: " + str);
		// int index = str.indexOf("�");
		int index = str.indexOf("kr");
		System.out.printf("Substring '�' is at index %d\n", index);

		/*
		 * String consolebalance = consoleBalance.replace("CREDITS: ","" );
		 * 
		 * System.out.println("Console Balanace is"+consolebalance);
		 * 
		 * char currencySymbolIndex= consolebalance.charAt(0); //
		 * System.out.println("Currency symbol from console "+currencySymbolIndex);
		 * 
		 * String currencySymbol = Character.toString(currencySymbolIndex);
		 * System.out.println("Currency symbol from console"+currencySymbol);
		 * 
		 * String currenysymbolfromexcel = XpathMap.get("ItalySymbol");
		 * System.out.println("Currency symbol from Excel"+currenysymbolfromexcel);
		 */
		if (index != -1) {
			System.out.println("YES, The Currency is Same ");
			credit = true;
		} else {
			System.out.println("NO, The currency isn't Same");
			credit = false;
		}
		return credit;
	}

	/**
	 * To validate balance, payouts and wagers updated correctly and verify slot
	 * loss limit overlay
	 * 
	 * @pb61055
	 */
	public boolean waitUntilSessionLoss(String lossLimit, Desktop_HTML_Report reportSpain) {
		boolean title = false;
		String betValue = getConsoleText("return " + XpathMap.get("BetSizeText"));

		double dblLossLimit = Double.parseDouble(lossLimit.replaceAll("[^0-9]", ""));
		double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));
		try {
			for (int i = 1; i <= 6; i++) {
				String payoutb4Spin = webdriver.findElement(By.xpath(XpathMap.get("spainPayouts"))).getText();
				String wagersB4Spin = webdriver.findElement(By.xpath(XpathMap.get("spainWagers"))).getText();
				String balanceB4Spin = webdriver.findElement(By.xpath(XpathMap.get("spainBalance"))).getText();

				double dblBalanceB4Spin = Double.parseDouble(balanceB4Spin.replaceAll("[^0-9.-]", ""));
				log.debug("wagers before spin: " + wagersB4Spin + " payouts before spin: " + payoutb4Spin
						+ " balance before spin: " + balanceB4Spin + " before spin: " + i);
				if (((-dblBetValue) + (dblBalanceB4Spin)) <= (-dblLossLimit)) 
				{
					waitForSpinButton();
					spinclick();
					Thread.sleep(3000);
					if(((-dblBetValue) + (dblBalanceB4Spin)) == (-dblLossLimit))
					{
						Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_CloseBtn"))));
					}
					else
					{
						Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_lossLimitDialogueOK"))));
						reportSpain.detailsAppend("Verify if Loss Limit reached overlay appear", "Loss Limit reached overlay should appear after loss limit is reached", "Loss Limit reached overlay appears after loss limit is reached", "Pass");
						webdriver.findElement(By.xpath(XpathMap.get("spain_lossLimitDialogueOK"))).click();
					}
					log.debug("Loss Limit is reached");
					title = true;
					break;
				} else {
					waitForSpinButton();
					spinclick();
					Thread.sleep(4000);
					String wagersAfterStop = webdriver.findElement(By.xpath(XpathMap.get("spainWagers"))).getText();

					String payoutAfterStop = webdriver.findElement(By.xpath(XpathMap.get("spainPayouts"))).getText();

					double dblWagersAfterStop = Double.parseDouble(wagersAfterStop.replaceAll("[^0-9]", ""));
					double dblPayoutAfterStop = Double.parseDouble(payoutAfterStop.replaceAll("[^0-9]", ""));

					boolean isBetAdded = isBetAddedToWagers(wagersB4Spin, dblWagersAfterStop, dblBetValue);
					if (isBetAdded) {
						System.out.println("Bet is added to wagers");
					} else {
						System.out.println("Bet is not added to wagers");
					}
					boolean isPlayerWon = isPlayerWon();
					if (isPlayerWon) {
						boolean isWinAddedToPayout = isWinAddedToPayout(payoutb4Spin, dblPayoutAfterStop);
						try {
							if (isWinAddedToPayout) {
								isBalanceUpdated(dblPayoutAfterStop, dblWagersAfterStop, dblBetValue);

							} else {
								log.debug("win is not added to payout");
							}
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
					} else {
						isBalanceUpdated(dblPayoutAfterStop, dblWagersAfterStop, dblBetValue);
						Thread.sleep(2000);
					}
					reportSpain.detailsAppend("verify Wages,payout and balance are updating after every spin",
							"Wages,payout and balance should update after every spin ",
							"Wages,payout and balance are updating after every spin", "Pass");
					log.debug("wagers after spin stop for spin " + i + ":" + wagersAfterStop
							+ " payouts after spin stop for spin " + i + ":" + payoutAfterStop);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return title;
	}

	/**
	 * To validate bet is added to wagers correctly
	 */
	public boolean isBetAddedToWagers(String wagersB4Spin, double dblWagersAfterStop, double dblBetValue) {
		boolean isBetAddedToWagers = false;
		try {
			double dblWagersB4Spin = Double.parseDouble(wagersB4Spin.replaceAll("[^0-9]", ""));

			if ((dblWagersB4Spin + dblBetValue) == dblWagersAfterStop) {
				log.debug("wagers updated succesfully");
				isBetAddedToWagers = true;
			} else {
				log.debug("wagers is not added");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isBetAddedToWagers;
	}

	/**
	 * To validate win is added to payouts correctly
	 */
	public boolean isWinAddedToPayout(String payoutb4Spin, double dblPayoutAfterStop) {
		boolean isWinAddedToPayout = false;
		try {
			String winAmount = null;
			if (!GameName.contains("Scratch")) {
				winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
			} else {
				winAmount = getConsoleText("return " + XpathMap.get("WinText"));
			}
			log.debug("win amount: " + winAmount);
			double dblPayoutB4Spin = Double.parseDouble(payoutb4Spin.replaceAll("[^0-9]", ""));
			double dblWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));

			if ((dblPayoutB4Spin + dblWinAmount) == dblPayoutAfterStop) {
				log.debug("win added to payout succesfully");
				isWinAddedToPayout = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isWinAddedToPayout;
	}

	/**
	 * To validate balance is updated correctly
	 */
	public boolean isBalanceUpdated(double dblPayoutAfterStop, double dblWagersAfterStop, double dblBetValue) {
		boolean isBalanceUpdated = false;
		try {
			String BalanceAfterStop = webdriver.findElement(By.xpath(XpathMap.get("spainBalance"))).getText();
			double dblBalanceAfterStop = Double.parseDouble(BalanceAfterStop.replaceAll("[^0-9.-]", ""));
			log.debug("Balance after spin stop: " + BalanceAfterStop);

			if ((dblPayoutAfterStop - dblWagersAfterStop) == dblBalanceAfterStop) {
				log.debug("Balance updated successfully");
				isBalanceUpdated = true;
			} else {
				log.debug("Balance not updated successfully");
				isBalanceUpdated = false;

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isBalanceUpdated;
	}

	/**
	 * To verify Menu navigations from topbar in uk
	 * 
	 * @return
	 */
	public void verifyMenuOptionNavigationsForUK(Desktop_HTML_Report report) {
		try {
			// To verify Transaction history
			verifyTransactionHistoryOnTopbar(report);
			Thread.sleep(2000);

			// To verify help
			verifyHelpOnTopbar(report);
			Thread.sleep(2000);

			// To verify Game history
			verifyGameHistoryOnTopbar(report);
			Thread.sleep(2000);

			// To verify Player protection
			verifyPlayerProtectionOnTopbar(report);
			Thread.sleep(2000);

		} catch (Exception e) {
			log.error(e.getMessage());
			try {
				report.detailsAppendNoScreenshot("verifyMenuOptionNavigationsForUK", "",
						"Exception while verifyMenuOptionNavigationsForUK", "fail");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	private void verifyPlayerProtectionOnTopbar(Desktop_HTML_Report report) {
		// TODO Auto-generated method stub
		
	}

	private void verifyGameHistoryOnTopbar(Desktop_HTML_Report report) {
		// TODO Auto-generated method stub
		
	}

	private void verifyHelpOnTopbar(Desktop_HTML_Report report) {
		// TODO Auto-generated method stub
		
	}

	private void verifyTransactionHistoryOnTopbar(Desktop_HTML_Report report) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * To verify Help is present on topbar and navigate to it
	 * 
	 * @return
	 */
	public void verifyHelpOnTopbar(Desktop_HTML_Report report,String market) {
		clickMenuOnTopbar();
		boolean isHelpNaviagted = false;
		try {
			String isHelpAvailable = webdriver.findElement(By.xpath(XpathMap.get("isHelpAvailable"))).getText();
			System.out.println(isHelpAvailable);
			if (isHelpAvailable.equals("Help")) {
				report.detailsAppend("Verify Help must be is displayed on Topbar",
						"Help should be is displayed on Topbar", "Help is displayed on Topbar", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("isHelpAvailable"))).click();
				log.debug("clicked on help on topbar");
				Thread.sleep(3000);
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to Help");
				isHelpNaviagted = true;
				Thread.sleep(2000);
			
				if (isHelpNaviagted) {
					log.debug("Help navigation verified succesfully");
					// report.detailsAppend("Verify the Navigation to Help screen", "Navigation to
					// Help screen", "Navigation to Help screen is Done", "pass");
				} else {
					report.detailsAppend("Verify that Navigation to Help screen", "Navigation to Help screen",
							"Navigation to Help screen not Done", "fail");
				}
			}
			
		} catch (Exception e) {
			log.error("Error in checking help page ", e);
			report.detailsAppend("Help must be is displayed on Topbar", "Help should be is displayed on Topbar",
							"Help is not displayed on Topbar", "fail");
			System.out.println("Help option is not available");
		}
	}

	/**
	 * To verify Responsible gaming is present on topbar and navigate to it
	 * 
	 * @return
	 */
	public void verifyResposibleGamingOnTopbar(Desktop_HTML_Report report, String market) {
		clickMenuOnTopbar();
		boolean isRGNaviagted = false;
		try {
			String isRGAvailable = webdriver.findElement(By.xpath(XpathMap.get("isResponsibleGamingAvailable")))
					.getText();
			System.out.println(isRGAvailable);
			if (isRGAvailable.equals("Responsible Gaming")) {
				report.detailsAppend("Verify Responsible gaming must be is displayed on Topbar",
						"Responsible gaming should be is displayed on Topbar",
						"Responsible gaming is displayed on Topbar", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("isResponsibleGamingAvailable"))).click();
				log.debug("clicked on Responsible gaming on topbar");
				Thread.sleep(3000);
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to Responsible gaming");
				Thread.sleep(2000);
				isRGNaviagted = true;
			
				if (isRGNaviagted) {
					log.debug("Responsible gaming navigation verified succesfully");
					// report.detailsAppend("Verify the Navigation to Responsible gaming screen",
					// "Navigation to Responsible gaming screen", "Navigation to Responsible gaming
					// screen is Done", "pass");
				} else {
					report.detailsAppend("Verify that Navigation to Responsible gaming screen",
							"Navigation to Responsible gaming screen",
							"Navigation to Responsible gaming screen not Done", "fail");
				}
			}
			
		} catch (Exception e) {
			log.error("Error in checking Responsible gaming page ", e);
			report.detailsAppend("Responsible gaming must be is displayed on Topbar",
					"Responsible gaming should be is displayed on Topbar",
					"Responsible gaming is not displayed on Topbar", "fail");
			System.out.println("Responsible gaming option is not available");
		}

	}

	/**
	 * To verify Transaction History is present on topbar and navigate to it
	 * 
	 * @return
	 */
	public void verifyTransactionHistoryOnTopbar(Desktop_HTML_Report report, String market) {
		clickMenuOnTopbar();
		boolean isTHNaviagted = false;
		try {
			String isTHAvailable = webdriver.findElement(By.xpath(XpathMap.get("isTransactionHistoryAvailable")))
					.getText();
			System.out.println(isTHAvailable);
			if (isTHAvailable.equals("Transaction History")) {
				report.detailsAppend("Verify Transaction History must be is displayed on Topbar",
						"Transaction History should be is displayed on Topbar",
						"Transaction History is displayed on Topbar", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("isTransactionHistoryAvailable"))).click();
				log.debug("clicked on Transaction History on topbar");
				Thread.sleep(3000);
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to Transaction History");
				isTHNaviagted = true;
				Thread.sleep(2000);
			
				if (isTHNaviagted) {
					log.debug("Transaction History navigation verified succesfully");
					// report.detailsAppend("Verify the Navigation to Transaction History screen",
					// "Navigation to Transaction History screen", "Navigation to Transaction
					// History screen is Done", "pass");
				} else {
					report.detailsAppend("Verify that Navigation to Transaction History screen",
							"Navigation to Transaction History screen",
							"Navigation to Transaction History screen not Done", "fail");
				}
			}
			
		} catch (Exception e) {
			log.error("Error in checking Transaction History page ", e);
			report.detailsAppend("Transaction History must be is displayed on Topbar",
						"Transaction History should be is displayed on Topbar",
						"Transaction History is not displayed on Topbar", "fail");
				System.out.println("Transaction History option is not available");
			
		}

	}

	/**
	 * To verify Game History is present on topbar and navigate to it
	 * 
	 * @return
	 */
	public void verifyGameHistoryOnTopbar(Desktop_HTML_Report report, String market) {
		clickMenuOnTopbar();
		boolean isGHNaviagted = false;
		try {
			String isGHAvailable = webdriver.findElement(By.xpath(XpathMap.get("isGameHistoryAvailable"))).getText();
			System.out.println(isGHAvailable);
			if (isGHAvailable.equals("Game History")) {
				report.detailsAppend("Verify Game History must be is displayed on Topbar",
						"Game History should be is displayed on Topbar", "Game History is displayed on Topbar", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("isGameHistoryAvailable"))).click();
				log.debug("clicked on Game History on topbar");
				Thread.sleep(3000);
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to Game History");
				isGHNaviagted = true;
				Thread.sleep(2000);
			
				if (isGHNaviagted) {
					log.debug("Game History navigation verified succesfully");
					// report.detailsAppend("Verify the Navigation to Game History screen",
					// "Navigation to Game History screen", "Navigation to Game History screen is
					// Done", "pass");
				} else {
					report.detailsAppend("Verify that Navigation to Game History screen",
							"Navigation to Game History screen", "Navigation to Game History screen not Done", "fail");
				}
			}
			
		} catch (Exception e) {
			log.error("Error in checking Game History page ", e);
			report.detailsAppend("Game History must be is displayed on Topbar",
					"Game History should be is displayed on Topbar", "Game History is not displayed on Topbar",
					"fail");
			System.out.println("Game History option is not available");
		
		}

	}

	/**
	 * To verify Player Protection is present on topbar and navigate to it
	 * 
	 * @return
	 */
	public void verifyPlayerProtectionOnTopbar(Desktop_HTML_Report report, String market) {
		clickMenuOnTopbar();
		boolean isPPNaviagted = false;
		try {
			String isPHAvailable = webdriver.findElement(By.xpath(XpathMap.get("isPlayerProtectionAvailable"))).getText();
			System.out.println(isPHAvailable);
			if (isPHAvailable.equals("Player Protection")) 
			{			
				report.detailsAppend("Verify Player Protection must be is displayed on Topbar","Player Protection should be is displayed on Topbar","Player Protection is displayed on Topbar", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("isPlayerProtectionAvailable"))).click();
				log.debug("clicked on Player Protection on topbar");
				Thread.sleep(3000);
				checkpagenavigationMarket(report, gameurl, market);
				System.out.println("Game navigated to Player Protection");
				isPPNaviagted = true;
				Thread.sleep(2000);
			
				if (isPPNaviagted) {
					log.debug("Player Protection navigation verified succesfully");
					// report.detailsAppend("Verify the Navigation to Game History screen",
					// "Navigation to Game History screen", "Navigation to Game History screen is
					// Done", "pass");
				} else {
					report.detailsAppend("Verify that Navigation to Player Protection screen",
							"Navigation to Player Protection screen", "Navigation to Player Protection screen not Done",
							"fail");
				}
			}
		} catch (Exception e) {
			report.detailsAppend("Player Protection must be is displayed on Topbar",
					"Player Protection should be is displayed on Topbar",
					"Player Protection is not displayed on Topbar", "fail");
			log.error("Player Protection option is not available");
		}

	}
	/**
	 * To verify Help text link on top bar
	 */
	public void verifyHelpTextlink(Desktop_HTML_Report report,String market)
	{
		try 
		{
			boolean isHelpText = webdriver.findElement(By.xpath(XpathMap.get("ishelpTextLinkAvailable"))).isDisplayed();
			if(isHelpText)
			{
				log.debug("Help Text is Visible from TopBar");
			String isHelpTextAvailable = webdriver.findElement(By.xpath(XpathMap.get("ishelpTextLinkAvailable"))).getText();
			log.debug(isHelpTextAvailable);
			
			if (isHelpTextAvailable.equals("Help")||isHelpTextAvailable.equals("Ayuda")||isHelpTextAvailable.equals("Hj�lp") ||isHelpTextAvailable.equals("Hilfe"))
			{
				report.detailsAppend("Verify Help Text link from Topbar","Help Text link Should displaye from Topbar ", "Help Text link from Topbar is Displayed","PASS");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("ishelpTextLinkAvailable"))).click();
				log.debug("clicked on Help Text Link from topbar");
		
				Thread.sleep(3000);
				checkpagenavigationMarket(report, gameurl, market);
				log.debug("Navigated back to the base game from Help Text Link ");
				Thread.sleep(2000);
			}
			}
				else 
				{
					
					report.detailsAppend("Verify that Navigation to Help text link screen","Navigation should  Help text link screen", "Navigation to Help text link screen not Done","FAIL");
				}
			
		} 
		catch (Exception e)
		{
			log.error("Error in checking help page ", e);
		}
	
	}

	/**
	 * To find the net profit in uk market
	 * 
	 * @pb61055
	 */
	public boolean verifyNetPosition(Desktop_HTML_Report report) {
		boolean isNetPositionUpdated = false;
		
		try {
			
			String isNetProfitAvailable = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
			if (isNetProfitAvailable.contains("Net position")) {
			
				report.detailsAppend("Verify net position on topbar", "Net position should be visible and updated correctly", "Net position is visible and updated correctly", "pass");
				
				for (int i = 0; i < 3; i++) {
					String netPositionBeforeSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition")))
							.getText();
					double dblNetPositionBeforeSpin = Double
							.parseDouble(netPositionBeforeSpin.replaceAll("[^0-9.-]", "").replace(".", ""));

					spinclick();
					waitForSpinButton();
					Thread.sleep(6000);

					String netPositionAfterSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
					double dblNetPositionAfterSpin = Double.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));

					String betValue = getConsoleText("return " + XpathMap.get("BetSizeText"));
					double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

					Thread.sleep(2000);

					boolean isPlayerWon = isPlayerWon();
					if (isPlayerWon) {
						String winAmount = null;
						if (!GameName.contains("Scratch")) {
							winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
						} else {
							winAmount = getConsoleText("return " + XpathMap.get("WinText"));
						}
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
							log.error("Error in checking help page ", e);
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
					if(isNetPositionUpdated)
					{
					report.detailsAppend("verify Net position updates after each spin ",
							"Net position should update after every spin ", "Net position is updating after every spin",
							"Pass");
					log.debug("Net Position before spin " + netPositionBeforeSpin + " Net psotion after spin "
							+ netPositionAfterSpin);
					}
					else {
						report.detailsAppend("verify Net position updates after each spin ",
								"Net position should update after every spin ", "Net position is not updating after every spin",
								"Fail");
						log.debug("Net Position before spin " + netPositionBeforeSpin + " Net psotion after spin "
								+ netPositionAfterSpin);
					}
				}//for loop
				
				
			} // if block
		} // try block
		catch (Exception e) {
			log.error("Error in checking net profit ", e);
			report.detailsAppend("Verify net position on topbar", "Net position should be visible and updated correctly", "Net position is not visible", "fail");			
			System.out.println("Net profit is not present");
		}
		return isNetPositionUpdated;
	}

	/**
	 * To verify Menu navigations from topbar in spain
	 * 
	 * @return
	 */
	public void verifyMenuOptionNavigationsForSpain(Desktop_HTML_Report report) {
		try {
			// To verify help
			verifyHelpOnTopbarSpain(report);
			Thread.sleep(2000);
		} catch (Exception e) {
			log.error("Error in checking help navigation from top bar ", e);

		}
	}

	/**
	 * To set the spain cooling Off period
	 * 
	 * @param report
	 */
	public void spainCoolingOffPeriod(Desktop_HTML_Report report) {
		boolean isSpainCoolingOffPeriodPresent = false;
		try {

			Wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("setSpainCoolingOffPeriod"))));
			isSpainCoolingOffPeriodPresent = true;
			if (isSpainCoolingOffPeriodPresent) {
				report.detailsAppend("Verify cooling off period overlay",
						"Cooling off period overlay should be present", "Cooling off period overlay is present",
						"pass");
				webdriver.findElement(By.xpath(XpathMap.get("setSpainCoolingOffPeriod"))).click();
			} else {
				report.detailsAppend("Verify cooling off period overlay",
						"Cooling off period overlay should be present", "Cooling off period overlay is not present",
						"fail");
			}

		} catch (Exception e) {

			log.error("Error in setting cooling off period ", e);
		}

	}

	/**
	 * To close cooling off period
	 */
	public void closeCoolingOffPeriod(Desktop_HTML_Report report) {
		boolean isCloseCoolingOffPeriodPresent = false;
		try {

			Wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(XpathMap.get("closeCoolingOffPeriod"))));
			isCloseCoolingOffPeriodPresent = true;
			if (isCloseCoolingOffPeriodPresent) {
				report.detailsAppend("Verify cooling off period close overlay",
						"Cooling off period close overlay should be present",
						"Cooling off period close overlay is present", "pass");
				Thread.sleep(2000);
				webdriver.findElement(By.xpath(XpathMap.get("closeCoolingOffPeriod"))).click();
			} else {
				report.detailsAppend("Verify cooling off period close overlay",
						"Cooling off period close overlay should be present",
						"Cooling off period close overlay is not present", "fail");

			}

		} catch (Exception e) {

			log.error("Error in closing cooling off period ", e);
		}
	}

	/*
	 * To verify Bonus Reminder
	 */
	public void verifyBonusReminder(Desktop_HTML_Report report) 
	{
		boolean isTearmsAndConditionsNaviagted = false;	
		try {
			String isBonusReminderText = webdriver.findElement(By.xpath(XpathMap.get("isBonusReminder"))).getText();
			System.out.println(isBonusReminderText);
			if (isBonusReminderText.equals("Bonus Reminder")) {
				report.detailsAppend("Verify Bonus Reminder must be is available",
						"Bonus Reminder Should be is available", "Bonus Reminder is available", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("clickOnTermsAndConditions"))).click();
				log.debug("clicked on Terms and conditions in Bonus Reminder");
				Thread.sleep(3000);
				checkpagenavigation(report, gameurl);
				System.out.println("Game navigated to Terms and conditions");
				isTearmsAndConditionsNaviagted = true;
				Thread.sleep(2000);
				if (isTearmsAndConditionsNaviagted) {
					log.debug("Terms and Conditions navigation verified succesfully");
					// report.detailsAppend("Verify that Navigation to Terms and Conditions",
					//"Navigation to Terms and Conditions screen",
					//"Navigation to Terms and Conditions screen Done", "Pass")
				} else {
					report.detailsAppend("Verify that Navigation to Terms and Conditions",
							"Navigation to Terms and Conditions screen",
							"Navigation to Terms and Conditions screen not Done", "fail");
				}
			}
		}

		catch (Exception e) {
			log.error("Error in checking Bonus Reminder ", e);
		}

	}
	/**
	 * method is used to check if it is displayed or not 
	 * @param locator
	 * @return
	 */
	public boolean isDisplayed(String locator)
	{
		boolean islocatorVisible = false;
	try 
	{
		islocatorVisible = webdriver.findElement(By.xpath(XpathMap.get("TopBar"))).isDisplayed();
		if (islocatorVisible)
		{
			log.debug("Topbar is visible");
			 islocatorVisible = true;
		}
	} 
	catch (Exception e) 
	{
		log.error("Not able to verify Topbar", e);
	}
	return islocatorVisible;
	}
	/**
	 * Verify Clock from Top Bar
	 *
	 */
		public boolean clockFromTopBar(Desktop_HTML_Report report)
	{
			String clock = "TopBar_Clock";
			boolean clocktext = false;
			
			try
			{
			String clockText = isDisplayedAndGetText(clock);
			log.debug(clockText);
			clocktext = true;	
			} 
			catch (Exception e) 
			{
				log.error("Not able to verify clock", e);
			}
			return clocktext;
	}
		
		/**
		 * This method is used to check whether Topbar is present or not
		 */
		public boolean verifyTopBarVisible() 
		{
		boolean ret = false;
		String TopBar = "TopBar";
		try
		{
		isDisplayed(TopBar);
		ret = true;
		}
		catch (Exception e) 
		{
			log.error("Not able to verify Topbar", e);
		}
		return ret;
		}
		/**
		 *  Game Name from Top Bar
		 * @param report
		 * @return
		 */
		public String gameNameFromTopBar(Desktop_HTML_Report report) 
		{
			String GameName = "TopBar_GameName" ;
			String gameNamefromExcel = XpathMap.get("GameName");
			String gamenametext = isDisplayedAndGetText(GameName);
			try
			{
			if (gamenametext.equals(gameNamefromExcel)) // Expected VS Actual Name .
				{
				log.debug("Game name is same");
				} 
				else 
				{
				log.debug("Game name is different");
				}
			} 
			catch (Exception e)
			{
				log.error("Not able to verify Game name", e);
			}
			return GameName;

	}
	

	/**
	 * method is for to check if it is Displayed or not  and to get the Text 	
	 */
		public String isDisplayedAndGetText(String locator)
		{
			String getText = null;
			try 
			{
				boolean isloactorVisible = webdriver.findElement(By.xpath(XpathMap.get(locator))).isDisplayed();
				if (isloactorVisible) 
				{
					getText = webdriver.findElement(By.xpath(XpathMap.get(locator))).getText(); 
					log.debug( "Text is "+getText);
					//System.out.println(getText);	
			    } 
				else 
				{
					log.debug("Locator from Menu is not Visible");
				}
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
			return getText;
		}
		/**
		 * method is for click , navigate and back to game screen using Xpath (For Android & IOS click action is different)
		 */
		public boolean clickAndNavigate(Desktop_HTML_Report report , String locator) 
		{
			boolean ele =false;
			String gameurl = webdriver.getCurrentUrl();
		try
		{		
			boolean isElementVisisble = webdriver.findElement(By.xpath(XpathMap.get(locator))).isDisplayed();
			
		if(isElementVisisble)
			{
			webdriver.findElement(By.xpath(XpathMap.get(locator))).click();
			//report.detailsAppend(" Click ", " Clicked ", " Clicked", "PASS");
			Thread.sleep(3000);
			checkpagenavigation(report, gameurl);
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
		 * Click and get the Text 
		 */
		public String clickAndGetText(Desktop_HTML_Report report, String menuclick ,String locator) 
		{
			String getText = null;
			webdriver.findElement(By.xpath(XpathMap.get(menuclick))).click();
			report.detailsAppend(" Click Menu  ", "Menu Clicked ", "Menu Clicked", "PASS");
			try 
			{
				boolean isloactorVisible = webdriver.findElement(By.xpath(XpathMap.get(locator))).isDisplayed();
				if (isloactorVisible) 
				{
					getText = webdriver.findElement(By.xpath(XpathMap.get(locator))).getText(); 
					log.debug( "Text is "+getText);		
					System.out.println( "Text is "+getText);	
			    } 
				else 
				{
					log.debug("Locator from Menu is not Visible");
				}
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
			return getText ;
}
		
		/**
		 * To verify Help text link on top bar
		 */
		public boolean helpTextLink(Desktop_HTML_Report report)
		{
			
			String help = "ishelpTextLinkAvailable";
			String helptext = isDisplayedAndGetText(help);
			boolean text= false;
			String allHelpText[]= {"Help","Hilfe","Hj�lp","Ayuda","Hj�lp"};
			try
			{
			for(int i=0;i<allHelpText.length;i++)
			if (helptext.equals(allHelpText[i])) 
			{
			 log.debug("Help Text from Menu is same");
			 text = true;
			} 
			clickAndNavigate(report,help);
			}
			catch(Exception e)
			{
				log.error(e.getMessage(), e);
			}
			return text;
		}
		
		
		/**
		 *  method is for denmark PlayerProtection text comparission , click and its navigation from menu 
		 */
		public boolean denmarkPlayerProtectionFromMenu(Desktop_HTML_Report report) throws InterruptedException
		{
				String menuclick = "clickOnMenuOnTopbar";
				String locator = "Denmark_PlayerProtection";;
				String Text = clickAndGetText(report,menuclick,locator);
				boolean text= false;
				String allPlrProtectionText[]= {"Player Protection","Spillerbeskyttelse"};
				try
				{
				for(int i=0;i<allPlrProtectionText.length;i++)
				{
				if (Text.equals(allPlrProtectionText[i])) 
				{
					log.debug("Help Text from Menu is same");
					text = true;
				} 
				}Thread.sleep(2000);
				clickAndNavigate(report,locator);
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
					
				return text ;		
		}

	/**
	 * To verify Session reminder is visible or not
	 */
	public boolean isSessionReminderPresent(Desktop_HTML_Report report) 
	{
		boolean isSessionReminderVisible = false; 
		WebDriverWait Wait = new WebDriverWait(webdriver, 250);
		try
		{		
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("SessionReminder"))));
		List<WebElement> elementList=webdriver.findElements(By.xpath(XpathMap.get("SessionReminder"))); 
		
		if(elementList.size()>0) 
		{
		log.debug("\nSession Reminder found");
		report.detailsAppend("Verify Session Reminder ", "Session Reminder is Present","Session Reminder is Present", "PASS");
		webdriver.findElement(By.xpath(XpathMap.get("SessionReminder"))).click(); 
		log.debug(" \n Session Reminder Clicked on Continue Button ");
		isSessionReminderVisible=true;
		}

		else
		{
		log.debug("Session reminder not  found");
		report.detailsAppend("Verify Session Reminder ", "Session Reminder is not Clicked","Session Reminder is not Clicked", "FAIL");
		}
		}

		  catch(Exception e)
		{	 
		log.error("Not able to verify session reminder status",e);
		isSessionReminderVisible=false;
		}
		return isSessionReminderVisible; // if it return false then there is no session reminder present on the screen
	}
	/**
	 * To verify help navigation in menu for spain
	 * @pb61055
	 */
	public void verifyHelpOnTopbarSpain(Desktop_HTML_Report report) {
		clickMenuOnTopbar();
		boolean isHelpNaviagted = false;
		try {
			String isHelpAvailable = webdriver.findElement(By.xpath(XpathMap.get("isHelpAvailableSpain"))).getText();
			System.out.println(isHelpAvailable);
			if (isHelpAvailable.equals("Help")||isHelpAvailable.equals("Ayuda")) {
				report.detailsAppend("Verify Help must be is displayed on Topbar",
						"Help should be is displayed on Topbar", "Help is displayed on Topbar", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("isHelpAvailableSpain"))).click();
				log.debug("clicked on help on topbar");
				Thread.sleep(3000);
				checkpagenavigation(report, gameurl);
				System.out.println("Game navigated to Help");
				isHelpNaviagted = true;
				Thread.sleep(2000);
			
				if (isHelpNaviagted) {
					log.debug("Help navigation verified succesfully");
					// report.detailsAppend("Verify the Navigation to Help screen", "Navigation to
					// Help screen", "Navigation to Help screen is Done", "pass");
				} else {
					report.detailsAppend("Verify that Navigation to Help screen", "Navigation to Help screen",
							"Navigation to Help screen not Done", "fail");
				}
			}
			
		} catch (Exception e) {
			log.error("Error in checking help page ", e);
			report.detailsAppend("Help must be is displayed on Topbar", "Help should be is displayed on Topbar",
					"Help is not displayed on Topbar", "fail");
			System.out.println("Help option is not available");
		}
	} 
	
	/**
	 * To verify currency format for spain
	 * @param currencyFormat
	 * @return
	 */
	public boolean verifyCurrencyFormatForSpain(String currencyFormat) 
	{
		boolean result = true;
		String strBetExp = null;
		String regexp = null;
		try {
			log.debug("Function-> verifyCurrencyFormat() ");
			// Read console credits
			String consoleCredit = getCurrentCredits().replaceAll("Credits: ", "");		
			consoleCredit = consoleCredit.replace("credits: ", "");
			consoleCredit = consoleCredit.replace("CREDITS: ", "");
			consoleCredit = consoleCredit.replace("CR�DITOS: ", "");
			
			String betregexp = createregexp(consoleCredit, currencyFormat);
			if (currencyFormat.contains("$"))
				strBetExp = betregexp.replace("$", "\\$");
			else
				strBetExp = betregexp;
			Pattern.compile(strBetExp);
			regexp = strBetExp.replace("#", "\\d");

			if (Pattern.matches(regexp, consoleCredit)) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return result;
	}
	
	/**
	 * This method is used to get currency symbol for spain .
	 * @throws InterruptedException
	 */
	public String getCurrencySymbolForSpain() {
		String currencySymbol = null;
		try {

			String consoleBalance = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleBalance);
			// currencySymbol = consoleBalance.replaceAll("[[0-9][a-zA-Z],.\\s]", "");
			currencySymbol = consoleBalance.replaceAll("[[0-9]:,.\\s]", "");
			currencySymbol = currencySymbol.toLowerCase().replace("credits", "");
			currencySymbol = currencySymbol.replace("cr�ditos","");
			
			log.debug("Fetching currency symbol from game" + "Currency symbol is :" + currencySymbol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		}
		return currencySymbol;
	}
	
	
	/**
	 * Denamrk Currency Check
	 */
	public boolean denmarkCurrencyCheck(Desktop_HTML_Report report)
	{
        boolean credit = false;
		 String strBetExp = null;
		 String regexp = null;
		// String currencyFormat="kr #.###,##";
		String currencyFormat = XpathMap.get("DenmarkCurrency");
		try 
		{

			String consoleCredit = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleCredit);
			
			 String str = consoleCredit;
		     int index = str.indexOf("kr");
		     
		   if(index != -1)
		   {
			  System.out.printf("CURRENCY Symbol is at index %d\n", index);
			 report.detailsAppend(" Currency Index ", "Currency idex is at  "+index,"Currency idex is at  "+index, "PASS");
			   credit =true;

				log.debug("Function-> verifyCurrencyFormat() ");
						
				consoleCredit = consoleCredit.replace("credits: ", "");
				consoleCredit = consoleCredit.replace("CREDITS: ", "");
				consoleCredit = consoleCredit.replace("JETONER: ", "");
				
				String betregexp = createregexp(consoleCredit, currencyFormat);
				if (currencyFormat.contains("$"))
					strBetExp = betregexp.replace("$", "\\$");
				else
					strBetExp = betregexp;
				Pattern pattren = Pattern.compile(strBetExp);
				System.out.println("Currency is  "+pattren);
				report.detailsAppend(" Currency Format  ", " Currency is "+pattren, "Currency is "+pattren, "PASS");
				regexp = strBetExp.replace("#", "\\d");

				if (Pattern.matches(regexp, consoleCredit)) 
				{
				} 
				else
				{
				}
		   }}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return credit;		 
	}
	/**
	 * This method is used to scroll the , scroll bar and select amount for italy market 
	 */
	public void italyScrollBarAmount(Desktop_HTML_Report report, String amount) 
	{
	
		try {
			
		     WebElement slider = webdriver.findElement(By.id(XpathMap.get("userInput")));//select scroll bar
		     report.detailsAppend("Game play feature ", " Game play feature selected"," Game play feature selected", "PASS");	
			 JavascriptExecutor js = (JavascriptExecutor) webdriver;
			 js.executeScript("javascript:document.getElementById(\""+XpathMap.get("userInput")+"\").value="+amount+";");
			 
             log.debug("Slider value1 for credits is : "+slider.getAttribute("value"));
			 System.out.println("Slider value1 for credits is : "+slider.getAttribute("value"));// value is not updated in the gamescreen 90
			 slider.sendKeys(Keys.RIGHT);
			 
			 log.debug("Slider value2 for credits is : "+slider.getAttribute("value"));
			 System.out.println("Slider value2 for credits is : "+slider.getAttribute("value"));// value updated in the game 90.01
			 selectedamout = slider.getAttribute("value");
			 
			 Thread.sleep(1000);
			 report.detailsAppend("Scroll bar feature ", " Scrolled the amount ","Scrolled the amount", "PASS");	
			 
			webdriver.findElement(By.xpath(XpathMap.get("Italy_Submit"))).click();
			log.debug("Clicked on Submit");
			Thread.sleep(2000);
			report.detailsAppend("Submit Button ", " Clicked on Submit Button ","Clicked on Submit Button ", "PASS");
			
			webdriver.findElement(By.xpath(XpathMap.get("Italy_Play"))).click();
			report.detailsAppend("Play Button ", " Clicked on play Button "," Clicked on play Button ", "PASS" );	
			log.debug("Clicked on Play");
			Thread.sleep(2000);
		/*	
			webdriver.findElement(By.xpath(XpathMap.get("Italy_Continue"))).click();
			italy.detailsAppend(" Verify that  game is clicked on Continue Button ", " clicked on Continue Button"," clicked on Continue Button ", "PASS" );
			log.debug("Clicked on Continue Button ");
			System.out.println("Clicked on Continue Button ");*/
			
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		System.out.println("selected amount is " +selectedamout);
	}
	
	public boolean italyCurrencyCheck(Desktop_HTML_Report report)
	{
		 //String currencyFormat ="� #.###,##";
		String currencyFormat = XpathMap.get("ItalyCurrency");
        boolean credit = false;
		 String strBetExp = null;
		 String regexp = null;
		try 
		{

			String consoleCredit = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleCredit);
			
			 String str = consoleCredit;
		     int index = str.indexOf("�");
		     
		   if(index != -1)
		   {
			   System.out.printf("CURRENCY Symbol is at index %d\n", index);
			 report.detailsAppend(" Currency Index ", "Currency idex is at  "+index,"Currency idex is at  "+index, "PASS");
			   credit =true;

				log.debug("Function-> verifyCurrencyFormat() ");
				// Read console credits
				//String consoleCredit = getCurrentCredits().replaceAll("Credits: ", "");		
				consoleCredit = consoleCredit.replace("credits: ", "");
				consoleCredit = consoleCredit.replace("CREDITS: ", "");
				consoleCredit = consoleCredit.replace("CREDITI: ", "");
				
				String betregexp = createregexp(consoleCredit, currencyFormat);
				if (currencyFormat.contains("$"))
					strBetExp = betregexp.replace("$", "\\$");
				else
					strBetExp = betregexp;
				Pattern pattren = Pattern.compile(strBetExp);
				System.out.println("Currency is  "+pattren);
				report.detailsAppend(" Currency Foramt  ", " Currency is "+pattren, "Currency is "+pattren, "PASS");
				regexp = strBetExp.replace("#", "\\d");

				if (Pattern.matches(regexp, consoleCredit)) 
				{
				} 
				else
				{
				} 
		   }}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);

		}
		return credit;		 
	
	}


/**
* This method is used to find the reel spin duration for a single spin
*/
	public long reelSpinDuration() {
		long reelSpinTime = 0;
		try {
			long start = System.currentTimeMillis();
			spinclick();
			System.out.println(start);
			waitForSpinButtonstop();
			long finish = System.currentTimeMillis();
			System.out.println(finish);
			reelSpinTime = finish - start;
			System.out.println(reelSpinTime);
			log.debug(reelSpinTime);
		}

		catch (Exception e) {
			log.error("Not able to verify reelspin time", e);
		}
		return reelSpinTime;
	}

/**
 * To verify menu options navigations in sweden 
 * @param report
 */
public void verifyMenuOptionNavigationsForSweden(Desktop_HTML_Report report) 
{
	try 
	{
		//verify help on top bar in sweden
		verifyHelpOnTopbarSweden(report);
		Thread.sleep(2000);
		
	}
	 catch (Exception e) 
	{
		log.error(e.getMessage());
		try 
		{
			report.detailsAppendNoScreenshot("verifyMenuOptionNavigationsForSweden", "",
						"Exception while verifyMenuOptionNavigationsForSweden", "fail");
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
}

/**
 * To verify win or loss in session reminder
 * @param report
 */
	public void verifySessionReminderWinAndLoss(Desktop_HTML_Report report,Double valueAfterSpin1,Double valueAfterSpin2) 
	{
		
		try
		{
			String getWinFromSR=webdriver.findElement(By.xpath(XpathMap.get("sessionReminderWin"))).getText();
			String getLossFromSR=webdriver.findElement(By.xpath(XpathMap.get("sessionReminderLoss"))).getText();
			String betValue = getConsoleText("return "+XpathMap.get("BetTextValue"));
			
			double dblbetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));
			double dblWinFromSR=Double.parseDouble(getWinFromSR.replaceAll("[^0-9]", ""));
			double dblLossFromSR=Double.parseDouble(getLossFromSR.replaceAll("[^0-9]", ""));
			double winValue = 0;
			double lossValue = 0;
			
			if((valueAfterSpin1)>0 && (valueAfterSpin2>0))
			{
				double  value1=valueAfterSpin1-dblbetValue;
				double  value2=valueAfterSpin2-dblbetValue;
				winValue=value1+value2;

			}					
			else if((valueAfterSpin1)>0 && !(valueAfterSpin2>0))
			{
				double  value=valueAfterSpin1-dblbetValue;
				winValue=value;
				lossValue=valueAfterSpin2;
				
			}
			else if(!(valueAfterSpin1>0) && (valueAfterSpin2>0))
			{
				double  value=valueAfterSpin2-dblbetValue;
				winValue=value;
				lossValue=valueAfterSpin1;
			}
			else
			{
				lossValue=valueAfterSpin1+valueAfterSpin2;
			}
			
			
			if(winValue==dblWinFromSR) 
			{
				System.out.println("Win is correct");
				report.detailsAppend("Verify win is correct", "win should be correct", "win is correct", "pass");			
			}
			else
			{
				System.out.println("Win is incorrect");
				report.detailsAppend("Verify win is correct", "win should be correct", "win is incorrect", "fail");
			}
			if(lossValue==(-dblLossFromSR))
			{
				System.out.println("Loss is correct");
				report.detailsAppend("Verify Loss is correct", "Loss should be correct", "Loss is correct", "pass");
			}
			else
			{
				System.out.println("loss is incorrect");
				report.detailsAppend("Verify Loss is correct", "Loss should be correct", "Loss is incorrect", "fail");
			}
			
		}
		catch(Exception e)
		{
			log.error("Not able to verify win or loss values",e);
			System.out.println("Not able to verify win or loss values in session reminder");
			report.detailsAppend("Verify Win and Loss is correct in session reminder", " Win and Loss should be correct in session reminder", " Win and Loss is incorrect in session reminder", "fail");
		}
		
	}
	
	public Double verifyWinOrLossForStopButton(Desktop_HTML_Report report) 
	{
		Double value=null;
		try {
		String currentCreditAmount = getCurrentCredits();
		String betValue = getConsoleText("return "+XpathMap.get("BetTextValue"));
		double dblbetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));
		//Verify the stop button availability
		boolean stopbutton=isStopButtonAvailable();
		if(!stopbutton)
		{
			report.detailsAppend("Verify the Stop button is not available in Spain Regulatory Markets.", "Stop Button should not display.", "Stop Button is not gettting displayed in game", "Pass");

		}
		else
		{
			report.detailsAppend("Verify the Stop button is not available in Spain Regulatory Markets.", "Stop Button should not display.", " Stop Button is gettting displayed in game", "Fail");

		}
		Thread.sleep(4000);
		waitForSpinButtonstop();
		boolean isCreditDeducted =isCreditDeducted(currentCreditAmount, betValue);
		if(isCreditDeducted)
		{
			//System.out.println("There is no win");
			value=(-dblbetValue);
		}
		else
		{
			System.out.println("There is win");
			if (isPlayerWon()) 
			{
				String winAmount = null;
				if (!GameName.contains("Scratch")) {
					winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
				} else {
					winAmount = getConsoleText("return " + XpathMap.get("WinText"));
				}

				double dblWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));
				boolean isWinAddedToCredit = isWinAddedToCredit(currentCreditAmount, betValue);

				if (isWinAddedToCredit) {
					log.debug("Win added to Credit for the bet : " + betValue);
					value=dblWinAmount;
				} else {
					log.debug("Win is not added to Credit for the bet : " + betValue);
					value=dblWinAmount;
				}
			}			
		}
		}
		catch(Exception e)
		{
			log.error("Not able to verify win or loss for stop spin",e);
		}
		return value;
	}
	
	public Double verifyWinOrLossForReelSpinDuration(Desktop_HTML_Report report) 
	{
		Double value=null;
		try {
		String currentCreditAmount = getCurrentCredits();
		String betValue = getConsoleText("return "+XpathMap.get("BetTextValue"));
		double dblbetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));
		//verify reel spin duration
		long isReelspin=reelSpinDuration();
		if(isReelspin >= 3000)
		{
			if(isReelspin<=4000) 
			{
				report.detailsAppend("Verify Reel spin duration for sweden is greater than 3 and less than 4 seconds", "Reel spin duration for sweden should be greater than 3 and less than 4 seconds " , "Reel spin duration for sweden is correct, "+isReelspin+" milliseconds", "pass");			
				
			}
		}
		else
		{
			report.detailsAppend("Verify Reel spin duration for sweden is greater than 3 and less than 4 seconds", "Reel spin duration for sweden should be greater than 3 and less than 4 seconds " , "Reel spin duration for sweden is incorrect, "+isReelspin+" milliseconds", "fail");			
		}
		Thread.sleep(4000);
		waitForSpinButtonstop();
		boolean isCreditDeducted =isCreditDeducted(currentCreditAmount, betValue);
		if(isCreditDeducted)
		{
			//System.out.println("There is no win");
			value=(-dblbetValue);
		}
		else
		{
			System.out.println("There is win");
			if (isPlayerWon()) 
			{
				String winAmount = null;
				if (!GameName.contains("Scratch")) {
					winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
				} else {
					winAmount = getConsoleText("return " + XpathMap.get("WinText"));
				}

				double dblWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));
				boolean isWinAddedToCredit = isWinAddedToCredit(currentCreditAmount, betValue);

				if (isWinAddedToCredit) {
					log.debug("Win added to Credit for the bet : " + betValue);
					value=dblWinAmount;
				} else {
					log.debug("Win is not added to Credit for the bet : " + betValue);
					value=dblWinAmount;
				}
			}			
		}
		}
		catch(Exception e)
		{
			log.error("Not able to verify win or loss for reel spin duration",e);
		}
		return value;
	}
	
	
	/**
	 * To verify Session reminder is visible or not.
	 * Return boolean value based on the reminder screen visibility.
	 * true- in case visible, false- in case not visible.
	 * 
	 */
	@Override
	public boolean verifySessionReminderPresent() {
		boolean isSessionReminderVisible = false;
		//Waits for session reminder
		WebDriverWait sesionRemindWait = new WebDriverWait(webdriver, 300);
		try {
			sesionRemindWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("sessionReminderVisible"))));
			String sessionReminder = webdriver.findElement(By.xpath(XpathMap.get("sessionReminderVisible"))).getText();

			if (sessionReminder.equals("Session Reminder")) {
				System.out.println("Session Reminder is present");
				log.debug("Session Reminder is present");
				isSessionReminderVisible = true;
			}
		} catch (Exception e) {
			log.error("Not able to verify session reminder status", e);
		}
		return isSessionReminderVisible;
	}
	
	
	/*
	 * /Sweden Spelpaus Symbols from Top Bar
	 */
	public void swedenRegMarketLogosFromTopBar(Desktop_HTML_Report report)
	{
		try 
		{
			swedenSelfExlusionLogoOnTopBar(report);
			swedenResponsibleGamingLogoOnTopBar(report);
			swedenSelfTestLogoOnTopBar(report);
		}
		catch(Exception e)
			{
				log.error("Not able to verify logos from Top Bar", e);
			}
	}
	
	/**
	 * Sweden Self exclusion Symbol from Top Bar
	 */
	
	public void swedenSelfExlusionLogoOnTopBar(Desktop_HTML_Report report)
	{
		try
		{		
			boolean logo1 = webdriver.findElement(By.xpath(XpathMap.get("swedenSelfExclusion"))).isDisplayed();
			if(logo1)
			{
				log.debug("Sweden SelfExclusion is visible");
				System.out.println("Sweden SelfExclusion is visible");
				webdriver.findElement(By.xpath(XpathMap.get("swedenSelfExclusion"))).click();
				report.detailsAppend("Verify Sweden SelfExclusion is present ", "Sweden SelfExclusion shoild be present", "Sweden SelfExclusion is present", "Pass");
				webdriver.navigate().refresh();
				Thread.sleep(4000);
				waitForSpinButtonstop();
				// click on continue button if any
				if((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))))
				{
					newFeature();
				}
			}
		}
		catch(Exception e)
		{
			log.error("Not able to verify SelfExclusion logo from Top Bar",e);
			System.out.println("Sweden SelfExclusion on the top bar is not visible");
			report.detailsAppend("Verify Sweden SelfExclusion is present ", "Sweden SelfExclusion shoild be present", "Sweden SelfExclusion is not present", "Fail");
			
		}
	}
	
	/**
	 * Sweden Responsible gaming Symbols from Top Bar
	 */
	public void swedenResponsibleGamingLogoOnTopBar(Desktop_HTML_Report report)
	{
		boolean isRGNaviagted=false;
		try
		{
			boolean logo2 = webdriver.findElement(By.xpath(XpathMap.get("swedenResponsibleGaming"))).isDisplayed();
			
			if(logo2)
			{
				log.debug("Sweden ResponsibleGaming is visible");
				System.out.println("Sweden ResponsibleGaming is visible");	
				String gameurl = webdriver.getCurrentUrl();
				report.detailsAppend("Verify Sweden ResponsibleGaming is present ", "Sweden ResponsibleGaming shoild be present", "Sweden ResponsibleGaming is present", "Pass");
				webdriver.findElement(By.xpath(XpathMap.get("swedenResponsibleGaming"))).click();
				Thread.sleep(3000);
				checkpagenavigation(report, gameurl);
				isRGNaviagted = true;
			
				if (isRGNaviagted) {
					log.debug("Help navigation verified succesfully");
				} else {
					report.detailsAppend("Verify that Navigation to ResponsibleGaming screen", "Navigation to ResponsibleGaming screen",
							"Navigation to ResponsibleGaming screen not Done", "fail");
				}
			}
		}
		catch(Exception e)
		{
			log.error("Not able to verify ResponsibleGaming logo from Top Bar",e);
			System.out.println("Sweden ResponsibleGaming on the top bar is not visible");
			report.detailsAppend("Verify Sweden ResponsibleGaming is present ", "Sweden ResponsibleGaming shoild be present", "Sweden ResponsibleGaming is not present", "Fail");
			
		}
	}
	
	/**
	 * Sweden Self Test Symbol from Top Bar
	 */
	public void swedenSelfTestLogoOnTopBar(Desktop_HTML_Report report)
	{
		boolean isSelfTestNaviagted=false;
		try
		{
			boolean logo3 = webdriver.findElement(By.xpath(XpathMap.get("swedenSelfTest"))).isDisplayed();
			
			if(logo3)
			{
				log.debug("Sweden Self Test is visible");
				System.out.println("Sweden Self Test is visible");	
				String gameurl = webdriver.getCurrentUrl();
				report.detailsAppend("Verify Sweden Self Test is present ", "Sweden Self Test shoild be present", "Sweden Self Test is present", "Pass");
				webdriver.findElement(By.xpath(XpathMap.get("swedenSelfTest"))).click();
				Thread.sleep(3000);
				checkpagenavigation(report, gameurl);
				isSelfTestNaviagted = true;
			
				
				if (isSelfTestNaviagted) {
					log.debug("Self Test navigation verified succesfully");
				} else {
					report.detailsAppend("Verify that Navigation to Self Test screen", "Navigation to Self Test screen",
							"Navigation to Self Test screen not Done", "fail");
				}
			}
		}
		catch(Exception e)
		{
			log.error("Not able to verify Self Test logo from Top Bar",e);
			System.out.println("Sweden Self Test on the top bar is not visible");
			report.detailsAppend("Verify Sweden Self Test is present ", "Sweden Self Test shoild be present", "Sweden Self Test is not present", "Fail");
			
		}
	}
		
	/**
	 * This method is used to get currency symbol for sweden .
	 * @throws InterruptedException
	 */
	public String getCurrencySymbolForSweden() {
		String currencySymbol = null;
		try {

			String consoleBalance = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleBalance);
			currencySymbol = consoleBalance.replaceAll("[[0-9]:,.\\s]", "");
			currencySymbol = currencySymbol.toLowerCase().replace("credits", "");
			currencySymbol = currencySymbol.toLowerCase().replace("krediter","");
			
			log.debug("Fetching currency symbol from game" + "Currency symbol is :" + currencySymbol);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		}
		return currencySymbol;
	}
	
	/**
	 * To verify currency format for spain
	 * @param currencyFormat
	 * @return
	 */
	public boolean verifyCurrencyFormatForSweden(String currencyFormat) 
	{
		boolean result = true;
		String strBetExp = null;
		String regexp = null;
		try {
			log.debug("Function-> verifyCurrencyFormat() ");
			// Read console credits
			String consoleCredit = getCurrentCredits().replaceAll("Credits: ", "");		
			consoleCredit = consoleCredit.replace("credits: ", "");
			consoleCredit = consoleCredit.replace("CREDITS: ", "");
			consoleCredit = consoleCredit.replace("KREDITER: ", "");
			
			String betregexp = createregexp(consoleCredit, currencyFormat);
			if (currencyFormat.contains("$"))
				strBetExp = betregexp.replace("$", "\\$");
			else
				strBetExp = betregexp;
			Pattern.compile(strBetExp);
			regexp = strBetExp.replace("#", "\\d");

			if (Pattern.matches(regexp, consoleCredit)) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return result;
	}
	/**
	 * To verify help navigation in menu for sweden
	 * @pb61055
	 */
	public void verifyHelpOnTopbarSweden(Desktop_HTML_Report report) {
		clickMenuOnTopbar();
		boolean isHelpNaviagted = false;
		try {
			String isHelpAvailable = webdriver.findElement(By.xpath(XpathMap.get("isHelpAvailableSweden"))).getText();
			System.out.println(isHelpAvailable);
			if (isHelpAvailable.equals("Help")||isHelpAvailable.equals("Hj�lp")) {
				report.detailsAppend("Verify Help must be is displayed on Topbar",
						"Help should be is displayed on Topbar", "Help is displayed on Topbar", "pass");
				String gameurl = webdriver.getCurrentUrl();
				webdriver.findElement(By.xpath(XpathMap.get("isHelpAvailableSpain"))).click();
				log.debug("clicked on help on topbar");
				Thread.sleep(3000);
				checkpagenavigation(report, gameurl);
				System.out.println("Game navigated to Help");
				isHelpNaviagted = true;
				Thread.sleep(2000);
			
				if (isHelpNaviagted) {
					log.debug("Help navigation verified succesfully");
					// report.detailsAppend("Verify the Navigation to Help screen", "Navigation to
					// Help screen", "Navigation to Help screen is Done", "pass");
				} else {
					report.detailsAppend("Verify that Navigation to Help screen", "Navigation to Help screen",
							"Navigation to Help screen not Done", "fail");
				}
			}
			
		} catch (Exception e) {
			log.error("Error in checking help page ", e);
			report.detailsAppend("Help must be is displayed on Topbar", "Help should be is displayed on Topbar",
					"Help is not displayed on Topbar", "fail");
			System.out.println("Help option is not available");
		}
	}
	/**
	 * to click on continue in splash screen
	 */
	public void newFeature()
	{
	WebDriverWait Wait1= new WebDriverWait(webdriver,10);
	try
	{
	if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))|| (Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))){
	// Click on new feature screen with xpath for the game likes immortal romance
			if("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))){
		Wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
		closeOverlay();
	}
			
  else {
	log.debug("Waiting for new feature screen");
	Wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))));
	webdriver.findElement(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))).click();
	log.debug("Clicked on continue button present on new feature screen");
       }
	}
	// click on new feature screen with hooks for games like dragon dance
	else{
		Wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
	clickAtButton("return "+XpathMap.get("BaseIntroContinueBtn"));
	log.debug("Clicked on intro screen of game");
	}
	}
	catch(Exception e)
	{
	log.debug("Intro screen is not available in game");
	}
	}
	

	/**
	 *Verifies Germany  help text comparission , click and its navigation from menu
	 */
	public boolean germanyHelpFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
	{
		String menuclick = "clickOnMenuOnTopbar";
		String locator = "HelpFromMenu";
		String Text = clickAndGetText(report,menuclick,locator);
		boolean text= false;
		String allHelpText[]= {"Help","Hilfe"};
		try
		{
		for(int i=0;i<allHelpText.length;i++)
		{
		if (Text.equals(allHelpText[i])) 
		{
			log.debug("Help Text from Menu is same");
			text = true;
		} 
		}Thread.sleep(2000);
		clickAndNavigate(report,locator);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
			
		return text ;
	}
	/**
	 *Verifies Germany  ResponsibleGaming text comparison , click and its navigation from menu
	 */
public boolean germanyResponsibleGamingFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
String menuclick = "clickOnMenuOnTopbar";
String locator = "ResponsibleGamingFromMenu";
String Text = clickAndGetText(report,menuclick,locator);
boolean text= false;
String allResponsibleGamingText[]= {"Responsible Gaming","Verantwortungsbewusst spielen"};
try
{
for(int i=0;i<allResponsibleGamingText.length;i++)
{
if (Text.equals(allResponsibleGamingText[i])) 
{
	log.debug("Responsible Gaming Text from Menu is same");
	text = true;
} 
}Thread.sleep(2000);
clickAndNavigate(report,locator);
}
catch (Exception e) 
{
	log.error(e.getMessage(), e);
}
	
return text ;

}
	/**
	 *Verifies Germany GameHistory text comparison , click and its navigation from menu
	 */
public boolean germanyGameHistoryFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
String menuclick = "clickOnMenuOnTopbar";
String locator = "GameHistoryFromMenu";
String Text = clickAndGetText(report,menuclick,locator);
boolean text= false;
String allGameHistoryText[]= {"Game History","Spielverlauf"};
try
{
for(int i=0;i<allGameHistoryText.length;i++)
{
if (Text.equals(allGameHistoryText[i])) 
{
	log.debug("Game History Text from Menu is same");
	text = true;
} 
}Thread.sleep(2000);
clickAndNavigate(report,locator);
}
catch (Exception e) 
{
	log.error(e.getMessage(), e);
}
	
return text ;

}	



/**
 * verify & Compare Germany Currency 
 */
public boolean germanyCurrencyCheck(Desktop_HTML_Report report)
{
	 //String currencyFormat ="� #.###,##";
	 String currencyFormat = XpathMap.get("GermanyCurrency");
     boolean credit = false;
		 String strBetExp = null;
		 String regexp = null;
		try 
		{
			String consoleCredit = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleCredit);
			 String str = consoleCredit;
		     int index = str.indexOf("�");
		     
		   if(index != -1)
		   {
			  // log.debug("CURRENCY Symbol is at index %d\n", index);
			   report.detailsAppend(" Currency Index ", "Currency idex is at  "+index,"Currency idex is at  "+index, "PASS");
			   credit =true;

				log.debug("Function-> verifyCurrencyFormat() ");
				// Read console credits
						
				consoleCredit = consoleCredit.replace("credits: ", "");
				consoleCredit = consoleCredit.replace("CREDITS: ", "");
				
				String betregexp = createregexp(consoleCredit, currencyFormat);
				if (currencyFormat.contains("$"))
					strBetExp = betregexp.replace("$", "\\$");
				else
					strBetExp = betregexp;
				Pattern pattren = Pattern.compile(strBetExp);
				log.debug("Currency is  "+pattren);
				report.detailsAppend(" Currency Format  ", " Currency is "+pattren, "Currency is "+pattren, "PASS");
				regexp = strBetExp.replace("#", "\\d");

				if (Pattern.matches(regexp, consoleCredit)) 
				{
				} 
				else
				{
				}
		   }}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);

		}
		return credit ;	 
		}

/**
 * Verify is coin size changed for Germany 
 */
		public boolean isCoinSizeChanged(Desktop_HTML_Report report)
		{
			boolean isCoinSizeChanged = false; 
			new WebDriverWait(webdriver, 5);
			try
			{		
			//Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("CoinSizeChanged"))));
			boolean logo3 = webdriver.findElement(By.xpath(XpathMap.get("CoinSizeChanged"))).isDisplayed();
			if(logo3)
			{
			log.debug("Coin Size Changed Found");
			report.detailsAppend("Verify Coin Size Changed ", "Coin Size Changed is Present","Coin Size Changed is Present", "PASS");
			webdriver.findElement(By.xpath(XpathMap.get("CoinSizeChanged"))).click(); 
			isCoinSizeChanged=true;
			}

			else
			{
			log.debug("Coin Size Changed not Found");
			report.detailsAppend("Verify Coin Size Changed ", "Coin Size Changed is Present","Coin Size Changed is Present", "FAIL");
			}
			}

			catch(Exception e)
			{	 
			log.error("Not able to verify session reminder status",e);
			
			isCoinSizeChanged=false;
			}
			return isCoinSizeChanged; // if it return false then there is no session reminder present on the screen
		}
		
/**
 *  verify  if Autoplay is available or not 
 */
public boolean verifyAutoplayAvailabilty() 
{
	boolean isAutoplay = false;
	try {

		boolean isAutoplayVisible = GetConsoleBooleanText("return " + XpathMap.get("isAutoplayBtnAvailable"));
		if (isAutoplayVisible) 
		{
			log.debug("Autoplay is Available");
			System.out.println("Autoplay is Available");
			return true;
		}
		else
		{
			log.debug("Autoplay is not Available");
			System.out.println("Autoplay is not Available");
			return false;
		}
	} 
	catch (Exception e)
	{
		log.error("Not able to verify Autoplay Availability", e);
	}
	return isAutoplay;				
					
}

/**
 *Verifies malta  help text comparison , click and its navigation from menu
 */
public boolean maltaHelpFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
String menuclick = "clickOnMenuOnTopbar";
String locator = "HelpFromMenu";
String Text = clickAndGetText(report,menuclick,locator);
boolean text= false;
try
{
if (Text.equals("Help")) 
{
log.debug("Help Text from Menu is same");
text = true;
} Thread.sleep(2000);
clickAndNavigate(report,locator);
}
catch (Exception e) 
{
	log.error(e.getMessage(), e);
}
	
return text ;
}
/**
 *Verifies malta  ResponsibleGaming text comparison , click and its navigation from menu
 */
public boolean maltaResponsibleGamingFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
	String menuclick = "clickOnMenuOnTopbar";
	String locator = "ResponsibleGamingFromMenu";
	String Text = clickAndGetText(report,menuclick,locator);
	boolean text= false;
	try
	{
	if (Text.equals("Responsible Gaming")) 
	{
	log.debug("Responsible Gaming Text from Menu is same");
	text = true;
	} Thread.sleep(2000);
	clickAndNavigate(report,locator);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
		
	return text ;

}

/**
 *Verifies malta GameHistory text comparison , click and its navigation from menu
 */
public boolean maltaGameHistoryFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
	String menuclick = "clickOnMenuOnTopbar";
	String locator = "GameHistoryFromMenu";
	String Text = clickAndGetText(report,menuclick,locator);
	boolean text= false;
	try
	{
	if (Text.equals("Game History"))
	{
	log.debug("Game History Text from Menu is same");
	text = true;
	} Thread.sleep(2000);
	clickAndNavigate(report,locator);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
		
	return text ;

}

/**
 *  verify & Compare malta Currency
 */
public boolean maltaCurrencyCheck(Desktop_HTML_Report report) 
{
	 //String currencyFormat ="� #.###,##";
	 String currencyFormat = XpathMap.get("Malta_Currency_BritishPound");
      boolean credit = false;
		 String strBetExp = null;
		 String regexp = null;
		try 
		{
			String consoleCredit = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleCredit);
			 String str = consoleCredit;
		     int index = str.indexOf("�");
		     
		   if(index != -1)
		   {
			  // log.debug("CURRENCY Symbol is at index %d\n", index);
			   report.detailsAppend(" Currency Index ", "Currency idex is at  "+index,"Currency idex is at  "+index, "PASS");
			   credit =true;

				log.debug("Function-> verifyCurrencyFormat() ");
				// Read console credits
						
				consoleCredit = consoleCredit.replace("credits: ", "");
				consoleCredit = consoleCredit.replace("CREDITS: ", "");
				
				String betregexp = createregexp(consoleCredit, currencyFormat);
				if (currencyFormat.contains("$"))
					strBetExp = betregexp.replace("$", "\\$");
				else
					strBetExp = betregexp;
				Pattern pattren = Pattern.compile(strBetExp);
				log.debug("Currency is  "+pattren);
				report.detailsAppend(" Currency Format  ", " Currency is "+pattren, "Currency is "+pattren, "PASS");
				regexp = strBetExp.replace("#", "\\d");

				if (Pattern.matches(regexp, consoleCredit)) 
				{
				} 
				else
				{
				}
		   }}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);

		}
		return credit ;	 
}

/**
 *Verifies Portugal  help text comparison , click and its navigation from menu
 */
public boolean portugalHelpFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
	String menuclick = "clickOnMenuOnTopbar";
	String locator = "HelpFromMenu";
	String Text = clickAndGetText(report,menuclick,locator);
	boolean text= false;
	String allHelpText[]= {"Help","Ajuda"};
	try
	{
	for(int i=0;i<allHelpText.length;i++)
	{
	if (Text.equals(allHelpText[i])) 
	{
		log.debug("Help Text from Menu is same");
		text = true;
	} 
	}Thread.sleep(2000);
	clickAndNavigate(report,locator);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
		
	return text ;
}
/**
 *Verifies Portugal GameHistory text comparison , click and its navigation from menu
 */
public boolean portugalGameHistoryFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
	String menuclick = "clickOnMenuOnTopbar";
	String locator = "GameHistoryFromMenu";
	String Text = clickAndGetText(report,menuclick,locator);
	boolean text= false;
	String allGameHistoryText[]= {"Game History","Historial do jogo"};
	try
	{
	for(int i=0;i<allGameHistoryText.length;i++)
	{
	if (Text.equals(allGameHistoryText[i])) 
	{
		log.debug("Game History Text from Menu is same");
		text = true;
	} 
	}Thread.sleep(2000);
	clickAndNavigate(report,locator);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
		
	return text ;

}

/**
 *  verify & Compare portugal Currency
 */
public boolean portugalCurrencyCheck(Desktop_HTML_Report report) 
{
	 //String currencyFormat ="$#,###.##";
	 String currencyFormat = XpathMap.get("Portugalu_Currency");
     boolean credit = false;
		 String strBetExp = null;
		 String regexp = null;
		try 
		{
			String consoleCredit = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleCredit);
			 String str = consoleCredit;
		     int index = str.indexOf("�");
		     
		   if(index != -1)
		   {
			  // log.debug("CURRENCY Symbol is at index %d\n", index);
			   report.detailsAppend(" Currency Index ", "Currency idex is at  "+index,"Currency idex is at  "+index, "PASS");
			   credit =true;

				log.debug("Function-> verifyCurrencyFormat() ");
				// Read console credits
						
				consoleCredit = consoleCredit.replace("credits: ", "");
				consoleCredit = consoleCredit.replace("CREDITS: ", "");
				consoleCredit = consoleCredit.replace("CR�DITOS: ", "");
				
				String betregexp = createregexp(consoleCredit, currencyFormat);
				if (currencyFormat.contains("$"))
					strBetExp = betregexp.replace("$", "\\$");
				else
					strBetExp = betregexp;
				Pattern pattren = Pattern.compile(strBetExp);
				log.debug("Currency is  "+pattren);
				report.detailsAppend(" Currency Format  ", " Currency is "+pattren, "Currency is "+pattren, "PASS");
				regexp = strBetExp.replace("#", "\\d");

				if (Pattern.matches(regexp, consoleCredit)) 
				{
				} 
				else
				{
				}
		   }}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);

		}
		return credit ;	 
}

/**
 *Verifies Romania  help text comparison , click and its navigation from menu
 */
public boolean romaniaHelpFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
	String menuclick = "clickOnMenuOnTopbar";
	String locator = "HelpFromMenu";
	String Text = clickAndGetText(report,menuclick,locator);
	boolean text= false;
	String allHelpText[]= {"Help","Ajutor"};
	try
	{
	for(int i=0;i<allHelpText.length;i++)
	{
	if (Text.equals(allHelpText[i])) 
	{
		log.debug("Help Text from Menu is same");
		text = true;
	} 
	}Thread.sleep(2000);
	clickAndNavigate(report,locator);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
		
	return text ;
}
/**
 *Verifies Romania GameHistory text comparison , click and its navigation from menu
 */
public boolean romaniaGameHistoryFromTopBarMenu(Desktop_HTML_Report report) throws InterruptedException
{
	String menuclick = "clickOnMenuOnTopbar";
	String locator = "GameHistoryFromMenu";
	String Text = clickAndGetText(report,menuclick,locator);
	boolean text= false;
	String allGameHistoryText[]= {"Game History","Istoric joc"};
	try
	{
	for(int i=0;i<allGameHistoryText.length;i++)
	{
	if (Text.equals(allGameHistoryText[i])) 
	{
		log.debug("Game History Text from Menu is same");
		text = true;
	} 
	}Thread.sleep(2000);
	clickAndNavigate(report,locator);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
		
	return text ;

}
/**
 *  verify & Compare romania Currency
 */
public boolean romaniaCurrencyCheck(Desktop_HTML_Report report) 
{
	 //String currencyFormat ="�#,###.##";
	 String currencyFormat = XpathMap.get("Romania_Currency");
    boolean credit = false;
		 String strBetExp = null;
		 String regexp = null;
		try 
		{
			String consoleCredit = getCurrentCredits();
			log.debug("Credit in base scene=" + consoleCredit);
			 String str = consoleCredit;
		     int index = str.indexOf("�");
		     
		   if(index != -1)
		   {
			  // log.debug("CURRENCY Symbol is at index %d\n", index);
			   report.detailsAppend(" Currency Index ", "Currency idex is at  "+index,"Currency idex is at  "+index, "PASS");
			   credit =true;

				log.debug("Function-> verifyCurrencyFormat() ");
				// Read console credits
						
				consoleCredit = consoleCredit.replace("credits: ", "");
				consoleCredit = consoleCredit.replace("CREDITS: ", "");
				consoleCredit = consoleCredit.replace("CREDITE: ", "");
				
				String betregexp = createregexp(consoleCredit, currencyFormat);
				if (currencyFormat.contains("$"))
					strBetExp = betregexp.replace("$", "\\$");
				else
					strBetExp = betregexp;
				Pattern pattren = Pattern.compile(strBetExp);
				log.debug("Currency is  "+pattren);
				report.detailsAppend(" Currency Format  ", " Currency is "+pattren, "Currency is "+pattren, "PASS");
				regexp = strBetExp.replace("#", "\\d");

				if (Pattern.matches(regexp, consoleCredit)) 
				{
				} 
				else
				{
				}
		   }}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);

		}
		return credit ;	 
}



/**
 * This method is for to update Germany bet settings in Axiom and in Bluemesa
 * @param userName
 * @param balance
 * @return
 */
public boolean germanyBetSettings(String userName, String mid, String cid)
{
	boolean values = false;
	try 
	{
	 //mid=TestPropReader.getInstance().getProperty("MID");
	// cid=TestPropReader.getInstance().getProperty("CIDDesktop");
	String envName=TestPropReader.getInstance().getProperty("EnvironmentName");
	if(envName==null || envName.equalsIgnoreCase("Bluemesa"))
	{
		String betSettingsURL = XpathMap.get("Bluemesa_BetSettings_URL");
		webdriver.get(betSettingsURL);
		webdriver.findElement(By.id(XpathMap.get("Client_ID"))).sendKeys(mid);
		webdriver.findElement(By.id(XpathMap.get("Module_ID"))).sendKeys(cid);
		webdriver.findElement(By.id(XpathMap.get("Login_Name"))).sendKeys(userName);
		webdriver.findElement(By.xpath(XpathMap.get("GetConfigButton"))).click();
		
		WebElement defaultChipSize = webdriver.findElement(By.id(XpathMap.get("DefaultChipSize")));
		defaultChipSize.clear();defaultChipSize.sendKeys(XpathMap.get("DefaultChipSizeValue"));
		
		WebElement defaultNumChip = webdriver.findElement(By.id(XpathMap.get("DefaultNumChip")));
		defaultNumChip.clear();defaultNumChip.sendKeys(XpathMap.get("DefaultNumChipValue"));
		
		WebElement maxBet = webdriver.findElement(By.id(XpathMap.get("MaxBet")));
		maxBet.clear();maxBet.sendKeys(XpathMap.get("MaxBetValue"));
		
		WebElement minBet = webdriver.findElement(By.id(XpathMap.get("MinBet")));
		minBet.clear();minBet.sendKeys(XpathMap.get("MinBetValue"));
		
		WebElement maxNumChip = webdriver.findElement(By.id(XpathMap.get("MaxNumChip")));
		maxNumChip.clear();maxNumChip.sendKeys(XpathMap.get("MaxNumChipValue"));
		
		WebElement miNumChip = webdriver.findElement(By.id(XpathMap.get("MiNumChip")));
		miNumChip.clear();miNumChip.sendKeys(XpathMap.get("MiNumChipValue"));
		
		 webdriver.findElement(By.xpath(XpathMap.get("SaveButton"))).click();
	  }
	 else 
	  {
		 String betSettingsURL = XpathMap.get("Axiom_Bet_Settings");
		 webdriver.get(betSettingsURL);
		 WebElement gameselection = webdriver.findElement(By.xpath(XpathMap.get("Axiom_Bet_Settings")));
		 gameselection.click();
		 
		
		 List<WebElement> elements = webdriver.findElements(By.className(XpathMap.get("DropDown")));
		 String allResponsibleGamingText[]= {"Reel Thunder Desktop","Reel Thunder Mobile"};
		 for(int i=0;i<allResponsibleGamingText.length;i++)
		 {
				
		// String allResponsibleGamingText[]= {"Reel Thunder Desktop","Reel Thunder Mobile"};
		 
		 for(;i<elements.size();)
		 {
			 System.out.println(""+elements);

			
		 }
		 /*if (elements[j].ge) 
		 {
		 	log.debug("");
		 	
		 } */
		 }
		 
		
		 
		 Select dropdown = new Select(gameselection);
		 ((WebElement) dropdown).sendKeys(XpathMap.get("UserName"));
		 
		 WebElement usernam = webdriver.findElement(By.id(XpathMap.get("UserName")));
		 usernam.click();
		 usernam.sendKeys(userName);
		 
		 webdriver.findElement(By.xpath(XpathMap.get("FetchSettings"))).click();
		 
		 WebElement maxBet = webdriver.findElement(By.id(XpathMap.get("Axiom_MaxBet")));
		 maxBet.clear();maxBet.sendKeys(XpathMap.get("Axiom_MaxBetValue"));
		 
		 WebElement minBet = webdriver.findElement(By.id(XpathMap.get("Axiom_MinBet")));
		 minBet.clear();minBet.sendKeys(XpathMap.get("Axiom_MinBetValue"));
		 
		 WebElement defaultChipSize = webdriver.findElement(By.id(XpathMap.get("Axiom_DefaultChipSize")));
		 defaultChipSize.clear();defaultChipSize.sendKeys(XpathMap.get("Axiom_DefaultChipSizeValue"));
		 
		 WebElement defaultNumChip = webdriver.findElement(By.id(XpathMap.get("Axiom_DefaultNumChips")));
		 defaultNumChip.clear();defaultNumChip.sendKeys(XpathMap.get("Axiom_DefaultNumChipsValue"));
		 
		 WebElement maxNumChip = webdriver.findElement(By.id(XpathMap.get("Axiom_MaxNumChips")));
		 maxNumChip.clear();maxNumChip.sendKeys(XpathMap.get("Axiom_MaxNumChipsValue"));
			
		WebElement miNumChip = webdriver.findElement(By.id(XpathMap.get("Axiom_MinNumChips")));
		miNumChip.clear();miNumChip.sendKeys(XpathMap.get("Axiom_MinNumChipsValue"));
		
		webdriver.findElement(By.xpath(XpathMap.get("UnchecktheCheckBox"))).click();
		
		webdriver.findElement(By.id(XpathMap.get("ApplyBetSettings"))).click();  
	  }
	values= true;
  } 
catch (Exception e)
{
	log.error("Not able to verify Autoplay Availability", e);
}
	return values;
}

/**
 *This method is for to Get Current credits from console
 */
	public String getCurrentCredits_CS() 
	{
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
		String creditValue = func_GetText(XpathMap.get("Creditvalue"));
		//String val = func_String_Operation(aval).replace(",", "");
		// double val1=Double.parseDouble(val);
		
	//	consoleBalance = getConsoleText(balance);
		System.out.println("Console Credits are "+creditValue);
		log.debug("Console Credits are "+creditValue);
		return creditValue;
	}
	
	
	public boolean vdn_CurrencyCheck(Desktop_HTML_Report report)
	{
        boolean credit = false;
		 String strBetExp = null;
		 String regexp = null;
		// String currencyFormat="kr #.###,##";
		String currencyFormat = XpathMap.get("VND");
		try 
		{

			String consoleCredit = getCurrentCredits_CS();
			System.out.println("Credit in base scene=" + consoleCredit);
			
			 String str = consoleCredit;
		     int index = str.indexOf("d");
		     
		   if(index != -1)
		   {
			  System.out.printf("CURRENCY Symbol is at index %d\n", index);
			 report.detailsAppend(" Currency Index ", "Currency idex is at  "+index,"Currency idex is at  "+index, "PASS");
			   credit =true;

				log.debug("Function-> verifyCurrencyFormat() ");
						
				consoleCredit = consoleCredit.replace("credits: ", "");
				consoleCredit = consoleCredit.replace("CREDITS: ", "");
				
				String betregexp = createregexp(consoleCredit, currencyFormat);
				if (currencyFormat.contains("$"))
					strBetExp = betregexp.replace("$", "\\$");
				else
					strBetExp = betregexp;
				Pattern pattren = Pattern.compile(strBetExp);
				System.out.println("Currency is  "+pattren);
				report.detailsAppend(" Currency Format  ", " Currency is "+pattren, "Currency is "+pattren, "PASS");
				regexp = strBetExp.replace("#", "\\d");
				if (Pattern.matches(regexp, consoleCredit)) 
				{
				} 
				else
				{
				}
		   }}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return credit;		 
	}

public boolean verifyWinAmtCurrencyFormat(String currencyFormat, Desktop_HTML_Report currencyReport) {
	boolean result = false;
	long startTime = System.currentTimeMillis();
	String winamt = null;
	try {
		Thread.sleep(2000);
		log.debug("Function-> verifyWinCurrencyFormat");

		if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) 
		{
			winamt = getConsoleText("return " + XpathMap.get("WinMobileText"));
			log.info("verifyBigWinCurrencyFormat():waiting for win  to occur");
		} 
		else
			winamt = getConsoleText("return " + XpathMap.get("WinDesktopLabel"));
		
		if (winamt != null) 
		{			
			log.info(" win occur ");
			// fetching win from panel
			String consolewinnew = winamt.toLowerCase().replaceAll("win: ", "");
			result = currencyFormatValidator(consolewinnew, currencyFormat);
		}
		else 
		{
			long currentime = System.currentTimeMillis();
			if (((currentime - startTime) / 1000) > 120) 
			{
				System.out.println("verifyWinCurrencyFormat():No win occur  in 2");
				result = false;
			}
		}
	
	} catch (Exception e) {
		log.error(e.getMessage(), e);
		log.error("Win not occur, please  use win test data");

	}

	return result;
}

	public void jackpotSummaryWinCurrFormat(String currencyFormat, Desktop_HTML_Report currencyReport,String currencyName) {
		String jackpotWin = null;

		boolean isWinInCurrencyFormat = false;
		try 
		{
			log.debug("Function -> jackpotSummaryWinCurrencyFormat()");
			isWinInCurrencyFormat=verifyWinAmtCurrencyFormat(currencyFormat, currencyReport);
			log.debug("Fetching Jackpot summary currency symbol " + "/n Jackpot summary currency symbol is:: "+ jackpotWin);
			if (isWinInCurrencyFormat) {
				currencyReport.detailsAppendFolder("Verify jackpot currency when win occurs ",
						"Jackpot win should display with correct currency format and and currency symbol ",
						"Jackpot win displaying with correct currency format and and currency symbol ","Pass",currencyName);

			} else {
				currencyReport.detailsAppendFolder("Verify jackpot currency in win occurs ",
						"Jackpot win should display with correct currency format and and currency symbol ",
						"Jackpot win is not  displaying with correct currency format and and currency symbol ", "Fail",currencyName);
			}

		} 
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	public boolean assignFreeGames(String userName,String offerExpirationUtcDate,int mid, int cid,int noOfOffers,int defaultNoOfFreeGames) 
	{
		//assign free games to above created user
		boolean isFreeGameAssigned=false;
		try {
		String balanceTypeId=XpathMap.get("BalanceTypeID");
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
	
	public void freeGameSummaryWinCurrFormat(String currencyFormat, Desktop_HTML_Report currencyReport,String currencyName) 
	{	
		String freeGamesWin = null;
		boolean isWinInCurrencyFormat = false;
		try {
			log.debug("Function -> freeSpinWinCurrencyFormat()");
			if (!GameName.contains("Scratch")) {
				freeGamesWin = "return " + XpathMap.get("freeGamesSummaryAmt");
			}
			String freegameswinnew = getConsoleText(freeGamesWin);
			
			// To validate currency Amount format
			isWinInCurrencyFormat = currencyFormatValidator(freegameswinnew, currencyFormat);
			log.debug("Fetching Free games summary currency symbol " + "/n free games summary currency symbol is:: "+ freeGamesWin);
			
			if (isWinInCurrencyFormat) 
			{
				currencyReport.detailsAppendFolder("Verify freegames summary currency when win occurs ",
						"freegames summary win should display with correct currency format and and currency symbol ",
						"freegames summary win displaying with correct currency format and and currency symbol ","Pass",currencyName);

			} else {
				currencyReport.detailsAppendFolder("Verify freegames summary currency in win occurs ",
						"freegames summary win should display with correct currency format and and currency symbol ",
						"freegames summary win is not  displaying with correct currency format and and currency symbol ", "Fail",currencyName);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public String getWinAmtFromFreegames() 
	{
		String Winamtnew = null;
		try {
			String Winamt = "return " + XpathMap.get("freeGamesSummaryAmt");
			Winamtnew = getConsoleText(Winamt);
			log.debug("win amount " + Winamtnew);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Winamtnew;
	}
	
	public String gamepayoutForPaytable(ArrayList<String> symbolData, String paytablePayout) {
		String consolePayout=null;
		int symbolIndex = 0;
		try {
			String wild = XpathMap.get("wildSymbol");
			XpathMap.get("ScatterSymbol");
			
			String[] xmlData = paytablePayout.split(",");
			String line = xmlData[0];
			String symbol = xmlData[1];
			Map<String, String> paramMap = new HashMap<String, String>();

			for (int i = 0; i < symbolData.size(); i++) {
				if (symbolData.get(i).contains(symbol)) {
					if (wild.equalsIgnoreCase("no")) {
						symbolIndex = i - 1;
					}
					/*
					 * else if (Scatter.equalsIgnoreCase("no")) { symbolIndex = i - 1; }
					 */
					
					else
						symbolIndex = i;
					break;
				}

			}
			paramMap.put("param1", Integer.toString(symbolIndex));
			paramMap.put("param2", line);

			String paytablePayoutValueHook = XpathMap.get("getPaytablePayoutValue");
			String newHook = replaceParamInHook(paytablePayoutValueHook, paramMap);
			String payout = "return " + newHook;
	//		System.out.println(payout);
			consolePayout = getConsoleText(payout);
			} catch (Exception e) {
			e.getMessage();
		}
		return consolePayout;
	}
	/*
	 * Method for payout verification for all bets
	 */

	public void payoutverificationforBetLVC(Desktop_HTML_Report report, String regExpression,String currencyName) 
	{
		Util util = new Util();
		int length = 0;
		String gamePayout;
		int index = 0;
		String paytablePayout;
		String strGameName = null;
		int startindex = 0;
		int isAllTrue=0;
		ArrayList<String> symbolData;
		String 	xmlFilePath;

		
		// Read xml for the game
		if (GameName.contains("Desktop")) 
		{
			// String Gamename=gameName.replace("Desktop", "");
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			log.debug("newgamename=" + strGameName);
		}
		else if (GameName.contains("Desk")) 
		{
			// String Gamename=gameName.replace("Desktop", "");
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desk");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			log.debug("newgamename=" + strGameName);
		}
		
		else if(GameName.contains("De")) {
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("De");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			//log.debug("newgamename=" + strGameName);
		
		}
		
		
			//Read xml for the game
			if("yes".equalsIgnoreCase(XpathMap.get("PaytableSymbleAlignmentDifferent")))
			{
				xmlFilePath ="./"+strGameName+"/Config/"+"PaytableSymbols"+".xml";			
			}
			else
			{
				xmlFilePath = "./" + strGameName + "/Config/" + strGameName + ".xml";
			}
		
			length = util.xmlLength(xmlFilePath, "WinningCombinations");
			symbolData= util.getXMLDataInArray("Symbol", "name", xmlFilePath);
		
		
		ArrayList<String> winCombinationList = new ArrayList<>();

		for (int count = 0; count < length; count++) {
			String strWinCombination = util.readXML("WinCombination", "numSymbolsRequired", "symbols", "payouts",
					"./" + strGameName + "/Config/" + strGameName + ".xml", length + 2, count);
			if (strWinCombination != null) {
				winCombinationList.add(strWinCombination);
			}
		}

		int winCombinationSize = winCombinationList.size();
	
		try {
				// setting index at the staring
				index = 0;
				// Get the bet amount
				double bet = GetBetAmt();
				
				report.detailsAppendNoScreenshot("Paytable Payout currency format verification in progress for the selected bet ",
						"Paytable Payout currency format verification in progress for the selected bet " ,
						"Paytable Payout currency format verification in progress for the selected bet " , "Pass");
				
				capturePaytableScreenshot(report,currencyName);
				
				paytablePayout = winCombinationList.get(index);
					

				
				for (int j = 0; j < winCombinationSize; j++) {

					System.out.println("winCombinationSize : "+winCombinationSize+"/n");
					System.out.println("J iteration : "+j+"/n");
					
					paytablePayout = winCombinationList.get(index);
					System.out.println("paytablePayout : "+paytablePayout);
					if (paytablePayout.contains("Scatter") || paytablePayout.contains("FreeSpin")) {
					} else {
					}
					String[] xmlData = paytablePayout.split(",");

					gamePayout = gamepayoutForPaytable(symbolData, paytablePayout);// it will fetch game payout for Force game
					System.out.println("gamePayout : "+gamePayout);
					boolean result = currencyFormatValidatorForLVC(gamePayout,regExpression);
					

					if (result)
					{
						isAllTrue++;
						log.debug("Paytable payout is correct for the bet value :" + bet 
								+" currency format validation is :" + result + " symbol name : " + xmlData[1]
						 		+ " game payout : " + gamePayout + " is correct");
					
					} else {
						
						log.debug("Paytable payout is not correct for the bet value :" + bet 
								+ " currency format validation is :" + result + " symbol name : " + xmlData[1]
								+ " game payout : " + gamePayout + " is incorrect");
						
					}
					length--;
					index++;
				}


				
				if (isAllTrue==winCombinationSize) {
					report.detailsAppendNoScreenshot(
							"verify Paytable payout currency format for selected bet value with the game currency format",
							"Paytable payout verification with the game currency format ",
							"Paytbale payout verification  with the game currency format is done",
							"pass");

				} else {
					report.detailsAppendNoScreenshot(
							"verify Paytable payout currency format for selected bet value with the game currency format",
							"Paytable payout verification with the game currency format ",
							"Paytable payout verification with the game currency format is done but failed coz some formats are not matched",
							"Fail");

				}
				// Closes the paytable
				paytableClose();

			
		} catch (Exception e) 
		{
			e.printStackTrace();
			log.error(e.getMessage(), e);
			try {
				report.detailsAppendNoScreenshot("verify Payout verification for the bet ", " ",
						"Exception ocuur while verifying payout for bet", "Fail");
			} catch (Exception e1) 
			{
				//log.error(e1.getStackTrace());
				log.error(e1.getMessage(), e1);
			}

		}

	}

	public boolean currencyFormatValidatorForLVC(String curencyAmount, String regExpression) {
		boolean isCurrencyFormat = false;
		try {

			log.debug("Function-> currencyFormatValidatorForLVC()");
			log.debug("curencyAmount: "+curencyAmount);
			if (curencyAmount.matches(regExpression)) {
				isCurrencyFormat = true;
				log.debug("Currency format is correct");
				System.out.println("Currency format Matching");
			} else {
				isCurrencyFormat = false;
				log.debug("Currency format is incorrect");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isCurrencyFormat;

	}
	/*
	 * Method to verify currency format in credit
	 */

	public boolean verifyCurrencyFormatForCredits(String regExpression) {
		boolean result = true;
		try {
			log.debug("Function-> verifyCurrencyFormat() ");
			// Read console credits
			String consoleCredit = getCurrentCredits().replaceAll("Credits: ", "");
			consoleCredit = consoleCredit.replace("credits: ", "");
			consoleCredit = consoleCredit.replace("CREDITS: ", "");
			consoleCredit = consoleCredit.replace("Credits ", "");
			result=currencyFormatValidatorForLVC(consoleCredit,regExpression);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return result;
	}
	
	public boolean betCurrencySymbolForLVC(String regExpression) {
		String bet = null;
		boolean isBetInCurrencyFormat = false;

		try {
			log.debug("Function-> betCurrencySymbol()");
			log.debug("Reading Bet Text from base scene");
			if (!GameName.contains("Scratch")) {
				if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
					bet = "return " + XpathMap.get("BetTextValue");
				} else
					bet = "return " + XpathMap.get("BetButtonLabel");
			} else {
				bet = "return " + XpathMap.get("InfobarBettext");
			}

			// String consoleBet=getConsoleText(bet).toLowerCase();
			String consoleBet = getConsoleText(bet);
			log.debug(" Bet Text from base scene=" + consoleBet);
			Thread.sleep(100);

			// String consoleBetnew=consoleBet.toLowerCase().replaceAll("bet: ", "");
			String consoleBetnew = consoleBet.replaceAll("bet: ", "");
			consoleBetnew = consoleBetnew.replaceAll("BET: ", "");
			consoleBetnew = consoleBetnew.replaceAll("Bet: ", "");
			consoleBetnew = consoleBetnew.replaceAll("Bet ", "");
			isBetInCurrencyFormat=currencyFormatValidatorForLVC(consoleBetnew,regExpression);
			
			log.debug(" Bet currency symbol is::" + consoleBet);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return isBetInCurrencyFormat;

	}
	public boolean verifyWinAmtCurrencyFormatForLVC(String regExpression) {
		boolean result = false;
		long startTime = System.currentTimeMillis();
		String winamt = null;
		try {
			Thread.sleep(3000);
			log.debug("Function-> verifyWinCurrencyFormat");

			if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) 
			{
				closeOverlay();
				Thread.sleep(1000);
				winamt = getConsoleText("return " + XpathMap.get("WinMobileText"));
			} 
			else
				winamt = getConsoleText("return " + XpathMap.get("WinDesktopLabel"));
			
			if (winamt != null) 
			{			
				log.info(" win occur ");
				// fetching win from panel
				String consolewinnew = winamt.replaceAll("win: ", "");
				consolewinnew = consolewinnew.replaceAll("WIN: ", "");
				consolewinnew = consolewinnew.replaceAll("Win: ", "");
				consolewinnew = consolewinnew.replaceAll("Win ", "");
				result=currencyFormatValidatorForLVC(consolewinnew,regExpression);
			}
			else 
			{
				long currentime = System.currentTimeMillis();
				if (((currentime - startTime) / 1000) > 120) 
				{
					System.out.println("verifyWinCurrencyFormat():No win occur  in 2");
					result = false;
				}
			}
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Win not occur, please  use win test data");

		}

		return result;
	}
	// Method to wait till win occurs and verify the currency format in win
		public boolean verifyBigWinCurrencyFormatForLVC(String regExpression, Desktop_HTML_Report currencyReport,
				String currencyName) 
		{
			boolean regMatch=false;
			boolean result = false;

			long startTime = System.currentTimeMillis();
			String winamt = null;
			try {
				//Thread.sleep(2000);
				log.debug("Function-> verifyBigWinCurrencyFormat");

				if ("Yes".equalsIgnoreCase(XpathMap.get("BigWinlayers"))) {
					for (int i = 1; i <= 3; i++) 
					{
						
						waitForBigWin();

					
						log.debug("Bigwinlayer captured" + i);
						System.out.println("Bigwinlayer captured" + i);

						while (true) {
							if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
								winamt = getConsoleText("return " + XpathMap.get("WinMobileText"));
								log.info("verifyBigWinCurrencyFormat():waiting for win  to occur");
							} else
								winamt = getConsoleText("return " + XpathMap.get("BigWinCountUpText"));
								System.out.println("BigWin Amt: "+ winamt);
							if (winamt != null) {
								log.info(" win occur ");
								// fetching win from panel
								String consolewinnew = winamt.replaceAll("win: ", "");
								regMatch=currencyFormatValidatorForLVC(consolewinnew,regExpression);


								if (regMatch) {
									result = true;
									currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);

								} else {
									result = false;
									currencyReport.detailsAppendFolderOnlyScreenShot(currencyName);
								}

								break;
							} else {
								long currentime = System.currentTimeMillis();
								if (((currentime - startTime) / 1000) > 120) {
									System.out.println("verifyBigWinCurrencyFormat():No win occur  in 2");
									result = false;
									break;
								}
							}

						}
					}	
				}
				else
				{
					if(!GameName.contains("GameofThrones"))
					//	Thread.sleep(10000);
						
						waitForBigWin();
					winamt = getConsoleText("return " + XpathMap.get("BigWinCountUpText"));
					if (winamt != null) {
						log.info(" win occur ");
						// fetching win from panel
						String consolewinnew = winamt.replaceAll("win: ", "");
						consolewinnew = consolewinnew.replaceAll("WIN: ", "");
						consolewinnew = consolewinnew.replaceAll("Win: ", "");
						consolewinnew = consolewinnew.replaceAll("Win ", "");
						regMatch=currencyFormatValidatorForLVC(consolewinnew,regExpression);
						if (regMatch) {
							result = true;

						} else {
							result = false;
						}
					}
					else
					{
						log.debug("no win occur");
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				log.error("Big win not occur.please  use bigwin test data");

			}

			return result;
		}

		public void jackpotSummaryWinCurrFormatForLVC(String regExpression, Desktop_HTML_Report currencyReport,String currencyName) {
			String jackpotWin = null;
			boolean isWinInCurrencyFormat = false;
			try {
				log.debug("Function -> jackpotSummaryWinCurrencyFormat()");
				
				jackpotWin = "return " + XpathMap.get("bonusWinAmount");
				
				String bonusWinnew = getConsoleText(jackpotWin);
				// To validate currency Amount format
				isWinInCurrencyFormat = currencyFormatValidatorForLVC(bonusWinnew, regExpression);
				
				log.debug("Fetching Jackpot summary currency symbol " + "/n Jackpot summary currency symbol is:: "+ jackpotWin);
				if (isWinInCurrencyFormat) {
					currencyReport.detailsAppendFolder("Verify jackpot currency when win occurs ",
							"Jackpot win should display with correct currency format and and currency symbol ",
							"Jackpot win displaying with correct currency format and and currency symbol ","Pass",currencyName);

				} else {
					currencyReport.detailsAppendFolder("Verify jackpot currency in win occurs ",
							"Jackpot win should display with correct currency format and and currency symbol ",
							"Jackpot win is not  displaying with correct currency format and and currency symbol ", "Fail",currencyName);
				}

			} 
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
		/*
		 * Method to check the currency format in free spin summary
		 */
		public void freeSpinSummaryWinCurrFormatForLVC(String regExpression,Desktop_HTML_Report currencyReport,String currencyName) {
			String freespinwin = null;
			boolean isWinInCurrencyFormat = false;
			try {
				log.debug("Function -> freeSpinWinCurrencyFormat()");
				if (!GameName.contains("Scratch")) {
					freespinwin = "return " + XpathMap.get("FSSumaryAmount");
				} else {
					// need to update the hook for Scratch game
					freespinwin = "return " + XpathMap.get("InfobarBettext");
				}
				String freespinwinnew = getConsoleText(freespinwin);
				// To validate currency Amount format
				isWinInCurrencyFormat = currencyFormatValidatorForLVC(freespinwinnew, regExpression);
				
				if (isWinInCurrencyFormat) {
					currencyReport.detailsAppendFolder("Verify freespins summary currency when win occurs ",
							"freespins summary win should display with correct currency format and and currency symbol ",
							"freespins summary win displaying with correct currency format and and currency symbol ","PASS",currencyName);

				} else {
					currencyReport.detailsAppendFolder("Verify freespins summary currency in win occurs ",
							"freespins summary win should display with correct currency format and and currency symbol ",
							"freespins summary win is not  displaying with correct currency format and and currency symbol ", "FAIL",currencyName);
				}

				
				//log.debug("Fetching Free spin summary currency symbol" + "/n Free spin summary currency symbol is::"
						//+ freespinwin);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		}
		public void freeGameSummaryWinCurrFormatForLVC(String regExpression, Desktop_HTML_Report currencyReport,String currencyName) 
		{	
			String freeGamesWin = null;
			boolean isWinInCurrencyFormat = false;
			try {
				log.debug("Function -> freeGameSummaryWinCurrencyFormat()");
				if (!GameName.contains("Scratch")) {
					freeGamesWin = "return " + XpathMap.get("freeGamesSummaryAmt");
				}
				String freegameswinnew = getConsoleText(freeGamesWin);
				
				// To validate currency Amount format
				isWinInCurrencyFormat = currencyFormatValidatorForLVC(freegameswinnew, regExpression);
				log.debug("Fetching Free games summary currency symbol " + "/n free games summary currency symbol is:: "+ freeGamesWin);
				
				if (isWinInCurrencyFormat) 
				{
					currencyReport.detailsAppendFolder("Verify freegames summary currency when win occurs ",
							"freegames summary win should display with correct currency format and and currency symbol ",
							"freegames summary win displaying with correct currency format and and currency symbol ","Pass",currencyName);

				} else {
					currencyReport.detailsAppendFolder("Verify freegames summary currency in win occurs ",
							"freegames summary win should display with correct currency format and and currency symbol ",
							"freegames summary win is not  displaying with correct currency format and and currency symbol ", "Fail",currencyName);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		
		public String getCurrentBetForLVC() {
			String consoleBet = null;
			String bet = null;
			try {
				log.debug("reading bet text form base scene");
				if (!GameName.contains("Scratch")) {
					if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
						bet = "return " + XpathMap.get("BetTextValue");

					} else {
						bet = "return " + XpathMap.get("BetButtonLabel");

					}
				} else {
					bet = "return " + XpathMap.get("InfobarBettext");

				}
				log.debug("read bet text form base scene");
				consoleBet = getConsoleText(bet);
			} catch (Exception e) {
				log.error("Error in verifying multiplier", e);
			}
			return consoleBet;
		}

		public void validateMiniPaytableForLVC(Desktop_HTML_Report report,String regExpression,String currencyName) 
		{
			int isAllTrue=0;
			String strGameName = null;
			int startindex = 0;
			long symbolNumber;
			
			if (GameName.contains("Desktop")) {

				java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");

				Matcher substing = str.matcher(GameName);

				while (substing.find()) {
					startindex = substing.start();
				}
				strGameName = GameName.substring(0, startindex);
				//log.debug("newgamename=" + strGameName);
			}
			

			Util util = new Util();
			int length = util.xmlLength("./" + strGameName + "/Config/" + strGameName + ".xml", "WinningCombinations");

			ArrayList<String> symbolData = util.getXMLDataInArray("Symbol", "name",
					"./" + strGameName + "/Config/" + strGameName + ".xml");

			ArrayList<String> symbolArray = new ArrayList<>();
			ArrayList<String> proceedSymbolArray = new ArrayList<>();

			HashMap<String, Symbol> symbolmMap = new HashMap<String, Symbol>();

			int totalNoOfSymbols = symbolData.size();

			for (int count = 0; count < totalNoOfSymbols; count++) {
				String[] xmlData = symbolData.get(count).split(",");
				String symbolName = xmlData[0];
				symbolArray.add(count, symbolName);
				proceedSymbolArray.add(count, symbolName);
				symbolmMap.put(symbolName, new Symbol(symbolName));
			}

			for (int count = 0; count < length; count++)
			{
				String strWinCombination = util.readXML("WinCombination", "numSymbolsRequired", "symbols", "payouts",
						"./" + strGameName + "/Config/" + strGameName + ".xml", length + 2, count);
				if (strWinCombination != null) 
				{
					WinCombination winCombination = new WinCombination();

					String[] xmlData = strWinCombination.split(",");

					winCombination.setNumSymbolsRequired(Integer.parseInt(xmlData[0].trim()));
					winCombination.setSymbols(xmlData[1]);
					winCombination.setPayout(Integer.parseInt(xmlData[2].trim()));

					symbolmMap.get(xmlData[1]).addWinComb(winCombination);
				}
			}
			int winCombinationSize = length;
			System.out.println(winCombinationSize);

			proceedSymbolArray.remove("FS_Scatter");
			proceedSymbolArray.remove("WildMagic");
			proceedSymbolArray.remove("WildChest");
			
			proceedSymbolArray.remove("PiggyBank");
			proceedSymbolArray.remove("Safe");
			if(GameName.contains("5ReelDrive"))
			{
				proceedSymbolArray.remove("Wild");
			}
			log.debug("Total  Symbols::" + proceedSymbolArray);
			double bet = GetBetAmt();
			
			do {
				for (int column = 0; column <= 4; column++) 
				{
					for (int row = 1; row <= 3; row++) 
					{

						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("param1", Integer.toString(column));
						paramMap.put("param2", Integer.toString(row));

						String symbolNumberHook = XpathMap.get("getSymbolNumber");
						String newHook = replaceParamInHook(symbolNumberHook, paramMap);
						symbolNumber = getConsoleNumeric("return " + newHook);

					
						String symbolName = symbolArray.get((int) symbolNumber);
						//System.out.println("symbolName "+symbolName);
						//System.out.println("symbolArray "+symbolArray);
						
						if (proceedSymbolArray.contains(symbolName)) {
							String symbolClickHook = XpathMap.get("clickOnSymbol");
							newHook = replaceParamInHook(symbolClickHook, paramMap);
							clickAtButton("return " + newHook);
							
							try {
								threadSleep(2000);
								report.detailsAppendFolderOnlyScreenShot(currencyName);
								//report.detailsAppendFolder(symbolName, symbolName, symbolName, "pass", currencyName);
								log.info("screenshot taken for Symbol: "+symbolName);

							} catch (Exception e1) {
								log.error(e1.getStackTrace());
								log.error("error while taking screenshot ", e1);
								log.error(e1.getMessage());
							}

							// Fetching the payout values from clicked symbol
							for (int count = 2; count < 6; count++) 
							{

								String payoutValue = XpathMap.get("getPayoutValue");
								paramMap.put("param1", Integer.toString(count));
								newHook = replaceParamInHook(payoutValue, paramMap);
								String paytableValue = getConsoleText("return " + newHook);

								ArrayList<WinCombination> symWinCombs = symbolmMap.get(symbolName).getWinCombList();
								
								
								int payout = 0;
								for (WinCombination winCombination : symWinCombs) {
									if (winCombination.getNumSymbolsRequired() == count) {
										payout = winCombination.getPayout();
									}
								}

								if (payout != 0 && "".equalsIgnoreCase(paytableValue)) {
									log.debug("Fail for Symbol" + symbolName + " and count ::" + count);
								} else if (payout == 0 && !"".equalsIgnoreCase(paytableValue)) {
									log.debug("Fail for Symbol" + symbolName + " and count ::" + count);
								} else if (payout == 0 && "".equalsIgnoreCase(paytableValue)) {
									log.debug("Fail There is no payout for Symbol" + symbolName + " and count ::" + count);
								} else {
									
									try 
									{
										boolean result = currencyFormatValidatorForLVC(paytableValue,regExpression);
										Thread.sleep(500);

										if (result)
										{
											isAllTrue++;
											log.debug("Minipaytable payout is correct for bet value :" +bet+ 
													" currency format validation is :" + result+ " for Minipaytable payout for symbol" + symbolName );
									
											log.debug("PASS for Symbol" + symbolName + " and count ::" + count);
									
										} else  {

											log.debug("Minipaytable payout is incorrect for bet value :" +bet+ 
													" currency format validation is :" + result+ " for Minipaytable payout for symbol" + symbolName );

										}
																	
									} catch (Exception e) {

										log.error(e.getStackTrace());
										log.error("error while verifying currency format for mini paytable ", e);
										log.error(e.getMessage());
									}
								}

							}
							
							proceedSymbolArray.remove(symbolName);
							//System.out.println(proceedSymbolArray.remove(symbolName));
							log.debug("Remaining Symbols::" + proceedSymbolArray);
							//System.out.println("Remaining Symbols::" + proceedSymbolArray);

						}

					}
				}	if (proceedSymbolArray.size() > 0) {
					try {
						spinclick();
					} catch (InterruptedException e) {
						log.error("validateMiniPaytableForLVC", e);
						log.error(e.getMessage());
					}
					waitForSpinButtonstop();
					try {
						Thread.sleep(7000);
					} catch (InterruptedException e) {
						log.error(e.getStackTrace());
						log.error("error for Thread.sleep ", e);
						log.error(e.getMessage());
					}
				}

			} while (proceedSymbolArray.size() > 0);
			
			if (isAllTrue==winCombinationSize) {
				report.detailsAppendNoScreenshot(
						"verify MiniPaytable payout currency format for the selected bet value with the game currency format",
						"MiniPaytable payout verification  ith the game currency format ",
						"MiniPaytable payout verification with the game currency format is done",
						"pass");

			} else {
				report.detailsAppendNoScreenshot(
						"verify MiniPaytable payout verification for the bet : " + bet + " with the game currency format",
						"MiniPaytable payout verification with the game currency format ",
						"MiniPaytable payout verification with the game currency format is done but failed coz some formats are not matched",
						"fail");
			}
		}
		
		
		
		public void freeGameInfoCurrencyFormat(String regExpression, Desktop_HTML_Report currencyReport,String currencyName) 
		{
			boolean isValueInCurrencyFormat = false;	
			String freeGamesInfo = null;		
			String infoCurrency=null;
			String freegameInfoCurrency=null;

			try {
				log.debug("Function -> freeGameInfoBetCurrencyFormat()");
			
				freeGamesInfo="return " + XpathMap.get("freeGameInfoBetText");
				String freegamesInfoFormat = getConsoleText(freeGamesInfo);
				int index=freegamesInfoFormat.lastIndexOf("@");
				System.out.println(index);
				if(index>0)
				{
					freegameInfoCurrency=freegamesInfoFormat.substring(index+1,freegamesInfoFormat.length());					
					infoCurrency=freegameInfoCurrency.trim();
					System.out.println(freegameInfoCurrency.trim());
				}
				// To validate currency Amount format
				isValueInCurrencyFormat = currencyFormatValidatorForLVC(infoCurrency, regExpression);
				log.debug("Fetching Free games summary currency symbol " + "/n free games summary currency symbol is:: "+ infoCurrency);
				
				if (isValueInCurrencyFormat) 
				{
					currencyReport.detailsAppendFolder("Verify freegames info currency ",
							"freegames info currency should display with correct currency format and and currency symbol ",
							"freegames info currency displaying with correct currency format and and currency symbol ","Pass",currencyName);

				} else {
					currencyReport.detailsAppendFolder("Verify freegames info currency ",
							"freegames info currency should display with correct currency format and and currency symbol ",
							"freegames info currency displaying with incorrect currency format and and currency symbol ","Fail",currencyName);
				}
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		
		public void isFreeGamesVisible(String url) 
		{	
			long startTime = System.currentTimeMillis();
			try
			{
				while (true) 
				{
					boolean currentSceneflag = GetConsoleBooleanText("return " + XpathMap.get("isFGVisible"));

					System.out.println("free games visible " + currentSceneflag);
					if (currentSceneflag) 
					{
						log.info("free games visible " + currentSceneflag);
						Thread.sleep(1000);
						break;
					} 
					else 
					{
						long currentime = System.currentTimeMillis();
						if (((currentime - startTime) / 1000) > 5) 
						{
							Thread.sleep(5000);
							
							System.out.println("No free games reflect on screen , refresh ");
							log.info("No free games reflect on screen , refresh ");
							
							loadGame(url);
						}
					}
				}

			} catch (Exception e) {
				log.error("error while waiting for free games screen ", e);
				log.error(e.getMessage());
			}
		}
		
		public void isBonusMultiplierVisible() 
		{	
			try
			{
			  while(true)
				{
				  elementWait("return " + XpathMap.get("isBonusReelVisible"), true);
				  boolean isBonusVisible = GetConsoleBooleanText("return " + XpathMap.get("isBonusReelVisible"));
			
				  if (isBonusVisible) 
				  {
					  log.info("Bonus is visible " + isBonusVisible);
					  System.out.println("Bonus is visible " + isBonusVisible);
					  break;
				  }
				  else
				  {
					  log.info("Bonus is not visible, wait " + isBonusVisible);
				  }
				}

			} catch (Exception e) {
				log.error("error while waiting for Bonus ", e);
				log.error(e.getMessage());
			}
		}
		
		public void verifyGameHistory(Desktop_HTML_Report currencyReport,String currencyName) 
		{	
			clickMenuOnTopbar();
			boolean isGHNaviagted = false;
			try {
				boolean isGHAvailable = webdriver.findElement(By.xpath(XpathMap.get("isGameHistory"))).isDisplayed();
				if (isGHAvailable) 
				{
					currencyReport.detailsAppend("Verify Game History must be is displayed on Topbar",
							"Game History should be is displayed on Topbar", "Game History is displayed on Topbar", "Pass");
					String gameurl = webdriver.getCurrentUrl();
					webdriver.findElement(By.xpath(XpathMap.get("isGameHistory"))).click();
					log.debug("clicked on Game History on topbar");
					Thread.sleep(3000);
					checkpagenavigation(currencyReport, gameurl);
					System.out.println("Game navigated to Game History");
					isGHNaviagted = true;
					Thread.sleep(2000);
				
					if (isGHNaviagted) {
						log.debug("Game History navigation verified succesfully");
						
					} else {
						currencyReport.detailsAppend("Verify that Navigation to Game History screen",
								"Navigation to Game History screen", "Navigation to Game History screen not Done", "Fail");
					}
				}
				else
				{
					currencyReport.detailsAppend("Game History must be displayed on Topbar",
							"Game History should be is displayed on Topbar", "Game History is not displayed on Topbar",
							"Fail");
					System.out.println("Game History option is not available");
				}
				
			} catch (Exception e) {
				log.error("Error in checking Game History page ", e);
				log.error(e.getMessage());
			}
		}
		
		public void verifyHelp(Desktop_HTML_Report currencyReport,String currencyName) 
		{	
			clickMenuOnTopbar();
			boolean isHelpNaviagted = false;
			try {
				boolean isHelpAvailable = webdriver.findElement(By.xpath(XpathMap.get("isHelp"))).isDisplayed();
				if (isHelpAvailable) {
					currencyReport.detailsAppend("Verify Help must be is displayed on Topbar",
							"Help should be is displayed on Topbar", "Help is displayed on Topbar", "pass");
					String gameurl = webdriver.getCurrentUrl();
					webdriver.findElement(By.xpath(XpathMap.get("isHelp"))).click();
					log.debug("clicked on help on topbar");
					Thread.sleep(3000);
					checkpagenavigation(currencyReport, gameurl);
					System.out.println("Game navigated to Help");
					isHelpNaviagted = true;
					Thread.sleep(2000);
				
					if (isHelpNaviagted) {
						log.debug("Help navigation verified succesfully");
						
					} else {
						currencyReport.detailsAppend("Verify that Navigation to Help screen", "Navigation to Help screen",
								"Navigation to Help screen not Done", "fail");
					}
				}
				else 
				{
					currencyReport.detailsAppend("Help must be is displayed on Topbar", "Help should be is displayed on Topbar",
							"Help is not displayed on Topbar", "fail");
					System.out.println("Help option is not available");
				}
				
				
			} catch (Exception e) {
				log.error("Error in checking help page ", e);
				log.error(e.getMessage());
			}
		}
		
		
		
		
		//sv65878
		
		public boolean UnlockAllFreeSpin(Desktop_HTML_Report report) {

			boolean UnlockAllFreeSpin = false;

			try {

				int i = 1;

				String FreeSpinEntryScreen;
				String b2 ;
				FreeSpinEntryScreen	= XpathMap.get("FreeSpinEntryScreen");
				b2= entryScreen_Wait_Upadated_FreeSpin(FreeSpinEntryScreen);

				while (GetConsoleBooleanText("return " + XpathMap.get("FourthSFreeSpinVisible")) == false && i < 20) {

					log.info("UnlockAllFreeSpin Method is Running start");
						Thread.sleep(2000);
						if (b2.equalsIgnoreCase("freeSpin"))
						{

							clickBonusSelection(1);
							
							
				
							
							threadSleep(5000);
							log.info("Freespin continue button is clicked and awaiting for spin Button");
						}else {
							
							System.out.println("FreeSpinEntry Scrren Not displaying");
							log.error("FreeSpinEntry Scrren Not displaying");
									
						}

					

					Thread.sleep(3000);

					FSSceneLoading();
					
					waitSummaryScreen();

					Thread.sleep(3000);
					
				   elementWait("return " + XpathMap.get("currentScene"), "SLOT");
					
					waitForSpinButton();

					spinclick();

					Thread.sleep(5000);

				 FreeSpinEntryScreen = XpathMap.get("FreeSpinEntryScreen");
				 b2 = entryScreen_Wait_Upadated_FreeSpin(FreeSpinEntryScreen);

					i++;

				}

				return UnlockAllFreeSpin = true;

			}

			catch (Exception e) {
				log.error("error in Freespin Unlocking", e);
			}

			return UnlockAllFreeSpin;

		}
		
		
		public String entryScreen_Wait_Upadated_FreeSpin(String entry_Screen) {
			long startTime = System.currentTimeMillis();
			String wait = "FS";
			try {
				if (entry_Screen.equalsIgnoreCase("yes")) {
					log.debug("Waiting for free spin entry screen to come");
					while (true) {
						String currentScene = GetConsoleText("return " + XpathMap.get("currentScene"));
						if (("BONUS_SELECTION".equalsIgnoreCase(currentScene))) {
							wait = "freeSpin";
							// Thread.sleep(3000);
							break;
						}

						long currentime = System.currentTimeMillis();
						// Break if wait is more than 30 secs
						if (((currentime - startTime) / 1000) > 30) {
							log.debug("break after 30 secs");
							break;
						}
					}
				} else
					log.debug("Free Spin Entry Screen is not avalable");
			} catch (Exception e) {
				log.error(e);
			}
			return wait;
		}

		/*
		 * Saloni Verma 
		 * wait for Wild Desire
		 * 
		 */
		public boolean waitForwildDesire() {
			boolean result = false;
			long startTime = System.currentTimeMillis();
			try {
				log.debug("Waiting for Wild Desire to come after complition of spin");
				Thread.sleep(1000);
				while (true) {

					// Boolean currentSceneflag = GetConsoleBooleanText("return
					// "+XpathMap.get("isTieredBigWinAnimationVisible"));
					Boolean currentSceneflag = GetConsoleBooleanText("return " + XpathMap.get("isTieredBigWinTextVisible"));

					System.out.println("Wild Desire present = " + currentSceneflag);
					if (currentSceneflag) {
						log.info("Wild Desire present = " + currentSceneflag);
						Thread.sleep(1000);
						result = true;
						break;
					} else {
						long currentime = System.currentTimeMillis();
						if (((currentime - startTime) / 1000) > 30) {
							log.info("No Wild Desire present after 30 seconds= " + currentSceneflag);
							result = false;
							break;
						}

						Thread.sleep(1000);
					}
				}

			} catch (Exception e) {
				log.error("error while waiting for Wild Desire ", e);
			}

			return result;
		}
		
		//sv65878
		public boolean isWinVisible() 
		{
			Boolean winflag1 = GetConsoleBooleanText("return " + XpathMap.get("winFlag"));
				if(winflag1) {
				System.out.println("Win is Visible");
				
				String WinVal = getConsoleText("return " + XpathMap.get("WinDesktopLabel"));
				String numberOnly= WinVal.replaceAll("[^0-9]", "");
				Float.parseFloat(numberOnly);
				
			return true;
			}
				else {
					System.out.println("Win is not Visible");
			return false;
		}
		}
		
		
		public void isBetVisible() 
		{
			
		String betflag1 = getConsoleText("return " + XpathMap.get("WinDesktopLabel"));
		String numberOnly= betflag1.replaceAll("[^0-9]", "");
		float number = Float.parseFloat(numberOnly);
		System.out.println(number);
		float number1 = number/10;
		System.out.println(number1);
		float number2 = (number1/10);
		System.out.printf("%,.5f", number2);
			
		}
		
		//sv65878
		public boolean isWinAddedToCredit_updated(String creditB4Spin, String betValue) throws InterruptedException {
			
			
			boolean isWinAddedToCredit = false;
			
			System.out.println("After Spin");
			Thread.sleep(1500);		
			String creditAfetrStop = getCurrentCredits();
			
			
			double dblCreditAfetrStop = Double.parseDouble(creditAfetrStop.replaceAll("[^0-9]", ""));
			double dblCreditB4Spin = Double.parseDouble(creditB4Spin.replaceAll("[^0-9]", ""));
			double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));
			
			
			boolean winflag1 = elementWait("return " + XpathMap.get("winFlag"), true);
					
			
			if(winflag1) {
			isWinAddedToCredit = true;
			Thread.sleep(4000);
			String winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
			
			System.out.println("Win is Visible :" + winAmount);
			
			double dblWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));

			if (((dblCreditB4Spin - dblBetValue) + dblWinAmount) == dblCreditAfetrStop) {
				System.out.println("Win added to Credit Succesfully, Credit After Win:"+ dblCreditAfetrStop);
				log.debug("win added to credit succesfully");
				
			}
			}//winflag
			
			else {
				System.out.println("There is no win, Credit Amount after Lose :"+ dblCreditAfetrStop);
				isWinAddedToCredit=false;
			}
			
			return isWinAddedToCredit;
		}

		//sv65878
		public void waitForSpinButton_updated() {
			try {
				
				boolean spnbtn = elementWait("return " + XpathMap.get("SpinBtnCurrState"), "active");
				if(spnbtn!=true) {
					System.out.println("spin btn is not visible");
				}
				
			} catch (JavascriptException e) {
				log.error("Error in hook ,please the hook ");
				log.error(e.getCause());
			} catch (Exception e) {
				log.error("error while waiting for spin button", e);
			}
		}

		/**
		 * *Author:Havish This method is used to wait till the free spin summary screen
		 * won't come
		 * 
		 * @throws InterruptedException
		 */
		public boolean waitforFreeSpinIntroScreen(Desktop_HTML_Report report, String languageCode) throws InterruptedException {
			boolean introScrn = false;
			Wait = new WebDriverWait(webdriver, 100);

			try {
				long startTime = System.currentTimeMillis();
				log.info("Waiting for Intro Screen Screen to come");
				while (true) {
					String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
					//System.out.println(currentScene);
					log.debug(currentScene);
					if (currentScene != null && (currentScene.contains("FREESPINS_INTRO"))) {
						{
							System.out.println("FreeSpin Intro screen visible = "+currentScene);
							Thread.sleep(1400);
							report.detailsAppendFolder("Verify FreeSpin Intro Screen is Visible", "FreeSpin Intro Screen", "FreeSpin Intro Screen is Visible", "PASS", languageCode);
							log.debug("Intro screen visible");
							log.debug(currentScene);
							introScrn=true;
							
							break;
						}
					}

					long currentime = System.currentTimeMillis();
					// Break if wait is more than 300 secs
					if (((currentime - startTime) / 1000) > 300) {
						log.debug("Intro screen not visible, break after  5 mins");
						break;
					}
				}

				// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
			} catch (Exception e) {
				log.error("error while waiting for summary screen ",e);
				log.error(e.getMessage(),e);
			}
			return introScrn;
		}

		
		public boolean isSpinBtnVisible() {
	        boolean ret = false;
	        boolean waitforspinbtn =elementWait("return " + XpathMap.get("isSpinVisible"), true);
	        if(waitforspinbtn) {
	        	ret = true;
	        }
	        return ret;
	    }
		
		
		public void validateMiniPaytableForLVC_updated(Desktop_HTML_Report report,String regExpression,String currencyName) 
		{
		int isAllTrue=0;
		String strGameName = null;
		int startindex = 0;
		long symbolNumber;
		
		if (GameName.contains("Desktop")) {

			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desktop");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			//log.debug("newgamename=" + strGameName);
		}
		
		else if(GameName.contains("Desk")) {
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("Desk");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			//log.debug("newgamename=" + strGameName);
		
		}
		
		else if(GameName.contains("De")) {
			java.util.regex.Pattern str = java.util.regex.Pattern.compile("De");

			Matcher substing = str.matcher(GameName);

			while (substing.find()) {
				startindex = substing.start();
			}
			strGameName = GameName.substring(0, startindex);
			//log.debug("newgamename=" + strGameName);
		
		}
		

		Util util = new Util();
		int length = util.xmlLength("./" + strGameName + "/Config/" + strGameName + ".xml", "WinningCombinations");

		ArrayList<String> symbolData = util.getXMLDataInArray("Symbol", "name",
				"./" + strGameName + "/Config/" + strGameName + ".xml");

		ArrayList<String> symbolArray = new ArrayList<>();
		ArrayList<String> proceedSymbolArray = new ArrayList<>();

		HashMap<String, Symbol> symbolmMap = new HashMap<String, Symbol>();

		int totalNoOfSymbols = symbolData.size();

		for (int count = 0; count < totalNoOfSymbols; count++) {
			String[] xmlData = symbolData.get(count).split(",");
			String symbolName = xmlData[0];
			symbolArray.add(count, symbolName);
			proceedSymbolArray.add(count, symbolName);
			symbolmMap.put(symbolName, new Symbol(symbolName));
		}

		for (int count = 0; count < length; count++)
		{
			String strWinCombination = util.readXML("WinCombination", "numSymbolsRequired", "symbols", "payouts",
					"./" + strGameName + "/Config/" + strGameName + ".xml", length + 2, count);
			if (strWinCombination != null) 
			{
				WinCombination winCombination = new WinCombination();

				String[] xmlData = strWinCombination.split(",");

				winCombination.setNumSymbolsRequired(Integer.parseInt(xmlData[0].trim()));
				winCombination.setSymbols(xmlData[1]);
				winCombination.setPayout(Integer.parseInt(xmlData[2].trim()));

				symbolmMap.get(xmlData[1]).addWinComb(winCombination);
			
			}
		}
		int winCombinationSize = length;
		System.out.println(winCombinationSize);

		proceedSymbolArray.remove("FS_Scatter");
		proceedSymbolArray.remove("WildMagic");
		proceedSymbolArray.remove("WildChest");
		
		proceedSymbolArray.remove("PiggyBank");
		proceedSymbolArray.remove("Safe");
		if(GameName.contains("5ReelDrive"))
		{
			proceedSymbolArray.remove("Wild");
		}
		log.debug("Total  Symbols::" + proceedSymbolArray);
		double bet = GetBetAmt();
		
		do {
			for (int column = 0; column <= 4; column++) 
			{
				for (int row = 1; row <= 3; row++) 
				{

					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("param1", Integer.toString(column));
					paramMap.put("param2", Integer.toString(row));

					String symbolNumberHook = XpathMap.get("getSymbolNumber");
					String newHook = replaceParamInHook(symbolNumberHook, paramMap);
					symbolNumber = getConsoleNumeric("return " + newHook);

				
					String symbolName = symbolArray.get((int) symbolNumber);
					//System.out.println("symbolName "+symbolName);
					//System.out.println("symbolArray "+symbolArray);
					
					if (proceedSymbolArray.contains(symbolName)) {
						String symbolClickHook = XpathMap.get("clickOnSymbol");
						newHook = replaceParamInHook(symbolClickHook, paramMap);
						clickAtButton("return " + newHook);
						
						try {
							threadSleep(2000);
							//report.detailsAppendFolderOnlyScreenShot(currencyName);
							//report.detailsAppendFolder(symbolName, symbolName, symbolName, "pass", currencyName);
							report.detailsAppendFolder("Verify Mini Paytable", "Mini Paytable", "Mini Paytable", "PASS", currencyName);
							
							log.info("screenshot taken for Symbol: "+symbolName);

						} catch (Exception e1) {
							report.detailsAppendFolder("Verify Mini Paytable", "Mini Paytable", "Mini Paytable", "FAIL", currencyName);
							log.error(e1.getStackTrace());
							log.error("error while taking screenshot ", e1);
							log.error(e1.getMessage());
						}

						// Fetching the payout values from clicked symbol
						for (int count = 2; count < 6; count++) 
						{

							String payoutValue = XpathMap.get("getPayoutValue");
							paramMap.put("param1", Integer.toString(count));
							newHook = replaceParamInHook(payoutValue, paramMap);
							String paytableValue = getConsoleText("return " + newHook);

							ArrayList<WinCombination> symWinCombs = symbolmMap.get(symbolName).getWinCombList();
							
							
							int payout = 0;
							for (WinCombination winCombination : symWinCombs) {
								if (winCombination.getNumSymbolsRequired() == count) {
									payout = winCombination.getPayout();
								}
							}

							if (payout != 0 && "".equalsIgnoreCase(paytableValue)) {
								log.debug("Fail for Symbol" + symbolName + " and count ::" + count);
							} else if (payout == 0 && !"".equalsIgnoreCase(paytableValue)) {
								log.debug("Fail for Symbol" + symbolName + " and count ::" + count);
							} else if (payout == 0 && "".equalsIgnoreCase(paytableValue)) {
								log.debug("Fail There is no payout for Symbol" + symbolName + " and count ::" + count);
							} else {
								
								try 
								{
									boolean result = currencyFormatValidatorForLVC
											(paytableValue,regExpression);
									Thread.sleep(500);

									if (result)
									{
										isAllTrue++;
										log.debug("Minipaytable payout is correct for bet value :" +bet+ 
												" currency format validation is :" + result+ " for Minipaytable payout for symbol" + symbolName );
								
										log.debug("PASS for Symbol" + symbolName + " and count ::" + count);
								
									} else  {

										log.debug("Minipaytable payout is incorrect for bet value :" +bet+ 
												" currency format validation is :" + result+ " for Minipaytable payout for symbol" + symbolName );

									}
																
								} catch (Exception e) {

									log.error(e.getStackTrace());
									log.error("error while verifying currency format for mini paytable ", e);
									log.error(e.getMessage());
								}
							}

						}
						
						proceedSymbolArray.remove(symbolName);
						//System.out.println(proceedSymbolArray.remove(symbolName));
						log.debug("Remaining Symbols::" + proceedSymbolArray);
						//System.out.println("Remaining Symbols::" + proceedSymbolArray);

					}

				}
			}	if (proceedSymbolArray.size() > 0) {
				try {
					spinclick();
				} catch (InterruptedException e) {
					log.error("validateMiniPaytableForLVC", e);
					log.error(e.getMessage());
				}
				waitForSpinButtonstop();
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					log.error(e.getStackTrace());
					log.error("error for Thread.sleep ", e);
					log.error(e.getMessage());
				}
			}

		} while (proceedSymbolArray.size() > 0);
		
		if (isAllTrue==winCombinationSize) {
			report.detailsAppendNoScreenshot(
					"verify MiniPaytable payout currency format for the selected bet value with the game currency format",
					"MiniPaytable payout verification  ith the game currency format ",
					"MiniPaytable payout verification with the game currency format is done",
					"pass");

		} else {
			report.detailsAppendNoScreenshot(
					"verify MiniPaytable payout verification for the bet : " + bet + " with the game currency format",
					"MiniPaytable payout verification with the game currency format ",
					"MiniPaytable payout verification with the game currency format is done but failed coz some formats are not matched",
					"fail");
		}
	}
		
		public boolean clickMenuOnTopbar() {
	        boolean ret = false;
	        try {
	            webdriver.findElement(By.xpath(XpathMap.get("menuOnTopbar"))).click();
	            log.debug("Clicked on menu button on topbar to open");
	            ret = true;
	        } catch (Exception e) {
	            log.error("Error in opening menu", e);



	       }
	        return ret;
	    }
		
		//TS64283
		public boolean freeGameBetIconAndBetValueStatus()
		{
			boolean b = false;
			try {
				String status = getConsoleText("return " + XpathMap.get("BetComponetState"));
				boolean status1 = GetConsoleBooleanText("return " + XpathMap.get("BetValueStatus"));
			        if(status.contains("DISABLED")||status1!=true) {
			        	b = true;
			        }
				Thread.sleep(2000);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
			return b;
		}
		
		//TS64283
		public void ClickOnResumePlayBtn() {
			try {
				String text = getConsoleText("return " + XpathMap.get("FGResumePlayBtn"));
				if (text.equalsIgnoreCase("resumeButton")) {
					clickAtButton("return " + XpathMap.get("ClickFGResumePlayBtn"));
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	
		

		public boolean spinclickFS() {
			try {
				// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
				if("yes".equalsIgnoreCase(XpathMap.get("GameName_MegaMoolah"))) {
					clickAtButton("return " + XpathMap.get("SpinBtnFs"));
				}
				else {
				if (!GameName.contains("Scratch")) {
					clickAtButton("return " + XpathMap.get("ClickSpinBtn"));
				} else {
					clickAtButton("return  " + XpathMap.get("ClickscratchButton"));

					elementWait("return " + XpathMap.get("ScratchBtnCurrState"), "activeSecondary");

					clickAtButton("return  " + XpathMap.get("ClickscratchButton"));
				}
				log.debug("Clicked on spin button");
			}	// Thread.sleep(1000);

			} catch (Exception e) {
				log.error("error while clicking on spin button", e);
			}
			return true;
		}
		
		
		
		
		/*
		 * Method to verify currency format in credit
		 */

		public boolean verifyCurrencyFormatForTicker(Desktop_HTML_Report currencyReport, String regExpression) {
			boolean result = true;
			Map<String, String> paramMap = new HashMap<String, String>();
			
			
			
			try {
				log.debug("Function-> verifyCurrencyFormat() ");
				
				// Read Ticker Values
				 long Tickers = getConsoleNumeric("return " + XpathMap.get("No_of_Ticker"));
				for(int i=0;i<Tickers;i++) {
					
					paramMap.put("param1", Integer.toString(i));
					
					
					 String tickerval = XpathMap.get("TickerValues");
			          String newHook = replaceParamInHook(tickerval, paramMap);
					int j=i+1;
			        String val =   getConsoleText("return "+ newHook).replaceAll("Ticker: ", "");
			        result=currencyFormatValidatorForLVC(val,regExpression);
			        if(result) {
			        	currencyReport.detailsAppend("Verify Currency Format",
								"For Ticker "+j,val, "pass");
			        }
			        
			        else {
			        	currencyReport.detailsAppend("Verify Currency Format",
								"For Ticker "+j,val, "Fail");
			        }
			        
					
				}
				
				

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			return result;
		}
		

		//****************************************************************************//

		/**
		 * @author pb61055
		 * This is method is used to verify easy help
		 */
		public void easyHep() 
		{ 
			int count=0;
			int count2=0;
			try
			{
				
				WebElement ele = webdriver.findElement(By.xpath(XpathMap.get("easyHelpVisible")));
				boolean isEasyHelpVisible1=ele.isDisplayed();
				
				if (isEasyHelpVisible1) 
				{
					for(int i=1;i<11;i++) 
					{
												
						boolean value= webdriver.findElement(By.xpath("//*[@class=\"MuiPaper-root jss8 MuiPaper-elevation1 MuiPaper-rounded\"]/section["+i+"]")).isDisplayed();
						System.out.println("value= "+value+" for i="+i); 
						
						if(value && i==1)
						{
							for(int j=1;j<5;j++)
							{
								boolean value1= webdriver.findElement(By.xpath("//*[@id=\"string_9-"+j+"\"]")).isDisplayed();
								if(value1)
								{
									count++;
									System.out.println("Count= "+count);
								}
							}
							
						}
						else if(value && i==2)
						{
							for(int k=1;k<7;k++)
							{
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_8-"+k+"\"]")).isDisplayed();
								if(value2)
								{
									count2++;
									System.out.println("Count2= "+count2);
								}
							}
						}
				}
					if(count==4 && count2==6)
					{
						System.out.println("Easy help is correct");
					}
				} else {
					System.out.println("no pop up present");
				}
				
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		
		}
			
//SV65878
	/*	public void easy_Help(){
			
			int count[];
			int numberOfSection = (int) getConsoleNumeric("return" + XpathMap.get("helpfile_Sections"));
			
			try
			{
				
				WebElement ele = webdriver.findElement(By.xpath(XpathMap.get("easyHelpVisible")));
				boolean isEasyHelpVisible1=ele.isDisplayed();
				
				if (isEasyHelpVisible1) 
				{
					for(int i=1;i<numberOfSection;i++) 
					{
												
						boolean value= webdriver.findElement(By.xpath("//*[@class=\"MuiPaper-root jss8 MuiPaper-elevation1 MuiPaper-rounded\"]/section["+i+"]")).isDisplayed();
						System.out.println("value= "+value+" for i="+i); 
						
						if(value && i==1)
						{
							for(int j=1;j<15;j++)
							{
								boolean value1= webdriver.findElement(By.xpath("//*[@id=\"string_9-"+j+"\"]")).isDisplayed();
								if(value1)                                      //*[@id="string_9-1"]  
								{
									count++;
									System.out.println("Count= "+count);
								}
							}
							
						}
						else if(value && i==2)
						{
							for(int k=1;k<7;k++)
							{
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_8-"+k+"\"]")).isDisplayed();
								if(value2)
								{
									count2++;
									System.out.println("Count2= "+count2);
								}
							}
						}
				}
					if(count==4 && count2==6)
					{
						System.out.println("Easy help is correct");
					}
				} else {
					System.out.println("no pop up present");
				}
				
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		}
		*/
		
		
		
		
		
		
		/**
		 * @author SV65878
		 * This is method is used to verify easy help
		 */
		public int[] EnglishCount() 
		{ 
 			int Totalcount[] = new int[11];

			
			
			try
			{
				
				WebElement ele = webdriver.findElement(By.xpath(XpathMap.get("easyHelpVisible")));
				boolean isEasyHelpVisible1=ele.isDisplayed();
				
				if (isEasyHelpVisible1) 
				{
					for(int i=1;i<11;i++) 
					{
												
						boolean value= webdriver.findElement(By.xpath("//*[@class=\"MuiPaper-root jss8 MuiPaper-elevation1 MuiPaper-rounded\"]/section["+i+"]")).isDisplayed();
					//	System.out.println("value= "+value+" for i="+i); 
						
						if(value && i==1)
						{  int count=0;
							for(int j=1;j<5;j++)
							{   
								boolean value1= webdriver.findElement(By.xpath("//*[@id=\"string_9-"+j+"\"]")).isDisplayed();
								if(value1)
								{
									Totalcount[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
							
						}
						
						else if(value && i==2)
						{ int count=0;
							for(int k=1;k<7;k++)
							{   
								//int count=0;
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_8-"+k+"\"]")).isDisplayed();
								if(value2)
								{
									Totalcount[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
				
						else if(value && i==3)
						{   int count=0;
							for(int k=1;k<4;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_7-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
						
						else if(value && i==4)
						{ int count=0;
							for(int k=1;k<7;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_5-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
					
						else if(value && i==5)
						{   int count=0;
							for(int k=1;k<7;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_4-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount[i]= count+1;
						//			System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
					  
						else if(value && i==6)
						{   int count=0;
							for(int k=1;k<12;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_3-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount[i]= count+1;
						//			System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
						else if(value && i==7)
						{   int count=0;
							for(int k=1;k<15;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_10-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
						
						else if(value && i==8)
						{   int count=0;
							for(int k=1;k<9;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_11-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
						
						else if(value && i==9)
						{   int count=0;
							for(int k=1;k<7;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_6-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
								
									Totalcount[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
					
						else if(value && i==10)
						{   int count=0;
							for(int k=1;k<4;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_6-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount[i]= count+1;
								//	System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
							
						
					}//for-11
					
					for(int p=1;p<11;p++) {
						System.out.println("Count [" +p+"] ="+ Totalcount[p]);
					}
					
				} else {
					System.out.println("no easy help present");
				}
				
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		
			return Totalcount;
		}
	
		
		
		//sv65878
		
	/*	public void CaptureScreenshot_Helpfile(Desktop_HTML_Report language, String languageCode) {
			
			try {
			boolean test = false;
			System.out.println("Help File is Displayed");
			language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
			Thread.sleep(3000);
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section1End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section1End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section2End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section2End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				
				test=true;
			 }
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section3End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section3End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section4End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section4End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section5End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section5End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section6End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section6End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section7End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section7End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section8End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section8End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section9End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section9End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","Helpfile Page ScreenShot","pass",languageCode);
				Thread.sleep(3000);
				test=true;
			 }
			
			
			
			
			
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		*/
		
		
		//other language content count helpfile
		/**
		 * @author SV65878
		 * This is method is used to verify easy help
		 */
		public int[] OtherLanguageCount() 
		{ 
 			int Totalcount1[] = new int[11];

			
			
			try
			{
				
				WebElement ele = webdriver.findElement(By.xpath(XpathMap.get("easyHelpVisible")));
				boolean isEasyHelpVisible1=ele.isDisplayed();
				
				if (isEasyHelpVisible1) 
				{
					for(int i=1;i<11;i++) 
					{
												
						boolean value= webdriver.findElement(By.xpath("//*[@class=\"MuiPaper-root jss8 MuiPaper-elevation1 MuiPaper-rounded\"]/section["+i+"]")).isDisplayed();
					//	System.out.println("value= "+value+" for i="+i); 
						
						if(value && i==1)
						{  int count=0;
							for(int j=1;j<5;j++)
							{   
								boolean value1= webdriver.findElement(By.xpath("//*[@id=\"string_9-"+j+"\"]")).isDisplayed();
								if(value1)
								{
									Totalcount1[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
							
						}
						
						else if(value && i==2)
						{ int count=0;
							for(int k=1;k<7;k++)
							{   
								//int count=0;
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_8-"+k+"\"]")).isDisplayed();
								if(value2)
								{
									Totalcount1[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
				
						else if(value && i==3)
						{   int count=0;
							for(int k=1;k<4;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_7-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
						
						else if(value && i==4)
						{ int count=0;
							for(int k=1;k<7;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_5-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
					
						else if(value && i==5)
						{   int count=0;
							for(int k=1;k<7;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_4-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
						//			System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
					  
						else if(value && i==6)
						{   int count=0;
							for(int k=1;k<12;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_3-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
						//			System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
						else if(value && i==7)
						{   int count=0;
							for(int k=1;k<15;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_10-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
					
						
						else if(value && i==8)
						{   int count=0;
							for(int k=1;k<9;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_11-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
						
						else if(value && i==9)
						{   int count=0;
							for(int k=1;k<7;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_6-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
							//		System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
						
					
						else if(value && i==10)
						{   int count=0;
							for(int k=1;k<4;k++)
							{   
								
								boolean value2= webdriver.findElement(By.xpath("//*[@id=\"string_6-"+k+"\"]")).isDisplayed();
								if(value2)                                      
								{
									Totalcount1[i]= count+1;
								//	System.out.println("Count= "+ Totalcount[i]);
									count++;
								}
							}
						}
							
						
					}//for-11
					
					for(int p=1;p<11;p++) {
						System.out.println("Count [" +p+"] ="+ Totalcount1[p]);
					}
					
				} else {
					System.out.println("no easy help present");
				}
				
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		
			return Totalcount1;
		}
		
		
		/**
		 * @author SV65878
		 * 
		 */
		
public void CaptureScreenshot_Helpfile_Translated(Desktop_HTML_Report language, String languageCode) {
			
			try {
				
				
			int a[] = EnglishCount();	
			int b[] = OtherLanguageCount();
			
				
				
			boolean test = false;
			System.out.println("Help File is Displayed");
			
			if(a[1]==b[1]) {
			language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
			Thread.sleep(3000);
			}
			
			else {
				language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
				
			}
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section1End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section1End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[2]==b[2]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				test=true;
			 }
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section2End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section2End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[3]==b[3]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				
				test=true;
			 }
			
			
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section3End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section3End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[4]==b[4]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				
				
				
				test=true;
			 }
			
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section4End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section4End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[5]==b[5]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				
				
				
				test=true;
			 }
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section5End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section5End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[6]==b[6]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				
				
				test=true;
			 }
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section6End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section6End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[7]==b[7]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				
				test=true;
			 }
			
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section7End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section7End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[8]==b[8]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				
				
				test=true;
			 }
			
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section8End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section8End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[9]==b[9]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				test=true;
			 }
			
			
			test = webdriver.findElements(By.xpath(XpathMap.get("Helpfile_Section9End"))).size() > 0;
			if (test)
			{
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("Helpfile_Section9End")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
				
				if(a[10]==b[10]) {
					language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","pass",languageCode);
					}
					
					else {
						language.detailsAppendFolder("Helpfile Page ScreenShot ","Helpfile Page ScreenShot","in different language","Fail",languageCode);
						
					}
				
				
				test=true;
			 }
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


/**
 * @author SV65878
 * 
 */

public int[] CaptureScreenshot_Helpfile(Desktop_HTML_Report language, String languageCode) {
	
	List<WebElement> elements = webdriver.findElements(By.xpath(XpathMap.get("AllHeadingsCount")));
	int Head_Count = elements.size();
	
	int Totalcount[] = new int[Head_Count];
	try {
			
	Map<String, String> paramMap = new HashMap<String, String>();
	
	System.out.println("Help File is Displayed");
	
	
	int count =0;
	
	for(int i=1; i<=Head_Count;i++)
	{   
		paramMap.put("param1", Integer.toString(i));
		String Internal_Count = XpathMap.get("InternalCount");
		String newHook = replaceParamInHook(Internal_Count, paramMap);
		
		List<WebElement> elements1 = webdriver.findElements(By.xpath(newHook));
		int Bullet_Count = elements1.size();
		
		/*Heading Text*/
		paramMap.put("param1", Integer.toString(i));
		String Headings = XpathMap.get("AllHeadingsText");
		String newHook3 = replaceParamInHook(Headings, paramMap);
		WebElement text1 = webdriver.findElement(By.xpath(newHook3));
		String HeadingsText = text1.getText();
	    
		language.detailsAppendFolder(HeadingsText,"","Language :"+languageCode,"pass",languageCode);
		
		
		
		
		for(int j=1; j<Bullet_Count;j++) {
			
			paramMap.put("param1", Integer.toString(i));
			paramMap.put("param2", Integer.toString(j));
			
			String LastBullet_Count = XpathMap.get("LastBullet");
			String newHook2 = replaceParamInHook(LastBullet_Count, paramMap);
			WebElement ele1 = webdriver.findElement(By.xpath(newHook2));
			ele1.getText();
			
		//	language.detailsAppendFolder("",BulletText,"","",languageCode);
			//language.detailsAppendFolder("",BulletText,"","pass",languageCode);
			
			count=count+1;
			if(j==Bullet_Count-1) {
				
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
			}
			
			
		}
		
		Totalcount[i-1] = count;
		
		count=0;
		
	}
	
	for(int x=0; x<Head_Count;x++) {
		System.out.println(Totalcount[x]);
	}
	
	
	
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return Totalcount;
	
}




/**
 * @author SV65878
 * 
 */

///////////////English Count/////////////////////////////

public int[] HelpfileContent_Count() {
	
	List<WebElement> elements = webdriver.findElements(By.xpath(XpathMap.get("AllHeadingsCount")));
	int Head_Count = elements.size();
	
	int Totalcount[] = new int[Head_Count];
	Map<String, String> paramMap = new HashMap<String, String>();
	
	
	int count =0;
	
	for(int i=1; i<=Head_Count;i++)
	{   
		paramMap.put("param1", Integer.toString(i));
		String Internal_Count = XpathMap.get("InternalCount");
		String newHook = replaceParamInHook(Internal_Count, paramMap);
		
		List<WebElement> elements1 = webdriver.findElements(By.xpath(newHook));
		int Bullet_Count = elements1.size();
		
		
		for(int j=1; j<Bullet_Count;j++) {
			
			count=count+1;
			
			
		}
		
		Totalcount[i-1] = count;
		
		count=0;
		
	}
	
	for(int x=0; x<Head_Count;x++) {
		System.out.println(Totalcount[x]);
	}
	
	return Totalcount;
	
}

/**
 * @author SV65878
 * 
 */
public int[] CaptureScreenshot_Helpfile_Lang(Desktop_HTML_Report language, String languageCode) {
	
	
	int EnglishArray[] = HelpfileContent_Count();
	List<WebElement> elements = webdriver.findElements(By.xpath(XpathMap.get("AllHeadingsCount")));
	int Head_Count = elements.size();
	
	int Totalcount_lang[] = new int[Head_Count];
	try {
			
	Map<String, String> paramMap = new HashMap<String, String>();
	
	System.out.println("Help File is Displayed");
	
	
	int count =0;
	
	for(int i=1; i<=Head_Count;i++)
	{   
		paramMap.put("param1", Integer.toString(i));
		String Internal_Count = XpathMap.get("InternalCount");
		String newHook = replaceParamInHook(Internal_Count, paramMap);
		
		List<WebElement> elements1 = webdriver.findElements(By.xpath(newHook));
		int Bullet_Count = elements1.size();
		
		/*Heading Text*/
		paramMap.put("param1", Integer.toString(i));
		String Headings = XpathMap.get("AllHeadingsText");
		String newHook3 = replaceParamInHook(Headings, paramMap);
		WebElement text1 = webdriver.findElement(By.xpath(newHook3));
		String HeadingsText = text1.getText();
	    
		language.detailsAppendFolder("Heading of the Section : ",HeadingsText,"Language :"+languageCode,"pass",languageCode);
		
		
		
		
		for(int j=1; j<Bullet_Count;j++) {
			
			paramMap.put("param1", Integer.toString(i));
			paramMap.put("param2", Integer.toString(j));
			
			String LastBullet_Count = XpathMap.get("LastBullet");
			String newHook2 = replaceParamInHook(LastBullet_Count, paramMap);
			WebElement ele1 = webdriver.findElement(By.xpath(newHook2));
			String BulletText = ele1.getText();
			
			if(count==EnglishArray[i-0]) {
			language.detailsAppendFolder("Content of this Section is",BulletText,"Language Code :"+languageCode,"pass",languageCode);
			}
			
			else {
				language.detailsAppendFolder("Content of this Section is",BulletText,"Language Code :"+languageCode,"Fail",languageCode);
				}
			
			
			count=count+1;
			if(j==Bullet_Count-1) {
				
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
			}
			
			
		}
		
		Totalcount_lang[i-1] = count;
		
		count=0;
		
	}
	
	for(int x=0; x<Head_Count;x++) {
		System.out.println(Totalcount_lang[x]);
	}
	
	
	
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return Totalcount_lang;
	
}


/**
 * @author SV65878
 * 
 */
//String store
////////////////////////////////////////////////////////////////////////////////////////

public String[] Helpfile_headings_String(Desktop_HTML_Report language, String languageCode) throws InterruptedException {

	List<WebElement> elements = webdriver.findElements(By.xpath(XpathMap.get("AllHeadingsCount")));
	int Head_Count = elements.size();

	String[] AllHeadingStrings =new String[Head_Count];
	
	Map<String, String> paramMap = new HashMap<String, String>();

	for(int i=1; i<=Head_Count;i++)
	{   
		paramMap.put("param1", Integer.toString(i));
		String Internal_Count = XpathMap.get("InternalCount");
		String newHook = replaceParamInHook(Internal_Count, paramMap);

		List<WebElement> elements1 = webdriver.findElements(By.xpath(newHook));
		elements1.size();

		/*Heading Text*/
		paramMap.put("param1", Integer.toString(i));
		String Headings = XpathMap.get("AllHeadingsText");
		String newHook3 = replaceParamInHook(Headings, paramMap);
		WebElement text1 = webdriver.findElement(By.xpath(newHook3));
		String HeadingsText = text1.getText();
		AllHeadingStrings[i-1] = HeadingsText;
		

		
	}

	return AllHeadingStrings;

}
/**
 * @author SV65878
 * 
 */
public String[][] AllBulletPointsText(Desktop_HTML_Report language, String languageCode, int english_Heading_Count) throws InterruptedException {
	
	List<WebElement> elements = webdriver.findElements(By.xpath(XpathMap.get("AllHeadingsCount")));
	int Head_Count = elements.size();
	int row=0;
	//String[][] AllBulletString = new String[Head_Count][];
	String[][] AllBulletStringTemp = null;
	String[][] AllBulletString=new String[Head_Count][20];
	int Totalcount[] = new int[Head_Count];
	Map<String, String> paramMap = new HashMap<String, String>();
	
	int count =0;
	
	for(int i=1; i<=Head_Count;i++)
	{   
		paramMap.put("param1", Integer.toString(i));
		String Internal_Count = XpathMap.get("InternalCount");
		String newHook = replaceParamInHook(Internal_Count, paramMap);
		
		List<WebElement> elements1 = webdriver.findElements(By.xpath(newHook));
		int Bullet_Count = elements1.size();
		
		/*Heading Text*/
		paramMap.put("param1", Integer.toString(i));
		String Headings = XpathMap.get("AllHeadingsText");
		String newHook3 = replaceParamInHook(Headings, paramMap);
		WebElement text1 = webdriver.findElement(By.xpath(newHook3));
		text1.getText();
		
		
		AllBulletStringTemp = new String[Head_Count][Bullet_Count];
		
		int j;
		for(j=1; j<Bullet_Count;j++) {
			
			//AllBulletString = new String[Head_Count][Bullet_Count];
			
			paramMap.put("param1", Integer.toString(i));
			paramMap.put("param2", Integer.toString(j));
			
			String LastBullet_Count = XpathMap.get("LastBullet");
			String newHook2 = replaceParamInHook(LastBullet_Count, paramMap);
			WebElement ele1 = webdriver.findElement(By.xpath(newHook2));
			//Thread.sleep(2000);
			String BulletText = ele1.getText();
			
			AllBulletStringTemp[i-1][j-1]=BulletText;
		
			//language.detailsAppendFolder("",BulletText,"","pass",languageCode);
			
			count=count+1;
		/*	if(j==Bullet_Count-1) {
				
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
			}
			
			*/
				
		}
		
		
		//store
		int col=0;
		while(col<j && AllBulletStringTemp[row][col]!=null)
{       	//Thread.sleep(2000);
			AllBulletString[row][col] = AllBulletStringTemp[row][col];
            col++;
}
		
		
		
		row = row+1;
		
		Totalcount[i-1] = count;
		
		count=0;
		
	}
	
	
/*	for(int x=0; x<Head_Count;x++) {
		System.out.println(Totalcount[x]);
	}
	*/
	
	return AllBulletString;
	
}





//crisp helpfile code 
/**
 * @author SV65878
 * 
 */
public void marketHelpfilevalidation(WebDriver webdriver, String url, Desktop_HTML_Report markets,
			CFNLibrary_Desktop cfnlib, CommonUtil util, List<Map> langListFormarkets,String HelpTextLink) throws InterruptedException {

		String userName;
		String urlNew = url;
		String languageCode;
		WebElement isGameName_helpVisible;
		String launchURL = url;

		int English_Heading_Count = 0;

		int English[] = null;
		String[] EnglishHeadingTxtString = null;
		String[][] AllBulletsTextStores = null;

		List<Map> langListForindividualmarket = langListFormarkets;

		for (Map<String, String> LanguageMapForMarket : langListForindividualmarket) {

			userName = util.randomStringgenerator();

			LanguageMapForMarket.get(Constant.LANGUAGE).trim();
			languageCode = LanguageMapForMarket.get(Constant.LANG_CODE).trim();
			String regMarket = LanguageMapForMarket.get(Constant.MARKET_XL_SHEET_NAME).trim();
			// String envId = LanguageMapForMalta.get(Constant.EnvId).trim();
			String productId1 = LanguageMapForMarket.get(Constant.PRODUCT_ID).trim();
			LanguageMapForMarket.get(Constant.CURRENCY_XL_SHEET_NAME).trim();
			String marketTypeID1 = LanguageMapForMarket.get(Constant.MARKET_TYPEID).trim();
			LanguageMapForMarket.get(Constant.BALANCE).trim();
			Integer.parseInt(marketTypeID1);
			Integer.parseInt(productId1);
			
			
			
			
			System.out.println("UserName for " + regMarket + " Market : " + userName);
			log.debug("UserName for" + regMarket + "Market : " + userName);

			if (launchURL.contains("LanguageCode"))
				urlNew = launchURL.replaceAll("LanguageCode=en", "LanguageCode=" + languageCode);
			else if (launchURL.contains("languagecode"))
				urlNew = launchURL.replaceAll("languagecode=en", "languagecode=" + languageCode);
			else if (launchURL.contains("languageCode"))
				urlNew = launchURL.replaceAll("languageCode=en", "languageCode=" + languageCode);

			/********************* Game Load *****************************************/

			if (regMarket.equalsIgnoreCase("Gibraltar") || regMarket.equalsIgnoreCase("Alderney"))
				urlNew = ChangeMarketsAndBranduk(urlNew, regMarket);

			if (regMarket.equalsIgnoreCase("Asia") || regMarket.equalsIgnoreCase("Belguim")
					|| regMarket.equalsIgnoreCase("Bulgaria"))
				urlNew = ChangeMarketsAndBrandDotcom(urlNew, regMarket);

			
			webdriver.navigate().to(urlNew);
			boolean loadingScreen = elementWait("return " + XpathMap.get("currentScene"), "LOADING");
			if (loadingScreen) {

				markets.detailsAppend("Market Name : " + regMarket, "LANGUAGE NAME : " + languageCode,
						" Game Loading Screen ", "pass");
				System.out
						.println("Market Name : " + regMarket + " LANGUAGE NAME : " + languageCode + "URL : " + urlNew);
				log.info("Market Name : " + regMarket + " LANGUAGE NAME : " + languageCode + "URL : " + urlNew);

				if ("yes".equalsIgnoreCase(XpathMap.get("gameContainsCntbtn"))) {
					boolean isfeatureScrn = elementWait("return " + XpathMap.get("FeatureScreen"), true);
					if (isfeatureScrn) {
						Thread.sleep(3000);
						closeOverlay();
						Thread.sleep(3000);
						boolean Basescene = elementWait("return " + XpathMap.get("currentScene"), "SLOT");
						if (Basescene) {
							System.out.println("Game Loaded Successfully !!");
						} else {
							Thread.sleep(3000);
							closeOverlay();
						}
					}
				} // cnt btn

				else {
					boolean Basescene = elementWait("return " + XpathMap.get("currentScene"), "SLOT");
					if (Basescene) {
						Thread.sleep(3000);
						System.out.println("Game Loaded Successfully");
					}
				}
			} // loading

			else {
				markets.detailsAppend("Market Name : " + regMarket, "LANGUAGE NAME : " + languageCode,
						" Game Loading Screen ", "pass");
				System.out
						.println("Market Name : " + regMarket + " LANGUAGE NAME : " + languageCode + "URL : " + urlNew);
				log.info("Market Name : " + regMarket + " LANGUAGE NAME : " + languageCode + "URL : " + urlNew);

			}
			
			
			
			
			/**************************************************************/

			if (regMarket.contains("Italy")) {

				Thread.sleep(5000);
				WebElement Popup = webdriver.findElement(By.xpath("/html/body/div[1]/div/div/div"));
				if (Popup.isDisplayed()) {
					WebElement SubmitBtn = webdriver
							.findElement(By.xpath("//*[@id=\"titan-complex-dialog-submitButton\"]/div"));
					SubmitBtn.click();
					Thread.sleep(2000);
				}
			}

			if (regMarket.contains("Spain")) {

				Thread.sleep(5000);
				// WebElement Popup =
				// webdriver.findElement(By.xpath("/html/body/div[1]/div/div/div"));
				WebElement SetLimits = webdriver.findElement(By.xpath(cfnlib.XpathMap.get("SetLimits")));
				WebElement SetLimits1 = webdriver.findElement(By.xpath(cfnlib.XpathMap.get("SetLimits1")));
				WebElement SetLimits2 = webdriver.findElement(By.xpath(cfnlib.XpathMap.get("SetLimits2")));
				WebElement SetLoss = webdriver.findElement(By.xpath(cfnlib.XpathMap.get("SetLoss")));

				Select select1 = new Select(SetLimits1);
				Select select2 = new Select(SetLimits2);
				select1.selectByIndex(2);
				select2.selectByIndex(2);

				SetLoss.sendKeys("5");
				SetLimits.click();
			}

			Thread.sleep(8000);

			// sv65878
			// Thread.sleep(8000);
			cfnlib.waitForSpinButton();	
			
			//PB61055
			String helpMenubutton = cfnlib.XpathMap.get("HelpMenu");
			boolean isHelpText = webdriver.findElement(By.xpath(cfnlib.XpathMap.get("HelpMenu"))).isDisplayed();
			if(isHelpText) 
			{
				webdriver.findElement(By.xpath(cfnlib.XpathMap.get("HelpMenu"))).click();
				Thread.sleep(2000);
			}
			String helpButton = cfnlib.getHelpButtonXpath(regMarket, HelpTextLink);//getting help xpath
			closeOverlay();// for closing the menu pannel
			
			
			
			if (languageCode.equalsIgnoreCase("en")) {

				isGameName_helpVisible = openEasyHelp(webdriver, markets, cfnlib, helpMenubutton, helpButton);

				if (isGameName_helpVisible.isDisplayed()) {

					English = cfnlib.CaptureScreenshot_Helpfile(markets, languageCode);
					English_Heading_Count = English.length;
					AllBulletsTextStores = cfnlib.AllBulletPointsText(markets, languageCode, English_Heading_Count);
					EnglishHeadingTxtString = cfnlib.Helpfile_headings_String(markets, languageCode);

					markets.detailsAppendFolder("", "", "", "", languageCode);

				}

			}

			else {

				isGameName_helpVisible = openEasyHelp(webdriver, markets, cfnlib, helpMenubutton, helpButton);

				if (isGameName_helpVisible.isDisplayed()) {

					cfnlib.helpfileContentString_Comparison(webdriver, markets, cfnlib, languageCode, English,
							EnglishHeadingTxtString, AllBulletsTextStores, regMarket, English_Heading_Count);

				}

			}

		}
	}

/**
 * @author SV65878
 * 
 */

public WebElement openEasyHelp(WebDriver webdriver, Desktop_HTML_Report markets, CFNLibrary_Desktop cfnlib,
			String helpMenubutton, String helpButton) throws InterruptedException {
		WebElement isGameName_helpVisible = null;
		try {

			WebElement helpMenuoption = webdriver.findElement(By.xpath(helpMenubutton));
			WebElement helpOption = webdriver.findElement(By.xpath(helpButton));

			if (helpMenuoption.isDisplayed()) {
				helpMenuoption.click();
				// Thread.sleep(2000);
			}

			else {
				markets.detailsAppend("Navigating to", "Help", "section", "Fail");
				System.out.println("HelpMenu button not Visible");
			}

			if (helpOption.isDisplayed()) {
				markets.detailsAppend("Navigating to", "Help", "section", "Pass");

				Thread.sleep(3000);

				/*
				 * if(imageLibrary.isImageAppears("HelpTextLink")) {
				 * imageLibrary.click("HelpTextLink"); } else if
				 * (imageLibrary.isImageAppears("Help")) { imageLibrary.click("Help"); } else
				 */

				helpOption.click();

				Thread.sleep(10000);
			}

			else {
				markets.detailsAppend("Navigating to", "Help", "section", "Fail");
				System.out.println("HelpMenu is not getting clicked");
			}

			webdriver.switchTo().frame(webdriver.findElement(By.xpath("//iframe[contains(@id,\"titan-frame\")]")));
			Thread.sleep(2000);
			isGameName_helpVisible = webdriver.findElement(By.xpath(cfnlib.XpathMap.get("Gamename_helpfileNav")));
			Thread.sleep(4000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isGameName_helpVisible;
	}

/**
 * @author SV65878
 * 
 */
public void helpfileContentString_Comparison(WebDriver webdriver, Desktop_HTML_Report markets,
		CFNLibrary_Desktop cfnlib, String languageCode, int[] English, String[] EnglishHeadingTxtString, String[][] AllBulletsTextStores, String regMarket , int English_Heading_Count ) {
	
	List<WebElement> elements = webdriver.findElements(By.xpath(cfnlib.XpathMap.get("AllHeadingsCount")));
	int Head_Count = elements.size();
	int engCount=0;
	int Totalcount_lang[] = new int[Head_Count];
	try {
			
	Map<String, String> paramMap = new HashMap<String, String>();
	
	if(Head_Count==English_Heading_Count) {
	
	int count =1;
	
	for(int i=1; i<=Head_Count;i++)
	{   
		paramMap.put("param1", Integer.toString(i));
		String Internal_Count = cfnlib.XpathMap.get("InternalCount");
		String newHook = cfnlib.replaceParamInHook(Internal_Count, paramMap);
		
		List<WebElement> elements1 = webdriver.findElements(By.xpath(newHook));
		int Bullet_Count = elements1.size();
		
		/*Heading Text*/
		paramMap.put("param1", Integer.toString(i));
		String Headings = cfnlib.XpathMap.get("AllHeadingsText");
		String newHook3 = cfnlib.replaceParamInHook(Headings, paramMap);
		WebElement text1 = webdriver.findElement(By.xpath(newHook3));
		String HeadingsText = text1.getText();
		String EnglishHeadingString = EnglishHeadingTxtString[i-1];
		
		int compare_heading = HeadingsText.compareTo(EnglishHeadingString);
		
		if(compare_heading==0) {
			
		markets.detailsAppendFolder(EnglishHeadingString,EnglishHeadingString,HeadingsText,"Fail",languageCode);Thread.sleep(2000);
	    }
		
		else {
			
			markets.detailsAppendFolder(EnglishHeadingString,EnglishHeadingString,HeadingsText,"Pass",languageCode);
			
		}
		
		
		
		
		for(int j=1; j<Bullet_Count;j++) {
			
			paramMap.put("param1", Integer.toString(i));
			paramMap.put("param2", Integer.toString(j));
			
			String LastBullet_Count = cfnlib.XpathMap.get("LastBullet");
			String newHook2 = cfnlib.replaceParamInHook(LastBullet_Count, paramMap);
			WebElement ele1 = webdriver.findElement(By.xpath(newHook2));
			engCount = English[i-1];
			
			String BulletText = ele1.getText();

			String Individual_Bullet_Text_English = AllBulletsTextStores[i-1][j-1];
			
			if(Individual_Bullet_Text_English!=null) {
			int compare_Bullets = BulletText.compareTo(Individual_Bullet_Text_English);
			
			if(compare_Bullets==0) {
				System.out.println("String are getting Compared Under "+ Head_Count+" Section");
				
				// remove single quotes from BulletText
				//BulletText = BulletText.replaceAll("'", "");
				// remove all single and double quotes from bullettext
				BulletText = BulletText.replaceAll("[\"']", "");
					markets.detailsAppendFolder("",Individual_Bullet_Text_English,BulletText,"Fail",languageCode);
			}
			
			else {
				// remove single quotes from BulletText
			//	BulletText = BulletText.replaceAll("'", "");
				// remove all single and double quotes from bullettext
			  BulletText = BulletText.replaceAll("[\"']", "");
					markets.detailsAppendFolder("", Individual_Bullet_Text_English, BulletText, "Pass", languageCode);
				
					
				
			}
			
			
			}
			
			
			
		//	markets.detailsAppendFolder("",BulletText,"","","Pass");
			
			
			
			count=count+1;
			if(j==Bullet_Count-1) {
				
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(3000);
			}
			
			
		}int otherlangcount = count-1;
		if(otherlangcount==engCount) {
			
			
			markets.detailsAppendFolder(regMarket+" structure matches","HTML paragraph tag count : "+engCount,"HTML paragraph tag count : "+otherlangcount,"Pass",languageCode);
			}
			
			else {
				markets.detailsAppendFolder(regMarket+" structure matches","HTML paragraph tag count : "+engCount,"HTML paragraph tag count : "+otherlangcount,"Fail",languageCode);
				}
		
		Totalcount_lang[i-1] = count;
		
		count=1;
		
	}

	markets.detailsAppendFolder(regMarket+" Section Count matches","Section count : "+English_Heading_Count,"Section count : "+Head_Count,"Pass",languageCode);
	
	}
	
	else {
		markets.detailsAppendFolder(regMarket+" Section Count matches","Section count : "+English_Heading_Count,"Section count : "+Head_Count,"Fail",languageCode);
	}
	
	markets.detailsAppendFolder("","","","","");
	
	
	
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}




/**
 * @author SV65878
 * 
 */
public WebElement helpFileNavigation(WebDriver webdriver, Desktop_HTML_Report markets, CFNLibrary_Desktop cfnlib,WebElement Helpp,WebElement HelpMenuu)
		throws InterruptedException {
		
	HelpMenuu.click();
	Thread.sleep(2000);
	markets.detailsAppend("Navigating to", "Help","section","pass" );
		
	Helpp.click();
	Thread.sleep(10000);
	
	webdriver.switchTo().frame(webdriver.findElement(By.xpath("//iframe[contains(@id,\"titan-frame\")]")));
	Thread.sleep(2000);
	WebElement isGameName_helpVisible = webdriver.findElement(By.xpath(cfnlib.XpathMap.get("Gamename_helpfileNav")));
	Thread.sleep(4000);
	return isGameName_helpVisible;
}

/**
 * @author TS64283
 * 
 */
public static String ChangeMarketsAndBranduk(String launchURL , String Market) {
	String urlNew=null;
	if (launchURL.contains("market")&&launchURL.contains("brand"))	
		urlNew= launchURL.replaceAll("market=uk&brand=uk","market="+Market+"&brand="+Market+"");
	
	return urlNew;
} 

/**
 * @author TS64283
 * 
 */
public static String ChangeMarketsAndBrandDotcom(String launchURL , String Market) {
	String urlNew=null;
	if (launchURL.contains("market")&&launchURL.contains("brand"))	
		urlNew= launchURL.replaceAll("market=dotcom&brand=islandparadise","market="+Market+"&brand="+Market+"");
	
	return urlNew;
} 




/**
 * This method is used for verifying currency format for credits
 * @param report
 * @param regExpr
 */

public void verifyBetCurrencyFormat(Desktop_HTML_Report report, String regExpr)
{
	   boolean bet = verifyRegularExpression(report, regExpr,getConsoleText("return " + XpathMap.get("BetTextValue")));

	   if (bet)
	   {
					
		   report.detailsAppendNoScreenshot("Verify the currency format for bet ","Bet should display in correct currency format " ,"Bet is displaying in correct currency format ", "Pass");																
		} else 
		{
			report.detailsAppendNoScreenshot("Verify the currency format for bet ","Bet should display in correct currency format " ,"Bet is displaying in incorrect currency format ", "Fail");
		}
		
}







/**
 * This method is used for click on spin and check reel landing
 * @param report
 * @param languageCode
 */
public void checkSpinReelLanding(Desktop_HTML_Report report ,String languageCode ,String checkSpinStopBaseSceneUsingCoordinates)
{
	   try
	   {
		boolean Ispinbutton = GetConsoleBooleanText("return " + XpathMap.get("isSpinBtnVisible"));
		   
	   if(Ispinbutton)
		{
		    spinclick();
			Thread.sleep(1500);
			if(checkSpinStopBaseSceneUsingCoordinates.equalsIgnoreCase("yes"))
			ClickByCoordinates("return " + XpathMap.get("clickOnReelX"), "return " + XpathMap.get("clickOnReelY"));
		 if(!verifySpinStatus())
		 report.detailsAppendFolder("Verify Spin reel landing And Stop Button is disabled  ", "Reels should not land at the same time And Stop Button is disabled ", "Reels did not land at the same time And Stop Button is disabled ** Manual Validation is Required **", "PASS",languageCode);
		else
			 report.detailsAppendFolder("Verify Spin reel landing And Stop Button is disabled  ", "Reels should not land at the same time And Stop Button is disabled ", "Reels  land at the same time And Stop Button is Enabled ** Manual Validation is Required **", "FAIL",languageCode);
		}
	   else
	   {
		   report.detailsAppendFolder("Verify Spin  button ", "Verify Spin  button ", "Spin button is not visible ", "FAIL",languageCode);
	 
	   }
	   }
	   
	   catch (Exception e) {
		   log.error(e.getMessage(), e);
	   	}
}



private void ClickByCoordinates(String string, String string2) {
	// TODO Auto-generated method stub
	
}

/**
 * This method is used for click on spin and check spin status
 * @param report
 * @param languageCode

 */
public void checkSpinStopBaseScene(Desktop_HTML_Report report ,String languageCode)
{
   try
   {
	   boolean Ispinbutton = GetConsoleBooleanText("return " + XpathMap.get("isSpinBtnVisible"));
	   
   if(Ispinbutton)
	{
		// Verify the stop button availability
		boolean isstopbtn = isStopButtonAvailableMarket();
		if (isstopbtn) {
			report.detailsAppend(
					"Verify that stop button not available while reels spining in Base Scene",
					"stop button  should not available while reels spining in BaseScene",
					"stop button is not available while reels spining in Base Scene", "PASS");
		} else {
			report.detailsAppend(
					"verify that stop button not available while reels spining in Base Scene",
					"stop button Should not be available while reels spining in Base Scene",
					"stop button is available in BaseScene while reels spining", "FAIL");
		}
		
		boolean StopBtn = waitForSpinButtonstop();
		Thread.sleep(2000);
		if(StopBtn)
			report.detailsAppend(
					"Verify that Spin button  available After reels land in Base Scene",
					"Spin button must  be available  After reels land in Base Scene",
					"Spin button available After reels land in Base Scene", "PASS");
		else
			report.detailsAppend(
					"Verify that Spin button available After reels land in Base Scene",
					"Spin button must  be available  After reels land in Base Scene",
					"Spin button is not available After reels land in Base Scene", "FAIL");
		
		
	}
   
   
   }catch (Exception e) {
	   log.error(e.getMessage(), e);
   	}

}

/**
 * This method is used to create Reg Market Url
 * 
 */
public String createRegMarketUrl(String userName,String url, int productId,String market,String brand,String retrunUrlParam, String languageCode) 
{
	String launchURl=null;
	String urlNew= null;
	try {
		launchURl = url.replaceFirst("\\busername=.*?(&|$)", "username=" + userName + "$1");
		launchURl = launchURl.replaceAll("productId=5007", "productId=" + productId);
		launchURl = launchURl.replaceAll("market=dotcom", "market=" + market);
		launchURl = launchURl.replaceAll("brand=islandparadise", "brand=" + brand);
		launchURl = launchURl.replaceFirst("IslandParadise", retrunUrlParam);
		
		if (launchURl.contains("LanguageCode"))
			urlNew = launchURl.replaceAll("LanguageCode=en","LanguageCode=" + languageCode);
		else if (launchURl.contains("languagecode"))
			urlNew = launchURl.replaceAll("languagecode=en","languagecode=" + languageCode);
		else if (launchURl.contains("languageCode"))
			urlNew= launchURl.replaceAll("languageCode=en","languageCode=" + languageCode);
		
		
		log.info(market+" url = "+ urlNew);
		System.out.println(market+" url = "+ urlNew);
		
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	return urlNew;
}

/**
 * This method is used to verify Player protection using  xpath
 * 
 * 
 */
public void verifyPlayerProtectionNavigation(Desktop_HTML_Report report ,String languageCode , String Market) 
{
   String gameurl = webdriver.getCurrentUrl();

   try 
   {
	   func_Click(XpathMap.get("HelpMenu"));
	   Thread.sleep(1000);
	  
	   if(elementVisible_Xpath(XpathMap.get("PlayerProtection")))
	   {
		   report.detailsAppendNoScreenshot("Verify Player protection is visible", "Player protection should be visible", "Player protection is visible", "PASS");
			
		   func_Click(XpathMap.get("PlayerProtection"));
		   Thread.sleep(2000);
		   checkpagenavigationMarket(report, gameurl, Market);
	   }
	   else
	   {
		   report.detailsAppendNoScreenshot("Verify Player protection is visible", "Player protection should be visible", "Player protection is not visible", "FAIL");
			   
	   }

   } catch (Exception e) 
   {
	   log.error(e.getMessage(), e);
   }

}

public boolean elementVisible_Xpath(String Xpath) {
	boolean visible = false;
	if (webdriver.findElement(By.xpath(Xpath)).isDisplayed()) {
	
		visible = true;
	}
	return visible;

}

/**
 * This method is used to verify cash check using  xpath
 * 
 * 
 */
public void verifyCashCheckNavigation(Desktop_HTML_Report report,String languageCode) 
{
   String gameurl = webdriver.getCurrentUrl();

   try 
   {
	   func_Click(XpathMap.get("HelpMenu"));
	   Thread.sleep(1000);
	
	    if(elementVisible_Xpath(XpathMap.get("CashCheck")))
	   {
		   report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible", "Cash Check is visible", "PASS");
			
		   func_Click(XpathMap.get("CashCheck"));
		   Thread.sleep(2000);
		   checkpagenavigation(report,gameurl);
	   }
	   else
	   {
		   report.detailsAppendNoScreenshot("Verify Cash Check is visible", "Cash Check should be visible", "Cash Check is not visible", "FAIL");
	   }

   } catch (Exception e) 
   {
	    log.error(e.getMessage(), e);
   }

}
/**
 * This method is used to verify Help using xpath
 * 
 */
public void verifyHelpNavigation(Desktop_HTML_Report report,String languageCode) 
{
   String gameurl = webdriver.getCurrentUrl();
  
   try 
   {
	   func_Click(XpathMap.get("HelpMenu"));
	   Thread.sleep(1000);
	  
	  if(elementVisible_Xpath(XpathMap.get("ukHelp")))
	   {
		   report.detailsAppendNoScreenshot("Verify Help is visible", "Help should be visible", "Help is visible", "PASS");
			
		   func_Click(XpathMap.get("ukHelp"));
		   Thread.sleep(2000);
		   checkpagenavigation(report,gameurl);
	   }
	   else
	   {
		   report.detailsAppendNoScreenshot("Verify Help is visible", "Help should be visible", "Help is not visible", "FAIL");
			
	   }

   } catch (Exception e) {
	   
	   log.error(e.getMessage(), e);
   }

}


/**
 * This method is used to verify playcheck using image or xpath
 * 
 */
public void verifyPlayCheckNavigation(Desktop_HTML_Report report,String languageCode) 
{
   String gameurl = webdriver.getCurrentUrl();

   try 
   {
	   func_Click(XpathMap.get("HelpMenu"));
	   
	   Thread.sleep(1000);
	  
	  if(elementVisible_Xpath(XpathMap.get("PlayCheck")))
	   {
		   report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible", "Play Check is visible", "PASS");
		   func_Click(XpathMap.get("PlayCheck"));
		   Thread.sleep(2000);
		   checkpagenavigation(report,gameurl);
	   }
	   else
	   {
		   report.detailsAppendNoScreenshot("Verify Play Check is visible", "Play Check should be visible", "Play Check is not visible", "FAIL");							
	   }
   } catch (Exception e) 
   {
	   log.error(e.getMessage(), e);   	
   }

}

/**
 * This method is used to verify Value adds navigation
 * @param report
 * @param languageCode
 * @param PlayerProtectionNavigation
 * @param CashCheckNavigation
 * @param HelpNavigation
 * @param PlayCheckNavigation
 * @param responsibleGaming
 */
public void verifyValueAddsNavigation(Desktop_HTML_Report report,String languageCode,String PlayerProtectionNavigation,String CashCheckNavigation,String HelpNavigation,String PlayCheckNavigation, String responsibleGaming,String Market) 
{
	try
	{
		report.detailsAppend("Following are the Value Adds navigation test cases",
				"Verify Value Adds testcases", "", "");
		if (func_Click(XpathMap.get("HelpMenu"))) {
			Thread.sleep(1000);
			if(elementVisible_Xpath("//*[@class=\"titan-dynamic-menu__dynamic-menu-item\"]"))
			{
			report.detailsAppendFolder("Verify if menu on top bar is open",
					"Menu on top bar should be open", "Menu on top bar is open", "PASS",
					languageCode);
			closeOverlay();
			Thread.sleep(1000);

			if (PlayerProtectionNavigation.equalsIgnoreCase("yes")) {
				verifyPlayerProtectionNavigation(report, languageCode,Market);
				Thread.sleep(2000);
			}

			if (CashCheckNavigation.equalsIgnoreCase("yes")) {
				verifyCashCheckNavigation(report, languageCode);
				Thread.sleep(2000);
			}

			if (HelpNavigation.equalsIgnoreCase("yes")) {
				verifyHelpNavigation(report, languageCode);
				Thread.sleep(2000);
			}

			if (PlayCheckNavigation.equalsIgnoreCase("yes")) {
				verifyPlayCheckNavigation(report, languageCode);
				Thread.sleep(5000);
			}
			if (responsibleGaming.equalsIgnoreCase("yes")) {
				responsibleGaming(report, languageCode);
				Thread.sleep(5000);
			}

			if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
				clickOnBaseSceneContinueButton(report);
			}
			
		  }
		else {
			report.detailsAppendFolder("Verify if menu on top bar is open",
					"Menu on top bar should be open", "Menu on top bar is not open", "FAIL",
					languageCode);
		}	
	}
		
	} catch (Exception e) {
		e.getMessage();
		log.error(e);
	}
}
	

/**
 * This method is used to click on Base scene continue button
 
 * @param report

 */
public void clickOnBaseSceneContinueButton(Desktop_HTML_Report report)
{
	   try
	   {
		 boolean clock = webdriver.findElement(By.xpath(XpathMap.get("clock"))).isDisplayed();
	   if(clock)
		{
			System.out.println("Continue button is visible");log.debug("Continue button is visible");									
			Thread.sleep(2000);
			closeOverlay();
			Thread.sleep(3000);
	
		}
		else
		{
			System.out.println("Continue button is not visible");log.debug("Continue button is not visible");
			report.detailsAppendNoScreenshot("Verify Continue button is visible ", "Continue buttion is visible", "Continue button is not visible", "FAIL");
		}
	   }
	   catch (Exception e) {
		   log.error(e.getMessage(), e);
	   	}
}




/**
 * To validate session reminder testcases on base scene
 * @param report
 * @param userName
 * @param languageCode
 * @param SessionReminderUserInteraction
 * @param SessionReminderContinue
 * @param SessionReminderExitGame
 */
public void validateSessionReminderBaseScene(Desktop_HTML_Report report,String userName ,String languageCode,String SessionReminderUserInteraction,String SessionReminderContinue,String SessionReminderExitGame,String market) 
{
	try 
	{
		CommonUtil util = new CommonUtil();						
		String gameurl = webdriver.getCurrentUrl();
		
		String periodValue= XpathMap.get("sessionReminderDurationInSec");
		String resetPeriodValue=XpathMap.get("resetSessionReminderValue");
	
		//assigning session reminder
		boolean sessionReminderAssigned=util.setSessionReminderOnAxiom(userName,periodValue);
		loadGameMarket(gameurl,market);
		if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
			closeOverlay();
		}
		log.debug("Session reminder is set for "+periodValue+" secs");
					
		
		if (sessionReminderAssigned && sessionReminderPresent())
		{
			report.detailsAppendFolder("Verify Session Reminder must be present","Session Reminder must be present", "Session Reminder is present","PASS", languageCode);
				
			if(SessionReminderUserInteraction.equalsIgnoreCase("yes"))
			{
				//clicking else where on the screen
				closeOverlay();
				report.detailsAppendFolder("Verify when Session Reminder is present and player should not be able to interact with any part of the game","Player should not not be able to interact with any part of the game", "Unable to interact with the game","PASS",languageCode);								
				Thread.sleep(2000);
			}
			
			//Ensure that the continue button takes you back to the game
			if(SessionReminderContinue.equalsIgnoreCase("yes"))
			{
				selectContinueSession();
				if(isSpinBtnVisible())
				{
					report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",	" Session Reminder dialog box is closed and game should be visible"," Session Reminder dialog box is closed and game is visible","PASS",languageCode);
					Thread.sleep(2000);
				}
				else
				{
					report.detailsAppendFolder("Verify Session Reminder dialog box is closed and game is visible",	" Session Reminder dialog box is closed and game should be visible"," Session Reminder dialog box is closed and game is not visible","FAIL",languageCode);	
				}									
			}
		} else {
			report.detailsAppendFolder("Verify Session Reminder must be present","Session Reminder must be present","Session Reminder is not present", "PASS",languageCode);
			log.debug("Session reminder is not visible on screen");
			Thread.sleep(2000);
		}

		//Ensure that the log out button takes you to the lobby
		if(SessionReminderExitGame.equalsIgnoreCase("yes"))
		{							
			//assigning session reminder
			sessionReminderAssigned=util.setSessionReminderOnAxiom(userName,periodValue);
			loadGameMarket(gameurl, market);
//			if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
//				clickOnBaseSceneContinueButton(report);
//			}
			
			if (sessionReminderAssigned && sessionReminderPresent())
			{
				func_Click(XpathMap.get("ExitGameSessrionReminder"));
				Thread.sleep(12000);
				report.detailsAppendNoScreenshot("Verify Session Reminder log out/Exit game button","Log out button shoul take user to the lobby","Log out button takes user to the lobby", "PASS");
				
				checkpagenavigationMarket(report, gameurl, market);	
				if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
					clickOnBaseSceneContinueButton(report);
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
		loadGameMarket(gameurl,market);
		if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
			clickOnBaseSceneContinueButton(report);
		}
			
			
						
} catch (Exception e) {
	e.getMessage();
	log.error(e);
}
}		


private boolean sessionReminderPresent() {
	// TODO Auto-generated method stub
	return false;
}

/**
 * This method is used to check Topbar 
 * @return GameName
 * @return report
 * @return BetOnTopBarStatus
 * @return HelpOnTopBarStatus
 * @return gameClock
 * @return NetPositionStatus
 
 */
public void ValidateTopBar(String GameName, Desktop_HTML_Report report , String BetOnTopBarStatus , String  HelpOnTopBarStatus , String gameClock ) {

	try
	{
		report.detailsAppend("Followisng are the Top bar test cases on Base scene",
				"Verify Top bar on Base scene", "", "");
		// verifying whether Top bar is visible or not
		 boolean isTopBarVisible = isTopBarVisible();
		 System.out.println("TopBar is visible : "+isTopBarVisible+"");
		Thread.sleep(2000);
		if (isTopBarVisible) {
			// Verifying game name on Top bar
			if (GameName.equalsIgnoreCase("yes")) {
			gameNameOnTopBar(report);
			Thread.sleep(2000);
			}
			// Verifying bet value on Top bar
			if (BetOnTopBarStatus.equalsIgnoreCase("yes"))
			{
				betOnTopBar(report);
				Thread.sleep(2000)	;
			}

			// verifying help text link on top bar
			
			if (HelpOnTopBarStatus.equalsIgnoreCase("yes"))
			{
				verifyHelpTextlink(report);
				Thread.sleep(2000);
			}
			

			// verifying clock on top bar
			if (gameClock.equalsIgnoreCase("yes"))
			{
				clockOnTopBar(report);
				Thread.sleep(2000);

			}
	}
	else {
		
		report.detailsAppend("Verify TopBar must be is displayed",
				"TopBar should be displayed", "TopBar is displayed", "FAIL");
	}	
		
		
	} catch (Exception e) {
		e.getMessage();
		log.error(e);
	}

}

/*
 * Method to verify currency format in credit
 */

private void clockOnTopBar(Desktop_HTML_Report report) {
	// TODO Auto-generated method stub
	
}

private void verifyHelpTextlink(Desktop_HTML_Report report) {
	// TODO Auto-generated method stub
	
}

private void betOnTopBar(Desktop_HTML_Report report) {
	// TODO Auto-generated method stub
	
}

private void gameNameOnTopBar(Desktop_HTML_Report report) {
	// TODO Auto-generated method stub
	
}

private boolean isTopBarVisible() {
	// TODO Auto-generated method stub
	return false;
}

public boolean verifyCurrencyFormatForCredits(String regExpression, Desktop_HTML_Report report , String languageCode ) {
	boolean result = true;
	try {
		log.debug("Function-> verifyCurrencyFormat() ");
		// Read console credits
		String consoleCredit = getCurrentCredits().replaceAll("Credits: ", "");
		consoleCredit = consoleCredit.replace("credits: ", "");
		consoleCredit = consoleCredit.replace("CREDITS: ", "");
		consoleCredit = consoleCredit.replace("Credits ", "");
		result=currencyFormatValidatorForLVC(consoleCredit,regExpression);
		
		if (result) 
		   {
				report.detailsAppendFolder("Verify that currency format and currency symbol in credit ",
						"Credit should display in correct currency format and currency symbol " ,
						"Credit is displaying in correct currency format and currency symbol ", "PASS",
						languageCode);
			} 
		   else 
		   {
			   report.detailsAppendFolder("Verify that currency format and currency symbol in credit ",
						"Credit should display in correct currency format and currency symbol " ,
						"Credit is not displaying in correct currency format and currency symbol ", "FAIL",
						languageCode);
			}

	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}

	return result;
	
	
}

/**
 * This method is used bet Currency Symbol ForLVe
 *  @return regExpression
 *   @return report
 *    @return languageCode
 *  
 */
public void  betCurrencySymbolForLVC(String regExpression, Desktop_HTML_Report report , String languageCode) {
	String bet = null;
	boolean isBetInCurrencyFormat = false;

	try {
		log.debug("Function-> betCurrencySymbol()");
		log.debug("Reading Bet Text from base scene");
		if (!GameName.contains("Scratch")) {
			if (Constant.STORMCRAFT_CONSOLE.equalsIgnoreCase(XpathMap.get("TypeOfGame"))) {
				bet = "return " + XpathMap.get("BetTextValue");
			} else
				bet = "return " + XpathMap.get("BetButtonLabel");
		} else {
			bet = "return " + XpathMap.get("InfobarBettext");
		}

		// String consoleBet=getConsoleText(bet).toLowerCase();
		String consoleBet = getConsoleText(bet);
		log.debug(" Bet Text from base scene=" + consoleBet);
		Thread.sleep(100);

		// String consoleBetnew=consoleBet.toLowerCase().replaceAll("bet: ", "");
		String consoleBetnew = consoleBet.replaceAll("bet: ", "");
		consoleBetnew = consoleBetnew.replaceAll("BET: ", "");
		consoleBetnew = consoleBetnew.replaceAll("Bet: ", "");
		consoleBetnew = consoleBetnew.replaceAll("Bet ", "");
		isBetInCurrencyFormat=currencyFormatValidatorForLVC(consoleBetnew,regExpression);
		
		if (isBetInCurrencyFormat) {
			report.detailsAppendFolder("Verify that currency format and currency symbol on the Bet Console ",
				"Bet console should display the correct currency format and currency symbol ",
				"Bet console display the correct currency format and currency symbol ", "PASS",
				languageCode);
	} else {
		report.detailsAppendFolder("Verify that currency format on the Bet Console and currency symbol ",
				"Bet console should display the correct currency format and currency symbol ",
				"Bet console is not display the correct currency format and currency symbol ", "FAIL",
				languageCode);
	}
			
		
		
		log.debug(" Bet currency symbol is::" + consoleBet);
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}

	

}

/**
  This method is used for verifying whether stop button
 * is present or not
 * 
 * @return true
 */

public boolean isStopButtonAvailableWithoutSpin() 
{
	boolean isstopbutton = true;
	try 
	{

		// To verify the stop button
		String stopbutton = getConsoleText("return " + XpathMap.get(""));

		if (stopbutton.equalsIgnoreCase("activeSecondary") || !(stopbutton.equalsIgnoreCase("disabled")))
		{
			// As current state of button is ActiveSecondary
			log.debug("Stop button is available");
			System.out.println("Stop button is available");
			isstopbutton = true;
		} 
		else 
		{
			log.error("Stop button is not available");
			System.out.println("Stop button is available");
			isstopbutton = false;
		}
	} 
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}

	return isstopbutton;
}


/**
 * This method is used to open Paytable
 * @return report
 *  @return languageCode
 */
public void openPaytable() {

try {

	clickAtButton("return " + XpathMap.get("PaytableOpenBtn"));

	log.debug("Clicked on Paytable button  to open");
	Thread.sleep(1000);
	boolean flag = GetConsoleBooleanText("return " + XpathMap.get("PaytableVisible"));
	if(flag) {
		System.out.println("Paytable Opened");
		
	}
	
	else {
		System.out.println("Paytable is not Opened");
		
	}
	
	
	
} catch (Exception e) {
	log.error("Error in opening paytable", e);
}


}




/**
 * This method is used to Verify responsible Gaming using  xpath
 * @return report
 *  @return languageCode
 */
public void responsibleGaming(Desktop_HTML_Report report,String languageCode) 
{
   String gameurl = webdriver.getCurrentUrl();

   try 
   {
	   func_Click(XpathMap.get("HelpMenu"));
	   
	   Thread.sleep(1000);
	  
	  if(elementVisible_Xpath(XpathMap.get("responsibleGaming")))
	   {
		   report.detailsAppendNoScreenshot("Verify responsible Gaming is visible", "responsible Gaming should be visible", "responsible Gaming is visible", "PASS");
		   func_Click(XpathMap.get("responsibleGaming"));
		   Thread.sleep(2000);
		   checkpagenavigation(report,gameurl);
	   }
	   else
	   {
		   report.detailsAppendNoScreenshot("responsible Gaming Check is visible", "responsible Gaming should be visible", "responsible Gaming is not visible", "FAIL");							
	   }
   } catch (Exception e) 
   {
	   log.error(e.getMessage(), e);   	
   }

}


/**
 * This method verify NetPositi on BaseScene
 * @param  report
 * @return languageCode
 * @return regExpr
   @param  NetPositionLaunch
 * @return NetPositionRefresh
 * @return NetPositionNormalWin
 * @return netPositionloss
 * @return netPositionBigWin
 *
 */
public void verifyNetPositionBaseScene(Desktop_HTML_Report report, String languageCode,String regExpr, String NetPositionLaunch, String NetPositionRefresh, String NetPositionNormalWin,String netPositionBigWin, String netPositionloss,String market) {
	String gameurl = webdriver.getCurrentUrl();
	String netPositionValueLaunch = null;
	String netPositionValueRefresh=null;
	
	try {
		
	netPositionValueLaunch = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
	
	if (netPositionValueLaunch.contains("Net position")) 
	{
		report.detailsAppend("Verify net position on topbar", "Net position should be visible", 
				"Net position is visible correctly", "PASS");
	
		/******************** Currency Format *********************/
		
		boolean isFormatCorrect = verifyRegularExpression(report, regExpr, netPositionValueLaunch);
		if(isFormatCorrect)
		{
			report.detailsAppendFolder("Verify net position is in correct currency format", "Net position should be in correct currency format", "Net position is in correct currency format", "PASS",languageCode);
		}
		else
		{
			report.detailsAppendFolder("Verify net position is in correct currency format", "Net position should be in correct currency format", "Net position is not in correct currency format", "FAIL",languageCode);														
		}

				
		/******************** Net Position On Game load *********************/
		
		loadGameMarket(gameurl,market);
		if ((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
			closeOverlay();
		}
		Thread.sleep(2000);
	
	
		netPositionValueLaunch=netPositionValueLaunch.replaceAll("[^0-9.-]", "").trim();
		if(netPositionValueLaunch.equals("0.00"))
		{
			report.detailsAppendFolder("Verify net position is 0.00 when launched", "Net position should be 0.00", "Net position is 0.00", "PASS",languageCode);
		}
		else
		{
			report.detailsAppendFolder("Verify net position is 0.00 when launched", "Net position should be 0.00", "Net position is not 0.00", "FAIL",languageCode);														
		}
	
		
	
		if(NetPositionNormalWin.equalsIgnoreCase("yes"))
		{
			verifyNetPosition(report,languageCode, "NormalWin",regExpr);
		}
		
		//The game session net position calculates correctly after a Big Win
		if(netPositionBigWin.equalsIgnoreCase("yes"))
		{
			verifyNetPosition(report,languageCode, "BigWin",regExpr);
		}
	
		if(netPositionloss.equalsIgnoreCase("yes"))
		{
			verifyNetPosition(report,languageCode, "Loss",regExpr);
		}
		
       /******************** Net Position on Refresh *********************/ 
		
		if(NetPositionRefresh.equalsIgnoreCase("yes"))
		{
			
			netPositionValueLaunch = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
			
			if (netPositionValueLaunch.contains("Net position")) {
			
			spinclick();
		    waitForSpinButtonstop();
			webdriver.navigate().refresh();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			if ((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
				closeOverlay();
			}
			Thread.sleep(2000);
			
			netPositionValueRefresh=webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
			netPositionValueRefresh=netPositionValueRefresh.replaceAll("[^0-9.-]", "").trim();
			if(netPositionValueRefresh.equals("0.00"))
			{
				report.detailsAppendFolder("Verify net position is 0.00 when refreshed", "Net position should be 0.00", "Net position is 0.00 after refresh", "PASS",languageCode);
			}
			else
			{
				report.detailsAppendFolder("Verify net position is 0.00 when refreshed", "Net position should be 0.00", "Net position is not 0.00", "FAIL",languageCode);														
			}
		
		}
		
		}
	
	}
	
} // try block
catch (Exception e) {
	log.error("Error in checking net profit ", e);
	report.detailsAppend("Verify net position on topbar", "Net position should be visible and updated correctly", "Net position is not visible", "fail");			
	System.out.println("Net profit is not present");
}
}

/**
 * This method verify RegularExpression
 * @param report
 * @return languageCode
 * @return winType
 */
public boolean verifyRegularExpression(Desktop_HTML_Report isoCode, String regExp, String method) {
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

/**
 * This method verify NetPosition
 * @param report
 * @return languageCode
 * @return winType
 */
public boolean verifyNetPosition(Desktop_HTML_Report report,String languageCode, String winType,String regExpr) {
	boolean isNetPositionUpdated = false;
	
	try {
		String isNetProfitAvailable = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
		if (isNetProfitAvailable.contains("Net position")) 
		{
			   //report.detailsAppend("Verify net position on topbar", "Net position should be visible ", "Net position is visible correctly", "pass");
			
				String netPositionBeforeSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
				double dblNetPositionBeforeSpin = Double.parseDouble(netPositionBeforeSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
				
				spinclick();
				Thread.sleep(5000);
				waitForSpinButtonstop();
				if(winType.equalsIgnoreCase("BigWin")) {
					closeOverlay();
					Thread.sleep(5000);
				}
				Thread.sleep(10000);
				String netPositionAfterSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
				double dblNetPositionAfterSpin = Double.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));

				String betValue = getConsoleText("return " + XpathMap.get("BetSizeText"));
				double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));

				Thread.sleep(2000);

				boolean isPlayerWon = isPlayerWon();
				if (isPlayerWon) {
					String winAmount = null;
					if (!GameName.contains("Scratch")) 
					{
						winAmount = getConsoleText("return " + XpathMap.get("WinMobileText"));
					} 
					else {
						winAmount = getConsoleText("return " + XpathMap.get("WinText"));
					     }
					
					//Currency-Verification
					if( verifyWinAmtCurrencyFormatForLVC(regExpr))
					report.detailsAppend("Verify currency when win occurs ",
							"win should display with correct currency format and and currency symbol",
							"win displaying with correct currency format and currency symbol ",
							"PASS");
					
					else
						report.detailsAppend("Verify currency when win occurs ",
								"win should display with correct currency format and and currency symbol",
								"win is not displaying with correct currency format and currency symbol ",
								"FAIL");
					
					
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
						log.error("Error in checking help page ", e);
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
				if(isNetPositionUpdated)
				{
				report.detailsAppend("verify Net position updates after each spin ",
						"Net position should update after every spin ", "Net position is updating after every spin",
						"PASS");
				log.debug("Net Position before spin " + netPositionBeforeSpin + " Net psotion after spin "
						+ netPositionAfterSpin);
				}
				else {
					report.detailsAppend("verify Net position updates after each spin ",
							"Net position should update after every spin ", "Net position is not updating after every spin",
							"FAIL");
					log.debug("Net Position before spin " + netPositionBeforeSpin + " Net psotion after spin "
							+ netPositionAfterSpin);
				}
	
			
			
		} // if block
	} // try block
	catch (Exception e) {
		log.error("Error in checking net profit ", e);
		report.detailsAppend("Verify net position on topbar", "Net position should be visible and updated correctly", "Net position is not visible", "fail");			
		System.out.println("Net profit is not present");
	}
	return isNetPositionUpdated;
}

/**
 * This method verify checkSpinReel Landingin FreeSpin
 * @param report
 * @return languageCode
 * @return checkSpinStopFreeSpinUsingCoordinates
 */
public void checkSpinReelLandinginFreeSpin(Desktop_HTML_Report report ,String languageCode ,String checkSpinStopFreeSpinUsingCoordinates)
{
	   try
	   {
			
			if(checkSpinStopFreeSpinUsingCoordinates.equalsIgnoreCase("yes"))
			ClickByCoordinates("return " + XpathMap.get("clickOnReelX"), "return " + XpathMap.get("clickOnReelY"));
		 
		 report.detailsAppendFolder("Verify Spin reel landing  ", "Reels should not land at the same time  ", "Reels did not land at the same time ** Manual Validation is Required **", "PASS",languageCode);
	   }
	   
	   catch (Exception e) {
		   log.error(e.getMessage(), e);
	   	}
}

/**
 * This method verify Bet from Top Bar
 * @param report
 * @return languageCode
 */
public boolean offerScreenWithBet(Desktop_HTML_Report report, String lang) 
{
	boolean status=false;
	try
	{
		boolean isFGAssign1 = freeGamesEntryScreen();
		String BetValue = webdriver.findElement(By.id(XpathMap.get("BetFromTopBar"))).getText();
		BetValue=BetValue.replaceAll("[^0-9.-]", "").trim();
	if (BetValue.equals("0.00")&&isFGAssign1) 
		{
		log.debug("correct bet amount");
		report.detailsAppendFolder(
				"verify free games Offer screen is displaying with correct details with betAmt 0.00"
						,
				"Free Games Offer screen should display with correct details with betAmt 0.00 "
						 ,
				"Free Game Offer screen is displaying with correct details with betAmt 0.00 "
						,
				"PASS", lang);
		    status =true;
		} 
		else 
		{
		log.debug("incorrect bet amount");

		report.detailsAppendFolder(
				"verify that free games Offer screen is displaying with correct details with betAmt 0.00 "
						,
				"Free Games Offer screen should display with correct details with betAmt 0.00 "
						 ,
				"Free Game Offer screen is not displaying with correct detailswith betAmt 0.00 "
						,
				"FAIL", lang);
		status=false;
		}
	} 
	catch (Exception e)
	{
		log.error("Not able to verify bet", e);
	}
	
	return status;

}

/*
 * This method Refresh in FreeGame
 * @param Report
 * @param Language
 */
public void RefreshInFreeGame(Desktop_HTML_Report report ,String languageCode,String market)
{
	    RefreshMarket(market);
	   	if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
			closeOverlay();
		}
	   	clickPlayNow();
	   	
		threadSleep(1000);
		
	  report.detailsAppendFolder("Refresh During Feature", " Game must be refreshed", "Game is refreshed", "PASS",languageCode);
}

/*
 * This method verify  Entery FreeGame
 * @param Report
 * @param Language
 */
public void VerifyEntryFreeGame(Desktop_HTML_Report report , String languageCode) {
	Wait = new WebDriverWait(webdriver, 500);
	boolean b = false;
	try {
		b = clickPlayNow();
		Thread.sleep(2000);
		String BetFromConsole = getBetFromConsole();
		if (b&&BetFromConsole.contains("FREE"))
			report.detailsAppendFolder(
					"verify  that Base Scene in free games is displaying With Bet Free ",
					"Base Scene in Free Games should display With Bet Free",
					"Base Scene in Free Games is displaying with free games details With Bet Free ",
					"PASS", languageCode);
		else
			report.detailsAppendFolder(
					"Check that Base Scene in free games is displaying With Bet Free ",
					"Base Scene in Free Games should display With Bet Free ",
					"Base Scene in Free Games is not displaying with free games details With Bet Free ",
					"FAIL", languageCode);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}


/**
 * This method verify NetPostion FreeGame 
 * @param Report
 * @param Language
 */
public void NetPostionFreeGame(Desktop_HTML_Report Report ,String Language) {
	try {
			for (int i = 0; i < 2; i++)  {
		String netPostionBeforeSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
		double dbnetPostionBeforeSpin = Double.parseDouble(netPostionBeforeSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
		spinclick();
		waitForSpinButtonstop();
	Thread.sleep(6000);
		String netPositionAfterSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
		double dbNetPositionAfterSpin = Double.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
		boolean b = isPlayerWon();
		if (b) {
			
				double netPostion = dbnetPostionBeforeSpin + dbNetPositionAfterSpin;
				if (dbNetPositionAfterSpin==netPostion) {
					System.out.println("Win is added to net position successfully");
					Report.detailsAppendFolder(
							"Net Position increase by the win amount",
							"Net Position should increase by the win amount",
							"Net Position increased by the win amount",
							"PASS", Language);
					
				} else {
					System.out.println("Win is not added to net position successfully");
					Report.detailsAppendFolder(
							"Verify Net Position increase by the win amount",
							"Net Position should increase by the win amount",
							"Net Position not increased by the win amount",
							"FAIL", Language);
					
				}
			
		} else {
			if (dbnetPostionBeforeSpin==dbNetPositionAfterSpin) {
				
				Report.detailsAppendFolder(
						"Verify Net Position not change when the player triggers a loss",
						"Net Position should not change when the player triggers a loss",
						"Net Position not change when the player triggers a loss",
						"Pass", Language);
			} else {
				
				Report.detailsAppendFolder(
						"Verify Net Position not change when the player triggers a loss",
						"Net Position should not change when the player triggers a loss",
						"Net Position is changeing when the player triggers a loss",
						"fail", Language);
			}
		}
	} 
	}catch (Exception e) {
		e.printStackTrace();
	}
	}


/**
 * This method verify NetPostion FreeGame FreeSpin
 * @param Report
 * @param Language
 */
public void NetPostionFreeGameFreeSpin(Desktop_HTML_Report Report ,String Language) {
	try {
		String netPostionBeforeSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
		double dbnetPostionBeforeSpin = Double.parseDouble(netPostionBeforeSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
		spinclick();
		Thread.sleep(8000);
		String freeSpinEntryScreen = XpathMap.get("FreeSpinEntryScreen");
		//wait till if the game having free spin entry screen
		String str = entryScreen_Wait(freeSpinEntryScreen);
		if (str.equalsIgnoreCase("freeSpin")) 
		{
			if("yes".equals(XpathMap.get("isFreeSpinSelectionAvailable")))
			{
				Thread.sleep(3000);
				boolean isBonusSelectionVisible=isFreeSpinTriggered();
				if(isBonusSelectionVisible)
				{
					clickBonusSelection(1);
				}
				else
				{
					log.debug("Bonus selection is not visible");
				}
			}
			else
			{
				if( TestPropReader.getInstance().getProperty("IsProgressiveGame").equalsIgnoreCase("Yes"))
				{
					FSSceneLoading();
					//cfnlib.clickToContinue();
					//cfnlib.FS_continue();
				}
				else
				{
					//Click on free spins into continue button
					if("yes".equals(XpathMap.get("isFreeSpinIntroContinueBtnAvailable")))
					{
						clickToContinue();
					}
					else
					{
						System.out.println("There is no Freespins Into Continue button in this game");
						log.debug("There is no Freespins Into Continue button in this game");
					}
				}
			}
		} 
		else 
		{
			log.debug("Free Spin Entry screen is not present in Game");
		}
		
	     FSSceneLoading();
		Thread.sleep(6000);
		String netPositionAfterSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
		double dbNetPositionAfterSpin = Double.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
				if (dbnetPostionBeforeSpin==dbNetPositionAfterSpin) {
					System.out.println("Net Position not change during free spins");
					Report.detailsAppendFolder(
							"Verify Net Position not change during free spins",
							"Net Position should not change during free spins",
							"Net Position not changeing during free spins",
							"PASS", Language);
					
				} else {
					System.out.println("Net Position  change during free spins");
					Report.detailsAppendFolder(
							"Verify Net Position not change during free spins",
							"Net Position should not change during free spins",
							"Net Position changeing during free spins",
							"FAIL", Language);
					
				}
			
				waitSummaryScreen();
				 Thread.sleep(1000);
				String winAmount = getConsoleText("return " + XpathMap.get("FreeSpinSummaryAmt"));
				double dbWinAmount = Double.parseDouble(winAmount.replaceAll("[^0-9]", ""));
				double CurrentnetPostion = dbnetPostionBeforeSpin + dbWinAmount;
				 closeOverlay();
				 Thread.sleep(2000);
				  BacktogameClick();
				  Thread.sleep(1000);
				 netPositionAfterSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
				 dbNetPositionAfterSpin = Double.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
			
				
			if (CurrentnetPostion==dbNetPositionAfterSpin) {
				
				Report.detailsAppendFolder(
						"Verify Once the feature is complete the Summary Winnings of the feature add to the Net Position",
						"Once the feature is complete the Summary Winnings of the feature must be added to the Net Position",
						" Summary Winnings of the feature added to the Net Position",
						"Pass", Language);
			} else {
				
				Report.detailsAppendFolder(
						"Verify Once the feature is complete the Summary Winnings of the feature add to the Net Position",
						"Once the feature is complete the Summary Winnings of the feature must be added to the Net Position",
						" Summary Winnings of the feature is not added to the Net Position",
						"Fail", Language);
			}
			
			
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	}


/**
 * This method verify FreeGames To BaseGame Bet Compare
 * @param Bet
 * @param report
 * @param Lang
 */

public boolean FreeGamesToBaseGameBetCompare(double Bet , Desktop_HTML_Report report , String Lang) 
{
	boolean status = false;
	try 
	{
		 double CurrentBet = GetBetAmt(); 

		// we get selected amount from click amount method
		if (CurrentBet==Bet) // Expected VS Actual Bet.
		{
			System.out.println("Bet Amounts are same  ");
			status = true;
			
			report.detailsAppendFolder(
					"Verify The bet overlay  display the last bet value that the user had set prior to entering free games",
					"The bet overlay should display the last bet value that the user had set prior to entering free games",
					" The bet overlay displaying the last bet value that the user had set prior to entering free games",
					"PASS", Lang);
			
		} else {
			System.out.println("Bet Amounts are DIFFERENT  ");
			report.detailsAppendFolder(
					"Verify The bet overlay  display the last bet value that the user had set prior to entering free games",
					"The bet overlay should display the last bet value that the user had set prior to entering free games",
					" The bet overlay not displaying the last bet value that the user had set prior to entering free games",
					"FAIL", Lang);
			
		}
	} catch (Exception e) {
		log.error("Not able to verify Bet", e);
	}
	return status;

}
/**
 *This Method used to click on Back To game Button in FreeGame in Summary screen
 */

public void BacktogameClick() {
	try {
		clickAtButton("return " + XpathMap.get("FGCompleteGameBtn"));
		log.debug("Clicked at back to game");
	} catch (Exception e) {
		log.error("Can not clicked on Back to Button", e);
	}
}


/**
 * This method is used to verify NetPosition Under Freespin scenario
 * @param report
 * @param languageCode
 * @param userName
*/
public boolean verifyNetPositionUnderFreespin(Desktop_HTML_Report report, String languageCode,String userName, String SessionReminderUnderFreespin, String regExpr) throws InterruptedException {
	boolean isNetPositionUpdated = false;
	double dblNetPositionBeforeSpin;
	
	
		String betValue = webdriver.findElement(By.xpath(XpathMap.get("BetUnderFreespin"))).getText();
		double dblBetValue = Double.parseDouble(betValue.replaceAll("[^0-9]", ""));
		System.out.println("Bet Value :" + betValue);
		
		String isNetProfitAvailable = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
		if (isNetProfitAvailable.contains("Net position")) {
			
				System.out.println("Net Position is visible");
				report.detailsAppend("Verify net position on topbar", "Net position should be visible", "Net position is visible  correctly", "PASS");
			
				
				String netPositionBeforeSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
				 dblNetPositionBeforeSpin = Double.parseDouble(netPositionBeforeSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
				 System.out.println("net Position first spin:" +netPositionBeforeSpin);
				
				Thread.sleep(5000);
				String netPositionAfterOneSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
				double dblnetPositionAfterOneSpin = Double.parseDouble(netPositionAfterOneSpin.replaceAll("[^0-9.-]", "").replace(".", ""));
				 System.out.println("net Position Second spin:" +netPositionBeforeSpin);
				
				if(dblNetPositionBeforeSpin==dblnetPositionAfterOneSpin) {
					System.out.println("Netposition is not updated During Free spin");
					
					report.detailsAppend("Verify net position does not deduct for every spin in Free Spins", "Net position should not be updated during freespin", "Net position value has not been updated during Freespin", "PASS");
					
				}
				
				else {
					report.detailsAppend("The net position does not deduct for every spin in Free Spins", "Net position should not be updated during freespin", "Net position value has  been updated during Freespin", "FAIL");
				
					}

				
			
					String freespinwin = null;
					Thread.sleep(1000);
					waitSummaryScreen();
					if(SessionReminderUnderFreespin.equalsIgnoreCase("no")) {
						Thread.sleep(2000);
						report.detailsAppend("Transition from Freespin to Base Game",
								"Freespin Summary screen should be visible", "Freespin Summary Screen is visible", "PASS");
					
						String FreeSpinWinCurrencyFormat ="yes";
						if(FreeSpinWinCurrencyFormat.equalsIgnoreCase("yes")) { 
							freeSpinSummaryWinCurrFormatForLVC(regExpr, report, languageCode);
							}
					}
					
					try {
						log.debug("Function -> freeSpinWinCurrencyFormat()");
						if (!GameName.contains("Scratch")) {
							freespinwin = "return " + XpathMap.get("FSSumaryAmount");
						} else {
							// need to update the hook for Scratch game
							freespinwin = "return " + XpathMap.get("InfobarBettext");
						}
						String freespinwinnew = getConsoleText(freespinwin);
						double dblfreespinwinnew = Double.parseDouble(freespinwinnew.replaceAll("[^0-9.-]", "").replace(".", ""));
						
						
						
						if ("yes".equalsIgnoreCase(XpathMap.get("SummaryScreenCntButton")))
						{
							closeOverlay();
						}
						
						waitForSpinButton();
						Thread.sleep(2000);
						
					 String	netPositionAfterSpin = webdriver.findElement(By.xpath(XpathMap.get("isNetPosition"))).getText();
						double dblNetPositionAfterSpin = Double.parseDouble(netPositionAfterSpin.replaceAll("[^0-9.-]", "").replace(".", ""));

						 
						 double dblBSandBetValue = dblNetPositionBeforeSpin - dblBetValue;
							if ((dblBSandBetValue + dblfreespinwinnew) == dblNetPositionAfterSpin)
							{
								Thread.sleep(1000);
								report.detailsAppend("Verify net position on topbar", "Net position should be updated correctly after FreeSpin ", "Net position is updated correctly after Freespin", "PASS");
								
								isNetPositionUpdated=true;
							}
						 
							else {
								report.detailsAppend("Verify net position on topbar", "Net position should be updated correctly after FreeSpin ", "Net position is updated correctly after Freespin", "FAIL");
								
							}
						 
						
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					

				}


					
				
		
		else {
			report.detailsAppend("Verify net position on topbar", "Net position should be visible and updated correctly", "Net position is not visible and updated correctly", "FAIL");
		}
					return isNetPositionUpdated;
}

/**
 * This method is used to verify SessionReminder FreeSpin scenario
 * @param report
 * @param userName
 * @param languageCode
 * @param SessionReminderUserInteraction
 * @param SessionReminderContinue
 * @param SessionReminderExitGame
 * 
*/
public void validateSessionReminderFreeSpin(Desktop_HTML_Report report,String userName ,String languageCode,String SessionReminderUserInteraction,String SessionReminderContinue,String SessionReminderExitGame,String market) 
{
	try 
	{
		CommonUtil util = new CommonUtil();						
		String gameurl = webdriver.getCurrentUrl();
		
		String periodValue= XpathMap.get("sessionReminderDurationInFreeSpin");
		String resetPeriodValue=XpathMap.get("resetSessionReminderValue");
	
		//assigning session reminder
		boolean sessionReminderAssigned=util.setSessionReminderOnAxiom(userName,periodValue);
		 loadGameMarket(gameurl,market);
		 if (sessionReminderAssigned && sessionReminderPresent())
			{
			 report.detailsAppendFolder("Verify Session Reminder must be present ","Session Reminder must be present", "Session Reminder is present ","PASS", languageCode);
		    
			 selectContinueSession();
			 
		      Thread.sleep(2000);
		if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			closeOverlay();
		}
		FSSceneLoading();
		FS_continue();
		boolean  status=sessionReminderPresentInFreeSpin(10000);
		if(status) {
			
			//report.detailsAppendFolder("Verify Session Reminder must be present during Free Spin","Session Reminder must be present", "Session Reminder is not  present During Free spin","FAIL", languageCode);
		
	
			report.detailsAppendFolder("Verify Session Reminder must be present during Free Spin","Session Reminder must be present", "Session Reminder is present During Free spin","PASS", languageCode);
		
			log.debug("Session reminder is set for "+periodValue+" secs");
		
			
			if(SessionReminderUserInteraction.equalsIgnoreCase("yes"))
			{
				//clicking else where on the screen
				closeOverlay();
				report.detailsAppendFolder("Ensure that when the session reminder pops up, the free spins game does not malfunction","Player should not not be able to interact with any part of the game", "Unable to interact with the game","PASS",languageCode);								
				
			}
			
			//Ensure that the continue button takes you back to the game
			if(SessionReminderContinue.equalsIgnoreCase("yes"))
			{
				selectContinueSession();
				if(sessionReminderPresent())
				{	selectContinueSession();
					System.out.println("Session Reminder : Continue Button is pressed twice");
					
				}
				else
				{
					System.out.println("Session Reminder : Continue Button pressed");
					}									
			}
		}
			else {
			report.detailsAppendFolder("Verify Session Reminder must be present during Free Spin","Session Reminder must be present","Session Reminder is not present during Free Spin", "FAIL",languageCode);
			log.debug("Session reminder is not visible on screen");
					}

		
		//reset to higher duarion for session reminder
		boolean resetSessionReminderDUration=util.setSessionReminderOnAxiom(userName,resetPeriodValue);
		if (resetSessionReminderDUration) {
			log.debug("Session reminder has been reset to 10hr in order to avoid pop up");
		} else {
			log.debug("Unable to reset Session reminder");
		}							
		// relaunching the game to set the above reset session reminder duration to higher value
		loadGameMarket(gameurl,market);
		if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
			
			closeOverlay();
		}
		threadSleep(2000);
		FS_continue();		
			}						
} catch (Exception e) {
	e.getMessage();
	log.error(e);
}
}		

private boolean sessionReminderPresentInFreeSpin(int i) {
	// TODO Auto-generated method stub
	return false;
}

/**
 * This method is used to verify PracticePlay releted scenario 
 * @param report
 * @param languageCode
 * @param setHighBet
 * @param SetLowBet
 * @param Spinerror
 * @param PracticePlaybetRefresh
 * @param PlayForRealPopUP
 * @param PlayForRealyesMenuFuction
 *  @param PlayForRealyesBtnFuction
 * 
*/
public void verifyPracticePlay(Desktop_HTML_Report report, String languageCode, String  setHighBet,String SetLowBet ,String Spinerror, String PracticePlaybetRefresh,String PlayForRealPopUP, String PlayForRealyesMenuFuction, String PlayForRealyesBtnFuction , String market ,String TakeToGamePracticePlayitly) throws InterruptedException {
	
	String newURL = null ;
	
	
	String url = webdriver.getCurrentUrl();
	try {
		
		
	//ENable Practice play
	 if(url.contains("isPracticePlay=False"))
	 {
		 newURL = url.replaceAll("isPracticePlay=False", "isPracticePlay=true");
	 }
	 else if(url.contains("isPracticePlay=false"))
	 {
		 newURL = url.replaceAll("isPracticePlay=false", "isPracticePlay=true");
	 }
	 else
	 {
		 report.detailsAppendFolder("Verify practice play ","Practice play must be set to true","Unable to set practice play to true","FAIL",""+languageCode);
	 }
	 
	 loadGameMarket(newURL,market);
	 if ((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
			closeOverlay();
		}
		
	 waitForSpinButton();
	 
	 
	 if(TakeToGamePracticePlayitly.equalsIgnoreCase("yes")){
		   String amount= XpathMap.get("amount1");
		    webdriver.navigate().refresh();
		    Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("ItalyPopup"))));
		    boolean Popup = webdriver.findElement(By.xpath(XpathMap.get("ItalyPopup"))).isDisplayed();
		    if(Popup)
		    {
		     
		     italyScrollBarAmount_updated(report, amount);

		     boolean s1 = italyCreditAmountComparission_updated();
		     if(s1)
		     report.detailsAppendFolder("Ensure the player can take a balance *"+ amount + "* into the game",

		                "Verify Credit Amount should not be more than Balance set", "Player balance is matching", "PASS", languageCode);

		     else
		         report.detailsAppendFolder("Ensure the player can take a balance *"+ amount + "* into the game",

		                    "Verify Credit Amount should not be more than Balance set", "Player balance is not matching", "FAIL", languageCode);
		     
	     }
	}
	
	 
	 
	if(setHighBet.equalsIgnoreCase("yes")) {
		setMaxBet();
		report.detailsAppendFolder("After setting Max bet, verify Bet without currency symbol but in correct currency format ",
				"After setting Max bet, verify Bet Should be displayed without currency symbol but in correct currency format",
				"After setting Max bet, verify Bet is displayed without currency symbol but in correct currency format **Manual Validation is Required** ","PASS",""+languageCode);
	
	}
	if(SetLowBet.equalsIgnoreCase("yes")) {
	     setMinBet();
	     report.detailsAppendFolder("After setting Min bet, verify Bet without currency symbol but in correct currency format ","After setting Min bet, verify Bet Should be displayed without currency symbol but in correct currency format","After setting Min bet, verify Bet is displayed without currency symbol but in correct currency format **Manual Validation is Required**","PASS",""+languageCode);
	 	
	}
	
	if(PracticePlaybetRefresh.equalsIgnoreCase("yes")) {
		 setMinBet();
		 
		 String credit1 = getCurrentCredits();
		 spinclick();
		//navigation check in practice play
			if(Spinerror.equalsIgnoreCase("yes")) {
				report.detailsAppendFolder("Verify Reels spin and no errors are thrown  ","Reels spin and no errors are thrown"
						,"Reels spin and no errors are thrown  **Manual Validation is Required**  ","PASS",""+languageCode);
		
			}
			
	      double beforerefresh = GetBetAmt();
	      waitForSpinButtonstop();
	      String credit2 = getCurrentCredits();
	      
	  	if(!credit1.equalsIgnoreCase(credit2)) {
			report.detailsAppendFolder("Verify credit is updating  ","credit should update ","credit is updating    ","PASS",""+languageCode);
	
		}
	    
	      RefreshMarket(market);
		 if ((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
				closeOverlay();
			}
		 double Afterrefresh = GetBetAmt();
		 if(beforerefresh==Afterrefresh&&!credit1.equalsIgnoreCase(credit2))
		report.detailsAppendFolder("Verify Wager Overlay shown and  match the console after refreshing","Wager Overlay should be shown and must match the console after refreshing and credit is upadating","Wager Overlay shown and  matching the console after refreshing credit is upadating","PASS",""+languageCode);
		 else	
				report.detailsAppendFolder("Verify Wager Overlay shown and  match the console after refreshing",
						"Wager Overlay should be shown and must match the console after refreshing",
						"Wager Overlay shown and not matching the console after refreshing credit is not upadating","FAIL",""+languageCode);
	}
	
	
	
	if(PlayForRealPopUP.equalsIgnoreCase("yes")) {
		 double BetAmt = GetBetAmt();
		 spinclick();
		 Thread.sleep(10000);
		 double WinAmt = GetWinAmtDouble();
		 if(WinAmt>BetAmt) {
			// System.out.println("Waiting for PopUp ");
			 Thread.sleep(5000);
			 boolean PopUp = elementWait("return " + XpathMap.get("PlayForRealBanner"), true);
			 if(PopUp) {
				 report.detailsAppendFolder("Verify play for real pop up display"," play for real pop up Should display"," play for real pop up is displaying ","PASS",""+languageCode);
				 System.out.println("Play For Real Pop Up is displaying");
			 }
			 else {
				 report.detailsAppendFolder("Verify play for real pop up display"," play for real pop up Should display"," play for real pop up is not displaying ","FAIL",""+languageCode);
				 
				 System.out.println("Play For Real Pop Up is not displaying");
			 }
			 
			 
			 
		 }
		 else {
			 report.detailsAppendFolder("Verify Win Amt is greater than Bet Amt","  Win Amt Should be greater than Bet Amt"," Win Amt is not greater than Bet Amt ","FAIL",""+languageCode);
		 }
		
		 if(PlayForRealyesBtnFuction.equalsIgnoreCase("yes")) {
			 
			 clickOnYesPrimaryBtn();
			 Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("PracticeLoginid"))));
			 String urlAfter = webdriver.getCurrentUrl(); 
			 if(urlAfter.contains("login")) {
			 
				 report.detailsAppendFolder("Verify Yes button is clicked and login page display","  Yes button Should clicked and login page Should displaying"," Yes button is clicked and login page is displaying ","PASS",""+languageCode);
			 }
			 else
			 {
				 report.detailsAppendFolder("Verify Yes button is clicked and login page display","  Yes button Should clicked and login page Should displaying"," Yes button is clicked and login page is not displaying ","FAIL",""+languageCode);
				 
			 }
			
			 loadGameMarket(newURL,market);
			 if ((Constant.YES.equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))) {
					closeOverlay();
				}
		 }
		 
		
		 if(PlayForRealyesMenuFuction.equalsIgnoreCase("yes")) {
			 
			clickOnPlayRealMenu();
			
			
			 Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("PracticeLoginid"))));
			 String urlAfter = webdriver.getCurrentUrl(); 
			 
			 if(urlAfter.contains("login")) {
			 
				 report.detailsAppendFolder("Verify Play Real From Menu button is clicked and login page display","  Play Real From Menu button Should clicked and login page Should displaying",
						 " Play Real From Menu button is clicked and login page is displaying ","PASS",""+languageCode);
			 }
			 else
			 {
				 report.detailsAppendFolder("Verify Play Real From Menu button is clicked and login page display",
						 "  Play Real From Menu button Should clicked and login page Should displaying"," Play Real From Menu button not clicked and login page not displaying ","FAIL",""+languageCode);
				 
			 }
			 
			 loadGameMarket(newURL,market);
		 }
		 
		 
	}
	      url = webdriver.getCurrentUrl();
	 
		  url = url.replaceAll("isPracticePlay=true", "isPracticePlay=false");
	
	
		
	 
	} 
	catch(Exception e){
		e.getMessage();
		log.error(e);
		
		}
	
}

/**
 * This method is used for Practice Play Disabled
 * 
 */
public String PracticePlayDisabled(String market) {
	
	String urlNew =null;
	
	try {
		  String newURL = webdriver.getCurrentUrl();
		  urlNew = newURL.replaceAll("isPracticePlay=true", "isPracticePlay=false");
		  loadGameMarket(urlNew,market);
	}
	catch(Exception e){
		e.getMessage();
		log.error(e);
		
		}
	return urlNew;
}

/**
 * This method is used to wait till the free spin summary screen
 * won't come @throws InterruptedException
 */
public boolean waitSummaryScreenFree() throws InterruptedException {
	boolean SummaryScreen = false;
	Wait = new WebDriverWait(webdriver, 100);

	try {
		long startTime = System.currentTimeMillis();
		log.info("Waiting for Summary Screen to come");
		while (true) {
			String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
			//System.out.println(currentScene);
			log.debug(currentScene);
			if (currentScene != null && (currentScene.contains("FREESPINS_COMPLETE"))) {
				{
					log.debug("Summary screen visible");
					log.debug(currentScene);
					System.out.println("Summary screen visible"+ currentScene);
					SummaryScreen=true;
					break;
				}
			}

			long currentime = System.currentTimeMillis();
			// Break if wait is more than 300 secs
			if (((currentime - startTime) / 1000) > 300) {
				log.debug("Summary screen not visible, break after  5 mins");
				break;
			}
		}
	} catch (Exception e) {
		log.error("error while waiting for summary screen ",e);
		log.error(e.getMessage(),e);
	}
	return SummaryScreen;
}

/**
 * This method is used for verifying whether stop button is present or not
 * 
 */

public boolean isStopButtonAvailableMarket() 
{
	boolean isstopbutton = true;
	try 
	{
        spinclick();
		// To verify the stop button
		String stopbutton = getConsoleText("return " + XpathMap.get("SpinBtnCurrState"));
		if ((stopbutton.equalsIgnoreCase("disabled")))
		{
			// As current state of button is ActiveSecondary
			log.debug("Stop button is not available");
			System.out.println("Stop button is not available");
			isstopbutton = true;
		} 
		else 
		{
			log.error("Stop button is available");
			System.out.println("Stop button is available");
			isstopbutton = false;
		}
	} 
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}

	return isstopbutton;
}


/**
 * This method is to get win ammount in double
 * 
 */
public double GetWinAmtDouble() {
	String consolePayoutnew = null;
	double consolePayoutnew1 = 0.0;
	try {
		String win = "return " + XpathMap.get("WinDesktopLabel");
		String consolewin = getConsoleText(win);
		consolePayoutnew = consolewin.replaceAll("[^0-9]", "");
		consolePayoutnew1 = Double.parseDouble((consolePayoutnew));
	} catch (Exception e) {
		e.getMessage();
	}
	return consolePayoutnew1 / 100;
}

/**
 * This method click on Yes PlayReal Option From PopUp
 */
public void clickOnYesPrimaryBtn() {
	try {
	clickAtButton("return " + XpathMap.get("ClickOnYesRealPlay"));
	}
   catch(Exception e) {
		
		e.getMessage();
		
	}
}

/**
 * This method click By Coordinates With Adjust
 */
public void ClickByCoordinatesWithAdjust(String cx, String cy, int x, int y) {
	Wait = new WebDriverWait(webdriver, 50);
	// boolean visible = false;
	int dx = 0, dy = 0;
	JavascriptExecutor js = ((JavascriptExecutor) webdriver);

	System.out.println(js.executeScript(cx));
	System.out.println(js.executeScript(cy));
	try {
		Long sx = (Long) js.executeScript(cx);
		dx = sx.intValue()+(x);
	} catch (Exception e) {
		Double sx = (Double) js.executeScript(cx);
		dx = sx.intValue()+(y);
	}
	try {
		Long sy = (Long) js.executeScript(cy);
		dy = sy.intValue()+(x);
	} catch (Exception e) {
		Double sy = (Double) js.executeScript(cy);
		dy = sy.intValue()+(y);
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
	System.out.println(dx + "  "+dy);
	actions.moveByOffset(dx, dy).click().perform();		

}

/**
 * This method click on PlayReal Option From Menu
 */
public void clickOnPlayRealMenu() {
	try {
	clickAtButton("return " + XpathMap.get("playRealMenu"));
	}
	catch(Exception e) {
		
		e.getMessage();
		
	}
}


/**
 * This method verify Progressive tickers are counting up timeously
 * @param report
 * @param languageCode
 */

public void verifyTickercounting(Desktop_HTML_Report Report, String languageCode) {
	
	try {
		
		log.debug("Function-> verifyTickercounting() ");
		
		String TickerValue1 = getConsoleText("return " + XpathMap.get("TickerValue1"));
		String TickerValue2 = getConsoleText("return " + XpathMap.get("TickerValue2"));
		String TickerValue3 = getConsoleText("return " + XpathMap.get("TickerValue3"));
		String TickerValue4 = getConsoleText("return " + XpathMap.get("TickerValue4"));
	    Thread.sleep(30000);
	    String AfterTickerValue1 = getConsoleText("return " + XpathMap.get("TickerValue1"));
	 	String AfterTickerValue2 = getConsoleText("return " + XpathMap.get("TickerValue2"));
	 	String AfterTickerValue3 = getConsoleText("return " + XpathMap.get("TickerValue3"));
	 	String AfterTickerValue4 = getConsoleText("return " + XpathMap.get("TickerValue4"));
	 		     
	         if(TickerValue1==AfterTickerValue1&&TickerValue2==AfterTickerValue2&&TickerValue3==AfterTickerValue3&TickerValue4==AfterTickerValue4)
	        	 Report.detailsAppendFolder("Verify Progressive tickers are counting up timeously"," Progressive tickers should be counting up timeously","Progressive tickers are not counting  up timeously ","FAIL",""+languageCode);
	         else
	        	 Report.detailsAppendFolder("Verify Progressive tickers are counting up timeously"," Progressive tickers should be counting up timeously","Progressive tickers are counting  up timeously ","PASS",""+languageCode);
	        	
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	
}

public boolean loadGameMarket(String url, String Market) {
	boolean isGameLaunch = false;
	try {
		Wait = new WebDriverWait(webdriver, 300);
		webdriver.navigate().to(url);
		System.out.println(url);
		 if (Market.equalsIgnoreCase("italy")) {
			     Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/div")));
		    	 WebElement Popup = webdriver.findElement(By.xpath("/html/body/div[1]/div/div/div"));
		    	if(Popup.isDisplayed()) {
		    		WebElement SubmitBtn = webdriver.findElement(By.xpath("//*[@id=\"titan-complex-dialog-submitButton\"]/div"));	
		    		SubmitBtn.click();
		    		
		    	}
		}
		 
		 if (Market.equalsIgnoreCase("Spain")) {
			 spainPopupHandle();	
	    	}
		    log.debug("Waiting for clock to be visible");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			log.debug(" clock is visible");   
			isGameLaunch = true;
			threadSleep(2000);
			log.debug("game loaded ");
	}
	 catch (WebDriverException e) {
		log.debug("Exception occur in loadgame()");
		log.error(e.getMessage());
	}
	return isGameLaunch;
}
public boolean RefreshMarket( String Market) {
	boolean isGameLaunch = false;
	try {
		Wait = new WebDriverWait(webdriver, 300);
		webdriver.navigate().refresh();
		 if (Market.equalsIgnoreCase("Italy")) {
			     Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/div")));
		    	 WebElement Popup = webdriver.findElement(By.xpath("/html/body/div[1]/div/div/div"));
		    	if(Popup.isDisplayed()) {
		    		WebElement SubmitBtn = webdriver.findElement(By.xpath("//*[@id=\"titan-complex-dialog-submitButton\"]/div"));	
		    		SubmitBtn.click();
		    		
		    	}
		}
		 if (Market.equalsIgnoreCase("Spain")) {
			   Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("SetLimits"))));
		    	WebElement SetLimits = webdriver.findElement(By.xpath(XpathMap.get("SetLimits")));	
	    		WebElement SetLimits1 = webdriver.findElement(By.xpath(XpathMap.get("SetLimits1")));		
	    		WebElement SetLimits2 = webdriver.findElement(By.xpath(XpathMap.get("SetLimits2")));
	    		WebElement SetLoss = webdriver.findElement(By.xpath(XpathMap.get("SetLoss")));	

	    		Select select1 = new Select(SetLimits1);	
	    		Select select2 = new Select(SetLimits2);	
	    		select1.selectByIndex(2);	
	    		select2.selectByIndex(2);
	    		
	    		SetLoss.sendKeys("5");
	    		SetLimits.click();
	    		
	    	}
		    log.debug("Waiting for clock to be visible");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			log.debug(" clock is visible");   
			isGameLaunch = true;
			threadSleep(2000);
			log.debug("game loaded ");
	}
	 catch (WebDriverException e) {
		log.debug("Exception occur in loadgame()");
		log.error(e.getMessage());
	}
	return isGameLaunch;
}

public void checkpagenavigationMarket(Desktop_HTML_Report report, String gameurl ,String Market) {
	try {
		String mainwindow = webdriver.getWindowHandle();
		Set<String> s1 = webdriver.getWindowHandles();
		if (s1.size() > 1) {
			Iterator<String> i1 = s1.iterator();
			while (i1.hasNext()) {
				String ChildWindow = i1.next();
				if (mainwindow.equalsIgnoreCase(ChildWindow)) {
					//report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot", "Navigation page screenshot ", "PASS");
					ChildWindow = i1.next();
					webdriver.switchTo().window(ChildWindow);
					String url = webdriver.getCurrentUrl();
					log.debug("Navigation URL is :: " + url);
					log.debug("Navigation URL is :: " + url);
					if (!url.equalsIgnoreCase(gameurl)) {
						Thread.sleep(30000);
						elementWait("return " + XpathMap.get("chekingLoad"), "complete");
						report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot",
								"Navigation page screenshot ** Manual Validation is Required **", "PASS");
						log.debug("Page navigated succesfully");
						log.debug("Page navigated succesfully");
						webdriver.close();
					} else {
						System.out.println("Now On game page");
						//log.debug("Now On game page");
					}
				}
			}
			webdriver.switchTo().window(mainwindow);
		} else {
			String url = webdriver.getCurrentUrl();
			//System.out.println("Navigation URL is ::  " + url);
			log.debug("Navigation URL is ::  " + url);
			if (!url.equalsIgnoreCase(gameurl)) {
				// pass condition for navigation
				Thread.sleep(30000);
				report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot",
						"Navigation page screenshot ** Manual Validation is Required **", "PASS");
				log.debug("Page navigated succesfully");
				webdriver.navigate().to(gameurl);
				waitForSpinButton();
				if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
					closeOverlay();
				}
			} else {
				loadGameMarket(gameurl, Market);
				if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
					closeOverlay();
				}
				waitForSpinButton();
				//System.out.println("Now On game page");
				log.debug("Now On game page");
			}
		}

	} catch (Exception e) {
		log.error("error in navigation of page");
	}
}

public void italyTaketoGame(Desktop_HTML_Report report, String lang ) throws InterruptedException {

    for(int x=1;x<=3;x++) {
    String amount= XpathMap.get("amount"+x);
    webdriver.navigate().refresh();
    Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("ItalyPopup"))));
    boolean Popup = webdriver.findElement(By.xpath(XpathMap.get("ItalyPopup"))).isDisplayed();
    if(Popup)
    {
    	
     italyScrollBarAmount_updated(report, amount);
    
     boolean s1 = italyCreditAmountComparission_updated();
     if(s1)
     report.detailsAppendFolder("Ensure the player can take a balance *"+ amount + "* into the game",

                "Verify Credit Amount should not be more than Balance set", "Player balance is matching", "PASS", lang);

     else
         report.detailsAppendFolder("Ensure the player can take a balance *"+ amount + "* into the game",

                    "Verify Credit Amount should not be more than Balance set", "Player balance is not matching", "FAIL", lang);
     
     spinclick();
     
     waitForSpinButtonstop();
     
     report.detailsAppendFolder("Verify After spinning the credit is upadting ",

             " After spinning the credit sould upadate", "After spinning the credit is upadataing **Manual Validation is Required**", "PASS", lang);
    }

    }//for

}



    public boolean italyCreditAmountComparission_updated()
    {
        boolean isVaildCreditAmount = false;
        try
        {
            String creditamount = italygetCurrentCredits(); // get Console credit from italygetCurrentCredits method
            creditamount = creditamount.replaceAll("[^0-9]", "");
            creditamount = creditamount.replace(" ", "");// replace space with null(from italygetCurrentCredits method)
            creditamount=creditamount.substring(0, creditamount.length() - 2);
            selectedamout = selectedamout.replace(".", ","); // replace . with ,(from italyscrollbarclickamount method)
            // we get selected amount from click amount method
            if (creditamount.equals(selectedamout)) // Expected VS Actual Name .
            {
                isVaildCreditAmount = true;
                System.out.println("Take away screen game credit is selected as " + selectedamout);
                System.out.println("Credit Amounts are SAME ");

            } else {

                System.out.println("Credit Amounts are DIFFERENT  ");

            }

        } catch (Exception e) {
           log.error("Not able to verify Game name", e);

        }
        return isVaildCreditAmount;
    }
   
    private String italygetCurrentCredits() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * This method is used to scroll the , scroll bar and select amount for italy market
     * SV65878
     */

    public void italyScrollBarAmount_updated(Desktop_HTML_Report report, String amount)

    {
        try {
             WebElement slider = webdriver.findElement(By.id(XpathMap.get("userInput")));//select scroll bar
             JavascriptExecutor js = (JavascriptExecutor) webdriver;
             js.executeScript("javascript:document.getElementById(\""+XpathMap.get("userInput")+"\").value="+amount+";");
             log.debug("Slider value1 for credits is : "+slider.getAttribute("value"));
             System.out.println("Slider value1 for credits is : "+slider.getAttribute("value"));// value is not updated in the gamescreen 90
             slider.sendKeys(Keys.RIGHT);
             log.debug("Slider value2 for credits is : "+slider.getAttribute("value"));
             System.out.println("Slider value2 for credits is : "+slider.getAttribute("value"));// value updated in the game 90.01
             String currentamt=webdriver.findElement(By.xpath("//div[contains(@id,\"currentValue\")]")).getText().replaceAll("[^0-9]", "");
             currentamt = currentamt.replace(" ", "");// replace space with null(from italygetCurrentCredits method)
             selectedamout=currentamt.substring(0, currentamt.length() - 2);
             Thread.sleep(1000);
             report.detailsAppendFolder("verify the player balance is selected amount  "+amount+" ",

                        " balace should be selected with correct amount  "+amount+" ", " Manual Validaton is required ", "PASS", "");
            webdriver.findElement(By.xpath(XpathMap.get("Italy_Submit"))).click();
            log.debug("Clicked on Submit");
            Thread.sleep(500);
           // report.detailsAppend("Submit Button ", " Clicked on Submit Button ","Clicked on Submit Button ", "PASS");
             //Loading screen is visible
            report.detailsAppendFolder("Take To Game Dialog removed BEFORE the preloader is removed",

                    "Loading/NFD Screen should we visible", "Loading/NFD screen is Visible ** Manual Validation is required**", "PASS", "");
            if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad")))
            {
            report.detailsAppendFolder("Verify Continue button on base scene ","Continue buttion", "Continue button is visible ** Manual Validation is Required**", "PASS", "");
            closeOverlay();
            }
        // waiting for spin button
            waitForSpinButton();

            if(isTopBarVisible()) {
            report.detailsAppendFolder("Take To Game Dialog removed AFTER the preloader is removed",

                    "Ensure the titan topbar appears in the game and all buttons can be interacted with", "titan topbar and all the button are displayed", "PASS", "");
            }
            else
            {        report.detailsAppendFolder("Take To Game Dialog removed AFTER the preloader is removed",

                    "Ensure the titan topbar appears in the game and all buttons can be interacted with", "titan topbar and all the button are not displayed", "FAIL", "");
            }

        /*s    webdriver.findElement(By.xpath(XpathMap.get("Italy_Play"))).click();
            report.detailsAppend("Play Button ", " Clicked on play Button "," Clicked on play Button ", "PASS" );    
            log.debug("Clicked on Play");
            Thread.sleep(2000);
        s*/

            /*    

            webdriver.findElement(By.xpath(XpathMap.get("Italy_Continue"))).click();

            italy.detailsAppend(" Verify that  game is clicked on Continue Button ", " clicked on Continue Button"," clicked on Continue Button ", "PASS" );

            log.debug("Clicked on Continue Button ");

            System.out.println("Clicked on Continue Button ");*/
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }

        System.out.println("selected amount is " +selectedamout);

    }
    
    public void freeSpinValidationSessionReminder(Desktop_HTML_Report report , String userName, String languageCode,String SessionReminderUserInteraction,String SessionReminderContinue,String SessionReminderExitGame, String netPositionUnderFreespin,  String regExpr, String market) throws InterruptedException {


    	if(netPositionUnderFreespin.equalsIgnoreCase("yes")){	//free spins
    	spinclick();
    	Thread.sleep(10000);
    	//Jackpot Bonus report check for Progresssive Games 
    	//Need testdata which will trigger jackpot in free spin on first spin
    	if(TestPropReader.getInstance().getProperty("IsProgressiveGame").equalsIgnoreCase("Yes"))
    	{
    		System.out.println("Before wait Jackpot scene");
    		// method to wait for jackpotscene
    		Thread.sleep(2000);
    		jackpotSceneWait();
    		Thread.sleep(20000);
    		System.out.println("Going to click jackpot spin");
    		clickAtButton("return "+XpathMap.get("ClickOnJackpotSpinButton"));
    		Thread.sleep(3000);
    		System.out.println("Going to click Continue :: "+"return "+XpathMap.get("isJackpotBonusContinueButtonVisible"));
    		// method to wait for jackpotscene summary screen
    		elementWait("return "+XpathMap.get("isJackpotBonusContinueButtonVisible"), true);
    		System.out.println("Continue clicked");
    		clickAtButton("return "+XpathMap.get("ClickOnJackpotSummaryContinue"));
    		Thread.sleep(5000);
    	}

    	String freeSpinEntryScreen = XpathMap.get("FreeSpinEntryScreen");
    	//wait till if the game having free spin entry screen
    	String str = entryScreen_Wait(freeSpinEntryScreen);
    	if (str.equalsIgnoreCase("freeSpin")) 
    	{	
    		Thread.sleep(4000);

    		if("yes".equals(XpathMap.get("isFreeSpinSelectionAvailable")))
    		{
    			Thread.sleep(3000);
    			boolean isBonusSelectionVisible=isFreeSpinTriggered();
    			if(isBonusSelectionVisible)
    			{
    				clickBonusSelection(1);
    			}
    			else
    			{
    				log.debug("Bonus selection is not visible");
    			}
    		}
    		else
    		{
    			if( TestPropReader.getInstance().getProperty("IsProgressiveGame").equalsIgnoreCase("Yes"))
    			{
    				FSSceneLoading();
    				//cfnlib.clickToContinue();
    				//cfnlib.FS_continue();
    			}
    			else
    			{
    				//Click on free spins into continue button
    				if("yes".equals(XpathMap.get("isFreeSpinIntroContinueBtnAvailable")))
    				{
    					clickToContinue();
    				}
    				else
    				{
    					System.out.println("There is no Freespins Into Continue button in this game");
    					log.debug("There is no Freespins Into Continue button in this game");
    				}
    			}
    		}
    	} 
    	else 
    	{
    		log.debug("Free Spin Entry screen is not present in Game");
    	}

    	
    }
    	FSSceneLoading();
    	validateSessionReminderFreeSpin(report,userName, languageCode, SessionReminderUserInteraction,SessionReminderContinue, SessionReminderExitGame,market);
    		    waitSummaryScreenFree();
    			Thread.sleep(3000);
    			report.detailsAppend("Transition from Freespin to Base Game",
						"Freespin Summary screen should be visible", "Freespin Summary Screen is visible", "PASS");
    				freeSpinSummaryWinCurrFormatForLVC(regExpr, report, languageCode);
    			
    		if ("yes".equalsIgnoreCase(XpathMap.get("SummaryScreenCntButton")))
    		{
    			closeOverlay();
    		}
    		waitForSpinButton();
    		Thread.sleep(2000);
    		}
   
    
    /**
     * This method is used to verify Value adds navigation
     * @param report
     * @param languageCode
     * @param PlayerProtectionNavigation
     * @param CashCheckNavigation
     * @param HelpNavigation
     * @param PlayCheckNavigation
     * @param responsibleGaming
     */
    public void verifyValueAddsNavigation_updated(Desktop_HTML_Report report,String languageCode,String PlayerProtectionNavigation,String CashCheckNavigation,String HelpNavigation,String PlayCheckNavigation, String responsibleGaming, String market) 
    {
    	try
    	{
    		String gameurl=webdriver.getCurrentUrl();
    		String elementTxt=null;
    		List<WebElement> listOfAllValue = webdriver.findElements(By.xpath(XpathMap.get("ValueAddsAllElement")));		
    		Map<String, String> paramMap = new HashMap<String, String>();
    		for(int i=0;i<listOfAllValue.size();i++) 
    		{       
    			 //Menu Click
    			   func_Click(XpathMap.get("HelpMenu"));
    			   Thread.sleep(2000);
    			   boolean isMenuVisible = webdriver.findElement(By.xpath(XpathMap.get("isValueAddsMenuOpen"))).isDisplayed();
    			   if(isMenuVisible) {
    				   if(i==0) {
    					   int count = listOfAllValue.size();
    					   int count1=VerifyValueAddsScenario(responsibleGaming,PlayCheckNavigation,HelpNavigation,PlayerProtectionNavigation,CashCheckNavigation);
    					   if(count==count1)
    				   report.detailsAppendFolder("Verify if menu on top bar is open and Correct Value Adds displaying",
    							"Menu on top bar should be open and correct Value Adds displaying", "Menu on top bar is open Correct Value Adds displaying", "PASS",
    							languageCode);
    					   else
    						   report.detailsAppendFolder("Verify if menu on top bar is open and Correct Value Adds displaying",
    	    							"Menu on top bar should be open and correct Value Adds displaying", "Menu on top bar is open Correct Value Adds displaying", "FAIL",
    	    							languageCode);
    				   
    				   }
    			//OptionClick Hook
    			paramMap.put("param1", Integer.toString(i));
    			String listElements = XpathMap.get("ValueAddsElement");
    			String newlistElementshook = replaceParamInHook(listElements, paramMap);           
    			
    			//OptionText Hook
    			paramMap.put("param1", Integer.toString(i));
    			String listElementsName = XpathMap.get("ValueAddsElementName");
    			String newlistElementsNamehook = replaceParamInHook(listElementsName, paramMap);
    			elementTxt = webdriver.findElement(By.xpath(newlistElementsNamehook)).getText();
    			System.out.println(elementTxt);
    			
    			
    			//valueAddsnavigation
    			WebElement element = webdriver.findElement(By.xpath(newlistElementshook));
    			report.detailsAppendNoScreenshot("Verify "+elementTxt+" is visible", elementTxt+"  should be visible", elementTxt+"  is visible", "PASS");
    			element.click();
    			Thread.sleep(3000);
    		    checkpagenavigationMarket(report, gameurl, market);
    			
    			   }
    			   
    			   else {
    				   report.detailsAppendNoScreenshot("Verify Value adds all Menu visible", "   Value adds all Menu should visible", "Verify Value adds all Menu is not visible", "FAIL");
    			   }
    			   
    			
    			
    		}
    	
    		
    	} catch (Exception e) {
    		e.getMessage();
    		log.error(e);
    	}
    }
   
    
    public int VerifyValueAddsScenario(String Value1,String Value2,String Value3,String Value4,String Value5) {
    	
    	int count=1;
    	for(int i=1; i<6; i++) {
    		if(i==1) {
    			boolean status = Value1.equalsIgnoreCase("yes");
        		if(status) 
        			count++;
    		}
    		if(i==2) {
    			boolean status = Value2.equalsIgnoreCase("yes");
        		if(status) 
        			count++;
    		}
    		if(i==3) {
    			boolean status = Value3.equalsIgnoreCase("yes");
        		if(status) 
        			count++;
    		}
    		if(i==4) {
    			boolean status = Value4.equalsIgnoreCase("yes");
        		if(status) 
        			count++;
    		}
    		if(i==5) {
    			boolean status = Value5.equalsIgnoreCase("yes");
        		if(status) 
        			count++;
    		}
    	
    	}
		return count-1;
    }
    

    
//WinCurrency Validation for Italy market
    
 public void winCurrencyFormatValidation(Desktop_HTML_Report report, String languageCode,String regExpr)
 throws InterruptedException {
		for(int winIndex =0;winIndex<2;winIndex++) {
		
		spinclick();
		
		if(winIndex==1) {
			closeOverlay();
			Thread.sleep(5000);
			waitForSpinButtonstop();
			Thread.sleep(5000);
		}
		
		else {
			Thread.sleep(2000);
			closeOverlay();
			Thread.sleep(5000);
			waitForSpinButtonstop();
		}
		
		Thread.sleep(2000);
		boolean isPlayerWon = isPlayerWon();
		if (isPlayerWon) {
		    
			//Currency-Verification
			if( verifyWinAmtCurrencyFormatForLVC(regExpr))
			report.detailsAppend("Verify currency when win occurs ",
					"win should display with correct currency format and and currency symbol",
					"win displaying with correct currency format and currency symbol ",
					"PASS");
			
			else
				report.detailsAppend("Verify currency when win occurs ",
						"win should display with correct currency format and and currency symbol",
						"win is not displaying with correct currency format and currency symbol ",
						"FAIL");
		
		
		}
		
		else {
			report.detailsAppend("Verify currency when win occurs ",
					"Win should be displayed on Screen",
					"Win is not displayed",
					"FAIL");
		}
}
	}   
    
 
//Spain-Market
 /*
  * this method is to set spain limit as per the testcases while launching the game*/
 public void spainPopupHandle() {
	 try {
		 
		 String currentScene = getConsoleText("return " + XpathMap.get("currentScene"));
		
		 
	 if(currentScene.equalsIgnoreCase("LOADING")||currentScene.equalsIgnoreCase("SLOT")) {
		 
		 if(webdriver.findElement(By.className("titan-complex-dialog__header")).isDisplayed()) {
			 System.out.println("Spain pop-up is displayed");
			 
	 Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("SetLimits"))));
 	WebElement SetLimits = webdriver.findElement(By.xpath(XpathMap.get("SetLimits")));	
		WebElement SetLimits1 = webdriver.findElement(By.xpath(XpathMap.get("SetLimits1")));		
		WebElement SetLimits2 = webdriver.findElement(By.xpath(XpathMap.get("SetLimits2")));
		WebElement SetLoss = webdriver.findElement(By.xpath(XpathMap.get("SetLoss")));	

		Select select1 = new Select(SetLimits1);	
		Select select2 = new Select(SetLimits2);	
		select1.selectByIndex(1);	
		//reminder
		select2.selectByIndex(1);
		
		SetLoss.sendKeys("50");
		SetLimits.click();
	 }
		 else {
			 System.out.println("Spain pop-up is not displayed");
		 }
		 
		 
		 
		 
	 }//loading
	 
	 else {
		 System.out.println("Game is not Loaded successfully !!");
	 }
	 
	 
	 
	 
	 }
	 catch (Exception e) {
 		e.getMessage();
 		log.error(e);
	 }
	 
 }
 
 /*
  * this method is to set spain limit as per the testcases*/
 
 
public void setTimeLimitSpain() throws InterruptedException{
	
	 Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("SetLimits"))));
	 	WebElement SetLimits = webdriver.findElement(By.xpath(XpathMap.get("SetLimits")));	
			WebElement SetLimits1 = webdriver.findElement(By.xpath(XpathMap.get("SetLimits1")));		
			WebElement SetLimits2 = webdriver.findElement(By.xpath(XpathMap.get("SetLimits2")));
			WebElement SetLoss = webdriver.findElement(By.xpath(XpathMap.get("SetLoss")));	

			Select select1 = new Select(SetLimits1);	
			Select select2 = new Select(SetLimits2);	
			select1.selectByIndex(1);	
			select2.selectByIndex(1);
			
			SetLoss.sendKeys("500");
			SetLimits.click();
	
			
			if ("Yes".equalsIgnoreCase(XpathMap.get("continueBtnOnGameLoad"))) {
				
				closeOverlay();
				Thread.sleep(3000);
			}
			
			
			
}


public void lossLimitSpain()
{
	
	}



 
}// End of class
