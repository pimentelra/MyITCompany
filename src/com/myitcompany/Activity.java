/*
  @author Pimentel
  @date December 2016
 */
package com.myitcompany;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

class Activity {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    Activity(String newName, LocalDate newStartDate, LocalDate newEndDate) {
        this.name = newName;
        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }

    static Activity readFromFile(JSONObject activityObject) {
        String endDateString = (String) activityObject.get("endDate");
        String startDateString = (String) activityObject.get("startDate");

        LocalDate endDate = LocalDate.parse(endDateString);
        LocalDate startDate = LocalDate.parse(startDateString);

        String name = (String) activityObject.get("name");

        return new Activity(name, startDate, endDate);
    }

    long getDaysCurrentMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfTheMonth = today.with(firstDayOfMonth());

        Long overlappedDays = getDaysBetween(today, firstDayOfTheMonth);
        if (overlappedDays != null) return overlappedDays;
        return 0;
    }

    long getDaysStillInCharge() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfTheMonth = today.with(lastDayOfMonth());

        Long overlappedDays = getDaysBetween(today, lastDayOfTheMonth);
        if (overlappedDays != null) return overlappedDays;
        return 0;
    }

    private Long getDaysBetween(LocalDate firstDate, LocalDate lastDate) {
        if ((this.startDate.compareTo(lastDate) <= 0) && (this.endDate.compareTo(firstDate) >= 0)) {
            PriorityQueue<Long> overlappedDays = new PriorityQueue<Long>();
            overlappedDays.add(ChronoUnit.DAYS.between(this.startDate, this.endDate));
            overlappedDays.add(ChronoUnit.DAYS.between(firstDate, this.endDate));
            overlappedDays.add(ChronoUnit.DAYS.between(this.startDate, lastDate));
            overlappedDays.add(ChronoUnit.DAYS.between(firstDate, lastDate));

            return overlappedDays.peek();
        }
        return null;
    }

    long getTotalDays() {
        return ChronoUnit.DAYS.between(this.startDate, this.endDate);
    }

    void update() {
        setEndDate(getEndDate().plusDays(1));
    }

    boolean inCurrentMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfTheCurrentMonth = today.with(firstDayOfMonth());

        return (this.startDate.compareTo(today) <= 0) && (this.endDate.compareTo(firstDayOfTheCurrentMonth) >= 0);
    }

    @SuppressWarnings("unchecked")
    JSONObject createJSON() {
        JSONObject activityObject = new JSONObject();
        activityObject.put("name", this.name);
        activityObject.put("startDate", this.startDate.toString());
        activityObject.put("endDate", this.endDate.toString());

        return activityObject;
    }

    String getName() {
        return name;
    }

    void setName(String newName) {
        this.name = newName;
    }

    LocalDate getStartDate() {
        return startDate;
    }

    void setStartDate(LocalDate newStartDate) {
        this.startDate = newStartDate;
    }

    LocalDate getEndDate() {
        return endDate;
    }

    void setEndDate(LocalDate newEndDate) {
        this.endDate = newEndDate;
    }
}
