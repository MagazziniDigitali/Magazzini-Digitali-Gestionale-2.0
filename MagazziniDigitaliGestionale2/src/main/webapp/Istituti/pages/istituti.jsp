<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#IstitutiTableContainer').jtable({
			title : '<s:text name="istituti.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabIstitute",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabIstituti?action=list',
                createAction:'TabIstituti?action=create',
                updateAction: 'TabIstituti?action=update'
//                ,deleteAction: 'TabIstituti?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="istituti.table.id" />',
					sort :true,
					width : '30%',
					key : true,
					list : true,
					create : false
				},
				login : {
					title : '<s:text name="istituti.table.login" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				password : {
					title : '<s:text name="istituti.table.password" />',
					edit : true,
					inputClass: "tabTextEdit",
					visibility : 'hidden',
					type: 'password'
				},
				ipAutorizzati : {
					title : '<s:text name="istituti.table.ipAutorizzati" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				nome : {
					title : '<s:text name="istituti.table.nome" />',
					sort :false,
					width : '30%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				indirizzo : {
					title : '<s:text name="istituti.table.indirizzo" />',
					width : '30%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				telefono : {
					title : '<s:text name="istituti.table.telefono" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				nomeContatto : {
					title : '<s:text name="istituti.table.nomeContatto" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				bibliotecaDepositaria : {
					title : '<s:text name="istituti.table.bibliotecaDepositaria" />',
					edit : true,
					visibility : 'hidden',
					type : 'checkbox',
					values : { '0' : 'No', '1' : 'Si' }
				},
				istitutoCentrale : {
					title : '<s:text name="istituti.table.istitutoCentrale" />',
					edit : true,
					visibility : 'hidden',
					type : 'checkbox',
					values : { '0' : 'No', '1' : 'Si' }
				},
				ipAccreditati : {
					title : '<s:text name="istituti.table.ipAccreditati" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				interfacciaApiUtente : {
					title : '<s:text name="istituti.table.interfacciaApiUtente" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				libreriaApiUtente : {
					title : '<s:text name="istituti.table.libreriaApiUtente" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				emailBagit : {
					title : '<s:text name="istituti.table.emailBagit" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				pathTmp : {
					title : '<s:text name="istituti.table.pathTmp" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				}
			}
		});

       $('#search').keyup(function(){
          $('#IstitutiTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#IstitutiTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="istituti.legend.title" /></legend>
	<div class="filtering">
      <s:text name="istituti.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="IstitutiTableContainer"></div>
</fieldset>
