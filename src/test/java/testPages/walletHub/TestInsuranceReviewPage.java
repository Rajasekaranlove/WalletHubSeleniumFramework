package testPages.walletHub;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wallethub.support.browserActions;

public class TestInsuranceReviewPage {

	WebDriver driver;

	@FindBy(xpath = "//h1[text()='Test Insurance Company']")
	WebElement landingPageHeader;

	@FindBy(xpath = "//h3[text()='Your Rating']")
	WebElement yourRatingSectionHeader;

	@FindBy(xpath = "//review-star[@class='rvs-svg']//*[@fill='none']")
	WebElement reviewsHighlighted;


	@FindBy(xpath = "//section[@class='rvtab-content']/article[1]//div[contains(@class,'rvtab-ci-top')]/div[2]/span[1]")
	WebElement yourReviewSectionHeader;

	@FindBy(xpath = "//section[@class='rvtab-content']/article[1]//div[contains(@class,'rvtab-ci-content')]")
	WebElement reviewContent;

	@FindBy(xpath = "//title[text()='test insurance company metatitle test']")
	WebElement pageTitle;


	public TestInsuranceReviewPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public String validateHighlightedRatings(WebDriver driver, String ratingStars) throws Exception
	{

		browserActions.waitForElement(driver, landingPageHeader, "Test Insurance Company landing page header");

		WebElement element = driver.findElement(By.xpath("//review-star[@class='rvs-svg']/div/*["+ ratingStars +"]"));

		browserActions.scroll_into_view(yourRatingSectionHeader, driver, "Your Rating section");

		browserActions.mouse_hover_action(element, driver, "Hover on rating start " + ratingStars);

		String noOfStarsHighlighted = String.valueOf(driver.findElements(By.xpath("//review-star[@class='rvs-svg']//*[@fill='none']")).size());

		return noOfStarsHighlighted;

	}

	public WriteReviewPage clickGivenRatingStar(WebDriver driver, String ratingStars) throws Exception
	{

		WebElement element = driver.findElement(By.xpath("//review-star[@class='rvs-svg']/div/*["+ ratingStars +"]"));

		browserActions.click_element(element, driver, "Click on Rating Star " + ratingStars);

		return new WriteReviewPage(driver);
	}


	public String getPostedReviewContent(WebDriver driver) throws Exception
	{
		
		browserActions.waitForJStoLoad(driver);
		
		browserActions.waitForElement(driver, landingPageHeader, "Landing Page Header");
		
		WebElement yourReviewSectionHeader = driver.findElement(By.xpath("//section[@class='rvtab-content']/article[1]//div[contains(@class,'rvtab-ci-top')]/div[2]/span[1]"));

		browserActions.scroll_into_view(yourReviewSectionHeader, driver, "Your Review Section Header");

		WebElement reviewContent = driver.findElement(By.xpath("//section[@class='rvtab-content']/article[1]//div[contains(@class,'rvtab-ci-content')]"));

		String postedReview = browserActions.getText(driver, reviewContent, "Your Review Content");

		return postedReview;

	}

}
