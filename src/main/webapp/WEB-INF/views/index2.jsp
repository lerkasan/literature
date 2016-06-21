<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
<style>
</style>
</head>
<body>
	<div align="center">
		<form>
			<input type="text" name="searchQuery" placeholder="Search...">&nbsp;<input
				type="submit" id="searchButton" value="Search" />
		</form>
	</div>
	<a href="resource/new">Add new RSS</a>
	<div align="center">
	<table>
	</table>
	</div>
	<div align="center">
		<table>
			<c:forEach var="news" items="${rssNews}">
				<tr>
					<td><c:url value="${news.link}" var="url"/>
							<a href="<c:out value='${url}'/>"><c:out value="${news.title}" /></a>
						</td>
					<td><c:out value="${news.author}" /></td>
					<td><c:out value="${news.publishedDate}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
