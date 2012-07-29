package com.kevintcoughlin.winston;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_COORDINATES = "coordinates";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";

	private static final String DATABASE_NAME = "coordinates.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE "
					+ TABLE_COORDINATES+" ("
					+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ COLUMN_DATE +" TEXT NOT NULL, "
					+ COLUMN_LATITUDE + " DOUBLE NOT NULL, "
					+ COLUMN_LONGITUDE + " DOUBLE NOT NULL);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COORDINATES);
		onCreate(db);
	}

}
