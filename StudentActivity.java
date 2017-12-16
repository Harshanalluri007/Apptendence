package com.example.jay_s.apptendance;

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
import java.sql.SQLException;
import java.util.concurrent.Executor;

public class StudentActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://frankencluster.com:3306/mobileappteam3";
    private static final String username = "team3admin";
    private static final String password = "Tardis10";

    private EditText firstnameText;
    private EditText lastnameText;
    private EditText pantherText;
    private Button enterButton;
    private TextView studentConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        firstnameText = (EditText) findViewById(R.id.firstnameText);
        lastnameText = (EditText) findViewById(R.id.lastnameText);
        pantherText = (EditText) findViewById(R.id.pantherText);
        enterButton = (Button) findViewById(R.id.Enterbutton);
        studentConfirmation = (TextView) findViewById(R.id.studentConfirmation);


        // enterButton's onclick listener in lambda notation.
        enterButton.setOnClickListener(v -> new enterStudent().execute());

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private class enterStudent extends AsyncTask<Void, Void, Void> {
        /*
        This class is created to run a separate thread in order to not disturb the main thread.

        This class is responsible for creating a thread that will connect to the database and
        insert a new student.

        It will activate method called doInBackground to establish a connection to the database, and
        execute the insert statement.
        After doing this, it will call method onPostExecute to do any action(s) that is(are) required
        after the inserting into the database.

         */

        @Override
        protected Void doInBackground(Void... voids) {
            /*
            Establishes the connection with the database mobileappteam3.
            It will insert a new student in the table called student.

            It will insert three parameters, panther number, first and last name.
            If an error is thrown it will display it in the text view under the button as:
            "could not add student."

             */

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, password);
                String insert = "Insert into mobileappteam3.Student(PantherNumber, FirstName, LastName)" +
                        "values(?,?,?)";
                PreparedStatement smt = con.prepareStatement(insert);
                smt.setString(1, pantherText.getText().toString());
                smt.setString(2, firstnameText.getText().toString());
                smt.setString(3, lastnameText.getText().toString());

                smt.execute();

                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                studentConfirmation.setText("Could not add Student.");
                studentConfirmation.setVisibility(View.VISIBLE);

            }
            return null;
        }// end of doInBackground
        ////////////////////////////////////////////////////////////////////////////////////////////////

        protected void onPostExecute(Void Results) {
            /*
            This method is called after doInBackground method is finished running.
            This method will display the textView to give a visual confirmation to the user that
            the new student has been added.
             */
            studentConfirmation.setText("Student added successfully.");
            studentConfirmation.setVisibility(View.VISIBLE);
        }//end of onPostExecute
    }//end of enterStudent

}//end of Activity.
