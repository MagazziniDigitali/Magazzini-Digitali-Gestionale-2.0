/**
 * 
 */
package it.depositolegale.gestionale.home.action.preIscrizione;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.istituzione.MDIstituzioneBusiness;
import it.bncf.magazziniDigitali.businessLogic.preRegistrazione.MDPreRegistrazioneBusiness;
import it.bncf.magazziniDigitali.businessLogic.software.MDSoftwareBusiness;
import it.bncf.magazziniDigitali.businessLogic.software.MDSoftwareConfigBusiness;
import it.bncf.magazziniDigitali.businessLogic.utenti.MDUtentiBusiness;
import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDPreRegistrazioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDRigthsDAO;
import it.bncf.magazziniDigitali.database.dao.MDSoftwareConfigDAO;
import it.bncf.magazziniDigitali.database.dao.MDSoftwareDAO;
import it.bncf.magazziniDigitali.database.dao.MDUtentiDAO;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.bncf.magazziniDigitali.database.entity.MDNodi;
import it.bncf.magazziniDigitali.database.entity.MDPreRegistrazione;
import it.bncf.magazziniDigitali.database.entity.MDRigths;
import it.bncf.magazziniDigitali.database.entity.MDSoftware;
import it.bncf.magazziniDigitali.database.entity.MDSoftwareConfig;
import it.bncf.magazziniDigitali.database.entity.MDUtenti;
import it.bncf.magazziniDigitali.utils.email.SendEmail;
import it.bncf.magazziniDigitali.utils.password.PassGen;
import it.depositolegale.gestionale.home.action.preIscrizione.exception.ValidatePreIscrizioneException;
import it.depositolegale.gestionale.user.action.LoginAction;
import mx.randalf.hibernate.exception.HibernateUtilException;

/**
 * @author massi
 *
 */
public class ValidatePreIscrizione extends SendEmail{

	private Logger log = Logger.getLogger(ValidatePreIscrizione.class);

	private String checkId = null;

	/**
	 * 
	 */
	public ValidatePreIscrizione(String checkId, String login, String password) {
		super(login, password);
		this.checkId = checkId;
	}

