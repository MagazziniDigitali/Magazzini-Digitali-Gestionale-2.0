<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#HomeDetailTableContainer').jtable({
			title : '<s:text name="homeDetail.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)
			columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabError",
            defaultSorting: 'dataIns DESC',
            actions: {
                listAction: 'TabHomeDetail?action=list'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
			fields : {
				id : {
					title : '<s:text name="homeDetail.table.id" />',
					sort :true,
					width : '20%',
					key : true,
					list : true,
					create : false
				},
				dataIns : {
					title : '<s:text name="homeDetail.table.dataIns" />',
					width : '12%',
					edit : true
				},
				type : {
					title : '<s:text name="homeDetail.table.type" />',
					width : '20%',
					edit : true
				},
				msgError : {
					title : '<s:text name="homeDetail.table.msgError" />',
					width : '48%',
					edit : true
				}
			}
		});
		$('#HomeDetailTableContainer').jtable('load');
	 		
	});
</script>
<fieldset class="statoMateriale" style="">
	<legend><s:text name="homeDetail.statoMateriale" /></legend>
	<table id="statoMat">
	  <tr>
	    <th><s:text name="homeDetail.id" /></th><td><s:property value="id" /></td>
	    <th><s:text name="homeDetail.nomeIstituto" /></th><td><s:property value="nomeIstituto" /></td>
	    <th><s:text name="homeDetail.nomeSoftware" /></th><td><s:property value="nomeSoftware" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.nomeFile" /></th><td><s:property value="nomeFile" /></td>
	    <th><s:text name="homeDetail.sha1" /></th><td><s:property value="sha1" /></td>
	    <th><s:text name="homeDetail.nomeFileMod" /></th><td><s:property value="nomeFileMod" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.premisFile" /></th><td><s:property value="premisFile" /></td>
	    <th><s:text name="homeDetail.idNodo" /></th><td><s:property value="idNodo" /></td>
	    <th><s:text name="homeDetail.indexPremis" /></th><td><s:property value="indexPremis" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.stato" /></th><td><s:property value="stato" /></td>
	    <s:set name="isError" value="statoError"/>
	    <th colspan="2" class="left"><s:if test="%{#isError=='Si'}">
	    <s:url action="HomeDetail.action" var="urlTag" escapeAmp="&">
    		<s:param name="idMdFilesTmp"><s:property value="idMdFilesTmp" /></s:param>
    		<s:param name="resetMovi">Si</s:param>
		</s:url>
		<a href="<s:property value="#urlTag" />"><s:text name="homeDetail.resetError" /></a>
	    </s:if></th>
	    <th><s:text name="homeDetail.xmlMimeType" /></th><td><s:property value="xmlMimeType" /></td>
	  </tr>
	  <tr>
	    <th colspan="6" class="line"></th>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.trasfDataStart" /></th><td><s:property value="trasfDataStart" /></td>
	    <th><s:text name="homeDetail.trasfDataEnd" /></th><td><s:property value="trasfDataEnd" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.decompDataStart" /></th><td><s:property value="decompDataStart" /></td>
	    <th><s:text name="homeDetail.decompDataEnd" /></th><td><s:property value="decompDataEnd" /></td>
	    <th><s:text name="homeDetail.decompEsito" /></th><td><s:property value="decompEsito" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.validDataStart" /></th><td><s:property value="validDataStart" /></td>
	    <th><s:text name="homeDetail.validDataEnd" /></th><td><s:property value="validDataEnd" /></td>
	    <th><s:text name="homeDetail.validEsito" /></th><td><s:property value="validEsito" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.copyPremisDataStart" /></th><td><s:property value="copyPremisDataStart" /></td>
	    <th><s:text name="homeDetail.copyPremisDataEnd" /></th><td><s:property value="copyPremisDataEnd" /></td>
	    <th><s:text name="homeDetail.copyPremisEsito" /></th><td><s:property value="copyPremisEsito" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.moveFileDataStart" /></th><td><s:property value="moveFileDataStart" /></td>
	    <th><s:text name="homeDetail.moveFileDataEnd" /></th><td><s:property value="moveFileDataEnd" /></td>
	    <th><s:text name="homeDetail.moveFileEstio" /></th><td><s:property value="moveFileEsito" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.publishDataStart" /></th><td><s:property value="publishDataStart" /></td>
	    <th><s:text name="homeDetail.publishDataEnd" /></th><td><s:property value="publishDataEnd" /></td>
	    <th><s:text name="homeDetail.publishEsito" /></th><td><s:property value="publishEsito" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.archiveDataStart" /></th><td><s:property value="archiveDataStart" /></td>
	    <th><s:text name="homeDetail.archiveDataEnd" /></th><td><s:property value="archiveDataEnd" /></td>
	    <th><s:text name="homeDetail.archiveEstio" /></th><td><s:property value="archiveEsito" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.indexDataStart" /></th><td><s:property value="indexDataStart" /></td>
	    <th><s:text name="homeDetail.indexDataEnd" /></th><td><s:property value="indexDataEnd" /></td>
	    <th><s:text name="homeDetail.indexEstio" /></th><td><s:property value="indexEsito" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="homeDetail.deleteLocalData" /></th><td colspan="3"><s:property value="deleteLocalData" /></td>
	    <th><s:text name="homeDetail.deleteLocalEsito" /></th><td><s:property value="deleteLocalEsito" /></td>
	  </tr>
	</table>
</fieldset>

<fieldset class="showError" style="">
	<legend><s:text name="homeDetail.showError" /></legend>
	<div id="HomeDetailTableContainer"></div>
</fieldset>
