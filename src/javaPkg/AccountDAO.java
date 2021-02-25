package javaPkg;
// Originally the PeopleDAO class, updated to manage the Account class

import java.io.File;
import java.io.FileNotFoundException;
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
	
	
	public AccountDAO(){
		
    }
	
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
    		connect_func();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    	return true;
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

    public void initialize() throws SQLException, FileNotFoundException{ 	
    	//initialize our database with our predefined sql scripts
    	//for whatever reason i cant seem to get this to behave with anything other than absolute paths...its really pissing me off.
    	batchSqlExecuter("C:\Users\17342\git\CSC4710-Project\src\sqlPkg\databaseInit.sql", "root","root1234");
    	batchSqlExecuter("C:\\Users\\17342\\git\\CSC4710-Project\\src\\sqlPkg\\populateTables.sql","root","root1234");
    }
    
    public void batchSqlExecuter(String file,String user, String password) throws SQLException, FileNotFoundException{
    	//Connect to database
    	connect_func(user, password);
    	
    	//set our file and scan it
    	File script = new File(file);
		Scanner reader = new Scanner(script);
		String initScript = "";
		while(reader.hasNextLine()) {
			initScript += reader.nextLine();
		}
		
		//break our script into a string array using ; as a delimeter
		String[] sqlCalls = initScript.split(";");
		
		for(int x=0; x < sqlCalls.length; x++) {
			statement = (Statement) connect.createStatement();
			statement.execute(sqlCalls[x] + ";");
		}
		
		reader.close();
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
