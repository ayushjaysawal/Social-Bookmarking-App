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
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>          
		<div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
			<b>
			<a href="<%=request.getContextPath()%>/addBook.jsp" style="font-size:16px;color:white;margin-left:1150px;text-decoration:none;">AddBook</a>
			<a href="<%=request.getContextPath()%>/addMovie.jsp" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">AddMovie</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Logout</a>
			</b>
		</div> 
	</div>
	<br><br>
	
	<div style="font-size: 24px;color: #333333;padding: 15px 0px 0px;border-bottom: #333333 1px solid;clear: both;">Saved Items</div>
	<br><br>
	<table>
    <tr> 
	  <td>   
	  <div style="font-size: 12px;color: #333333;padding: 15px 0px 0px;border-bottom: #333333 1px solid;clear: both;">User Books</div>
	  <br><br>
    <c:choose>
		<c:when test="${!empty(userbooks)}">
			<table>
			   <c:forEach var = "book" items="${userbooks}">
			     <tr>
				   <td style="color:gray;">
				     Title: <span style="color: #B13100;">${book.title}</span>
					 <br><br>
					 By: <span style="color: #B13100;">${book.authors[0]}</span>
					 <br><br>
					 Publication Year: <span style="color: #B13100;">${book.publicationYear}</span>
					 <br>
					 <a href = "<%=request.getContextPath()%>/bookmark/removeubook?bid=${book.id}" style="font-size:12px;color:#0058A6;font-weight:bold;text-decoration:none">Remove</a>
					</td>
				  </tr>
				  <tr>
		     	    <td>&nbsp;</td>
		  		  </tr>
		  		 
			   </c:forEach>
			   
			</table>
     	</c:when>
     <c:otherwise>
		<br><br>
       	<span style="font-size: 24px;color: #333333;margin:100px;">No User Books!</span>
     </c:otherwise>
    </c:choose>
    </td>
     
    <td style="padding-left: 700px; vertical-align: top;">
       <div style="font-size: 12px;color: #333333;padding: 15px 0px 0px;border-bottom: #333333 1px solid;clear: both;">User Movies</div>
         <c:choose>
		<c:when test="${!empty(usermovies)}">
			<table>
			  <c:forEach var = "movie" items="${usermovies}">
	     <tr>
		   <td style="color:gray;">
			 Title <span style="color: #B13100;">${movie.title}</span>
			 <br><br>
			 Director: <span style="color: #B13100;">${movie.directors[0]}</span>
			 <br><br>
			 Release Year: <span style="color: #B13100;">${movie.releaseyear}</span>
			 <br>
			 <a href = "<%=request.getContextPath()%>/bookmark/remove?mid=${movie.id}" style="font-size:12px;color:#0058A6;font-weight:bold;text-decoration:none">Remove</a>
		   </td>
		  </tr>
		  <tr>
     	    <td>&nbsp;</td>
  		  </tr>
  		 
	   </c:forEach>
			</table>
     	</c:when>
     <c:otherwise>
		<br><br>
       	<span style="font-size: 24px;color: #333333;margin:100px;">No User Movies!</span>
     </c:otherwise>
    </c:choose>
    </td>
    </tr>
    </table>
</body>
</html>