package javaPkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FollowDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public FollowDAO(){}
	
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
	
	/**
    * Returns the follower count for the user
    * @param followeeemail, email to be checked
    * @return count, number of followers for this input email
    */
   public int followCount(String followeeemail) throws SQLException {
   	int count = 0;
   	String sql = "SELECT COUNT(followeremail) FROM followers WHERE followeeemail = ?";
   	connect_func();
   	
   	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
   	preparedStatement.setString(1, followeeemail);
       ResultSet resultSet = preparedStatement.executeQuery();
       count = resultSet.getInt();
   	
   	preparedStatement.close();
   	
   	disconnect();
   	return count;
   }
   
   /**
    * Allows a given user to follow another user
    * @param follow, a Follow object
    */
   public void insert(Follow follow) throws SQLException {
   	String sql = "INSERT into follows(followeremail, followeeemail) values (?, ?)";
   	connect_func();
   	
   	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
   	preparedStatement.setString(1, follow.getFollowerEmail());
   	preparedStatement.setString(2, follow.getFolloweeEmail());
   	
   	preparedStatement.executeUpdate();
   	preparedStatement.close();
   	
   	disconnect();
   }
   
   /**
    * Allows a given user to unfollow another user
    * @param follow, a Follow object
    */
   public void delete(Follow follow) throws SQLException {
   	String sql = "DELETE from follows where followeremail = ? and followeeemail = ?";
   	connect_func();
   	
   	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
   	preparedStatement.setString(1, follow.getFollowerEmail());
   	preparedStatement.setString(2, follow.getFolloweeEmail());
   	
   	preparedStatement.executeUpdate();
   	preparedStatement.close();
   	
   	disconnect();
   }
   
   /**
    * Checks if the follows pair exists for the unfollow function
    * @param followeremail, the email of the follower
    * @param followeeemail, the email of the followee
    * @return flag, true if the pair exists, false otherwise
    */
   public boolean check(String followeremail, String followeeemail) throws SQLException {
   	boolean flag = false;
   	String sql = "SELECT * FROM follows WHERE followeremail = ? and followeeemail = ?";
   	connect_func();
   	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
       preparedStatement.setString(1, followeremail);
       preparedStatement.setString(2, followeremail);
       ResultSet resultSet = preparedStatement.executeQuery();
               
       if (resultSet.next()) {
       	flag = true;
       }
           
   	return flag;
   }
}
