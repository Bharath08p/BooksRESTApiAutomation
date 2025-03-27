package helpers;

public class GetGlobalVariables {


		public static String getBaseURL()
		{
			return PropertyFileReader.getPropertyFileData("BASE_URL");
		}
		
		public static String getUser()
		{
			return PropertyFileReader.getPropertyFileData("user");
		}
		
		
		public static String getPassword()
		{
			return PropertyFileReader.getPropertyFileData("password");
		}
		
		public static String getTestDataFilePath()
		{
			return PropertyFileReader.getPropertyFileData("testdatafile");
		}
		
		public static String getSheetName()
		{
			return PropertyFileReader.getPropertyFileData("sheetName");
		}

}
