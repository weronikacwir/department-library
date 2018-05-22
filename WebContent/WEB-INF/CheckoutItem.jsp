<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Check Out Library Item</title>
<link rel='stylesheet' href='https://csns.calstatela.edu/css/style.css'>
</head>

<body>
	<div style="margin-left: 40px;">
		<p>
			ID:&nbsp;<strong>${item.id}</strong>
		</p>

		<p>
			Name:&nbsp;<strong>${item.name}</strong>
		</p>


		<form action='CheckoutItem' method='post'>
			<input type='hidden' name='id' value="${item.id}" /> <input
				type='hidden' name='borrowed' value="${borrowed}" />
			<table border="1" cellpadding="2" cellspacing="2">
				<tbody>

					<tr>
						<td>Date Borrowed:</td>
						<td>${borrowed}</td>
					</tr>

					<tr>
						<td>Due Back By (Optional):</td>
						<td><input type='text' name='due' size='60' /></td>
					</tr>

					<tr>
						<td>CIN:</td>
						<td><input name='cin' size='60' required /></td>
					</tr>

					<tr>
						<td>Name:</td>
						<td><input name='name' size='60' required /></td>
					</tr>

					<tr>
						<td colspan=2><input type='submit' name='checkout'
								value='Check Out' /></td>
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

	</div>

</body>
</html>