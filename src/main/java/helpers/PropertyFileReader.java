package helpers;


import net.serenitybdd.model.di.ModelInfrastructure;
import net.thucydides.model.util.EnvironmentVariables;

public class PropertyFileReader {

	public static  String getPropertyFileData(String key){ 
		EnvironmentVariables environmentVariables = ModelInfrastructure.getEnvironmentVariables();
    	String value = "";
        try{  
        	value = environmentVariables.getProperty(key);		                 
          	}
	   catch(Exception e){  
          throw new RuntimeException("Failed to read from Serenity.properties file.");  
        }
        return value;
     } 

}
