package com.android.test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import com.android.resources.Utils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseClass {

	private static AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;
	public static AndroidDriver<WebElement> driver;

	String node_js = "C:\\Program Files\\nodejs\\node.exe";
	String appium_js = "C:\\Users\\jyovenug\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js";
	String file_path = "C:\\Users\\jyovenug\\Appium_Proj";
	List<String> items;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeClass(alwaysRun = true)
	public void startServer() throws Exception {
		extent = new ExtentReports(System.getProperty("user.dir") + "\\test-output\\" + "ExtentScreenshot.html", true);
		builder = new AppiumServiceBuilder();
		builder.usingDriverExecutable(new File(node_js));
		builder.withAppiumJS(new File(appium_js));
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		// builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");
		service = AppiumDriverLocalService.buildService(builder);
		test = extent.startTest("Amazon App test");
		System.out.println("before test");
		init();

	}

	@AfterMethod
	public void getResult(ITestResult result) {
		String screenShotPath = Utils.capture(driver, "screenShotName");
		if (result.isSuccess()) {
			// test.log(LogStatus.FAIL, result.getThrowable());
			test.log(LogStatus.PASS, "Snapshot below pass: " + test.addScreenCapture(screenShotPath));
			System.out.println("If  " + result.isSuccess());
		} else {
			System.out.println("Else  " + result.isSuccess());
			test.log(LogStatus.FAIL, "Snapshot below fail: " + test.addScreenCapture(screenShotPath));
		}

		extent.endTest(test);

	}

	@AfterClass
	public void stopServer() {

		if (service.isRunning() == true) {
			service.stop();

		}
		extent.flush();
		extent.close();
	}

	public static AndroidDriver<WebElement> init() throws Exception {

		Utils util = new Utils();
		util.adbDevices();
		service.start();
		System.out.println("service started");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "emulator-5554");
		capabilities.setCapability("avd", "Pixel_2_API_28");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("noReset", "true");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("platformVersion", "9.0");
		// capabilities.setCapability("app", newApp.getAbsolutePath());
		capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
		// capabilities.setCapability("appPackage",
		// "com.amazon.mShop.MShopApplication");
		capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		capabilities.setCapability("autoGrantPermissions", "true");
		capabilities.setCapability("unicodeKeyboard", "true");
		// capabilities.setCapability("resetKeyboard", "true");
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driver;
	}

	public static AndroidDriver<WebElement> getDriver() {
		return driver;
	}

}
