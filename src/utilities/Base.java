package utilities;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.*;

import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.remote.*;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.*;

import classes.ReportType;
import pages.*;


public class Base {
	protected static WebDriver driver;
	//protected WebDriver localDriver;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());	
	protected static HilanetCompanyPage hilanetCompanyPage;
	protected static HilanetLogin hilanetLogin;
	protected static HilanetMainPage hilanetMainPage;
	protected static HilanetTableHours hilanetTableHours;

	protected static MalamLoginPage malamLoginPage;
	protected static MalamMainPage malamMainPage;
	protected static MonthlyReportPage monthlyReportPage; 
//	protected static CompanyDetailsPage companyDetailsPage;
//	
//	protected static ContactsPage contactsPage;
//	protected static ContactInformationPage contactInformationPage;
//	protected static ContactDetailsPage contactDetailsPage;
//	
//	protected static NewDealPage newDealPage;
	/*protected base(WebDriver localDriver) {
		this.localDriver=localDriver;
	}*/
	@BeforeClass
	public static void startSession() throws ParserConfigurationException, SAXException, IOException
	{		
		initBrowser(getData("BrowserType"));
		InstanceReport();
		ManagePage.init();
	}
	
	@AfterClass
	public static void closeSession()
	{
		finalizeExtentReport();
		if(driver!=null)
		{
			driver.quit();
		}
	}
	@Rule
	public TestName testName=new TestName();
	@Before
	public void doBeforeTest() throws IOException, ParserConfigurationException, SAXException
	{
		String text = testName.getMethodName().split("_")[1];
		String cleanText = text.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
		initReportTest(testName.getMethodName().split("_")[0],cleanText);
		//loginPage.loginUser(getData("LoginUserName"), getData("LoginPassword"));
		
		//driver.navigate().refresh();//for firefox browser
	}
	@After
	public void doAfterTest() throws IOException, ParserConfigurationException, SAXException
	{
		//mainPageCrm.clickLogout();
		finalizeReportTest();
	}
	
