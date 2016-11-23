<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#NodiTableContainer').jtable({
			title : '<s:text name="nodi.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabNodi",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabNodi?action=list',
                createAction:'TabNodi?action=create',
                updateAction: 'TabNodi?action=update'
//                ,deleteAction: 'TabNodi?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="nodi.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					create : false
				},
				nome : {
					title : '<s:text name="nodi.table.nome" />',
					sort :false,
					width : '10%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				descrizione : {
					title : '<s:text name="nodi.table.descrizione" />',
					sort :false,
					width : '10%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				rsync : {
					title : '<s:text name="nodi.table.rsync" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				rsyncPassword : {
					title : '<s:text name="nodi.table.rsyncPassword" />',
					edit : true,
					inputClass: "tabTextEdit",
					visibility : 'hidden',
					type: 'password'
				},
				urlCheckStorage : {
					title : '<s:text name="nodi.table.urlCheckStorage" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				pathStorage : {
					title : '<s:text name="nodi.table.pathStorage" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				active : {
					title : '<s:text name="nodi.table.active" />',
					edit : true,
					visibility : 'hidden',
					type : 'checkbox',
					values : { '0' : 'No', '1' : 'Si' }
				}
			}
		});

       $('#search').keyup(function(){
          $('#NodiTableContainer').jtable('load', {searchname: $(this).val(), searchcogname: $('#searchCognome').val()});
       });
       $('#searchCognome').keyup(function(){
           $('#NodiTableContainer').jtable('load', {searchcogname: $(this).val(), searchname: $('#search').val()});
        });
		$('#NodiTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="nodi.legend.title" /></legend>
	<div class="filtering">
      <s:text name="nodi.table.cognome" />: <input type="text" id="searchCognome"/>
   </div>
	<div class="filtering">
      <s:text name="nodi.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="NodiTableContainer"></div>
</fieldset>
