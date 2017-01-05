/**
 * @author Pimentel
 * @date December 2016
 */
package com.myitcompany;

import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONObject;

import static java.time.temporal.TemporalAdjusters.*;

public class Activity {
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;

	public Activity(String newName, LocalDate newStartDate, LocalDate newEndDate) {
		this.name = newName;
		this.startDate = newStartDate;
		this.endDate = newEndDate;
	}

	public Activity(String newName, int newStartDateYear, int newStartDateMonth, int newStartDateDay, int newEndDateYear, int newEndDateMonth, int newEndDateDay) {
		this.name = newName;
		this.startDate = LocalDate.of(newStartDateYear, newStartDateMonth, newStartDateDay);
		this.endDate = LocalDate.of(newEndDateYear, newEndDateMonth, newEndDateDay);
	}

	public long getDaysCurrentMonth() {
		LocalDate today = LocalDate.now();
		LocalDate firstDayOfTheMonth = today.with(firstDayOfMonth());

		if((this.startDate.compareTo(today) <= 0)  &&  (this.endDate.compareTo(firstDayOfTheMonth) >= 0)) {
			ArrayList<Long> overlappedDays = new ArrayList<Long>();
			long value1 = ChronoUnit.DAYS.between(this.startDate, this.endDate);
			long value2 = ChronoUnit.DAYS.between(firstDayOfTheMonth, this.endDate);
			long value3 = ChronoUnit.DAYS.between(this.startDate, today);
			long value4 = ChronoUnit.DAYS.between(firstDayOfTheMonth, today);

			overlappedDays.add(value1);
			overlappedDays.add(value2);
			overlappedDays.add(value3);
			overlappedDays.add(value4);

			Collections.sort(overlappedDays);

			return overlappedDays.get(0);
		}
		return 0;
	}

	public long getDaysStillInCharge() {
		LocalDate today = LocalDate.now();
		LocalDate lastDayOfTheMonth = today.with(lastDayOfMonth());

		if((this.startDate.compareTo(lastDayOfTheMonth) <= 0)  &&  (this.endDate.compareTo(today) >= 0)) {
			ArrayList<Long> overlappedDays = new ArrayList<Long>();
			long value1 = ChronoUnit.DAYS.between(this.startDate, this.endDate);
			long value2 = ChronoUnit.DAYS.between(today, this.endDate);
			long value3 = ChronoUnit.DAYS.between(this.startDate, lastDayOfTheMonth);
			long value4 = ChronoUnit.DAYS.between(today, lastDayOfTheMonth);

			overlappedDays.add(value1);
			overlappedDays.add(value2);
			overlappedDays.add(value3);
			overlappedDays.add(value4);

			Collections.sort(overlappedDays);

			return overlappedDays.get(0);
		}
		return 0;
	}

	public long getTotalDays() {
		return ChronoUnit.DAYS.between(this.startDate, this.endDate);
	}

	public void update() {
		setEndDate(getEndDate().plusDays(1));
	}

	public boolean inCurrentMonth() {
		LocalDate today = LocalDate.now();
		LocalDate firstDayOfTheCurrentMonth = today.with(firstDayOfMonth());

		if((this.startDate.compareTo(today) <= 0)  &&  (this.endDate.compareTo(firstDayOfTheCurrentMonth) >= 0)) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject createJSON() {
		JSONObject activityObject = new JSONObject();
		activityObject.put("name", this.name);
		activityObject.put("startDate", this.startDate.toString());
		activityObject.put("endDate", this.endDate.toString());

		return activityObject;
	}

	public String getName() {
		return name;
	}
	public void setName(String newName) {
		this.name = newName;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate newStartDate) {
		this.startDate = newStartDate;	
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate newEndDate) {
		this.endDate = newEndDate;
	}

	public static Activity readFromFile(JSONObject activityObject) {
		String endDateString = (String) activityObject.get("endDate");
		String startDateString = (String) activityObject.get("startDate");

		LocalDate endDate = LocalDate.parse(endDateString);
		LocalDate startDate = LocalDate.parse(startDateString);

		String name = (String) activityObject.get("name");

		Activity activity = new Activity(name, startDate, endDate);

		return activity;
	}
}
