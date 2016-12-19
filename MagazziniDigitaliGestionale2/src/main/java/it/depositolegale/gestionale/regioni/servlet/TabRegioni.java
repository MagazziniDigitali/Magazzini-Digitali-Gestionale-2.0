package it.depositolegale.gestionale.regioni.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.regioni.RegioniBusiness;
import it.bncf.magazziniDigitali.database.entity.Regioni;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabRegioni extends BasicTabServlet<RegioniBusiness, Regioni> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566932431859834359L;

	public TabRegioni() {
	}



	@Override
	protected RegioniBusiness newInstanceBusiness() {
		return new RegioniBusiness();
	}

	@Override
	protected HashTable<String, Object> searchList(HttpServletRequest request) {
		return null;
	}

	@Override
	protected HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException {

		return null;
	}

	@Override
	protected String resultOptions(List<Regioni> list) {
		String result = "";
		if (list != null && list.size()>0){
			for (int x=0; x<list.size(); x++){
				result += (x>0?", ":"");
				result +="{\"Value\":\""+list.get(x).getId()+"\", \"DisplayText\":\""+list.get(x).getNomeRegione()+"\"}";
			}
		}
		return result;
	}
}