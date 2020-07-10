package it.depositolegale.gestionale.configDefaults.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.configDefaults.MDConfigDefaultsRowBusiness;
import it.bncf.magazziniDigitali.database.entity.MDConfigDefaults;
import it.bncf.magazziniDigitali.database.entity.MDConfigDefaultsRow;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabConfigDefaultsRow extends BasicTabServlet<MDConfigDefaultsRowBusiness, MDConfigDefaultsRow> {

	/**
   * 
   */
  private static final long serialVersionUID = -899748214513964030L;

  public TabConfigDefaultsRow() {
	}

	@Override
	protected MDConfigDefaultsRowBusiness newInstanceBusiness() {
		return new MDConfigDefaultsRowBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		dati.put("idConfigDefaults", (MDConfigDefaults)ActionContext.getContext().getSession().get("mdConfigDefaults"));
		
		if (searchname != null &&
				!searchname.trim().equals("")){
			dati.put("name", searchname.trim());
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

		dati.put("idConfigDefaults", (MDConfigDefaults)ActionContext.getContext().getSession().get("mdConfigDefaults"));

		if (request.getParameter("name") != null) {
			dati.put("name", request.getParameter("name"));
		}

    if (request.getParameter("descrizione") != null) {
      dati.put("descrizione", request.getParameter("descrizione"));
    }
		
		if (request.getParameter("value") != null) {
			dati.put("value", request.getParameter("value"));
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDConfigDefaultsRow> list) {
		return null;
	}

	@Override
	protected String checkSortKey(String string) {
		if (string.equals("idConfigDefaultsID")){
			string = "idConfigDefaults.nome";
		}
		return string;
	}
}