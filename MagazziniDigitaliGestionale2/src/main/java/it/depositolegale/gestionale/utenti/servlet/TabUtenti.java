package it.depositolegale.gestionale.utenti.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.utenti.MDUtentiBusiness;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.entity.MDUtenti;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabUtenti extends BasicTabServlet<MDUtentiBusiness, MDUtenti> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabUtenti() {
	}

	@Override
	protected MDUtentiBusiness newInstanceBusiness() {
		return new MDUtentiBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		if (searchname != null && !searchname.trim().equals("")) {
			dati.put("nome", searchname.trim());
		}

		searchname = request.getParameter("searchcogname");

		if (searchname != null && !searchname.trim().equals("")) {
			dati.put("cognome", searchname.trim());
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request)
			throws HibernateException, HibernateUtilException {
		HashTable<String, Object> dati = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;

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

		if (request.getParameter("cognome") != null) {
			dati.put("cognome", request.getParameter("cognome"));
		}

		if (request.getParameter("nome") != null) {
			dati.put("nome", request.getParameter("nome"));
		}

		if (request.getParameter("amministratore") != null) {
			dati.put("amministratore", new Integer(request.getParameter("amministratore")));
		} else {
			dati.put("amministratore", 0);
		}

		if (request.getParameter("ipAutorizzati") != null) {
			dati.put("ipAutorizzati", request.getParameter("ipAutorizzati"));
		}

		if (request.getParameter("idIstituzioneID") != null) {
			mdIstituzioneDAO = new MDIstituzioneDAO();
			dati.put("idIstituzione", mdIstituzioneDAO.findById(request.getParameter("idIstituzioneID")));
		}

		return dati;
	}

	@Override
	protected String resultOptions(List<MDUtenti> list) {
		return null;
	}
}