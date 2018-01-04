<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#PreRegistrazioneTableContainer').jtable({
			title : '<s:text name="preRegistrazione.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabPreRegistrazione",
            defaultSorting: 'utenteCognome ASC',
            actions: {
                listAction: 'TabPreRegistrazione?action=list',
//                createAction:'TabPreRegistrazione?action=create',
//                updateAction: 'TabPreRegistrazione?action=update'
//                ,deleteAction: 'TabPreRegistrazione?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
            	msg : {
					title : '<s:text name="preRegistrazione.table.msg" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					display: function (data) {
						if (data.record.msg ==''){
					        return '';
						} else {
					        return '<a href="PreRegistrazione.action?action=change&emailValidata='+data.record.emailValidata+'&id='+data.record.id+'">'+data.record.msg+'</a>';
						}
				    },
					create : false
				},
				id : {
					title : '<s:text name="preRegistrazione.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					create : false
				},
				utenteCognome : {
					title : '<s:text name="preRegistrazione.table.utenteCognome" />',
					width : '20%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				utenteNome : {
					title : '<s:text name="preRegistrazione.table.utenteNome" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				dataPreIscrizione : {
					title : '<s:text name="preRegistrazione.table.dataPreIscrizione" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				dataEmailValidazione1 : {
					title : '<s:text name="preRegistrazione.table.dataEmailValidazione1" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				emailValidataDesc : {
					title : '<s:text name="preRegistrazione.table.emailValidata" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				}
			}
		});

       $('#search').keyup(function(){
          $('#PreRegistrazioneTableContainer').jtable('load', {searchname: $(this).val(), searchcogname: $('#searchCognome').val()});
       });
       $('#searchCognome').keyup(function(){
           $('#PreRegistrazioneTableContainer').jtable('load', {searchcogname: $(this).val(), searchname: $('#search').val()});
        });
		$('#PreRegistrazioneTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="preRegistrazione.legend.title" /></legend>
	<div class="filtering">
      <s:text name="preRegistrazione.table.utenteCognome" />: <input type="text" id="searchCognome"/>
   </div>
	<div class="filtering">
      <s:text name="preRegistrazione.table.utenteNome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="PreRegistrazioneTableContainer"></div>
</fieldset>
