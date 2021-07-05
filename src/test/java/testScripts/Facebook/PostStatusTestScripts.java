package testScripts.Facebook;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.wallethub.support.HelperMethod;
import org.wallethub.support.TestNGSupportBaseClass;
import org.wallethub.support.WebDriverUtils;

import testPages.Facebook.HomePage;
import testPages.Facebook.LoginPage;

public class PostStatusTestScripts {

	protected static Logger log=null;

	public PostStatusTestScripts() {
		log = LogManager.getLogger(PostStatusTestScripts.class);
	}

	@Test(dataProvider = "facebookPostStatusTestData", 
			dataProviderClass = TestNGSupportBaseClass.class, 
			groups="login",
			description = "This method will validate if user is able to post status in facebook")
	public void validatePostStatusFunctionality
	(String Portal, String browserExecution, String Scenario, String scenarioDescription, 
			String url, String userName, String password) throws Exception
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

		HomePage homePage = loginPage.validateSuccessfulLogin(driver, userName, password);
		
		Reporter.log("2. Logged in Successfully");
		log.info("2. Logged in Successfully");

		homePage.validateHomePageLoad(driver);
		
		Reporter.log("3. Home Page is loaded");
		log.info("3. Home Page is loaded");

		String messageToPost = HelperMethod.generateRandomMessage(25);

		String actualPostedMessage = homePage.validatePostStatusFunctionality(driver, messageToPost);

		Assert.assertEquals(actualPostedMessage, messageToPost, "String mismatch in the status message posted. "
				+ "Expected Posted Message should be " + messageToPost + " But actually found : " + actualPostedMessage);

		Reporter.log("4. Posted a status successfully in Facebook");
		log.info("4. Posted a status successfully in Facebook");
	}

}
