<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="login" style="">
	<legend><s:text name="login.title" /></legend>

	<s:if test="hasActionErrors()">
	   <div class="errors">
	      <s:actionerror/>
	   </div>
	</s:if>
	<s:if test="hasActionMessages()">
	   <div class="welcome">
	      <s:actionmessage/>
	   </div>
	</s:if>

	<s:form action="Home">
		<s:textfield name="username" key="global.username"  />
		<s:password name="password" key="global.password" />
		<s:submit name="submit" key="global.submit"/>
	</s:form>
</fieldset>