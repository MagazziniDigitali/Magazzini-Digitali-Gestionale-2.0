/**
 * 
 */
package it.depositolegale.gestionale.altaRisoluzione.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class AltaRisoluzioneAction extends LoginAction {

	private Logger log = LogManager.getLogger(AltaRisoluzioneAction.class);

	/**
	 * Utilizzato per la indicare le pagine di Login
	 */
	public static String ALTARISOLUZIONE = "altaRisoluzione";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;
	
	/**
	 * 
	 */
	public AltaRisoluzioneAction() {
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		result = super.execute();
		
		if (result.equals(HOME)){
			result = ALTARISOLUZIONE;
		}
		return result;
	}

	public String getUrlAuthentication(){
		String url = "";
		
		try {
			url = mdConfiguration.getSoftwareConfigString("AuthenticationSoftwarePort");
		} catch (MDConfigurationException e) {
			log.error(e.getMessage(), e);
		}
		return url;
	}

	public String getSoftwareLogin(){
		return (pIva==null || pIva.trim().equals("")?"<b>[Indicare l'identificativo del programma]</b>":"TD_"+pIva);
	}
}
