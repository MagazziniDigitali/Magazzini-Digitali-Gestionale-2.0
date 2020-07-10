<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="isMD" value="isMD"/>
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
                listAction: 'TabIstituti?action=list&bibliotecaDepositaria=0',
                <s:if test="%{#isMD=='Si'}">
                createAction:'TabIstituti?action=create&bibliotecaDepositaria=0',
                </s:if>
                updateAction: 'TabIstituti?action=update&bibliotecaDepositaria=0'
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
					edit : false,
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
				pIva : {
					title : '<s:text name="istituti.table.pIva" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
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
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				idRegioneID : {
					title : '<s:text name="istituti.table.idRegione" />',
					edit : true,
					width : '20%',
					visibility : 'hidden',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabRegioni?action=options';
				    }
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
					edit : false,
					visibility : 'hidden',
					type : 'hidden',
					defaultValue :  '0'
				},
//				ipAccreditati : {
//					title : '<s:text name="istituti.table.ipAccreditati" />',
//					edit : true,
//					visibility : 'hidden',
//					inputClass: "tabTextEdit"
//				},
//				interfacciaApiUtente : {
//					title : '<s:text name="istituti.table.interfacciaApiUtente" />',
//					edit : true,
//					visibility : 'hidden',
//					inputClass: "tabTextEdit"
//				},
//				libreriaApiUtente : {
//					title : '<s:text name="istituti.table.libreriaApiUtente" />',
//					edit : true,
//					visibility : 'hidden',
//					inputClass: "tabTextEdit"
//				},
//				emailBagit : {
//					title : '<s:text name="istituti.table.emailBagit" />',
//					edit : true,
//					visibility : 'hidden',
//					inputClass: "tabTextEdit"
//				},
//				pathTmp : {
//					title : '<s:text name="istituti.table.pathTmp" />',
//					edit : true,
//					visibility : 'hidden',
//					inputClass: "tabTextEdit"
//				},
				note : {
					title : '<s:text name="istituti.table.note" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				url : {
					title : '<s:text name="istituti.table.url" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				altaRisoluzione : {
					title : '<s:text name="istituti.table.altaRisoluzione" />',
					edit : true,
					type : 'checkbox',
					values : { '0' : '<s:text name="istituti.table.altaRisoluzione.0" />', 
						'1' : '<s:text name="istituti.table.altaRisoluzione.1" />' }
				},
				bagit : {
					title : '<s:text name="istituti.table.bagit" />',
					edit : true,
					type : 'checkbox',
					values : { '0' : '<s:text name="istituti.table.bagit.0" />', 
						'1' : '<s:text name="istituti.table.bagit.1" />' }
				},
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
