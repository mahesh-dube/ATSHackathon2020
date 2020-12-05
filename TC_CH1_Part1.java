package pocFramework.pocFramework;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import Function_Library.AdvanceFunctions;
import Function_Library.GenericFunctions;
import TestBase.TestBase;
import Utility.ExcelUtility;

@Test
public class TC_CH1_Part1 extends TestBase {

	public void VerifyVideo() throws IOException, InterruptedException, AWTException {

		driver.get(ExcelUtility.retrieveTestData("Data_Ch1URL"));

		AdvanceFunctions.keyboardActions("esc");

		// scroll to top
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");

		// Check if first videos available on top
		GenericFunctions.CheckIfElementExist(ExcelUtility.getLocatorType("OBJ_Video1"),
				ExcelUtility.getORData("OBJ_Video1"));
		// Check if second videos available on top
		GenericFunctions.CheckIfElementExist(ExcelUtility.getLocatorType("OBJ_Video3"),
				ExcelUtility.getORData("OBJ_Video2"));
		// Check if third videos available on top
		GenericFunctions.CheckIfElementExist(ExcelUtility.getLocatorType("OBJ_Video3"),
				ExcelUtility.getORData("OBJ_Video3"));

	}
}