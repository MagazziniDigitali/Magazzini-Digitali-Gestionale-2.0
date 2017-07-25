package it.depositolegale.gestionale.event.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.event.MDEventBusiness;
import it.bncf.magazziniDigitali.database.entity.MDEvent;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabEvent extends BasicTabServlet<MDEventBusiness, MDEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabEvent() {
	}



	@Override
	protected MDEventBusiness newInstanceBusiness() {
		return new MDEventBusiness();
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
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException{
		HashTable<String, Object> dati = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("nome") != null) {
			dati.put("nome", request.getParameter("nome"));
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDEvent> list) {
		return null;
	}

	@Override
	protected String checkSortKey(String string) {
		return string;
	}
}