package testPages.walletHub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.wallethub.support.browserActions;

public class SignUpPage {

	WebDriver driver;

	@FindBy(xpath="//title[text()='Join WalletHub']")
	WebElement signUpPageTitle;

	@FindBy(css = ".h1-heading")
	WebElement pageHeader; //Get Header using getText()

	@FindBy(xpath = "//a[text()='Login']")
	WebElement loginSection;

	@FindBy(xpath = "//input[@placeholder='Email Address']")
	WebElement emailAddressInput;

	@FindBy(xpath = "//input[@placeholder='Password']")
	WebElement passwordInput;

	@FindBy(xpath = "//span[text()='Login']//ancestor::button")
	WebElement loginButton;

	public SignUpPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public TestInsuranceReviewPage loginIntoApplication(WebDriver driver, String userName, String password) throws Exception
	{
		browserActions.waitForElement(driver, pageHeader, "Login or sign up so we can post your review.");

		browserActions.click_element(loginSection, driver, "Login section");

		browserActions.send_text(emailAddressInput, driver, userName, "Email Address Input Field");
		
		browserActions.send_text(passwordInput, driver, password, "Password Input Field");
		
		browserActions.click_element(loginButton, driver, "Login Button");
		
		browserActions.invisibility_of_element_present(By.xpath("//title[text()='Join WalletHub']"), driver, "Join WalletHub page");

		browserActions.waitForJStoLoad(driver);
		
		return new TestInsuranceReviewPage(driver);
	}
}
