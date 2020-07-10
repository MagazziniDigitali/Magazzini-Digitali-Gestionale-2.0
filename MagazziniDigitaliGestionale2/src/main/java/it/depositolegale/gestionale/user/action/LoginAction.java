/**
 * 
 */
package it.depositolegale.gestionale.user.action;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDUtentiDAO;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.bncf.magazziniDigitali.database.entity.MDUtenti;
import it.depositolegale.configuration.MDConfiguration;
import it.depositolegale.www.utenti.Utenti;
import mx.randalf.hibernate.exception.HibernateUtilException;


/**
 * @author massi
 *
 */
public class LoginAction extends ActionSupport {

	public Logger log = LogManager.getLogger(LoginAction.class);

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String ERROR = "error";

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String LOGIN = "login";

	/**
	 * Utilizzato per la indicare le pagine di Conferma
	 */
	public static String CONFIRM = "confirm";

	/**
	 * Utilizzato per la indicare le pagine di Home
	 */
	public static String HOME = "home";

	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;

	/**
	 * Indica l'utente identificato oppure richiesto di essere autenticato
	 */
	protected String username = null;

	/**
	 * Indica la password indicata
	 */
	protected String password = null;

	/**
	 * Indica l'indentificativo dell'istituto
	 */
	protected String idIstituto = null;

	protected String istituto = null;

	protected String idUtente = null;

	protected String utente = null;

	protected String amministratore = null;

	protected String superAdmin = null;

	protected Integer altaRisoluzione = null;

	protected Integer bagit = null;
	
	protected String pIva = null;

	public static MDConfiguration mdConfiguration = null;

	private String logout = null;

