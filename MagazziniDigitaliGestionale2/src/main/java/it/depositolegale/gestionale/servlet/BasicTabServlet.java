package it.depositolegale.gestionale.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.bncf.magazziniDigitali.businessLogic.BusinessLogic;
import it.bncf.magazziniDigitali.businessLogic.HashTable;
import it.bncf.magazziniDigitali.businessLogic.exception.BusinessLogicException;
import it.bncf.magazziniDigitali.configuration.exception.MDConfigurationException;
import it.magazziniDigitali.xsd.premis.exception.PremisXsdException;
import mx.randalf.hibernate.exception.HibernateUtilException;
import mx.randalf.xsd.exception.XsdException;

/**
 * Classe di Base per la gestione delle Servlet delle chiamate della tabelle Ajax
 * @author massi
 *
 * @param <B>
 * @param <T>
 */
public abstract class BasicTabServlet<B extends BusinessLogic<?, ?, ?>, T extends Serializable > extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5985661054281034999L;

	/** 
	 * Variabile utilizzata per loggare la classe
	 */
	private Logger log = Logger.getLogger(BasicTabServlet.class);

	/**
	 * Costruttore della classe
	 */
	public BasicTabServlet() {
		
	}

	/**
	 * Metodo invocato dalle chiamate Get alla Servlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Metodo invocato dalle chiamate Post alla Servlet
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = null;

		action = request.getParameter("action");
		if (action != null) {
			response.setContentType("application/json");
			if (action.equals("list")) {
				list(request, response);
			} else if (action.equals("create") ||
						action.equals("update")) {
				update(request, response);
			} else if (action.equals("delete")) {// Delete record
				delete(request, response);
			} else if (action.equals("options")){
				options(request, response);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void options(HttpServletRequest request, HttpServletResponse response) throws IOException {
		B business = null;
		List<T> list = null;
		String result = "";
		HashTable<String, Object> dati = null;
		
		try {
			business = newInstanceBusiness();
			dati = searchList(request);
			list = (List<T>) business.find(dati, 0, 0);
			result = resultOptions(list);
			response.getWriter().print("{\"Result\":\"OK\", \"Options\":[{\"Value\":\"\", \"DisplayText\":\"\"}, "+result+"]}");
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
			response.getWriter().print(error);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
			response.getWriter().print(error);
//		} catch (ConfigurationException e) {
//			log.error(e.getMessage(), e);
//			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
//			response.getWriter().print(error);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
			response.getWriter().print(error);
		}

	}


	/**
	 * Metodo utilizzato per rispondere alle chiamate di list 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		B business = null;
		int startPageIndex = 0;
		int recordsPerPage = 0;
		String jtSorting = null;
		String[] st = null;
		HashTable<String, Object> dati = null;
		String jsonArray = null;
		
		try {
			// Fetch Data from User Table
			startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
			recordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
			if (startPageIndex==0){
				startPageIndex=1;
			} else {
				startPageIndex = (startPageIndex/recordsPerPage)+1;
			}
			jtSorting = request.getParameter("jtSorting");

			business = newInstanceBusiness();

			if (jtSorting != null && jtSorting.trim().length() > 0 && jtSorting.trim().indexOf(" ") > -1) {
				st = jtSorting.trim().split(" ");
				business.addOrder(st[0], st[1]);
			}
			
			dati = searchList(request);

			// Fetch Data from Student Table
//			list = (List<T>) business.find(dati, startPageIndex, recordsPerPage);
			jsonArray = find(business, dati, startPageIndex, recordsPerPage);

			// Get Total Record Count for Pagination
			int userCount = business.rowsCount(dati).intValue();


			// Return Json in the format required by jTable plugin
			jsonArray = "{\"Result\":\"OK\",\"Records\":" + jsonArray + ",\"TotalRecordCount\":" + userCount + "}";
			response.getWriter().print(jsonArray);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
			response.getWriter().print(error);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
			response.getWriter().print(error);
		} catch (HibernateUtilException e) {
			log.error(e.getMessage(), e);
			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
			response.getWriter().print(error);
//		} catch (ConfigurationException e) {
//			log.error(e.getMessage(), e);
//			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "}";
//			response.getWriter().print(error);
		}
	}

	@SuppressWarnings("unchecked")
	protected String find(B business, HashTable<String, Object> dati,
			int startPageIndex, int recordsPerPage) throws HibernateException, HibernateUtilException{
		List<T> list = null;
		Gson gson = null;
		String jsonArray = null;
		
		try {
			list = (List<T>) business.find(dati, startPageIndex, recordsPerPage);
			
			gson = new GsonBuilder().setPrettyPrinting().create();
			jsonArray = convertJson(business, list);
			if (jsonArray ==null){
				// Convert Java Object to Json
				jsonArray = gson.toJson(list);
			}
		} catch (SecurityException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new HibernateException(e.getMessage(), e);
		} catch (BusinessLogicException e) {
			throw new HibernateException(e.getMessage(), e);
		}

		return jsonArray;
	}

	protected String convertJson(B business, List<T> lists)
			throws SecurityException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, BusinessLogicException {
		String jsonArray = null;
		boolean primo = true;

		try {
			jsonArray = "[\n";
			if (lists != null){
				for (T list : lists){
					
					jsonArray +=(primo?"":",\n");
					primo=false;
					jsonArray += business.toJson((T)list);
				}
			}
			jsonArray += "\n]\n";
		} catch (SecurityException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (InvocationTargetException e) {
			throw e;
		} catch (BusinessLogicException e) {
			e.printStackTrace();
			throw e;
		}

		return jsonArray;
	}
	
	/**
	 * Metodo utilizzato per la gestione delle attività di inserimento e modifica di una
	 * determinata tablla
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		B business = null;
		HashTable<String, Object> dati = null;
		Gson gson = null;
		T table = null;
		String id = null;
		String jsonArray = null;

		try {
			business = newInstanceBusiness();
			checkPreUpdate(business, request);

			dati = campiUpdate(request);

			id = (String) business.save(dati);

			postUpdate(id, dati);
			gson = new GsonBuilder().setPrettyPrinting().create();

			table = (T) business.findById(id);

			// Convert Java Object to Json
			jsonArray = business.toJson(table);
			if (jsonArray ==null){
				// Convert Java Object to Json
				jsonArray = gson.toJson(table);
			}

			// Return Json in the format required by jTable plugin
			String jsonData = "{\"Result\":\"OK\",\"Record\":" + jsonArray + "}";
			response.getWriter().print(jsonData);
		} catch (IllegalAccessException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (NoSuchMethodException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (HibernateException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (NamingException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (HibernateUtilException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (IOException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (MDConfigurationException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (PremisXsdException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (XsdException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (SecurityException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (IllegalArgumentException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		} catch (BusinessLogicException e) {
			String error = "{\"Result\":\"ERROR\",\"Message\":\"" + e.getMessage() + "\"}";
			response.getWriter().print(error);
		}

	}

	protected void checkPreUpdate(B business, HttpServletRequest request)  throws HibernateException, HibernateUtilException {
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Integer studentId = null;
		// try{
		// if (request.getParameter("studentId") != null) {
		// studentId = Integer.parseInt(request
		// .getParameter("studentId"));
		//// dao.deleteStudent(studentId);
		// String jsonData = "{\"Result\":\"OK\"}";
		// response.getWriter().print(jsonData);
		// }
		// } catch (IOException e) {
		// String error = "{\"Result\":\"ERROR\",\"Message\":" + e.getMessage()+
		// "}";
		// response.getWriter().print(error);
		// }
	}
	
	protected abstract B newInstanceBusiness();

	protected abstract HashTable<String, Object> searchList(HttpServletRequest request);

	protected abstract HashTable<String, Object> campiUpdate(HttpServletRequest request) throws HibernateException, HibernateUtilException;

	protected abstract String resultOptions(List<T> list);

	protected void postUpdate(String id, HashTable<String, Object> dati)
			throws MDConfigurationException, PremisXsdException, XsdException, IOException {
		// TODO Auto-generated method stub
		
	}

}