package com.example.jay_s.apptendance;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    private TextView forumPage;
    private EditText displayText;
    private EditText editText;
    private Button addButton;

    private ArrayList questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        new retrieveQuestoins().execute();

        forumPage = (TextView)findViewById(R.id.forumtextView);

        //DisplayText is the textview that will display all the questions and answers.
        // made it so that it cannot be editable.
        displayText = (EditText)findViewById(R.id.displayText);
        displayText.setFocusable(false);
        displayText.setEnabled(false);
        displayText.setBackgroundColor(Color.TRANSPARENT);




        //This is used to enter your answer to the question.
        editText = (EditText)findViewById(R.id.editText);

        // add Button for the answer to the question.
        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayText.append(editText.getText().toString());
                editText.getText().clear();

            }
        });

    }
    private class addQuestion extends AsyncTask<Void,Void,Void>{

        private String qestion;
        private String answer;

        private static final String url = "jdbc:mysql://frankencluster.com:3306/mobileappteam3";
        private static final String username = "team3admin";
        private static final String password = "Tardis10";
        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url,username,password);

                answer = "INSERT INTO mobileappteam3.Answer(Answer)" +
                        "Values(?)";
                PreparedStatement ansSmt = con.prepareStatement(answer);
                ansSmt.setString(1, "A: "+editText.getText().toString());

                ansSmt.execute();

                con.close();

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    private class retrieveQuestoins extends AsyncTask<Void, Void,Void>{



        private static final String url = "jdbc:mysql://frankencluster.com:3306/mobileappteam3";
        private static final String username = "team3admin";
        private static final String password = "Tardis10";

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection listCon = DriverManager.getConnection(url,username,password);
                String questionQuery = "SELECT Questions FROM mobileappteam3.Questions";

                Statement qSmt = listCon.createStatement();
                ResultSet rs =  qSmt.executeQuery(questionQuery);

                questions = new ArrayList();
                while(rs.next()){
                    questions.add(rs.getString(1));
                }
                qSmt.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void Result){

           for(int i =0; i<questions.size(); i++){
               displayText.append(questions.get(i).toString()+"\n");

           }
        }
    }

}
