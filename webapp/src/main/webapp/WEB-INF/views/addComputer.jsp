<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http:
//www.w3.org/TR/html4/loose.dtd">
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
		<a class="navbar-brand" href="Dashboard"> <spring:message
				code="application.applicationName" />
		</a>
		<div class="pull-right">
			<form id="logout-form" action="<c:url value="/logout"/>"
				method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<a href="#" class="navbar-brand"
				onclick="document.getElementById('logout-form').submit();">
				Logout </a>
		</div>
	</div>
	</header>


	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>
					<spring:message code="addComputer.addComputer" />
				</h1>
				<form:form action="AddComputer" method="POST"
					modelAttribute="computerDTO">
					<fieldset>
						<div class="form-group">
							<spring:message code="computer.name" var="computerName" />
							<label for="computerName"> ${computerName} </label>
							<form:input data-validation="custom"
								data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* \.]+$"
								type="text" class="form-control" name="name" id="computerName"
								path="name" placeholder="${computerName}" />
						</div>
						<div class="form-group">
							<spring:message code="computer.introducedDate"
								var="introducedDate" />
							<label for="introduced"> ${introducedDate} </label>
							<form:input data-validation="date"
								data-validation-format="yyyy-mm-dd"
								data-validation-optional="true" type="date" class="form-control"
								name="introduced" id="introduced" path="introduced"
								placeholder="${introducedDate}" />
						</div>
						<div class="form-group">
							<spring:message code="computer.discontinuedDate"
								var="discontinuedDate" />
							<label for="discontinued"> ${discontinuedDate } </label>
							<form:input data-validation="date"
								data-validation-format="yyyy-mm-dd"
								data-validation-optional="true" type="date" class="form-control"
								name="discontinued" id="discontinued" path="discontinued"
								placeholder="${discontinuedDate}" />
						</div>
						<div class="form-group">
							<form:label for="company" path="company">
								<spring:message code="computer.company" />
							</form:label>
							<form:select class="form-control" name="companyId"
								path="company.id" id="companyId">
								<form:option value="-1">--</form:option>
								<c:forEach var="company" items="${companyList}">
									<form:option value="${company.id}">${company.name}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" name="submit"
							value=<spring:message code="addComputer.add"/>
							class="btn btn-primary">
						<spring:message code="addComputer.or" />
						<a href="Dashboard" class="btn btn-default"><spring:message
								code="addComputer.cancel" /></a>
					</div>
				</form:form>
				${res}
			</div>
		</div>
	</div>
	</section>
</body>

</html>