<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="resetPassword" style="">
	<legend><s:text name="resetPassword.title" /></legend>

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

	<s:form action="ResetPassword">
		<s:textfield name="username" key="global.username"  />
		<s:textfield name="email" key="global.email" />
		<s:submit name="submit" key="resetPassword.submit"/>
	</s:form>
	
</fieldset>