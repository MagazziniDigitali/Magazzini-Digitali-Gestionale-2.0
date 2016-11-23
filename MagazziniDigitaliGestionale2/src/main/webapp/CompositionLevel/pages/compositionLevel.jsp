<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#CompositionLevelTableContainer').jtable({
			title : '<s:text name="compositionLevel.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabCompositionLevel",
            defaultSorting: 'key ASC',
            actions: {
                listAction: 'TabCompositionLevel?action=list',
                createAction:'TabCompositionLevel?action=create',
                updateAction: 'TabCompositionLevel?action=update'
//                ,deleteAction: 'TabCompositionLevel?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="compositionLevel.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					create : false
				},
				key : {
					title : '<s:text name="compositionLevel.table.key" />',
					sort :false,
					width : '10%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				value : {
					title : '<s:text name="compositionLevel.table.value" />',
					edit : true,
					visibility : 'hidden',
					type : 'checkbox',
					values : { '0' : 'No', '1' : 'Si' }
				},
			}
		});

       $('#search').keyup(function(){
          $('#CompositionLevelTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#CompositionLevelTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="compositionLevel.legend.title" /></legend>
	<div class="filtering">
      <s:text name="compositionLevel.table.key" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="CompositionLevelTableContainer"></div>
</fieldset>
