package net.sf.sdz.data.db;

import java.sql.*;

/**
 * Creates a SQLite database and table, inserts two rows, then selects and
 * prints them back.
 * <p>
 * Requires the sqlite-jdbc jar (https://github.com/xerial/sqlite-jdbc) on
 * the classpath.
 */
public class ReadWriteSQLite {

    String url = "jdbc:sqlite:test2.db";
    String sqlTableCreate = "CREATE TABLE datarecords (\n"
            + "\tid int PRIMARY KEY NOT NULL,\n"
            + "\tname text,\n"
            + "\tweight real,\n"
            + "  date text,\n" // date ("YYYY-MM-DD HH:MM:SS.SSS")
            + "  active int\n" // boolean
            + ")";

    public ReadWriteSQLite() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlTableCreate);
            String sqlInsertUpdate = "INSERT INTO datarecords (id,name,weight,date,active)"
                    + "VALUES (1,'John Brown',175.2342, '2008-12-13 23:53:23.353',1)";
            stmt.executeUpdate(sqlInsertUpdate);
            sqlInsertUpdate = "INSERT INTO datarecords (id,name,weight,date,active)"
                    + "VALUES (2,'George Washington',248.9263, '1776-03-14 02:15:22.543',0)";
            stmt.executeUpdate(sqlInsertUpdate);
            String sqlSelectQuery = "SELECT * FROM datarecords";
            ResultSet rs = stmt.executeQuery(sqlSelectQuery);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDouble("weight") + "\t"
                        + rs.getString("date") + "\t"
                        + rs.getInt("active") + "\t");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ReadWriteSQLite();
    }
}
