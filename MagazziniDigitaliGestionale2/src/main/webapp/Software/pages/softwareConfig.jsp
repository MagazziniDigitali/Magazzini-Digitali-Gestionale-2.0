<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#SoftwareConfigTableContainer').jtable({
			title : '<s:text name="softwareConfig.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabSoftwareConfig",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabSoftwareConfig?action=list',
                createAction:'TabSoftwareConfig?action=create',
                updateAction: 'TabSoftwareConfig?action=update'
//                ,deleteAction: 'TabSoftwareConfig?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="softwareConfig.table.id" />',
					sort :true,
					width : '16%',
					key : true,
					list : true,
					create : false
				},
				idSoftwareID: {
					title : '<s:text name="softwareConfig.table.idSoftware" />',
					edit : true,
					create : false,
					width : '16%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabSoftware?action=options';
				    }
				},
				nome : {
					title : '<s:text name="softwareConfig.table.nome" />',
					edit : true,
					width : '16%',
					inputClass: "tabTextEdit"
				},
				descrizione : {
					title : '<s:text name="softwareConfig.table.descrizione" />',
					edit : true,
					width : '16%',
					inputClass: "tabTextEdit"
				},
				value : {
					title : '<s:text name="softwareConfig.table.value" />',
					edit : true,
					width : '16%',
					inputClass: "tabTextEdit"
				},
				idNodoID: {
					title : '<s:text name="softwareConfig.table.idNodo" />',
					edit : true,
					width : '16%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabNodi?action=options';
				    }
				}
			}
		});

       $('#search').keyup(function(){
          $('#SoftwareConfigTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#SoftwareConfigTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="softwareConfig.legend.title" /></legend>
	<div class="filtering">
      <s:text name="softwareConfig.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="SoftwareConfigTableContainer"></div>
</fieldset>
