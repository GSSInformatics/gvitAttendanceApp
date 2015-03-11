package com.gvit.gims.attendance;

import java.util.Map;
import com.gvit.gims.attendance.login.DatabaseHelper;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class LoginDatabaseAdapter {

	static final String PROVIDER_NAME = "com.gvit.gims.attendance";
	static final String URL = "content://" + PROVIDER_NAME + "/login";
	static final Uri CONTENT_URI = Uri.parse(URL);
	static final String DATABASE_NAME = "attendance.db";
	static final int DATABASE_VERSION = 1;
//	public static final int NAME_COLUMN = 3;

	static final String DATABASE_CREATE = "create table " + "LOGIN" + "( "
			+ "ID integer primary key autoincrement,USERNAME text,"
			+ "PASSWORD text) ";

	public SQLiteDatabase db;
	private final Context context;
	private DatabaseHelper dbHelper;

	public LoginDatabaseAdapter(Context _context) {
		context = _context;
//		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null,
//				DATABASE_VERSION);

	}

	public LoginDatabaseAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntries1(Map<String, String> usersAndPass) {

		db.beginTransaction();
		String sql = "INSERT INTO LOGIN (NAME, PASSWORD) VALUES (?, ?)";
		SQLiteStatement stmt = db.compileStatement(sql);
		for (Map.Entry<String, String> userNPass : usersAndPass.entrySet()) {
			String userName = userNPass.getKey();
			String password = userNPass.getValue();
			stmt.bindString(1, userName);
			stmt.bindString(2, password);
			stmt.execute();
			stmt.clearBindings();
		}
		db.setTransactionSuccessful();
		db.endTransaction();

	}

}
