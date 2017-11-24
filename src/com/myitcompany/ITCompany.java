/*
 * Copyright (c) 2017. Raul Pimentel.
 */

package com.myitcompany;

public class ITCompany {

	public static void main(String[] args) {
		Company company = new Company();
		
		company.readJSONFromFile("backup.txt");
		company.printToConsole();
		company.update();
		
		//uncomment the next line to generate more data
		//company.generateData();
		
		company.printToConsole();
		company.writeJSONToFile("backup.txt");
	}
}
