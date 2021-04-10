package javaPkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImageTagDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public ImageTagDAO(){}
	
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
	
	protected void insert(ImageTag it) throws SQLException{
		  String sql = "INSERT into imageTag(imageID,tagID) values (?, ?)";
		  connect_func();
		   	
		  preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		  preparedStatement.setInt(1, it.getImageId());
		  preparedStatement.setInt(2, it.getTagId());
		   	
		  preparedStatement.executeUpdate();
		  preparedStatement.close();
		   	
		  disconnect();
	}
	
	public List<Tag> getTags(int imageId) throws SQLException{
		List<Tag> tags = new ArrayList<Tag>();
		String sql = "select * from imageTag it " +
					 "join tags t on it.tagId = t.tagId " +
					 "where imageId = ?;";
		connect_func("root","root1234");
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, imageId);
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()){
			String t = rs.getString("tag");
			tags.add(new Tag(t));
		}
		preparedStatement.close();
		disconnect();
		return tags;
	}
}
