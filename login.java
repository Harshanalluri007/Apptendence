package com.example.jay_s.apptendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        clickLoginButton();
        clickTestButton();
    }

    private void clickLoginButton() {
        Button nxtButton = (Button) findViewById(R.id.buttonLogin);
        nxtButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(login.this, select_course.class);
                startActivity(intent);
            }
        });
    }

    private void clickTestButton() {
        Button testButton = (Button) findViewById(R.id.RegisterButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
