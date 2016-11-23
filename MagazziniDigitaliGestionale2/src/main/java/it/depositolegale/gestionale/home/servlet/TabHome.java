package it.depositolegale.gestionale.home.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.filesTmp.MDFilesTmpBusiness;
import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDSoftwareDAO;
import it.bncf.magazziniDigitali.database.dao.MDStatoDAO;
import it.bncf.magazziniDigitali.database.entity.MDFilesTmp;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.bncf.magazziniDigitali.database.entity.MDStato;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import it.depositolegale.gestionale.user.action.LoginAction;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabHome extends BasicTabServlet<MDFilesTmpBusiness, MDFileTmpTab> {

	private Logger log = Logger.getLogger(TabHome.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabHome() {
	}



	@Override
	protected MDFilesTmpBusiness newInstanceBusiness() {
		return new MDFilesTmpBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;
		String stato = null;
		MDStatoDAO mdStatoDAO = null;
		MDStato[] mdStatos = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;
		MDIstituzione mdIstituzione = null;

		try {
			searchname = request.getParameter("nomeFile");

			dati = new HashTable<String, Object>();
			if (searchname != null &&
					!searchname.trim().equals("")){
				dati.put("nomeFile", searchname.trim());
			}

			stato = request.getParameter("stato");
			if (stato != null &&
					!stato.trim().equals("")){
				mdStatoDAO = new MDStatoDAO();
				mdStatos = new MDStato[1];
				mdStatos[0] = mdStatoDAO.findById(stato.trim());
				dati.put("stato", mdStatos);
			}
			
			if (ActionContext.getContext().getSession().get("idIstituto")!=null){
				mdIstituzioneDAO = new MDIstituzioneDAO();
				mdIstituzione = mdIstituzioneDAO.findById((String) ActionContext.getContext().getSession().get("idIstituto"));
				dati.put("idIstituto", mdIstituzione);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (HibernateUtilException e) {
			e.printStackTrace();
		}
		
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException{
		HashTable<String, Object> dati = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;
		MDSoftwareDAO mdSoftwareDAO = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("idIstituzioneID") != null) {
			mdIstituzioneDAO = new MDIstituzioneDAO();
			dati.put("idIstituzione", mdIstituzioneDAO.findById(request.getParameter("idIstituzioneID")));
		}

		if (request.getParameter("idSoftwareID") != null) {
			mdSoftwareDAO = new MDSoftwareDAO();
			dati.put("idSoftware", mdSoftwareDAO.findById(request.getParameter("idSoftwareID")));
		}

		if (request.getParameter("nomeFile") != null) {
			dati.put("nomeFile", request.getParameter("nomeFile"));
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDFileTmpTab> list) {
		return null;
	}

	/**
	 * @see it.depositolegale.gestionale.servlet.BasicTabServlet#find(it.bncf.magazziniDigitali.businessLogic.BusinessLogic, it.bncf.magazziniDigitali.businessLogic.HashTable, int, int)
	 */
	@Override
	protected String find(MDFilesTmpBusiness business, HashTable<String, Object> dati, int startPageIndex,
			int recordsPerPage) throws HibernateException, HibernateUtilException {
		List<MDFilesTmp> mdFilesTmps = null;
		String jsonArray = "";
		boolean primo=true;
		
		mdFilesTmps = business.find(dati, startPageIndex, recordsPerPage);
		jsonArray += "[\n";
		if (mdFilesTmps != null){
			for (MDFilesTmp mdFilesTmp : mdFilesTmps){
				
				jsonArray +=(primo?"":",\n");
				primo=false;
				jsonArray += "  {\n";
				jsonArray += "    \"id\": \""+mdFilesTmp.getId()+"\",\n";
				jsonArray += "    \"nomeFile\": \""+mdFilesTmp.getNomeFile()+"\",\n";
				try {
					if (mdFilesTmp.getStato() != null){
						jsonArray += "    \"statoName\": \""+mdFilesTmp.getStato().getDescrizione()+"\",\n";
					}

					jsonArray += "    \"statoJob\": \""+
							LoginAction.mdConfiguration.getStatoJob("Job_" + mdFilesTmp.getId(), mdFilesTmp.getId())+
							"\"\n";
				} catch (MDConfigurationException e) {
					log.error(e.getMessage(), e);
				}

				
				
				jsonArray += "  }";
			}
		}
		jsonArray += "\n]\n";
//		System.out.println(jsonArray);
		return jsonArray;
	}
}