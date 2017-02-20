package it.depositolegale.gestionale.home.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.filesTmp.MDFilesTmpErrorBusiness;
import it.bncf.magazziniDigitali.database.dao.MDFilesTmpDAO;
import it.bncf.magazziniDigitali.database.entity.MDFilesTmpError;
import it.depositolegale.gestionale.home.action.HomeDetailAction;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabHomeDetail extends BasicTabServlet<MDFilesTmpErrorBusiness, MDFilesTmpError> {

	private Logger log = Logger.getLogger(TabHomeDetail.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabHomeDetail() {
	}



	@Override
	protected MDFilesTmpErrorBusiness newInstanceBusiness() {
		return new MDFilesTmpErrorBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		MDFilesTmpDAO mdFilesTmpDAO = null;

		try {

			dati = new HashTable<String, Object>();
			mdFilesTmpDAO = new MDFilesTmpDAO();
			dati.put("idMdFilesTmp", mdFilesTmpDAO.findById((String)ActionContext.getContext().getSession().get("idMdFilesTmp")));
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
		}
		
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException{

		return null;
	}

	@Override
	protected String resultOptions(List<MDFilesTmpError> list) {
		return null;
	}

	/**
	 * @see it.depositolegale.gestionale.servlet.BasicTabServlet#find(it.bncf.magazziniDigitali.businessLogic.BusinessLogic, it.bncf.magazziniDigitali.businessLogic.HashTable, int, int)
	 */
	@Override
	protected String find(MDFilesTmpErrorBusiness business, HashTable<String, Object> dati, int startPageIndex,
			int recordsPerPage) throws HibernateException, HibernateUtilException {
		List<MDFilesTmpError> mdFilesTmps = null;
		String jsonArray = "";
		boolean primo=true;
		
		mdFilesTmps = business.find(dati, startPageIndex, recordsPerPage);
		jsonArray += "[\n";
		if (mdFilesTmps != null){
			for (MDFilesTmpError mdFilesTmp : mdFilesTmps){
				
				jsonArray +=(primo?"":",\n");
				primo=false;
				jsonArray += "  {\n";
				jsonArray += "    \"id\": \""+mdFilesTmp.getId()+"\",\n";
				jsonArray += "    \"dataIns\": \""+HomeDetailAction.convert(mdFilesTmp.getDataIns())+"\",\n";
				jsonArray += "    \"type\": \""+(mdFilesTmp.getType().getDescrizione()==null?"":mdFilesTmp.getType().getDescrizione().replace("\"", "\\\""))+"\",\n";
				jsonArray += "    \"msgError\": \""+(mdFilesTmp.getMsgError()==null?"":mdFilesTmp.getMsgError().replace("\"", "\\\""))+"\"\n";
				jsonArray += "  }";
			}
		}
		jsonArray += "\n]\n";
//		System.out.println(jsonArray);
		return jsonArray;
	}
}