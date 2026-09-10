<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Error del servidor</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/loginPageStyles.css">
</head>
<body>
	<main class="login_main">
		<div class="login_page_div">
			<img width="400px" class="logo_image_big" src="${pageContext.request.contextPath}/assets/logo.png" alt="Logo de la empresa"/>
			<div class="login_form_div">
				<h1>Error del servidor</h1>
				<p>Sentimos las molestias, el servidor está experimentando problemas. Vuelva a intentarlo más adelante.</p>
			</div>
		</div>
	</main>
</body>
</html>