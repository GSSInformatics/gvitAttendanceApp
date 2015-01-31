package com.gvit.gims.attendance.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class ConvertStringToDate {

	public static Date converStringToDate(String dateAsString) {

		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsString);
		} catch (ParseException e) {
			Log.e("ParseError", "Error durig parsing of date object", e);
		}
		return date;
	}

}
