package de.davidmpaz.importer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.inject.Singleton;

import de.davidmpaz.logBook.Activity;
import de.davidmpaz.logBook.LogBookFactory;
import de.davidmpaz.logBook.LogBookPackage;
import de.davidmpaz.logBook.LogEntry;
import de.davidmpaz.logBook.Model;
import de.davidmpaz.logBook.NumberLiteral;
import de.davidmpaz.logBook.Task;

/**
 * Specific importer for redmine time entries
 *
 * Read time entries from a JSON response of a redmine AP
 * call: /time_entries.json and build a log book model with it
 */
@Singleton
public class RedmineTimeEntriesImporter implements ITimeEntriesImporter {

	private static final Logger logger = Logger.getLogger(AbstractInternalAntlrParser.class);

	@Override
	public Model getModelFrom(String data) {
		Model model = LogBookFactory.eINSTANCE.createModel();

		JSONArray entries = getTimeEntries(data);
		HashMap<String, List<Task>> tasks = new HashMap<>();
		try {
			tasks = extractTasks(entries);
		} catch (ParseException e) {
			logger.error(e);
			return model;
		}

		List<LogEntry> logEntries = new ArrayList<>();
		for(String date : tasks.keySet()) {
			de.davidmpaz.logBook.Date d = extractDate(date);
			LogEntry entry = LogBookFactory.eINSTANCE.createLogEntry();
			entry.setDate(d);
			entry.eSet(LogBookPackage.Literals.LOG_ENTRY__TASKS, tasks.get(date));
			logEntries.add(entry);
		}

		model.eSet(LogBookPackage.Literals.MODEL__ENTRIES, logEntries);

		return model;
	}

	private JSONArray getTimeEntries(String data) {
		final JSONObject obj = new JSONObject(data);
		return  obj.getJSONArray("time_entries");
	}

	private HashMap<String, List<Task>> extractTasks(JSONArray entries) throws ParseException {
		HashMap<String, List<Task>> result = new HashMap<>();

		final int n = entries.length();
		for (int i = 0; i < n; ++i) {
			final JSONObject entry = entries.getJSONObject(i);
			Task task = LogBookFactory.eINSTANCE.createTask();

			task.setTaskId(entry.getJSONObject("issue").getInt("id"));
			task.setTime(extractNumberLiteral(entry.getFloat("hours")));

			String act = extractActivity(entry.getJSONObject("activity"));
			task.setActivity(Activity.getByName(act));

			task.setDescription(entry.getString("comments"));

			String date = extractDate(entry);
			if (!result.containsKey(date)) {
				ArrayList<Task> list = new ArrayList<>();
				result.put(date, list);
			}

			result.get(date).add(task);
		}

		return result;
	}

	private NumberLiteral extractNumberLiteral(Float floatValue) {
		NumberLiteral nl = LogBookFactory.eINSTANCE.createNumberLiteral();
		String str = Float.toString(floatValue);
		String[] parts = str.split("\\.");
		nl.setValue(Integer.parseInt(parts[0]));
		nl.setDecimal(Integer.parseInt(parts[1]));

		return nl;
	}

	private String extractActivity(JSONObject activity) {
		return activity.getString("name");
	}

	private String extractDate(JSONObject entry) throws ParseException {
		return entry.getString("spent_on");
	}

	private de.davidmpaz.logBook.Date extractDate(String date) {
		de.davidmpaz.logBook.Date result = LogBookFactory.eINSTANCE.createDate();
		result.setDay(date.split("-")[2]);
		result.setMonth(date.split("-")[1]);
		result.setYear(date.split("-")[0]);

		return result;
	}

}
