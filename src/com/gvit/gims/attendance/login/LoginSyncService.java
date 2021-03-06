package com.gvit.gims.attendance.login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class LoginSyncService extends Service {

	private static CredentialSyncAdapter syncAdapter = null;
	// Object to use as a thread-safe lock
	private static final Object syncAdapterLock = new Object();

	@Override
	public void onCreate() {
		super.onCreate();
		synchronized (syncAdapterLock) {
			if (syncAdapter == null) {
				syncAdapter = new CredentialSyncAdapter(
						getApplicationContext(), true);
			}
		}
	}

	 @Override
	 public IBinder onBind(Intent arg0) {
		 return syncAdapter.getSyncAdapterBinder();
	 }

}
