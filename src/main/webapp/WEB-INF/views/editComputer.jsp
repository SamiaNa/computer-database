<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
					<form:form action="EditComputer" method="POST"
						modelAttribute="computerDTO">
						<input type="hidden" value="${param.id}" name="id" id="id" />
						<fieldset>
							<div class="form-group">
								<form:label for="computerName" path="name">Computer name</form:label>
								<input data-validation="custom"
									data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* \.]+$"
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="name"
									value="${computer.name}">
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced">Introduced date</form:label>
								<input data-validation="date"
									data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" type="date"
									class="form-control" id="introduced"
									placeholder="Introduced date" name="introduced"
									value="${computer.introduced}">
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued">Discontinued date</form:label>
								<input data-validation="date"
									data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" type="date"
									class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date"
									value="${computer.discontinued}">
							</div>
							<div class="form-group">
								<form:label for="company" path="company">Company</form:label>
								<form:select class="form-control" name="companyId"
									id="companyId" path="company.id">
									<form:option value="${computer.company.id}">--</form:option>
									<c:forEach var="company" items="${companyList}">
										<c:choose>
											<c:when test="${company.id == computer.company.id}">
												<option selected value="${company.id}">${company.name}</option>

											</c:when>
											<c:otherwise>
												<option value="${company.id}">${company.name}</option>

											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
								<c:out value="${res}" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" name="submit" value="Edit"
								class="btn btn-primary"> or <a href="Dashboard"
								class="btn btn-default">Cancel</a>
						</div>
					</form:form>
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