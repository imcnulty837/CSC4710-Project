package javaPkg.dataAccess;
// Hi
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImageDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public ImageDAO(){}
	
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
     * Retrieves the id of the image targeted by the email and url combo
     * @param email, the poster's email
     * @param url, the image's url
     * @return id, the id of the image
     */
    public int retrieveImageId(String email, String url) throws SQLException {
    	int id = 0;
    	String sql = "Select imageid from Image where email = ? and url = ?";
    	connect_func();
    	
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, email);
    	preparedStatement.setString(2, url);
        ResultSet resultSet = preparedStatement.executeQuery();
        id = resultSet.getInt("imageId");
        
        resultSet.close();
        statement.close();
        disconnect();
        return id;
    }
    
    /**
     * Adds a new entry to the image table with the given parameters
     * @param image, an image object
     */
    public void insert(Image image) throws SQLException {
    	String sql = "Insert into image(email, url, description) values (?,?,?)";
    	connect_func();
    	
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, image.getEmail());
    	preparedStatement.setString(2, image.getUrl());
    	preparedStatement.setString(3, image.getDescription());
    	
    	preparedStatement.executeUpdate();
    	preparedStatement.close();
    	
    	disconnect();
    }
    
    /**
     * Deletes an entry from the image table
     * @param image, an image object
     */
    public void delete(Image image) throws SQLException {
    	String sql = "Delete from image where email = ? and url = ?";
    	connect_func();
    	
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, image.getEmail());
    	preparedStatement.setString(2, image.getUrl());
    	
    	preparedStatement.executeUpdate();
    	preparedStatement.close();
    	
    	disconnect();
    }
    
    /**
     * Changes the description of the photo
     * @param image, an Image object
     */
    public void update(Image image) throws SQLException {
    	String sql = "Update Image set description = ? where email = ? and url = ?";
    	connect_func();
    	
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, image.getDescription());
    	preparedStatement.setString(2, image.getEmail());
    	preparedStatement.setString(3, image.getUrl());
    	
    	preparedStatement.executeUpdate();
    	preparedStatement.close();
    	disconnect();
    }

    public List<Image> getFeed(String user){
    	List<Image> images = new ArrayList<Image>;
    	String sql = "Select * from Image where email = ?";
    	preparedStatment = connect.prepareStatement(sql);
    	preparedStatement.setString(1, user);
    	ResultSet resultSet = preparedStatement.executeQuery();

    	while(resultSet.next()) {
    		int id = resultSet.getInt("imageId");
    		Timestamp t = resultSet.getTimestamp("ts");
    		String em = resultSet.getString("email");
    		String url resultSet.getString("url");
    		String descript = resultSet.getString("description");

    		images.add(new Image(id, t, em, url, descript));
    	}
    	resultSet.close()
    	statement.close()
    	disconnect();
    	return images;
    }
}
