package com.example.jay_s.apptendance;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RemoveActivity extends AppCompatActivity {
    //declare our strings to connect to the database.
    private static final String url = "jdbc:mysql://frankencluster.com:3306/mobileappteam3";
    private static final String username = "team3admin";
    private static final String password = "Tardis10";

    // declare our widgets that are on the view.
    private EditText removeFirstText;
    private EditText removeLastText;
    private EditText removePanther;
    private Button removeButton;
    private TextView removeConfirmation;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        // in the onCreate we are instaniating the widgets in the activity.
        // we are "Creating them."
        removeFirstText = (EditText) findViewById(R.id.removeFirstText);
        removeLastText = (EditText) findViewById(R.id.removeLastText);
        removePanther = (EditText) findViewById(R.id.removePanther);
        removeButton = (Button) findViewById(R.id.removebutton);
        removeConfirmation = (TextView) findViewById(R.id.removeConfirmation);

        // Create an onclick listener, this is the lambda notation.
        removeButton.setOnClickListener(v -> new deleteData().execute());

    }// end of onCreate

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class deleteData extends AsyncTask<Void, Void, Void> {
        /*
        This inner class is needed to run anything that would interupt the main thread, which is
        busy running our view.

        AsyncTask has few methods that we have to use.
        doInBackground is one of them; it is used to run a separate thread to execute the connection
        to the database and execute our delete statement.
        This has to run in the background.
         */

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, password);
                String delete = "Delete from mobileappteam3.Student where PantherNumber = ? AND LastName = ?";
                PreparedStatement smt = con.prepareStatement(delete);
                smt.setString(1, removePanther.getText().toString());
                smt.setString(2, removeLastText.getText().toString());

                smt.execute();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                removeConfirmation.setText("Could not delete.");
                removeConfirmation.setVisibility(View.VISIBLE);
            }
            return null;
        }// end of doInBackground

        ////////////////////////////////////////////////////////////////////////////////////////////

        protected void onPostExecute(Void Result) {
            /*
            This method is also part of AsyncTask.
            It does actions that should occur after the execution of the doInBackground method.
            Without this, views get conflicted with which view the action needs to occur on.

            This method display our confirmation of a successful database connection and deletion.
             */
            removeConfirmation.setText("Delete successful");
            removeConfirmation.setVisibility(View.VISIBLE);
        }//end of onPostExecute
    }//end of deleteData

////////////////////////////////////////////////////////////////////////////////////////////////////
}
