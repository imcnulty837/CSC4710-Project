package javaPkg;
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
import javax.servlet.annotation.WebServlet;
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
    
 
    public ControlServlet() {
    	
    }
    
    public void init() {
        accountDAO = new AccountDAO(); 
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        
        try {
        	switch(action) {
        	case "/login":
        		 //When a login attempt occurs in login.jsp, it passes through doGet then calls our login function below
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		accountDAO.initialize();
        		System.out.println("Database successfully initialized!");
        		response.sendRedirect("rootView.jsp");
        		break;
        	}
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        	throw new ServletException(ex);
        }
    }

    
	/*
	 * private void listPeople(HttpServletRequest request, HttpServletResponse
	 * response) throws SQLException, IOException, ServletException { List<Account>
	 * listPeople = accountDAO.listAllPeople(); request.setAttribute("listPeople",
	 * listPeople); RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("PeopleList.jsp"); dispatcher.forward(request,
	 * response); }
	 * 
	 * // to insert a people private void showNewForm(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("InsertPeopleForm.jsp");
	 * dispatcher.forward(request, response); }
	 * 
	 * // to present an update form to update an existing Student private void
	 * showEditForm(HttpServletRequest request, HttpServletResponse response) throws
	 * SQLException, ServletException, IOException { String email =
	 * request.getParameter("email"); Account existingPeople =
	 * accountDAO.getPeople(email); RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("EditPeopleForm.jsp");
	 * request.setAttribute("people", existingPeople); dispatcher.forward(request,
	 * response); // The forward() method works at server side, and It sends the
	 * same request and response objects to another servlet.
	 * 
	 * }
	 * 
	 * // after the data of a people are inserted, this method will be called to
	 * insert the new people into the DB // private void
	 * insertPeople(HttpServletRequest request, HttpServletResponse response) throws
	 * SQLException, IOException { String email = request.getParameter("email");
	 * String firstName = request.getParameter("firstName"); String lastName =
	 * request.getParameter("lastName"); String password =
	 * request.getParameter("password"); String birthday =
	 * request.getParameter("birthday"); String gender =
	 * request.getParameter("gender"); Account newAccount = new Account(email,
	 * firstName, lastName, password, birthday, gender);
	 * accountDAO.insert(newAccount); response.sendRedirect("list"); // The
	 * sendRedirect() method works at client side and sends a new request }
	 * 
	 * private void updatePeople(HttpServletRequest request, HttpServletResponse
	 * response) throws SQLException, IOException { String email =
	 * request.getParameter("email");
	 * 
	 * System.out.println(email); String firstName =
	 * request.getParameter("firstName"); String lastName =
	 * request.getParameter("lastName"); String password =
	 * request.getParameter("password"); String birthday =
	 * request.getParameter("birthday"); String gender =
	 * request.getParameter("gender");
	 * 
	 * System.out.println(firstName);
	 * 
	 * Account account = new Account(email, firstName, lastName, password, birthday,
	 * gender); accountDAO.update(account); response.sendRedirect("list"); }
	 * 
	 * private void deletePeople(HttpServletRequest request, HttpServletResponse
	 * response) throws SQLException, IOException { String email =
	 * request.getParameter("id"); //People people = new People(id);
	 * accountDAO.delete(email); response.sendRedirect("list"); }
	 */
    
    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//get parameters from our login.jsp textboxes
    	 String username = request.getParameter("username");
    	 String password = request.getParameter("password");
    	 
    	 System.out.println("Echo1");
    	 //simple if to compare the textbox contents with our users treemap
    	 if (username.equals("root") && password.equals("root1234")) {
			 System.out.println("Login Successful! Redirecting now! Welcome Boss");
			 HttpSession session = request.getSession();
			 session.setAttribute("username", username);
			 response.sendRedirect("rootView.jsp");
    	 }
    	 else if(accountDAO.dbLogin(username,password)) {
    			 System.out.println("Login Successful! Redirecting now!");
    			 HttpSession session = request.getSession();		//create a httpsession to save our successful login request for convenience
    			 session.setAttribute("username", username);
    			 response.sendRedirect("accountView.jsp");				//redirect our user to accountview
    	 }
    	 else {
    		 System.out.println("Failed Login");
    	 }
    }
    
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
    	String username = request.getParameter("username");
   	 	String firstName = request.getParameter("firstName");
   	 	String lastName = request.getParameter("lastName");
   	 	String birthday = request.getParameter("birthday");
   	 	String gender = request.getParameter("gender");
   	 	String password = request.getParameter("password");
   	 	String confirm = request.getParameter("confirmation");
   	 	
   	 	if (password.equals(confirm)) {
   	 		if (!accountDAO.checkEmail(username)) {
	   	 		System.out.println("Registration Successful! Added to database, redirecting to Account View!");
	   	 		Account account = new Account(username, firstName, lastName, password, birthday, gender);
	   	 		accountDAO.insert(account);
	   	 		response.sendRedirect("accountView.jsp");
   	 		}
	   	 	else {
	   	 		System.out.println("Username taken, please enter new username");
	   	 		response.sendRedirect("register.jsp");
	   	 	}
   	 	}
   	 	else {
   	 		System.out.println("Password and Password Confirmation do not match");
   	 		response.sendRedirect("register.jsp");
   	 	}
    }
}