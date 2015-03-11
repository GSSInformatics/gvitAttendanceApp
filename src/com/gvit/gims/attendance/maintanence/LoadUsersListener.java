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

import com.Wsdl2Code.WebServices.Attendance.Attendance;
import com.Wsdl2Code.WebServices.Attendance.IWsdl2CodeEvents;
import com.Wsdl2Code.WebServices.Users.Users;
import com.Wsdl2Code.WebServices.Users.VectorUsers;
import com.gvit.gims.attendance.R;
import com.gvit.gims.attendance.login.LoginDBContentProvider;

/**
 * @author admin
 *
 */
public class LoadUsersListener implements OnClickListener, IWsdl2CodeEvents {

	private ContentResolver contentResolver;
	private Button userBtn;
	private View userProgress;

	public LoadUsersListener(Activity context) {
		this.contentResolver = context.getContentResolver();
		userBtn = (Button) context.findViewById(R.id.usersButton);
		userProgress = context.findViewById(R.id.usersProgress);
	}

	@Override
	public void Wsdl2CodeStartedRequest() {
		userProgress.setVisibility(View.VISIBLE);
		userBtn.setEnabled(false);
	}

	@Override
	public void Wsdl2CodeFinished(String methodName, Object Data) {
		VectorUsers userList = (VectorUsers) Data;
		for (Users user : userList) {
			ContentValues rowValue = new ContentValues();
			rowValue.put("USERNAME", user.name);
			rowValue.put("PASSWORD", user.password);
			getContentResolver().insert(
					LoginDBContentProvider.LOGIN_CONTENT_URI, rowValue);
		}
		userProgress.setVisibility(View.GONE);
		userBtn.setEnabled(true);

	}

	private ContentResolver getContentResolver() {
		return contentResolver;
	}

	@Override
	public void Wsdl2CodeFinishedWithException(Exception ex) {
		userProgress.setVisibility(View.GONE);
		userBtn.setEnabled(true);
	}

	@Override
	public void Wsdl2CodeEndedRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.usersButton:
			Attendance webservice = new Attendance(this);
			try {
				webservice.getUsersAsync();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

	}

}
