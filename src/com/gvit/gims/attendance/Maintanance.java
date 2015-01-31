package com.gvit.gims.attendance;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import com.Wsdl2Code.WebServices.Attendance.IWsdl2CodeEvents;
import com.Wsdl2Code.WebServices.Users.Users;
import com.Wsdl2Code.WebServices.Users.VectorUsers;
import com.gvit.gims.attendance.login.LoginDBContentProvider;
import com.gvit.gims.attendance.maintanence.LoadAttendanceListener;
import com.gvit.gims.attendance.maintanence.LoadStudentsListener;
import com.gvit.gims.attendance.maintanence.LoadSubjectsListener;
import com.gvit.gims.attendance.maintanence.LoadUsersListener;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class Maintanance extends Activity implements OnClickListener,
		IWsdl2CodeEvents {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintanance);
		registerListeners();
	}

	private void registerListeners() {
		findViewById(R.id.returnButton).setOnClickListener(this);
		
		ProgressBar stuProgBar = (ProgressBar) findViewById(R.id.stuProgressBar);
		findViewById(R.id.studentsButton).setOnClickListener(new LoadStudentsListener(getContentResolver(), stuProgBar));
		
		findViewById(R.id.usersButton).setOnClickListener(new LoadUsersListener(getContentResolver()));
		findViewById(R.id.subjectsButton).setOnClickListener(new LoadSubjectsListener(getContentResolver()));
		findViewById(R.id.attendanceButton).setOnClickListener(new LoadAttendanceListener(getContentResolver()));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.returnButton:
			Intent returnToLogin = new Intent(Maintanance.this,
					LoginActivity.class);
			startActivity(returnToLogin);
			break;
		default:
			break;
		}
	}

	@Override
	public void Wsdl2CodeStartedRequest() {
	}

	@Override
	public void Wsdl2CodeFinished(String methodName, Object Data) {
		
	}

	@Override
	public void Wsdl2CodeFinishedWithException(Exception ex) {

	}

	@Override
	public void Wsdl2CodeEndedRequest() {
		// TODO Auto-generated method stub

	}
}
