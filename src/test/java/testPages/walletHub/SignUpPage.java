package testPages.walletHub;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage {

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
	
	public SignUpPage() {
		// TODO Auto-generated constructor stub
	}

}
