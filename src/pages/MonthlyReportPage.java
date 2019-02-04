package pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import classes.ExcelFileHandle;
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
			int dayCounter=0;
			for(int day=0;day<days.size();day++) {
				
				String dayText=days.get(day).getDay();
				int dayFromSource=Integer.parseInt(days.get(day).getDay().substring(0, 2));
				WebElement dayOfMalam=this.driver.findElement(By.id(BEGINING+(dayCounter)+":clockInDate"+ENDING));
				int dayOfDestintion=Integer.parseInt(dayOfMalam.getAttribute("value").substring(0,2));
				if(dayOfDestintion!=dayFromSource) {
					do {
						paintFail(driver,dayOfMalam);
						dayCounter++;
						dayOfMalam=this.driver.findElement(By.id(BEGINING+(dayCounter)+":clockInDate"+ENDING));
						dayOfDestintion=Integer.parseInt(dayOfMalam.getAttribute("value").substring(0,2));
					}while(dayOfDestintion!=(dayFromSource));
				}
				try {
					dayOfMalam=this.driver.findElement(By.id(BEGINING+(dayCounter)+":clockInDate"+ENDING));
					paintSuccess(driver,dayOfMalam);
					//dayOfMalam.clear();
					//dayOfMalam.sendKeys(days.get(day).getDay());
					String entery=days.get(day).getEntryTime();
					String exit=days.get(day).getExitTime();

					WebElement dayOfMalamEnter=this.driver.findElement(By.id(BEGINING+dayCounter+":clockInDate"+ENDING));
					//id=pt1:dataTable:0:clockOutDate::content
					WebElement dayOfMalamExit=this.driver.findElement(By.id(BEGINING+dayCounter+":clockOutDate"+ENDING));
					if(days.get(day).getEntryTime().equals(" ") || days.get(day).getEntryTime().equals("") || days.get(day).getExitTime().equals(" ") || days.get(day).getExitTime().equals("")) {
						paintFail(driver,dayOfMalamEnter);
						paintFail(driver,dayOfMalamExit);
						throw new Exception("The enter time or exit time of day "+days.get(dayCounter).getDay()+" is empty");
					}
					else {
						WebElement startHour=this.driver.findElement(By.id(BEGINING+dayCounter+":clockInTime"+ENDING));
						startHour.clear();
						startHour.sendKeys(days.get(day).getEntryTime());
						WebElement endHour=this.driver.findElement(By.id(BEGINING+dayCounter+":clockOutTime"+ENDING));
						endHour.clear();
						endHour.sendKeys(days.get(day).getExitTime());
						System.out.println(days.get(day).getDay()+", "+days.get(day).getEntryTime()+", "+days.get(day).getExitTime()+", "+days.get(day).getTimePerDay());
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",dayOfMalamEnter);
						paintSuccess(driver,dayOfMalamEnter);
						paintSuccess(driver,dayOfMalamExit);
						stepPass("Day="+(dayCounter+1));
						//sleep();
					}

				}
				catch(Exception e) {
					stepFail(e.getMessage());
				}
				dayCounter++;
			}
			saveBtn.click();
			okAfterSave.click();
//			for(int day=0;day<days.size();day++)
//			{
//				try {
//					//WebElement dayOfMalam=this.driver.findElement(By.id(BEGINING+(day+1)+":clockInDate"+ENDING));
//					//dayOfMalam.clear();
//					//dayOfMalam.sendKeys(days.get(day).getDay());
//					String entery=days.get(day).getEntryTime();
//					String exit=days.get(day).getExitTime();
//
//					WebElement dayOfMalamEnter=this.driver.findElement(By.id(BEGINING+day+":clockInDate"+ENDING));
//					//id=pt1:dataTable:0:clockOutDate::content
//					WebElement dayOfMalamExit=this.driver.findElement(By.id(BEGINING+day+":clockOutDate"+ENDING));
//					if(days.get(day).getEntryTime().equals(" ") || days.get(day).getEntryTime().equals("") || days.get(day).getExitTime().equals(" ") || days.get(day).getExitTime().equals("")) {
//						paintFail(driver,dayOfMalamEnter);
//						paintFail(driver,dayOfMalamExit);
//						throw new Exception("The enter time or exit time of day "+days.get(day).getDay()+" is empty");
//					}
//					else {
//						WebElement startHour=this.driver.findElement(By.id(BEGINING+day+":clockInTime"+ENDING));
//						startHour.clear();
//						startHour.sendKeys(days.get(day).getEntryTime());
//						WebElement endHour=this.driver.findElement(By.id(BEGINING+day+":clockOutTime"+ENDING));
//						endHour.clear();
//						endHour.sendKeys(days.get(day).getExitTime());
//						System.out.println(days.get(day).getDay()+", "+days.get(day).getEntryTime()+", "+days.get(day).getExitTime()+", "+days.get(day).getTimePerDay());
//						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",dayOfMalamEnter);
//						paintSuccess(driver,dayOfMalamEnter);
//						paintSuccess(driver,dayOfMalamExit);
//						stepPass("Day="+(day+1));
//						//sleep();
//					}
//
//				}
//				catch(Exception e) {
//					stepFail(e.getMessage());
//				}
//			}
//			saveBtn.click();
//			okAfterSave.click();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			// TODO: handle exception
		}
	}
	public List<ReportType> importHoursFromFile(String excelFile) throws Exception
	{
		List<ReportType> hours=new ArrayList<ReportType>();
		String cell="";
		int row=1;
		boolean noRowsAnyMore=false;
		do{
			if(ExcelFileHandle.getCellData(row, 0).equals("")) {
				noRowsAnyMore=true;
			}
			else {
				String day=ExcelFileHandle.getCellData(row, 0);
				String entry=ExcelFileHandle.getCellData(row, 1);
				String exit=ExcelFileHandle.getCellData(row, 2);
				String sum=ExcelFileHandle.getCellData(row, 3);
				hours.add(new ReportType(day, entry, exit, sum));
				System.out.println(hours.get(row-1));
			}
			row++;
		}while(!noRowsAnyMore);
		return hours;
	}
}
