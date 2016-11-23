/**
 * 
 */
package it.depositolegale.gestionale.software.action;

import it.depositolegale.gestionale.user.action.LoginAction;

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
}
