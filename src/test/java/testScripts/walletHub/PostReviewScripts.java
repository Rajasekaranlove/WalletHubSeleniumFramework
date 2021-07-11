package testScripts.walletHub;

import java.util.LinkedHashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.wallethub.support.WebDriverUtils;
import org.wallethub.support.browserActions;

import testPages.walletHub.SignUpPage;
import testPages.walletHub.TestInsuranceReviewPage;
import testPages.walletHub.WriteReviewPage;

import org.wallethub.support.HelperMethod;
import org.wallethub.support.TestNGSupportBaseClass;

public class PostReviewScripts extends TestNGSupportBaseClass{

	protected static Logger log=null;

	public PostReviewScripts() {
		log = LogManager.getLogger(PostReviewScripts.class);  
	}


	@Test(dataProvider = "walletHubPostReviewsTestData", 
			dataProviderClass = TestNGSupportBaseClass.class, 
			groups="reviews",
			description = "This method will validate if the wallethub customer is able to post reviews")
	public void validatePostingReviewsFunctionality
	(String Portal, String browserExecution, String Scenario, String scenarioDescription, 
			String url, String userName, String password, String profileNameDisplay, String policy, String reviewStars, 
			String review_Comments, String messageLength) throws Exception
	{

		WebDriver driver = null;
		WebDriverUtils.getInstance().setDriver(browserExecution);
		driver = WebDriverUtils.getInstance().getDriver();

		if(driver==null)
			throw new Exception("WebDriver is not initiated. ");

		driver.get(url);

		Reporter.log("1. Lauched the website: " + url);
		log.info("1. Lauched the website: " + url);

		TestInsuranceReviewPage testInsuranceReviewPage = new TestInsuranceReviewPage(driver);

		String actualHighlightedRatings = testInsuranceReviewPage.validateHighlightedRatings(driver, reviewStars);

		Assert.assertEquals(actualHighlightedRatings, reviewStars, "The highlighted number of rating stars is mismatched. "
				+ " Actual highlighted ratings " + actualHighlightedRatings + " But the expected highlighted rating stars should be " + reviewStars);

		Reporter.log("2. Validated the rating stars are highlighted on hovering ");
		log.info("2. Validated the rating stars are highlighted on hovering ");

		WriteReviewPage writeReviewPage = testInsuranceReviewPage.clickGivenRatingStar(driver, reviewStars);

		Reporter.log("3. Clicked on the Rating star " + reviewStars);
		log.info("3. Clicked on the Rating star " + reviewStars);

		String actualHighlightedRatingsInWriteReviewPage = writeReviewPage.validateReviewStarsHighlighted(driver);

		Assert.assertEquals(actualHighlightedRatingsInWriteReviewPage, reviewStars, "The highlighted number of rating stars in Write Review page is mismatched. "
				+ " Actual highlighted ratings " + actualHighlightedRatingsInWriteReviewPage + " But the expected highlighted rating stars should be " + reviewStars);

		Reporter.log("4. Validated the selected rating stars are highlighted/selected and retained in Write Review page ");
		log.info("4. Validated the selected rating stars are highlighted/selected and retained in Write Review page ");


		writeReviewPage.selectPolicyDropdown(driver,policy);

		String reviewComments = null;
		if (review_Comments.equalsIgnoreCase("Yes[AutoGenerate]")) {
			reviewComments = HelperMethod.generateRandomMessage(Integer.parseInt(messageLength));
		}

		writeReviewPage.writeReviews(driver, reviewComments);

		Reporter.log("5. Inputed the rating feedback");
		log.info("5. Inputed the rating feedback");


		LinkedHashMap<String, String> noOfCharactersConstraint = writeReviewPage.getNumberOfEnteredCharacters(driver);

		String numberOfMinimumCharactersConstraints = noOfCharactersConstraint.get("No of minimum characters text");

		Assert.assertEquals(numberOfMinimumCharactersConstraints, "130 character minimum -- Count: 200", 
				"Mismatch in the minimum number of charcters constraint. ");

		String numberOfCharactersEntered = noOfCharactersConstraint.get("Actual number of characters entered");

		Assert.assertEquals(numberOfCharactersEntered, messageLength, "Mismatch in the entered number of charcters. "
				+ "Actual count: " + numberOfCharactersEntered + " But the expected count should be " + messageLength);


		Reporter.log("6. Validated the number of characters constraints");
		log.info("6. Validated the number of characters constraints");

		SignUpPage signUpPage = writeReviewPage.clickSubmitBtn(driver);


		Reporter.log("7. Submitted the rating stars and feedback");
		log.info("7. Submitted the rating stars and feedback");

		testInsuranceReviewPage = signUpPage.loginIntoApplication(driver, userName, password);

		Reporter.log("8. Successfully logged into application");
		log.info("8. Successfully logged into application");

		String actualPostedReviewMessage = testInsuranceReviewPage.getPostedReviewContent(driver);

		Assert.assertEquals(actualPostedReviewMessage, reviewComments, "Mismatch in the posted review comment. "
				+ " Actually posted message is " + actualPostedReviewMessage + ". But the expected posted message should be " + reviewComments);

		Reporter.log("9. Validated the posted review feedback content");
		log.info("9. Validated the posted review feedback content");


	}

}
