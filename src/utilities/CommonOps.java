package utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import net.sourceforge.htmlunit.cyberneko.filters.ElementRemover;

public class CommonOps extends Base{
	public static void verifyElementExists(WebElement elem) throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			elem.isDisplayed();
			drawElemet(elem);
			stepPass("Element "+elem+" exists !");			
		}
		catch (Exception e)
		{
			stepFail("Element "+elem+" not exists !");
			//fail(e.getMessage());
		}
	}
	public static void excuteOpertionOnElement(WebDriver driver,WebElement element,String elementName,ElementOpertions eo) throws IOException, ParserConfigurationException, SAXException {
		try {
			waitForElementToBeVisible(element, elementName);
			switch (eo) {
			case CLICK:
				element.click();
				break;
			case CLEAR:
				element.clear();
				break;
			case MOVETOELEMENT:
				Actions action=new Actions(driver);
				action.moveToElement(element).build().perform();
				break;
			default:
				break;
			}
			stepPass(eo.name()+ " for element "+elementName);
		}
		catch (Exception e) {
			// TODO: handle exception
			stepFail(e.getMessage());
			//fail(e.getMessage());
		}
		catch (AssertionError ae) {
			stepFail(ae.getMessage());
			//fail(ae.getMessage());
		}
	}
	public static void typeTextInTexbox(WebDriver driver,WebElement element,String elementName,String textToElement) throws IOException, ParserConfigurationException, SAXException {
		try {
			waitForElementToBeVisible(element, elementName);
			element.clear();
			element.sendKeys(textToElement);	
			if(element.getAttribute("type").toLowerCase().equals("password")) {textToElement=" ";}
			stepPass("Typed "+textToElement+"into textbox "+elementName);
		}
		catch (Exception e) {
			stepFail(e.getMessage());
			//fail(e.getMessage());
		}
		catch (AssertionError ae) {
			stepFail(ae.getMessage());
			//fail(ae.getMessage());
		}
	}
	
	public static void verifyValueExists(WebElement elem, String expectedValue) throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			String actual = elem.getText().trim();
			drawElemet(elem);
			assertEquals(expectedValue, actual);
			stepPass(expectedValue+" exists");
		}
		catch (Exception e)
		{
			stepFail("Problem with "+elem);
			//fail(e.getMessage());
		}
		catch (AssertionError ea)
		{
			stepFail("assert failed of "+expectedValue);
			//fail(ea.getMessage());
		}
	}
	
	
	public static void selectDropDownByValue(WebElement elem, String valueLanguage) throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			Select myValue = new Select(elem);
			myValue.selectByValue(valueLanguage);
			stepPass("combobox with value "+valueLanguage+" selected");
		}
		catch (Exception e)
		{
			stepFail(elem+" didnt selected");
			//fail(e.getMessage());
		}
	}

	public static void selectDropDownByVisibleText(WebElement elem, String valueLanguage) throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			Select myValue = new Select(elem);
			myValue.selectByVisibleText(valueLanguage);
			stepPass("combobox with visible text "+valueLanguage+" selected");
		}
		catch (Exception e)
		{
			stepFail(e.getMessage());
			//fail(e.getMessage());
		}
	}
	public static String getDropDownValue(WebElement elem) throws IOException, ParserConfigurationException, SAXException
	{
		String selectedValue="";
		try
		{
			Select myValue = new Select(elem);
			selectedValue=myValue.getFirstSelectedOption().getText();
			//myValue.selectByVisibleText(valueLanguage);
			stepPass("The option "+selectedValue+" is selected");
		}
		catch (Exception e)
		{
			stepFail("No "+elem+" found");
			//fail(e.getMessage());
		}
		return selectedValue;
	}
	
	public static void clickOnAlert() throws IOException, ParserConfigurationException, SAXException {
		try
		{
			Alert alertWin=driver.switchTo().alert();
			alertWin.accept();
			stepPass("Clicked on alert");
		}
		catch(Exception e)
		{
			stepFail("Didnt found alert");
			//failOfTestCase(e.getMessage());
		}
	}
	public static String monthNameIn3Letters(int month)
	{
		DateFormat formatter = new SimpleDateFormat("MMM", Locale.US);
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    calendar.set(Calendar.MONTH, month-1);
	    return formatter.format(calendar.getTime());
	    
	}
	public static String getMeetingDatetHour(String dateNewMeeting,String hour) throws ParseException
	{
		//<StartHour>02:00 PM</StartHour>
		String res="";
		String newDateNewMeeting[]=dateNewMeeting.split("/");
		res+=monthNameIn3Letters(Integer.parseInt(newDateNewMeeting[1]));//res="Nov"
		res+=" "+newDateNewMeeting[0]+", "+newDateNewMeeting[2]+", ";//res="Nov 11, 2018, "
		String hourSplit[]=hour.split(" ");//"02:00 PM"
		String hourNumber=hourSplit[0];//"02:00"
		String hourSpiltMinute[]=hourNumber.split(":");//"02","00"
		String hourAfterSplit=hourSpiltMinute[0];//"02"
		String minuteAfterSplit=hourSpiltMinute[1];//"00"
		int hourInt=Integer.parseInt(hourAfterSplit);//2
		String convertedHour=String.valueOf(hourInt)+":"+minuteAfterSplit+":00";//"2:00:00"
		res+=convertedHour+" "+hourSplit[1];//res="Nov 11, 2018, 2:00:00 PM"
		return res;
	}
	public static String getTodayString()
	{
		//Monday Jan 22, 2018
		DateFormat dateFormat = new SimpleDateFormat("EEEE MMM dd, yyyy");
		Date date = new Date();
		String day=dateFormat.format(date);
		return day;
	}
	private static void drawElemet(WebElement element)
	{
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='2px solid blue'",element);
	}
	public static WebElement waitForElementToBeVisible(WebElement element,String elementName,String pattern) throws IOException, ParserConfigurationException, SAXException, InterruptedException
	{
		WebElement visibleElement=null;
		boolean found=false;
			try {
				WebDriverWait wait=new WebDriverWait(driver,10);
				found=wait.until(ExpectedConditions.textToBePresentInElement(element, pattern));
				if(found) {
					visibleElement=element;
				}
				paintSuccess(driver, element);
				stepPass("element "+elementName+" is visible with text "+pattern);
			}

			catch (Exception e) {
				stepFail("element "+elementName+" is not visible with text "+pattern);
			}

		return visibleElement;
	}
	
	public static WebElement waitForElementToBeVisible(WebElement element,String elementName) throws IOException, ParserConfigurationException, SAXException, InterruptedException
	{
		WebElement visibleElement=null;
//		long startTime =System.currentTimeMillis();
//		long currentTime =startTime;
		//while(currentTime<startTime+5000){
//		boolean found=false;
//		while(!found) {
			try {
				WebDriverWait wait=new WebDriverWait(driver,10);
				visibleElement=wait.until(ExpectedConditions.visibilityOf(element));
				//visibleElement=wait.until(ExpectedConditions.elementToBeClickable(element));				
				paintSuccess(driver, element);
				stepPass("element "+elementName+" is visible");
//				found=true;
			}
//			catch (StaleElementReferenceException e) {
//				Thread.sleep(2500);
//				//stepPass("Search for element "+elementName+" again");
//				continue;
//			}
			catch (Exception e) {
				stepFail("element "+elementName+" is not visible");
			}
//			currentTime =System.currentTimeMillis();
//		}
		return visibleElement;
	}
	public static WebElement waitForElementToBeVisible_original(WebElement element,String elementName) throws IOException, ParserConfigurationException, SAXException
	{
		WebElement visibleElement=null;
		try
		{
			WebDriverWait wait=new WebDriverWait(driver, 30);
			visibleElement=wait.until(ExpectedConditions.visibilityOf(element));
			paintSuccess(driver, element);
			
			stepPass("element "+elementName+" is visible");
		}		
		catch (Exception e) {
			stepFail("element "+elementName+" is not visible");
			//failOfTestCase(e.getMessage());
		}
		return visibleElement;
	}
	public static WebElement waitForElementToBeClickable(WebElement element,String elementName) throws IOException, ParserConfigurationException, SAXException
	{
		WebElement clicableElement=null;
		try
		{
			WebDriverWait wait=new WebDriverWait(driver, 5);
			clicableElement=wait.until(ExpectedConditions.elementToBeClickable(element));
			stepPass("element "+elementName+" is clickable");
		}
		catch (Exception e) {
			stepFail("element "+elementName+" is not clickable");
			//failOfTestCase(e.getMessage());
		}
		return clicableElement;
	}
	public static void moveToElement(WebElement element,String elementName) throws IOException, ParserConfigurationException, SAXException
	{
		try {
			Actions act = new Actions(driver);
			act.moveToElement(element).build().perform();
			stepPass("Moved to element "+elementName);
		}
		catch (Exception e) {
			stepFail("didnt Moved to element "+elementName);
			//failOfTestCase(e.getMessage());
			// TODO: handle exception
		}
	}
}
