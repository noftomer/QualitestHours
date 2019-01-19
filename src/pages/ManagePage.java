package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utilities.*;

public class ManagePage extends Base{
	protected WebDriver driver;
	public ManagePage(WebDriver driver) {
		this.driver=driver;
	}
	public static void init()
	{
		hilanetCompanyPage=PageFactory.initElements(Base.driver, HilanetCompanyPage.class);
		hilanetLogin=PageFactory.initElements(Base.driver, HilanetLogin.class);
		hilanetMainPage=PageFactory.initElements(Base.driver, HilanetMainPage.class);
		hilanetTableHours=PageFactory.initElements(Base.driver, HilanetTableHours.class);
		malamLoginPage=PageFactory.initElements(Base.driver, MalamLoginPage.class);
		malamMainPage=PageFactory.initElements(Base.driver, MalamMainPage.class);
		monthlyReportPage=PageFactory.initElements(Base.driver, MonthlyReportPage.class);
	}
}
