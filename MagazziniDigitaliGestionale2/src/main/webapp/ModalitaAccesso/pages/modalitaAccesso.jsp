<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#ModalitaAccessoTableContainer').jtable({
			title : '<s:text name="modalitaAccesso.table.title" />',
      paging: true, //Enable paging
//      pageSize: 3, Set page size (default: 10)           
      columnResizable: true, //Actually, no need to set true since it's default
      columnSelectable: true, //Actually, no need to set true since it's default
      saveUserPreferences: true, //Actually, no need to set true since it's default
      sorting: true,
      class: "tabModalitaAccesso",
      defaultSorting: 'descrizione ASC',
      actions: {
        listAction: 'TabModalitaAccesso?action=list',
        createAction:'TabModalitaAccesso?action=create',
        updateAction: 'TabModalitaAccesso?action=update'
//                ,deleteAction: 'TabModalitaAccesso?action=delete'
        },
			options: {
		    sorting: false,
		    defaultSorting: ''
		    },
    
      fields : {
				id : {
					title : '<s:text name="modalitaAccesso.table.id" />',
					sort :true,
					width : '10%',
					edit : true,
					inputClass: "tabTextEdit" 
				},
				descrizione : {
					title : '<s:text name="modalitaAccesso.table.descrizione" />',
					sort :true,
					width : '90%',
					edit : true,
					inputClass: "tabTextEdit" 
				},
			}
		});

       $('#search').keyup(function(){
          $('#ModalitaAccessoTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#ModalitaAccessoTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="modalitaAccesso.legend.title" /></legend>
	<div class="filtering">
      <s:text name="modalitaAccesso.table.key" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="ModalitaAccessoTableContainer"></div>
</fieldset>
