<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Add Library Items</title>
<link rel='stylesheet' href='https://csns.calstatela.edu/css/style.css'>
</head>

<body>

	<form action='AddItems' method='post'>
		<table border='1' cellpadding='2' cellspacing='2'
			style='text-align: left'>
			<tbody>

				<tr>
					<td>Type:</td>
					<td>
						<select name='type'>
							<c:forEach items="${types}" var="type">
								<option>${type}</option>
							</c:forEach>
					</td>
				</tr>

				<tr>
					<td>Name:</td>
					<td><input name='name' size='60' required /></td>
				</tr>

				<tr>
					<td>Additional Info:</td>
					<td><input name='info' size='60' /></td>
				</tr>

				<tr>
					<td># of Copies:</td>
					<td><input name='copies' size='8' required /></td>
				</tr>

				<tr>
					<td colspan=2><input type='submit' name='add' value='Add' /></td>
				</tr>

			</tbody>
		</table>
	</form>

	<c:choose>
		<c:when test="${user != null}">
			<p>
				<a href='LibraryLogout'>Logout</a>
			</p>
		</c:when>
		<c:otherwise>
			<p>
				<a href='LibraryLogin'>Login</a>
			</p>
		</c:otherwise>
	</c:choose>


</body>
</html>