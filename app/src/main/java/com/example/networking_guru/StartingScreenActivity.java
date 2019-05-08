package com.example.networking_guru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.networking_guru.R.id.button_about_us;
import static com.example.networking_guru.R.id.button_start_main_quiz;
import static com.example.networking_guru.R.id.button_start_quiz;

public class StartingScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
    private int highscore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        loadHighcore();


        //These are buttons for the main menu
        Button buttonStartQuiz = findViewById(button_start_quiz);
        Button buttonStartmainQuiz = findViewById(button_start_main_quiz);
        Button buttonAboutUs = findViewById(button_about_us);


        //Listener for trial quiz button
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        //Listener for about us button
        buttonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutUs();
            }
        });


        //Listener for main quiz button
        buttonStartmainQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainQuiz();
            }
        });

    }

    /*
        The intents below redirects user to the intended activity depending on the button pressed.
     */
    private void startQuiz(){
        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    private void mainQuiz(){
        Intent b = new Intent(StartingScreenActivity.this, MainActivity.class);
        startActivity(b);
    }

    private void aboutUs(){
        Intent i = new Intent(StartingScreenActivity.this, AboutNetworkingGuru.class);
        startActivity(i);
    }




    //This is for setting up highscore.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highscore){
                    updateHighscore(score);
                }
            }
        }
    }

    //Loads highscore when you open the application
    private void loadHighcore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
    }

    //Updates highscore if a user gets high score.
    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}
