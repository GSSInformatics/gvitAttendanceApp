/**
 * 
 */
package com.gvit.gims.attendance.maintanence;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.Wsdl2Code.WebServices.Attendance.Attendance;
import com.Wsdl2Code.WebServices.Attendance.IWsdl2CodeEvents;
import com.Wsdl2Code.WebServices.Users.Subject;
import com.Wsdl2Code.WebServices.Users.VectorSubject;
import com.gvit.gims.attendance.R;
import com.gvit.gims.attendance.login.LoginDBContentProvider;

/**
 * @author admin
 *
 */
public class LoadSubjectsListener implements OnClickListener, IWsdl2CodeEvents {

	private ContentResolver contentResolver;
	private ProgressBar progressBar;
	private Button subjectsBtn;

	public LoadSubjectsListener(Activity context) {
		this.contentResolver = context.getContentResolver();
		progressBar = (ProgressBar) context.findViewById(R.id.subjectProgress);
		subjectsBtn = (Button) context.findViewById(R.id.subjectsButton);
	}

	@Override
	public void Wsdl2CodeStartedRequest() {
		progressBar.setVisibility(View.VISIBLE);
		subjectsBtn.setEnabled(false);

	}

	@Override
	public void Wsdl2CodeFinished(String methodName, Object Data) {
		VectorSubject subjectList = (VectorSubject) Data;
		for (Subject subject : subjectList) {
			ContentValues rowValue = new ContentValues();
			rowValue.put("YEAR", subject.year);
			rowValue.put("SEMESTER", subject.semester);
			rowValue.put("BRANCH", subject.branch);
			rowValue.put("SECTION", subject.section);
			rowValue.put("SUBJECTCODE", subject.subjectCode);
			getContentResolver().insert(
					LoginDBContentProvider.SUBJECT_CONTENT_URI, rowValue);
		}
		progressBar.setVisibility(View.GONE);
		subjectsBtn.setEnabled(true);

	}

	private ContentResolver getContentResolver() {
		return contentResolver;
	}

	@Override
	public void Wsdl2CodeFinishedWithException(Exception ex) {
		progressBar.setVisibility(View.GONE);
		subjectsBtn.setEnabled(true);
	}

	@Override
	public void Wsdl2CodeEndedRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.subjectsButton:
			Attendance subjectService = new Attendance(this);
			try {
				subjectService.getSubjectsAsync();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

}
