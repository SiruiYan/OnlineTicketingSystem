package onlineticketing.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    static final String JDBC_DRIVER = "org.postgresql.Driver";  
    static final String DB_URL = "jdbc:postgresql://ec2-50-17-194-129.compute-1.amazonaws.com:5432/d8ai6gphpuhcgm?sslmode=require";
    //static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
 
    static final String USER = "wvndimtlxizlyg";
    //static final String USER = "postgres";
    static final String PASSWORD = "4f5ff5a17114e7187257dbcba5089585221e41b69cde6eddfb1b8a08b75d6035";
    //static final String PASSWORD = "12345";
    
    static Connection dbConnection = null;
    
    /**
     * This method creates a database connection.
     * Then prepare the incoming sql statement using this connection, 
     * and return the prepared statement.
     * 
     * @param stm	a sql statement that needs to be prepared
     * @return		the prepared statement
     */
    public static PreparedStatement prepare(String stm) {
		PreparedStatement preparedStatement = null;
		try {
			if (dbConnection == null) {
				dbConnection = getDBConnection();
			}
			System.out.println(stm);
			preparedStatement = dbConnection.prepareStatement(stm);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return preparedStatement;
	}
    
    /**
     * This method closes the incoming prepared statement,
     * and closes its related database connection.
     * 
     * @param preparedStatement	a prepared statement
     */
    public static void close(PreparedStatement preparedStatement) {
    	Connection dbConnection;
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * This method creates a database connection.
     * 
     * @return a database connection
     */
    private static Connection getDBConnection() {
    	Connection conn = null;
    	try {
			// register driver
			Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connect database...");
			return conn;
		} catch (SQLException se){
            // process JDBC error
            se.printStackTrace();
        } catch (Exception e){
            // process Class.forName error
            e.printStackTrace();
        }
		return null;
	}
}
