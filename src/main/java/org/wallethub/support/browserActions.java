package org.wallethub.support;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class browserActions {

	public static int maxElementWait = 10;

	protected static Logger log=null;

	static {
		log = LogManager.getLogger(browserActions.class);
	}
	
	/**
	 * 
	 * @param element
	 * @param driver
	 * @param elementDescription
	 * @throws Exception
	 */
	public static void click_element(WebElement element, WebDriver driver, String elementDescription) throws Exception {
		long startTime = StopWatch.startTime();
		try {
			if (!waitForElement(driver, element, elementDescription))
				throw new Exception(elementDescription + " not found in page!!");
			element.click();
		} catch (NoSuchElementException e) {
			long elapsedTime = StopWatch.elapsedTime(startTime);
			log.error(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
			throw new Exception(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
		}
	}


	public static void click_hidden_element(WebElement element, WebDriver driver, String elementDescription) throws Exception {
		long startTime = StopWatch.startTime();
		try {
			if (!waitForElement(driver, element, elementDescription))
				throw new Exception(elementDescription + " not found in page!!");
			
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", element);
			
		} catch (NoSuchElementException e) {
			long elapsedTime = StopWatch.elapsedTime(startTime);
			log.error(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
			throw new Exception(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
		}
	}
	

	/**
	 * 
	 * @param element
	 * @param driver
	 * @param text
	 * @param elementDescription
	 * @throws Exception
	 */
	public static void send_text(WebElement element, WebDriver driver, String text, String elementDescription) throws Exception {
		long startTime = StopWatch.startTime();
		try {
			if (!waitForElement(driver, element, elementDescription))
				throw new Exception(elementDescription + " not found in page!!");
			element.sendKeys(text);
		} catch (NoSuchElementException e) {
			long elapsedTime = StopWatch.elapsedTime(startTime);
			log.error(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
			throw new Exception(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
		}
	}


	/**
	 * 
	 * @param driver
	 * @param element
	 * @param elementDescription
	 * @return
	 * @throws Exception
	 */
	public static String getText(WebDriver driver, WebElement element, String elementDescription)
			throws Exception {
		long startTime = StopWatch.startTime();
		String textFromHTMLAttribute = "";
		try {
			textFromHTMLAttribute = element.getText().trim();
			
			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = element.getAttribute("value");

		} catch (NoSuchElementException e) {
			long elapsedTime = StopWatch.elapsedTime(startTime);
			log.error(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
			throw new Exception(elementDescription + " not found in page!!" + "Waited Duration: " + elapsedTime);
		}

		return textFromHTMLAttribute;
	}


	/**
	 * 
	 * @param driver
	 * @return
	 */
	public static boolean waitForJStoLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, maxElementWait);
		
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((long)((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				}

				catch (Exception e) {
					return true;
				}
			}
		};
		
		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		};
		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
	
	/**
	 * 
	 * @param driver
	 * @param element
	 * @return
	 * @throws Exception 
	 */
	public static boolean waitForElement(WebDriver driver, WebElement element, String elementDescription) throws Exception {
		boolean statusOfElementToBeReturned = false;
		long startTime = StopWatch.startTime();
		WebDriverWait wait = new WebDriverWait(driver, maxElementWait);
		try {
			WebElement waitElement = wait.until(ExpectedConditions.visibilityOf(element));
			if (waitElement.isDisplayed() && waitElement.isEnabled()) {
				statusOfElementToBeReturned = true;
			}
		} catch (Exception e) {    	 	
			statusOfElementToBeReturned = false;
			Long elaspedTime = StopWatch.elapsedTime(startTime);
			log.error(elementDescription + " not found in page!!" + "Waited Duration: " + elaspedTime);
			throw new Exception(elementDescription + " element not found in page!! " + "Waited Duration: " + elaspedTime);
		}
		return statusOfElementToBeReturned;
	}

}
