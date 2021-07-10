package testPages.walletHub;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TestInsuranceReviewPage {

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




	
	public TestInsuranceReviewPage() {
		// TODO Auto-generated constructor stub
	}

}