	/**
	 * 
	 */
	public LoginAction() {
		try {
			if (LoginAction.mdConfiguration== null){
				LoginAction.mdConfiguration = 
					new MDConfiguration("GS",
							(String)ActionContext.getContext().getApplication().get("nomeCatalogo"));
			} else {
				LoginAction.mdConfiguration.init();
			}
		} catch (MDConfigurationException e) {
			log.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
	}

	@Override
	public String execute() throws Exception {
		Map<String, Object> session = null;
		if (hasActionErrors()){
			return ERROR;
		} else if (LoginAction.mdConfiguration !=null &&
				LoginAction.mdConfiguration.getSoftware()==null){
			addActionError(getText("software.nonIdentificato"));
			return ERROR;
		} else if (logout != null){
			deleteSession();
			return LOGIN;
		} else {
			session = ActionContext.getContext().getSession();
			if (session.get("logined") != null &&
					session.get("logined").equals("true")){
				readSession(session);
				return HOME;
			} else {
				return LOGIN;
			}
		}
	}

	protected void readSession(Map<String, Object> session){
		username = (String) session.get("username");
		if (session.get("idIstituto") != null){
			idIstituto = (String) session.get("idIstituto");
			istituto = (String) session.get("istituto");
		}
		idUtente = (String) session.get("idUtente");
		utente = (String) session.get("utente");
		amministratore = (String) session.get("amministratore");
		superAdmin = (String) session.get("superAdmin");
		altaRisoluzione = (Integer) session.get("altaRisoluzione");
		bagit = (Integer) session.get("bagit");
		pIva = (String) session.get("pIva");
	}

	protected void deleteSession() {
		Map<String, Object> session = null;
		session = ActionContext.getContext().getSession();
		session.remove("logined");
		ActionContext.getContext().setSession(session);
	}

	protected void initSession(Utenti utenti){
		Map<String, Object> session = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;
		MDIstituzione mdIstituzione = null;
		MDUtentiDAO mdUtentiDAO = null;
		MDUtenti mdUtenti= null;
		String idIstituzione = null;

		session = ActionContext.getContext().getSession();
		session.put("logined", "true");
		session.put("username", username);
		if (utenti.getDatiUtente().getIstituzione() != null){
			idIstituzione = utenti.getDatiUtente().getIstituzione().getId();
			try {
				mdIstituzioneDAO = new MDIstituzioneDAO();
				mdIstituzione = mdIstituzioneDAO.findById(idIstituzione);
			} catch (HibernateException e) {
			} catch (HibernateUtilException e) {
			}
			if (mdIstituzione != null){
				if (mdIstituzione.getAltaRisoluzione()!= null){
					session.put("altaRisoluzione", mdIstituzione.getAltaRisoluzione());
				} else {
					session.put("altaRisoluzione", 0);
				}
				if (mdIstituzione.getBagit()!= null){
					session.put("bagit", mdIstituzione.getBagit());
				} else {
					session.put("bagit", 0);
				}
				if (mdIstituzione.getpIva()!= null){
					session.put("pIva", mdIstituzione.getpIva());
				} else {
					session.put("pIva", "");
				}
			} else {
				session.put("altaRisoluzione", 0);
				session.put("bagit", 0);
				session.put("pIva", "");
			}
			session.put("idIstituto", idIstituzione);
			session.put("istituto", utenti.getDatiUtente().getIstituzione().getNome());
		} else {
			session.put("idIstituto", null);
			session.put("istituto", null);
			session.put("altaRisoluzione", 0);
			session.put("bagit", 0);
			session.put("pIva", "");
		}
		session.put("idUtente", utenti.getDatiUtente().getId());
		session.put("utente", utenti.getDatiUtente().getCognome()+
				" "+
				utenti.getDatiUtente().getNome());
		session.put("amministratore", utenti.getDatiUtente().getAmministratore()+"");
		try {
			mdUtentiDAO = new MDUtentiDAO();
			mdUtenti = mdUtentiDAO.findById(utenti.getDatiUtente().getId());
			if (mdUtenti != null){
				session.put("superAdmin", mdUtenti.getSuperAdmin()+"");
			}
		} catch (HibernateException e) {
		} catch (HibernateUtilException e) {
		}
		ActionContext.getContext().setSession(session);

	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean showMenu(){
		Boolean result = false;
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (session.get("logined") != null &&
				session.get("logined").equals("true")){
			result = true;
		}
		return result;
	}

	public Boolean showMenuAdmin(){
		Boolean result = false;
		if (amministratore.trim().equals("1")){
			result = true;
		}
		return result;
	}

  public Boolean showMenuDepositanti(){
    return showMenuIstituti(0);
  }

  public Boolean showMenuBiblioteche(){
    return showMenuIstituti(1);
  }

  public Boolean showMenuIstituti(int bibliotecaDepositaria){
    Boolean result = false;
    MDIstituzioneDAO mdIstituzioneDAO = null;
    MDIstituzione mdIstituzione = null;

    try {
      if (amministratore.trim().equals("1") &&
          idIstituto==null){
        result = true;
      } else if (idIstituto!=null){
        mdIstituzioneDAO = new MDIstituzioneDAO();
        mdIstituzione = mdIstituzioneDAO.findById(idIstituto);
        if (mdIstituzione != null &&
            mdIstituzione.getBibliotecaDepositaria()==bibliotecaDepositaria) {
          result = true;
        }
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (HibernateUtilException e) {
      e.printStackTrace();
    }
    return result;
  }

  public Boolean showMenuAdminMD(){
    Boolean result = false;
    if (amministratore.trim().equals("1") &&
        idIstituto==null){
      result = true;
    }
    return result;
  }

	public Boolean showMenuSuperAdmin(){
		Boolean result = false;
		if (superAdmin != null && superAdmin.trim().equals("1")){
			result = true;
		}
		return result;
	}

	public Boolean showMenuAltaRisoluzione(){
		Boolean result = false;
		if (altaRisoluzione == 1){
			result = true;
		}
		return result;
	}

	public Boolean showMenuBagit(){
		Boolean result = false;
		if (bagit == 1){
			result = true;
		}
		return result;
	}

	public String getIdIstituto() {
		return idIstituto;
	}

	public void setIdIstituto(String idIstituto) {
		this.idIstituto = idIstituto;
	}

	public String getUrlAltaRisoluzione(){
		try {
			return LoginAction.mdConfiguration.getSoftwareConfigString("urlAltaRisoluzione");
		} catch (MDConfigurationException e) {
			return "";
		}
	}

	public String getIstituto() {
		if ((istituto==null || istituto.trim().equals("")) && idIstituto==null){
			return "[Magazzini Digitali]";
		} else {
			return istituto;
		}
	}

	public void setIstituto(String istituto) {
		this.istituto = istituto;
	}

	/**
	 * @return the idUtente
	 */
	public String getIdUtente() {
		return idUtente;
	}

	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	/**
	 * @return the utente
	 */
	public String getUtente() {
		return utente;
	}

	/**
	 * @param utente the utente to set
	 */
	public void setUtente(String utente) {
		this.utente = utente;
	}

	/**
	 * @return the amministratore
	 */
	public String getAmministratore() {
		return amministratore;
	}

	/**
	 * @param amministratore the amministratore to set
	 */
	public void setAmministratore(String amministratore) {
		this.amministratore = amministratore;
	}

	/**
	 * @return the logout
	 */
	public String getLogout() {
		return logout;
	}

	/**
	 * @param logout the logout to set
	 */
	public void setLogout(String logout) {
		this.logout = logout;
	}

	/**
	 * @return the superAdmin
	 */
	public String getSuperAdmin() {
		return superAdmin;
	}

	/**
	 * @param superAdmin the superAdmin to set
	 */
	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}
}
