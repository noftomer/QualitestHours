package testCases;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.*;
import org.xml.sax.SAXException;

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
		String fileName="c:\\hours\\hours"+getRandomNumber()+".csv";
		//hilanetTableHours.importHoursToFile(fileName);
		List<ReportType> hours= hilanetTableHours.importHoursToFile();
		goToUrl(getData("URLDestenion"));
		
		malamLoginPage.fillForm(getData("compnyDestinion"),
				getData("ID"),
				getData("PasswordDestenion"));
		malamMainPage.goToHoursTable();
		monthlyReportPage.importHours(hours);
		sleep();
	}
}
