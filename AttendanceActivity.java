package com.example.jay_s.apptendance;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.nio.file.StandardWatchEventKinds;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AttendanceActivity extends AppCompatActivity {
    private static final String url = "jdbc:mysql://frankencluster.com:3306/mobileappteam3";
    private static final String username = "team3admin";
    private static final String password = "Tardis10";


    private List things;
    private Button deleteButton;
    private Button enterButton;
    private Button forumButton;
    private Button submitButton;

    private CheckBox checkBox6;
    private CheckBox checkBox5;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;

    private AttendanceModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        new getStudentTable().execute();

        submitButton = (Button) findViewById(R.id.submitButton);
        forumButton = (Button) findViewById(R.id.forumButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        enterButton = (Button) findViewById(R.id.enterButton);

        // top to bottom in scrollview: 6,5,2,3,4
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);


        // all the button click events.
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    enterButtonClicked();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new updateAttendance().execute();
            }
        });
        forumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumClicked();
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void enterButtonClicked() throws SQLException {
        // adds student's name to the database.
        //this method will call a method in the model class to add it to the data base.
        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteClick() {
        /*
        used to delete a select student from the database.
         */
        startActivity(new Intent(getApplicationContext(), RemoveActivity.class));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void forumClicked() {
        /*
        opens another activity for the forum.
        Intent.
         */
        startActivity(new Intent(getApplicationContext(), ForumActivity.class));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void submitClicked() {
        /*
        makes all the students as present.
         */
    }

    private class getStudentTable extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //creating connection to the database mobileappteam3.
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, password);
                //forming query statement.
                String select = "select FirstName, LastName From mobileappteam3.Student";

                //preparing a statment and executing statement.
                // getting metadata of the database, for column numbers and values.
                Statement smt = con.createStatement();
                ResultSet rs = smt.executeQuery(select);
                ResultSetMetaData rms = rs.getMetaData();

                things = new ArrayList();
                while (rs.next()) {
                    things.add(rs.getString(1));
                    things.add(rs.getString(2));
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void Results) {
            for (int i = 0; i < things.size(); i++) {
                checkBox6.setText(things.get(0).toString() + " " + things.get(1).toString());
                checkBox5.setText(things.get(2).toString() + " " + things.get(3).toString());
                checkBox2.setText(things.get(4).toString() + " " + things.get(5).toString());
                checkBox3.setText(things.get(6).toString() + " " + things.get(7).toString());
                checkBox4.setText(things.get(8).toString() + " " + things.get(9).toString());
            }//end of for-loop
        }//end of onPostExecute
    }//end of getStudentTable


    private class updateAttendance extends AsyncTask<Void, Void, Void> {

        private String date;
        private Calendar c = Calendar.getInstance();
        private SimpleDateFormat df = new SimpleDateFormat("MM_dd_YYYY");
        private String update;


        @Override
        protected Void doInBackground(Void... voids) {
            //  this class will, unfortunately have to be here.
            // This will do what I was planning on doing in the model class.

            date = df.format(c.getTime());

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, password);

                // used to add a column in the table Student.
                Statement smt = con.createStatement();
                smt.execute("ALTER TABLE mobileappteam3.Student " +
                        "ADD " + date + " VARCHAR(1);");


                //Shit load of if-statements to mark a student here for attendance.
                // https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
                // this is not the best way.
                if (checkBox6.isSelected()) {
                    update = "UPDATE mobileappteam3.Student " +
                            "SET " + date + " = 1 " +
                            "WHERE FirstName = Derek";
                    PreparedStatement smtcb6 = con.prepareStatement(update);
//                    smtcb6.setString(1, "1");
//                    smtcb6.setString(2, "Derek");
                    smtcb6.executeUpdate();
                    con.commit();
                }
                if (checkBox5.isSelected()) {
                    update = "UPDATE mobileappteam3.Student " +
                            "SET " + date + "= ?" +
                            "WHERE FirstName = ?";
                    PreparedStatement smtcb5 = con.prepareStatement(update);
                    smtcb5.setString(1, "1");
                    smtcb5.setString(2, "Harsha");
                    smtcb5.executeUpdate();
                    con.commit();
                }
                if (checkBox2.isSelected()) {
                    update = "UPDATE mobileappteam3.Student " +
                            "SET " + date + "= ?" +
                            "WHERE FirstName = ?";
                    PreparedStatement smtcb2 = con.prepareStatement(update);
                    smtcb2.setString(1, "1");
                    smtcb2.setString(2, "Jay");
                    smtcb2.executeUpdate();
                    con.commit();
                }
                if (checkBox3.isSelected()) {
                    update = "UPDATE mobileappteam3.Student" +
                            "SET " + date + " = ?" +
                            "WHERE FirstName = ?";
                    PreparedStatement smtcb3 = con.prepareStatement(update);
                    smtcb3.setString(1, "1");
                    smtcb3.setString(2, "Kevin");
                    smtcb3.executeUpdate();
                    con.commit();
                }
                if (checkBox4.isSelected()) {
                    update = "UPDATE mobileappteam3.Student" +
                            "SET " + date + " = ?" +
                            "WHERE FirstName = ?";
                    PreparedStatement smtcb4 = con.prepareStatement(update);
                    smtcb4.setString(1, "1");
                    smtcb4.setString(2, "Barry");
                    smtcb4.executeUpdate();
                    con.commit();
                }

                con.close();




            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
