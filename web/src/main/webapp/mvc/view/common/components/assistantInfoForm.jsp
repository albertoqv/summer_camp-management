<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

String dniAttribute = request.getParameter("dniAttribute");
String birthDateAttribute = request.getParameter("birthDateAttribute");
String requireSpecialAttentionAttributes = request.getParameter("requireSpecialAttentionAttributes");

String firstNameValue = request.getParameter("firstNameValue");
firstNameValue = firstNameValue == null ? "" : "value='" + firstNameValue + "'";

String lastNameValue = request.getParameter("lastNameValue");
lastNameValue = lastNameValue == null ? "" : "value='" + lastNameValue + "'";

String dniValue = request.getParameter("dniValue");
dniValue = dniValue == null ? "" : "value='" + dniValue + "'";

String birthDateValue = request.getParameter("birthDateValue");

birthDateValue = birthDateValue == null ? "" : "value=\"" + birthDateValue + "\"";

%>
<input class="form_input" type="text" placeholder="DNI con letra" id="dni" name="dni" pattern="[0-9]{8}[A-Z]" <%= dniAttribute %> <%= dniValue %>>
<input class="form_input" type="text" placeholder="Nombre" id="firstName" name="firstName" placeholder="Nombre" required <%= firstNameValue %>>
<input class="form_input" type="text" placeholder="Apellidos" id="lastName" name="lastName" placeholder="Apellidos" required <%= lastNameValue %>>
<div class="form_div">
	<div>
		<label for="birthDate">Fecha de nacimiento</label>
		<input type="date" id="birthDate" name="birthDate" <%= birthDateAttribute %> <%= birthDateValue %>>
	</div>
	<div>
		<label for="requireSpecialAttention">Requiere atención especial</label>
		<input type="checkbox" id="requireSpecialAttention" name="requireSpecialAttention" <%= requireSpecialAttentionAttributes %>>
	</div>
</div>