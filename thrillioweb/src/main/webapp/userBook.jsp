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
			<a href="<%=request.getContextPath()%>/bookmark" style="font-size:16px;color:white;margin-left:1150px;text-decoration:none;">Books</a>
			</b>
		</div>	         
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/bookmark/addUserBook">
      <fieldset>
	    <legend>Add Book</legend>	    
	    <table>
	    	<tr>
	    		<td><label>Title:</label></td>
        		<td>
        			<input type="text" name="title"><br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label>Publish Year:</label></td>
        		<td>
        			<input type="text" name="publishYear"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td><label>Author:</label></td>
        		<td>
        			<input type="text" name="author"><br>
        		</td>        
        	</tr>
        	
        	<tr>
        		<td>&nbsp;</td>
        		<td><input type="submit" name="submitRegisterForm" value="Add"></td>
        	</tr>
        </table>
	  </fieldset>      
    </form>
</body>
</html>