<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear nueva actividad</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/common.css">
</head>
<body>
	<jsp:include page="../../common/components/header.jsp"></jsp:include>
	<div class="content_container">
		<main>
			<form action="/web/createActivity" method="post">
				<label for="activity_name">Nombre de la actividad:</label>
				<input type="text" name="activity_name" id="activity_name">
				<br>
				
				<label for="educative_level">Nivel educativo:</label>
				<select name="educative_level" id="educative_level">
				  <option value="ELEMENTARY">Infantil</option>
				  <option value="PRESCHOOL">Juvenil</option>
				  <option value="TEENAGER">Adolescente</option>
				</select>
				<br>
				
				<label for="time_slot">Horario:</label>
				<input type="radio" name="time_slot" value="AFTERNOON" id="time_slot_afternoon">
				<label for="time_slot_afternoon">Tarde</label>
				<input type="radio" name="time_slot" value="MORNING" id="time_slot_morning">
				<label for="time_slot_morning">Mañana</label>
				<br>
				
				<label for="capacity">Capacidad:</label>
				<input type="number" name="capacity" id="capacity" min="0" max="999" required>
				<br>
				
				<label for="needed_monitors">Número de monitores:</label>
				<input type="number" name="needed_monitors" id="needed_monitors" min="0" max="20" required>
				<br>
				
				<input type="submit" value="Siguiente">
			</form>
		</main>
	</div>
</body>
</html>