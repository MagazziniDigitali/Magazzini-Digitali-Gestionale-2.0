/**
 * 
 */
package it.depositolegale.gestionale.compositionLevel.action;

import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class CompositionLevelAction extends LoginAction {

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String COMPOSITIONLEVEL = "compositionLevel";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public CompositionLevelAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		result = super.execute();
		
		if (result.equals(HOME)){
			result = COMPOSITIONLEVEL;
		}
		return result;
	}
}
