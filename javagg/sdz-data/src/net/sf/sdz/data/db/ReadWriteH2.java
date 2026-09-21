package net.sf.sdz.data.db;

import java.sql.*;

/**
 * Same idea as {@link ReadWriteSQLite}, recoded for the H2 database, which
 * supports a wider range of column types (IDENTITY, DATE, BOOLEAN, ...).
 * <p>
 * Requires the H2 jar (https://www.h2database.com/) on the classpath.
 */
public class ReadWriteH2 {

    String url = "jdbc:h2:file:./h2test.db";
    String sqlTableCreate = "CREATE TABLE IF NOT EXISTS datarecords (\n"
            + "\tid identity PRIMARY KEY NOT NULL,\n"
            + "\tname varchar,\n"
            + "\tweight float,\n"
            + "  date date,\n"
            + "  active boolean\n"
            + ")";

    public ReadWriteH2() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlTableCreate);
            String sqlInsertUpdate = "INSERT INTO datarecords (id,name,weight,date,active)"
                    + "VALUES (1,'John Brown',175.2342, '2008-12-13',true)";
            stmt.executeUpdate(sqlInsertUpdate);
            sqlInsertUpdate = "INSERT INTO datarecords (id,name,weight,date,active)"
                    + "VALUES (2,'George Washington',248.9263, '1776-03-14',false)";
            stmt.executeUpdate(sqlInsertUpdate);

            String sqlSelectQuery = "SELECT * FROM datarecords";
            ResultSet rs = stmt.executeQuery(sqlSelectQuery);
            while (rs.next()) {
                System.out.println(getColumnString(new Integer(rs.getInt("id")).toString(), 10) + "\t"
                        + getColumnString(rs.getString("name"), 20) + "\t"
                        + getColumnString(new Float(rs.getFloat("weight")).toString(), 8) + "\t"
                        + getColumnString(rs.getDate("date").toString(), 10) + "\t"
                        + getColumnString(new Boolean(rs.getBoolean("active")).toString(), 6));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String getColumnString(String data, int colLen) {
        data += "                                         ";
        return data.substring(0, colLen);
    }

    public static void main(String args[]) {
        new ReadWriteH2();
    }
}
