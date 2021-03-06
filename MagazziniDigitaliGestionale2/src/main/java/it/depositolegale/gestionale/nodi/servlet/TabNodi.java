package it.depositolegale.gestionale.nodi.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.nodi.MDNodiBusiness;
import it.bncf.magazziniDigitali.database.entity.MDNodi;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabNodi extends BasicTabServlet<MDNodiBusiness, MDNodi> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabNodi() {
	}



	@Override
	protected MDNodiBusiness newInstanceBusiness() {
		return new MDNodiBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		HashTable<String, Object> dati = null;
		String searchname = null;

		searchname = request.getParameter("search");

		dati = new HashTable<String, Object>();
		if (searchname != null &&
				!searchname.trim().equals("")){
			dati.put("nome", searchname.trim());
		}

		searchname = request.getParameter("searchDescrizione");

		if (searchname != null &&
				!searchname.trim().equals("")){
			dati.put("descrizione", searchname.trim());
		}
		return dati;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException{
		HashTable<String, Object> dati = null;

		dati = new HashTable<String, Object>();

		if (request.getParameter("id") != null) {
			dati.put("id", request.getParameter("id"));
		}

		if (request.getParameter("nome") != null) {
			dati.put("nome", request.getParameter("nome"));
		}

		if (request.getParameter("descrizione") != null) {
			dati.put("descrizione", request.getParameter("descrizione"));
		}

		if (request.getParameter("tipo") != null) {
			dati.put("tipo", request.getParameter("tipo"));
			if (request.getParameter("tipo").equals("F")) {
				if (request.getParameter("pathStorage") != null) {
					dati.put("pathStorage", request.getParameter("pathStorage"));
				}
			} else if (request.getParameter("tipo").equals("M")) {
				if (request.getParameter("rsync") != null) {
					dati.put("rsync", request.getParameter("rsync"));
				}

				if (request.getParameter("rsyncPassword") != null) {
					dati.put("rsyncPassword", request.getParameter("rsyncPassword"));
				}

				if (request.getParameter("urlCheckStorage") != null) {
					dati.put("urlCheckStorage", request.getParameter("urlCheckStorage"));
				}
			} else if (request.getParameter("tipo").equals("S")) {
				if (request.getParameter("s3Url") != null) {
					dati.put("s3Url", request.getParameter("s3Url"));
				}

				if (request.getParameter("s3Region") != null) {
					dati.put("s3Region", request.getParameter("s3Region"));
				}

				if (request.getParameter("s3AccessKey") != null) {
					dati.put("s3AccessKey", request.getParameter("s3AccessKey"));
				}

				if (request.getParameter("s3SecretKey") != null) {
					dati.put("s3SecretKey", request.getParameter("s3SecretKey"));
				}

				if (request.getParameter("s3BucketName") != null) {
					dati.put("s3BucketName", request.getParameter("s3BucketName"));
				}
			}
		}


		if (request.getParameter("active") != null) {
			dati.put("active", new Integer(request.getParameter("active")));
		} else {
			dati.put("active", 0);
		}

		return dati;
	}



	@Override
	protected String resultOptions(List<MDNodi> list) {
		String result = "";
		if (list != null && list.size()>0){
			for (int x=0; x<list.size(); x++){
				result += (x>0?", ":"");
				result +="{\"Value\":\""+list.get(x).getId()+"\", \"DisplayText\":\""+list.get(x).getNome()+" ("+list.get(x).getDescrizione()+")\"}";
			}
		}
		return result;
	}

	@Override
	protected String checkSortKey(String string) {
		return string;
	}
}