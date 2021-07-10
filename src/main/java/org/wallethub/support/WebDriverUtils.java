package org.wallethub.support;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverUtils {

	private static WebDriverUtils instance = new WebDriverUtils();

	public static WebDriverUtils getInstance()
	{
		return instance;
	}

	ThreadLocal<WebDriver> driver1 = new ThreadLocal<WebDriver>();

	/**
	 * 
	 * @return
	 */
	public WebDriver getDriver()
	{
		return driver1.get();
	}

	/**
	 * 
	 * @param browser
	 * @param driverPath
	 * @throws Exception
	 */
	public void setDriver(String  browser) throws Exception
	{
		synchronized(this){
			try 
			{
				WebDriver driver = null;

				switch (browser.toUpperCase()) 
				{
				case "CHROME" : {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "driver" + File.separator + "chromedriver.exe");
					DesiredCapabilities cap=new DesiredCapabilities();
					cap.setCapability(CapabilityType.BROWSER_NAME, browser);
					//cap.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
					cap.setPlatform(Platform.WINDOWS);
					cap.setCapability("Make-default-browser", true);
					
					cap.setCapability(CapabilityType.TAKES_SCREENSHOT, true);

					ChromeOptions opt = new ChromeOptions();
					opt.addArguments("--ignore-certificate-errors");
					opt.addArguments("--disable-extensions"); 
					opt.addArguments("--disable-notifications");
					cap.setCapability(ChromeOptions.CAPABILITY, opt);
					opt.merge(cap);
					driver = new ChromeDriver(cap);
					break;
					}

				case "IE" : {
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator + "driver" + File.separator + "IEDriverServer.exe");
					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					caps.setCapability("ignoreZoomSetting", true);	
					caps.setCapability("Enable Protected Mode", false);					
					caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					caps.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
					caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
					caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					caps.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS,true);
					caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,"dismiss");

					caps.setBrowserName("internet explorer");
					//caps.setVersion(browserVersion);
					caps.setPlatform(Platform.WINDOWS);
					driver = new InternetExplorerDriver(caps);
					break;
					}

				case "EDGE" : {	
					System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + File.separator + "driver" + File.separator + "msedgedriver.exe");
					DesiredCapabilities capability = DesiredCapabilities.edge();
					capability.setCapability(CapabilityType.BROWSER_NAME, BrowserType.EDGE);
					//capability.setVersion(browserVersion);
					capability.setCapability(CapabilityType.BROWSER_VERSION, "WINDOWS 10");
					
					HashMap<String, Object> edgePrefs = new HashMap<String, Object>();
					edgePrefs.put("profile.default_content_settings.popups", 0);
					edgePrefs.put("profile.default_content_setting_values.notifications", 2);	
					
					edgePrefs.put("--ignore-certificate-errors", true);
					
					edgePrefs.put("--disable-extensions", true);
					
					edgePrefs.put("--disable-notifications", true);
					
					EdgeOptions options = new EdgeOptions();
					options.setCapability("prefs", edgePrefs);
					
					driver = new EdgeDriver(options);
					break;
					}

				case "FIREFOX" : {
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + File.separator + "driver" + File.separator + "geckodriver.exe");
					DesiredCapabilities capabilities = DesiredCapabilities.firefox();
					FirefoxProfile fp = new FirefoxProfile();
					capabilities.setBrowserName(browser);
					//capabilities.setVersion(browserVersion);

					capabilities.setCapability(CapabilityType.PLATFORM_NAME, "WINDOWS");
					//capabilities.setCapability(CapabilityType.VERSION, platformVersion);

					String mimeTypes = "text/plain,"; // .txt
					mimeTypes += "application/pdf,"; // .pdf
					mimeTypes += "application/msword,"; // .doc

					fp.setPreference("browser.helperApps.neverAsk.saveToDisk", mimeTypes);
					fp.setPreference("network.automatic-ntlm-auth.trusted-uris", "localhost");

					capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
					capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
					capabilities.setCapability("unexpectedAlertBehaviour", "ignore");
					capabilities.setCapability("marionette", true);
					capabilities.setCapability(FirefoxDriver.PROFILE, fp);

					capabilities.setCapability("Make-default-browser", true);
					
					fp.setPreference("permissions.default.desktop-notification", 1);

					driver = new FirefoxDriver(capabilities);
					break;
					}

				}

				if (driver.equals(null))
					throw new Exception("There is some confuguration issue while initiating the Selenium WebDriver. ");

				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

				driver1.set(driver);
			}
			catch (Exception e) {
				throw new Exception("Exception encountered in setDriver Method : " + e.getMessage().toString(), e);
			}
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void removeDriver() throws Exception
	{
		try {
			if (driver1.get()!=null) {
				driver1.get().quit();
				driver1.remove();
			}
		}
		catch(Exception e)
		{
			throw new Exception("Problem while quiting the driver instance" + e.getMessage().toString(), e);
		}
	}

}