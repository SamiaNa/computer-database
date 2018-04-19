<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<body><%@ taglib uri="http://java.sun.com/jsp/jstl/core"
		prefix="c"%>

	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard.jsp"> Application -
			Computer Database </a>
	</div>
	</header>

	<c:if test="${companyList == null}">
		<c:redirect url="/AddComputer" />
	</c:if>
	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form:form action="AddComputer" method="POST" modelAttribute="computerDTO">
					<fieldset>
						<div class="form-group">
							<form:label for="computerName" path="name">Computer name</form:label> 
							<input data-validation="custom" data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* \.]+$" 
								type="text" class="form-control" name="name" id="computerName"
								placeholder="Computer name">
						</div>
						<div class="form-group">
							<form:label for="introduced" path="introduced">Introduced date</form:label> 
							<input data-validation="date" data-validation-format="yyyy-mm-dd"
								data-validation-optional="true"
								type="date" class="form-control" name="introduced"
								id="introduced" placeholder="Introduced date">
						</div> 
						<div class="form-group">
							<form:label for="discontinued" path="discontinued">Discontinued date</form:label> 
							<input data-validation="date" data-validation-format="yyyy-mm-dd"
								data-validation-optional="true"
								type="date" class="form-control" name="discontinued"
								id="discontinued" placeholder="Discontinued date">
						</div>
						<div class="form-group">
							<form:label for="companyId" path="company" >Company</form:label> 
							<form:select
								class="form-control" name="companyId" path="company.id" id="company">
								<option value="-1"> </option>
								<c:forEach var="company" items="${companyList}">
									<form:option value="${company.id}">${company.name}</form:option>
								</c:forEach>
</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" name="submit" value="Add"
							class="btn btn-primary"> or <a
							href="Dashboard" class="btn btn-default">Cancel</a>
					</div>
				</form:form>
				${res}
			</div>
		</div>
	</div>
	</section>
</body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
  $.validate({
    lang: 'en',
    modules:'html5'
  });
</script>
</html>