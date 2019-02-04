package pages;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.xml.sax.SAXException;

import utilities.Base;

public class HilanetLogin extends ManagePage {
	
	@FindBy(how=How.ID,using="user_nm")
	private WebElement userName;
	
	@FindBy(how=How.ID,using="password_nm")
	private WebElement password;

	@FindBy(how=How.CSS,using="button[type=\"submit\"]")
	private WebElement btn;
	
	
	public HilanetLogin(WebDriver driver) {
		super(driver);
	}
	public void fillForm(String userName,String password) throws IOException, ParserConfigurationException, SAXException
	{
		try {
			this.userName.sendKeys(userName);
			this.password.sendKeys(password);
			this.btn.click();
			stepPass("Filled login form with id and password");
		}
		catch (Exception e) {
			stepFail("didnt filled login form with id and password because "+e.getMessage());
			failOfTestCase(e.getMessage());
		}
		
	}
}
