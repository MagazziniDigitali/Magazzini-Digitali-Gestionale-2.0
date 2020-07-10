<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#ConfigDefaultsRowTableContainer').jtable({
			title : '<s:text name="configDefaultsRow.table.title" />',
      paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
      columnResizable: true, //Actually, no need to set true since it's default
      columnSelectable: true, //Actually, no need to set true since it's default
      saveUserPreferences: true, //Actually, no need to set true since it's default
      sorting: true,
      class: "tabConfigDefaultsRow",
      defaultSorting: 'nome ASC',
      actions: {
        listAction: 'TabConfigDefaultsRow?action=list',
        createAction:'TabConfigDefaultsRow?action=create',
        updateAction: 'TabConfigDefaultsRow?action=update'
//        ,deleteAction: 'TabSoftwareConfig?action=delete'
      },
			options: {
		    sorting: false,
		    defaultSorting: ''
		  },
    
      fields : {
				id : {
					title : '<s:text name="configDefaultsRow.table.id" />',
					sort :true,
					width : '16%',
					key : true,
					list : true,
					create : false
				},
				idConfigDefaultsID: {
					title : '<s:text name="configDefaultsRow.table.idConfigDefaults" />',
					edit : false,
					create : false,
					width : '16%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabConfigDefaults?action=options';
				    }
				},
				name : {
					title : '<s:text name="configDefaultsRow.table.name" />',
					edit : true,
					width : '16%',
					inputClass: "tabTextEdit"
				},
        descrizione : {
          title : '<s:text name="configDefaultsRow.table.descrizione" />',
          edit : true,
          width : '16%',
          inputClass: "tabTextEdit"
        },
				value : {
					title : '<s:text name="configDefaultsRow.table.value" />',
					edit : true,
					width : '16%',
					inputClass: "tabTextEdit"
				}
			}
		});

       $('#search').keyup(function(){
          $('#ConfigDefaultsRowTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#ConfigDefaultsRowTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="configDefaultsRow.legend.title" /></legend>
	<div class="filtering">
      <s:text name="configDefaultsRow.table.name" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="ConfigDefaultsRowTableContainer"></div>
</fieldset>
