<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
</style>
<link rel="stylesheet" href="/literature/resources/css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Authors list:</title>
</head>
<body>
	<p align="left">Authors:</p>
	<div align="left" style="float: left">
		<c:forEach var="i" begin="65" end="90">
			<a
				href="/literature/author/letter/<%=Character.toChars((Integer) pageContext.getAttribute("i"))%>"><%=Character.toChars((Integer) pageContext.getAttribute("i"))%></a>&nbsp;
			</c:forEach>
		<br>
		<table class="list" id="list">
			<tr>
				<th>N</th>
				<th>Name</th>
				<th>Articles/Books in database</th>
			</tr>
			<c:choose>
				<c:when test="${not empty authors}">
					<c:set var="i" value="${1}" />
					<c:forEach var="author" items="${authors}">
						<tr>
							<td><c:out value="${i}" /></td>
							<td><c:out value="${author.fullName}" /></td>
							<td><a href="/literature/author/${author.id}"> <c:out
										value="${fn:length(author.itemsToRead)}" /></a></td>
						</tr>
						<c:set var="i" value="${i + 1}" />
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
	</div>

	<div style="float: left">
		<table>
			<tr>
				<td valign="top"><c:if test="${not empty items}">
						<b>Materials written by <c:out value="${author.fullName}" />:
						</b>
						<br>
						<table>
							<c:forEach var="item" items="${items}">
								<tr>
									<td valign="top"><a href="<c:out value='${item.url}'/>">
											<c:out value="${item.title}" />
									</a></td>
								</tr>
							</c:forEach>
						</table>
					</c:if></td>
			</tr>
		</table>
	</div>
</body>
</html>