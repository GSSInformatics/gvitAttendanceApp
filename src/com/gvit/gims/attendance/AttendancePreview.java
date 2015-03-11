package com.gvit.gims.attendance;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gvit.gims.attendance.Export.ExportToExcel;
import com.gvit.gims.attendance.dtos.ClassInformationDTO;
import com.gvit.gims.attendance.login.LoginDBContentProvider;
import com.gvit.gims.navigationdrawer.BaseActivity;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 * 
 * 
 */
public class AttendancePreview extends BaseActivity {

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_preview);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from

		set(navMenuTitles, navMenuIcons);

		final ClassInformationDTO classSelection = (ClassInformationDTO) getIntent()
				.getSerializableExtra("classSelectionDTO");
		String selectedAttendance = new StringBuilder()
				.append(classSelection.getDepartment())
				.append(" ,").append(classSelection.getYear()).append(" ,")
				.append(classSelection.getSection()).append(" ,")
				.append(classSelection.getPeriod())
				.append(" absentees are as follows").toString();
		TextView previewHead = (TextView) findViewById(R.id.previewHeader);
		previewHead.setText(selectedAttendance);

		ArrayList<Student> stringArrayListExtra = classSelection.getAbsentees();
		ListView absenteesListView = (ListView) findViewById(R.id.absenteesListView);
		absenteesListView.setAdapter(new ArrayAdapter<Student>(
				AttendancePreview.this, android.R.layout.simple_list_item_1,
				stringArrayListExtra));

		View confirmButton = findViewById(R.id.confirmButton);
		confirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						for(Student student:classSelection.getAbsentees()){
							ContentValues rowData = new ContentValues();
							rowData.put("ATTDATE", classSelection.getAttendanceDate());
							rowData.put("YEAR", classSelection.getYear());
							rowData.put("BRANCH", classSelection.getDepartment());
							rowData.put("SECTION", classSelection.getSection());
							rowData.put("REGNO", student.regno);
							rowData.put("PERIOD", classSelection.getPeriod());
							rowData.put("SUBJECT", classSelection.getSubject());
							getContentResolver().insert(
									LoginDBContentProvider.STATUS_CONTENT_URI,
									rowData);
						}
						ExportToExcel export = new ExportToExcel(
								classSelection, getBaseContext());
						export.exportDataToCSV("Attendance.csv");
					}
				}).start();
				Intent intent = new Intent(AttendancePreview.this,
						ClassSelection.class);
				startActivity(intent);
			}
		});
	}
}
