package it.depositolegale.gestionale.configDefaults.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;

import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.configDefaults.MDConfigDefaultsBusiness;
import it.bncf.magazziniDigitali.database.dao.MDIstituzioneDAO;
import it.bncf.magazziniDigitali.database.entity.MDConfigDefaults;
import it.bncf.magazziniDigitali.database.entity.MDIstituzione;
import it.depositolegale.gestionale.servlet.BasicTabServlet;
import mx.randalf.hibernate.exception.HibernateUtilException;

public class TabConfigDefaults extends BasicTabServlet<MDConfigDefaultsBusiness, MDConfigDefaults> {

  /**
   * 
   */
  private static final long serialVersionUID = -3003407619316248179L;

  @Override
  protected String checkSortKey(String string) {
    return string;
  }

  @Override
  protected MDConfigDefaultsBusiness newInstanceBusiness() {
    return new MDConfigDefaultsBusiness();
  }

  @Override
  protected HashTable<String, Object> searchList(HttpServletRequest request) {
    HashTable<String, Object> dati = null;
    String searchname = null;
    MDIstituzioneDAO mdIstituzioneDAO = null;
    MDIstituzione mdIstituzione = null;

    try {
      searchname = request.getParameter("searchname");

      dati = new HashTable<String, Object>();
      if (searchname != null &&
          !searchname.trim().equals("")){
        dati.put("name", searchname.trim());
      }

      
      if (ActionContext.getContext().getSession().get("idIstituto")!=null){
        mdIstituzioneDAO = new MDIstituzioneDAO();
        mdIstituzione = mdIstituzioneDAO.findById((String) ActionContext.getContext().getSession().get("idIstituto"));
        if (mdIstituzione.getBibliotecaDepositaria()==0) {
          dati.put("tipoIstituto", new String[] {"E","D"});
        } else {
          dati.put("tipoIstituto", new String[] {"E","B"});
        }
        dati.put("idIstituzione", mdIstituzione);
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (HibernateUtilException e) {
      e.printStackTrace();
    }
    
    return dati;
  }

  @Override
  protected HashTable<String, Object> campiUpdate(HttpServletRequest request)
      throws HibernateException, HibernateUtilException {
    HashTable<String, Object> dati = null;

    dati = new HashTable<String, Object>();

    if (request.getParameter("id") != null) {
      dati.put("id", request.getParameter("id"));
    }

    if (request.getParameter("name") != null) {
      dati.put("name", request.getParameter("name"));
    }

    if (request.getParameter("tipoIstituto") != null) {
      dati.put("tipoIstituto", request.getParameter("tipoIstituto"));
    }

    return dati;
  }

  @Override
  protected String resultOptions(List<MDConfigDefaults> list) {
    String result = "";

    if (list != null && list.size()>0){
      for (int x=0; x<list.size(); x++){
        
        result += (x>0?", ":"");
        result +="{\"Value\":\""+list.get(x).getId()+"\", \"DisplayText\":\""+list.get(x).getName()+"\"}";
      }
    }
    return result;
  }

}
