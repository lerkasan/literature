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

		<table class="list" id="list">
			<tr>
				<th>N</th>
				<th>Name</th>
				<th>Articles/Books in database</th>
			</tr>
			<c:choose>
				<c:when test="${not empty authors}">
					<c:set var="i" value="${(currentIndex - 1)*50 + 1}" />
					<c:forEach var="author" items="${authors.content}">
						<tr>
							<td><c:out value="${i}" /></td>
							<td><c:out value="${author.fullName}" /></td>
							<td><a href="/literature/author/${author.id}"> <c:out
										value="${fn:length(author.itemsToRead)}" /></a></td>
						</tr>
						<c:set var="i" value="${i + 1}" />
					</c:forEach>
					<tr>
						<td colspan=3><c:url var="firstUrl" value="/author/list/1" /> <c:url
								var="lastUrl" value="/author/list/${authors.totalPages}" /> <c:url
								var="prevUrl" value="/author/list/${currentIndex - 1}" /> <c:url
								var="nextUrl" value="/author/list/${currentIndex + 1}" />

							<div class="pagination" align="center" style="float: left">
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
											<c:url var="pageUrl" value="/author/list/${i}" />
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
											<c:when test="${currentIndex == authors.totalPages}">
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
							</div></td>
					</tr>
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