package com.oracle.tutorial.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyQueries {

  Connection con;
  JDBCUtilities settings;

  public MyQueries(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void getMyData(Connection con) throws SQLException {
    Statement stmt = null;
    String query =
        "SELECT SUPPLIERS.SUP_NAME, COUNT(DISTINCT COFFEES.COF_NAME) AS COFFEE_COUNT " +
        "FROM SUPPLIERS " +
        "LEFT JOIN COFFEES ON SUPPLIERS.SUP_ID = COFFEES.SUP_ID " +
        "GROUP BY SUPPLIERS.SUP_NAME " +
        "ORDER BY SUPPLIERS.SUP_NAME";

    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Fornecedores e quantidade de tipos de café vendidos:");
        while (rs.next()) {
            String supplierName = rs.getString("SUP_NAME");
            int coffeeCount = rs.getInt("COFFEE_COUNT");
            System.out.println(supplierName + ": " + coffeeCount + " tipo(s) de café");
        }
    } catch (SQLException e) {
        JDBCUtilities.printSQLException(e);
    } finally {
        if (stmt != null) { stmt.close(); }
    }
}


  public static void main(String[] args) {
    JDBCUtilities myJDBCUtilities;
    Connection myConnection = null;
    if (args[0] == null) {
      System.err.println("Properties file not specified at command line");
      return;
    } else {
      try {
        myJDBCUtilities = new JDBCUtilities(args[0]);
      } catch (Exception e) {
        System.err.println("Problem reading properties file " + args[0]);
        e.printStackTrace();
        return;
      }
    }

    try {
      myConnection = myJDBCUtilities.getConnection();

 	MyQueries.getMyData(myConnection);

    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
