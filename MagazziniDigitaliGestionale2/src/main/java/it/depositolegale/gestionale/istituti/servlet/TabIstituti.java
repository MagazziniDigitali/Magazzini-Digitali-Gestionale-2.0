package it.depositolegale.gestionale.istituti.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.istituzione.MDIstituzioneBusiness;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.depositolegale.gestionale.servlet.BasicTabServlet;

public class TabIstituti extends BasicTabServlet<MDIstituzioneBusiness, MDIstituzione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabIstituti() {
	}



	@Override
	protected MDIstituzioneBusiness newInstanceBusiness() {
		return new MDIstituzioneBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		if (searchname != null &&
				!searchname.trim().equals("")){
			dati.put("nome", searchname.trim());
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) {
		HashTable<String, Object> dati = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("login") != null) {
			dati.put("login", request.getParameter("login"));
		}

		if (request.getParameter("password") != null) {
			dati.put("password", request.getParameter("password"));
		}

		if (request.getParameter("ipAutorizzati") != null) {
			dati.put("ipAutorizzati", request.getParameter("ipAutorizzati"));
		}

		if (request.getParameter("nome") != null) {
			dati.put("nome", request.getParameter("nome"));
		}

		if (request.getParameter("indirizzo") != null) {
			dati.put("indirizzo", request.getParameter("indirizzo"));
		}

		if (request.getParameter("telefono") != null) {
			dati.put("telefono", request.getParameter("telefono"));
		}

		if (request.getParameter("nomeContatto") != null) {
			dati.put("nomeContatto", request.getParameter("nomeContatto"));
		}

		if (request.getParameter("bibliotecaDepositaria") != null) {
			dati.put("bibliotecaDepositaria", new Integer(request.getParameter("bibliotecaDepositaria")));
		}else {
			dati.put("bibliotecaDepositaria", 0);
		}

		if (request.getParameter("istitutoCentrale") != null) {
			dati.put("istitutoCentrale", new Integer(request.getParameter("istitutoCentrale")));
		}else {
			dati.put("istitutoCentrale", 0);
		}

		if (request.getParameter("ipAccreditati") != null) {
			dati.put("ipAccreditati", request.getParameter("ipAccreditati"));
		}

		if (request.getParameter("interfacciaApiUtente") != null) {
			dati.put("interfacciaApiUtente", request.getParameter("interfacciaApiUtente"));
		}

		if (request.getParameter("libreriaApiUtente") != null) {
			dati.put("libreriaApiUtente", request.getParameter("libreriaApiUtente"));
		}

		if (request.getParameter("emailBagit") != null) {
			dati.put("emailBagit", request.getParameter("emailBagit"));
		}

		if (request.getParameter("pathTmp") != null) {
			dati.put("pathTmp", request.getParameter("pathTmp"));
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDIstituzione> list) {
		String result = "";
		if (list != null && list.size()>0){
			for (int x=0; x<list.size(); x++){
				result += (x>0?", ":"");
				result +="{\"Value\":\""+list.get(x).getId()+"\", \"DisplayText\":\""+list.get(x).getNome()+"\"}";
			}
		}
		return result;
	}
}