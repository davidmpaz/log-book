package de.davidmpaz.importer;

/**
 * Used mainly to propagate the string constants used
 *
 * These strings are used in the validation process and in the custom
 * Preference page. Since they identify the preference in the store and
 * the scope and dependency order of plug-ins is important we included
 * here.
 *
 * @author davidmpaz
 *
 */
public class Properties {

	public static final String PROPERTY_ISSUES_FILE = "de.davidmpaz.issues_file";
	public static final String PROPERTY_DEFAULT_ISSUES_FILE = "~/issues.json";

	public static final String PROPERTY_TIME_FILE = "de.davidmpaz.time_file";
	public static final String PROPERTY_DEFAULT_TIME_FILE = "~/time_entries.json";
}