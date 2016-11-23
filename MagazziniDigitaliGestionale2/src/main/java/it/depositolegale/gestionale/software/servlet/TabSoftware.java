package it.depositolegale.gestionale.software.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.software.MDSoftwareBusiness;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDRigthsDAO;
import it.bncf.magazziniDigitali.database.entity.MDSoftware;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabSoftware extends BasicTabServlet<MDSoftwareBusiness, MDSoftware> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabSoftware() {
	}



	@Override
	protected MDSoftwareBusiness newInstanceBusiness() {
		return new MDSoftwareBusiness();
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
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException {
		HashTable<String, Object> dati = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;
		MDRigthsDAO mdRigthsDAO = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("nome") != null) {
			dati.put("nome", request.getParameter("nome"));
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

		if (request.getParameter("idIstituzioneID") != null) {
			mdIstituzioneDAO = new MDIstituzioneDAO();
			dati.put("idIstituzione", mdIstituzioneDAO.findById(request.getParameter("idIstituzioneID")));
		}

		if (request.getParameter("idRigthsID") != null) {
			mdRigthsDAO = new MDRigthsDAO();
			dati.put("idRigths", mdRigthsDAO.findById(request.getParameter("idRigthsID")));
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDSoftware> list) {
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