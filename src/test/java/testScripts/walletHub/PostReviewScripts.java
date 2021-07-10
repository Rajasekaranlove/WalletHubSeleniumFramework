package testScripts.walletHub;

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
			String url, String userName, String password, String profileNameDisplay, String Policy, String reviewStars, String review_Comments) throws Exception
	{
		
		WebDriver driver = null;
		WebDriverUtils.getInstance().setDriver(browserExecution);
		driver = WebDriverUtils.getInstance().getDriver();

		if(driver==null)
			throw new Exception("WebDriver is not initiated. ");

		driver.get(url);
		
		Reporter.log("1. Lauched the website: " + url);
		log.info("1. Lauched the website: " + url);
		
		driver.findElement(By.xpath("//h1[text()='Test Insurance Company']"));
		
		WebElement element = driver.findElement(By.xpath("//review-star[@class='rvs-svg']/div/*["+ reviewStars +"]"));

		WebElement ratingSection =  driver.findElement(By.xpath("//h3[text()='Your Rating']"));
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ratingSection);
		
		Actions actions = new Actions(driver);
		
		actions.moveToElement(element).build().perform();
		
		int noOfStarsHighlighted = driver.findElements(By.xpath("//review-star[@class='rvs-svg']//*[@fill='none']")).size();
		
		System.out.println(noOfStarsHighlighted);
		
		element.click();
		
		driver.findElement(By.xpath("//write-review//h4"));
		
		int noOfStarsHighlightedInWriteReviewPage = driver.findElements(By.xpath("//write-review//review-star[@class='rvs-svg']//*[@fill=\"none\"]")).size();
		
		System.out.println(noOfStarsHighlightedInWriteReviewPage);
		
		
		driver.findElement(By.xpath("//div[@class='dropdown second']")).click();
		
		driver.findElement(By.xpath("//div[@class='dropdown second opened']"));

		
		driver.findElement(By.xpath("//div[@class='dropdown second opened']//ul/li[text()='Health Insurance']")).click();
		
		String reviewComments = null;

		if (review_Comments.equalsIgnoreCase("Yes[AutoGenerate]")) {
			
			reviewComments = HelperMethod.generateRandomMessage(200);

			driver.findElement(By.xpath("//write-review//textarea[@placeholder='Write your review...']")).sendKeys(reviewComments);
			
			String text = driver.findElement(By.xpath("//write-review//textarea//ancestor::div"
					+ "[contains(@class,'progress-indicator-container')]//div[@class='wrev-user-input-count']")).getText();

			System.out.println(text);

			String count = driver.findElement(By.xpath("//write-review//textarea//ancestor::div[contains(@class,'progress-indicator-container')]//div[@class='wrev-user-input-count']/span")).getText();
			
			System.out.println(count);
		}
		
	
		driver.findElement(By.xpath("//sub-navigation//div[text()='Submit']")).click();
		
		
		System.out.println(driver.findElement(By.cssSelector(".h1-heading")).getText());
		//Login or sign up so we can post your review.
		
		
		driver.findElement(By.xpath("//a[text()='Login']")).click();
		

		driver.findElement(By.xpath("//input[@placeholder='Email Address']")).sendKeys(userName);
		
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
		
		driver.findElement(By.xpath("//span[text()='Login']//ancestor::button")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 60);
		
		WebElement pageTitle = driver.findElement(By.xpath("//title[text()='Join WalletHub']"));
		System.out.println("Check -1 ");
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//title[text()='Join WalletHub']")));
		System.out.println("Check 0 ");
		browserActions.waitForJStoLoad(driver);
		
		System.out.println("Check 1 ");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@class='rvtab-content']"
				+ "/article[1]//div[contains(@class,'rvtab-ci-top')]/div[2]/span[1]")));
		
		String yourReviewHeader = driver.findElement(By.xpath("//section[@class='rvtab-content']/"
				+ "article[1]//div[contains(@class,'rvtab-ci-top')]/div[2]/span[1]")).getText();
		
		System.out.println("Check 2 ");
		System.out.println(yourReviewHeader);
		
		String postedReview = driver.findElement(By.xpath("//section[@class='rvtab-content']/article[1]//div[contains(@class,'rvtab-ci-content')]")).getText();
		
		System.out.println(postedReview);
		
		Assert.assertEquals(postedReview, reviewComments, "The posted review comment is not matched with "
				+ "the expected. Actual posted message is " + postedReview + " But the expected posted message is " + reviewComments);
		
	}

}
