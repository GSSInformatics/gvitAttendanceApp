package com.gvit.gims.attendance;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gvit.gims.attendance.timetable.TimeTableActivity;
import com.gvit.gims.navigationdrawer.BaseActivity;

public class MainActivity extends BaseActivity {

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button attendanceBtn = (Button) findViewById(R.id.attendanceBtn);
		attendanceBtn.setOnClickListener(addClickListener());
		Button timeTableBtn = (Button) findViewById(R.id.timeTableBtn);
		timeTableBtn.setOnClickListener(addClickListener());
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from

		set(navMenuTitles, navMenuIcons);
	}

	private OnClickListener addClickListener() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.attendanceBtn:
					Intent startApp = new Intent(MainActivity.this,
							ClassSelection.class);
					startActivity(startApp);
					break;
				case R.id.timeTableBtn:
					Intent startTimeTable = new Intent(MainActivity.this,
							TimeTableActivity.class);
					startActivity(startTimeTable);
					break;
				default:
					break;
				}
			}
		};
		return listener;
	}
}
