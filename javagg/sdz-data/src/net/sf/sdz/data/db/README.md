# JDBC Database Examples

## SQLite

`ReadWriteSQLite` opens (creating if needed) a SQLite database at
`test2.db` in the working directory (`sdz-data/data` when run via `runsdz.sh`), creates a `datarecords` table, inserts two rows, then runs a
`SELECT *` and prints the results.

Setup:
1. Download the [sqlite-jdbc jar](https://github.com/xerial/sqlite-jdbc/releases) and add it to
   `sdz-data`'s classpath (e.g. drop it in the project's `lib/` folder alongside `jdom-2.0.6.1.jar`).
2. Adjust `url` in `ReadWriteSQLite` if you want the `.db` file somewhere other than
   the working directory.

```java
Class.forName("org.sqlite.JDBC");
Connection conn = DriverManager.getConnection(url);
Statement stmt = conn.createStatement();
stmt.executeUpdate(sqlTableCreate);
...
ResultSet rs = stmt.executeQuery("SELECT * FROM datarecords");
while (rs.next()) {
    // rs.getInt("id"), rs.getString("name"), ...
}
```

## H2

`ReadWriteH2` is the same example recoded for [H2](https://www.h2database.com/), which supports
more column types than SQLite (`IDENTITY`, `DATE`, `BOOLEAN`, etc — SQLite is intentionally
lightweight and mostly typeless). Note H2 auto-creates the database file on first connection,
same as SQLite.

Setup:
1. Download the H2 jar from the H2 install (`h2/bin/h2-<version>.jar`) and add it to
   `sdz-data`'s classpath. The article specifically recommends **1.4.195** — the next version,
   1.4.196, apparently threw a driver class-not-found exception on startup.
2. Adjust `url` in `ReadWriteH2` if you want the `.db` file somewhere other than
   the working directory.

`getColumnString(data, colLen)` is a small helper that right-pads a string with spaces and
truncates it to a fixed width, used to line up the console output into columns.

---
*Ported from the "Local Data Storage" article series, softwaredeveloperzone.com.*
