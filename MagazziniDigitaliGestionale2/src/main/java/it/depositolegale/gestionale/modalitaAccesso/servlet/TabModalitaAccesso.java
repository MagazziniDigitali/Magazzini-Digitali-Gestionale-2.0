package it.depositolegale.gestionale.modalitaAccesso.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.modalitaAccesso.MDModalitaAccessoBusiness;
import it.bncf.magazziniDigitali.database.entity.MDModalitaAccesso;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabModalitaAccesso extends BasicTabServlet<MDModalitaAccessoBusiness, MDModalitaAccesso> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabModalitaAccesso() {
	}



	@Override
	protected MDModalitaAccessoBusiness newInstanceBusiness() {
		return new MDModalitaAccessoBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		if (searchname != null &&
				!searchname.trim().equals("")){
			dati.put("descrizione", searchname.trim());
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException {
		HashTable<String, Object> dati = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("descrizione") != null) {
			dati.put("descrizione", request.getParameter("descrizione"));
		}

		return dati;
	}

	@Override
	protected String resultOptions(List<MDModalitaAccesso> list) {
		String result = "";
		if (list != null && list.size()>0){
			for (int x=0; x<list.size(); x++){
				result += (x>0?", ":"");
				result +="{\"Value\":\""+list.get(x).getId()+"\", \"DisplayText\":\""+list.get(x).getDescrizione()+"\"}";
			}
		}
		return result;
	}
}