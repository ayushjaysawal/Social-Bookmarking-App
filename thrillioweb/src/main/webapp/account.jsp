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
			<a href="<%=request.getContextPath()%>/bookmark/movie" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Movie</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Logout</a>
			</b>
			
		</div>		
	</div>
	<br><br>
	
			 First Name : <span style="color: #B13100;">${accounts.firstname}</span>
			 <br><br>
			 Last Name : <span style="color: #B13100;">${accounts.lastname}</span>
			 <br><br>
			 Gender : <span style="color: #B13100;">${accounts.gender}</span>
			 <br><br>
			 Email : <span style="color: #B13100;">${accounts.email}</span>
			 <br><br>
			 Kid_Friendly Status : <span style="color: #B13100;">${accounts.kid_friendly}</span>
			 <br><br>
			 Change Password : <a href = "<%=request.getContextPath()%>/changePassword.jsp" style="font-size:18px;color:#0058A6;font-weight:bold;text-decoration:none">Change Password</a>
		     <br><br>
			 Change Kid_Friendly Status : <a href = "<%=request.getContextPath()%>/changeKidFriendly.jsp" style="font-size:18px;color:#0058A6;font-weight:bold;text-decoration:none">Change Kid_Friendly</a> 
	
</body>
</html>