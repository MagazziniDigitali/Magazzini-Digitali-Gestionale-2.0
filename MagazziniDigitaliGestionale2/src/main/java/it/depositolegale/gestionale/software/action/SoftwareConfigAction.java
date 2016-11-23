/**
 * 
 */
package it.depositolegale.gestionale.software.action;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.database.dao.MDSoftwareDAO;
import it.bncf.magazziniDigitali.database.entity.MDSoftware;
import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class SoftwareConfigAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String SOFTWARECONFIG = "softwareConfig";

	/**
	 * Indica l'utente identificato oppure richiesto di essere autenticato
	 */
	private String idSoftware = null;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public SoftwareConfigAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		MDSoftwareDAO mdSoftwareDAO = null;
		MDSoftware mdSoftware = null;
		
		result = super.execute();
		
		if (result.equals(HOME)){
			if (idSoftware != null){
				mdSoftwareDAO = new MDSoftwareDAO();
				
				mdSoftware = mdSoftwareDAO.findById(idSoftware);
				if (mdSoftware != null && mdSoftware.getId().equals(idSoftware)){
					result = SOFTWARECONFIG;
					ActionContext.getContext().getSession().put("idSoftware", idSoftware);
					ActionContext.getContext().getSession().put("software", mdSoftware.getNome());
					ActionContext.getContext().getSession().put("mdSoftware", mdSoftware);
				} else {
					result = LOGIN;
					ActionContext.getContext().getSession().put("logined",null);
					addActionError(getText("softwareConfig.mdSoftware.notPresent",new String[]{idSoftware}));
				}
			} else {
				result = LOGIN;
				ActionContext.getContext().getSession().put("logined",null);
				addActionError(getText("softwareConfig.idSoftware.notPresent"));
			}
		}
		return result;
	}

	/**
	 * @return the idSoftware
	 */
	public String getIdSoftware() {
		return idSoftware;
	}

	/**
	 * @param idSoftware the idSoftware to set
	 */
	public void setIdSoftware(String idSoftware) {
		this.idSoftware = idSoftware;
	}
}
