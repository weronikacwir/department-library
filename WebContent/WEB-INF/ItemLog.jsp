<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Checkout Log</title>
<link rel='stylesheet' href='https://csns.calstatela.edu/css/style.css'>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>

function currentDateToString() {
    var now = new Date();
	var day = now.getDate();
	var month = now.getMonth() + 1;
	var year = now.getFullYear();
	return month + '/' + day + '/' + year; 
}

$(function(){
	$(".return").click(function(){
		var cell = $(this).closest("td");
		var id = ${item.id}
	    $.ajax({
	        url: "AjaxReturnLibaryItem",
	        cache: false,
	        data: {
	            "id" : id
	        },
	        success: function(data){
	            cell.empty();
	            cell.text(currentDateToString());
	            var paragraph = $(".backandcheckout");
	            var backHTML = paragraph.html();
	            var checkOutHTML = "<a href='CheckoutItem?id=${item.id}'>Check Out</a>"
	            paragraph.html(backHTML + " | " + checkOutHTML);
	        }
	    });		
	});
});
</script>
</head>

<body>
	<div style="margin-left: 40px;">
		<p>
			ID:&nbsp;<strong>${item.id}</strong>
		</p>

		<p>
			Name:&nbsp;<strong>${item.name}</strong>
		</p>

		<p class="backandcheckout">
			<a href="DisplayItems">Back to Items</a>

			<c:if test="${user != null and item.available}"> 
				&nbsp;|&nbsp;<a href="CheckoutItem?id=${item.id}">Check Out</a>
			</c:if>
		</p>
		<table border="1" cellpadding="2" cellspacing="2">
			<thead>
				<tr>
					<th scope="col">CIN</th>
					<th scope="col">Name</th>
					<th scope="col">Date Borrowed</th>
					<th scope="col">Due Back By</th>
					<th scope="col">Date Returned</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${log}" var="entry">
					<tr>
						<td>${entry.cin}</td>
						<td>${entry.name}</td>
						<td style='text-align: center'>${entry.borrowed}</td>
						<td style='text-align: center'>${entry.due}</td>
						<td style='text-align: center'>
							<c:choose>
								<c:when test='${entry.returned == null and user != null}'>
									<button class="return">Return</button>
								</c:when>
								<c:otherwise>
									${entry.returned}
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>

		</table>

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