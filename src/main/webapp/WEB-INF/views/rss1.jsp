<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
<link rel="stylesheet" href="resources/css/style.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script>
	$(function() {
		$(".tabrow li").click(function(e) {
			<!-- e.preventDefault(); -->
			$(".tabrow li").removeClass("selected");
			$(this).addClass("selected");
		});
	});
</script>
<style>
.tabrow a {
	color: #555;
	text-decoration: none;
}
</style>
</head>
<body>
	<div align="center">
		<form>
			<input type="text" name="searchQuery" placeholder="Search...">&nbsp;<input
				type="submit" id="searchButton" value="Search" />
		</form>
	</div>

	<div align="center">

		<ul class="tabrow">
			<c:forEach var="rss" items="${rssList}">
				<li> <a href="<c:out value='/literature/rss/${rss.name}'/>"><c:out
							value="${rss.name}" /></a></li>
			</c:forEach>
		</ul>

		<a href="resource/new">Add new RSS</a>


	</div>
	<div align="center">
		<table>
		<c:set var="i" value="${0}"/>
			<c:forEach var="news" items="${rssNews}">
				<tr>
					<td><input type="checkbox" name="selectedRss" value="${i}" /></td>
					<td> <a href="<c:out value='${news.link}'/>"><c:out value="${news.title}" /></a>
					</td>
					<td><c:out value="${news.author}" /></td>
					<td><c:out value="${news.publishedDate}" /></td>
				</tr>
				<c:set var="i" value="${i + 1}" />
			</c:forEach>
		</table>
	</div>
</body>
</html>
