package testScripts.Facebook;

import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.wallethub.support.WebDriverUtils;

import testPages.Facebook.HomePage;
import testPages.Facebook.LoginPage;

import org.wallethub.support.TestNGSupportBaseClass;

public class LoginTestScripts extends TestNGSupportBaseClass{

	protected static Logger log=null;

	public LoginTestScripts() {
		log = LogManager.getLogger(LoginTestScripts.class);  
	}


	@Test(dataProvider = "facebookLoginTestData", 
			dataProviderClass = TestNGSupportBaseClass.class, 
			groups="login",
			description = "This method will validate the facebook login functionality")
	public void validateFacebookLoginFunctionality
	(String Portal, String browserExecution, String Scenario, String scenarioDescription, 
			String url, String userName, String password, String profileNameDisplay) throws Exception
	{
		WebDriver driver = null;
		WebDriverUtils.getInstance().setDriver(browserExecution);
		driver = WebDriverUtils.getInstance().getDriver();

		if(driver==null)
			throw new Exception("WebDriver is not initiated. ");

		driver.get(url);

		Reporter.log("1. Lauched the website: " + url);
		log.info("1. Lauched the website: " + url);

		LoginPage loginPage = new LoginPage(driver);

		if(scenarioDescription.contains("login into Facebook application using valid credentials")) {

			HomePage homePage = loginPage.validateSuccessfulLogin(driver, userName, password);

			Reporter.log("2. Logged in Successfully");
			log.info("2. Logged in Successfully");

			homePage.validateHomePageLoad(driver);

			Reporter.log("3. Home Page is loaded");
			log.info("3. Home Page is loaded");

			String actualProfileNameDisplay = homePage.getLoggedInUserName(driver);

			Assert.assertEquals(actualProfileNameDisplay, profileNameDisplay, 
					"Mismatch in Profile Name display. Actual Profile Name displayed is: " + actualProfileNameDisplay + 
					"\n Expected Profile Name should be: " + profileNameDisplay);

		}
		else if(scenarioDescription.contains("login failure in Facebook"))
		{

			String actualErrorMessage = loginPage.validateFailureLogin(driver, userName, password);
			
			System.out.println(actualErrorMessage);

			Reporter.log("2. Invalid Credentials is provided");
			log.info("2. Invalid Credentials is provided");

			ResourceBundle resourceBundle = ResourceBundle.getBundle("UIComponentsTextAppearances");

			String expectedFailreErrorMessage= null;
			if (resourceBundle != null)
				 expectedFailreErrorMessage = resourceBundle.getString("failre_error_message");
			        
			Assert.assertEquals(actualErrorMessage, expectedFailreErrorMessage, 
					"Mismatch in Profile Name display. Actual Profile Name displayed is: " + actualErrorMessage + 
					"\n Expected Profile Name should be: " + expectedFailreErrorMessage);

			Reporter.log("3. Successfully validated the failure error message on login failure in Facebook");
			log.info("3. Successfully validated the failure error message on login failure in Facebook");
		}

	}


}
