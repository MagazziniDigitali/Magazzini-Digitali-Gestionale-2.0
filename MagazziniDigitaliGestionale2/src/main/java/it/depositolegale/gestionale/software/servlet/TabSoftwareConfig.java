package it.depositolegale.gestionale.software.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.software.MDSoftwareConfigBusiness;
import it.bncf.magazziniDigitali.database.dao.MDNodiDAO;
import it.bncf.magazziniDigitali.database.entity.MDSoftware;
import it.bncf.magazziniDigitali.database.entity.MDSoftwareConfig;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabSoftwareConfig extends BasicTabServlet<MDSoftwareConfigBusiness, MDSoftwareConfig> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabSoftwareConfig() {
	}



	@Override
	protected MDSoftwareConfigBusiness newInstanceBusiness() {
		return new MDSoftwareConfigBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		dati.put("idSoftware", (MDSoftware)ActionContext.getContext().getSession().get("mdSoftware"));
		
		if (searchname != null &&
				!searchname.trim().equals("")){
			dati.put("nome", searchname.trim());
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException {
		HashTable<String, Object> dati = null;
		MDNodiDAO mdNodiDAO = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		dati.put("idSoftware", (MDSoftware)ActionContext.getContext().getSession().get("mdSoftware"));

		if (request.getParameter("nome") != null) {
			dati.put("nome", request.getParameter("nome"));
		}

		if (request.getParameter("descrizione") != null) {
			dati.put("descrizione", request.getParameter("descrizione"));
		}

		if (request.getParameter("value") != null) {
			dati.put("value", request.getParameter("value"));
		}

		if (request.getParameter("idNodoID") != null) {
			mdNodiDAO = new MDNodiDAO();
			dati.put("idNodo", mdNodiDAO.findById(request.getParameter("idNodoID")));
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDSoftwareConfig> list) {
		return null;
	}
}