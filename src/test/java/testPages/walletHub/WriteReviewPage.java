package testPages.walletHub;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.wallethub.support.HelperMethod;
import org.wallethub.support.browserActions;

public class WriteReviewPage {


	WebDriver driver;

	@FindBy(xpath = "//write-review//h4")
	WebElement writeReviewPageHeader;


	@FindBy(xpath = "//write-review//review-star[@class='rvs-svg']//*[@fill='none']")
	WebElement writeReviewPageReviewsHighlighted;


	@FindBy(xpath = "//div[@class='dropdown second']")
	WebElement policyTypeDropdown;


	@FindBy(xpath = "//div[@class='dropdown second opened']")
	WebElement policyTypeDropdownOpened;


	@FindBy(xpath = "//write-review//textarea[@placeholder='Write your review...']")
	WebElement writeReviewTextArea;

	@FindBy(xpath = "//write-review//textarea//ancestor::div[contains(@class,'progress-indicator-container')]//div[@class='wrev-user-input-count']")
	WebElement noOfCharctersAllowedDescription;

	@FindBy(xpath = "//write-review//textarea//ancestor::div[contains(@class,'progress-indicator-container')]//div[@class='wrev-user-input-count']/span")
	WebElement currentNoOfCharacters;

	@FindBy(xpath = "//sub-navigation//div[text()='Submit']")
	WebElement writeReviewPageSubmitBtn;

	public WriteReviewPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public String validateReviewStarsHighlighted(WebDriver driver) throws Exception
	{

		browserActions.waitForElement(driver, writeReviewPageHeader, "Write Review Page Header");

		String noOfStarsHighlightedInWriteReviewPage = String.valueOf(driver.findElements(By.xpath("//write-review//review-star[@class='rvs-svg']//*[@fill='none']")).size());

		return noOfStarsHighlightedInWriteReviewPage;
	}

	public void selectPolicyDropdown(WebDriver driver, String dropdownValue) throws Exception
	{

		browserActions.click_element(policyTypeDropdown, driver, "Policy Type dropdown");

		browserActions.waitForElement(driver, policyTypeDropdownOpened, "Policy Type dropdown opened");

		WebElement element = driver.findElement(By.xpath("//div[@class='dropdown second opened']//ul/li[text()='" + dropdownValue + "']"));

		browserActions.click_element(element, driver, "Policy Type Value");

	}


	public void writeReviews(WebDriver driver, String reviewComments) throws Exception
	{
		browserActions.send_text(writeReviewTextArea, driver, reviewComments, "Write Review Text Area");
	}

	public LinkedHashMap<String, String> getNumberOfEnteredCharacters(WebDriver driver) throws Exception
	{
		LinkedHashMap<String, String> numberOfCharcterTexts = new LinkedHashMap<String, String>();

		numberOfCharcterTexts.put("No of minimum characters text", 
				browserActions.getText(driver, noOfCharctersAllowedDescription, "No of minimum charcaters constraint"));

		numberOfCharcterTexts.put("Actual number of characters entered", 
				browserActions.getText(driver, currentNoOfCharacters, "Count of charcters entered"));

		return numberOfCharcterTexts;

	}

	public SignUpPage clickSubmitBtn(WebDriver driver) throws Exception
	{
		WebElement element = driver.findElement(By.xpath("//*[text()='Submit']"));
		browserActions.click_element(element, driver, "Submit Button");
		return new SignUpPage(driver);
	}	

}
