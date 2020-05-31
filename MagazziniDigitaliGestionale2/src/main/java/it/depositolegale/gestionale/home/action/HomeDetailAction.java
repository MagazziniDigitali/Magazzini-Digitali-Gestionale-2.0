/**
 * 
 */
package it.depositolegale.gestionale.home.action;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.database.dao.MDFilesTmpDAO;
import it.bncf.magazziniDigitali.database.dao.MDStatoDAO;
import it.bncf.magazziniDigitali.database.entity.MDFilesTmp;
import it.depositolegale.gestionale.user.action.LoginAction;
import mx.randalf.hibernate.exception.HibernateUtilException;

/**
 * @author massi
 *
 */
public class HomeDetailAction extends LoginAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;

	private static String HOMEDETAIL="homeDetail";

	private Logger log = LogManager.getLogger(HomeDetailAction.class);

	private String idMdFilesTmp = null;

	private String resetMovi = null;

	private MDFilesTmp  mdFilesTmp = null;

	/**
	 * 
	 */
	public HomeDetailAction() {
		super();
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		MDFilesTmpDAO  mdFilesTmpDAO = null;
		MDStatoDAO mdStatoDAO = null;
		
		try {
			result = super.execute();
			
			if (result.equals(HOME)){
				if (idMdFilesTmp != null){
					mdFilesTmpDAO = new MDFilesTmpDAO();
					mdFilesTmp = mdFilesTmpDAO.findById(idMdFilesTmp);
					
					if (resetMovi != null){
						
						if (mdFilesTmp.getStato().getId().equals("ERRORARCHIVE") ||
								mdFilesTmp.getStato().getId().equals("ERRORCOPY") ||
								mdFilesTmp.getStato().getId().equals("ERRORDECOMP") ||
								mdFilesTmp.getStato().getId().equals("ERRORINDEX") ||
								mdFilesTmp.getStato().getId().equals("ERRORMOVE") ||
								mdFilesTmp.getStato().getId().equals("ERRORPUB") ||
								mdFilesTmp.getStato().getId().equals("ERRORTRASF") ||
								mdFilesTmp.getStato().getId().equals("ERRORVAL")){

							mdStatoDAO = new MDStatoDAO();
							if (mdFilesTmp.getStato().getId().equals("ERRORTRASF")){
								mdFilesTmp.setStato(mdStatoDAO.INITTRASF());
							} else if (mdFilesTmp.getStato().getId().equals("ERRORVAL") || 
									mdFilesTmp.getStato().getId().equals("ERRORDECOMP")){
								mdFilesTmp.setStato(mdStatoDAO.INITVALID());
							} else if (mdFilesTmp.getStato().getId().equals("ERRORCOPY") ||
									mdFilesTmp.getStato().getId().equals("ERRORMOVE") ||
									mdFilesTmp.getStato().getId().equals("ERRORPUB")){
								mdFilesTmp.setStato(mdStatoDAO.INITPUBLISH());
							} else if (mdFilesTmp.getStato().getId().equals("ERRORARCHIVE")){
								mdFilesTmp.setStato(mdStatoDAO.INITARCHIVE());
							} else if (mdFilesTmp.getStato().getId().equals("ERRORINDEX")){
								mdFilesTmp.setStato(mdStatoDAO.INITINDEX());
							}

							mdFilesTmpDAO.update(mdFilesTmp);
						}
					}
					
					if (mdFilesTmp != null &&
							mdFilesTmp.getId().equals(idMdFilesTmp)){
						result = HOMEDETAIL;
						ActionContext.getContext().getSession().put("idMdFilesTmp",idMdFilesTmp);
					} else {
						result = LOGIN;
						ActionContext.getContext().getSession().put("logined",null);
						addActionError(getText("homeDetail.mdMdFilesTmp.notPresent",new String[]{idMdFilesTmp}));
					}
				} else {
					result = LOGIN;
					ActionContext.getContext().getSession().put("logined",null);
					addActionError(getText("homeDetail.idMdFilesTmp.notPresent"));
				}
			}
		} catch (HibernateException e) {
			log.error(e.getMessage(),e);
			throw e;
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(),e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
		return result;
	}

	private String convert(String value){
		if (value!= null){
			return value;
		} else {
			return "";
		}
	}

	public static String convert(Date value){
		GregorianCalendar gc = null;
		String result = null;
		DecimalFormat df2 = new DecimalFormat("00");
		DecimalFormat df4 = new DecimalFormat("0000");

		if (value!= null){
			gc = new GregorianCalendar();
			gc.setTimeInMillis(value.getTime());
			
			result = df2.format(gc.get(Calendar.DAY_OF_MONTH));
			result += "/";
			result += df2.format(gc.get(Calendar.MONTH)+1);
			result += "/";
			result += df4.format(gc.get(Calendar.YEAR));
			result += " ";
			result += df2.format(gc.get(Calendar.HOUR_OF_DAY));
			result += ":";
			result += df2.format(gc.get(Calendar.MINUTE));
			result += ":";
			result += df2.format(gc.get(Calendar.SECOND));
			result += ".";
			result += gc.get(Calendar.MILLISECOND);
			return result;
		} else {
			return "";
		}
	}

	private String convert(Boolean value){
		if (value!= null){
			if (value){
				return "Positivo";
			} else {
				return "Negativo";
			}
		} else {
			return "";
		}
	}

	public String getId(){
		return mdFilesTmp.getId();
	}

	public String getNomeIstituto(){
		if (mdFilesTmp.getIdIstituto() != null){
			return mdFilesTmp.getIdIstituto().getNome();
		} else {
			return "";
		}
	}

	public String getNomeSoftware(){
		if (mdFilesTmp.getIdSoftware() != null){
			return mdFilesTmp.getIdSoftware().getNome();
		} else {
			return "";
		}
	}

	public String getStato(){
		return mdFilesTmp.getStato().getDescrizione();
	}

	public String getStatoError(){
		String result ="No";
		if (mdFilesTmp.getStato().getId().equals("ERRORARCHIVE") ||
				mdFilesTmp.getStato().getId().equals("ERRORCOPY") ||
				mdFilesTmp.getStato().getId().equals("ERRORDECOMP") ||
				mdFilesTmp.getStato().getId().equals("ERRORINDEX") ||
				mdFilesTmp.getStato().getId().equals("ERRORMOVE") ||
				mdFilesTmp.getStato().getId().equals("ERRORPUB") ||
				mdFilesTmp.getStato().getId().equals("ERRORTRASF") ||
				mdFilesTmp.getStato().getId().equals("ERRORVAL")){
			result="Si";
		}
			
		return result;
	}
	public String getNomeFile(){
		return convert(mdFilesTmp.getNomeFile());
	}

	public String getSha1(){
		return convert(mdFilesTmp.getSha1());
	}

	public String getNomeFileMod(){
		return convert(mdFilesTmp.getNomeFileMod());
	}

	public String getXmlMimeType(){
		return mdFilesTmp.getXmlMimeType();
	}

	public String getPremisFile(){
		return convert(mdFilesTmp.getPremisFile());
	}

	public String getIdNodo(){
		return convert(mdFilesTmp.getIdNodo().getNome());
	}

	public String getIndexPremis(){
		return convert(mdFilesTmp.getIndexPremis());
	}

	public String getTrasfDataStart(){
		return convert(mdFilesTmp.getTrasfDataStart());
	}

	public String getTrasfDataEnd(){
		return convert(mdFilesTmp.getTrasfDataEnd());
	}

	public String getValidDataStart(){
		return convert(mdFilesTmp.getValidDataStart());
	}

	public String getValidDataEnd(){
		return convert(mdFilesTmp.getValidDataEnd());
	}

	public String getValidEsito(){
		return convert(mdFilesTmp.getValidEsito());
	}

	public String getDecompDataStart(){
		return convert(mdFilesTmp.getDecompDataStart());
	}

	public String getDecompDataEnd(){
		return convert(mdFilesTmp.getDecompDataEnd());
	}

	public String getDecompEsito(){
		return convert(mdFilesTmp.getDecompEsito());
	}

	public String getPublishDataStart(){
		return convert(mdFilesTmp.getPublishDataStart());
	}

	public String getPublishDataEnd(){
		return convert(mdFilesTmp.getPublishDataEnd());
	}

	public String getPublishEsito(){
		return convert(mdFilesTmp.getPublishEsito());
	}

	public String getCopyPremisDataStart(){
		return convert(mdFilesTmp.getCopyPremisDataStart());
	}

	public String getCopyPremisDataEnd(){
		return convert(mdFilesTmp.getCopyPremisDataEnd());
	}

	public String getCopyPremisEsito(){
		return convert(mdFilesTmp.getCopyPremisEsito());
	}

	public String getMoveFileDataStart(){
		return convert(mdFilesTmp.getMoveFileDataStart());
	}

	public String getMoveFileDataEnd(){
		return convert(mdFilesTmp.getMoveFileDataEnd());
	}

	public String getMoveFileEsito(){
		return convert(mdFilesTmp.getMoveFileEsito());
	}

	public String getArchiveDataStart(){
		return convert(mdFilesTmp.getArchiveDataStart());
	}

	public String getArchiveDataEnd(){
		return convert(mdFilesTmp.getArchiveDataEnd());
	}

	public String getArchiveEsito(){
		return convert(mdFilesTmp.getArchiveEsito());
	}

	public String getIndexDataStart(){
		return convert(mdFilesTmp.getIndexDataStart());
	}

	public String getIndexDataEnd(){
		return convert(mdFilesTmp.getIndexDataEnd());
	}

	public String getIndexEsito(){
		return convert(mdFilesTmp.getIndexEsito());
	}

	public String getDeleteLocalData(){
		return convert(mdFilesTmp.getDeleteLocalData());
	}

	public String getDeleteLocalEsito(){
		return convert(mdFilesTmp.getDeleteLocalEsito());
	}

	/**
	 * @return the idMdFilesTmp
	 */
	public String getIdMdFilesTmp() {
		return idMdFilesTmp;
	}

	/**
	 * @param idMdFilesTmp the idMdFilesTmp to set
	 */
	public void setIdMdFilesTmp(String idMdFilesTmp) {
		this.idMdFilesTmp = idMdFilesTmp;
	}

	/**
	 * @return the resetMovi
	 */
	public String getResetMovi() {
		return resetMovi;
	}

	/**
	 * @param resetMovi the resetMovi to set
	 */
	public void setResetMovi(String resetMovi) {
		this.resetMovi = resetMovi;
	}
}
