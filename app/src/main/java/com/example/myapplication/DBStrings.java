package com.example.myapplication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBStrings {
    static final String DATABASE_URL = "cs4261.cteat7fyzof8.us-east-1.rds.amazonaws.com:3306";
    static final String DATABASE_NAME = "project1";
    static final String USERNAME = "karrywang";
    static final String PASSWORD = "wangjunkai";

    public class GetData extends AsyncTask<String, String, String>{
        String msg = "";
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://" + DATABASE_URL + "/" + DATABASE_NAME;

        @Override
        protected String doInBackground(String... strings) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                stmt = conn.createStatement();
                String sql = "";

                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                }
            }
            catch (SQLException connError) {
                msg = "An exception was thrown for JDBC.";
                connError.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

        }
    }
}