	public static String getData (String nodeName) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile =new File("C://HoursConfig.xml");
		if(!fXmlFile.exists()) {
			throw new FileNotFoundException("file C://HoursConfig.xml doesnt exists");
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);		
		doc.getDocumentElement().normalize();
		return doc.getElementsByTagName(nodeName).item(0).getTextContent();
	}
	
	public static void initBrowser(String browserType) throws ParserConfigurationException, SAXException, IOException
	{
		switch (browserType.toLowerCase())
		{
		case "firefox":
			driver = initFFDriver();
			break;
		case "ie":
			driver = initIEDriver();
			break;
		case "chrome":
			driver = initChromeDriver();
			break;
		default:
			stepFail("No valid browser");
			failOfTestCase("No valid browser");
		}
		driver.manage().window().maximize();
	}
	public static void goToUrl(String url)
	{
		try {
			driver.get(url);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	public static WebDriver initFFDriver() throws ParserConfigurationException, SAXException, IOException
	{
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
		WebDriver driverff = new FirefoxDriver();
		return driverff;
		

	}
	
	public static WebDriver initIEDriver() throws ParserConfigurationException, SAXException, IOException
	{
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\MicrosoftWebDriver.exe");
		WebDriver driverie = new InternetExplorerDriver();
		return driverie;
	}
	
	public static WebDriver initChromeDriver() throws ParserConfigurationException, SAXException, IOException
	{
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		return driver;
	}
	
	public static void InstanceReport() throws ParserConfigurationException, SAXException, IOException
	{
		try
		{
			extent = new ExtentReports(getData("ReportFilePath")+timeStamp+"//" +getData("BrowserType").toLowerCase()+getData("ReportFileName") + timeStamp + ".html", true);		
		}
		catch(Exception e)
		{
			fail("Exception InstanceReport()="+e.getMessage());
		}
		catch (AssertionError e) {
			fail("AssertinoError InstanceReport()="+e.getMessage());
		}
	}
	
	public static void initReportTest(String testName, String testDescription)
	{
		try
		{
			test = extent.startTest(testName, testDescription);
		}
		catch(Exception e)
		{
			fail("Exception initReportTest()="+e.getMessage());
		}
		catch (AssertionError e) {
			fail("AssertinoError initReportTest()="+e.getMessage());
		}
	}
	
	public static void finalizeReportTest()
	{
		try
		{
			extent.endTest(test);
		}
		catch(Exception e)
		{
			fail("Exception finalizeReportTest()="+e.getMessage());
		}
		catch (AssertionError e) {
			fail("AssertinoError finalizeReportTest()="+e.getMessage());
		}
	}
	public static void stepPass(String description)
	{
		try
		{
			test.log(LogStatus.PASS, description);
			//test.log(LogStatus.PASS, description+", see screenshut: " + test.addScreenCapture(takeSS()));
			
		
		}
		catch(Exception e)
		{
			fail("Exception stepPass("+description+")="+e.getMessage());
		}
		catch (AssertionError e) {
			fail("AssertinoError stepPass("+description+")="+e.getMessage());
		}
	}
	public static void stepPassWithScreenShut(String description)
	{
		try
		{
			test.log(LogStatus.PASS, description+", see screenshut: " + test.addScreenCapture(takeSS()));
			
		
		}
		catch(Exception e)
		{
			fail("Exception stepPass("+description+")="+e.getMessage());
		}
		catch (AssertionError e) {
			fail("AssertinoError stepPass("+description+")="+e.getMessage());
		}
	}
	public static void stepFail(String description) throws IOException, ParserConfigurationException, SAXException
	{
		try
		{
			test.log(LogStatus.FAIL, description+", see screenshut: " + test.addScreenCapture(takeSS()));
			
		}
		catch(Exception e)
		{
			fail("Exception stepFail("+description+")="+e.getMessage());
		}
		catch (AssertionError e) {
			fail("AssertinoError stepFail("+description+")="+e.getMessage());
		}
	}
	public static void finalizeExtentReport()
	{
		try{
			if(extent!=null){
				extent.flush();
				extent.close();
			}
		}
		catch(Exception e)
		{
			fail("Exception finalizeExtentReport()="+e.getMessage());
		}
		catch (AssertionError e) {
			fail("AssertinoError finalizeExtentReport()="+e.getMessage());
		}
		
	}
	public static void failOfTestCase(String description)
	{
		fail(description);
	}
	public static String takeSS() throws IOException, ParserConfigurationException, SAXException
	{
		String SSpath = getData("ReportFilePath")+timeStamp+"//" + "screenshot_" + getRandomNumber() + ".png";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(SSpath));
		return SSpath;
	}
	
	public static int getRandomNumber()
	{
		Random rand = new Random();
		//int  n = rand.nextInt(999999) + 1000;
		return rand.nextInt(999999) + 1000;
	}
	public void sleep() throws InterruptedException, NumberFormatException, ParserConfigurationException, SAXException, IOException
	{
		Thread.sleep(Long.parseLong(getData("milis")));
	}
	String fileHeader="day,entry,end,sum";
		static String commaDelimiter = ",";
		String newLineSeparator = "\n";
	public void writeToCsvFile(String fileName,List<ReportType> collection) throws IOException {
		FileWriter fileWriter = null;
		File hoursFile=new File(fileName);
		if(!hoursFile.exists()) {
			hoursFile.createNewFile();
		}
		try
		{
			fileWriter = new FileWriter(fileName);
			 fileWriter.append(fileHeader);
			 fileWriter.append(newLineSeparator);
			 for(ReportType report:collection)
			 {
				 fileWriter.append(report.getDay());
				 fileWriter.append(commaDelimiter);
				 fileWriter.append(report.getEntryTime());
				 fileWriter.append(commaDelimiter);
				 fileWriter.append(report.getExitTime());
				 fileWriter.append(commaDelimiter);
				 fileWriter.append(report.getTimePerDay());
				 fileWriter.append(commaDelimiter);
				 fileWriter.append(newLineSeparator);
			 }
		}
		catch (Exception e) {
			failOfTestCase("Didnt wrote to csv file because: "+e.getMessage());
		}
		finally
		{
			fileWriter.flush();
			fileWriter.close();
		}
	}
	public void removeHoursToFile(String fileName) {
		try{
    		
    		File file = new File(fileName);
        	
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
    	   
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
	}
	public static List<ReportType> readCsvFile(String fileName) {
		BufferedReader fileReader = null;
		List<ReportType> days = new ArrayList<ReportType>();
		try {
			String line = "";
			fileReader = new BufferedReader(new FileReader(fileName));
			fileReader.readLine();
			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(commaDelimiter);
				 if (tokens.length > 0) {
					 ReportType row = new ReportType(tokens[0],tokens[1],tokens[2],tokens[3],tokens[4]);
							 //(Long.parseLong(tokens[STUDENT_ID_IDX]), tokens[STUDENT_FNAME_IDX], tokens[STUDENT_LNAME_IDX], tokens[STUDENT_GENDER], Integer.parseInt(tokens[STUDENT_AGE]));
					         //            students.add(student);
					 days.add(row);
					 
				 }
			}
			return days;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error in CsvFileReader !!!"+e.getMessage());

		} 
		 finally {
			 try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error while closing fileReader !!!"+e.getMessage());

			}
		 }
		return null;
	}

	public static void paintSuccess(WebDriver driver, WebElement row) throws IOException, ParserConfigurationException, SAXException {
		
	    	try {
				((JavascriptExecutor)driver).executeScript("arguments[0].style.border='2px solid green';", row);
				stepPass("Painted element "+row.getText()+" with green");
			}
			
			catch (Exception e) {
				stepFail(e.getMessage());
			}
	    	
	}
	public static void paintFail(WebDriver driver, WebElement row) throws IOException, ParserConfigurationException, SAXException {
		try {
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border='2px solid red';", row);
			stepPass("Painted element "+row.getText()+" with green");
		}
		catch (Exception e) {
			stepFail(e.getMessage());
		}
	}
}
