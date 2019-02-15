package pages;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.xml.sax.SAXException;

import utilities.CommonOps;
import utilities.ElementOpertions;

public class MalamLoginPage extends  ManagePage {

	@FindBy(how=How.ID,using="indexNumInput::content")
	private WebElement companyNumber;
	
	@FindBy(how=How.ID,using="useridInput::content")
	private WebElement employeeID;
	
	@FindBy(how=How.ID,using="it2::content")
	private WebElement password;
	
	@FindBy(how=How.ID,using="loginButtonText")
	private WebElement loginBtn;
	
	public MalamLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void fillForm(String companyNumber,String empID,String pass) throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			CommonOps.typeTextInTexbox(driver, this.companyNumber, "Company number", companyNumber);
			//this.companyNumber.sendKeys(companyNumber);
			CommonOps.typeTextInTexbox(driver, this.employeeID, "ID", empID);
			//this.employeeID.sendKeys(empID);
			CommonOps.typeTextInTexbox(driver, this.password, "Password", pass);
			//this.password.sendKeys(pass);
			CommonOps.excuteOpertionOnElement(driver, loginBtn, "Login", ElementOpertions.CLICK);
			//loginBtn.click();
			stepPass("Filled login form->company , employeeid and password");
		}
		catch (Exception e) {
			stepFail("Didnt Filled login form->company , employeeid and password because "+e.getMessage());
			failOfTestCase(e.getMessage());
		}
	}
}
