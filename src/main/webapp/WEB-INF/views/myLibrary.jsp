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
<title>My Library</title>
</head>
<body>
<br>
	<p align="center">Articles and Books:</p>
	<div align="center">
		<cf:form action="/literature/library/remove" method="POST">
		<table class="list" id="list">
			<tr>
				<th></th>
				<th>N</th>
				<th>Title</th>
				<th>Type</th>
				<th>Access</th>
				<th>Published</th>
				<th>ISBN</th>
				<th>Publisher</th>
				<th>Added</th>
			</tr>
			<c:choose>
				<c:when test="${not empty items and not empty items}">
					<c:set var="i" value="${1}" />
					<c:forEach var="item" items="${items}">
						<tr>
						<td><input type="checkbox" name="selectedItems"
									value="${i-1}" /></td>
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
					<tr>
							<td align="right" colspan=9><input type="submit"
								id="removeFromLibrary" value="Remove from my Library" /></td>
						</tr>
				</c:when>
			</c:choose>
		</table>
		</cf:form>
	</div>
</body>
</html>