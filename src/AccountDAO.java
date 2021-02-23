// Originally the PeopleDAO class, updated to manage the Account class

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
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
	
	
	public AccountDAO(){
		
    }
	       
    /**
     * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
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
    
    // I created this so I can actually work with the different functions in AccountDAO
    // Also so I can actually register new users
    // Still need to implement it so we can create every table necessary
    public void initialize() throws SQLException{
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	String dropUser = "DROP TABLE IF EXISTS User";
        String createUser = "CREATE TABLE User(" +
        					"email VARCHAR(32) NOT NULL," +
        					"firstName VARCHAR(20)," +
        					"lastName VARCHAR(20)," +
        					"password VARCHAR(20)," +
        					"birthday DATE," +
        					"gender VARCHAR(10)," +
        					"PRIMARY KEY (email)" +
        					");";
        
		//String insertUser = "insert into User(email, firstName, lastName, password, birthday, gender) values (?,?,?,?,?,?)";
		
		// Creates User table
		statement.executeUpdate(dropUser);
		statement.executeUpdate(createUser);
		
		// Need to add 8 more default tuples for part 2
		Account account1 = new Account("john1234@gmail.com", "John", "Smith",
				  "john1234", "1999-02-20", "male");
		insert(account1);
		Account account2 = new Account("jane5678@gmail.com", "Jane", "Doe",
				  "jane5678", "1969-04-20", "female");
		insert(account2);
		
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
    	connect_func();         
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
    	connect_func();
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
}
