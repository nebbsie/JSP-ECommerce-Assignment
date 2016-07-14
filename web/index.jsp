<!DOCTYPE html>
<html>
<head>
	<title>onlineShopping-home</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>



</head>
<body>

	<div class="container">
		
                <ul>
                <li><a style="color: white;font-size: 150%;"href=>onlineShopping</a></li>
                <ul style="float:right;list-style-type:none;">
                    <li><a href="index.html">Home</a></li>
                    <li><a href="help.html">Help</a></li>
                    <li ><a href="signOut">Sign Out></i></a></li>
                </ul>
            </ul>


		
		<div class="login">
		<h2 class="homeSub">Login</h2>
			<form class="panel panel-default" role="form" action="controller" method="post">
				  
				  <div class="form-group">
				    <input type="text" name="username" class="form-control" placeholder="Enter Username">
                                    <%
                                        if(request.getSession().getAttribute("Re Enter Username").equals("true"))
                                        {
                                            %>
                                            <p style="color: red;">*Re Enter Username</p>
                                            <%
                                        }
                                        %>
				  </div>
				  <div class="form-group">
				  	<input type="password" name="password" class="form-control" placeholder="Enter Password">
                                        <%
                                        if(request.getSession().getAttribute("Re Enter Password").equals("true"))
                                        {
                                            %>
                                            <p style="color: red;">*Re Enter Password</p>
                                            <%
                                        }
                                        %>
				  </div>
				  </br>
				  <input type="hidden" name="command" value="login" />
				  <button type="submit" class="btn btn-default">Login</button>
			</form>
		</div>

		
		</div>



</body>
</html>