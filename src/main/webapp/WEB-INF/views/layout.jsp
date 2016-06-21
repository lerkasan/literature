<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        <link rel="stylesheet" href="resources/css/style.css" type="text/css"></link>
</head>
<body>
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="footer" />
</body>
</html>