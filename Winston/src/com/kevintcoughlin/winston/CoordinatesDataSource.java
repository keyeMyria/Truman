package com.kevintcoughlin.winston;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class CoordinatesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_DATE,
			MySQLiteHelper.COLUMN_LONGITUDE, 
			MySQLiteHelper.COLUMN_LATITUDE };

	public CoordinatesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Coordinate createCoordinate(double latitude, double longitude) {
		ContentValues values = new ContentValues();
		
		// Get time of creation and format to date and time
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String creation = df.format(date);
		
		// Put attributes in Content Values
		values.put(MySQLiteHelper.COLUMN_DATE, creation);
		values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
		values.put(MySQLiteHelper.COLUMN_LONGITUDE, longitude);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_COORDINATES, null, values);
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_COORDINATES,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		
		cursor.moveToFirst();
		
		Coordinate newCoordinate = cursorToCoordinate(cursor);
		
		cursor.close();
		return newCoordinate;
	}

	public void deleteCoordinate(Coordinate coordinate) {
		long id = coordinate.getId();
		System.out.println("Coordinate deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_COORDINATES, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void getColumnNames(){
		Cursor ti = database.rawQuery("PRAGMA table_info(coordinates)", null);
	    if ( ti.moveToFirst() ) {
	        do {
	            System.out.println("Column: " + ti.getString(1));
	        } while (ti.moveToNext());
	    }
	}

	public List<Coordinate> getAllCoordinates() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_COORDINATES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Coordinate coordinate = cursorToCoordinate(cursor);
			coordinates.add(coordinate);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return coordinates;
	}

	private Coordinate cursorToCoordinate(Cursor cursor) {
		Coordinate coordinate = new Coordinate();
		coordinate.setId(cursor.getLong(0));
		coordinate.setDate(cursor.getString(1));
		coordinate.setLongitude(cursor.getInt(2));
		coordinate.setLatitude(cursor.getInt(3));
		
		return coordinate;
	}
} 
