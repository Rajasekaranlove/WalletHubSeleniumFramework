package org.wallethub.support;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public class TestNGSupportBaseClass{

	protected HashMap<String,String> testData;
	protected static Logger log = LogManager.getLogger(TestNGSupportBaseClass.class);  

	protected static String executionMode;
	protected static String environment;
	protected static String driverHost;
	protected static String driverPort;
	protected static String browser;
	protected static String browserVersion;
	protected static String driverPath;
	protected static String platform;
	protected static String platformVersion;
	protected static String QA_URL;
	protected static String Staging_URL;
	protected static String Production_URL;
	protected static String headless;

	static Calendar calendar = null;
	static String folderName;
	static TestRunner testRunner = null;

	protected static String testDataXLS = null;
	protected static String testDataSheet = null;
	protected static String testRun = null;
	protected static String URL = null;

	protected static WebDriver driver = null;

	public TestNGSupportBaseClass()
	{
		driverPath = System.getProperty("user.dir") + File.separator + "drivers";
	}

	@DataProvider(parallel=true)
	public Object[][] facebookLoginTestData()
	{
		Object[][] testData = ReadFromExcel.readExcelTestData("QA_WalletHub_TestData.xlsx", "loginTestData");
		return testData;
	}

	
	@DataProvider(parallel=true)
	public Object[][] facebookPostStatusTestData()
	{
		Object[][] testData = ReadFromExcel.readExcelTestData("QA_WalletHub_TestData.xlsx", "postStatus");
		return testData;
	}

	
	
	@DataProvider(parallel=true)
	public Object[][] walletHubPostReviewsTestData()
	{
		Object[][] testData = ReadFromExcel.readExcelTestData("QA_WalletHub_TestData.xlsx", "walletHubPostReviews");
		return testData;
	}


	

	@BeforeSuite(alwaysRun=true)
	public void initInstance(ITestContext context) throws IOException
	{
		calendar = Calendar.getInstance();
		folderName = String.valueOf(calendar.getTime()).replace(":", "-");
		testRunner = (TestRunner) context;
		testRunner.setOutputDirectory(System.getProperty("user.dir") + "\\Reports\\" + folderName);

		Properties props = new Properties();
		try {
			props.load(new FileInputStream("src/test/resources/log4j.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
	}

	@AfterMethod(alwaysRun=true)
	public void afterMethod(ITestResult result, ITestContext context) throws Exception
	{
		if((result.getStatus() == ITestResult.FAILURE) || (result.getStatus() ==ITestResult.SUCCESS)) {
			if(driver!=null)
				CaptureScreenshot.takeSnapShot(driver, folderName, result.getMethod().getMethodName());
		}

		//WebDriverUtils.getInstance().removeDriver();
	}
}
