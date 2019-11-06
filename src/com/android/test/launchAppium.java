package com.android.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.android.pageObjects.SignInPage;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class launchAppium {

	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;
	AndroidDriver<WebElement> driver = null;
	
	
	String node_js =   "C:\\Program Files\\nodejs\\node.exe";
	String appium_js = "C:\\Users\\jyovenug\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js";
	String file_path = "C:\\Users\\jyovenug\\Appium_Proj";
	List<String> items;

	@BeforeTest
	public void startServer() throws Exception {

		builder = new AppiumServiceBuilder();
		builder.usingDriverExecutable(new File(node_js));
		builder.withAppiumJS(new File(appium_js));
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		// builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");
		service = AppiumDriverLocalService.buildService(builder);
	}
	
	@DataProvider(name = "Data")
	 
	  public static Object[][] items() {
	 
	        // The number of times data is repeated, test will be executed the same no. of times
	 
	        // Here it will execute two times
	 
	        return new Object[][]{ { "Kindle" }, {"iPAD" }};
	 
	  }

	@AfterTest
	public void stopServer() {

		if (service.isRunning() == true)

		{
			service.stop();
		}
	}

	public void adbDevices() throws Exception {
		Process process = Runtime.getRuntime().exec("adb devices");
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;

		Pattern pattern = Pattern.compile("^([a-zA-Z0-9\\-]+)(\\s+)(device)");
		Matcher matcher;

		while ((line = in.readLine()) != null) {
			if (line.matches(pattern.pattern())) {
				matcher = pattern.matcher(line);
				if (matcher.find())
					System.out.println("Attached devices are - " + matcher.group(1));
			}
		}

	}
	

	@Test
	public void test_Server() throws Exception {
		
		Utils util = new Utils();
		
		adbDevices();
		service.start();
		System.out.println("service started");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// File app= new File(apk_file_path);
		// File newApp = new File (app ,"amazon.apk" );
		capabilities.setCapability("deviceName", "emulator-5554");
		capabilities.setCapability("avd", "Pixel_3_API_28");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("noReset", "true");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("platformVersion", "9.0");
		// capabilities.setCapability("app", newApp.getAbsolutePath());
		capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
		//capabilities.setCapability("appPackage", "com.amazon.mShop.MShopApplication");
		capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		capabilities.setCapability("autoGrantPermissions", "true");
		capabilities.setCapability("unicodeKeyboard", "true");
		// capabilities.setCapability("resetKeyboard", "true");
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		Thread.sleep(5000);
		util.scrollDown(driver);
		SignInPage signInPage = new SignInPage(driver);
		signInPage.clickOnBreadCrumb();
		util.screenshot(driver, "APPium");
		signInPage.clickOnSignIn();
		//excelRead.waitForScreenToLoad(driver, element, seconds);
		util.screenshot(driver, "APPium");
		signInPage.EnterUserName("");
		signInPage.clickUserNameContinue();
		signInPage.enterPassword("");
		Thread.sleep(2000);
		signInPage.clickPasswordContinueBtn();
		items = util.readExcel(file_path, "amazon.xlsx", "amazon");
		signInPage.clickSearchIcon();
		for (String item : items) {
			WebElement element = driver.findElement(By.id("in.amazon.mShop.android.shopping:id/search_auto_text"));
			signInPage.editSearchText(item);
			Thread.sleep(4000);
			int x = element.getLocation().getX();
			int y = element.getLocation().getY();
			System.out.println(x + y);
			TouchAction action = new TouchAction(driver);
			action.tap(x + 100, y + 200).release().perform();
			signInPage.clickOnItem();
			Thread.sleep(5000);
			util.scrollDown(driver);
			util.scrollDown(driver);
			signInPage.clickAddToCartBtn();
			System.out.println("item value : " + item);
			signInPage.clickSearchIconOnItemDescPage();
		}
		WebElement addToCart = driver.findElement(By.id("in.amazon.mShop.android.shopping:id/action_bar_cart_image"));
		addToCart.click();
		Thread.sleep(5000);
		//signInPage.clickAddToCartImage();
		signInPage.clickProceedToBuy();
		WebElement deliverToThis = driver.findElement(By.xpath("//android.widget.Button[contains(text(),‘Deliver to this address’)]"));
		deliverToThis.click();
		//signInPage.clickDeliverToThisBtn();
		signInPage.clickContinueOnShipping();
		signInPage.clickContinueOnShipping();
		signInPage.clickHomeLogo();
		driver.findElement(By.id("in.amazon.mShop.android.shopping:id/action_bar_cart_image")).click();
		signInPage.clickAddToCartImage();
		for (String item : items) {
			System.out.println("item deleted : " + item);
			signInPage.clickDeleteBtn();
		}
		signInPage.clickOnBreadCrumb();
		signInPage.clickSettings();
		signInPage.clickLogOut();
		signInPage.clickSignOut();
		driver.quit();
	}



}
