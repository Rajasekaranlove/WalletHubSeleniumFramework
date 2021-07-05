package org.wallethub.support;

import java.io.File;
import java.util.Calendar;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

public class CaptureScreenshot extends TestNGSupportBaseClass{
	public static void takeSnapShot(WebDriver webdriver,String folderName, String testMethodName) throws Exception{
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		String outputFolder = testRunner.getOutputDirectory();
		String file = outputFolder + File.separator + testMethodName+".png";
		
		File DestFile=new File(file);
		FileUtils.copyFile(SrcFile, DestFile);
	}
}
