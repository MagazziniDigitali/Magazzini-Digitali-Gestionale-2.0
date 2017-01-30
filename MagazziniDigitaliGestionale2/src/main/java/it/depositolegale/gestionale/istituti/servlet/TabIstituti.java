package it.depositolegale.gestionale.istituti.servlet;

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
import it.bncf.magazziniDigitali.businessLogic.istituzione.MDIstituzioneBusiness;
import it.bncf.magazziniDigitali.businessLogic.oggettoDigitale.implement.OggettoDigitale;
import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.RegioniDAO;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.bncf.magazziniDigitali.database.entity.MDRigths;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import it.depositolegale.gestionale.user.action.LoginAction;
import it.magazziniDigitali.xsd.agent.AgentXsd;
import it.magazziniDigitali.xsd.premis.PremisXsd;
import it.magazziniDigitali.xsd.premis.exception.PremisXsdException;
import mx.randalf.hibernate.exception.HibernateUtilException;
import mx.randalf.xsd.exception.XsdException;

public class TabIstituti extends BasicTabServlet<MDIstituzioneBusiness, MDIstituzione> {

	private boolean createPremis = false;

	private Logger log = Logger.getLogger(TabIstituti.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabIstituti() {
	}



	@Override
	protected MDIstituzioneBusiness newInstanceBusiness() {
		return new MDIstituzioneBusiness();
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

		searchname = request.getParameter("bibliotecaDepositaria");

		if (searchname != null &&
				!searchname.trim().equals("") &&
				!searchname.trim().equals("undefined")){
			dati.put("bibliotecaDepositaria", Integer.parseInt(searchname.trim()));
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException {
		HashTable<String, Object> dati = null;
		RegioniDAO regioniDAO = null;

		try {
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

			if (request.getParameter("ipAutorizzati") != null) {
				dati.put("ipAutorizzati", request.getParameter("ipAutorizzati"));
			}

			if (request.getParameter("nome") != null) {
				dati.put("nome", request.getParameter("nome"));
			}

			if (request.getParameter("indirizzo") != null) {
				dati.put("indirizzo", request.getParameter("indirizzo"));
			}

			if (request.getParameter("telefono") != null) {
				dati.put("telefono", request.getParameter("telefono"));
			}

			if (request.getParameter("nomeContatto") != null) {
				dati.put("nomeContatto", request.getParameter("nomeContatto"));
			}

			if (request.getParameter("bibliotecaDepositaria") != null) {
				dati.put("bibliotecaDepositaria", new Integer(request.getParameter("bibliotecaDepositaria")));
			}else {
				dati.put("bibliotecaDepositaria", 0);
			}

			if (request.getParameter("istitutoCentrale") != null) {
				dati.put("istitutoCentrale", new Integer(request.getParameter("istitutoCentrale")));
			}else {
				dati.put("istitutoCentrale", 0);
			}

			if (request.getParameter("ipAccreditati") != null) {
				dati.put("ipAccreditati", request.getParameter("ipAccreditati"));
			}

			if (request.getParameter("interfacciaApiUtente") != null) {
				dati.put("interfacciaApiUtente", request.getParameter("interfacciaApiUtente"));
			}

			if (request.getParameter("libreriaApiUtente") != null) {
				dati.put("libreriaApiUtente", request.getParameter("libreriaApiUtente"));
			}

			if (request.getParameter("emailBagit") != null) {
				dati.put("emailBagit", request.getParameter("emailBagit"));
			}

			if (request.getParameter("pathTmp") != null) {
				dati.put("pathTmp", request.getParameter("pathTmp"));
			}

			if (request.getParameter("note") != null) {
				dati.put("note", request.getParameter("note"));
			}

			if (request.getParameter("url") != null) {
				dati.put("url", request.getParameter("url"));
			}

			if (request.getParameter("idRegioneID") != null &&
					!request.getParameter("idRegioneID").equals("")) {
				regioniDAO = new RegioniDAO();
				dati.put("idRegione", regioniDAO.findById(Integer.parseInt(request.getParameter("idRegioneID"))));
			}
		} catch (NumberFormatException e) {
			throw new HibernateUtilException(e.getMessage(),e);
		} catch (HibernateException e) {
			throw e;
		} catch (HibernateUtilException e) {
			throw e;
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDIstituzione> list) {
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
	protected void checkPreUpdate(MDIstituzioneBusiness business, 
			HttpServletRequest request) throws HibernateException, 
				HibernateUtilException {
		MDIstituzione mdIstituzione = null;
		super.checkPreUpdate(business, request);
		String value = null;
		
		try {
			if (request.getParameter("id") != null &&
					!request.getParameter("id").trim().equals("")){
				mdIstituzione = business.findById(request.getParameter("id"));
				
				if (mdIstituzione != null){
					if (request.getParameter("nome") != null && 
							!request.getParameter("nome").trim().equals("")){
						value = request.getParameter("nome").trim();
					} else {
						value = null;
					}
					if ((mdIstituzione.getNome() == null && value !=null) ||
							( mdIstituzione.getNome() != null && value ==null) ||
							( mdIstituzione.getNome() != null && 
								!mdIstituzione.getNome().trim().equals(value))){
						createPremis=true;
					}

					if (request.getParameter("note") != null && 
							!request.getParameter("note").trim().equals("")){
						value = request.getParameter("note").trim();
					} else {
						value = null;
					}
					if ((mdIstituzione.getNote() == null && value !=null) ||
							( mdIstituzione.getNote() != null && value ==null) ||
							( mdIstituzione.getNote() != null && 
								!mdIstituzione.getNote().trim().equals(value))){
						createPremis=true;
					}

					if (request.getParameter("url") != null && 
							!request.getParameter("url").trim().equals("")){
						value = request.getParameter("url").trim();
					} else {
						value = null;
					}
					if ((mdIstituzione.getUrl() == null && value !=null) ||
							( mdIstituzione.getUrl() != null && value ==null) ||
							( mdIstituzione.getUrl() != null && 
								!mdIstituzione.getUrl().trim().equals(value))){
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
				if (dati.get("url") != null &&
						!((String)dati.get("url")).trim().equals("")){
					agentXsd.addAgentIdentifier(AgentXsd.URI, (String) dati.get("url"));
				}
				
				agentXsd.addAgentName((String) dati.get("nome"));
				agentXsd.setAgentType(PremisXsd.DEPOSITANTE);
				
				if (dati.get("note") != null &&
						!((String)dati.get("note")).trim().equals("")){
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
								"Agent_Depositante",
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