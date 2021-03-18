package javaPkg;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

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
import java.util.List;
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
    private ImageDAO imageDAO;
    private FollowDAO followDAO;
    private String currentUser;
    
    public ControlServlet() {
    	
    }
    
    public void init() {
        accountDAO = new AccountDAO(); 
        imageDAO = new ImageDAO();
        followDAO = new FollowDAO();
        currentUser = "";
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
        	case "/community":
        		listUsers(request,response);
        		break;
        	}
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        	throw new ServletException(ex);
        }
    }
    
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    	//get parameters from our login.jsp textboxes
    	 String username = request.getParameter("username");
    	 String password = request.getParameter("password");
    	 
    	 //simple if to compare the textbox contents with our users treemap
    	 if (username.equals("root") && password.equals("root1234")) {
			 System.out.println("Login Successful! Redirecting now! Welcome Boss");
			 HttpSession session = request.getSession();
			 session.setAttribute("username", username);
			 response.sendRedirect("rootView.jsp");
    	 }
    	 else if(accountDAO.dbLogin(username,password)) {
		 	 currentUser = username;
			 System.out.println("Login Successful! Redirecting now!");
			 feedPage(request, response);
    	 }
    	 else {
    		 request.setAttribute("loginFailedStr","Login Failed: Please check your credentials.");
    		 request.getRequestDispatcher("login.jsp").forward(request, response);
    	 }
    }
    
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
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
	   	 		response.sendRedirect("feedPage.jsp");
   	 		}
	   	 	else {
	   	 		System.out.println("Username taken, please enter new username");
	    		 request.setAttribute("errorOne","Registration failed: Username taken, please enter a new username.");
	    		 request.getRequestDispatcher("register.jsp").forward(request, response);
	   	 	}
   	 	}
   	 	else {
   	 		System.out.println("Password and Password Confirmation do not match");
   		 request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
   		 request.getRequestDispatcher("register.jsp").forward(request, response);
   	 	}
    }
    
    private void feedPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	List<Image> images = imageDAO.getFeed(currentUser);
    	request.setAttribute("username", currentUser);
    	request.setAttribute("listImages", images);       
    	request.getRequestDispatcher("feedPage.jsp").forward(request,response);
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	List<Account> users = accountDAO.listAllPeople();
    	List<Boolean> follows = followDAO.followList(currentUser);
    	request.setAttribute("userList", users);
    	request.setAttribute("followList", follows);
    	request.getRequestDispatcher("CommunityPage.jsp").forward(request, response);
    }
}