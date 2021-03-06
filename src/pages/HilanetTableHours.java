package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

//import org.apache.xerces.util.SynchronizedSymbolTable;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.xml.sax.SAXException;

import classes.ReportType;
import utilities.Base;
import utilities.CommonOps;

public class HilanetTableHours extends ManagePage{

	@FindBys( {@FindBy(how=How.CSS,using="table[id=tReps] tbody tr[id^=ctl00_mp_rptInner_ct]")} )
	private List<WebElement> rowsOfTable;

	@FindBys({@FindBy(how=How.TAG_NAME,using="iframe")})
	private List<WebElement> frames;

	@FindBy(how=How.CSS,using="td[id^='ctl00_mp_rptInner_ctl'][id$='_tdDay'] > a")
	private List<WebElement> daysOfWorkInCurrentMonth;
	
	@FindBy(how=How.CSS,using="span[id^='ctl00_mp_rptInner_ctl'][id$='_tdEntrySpan']")
	private List<WebElement> entersHour;
	
	@FindBy(how=How.CSS,using="span[id^='ctl00_mp_rptInner_ctl'][id$='_tdExitSpan']")
	private List<WebElement> exitsHour;
	
	@FindBy(how=How.CSS,using="td[id^='ctl00_mp_rptInner_ctl'][id$='_tdSum']")
	private List<WebElement> sumPerDay;
	
	@FindBy(how=How.CSS,using="td[id^='ctl00_mp_rptInner_ctl'][id$='_tdRT'] > div")
	private List<WebElement> reportType;
	
