<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard.html"> Application -
			Computer Database </a>
	</div>
	</header>

	<section id="main">
	<div class="container">
		<h1 id="homeTitle">
			<c:out value="${page.count} Computers found" />
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="/ComputerListServlet" method="GET"
					class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchsubmit" name="submit"
						value="Filter by name" class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer"
					href="./static/views/addComputer.jsp">Add Computer</a> <a
					class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();">Edit</a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="#" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th>Computer name</th>
					<th>Introduced date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued date</th>
					<!-- Table header for Company -->
					<th>Company</th>

				</tr>
			</thead>


			<!-- Browse attribute computers -->
			<tbody id="results">

				<c:forEach var="computer" items="${requestScope.page.DTOElements}">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="0"></td>
						<td><a href="editComputer.html" onclick=""> <c:out
									value="${computer.name}" /></a></td>
						<td><c:out value="${computer.introduced}" /></td>
						<td><c:out value="${computer.discontinued}" /></td>
						<td><c:out value="${computer.companyName}" /></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
	</section>

	<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<ul class="pagination">

			<c:if test="${page.number > 1}">
				<li>
				<a href=<c:url value="/ComputerListServlet?pageNumber=${page.number-1}&pageSize=${requestScope.page.size}"/>
					aria-label="Previous"> 
						<span aria-hidden="true">&laquo;</span>
				</a>
				</li>
			</c:if>

			<c:forEach var="i" begin="0" end="4">
				<c:if
					test="${(page.number + i <= (page.count / requestScope.page.size) && requestScope.page.count % requestScope.page.size == 0)
			|| (page.number+i <= (page.count / requestScope.page.size) + 1 && requestScope.page.count % requestScope.page.size != 0)}">
					<li>
					<a href=<c:url value="/ComputerListServlet?pageNumber=${page.number+i}&pageSize=${requestScope.page.size}" />
						><c:out value ="${page.number+i}"/></a></li>
				</c:if>
			</c:forEach>
			<c:if
				test="${(page.number + 1 < (requestScope.page.count / requestScope.page.size) && requestScope.page.count % requestScope.page.size == 0)
			|| (page.number + 1 <(requestScope.page.count / requestScope.page.size) + 1 && requestScope.page.count % requestScope.page.size != 0)}">
				<li><a
					href=<c:url
					value="/ComputerListServlet?pageNumber=${page.number+1}&pageSize=${requestScope.page.size}" />
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>


		</ul>

		<div class="btn-group btn-group-sm pull-right" role="group">

			<form
				action="ComputerListServlet?page.number=${page.number}&pageSize=10"
				method="post">
				<button type="submit" class="btn btn-default">10</button>
				<button type="submit" class="btn btn-default"
					formaction="ComputerListServlet?pageNumber=${page.number}&pageSize=50">50</button>
				<button type="submit" class="btn btn-default"
					formaction="ComputerListServlet?pageNumber=${page.number}&pageSize=100">100</button>
			</form>

		</div>
	</footer>
	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>


</body>
</html>