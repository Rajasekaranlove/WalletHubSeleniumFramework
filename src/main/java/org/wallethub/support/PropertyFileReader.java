package org.wallethub.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.log4testng.Logger;


/**
 * PropertyFileReader is to set the environment variable declaration
 * mapping for config properties in the UI test
 */
public class PropertyFileReader {

	private static final Logger log = Logger.getLogger(PropertyFileReader.class);
	private static PropertyFileReader envPropertiesObj;
	private static PropertyFileReader testDataPropertiesObj;
	
	private static File config_file;
	private static File file;
	
	private Properties properties;
	private Properties testDataProperties;


	private PropertyFileReader() {
		properties = loadConfigProperties();
	}

	private PropertyFileReader(String propertyFile) {
		testDataProperties = loadProperties(propertyFile);
	}

	private Properties loadConfigProperties() {
		config_file = new File("./src/test/resources/config.properties");
		FileInputStream fileInput = null;
		Properties props = new Properties();

		try {
			fileInput = new FileInputStream(config_file);
			props.load(fileInput);
			fileInput.close();
		} catch (FileNotFoundException e) {
			log.error("config.properties is missing or corrupt : " + e.getMessage());
		} catch (IOException e) {
			log.error("read failed due to: " + e.getMessage());
		}

		return props;
	}

	private Properties loadProperties(String propertyFile) {
		file = new File("./src/test/resources/"+propertyFile+".properties");
		FileInputStream fileInput = null;
		Properties props = new Properties();

		try {
			fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		} catch (FileNotFoundException e) {
			log.error(""+propertyFile+".properties is missing or corrupt : " + e.getMessage());
		} catch (IOException e) {
			log.error("Read failed due to: " + e.getMessage());
		}

		return props;
	}

	public static PropertyFileReader getInstance() {
		if (envPropertiesObj == null) {
			envPropertiesObj = new PropertyFileReader();
		}
		return envPropertiesObj;
	}

	public static PropertyFileReader getInstance(String propertyFile) {
		if (testDataPropertiesObj == null) {
			testDataPropertiesObj = new PropertyFileReader(propertyFile);
		}
		return testDataPropertiesObj;
	}

	public String getConfigProperty(String key) {
		return properties.getProperty(key);
	}
	

	public String getProperty(String key) {
		return testDataProperties.getProperty(key);
	}
	
	public void saveProperties(Properties p) throws IOException
	{
		FileOutputStream fr = new FileOutputStream(file);
		p.store(fr, "Properties");
		fr.close();
		System.out.println("After saving properties: " + p);
	}

	public Properties setProperty(String file, String key, String value) {
		// TODO Auto-generated method stub
		//Properties p = new Properties();
		try {
			testDataProperties.setProperty(key, value);
			testDataPropertiesObj.saveProperties(testDataProperties);
			testDataProperties = loadProperties(file);
			//testDataProperties = p;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testDataProperties;
	}
}
