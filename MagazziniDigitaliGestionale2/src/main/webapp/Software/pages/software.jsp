<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#SoftwareTableContainer').jtable({
			title : '<s:text name="software.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabSoftware",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabSoftware?action=list',
                createAction:'TabSoftware?action=create',
                updateAction: 'TabSoftware?action=update'
//                ,deleteAction: 'TabSoftware?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="software.table.id" />',
					sort :true,
					width : '10%',
					key : true,
					list : true,
					display: function (data) {
				        return '<a href="SoftwareConfig.action?idSoftware='+data.record.id+'">'+data.record.id+'</a>';
				    },
					create : false
				},
				nome : {
					title : '<s:text name="software.table.nome" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				login : {
					title : '<s:text name="software.table.login" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				password : {
					title : '<s:text name="software.table.password" />',
					edit : true,
					inputClass: "tabTextEdit",
					visibility : 'hidden',
					type: 'password'
				},
				ipAutorizzati : {
					title : '<s:text name="software.table.ipAutorizzati" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				bibliotecaDepositaria : {
					title : '<s:text name="biblioteche.table.bibliotecaDepositaria" />',
					edit : true,
//					type : 'checkbox',
					options : { '0' : 'Depositanti', 
						'1' : 'Biblioteche' }
				},
				idIstituzioneID: {
					title : '<s:text name="software.table.idIstituzione" />',
					dependsOn : 'bibliotecaDepositaria',
//					edit : true,
					width : '20%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
						 if (data.source == 'list') {
	                            //Return url of all cities for optimization. 
	                            //This method is called for each row on the table and jTable caches options based on this url.
	                            return 'TabIstituti?action=options';
	                        }
	 
	                        //This code runs when user opens edit/create form or changes country combobox on an edit/create form.
	                        //data.source == 'edit' || data.source == 'create'
	                        return 'TabIstituti?action=options&bibliotecaDepositaria=' + data.dependedValues.bibliotecaDepositaria;
				    }
				},
				idRigthsID: {
					title : '<s:text name="software.table.idRigths" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabRigths?action=options';
				    }
				},
				note : {
					title : '<s:text name="software.table.note" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				}
			}
		});

       $('#search').keyup(function(){
          $('#SoftwareTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#SoftwareTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="software.legend.title" /></legend>
	<div class="filtering">
      <s:text name="software.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="SoftwareTableContainer"></div>
</fieldset>
