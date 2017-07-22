/**
 * @author Pimentel
 * @date December 2016
 */
package com.myitcompany;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Programmer implements Employee {
	private static int numberOfProgrammers;
	private String lastName;
	private String firstName;
	private ArrayList<Activity> assignedActivities = new ArrayList<Activity>();

	public Programmer(String newLastName, String newFirstName) {
		this.lastName = newLastName;
		this.firstName = newFirstName;
		Programmer.numberOfProgrammers++;
	}

	public void printToConsole(int salary) {
		for(int i = 0; i < assignedActivities.size(); i++) {
			System.out.println(this.lastName + ", " + this.firstName + ", in charge of \"" + assignedActivities.get(i).getName() + 
					"\" from " + assignedActivities.get(i).getStartDate() + " to " + assignedActivities.get(i).getEndDate() +
					" (duration " + assignedActivities.get(i).getTotalDays() + " days), this month: " +
					assignedActivities.get(i).getDaysCurrentMonth() + " days (total cost = " +
					(salary * assignedActivities.get(i).getTotalDays()) + " Euros)");
		}
	}

	public long getDaysCurrentMonth() {
		long daysCurrentMonth = 0;
		for(int i = 0; i < assignedActivities.size(); i++) {
			daysCurrentMonth += assignedActivities.get(i).getDaysCurrentMonth();
		}
		return daysCurrentMonth;
	}

	public long getDaysStillInCharge() {
		long daysStillInCharge = 0;
		for(int i = 0; i < assignedActivities.size(); i++) {
			daysStillInCharge += assignedActivities.get(i).getDaysStillInCharge();
		}
		return daysStillInCharge;
	}

	public long getTotalDays() {
		long totalDays = 0;
		for(int i = 0; i < assignedActivities.size(); i++) {
			totalDays += assignedActivities.get(i).getTotalDays();
		}
		return totalDays;
	}

	public void update() {
		for(int i = 0; i < assignedActivities.size(); i++) {
			assignedActivities.get(i).update();
		}
	}

	public boolean workedCurrentMonth() {
		boolean workedCurrentMonth = false;
		for(int i = 0; i < assignedActivities.size(); i++) {
			if(assignedActivities.get(i).inCurrentMonth()) {
				workedCurrentMonth = true;
				break;
			}
		}
		return workedCurrentMonth;
	}

	@SuppressWarnings("unchecked")
	public JSONObject createJSON() {
		JSONObject programmerObject = new JSONObject();
		programmerObject.put("lastName", this.lastName);
		programmerObject.put("firstName", this.firstName);
		JSONArray activitiesArray = new JSONArray();
		for(int i = 0; i < assignedActivities.size(); i++) {
			JSONObject activityObject = assignedActivities.get(i).createJSON();
			activitiesArray.add(activityObject);
		}
		programmerObject.put("activities", activitiesArray);
		return programmerObject;
	}

	public static Programmer readFromFile(JSONObject programmerObject) {
		String lastName = (String) programmerObject.get("lastName");
		String firstName = (String) programmerObject.get("firstName");
		JSONArray activitiesArray = (JSONArray) programmerObject.get("activities");

		Programmer programmer = new Programmer(lastName, firstName);

		for(int i = 0; i < activitiesArray.size(); i++) {
			JSONObject activityObject = (JSONObject) activitiesArray.get(i);
			Activity activity = Activity.readFromFile(activityObject);
			programmer.assignActivity(activity);
		}
		return programmer;
	}

	public static int getNumberOfProgrammers() {
		return numberOfProgrammers;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	public ArrayList<Activity> getAssignedActivities() {
		return assignedActivities;
	}

	public void assignActivity(Activity newActivity) {
		assignedActivities.add(newActivity);
	}
}
