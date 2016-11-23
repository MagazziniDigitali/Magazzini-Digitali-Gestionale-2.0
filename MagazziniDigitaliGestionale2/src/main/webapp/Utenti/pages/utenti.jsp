<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#UtentiTableContainer').jtable({
			title : '<s:text name="utenti.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabUtenti",
            defaultSorting: 'cognome ASC',
            actions: {
                listAction: 'TabUtenti?action=list',
                createAction:'TabUtenti?action=create',
                updateAction: 'TabUtenti?action=update'
//                ,deleteAction: 'TabUtenti?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="utenti.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					create : false
				},
				login : {
					title : '<s:text name="utenti.table.login" />',
					sort :false,
					width : '10%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				password : {
					title : '<s:text name="utenti.table.password" />',
					edit : true,
					inputClass: "tabTextEdit",
					visibility : 'hidden',
					type: 'password'
				},
				cognome : {
					title : '<s:text name="utenti.table.cognome" />',
					width : '20%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				nome : {
					title : '<s:text name="utenti.table.nome" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				amministratore : {
					title : '<s:text name="utenti.table.amministratore" />',
					edit : true,
					visibility : 'hidden',
					type : 'checkbox',
					values : { '0' : 'No', '1' : 'Si' }
				},
				ipAutorizzati : {
					title : '<s:text name="utenti.table.ipAutorizzati" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				idIstituzioneID: {
					title : '<s:text name="utenti.table.idIstituzione" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabIstituti?action=options';
				    }
				}
			}
		});

       $('#search').keyup(function(){
          $('#UtentiTableContainer').jtable('load', {searchname: $(this).val(), searchcogname: $('#searchCognome').val()});
       });
       $('#searchCognome').keyup(function(){
           $('#UtentiTableContainer').jtable('load', {searchcogname: $(this).val(), searchname: $('#search').val()});
        });
		$('#UtentiTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="utenti.legend.title" /></legend>
	<div class="filtering">
      <s:text name="utenti.table.cognome" />: <input type="text" id="searchCognome"/>
   </div>
	<div class="filtering">
      <s:text name="utenti.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="UtentiTableContainer"></div>
</fieldset>
