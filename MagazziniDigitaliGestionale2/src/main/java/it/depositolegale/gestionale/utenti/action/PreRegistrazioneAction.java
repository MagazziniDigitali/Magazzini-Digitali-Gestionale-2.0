/**
 * 
 */
package it.depositolegale.gestionale.utenti.action;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.database.dao.MDPreRegistrazioneDAO;
import it.bncf.magazziniDigitali.database.entity.MDPreRegistrazione;
import it.depositolegale.gestionale.home.action.preIscrizione.ValidatePreIscrizione;
import it.depositolegale.gestionale.user.action.LoginAction;
import it.depositolegale.gestionale.verifica.preRegistrazione.elab.ElabGoogle;

/**
 * @author massi
 *
 */
public class PreRegistrazioneAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String PREREGISTRAZIONE = "preRegistrazione";

	private String action = null;

	private String emailValidata = null;

	private String id = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public PreRegistrazioneAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		String login = null;
		String password = null;
		String urlConfirm = null;
		ElabGoogle elabGoogle = null;
		ValidatePreIscrizione validatePreIscrizione = null;
		MDPreRegistrazioneDAO mdPreRegistrazioneDAO = null;
		MDPreRegistrazione mdPreRegistrazione = null;
		Timestamp newDate = null;
		HashTable<String, Object> dati = null;
		
		result = super.execute();
		login = mdConfiguration.getSoftwareConfigString("send.email.login");
		password = mdConfiguration.getSoftwareConfigString("send.email.password");
		urlConfirm = mdConfiguration.getSoftwareConfigString("url.validate");
		
		if (result.equals(HOME)){
			if (action != null &&
					action.equals("change")){
				if (emailValidata != null && id != null){
					newDate = new Timestamp(new GregorianCalendar().getTimeInMillis());

					mdPreRegistrazioneDAO = new MDPreRegistrazioneDAO();
					mdPreRegistrazione = mdPreRegistrazioneDAO.findById(id);
					if (mdPreRegistrazione != null){

						if (emailValidata.equals("0")){
							elabGoogle = new ElabGoogle(null, null, login, password, urlConfirm);
							elabGoogle.sendMsg(id, 
									mdPreRegistrazione.getUtenteEmail(), 
									mdPreRegistrazione.getUtenteNome(), 
									mdPreRegistrazione.getUtenteCognome(), 
									urlConfirm);
							mdPreRegistrazione.setDataPreIscrizione(newDate);
							mdPreRegistrazioneDAO.save(mdPreRegistrazione);
						} else if (emailValidata.equals("-1")){
							validatePreIscrizione = new ValidatePreIscrizione( null, 
									null, 
									mdConfiguration.getSoftwareConfigString("send.email.emailAdmin"), 
									mdConfiguration.getSoftwareConfigString("url.homeGestionale"), 
									urlConfirm, login, password);
							dati = new HashTable<String, Object>();
							dati.put("id", id);
							validatePreIscrizione.confirmFase1(dati, mdPreRegistrazione, urlConfirm);
						} else if (emailValidata.equals("-2")){
							validatePreIscrizione = new ValidatePreIscrizione( null, 
									null, 
									mdConfiguration.getSoftwareConfigString("send.email.emailAdmin"), 
									mdConfiguration.getSoftwareConfigString("url.homeGestionale"), 
									urlConfirm, login, password);
							dati = new HashTable<String, Object>();
							dati.put("id", id);
							validatePreIscrizione.confirmFase2(dati, mdPreRegistrazione);
						}
					}
				}
			}
			result = PREREGISTRAZIONE;
		}
		return result;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the emailValidata
	 */
	public String getEmailValidata() {
		return emailValidata;
	}

	/**
	 * @param emailValidata the emailValidata to set
	 */
	public void setEmailValidata(String emailValidata) {
		this.emailValidata = emailValidata;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
