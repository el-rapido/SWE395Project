
package tai.foundation.db;

import java.sql.*;

/**
* A utility class for simplifying PreparedStatement usage.
* Provides methods to add parameters of various types to a PreparedStatement.
* Creation date: (06.11.2001 03:59:52)
* Author: Yusuf Donmez
*/
public class EasyPreparedStatement {
private int paramCount; // Tracks the number of parameters added
private PreparedStatement statement;

/**
* Constructor for EasyPreparedStatement.
* 
* @param conn The database connection.
* @param sqlStatement The SQL statement with placeholders.
* @throws SQLException If the statement cannot be prepared.
* @throws IllegalArgumentException If connection or SQL statement is null.
*/
public EasyPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
if (conn == null || sqlStatement == null) {
throw new IllegalArgumentException("Connection and SQL statement cannot be null.");
}
statement = conn.prepareStatement(sqlStatement);
}

/**
* Adds a byte parameter to the statement.
* 
* @param value The byte value.
* @throws SQLException If an error occurs.
*/
public synchronized void addParameter(byte value) throws SQLException {
statement.setByte(++paramCount, value);
}

public synchronized void addParameter(double param) throws SQLException {
statement.setDouble(++paramCount, param);
}

public synchronized void addParameter(float param) throws SQLException {
statement.setFloat(++paramCount, param);
}

public synchronized void addParameter(int param) throws SQLException {
statement.setInt(++paramCount, param);
}

public synchronized void addParameter(long param) throws SQLException {
statement.setLong(++paramCount, param);
}

public synchronized void addParameter(String param) throws SQLException {
statement.setString(++paramCount, param);
}

public synchronized void addParameter(java.sql.Date param) throws SQLException {
statement.setDate(++paramCount, param);
}

public synchronized void addParameter(java.sql.Time param) throws SQLException {
statement.setTime(++paramCount, param);
}

public synchronized void addParameter(java.sql.Timestamp param) throws SQLException {
statement.setTimestamp(++paramCount, param);
}

public synchronized void addParameter(java.util.Date value) throws SQLException {
if (value == null) {
statement.setDate(++paramCount, null);
} else {
statement.setDate(++paramCount, new java.sql.Date(value.getTime()));
}
}

public synchronized void addParameter(short param) throws SQLException {
statement.setShort(++paramCount, param);
}

/**
* Clears all parameters and resets the parameter count.
* 
* @throws SQLException If an error occurs.
*/
public synchronized void clearParameters() throws SQLException {
statement.clearParameters();
paramCount = 0;
}

/**
* Returns the underlying PreparedStatement.
* 
* @return The PreparedStatement.
*/
public PreparedStatement getPreparedStatement() {
return statement;
}

/**
* Closes the PreparedStatement.
* 
* @throws SQLException If an error occurs.
*/
public synchronized void close() throws SQLException {
if (statement != null) {
statement.close();
}
}
}

