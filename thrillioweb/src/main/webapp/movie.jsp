<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>thrill.io</title>
</head>
<body style="font-family:Arial;font-size:20px;">
	<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0px 0px 0px 10px;color:white;text-decoration: none;">thrill.io</a></b>
		<div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
			<b>
			<a href="<%=request.getContextPath()%>/bookmark/mybooks" style="font-size:16px;color:white;margin-left:1150px;text-decoration:none;">Saved Items</a>
			<a href="<%=request.getContextPath()%>/bookmark" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Book</a>
			<a href="<%=request.getContextPath()%>/bookmark/account" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Account</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Logout</a>
			</b>
			
		</div>		
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/bookmark/searchmovie">
      <fieldset>
	    <legend>Search</legend>	    
	    <table>
	        <tr>
        		<td><label>Search By:</label></td>
        		<td>
        			<select name = "searchBy">
				      <option>Title</option>
				      <option>Rating</option>
				      
			        </select>
        		</td>        
        	</tr>
	    	<tr>	
        		<td>
        			<input type="text" name="Mname">        			
        		</td>
        	
        		<td>&nbsp;</td>
        		<td><input type="submit" name="submitSearchForm" value="search"></td>
        	</tr>
        	<tr>
        		<td><label>Didn't find Movie!:</label></td>
        		<td>
        			<a href = "<%=request.getContextPath()%>/userMovie.jsp" style="font-size:18px;color:#0058A6;font-weight:bold;text-decoration:none">AddMovie</a>
        		</td>        
        	</tr>
        </table>
	  </fieldset>      
    </form>
    
	<table>
	   <c:forEach var = "movie" items="${movies}">
	     <tr>
		   <td style="color:gray;">
			 By <span style="color: #B13100;">${movie.title}</span>
			 <br><br>
			 Rating: <span style="color: #B13100;">${movie.imdbRating}</span>
			 <br><br>
			 Publication Year: <span style="color: #B13100;">${movie.releaseyear}</span>
			 <br><br>
			 <a href = "<%=request.getContextPath()%>/bookmark/save?mid=${movie.id}" style="font-size:18px;color:#0058A6;font-weight:bold;text-decoration:none">Save</a>
		   </td>
		  </tr>
		  <tr>
     	    <td>&nbsp;</td>
  		  </tr>
  		 
	   </c:forEach>
	   
	</table>

</body>
</html>