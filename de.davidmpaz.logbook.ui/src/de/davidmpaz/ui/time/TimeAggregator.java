package de.davidmpaz.ui.time;

import de.davidmpaz.logBook.LogEntry;
import de.davidmpaz.logBook.Task;

/**
 * Utility class to work with time registered in tasks
 */
public class TimeAggregator {
	
	public float getHours(LogEntry day) {
		float result = 0;
		for(Task task : day.getTasks()) {
			result += getHours(task);
		}

		return result;
	}
	
	public String getHoursAsString(LogEntry day) {
		return Float.toString(getHours(day));
	}
	
	public String getHourLabel(LogEntry day) {
		float hours = getHours(day);
		String suffix = hours > 1 ? "Hours" : "Hour";
		return " (" + Float.toString(hours) + " " + suffix + ")";
	}
	
	public float getHours(Task task) {
		String time = task.getTime().getValue() + "." + task.getTime().getDecimal();
		return Float.parseFloat(time);
	}

}
