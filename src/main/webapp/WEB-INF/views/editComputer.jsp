<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
			<a class="navbar-brand" href="Dashboard"> <spring:message code="application.applicationName"/></a>
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
					<h1><spring:message code="editComputer.editComputer"/></h1>
					<form action="EditComputer" method="POST">
				
						<input type="hidden" name="id" id="id" value="${param.id}"></input>
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="computer.name"/></label> <input
									data-validation="custom"
									data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* \.]+$"
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="name"
									value="${computer.name}">
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="computer.introducedDate"/></label> <input
									data-validation="date" data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" type="date"
									class="form-control" id="introduced"
									placeholder="Introduced date" name="introduced"
									value="${computer.introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="computer.discontinuedDate"/></label> <input
									data-validation="date" data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" type="date"
									class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date"
									value="${computer.discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId" path="company"><spring:message code="computer.company"/></label>
								 <select
									class="form-control" name="companyId" id="companyId" path="company.id">
									<option value="0">--</option>
									<c:forEach var="company" items="${companyList}">
													<option value="${company.id}">${company.name}</option>
										
									<!--  	<c:choose>
											<c:when test="${company.id == computer.company.id}">
												<option selected value="${company.id}">${company.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${company.id}">${company.name}</option>

											</c:otherwise>
										</c:choose>
										-->
									</c:forEach>
								</select>
								<c:out value="${res}" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" name="submit" value=<spring:message code="editComputer.edit"/>
								class="btn btn-primary"> <spring:message code="editComputer.or"/> <a href="Dashboard"
								class="btn btn-default"><spring:message code="editComputer.cancel"/></a>
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