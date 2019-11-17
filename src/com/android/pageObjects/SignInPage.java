package com.android.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.android.test.BaseClass;

public class SignInPage {
	private WebDriver driver;

	@FindBy(xpath = "//*[@class='android.widget.ImageView']")
	WebElement breadCrumb;

	@FindBy(xpath = "//*[@class='android.widget.EditText']")
	WebElement userName;

	@FindBy(xpath = "//android.widget.ImageView[@index='1']")
	WebElement signInImage;

	@FindBy(xpath = "//*[@text='Continue']")
	WebElement userNameContinueBtn;

	@FindBy(xpath = "//android.widget.EditText[@index=1]")
	WebElement password;

	@FindBy(xpath = "//android.widget.Button[@text='Login']")
	WebElement passwordContinueBtn;

	@FindBy(xpath = "in.amazon.mShop.android.shopping:id/action_bar_home_logo")
	WebElement homeLogo;

	@FindBy(xpath = "//android.widget.TextView[@text='Settings']")
	WebElement settings;
	@FindBy(xpath = "//android.widget.TextView[@text='Your Orders']")
	WebElement logout;
	@FindBy(xpath = "//android.widget.Button[@text='SIGN OUT']")
	WebElement signOut;

	public SignInPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickOnBreadCrumb() {

		if (this.breadCrumb.isDisplayed()) {
			Assert.assertTrue(this.breadCrumb.isDisplayed(), "BreadCrumb is displayed");
			breadCrumb.click();
			System.out.println("Amazon website opened");
		}
	}

	public void EnterUserName(String usrName) {

		if (this.userName.isDisplayed()) {
			userName.clear();
			userName.sendKeys(usrName);
		}
	}

	public void clickOnSignIn() {

		if (this.signInImage.isDisplayed()) {
			signInImage.click();
		}
	}

	public void clickUserNameContinue() {

		if (this.userNameContinueBtn.isDisplayed()) {
			userNameContinueBtn.click();
		}
	}

	public void enterPassword(String pass) {

		if (this.password.isDisplayed()) {
			password.clear();
			password.sendKeys(pass);
		}
	}

	public void clickPasswordContinueBtn() {

		if (this.passwordContinueBtn.isDisplayed()) {
			passwordContinueBtn.click();
		}
	}

	public void clickHomeLogo() {

		if (this.homeLogo.isDisplayed()) {
			homeLogo.click();
		}
	}

	public void clickSettings() {

		if (this.settings.isDisplayed()) {
			settings.click();
		}
	}

	public void clickLogOut() {

		if (this.logout.isDisplayed()) {
			logout.click();
		}
	}

	public void clickSignOut() {

		if (this.signOut.isDisplayed()) {
			signOut.click();
		}
	}

}
