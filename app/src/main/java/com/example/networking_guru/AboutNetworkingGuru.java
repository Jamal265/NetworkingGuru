package com.example.networking_guru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.networking_guru.R.id.back_button;
import static com.example.networking_guru.R.id.button_about_us;

public class AboutNetworkingGuru extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_networking_guru);

        Button backButton = findViewById(back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPage();
            }
        });


    }

    private void mainPage(){
        Intent intent = new Intent(AboutNetworkingGuru.this, StartingScreenActivity.class);
        startActivity(intent);
    }
}
