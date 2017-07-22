/*
  @author Pimentel
  @date December 2016
 */
package com.myitcompany;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Company {
    private ArrayList<ProjectTeam> teams = new ArrayList<ProjectTeam>();

    void printToConsole() {
        System.out.println("IT COMPANY - Report\n");
        System.out.println("IT Company is actually composed of " + teams.size() + " project teams, and " +
                Programmer.getNumberOfProgrammers() + " programmers.");
        System.out.printf("This month, %d days have been consumed by %d programmers, and %d days still in charge.\n%n",
                getDaysCurrentMonth(),
                programmersWorkedCurrentMonth(),
                getDaysStillInCharge());
        for (int i = 0; i < teams.size(); i++) {
            System.out.println("Project team: " + (i + 1));
            teams.get(i).printToConsole();
            System.out.println("");
        }
        System.out.println("");
    }

    private long getDaysCurrentMonth() {
        return teams.stream().mapToLong(ProjectTeam::getDaysCurrentMonth).sum();
    }

    private long getDaysStillInCharge() {
        return teams.stream().mapToLong(ProjectTeam::getDaysStillInCharge).sum();
    }

    long getTotalDays() {
        return teams.stream().mapToLong(ProjectTeam::getTotalDays).sum();
    }

    private void addProjectTeam(ProjectTeam newProjectTeam) {
        teams.add(newProjectTeam);
    }

    void update() {
        IntStream.range(0, teams.size()).forEach(i -> teams.get(i).update());
    }

    private int programmersWorkedCurrentMonth() {
        return teams.stream().mapToInt(ProjectTeam::programmersWorkedCurrentMonth).sum();
    }

    void readJSONFromFile(String filename) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader(filename));
            JSONArray teamsArray = (JSONArray) object.get("teams");

            IntStream.range(0, teamsArray.size())
                    .mapToObj(i -> (JSONObject) teamsArray.get(i))
                    .map(ProjectTeam::readFromFile)
                    .forEach(this::addProjectTeam);

        } catch (FileNotFoundException e) {
            generateData();
            writeJSONToFile(filename);
            printToConsole();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    private JSONObject createJSON() {
        JSONObject companyObject = new JSONObject();
        JSONArray teamsArray = teams.stream()
                .map(ProjectTeam::createJSON)
                .collect(Collectors.toCollection(JSONArray::new));
        companyObject.put("teams", teamsArray);
        return companyObject;
    }

    void writeJSONToFile(String filename) {
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

    private void generateData() {
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
