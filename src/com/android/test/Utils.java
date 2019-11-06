package com.android.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;
import com.thoughtworks.selenium.webdriven.commands.KeyEvent;

import android.content.pm.PackageManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class Utils {

	public List<String> readExcel(String filePath, String fileName, String sheetName) throws IOException {

		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook itemData = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		String value = null;
		List<String> excel_values = new ArrayList<String>();
		System.out.println(fileExtensionName);
		
		if (fileExtensionName.equals(".xlsx")) {
			itemData = new XSSFWorkbook(inputStream);
		} 
		
		else if (fileExtensionName.equals(".xls")) {
			itemData = new HSSFWorkbook(inputStream);
		}
		
		Sheet testDataSheet = itemData.getSheet(sheetName);
		int rowCount = testDataSheet.getLastRowNum() - testDataSheet.getFirstRowNum();
		
		for (int i = 0; i < rowCount + 1; i++) {
			Row row = testDataSheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				value  = row.getCell(j).getStringCellValue();
				excel_values.add(value);
				System.out.print(row.getCell(j).getStringCellValue() + "| ");
			}
		}
		return excel_values;

	}
	
	public static boolean verifyElementDisplayed(WebElement element)
	{
		boolean isDisplayed = true;
		try
		{
			element.getTagName();
		}
		catch(Exception e)
		{
			isDisplayed = false;
		}
		return isDisplayed;
	}
	
	public static boolean verifyElementnotDisplayed(WebElement element)
	{
		return !verifyElementDisplayed(element);
	}

	public void waitForScreenToLoad(AppiumDriver lDriver, WebElement element, int seconds){

	      WebDriverWait wait = new WebDriverWait(lDriver,seconds);
	      wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
	    try {
	        packageManager.getPackageInfo(packageName, 0);
	        return true;
	    } catch (PackageManager.NameNotFoundException e) {
	        return false;
	    }
	}
	
	public void screenshot(AndroidDriver driver,String path_screenshot) throws IOException{
	    File srcFile=driver.getScreenshotAs(OutputType.FILE);
	    String filename=UUID.randomUUID().toString(); 
	    File targetFile=new File(path_screenshot + filename +".jpg");
	    FileUtils.copyFile(srcFile,targetFile);
	    System.out.println(OutputType.FILE);
	}
	
	public void scrollDown(AndroidDriver driver) {
		int pressX = driver.manage().window().getSize().width / 2;
		int bottomY = driver.manage().window().getSize().height * 4 / 5;
		int topY = driver.manage().window().getSize().height / 12;
		// scroll with TouchAction by itself
		scroll(pressX, bottomY, pressX, topY, driver);
	}
	private void scroll(int fromX, int fromY, int toX, int toY, AndroidDriver driver) {
		TouchAction touchAction = new TouchAction(driver);
		touchAction.longPress(fromX, fromY).moveTo(toX, toY).release().perform();
	}
}
