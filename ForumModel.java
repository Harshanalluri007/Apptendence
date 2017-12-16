package com.example.jay_s.apptendance;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jay_s on 11/11/2017.
 */

public class ForumModel {
    /*
    This class will hold the connection to the database for questions and answers.
    The two methods will take in string parameteres to insert it into the database, and
    one to get question and answers from the database.

    For insert it must take in a question.
    For display, it should show the answer under each of the questions. specified with a relation.
    This might be the trickest part of the whole thing.
     */
    private static final String  url = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private static final  String username = "root";
    private static final String password = "Tardis24!";
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void addQuestion(String s) throws SQLException{
        /*
        This method will take in a question as a parameter and insert that into the database.
        This method will make a connection to the database and use the insert statement.

        This method will take a string query to be executed.
        It should put the insert the question into the database.
         */
        Connection con = DriverManager.getConnection(url, username, password);
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(s);

        con.close();


    }
    ////////////////////////////////////////////////////////////////////////////////////////////////;

    public String getAnswer(String s) throws SQLException{
        /*
        This method is only used to get the answer to the question.
        Not sure if it will need to take in a parameter.
        Need to find a way to make association with the question
        this method will hold the select statement and execute the connection.

        This will return answers to the questions as a string.
        Should be able to make a connection.
         */


        Connection con = DriverManager.getConnection(url,username,password);
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(s);

        while(rs.next()){
            return rs.getString(s);
        }
        return "Could not retrieve data from the database.";
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
