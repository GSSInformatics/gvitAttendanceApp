/**
 * 
 */
package com.gvit.gims.attendance.maintanence;

import android.content.ContentResolver;
import android.database.Cursor;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;

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

	public LoadAttendanceListener(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	@Override
	public void Wsdl2CodeStartedRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Wsdl2CodeFinished(String methodName, Object Data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Wsdl2CodeFinishedWithException(Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Wsdl2CodeEndedRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attendanceButton:
			String columns[] = { "YEAR", "BRANCH", "SECTION", "REGNO",
					"PERIOD", "SUBJECT", "STATUS" };

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
			while (attendance.moveToNext()) {
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
					// TODO Auto-generated catch block
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
