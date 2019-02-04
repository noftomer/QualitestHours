package pages;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.xml.sax.SAXException;

import utilities.CommonOps;

public class MalamMainPage extends ManagePage {

	@FindBy(how=How.CSS,using="*[id=\"pt1:j_id7\"] div table tbody tr td div[class=\"x10u\"]")
	private WebElement presentDropDown;

	@FindBy(how=How.CSS,using="*[id='pt1:timesheet__31410110'] td[class='x113']")
	private WebElement pressnetOption;
	
	@FindBy(how=How.ID,using="pt1:theDashboard")
	private WebElement dashBoard;
	
	public MalamMainPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	private void hideDashBoard() throws IOException, ParserConfigurationException, SAXException {
		CommonOps.waitForElementToBeVisible(dashBoard, "dashboar");
		((JavascriptExecutor)this.driver).executeScript("arguments[0].style.visibility='hidden';", dashBoard);
		//visibility:hidden
	}
	public void goToHoursTable() throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			
			hideDashBoard();
			presentDropDown.click();
			CommonOps.waitForElementToBeVisible(pressnetOption, "Nochechut");
			pressnetOption.click();
			stepPass("Moved to hours table");
		}
		catch (Exception e) {
			stepFail("Didnt moved to hours table");
			failOfTestCase(e.getMessage());
		}
	}
}
