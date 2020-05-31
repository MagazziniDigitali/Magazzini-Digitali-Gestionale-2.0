package it.depositolegale.gestionale.rigths.servlet;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.oggettoDigitale.implement.OggettoDigitale;
import it.bncf.magazziniDigitali.businessLogic.rigths.MDRigthsBusiness;
import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.bncf.magazziniDigitali.database.dao.MDModalitaAccessoDAO;
import it.bncf.magazziniDigitali.database.entity.MDModalitaAccesso;
import it.bncf.magazziniDigitali.database.entity.MDRigths;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import it.depositolegale.gestionale.user.action.LoginAction;
import it.magazziniDigitali.xsd.premis.exception.PremisXsdException;
import it.magazziniDigitali.xsd.rights.RightsXsd;
import mx.randalf.hibernate.exception.HibernateUtilException;
import mx.randalf.xsd.exception.XsdException;

public class TabRigths extends BasicTabServlet<MDRigthsBusiness, MDRigths> {

	private Logger log = LogManager.getLogger(TabRigths.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabRigths() {
	}



	@Override
	protected MDRigthsBusiness newInstanceBusiness() {
		return new MDRigthsBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("searchname");

		dati = new HashTable<String, Object>();
		if (searchname != null &&
				!searchname.trim().equals("")){
			dati.put("nome", searchname.trim());
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException {
		HashTable<String, Object> dati = null;
		MDModalitaAccessoDAO mdModalitaAccessoDAO = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("nome") != null) {
			dati.put("nome", request.getParameter("nome"));
		}

		if (request.getParameter("idModalitaAccessoID") != null) {
			mdModalitaAccessoDAO = new MDModalitaAccessoDAO();
			dati.put("idModalitaAccesso", mdModalitaAccessoDAO.findById(request.getParameter("idModalitaAccessoID")));
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDRigths> list) {
		String result = "";
		if (list != null && list.size()>0){
			for (int x=0; x<list.size(); x++){
				result += (x>0?", ":"");
				result +="{\"Value\":\""+list.get(x).getId()+"\", \"DisplayText\":\""+list.get(x).getNome()+"\"}";
			}
		}
		return result;
	}

	/**
	 * @throws MDConfigurationException 
	 * @see it.depositolegale.gestionale.servlet.BasicTabServlet#postUpdate(java.lang.String, it.bncf.magazziniDigitali.businessLogic.HashTable)
	 */
	@Override
	protected void postUpdate(String id, HashTable<String, Object> dati) 
			throws MDConfigurationException, PremisXsdException, XsdException, IOException {
		RightsXsd<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> rightsXsd = null;
		File filePremis = null;
		GregorianCalendar gc = new GregorianCalendar();
		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df3 = new DecimalFormat("000");
		DecimalFormat df2 = new DecimalFormat("00");
		String agId = null;

		super.postUpdate(id, dati);

		try {
//			if (createPremis){
				agId = id+"-"+
						df4.format(gc.get(Calendar.YEAR))+
						df2.format(gc.get(Calendar.MONTH)+1)+
						df2.format(gc.get(Calendar.DAY_OF_MONTH))+
						df2.format(gc.get(Calendar.HOUR_OF_DAY))+
						df2.format(gc.get(Calendar.MINUTE))+
						df2.format(gc.get(Calendar.SECOND))+
						df3.format(gc.get(Calendar.MILLISECOND));
				rightsXsd = RightsXsd.initialize();
//				
				
				if(((MDModalitaAccesso)dati.get("idModalitaAccesso")).getId().equals("A")){
					rightsXsd.addRightsStatementComplexTypeAltaRisoluzione(id, null);
				} else if(((MDModalitaAccesso)dati.get("idModalitaAccesso")).getId().equals("B")){
					rightsXsd.addRightsStatementComplexTypeAccessoAperto(id);
				} else if(((MDModalitaAccesso)dati.get("idModalitaAccesso")).getId().equals("C")){
					rightsXsd.addRightsStatementComplexTypeProtettoLicenza(id, null);
				}

				filePremis = new File(
						OggettoDigitale.genFilePremis(
								LoginAction.mdConfiguration.getSoftwareConfigString("path.premis"), 
								"Rigths",
								agId,".premis"));

				rightsXsd.write(filePremis, false);
//			}
		} catch (MDConfigurationException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (PremisXsdException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (XsdException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	protected String checkSortKey(String string) {
		if (string.equals("idModalitaAccessoID")){
			string = "idModalitaAccesso.descrizione";
		}
		return string;
	}
}