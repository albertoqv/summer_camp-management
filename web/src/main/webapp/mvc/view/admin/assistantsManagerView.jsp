<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Camping Site</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
	</head>
	<body>
		<jsp:include page="../common/components/header.jsp"></jsp:include>
		<div class="content_container">
			<jsp:include page="./navbarAdmin.jsp">
				<jsp:param name="selected" value="assistantsManager"/>
			</jsp:include>		
			<main>
				<h1>Lista de asistentes</h1>
				<jsp:include page="../common/components/listOfAssistants.jsp"></jsp:include>
			</main>
		</div>

</body>
</html>