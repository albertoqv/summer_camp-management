<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Summer Camp</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
	</head>
	<body>
		<jsp:include page="../common/components/header.jsp"></jsp:include>
		<div class="content_container">
			<jsp:include page="./navbarAdmin.jsp">
				<jsp:param name="selected" value="monitorsManager"/>
			</jsp:include>
			<main>
				<h1>Gestión de monitores</h1>
				<a class="btn" href="/web/createMonitor">Dar de alta un nuevo monitor</a>
				
				<jsp:include page="../common/components/message.jsp"></jsp:include>
	
				<jsp:include page="../common/components/listOfMonitors.jsp"></jsp:include>
			</main>
		</div>

</body>
</html>