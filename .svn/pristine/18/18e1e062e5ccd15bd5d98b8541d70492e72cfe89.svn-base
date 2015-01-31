/**
 * 
 */
package com.gvit.gims.attendance.login;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class CredentialSyncAdapter extends AbstractThreadedSyncAdapter {

	private ContentResolver mContentResolver;

	public CredentialSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		mContentResolver = context.getContentResolver();
		mContentResolver.notifyChange(LoginDBContentProvider.LOGIN_CONTENT_URI,
				null, false);
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
//		// Creating or opening the database
//				for (int i = 0; i < 5; i++) {
//					ContentValues loginData = new ContentValues();
//					loginData.put("USERNAME", "user" + i);
//					loginData.put("PASSWORD", "pass" + i);
//					try {
//						provider.insert(LoginDBContentProvider.CONTENT_URI,
//								loginData);
//					} catch (RemoteException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
	}
	
	

}
