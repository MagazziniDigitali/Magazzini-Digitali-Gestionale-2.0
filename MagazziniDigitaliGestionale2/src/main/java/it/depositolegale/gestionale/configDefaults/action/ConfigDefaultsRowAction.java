/**
 * 
 */
package it.depositolegale.gestionale.configDefaults.action;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.database.dao.MDConfigDefaultsDAO;
import it.bncf.magazziniDigitali.database.entity.MDConfigDefaults;
import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class ConfigDefaultsRowAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String CONFIGDEFAULTSROW = "configDefaultsRow";

	/**
	 * Indica l'utente identificato oppure richiesto di essere autenticato
	 */
	private String idConfigDefaults = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public ConfigDefaultsRowAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		MDConfigDefaultsDAO mdConfigDefaultsDAO = null;
		MDConfigDefaults mdConfigDefaults = null;
		
		result = super.execute();
		
		if (result.equals(HOME)){
			if (idConfigDefaults != null){
			  mdConfigDefaultsDAO = new MDConfigDefaultsDAO();
				
			  mdConfigDefaults = mdConfigDefaultsDAO.findById(idConfigDefaults);
				if (mdConfigDefaults != null && mdConfigDefaults.getId().equals(idConfigDefaults)){
					result = CONFIGDEFAULTSROW;
					ActionContext.getContext().getSession().put("idConfigDefaults", idConfigDefaults);
					ActionContext.getContext().getSession().put("configDefaults", mdConfigDefaults.getName());
					ActionContext.getContext().getSession().put("mdConfigDefaults", mdConfigDefaults);
				} else {
					result = LOGIN;
					ActionContext.getContext().getSession().put("logined",null);
					addActionError(getText("configDefaultsRow.mdConfigDefaults.notPresent",new String[]{idConfigDefaults}));
				}
			} else {
				result = LOGIN;
				ActionContext.getContext().getSession().put("logined",null);
				addActionError(getText("configDefaultsRow.idConfigDefaults.notPresent"));
			}
		}
		return result;
	}

  public String getIdConfigDefaults() {
    return idConfigDefaults;
  }

  public void setIdConfigDefaults(String idConfigDefaults) {
    this.idConfigDefaults = idConfigDefaults;
  }
}
