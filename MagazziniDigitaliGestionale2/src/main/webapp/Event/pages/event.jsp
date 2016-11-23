<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#EventTableContainer').jtable({
			title : '<s:text name="event.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabEvent",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabEvent?action=list',
                createAction:'TabEvent?action=create',
                updateAction: 'TabEvent?action=update'
//                ,deleteAction: 'TabEvent?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="event.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					create : false
				},
				nome : {
					title : '<s:text name="event.table.nome" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
			}
		});

       $('#search').keyup(function(){
          $('#EventTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#EventTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="event.legend.title" /></legend>
	<div class="filtering">
      <s:text name="event.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="EventTableContainer"></div>
</fieldset>
