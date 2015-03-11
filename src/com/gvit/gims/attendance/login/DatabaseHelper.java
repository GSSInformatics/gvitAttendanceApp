package com.gvit.gims.attendance.login;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public DatabaseHelper(Context context) {
		super(context, LoginDBContentProvider.DATABASE_NAME, null,
				LoginDBContentProvider.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase _db) {
		_db.execSQL(LoginDBContentProvider.CREATE_LOGIN_TABLE);

		ContentValues supercredential = new ContentValues();
		supercredential.put("USERNAME", "superuser");
		supercredential.put("PASSWORD", "supernova");
		_db.insert(LoginDBContentProvider.TABLE_NAME_LOGIN, "", supercredential);

		supercredential.put("USERNAME", "Ajay");
		supercredential.put("PASSWORD", "supernova");
		_db.insert(LoginDBContentProvider.TABLE_NAME_LOGIN, "", supercredential);

		_db.execSQL(LoginDBContentProvider.CREATE_STATUS_TABLE);

		_db.execSQL(LoginDBContentProvider.CREATE_STUDENT_TABLE);
		ContentValues students = new ContentValues();
		students.put("YEAR", "I");
		students.put("BRANCH", "CSE");
		students.put("SECTION", "B");
		students.put("REGNO", "1");
		students.put("NAME", "BOB thebuilder");
		_db.insert(LoginDBContentProvider.TABLE_NAME_STUDENT, "", students);

		_db.execSQL(LoginDBContentProvider.CREATE_SUBJECT_TABLE);
		ContentValues subjects = new ContentValues();
		subjects.put("YEAR", "1");
		subjects.put("BRANCH", "CSE");
		subjects.put("SUBJECTCODE", "MAT-1");
		_db.insert(LoginDBContentProvider.TABLE_NAME_SUBJECT, "", subjects);

	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		// Log the version upgrade.
		Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to "
				+ _newVersion + ", which will destroy all old data");
		// _db.execSQL("DROP TABLE IF EXISTS " +
		// LoginDBContentProvider.TABLE_NAME_LOGIN);
		// _db.execSQL("DROP TABLE IF EXISTS " +
		// LoginDBContentProvider.TABLE_NAME_ABSENTEES);
		onCreate(_db);
	}

	public ArrayList<Cursor> getData(String Query) {
		// get writable database
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		String[] columns = new String[] { "mesage" };
		// an array list of cursor to save two cursors one has results from the
		// query
		// other cursor stores error message if any errors are triggered
		ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
		MatrixCursor Cursor2 = new MatrixCursor(columns);
		alc.add(null);
		alc.add(null);

		try {
			String maxQuery = Query;
			// execute the query results will be save in Cursor c
			Cursor c = sqlDB.rawQuery(maxQuery, null);

			// add value to cursor2
			Cursor2.addRow(new Object[] { "Success" });

			alc.set(1, Cursor2);
			if (null != c && c.getCount() > 0) {

				alc.set(0, c);
				c.moveToFirst();

				return alc;
			}
			return alc;
		} catch (SQLException sqlEx) {
			Log.d("printing exception", sqlEx.getMessage());
			// if any exceptions are triggered save the error message to cursor
			// an return the arraylist
			Cursor2.addRow(new Object[] { "" + sqlEx.getMessage() });
			alc.set(1, Cursor2);
			return alc;
		} catch (Exception ex) {

			Log.d("printing exception", ex.getMessage());

			// if any exceptions are triggered save the error message to cursor
			// an return the arraylist
			Cursor2.addRow(new Object[] { "" + ex.getMessage() });
			alc.set(1, Cursor2);
			return alc;
		}

	}
}
