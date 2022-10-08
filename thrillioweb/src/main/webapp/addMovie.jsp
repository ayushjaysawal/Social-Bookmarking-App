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
			<a href="<%=request.getContextPath()%>/addBook.jsp" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">AddBook</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Logout</a>
			</b>
		</div>	         
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/bookmark/addMovies">
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
        		<td><label>Release Year:</label></td>
        		<td>
        			<input type="text" name="releaseYear"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Actor:</label></td>
        		<td>
        			<input type="text" name="actor"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Director:</label></td>
        		<td>
        			<input type="text" name="director"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>IMDB Rating:</label></td>
        		<td>
        			<input type="text" name="movieRating"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Movie Genre:</label></td>
        		<td>
        			<select name = "genre">
				      <option>Classics</option>
				      <option>Drama</option>
				      <option>Scifi_and_Fantasy</option>
				      <option>Comedy</option>
				      <option>Children_and_Family</option>
				      <option>Action_and_Adventure</option>
				      <option>Thrillers</option>
				      <option>Music_and_Musicals</option>
				      <option>Television</option>
				      <option>Horror</option>
				      <option>Special_Interest</option>
				      <option>Independent</option>
				      <option>Sports_and_Fitness</option>
				      <option>Anime_and_Animation</option>
				      <option>Classic_movie_Musicals</option>
				      <option>Faith_and_Spirituality</option>
				      <option>Foreign_Dramas</option>
				      <option>Romantic</option>
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