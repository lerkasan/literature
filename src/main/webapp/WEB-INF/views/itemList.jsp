<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="cf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
</style>
<link rel="stylesheet" href="/literature/resources/css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Articles and Books:</title>
</head>
<body>
	<br>
	<p align="center">Articles and Books:</p>
	<div align="center">
		<table class="list" id="list">
			<tr>
				<th>N</th>
				<th>Title</th>
				<th>Type</th>
				<th>Access</th>
				<th>Published</th>
				<th>ISBN</th>
				<th>Publisher</th>
				<th>Added</th>
			</tr>
			<tr>
				<cf:form action="/literature/item/list/1" id="selection">
					<td></td>
					<td><c:forEach var="i" begin="65" end="90">
							<a
								href="letter/<%=Character.toChars((Integer) pageContext.getAttribute("i"))%>"><%=Character.toChars((Integer) pageContext.getAttribute("i"))%></a>&nbsp;
			</c:forEach> <br>
					<c:forEach var="i" begin="48" end="57">
							<a
								href="letter/<%=Character.toChars((Integer) pageContext.getAttribute("i"))%>"><%=Character.toChars((Integer) pageContext.getAttribute("i"))%></a>&nbsp;
						</c:forEach></td>
					<td><select name="typeSelection">
							<option selected value="select_option">-- select an option --</option>
							<c:forEach var="itemType" items="${itemTypes}">
								<option value="${itemType}"><c:out value="${itemType}" /></option>
							</c:forEach>
					</select></td>
					<td><select name="accessSelection">
							<c:forEach var="accessType" items="${accessTypes}">
								<option disabled selected value>-- select an option --
								</option>
								<option value="${accessType}"><c:out
										value="${accessType}" /></option>
							</c:forEach>
					</select></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><input type="submit" value="Select" width="20" height="10"></td>
				</cf:form>
			</tr>
			<c:choose>
				<c:when test="${not empty items.content}">
					<c:set var="i" value="${(currentIndex - 1)*50 + 1}" />
					<c:forEach var="item" items="${items.content}">

						<tr>
							<td><c:out value="${i}" /></td>
							<td><a href="<c:out value='${item.url}'/>"><c:out
										value="${item.title}" /></a></td>
							<td><c:out value="${item.itemType}" /></td>
							<td><c:out value="${item.accessType}" /></td>
							<td><c:out value="${item.publishDate}" /></td>
							<td><c:choose>
									<c:when test="${item.itemType == 'BOOK'}">
										<c:out value="${item.isbn}" />
									</c:when>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${item.itemType == 'BOOK'}">
										<c:out value="${item.publishing}" />
									</c:when>
								</c:choose></td>
							<td><c:out value="${item.addedAt}" /></td>
						</tr>
						<c:set var="i" value="${i + 1}" />
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
	</div>
	<c:url var="firstUrl" value="/item/list/1" />
	<c:url var="lastUrl" value="/item/list/${items.totalPages}" />
	<c:url var="prevUrl" value="/item/list/${currentIndex - 1}" />
	<c:url var="nextUrl" value="/item/list/${currentIndex + 1}" />

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
					<c:url var="pageUrl" value="/item/list/${i}" />
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
					<c:when test="${currentIndex == items.totalPages}">
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