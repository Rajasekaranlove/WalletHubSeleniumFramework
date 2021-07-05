package testPages.Facebook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.wallethub.support.browserActions;

public class LoginPage {

	WebDriver driver;

	@FindBy(xpath = "//img[contains(@class,'fb_logo')]")
	WebElement facebookLogo;

	@FindBy(xpath = "//input[@name='email']")
	WebElement emailTextbox;

	@FindBy(xpath = "//input[@name='pass']")
	WebElement passwordTextbox;

	@FindBy(xpath = "//button[@name='login']")
	WebElement loginButton; // Returns HomePage

	@FindBy(xpath = "//a[@aria-label='Home']")
	WebElement homeIcon;

	@FindBy(xpath = "(//div[contains(@class,'clearfix')])[3]/div[2]")
	WebElement loginFailureErrorMessage;

	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}


	public HomePage validateSuccessfulLogin(WebDriver driver, String userName, String password) throws Exception
	{
		browserActions.send_text(emailTextbox, driver, userName, "Email Field");

		browserActions.send_text(passwordTextbox, driver, password, "Password Field");

		browserActions.click_element(loginButton, driver, "Login Button");

		browserActions.waitForJStoLoad(driver);

		browserActions.waitForElement(driver, homeIcon, "Login Button");

		return new HomePage(driver);

	}

	public String validateFailureLogin(WebDriver driver, String userName, String password) throws Exception{
		browserActions.send_text(emailTextbox, driver, userName, "Email Field");
		browserActions.send_text(passwordTextbox, driver, password, "Password Field");
		browserActions.click_element(loginButton, driver, "Login Button");
		browserActions.waitForJStoLoad(driver);
		browserActions.waitForElement(driver, loginFailureErrorMessage, 
				"Login Failure Error Message Lcoator");
		return browserActions.getText(driver, loginFailureErrorMessage, 
				"Login Failure Error Message Lcoator");
	}

}
