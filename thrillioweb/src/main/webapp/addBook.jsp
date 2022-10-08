<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b> 
		<div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
			<b>
			<a href="<%=request.getContextPath()%>/bookmark/request" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Request</a>
			<a href="<%=request.getContextPath()%>/addMovie.jsp" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">AddMovie</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Logout</a>
			</b>
		</div>	         
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/bookmark/addBooks">
      <fieldset>
	    <legend>Details</legend>	    
	    <table>
	    	<tr>
	    		<td><label>Title:</label></td>
        		<td>
        			<input type="text" name="title"><br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label>Image:</label></td>
        		<td>
        			<input type="text" name="image"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Publication Year:</label></td>
        		<td>
        			<input type="text" name="publicationYear"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Publisher:</label></td>
        		<td>
        			<input type="text" name="publisher"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Author_Name:</label></td>
        		<td>
        			<input type="text" name="authorName"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Book Rating:</label></td>
        		<td>
        			<input type="text" name="bookRating"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Book Genre:</label></td>
        		<td>
        			<select name = "genre">
				      <option>Art</option>
				      <option>Biography</option>
				      <option>Children</option>
				      <option>Fiction</option>
				      <option>History</option>
				      <option>Mystery</option>
				      <option>Philosophy</option>
				      <option>Religion</option>
				      <option>Romance</option>
				      <option>Self_help</option>
				      <option>Technical</option>
				      
			        </select>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Kid Friendly:</label></td>
        		<td>
        			<select name = "kidFriendly">
				      <option>Approved</option>
				      <option>Rejected</option>
				      <option>Unknown</option>
			        </select>
        		</td>        
        	</tr>
        	
        	<tr>
        		<td>&nbsp;</td>
        		<td><input type="submit" name="submitRegisterForm" value="Log In"></td>
        	</tr>
        	<tr>
        		<td>&nbsp;</td>
        		<td><input type="Reset"></td>
        	</tr>
        	
        </table>
	  </fieldset>      
    </form>
</body>
</html>