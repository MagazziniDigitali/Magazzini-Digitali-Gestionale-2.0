/**
 * 
 */
package it.depositolegale.gestionale.changePassword.action;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.database.dao.MDUtentiDAO;
import it.bncf.magazziniDigitali.database.entity.MDUtenti;
import it.depositolegale.gestionale.user.action.LoginAction;
import mx.randalf.hibernate.exception.HibernateUtilException;
import mx.randalf.tools.SHA256Tools;

/**
 * @author massi
 *
 */
public class ChangePasswordAction extends LoginAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4679026894073510080L;

	private Logger log = LogManager.getLogger(ChangePasswordAction.class);

	private String CHANGEPASSWORD = "changePassword";

	private String oldPassword = null;

	private String newPassword = null;

	private String confPassword = null;

	/**
	 * 
	 */
	public ChangePasswordAction() {
		super();
    }
	
	@Override
	public String execute() throws Exception 
	{
		String result = "";
		MDUtentiDAO mdUtentiDAO = null;
		MDUtenti mdUtenti = null; 
		SHA256Tools sha256Tools = null;
		String newPwd = null;
		
		result = super.execute();
		
		if (result.equals(HOME)){

			result = CHANGEPASSWORD;
			try {
				if (oldPassword != null &&
						newPassword != null  &&
						confPassword != null){
					if (newPassword.equals(confPassword)){
						mdUtentiDAO = new MDUtentiDAO();
						mdUtenti = mdUtentiDAO.findById(idUtente);
						if (mdUtenti!= null){
							sha256Tools = new SHA256Tools();
							newPwd = new String(sha256Tools.checkSum(oldPassword.getBytes()));
							if (mdUtenti.getPassword().equals(newPwd)){
								newPwd = new String(sha256Tools.checkSum(newPassword.getBytes()));
								mdUtenti.setPassword(newPwd);
								mdUtentiDAO.update(mdUtenti);
								addActionMessage("La password è stata cambiata regolarmente");
							} else {
								addFieldError("oldPassword", getText("oldPassword.error"));
							}
						} else {
							addActionError(getText("utente.notFound"));
						}
					} else {
						addFieldError("confPassword", getText("confPassword.error"));
					}
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
			}
		}
		return result;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confPassword
	 */
	public String getConfPassword() {
		return confPassword;
	}

	/**
	 * @param confPassword the confPassword to set
	 */
	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}
}
