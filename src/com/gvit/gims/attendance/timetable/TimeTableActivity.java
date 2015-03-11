package com.gvit.gims.attendance.timetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.gvit.gims.attendance.R;
import com.gvit.gims.navigationdrawer.BaseActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(3)
public class TimeTableActivity extends BaseActivity implements OnClickListener {
	private static final String tag = "MyCalendarActivity";

	private TextView currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	private int month, year;

	private ListView eventTimeView;

	private TextView workloadTextView;
	private static final String dateTemplate = "MMMM yyyy";
	
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param addEvent
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable);

		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
				+ year);

		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (TextView) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) this.findViewById(R.id.calendar);

		eventTimeView = (ListView) findViewById(R.id.eventTimes);
		adapter = new GridCellAdapter(TimeTableActivity.this,
				R.id.calendar_day_gridcell, month, year, createEvents1(),
				eventTimeView);
		workloadTextView = (TextView) findViewById(R.id.workLoad);
		String loadForMonth = getWorkLoadForMonth(createEvents1(),
				getAllDates(adapter.getNumberOfDaysOfMonth(month - 1)));
		workloadTextView.setText("WorkLoad=" + loadForMonth);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from

		set(navMenuTitles, navMenuIcons);

	}

	private ICollegeEvent createEvents1() {
		final Map<String, List<IEventTime>> event = new HashMap<String, List<IEventTime>>();
		List<IEventTime> timings = new ArrayList<IEventTime>();
		List<IEventTime> timings2 = new ArrayList<IEventTime>();

		timings.add(createEvent());
		timings.add(createEvent3());
		timings.add(createEvent2());
		timings2.add(createEvent4());

		event.put("23-2-2015", timings);
		event.put("24-2-2015", timings);
		event.put("25-2-2015", timings);
		event.put("26-2-2015", timings);
		event.put("27-2-2015", timings);
		event.put("28-2-2015", timings);
		event.put("1-3-2015", timings);
		event.put("24-3-2015", timings2);

		ICollegeEvent collegeEvent1 = new ICollegeEvent() {

			@Override
			public Map<String, List<IEventTime>> getEvents() {
				return event;
			}
		};
		return collegeEvent1;
	}

	/**
	 * 
	 * @param month
	 * @param year
	 */
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(TimeTableActivity.this,
				R.id.calendar_day_gridcell, month, year, createEvents1(),
				eventTimeView);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		String loadForMonth = getWorkLoadForMonth(createEvents1(),
				getAllDates(adapter.getNumberOfDaysOfMonth(month - 1)));
		workloadTextView.setText("WorkLoad=" + loadForMonth);
	}

	private List<String> getAllDates(int daysOfMonth) {
		List<String> allDaysOfMonth = new ArrayList<String>();
		for (int i = 1; i <= daysOfMonth; i++) {
			StringBuilder day = new StringBuilder();
			day.append(i).append("-").append(month).append("-").append(year);
			allDaysOfMonth.add(day.toString());
		}
		return allDaysOfMonth;
	}

	@Override
	public void onClick(View v) {
		if (v == prevMonth) {
			CustomList eventCusList = new CustomList(TimeTableActivity.this,
					Collections.<IEventTime> emptyList());
			eventCusList.notifyDataSetChanged();
			eventTimeView.setAdapter(eventCusList);
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
		if (v == nextMonth) {
			CustomList eventCusList = new CustomList(TimeTableActivity.this,
					Collections.<IEventTime> emptyList());
			eventCusList.notifyDataSetChanged();
			eventTimeView.setAdapter(eventCusList);
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
	}

	private String getWorkLoadForMonth(ICollegeEvent allEvents,
			List<String> monthDates) {
		long seconds = 0;
		for (String eventDate : allEvents.getEvents().keySet()) {
			if (monthDates.contains(eventDate)) {
				List<IEventTime> eventList = allEvents.getEvents().get(
						eventDate);
				for (IEventTime event : eventList) {

					seconds = seconds + getTimeDifference(event.getTimeRange());
				}
			}
		}
		String hrs = String.valueOf(seconds / 3600);
		return hrs;

	}

	private long getTimeDifference(String timeRange) {
		String[] times = timeRange.split("-");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		long seconds = 0;
		try {
			Date date1 = format.parse(times[0]);
			Date date2 = format.parse(times[1]);
			long difference = date2.getTime() - date1.getTime();
			seconds = difference / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seconds;
	}

	@Override
	public void onDestroy() {
		Log.d(tag, "Destroying View ...");
		super.onDestroy();
	}

	IEventTime createEvent() {
		return new IEventTime() {

			@Override
			public String getTimeRange() {
				return "10:00-13:30";
			}

			@Override
			public String getEventName() {
				return "Online Exam in C,C#";
			}
		};
	}

	IEventTime createEvent3() {
		return new IEventTime() {

			@Override
			public String getTimeRange() {
				return "13:30-14:30";
			}

			@Override
			public String getEventName() {
				return "Results";
			}
		};
	}

	IEventTime createEvent2() {
		return new IEventTime() {

			@Override
			public String getTimeRange() {
				return "14:30-16:00";
			}

			@Override
			public String getEventName() {
				return "Online Exam in java,php";
			}
		};
	}

	IEventTime createEvent4() {
		return new IEventTime() {

			@Override
			public String getTimeRange() {
				return "16:00-17:00";
			}

			@Override
			public String getEventName() {
				return "Winners";
			}
		};
	}

}
