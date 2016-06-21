<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
</style>
<link rel="stylesheet" href="/literature/resources/css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Resource list:</title>
</head>
<body>
	<p align="center">Resources:</p>
	<div align="center">
		<table class="list" id="list">
			<tr>
				<th>N</th>
				<th>Name</th>
				<th>Url</th>
				<th>Domain</th>
				<th>Request format</th>
				<th>Response format</th>
			</tr>
			<c:choose>
				<c:when test="${not empty resources.content}">
				<c:set var="i" value="${1}" />
					<c:forEach var="resource" items="${resources.content}">
						<tr>
							<td><c:out value="${i}" /></td>
							<td><c:out value="${resource.name}" /></td>
							<td><c:out value="${resource.url}" /></td>
							<td><c:out value="${resource.domain}" /></td>
							<td><c:out value="${resource.parameterFormat}" /></td>
							<td><c:out value="${resource.responseFormat}" /></td>
						</tr>
						<c:set var="i" value="${i + 1}" />
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
	</div>
	<c:url var="firstUrl" value="/resource/list/1" />
	<c:url var="lastUrl" value="/resource/list/${resourceList.totalPages}" />
	<c:url var="prevUrl" value="/resource/list/${currentIndex - 1}" />
	<c:url var="nextUrl" value="/resource/list/${currentIndex + 1}" />

	<div class="pagination" align="center">
		<table align="center">
			<tr>
				<c:choose>
					<c:when test="${currentIndex == 1}">
						<td class="disabled"><a href="#">&lt;&lt;&nbsp;First</a></td>
						<td class="disabled"><a href="#">&lt;&nbsp;Previous</a></td>
					</c:when>
					<c:otherwise>
						<td><a href="${firstUrl}">&lt;&lt;&nbsp;First</a></td>
						<td><a href="${prevUrl}">&lt;&nbsp;Previous</a></td>
					</c:otherwise>
				</c:choose>
				<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
					<c:url var="pageUrl" value="/resource/list/${i}" />
					<c:choose>
						<c:when test="${i == currentIndex}">
							<td class="active"><a href="${pageUrl}"><c:out
										value="${i}" /></a></td>
						</c:when>
						<c:otherwise>
							<td><a href="${pageUrl}"><c:out value="${i}" /></a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test="${currentIndex == resourceList.totalPages+1}">
						<td class="disabled"><a href="#">Next&nbsp;&gt;</a></td>
						<td class="disabled"><a href="#">Last&nbsp;&gt;&gt;</a></td>
					</c:when>
					<c:otherwise>
						<td><a href="${nextUrl}">Next&nbsp;&gt;</a></td>
						<td><a href="${lastUrl}">Last&nbsp;&gt;&gt;</a></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
	</div>
</body>
</html>