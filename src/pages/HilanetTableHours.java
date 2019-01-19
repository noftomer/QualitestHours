package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.util.SynchronizedSymbolTable;
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
	private List<ReportType> iterateOverDays() throws IOException, ParserConfigurationException, SAXException
	{
		List<ReportType> daysToReport=new ArrayList<ReportType>();
		try {
			String value=CommonOps.getDropDownValue(comboOptions);		
			if(!value.equals("חודש נוכחי"))
			{
				CommonOps.selectDropDownByVisibleText(comboOptions, "חודש נוכחי");
			}
			//String selector="table[id=tReps] tbody tr[id^=ctl00_mp_rptInner_ct]";
			String begining="ctl00_mp_rptInner_ctl";
			//int rows=driver.findElements(By.cssSelector(selector)).size();
			int rows=rowsOfTable.size();
			String number="";
			for(int i=0;i<rows;i++)
			{
				try {
					number=String.valueOf(i);
					if(i<10) {
						number="0"+number;
					}
					WebElement dayElem=driver.findElement(By.id(begining+number+"_tdDay"));
					((JavascriptExecutor)driver).executeScript("arguments[0].style.border='1px solid red'",dayElem);
					String day=dayElem.getText();
					day=day.substring(day.length()-5, day.length());
					
					WebElement enteryElem=driver.findElement(By.id(begining+number+"_tdEntry"));
					((JavascriptExecutor)driver).executeScript("arguments[0].style.border='1px solid red'",enteryElem);
					String entry=enteryElem.getText();
					
					WebElement exitElem=driver.findElement(By.id(begining+number+"_tdExit"));
					((JavascriptExecutor)driver).executeScript("arguments[0].style.border='1px solid red'",exitElem);
					String exit=exitElem.getText();
					
					WebElement sumElem=driver.findElement(By.id(begining+number+"_tdSum"));
					((JavascriptExecutor)driver).executeScript("arguments[0].style.border='1px solid red'",sumElem);
					String sum=sumElem.getText();
					stepPass("Found day "+(i+1));
					
					daysToReport.add(new ReportType(day, entry, exit, sum));
					//System.out.println("index"+i+"=> day="+day+" entry="+entry+" exit="+exit+" sum="+sum);
				}
				catch (Exception e) {
					stepFail(e.getMessage());
				}
				
			}	
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
