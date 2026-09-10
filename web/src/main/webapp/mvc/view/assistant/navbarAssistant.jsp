<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
String selectedStr = request.getParameter("selected") != null ? request.getParameter("selected") : "";
boolean inscriptions = false;
boolean availableCamps = false;


if(selectedStr.equals("inscriptions")){
	inscriptions = true;
} else if(selectedStr.equals("availableCamps")) {
	availableCamps = true;
} 
%>
    
<aside class="navbar">
  <ul class="navbar_ul">
    <li class="navbar_li  <%= inscriptions ? "navbar_li_selected"  : "" %>" ><a class="navbar_a" href="/web/inscriptions">Inscripciones realizadas</a></li>
    <li class="navbar_li  <%= availableCamps ? "navbar_li_selected"  : "" %>"><a class="navbar_a" href="/web/availableCamps">Campamentos disponibles</a></li>
  </ul>
</aside>