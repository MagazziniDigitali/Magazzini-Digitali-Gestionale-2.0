<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#ConfigDefaultsTableContainer').jtable({
			title : '<s:text name="configDefaults.table.title" />',
      paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
      columnResizable: true, //Actually, no need to set true since it's default
      columnSelectable: true, //Actually, no need to set true since it's default
      saveUserPreferences: true, //Actually, no need to set true since it's default
      sorting: true,
      class: "configDefaults",
      defaultSorting: 'name ASC',
      actions: {
        listAction: 'TabConfigDefaults?action=list',
        createAction:'TabConfigDefaults?action=create',
        updateAction: 'TabConfigDefaults?action=update'
//        ,deleteAction: 'TabSoftware?action=delete'
      },
			options: {
		    sorting: false,
		    defaultSorting: ''
		  },
    
      fields : {
				id : {
					title : '<s:text name="configDefaults.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					display: function (data) {
				    return '<a href="ConfigDefaultsRow.action?idConfigDefaults='+data.record.id+'">'+data.record.id+'</a>';
				  },
					create : false
				},
				name : {
					title : '<s:text name="configDefaults.table.name" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				tipoIstituto : {
					title : '<s:text name="configDefaults.table.tipoIstituto" />',
					edit : true,
					width : '20%',
          options : { 'D' : 'Depositante', 
            'B' : 'Biblioteche',
            'E' : 'Entrambe'}
				}
			}
		});

    $('#search').keyup(function(){
      $('#ConfigDefaultsTableContainer').jtable('load', {searchname: $(this).val()});
    });
		$('#ConfigDefaultsTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="configDefaults.legend.title" /></legend>
	<div class="filtering">
      <s:text name="configDefaults.table.name" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="ConfigDefaultsTableContainer"></div>
</fieldset>
