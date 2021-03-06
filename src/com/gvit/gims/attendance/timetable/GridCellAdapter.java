/**
 * 
 */
package com.gvit.gims.attendance.timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.gvit.gims.attendance.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author VAMSI KRISHNA
 *
 */
public class GridCellAdapter extends BaseAdapter implements OnClickListener {
	private static final String tag = "GridCellAdapter";
	private final Activity _context;

	private final List<String> list;
	private static final int DAY_OFFSET = 1;
	private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
			"Wed", "Thu", "Fri", "Sat" };
	private final String[] months = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };
	private int daysInMonth;
	private int currentDayOfMonth;
	private int currentWeekDay;
	private Button gridcell;
	private ICollegeEvent collegeEvents;
	private ListView eventListView;

	// Days in Current Month
	public GridCellAdapter(Activity context, int textViewResourceId, int month,
			int year, ICollegeEvent events, ListView eventTimeView) {
		this._context = context;
		this.list = new ArrayList<String>();
		this.eventListView = eventTimeView;
		this.collegeEvents = events;
		Log.d(tag, "==> Passed in Date FOR Month: " + month + " " + "Year: "
				+ year);
		Calendar calendar = Calendar.getInstance();
		setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
		setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
		Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
		Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

		// Print Month
		printMonth(month, year);

	}

	private String getMonthAsString(int i) {
		return months[i];
	}

	private String getWeekDayAsString(int i) {
		return weekdays[i];
	}

	public int getNumberOfDaysOfMonth(int i) {
		return daysOfMonth[i];
	}

	public String getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	/**
	 * Prints Month
	 * 
	 * @param mm
	 * @param yy
	 */
	private void printMonth(int mm, int yy) {
		Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;

		int currentMonth = mm - 1;
		String currentMonthName = getMonthAsString(currentMonth);
		daysInMonth = getNumberOfDaysOfMonth(currentMonth);

		Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
				+ daysInMonth + " days.");

		GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
		Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

		if (currentMonth == 11) {
			prevMonth = currentMonth - 1;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy + 1;
			Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:" + prevMonth
					+ " NextMonth: " + nextMonth + " NextYear: " + nextYear);
		} else if (currentMonth == 0) {
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 1;
			Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
					+ prevMonth + " NextMonth: " + nextMonth + " NextYear: "
					+ nextYear);
		} else {
			prevMonth = currentMonth - 1;
			nextMonth = currentMonth + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
					+ prevMonth + " NextMonth: " + nextMonth + " NextYear: "
					+ nextYear);
		}

		int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		trailingSpaces = currentWeekDay;

		Log.d(tag, "Week Day:" + currentWeekDay + " is "
				+ getWeekDayAsString(currentWeekDay));
		Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
		Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

		if (cal.isLeapYear(cal.get(Calendar.YEAR)))
			if (mm == 2)
				++daysInMonth;
			else if (mm == 3)
				++daysInPrevMonth;

		// Trailing Month days
		for (int i = 0; i < trailingSpaces; i++) {
			Log.d(tag,
					"PREV MONTH:= "
							+ prevMonth
							+ " => "
							+ getMonthAsString(prevMonth)
							+ " "
							+ String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
									+ i));
			list.add(String
					.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
							+ i)
					+ "-GREY"
					+ "-"
					+ getMonthAsString(prevMonth)
					+ "-"
					+ prevYear);
		}

		// Current Month Days
		for (int i = 1; i <= daysInMonth; i++) {
			Log.d(currentMonthName, String.valueOf(i) + " "
					+ getMonthAsString(currentMonth) + " " + yy);
			if (i == getCurrentDayOfMonth()) {
				list.add(String.valueOf(i) + "-BLUE" + "-"
						+ getMonthAsString(currentMonth) + "-" + yy);
			} else {
				list.add(String.valueOf(i) + "-WHITE" + "-"
						+ getMonthAsString(currentMonth) + "-" + yy);
			}
		}

		// Leading Month days
		for (int i = 0; i < list.size() % 7; i++) {
			Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
			list.add(String.valueOf(i + 1) + "-GREY" + "-"
					+ getMonthAsString(nextMonth) + "-" + nextYear);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) _context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.screen_gridcell, parent, false);
		}

		// Get a reference to the Day gridcell
		gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
		gridcell.setOnClickListener(this);

		// ACCOUNT FOR SPACING

		Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
		String[] day_color = list.get(position).split("-");
		String theday = day_color[0];
		String themonth = day_color[2];
		String theyear = day_color[3];

		// Set the Day GridCell
		gridcell.setText(theday);
		gridcell.setTag(theday + "-" + themonth + "-" + theyear);
		Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
				+ theyear);

		if (day_color[1].equals("GREY")) {
			gridcell.setTextColor(this._context.getResources().getColor(
					R.color.lightgray));
		}
		if (day_color[1].equals("WHITE")) {
			gridcell.setTextColor(this._context.getResources().getColor(
					R.color.lightgray02));
		}
		if (day_color[1].equals("BLUE")) {
			gridcell.setTextColor(this._context.getResources().getColor(
					R.color.orrange));
		}
		for (String eventDate : collegeEvents.getEvents().keySet()) {
			// String colEvent = cEvent.getDate();
			String[] eventday_split = eventDate.split("-");
			String eventday = eventday_split[0];
			int eventmonth = Integer.parseInt(eventday_split[1]);
			String eventyear = eventday_split[2];
			if (eventday.equalsIgnoreCase(theday)
					&& getMonthAsString(eventmonth - 1).equalsIgnoreCase(
							themonth) && eventyear.equalsIgnoreCase(theyear)) {
				gridcell.setTextColor(this._context.getResources().getColor(
						R.color.blue));
			}

		}
		return row;
	}

	@Override
	public void onClick(View view) {
		String date_month_year = (String) view.getTag();
		updateEventListView(date_month_year, collegeEvents.getEvents());

	}

	public int getCurrentDayOfMonth() {
		return currentDayOfMonth;
	}

	private void setCurrentDayOfMonth(int currentDayOfMonth) {
		this.currentDayOfMonth = currentDayOfMonth;
	}

	public void setCurrentWeekDay(int currentWeekDay) {
		this.currentWeekDay = currentWeekDay;
	}

	public int getCurrentWeekDay() {
		return currentWeekDay;
	}

	private void updateEventListView(String selectedDate,
			Map<String, List<IEventTime>> eventData) {
		String[] tag_split = selectedDate.split("-");
		String tagDay = tag_split[0];
		int tagMonth = Arrays.asList(months).indexOf(tag_split[1]) + 1;
		String tagYear = tag_split[2];
		StringBuilder tagString = new StringBuilder(tagDay);
		tagString.append("-").append(tagMonth).append("-").append(tagYear);
		String tagDate = tagString.toString();
		if (eventData.containsKey(tagDate)) {
			List<IEventTime> events = eventData.get(tagDate);
			CustomList eventCusList = new CustomList(_context, events);
			eventListView.setAdapter(eventCusList);
		} else {
			CustomList eventCusList = new CustomList(_context,
					Collections.<IEventTime> emptyList());
			eventCusList.notifyDataSetChanged();
			eventListView.setAdapter(eventCusList);
		}
	}

}