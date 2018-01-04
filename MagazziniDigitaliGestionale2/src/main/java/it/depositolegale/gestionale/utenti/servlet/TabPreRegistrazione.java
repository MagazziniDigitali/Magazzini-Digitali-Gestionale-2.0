package it.depositolegale.gestionale.utenti.servlet;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.exception.BusinessLogicException;
import it.bncf.magazziniDigitali.businessLogic.preRegistrazione.MDPreRegistrazioneBusiness;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.entity.MDPreRegistrazione;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabPreRegistrazione extends BasicTabServlet<MDPreRegistrazioneBusiness, MDPreRegistrazione> {

//	private boolean createPremis = false;
//
//	private static Logger log = Logger.getLogger(TabPreRegistrazione.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabPreRegistrazione() {
	}

	@Override
	protected MDPreRegistrazioneBusiness newInstanceBusiness() {
		return new MDPreRegistrazioneBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		if (searchname != null && !searchname.trim().equals("")) {
			dati.put("utenteNome", searchname.trim());
		}

		searchname = request.getParameter("searchcogname");

		if (searchname != null && !searchname.trim().equals("")) {
			dati.put("utenteCognome", searchname.trim());
		}
		
		dati.put("emailValidata", "0,1,-1,-2");
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

		if (request.getParameter("note") != null) {
			dati.put("note", request.getParameter("note"));
		}

		if (request.getParameter("codiceFiscale") != null) {
			dati.put("codiceFiscale", request.getParameter("codiceFiscale"));
		}

		if (request.getParameter("email") != null) {
			dati.put("email", request.getParameter("email"));
		}

		return dati;
	}

	@Override
	protected String resultOptions(List<MDPreRegistrazione> list) {
		return null;
	}

	@Override
	protected String checkSortKey(String string) {
		if (string.equals("idIstituzioneID")){
			string = "idIstituzione.nome";
		}
		return string;
	}

	/* (non-Javadoc)
	 * @see it.depositolegale.gestionale.servlet.BasicTabServlet#convertJson(it.bncf.magazziniDigitali.businessLogic.BusinessLogic, java.util.List)
	 */
	@Override
	protected String convertJson(MDPreRegistrazioneBusiness business, List<MDPreRegistrazione> lists)
			throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			BusinessLogicException {
		String jsonArray = null;
		boolean primo = true;

		try {
			jsonArray = "[\n";
			if (lists != null){
				for (MDPreRegistrazione list : lists){
					
					jsonArray +=(primo?"":",\n");
					primo=false;
					jsonArray += "  {\n";
					jsonArray += "    \"id\": \""+list.getId()+"\",\n";
					jsonArray += "    \"utenteCognome\": \""+list.getUtenteCognome()+"\",\n";
					jsonArray += "    \"utenteNome\": \""+list.getUtenteNome()+"\",\n";
					jsonArray += "    \"dataPreIscrizione\": \""+list.getDataPreIscrizione()+"\",\n";
					jsonArray += "    \"dataEmailValidazione1\": \"";
					if (list.getDataEmailValidata1() != null){
						jsonArray += list.getDataEmailValidata1();
					}
					jsonArray += "\",\n";
					
					jsonArray += "    \"emailValidata\": \""+list.getEmailValidata()+"\",\n";
					if (list.getEmailValidata()==0){
						jsonArray += "    \"msg\": \"Invia email richiedente\",\n";
						jsonArray += "    \"emailValidataDesc\": \"In attesa di validazione della Email\"\n";
					} else if (list.getEmailValidata()==1){
						jsonArray += "    \"msg\": \"\",\n";
						jsonArray += "    \"emailValidataDesc\": \"In attesa di conferma dell'amministratore\"\n";
					} else if (list.getEmailValidata()==2){
						jsonArray += "    \"msg\": \"\",\n";
						jsonArray += "    \"emailValidataDesc\": \"Utente confermato\"\n";
					} else if (list.getEmailValidata()==-1){
						jsonArray += "    \"msg\": \"Conferma Email richiedente\",\n";
						jsonArray += "    \"emailValidataDesc\": \"Scaduto il Tempo di Validazione della email\"\n";
					} else if (list.getEmailValidata()==-2){
						jsonArray += "    \"msg\": \"Conferma dell'amministratore\",\n";
						jsonArray += "    \"emailValidataDesc\": \"Scaduto il Tempo di Conferma dell'amministratore\"\n";
					}
					
					jsonArray += "  }";
				}
			}
			jsonArray += "\n]\n";
		} catch (SecurityException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		}

		return jsonArray;
	}
}