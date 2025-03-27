package helpers;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
 
public class dataDrivenFromExcel
{
    public FileInputStream fis = null;
    public XSSFWorkbook workbook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    private CellStyle generalStyle;
 
    public dataDrivenFromExcel(String xlFilePath)
    {
       
        try {
        	fis = new FileInputStream(xlFilePath);
			workbook = new XSSFWorkbook(fis);
		    fis.close();
		}  catch (Exception e) {
			throw new RuntimeException("Issue in function dataDrivenFromExcel: "+e.getMessage());
		 }
    
    }
    
    public int getRowIndexUsingTestCaseName(String sheetName, String TestCaseName)
    {
    	int rowNum = -1;
		try{
	    	
	        sheet = workbook.getSheet(sheetName);
	        Iterator<Row>  rows= sheet.iterator();
			Row firstrow= rows.next();
			Iterator<Cell> ce=firstrow.cellIterator();
			int k=0;
			int coloumn = 0;
			int l=1;
			while(ce.hasNext())
			{
				Cell value=ce.next();
				
				if(value.getStringCellValue().equalsIgnoreCase("TestCaseName"))
				{
					coloumn=k;
					
				}
				
				k++;
			}

		
			while(rows.hasNext())
			{
				
				Row r=rows.next();
				if(r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(TestCaseName))
				{
					
					rowNum=l;
				}
				l++;
			}
		}catch (Exception e) {
			throw new RuntimeException("Issue in function getRowIndexUsingTestCaseName: "+e.getMessage());
		 }
        return rowNum;
    	
    }
 
    public int getColIndexUsingFieldName(String sheetName, String fieldName)
    {
     	int colNum = -1;
     	try{
    	sheet = workbook.getSheet(sheetName);
   
    	XSSFRow row = sheet.getRow(0);        
        for(int i = 0; i < row.getLastCellNum(); i++)
        {
            if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(fieldName))
            	colNum = i;
        }
     	}catch(Exception e){
     		throw new RuntimeException("Issue in function getColIndexUsingFieldName: "+e.getMessage());
     	}
        return colNum;
    	
    }
    
    
	public String getCellData(String sheetName, String TestCaseName, String fieldName)
    {
        try
        {
        	 int rowIndex = getRowIndexUsingTestCaseName(sheetName, TestCaseName);
        	 int colIndex = getColIndexUsingFieldName(sheetName, fieldName);
        	 row = sheet.getRow(rowIndex);
             cell = row.getCell(colIndex);
             String CellValue = "";
             switch (cell.getCellType()) 
             {
             case STRING:
            	 CellValue = cell.getStringCellValue();
					break;
				case _NONE:
					break;
				case BLANK:
					break;
				case BOOLEAN:
					CellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case NUMERIC:
					if(DateUtil.isCellDateFormatted(cell))
					{
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	                    Date date = cell.getDateCellValue();
	                    CellValue = df.format(date);
	                    break;
					}
					else
					{
						CellValue = String.valueOf(cell.getNumericCellValue());
						String[] number = CellValue.split( "[.]" );
						CellValue = number[0];
					break;
					}
				default:
					break;
             }
           
        return CellValue;
        }  
        catch(Exception e)
        {
            throw new RuntimeException("row "+TestCaseName+" or column "+fieldName +" does not exist  in Excel: "+e.getMessage());
        }
    }
    
     
    public void setCellValue(String sheetName, String TestCaseName, String fieldName, String value)
    {
    	sheet = workbook.getSheet(sheetName);
    	int rowIndex = getRowIndexUsingTestCaseName(sheetName, TestCaseName);
    	int colIndex = getColIndexUsingFieldName(sheetName, fieldName);   
    	XSSFRow row = sheet.getRow(rowIndex);
    	XSSFCell cell = row.getCell(colIndex);
    	if(cell==null)
    	{
    		row.createCell(colIndex);
    		cell=row.getCell(colIndex);
    		cell.setCellType(CellType.STRING);
     	}
    	cell.setCellValue(value);
    	cell.setCellStyle(generalStyle);
    	try (FileOutputStream fos = new FileOutputStream(GetGlobalVariables.getTestDataFilePath())) {
            workbook.write(fos);
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Test Data file being used by some other process: "+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}   
}