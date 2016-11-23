/**
 * 
 */
package it.depositolegale.gestionale.home.servlet;

import java.io.Serializable;

import org.apache.log4j.Logger;

import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.entity.MDFilesTmp;
import it.depositolegale.gestionale.user.action.LoginAction;

/**
 * @author massi
 *
 */
public class MDFileTmpTab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1743362002559938710L;

	private Logger log = Logger.getLogger(MDFileTmpTab.class);

	private String id;

	private String nomeFile;

	private String statoName;

	private String statoJob;

	/**
	 * 
	 */
	public MDFileTmpTab() {
		
	}

	public void init(MDFilesTmp mdFilesTmp) {
		this.id = mdFilesTmp.getId();
		this.nomeFile = mdFilesTmp.getNomeFile();
		try {
			if (mdFilesTmp.getStato() != null){
				this.statoName = mdFilesTmp.getStato().getDescrizione();
			}

			statoJob = LoginAction.mdConfiguration.getStatoJob("Job_" + mdFilesTmp.getId(), mdFilesTmp.getId());
		} catch (MDConfigurationException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the nomeFile
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @return the statoName
	 */
	public String getStatoName() {
		return statoName;
	}

	/**
	 * @return the statoJob
	 */
	public String getStatoJob() {
		return statoJob;
	}
}
