<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Library Login</title>
<link rel='stylesheet' href='https://csns.calstatela.edu/css/style.css'>
</head>

<body>

	<form action='LibraryLogin' method='post'>
		<br>Username:<input name='username' size='60' required />
		<br>Password:<input type='password' name='pw' size='60' required /> 
		<br><input type='submit' name='login' value='Login' /></br>
	</form>

</body>
</html>