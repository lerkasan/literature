<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="cf"%>
 
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
<link rel="stylesheet" href="/literature/resources/css/style.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script>
	$(function() {
		$(".tabrow li").click(function(e) {
			<!--
			e.preventDefault();
			-->
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
		<form action="search" method="POST">
			<input type="text" name="searchQuery" placeholder="Search..." />&nbsp;<input
				type="submit" id="searchButton" value="Search" />
		</form>
	</div>

	<div align="center">

		<ul class="tabrow">
			<!--<c:choose>
			  	<c:when test="${not empty rssList}"> -->
			<c:forEach var="rss" items="${rssList}">
				<li
					<c:if test="${rss.name}==${rssName}"><c:out value="class='selected'"/></c:if>>
					<a href="<c:out value='/literature/rss/${rss.name}'/>"><c:out
							value="${rss.name}" /></a>
				</li>
			</c:forEach>
			<!--	</c:when>
			 </c:choose> -->
		</ul>

		<a href="/literature/resource/new">Add new RSS</a>


	</div>
	<div align="center">
		<form action="save" method="POST">
			<table>
				<!-- <c:choose>
					<c:when test="${not empty rssNews}"> -->

						<c:set var="i" value="${0}" />
						<c:forEach var="news" items="${rssNews}">
							<tr>
								<td><input type="checkbox" name="selectedRss" value="${i}" /></td>
								<td><a href="<c:out value='${news.link}'/>"><c:out
											value="${news.title}" /></a></td>
								<td><c:out value="${news.author}" /></td>
								<td><c:out value="${news.publishedDate}" /></td>
							</tr>
							<tr>
								<td align="right" colspan=4><input type="submit"
									id="saveRssNewsButton" value="Save news" /></td>
							</tr>
							<c:set var="i" value="${i + 1}" />
						</c:forEach>
					<!-- </c:when>
				</c:choose> -->
			</table>
		</form>
	</div>
</body>
</html>
