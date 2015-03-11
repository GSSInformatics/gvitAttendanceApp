/**
 * 
 */
package com.gvit.gims.attendance.maintanence;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.Wsdl2Code.WebServices.Attendance.Attendance;
import com.Wsdl2Code.WebServices.Attendance.IWsdl2CodeEvents;
import com.Wsdl2Code.WebServices.Attendance.VectorString;
import com.gvit.gims.attendance.R;
import com.gvit.gims.attendance.login.LoginDBContentProvider;

/**
 * @author admin
 *
 */
public class LoadAttendanceListener implements OnClickListener,
		IWsdl2CodeEvents {

	private ContentResolver contentResolver;
	private Button attendanceBtn;
	private ProgressBar syncProgress;

	public LoadAttendanceListener(Activity context) {
		this.contentResolver = context.getContentResolver();
		attendanceBtn = (Button) context.findViewById(R.id.syncAttButton);
		syncProgress = (ProgressBar) context.findViewById(R.id.progressBar4);
	}

	@Override
	public void Wsdl2CodeStartedRequest() {
		attendanceBtn.setEnabled(false);
		syncProgress.setVisibility(View.VISIBLE);
	}

	@Override
	public void Wsdl2CodeFinished(String methodName, Object Data) {
		syncProgress.setVisibility(View.GONE);
		attendanceBtn.setEnabled(true);
	}

	@Override
	public void Wsdl2CodeFinishedWithException(Exception ex) {
		syncProgress.setVisibility(View.GONE);
		attendanceBtn.setEnabled(true);
	}

	@Override
	public void Wsdl2CodeEndedRequest() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.syncAttButton:
			String columns[] = { "YEAR", "BRANCH", "SECTION", "REGNO",
					"PERIOD", "SUBJECT" };

			String wheredate = "ATTDATE";
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			String attDate = new StringBuilder().append(today.monthDay)
					.append("/").append(today.month + 1).append("/")
					.append(today.year).toString();

			String[] selectionArgs = { attDate };
			Cursor attendance = getContentResolver().query(
					LoginDBContentProvider.STATUS_CONTENT_URI, columns,
					wheredate, selectionArgs, null);
			Attendance webserviceCall = new Attendance(this);
			while (attendance != null && attendance.moveToNext()) {
				String year = attendance.getString(attendance
						.getColumnIndex("YEAR"));
				String branch = attendance.getString(attendance
						.getColumnIndex("BRANCH"));
				String section = attendance.getString(attendance
						.getColumnIndex("SECTION"));
				String regno = attendance.getString(attendance
						.getColumnIndex("REGNO"));
				String period = attendance.getString(attendance
						.getColumnIndex("PERIOD"));
				String subject = attendance.getString(attendance
						.getColumnIndex("SUBJECT"));
				VectorString absente = new VectorString();
				absente.add(regno);
				try {
					webserviceCall.persistAttendanceAsync(attDate, branch,
							branch, year, section, period, subject, absente);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	private ContentResolver getContentResolver() {
		return contentResolver;
	}

}
