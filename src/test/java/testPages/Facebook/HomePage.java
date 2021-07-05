package testPages.Facebook;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.wallethub.support.HelperMethod;
import org.wallethub.support.browserActions;

public class HomePage {

	WebDriver driver;


	@FindBy(xpath = "//a[@aria-label='Home']")
	WebElement homeIcon;

	@FindBy(xpath = "//div[@aria-label='Create a post']/div[1]/div//span")
	WebElement whatsOnYourMindArea;

	@FindBy(xpath = "//div[@role='dialog']")
	WebElement createPostDialog;

	@FindBy(xpath = "//span[text()='Create post']")
	WebElement createPostDialogHeaderText;


	@FindBy(xpath = "//div[@data-contents='true']")
	WebElement createPostWhatsOnYourMindArea;


	@FindBy(xpath = "//*[@data-text='true']/ancestor::span")
	WebElement createPostTextAreaSpan;

	@FindBy(xpath = "//*[@data-text='true']")
	WebElement createPostTextArea;

	@FindBy(xpath = "//span[text()='Post']")
	WebElement postButton;

	@FindBy(xpath = "//span[text()='Post']")
	WebElement articleSection;

	@FindBy(xpath = "((//div[@role='article'])[1]//div[text()])[1]")
	WebElement articleStatusMessage;

	@FindBy(xpath = "//span[text()='Like']")
	WebElement likeButton;

	@FindBy(xpath = "//div[@aria-label=\"Like: 1 person\"]")
	WebElement whoLikedArea;


	@FindBy(xpath = "(//div[@data-pagelet='LeftRail']//a//span[text()])[1]")
	WebElement userNameDisplay;
	
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public void validateHomePageLoad(WebDriver driver) throws Exception
	{
		browserActions.waitForElement(driver, homeIcon, "Home Icon");

		browserActions.click_element(homeIcon, driver, "Home Icon");

		browserActions.waitForElement(driver, whatsOnYourMindArea, "What's on your mind!");

	}
	
	public String getLoggedInUserName(WebDriver driver) throws Exception {
		
		String loggedInUserName = null;
		browserActions.waitForElement(driver, homeIcon, "Home Icon");
		loggedInUserName = browserActions.getText(driver, userNameDisplay, "User Name Display after logged in");
		return loggedInUserName;
	}

	

	public String validatePostStatusFunctionality(WebDriver driver, String messageToPost) throws Exception 
	{

		browserActions.waitForElement(driver, homeIcon, "Home Icon");

		browserActions.click_element(homeIcon, driver, "Home Icon");

		browserActions.click_element(whatsOnYourMindArea, driver, "What's on your mind!");

		browserActions.waitForElement(driver, createPostDialog, "Create Post Dialog");

		browserActions.waitForElement(driver, createPostDialogHeaderText, "Create Post Dialog Title");

		browserActions.click_hidden_element(createPostWhatsOnYourMindArea, driver, "Post Text Area Span");

		createPostWhatsOnYourMindArea.sendKeys(Keys.SPACE);

		browserActions.send_text(createPostTextArea, driver, messageToPost, "Create Post Text Area");

		browserActions.click_element(postButton, driver, "Post Button");

		browserActions.waitForElement(driver, articleSection, "Article / Post section");

		String postedMessage = browserActions.getText(driver, articleStatusMessage, "Posted Message");

		browserActions.click_element(likeButton, driver, "Posted Message");

		browserActions.waitForElement(driver, whoLikedArea, "Who Liked Your Status!");

		return postedMessage;

	}
}
