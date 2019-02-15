package pages;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.xml.sax.SAXException;

import utilities.Base;
import utilities.CommonOps;
import utilities.ElementOpertions;

public class HilanetMainPage extends ManagePage{

	@FindBy(how=How.ID,using="tabItem_9_3_SpanBackground")
	private WebElement attendance;
	
	@FindBy(how=How.ID,using="innerNavBarItem_50")
	private WebElement attendanceApproval;
	
	public HilanetMainPage(WebDriver driver) {
		super(driver);
	}
	public void goToHoursTable() throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			CommonOps.excuteOpertionOnElement(driver, attendance, "Attendance", ElementOpertions.MOVETOELEMENT);
//			Actions action=new Actions(this.driver);
//			action.moveToElement(attendance).build().perform();
			CommonOps.excuteOpertionOnElement(driver, attendanceApproval, "Attendance approval", ElementOpertions.CLICK);
			//CommonOps.waitForElementToBeVisible(attendanceApproval, "attendanceApproval");
			//attendanceApproval.click();
			stepPass("Go to hours table");
		}
		catch (Exception e) {
			stepFail("didnt go to hours table because "+e.getMessage());
			failOfTestCase(e.getMessage());
		}
	}
}
