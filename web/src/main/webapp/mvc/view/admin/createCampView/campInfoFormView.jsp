<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear nuevo campamento</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../../common/components/header.jsp"></jsp:include>
	<div class="content_container">
		<main>
			<form action="/web/createCamp" method="post">
				<label for="start_date">Fecha de inicio:</label>
				<input type="date" name="start_date" id="start_date" required>
				<br>
				
				<label for="end_date">Fecha de fin:</label>
				<input type="date" name="end_date" id="end_date" required>
				<br>
				
				<label for="educative_level">Nivel Educativo:</label>
				<select name="educative_level" id="educative_level">
				  <option value="ELEMENTARY">Infantil</option>
				  <option value="PRESCHOOL">Juvenil</option>
				  <option value="TEENAGER">Adolescente</option>
				</select>
				<br>
				
				<label for="capacity">Capacidad:</label>
				<input type="number" name="capacity" id="capacity" min="0" max="999" required>
				<br>
				
				<input type="submit" value="Siguiente">
			</form>
		</main>
	</div>
</body>
</html>