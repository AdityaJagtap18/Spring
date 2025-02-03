package com.springcrud.CurdOparation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

	public String getProperty(String keyString) {
		try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			String property = prop.getProperty(keyString);
			System.out.println("String from properties file =>  "+keyString+" : " + property);
			return property;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

	}

}
