/**
 * @author Pimentel
 * @date December 2016
 */
package com.myitcompany;

public class ITCompany {

	public static void main(String[] args) {
		Company company = new Company();
		
		company.readJSONFromFile("backup.txt");
		company.printToConsole();
		company.update();
		
		//generate more data
		//company.generateData();
		
		company.printToConsole();
		company.writeJSONToFile("backup.txt");
	}
}
