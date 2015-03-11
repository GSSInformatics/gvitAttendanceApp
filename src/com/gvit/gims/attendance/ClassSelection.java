package com.gvit.gims.attendance;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.gvit.gims.attendance.Export.LoadCSVToDB;
import com.gvit.gims.attendance.dtos.ClassInformationDTO;
import com.gvit.gims.attendance.login.LoginDBContentProvider;
import com.gvit.gims.navigationdrawer.BaseActivity;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class ClassSelection extends BaseActivity {

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private Spinner departSpin;
	private Spinner yearSpin;
	private Spinner subjectSpin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_selection);
		departSpin = (Spinner) findViewById(R.id.departmentSpinner);
		departSpin.setOnItemSelectedListener(createListener());

		yearSpin = (Spinner) findViewById(R.id.yearSpinner);
		yearSpin.setOnItemSelectedListener(createListener());

		subjectSpin = (Spinner) findViewById(R.id.subjectSpin);
		turnOffCalenderViewForDatePicker();
		registerListenerOnStudentButton();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from

		set(navMenuTitles, navMenuIcons);
	}

	private void registerListenerOnStudentButton() {
		View loadStundButton = findViewById(R.id.loadStudentsButton);
		loadStundButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ClassSelection.this, Students.class);

				// 3. or you can add data to a bundle
				Bundle extras = new Bundle();
				String department = (String) departSpin.getSelectedItem();

				String year = (String) yearSpin.getSelectedItem();

				Spinner sectionSpin = (Spinner) findViewById(R.id.sectionSpinner);
				String section = (String) sectionSpin.getSelectedItem();

				Spinner periodSpin = (Spinner) findViewById(R.id.periodSpin);
				String period = (String) periodSpin.getSelectedItem();

				String subject = (String) subjectSpin.getSelectedItem();

				DatePicker attenDatPick = (DatePicker) findViewById(R.id.attendanceDate);
				String attendanceDate = new StringBuilder()
						.append(attenDatPick.getDayOfMonth()).append("/")
						.append(attenDatPick.getMonth() + 1).append("/")
						.append(attenDatPick.getYear()).toString();

				StringBuilder calculatedString = new StringBuilder();
				calculatedString.append(department).append(year)
						.append(section);

				extras.putString("studentListSelection",
						calculatedString.toString());

				ClassInformationDTO classDTO = new ClassInformationDTO();
				classDTO.setDepartment(department);
				classDTO.setPeriod(period);
				classDTO.setSection(section);
				classDTO.setSubject(subject);
				classDTO.setYear(year);
				classDTO.setAttendanceDate(attendanceDate);
				extras.putSerializable("classSelectionDTO", classDTO);

				// 4. add bundle to intent
				intent.putExtras(extras);

				startActivity(intent);
			}
		});
	}

	private OnItemSelectedListener createListener() {
		OnItemSelectedListener listener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String department = (String) departSpin.getSelectedItem();
				String year = (String) yearSpin.getSelectedItem();
				loadSubjects(department, year);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		};
		return listener;
	}

	private void turnOffCalenderViewForDatePicker() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {
			try {
				View datePicker = findViewById(R.id.attendanceDate);
				Method m = datePicker.getClass().getMethod(
						"setCalendarViewShown", boolean.class);
				m.invoke(datePicker, false);
			} catch (Exception e) {
			}
		}
	}

	private void loadSubjects(String department, String year) {

		String[] columns = { "SUBJECTCODE" };
		String[] filters = { department, year };
		Cursor cursor = getContentResolver().query(
				LoginDBContentProvider.SUBJECT_CONTENT_URI, columns, null,
				filters, null);
		List<String> subjects = new ArrayList<String>();
		while (cursor != null && cursor.moveToNext()) {
			String code = cursor
					.getString(cursor.getColumnIndex("SUBJECTCODE"));
			subjects.add(code);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, subjects);
		subjectSpin.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		actionForMenuItemSelection(item);
		return super.onOptionsItemSelected(item);
	}

	private void actionForMenuItemSelection(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.export:
			Toast.makeText(this, "Exporting to ExcelSheet", Toast.LENGTH_SHORT)
					.show();
			String path = getBaseContext().getFilesDir().getAbsolutePath()
					+ "/AttendanceExport/" + "TodaysDate.csv";
			new LoadCSVToDB(getBaseContext(), path);
			break;
		}
	}
}
