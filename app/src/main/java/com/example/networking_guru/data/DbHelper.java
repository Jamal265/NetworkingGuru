package com.example.networking_guru.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.networking_guru.Question2;

import java.util.ArrayList;
import java.util.List;

import static com.example.networking_guru.data.QuizContract.MovieEntry.KEY_ANSWER;
import static com.example.networking_guru.data.QuizContract.MovieEntry.KEY_ID;
import static com.example.networking_guru.data.QuizContract.MovieEntry.KEY_OPTA;
import static com.example.networking_guru.data.QuizContract.MovieEntry.KEY_OPTB;
import static com.example.networking_guru.data.QuizContract.MovieEntry.KEY_OPTC;
import static com.example.networking_guru.data.QuizContract.MovieEntry.KEY_QUES;
import static com.example.networking_guru.data.QuizContract.MovieEntry.TABLE_QUEST;

public class DbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "triviaQuiz";
	// tasks table name

	private SQLiteDatabase dbase;
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		dbase=db;
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		db.execSQL(sql);		
		addQuestion2s();
		//db.close();
	}
	private void addQuestion2s()
	{
		Question2 q1=new Question2("Is telnet a secure way of login into a cisco switch?","True", "False", "All of the above", "False");
		this.addQuestion(q1);
		Question2 q2=new Question2("What does the term STP stand for?", "Spanning-text Protocol", "Standard-tree Protocol", "Spanning-tree Protocol", "Spanning-tree Protocol");
		this.addQuestion(q2);
		Question2 q3=new Question2("What does the term TCP stand for?","Transfer Communication Protocol", "Transmission Control Protocol","Transfer Control Protocol", "Transmission Control Protocol" );
		this.addQuestion(q3);
		Question2 q4=new Question2("Which command would you use in the CLI at User mode to enter Privileged EXEC mode?", "Enable", "Admin", "Disable","Enable");
		this.addQuestion(q4);
		Question2 q5=new Question2("Which of the following commands correctly sets the physical speed of a serial interface to 64Kbps?","Router1(config-if)#clockrate 64","Router1(config-if)#bandwidth 64000","Router1(config-if)#clockrate 64000","Router1(config-if)#clockrate 64000");
		this.addQuestion(q5);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
		// Create tables again
		onCreate(db);
	}
	// Adding new question
	public void addQuestion(Question2 quest) {
		//SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_QUES, quest.getQUESTION()); 
		values.put(KEY_ANSWER, quest.getANSWER());
		values.put(KEY_OPTA, quest.getOPTA());
		values.put(KEY_OPTB, quest.getOPTB());
		values.put(KEY_OPTC, quest.getOPTC());
		// Inserting Row
		dbase.insert(TABLE_QUEST, null, values);		
	}
	public List<Question2> getAllQuestions() {
		List<Question2> quesList = new ArrayList<Question2>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		dbase=this.getReadableDatabase();
		Cursor cursor = dbase.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Question2 quest = new Question2();
				quest.setID(cursor.getInt(0));
				quest.setQUESTION(cursor.getString(1));
				quest.setANSWER(cursor.getString(2));
				quest.setOPTA(cursor.getString(3));
				quest.setOPTB(cursor.getString(4));
				quest.setOPTC(cursor.getString(5));
				quesList.add(quest);
			} while (cursor.moveToNext());
		}
		// return quest list
		return quesList;
	}
	public int rowcount()
	{
		int row=0;
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		row=cursor.getCount();
		return row;
	}
}
