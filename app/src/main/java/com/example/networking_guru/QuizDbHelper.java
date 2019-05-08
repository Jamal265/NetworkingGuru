package com.example.networking_guru;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.networking_guru.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    //Creating database.
    private static final String DATABASE_NAME ="Networking_Guru.db";
    private static final int DATABASE_VERSION =1;

    private  SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String  SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    //Drops if database exists when the user presses start quiz.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    /*
    * This block is filling the questions in the database if you want to update or add questions
    * this the section to change.
    * */
    private void fillQuestionsTable(){
        Question q1 = new Question("What do you understand by the term DHCP ?", "Dynamic Host Configuration Protocol", "Decimal High Cost Protocol", "Disk Host Carrier protocol",1);
        addQuestion(q1);

        Question q2 = new Question("Which of the following is a Routing protocol ?", "VLAN", "OSPF", "STP",2);
        addQuestion(q2);

        Question q3 = new Question("How do you save a command on a Cisco Router ?", "save-commands", "copy-commands", "copy run-start",3);
        addQuestion(q3);

        Question q4 = new Question("Which Routing Protocol has a faster Convergence ?", "OSPF", "RIP", "HSRP",1);
        addQuestion(q4);

        Question q5 = new Question("What do you understand by the term HSRP", "Hot Standby Router Private", "Hot Standby-Routing Protocol", "Host Standby Router Protocol",2);
        addQuestion(q5);

    }

    //this is where the data is being inserted into our database
    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr()); //forgot this line
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        //Iterating through the questions during the quiz.
        if(c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            }while (c.moveToNext());
        }

        c.close();
        return questionList;
    }


}
