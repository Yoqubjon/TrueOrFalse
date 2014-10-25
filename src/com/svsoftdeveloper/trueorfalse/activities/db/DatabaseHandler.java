package com.svsoftdeveloper.trueorfalse.activities.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.svsoftdeveloper.trueorfalse.R;
import com.svsoftdeveloper.trueorfalse.files.FileWorker;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	final String TAG = "States";
	
	
	
	private static final int DATABASE_VERSION = 8;
	private static final String DATABASE_NAME = "trueorfalse";

	private static final String TABLE_QUESTIONES = "questions";

	private static final String KEY_QUESTIONES_ID = "_id";
	//private static final int ID_QUESTIONES_COLUMN = 0;

	public static final String QUESTIONES_TEXT = "text";
	//private static final int QUESTIONES_TEXT_COLUMN = 1;

	public static final String QUESTIONES_ANSWER = "answer";
	//private static final int QUESTIONES_ANSWER_COLUMN = 2;

	public static final String QUESTIONES_EXPLANATION = "explanation";
	//public static final int QUESTIONES_EXPLANATION_COLUMN = 3;
	
	public static final String QUESTIONES_USED = "used";
	//public static final int QUESTIONES_USED_COLUMN = 4;
	
	private static final String DB_QUESTIONS_TABLE_CREATE = "create table "
			+ TABLE_QUESTIONES + " ( " 
			+ KEY_QUESTIONES_ID + " integer primary key autoincrement, "
			+ QUESTIONES_TEXT + " text, "
			+ QUESTIONES_ANSWER + " integer, "
			+ QUESTIONES_EXPLANATION + " text, " 
			+ QUESTIONES_USED + " integer" + ");";
	
	
    
	private Context dbContext;
	private SQLiteDatabase myDB;
	
	
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbContext = context;
    } 
  
    // Creating Tables 
    @Override 
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DB_QUESTIONS_TABLE_CREATE);

    } 
  
    // Upgrading database 
    @Override 
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed 
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONES);
  
        // Create tables again 
        onCreate(db);
    } 
    
    public DatabaseHandler open() throws SQLException {

		try {

			myDB = this.getWritableDatabase();

		} catch (SQLiteException e) {
			myDB = this.getReadableDatabase();

		}
		return this;
	}
    
    //---------File reader------------//
    public List<String> getQuestionLinesList(){
		
		String line;
		List<String> lines = new ArrayList<String>();
		Log.d(TAG, " Trying to get resourses");//-------------------------------
		Resources res = dbContext.getResources();
		
		try { 
			BufferedReader buffer =  new BufferedReader(new InputStreamReader(res.openRawResource(R.raw.questions_list)));
		 
		    while((line = buffer.readLine()) != null) {
		    	lines.add(line);
		    	Log.d(TAG, line);
		    } 
		} 
		catch(IOException e) {
		    e.printStackTrace();
		}
		
		return lines;
	}
    
    public List<Question> questionLineParser(List<String> list){
		
    	List<Question> result = new ArrayList<Question>();
		
    	for(int i = 0; i < list.size(); i++){
    		String[] array = list.get(i).split("/");
    		if(array.length < 3){
    			Log.d(TAG, "Size of array: " + array.length);
    			result.add(new Question(0, array[0], array[1], "", "0"));
    			Log.d(TAG, array[0] + " " + array[1] + " " + "0");
    		}
    		else{
    			Log.d(TAG, "Size of array: " + array.length);
    			result.add(new Question(0, array[0], array[1], array[2], "0"));
    			Log.d(TAG, array[0] + " " + array[1] + " " + array[2] + "0");
    		}
    	}
		return result;
	}

	
	
  	public void fillDB(){
  			
  		FileWorker fileWorker = new FileWorker(dbContext);
  			
  		List<Question> resultList = fileWorker.questionLineParser(fileWorker.getQuestionLinesList());
  			
  		for (int k = 0; k < resultList.size(); k++) {
  			addQuestion(resultList.get(k));
  	       }
  	}
  		//--------------------------------//
  	
  	
  	
  
    /** 
     * All CRUD(Create, Read, Update, Delete) Operations 
     */ 
  
    // Adding new contact 
    public void addQuestion(Question question) {
    	
        SQLiteDatabase db = this.getWritableDatabase();
  
        ContentValues values = new ContentValues();
        values.put(QUESTIONES_TEXT, question.getText());
		Log.d(TAG, "Text: " + question.getText());
		values.put(QUESTIONES_ANSWER, question.getAnswer());
		Log.d(TAG, "Answer: " + question.getAnswer());
		values.put(QUESTIONES_EXPLANATION, question.getExplanation());
		Log.d(TAG, "Explanation: " + question.getExplanation());
		values.put(QUESTIONES_USED, question.getUsed());
		Log.d(TAG, "Used flag: " + question.getUsed());
  
        // Inserting Row 
        db.insert(TABLE_QUESTIONES, null, values);
        db.close(); // Closing database connection
    } 
  
    // Getting single contact 
    public Question getQuestion(int id) {
    	
        SQLiteDatabase db = this.getReadableDatabase();
  
        Cursor cursor = db.query(TABLE_QUESTIONES, new String[] { KEY_QUESTIONES_ID,
        		QUESTIONES_TEXT, QUESTIONES_ANSWER,  QUESTIONES_EXPLANATION, QUESTIONES_USED}, KEY_QUESTIONES_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
  
        Question question = new Question(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return 
        return question;
    } 
      
    // Getting All Questions 
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();
        // Select All Query 
        String selectQuery = "SELECT * FROM " + TABLE_QUESTIONES;
  
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
  
        // looping through all rows and adding to list 
        if (cursor.moveToFirst()) {
            do { 
            	Question question = new Question(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                // Adding contact to list 
            	questionList.add(question);
            } while (cursor.moveToNext());
        } 
        // return contact list 
        return questionList;
    } 
  
    // Updating single contact 
    public int updateQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
  
        ContentValues values = new ContentValues();
        values.put(QUESTIONES_TEXT, question.getText());
        values.put(QUESTIONES_ANSWER, question.getAnswer());
        values.put(QUESTIONES_EXPLANATION, question.getExplanation());
        values.put(QUESTIONES_USED, question.getUsed());
  
        // updating row 
        return db.update(TABLE_QUESTIONES, values, KEY_QUESTIONES_ID + " = ?", new String[] { String.valueOf(question.getId())});
    } 
  
    // Deleting single contact 
    public void deleteQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUESTIONES, KEY_QUESTIONES_ID + " = ?",
                new String[] { String.valueOf(question.getId()) });
        db.close(); // Closing database connection
    } 
  
  
    // Getting contacts Count 
    public int getQuestionsCount() {
    	
        String countQuery = "SELECT * FROM " + TABLE_QUESTIONES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
  
        // return count 
        return count;
    } 
  
}
