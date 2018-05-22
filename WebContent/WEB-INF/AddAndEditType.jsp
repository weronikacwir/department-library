<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Department Library Catalog</title>
<link rel='stylesheet' href='https://csns.calstatela.edu/css/style.css'>
</head>

<body>

	<table border='1' cellpadding='2' cellspacing='2'>

		<thead>
			<tr>
				<th style='text-align: center'>Type</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${types}" var="type">
				<tr>
					<td>
						<form action='EditType' method='post'>
							<input type='hidden' name='originalType' value='${type}' />
							<input type='text' name='editedType' value='${type}' />
							<input type='submit' name='edit' value='Edit' />
						</form>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td>
					<form action='AddType' method='post'>
						<input type='text' name='newType' />
						<input type='submit' name='add' value='Add' />
					</form>
				</td>
			</tr>
		</tbody>
	</table>

	<p>
		<a href='DisplayItems'>Back To Catalog</a>
	</p>

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