package de.davidmpaz.importer;

import de.davidmpaz.logBook.Model;

/**
 * Contract for getting a LogBook Model
 *
 * Get the model out of data string representation
 */
public interface ITimeEntriesImporter {
	/**
	 * Parse data and extract information useful to build a model
	 */
	Model getModelFrom(String data);
}
