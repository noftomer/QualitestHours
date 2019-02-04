package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

public class ExcelFileHandle {
	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;
	private static FileInputStream ExcelFile;
	public static void setExcelFile(String Path,String SheetName) throws Exception {

		try {

			// Open the Excel file

			ExcelFile = new FileInputStream(new File(Path));

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e){

			throw (e);

		}

	}
	public static void closeExcelFile() throws Exception {

		try {

			ExcelWBook.close();
			ExcelFile.close();
		} catch (Exception e){

			throw (e);

		}

	}
	public static String getCellData(int RowNum, int ColNum) throws Exception{

		try{

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			
			String CellData = Cell.getStringCellValue();

			return CellData;

		}catch (Exception e){

			return"";

		}

	}
	public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception {
		try {
			Row  = ExcelWSheet.getRow(RowNum);

			Cell = Row.getCell(ColNum,null);// Row.RETURN_BLANK_AS_NULL);

			if (Cell == null) {

				Cell = Row.createCell(ColNum);

				Cell.setCellValue(Result);

			} else {

				Cell.setCellValue(Result);

			}

			FileOutputStream fileOut = new FileOutputStream("C:\\hours.xlsx");

			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();
		}
		catch (Exception e) {
			throw e;
		}
	}
}
