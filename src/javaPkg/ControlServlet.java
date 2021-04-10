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
    private AccountDAO accountDAO = new AccountDAO();
    private ImageDAO imageDAO = new ImageDAO();
    private FollowDAO followDAO = new FollowDAO();
    private TagDAO tagDAO = new TagDAO();
    private ImageTagDAO imageTagDAO = new ImageTagDAO();
    private LikeDAO likeDAO = new LikeDAO();
    private String currentUser;
    
    public ControlServlet() {
    	
    }
    
    public void init() {
        accountDAO = new AccountDAO(); 
        imageDAO = new ImageDAO();
        followDAO = new FollowDAO();
        tagDAO = new TagDAO();
        imageTagDAO = new ImageTagDAO();
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
        	case "/feed":
        		feedPage(request,response);
        		break; 
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	case "/coolImages":
        		rootPage(request,response, "coolImages");
        		break;
        	case "/recentImages":
        		rootPage(request, response, "recentImages");
        		break;
        	case "/viralImages":
        		rootPage(request, response, "viralImages");
        		break;
        	case "/poorImages":
        		rootPage(request, response, "poorImages");
        		break;
        	case "/topUsers":
        		getUserList(request, response, "topUsers");
        		request.setAttribute("view", "Top Users!");
        		break;
        	case "/popularUsers":
        		getUserList(request, response, "popularUsers");
        		request.setAttribute("view", "Popular Users!");
        		break;
        	case "/positiveUsers":
        		getUserList(request, response, "positiveUsers");
        		request.setAttribute("view", "Positive Users!");
        		break;
        	case "/inactiveUsers":
        		getUserList(request, response, "inactiveUsers");
        		request.setAttribute("view", "Inactive Users! :(");
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
        	case "/follow":
        		follow(request,response);
        		break;
        	case "/postForm":
        		postImage(request,response);
        		break;
        	case "/delete":
        		deleteImage(request, response);
        		break;
        	case "/updateImage":
        		updateImage(request, response);
        		break;
        	case "/like":
        		System.out.println(request.getParameter("id"));
        		if (!likeDAO.check(currentUser, Integer.parseInt(request.getParameter("id")))) {
        			likeImage(request,response);
        		}
        		else {
        			System.out.println("you have already liked this photo!");
        			feedPage(request,response);
        		}
        		break;
        	case "/dislike":
        		System.out.println(request.getParameter("id"));
        		if (likeDAO.check(currentUser, Integer.parseInt(request.getParameter("id")))) {
        			dislikeImage(request,response);
        		}
        		else {
        			System.out.println("you already don't like this photo!");
        			feedPage(request,response);
        		}
        		break;
        	}
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        	//throw new ServletException(ex);
        }
    }

	private void feedPageView(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
    	List<Image> images = imageDAO.getImageView(view, currentUser);
    	//List<Integer> likes = null;
    	for (int i = 0; i < images.size(); i = i+1) {
    		//likes.add(likeDAO.likeCount(images.get(i).getImageId()));
    		Image temp = images.get(i);
    		temp.setLikeCount(likeDAO.likeCount(temp.getImageId()));
    		images.set(i, temp);
    	}
    	request.setAttribute("username", currentUser);
    	request.setAttribute("listImages", images); 
    	//request.setAttribute("listLikes", likes);
    	request.getRequestDispatcher("feedPage.jsp").forward(request,response);
    }

	private void likeImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
    	//String url = request.getParameter("url");
    	int id = Integer.parseInt(request.getParameter("id"));
    	likeDAO.insert(new Like(currentUser,id));
    	feedPage(request,response);
    }
    
    private void dislikeImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
    	//String url = request.getParameter("url");
    	int id = Integer.parseInt(request.getParameter("id"));
    	likeDAO.delete(new Like(currentUser,id));
    	feedPage(request,response);
    }
    
    private void updateImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
		String newDesc = request.getParameter("description");
    	String url = request.getParameter("url");
		imageDAO.update(new Image(url, currentUser, newDesc));
    	feedPage(request,response);
    }
    
    private void deleteImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
    	String url = request.getParameter("url");
    	imageDAO.delete(new Image(url, currentUser, ""));
    	feedPage(request,response);
    }
    
    private void postImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	System.out.println(currentUser);
    	String url = request.getParameter("url");
    	String desc = request.getParameter("description");
		Image newImg = new Image(url, currentUser, desc);
		int imageId = -1;
		int tagId = -1;
		
		imageDAO.insert(newImg);
		imageId = imageDAO.retrieveImageId(currentUser, url);
		
		String tagsInput = request.getParameter("tags");
		if(!tagsInput.isEmpty()) {
			String[] tags = tagsInput.split(",");
			for(String s: tags) {
				tagId = tagDAO.checkExists(new Tag(s));
				System.out.println(imageId + " " + tagId + " " + s);
				imageTagDAO.insert(new ImageTag(imageId, tagId));
			}
		}
		feedPage(request, response);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		currentUser = "";
		response.sendRedirect("login.jsp");
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
			 rootPage(request, response, "");
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
	   	 		response.sendRedirect("login.jsp");
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
    
    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
    	System.out.println("made it here");
    	List<Image> images = imageDAO.getRootView(view);
    	request.setAttribute("listImages", images);
    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
    }
    
    private void feedPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	List<Image> images = imageDAO.getFeed(currentUser);
    	//List<Integer> likes = null;
    	for (int i = 0; i < images.size(); i = i+1) {
    		//likes.add(likeDAO.likeCount(images.get(i).getImageId()));
    		Image temp = images.get(i);
    		temp.setLikeCount(likeDAO.likeCount(temp.getImageId()));
    		images.set(i, temp);
    	}
    	request.setAttribute("username", currentUser);
    	request.setAttribute("listImages", images); 
    	//request.setAttribute("listLikes", likes);
    	request.getRequestDispatcher("feedPage.jsp").forward(request,response);
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	//implements the community page by requesting appropriate data to display
    	List<Account> users = accountDAO.listAllPeople();
    	List<Boolean> follows = followDAO.followList(currentUser);
    	request.setAttribute("userList", users);
    	request.setAttribute("followList", follows);
    	request.getRequestDispatcher("CommunityPage.jsp").forward(request, response);
    }
    
    private void getUserList(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
    	List<Account> users = accountDAO.getUserView(view);
    	request.setAttribute("userList", users);
    	request.getRequestDispatcher("userView.jsp").forward(request, response);
    }
    
    private void follow(HttpServletRequest request, HttpServletResponse response) throws SQLException,ServletException, IOException{
    	boolean status = Boolean.parseBoolean(request.getParameter("status"));
    	
    	Follow followObj = new Follow(currentUser,request.getParameter("email"));
    	try {
	    	if(status) {
	    		followDAO.delete(followObj);
	    	}
	    	else {
	    		followDAO.insert(followObj);
	    	}
	    	listUsers(request,response);
    	}
    	catch(SQLException e) {
    		System.out.println(e.getMessage());
    		listUsers(request,response);
    	}
    }
    
}