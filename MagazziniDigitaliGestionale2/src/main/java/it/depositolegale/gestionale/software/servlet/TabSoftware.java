package it.depositolegale.gestionale.software.servlet;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.oggettoDigitale.implement.OggettoDigitale;
import it.bncf.magazziniDigitali.businessLogic.software.MDSoftwareBusiness;
import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDRigthsDAO;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.bncf.magazziniDigitali.database.entity.MDRigths;
import it.bncf.magazziniDigitali.database.entity.MDSoftware;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import it.depositolegale.gestionale.user.action.LoginAction;
import it.magazziniDigitali.xsd.agent.AgentXsd;
import it.magazziniDigitali.xsd.premis.PremisXsd;
import it.magazziniDigitali.xsd.premis.exception.PremisXsdException;
import mx.randalf.hibernate.exception.HibernateUtilException;
import mx.randalf.xsd.exception.XsdException;

public class TabSoftware extends BasicTabServlet<MDSoftwareBusiness, MDSoftware> {

	private boolean createPremis = false;

	private Logger log = Logger.getLogger(TabSoftware.class);
	
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

		if (request.getParameter("bibliotecaDepositaria") != null) {
			dati.put("bibliotecaDepositaria", new Integer(request.getParameter("bibliotecaDepositaria")));
		}else {
			dati.put("bibliotecaDepositaria", 0);
		}

		if (request.getParameter("idIstituzioneID") != null) {
			mdIstituzioneDAO = new MDIstituzioneDAO();
			dati.put("idIstituzione", mdIstituzioneDAO.findById(request.getParameter("idIstituzioneID")));
		}

		if (request.getParameter("idRigthsID") != null) {
			mdRigthsDAO = new MDRigthsDAO();
			dati.put("idRigths", mdRigthsDAO.findById(request.getParameter("idRigthsID")));
		}

		if (request.getParameter("note") != null) {
			dati.put("note", request.getParameter("note"));
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

	/**
	 * @throws HibernateUtilException 
	 * @see it.depositolegale.gestionale.servlet.BasicTabServlet#checkPreUpdate(it.bncf.magazziniDigitali.businessLogic.BusinessLogic, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void checkPreUpdate(MDSoftwareBusiness business, 
			HttpServletRequest request) throws HibernateException, 
				HibernateUtilException {
		MDSoftware mdSoftware = null;
		super.checkPreUpdate(business, request);
		String value = null;
		
		try {
			if (request.getParameter("id") != null &&
					!request.getParameter("id").trim().equals("")){
				mdSoftware = business.findById(request.getParameter("id"));
				
				if (mdSoftware != null){
					if (request.getParameter("nome") != null && 
							!request.getParameter("nome").trim().equals("")){
						value = request.getParameter("nome").trim();
					} else {
						value = null;
					}
					if ((mdSoftware.getNome() == null && value !=null) ||
							( mdSoftware.getNome() != null && value ==null) ||
							( mdSoftware.getNome() != null && 
								!mdSoftware.getNome().trim().equals(value))){
						createPremis=true;
					}

					if (request.getParameter("note") != null && 
							!request.getParameter("note").trim().equals("")){
						value = request.getParameter("note").trim();
					} else {
						value = null;
					}
					if ((mdSoftware.getNote() == null && value !=null) ||
							( mdSoftware.getNote() != null && value ==null) ||
							( mdSoftware.getNote() != null && 
								!mdSoftware.getNote().trim().equals(value))){
						createPremis=true;
					}

					if (request.getParameter("idIstituzioneID") != null && 
							!request.getParameter("idIstituzioneID").trim().equals("")){
						value = request.getParameter("idIstituzioneID").trim();
					} else {
						value = null;
					}
					if ((mdSoftware.getIdIstituzione() == null && value !=null) ||
							( mdSoftware.getIdIstituzione() != null && value ==null) ||
							( mdSoftware.getIdIstituzione() != null && 
								!mdSoftware.getIdIstituzione().getId().trim().equals(value))){
						createPremis=true;
					}

					if (request.getParameter("idRigthsID") != null && 
							!request.getParameter("idRigthsID").trim().equals("")){
						value = request.getParameter("idRigthsID").trim();
					} else {
						value = null;
					}
					if ((mdSoftware.getIdRigths() == null && value !=null) ||
							( mdSoftware.getIdRigths() != null && value ==null) ||
							( mdSoftware.getIdRigths() != null && 
								!mdSoftware.getIdRigths().getId().trim().equals(value))){
						createPremis=true;
					}
				} else {
					createPremis=true;
				}
			} else {
				createPremis=true;
			}
		} catch (HibernateException e) {
			throw e;
		} catch (HibernateUtilException e) {
			throw e;
		}
	}

	/**
	 * @throws MDConfigurationException 
	 * @see it.depositolegale.gestionale.servlet.BasicTabServlet#postUpdate(java.lang.String, it.bncf.magazziniDigitali.businessLogic.HashTable)
	 */
	@Override
	protected void postUpdate(String id, HashTable<String, Object> dati) 
			throws MDConfigurationException, PremisXsdException, XsdException, IOException {
		AgentXsd<?, ?, ?, ?, ?, ?> agentXsd = null;
		File filePremis = null;
		GregorianCalendar gc = new GregorianCalendar();
		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df3 = new DecimalFormat("000");
		DecimalFormat df2 = new DecimalFormat("00");
		String agId = null;

		super.postUpdate(id, dati);

		try {
			if (createPremis){
				agId = id+"-"+
						df4.format(gc.get(Calendar.YEAR))+
						df2.format(gc.get(Calendar.MONTH)+1)+
						df2.format(gc.get(Calendar.DAY_OF_MONTH))+
						df2.format(gc.get(Calendar.HOUR_OF_DAY))+
						df2.format(gc.get(Calendar.MINUTE))+
						df2.format(gc.get(Calendar.SECOND))+
						df3.format(gc.get(Calendar.MILLISECOND));
				agentXsd = AgentXsd.initialize();
//				
//				agentXsd.addAgentIdentifier(AgentXsd.UUID_MD_AG_ID, agId);

				agentXsd.addAgentIdentifier(PremisXsd.UUID_MD_AG, id);
				if (dati.get("uri") != null){
					agentXsd.addAgentIdentifier(AgentXsd.URI, (String) dati.get("uri"));
				}
				
				agentXsd.addAgentName((String) dati.get("nome"));
				agentXsd.setAgentType(PremisXsd.SOFTWARE);
				
				if (dati.get("note") != null){
					agentXsd.addAgentNote((String) dati.get("note"));
				}

				if (dati.get("idIstituzione") != null){
					agentXsd.addAgentExtensionDepositante(((MDIstituzione) dati.get("idIstituzione")).getId());
				}
				
				if (dati.get("idRigths") != null) {
					agentXsd.addLinkingRightsStatementIdentifier(((MDRigths)dati.get("idRigths")).getId());
				}
				filePremis = new File(
						OggettoDigitale.genFilePremis(
								LoginAction.mdConfiguration.getSoftwareConfigString("path.premis"), 
								"Agent_Software",
								agId,".premis"));

				agentXsd.write(filePremis, false);
			}
		} catch (MDConfigurationException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (PremisXsdException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (XsdException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
}