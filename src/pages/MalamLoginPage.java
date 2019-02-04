package pages;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.xml.sax.SAXException;

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
			this.companyNumber.sendKeys(companyNumber);
			this.employeeID.sendKeys(empID);
			this.password.sendKeys(pass);
			loginBtn.click();
			stepPass("Filled login form->company , employeeid and password");
		}
		catch (Exception e) {
			stepFail("Didnt Filled login form->company , employeeid and password because "+e.getMessage());
			failOfTestCase(e.getMessage());
		}
	}
}
