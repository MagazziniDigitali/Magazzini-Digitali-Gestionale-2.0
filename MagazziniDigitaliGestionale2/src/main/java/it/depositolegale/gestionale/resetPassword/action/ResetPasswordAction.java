/**
 * 
 */
package it.depositolegale.gestionale.resetPassword.action;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionSupport;

import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.MDUtentiDAO;
import it.bncf.magazziniDigitali.database.entity.MDUtenti;
import it.bncf.magazziniDigitali.utils.email.SendEmail;
import it.bncf.magazziniDigitali.utils.password.PassGen;
import it.depositolegale.gestionale.user.action.LoginAction;
import mx.randalf.hibernate.exception.HibernateUtilException;
import mx.randalf.tools.SHA256Tools;


/**
 * @author massi
 *
 */
public class ResetPasswordAction extends ActionSupport {

	private Logger log = Logger.getLogger(ResetPasswordAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1911658378052845272L;

	private static String RESETPASSWORD ="resetPassword";

	/**
	 * Indica l'utente identificato oppure richiesto di essere autenticato
	 */
	private String username = null;

	/**
	 * Indica la password indicata
	 */
	private String email = null;

	/**
	 * 
	 */
	public ResetPasswordAction() {
	}

	@Override
	public String execute() throws Exception {
		if (hasActionErrors()){
			return ERROR;
		} else {
			if ((username != null && !username.trim().equals("")) &&
					(email != null && !email.trim().equals(""))){
				if (resetPassword()){
					return LOGIN;
				} else {
					return RESETPASSWORD;
				}
			} else {
				return RESETPASSWORD;
			}
		}
	}

	private boolean resetPassword() throws HibernateException, NoSuchAlgorithmException, HibernateUtilException, 
			MDConfigurationException, MessagingException {
		MDUtentiDAO mdUtentiDAO = null;
		List<MDUtenti> mdUtentis = null;
		MDUtenti mdUtenti = null;
		String password = null;
		String newPassword = null;
		SHA256Tools sha256Tools = null;
		SendEmail sendEmail = null;
		boolean result = false;

		try {
			mdUtentiDAO = new MDUtentiDAO();
			mdUtentis = mdUtentiDAO.find(username, null, null, email, null);

			for (int x=0; x<mdUtentis.size(); x++){
				if (mdUtentis.get(x).getLogin().trim().equals(username) && 
						mdUtentis.get(x).getEmail().trim().equalsIgnoreCase(email)){
					mdUtenti = mdUtentis.get(x);
					break;
				}
			}
			if (mdUtenti != null){
				password = PassGen.generateSessionKey();
				sha256Tools = new SHA256Tools();
				newPassword = new String(sha256Tools.checkSum(password.getBytes()));
				mdUtenti.setPassword(newPassword);
				mdUtentiDAO.update(mdUtenti);
				sendEmail = new SendEmail(LoginAction.mdConfiguration.getSoftwareConfigString("send.email.login"),
						LoginAction.mdConfiguration.getSoftwareConfigString("send.email.password"));
				sendEmail.sendMsg(mdUtenti.getEmail(), 
						"Magazzini Digitali - Reset Password", 
						sendEmail.corpoMsg(
						"<br/>Gentile "+mdUtenti.getNome()+" "+mdUtenti.getCognome()+",<br/>"
						+ "</br/>come richiesto, abbiamo provveduto a modificare la sua password.<br/>"
						+ "Di seguito trover&agrave; la nuova password.<br/>"+
						"<br/>"
						+ "Passowrd: <b>"+password+"</b><br/>"
						+ "<br/>"
						+ "Per qualsiasi informazione pu&ograve; contattarci all'indirizzo <a href=\"mailto:info@depositolegale.it\">info@depositolegale.it</a>.<br/>" + 
						"<br/>"
						));
				addActionMessage("Come richiesto la password è stata cambiata, a breve riceverà per email la nuova password");
				result = true;
			} else {
				addActionError("Le credenziali inserite non sono valide. Si prega di controllare e riprovare");
			}
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			addActionError(e.getMessage());
			throw e;
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			addActionError(e.getMessage());
			throw e;
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			addActionError(e.getMessage());
			throw e;
		} catch (MDConfigurationException e) {
			log.error(e.getMessage(), e);
			addActionError(e.getMessage());
			throw e;
		} catch (MessagingException e) {
			log.error(e.getMessage(), e);
			addActionError(e.getMessage());
			throw e;
		}
		return result;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
