/*
  @author Pimentel
  @date December 2016
 */
package com.myitcompany;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Programmer implements Employee {
    private static int numberOfProgrammers;
    private String lastName;
    private String firstName;
    private ArrayList<Activity> assignedActivities = new ArrayList<Activity>();

    Programmer(String newLastName, String newFirstName) {
        this.lastName = newLastName;
        this.firstName = newFirstName;
        Programmer.numberOfProgrammers++;
    }

    static Programmer readFromFile(JSONObject programmerObject) {
        String lastName = (String) programmerObject.get("lastName");
        String firstName = (String) programmerObject.get("firstName");
        JSONArray activitiesArray = (JSONArray) programmerObject.get("activities");

        Programmer programmer = new Programmer(lastName, firstName);

        IntStream.range(0, activitiesArray.size())
                .mapToObj(i -> (JSONObject) activitiesArray.get(i))
                .map(Activity::readFromFile)
                .forEach(programmer::assignActivity);
        return programmer;
    }

    static int getNumberOfProgrammers() {
        return numberOfProgrammers;
    }

    void printToConsole(int salary) {
        IntStream.range(0, assignedActivities.size())
                .forEach(i -> System.out.printf("%s, %s, in charge of \"%s\" from %s to %s (duration %d days), this month: %d days (total cost = %d Euros)%n",
                        this.lastName,
                        this.firstName,
                        assignedActivities.get(i).getName(),
                        assignedActivities.get(i).getStartDate(),
                        assignedActivities.get(i).getEndDate(),
                        assignedActivities.get(i).getTotalDays(),
                        assignedActivities.get(i).getDaysCurrentMonth(),
                        salary * assignedActivities.get(i).getTotalDays()));
    }

    long getDaysCurrentMonth() {
        return assignedActivities
                .stream()
                .mapToLong(Activity::getDaysCurrentMonth)
                .sum();
    }

    long getDaysStillInCharge() {
        return assignedActivities
                .stream()
                .mapToLong(Activity::getDaysStillInCharge)
                .sum();
    }

    long getTotalDays() {
        return assignedActivities
                .stream()
                .mapToLong(Activity::getTotalDays)
                .sum();
    }

    void update() {
        IntStream.range(0, assignedActivities.size())
                .forEach(i -> assignedActivities.get(i).update());
    }

    boolean workedCurrentMonth() {
        return IntStream.range(0, assignedActivities.size())
                .anyMatch(i -> assignedActivities.get(i).inCurrentMonth());
    }

    @SuppressWarnings("unchecked")
    JSONObject createJSON() {
        JSONObject programmerObject = new JSONObject();
        programmerObject.put("lastName", this.lastName);
        programmerObject.put("firstName", this.firstName);
        JSONArray activitiesArray = assignedActivities.stream()
                .map(Activity::createJSON)
                .collect(Collectors.toCollection(JSONArray::new));
        programmerObject.put("activities", activitiesArray);
        return programmerObject;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    ArrayList<Activity> getAssignedActivities() {
        return assignedActivities;
    }

    void assignActivity(Activity newActivity) {
        assignedActivities.add(newActivity);
    }
}
