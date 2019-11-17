package com.android.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static String capture(WebDriver driver,String screenShotName) 
    {
        String dest = System.getProperty("user.dir") +"\\ErrorScreenshots\\"+screenShotName+getcurrentdateandtime()+".png";

        try {
        	TakesScreenshot ts = (TakesScreenshot)driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(dest);
			FileUtils.copyFile(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
                     
        return dest;
    }
	
	public static String getcurrentdateandtime(){
		String str = null;
		try{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
		Date date = new Date();
		str= dateFormat.format(date);
		str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
		}
		catch(Exception e){

		}
		return str;
		}
}
