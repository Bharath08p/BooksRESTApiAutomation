package helpers;

public class TestData {

	static String TestDataFilePath = GetGlobalVariables.getTestDataFilePath();
	static dataDrivenFromExcel ExcelObj = new dataDrivenFromExcel(TestDataFilePath);
	static String sheetName = GetGlobalVariables.getSheetName();


	public static String getName(String TestCaseName) 
	{
		return ExcelObj.getCellData(sheetName, TestCaseName, "name");
	}

	public static String getAuthor(String TestCaseName) 
	{
		return ExcelObj.getCellData(sheetName, TestCaseName, "author");
	}
	
	public static String getPublication(String TestCaseName) 
	{
		return ExcelObj.getCellData(sheetName, TestCaseName, "publication");
	}
	
	public static String getCategory(String TestCaseName) 
	{
		return ExcelObj.getCellData(sheetName, TestCaseName, "category");
	}
	
	public static String getPages(String TestCaseName) 
	{
		return ExcelObj.getCellData(sheetName, TestCaseName, "pages");
		
	}
	
	public static String getPrice(String TestCaseName) 
	{
		return ExcelObj.getCellData(sheetName, TestCaseName, "price");
		
	}

}

