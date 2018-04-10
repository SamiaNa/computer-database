<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.jsp"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${param.id}" />
					</div>
					<h1>Edit Computer</h1>
					<form action="EditComputer" method="POST">
						<input type="hidden" value="${param.id}" name="id" id="id" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									data-validation="custom"
									data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* \.]+$"
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="name"
									value="${computer.name}">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									data-validation="date" data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" type="date"
									class="form-control" id="introduced"
									placeholder="Introduced date" name="introduced"
									value="${computer.introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									data-validation="date" data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" type="date"
									class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date"
									value="${computer.discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" name="companyId" id="companyId">
									<option value="0">--</option>
									<c:forEach var="company" items="${companyList}">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>

								</select>
								<c:out value="${res}" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" name="submit" value="Edit"
								class="btn btn-primary"> or <a
								href="Dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
	$.validate({
		lang : 'en',
		modules : 'html5'
	});
</script>
</html>