package it.depositolegale.gestionale.compositionLevel.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.compositionLevel.MDCompositionLevelBusiness;
import it.bncf.magazziniDigitali.database.entity.MDCompositionLevel;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabCompositionLevel extends BasicTabServlet<MDCompositionLevelBusiness, MDCompositionLevel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabCompositionLevel() {
	}

	@Override
	protected MDCompositionLevelBusiness newInstanceBusiness() {
		return new MDCompositionLevelBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		if (searchname != null && !searchname.trim().equals("")) {
			dati.put("key", searchname.trim());
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request)
			throws HibernateException, HibernateUtilException {
		HashTable<String, Object> dati = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("key") != null) {
			dati.put("key", request.getParameter("key"));
		}

		if (request.getParameter("value") != null) {
			dati.put("value", new Integer(request.getParameter("value")));
		} else {
			dati.put("value", 0);
		}

		return dati;
	}

	@Override
	protected String resultOptions(List<MDCompositionLevel> list) {
		return null;
	}

	@Override
	protected String checkSortKey(String string) {
		return string;
	}
}