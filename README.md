# CSC4710-Project
Authors: Nicholas Foster HB7798, Ian McNulty GI5631

Recommended actions for running:
-ensure your current run configurations site set the working directory to the default workspace location variable commonly shown as ${workspace_loc:CSC4710-Project}
-ensure the project is properly linked to jstl-1.2.jar, mysql-connector-java-8.0.13.jar, and servlet-api.jar such that all servlet data is functional
-please ensure mysql exists on your local machine and has a "root" user with a "root1234" password

getting started:
-run login.jsp
-register a new user or review the pre-existing ones from the populatetables.sql file
-follow other users and post images per the constraints specified in the assignment
-posts are restricted to 5 per day per user
-likes are restricted to 3 per day per user
-enjoy =D

contributions:
Ian McNulty:
- Register.jsp
- ControlServlet functions
- AccountDAO/Account
- LikeDAO/Like
- FollowDAO/Follow
- TagDAO/Tag
- ImageTagDAO/Tag
- CommentsDAO/Comment
- comment view under image
- getUserView
- userview.jsp
- tagview.jsp
- tag.getView
- SQL View statement creation of views 7 - 10
Nicholas Foster:
- sqlBatchExecutor
- Databaseinit.sql
- populateTables.sql
- Login.jsp
- Feed Page.jsp
- Community Page.jsp
- rootView.jsp
- getUserList
- getCommonUser
- getImageView
- ControlServlet functions
- ImageDAO/Image
- Like/Dislike Button
- Follow/Unfollow Button
- Feed Page primary view
- Tag view under images
- SQL View statement creation of views 1 - 6