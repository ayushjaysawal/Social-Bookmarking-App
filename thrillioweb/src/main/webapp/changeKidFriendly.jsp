<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0px 0px 0px 10px;color:white;text-decoration: none;">thrill.io</a></b>
		<div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
			<b>
			<a href="<%=request.getContextPath()%>/bookmark/account" style="font-size:16px;color:white;margin-left:1150px;text-decoration:none;">Account</a>
			</b>
		</div>		
	</div>
	<br><br>
	<div style="color:red">${errorMessage}</div>
	<form method="POST" action="<%=request.getContextPath()%>/bookmark/changeKidFriendly">
      <fieldset>
	    <legend>Change KidFriendly_Status</legend>	    
	    <table>
        	<tr>
        		<td><label>Enter Password:</label></td>
        		<td>
        			<input type="password" name="Cpassword"><br>
        		</td>        
        	</tr>
        	
        	<tr>
        		<td>&nbsp;</td>
        		<td><input type="submit" name="submitPassword" value="Change kidFriendly"></td>
        	</tr>
        </table>
	  </fieldset>      
    </form>
</body>
</html>