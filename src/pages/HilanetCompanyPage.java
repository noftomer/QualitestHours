package pages;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.xml.sax.SAXException;

import utilities.Base;

public class HilanetCompanyPage extends ManagePage{

	@FindBy(how=How.ID,using="orgId")
	private WebElement companyID;
	
	@FindBy(how=How.CSS,using="button[type='submit']")
	private WebElement btnContinue;
	
	
	public HilanetCompanyPage(WebDriver driver)
	{
		super(driver);
	}
	public void fillForm(String companyNumber) throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			companyID.sendKeys(companyNumber);
			btnContinue.click();
			stepPass("Filled the form with company number ");
		}
		catch (Exception e) {
			// TODO: handle exception
			stepFail("didnt filled the form with company number because "+e.getMessage());
			failOfTestCase(e.getMessage());
		}
	}
}
