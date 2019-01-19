package pages;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import classes.ReportType;
import utilities.Base;

public class MonthlyReportPage extends ManagePage {

	
	@FindBy(how=How.ID,using="pt1:saveButton")
	private WebElement saveBtn;	
	
	@FindBy(how=How.ID,using="d1::msgDlg::cancel")
	private WebElement okAfterSave;
	
	private final String BEGINING="pt1:dataTable:";
	private final String ENDING="::content";
	//enter date
	//id=pt1:dataTable:0:clockInDate::content
	//id=pt1:dataTable:1:clockInDate::content
	//id=pt1:dataTable:2:clockInDate::content
	
	//exit date
	//id=pt1:dataTable:0:clockOutDate::content
	//id=pt1:dataTable:1:clockOutDate::content
	//id=pt1:dataTable:2:clockOutDate::content
	
	//enter hour
	//id=pt1:dataTable:0:clockInTime::content
	//id=pt1:dataTable:1:clockInTime::content
	
	//exit hour
	//id=pt1:dataTable:0:clockOutTime::content
	
	public MonthlyReportPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public void importHours(List<ReportType> days)
	{
		//List<ReportType> days=null;
		try {
			//days=readCsvFile(fileName);
			for(int day=0;day<days.size();day++)
			{
				try {
					//WebElement dayOfMalam=this.driver.findElement(By.id(BEGINING+(day+1)+":clockInDate"+ENDING));
					//dayOfMalam.clear();
					//dayOfMalam.sendKeys(days.get(day).getDay());
					String entery=days.get(day).getEntryTime();
					String exit=days.get(day).getExitTime();
					
					WebElement dayOfMalamEnter=this.driver.findElement(By.id(BEGINING+day+":clockInDate"+ENDING));
					//id=pt1:dataTable:0:clockOutDate::content
					WebElement dayOfMalamExit=this.driver.findElement(By.id(BEGINING+day+":clockOutDate"+ENDING));
					
					if(days.get(day).getEntryTime().equals(" ") || days.get(day).getExitTime().equals(" ")) {
						paintFail(driver,dayOfMalamEnter);
						paintFail(driver,dayOfMalamExit);
						throw new Exception("The enter time or exit time of day "+days.get(day).getDay()+" is empty");
					}
					else {
						WebElement startHour=this.driver.findElement(By.id(BEGINING+day+":clockInTime"+ENDING));
						startHour.clear();
						startHour.sendKeys(days.get(day).getEntryTime());
						WebElement endHour=this.driver.findElement(By.id(BEGINING+day+":clockOutTime"+ENDING));
						endHour.clear();
						endHour.sendKeys(days.get(day).getExitTime());
						System.out.println(days.get(day).getDay()+", "+days.get(day).getEntryTime()+", "+days.get(day).getExitTime());
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",dayOfMalamEnter);
						paintSuccess(driver,dayOfMalamEnter);
						paintSuccess(driver,dayOfMalamExit);
						stepPass("Day="+(day+1));
						//sleep();
					}
					
				}
				catch(Exception e) {
					stepFail(e.getMessage());
				}
			}
			saveBtn.click();
			okAfterSave.click();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			// TODO: handle exception
		}
	}

}
