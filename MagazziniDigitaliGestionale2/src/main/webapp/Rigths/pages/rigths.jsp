<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#RigthsTableContainer').jtable({
			title : '<s:text name="rigths.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabRigths",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabRigths?action=list',
                createAction:'TabRigths?action=create',
                updateAction: 'TabRigths?action=update'
//                ,deleteAction: 'TabUtenti?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="rigths.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					create : false
				},
				nome : {
					title : '<s:text name="rigths.table.nome" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				tipo : {
					title : '<s:text name="rigths.table.tipo" />',
					edit : true,
					visibility : 'hidden',
					options : { 'A' : '<s:text name="rigths.table.tipo.A" />', 
						'B' : '<s:text name="rigths.table.tipo.B" />', 
						'C' : '<s:text name="rigths.table.tipo.C" />' }
				},
			}
		});

       $('#search').keyup(function(){
          $('#RigthsTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#RigthsTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="rigths.legend.title" /></legend>
	<div class="filtering">
      <s:text name="rigths.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="RigthsTableContainer"></div>
</fieldset>
