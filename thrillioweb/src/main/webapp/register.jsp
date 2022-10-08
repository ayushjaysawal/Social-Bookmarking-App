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
			<a href="<%=request.getContextPath()%>/login.jsp" style="font-size:16px;color:white;margin-left:1150px;text-decoration:none;">Login</a>
			</b>
		</div>	         
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/register">
      <fieldset>
	    <legend>Register</legend>	    
	    <table>
	    	<tr>
	    		<td><label>Email:</label></td>
        		<td>
        			<input type="text" name="email"><br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label>Password:</label></td>
        		<td>
        			<input type="password" name="password"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>First_Name:</label></td>
        		<td>
        			<input type="text" name="first_name"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Last_Name:</label></td>
        		<td>
        			<input type="text" name="last_name"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Gender:</label></td>
        		<td>
        			<select name = "gender">
				      <option>Male</option>
				      <option>Female</option>
				      <option>Other</option>
			        </select>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Kid_Friendly:</label></td>
        		<td>
        			<select name = "kidFriendly">
				      <option>On</option>
				      <option>Off</option>
			        </select>
        		</td>        
        	</tr>
        	
        	
        	<tr>
        		<td>&nbsp;</td>
        		<td><input type="submit" name="submitRegisterForm" value="Log In"></td>
        	</tr>
        </table>
	  </fieldset>      
    </form>
</body>
</html>