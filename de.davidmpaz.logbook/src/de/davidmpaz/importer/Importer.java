package de.davidmpaz.importer;

import de.davidmpaz.logBook.Model;

/**
 * Contract for getting a LogBook Model
 * 
 * Get the model out of data string representation
 */
public interface Importer {
	Model getModelFrom(String data);
}
