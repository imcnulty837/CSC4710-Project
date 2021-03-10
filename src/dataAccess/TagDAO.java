package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dataType.Tag;

public class TagDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public TagDAO(){}
	
	protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/testdb?"
  			          + "useSSL=false&user=john&password=john1234");
            System.out.println(connect);
        }
    }
	
	public void connect_func(String username, String password) throws SQLException {
    	//connect to the database using a specified username and password
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/testdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
	
	protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }

	public void insert(Tag tag) throws SQLException {
	  String sql = "INSERT into tags(tag) values (?)";
	  connect_func();
	   	
	  preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	  preparedStatement.setString(1, tag.getTag());
	   	
	  preparedStatement.executeUpdate();
	  preparedStatement.close();
	   	
	  disconnect();
	} 

	public void delete(Tag tag) throws SQLException {
	  String sql = "DELETE from tags where tag = ?";
	  connect_func();
	   	
	  preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	  preparedStatement.setString(1, tag.getTag());
	   	
	  preparedStatement.executeUpdate();
	  preparedStatement.close();
	   	
	  disconnect();
	}	
}
