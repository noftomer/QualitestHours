package testCases;
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
//		goToUrl(getData("URLSource"));
//		hilanetCompanyPage.fillForm(getData("compnySource"));
//		hilanetLogin.fillForm(getData("ID"),getData("PasswordSource"));
//		hilanetMainPage.goToHoursTable();
//		//String fileName="c:\\hours\\hours"+getRandomNumber()+".csv";
//		//hilanetTableHours.importHoursToFile(fileName);
//		List<ReportType> hours= hilanetTableHours.importHoursToFile();
		goToUrl(getData("URLDestenion"));
		List<ReportType> hours=new ArrayList<>();
		//hours.add(new ReportType("03/02", "08:01", "17:51", "09:00"));
		//hours.add(new ReportType("04/02", "08:02", "17:34", "09:00"));
		//hours.add(new ReportType("05/02", "08:03", "17:23", "09:00"));
		hours.add(new ReportType("06/02", "08:04", "17:09", "09:00"));
		hours.add(new ReportType("07/02", "08:05", "17:01", "09:00"));
		//hours.add(new ReportType("10/02", "08:06", "17:12", "09:00"));
		//hours.add(new ReportType("11/02", "08:07", "17:19", "09:00"));
		//hours.add(new ReportType("12/02", "08:08", "17:18", "09:00"));
		//hours.add(new ReportType("13/02", "08:09", "17:12", "09:00"));
		//hours.add(new ReportType("14/02", "08:10", "17:24", "09:00"));
		hours.add(new ReportType("17/02", "08:11", "17:50", "09:00"));
		hours.add(new ReportType("18/02", "08:12", "17:30", "09:00"));
		//hours.add(new ReportType("19/02", "08:13", "17:20", "09:00"));
		hours.add(new ReportType("20/02", "08:14", "17:11", "09:00"));
		hours.add(new ReportType("21/02", "08:15", "17:12", "09:00"));
		//hours.add(new ReportType("24/02", "08:16", "17:13", "09:00"));
		//hours.add(new ReportType("25/02", "08:17", "17:31", "09:00"));
		//hours.add(new ReportType("26/02", "08:18", "17:14", "09:00"));
		hours.add(new ReportType("27/02", "08:19", "17:44", "09:00"));
		hours.add(new ReportType("28/02", "08:21", "17:55", "09:00"));
		malamLoginPage.fillForm(getData("compnyDestinion"),
				getData("ID"),
				getData("PasswordDestenion"));
		malamMainPage.goToHoursTable();
		monthlyReportPage.importHours(hours);
		sleep();
	}
	//@Test
	public void copyHoursFromExcelFile_thisReportForHoursFromExcelFile() throws Exception
	{
		String fileName="c:\\hours.xlsx";
		ExcelFileHandle.setExcelFile(fileName, "sheet1");
//		goToUrl(getData("URLDestenion"));
//		
//		malamLoginPage.fillForm(getData("compnyDestinion"),
//				getData("ID"),
//				getData("PasswordDestenion"));
//		malamMainPage.goToHoursTable();
		
		List<ReportType> hours=monthlyReportPage.importHoursFromFile(fileName);
		monthlyReportPage.importHours(hours);
		ExcelFileHandle.closeExcelFile();
		System.out.println("Finish");
//		sleep();
	}
}
