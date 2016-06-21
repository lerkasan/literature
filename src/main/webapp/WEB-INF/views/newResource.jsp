<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="cf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.error {
	color: red;
	font-weight: bold;
	font-size: 10px
}
</style>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet"
	type="text/css">
</head>
<body>
	<cf:form method="POST" action="add" modelAttribute="resource">
		<div align="center">
			<table id="fill">
			<tr><th colspan="2"><center>Enter properties of new RSS resource:</center></th></tr>
				<tr>
					<td><cf:label path="name">Name:</cf:label></td>
					<td><cf:input path="name" /><br /> <cf:errors path="name"
							cssClass="error" /></td>
				</tr>
				<tr>
					<td><cf:label path="url">URL:</cf:label></td>
					<td><cf:input path="url" /><br /> <cf:errors path="url"
							cssClass="error" /></td>
				</tr>
				<tr>
					<td><cf:label path="domain">Domain:</cf:label></td>
					<td><cf:input path="domain" /><br /> <cf:errors
							path="domain" cssClass="error" /></td>
				</tr>
				<tr>
					<td><cf:label path="responseFormat">Response format</cf:label></td>
					<td><cf:input path="responseFormat" value="rss" readonly="true"/><br /> <cf:errors
							path="responseFormat" cssClass="error" /></td>
				</tr>
				<tr align="right">
					<td colspan="2"><div align="right"><input type="submit" value="Submit" align="right"/></div></td>
				</tr>
			</table>
		</div>
	</cf:form>
</body>
</html>