<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#HomeTableContainer').jtable({
			title : '<s:text name="home.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)
			columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabIstitute",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabHome?action=list'//,
//                createAction:'Controller?action=create',
//                updateAction: 'Controller?action=update',
//                deleteAction: 'Controller?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
			fields : {
				id : {
					title : '<s:text name="home.table.id" />',
					sort :true,
					width : '30%',
					key : true,
					list : true,
					display: function (data) {
				        return '<a href="HomeDetail.action?idMdFilesTmp='+data.record.id+'">'+data.record.id+'</a>';
				    },
					create : false
				},
				nomeFile : {
					title : '<s:text name="home.table.nomeFile" />',
					width : '20%',
					edit : true
				},
				data : {
					title : '<s:text name="home.table.data" />',
					width : '10%',
					edit : true
				},
				istituzione : {
					title : '<s:text name="home.table.istituzione" />',
					width : '15%',
					edit : true
				},
				statoName : {
					title : '<s:text name="home.table.stato" />',
					width : '20%',
					edit : true
				},
				statoJob : {
					title : '<s:text name="home.table.statoJob" />',
					width : '10%',
					edit : true
				}
			}
		});
		$('#HomeTableContainer').jtable('load');

       $('#search').click(function(){
           $('#HomeTableContainer').jtable('load', {nomeFile: $('#nomeFile').val(), stato: $('#stato').val()});
        });
	 		
	});
</script>

<fieldset class="situazioneMateriale" style="">
	<legend><s:text name="home.situazioneMateriale" /></legend>

	<table id="sitMat">
	  <tr>
	    <th><s:text name="home.initTrasf" /></th><td class="<s:property value="initTrasfClass" />"><s:property value="initTrasf" /></td>
	    <th><s:text name="home.fineTrasf" /></th><td class="<s:property value="fineTrasfClass" />"><s:property value="fineTrasf" /></td>
	    <th><s:text name="home.initValid" /></th><td class="<s:property value="initValidClass" />"><s:property value="initValid" /></td>
	    <th><s:text name="home.fineValid" /></th><td class="<s:property value="fineValidClass" />"><s:property value="fineValid" /></td>
	    <th><s:text name="home.initPublish" /></th><td class="<s:property value="initPublishClass" />"><s:property value="initPublish" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="home.finePublish" /></th><td class="<s:property value="finePublishClass" />"><s:property value="finePublish" /></td>
	    <th><s:text name="home.initArchive" /></th><td class="<s:property value="initArchiveClass" />"><s:property value="initArchive" /></td>
	    <th><s:text name="home.fineArchive" /></th><td class="<s:property value="fineArchiveClass" />"><s:property value="fineArchive" /></td>
	    <th><s:text name="home.initIndex" /></th><td class="<s:property value="initIndexClass" />"><s:property value="initIndex" /></td>
	    <th><s:text name="home.checkIndex" /></th><td class="<s:property value="checkIndexClass" />"><s:property value="checkIndex" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="home.fineIndex" /></th><td class="<s:property value="fineIndexClass" />"><s:property value="fineIndex" /></td>
	    <th><s:text name="home.errorTrasf" /></th><td class="<s:property value="errorTrasfClass" />"><s:property value="errorTrasf" /></td>
	    <th><s:text name="home.errorVal" /></th><td class="<s:property value="errorValClass" />"><s:property value="errorVal" /></td>
	    <th><s:text name="home.errorDecomp" /></th><td class="<s:property value="errorDecompClass" />"><s:property value="errorDecomp" /></td>
	    <th><s:text name="home.errorCopy" /></th><td class="<s:property value="errorCopyClass" />"><s:property value="errorCopy" /></td>
	  </tr>
	  <tr>
	    <th><s:text name="home.errorMove" /></th><td class="<s:property value="errorMoveClass" />"><s:property value="errorMove" /></td>
	    <th><s:text name="home.errorPub" /></th><td class="<s:property value="errorPubClass" />"><s:property value="errorPub" /></td>
	    <th><s:text name="home.errorDelete" /></th><td class="<s:property value="errorDeleteClass" />"><s:property value="errorDelete" /></td>
	    <th><s:text name="home.errorArchive" /></th><td class="<s:property value="errorArchiveClass" />"><s:property value="errorArchive" /></td>
	    <th><s:text name="home.errorIndex" /></th><td class="<s:property value="errorIndexClass" />"><s:property value="errorIndex" /></td>
	  </tr>
	</table>
</fieldset>
<fieldset class="searchMateriale" style="">
	<legend><s:text name="home.searchMateriale" /></legend>
	<div class="filteringLeft">
      <s:text name="home.table.nomeFile" />: <input type="text" id="nomeFile"/>
   </div>
	<div class="filteringLeft">
<!--
       list="stato"
       listKey="id"
       listValue="descrizione"
       -->
      <s:select key="home.table.stato"
       id="stato"
       name="stato"
       list="#{}"
       multiple="false"
       required="true"
       headerKey=""
       headerValue="Seleziona .....">
       <s:iterator var="customers" value="stato">
       <s:optgroup label="%{key}" list="%{value}" listKey="id" 
listValue="descrizione"/>
</s:iterator>
       </s:select>
       
       
   </div>
	<div class="filteringLeft">
		<s:submit id="search" key="home.search"></s:submit>
	</div>
	<div class="filtering">&nbsp;</div>
	<div id="HomeTableContainer"></div>
</fieldset>
