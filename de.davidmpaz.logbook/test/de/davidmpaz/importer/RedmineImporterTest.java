package de.davidmpaz.importer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.inject.Inject;

import de.davidmpaz.logBook.LogEntry;
import de.davidmpaz.logBook.Model;
import de.davidmpaz.logBook.Task;

class RedmineTimeEntriesImporterTest {

	@Inject()
	ITimeEntriesImporter importer = new RedmineTimeEntriesImporter();

	@Test
	void test() {
		String json = "{\n"
				+ "  \"time_entries\": [\n"
				+ "    {\n"
				+ "      \"id\": 54198,\n"
				+ "      \"project\": {\n"
				+ "        \"id\": 216814,\n"
				+ "        \"name\": \"cooking diner\"\n"
				+ "      },\n"
				+ "      \"issue\": {\n"
				+ "        \"id\": 344452\n"
				+ "      },\n"
				+ "      \"user\": {\n"
				+ "        \"id\": 533011,\n"
				+ "        \"name\": \"Alexey Lychits\"\n"
				+ "      },\n"
				+ "      \"activity\": {\n"
				+ "        \"id\": 9,\n"
				+ "        \"name\": \"Development\"\n"
				+ "      },\n"
				+ "      \"hours\": 1.6,\n"
				+ "      \"comments\": \"\",\n"
				+ "      \"spent_on\": \"2023-02-20\",\n"
				+ "      \"created_on\": \"2023-02-20T15:43:06.000Z\",\n"
				+ "      \"updated_on\": \"2023-02-20T15:43:06.000Z\"\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"total_count\": 1,\n"
				+ "  \"offset\": 0,\n"
				+ "  \"limit\": 25\n"
				+ "}";

		Model model = importer.getModelFrom(json);

		LogEntry entry = model.getEntries().get(0);
		assertEquals(1, model.getEntries().size());
		assertEquals(1, entry.getTasks().size());
		Task task = entry.getTasks().get(0);
		assertEquals(344452, task.getTaskId());
		assertEquals("meeting", task.getActivity().getName());
		assertEquals(1, task.getTime().getValue());
		assertEquals(6, task.getTime().getDecimal());
	}

}
