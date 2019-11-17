package com.android.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddToCartPage {

	private WebDriver driver;
	
	@FindBy(xpath = "//android.widget.Button[@text='Proceed to Buy']")
	WebElement proceedToBuyBtn;
	@FindBy(xpath = "//android.widget.Button[contains(text(),‘Deliver to this address’)]")
	WebElement deliverToThisBtn;
	@FindBy(xpath = "//android.widget.Button[@text='Continue']")
	WebElement continueOnShipping;
	@FindBy(xpath = "//android.widget.Button[@text='Delete']")
	WebElement deleteBtn;
	
	
	public AddToCartPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickProceedToBuy() {

		if (this.proceedToBuyBtn.isDisplayed()) {
			proceedToBuyBtn.click();
		}
	}
	
	public void clickDeliverToThisBtn() {

		if (this.deliverToThisBtn.isDisplayed()) {
			deliverToThisBtn.click();
		}
	}
	
	public void clickContinueOnShipping() {

		if (this.continueOnShipping.isDisplayed()) {
			continueOnShipping.click();
		}
	}
	
	public void clickDeleteBtn() {

		if (this.deleteBtn.isDisplayed()) {
			deleteBtn.click();
		}
	}
	
}
