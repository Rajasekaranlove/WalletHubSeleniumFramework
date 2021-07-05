package org.wallethub.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadFromExcel {
	
	/**
	 * 
	 * @param workBookName
	 * @param workSheet
	 * @return
	 */
	public static Object[][] readExcelTestData(String workBookName, String workSheet) {
		String filePath = "";
		FileInputStream fs = null;
		Workbook wb = null;
		Sheet sheet = null;
		
		try {
			filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\externalTestData\\" + workBookName;
			filePath = filePath.replace("\\", File.separator);

			fs = new FileInputStream(filePath);
			
			System.out.println(filePath);
			wb = WorkbookFactory.create(fs);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		sheet = wb.getSheet(workSheet);
		
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		for(int i = 0 ; i< sheet.getLastRowNum(); i++)
		{
			for (int j=0; j< sheet.getRow(0).getLastCellNum(); j++)
			{
				if(sheet.getRow(i+1).getCell(j) != null)
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				else
					break;
			}
		}

		System.out.println(data);
		
		return data;
	}

	public static class ReadExcelSelfTest{
		public static void main(final String[] args)
		{
			ReadFromExcel read = new ReadFromExcel();
			if (read!=null)
			{
				read.readExcelTestData("Test data", "QA_ReachApplicationTestData.xls");
			}
		}
	}

}
