package com.svsoftdeveloper.trueorfalse.activities.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class MyDBAdapter {
	
	private static final int DATABASE_VESION = 1;
	private static final String DATABASE_NAME = "trueorfalse.db";

	private static final String DATABASE_TABLE_QUESTIONES = "questions";

	private static final String KEY_QUESTIONES_ID = "_id";
	private static final int ID_QUESTIONES_COLUMN = 0;

	public static final String QUESTIONES_TEXT = "text";
	private static final int QUESTIONES_TEXT_COLUMN = 1;

	public static final String QUESTIONES_ANSWER = "answer";
	private static final int QUESTIONES_ANSWER_COLUMN = 2;

	public static final String QUESTIONES_EXPLANATION = "explanation";
	public static final int QUESTIONES_EXPLANATION_COLUMN = 3;
	
	public static final String QUESTIONES_USED = "used";
	public static final int QUESTIONES_USED_COLUMN = 4;
	
	
	private static final String DB_QUESTIONS_TABLE_CREATE = "create table "
			+ DATABASE_TABLE_QUESTIONES + " ( " 
			+ KEY_QUESTIONES_ID + " integer primary key autoincrement, "
			+ QUESTIONES_TEXT + " text, "
			+ QUESTIONES_ANSWER + " integer, "
			+ QUESTIONES_EXPLANATION + " text, " 
			+ QUESTIONES_USED + " integer" + ");";
	
	
	
	private MyDBHelper dBHelper;
	private SQLiteDatabase db;
	private final Context context;

	public MyDBAdapter(Context _context) {
		context = _context;
		dBHelper = new MyDBHelper(context, DATABASE_NAME, null, DATABASE_VESION);
	}

	public MyDBAdapter open() throws SQLException {

		try {
			db = dBHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			db = dBHelper.getReadableDatabase();
		}
		return this;
	}

	public void close() {
		if (db != null) {
			db.close();
		}
	}

	public Cursor getAllEntries() {
		Cursor mCursor = db.query(DATABASE_TABLE_QUESTIONES, null, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public long addMessageToDB(Question question) {

		long result;
		ContentValues cv = new ContentValues();
		cv.put(QUESTIONES_TEXT, question.getText());
		cv.put(QUESTIONES_ANSWER, question.getAnswer());
		cv.put(QUESTIONES_EXPLANATION, question.getExplanation());
		cv.put(QUESTIONES_USED, question.getUsed());

		db.beginTransaction();
	    try {
	    	result = db.insert(DATABASE_TABLE_QUESTIONES, null, cv);
	      db.setTransactionSuccessful();
	    } finally {
	      db.endTransaction();
	    }
	    return result;
	}

	public int getNumberOfMessages() {

		return getAllEntries().getCount();
	}
	
	public void clearHistory() {

		db.beginTransaction();
		try {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_QUESTIONES);
			db.execSQL(DB_QUESTIONS_TABLE_CREATE);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	private class MyDBHelper extends SQLiteOpenHelper {

		public MyDBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.beginTransaction();
			try {
				_db.execSQL(DB_QUESTIONS_TABLE_CREATE);
				_db.setTransactionSuccessful();
			} finally {
				_db.endTransaction();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			_db.beginTransaction();
			try {
				_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_QUESTIONES);
				_db.setTransactionSuccessful();
			} finally {
				_db.endTransaction();
			}
			onCreate(_db);
		}
	}

}
