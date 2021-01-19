package se.boalbert.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Connection {

	/**
	 * Fetches connection-string in src/resources/login.txt
	 * @return string with connection details
	 */
	public static String connectionString() {
		String filename = "login.txt";
		File file = new File(".\\src\\resources\\" + filename);

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String connectionDetails;

			while((connectionDetails = br.readLine()) != null)  {

				return connectionDetails;

			}
		} catch (IOException e) {
			System.out.println("Error fetching connection-string.");
			e.printStackTrace();
		}
		return null;
	}
}
