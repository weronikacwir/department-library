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
				<th style='text-align: center'>ID</th>
				<th style='text-align: center'>Type</th>
				<th style='text-align: center'>Name</th>
				<th style='text-align: center'>Additional Info</th>
				<th style='text-align: center'>Available</th>
				<c:if test="${user != null}">
					<th style='text-align: center'>Operation</th>
				</c:if>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${items}" var="item">
				<tr>
					<td style='text-align: center'>${item.id}</td>
					<td>${item.type}</td>
					<td><a href="ItemLog?id=${item.id}">${item.name}</a></td>
					<td>${item.additionalInfo}</td>
					<td style='text-align: center'>
						<c:choose>
							<c:when test="${item.available}">Yes</c:when>
							<c:otherwise>No</c:otherwise>
						</c:choose></td>
					<c:if test="${user != null}">
						<td style='text-align: center'><a
							href='EditItem?id=${item.id}'>Edit</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${user != null}">
		<p>
			<a href='AddItems'>Add Items</a> | <a href='EditType'>Edit Types</a>
		</p>
	</c:if>

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