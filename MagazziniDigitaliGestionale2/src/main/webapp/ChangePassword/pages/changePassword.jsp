<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<fieldset class="changePassword" style="">
	<legend><s:text name="changePassword.title" /></legend>

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

	<s:form action="ChangePassword">
		<s:password name="oldPassword" key="changePassword.oldPassword"  />
		<s:password name="newPassword" key="changePassword.newPassword" />
		<s:password name="confPassword" key="changePassword.confPassword" />
		<s:submit name="submit" key="changePassword.submit"/>
	</s:form>
	
</fieldset>