/**
 * 
 */
package it.depositolegale.gestionale.configDefaults.action;

import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class ConfigDefaultsAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String CONFIGDEFAULTS = "configDefaults";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public ConfigDefaultsAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		result = super.execute();
		
		if (result.equals(HOME)){
			result = CONFIGDEFAULTS;
		}
		return result;
	}
}
