/**
 * 
 */
package com.ubs.opsit.interviews;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eric
 *
 */
public class BerlinClock implements TimeConverter {

	public static final String TIME_REGEX_PATTERN = "([0-1][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])";
	public static final String INVALID_TIME_FORMAT = "Input time is not correct. Please input your time in HH:mm:ss format.";

	public static final String TIME_SEPARATOR = ":";
	public static final String RED_LAMP = "R";
	public static final String YELLOW_LAMP = "Y";
	public static final String LAMP_OFF = "O";
	public static final String THREE_ON = "YYY";
	public static final String QUARTER_ON = "YYR";

	public static final int NO_OF_TOP_MINUTES_LAMPS = 11;
	public static final int NO_OF_LAMPS_PER_ROW = 4;


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ubs.opsit.interviews.TimeConverter#convertTime(java.lang.String)
	 */
	@Override
	public String convertTime(String aTime) throws IllegalArgumentException {

		if (!validateInputTime(aTime)) {
			return INVALID_TIME_FORMAT;
		}

		String[] timeParts = aTime.split(TIME_SEPARATOR);

		return getSeconds(Integer.parseInt(timeParts[2])).append(System.getProperty("line.separator"))
				.append(getTopHours(Integer.parseInt(timeParts[0]))).append(System.getProperty("line.separator"))
				.append(getBottomHours(Integer.parseInt(timeParts[0]))).append(System.getProperty("line.separator"))
				.append(getTopMinutes(Integer.parseInt(timeParts[1]))).append(System.getProperty("line.separator"))
				.append(getBottomMinutes(Integer.parseInt(timeParts[1]))).toString();

	}

	protected StringBuilder getSeconds(int number) {
		if (number % 2 == 0)
			return new StringBuilder(YELLOW_LAMP);
		else
			return new StringBuilder(LAMP_OFF);
	}

	protected StringBuilder getTopHours(int number) {
		return getOnOff(NO_OF_LAMPS_PER_ROW, getTopNumberOfOnSigns(number));
	}

	protected StringBuilder getBottomHours(int number) {
		return getOnOff(NO_OF_LAMPS_PER_ROW, number % 5);
	}

	protected StringBuilder getTopMinutes(int number) {
		return changeQuarterLight(getOnOff(NO_OF_TOP_MINUTES_LAMPS, getTopNumberOfOnSigns(number), YELLOW_LAMP));
	}

	protected StringBuilder getBottomMinutes(int number) {
		// Minutes lamps set it to yellow
		return getOnOff(NO_OF_LAMPS_PER_ROW, number % 5, YELLOW_LAMP);
	}

	private StringBuilder getOnOff(int lamps, int onSigns) {
		// Hours lamps set it to red
		return getOnOff(lamps, onSigns, RED_LAMP);
	}

	// Build on off lamps for minutes and hours
	private StringBuilder getOnOff(int lamps, int onSigns, String onSign) {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < onSigns; i++) {
			out.append(onSign);
		}
		for (int i = 0; i < (lamps - onSigns); i++) {
			out.append(LAMP_OFF);
		}
		return out;
	}

	// Count number of on signs
	private int getTopNumberOfOnSigns(int number) {
		return (number - (number % 5)) / 5;
	}

	private StringBuilder changeQuarterLight(StringBuilder strBuilder) {

		for (int i = 0; i < strBuilder.length(); i++) {
			if ((i + 1) % 3 == 0 && YELLOW_LAMP.charAt(0) == (strBuilder.charAt(i))) {
				strBuilder.replace(i, i + 1, RED_LAMP);
			}
		}

		return strBuilder;
	}

	private boolean validateInputTime(String inputTime) {
		try {
			if (null != inputTime && !inputTime.isEmpty()) {
				Pattern timeRegexPattern = Pattern.compile(TIME_REGEX_PATTERN);
				Matcher timeMatcher = timeRegexPattern.matcher(inputTime);
				if (!timeMatcher.matches()) {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

}
