package testCases;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.*;
import org.xml.sax.SAXException;

import classes.ExcelFileHandle;
import classes.ReportType;

import utilities.*;

public class Sanity extends Base{
	
	@Test
	public void copyHours_thisReportForHours() throws IOException, ParserConfigurationException, SAXException, NumberFormatException, InterruptedException
	{	  
		goToUrl(getData("URLSource"));
		hilanetCompanyPage.fillForm(getData("compnySource"));
		
		hilanetLogin.fillForm(getData("ID"),getData("PasswordSource"));
		hilanetMainPage.goToHoursTable();
		
		List<ReportType> hours= hilanetTableHours.importHoursToFile();
//		List<ReportType> hours=new ArrayList<>();
////		hours.add(new ReportType("03/02", "08:00", "17:00", "09:00", ""));
////		hours.add(new ReportType("04/02", "08:01", "17:01", "09:00", ""));
//		hours.add(new ReportType("05/02", "08:05", "17:02", "09:00", ""));
////		hours.add(new ReportType("06/02", "08:00", "17:03", "09:00", ""));
////		hours.add(new ReportType("07/02", "08:07", "17:04", "09:00", ""));
//		
//		hours.add(new ReportType("10/02", "08:45", "17:05", "09:00", ""));
//		hours.add(new ReportType("11/02", "08:23", "17:06", "09:00", ""));
////		hours.add(new ReportType("12/02", "08:33", "17:07", "09:00", ""));
////		hours.add(new ReportType("13/02", "08:55", "17:08", "09:00", ""));
////		hours.add(new ReportType("14/02", "08:40", "17:09", "09:00", ""));
//		
//		hours.add(new ReportType("17/02", "08:30", "17:08", "09:00", ""));
//		hours.add(new ReportType("18/02", "08:20", "17:07", "09:00", ""));
////		hours.add(new ReportType("19/02", "08:10", "17:06", "09:00", ""));
//		hours.add(new ReportType("20/02", "08:06", "17:05", "09:00", ""));
//		hours.add(new ReportType("21/02", "08:01", "17:04", "09:00", ""));
//		
////		hours.add(new ReportType("24/02", "08:12", "17:03", "09:00", ""));
//		hours.add(new ReportType("25/02", "08:23", "17:02", "09:00", ""));
////		hours.add(new ReportType("26/02", "08:13", "17:01", "09:00", ""));
//		hours.add(new ReportType("27/02", "08:12", "17:00", "09:00", ""));
//		hours.add(new ReportType("28/02", "08:11", "17:01", "09:00", ""));
		
		goToUrl(getData("URLDestenion"));
		malamLoginPage.fillForm(getData("compnyDestinion"),
				getData("ID"),
				getData("PasswordDestenion"));		
		malamMainPage.goToHoursTable();
		
		monthlyReportPage.importHours(hours);		
//		monthlyReportPage.validateHoursFromSourceEqualsToDestintion(hours);
		sleep();
	}
	//@Test
	public void copyHoursFromExcelFile_thisReportForHoursFromExcelFile() throws Exception
	{
		String fileName="c:\\hours.xlsx";
		ExcelFileHandle.setExcelFile(fileName, "sheet1");		
		List<ReportType> hours=monthlyReportPage.importHoursFromFile(fileName);
		monthlyReportPage.importHours(hours);
		ExcelFileHandle.closeExcelFile();
		System.out.println("Finish");
//		sleep();
	}
}
