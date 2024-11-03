package com.oracle.tutorial.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyQueries3 {

  Connection con;
  JDBCUtilities settings;

  public MyQueries3(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void getMyData(Connection con) throws SQLException {
    Statement stmt = null;
    String query =
        "SELECT c.nome_cliente, SUM(d.saldo_deposito) AS total_depositos, SUM(e.valor_emprestimo) AS total_emprestimos " +
        "FROM cliente c " +
        "JOIN deposito d ON c.nome_cliente = d.nome_cliente " +
        "JOIN emprestimo e ON c.nome_cliente = e.nome_cliente " +
        "GROUP BY c.nome_cliente " +
        "HAVING COUNT(d.numero_deposito) > 0 AND COUNT(e.numero_emprestimo) > 0";

    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Clientes e total de depósitos:");
        while (rs.next()) {
            String clientName = rs.getString("nome_cliente");
            double totalDeposits = rs.getDouble("total_depositos");
            double totalEmpréstimos = rs.getDouble("total_emprestimos");
            System.out.println(clientName + ": " + totalDeposits + " total de depósitos, " + totalEmpréstimos + " total de empréstimos");
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

      MyQueries3.getMyData(myConnection);

    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
