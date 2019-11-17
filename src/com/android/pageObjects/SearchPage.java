package com.android.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
	private WebDriver driver;

	@FindBy(xpath = "//*[@text='Search']")
	WebElement search;
	@FindBy(id = "in.amazon.mShop.android.shopping:id/search_auto_text")
	WebElement serachEditText;
	@FindBy(xpath = "//android.view.View[@index=0]")
	WebElement item;
	@FindBy(xpath = "//android.widget.Button[@text='Add to Cart']")
	WebElement addToCartBtn;
	@FindBy(xpath = "//android.widget.ImageButton[@index=0]")
	WebElement searchIconOnItemDescPage;
	@FindBy(id = "in.amazon.mShop.android.shopping:id/action_bar_cart_image")
	// @FindBy(xpath = "//android.widget.ImageView[@index=0]")
	WebElement addToCartImage;

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickSearchIcon() {

		if (this.search.isDisplayed()) {
			search.click();
		}
	}

	public void editSearchText(String item) {

		if (this.serachEditText.isDisplayed()) {
			serachEditText.click();
			serachEditText.clear();
			serachEditText.sendKeys(item);
		}
	}

	public void clickOnItem() {

		if (this.item.isDisplayed()) {
			item.click();
		} else
			System.out.println("item not found");
	}

	public void clickAddToCartBtn() {

		if (this.addToCartBtn.isDisplayed()) {
			addToCartBtn.click();
		}
	}

	public void clickSearchIconOnItemDescPage() {

		if (this.searchIconOnItemDescPage.isDisplayed()) {
			searchIconOnItemDescPage.click();
		}
	}

	public void clickAddToCartImage() {

		if (this.addToCartImage.isDisplayed()) {
			addToCartImage.click();
		}
	}

}
