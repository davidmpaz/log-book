/*
 * generated by Xtext 2.29.0
 */
package de.davidmpaz.validation;

import java.time.Year;
import java.time.YearMonth;

import org.eclipse.xtext.validation.Check;

import de.davidmpaz.logBook.Date;
import de.davidmpaz.logBook.LogBookPackage;

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class LogBookValidator extends AbstractLogBookValidator {

	public static final String DUBIOUS_YEAR = "dubiousYear";
	
	public static final String INVALD_DAY = "invalidDay";

	@Check
	public void checkDate(Date date) {
		validateYear(date);
		validateDay(date);
	}

	private void validateDay(Date date) {
		int day = Integer.parseInt(date.getDay());
		int maxDays = getMonthMaximumDays(date);
		String msg = String.format(
			"Invalid day value %d on date. Enter a value between %d and %d", day, 1, maxDays
		);

		if(day < 1 || day > maxDays) {
			error(msg, LogBookPackage.Literals.DATE__DAY, INVALD_DAY);
		}
	}

	private void validateYear(Date date) {
		int year = Integer.parseInt(date.getYear());
		int currentYear = Year.now().getValue();

		if(year < currentYear) {
			warning("You are booking time in the pass!", LogBookPackage.Literals.DATE__YEAR, DUBIOUS_YEAR);
		}

		if(year > currentYear) {
			warning("You are booking time in the future!", LogBookPackage.Literals.DATE__YEAR, DUBIOUS_YEAR);
		}
	}

	/**
	 * Given a Date get the number of maximum days the month has
	 * 
	 * @param date Date typed by user
	 * @return
	 */
	private int getMonthMaximumDays(Date date) {
		YearMonth yearMonthObject = YearMonth.of(
			Integer.parseInt(date.getYear()),
			Integer.parseInt(date.getMonth())
		);
		return yearMonthObject.lengthOfMonth();
	}
	
}
