<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../common/components/header.jsp"></jsp:include>
	<div class="content_container">
		<main>
			<h1>Introduzca su información de asistente</h1>
			<form class="form" action="../controller/registerAssistantController.jsp" method="post">
				<jsp:include page="../common/components/assistantInfoForm.jsp">
						<jsp:param name="dniAttribute" value="required"/>
						<jsp:param name="birthDateAttribute" value="required"/>
			 	</jsp:include>
			 	<input class="submit_btn" type="submit" value="Guardar"/>
		 	</form>
		</main>
	</div>
</body>
</html>