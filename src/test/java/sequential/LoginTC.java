
package sequential;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import commonUtility.PropertyFileRead;
import excelUtility.ExcelRead;
import pomClasses.POMLogin;
import webDriverUtility.DriverManager;

public class LoginTC {
	public static WebDriver driver;
	POMLogin objPomLogin;
	
	static String url = "https://qalegend.com/billing/public/login";
	static String browser = "chrome";

	@Test(priority = 1, enabled = true)
	public void logIn() throws IOException {
		String username = ExcelRead.readStringData(1, 0);
		String password = ExcelRead.integerData(1, 1);
		objPomLogin.login(username, password);

		String current_url = driver.getCurrentUrl();
		SoftAssert asser = new SoftAssert();
		asser.assertEquals(PropertyFileRead.readConfigFile("url"), current_url);
		asser.assertAll();
	}
	
	@Test(priority = 2, enabled = true, groups={"failed"})
	public void logInFailed() throws IOException, InterruptedException {
		String username = ExcelRead.readStringData(1, 0);
		String password = ExcelRead.integerData(1, 1);
		objPomLogin.login(username, password);

		String current_url = driver.getCurrentUrl();
		SoftAssert asser = new SoftAssert();
		asser.assertEquals("123", current_url);
		objPomLogin.signout();
		asser.assertAll();
	}
	

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		DriverManager objDriverManager = new DriverManager();
		objDriverManager.launchBrowser(url, browser);
		driver = objDriverManager.driver;
		objPomLogin = new POMLogin(driver);
	}



}
