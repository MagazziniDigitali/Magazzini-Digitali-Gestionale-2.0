/**
 * 
 */
package it.depositolegale.gestionale.modalitaAccesso.action;

import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class ModalitaAccessoAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String MODALITAACCESSO = "modalitaAccesso";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public ModalitaAccessoAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		result = super.execute();
		
		if (result.equals(HOME)){
			result = MODALITAACCESSO;
		}
		return result;
	}
}
