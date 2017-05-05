<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#BibliotecheTableContainer').jtable({
			title : '<s:text name="biblioteche.table.title" />',
            paging: true, //Enable paging
//            pageSize: 3, Set page size (default: 10)           
            columnResizable: true, //Actually, no need to set true since it's default
            columnSelectable: true, //Actually, no need to set true since it's default
            saveUserPreferences: true, //Actually, no need to set true since it's default
            sorting: true,
            class: "tabIstitute",
            defaultSorting: 'nome ASC',
            actions: {
                listAction: 'TabIstituti?action=list&bibliotecaDepositaria=1',
                createAction:'TabIstituti?action=create&bibliotecaDepositaria=1',
                updateAction: 'TabIstituti?action=update&bibliotecaDepositaria=1'
//                ,deleteAction: 'TabIstituti?action=delete'
            },
			options: {
		        sorting: false,
		        defaultSorting: ''
		    },
    
            fields : {
				id : {
					title : '<s:text name="biblioteche.table.id" />',
					sort :true,
					width : '30%',
					key : true,
					list : true,
					create : false
				},
				login : {
					title : '<s:text name="biblioteche.table.login" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit"
				},
				password : {
					title : '<s:text name="biblioteche.table.password" />',
					edit : true,
					inputClass: "tabTextEdit",
					visibility : 'hidden',
					type: 'password'
				},
				nome : {
					title : '<s:text name="biblioteche.table.nome" />',
					sort :false,
					width : '30%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				indirizzo : {
					title : '<s:text name="biblioteche.table.indirizzo" />',
					width : '30%',
					edit : true,
					inputClass: "tabTextEdit"
				},
				idRegioneID : {
					title : '<s:text name="biblioteche.table.idRegione" />',
					edit : true,
					width : '20%',
					inputClass: "tabTextEdit",
					optionsSorting: 'text',
					options: function(data) {
				            return 'TabRegioni?action=options';
				    }
				},
				telefono : {
					title : '<s:text name="biblioteche.table.telefono" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				nomeContatto : {
					title : '<s:text name="biblioteche.table.nomeContatto" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				bibliotecaDepositaria : {
					edit : true,
					visibility : 'hidden',
					type : 'hidden',
					defaultValue :  '1'
				},
				istitutoCentrale : {
					title : '<s:text name="biblioteche.table.istitutoCentrale" />',
					edit : true,
					type : 'checkbox',
					values : { '0' : '<s:text name="biblioteche.table.istitutoCentrale.0" />', 
						'1' : '<s:text name="biblioteche.table.istitutoCentrale.1" />' }
				},
				ipAccreditati : {
					title : '<s:text name="biblioteche.table.ipAccreditati" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				interfacciaApiUtente : {
					title : '<s:text name="biblioteche.table.interfacciaApiUtente" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				libreriaApiUtente : {
					title : '<s:text name="biblioteche.table.libreriaApiUtente" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				note : {
					title : '<s:text name="biblioteche.table.note" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
				url : {
					title : '<s:text name="biblioteche.table.url" />',
					edit : true,
					visibility : 'hidden',
					inputClass: "tabTextEdit"
				},
			}
		});

       $('#search').keyup(function(){
          $('#BibliotecheTableContainer').jtable('load', {searchname: $(this).val()});
       });
		$('#BibliotecheTableContainer').jtable('load');
	});
</script>

<fieldset class="searchMateriale" style="">
	<legend><s:text name="biblioteche.legend.title" /></legend>
	<div class="filtering">
      <s:text name="biblioteche.table.nome" />: <input type="text" id="search"/>
   </div>
   <br/>
	<div id="BibliotecheTableContainer"></div>
</fieldset>
