<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%
    InputStream inputStream = getServletContext().
    	getResourceAsStream("/META-INF/maven/it.depositolegale.gestionale/MagazziniDigitaliGestionale2/pom.properties");
    Properties mavenProperties= new Properties();
    mavenProperties.load(inputStream );
    String version = (String) mavenProperties.get("version");

%>
<!-- 
<div align="center">&copy; Magazzini digitali 2016
<br>
Ver. <%= version %> - Server <%=request.getServerName() %></div>
 -->
