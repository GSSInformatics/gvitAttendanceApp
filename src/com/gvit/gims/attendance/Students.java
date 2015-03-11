package com.gvit.gims.attendance;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gvit.gims.attendance.dtos.ClassInformationDTO;
import com.gvit.gims.attendance.login.LoginDBContentProvider;
import com.gvit.gims.navigationdrawer.BaseActivity;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class Students extends BaseActivity {

	private final List<Student> absentees = new ArrayList<>();
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_students);
		List<Student> studentsFromDB = loadStudentsFromDB();
		loadStudentList(studentsFromDB);
		createLoadStudentsButton();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from

		set(navMenuTitles, navMenuIcons);
	}

	private void createLoadStudentsButton() {
		View loadStundButton = findViewById(R.id.preview);
		loadStundButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ClassInformationDTO classSelectionDTO = (ClassInformationDTO) getIntent()
						.getSerializableExtra("classSelectionDTO");
				classSelectionDTO.setAbsentees((ArrayList<Student>) absentees);

				Intent intent = new Intent(Students.this,
						AttendancePreview.class);

				// 3. or you can add data to a bundle
				Bundle extras = new Bundle();
				extras.putSerializable("classSelectionDTO", classSelectionDTO);
				// 4. add bundle to intent
				intent.putExtras(extras);

				// 5. start the activity
				startActivity(intent);
			}
		});
	}

	private List<Student> loadStudentsFromDB() {
		ClassInformationDTO classInfo = (ClassInformationDTO) getIntent()
				.getSerializableExtra("classSelectionDTO");
		String[] columns = { "REGNO", "NAME" };
		String[] filters = { classInfo.getYear(), classInfo.getDepartment(),
				classInfo.getSection() };
		Cursor cursor = getContentResolver().query(
				LoginDBContentProvider.STUDENT_CONTENT_URI, columns, null,
				filters, "REGNO");
		List<Student> students = new ArrayList<Student>();
		while (cursor.moveToNext()) {
			String fName = cursor.getString(cursor.getColumnIndex("NAME"));
			String regno = cursor.getString(cursor.getColumnIndex("REGNO"));
			Student s = new Student(fName, regno);
			students.add(s);
		}

		return students;

	}

	private List<Student> loadStudentList(List<Student> students) {
		CustomList adapter = new CustomList(Students.this,
				students.toArray(new Student[students.size()]),
				R.drawable.happy);

		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				TextView txtTitle = (TextView) view
						.findViewById(R.id.attendStatus);
				TextView studentIDView = (TextView) view
						.findViewById(R.id.studentsID);
				TextView firstNameView = (TextView) view
						.findViewById(R.id.firstName);
				String regno = (String) studentIDView.getText();
				Student student = new Student((String) firstNameView.getText(),
						regno);
				
				CharSequence status = txtTitle.getText();
				if (status.equals(getResources().getString(R.string.present))) {
					txtTitle.setText(getResources().getString(R.string.absent));
					absentees.add(student);
					Toast.makeText(Students.this, regno + " is absent",
							Toast.LENGTH_SHORT).show();
				} else {
					txtTitle.setText(getResources().getString(R.string.present));
					absentees.remove(student);
				}
			}

		});
		return absentees;
	}
}
