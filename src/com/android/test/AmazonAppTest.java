package com.android.test;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.android.pageObjects.AddToCartPage;
import com.android.pageObjects.SearchPage;
import com.android.pageObjects.SignInPage;
import com.android.resources.Utils;
import io.appium.java_client.TouchAction;

public class AmazonAppTest extends BaseClass {

	String file_path = "C:\\Users\\jyovenug\\Appium_Proj";

	@Test(priority = 1, testName = "Open SignIn Page")
	public void openSignInPage() throws Exception {
		SignInPage signInPage = new SignInPage(getDriver());
		signInPage.clickOnBreadCrumb();
		signInPage.clickOnSignIn();
	}

	@DataProvider
	public Object[][] items() throws Exception {
		List<String> items = new ArrayList<String>();
		Utils util = new Utils();
		items = util.readExcel(file_path, "amazon.xlsx", "amazon");
		Object[][] data = new Object[items.size()][];
		System.out.println(data);
		int i = 0;
		for (String excelItem : items) {
			data[i] = new Object[] { excelItem };
			i++;
		}
		return data;

	}

	@Test(priority = 2, testName = "Login with Username and Password")
	public void login() {

		try {

			SignInPage signInPage = new SignInPage(getDriver());
			SearchPage searchPage = new SearchPage(getDriver());
			signInPage.EnterUserName("");
			signInPage.clickUserNameContinue();
			signInPage.enterPassword("");
			signInPage.clickPasswordContinueBtn();
			searchPage.clickSearchIcon();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block

		}
	}

	@Test(priority = 3, testName = "Search few items", dataProvider = "items")
	public void searchItems(String searchItems) {

		try {
			SearchPage searchPage = new SearchPage(getDriver());
			Utils util = new Utils();
			WebElement element = getDriver().findElement(By.id("in.amazon.mShop.android.shopping:id/search_auto_text"));

			searchPage.editSearchText(searchItems);
			Thread.sleep(4000);
			int x = element.getLocation().getX();
			int y = element.getLocation().getY();
			System.out.println(x + y);
			TouchAction action = new TouchAction(getDriver());
			action.tap(x + 100, y + 200).release().perform();
			searchPage.clickOnItem();
			System.out.println("clicked on item");
			Thread.sleep(5000);
			util.scrollDown(getDriver());
			System.out.println("scrolling down 1");
			util.scrollDown(getDriver());
			System.out.println("scrolling down 2");

			searchPage.clickAddToCartBtn();
			System.out.println("item value : " + searchItems);
			searchPage.clickSearchIconOnItemDescPage();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 4, testName = "Delete from cart")
	public void addToCart() throws Exception {
		SignInPage signInPage = new SignInPage(getDriver());
		SearchPage searchPage = new SearchPage(getDriver());
		AddToCartPage addToCartPage = new AddToCartPage(getDriver());
		Utils util = new Utils();
		items = util.readExcel(file_path, "amazon.xlsx", "amazon");

		try {
			searchPage.clickAddToCartImage();
			Thread.sleep(5000);
			addToCartPage.clickProceedToBuy();
			Thread.sleep(4000);
			addToCartPage.clickDeliverToThisBtn();
			addToCartPage.clickContinueOnShipping();
			addToCartPage.clickContinueOnShipping();
			signInPage.clickHomeLogo();
			searchPage.clickAddToCartImage();
			for (String item : items) {
				System.out.println("item deleted : " + item);
				addToCartPage.clickDeleteBtn();
			}
			signInPage.clickOnBreadCrumb();
			signInPage.clickSettings();
			signInPage.clickLogOut();
			signInPage.clickSignOut();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
