package com.gvit.gims.attendance.login;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class LoginDBContentProvider extends ContentProvider {
	static final String TABLE_NAME_LOGIN = "LOGIN";
	static final String TABLE_NAME_ABSENTEES = "ABSENTEES";
	static final String TABLE_NAME_STUDENT = "STUDENT";
	public static final String TABLE_NAME_SUBJECT = "SUBJECT";

	static final String PROVIDER_NAME = "com.gvit.gims.attendance.login";
	
	static final String LOGIN_URL = "content://" + PROVIDER_NAME + "/LOGIN";
	public static final Uri LOGIN_CONTENT_URI = Uri.parse(LOGIN_URL);
	
	static final String STATUS_URL = "content://" + PROVIDER_NAME + "/STATUS";
	public static final Uri STATUS_CONTENT_URI = Uri.parse(STATUS_URL);

	static final String STUDENT_URL = "content://" + PROVIDER_NAME + "/STUDENT";
	public static final Uri STUDENT_CONTENT_URI = Uri.parse(STUDENT_URL);
	
	static final String SUBJECT_URL = "content://" + PROVIDER_NAME + "/SUBJECT";
	public static final Uri SUBJECT_CONTENT_URI = Uri.parse(SUBJECT_URL);
	
	static final String DATABASE_NAME = "attendance.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 3;
	static final String CREATE_LOGIN_TABLE = "create table " + TABLE_NAME_LOGIN
			+ "( " + "ID integer primary key autoincrement,USERNAME text,"
			+ "PASSWORD text) ";

	static final String CREATE_STATUS_TABLE = "create table "
			+ TABLE_NAME_ABSENTEES + "( "
			+ "ID integer primary key autoincrement,ATTDATE text,"
			+ "YEAR text, BRANCH text, SECTION text,"
			+ "REGNO text, PERIOD text, SUBJECT text) ";
	
	static final String CREATE_STUDENT_TABLE =  "create table "
			+ TABLE_NAME_STUDENT + "( "
			+ "ID integer primary key autoincrement,"
			+ "YEAR text, BRANCH text, SECTION text,"
			+ "REGNO text, NAME text) ";
	
	static final String CREATE_SUBJECT_TABLE =  "create table "
			+ TABLE_NAME_SUBJECT + "( "
			+ "ID integer primary key autoincrement,"
			+ "YEAR text, BRANCH text, SECTION text,"
			+ "SEMESTER text, SUBJECTCODE text) ";
	
	private static UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME_LOGIN, 1);
		uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME_ABSENTEES, 2);
		uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME_STUDENT, 3);
	}

	private SQLiteDatabase db;

	@Override
	public int delete(Uri uri, String selection, String[] selecArgs) {
		getContext().getContentResolver().notifyChange(uri, null);
		return db.delete(TABLE_NAME_LOGIN, selection, selecArgs);
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		Uri _uri = null;
		switch (uriMatcher.match(uri)) {
		case 1:
			long rowID = db.insert(TABLE_NAME_LOGIN, null, values);

			if (rowID > 0) {
				_uri = ContentUris.withAppendedId(uri, rowID);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			break;
		case 2:
			long row = db.insert(TABLE_NAME_ABSENTEES, null, values);

			if (row > 0) {
				_uri = ContentUris.withAppendedId(uri, row);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			break;
		case 3:
			long studentRow = db.insert(TABLE_NAME_STUDENT, null, values);

			if (studentRow > 0) {
				_uri = ContentUris.withAppendedId(uri, studentRow);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			break;
		case 4: 
			long subjectRow = db.insert(TABLE_NAME_SUBJECT, null, values);

			if (subjectRow > 0) {
				_uri = ContentUris.withAppendedId(uri, subjectRow);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			break;
		}
		return _uri;

	}

	@Override
	public boolean onCreate() {
		DatabaseHelper dbHelper = new DatabaseHelper(getContext(),
				DATABASE_NAME, null, DATABASE_VERSION);
		/**
		 * Create a write able database which will trigger its creation if it
		 * doesn't already exist.
		 */
		db = dbHelper.getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch (uriMatcher.match(uri)) {
		case 1:
			SQLiteQueryBuilder getUsers = new SQLiteQueryBuilder();
			getUsers.setTables(TABLE_NAME_LOGIN);
			return getUsers.query(db, null, null, null, null, null, sortOrder);
		case 2:
			SQLiteQueryBuilder loadAttendance = new SQLiteQueryBuilder();
			loadAttendance.setTables(TABLE_NAME_ABSENTEES);
			loadAttendance.appendWhere(selection + "=?");
			return loadAttendance.query(db, projection, selection,
					selectionArgs, null, null, sortOrder);
		
		case 3:
			SQLiteQueryBuilder loadStudents = new SQLiteQueryBuilder();
			loadStudents.setTables(TABLE_NAME_STUDENT);
			String select = "YEAR=? AND BRANCH=? AND SECTION=?";
			return loadStudents.query(db, projection, select, selectionArgs, null, null, sortOrder);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
