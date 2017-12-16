package com.example.jay_s.apptendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class select_course extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);

        clickCourseButton();
    }


    private void clickCourseButton() {
        Button btnToSelectCourse = (Button) findViewById(R.id.buttonToSelectCourse);
        btnToSelectCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(select_course.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });
    }
}
