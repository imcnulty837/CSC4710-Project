package javaPkg;
// Originally the PeopleDAO class, updated to manage the Account class

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/AccountDAO")
public class AccountDAO {     
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public AccountDAO(){}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
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
    
    public boolean dbLogin(String username, String password) {
    	try {
    		connect_func("root", "root1234");
    		String sql = "Select * from User where email = ? and password = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, username);
    		preparedStatement.setString(2, password);
    		System.out.println(preparedStatement.toString());
    		ResultSet rs = preparedStatement.executeQuery();
    		//returns false if the result set is empty, true if result set is true
    		return rs.next();
    		
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
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

    public void initialize() throws SQLException, FileNotFoundException, IOException{ 	
    	//if an error occurs here, its possible your environment doesn't have a correct path to the project
    	//to fix: go to Run > run configurations > tomcat server > arguments
    	//set your working directory to match the project folder
    	batchSqlExecuter("src/sqlPkg/databaseInit.sql");
    	batchSqlExecuter("src/sqlPkg/populateTables.sql");
    }
    
    public void batchSqlExecuter(String file) throws SQLException, FileNotFoundException, IOException{
    	//this is a batch executer meant to perform management bulk-sql operations from .sql files
    	//Connect to database
    	connect_func("root","root1234");
    	
		StringBuilder initScript = new StringBuilder("");
    	//set our file and try to scan it
    	try {
	    	FileReader myFile = new FileReader(file);
	    	BufferedReader reader = new BufferedReader(myFile);
			String line = reader.readLine();
			while(line != null) {
				initScript.append(line);
				line = reader.readLine();
			}
			reader.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    		System.out.println(e.getMessage());
    	}
		
		String sqlScript = initScript.toString();
		//break our script into a string array using ; as a delimiter
		String[] sqlCalls = sqlScript.split(";");
		
		for(int x=0; x < sqlCalls.length; x++) {
			statement = (Statement) connect.createStatement();
			statement.execute(sqlCalls[x] + ";");
		}

		disconnect();
    }
    
    public List<Account> listAllPeople() throws SQLException {
        List<Account> listPeople = new ArrayList<Account>();        
        String sql = "SELECT * FROM User";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            String birthday = resultSet.getString("birthday");
            String gender = resultSet.getString("gender");
             
            Account people = new Account(email,firstName, lastName, password, birthday,gender);
            listPeople.add(people);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listPeople;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
         
    public void insert(Account people) throws SQLException {
    	connect_func("root","root1234");         
		String sql = "insert into User(email, firstName, lastName, password, birthday, gender) values (?, ?, ?,?,?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, people.email);
		preparedStatement.setString(2, people.firstName);
		preparedStatement.setString(3, people.lastName);
		preparedStatement.setString(4, people.password);
		preparedStatement.setString(5, people.birthday);
		preparedStatement.setString(6, people.gender);
//		preparedStatement.executeUpdate();
		
        //boolean rowInserted = preparedStatement.executeUpdate() > 0;
		preparedStatement.executeUpdate();
        preparedStatement.close();
//        disconnect();
        //return rowInserted;
    }     
     
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM User WHERE email = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowDeleted;     
    }
     
    public boolean update(Account people) throws SQLException {
        String sql = "update User set firstName=?, lastName =?,password = ?,birthday=?, gender=? where email = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, people.firstName);
        preparedStatement.setString(2, people.lastName);
        preparedStatement.setString(3, people.password);
        preparedStatement.setString(4, people.birthday);
        preparedStatement.setString(5, people.gender);
        preparedStatement.setString(6, people.email);
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowUpdated;     
    }
    
    public boolean checkEmail(String email) throws SQLException {
    	boolean torf = false;
    	String sql = "SELECT * FROM User WHERE email = ?";
    	connect_func("root", "root1234");
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(torf);	
        
        if (resultSet.next()) {
        	torf = true;
        }
        
        System.out.println(torf);
    
    	return torf;
    }
	
    public Account getPeople(String email) throws SQLException {
    	Account account = null;
        String sql = "SELECT * FROM User WHERE email = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            String birthday = resultSet.getString("birthday");
            String gender = resultSet.getString("gender");
             
            account = new Account(email, firstName, lastName, password, birthday,gender);
        }
         
        resultSet.close();
        statement.close();
         
        return account;
    }
    
    public void init_backup() throws SQLException, FileNotFoundException, IOException{
    	//This is a backup database initialize function just in case something goes wrong with the batch executer or database initialize file.
    	//generally this will never get called but it never hurts to have a backup plan ;D
    	/**
    	connect_func();
        statement =  (Statement) connect.createStatement();
        
        String[] CREATION = {"drop database if exists testdb; ",
					        "create database testdb; ",
					        "use testdb; ",
					        "drop table if exists User; ",
					        ("CREATE TABLE if not exists User( " +
					            "email VARCHAR(32) NOT NULL, " + 
					            "firstName VARCHAR(20) NOT NULL, " +
					            "lastName VARCHAR(20) NOT NULL, " +
					            "gender VARCHAR(10) NOT NULL, " +
					            "password VARCHAR(20) NOT NULL, " +
					            "birthday DATE NOT NULL, " +
					            "PRIMARY KEY (email) " +
					        "); "),
					        "drop table if exists Image; ",
					        ("CREATE TABLE if not exists Image( " +
					        	"imageId INTEGER NOT NULL auto_increment, " +
					        	"ts TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
					        	"email Varchar(32), " +
					            "url VARCHAR(100), " +
					            "description VARCHAR(200), " +
					            "foreign key (email) references user(email), " +
					            "PRIMARY KEY (imageId) " +
					        "); "),
					        "drop table if exists tags; ",
					        ("create table if not exists tags( " +
					        	"tagId integer NOT NULL auto_increment, " +
					            "tag varchar(16), " +
					            "Primary key (tagId), " +
					            "constraint check_lowercase check (lower(tag) = tag) " +
					        "); "),
					        "drop table if exists follows; ",
					        ("create table if not exists follows( " +
					        	"followerEmail VARCHAR(32) not null, " +
					            "followeeEmail VARCHAR(32) not null, " +
					            "FOREIGN KEY (followerEmail) References User(email), " +
					            "FOREIGN KEY (followeeEmail) References User(email), " +
					            "Primary key (followerEmail, followeeEmail), " +
					            "constraint self_follow check (FollowerEmail <> FolloweeEmail) " +
					        "); "),
					        "drop table if exists Comments; ",
					        ("Create Table if not exists Comments( " +
					        	"imageId Integer, " +
					            "email VARCHAR(32), " +
					            "comment varchar(200), " +
					            "FOREIGN KEY (email) REFERENCES User(email), " +
					            "FOREIGN KEY (imageId) References image(imageId), " +
					            "Primary key(email,imageId) " +
					        "); "),
					        "drop table if exists ImageTag; ",
					        ("create table if not exists ImageTag( " +
					        	"imageId Integer, " +
					            "tagId integer, " +
					        	"FOREIGN KEY (imageId) REFERENCES Image(imageId), " +
					            "FOREIGN KEY (tagId) References tags(tagId), " +
					            "primary key(imageId, tagId) " +
					        "); "),			
					        "drop table if exists likes; ",
					        ("create table if not exists likes( " +
					        	"email  varchar(32), " +
					            "imageId integer, " +
					            "likeSwitch boolean, " +
					            "Foreign key (email) references user(email), " +
					            "Foreign key (imageID) references image(imageid), " +
					            "primary key (email, imageId) " +
					        "); ")};
        
        String[] FAKETUPLES = {("insert into User(email, firstName, lastName, password, birthday, gender) "+
			        		"values ('john1234@gmail.com', 'John', 'Smith', 'john1234', '1999-02-20', 'male'),"+
			    		 	"('jane5678@gmail.com', 'Jane', 'Doe','jane5678', '1969-04-20', 'female'),"+
			    	 	 	"('jingle444@gmail.com', 'Jingle', 'heimer-schmidt','jingle444', '1984-02-03', 'male'),"+
			    		 	"('bubble.Gum@gmail.com', 'Jacob', 'heimer-schmidt','bubble.Gum', '2002-02-04', 'male'),"+
			    		 	"('freyja111@gmail.com', 'Freyja', 'Aesmiri','Freyja111', '1947-06-11', 'female'),"+
			    		 	"('blue42@gmail.com', 'katalyn', 'Dinkleburger','blue42', '1997-04-04', 'female'),"+
			    			"('echoOne@gmail.com', 'Nick', 'Foster','echoOne', '1993-05-15', 'male'),"+
			    			"('test@gmail.com', 'test', 'test','test', '2012-07-14', 'female'),"+
			    			"('aquaforce@gmail.com', 'Jeff', 'Himagain','aquaforce', '1995-05-05', 'male'),"+
			    			"('valarie420@gmail.com', 'Valerie', 'Schmidt','val420', '1977-12-24', 'female');"),
						    ("insert into image(email, url, description)"+
						    	"values ('john1234@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('jane5678@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('jingle444@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('bubble.Gum@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('freyja111@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('blue42@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('echoOne@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('test@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('aquaforce@gmail.com', 'placeholderURL', 'an image of a sunset'),"+
						               "('valarie420@gmail.com', 'placeholderURL', 'an image of a sunset');"),
						    ("insert into tags(tag)"+
						    	"values ('Michigan'),"+
						    			"('Huron'),"+
						    			"('Ontario'),"+
						    			"('Erie'),"+
						    			"('Superior'),"+
						    			"('Atlantic'),"+
						    			"('Pacific'),"+
						    			"('Tahoe'),"+
						    			"('Mississippi'),"+
						    			"('Gladwin');"),
						    ("insert into follows(followerEmail, followeeEmail)"+
						    	"values ('john1234@gmail.com', 'echoOne@gmail.com'),"+
						    			"('john1234@gmail.com', 'aquaforce@gmail.com'),"+
						    			"('john1234@gmail.com', 'test@gmail.com'),"+
						    			"('john1234@gmail.com', 'freyja111@gmail.com'),"+
						    			"('john1234@gmail.com', 'bubble.Gum@gmail.com'),"+
						    			"('john1234@gmail.com', 'jingle444@gmail.com'),"+
						    			"('john1234@gmail.com', 'jane5678@gmail.com'),"+
						    			"('jane5678@gmail.com', 'echoOne@gmail.com'),"+
						    			"('test@gmail.com','aquaforce@gmail.com'),"+
						    			"('valarie420@gmail.com','blue42@gmail.com');"),
						    ("insert into comments(imageId, email, comment)"+
						    	"values (1,'john1234@gmail.com','Cool pic'),"+
						    			"(1,'jane5678@gmail.com','ur so lucky!!1'),"+
						    			"(2,'john1234@gmail.com','what a pretty sunset'),"+
						    			"(3,'jingle444@gmail.com','where did you get that pic?'),"+
						    			"(4,'jane5678@gmail.com','omg its beautiful!'),"+
						    			"(4,'test@gmail.com','noice'),"+
						    			"(4,'john1234@gmail.com','Very cool!'),"+
						    			"(5,'test@gmail.com','love it'),"+
						    			"(6,'jingle444@gmail.com','kinda lame tbh'),"+
						    			"(7,'john1234@gmail.com','Pretty pic, maybe a better angle next time');"),
						    ("insert into ImageTag(imageId, tagId)"+
						    	"values (1,1),"+
						    			"(1,2),"+
						    			"(1,3),"+
						    			"(2,1),"+
						    			"(3,1),"+
						    			"(3,2),"+
						    			"(4,1),"+
						    			"(5,1),"+
						    			"(6,1),"+
						    			"(7,1);"),
						    ("insert into likes(email, imageId, likeSwitch)"+
						    	"values ('john1234@gmail.com',1,true),"+
						    			"('jane5678@gmail.com',1,true),"+
						    			"('jingle444@gmail.com',1,true),"+
						    			"('john1234@gmail.com',2,true),"+
						    			"('jane5678@gmail.com',3,true),"+
						    			"('jingle444@gmail.com',4,true),"+
						    			"('test@gmail.com',4,true),"+
						    			"('jingle444@gmail.com',5,true),"+
						    			"('jane5678@gmail.com',4,true),"+
						    			"('john1234@gmail.com',5,true);")};
        
        for (int i = 0; i < CREATION.length; i++)
        	statement.execute(CREATION[i]);
        for (int i = 0; i < FAKETUPLES.length; i++)	
        	statement.execute(FAKETUPLES[i]);
        disconnect();
        **/
    }
}
