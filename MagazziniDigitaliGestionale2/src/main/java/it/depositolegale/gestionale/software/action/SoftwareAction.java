/**
 * 
 */
package it.depositolegale.gestionale.software.action;

import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.depositolegale.gestionale.user.action.LoginAction;
import mx.randalf.hibernate.exception.HibernateUtilException;

/**
 * @author massi
 *
 */
public class SoftwareAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String SOFTWARE = "software";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public SoftwareAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		result = super.execute();
		
		if (result.equals(HOME)){
			result = SOFTWARE;
		}
		return result;
	}

	public Boolean isEditDepositante() {
	  Boolean result = false;
	  MDIstituzioneDAO mdIstituzioneDAO = null;
    MDIstituzione mdIstituzione = null;

    try {
      if (ActionContext.getContext().getSession().get("idIstituto")!=null){
        mdIstituzioneDAO = new MDIstituzioneDAO();
        mdIstituzione = mdIstituzioneDAO.findById((String) ActionContext.getContext().getSession().get("idIstituto"));
        if (mdIstituzione.getBibliotecaDepositaria()==0) {
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

  public Boolean isMD() {
    Boolean result = false;
    if (ActionContext.getContext().getSession().get("idIstituto")==null){
      result = true;
    }
    return result;
  }

  public Boolean isEditBiblioteche() {
    Boolean result = false;
    MDIstituzioneDAO mdIstituzioneDAO = null;
    MDIstituzione mdIstituzione = null;

    try {
      if (ActionContext.getContext().getSession().get("idIstituto")!=null){
        mdIstituzioneDAO = new MDIstituzioneDAO();
        mdIstituzione = mdIstituzioneDAO.findById((String) ActionContext.getContext().getSession().get("idIstituto"));
        if (mdIstituzione.getBibliotecaDepositaria()==1) {
          result = true;
        }
      } else {
        result = true;
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (HibernateUtilException e) {
      e.printStackTrace();
    }
    return result;
	}
}
