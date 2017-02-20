/**
 * 
 */
package it.depositolegale.gestionale.home.action;

import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.businessLogic.oggettoDigitale.OggettoDigitaleBusiness;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.dao.MDStatoDAO;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.bncf.magazziniDigitali.database.entity.MDStato;
import it.depositolegale.gestionale.user.action.LoginAction;
import it.depositolegale.utenti.MDUtenti;
import it.depositolegale.www.errorMsg.ErrorType_type;
import it.depositolegale.www.utenti.Utenti;
import mx.randalf.hibernate.exception.HibernateUtilException;

/**
 * @author massi
 *
 */
public class HomeAction extends LoginAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4694613504321042884L;

	private Logger log = Logger.getLogger(HomeAction.class);

	private TreeMap<String, Long> cruscotto = null;

	public static String INITTRASF		= "INITTRASF";
	public static String FINETRASF		= "FINETRASF";
	public static String ERRORTRASF		= "ERRORTRASF";

	public static String INITVALID		= "INITVALID";
	public static String FINEVALID		= "FINEVALID";
	public static String ERRORVAL		= "ERRORVAL";

	public static String INITPUBLISH	= "INITPUBLISH";
	public static String FINEPUBLISH	= "FINEPUBLISH";
	public static String ERRORDECOMP	= "ERRORDECOMP";
	public static String ERRORCOPY		= "ERRORCOPY";
	public static String ERRORMOVE		= "ERRORMOVE";
	public static String ERRORPUB		= "ERRORPUB";
	public static String ERRORDELETE	= "ERRORDELETE";

	public static String INITARCHIVE	= "INITARCHIVE";
	public static String FINEARCHIVE	= "FINEARCHIVE";
	public static String ERRORARCHIVE	= "ERRORARCHIVE";

	public static String INITINDEX		= "INITINDEX";
	public static String CHECKINDEX		= "CHECKINDEX";
	public static String FINEINDEX		= "FINEINDEX";
	public static String ERRORINDEX		= "ERRORINDEX";
	
	/**
	 * 
	 */
	public HomeAction() {
		super();
    }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		MDUtenti mdUtenti = new MDUtenti(); 
		HttpServletRequest request = null;
		String ipClient  = null;
		Utenti utenti = null;
		
		result = super.execute();
		
		if (!result.equals(HOME) && !result.equals(ERROR)){
			if (username != null){
				
				request = ServletActionContext.getRequest();
				
				//is client behind something?
				ipClient = request.getHeader("X-FORWARDED-FOR");
				if (ipClient == null) {
					ipClient = request.getRemoteAddr();
				}
				utenti = mdUtenti.checkUtenti(LoginAction.mdConfiguration, ipClient, username, password);
				if (utenti != null){
					if (utenti.getErrorMsg() != null &&
							utenti.getErrorMsg().length>0){
						for (int x=0;x<utenti.getErrorMsg().length; x++){
							if (utenti.getErrorMsg()[x].getErrorType().equals(ErrorType_type.LOGINERROR) ||
									utenti.getErrorMsg()[x].getErrorType().equals(ErrorType_type.PASSWORDERROR)){
								addFieldError("username", getText("username.required"));
								addFieldError("password", getText("password.required"));
							} else {
								addActionError(utenti.getErrorMsg()[x].getMsgError());
							}
						}
						result = LOGIN;
					} else {
						initSession(utenti);
						readSession(ActionContext.getContext().getSession());

						result = HOME;
					}
				} else {
					addActionError(getText("authentication.faild"));
					result = LOGIN;
				}
			}
		}
		if (result.equals(HOME)){
			readCruscotto();
		}
		return result;
	}

	private void readCruscotto(){
		OggettoDigitaleBusiness odb = null;
		MDIstituzioneDAO mdIstituzioneDAO = null;
		MDIstituzione mdIstituzione = null;
		
		try {
			odb = new OggettoDigitaleBusiness();
			if (idIstituto != null){
				mdIstituzioneDAO = new MDIstituzioneDAO();
				mdIstituzione = mdIstituzioneDAO.findById(idIstituto);
			}
			cruscotto = odb.findStatus(mdIstituzione);
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (HibernateUtilException e) {
			e.printStackTrace();
		}

	}

	private Integer getCrustotto(String key){
		Integer result = 0;
		if (cruscotto != null){
			if (cruscotto.get(key)!= null){
				result = cruscotto.get(key).intValue();
			}
		}
		return result;
	}

	public Integer getInitTrasf(){
		return getCrustotto(INITTRASF);
	}

	public String getInitTrasfClass(){
		return (getInitTrasf().intValue()==0?"":"tbArancione");
	}

	public Integer getFineTrasf(){
		return getCrustotto(FINETRASF);
	}

	public String getFineTrasfClass(){
		return (getFineTrasf().intValue()==0?"":"tbGiallo");
	}

	public Integer getErrorTrasf(){
		return getCrustotto(ERRORTRASF);
	}

	public String getErrorTrasfClass(){
		return (getErrorTrasf().intValue()==0?"":"tbRosso");
	}

	public Integer getInitValid(){
		return getCrustotto(INITVALID);
	}

	public String getInitValidClass(){
		return (getInitValid().intValue()==0?"":"tbArancione");
	}

	public Integer getFineValid(){
		return getCrustotto(FINEVALID);
	}

	public String getFineValidClass(){
		return (getFineValid().intValue()==0?"":"tbGiallo");
	}

	public Integer getErrorVal(){
		return getCrustotto(ERRORVAL);
	}

	public String getErrorValClass(){
		return (getErrorVal().intValue()==0?"":"tbRosso");
	}

	public Integer getInitPublish(){
		return getCrustotto(INITPUBLISH);
	}

	public String getInitPublishClass(){
		return (getInitPublish().intValue()==0?"":"tbArancione");
	}

	public Integer getFinePublish(){
		return getCrustotto(FINEPUBLISH);
	}

	public String getFinePublishClass(){
		return (getFinePublish().intValue()==0?"":"tbVerde");
	}

	public Integer getErrorDecomp(){
		return getCrustotto(ERRORDECOMP);
	}

	public String getErrorDecompClass(){
		return (getErrorDecomp().intValue()==0?"":"tbRosso");
	}

	public Integer getErrorCopy(){
		return getCrustotto(ERRORCOPY);
	}

	public String getErrorCopyClass(){
		return (getErrorCopy().intValue()==0?"":"tbRosso");
	}

	public Integer getErrorMove(){
		return getCrustotto(ERRORMOVE);
	}

	public String getErrorMoveClass(){
		return (getErrorMove().intValue()==0?"":"tbRosso");
	}

	public Integer getErrorPub(){
		return getCrustotto(ERRORPUB);
	}

	public String getErrorPubClass(){
		return (getErrorPub().intValue()==0?"":"tbRosso");
	}

	public Integer getErrorDelete(){
		return getCrustotto(ERRORDELETE);
	}

	public String getErrorDeleteClass(){
		return (getErrorDelete().intValue()==0?"":"tbRosso");
	}

	public Integer getInitArchive(){
		return getCrustotto(INITARCHIVE);
	}

	public String getInitArchiveClass(){
		return (getInitArchive().intValue()==0?"":"tbArancione");
	}

	public Integer getFineArchive(){
		return getCrustotto(FINEARCHIVE);
	}

	public String getFineArchiveClass(){
		return (getFineArchive().intValue()==0?"":"tbVerde");
	}

	public Integer getErrorArchive(){
		return getCrustotto(ERRORARCHIVE);
	}

	public String getErrorArchiveClass(){
		return (getErrorArchive().intValue()==0?"":"tbRosso");
	}

	public Integer getInitIndex(){
		return getCrustotto(INITINDEX);
	}

	public String getInitIndexClass(){
		return (getInitIndex().intValue()==0?"":"tbArancione");
	}

	public Integer getCheckIndex(){
		return getCrustotto(CHECKINDEX);
	}

	public String getCheckIndexClass(){
		return (getCheckIndex().intValue()==0?"":"tbArancione");
	}

	public Integer getFineIndex(){
		return getCrustotto(FINEINDEX);
	}

	public String getFineIndexClass(){
		return (getFineIndex().intValue()==0?"":"tbVerde");
	}
	
	public Integer getErrorIndex(){
		return getCrustotto(ERRORINDEX);
	}

	public String getErrorIndexClass(){
		return (getErrorIndex().intValue()==0?"":"tbRosso");
	}
	
	public List<MDStato> getStato(){
		MDStatoDAO mdStatoDAO = null;
		List<MDStato> mdStatos = null;
		List<Order> orders = null;
		
		try {
			mdStatoDAO = new MDStatoDAO();
			
			orders = new Vector<Order>();
			orders.add(Order.asc("sequenza"));
			mdStatos = mdStatoDAO.findAll(orders);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
		}
		return mdStatos;
	}
}
