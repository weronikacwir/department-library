<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Edit Library Item</title>
<link rel='stylesheet' href='https://csns.calstatela.edu/css/style.css'>
</head>

<body>

	<form action='EditItem' method='post'>
		<input type='hidden' name='id' value="${item.id}" />

		<table border='1' cellpadding='2' cellspacing='2'
			style='text-align: left'>
			<tbody>

				<tr>
					<td>ID:</td>
					<td>${item.id}</td>
				</tr>

				<tr>
					<td>Type:</td>
					<td><select name='type'>
							<c:forEach items="${types}" var="type">
								<c:choose>
									<c:when test="${item.type == type}">
										<option selected>${type}</option>
									</c:when>
									<c:otherwise>
										<option>${type}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td>Name:</td>
					<td><input name='name' value='${item.name}' size='60' required /></td>
				</tr>

				<tr>
					<td>Additional Info:</td>
					<td>
						<input name='info' value='${item.additionalInfo}' size='60' />
					</td>
				</tr>
				<tr>
					<td colspan=2><input type='submit' name='save' value='Save' /></td>
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