package javaPkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
  			          + "useSSL=false&user=root&password=root1234");
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
	
	public int checkExists(Tag tag) throws SQLException{
		//check if the tag exists via a select statement
		int tagId = -1;
		String sql = "SELECT * from tags where tag = ?";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, tag.getTag());
		//execute the query and see if it had results
		ResultSet results = preparedStatement.executeQuery();
		if(results.next()){
			tagId = results.getInt("tagID");
			preparedStatement.close();
			disconnect();
		}
		else {
			//if the tag doesn't exist, we'll want it to exist, so insert it
			insert(tag);
			tagId = checkExists(tag);
		}
		return tagId;
	}
	
	public List<Tag> getView() throws SQLException{
		List<Tag> listTag = new ArrayList<Tag>();
		String sql = "SELECT * FROM topTags";
		connect_func();
		statement = (Statement) connect.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			String tagName = resultSet.getString("tag");
			
			Tag tag = new Tag(tagName);
			listTag.add(tag);
		}
		
		resultSet.close();
		disconnect();
		return listTag;
	}
	
}
