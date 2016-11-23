<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#SoftwareTableContainer').jtable({
			title : '<s:text name="software.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabSoftware",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabSoftware?action=list',
                createAction:'TabSoftware?action=create',
                updateAction: 'TabSoftware?action=update'
//                ,deleteAction: 'TabSoftware?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="software.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					display: function (data) {
				        return '<a href="SoftwareConfig.action?idSoftware='+data.record.id+'">'+data.record.id+'</a>';
				    },
					create : false
				},
				nome : {
					title : '<s:text name="software.table.nome" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				login : {
					title : '<s:text name="software.table.login" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				password : {
					title : '<s:text name="software.table.password" />',
					edit : true,
					inputClass: "tabTextEdit",
					visibility : 'hidden',
					type: 'password'
				},
				ipAutorizzati : {
					title : '<s:text name="software.table.ipAutorizzati" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				idIstituzioneID: {
					title : '<s:text name="software.table.idIstituzione" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabIstituti?action=options';
				    }
				},
				idRigthsID: {
					title : '<s:text name="software.table.idRigths" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabRigths?action=options';
				    }
				}
			}
		});

       $('#search').keyup(function(){
          $('#SoftwareTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#SoftwareTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="software.legend.title" /></legend>
	<div class="filtering">
      <s:text name="software.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="SoftwareTableContainer"></div>
</fieldset>
