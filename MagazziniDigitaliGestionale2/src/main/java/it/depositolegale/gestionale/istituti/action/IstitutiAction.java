/**
 * 
 */
package it.depositolegale.gestionale.istituti.action;

import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class IstitutiAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String ISTITUTI = "istituti";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public IstitutiAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		result = super.execute();
		
		if (result.equals(HOME)){
			result = ISTITUTI;
		}
		return result;
	}
}
