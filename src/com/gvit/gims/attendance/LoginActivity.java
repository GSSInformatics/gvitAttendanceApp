package com.gvit.gims.attendance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gvit.gims.attendance.login.AndroidDatabaseManager;
import com.gvit.gims.attendance.login.LoginDBContentProvider;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 * 
 *        A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
		TextView tv = (TextView) findViewById(R.id.databaseViewer);

		tv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent dbmanager = new Intent(LoginActivity.this,
						AndroidDatabaseManager.class);
				startActivity(dbmanager);
			}
		});

		Button button = (Button) findViewById(R.id.showTable);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent dbmanager = new Intent(LoginActivity.this,
						AndroidDatabaseManager.class);
				startActivity(dbmanager);
			}
		});
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
		}
		if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
			showProgress(true);
			mAuthTask = new UserLoginTask(email, password);
			AsyncTask<Void, Void, Boolean> loginExecution = mAuthTask
					.execute((Void) null);
			try {
				boolean login = loginExecution.get().booleanValue();
				if (login && email.equals("superuser")) {
					Intent maintanance = new Intent(LoginActivity.this,
							Maintanance.class);
					startActivity(maintanance);
				} else if (login && !email.equals("superuser")) {

					Intent startApp = new Intent(LoginActivity.this,
							ClassSelection.class);
					startActivity(startApp);
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			String[] columns = { "USERNAME", "PASSWORD" };
			String[] filters = { mEmail, mPassword };
			Cursor loginCursor = getContentResolver().query(
					LoginDBContentProvider.LOGIN_CONTENT_URI, columns, null,
					filters, null);
			Map<String, String> userMap = new HashMap<String, String>();
			while (loginCursor != null && loginCursor.moveToNext()) {
				String user = loginCursor.getString(loginCursor
						.getColumnIndex("USERNAME"));
				String pass = loginCursor.getString(loginCursor
						.getColumnIndex("PASSWORD"));
				userMap.put(user, pass);
			}

			if (userMap.containsKey(mEmail)) {
				return userMap.get(mEmail).equals(mPassword);
			}
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