	public HilanetTableHours(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(how=How.ID,using="ctl00_mp_Strip_ChoiceCombo")
	private WebElement comboOptions;
	
	@FindBy(how=How.ID,using="mainIFrame")
	private WebElement frame;
	
	//public void importHoursToFile(String fileName) throws IOException, ParserConfigurationException, SAXException 
	public List<ReportType> importHoursToFile() throws IOException, ParserConfigurationException, SAXException
	{
		List<ReportType> daysToReport=null;
		try {
			for(WebElement f:frames)
			{
				if(f.getAttribute("id").equals(frame.getAttribute("id")))
				{
					this.driver.switchTo().frame(f); 
					daysToReport=iterateOverDays();
					driver.switchTo().defaultContent();
					break;
				}
			}	
			Base.stepPass("Iterate over all days");
		}
		catch (Exception e) {
			Base.stepFail(e.getMessage());
			Base.failOfTestCase(e.getMessage());
		}
		catch (AssertionError ex) {
			Base.stepFail(ex.getMessage());
			Base.failOfTestCase(ex.getMessage());
		}
		return daysToReport;
	}
	private List<ReportType> iterateOverDays_ignore() throws IOException, ParserConfigurationException, SAXException
	{
		List<ReportType> daysToReport=new ArrayList<ReportType>();
		try {
			String value=CommonOps.getDropDownValue(comboOptions);		
			if(!value.equals("חודש נוכחי"))
			{
				CommonOps.selectDropDownByVisibleText(comboOptions, "חודש נוכחי");
			}
			String begining="ctl00_mp_rptInner_ctl";
			int rows=rowsOfTable.size();
			String number="";
			for(int i=0;i<daysOfWorkInCurrentMonth.size();i++)
			{
				
				try {
					
						WebElement currentDay=CommonOps.waitForElementToBeVisible(daysOfWorkInCurrentMonth.get(i), "Day "+String.valueOf(i));
				    	String day=currentDay.getText();
						day=day.substring(day.length()-5, day.length());
						String txt="'"+day+"', ";
						WebElement enterHour=CommonOps.waitForElementToBeVisible(entersHour.get(i), "Enter hour for day "+String.valueOf(i));
						txt+="'"+enterHour.getText()+"',";
						WebElement exitHour=CommonOps.waitForElementToBeVisible(exitsHour.get(i), "Exit hour for day "+String.valueOf(i));
						txt+="'"+exitHour.getText()+"', ";
						WebElement sumOfCurrentDay=CommonOps.waitForElementToBeVisible(sumPerDay.get(i), "Sum for day "+String.valueOf(i));
						txt+="'"+sumOfCurrentDay.getText()+"', ";
						WebElement report=CommonOps.waitForElementToBeVisible(reportType.get(i),"Report type for day "+String.valueOf(i));
						txt+="'"+report.getText()+"'";
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)",currentDay);
						daysToReport.add(new ReportType(day, enterHour.getText(), exitHour.getText(), sumOfCurrentDay.getText(), report.getText()));
						stepPass(txt);
						System.out.println(txt);
				    	
					
				}
				catch (Exception e) {
					stepFail(e.getMessage());
					System.err.println(e.getMessage());
				}
			}
			//stepPassWithScreenShut("All signed days");
		}
		catch (Exception e) {
			stepFail(e.getMessage());
			
			System.err.println(e.getMessage());
			// TODO: handle exception
		}
		return daysToReport;
	}
	private String convertToUTF8(String s) {
	    String out = null;
	    try {
	        out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
	    } catch (java.io.UnsupportedEncodingException e) {
	        return null;
	    }
	    return out;
	}
	private void chooseCurrentMonth() throws IOException, ParserConfigurationException, SAXException {
		String value=CommonOps.getDropDownValue(comboOptions);		
		if(!value.equals("חודש נוכחי"))
		{
			CommonOps.selectDropDownByVisibleText(comboOptions, "חודש נוכחי");
		}
	}
	private List<ReportType> iterateOverDays() throws IOException, ParserConfigurationException, SAXException
	{
		List<ReportType> daysToReport=new ArrayList<ReportType>();
		try {
			chooseCurrentMonth();
     		boolean sameDay=false;
			String begining="ctl00_mp_rptInner_ctl";
			int rows=rowsOfTable.size();
			String number="";
			Thread.sleep(1500);
			String day="";
			int numberOfCellsOfRow=driver.findElements(By.cssSelector("tr#ctl00_mp_rptInner_ctl00_trReps td")).size();
			for(int i=0;i<rows;i++)
			{
				
				try {
					number=String.valueOf(i);
					if(i<10) {
						number="0"+number;
					}
					//tr tag css=ctl00_mp_rptInner_ctl11_trReps
					//		     ctl00_mp_rptInner_ctl17_trReps
					//			 ctl00_mp_rptInner_ctl21_trReps
					//if css="tr#ctl00_mp_rptInner_ctl16_trReps td" count ==17 it's with day cell
					//else if count==16 it's without tge day cell
					List<WebElement> elements=new ArrayList<>();
					///////////////////
					int currentCellOfRow=driver.findElements(By.cssSelector("tr#ctl00_mp_rptInner_ctl"+number+"_trReps td")).size();
					if(currentCellOfRow<numberOfCellsOfRow) {
						//its the same day without day cell
					sameDay=true;
//						i++;
//						number=String.valueOf(i);
//						if(i<10) {
//							number="0"+number;
//						}
						
//						//enter
//						//ctl00_mp_rptInner_ctl16_tdEntry
//						//ctl00_mp_rptInner_ctl17_tdEntry
//						
//						//exit
//						//ctl00_mp_rptInner_ctl16_tdExit
//						//ctl00_mp_rptInner_ctl17_tdExit
//						
//						//sum
//						//ctl00_mp_rptInner_ctl16_tdSum
//						//ctl00_mp_rptInner_ctl17_tdSum
					}
					///////////////////////
					if(!sameDay) {
					WebElement dayElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdDay")), "day "+String.valueOf(number));
					//WebElement dayElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdDay")), "day "+String.valueOf(number), "/");
					elements.add(dayElem);
					//if(dayElem!=null) {
						String currentDay=dayElem.getText();
						currentDay=currentDay.substring(currentDay.length()-5, currentDay.length());
						day=currentDay;
//					}
//					else {
//						i++;
//						number=String.valueOf(i);
//						if(i<10) {
//							number="0"+number;
//						}
//					}
					}
					WebElement enteryElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdEntry")), "Enter hour for day "+String.valueOf(number));
					//WebElement enteryElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdEntry")), "Enter hour for day "+String.valueOf(number),":");
					if(enteryElem.getText().equals(" ")) {
						enteryElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdMEn")),"Enter hour for day "+String.valueOf(number));
					}
					elements.add(enteryElem);
					String entry=enteryElem.getText();
					
					WebElement exitElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdExit")),"Exit hour for day "+String.valueOf(number));
					if(exitElem.getText().equals(" ")) {
						exitElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdME")),"Exit hour for day "+String.valueOf(number));
					}
					elements.add(exitElem);
					String exit=exitElem.getText();
					
					WebElement sumElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdSum")),"Hours for day "+String.valueOf(number));
					if(sumElem.getText().equals(" ")) {
						sumElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.id(begining+number+"_tdMS")),"Hours for day "+String.valueOf(number));
					}
					elements.add(sumElem);
					String sum=sumElem.getText();
					
					WebElement reportTypeElem=CommonOps.waitForElementToBeVisible(driver.findElement(By.xpath("//*[@id=\"ctl00_mp_rptInner_ctl"+number+"_tdRT\"]/div")),"Report day "+String.valueOf(number));
					elements.add(reportTypeElem);
					
					String reportTypeText=reportTypeElem.getText();
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)",reportTypeElem);
						
					ReportType r=new ReportType(day, entry, exit, sum,reportTypeText);
					System.out.println("Hilanet day is "+r);
					stepPass("Found day "+r.toString());
					if(sameDay)
					{
						sameDay=false;
							
					}
					daysToReport.add(r);
				}
				catch (Exception e) {
					stepFail(e.getMessage());
				}
			}	
			System.out.println();
			stepPass("Wrote the days");
		}
		catch (Exception e) {
			stepFail(e.getMessage());
			failOfTestCase(e.getMessage());
		}
		catch (AssertionError ex) {
			stepFail(ex.getMessage());
			failOfTestCase(ex.getMessage());
		}
//		finally {
//			writeToCsvFile(fileName, daysToReport);
//
//		}
		return daysToReport;
	}
	
	
}
