package javaPkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public CommentDAO(){}
	
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
	
	public void insert(Comment comment) throws SQLException {
		String sql = "INSERT into comments(imageid, email, comment) values (?,?,?)";
		connect_func();
		   	
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, comment.getImageId());
		preparedStatement.setString(2, comment.getEmail());
		preparedStatement.setString(3, comment.getComment());
		   	
		preparedStatement.executeUpdate();
		preparedStatement.close();
		   	
		disconnect();
	} 

	public void delete(Comment comment) throws SQLException {
		String sql = "DELETE from comments where imageid = ? and email = ?";
		connect_func();
		   	
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, comment.getImageId());
		preparedStatement.setString(2, comment.getEmail());
		   	
		preparedStatement.executeUpdate();
		preparedStatement.close();
		   	
		disconnect();
	}	
	
	public List<Comment> getComments(int id) throws SQLException{
    	List<Comment> comments = new ArrayList<Comment>();
    	connect_func("root","root1234");
    	String sql = "select * from comments where imageid = ?";
    	
    	preparedStatement = connect.prepareStatement(sql);
    	preparedStatement.setInt(1, id);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()) {
    		String em = resultSet.getString("email");
    		String com = resultSet.getString("comment");
    		
    		comments.add(new Comment(id, em, com));
    	}
    	disconnect();
    	return comments;
    }
}
