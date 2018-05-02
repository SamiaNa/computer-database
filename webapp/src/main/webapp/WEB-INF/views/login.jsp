<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http:
//www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login Page</title>
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
					code="application.applicationName" /></a>
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
					<h1>
						<spring:message code="editComputer.editComputer" />
					</h1>
					<form:form action="EditComputer" method="POST"
						modelAttribute="computer">
						<fieldset>
							<div class="form-group">
								Login
									<input id="login"/>
							</div>
							<div class="form-group">
								Password
									<input id="password"/>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" name="submit"
								value=<spring:message code="editComputer.edit"/>
								class="btn btn-primary">
							<spring:message code="editComputer.or" />
							<a href="Dashboard" class="btn btn-default"><spring:message
									code="editComputer.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	

</body>
</html>