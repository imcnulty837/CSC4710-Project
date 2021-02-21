import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
 
/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AccountDAO accountDAO;
    TreeMap<String, String> users;
 
    public void init() {
        accountDAO = new AccountDAO(); 
        
        //initialize our TreeMap users containing our webpage users login data
        users = new TreeMap<String,String>();
        populateUsers();
    }
    
    public void populateUsers() {
    	//reads the loginData file and inserts all webpage users into a TreeMap
    	try {
    		File loginData = new File("CSC4710-Project\\src\\LoginData");
    		Scanner reader = new Scanner(loginData);
    		while(reader.hasNextLine()) {
    			String[] data = reader.nextLine().split("\t");
    			users.put(data[0], data[1]);
    		}
    		reader.close();
    	}
    	catch(FileNotFoundException e) {
    		System.out.println("failed to read login data file.");
    		e.printStackTrace();
    	}
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("how did i get here?");
        String action = request.getServletPath();
        System.out.println(action);
        
        try {
        	switch(action) {
        	case "/login":
        		 //When a login attempt occurs in login.jsp, it passes through doGet then calls our login function below
        		login(request,response);
        		break;
        	}
        }
        catch(Exception ex) {
        	throw new ServletException(ex);
        }
        /* Disabled the functions we're not currently using
        try {
            switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
            	insertPeople(request, response);
                break;
            case "/delete":
            	deletePeople(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
            	updatePeople(request, response);
                break;
            default:          	
            	listPeople(request, response);           	
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        */
    }

    private void listPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Account> listPeople = accountDAO.listAllPeople();
        request.setAttribute("listPeople", listPeople);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("PeopleList.jsp");       
        dispatcher.forward(request, response);
    }
 
    // to insert a people
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("InsertPeopleForm.jsp");
        dispatcher.forward(request, response);
    }
 
    // to present an update form to update an  existing Student
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String email = request.getParameter("email");
        Account existingPeople = accountDAO.getPeople(email);
        RequestDispatcher dispatcher = request.getRequestDispatcher("EditPeopleForm.jsp");
        request.setAttribute("people", existingPeople);
        dispatcher.forward(request, response); // The forward() method works at server side, and It sends the same request and response objects to another servlet.
 
    }
 
    // after the data of a people are inserted, this method will be called to insert the new people into the DB
    // 
    private void insertPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String birthday = request.getParameter("birthday");
        String gender = request.getParameter("gender");
        Account newAccount = new Account(email, firstName, lastName, password, birthday, gender);
        accountDAO.insert(newAccount);
        response.sendRedirect("list");  // The sendRedirect() method works at client side and sends a new request
    }
 
    private void updatePeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String email = request.getParameter("email");
        
        System.out.println(email);
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String birthday = request.getParameter("birthday");
        String gender = request.getParameter("gender");
        
        System.out.println(firstName);
        
        Account account = new Account(email, firstName, lastName, password, birthday, gender);
        accountDAO.update(account);
        response.sendRedirect("list");
    }
 
    private void deletePeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String email = request.getParameter("id");
        //People people = new People(id);
        accountDAO.delete(email);
        response.sendRedirect("list"); 
    }
   
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//get parameters from our login.jsp textboxes
    	 String username = request.getParameter("username");
    	 String password = request.getParameter("password");
    	 
    	 System.out.println(username);
    	 System.out.println(password);
    	 //simple if to compare the textbox contents with our users treemap
    	 if (username.equals("root") && password.equals("root1234")) {
			 System.out.println("Login Successful! Redirecting now! Welcome Boss");
			 HttpSession session = request.getSession();
			 session.setAttribute("username", username);
			 response.sendRedirect("AccountView.jsp");
    	 }
    	 else if(users.containsKey(username)) {
    		 if(users.get(username).equals(password)) {
    			 System.out.println("Login Successful! Redirecting now!");
    			 HttpSession session = request.getSession();		//create a httpsession to save our successful login request for convenience
    			 session.setAttribute("username", username);
    			 response.sendRedirect("AccountView.jsp");				//redirect our user to accountview
    		 }
    		 else {
    			 System.out.println("Invalid Password");
    		 }
    	 }
    	 else {
    		 System.out.println("The specified user does not exist");
    	 }
    }
    
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
    	
    }
}