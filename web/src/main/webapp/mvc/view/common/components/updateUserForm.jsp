<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="business.enums.UserRole"%>
    
<%

String email = request.getParameter("email");
String password = request.getParameter("password");
String role = request.getParameter("role");
String dni = request.getParameter("dni");
String firstName = request.getParameter("firstName");
String lastName = request.getParameter("lastName");
String birthDate = request.getParameter("birthDate");
String requireSpecialAttention = request.getParameter("requireSpecialAttention");



%>
<div>
<label for="email">Email: </label>
<input class="form_input" type="text" name="email" disabled value="<%= email %>">	
<br/>
</div>
<div>
	<label for="password">Password: </label>
	<input class="form_input" type="password" name="password" value="<%= password %>">
	<br/>
</div>
<input type="hidden" name="role" value="<%= role %>"/>
<select class="form_input" name="role" disabled>
  <option value="ADMIN" <%= role.equals("ADMIN") ? "selected" : "" %>>Administrador</option>
  <option value="ASSISTANT" <%= role.equals("ASSISTANT") ? "selected" : "" %>>Asistente</option>
</select>
<br/>

<%

if (role.equals(UserRole.ASSISTANT.name())){
	LocalDate localDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	String birthDateFormatted = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
	String requireSpecialAttentionAttributes = "disabled" + (requireSpecialAttention.equals("true") ? " selected" : "");
%>

	<jsp:include page="./assistantInfoForm.jsp">
		<jsp:param name="dniAttribute" value="disabled"/>
		<jsp:param name="birthDateAttribute" value="disabled"/>
		<jsp:param name="requireSpecialAttentionAttributes" value="<%= requireSpecialAttentionAttributes %>"/>
		<jsp:param name="firstNameValue" value="<%= firstName %>"/>
		<jsp:param name="lastNameValue" value="<%= lastName %>"/>
		<jsp:param name="dniValue" value="<%= dni %>"/>
		<jsp:param name="birthDateValue" value="<%= birthDateFormatted %>"/>
	</jsp:include>
	<input type="hidden" name="requireSpecialAttention" value="<%= requireSpecialAttention %>"/>
<%
}
%>