/**
 * @author Pimentel
 * @date December 2016
 */
package com.myitcompany;

import java.io.*;
import java.time.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Company {
	private ArrayList<ProjectTeam> teams = new ArrayList<ProjectTeam>();

	public void printToConsole() {
		System.out.println("IT COMPANY - Report\n");
		System.out.println("IT Company is actually composed of " + teams.size() + " project teams, and " +
				Programmer.getNumberOfProgrammers() + " programmers.");
		System.out.println("This month, " + getDaysCurrentMonth() + " days have been consummed by " +
				programmersWorkedCurrentMonth() + " programmers, and " +
				getDaysStillInCharge() + " days still in charge.\n");
		for(int i = 0; i < teams.size(); i++) {
			System.out.println("Project team: " + (i + 1));
			teams.get(i).printToConsole();
			System.out.println("");
		}
		System.out.println("");
	}

	public long getDaysCurrentMonth() {
		long daysCurrentMonth = 0;
		for(int i = 0; i < teams.size(); i++) {
			daysCurrentMonth += teams.get(i).getDaysCurrentMonth();
		}
		return daysCurrentMonth;
	}

	public long getDaysStillInCharge() {
		long daysStillInCharge = 0;
		for(int i = 0; i < teams.size(); i++) {
			daysStillInCharge += teams.get(i).getDaysStillInCharge();
		}
		return daysStillInCharge;
	}

	public long getTotalDays() {
		long totalDays = 0;
		for(int i = 0; i < teams.size(); i++) {
			totalDays += teams.get(i).getTotalDays();
		}
		return totalDays;
	}
	
	public void addProjectTeam(ProjectTeam newProjectTeam) {
		teams.add(newProjectTeam);
	}

	public void update() {
		for(int i = 0; i < teams.size(); i++) {
			teams.get(i).update();
		}
	}

	public int programmersWorkedCurrentMonth() {
		int programmersCompanyWorkedCurrentMonth = 0;

		for(int i = 0; i < teams.size(); i++) {
			int programmersTeamWorkedCurrentMonth = teams.get(i).programmersWorkedCurrentMonth();
			programmersCompanyWorkedCurrentMonth += programmersTeamWorkedCurrentMonth;
		}
		return programmersCompanyWorkedCurrentMonth;
	}

	public void readJSONFromFile(String filename) {
		JSONParser parser = new JSONParser();

		try {
			JSONObject object = (JSONObject) parser.parse(new FileReader(filename));
			JSONArray teamsArray = (JSONArray) object.get("teams");

			for(int i = 0; i < teamsArray.size(); i++) {
				JSONObject teamObject = (JSONObject) teamsArray.get(i);

				ProjectTeam team = ProjectTeam.readFromFile(teamObject);

				addProjectTeam(team);
			}

		} catch (FileNotFoundException e) {
			generateData();
			writeJSONToFile(filename);
			printToConsole();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	public JSONObject createJSON() {
		JSONObject companyObject = new JSONObject();
		JSONArray teamsArray = new JSONArray();
		for(int i = 0; i < teams.size(); i++) {
			JSONObject teamObject = teams.get(i).createJSON();
			teamsArray.add(teamObject);
		}
		companyObject.put("teams", teamsArray);
		return companyObject;
	}

	public void writeJSONToFile(String filename) {
		JSONObject object = createJSON(); 
		try {
			FileWriter file = new FileWriter(filename);
			file.write(object.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateData() {
		LocalDate date1 = LocalDate.of(2016, 11, 17);
		LocalDate date2 = LocalDate.of(2016, 12, 18);
		Activity activity1 = new Activity("Programming in C", date1, date2);
		
		Programmer prog1 = new Programmer("Ritchie", "Dennis");
		prog1.assignActivity(activity1);
		
		LocalDate date3 = LocalDate.of(2017, 1, 2);
		LocalDate date4 = LocalDate.of(2017, 1, 28);
		Activity activity2 = new Activity("Debugging", date3, date4);
		
		Programmer prog2 = new Programmer("Thompson", "Ken");
		prog2.assignActivity(activity2);
		
		LocalDate date5 = LocalDate.of(2016, 12, 2);
		LocalDate date6 = LocalDate.of(2017, 1, 28);
		Activity activity3 = new Activity("Debugging", date5, date6);
		
		Programmer prog3 = new Programmer("Stallman", "Richard");
		prog3.assignActivity(activity3);
		
		LocalDate date7 = LocalDate.of(2016, 12, 2);
		LocalDate date8 = LocalDate.of(2017, 1, 28);
		Activity activity4 = new Activity("Programming in COBOL", date7, date8);
		
		Programmer prog4 = new Programmer("Hoper", "Grace");
		prog4.assignActivity(activity4);
		
		FullPriceProjectTeam fullTeam = new FullPriceProjectTeam();
		fullTeam.setSalary(10);
		fullTeam.addProgrammer(prog1);
		fullTeam.addProgrammer(prog2);
		
		HalfPriceProjectTeam halfTeam = new HalfPriceProjectTeam();
		halfTeam.setSalary(10);
		halfTeam.addProgrammer(prog3);
		halfTeam.addProgrammer(prog4);
		
		addProjectTeam(fullTeam);
		addProjectTeam(halfTeam);
	}
}
