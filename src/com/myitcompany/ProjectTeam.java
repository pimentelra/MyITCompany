/**
 * @author Pimentel
 * @date December 2016
 */
package com.myitcompany;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class ProjectTeam {
	private static int numberOfProjectTeams;
	private ArrayList<Programmer> programmers = new ArrayList<Programmer>();
	private int salary;

	public long getDaysCurrentMonth() {
		long daysCurrentMonth = 0;
		for(int i = 0; i < programmers.size(); i++) {
			daysCurrentMonth += programmers.get(i).getDaysCurrentMonth();
		}
		return daysCurrentMonth;
	}

	public long getDaysStillInCharge() {
		long daysStillInCharge = 0;
		for(int i = 0; i < programmers.size(); i++) {
			daysStillInCharge += programmers.get(i).getDaysStillInCharge();
		}
		return daysStillInCharge;
	}

	public long getTotalDays() {
		long totalDays = 0;
		for(int i = 0; i < programmers.size(); i++) {
			totalDays += programmers.get(i).getTotalDays();
		}
		return totalDays;
	}

	public void update() {
		for(int i = 0; i < programmers.size(); i++) {
			programmers.get(i).update();
		}
	}

	public int programmersWorkedCurrentMonth() {
		int programmersWorkedCurrentMonth = 0;
		for(int i = 0; i < programmers.size(); i++) {
			if(programmers.get(i).workedCurrentMonth()) {
				programmersWorkedCurrentMonth++;
			}
		}
		return programmersWorkedCurrentMonth;
	}

	@SuppressWarnings("unchecked")
	public JSONObject createJSON() {
		JSONObject teamObject = new JSONObject();
		if(this.getClass() == FullPriceProjectTeam.class) {
			teamObject.put("type", "full");
		} else {
			teamObject.put("type", "half");
		}
		teamObject.put("salary", this.salary);
		JSONArray programmersArray = new JSONArray();
		for(int i = 0; i < getProgrammers().size(); i++) {
			JSONObject programmerObject = getProgrammers().get(i).createJSON();
			programmersArray.add(programmerObject);
		}
		teamObject.put("programmers", programmersArray);
		return teamObject;
	}
	
	

	public void printToConsole() {
		for(int i = 0; i < programmers.size(); i++) {
			programmers.get(i).printToConsole(getSalary());
		}
	}

	public static int getNumberOfProjectTeams() {
		return numberOfProjectTeams;
	}
	public static void increaseNumberOfProjectTeams() {
		ProjectTeam.numberOfProjectTeams++;
	}
	public void addProgrammer(Programmer newProgrammer) {
		this.programmers.add(newProgrammer);
	}
	public void removeProgrammer(Programmer programmer) {
		this.programmers.remove(programmer);
	}
	public ArrayList<Programmer> getProgrammers() {
		return this.programmers;
	}
	public int getSalary() {
		return this.salary;
	};

	public void setSalary(int newSalary) {
		this.salary = newSalary;
	}

	public static ProjectTeam readFromFile(JSONObject teamObject) {
		String type = (String) teamObject.get("type");
		int salary = ((Long)teamObject.get("salary")).intValue();
		JSONArray programmersArray = (JSONArray) teamObject.get("programmers");
		
		ProjectTeam team;
		if(type == "full") {
			team = new FullPriceProjectTeam();
		} else {
			team = new HalfPriceProjectTeam();
		}
		
		for(int i = 0; i < programmersArray.size(); i++) {
			JSONObject programmerObject = (JSONObject) programmersArray.get(i);
			Programmer programmer = Programmer.readFromFile(programmerObject);
			team.addProgrammer(programmer);
		}
		
		team.setSalary(salary);
		return team;
	}
}
