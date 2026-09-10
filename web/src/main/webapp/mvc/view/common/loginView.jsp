<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Log in</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/loginPageStyles.css">
</head>
<body>
	<main class="login_main">
		<div class="login_page_div">
			<img width="400px" class="logo_image_big" src="${pageContext.request.contextPath}/assets/logo.png" alt="Logo de la empresa"/>
			<div class="login_form_div">
				<h1>Iniciar sesión</h1>
				<jsp:include page="./components/message.jsp"></jsp:include>
				<form class="login_form" method="post" action="../controller/loginController.jsp">
					<input class="form_input" type="text" name="email" placeholder="Email">	
					<input class="form_input" type="password" name="password" placeholder="Contraseña">
					<p>¿No tiene una cuenta? <a href="../controller/registerController.jsp">Cree una.</a></p>
			 		<input class="login_btn" type="submit" value="Entrar">
				</form>	
			</div>
		</div>
	</main>
</body>
</html>