/*
 * Copyright (c) 2017. Raul Pimentel. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
  @author Pimentel
  @date December 2016
 */
package com.myitcompany;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class ProjectTeam {
    private static int numberOfProjectTeams;
    private ArrayList<Programmer> programmers = new ArrayList<Programmer>();
    private int salary;

    static int getNumberOfProjectTeams() {
        return numberOfProjectTeams;
    }

    static void increaseNumberOfProjectTeams() {
        ProjectTeam.numberOfProjectTeams++;
    }

    static ProjectTeam readFromFile(JSONObject teamObject) {
        String type = (String) teamObject.get("type");
        int salary = ((Long) teamObject.get("salary")).intValue();
        JSONArray programmersArray = (JSONArray) teamObject.get("programmers");

        ProjectTeam team;
        if (Objects.equals(type, "full")) {
            team = new FullPriceProjectTeam();
        } else {
            team = new HalfPriceProjectTeam();
        }

        IntStream.range(0, programmersArray.size())
                .mapToObj(i -> (JSONObject) programmersArray.get(i))
                .map(Programmer::readFromFile)
                .forEach(team::addProgrammer);

        team.setSalary(salary);
        return team;
    }

    long getDaysCurrentMonth() {
        return programmers.stream()
                .mapToLong(Programmer::getDaysCurrentMonth)
                .sum();
    }

    long getDaysStillInCharge() {
        return programmers.stream()
                .mapToLong(Programmer::getDaysStillInCharge)
                .sum();
    }

    long getTotalDays() {
        return programmers.stream()
                .mapToLong(Programmer::getTotalDays)
                .sum();
    }

    void update() {
        IntStream.range(0, programmers.size())
                .forEach(i -> programmers.get(i).update());
    }

    int programmersWorkedCurrentMonth() {
        return (int) IntStream.range(0, programmers.size())
                .filter(i -> programmers.get(i).workedCurrentMonth())
                .count();
    }

    @SuppressWarnings("unchecked")
    JSONObject createJSON() {
        JSONObject teamObject = new JSONObject();
        if (this.getClass() == FullPriceProjectTeam.class) {
            teamObject.put("type", "full");
        } else {
            teamObject.put("type", "half");
        }
        teamObject.put("salary", this.salary);
        JSONArray programmersArray = getProgrammers()
                .stream()
                .map(Programmer::createJSON)
                .collect(Collectors.toCollection(JSONArray::new));
        teamObject.put("programmers", programmersArray);
        return teamObject;
    }

    void printToConsole() {
        IntStream.range(0, programmers.size())
                .forEach(i -> programmers.get(i).printToConsole(getSalary()));
    }

    void addProgrammer(Programmer newProgrammer) {
        this.programmers.add(newProgrammer);
    }

    void removeProgrammer(Programmer programmer) {
        this.programmers.remove(programmer);
    }

    private ArrayList<Programmer> getProgrammers() {
        return this.programmers;
    }

    int getSalary() {
        return this.salary;
    }

    void setSalary(int newSalary) {
        this.salary = newSalary;
    }
}
