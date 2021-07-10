package testPages.walletHub;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WriteReviewPage {
	
	@FindBy(xpath = "//write-review//h4")
	WebElement writeReviewPageHeader;


	@FindBy(xpath = "//write-review//review-star[@class='rvs-svg']//*[@fill='none']")
	WebElement writeReviewPageReviewsHighlighted;


	@FindBy(xpath = "//div[@class='dropdown second']")
	WebElement typeDropdown;


	@FindBy(xpath = "//div[@class='dropdown second opened']")
	WebElement typeDropdownOpened;


	 //div[@class='dropdown second opened']//ul/li[text()='Health Insurance']


	@FindBy(xpath = "//write-review//textarea[@placeholder='Write your review...']")
	WebElement writeReviewTextArea;

	@FindBy(xpath = "//write-review//textarea//ancestor::div[contains(@class,'progress-indicator-container')]//div[@class='wrev-user-input-count']")
	WebElement noOfCharctersAllowedDescription;

	@FindBy(xpath = "//write-review//textarea//ancestor::div[contains(@class,'progress-indicator-container')]//div[@class='wrev-user-input-count']/span")
	WebElement currentNoOfCharacters;

	@FindBy(xpath = "//sub-navigation//div[text()='Submit']")
	WebElement writeReviewPageSubmitBtn;
	
	public WriteReviewPage() {
		// TODO Auto-generated constructor stub
	}

}
