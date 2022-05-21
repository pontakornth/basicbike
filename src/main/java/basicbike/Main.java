package basicbike;

import basicbike.dao.*;
import basicbike.gui.MainGui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {
	public static final String PROPERTIES_FILE = "config.properties";
	private static Properties properties = null;

	/**
	 * Load properties from configuration file.
	 *
	 * @return properties object with data from PROPERTIES_FILE
	 */
	public static Properties getProperties() {
		// Only load the properties one time
		if (properties == null) try {
			properties = new Properties();
			InputStream input = Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
			properties.load(input);
			input.close();
			// Testing: verify the properties were read.		
			//for(Object key: properties.keySet()) {
			//	System.out.printf("%s = %s\n", key, properties.get(key));
			//}
		} catch (IOException ex) {
			System.err.printf("Exception reading properties from %s\nException: %s\n",
					PROPERTIES_FILE, ex.getMessage());
		}
		return properties;
	}
	
	/**
	 * Print all bikes, as a test of the DAO.
	 * @param daoFactory the DaoFactory for getting a BikeDao.
	 */
	public static void printInventory(DaoFactory daoFactory) {
		BikeDao bikeDao = daoFactory.getBikeDao();
		// print all the bikes
		String format = "%6d  %-20.20s  %-20.20s\n";
		bikeDao.forEach(bike -> 
			System.out.printf(format, bike.getId(), bike.getModel(), bike.getType()));
	}


	public static void main(String[] args) {
		DaoFactory daoFactory = null;
		try {
			daoFactory = new DaoFactory();
//			printInventory(daoFactory);
			MainGui mainGui = new MainGui(daoFactory);
			mainGui.start();
		} 
		catch (Exception e) {
			// If something bad happens, the program must close the connection.
			e.printStackTrace();
		}
	}
}
