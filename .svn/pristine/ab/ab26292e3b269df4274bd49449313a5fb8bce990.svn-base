/**
 * 
 */
package com.gvit.gims.attendance.login;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class AuthenticatorService extends Service {
	  private Authenticator authenticator;
	  @Override
	  public void onCreate() {
	    authenticator = new Authenticator(this);
	  }
	 
	  /*
	  * When the system binds to this Service to make the RPC call
	  * return the authenticator’s IBinder.
	  */
	  @Override
	  public IBinder onBind(Intent intent) {
	    return authenticator.getIBinder();
	  }
	}