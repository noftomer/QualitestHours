package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.xml.sax.SAXException;

import classes.ExcelFileHandle;
import classes.ReportType;
import utilities.Base;
import utilities.CommonOps;
import utilities.ElementOpertions;

public class MonthlyReportPage extends ManagePage {


	@FindBy(how=How.ID,using="pt1:saveButton")
	private WebElement saveBtn;	

	@FindBy(how=How.ID,using="d1::msgDlg::cancel")
	private WebElement okAfterSave;

	@FindBy(how=How.CSS,using="input[id^='pt1:dataTable:'][id$=':clockInDate::content']")
	private List<WebElement> daysOfWorkInCurrentMonth;
	
	private final String BEGINING="pt1:dataTable:";
	private final String ENDING="::content";
		public MonthlyReportPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

		public void validateHoursFromSourceEqualsToDestintion(List<ReportType> days)
		{
			//pt1:dataTable:0:hourTotalLabel
			//pt1:dataTable:1:hourTotalLabel
			//pt1:dataTable:2:hourTotalLabel
			//pt1:dataTable:3:hourTotalLabel
			
			String start="pt1:dataTable:";
			String end=":hourTotalLabel";
			try {
				//days=readCsvFile(fileName);
				int dayCounter=0;
				for(int day=0;day<days.size();day++) {
					try {
						WebElement hoursOfDay=driver.findElement(By.id(start+String.valueOf(day)+end));
						if(hoursOfDay.getText().equals(days.get(day).getTimePerDay())) {
							paintSuccess(driver, hoursOfDay);
						}
						else {
							paintFail(driver, hoursOfDay);
						}
							
					}
					catch(Exception e) {
						stepFail(e.getMessage());
					}
					
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
				// TODO: handle exception
			}
		}
		
	public void importHours(List<ReportType> days) throws IOException, ParserConfigurationException, SAXException
	{
		try {
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
					String entery=days.get(day).getEntryTime();
					String exit=days.get(day).getExitTime();

					WebElement dayOfMalamEnter=this.driver.findElement(By.id(BEGINING+dayCounter+":clockInDate"+ENDING));
					WebElement dayOfMalamExit=this.driver.findElement(By.id(BEGINING+dayCounter+":clockOutDate"+ENDING));
					if(days.get(day).getEntryTime().equals(" ") || days.get(day).getEntryTime().equals("") || days.get(day).getExitTime().equals(" ") || days.get(day).getExitTime().equals("")) {
						paintFail(driver,dayOfMalamEnter);
						paintFail(driver,dayOfMalamExit);
						
						WebElement reportCodeCombobox=driver.findElement(By.id("pt1:dataTable:"+String.valueOf(dayCounter)+":workTypeSelect::content"));
						String reportTypeText=days.get(day).getReportType();
						CommonOps.selectDropDownByVisibleText(reportCodeCombobox, reportTypeText);
						
						throw new Exception("The enter time or exit time of day "+days.get(dayCounter).getDay()+" is empty");
					}
					else {
						WebElement startHour=this.driver.findElement(By.id(BEGINING+dayCounter+":clockInTime"+ENDING));
						CommonOps.typeTextInTexbox(driver,startHour, "Enter hour",days.get(day).getEntryTime());
						//startHour.clear();
						//startHour.sendKeys(days.get(day).getEntryTime());
						WebElement endHour=this.driver.findElement(By.id(BEGINING+dayCounter+":clockOutTime"+ENDING));
						CommonOps.typeTextInTexbox(driver, endHour, "Exit hour", days.get(day).getExitTime());
						//endHour.clear();
						//endHour.sendKeys(days.get(day).getExitTime());
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
			CommonOps.excuteOpertionOnElement(driver, saveBtn, "Save button", ElementOpertions.CLICK);
			//saveBtn.click();
			CommonOps.excuteOpertionOnElement(driver, okAfterSave, "OK button", ElementOpertions.CLICK);
			//okAfterSave.click();
			//pt1:dataTable:0:clockInDate::content
			//pt1:dataTable:
			//							 ::content
//			WebElement dayOfMalamEnter=this.driver.findElement(By.id(BEGINING+"0:clockInDate"+ENDING));
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",dayOfMalamEnter);
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
				String reportType=ExcelFileHandle.getCellData(row, 4);
				hours.add(new ReportType(day, entry, exit, sum,reportType));
				System.out.println(hours.get(row-1));
			}
			row++;
		}while(!noRowsAnyMore);
		return hours;
	}
}
