package com.example.jay_s.apptendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterActivity extends AppCompatActivity {
    private EditText FirstNameText;
    private EditText LastNameText;
    private EditText UsernameText;
    private EditText PasswordText;
    private EditText PnumberText;
    private Button LoginButton;
    private Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirstNameText = (EditText)findViewById(R.id.FirstNameText);
        LastNameText = (EditText)findViewById(R.id.LastNameText);
        UsernameText = (EditText)findViewById(R.id.UsernameText);
        PasswordText = (EditText)findViewById(R.id.PasswordText);
        PnumberText = (EditText)findViewById(R.id.PnumberText);
        RegisterButton = (Button)findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddTeacher().execute();
            }
        });
        LoginButton = (Button)findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });

    }
    private class AddTeacher extends AsyncTask<Void,Void,Void>{

        private static final String url = "jdbc:mysql://frankencluster.com:3306/mobileappteam3";
        private static final String username = "team3admin";
        private static final String password = "Tardis10";

        private String insertString;

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection teacherCon = DriverManager.getConnection(url,username,password);
                insertString = "INSERT INTO mobileappteam3.Teacher(PantherNumber, FirstName, LastName, Username, Password)" +
                        "Values(?,?,?,?,?)";
                PreparedStatement teacherSmt = teacherCon.prepareStatement(insertString);
                teacherSmt.setString(1,PnumberText.getText().toString());
                teacherSmt.setString(2,FirstNameText.getText().toString());
                teacherSmt.setString(3,LastNameText.getText().toString());
                teacherSmt.setString(4,UsernameText.getText().toString());
                teacherSmt.setString(5,PasswordText.getText().toString());

                teacherSmt.execute();
                teacherCon.close();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void Results){
            FirstNameText.getText().clear();
            LastNameText.getText().clear();
            UsernameText.getText().clear();
            PasswordText.getText().clear();
            PnumberText.getText().clear();
        }
    }
}