	public void inizializzaUtente() throws ValidatePreIscrizioneException {
		MDPreRegistrazioneBusiness mdPreRegistrazioneBusiness = null;
		MDPreRegistrazioneDAO mdPreRegistrazioneDAO = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;
		MDPreRegistrazione mdPreRegistrazione = null;
		MDIstituzione mdIstituzione = null;
		MDUtenti mdUtenti = null;
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar gcData = new GregorianCalendar();
		HashTable<String, Object> dati = null;
		String password = null;

		try {
			mdPreRegistrazioneDAO = new MDPreRegistrazioneDAO();
			mdPreRegistrazione = mdPreRegistrazioneDAO.findById(checkId);
			if (mdPreRegistrazione != null) {
				gcData.setTimeInMillis(mdPreRegistrazione.getDataPreIscrizione().getTime());
				gcData.set(Calendar.DAY_OF_MONTH, 2);
				dati = new HashTable<String, Object>();
				dati.put("id", checkId);
				if (mdPreRegistrazione.getEmailValidata() == -1) {
					throw new ValidatePreIscrizioneException(
							"La richiesta di attivazione [" + checkId + "] risulta già scaduta");
				} else if (gcData.getTimeInMillis() < gc.getTimeInMillis()) {
					dati.put("emailValidata", -1);
					mdPreRegistrazioneBusiness = new MDPreRegistrazioneBusiness();
					mdPreRegistrazioneBusiness.save(dati);
					throw new ValidatePreIscrizioneException(
							"La richiesta di attivazione [" + checkId + "] risulta già scaduta");
				} else {
					password = PassGen.generateSessionKey();
					if (mdPreRegistrazione.getIdIstituzione() == null) {
						mdIstituzioneDAO = new MDIstituzioneDAO();
						mdIstituzione = mdIstituzioneDAO.findByPIva(mdPreRegistrazione.getIstituzionePIva());
						if (mdIstituzione==null){
							mdIstituzione = createIstituzione(mdPreRegistrazione, password);
							if (mdPreRegistrazione.getAltaRisoluzione() == 1) {
								createSoftwareAltaRisoluzione(mdPreRegistrazione, password, mdIstituzione);
							}
						}
						dati.put("idIstituzione", mdIstituzione);
					} else {
						mdIstituzione = mdPreRegistrazione.getIdIstituzione();
					}

					mdUtenti = createUtente(mdPreRegistrazione, password, mdIstituzione);
					dati.put("idUtente", mdUtenti);

					dati.put("emailValidata", 1);
					mdPreRegistrazioneBusiness = new MDPreRegistrazioneBusiness();
					mdPreRegistrazioneBusiness.save(dati);
					sendMsg(mdPreRegistrazione.getUtenteEmail(), mdPreRegistrazione.getUtenteNome(), 
							mdPreRegistrazione.getUtenteCognome(), mdPreRegistrazione.getUtenteCodiceFiscale(), 
							password);
				}
			} else {
				throw new ValidatePreIscrizioneException("Le credenziali [" + checkId + "] non sono valide");
			}
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (ValidatePreIscrizioneException e) {
			throw e;
		} catch (MessagingException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		}
	}

	private void sendMsg(String to,String nome, String cognome, String login, String password) throws MessagingException {
		sendMsg(to, "Magazzini Digitali - Esito registrazione", 
				"<center><img src=\"http://www.depositolegale.it/wp-content/uploads/2010/10/logo.png\"/></center><br/>"
				+ "<br/>Buongiorno, Sig. "+nome+" "+cognome+"<br/>"
				+ "</br/>La richiesta di registrazione &egrave; stata confermata.<br/>"+
				"<br/>"+
				"Le credenziali per l'accesso sono:<br/>"
				+ "<br/>"
				+ "Login: <b>"+login+"</b><br/>"
				+ "Passowrd: <b>"+password+"</b><br/>"
				+ "<br/>"
				+ "In caso di necessit&agrave; ci puole contattare all'indirizzo info@depositolegale.it.<br/>" + 
				"<br/>"
				+ "Staf Magazzini Digitali");
	}

	private MDUtenti createUtente(MDPreRegistrazione mdPreRegistrazione, String password, MDIstituzione mdIstituzione ) throws ValidatePreIscrizioneException {
		MDUtentiBusiness mdUtentiBusiness = null;
		MDUtentiDAO mdUtentiDAO = null;
		MDUtenti mdUtenti = null;
		String id = null;
		HashTable<String, Object> dati = null;

		try {
			mdUtentiBusiness = new MDUtentiBusiness();
			dati = new HashTable<String, Object>();

			dati.put("login", mdPreRegistrazione.getUtenteCodiceFiscale());
			dati.put("password", password);
			dati.put("cognome", mdPreRegistrazione.getUtenteCognome());
			dati.put("nome", mdPreRegistrazione.getUtenteNome());
			dati.put("amministratore", 0);
			dati.put("ipAutorizzati", "*.*.*.*");
			dati.put("idIstituzione", mdIstituzione);

			if (mdPreRegistrazione.getUtenteNote() != null) {
				dati.put("note", mdPreRegistrazione.getUtenteNote());
			}

			dati.put("codiceFiscale", mdPreRegistrazione.getUtenteCodiceFiscale());
			dati.put("email", mdPreRegistrazione.getUtenteEmail().trim().toLowerCase());
			
			id = mdUtentiBusiness.save(dati);

			mdUtentiDAO = new MDUtentiDAO();
			mdUtenti = mdUtentiDAO.findById(id);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		}

		return mdUtenti;
	}

	private MDIstituzione createIstituzione(MDPreRegistrazione mdPreRegistrazione, String password)
			throws ValidatePreIscrizioneException {
		MDIstituzioneBusiness mdIstituzioneBusiness = null;
		MDIstituzione mdIstituzione = null;
		String id = null;
		HashTable<String, Object> dati = null;
		DecimalFormat df11 = new DecimalFormat("00000000000"); 

		try {
			mdIstituzioneBusiness = new MDIstituzioneBusiness();

			dati = new HashTable<String, Object>();
			if (mdPreRegistrazione.getIstituzionePIva().length()==16){
				dati.put("login", mdPreRegistrazione.getIstituzionePIva());
			} else {
				try{
					dati.put("login", ""+df11.format(new Long(mdPreRegistrazione.getIstituzionePIva())));
				} catch (IllegalArgumentException e){
					dati.put("login", mdPreRegistrazione.getIstituzionePIva());
				}
			}
			dati.put("password", password);
			dati.put("nome", mdPreRegistrazione.getIstituzioneNome());
			dati.put("indirizzo", mdPreRegistrazione.getIstituzioneIndirizzo());

			if (mdPreRegistrazione.getIstituzioneTelefono() != null) {
				dati.put("telefono", mdPreRegistrazione.getIstituzioneTelefono());
			}

			if (mdPreRegistrazione.getIstituzioneNomeContatto() != null) {
				dati.put("nomeContatto", mdPreRegistrazione.getIstituzioneNomeContatto());
			} else {
				dati.put("nomeContatto",
						mdPreRegistrazione.getUtenteCognome() + " " + mdPreRegistrazione.getUtenteNome());
			}

			dati.put("bibliotecaDepositaria", 0);
			dati.put("istitutoCentrale", 0);

			if (mdPreRegistrazione.getAltaRisoluzione() == 1) {
				dati.put("pathTmp", LoginAction.mdConfiguration.getSoftwareConfigString("path.tmp") + File.separator
						+ dati.get("login"));
			}

			if (mdPreRegistrazione.getIstituzioneNote() != null) {
				dati.put("note", mdPreRegistrazione.getIstituzioneNote());
			}

			if (mdPreRegistrazione.getIstituzioneUrl() != null) {
				dati.put("url", mdPreRegistrazione.getIstituzioneUrl());
			}

			dati.put("idRegione", mdPreRegistrazione.getIdRegione());

			dati.put("pIva", dati.get("login"));

			if (mdPreRegistrazione.getAltaRisoluzione()==1){
				dati.put("altaRisoluzione", "1");
			} else {
				dati.put("altaRisoluzione", "0");
			}
			if ((mdPreRegistrazione.getRivisteAperte()!= null &&
					!mdPreRegistrazione.getRivisteAperte().trim().equals("")) ||
					(mdPreRegistrazione.getRivisteRistrette()!= null &&
					!mdPreRegistrazione.getRivisteRistrette().trim().equals("")) ||
					(mdPreRegistrazione.getEbookAperte()!= null &&
					!mdPreRegistrazione.getEbookAperte().trim().equals("")) ||
					(mdPreRegistrazione.getEbookRistrette()!= null &&
					!mdPreRegistrazione.getEbookRistrette().trim().equals(""))){
				dati.put("bagit", "1");
			} else {
				dati.put("bagit", "0");
			}
			id = mdIstituzioneBusiness.save(dati);
			mdIstituzione = mdIstituzioneBusiness.findById(id);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (MDConfigurationException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		}
		return mdIstituzione;
	}

	private void createSoftwareAltaRisoluzione(MDPreRegistrazione mdPreRegistrazione, String password,
			MDIstituzione mdIstituzione) throws ValidatePreIscrizioneException {
		MDSoftwareBusiness mdSoftwareBusiness = null;
		MDRigthsDAO mdRigthsDAO = null;
		MDSoftwareDAO mdSoftwareDAO = null;
		String id = null;
		HashTable<String, Object> dati = null;
		List<MDRigths> mdRigths = null;

		try {
			mdSoftwareBusiness = new MDSoftwareBusiness();
			dati = new HashTable<String, Object>();

			dati.put("nome", "Trasferimento Dati da " + mdPreRegistrazione.getIstituzionePIva());
			dati.put("login", "TD_"+mdPreRegistrazione.getIstituzionePIva());
			dati.put("password", password);
			dati.put("ipAutorizzati", "127.0.0.1");
			dati.put("bibliotecaDepositaria", 0);
			dati.put("idIstituzione", mdIstituzione);

			mdRigthsDAO = new MDRigthsDAO();
			mdRigths = mdRigthsDAO.find("Alta Risoluzione", null);
			if (mdRigths != null && mdRigths.size() > 0) {
				for (int x = 0; x < mdRigths.size(); x++) {
					if (mdRigths.get(x).getNome().trim().equalsIgnoreCase("Alta Risoluzione")) {
						dati.put("idRigths", mdRigths.get(x));
						break;
					}
				}
			}

			id = mdSoftwareBusiness.save(dati);

			mdSoftwareDAO = new MDSoftwareDAO();
			duplicaConfAltaRisoluzione("TD_MD", mdSoftwareDAO.findById(id), password,
					mdPreRegistrazione.getIstituzionePIva());
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (ValidatePreIscrizioneException e) {
			throw e;
		}
	}

	private void duplicaConfAltaRisoluzione(String id, MDSoftware newMdSoftware, String password, String pIva)
			throws ValidatePreIscrizioneException {
		MDSoftwareDAO mdSoftwareDAO = null;
		MDSoftwareConfigDAO mdSoftwareConfigDAO = null;
		MDSoftware mdSoftware = null;
		List<MDSoftwareConfig> mdSoftwareConfigs = null;
		List<MDSoftware> mdSoftwares = null;
		MDSoftwareConfig mdSoftwareConfig = null;
		String rsync = null;

		try {
			mdSoftwareDAO = new MDSoftwareDAO();
			mdSoftwares = mdSoftwareDAO.find(null,id,null);
			for (int x=0;x<mdSoftwares.size(); x++){
				if (mdSoftwares.get(x).getLogin().equals(id)){
					mdSoftware = mdSoftwares.get(x);
					break;
				}
			}

			if (mdSoftware != null){
				mdSoftwareConfigDAO = new MDSoftwareConfigDAO();
				mdSoftwareConfigs = mdSoftwareConfigDAO.find(mdSoftware, null, null);
				for (int x = 0; x < mdSoftwareConfigs.size(); x++) {
					mdSoftwareConfig = mdSoftwareConfigs.get(x);
					if (mdSoftwareConfig.getNome().equals("sendRsync")) {
						rsync = mdSoftwareConfig.getValue();
						rsync = rsync.substring(0, rsync.lastIndexOf("/"));
						createSoftwareConfig(newMdSoftware, mdSoftwareConfig.getNome(), mdSoftwareConfig.getDescrizione(),
								rsync + "/"+pIva, null);
					} else if (mdSoftwareConfig.getNome().equals("sendRsyncPwd")) {
						createSoftwareConfig(newMdSoftware, mdSoftwareConfig.getNome(), mdSoftwareConfig.getDescrizione(),
								password, null);
					} else {
						createSoftwareConfig(newMdSoftware, mdSoftwareConfig.getNome(), mdSoftwareConfig.getDescrizione(),
								mdSoftwareConfig.getValue(), mdSoftwareConfig.getIdNodo());
					}
				}
			}
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (ValidatePreIscrizioneException e) {
			throw e;
		}
	}

	private void createSoftwareConfig(MDSoftware newMdSoftware, String nome, String descrizione, String value,
			MDNodi idNodo) throws ValidatePreIscrizioneException {
		MDSoftwareConfigBusiness mdSoftwareConfigBusiness = null;
		HashTable<String, Object> dati = null;

		try {
			mdSoftwareConfigBusiness = new MDSoftwareConfigBusiness();

			dati = new HashTable<String, Object>();
			dati.put("idSoftware", newMdSoftware);

			if (nome != null) {
				dati.put("nome", nome);
			}

			if (descrizione != null) {
				dati.put("descrizione", descrizione);
			}

			if (value != null) {
				dati.put("value", value);
			}

			if (idNodo != null) {
				dati.put("idNodo", idNodo);
			}
			mdSoftwareConfigBusiness.save(dati);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
			throw new ValidatePreIscrizioneException(e.getMessage(), e);
		}
	}

}
