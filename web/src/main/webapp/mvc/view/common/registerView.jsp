<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="display.web.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Sign up</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/registerPageStyles.css">
</head>
<body>
	<main class="register_main">
		<div class="register_page_div">
			<img width="400px" class="logo_image_big" src="${pageContext.request.contextPath}/assets/logo.png" alt="Logo de la empresa"/>
			<div class="register_form_div">
				<h1>Crear una cuenta</h1>
				<jsp:include page="./components/message.jsp"></jsp:include>
				<form class="register_form" method="post" action="../controller/registerController.jsp">
				    <input class="form_input" type="email" name="email" placeholder="Email" required>
				    <input class="form_input" type="password" name="password" placeholder="Contraseña" required>
				    <select class="form_input" name="role" required>
				      <option value="ADMIN">Administrador</option>
				      <option value="ASSISTANT" selected>Asistente</option>
				    </select>
				    <p>¿Ya tiene una cuenta? <a href="../controller/loginController.jsp">Accede.</a></p>
				    <input class="register_btn" type="submit" value="Registrar">
				</form>
			</div>
		</div>
	</main>
	
	
</body>
</html>