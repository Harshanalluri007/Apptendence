package com.example.jay_s.apptendance; /**
 * Created by jay_s on 11/8/2017.
 */

import android.os.AsyncTask;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AttendanceModel extends AsyncTask<String, String, String> {

    private static final String url = "jdbc:mysql://frankencluster.com:3306/mobileappteam3";
    private static final String user = "team3admin";
    private static final String pass = "Tardis10";

    private AttendanceActivity activity;
    private String date;
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat df = new SimpleDateFormat("MM_dd_YYYY");

    private String update;


    public AttendanceActivity getData(AttendanceActivity a) {
        return this.activity = a;
    }

    @Override
    protected String doInBackground(String... strings) {
        date = df.format(c.getTime());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            Statement smt = con.createStatement();
            smt.execute("ALTER TABLE mobileappteam3.Student " +
                    "ADD " + date + " VARCHAR(20);");




            con.close();


            // add a new column, then update that column. depending on who got clicked.

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // make methods to enter data into the database and to get data out of the database.
}
