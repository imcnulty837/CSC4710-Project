package javaPkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LikeDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public LikeDAO(){}
	
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
	
	/**
     * Prints out the like count of a particular image
     * @param imageid, the id of the image whos like count is being evaluated
     */
    public int likeCount(int imageid) throws SQLException {
    	int count = 0;
    	String sql = "SELECT COUNT(likeSwitch) as total FROM Likes WHERE likeSwitch = true AND imageId = ?";
    	connect_func();
    	System.out.println("Inside LikeCount");
    	
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setInt(1, imageid);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
        	count = resultSet.getInt("total");
        }
    	
    	preparedStatement.close();
    	
    	disconnect();
    	return count;
    }
    
    /**
     * Adds a true value to the likes table according to email of liker and image id of liked photo
     * @param like, a Like object
     */
    public void insert(Like like) throws SQLException {
    	String sql = "INSERT into Likes(email, imageId, likeSwitch) values (?, ?, true)";
    	connect_func();
    	
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, like.getEmail());
    	preparedStatement.setInt(2, like.getImageId());
    	
    	preparedStatement.executeUpdate();
    	preparedStatement.close();
    	
    	disconnect();
    }
    
    /**
     * Unlike a photo
     * @param like, a Like object
     */
    public void delete(Like like) throws SQLException {
    	String sql = "DELETE from Likes where email = ? and imageid = ?";
    	connect_func();
    	
	    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	    	preparedStatement.setString(1, like.getEmail());
	    	preparedStatement.setInt(2, like.getImageId());
	    	
	    	preparedStatement.executeUpdate();
	    	preparedStatement.close();
    	
    	disconnect();
    }
    
    /**
     * Checks if the like pair exists for the unlike function
     * @param email, the email of the user unliking a picture
     * @param imageid, the id of the picture being unliked
     * @return flag, true if the pair exists, false otherwise
     */
    public boolean check(String email, int imageid) throws SQLException {
    	boolean flag = false;
    	String sql = "SELECT * FROM Likes WHERE email = ? and imageid = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setInt(2, imageid);
        ResultSet resultSet = preparedStatement.executeQuery();
                
        if (resultSet.next()) {
        	flag = true;
        }
            
    	return flag;
    }
}
